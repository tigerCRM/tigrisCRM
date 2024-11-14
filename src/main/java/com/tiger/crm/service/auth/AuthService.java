package com.tiger.crm.service.auth;

import com.tiger.crm.repository.dto.user.User;

public interface AuthService {
    // 메뉴(url) 권한 조회
    public String findAuthCodeByUrl(String requestUrl, String userClass);
}
