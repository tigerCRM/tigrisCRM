//package com.example.crm.core.session;
//
//import com.example.crm.core.context.ConfigProperties;
//import jakarta.servlet.http.Cookie;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//public class SessionInterceptor extends HandlerInterceptorAdapter {
//
//	@Autowired private ConfigProperties config;
//
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//		Cookie[] cookies = request.getCookies();
//		if(cookies != null)
//		{
//			for(Cookie cookie : cookies)
//			{
//				if(cookie.getName().equals(config.getCookieName()))
//				{
//					return true;
//				}
//			}
//		}
//
//		response.sendRedirect("/disallowed");
//		return false;
//
//	}
//}
