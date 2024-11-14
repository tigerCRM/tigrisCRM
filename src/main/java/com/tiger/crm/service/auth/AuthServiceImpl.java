package com.tiger.crm.service.auth;

import com.tiger.crm.repository.mapper.AuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    // 메뉴(url) 권한 조회
    @Override
    public String findAuthCodeByUrl(String requestUrl, String userClass) {
        return authMapper.findAuthCodeByMemberId(requestUrl, userClass);
    }
}
