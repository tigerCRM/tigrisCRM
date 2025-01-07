package com.tiger.crm.service.analytics;

import com.tiger.crm.repository.dto.analytics.AnalyticsWeekDto;

import java.util.List;

public interface AnalyticsService {
    //주간날짜 가져오기
    List<String> getDate(String startDate);

    //매니저별 주간접수 현황 가져오기
    List<AnalyticsWeekDto> getAnalyticsWeek(String startDate);

}
