package com.tiger.crm.common.session;

import com.tiger.crm.common.auth.AuthorizationService;
import com.tiger.crm.repository.dto.user.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 URL 가져오기
        String requestUrl = request.getRequestURI();

        // 세션에서 userId를 조회
        User user = (User) request.getSession().getAttribute("loginUser");

        // user가 없거나 세션에서 로그인된 정보가 없으면 로그인 페이지로 리다이렉트
        if (user == null || user.getUserId() == null) {
            response.sendRedirect("/login");
            return false;
        }

        // 메뉴별 권한 체크 수행
        boolean hasAccess = authorizationService.checkAccessPermission(user.getUserClass(), requestUrl);

        // 권한이 없을 경우 접근 금지 처리
        if (!hasAccess) {
            // response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);  // 403 상태 코드 반환
            response.setContentType("text/html; charset=UTF-8");  // 응답 내용 타입과 문자 인코딩 설정
            response.getWriter().write("<script>alert('접근권한이 없습니다.'); window.history.back();</script>");
            response.getWriter().flush();
            return false;
        }

        // 권한이 있다면 요청을 계속 진행
        return true;
    }

}
