package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.repository.dto.analytics.AnalyticsWeekDto;
import com.tiger.crm.repository.dto.company.CompanyOptionDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.analytics.AnalyticsService;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.ticket.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    @Autowired
    private TicketService ticketService;
    /*
    * 주간처리현황 페이지 보기
    * */
    @GetMapping("/analytics_week")
    public String getAnalyticsWeek(HttpServletRequest request, HttpServletResponse response, Model model,PagingRequest pagingRequest)
    {
        HttpSession session = request.getSession(false);
        UserLoginDto loginUser = (UserLoginDto)session.getAttribute("loginUser");

        String startDate = "";

        //고객사 리스트
        List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
        model.addAttribute("companyOptions", companyOptions);

        //날짜 리스트
        List<String> dayList = analyticsService.getDate(startDate);
        model.addAttribute("dayList", dayList);

        //접수 리스트
        List<AnalyticsWeekDto> weekReceiptList = analyticsService.getAnalyticsWeek(startDate);
        model.addAttribute("weekReceiptList", weekReceiptList);

        // 요청 조회
        PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
        model.addAttribute("ticketList", pageResponse);

        return "analyticsWeek";
    }

    @PostMapping("/analytics_week")
    public String getAnalyticsWeekSearchWeek(HttpServletRequest request, HttpServletResponse response, Model model,PagingRequest pagingRequest) {

        String startDate = request.getParameter("startDate");

        //고객사 리스트
        List<CompanyOptionDto> companyOptions = commonService.getCompanyOption();
        model.addAttribute("companyOptions", companyOptions);

        //날짜 리스트
        List<String> dayList = analyticsService.getDate(startDate);
        model.addAttribute("dayList", dayList);

        //접수 리스트
        List<AnalyticsWeekDto> weekReceiptList = analyticsService.getAnalyticsWeek(startDate);
        model.addAttribute("weekReceiptList", weekReceiptList);
        // 요청 조회
        PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketList(pagingRequest);
        model.addAttribute("ticketList", pageResponse);
        return "analyticsWeek :: analyticsWeekFragment";
    }

    @PostMapping("/analytics_week_detail")
    public String getAnalyticsWeekSearchWeekDetail(HttpServletRequest request, HttpServletResponse response, Model model,PagingRequest pagingRequest) {

        // 요청 조회
        PagingResponse<Map<String, Object>> pageResponse = ticketService.getTicketListAnalytics(pagingRequest);
        model.addAttribute("ticketList", pageResponse);
        return "analyticsWeek :: analyticsWeekDetail";
    }
}
