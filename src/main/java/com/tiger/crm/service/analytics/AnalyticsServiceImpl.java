package com.tiger.crm.service.analytics;

import com.tiger.crm.common.util.DateUtils;
import com.tiger.crm.repository.dto.analytics.AnalyticsWeekDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.AnalyticsMapper;
import com.tiger.crm.service.common.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class AnalyticsServiceImpl implements AnalyticsService{

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private CommonService commonService;
    @Autowired
    private AnalyticsMapper analyticsMapper;
    
    //일주일 날짜 가져오기
    @Override
    public List<String> getDate(String startDate) {
        LocalDate localDate = (startDate == null || startDate.isEmpty())? LocalDate.now() : LocalDate.parse(startDate);
        return DateUtils.getWeekDates(localDate);
    }

    //매니저별 주간접수 현황 가져오기
    @Override
    public List<AnalyticsWeekDto> getAnalyticsWeek(String startDate) {
        List<String> weekDates = getDate(startDate);
        return analyticsMapper.getAnalyticsWeekReceipt(weekDates.get(0), weekDates.get(1),weekDates.get(2),weekDates.get(3),weekDates.get(4),weekDates.get(5),weekDates.get(6));
    }

}
