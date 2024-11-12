package com.tiger.crm.service.login;

import com.tiger.crm.repository.dto.user.User;

public interface LoginService {

    public User login(String id, String password);
}
