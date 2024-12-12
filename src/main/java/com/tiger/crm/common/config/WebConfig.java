package com.tiger.crm.common.config;

import com.tiger.crm.common.session.SessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@Component
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private SessionInterceptor sessionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 모든 URL 패턴에 대해 인터셉터 적용
		registry.addInterceptor(sessionInterceptor)
				.addPathPatterns("/**")      // 모든 요청에 대해 인터셉터 적용
				.excludePathPatterns(        // 예외 URL 설정
						"/login",            // 로그인 페이지
						"/logout",           // 로그아웃 호출
						"/error",            // 에러 페이지
						"/assets/**",        // 정적 자원 (CSS, JS, 이미지 등)
						"/js/**",			// 공통 JS
						"/lib/**",           // 추가: jQuery, 외부 라이브러리 등의 자원 경로
						"/public/**",        // 외부에 공개된 자원 1
						"/Style/**",		 // 외부에 공개된 자원 2
						"/favicon.ico"     // 파비콘
				);
	}
}
