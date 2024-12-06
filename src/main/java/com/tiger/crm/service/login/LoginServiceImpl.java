package com.tiger.crm.service.login;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.repository.mapper.LoginMapper;
import com.tiger.crm.repository.mapper.MailMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private MailMapper mailMapper;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Override
    public UserLoginDto login(String id, String password) {
        if(!passwordEncoder.matches(password, loginMapper.getUserPwByUserId(id))){
            LOGGER.info("비밀번호 에러");
            return null;
        }
        return loginMapper.getUser(id , password);

    }

    // 비밀번호 초기화
    @Override
    public void resetPassword(UserLoginDto user) throws MessagingException {
        try {
            // 1. 임시 비밀번호 생성
            String tempPassword = generateTempPassword();
            user.setUserId(user.getUserId());
            user.setUserPw(passwordEncoder.encode(tempPassword)); // 암호화 후 저장

            // 2. 비밀번호 업데이트
            loginMapper.resetPassword(user);

            // 3. 메일 발송
            Map<String, Object> model = new HashMap<>();
            model.put("userName", user.getUserName());
            model.put("password", tempPassword);

            mailService.sendEmail(user.getUserId(), "비밀번호 초기화", "password-reset-email", model);

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
