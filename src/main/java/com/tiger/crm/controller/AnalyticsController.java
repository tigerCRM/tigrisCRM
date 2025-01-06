package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.repository.dto.analytics.AnalyticsWeekDto;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.analytics.AnalyticsService;
import com.tiger.crm.service.common.CommonService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class AnalyticsController {

    /*
    * 통계 현황
    * 작성자 : 제예솔
    * 설명 : 통계 페이지
    * */
    private final ConfigProperties config;
    private final CommonService commonService;
    private final AnalyticsService analyticsService;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    /*
    * 주간처리현황 페이지 보기
    * */
    @GetMapping("/analytics_week")
    public String getAnalyticsWeek(PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        HttpSession session = request.getSession(false);
        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");

        //고객사 리스트
        List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
        model.addAttribute("companyOptions", companyOptions);

        //줘야 하는 데이터..
        //{남유경, [12월 30일(접수 00, 처리 00), 12월 31일(접수 00, 처리 00)] }
        List<AnalyticsWeekDto> analyticsWeeksample = analyticsService.getAnalyticsWeek();


        return "analyticsWeek";
    }
}
