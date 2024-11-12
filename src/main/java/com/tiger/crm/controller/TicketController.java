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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
public class TicketController {
    @Autowired
    private TicketService ticketService;

    private Logger LOGGER = LoggerFactory.getLogger(getClass());

    @RequestMapping(value={"/ticketlist"}, method = RequestMethod.GET)
    public String TiketList(TicketDto storageSearchDto, HttpServletRequest request, HttpServletResponse response, Model model)
    {

        // 게시글 리스트
        List<Map<String, Object>> ticketList = ticketService.getTicketList();
        model.addAttribute("ticketList", ticketList);

        return "ticketlist";
    }

}
