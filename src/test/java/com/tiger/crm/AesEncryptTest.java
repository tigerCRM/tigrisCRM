package com.tiger.crm;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.common.util.AESUtils;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.LoginMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.crypto.SecretKey;
import java.util.List;

@SpringBootTest
public class AesEncryptTest {

    @Autowired
    private ConfigProperties configProperties;
    String encryptedPhoneNumber = "VydNwkSjNYzug7DAKrQuXQ==";
    @Autowired
    private LoginMapper loginMapper;

    @Test
     void keyGenerator() throws Exception {
        SecretKey key = AESUtils.generateKey();
        String encodedKey = AESUtils.encodeKey(key);
        System.out.println("Encoded Secret Key: " + encodedKey);
    }

    @Test
    void encryptPhoneNumber() throws Exception {
        String testA = "010-9209-2667";
        encryptedPhoneNumber = AESUtils.encrypt(testA, AESUtils.decodeKey(configProperties.getAesSecretKey()));
        System.out.println("암호화된 데이터: " + encryptedPhoneNumber);
    }

    @Test
    void decryptPhoneNumber() throws Exception {
        System.out.println("암호화된 데이터: " + encryptedPhoneNumber);
        System.out.println("복호화된 데이터: " + AESUtils.decrypt(encryptedPhoneNumber, AESUtils.decodeKey(configProperties.getAesSecretKey())));
        System.out.println("일치여부 " + "010-9209-2667".equals(AESUtils.decrypt(encryptedPhoneNumber, AESUtils.decodeKey(configProperties.getAesSecretKey()))));
    }

    /*사용자 전화번호 암호화*/
    @Test
    @Transactional
    @Commit
    void updateAllPhoneNumber() throws Exception {
        List<UserLoginDto> users = loginMapper.getAllUsers();

        for (UserLoginDto user : users) {
            String plainPhone = user.getPhone(); // 기존 전화번호
            String encryptedPhone = AESUtils.encrypt(plainPhone, AESUtils.decodeKey(configProperties.getAesSecretKey()));
            // 비밀번호 업데이트
            loginMapper.updateUserPhone(user.getUserId(),encryptedPhone);
        }
    }
}
