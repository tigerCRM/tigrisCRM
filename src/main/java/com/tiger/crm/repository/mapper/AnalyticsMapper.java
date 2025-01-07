package com.tiger.crm.repository.mapper;

import com.tiger.crm.repository.dto.analytics.AnalyticsWeekDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Mapper
public interface AnalyticsMapper {

    //주간 접수 리스트 가져오기
    List<AnalyticsWeekDto> getAnalyticsWeekReceipt(String mon, String tue, String wed, String thu, String fri, String sat , String sun);
}
