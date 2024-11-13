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
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
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
            // ticketList = ticketService.searchTickets(searchKeyword);  // searchTickets는 검색어로 필터링된 결과 반환하는 메서드
            ticketList = ticketService.getTicketList(searchKeyword);
        } else {
            ticketList = ticketService.getTicketList(searchKeyword);
          // ticketList = ticketService.getTicketList();  // 기본 전체 리스트
        }

        model.addAttribute("ticketList", ticketList);
        model.addAttribute("searchKeyword", searchKeyword);

        return "ticketlist";  // 반환되는 뷰 이름
    }
    @PostMapping("/ticketlisttest") // AJAX 요청을 위한 POST 매핑
    public String searchTickets(@RequestBody Map<String, String> params, Model model) {
        String searchKeyword = params.get("searchKeyword");

        List<Map<String, Object>> ticketList;

        // 검색어가 있으면 해당 결과를 가져오고, 없으면 전체 리스트 가져옴
        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            ticketList = ticketService.getTicketList(searchKeyword);
        } else {
            ticketList = ticketService.getTicketList(searchKeyword);
        }

        model.addAttribute("ticketList", ticketList);
        return "ticketlist :: #ticketListContainer"; // 프래그먼트 ID에 맞게 반환
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
