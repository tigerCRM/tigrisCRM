package com.tiger.crm;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.LoginMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;

import java.util.List;

@SpringBootTest
public class PasswordEncryptTest {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private LoginMapper loginMapper;

    @Test
    void passwordEnc() {
        System.out.println("testHello2");
        String pwd = "test12345";
        String encodedPwd = passwordEncoder.encode(pwd); //암호화 하는부분
        System.out.println(encodedPwd);

    }
    @Test
    void pwdMatch(){
        // 기존 저장해두었던 암호화된 비밀번호
        String encodedPwd = "{bcrypt}$2a$10$/sp4LuU4aqkEHoRlXQLjgeinyR36c5JVx3hhvkfL57OLoavN2TPpC";
        // 검증할 비밀번호
        String newPwd = "XpYjMHCoI8";
        if(passwordEncoder.matches(newPwd, encodedPwd)){
            System.out.println("true");
        }else{
            System.out.println("false");
        }
    }

    @Test
    @Transactional
    @Commit
    void updateAllPassword(){
        List<UserLoginDto> users = loginMapper.getAllUsers();

        for (UserLoginDto user : users) {
            String plainPassword = user.getUserPw(); // 기존 비밀번호
            String encryptedPassword = passwordEncoder.encode(plainPassword); // 암호화
            // 비밀번호 업데이트
            loginMapper.updateUserPassword(user.getUserId(),encryptedPassword);
        }

    }
}
