package com.tiger.crm.repository.mail;

import com.tiger.crm.common.exception.CustomException;
import com.tiger.crm.repository.dto.mail.MailDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mapper.MailMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

import java.util.List;
import java.util.Map;

@Service
public class MailService {

    @Autowired
    private MailMapper mailMapper;

    private final JavaMailSender mailSender;
    private final ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    public MailService(JavaMailSender mailSender, ThymeleafViewResolver thymeleafViewResolver) {
        this.mailSender = mailSender;
        this.thymeleafViewResolver = thymeleafViewResolver;
    }

    // 이메일 발송 메서드
    public void sendEmail(String to, String subject, String templateName, Map<String, Object> model) throws MessagingException {

        // 1. html 렌더링 생성
        String htmlContent = generateHtmlContent(templateName, model);

        // 2. 이메일 발송
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("ygnam@tigrison.com");       // 발송인(crm 관리자 계정으로 보낼거니까 고정)
            helper.setTo(to);                             // 수령인
            helper.setSubject("[타이거컴퍼니]" + subject);  // 제목
            helper.setText(htmlContent, true);       // html양식

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new CustomException("이메일 발송 중 오류가 발생했습니다.", e);
        }

        // 3. 메일 발송 이력 저장
        try {
            MailDto mail = new MailDto();
            mail.setCategory(templateName);             // 분류명
            mail.setTitle(subject);                     // 제목
            mail.setContent(htmlContent);               // html양식
            mail.setReceiverAddr(to);                   // 수령인
            mail.setSenderAddr("ygnam@tigrison.com"); // 발송인(crm 관리자 계정으로 보낼거니까 고정)

            mailMapper.saveMailHist(mail);

        } catch (Exception e){
            throw new CustomException("메일 발송 이력 저장 중 오류가 발생했습니다.", e);
        }
    }

    // 템플릿을 렌더링하는 메서드
    private String generateHtmlContent(String templateName, Map<String, Object> model) {
        try {
            // Map 데이터를 Thymeleaf Context 객체로 변환
            Context context = new Context();
            context.setVariables(model);

            // 템플릿 렌더링
            return thymeleafViewResolver.getTemplateEngine().process(templateName, context);

        } catch (Exception e) {
            throw new CustomException("메일 템플릿 렌더링 중 오류가 발생했습니다.", e);
        }
    }

    // 메일 발송 내역 조회
    public PagingResponse<Map<String, Object>> getMailHistList(PagingRequest pagingRequest) {
        try{
            // 전체 건수 조회
            int totalRecords = mailMapper.getMailHistListCount(pagingRequest);
            // 메일 발송 내역 조회
            List<Map<String, Object>> mailHistList = mailMapper.getMailHistList(pagingRequest);

            return new PagingResponse<>(mailHistList, totalRecords, pagingRequest);

        }catch (Exception e){
            throw new CustomException("메일 발송 내역 조회 중 오류가 발생했습니다.", e);
        }
    }


}
