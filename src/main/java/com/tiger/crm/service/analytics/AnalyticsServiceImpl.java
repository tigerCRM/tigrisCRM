package com.tiger.crm.service.analytics;

import com.tiger.crm.repository.dto.analytics.AnalyticsWeekDto;
import com.tiger.crm.repository.dto.analytics.DayReceiptAndClosedDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.repository.mapper.AnalyticsMapper;
import com.tiger.crm.repository.mapper.NoticeBoardMapper;
import com.tiger.crm.service.common.CommonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
@Service
public class AnalyticsServiceImpl implements AnalyticsService{

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    @Autowired
    private CommonService commonService;

    @Autowired
    private AnalyticsMapper analyticsMapper;
    @Override
    public List<AnalyticsWeekDto> getAnalyticsWeek() {

        List<AnalyticsWeekDto> analyticsWeek = new ArrayList<>();
        List<UserLoginDto> managers = commonService.getManagerList();

        for(int i = 0 ; i < managers.size() ; i ++){
            //analyticsWeek.set(i, new AnalyticsWeekDto(managers.get(i).getUserId(), managers.get(i).getUserName()));
        }
        LOGGER.info("test @@" + analyticsWeek);

        return null;
    }
}
