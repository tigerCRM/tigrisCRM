package com.tiger.crm.service.login;

import com.tiger.crm.repository.dto.user.User;
import jakarta.mail.MessagingException;

public interface LoginService {

    public User login(String id, String password);

    // 비밀번호 초기화
    public void resetPassword(User user) throws MessagingException;
}
