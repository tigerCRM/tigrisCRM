package com.tiger.crm.service.login;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.repository.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.mail.MessagingException;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    LoginMapper loginMapper;

    @Autowired
    MailService mailService;

    @Override
    public UserLoginDto login(String id, String password) {
        return loginMapper.getUser(id , password);
    }

    // 비밀번호 초기화
    @Override
    public void resetPassword(UserLoginDto user) throws MessagingException {
        try {
            // 1. 임시 비밀번호 생성
            String tempPassword = generateTempPassword();

            user.setUserId("sys1@test.com");
            user.setPassword(tempPassword);

            // 2. 비밀번호 업데이트
            loginMapper.resetPassword(user);

            // 3. 메일 발송
            Map<String, Object> model = new HashMap<>();
            model.put("userName", "안상재");
            model.put("password", tempPassword);
            mailService.sendEmail("dkstkdwo93@tigrison.com", "비밀번호 초기화", "password-reset-email", model);

        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    // 비밀번호 초기화 (임시 비밀번호 생성 메서드)
    public String generateTempPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder tempPassword = new StringBuilder(10);

        for (int i = 0; i < 10; i++) {
            int index = random.nextInt(characters.length());
            tempPassword.append(characters.charAt(index));
        }
        return tempPassword.toString();
    }
}
