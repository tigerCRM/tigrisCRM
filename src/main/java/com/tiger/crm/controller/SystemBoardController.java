package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.repository.dto.page.PagingRequest;
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

@Controller
public class SystemBoardController {
    
    /*
    * 시스템관리
    * 작성자 : 제예솔
    * */
    @Autowired
    private ConfigProperties config;
    private Logger LOGGER = LoggerFactory.getLogger(getClass());
    
    /*
    * 시스템 정보 페이지
    * */
    @RequestMapping(value = {"systemBoardList"}, method = RequestMethod.GET)
    public String getSystemBoardListPage(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session = request.getSession(false);

        return "systemBoard";
    }

    @RequestMapping(value = {"systemBoard"}, method = RequestMethod.POST)
    public String getSystemBoardPage(HttpServletRequest request, HttpServletResponse response, Model model)
    {
        HttpSession session = request.getSession(false);

        return null;
    }
}
