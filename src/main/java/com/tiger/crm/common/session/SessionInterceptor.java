package com.tiger.crm.common.session;

import com.tiger.crm.common.auth.AuthorizationService;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 요청 URL 가져오기
        String requestUrl = request.getRequestURI();
        String redirectUrl = request.getParameter("redirect"); // redirect 파라미터 추출

        // 세션에서 userId를 조회
        UserLoginDto user = (UserLoginDto) request.getSession().getAttribute("loginUser");

        if (user == null) { // 로그인되지 않은 경우 처리
            // 세션에 리다이렉트 URL을 저장
            if (redirectUrl != null && !redirectUrl.isEmpty()) {
                request.getSession().setAttribute("redirectUrl", redirectUrl);
            }

            // 로그인 페이지로 리다이렉트
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
        if (user != null) {
            // 로그인 정보를 request attribute에 추가 (모든 컨트롤러에서 접근 가능)
            request.setAttribute("user", user);
        }

        // 권한이 있다면 요청을 계속 진행
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // 요청이 컨트롤러에서 처리된 후 (뷰를 렌더링하기 전)에 추가 작업이 필요하다면
        if (modelAndView != null) {
            Object user = request.getAttribute("user");
            if (user != null) {
                modelAndView.addObject("user", user);  // model에 추가
            }
        }
    }
}
