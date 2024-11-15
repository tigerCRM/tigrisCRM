package com.tiger.crm.service.auth;

public interface AuthService {
    // 메뉴(url) 권한 조회
    public String findAuthCodeByUrl(String requestUrl, String userClass);
}
