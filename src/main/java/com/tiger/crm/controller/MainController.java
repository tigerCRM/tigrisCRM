package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.repository.dto.TicketDto;
import com.tiger.crm.service.TicketService;
import com.tiger.crm.common.crypto.AES128Utils;
import com.tiger.crm.repository.dto.user.User;
import com.tiger.crm.repository.dto.user.UserSearchDto;
import com.tiger.crm.service.user.UserService;

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

	/*
	 * 랜딩페이지
	 * 설명 : 세션이 살아 있으면 메인페이지로, 세션이 없으면 로그인으로 리다이렉트
	 * */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String intro(@ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if(session == null){
			LOGGER.info("세션 없음");
			return "redirect:/login";
		}
		return "redirect:/main";
	}

	/*
	* 메인페이지
	* 설명 : 세션이 살아있으면 세션 정보 화면으로 보내줌, 세션 없으면 로그인 페이지로
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
