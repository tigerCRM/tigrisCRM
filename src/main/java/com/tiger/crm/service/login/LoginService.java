package com.tiger.crm.service.login;

import com.tiger.crm.repository.dto.user.UserLoginDto;
import jakarta.mail.MessagingException;

public interface LoginService {

    public UserLoginDto login(String id, String password);

    // 비밀번호 초기화
    public void resetPassword(UserLoginDto user) throws MessagingException;
}
