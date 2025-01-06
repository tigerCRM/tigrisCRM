package com.tiger.crm.service.analytics;

import com.tiger.crm.repository.dto.analytics.AnalyticsWeekDto;

import java.util.List;

public interface AnalyticsService {
    List<AnalyticsWeekDto> getAnalyticsWeek();
}
