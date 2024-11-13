package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.repository.dto.TicketDto;
import com.tiger.crm.service.TicketService;
import com.tiger.crm.service.user.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;
import java.util.Map;

@Controller
public class MainController
{

	@Autowired
	private ConfigProperties config;
	@Autowired
	private UserService userService;
	@Autowired
	private TicketService ticketService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());


	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String intro(String userId, String userEmpNo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr)
	{
		//todo : (예솔) 세션이 살아있으면 메인페이지, 세션이 죽어있으면 로그인으로 넘기는 로직 필요
		rttr.addFlashAttribute("userId", 	userId);
		rttr.addFlashAttribute("userEmpNo", userEmpNo);
		return "redirect:/login";
	}

	/*
	* 메인페이지
	* */
	@RequestMapping(value = {"main"}, method = RequestMethod.GET)
	public String main(TicketDto storageSearchDto, HttpServletRequest request, HttpServletResponse response, Model model) {

		// 게시글 리스트
		List<Map<String, Object>> ticketList = ticketService.getTicketList();
		model.addAttribute("ticketList", ticketList);

		// todo : 요청내역(ticket), 공지사항(board) 내용 불러와서 뿌려야함.

		return "/main";
	}
}
