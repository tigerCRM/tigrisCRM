package com.tiger.crm.service.login;

import com.tiger.crm.repository.dto.user.User;
import com.tiger.crm.repository.mapper.LoginMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{
    @Autowired
    LoginMapper loginMapper;
    @Override
    public User login(String id, String password) {
        return loginMapper.getUser(id , password);
    }
}
