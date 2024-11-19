package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.repository.dto.board.SystemBoardDto;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.service.board.SystemBoardService;
import com.tiger.crm.service.ticket.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Map;

@Controller
public class SystemBoardController {
    
    /*
    * 시스템관리
    * 작성자 : 제예솔
    * 설명 : 시스템관리 리스트, 글작성, 수정 및 삭제
    * */
    @Autowired
    private ConfigProperties config;

    @Autowired
    private SystemBoardService systemBoardService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    /*
    * 시스템 정보 페이지
    * */
    @RequestMapping(value = {"systemBoardList"}, method = RequestMethod.GET)
    public String getSystemBoardListPage(SystemBoardDto systemBoardDto, PagingRequest pagingRequest, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        HttpSession session = request.getSession(false);
        PagingResponse<Map<String, Object>> pageResponse = systemBoardService.getSystemBoardList(pagingRequest);
        model.addAttribute("systemBoardList", pageResponse);
        return "systemBoardList";
    }

    @RequestMapping(value = {"systemBoard"}, method = RequestMethod.POST)
    public String getSystemBoardPage(SystemBoardDto systemBoardDto, HttpServletRequest request, HttpServletResponse response, Model model)
    {
        HttpSession session = request.getSession(false);

        return null;
    }
}
