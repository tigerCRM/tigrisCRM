package com.tiger.crm.repository.mail;

import com.tiger.crm.repository.dto.mail.MailDto;
import com.tiger.crm.repository.mapper.MailMapper;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.view.ThymeleafViewResolver;

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
        try {
            // MimeMessage 객체 생성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(to); // 수신자
            helper.setSubject(subject); // 제목 설정

            // Thymeleaf 템플릿 렌더링
            String htmlContent = generateHtmlContent(templateName, model);

            // 이메일 내용 설정 (HTML)
            helper.setText(htmlContent, true);
            helper.setFrom("dkstkdwo93@naver.com");  // 네이버 계정과 동일한 발신자 이메일 설정

            // 이메일 발송
            mailSender.send(message);

            // 메일 발송 이력 저장
            MailDto mail = new MailDto();
            mail.setCategory(templateName);
            mail.setTitle(subject);
            mail.setContent(htmlContent);
            mail.setSenderAddr("dkstkdwo93@tigrison.com");
            mail.setReceiverAddr(to);

            mailMapper.saveMailHist(mail);

        }catch (Exception e){
            System.err.println("Error sending email: " + e.getMessage());
            e.printStackTrace();
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
            throw new RuntimeException("Template rendering failed", e);
        }
    }
}
