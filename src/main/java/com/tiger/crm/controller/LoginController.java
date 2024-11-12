package com.tiger.crm.controller;

import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController
{
	@Autowired
	private UserService userService;

	@Autowired
	private MailService mailService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());


	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String main(String userId, String userEmpNo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr)
	{
		rttr.addFlashAttribute("userId", 	userId);
		rttr.addFlashAttribute("userEmpNo", userEmpNo);
		return "redirect:/intro";
	}

	/*
	* 로그아웃
	*/
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request) {
		// 세션을 무효화하여 로그아웃 처리
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();  // 세션 무효화(필요시)
		}

		// 로그인 페이지로 리다이렉트
		return "redirect:/intro";
	}

	/*
	 * 비밀번호 초기화 이메일 발송
	 */
	@RequestMapping("/resetPassword")
	public String resetPassword(String userEmail, String userName/*, String resetLink*/) throws MessagingException {
		try {
			Map<String, Object> model = new HashMap<>();
			// model.put("userName", userName);
			model.put("userName", "안상재");
			// model.put("resetLink", resetLink);

			// mailService.sendEmail(userEmail, "Password Reset Request", "password-reset-email", model);
			mailService.sendEmail("dkstkdwo93@tigrison.com", "비밀번호 초기화", "password-reset-email", model);

		}catch (Exception e){
			System.out.println(e.getStackTrace());
		}

		return "redirect:/main";
	}
}
