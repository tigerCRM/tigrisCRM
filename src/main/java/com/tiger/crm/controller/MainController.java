package com.tiger.crm.controller;

import com.tiger.crm.common.context.ConfigProperties;
import com.tiger.crm.repository.dto.page.PagingRequest;
import com.tiger.crm.repository.dto.page.PagingResponse;
import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.service.common.CommonService;
import com.tiger.crm.service.main.MainService;
import com.tiger.crm.repository.dto.user.UserLoginDto;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class MainController
{
	@Autowired
	private CommonService commonService;
	@Autowired
	private ConfigProperties config;
	@Autowired
	private MainService mainService;
	@Autowired
	private MailService mailService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());

	/*
	 * 랜딩페이지
	 * 설명 : 세션이 살아 있으면 메인페이지로, 세션이 없으면 로그인으로 리다이렉트
	 * */
	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String intro(@ModelAttribute("user") UserLoginDto user, HttpServletRequest request, HttpServletResponse response)
	{
		HttpSession session = request.getSession(false);
		if(session == null){
			LOGGER.info("세션 없음");
			return "redirect:login";
		}
		return "redirect:main";
	}

	/*
	* 메인페이지
	* 설명 : 세션이 살아있으면 세션 정보 화면으로 보내줌, 세션 없으면 로그인 페이지로
	* */
	@RequestMapping(value = {"main"}, method = RequestMethod.GET)
	public String mainPage(@ModelAttribute PagingRequest pagingRequest, @ModelAttribute("user") UserLoginDto user, HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);

		if (session == null){
			LOGGER.info("세션 없음");
			return "redirect:login";
		}

		// 유저정보
		user = (UserLoginDto)session.getAttribute("loginUser");
		LOGGER.info("세션정보 : " + user.toString());
		model.addAttribute("user", user);

		// 메인 페이지 요청내역 조회
		List<Map<String, Object>> ticketList = mainService.getMainTicketList(user);
		// 메인 페이지 완료내역 조회
		List<Map<String, Object>> ticketList2 = mainService.getCloseList(user);
		// 공지사항 목록 조회
		List<Map<String, Object>> noticeList = mainService.getNoticeList(user);

		model.addAttribute("ticketList", ticketList);
		model.addAttribute("ticketList2", ticketList2);
		model.addAttribute("noticeList", noticeList);

		return "main";
	}

	/*
	 * 1. 메일발송내역 페이지(초기 접속 시)
	 */
	@GetMapping(value = { "mailHistory"})
	public String mailHistory(@ModelAttribute PagingRequest pagingRequest, Model model)	{
		try {
			// 메일 발송 내역 조회
			PagingResponse<Map<String, Object>> pageResponse = mailService.getMailHistList(pagingRequest);
			model.addAttribute("mailHistList", pageResponse);
			model.addAttribute("searchOptions", commonService.getSelectOptions("mh_search"));
		}catch (Exception e) {
			e.printStackTrace();
		}

		return "mailHistory";
	}

	/*
	 * 2. 메일발송내역 페이지(상세 검색 시)
	 */
	@PostMapping(value = { "mailHistory"})
	public String searchMailHistory(@ModelAttribute PagingRequest pagingRequest, Model model)	{

		try {
			// 메일 발송 내역 조회
			PagingResponse<Map<String, Object>> pageResponse = mailService.getMailHistList(pagingRequest);
			model.addAttribute("mailHistList", pageResponse);

			// 부분 뷰 렌더링 (리스트 부분만 갱신)
			return "mailHistory :: mailHistListFragment";

		} catch (IllegalArgumentException e) {
			// 입력 값에 대한 오류 처리 (예: 유효하지 않은 파라미터)
			model.addAttribute("error", "잘못된 입력 값이 있습니다. 다시 확인해 주세요.");
			e.printStackTrace();
		} catch (Exception e) {
			// 데이터 조회 중 발생한 일반적인 오류 처리
			model.addAttribute("error", "데이터를 불러오는 중 오류가 발생했습니다. 다시 시도해주세요.");
			e.printStackTrace();
		}

		return "mailHistory";
	}
}
