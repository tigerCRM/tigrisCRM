package com.tiger.crm.controller;

import com.tiger.crm.repository.dto.TicketDto;
import com.tiger.crm.service.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping(value = {"/ticketlist"}, method = RequestMethod.GET)
    public String TiketList(
            @RequestParam(value = "searchKeyword", required = false) String searchKeyword,
            HttpServletRequest request, HttpServletResponse response, Model model) {

        // 검색 조건에 맞는 게시글 리스트 가져오기
        List<Map<String, Object>> ticketList;

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
           ticketList = ticketService.searchTickets(searchKeyword);  // searchTickets는 검색어로 필터링된 결과 반환하는 메서드
           // ticketList = ticketService.getTicketList();
        } else {
            ticketList = ticketService.getTicketList();  // 기본 전체 리스트
        }

        model.addAttribute("ticketList", ticketList);
        model.addAttribute("searchKeyword", searchKeyword);  // 검색어를 뷰에 전달하여 검색어가 유지되도록

        return "ticketlist";  // 반환되는 뷰 이름
    }
   /* @RequestMapping(value={"/ticketlist"}, method = RequestMethod.GET)
    public String TiketList(HttpServletRequest request, HttpServletResponse response, Model model)
    {

        // 게시글 리스트
        List<Map<String, Object>> ticketList = ticketService.getTicketList();
        model.addAttribute("ticketList", ticketList);

        return "ticketlist";
    }*/

}
