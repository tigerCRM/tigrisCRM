package com.tiger.crm.controller;

import com.tiger.crm.repository.dto.user.User;
import com.tiger.crm.service.login.LoginService;
import com.tiger.crm.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController
{
	@Autowired
	private LoginService loginService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());


	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String main(Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr)
	{
		return "/login";
	}

	/*
	* 로그인(회원확인)
	* 작성자 : 제예솔
	* 설명 : 로그인 성공 -> 메인화면 / 로그인 실패 -> 로그인 페이지
	* */
	@PostMapping(value = {"/login"})
	public String login(HttpServletRequest request, HttpServletResponse response)
	{
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		User user = loginService.login(id,password);

		if (user == null) {
			return "redirect:/login";
		}

		return "/main";
	}

}
