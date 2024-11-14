package com.tiger.crm.common.auth;

import com.tiger.crm.service.auth.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthService authService;

    /*
     * 회원이 특정 URL에 접근할 수 있는지 검사
     * userClass: 세션에서 가져온 권한 코드
     */
    public boolean checkAccessPermission(String userClass, String requestUrl) {
        try {

            // 1. 메뉴(url) 권한 조회
            String urlClassCode = authService.findAuthCodeByUrl(requestUrl, userClass);

            // 2. 매핑된 권한이 없을 경우, false반환
            if (urlClassCode == null) {
                LOGGER.warn("DB(T_AUTH_MANAGE)에 저장된 권한이 없습니다: {}", requestUrl);
                return false;
            }

            // 3. 세션 권한코드와, 권한 테이블의 코드 비교
            return urlClassCode != null && urlClassCode.equals(userClass);

        } catch (Exception e) {
            LOGGER.error("권한 체크중 오류 발생 {}: {}", requestUrl, e.getMessage());
            throw new RuntimeException("권한 체크중 오류 발생", e);
        }
    }
}
