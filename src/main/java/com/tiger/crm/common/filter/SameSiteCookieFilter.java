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

        // 모든 쿠키를 가져옴
        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // JSESSIONID 쿠키를 찾음
                if ("JSESSIONID".equals(cookie.getName())) {
                    // SameSite 속성과 기타 속성을 설정
                    Cookie newCookie = new Cookie(cookie.getName(), cookie.getValue());
                    newCookie.setPath("/");
                    newCookie.setHttpOnly(true);
                    newCookie.setSecure(true); // HTTPS 환경일 경우
                    newCookie.setMaxAge(cookie.getMaxAge()); // 기존 쿠키 만료시간 유지
                    newCookie.setDomain(httpServletRequest.getServerName());
                    // SameSite 속성을 추가 (Lax 또는 Strict)
                    httpServletResponse.addHeader("Set-Cookie",
                            newCookie.getName() + "=" + newCookie.getValue()
                                    + "; Path=" + newCookie.getPath()
                                    + "; HttpOnly; Secure; SameSite=Lax");
                }
            }
        }

        // 필터 체인 진행
        chain.doFilter(request, response);
    }
}
