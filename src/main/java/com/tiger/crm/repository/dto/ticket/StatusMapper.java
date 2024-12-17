package com.tiger.crm.repository.dto.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StatusMapper {

    private static Map<String, String> statusMap;
    private static Map<String, String> requestMap;
    private static Map<String, String> supportMap;
    // setting.properties의 값 주입
    @Value("${code.select.t_status}")
    private String statusConfig;

    @Value("${code.select.t_request}")
    private String requestConfig;

    @Value("${code.select.t_support}")
    private String supportConfig;

    @PostConstruct
    public void init() {
        if (statusConfig != null && !statusConfig.isEmpty()) {
            statusMap = Arrays.stream(statusConfig.split(","))
                    .map(entry -> entry.split(":"))
                    .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        }

        if (requestConfig != null && !requestConfig.isEmpty()) {
            requestMap = Arrays.stream(requestConfig.split(","))
                    .map(entry -> entry.split(":"))
                    .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        }
        if (supportConfig != null && !supportConfig.isEmpty()) {
            supportMap = Arrays.stream(supportConfig.split(","))
                    .map(entry -> entry.split(":"))
                    .collect(Collectors.toMap(entry -> entry[0], entry -> entry[1]));
        }
    }

    public static String getStatusText(String code) {
        if (statusMap == null) {
            return "알 수 없음";
        }
        return statusMap.getOrDefault(code, "알 수 없음");
    }

    public static String getRequestText(String code) {
        if (requestMap == null) {
            return "알 수 없음";
        }
        return requestMap.getOrDefault(code, "알 수 없음");
    }

    public static String getSupportText(String code) {
        if (supportMap == null) {
            return "알 수 없음";
        }
        return supportMap.getOrDefault(code, "알 수 없음");
    }
}