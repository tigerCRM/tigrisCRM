package com.tiger.crm.common.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class SameSiteCookieFilter implements Filter {

    /*
    * samesite 설정 위한 필터
    * 작성자 : 제예솔
    * 설명 : 세션 쿠키에 samesite 설정을 추가해 csrf 공격 방지
    * */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        // 모든 쿠키 가져오기
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // JSESSIONID 쿠키 찾기
                if ("JSESSIONID".equals(cookie.getName())) {
                    // 새 JSESSIONID 쿠키 설정 (SameSite 추가)
                    Cookie newCookie = new Cookie(cookie.getName(), cookie.getValue());
                    newCookie.setPath("/");
                    newCookie.setHttpOnly(true);
                    newCookie.setSecure(true); // HTTPS 환경에서만 동작
                    newCookie.setMaxAge(cookie.getMaxAge()); // 기존 만료 시간 유지
                    httpServletResponse.addCookie(newCookie);
                }
            }
        }

        // URL에 JSESSIONID가 포함된 경우 제거
        if (httpServletRequest.isRequestedSessionIdFromURL()) {
            String url = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(url);  // JSESSIONID 제거 후 리디렉트
            return;
        }

        // 필터 체인 진행
        chain.doFilter(request, response);
    }
}
