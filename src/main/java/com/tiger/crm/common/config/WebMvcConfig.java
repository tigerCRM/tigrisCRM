package com.tiger.crm.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/files/**")  // 요청 URL 패턴
                .addResourceLocations("file:/home/crmdevuser/files/")  // 실제 파일 경로
                .setCachePeriod(3600); // 캐시 설정 (1시간)
    }
}