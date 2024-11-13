package com.tiger.crm.controller;

import com.tiger.crm.repository.mail.MailService;
import com.tiger.crm.repository.dto.user.User;
import com.tiger.crm.service.login.LoginService;
import com.tiger.crm.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController
{
	@Autowired
	private LoginService loginService;

	@Autowired
	private MailService mailService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());


	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String loginPage(@ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response)
	{
		return "/login";
	}

	/*
	* 로그인(회원확인)
	* 작성자 : 제예솔
	* 설명 : 로그인 성공 -> 메인화면 / 로그인 실패 -> 로그인 페이지
	* */
	@PostMapping(value = {"/login"})
	public String login(@ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response, BindingResult bindingResult, RedirectAttributes redirectAttributes)
	{

		//검증로직 1 : id 또는 password 가 공백일 경우
		if(user.getUserId() == null || user.getUserId().trim().isEmpty()){
			bindingResult.rejectValue("userId", "id.empty");
		}
		if(user.getUserPw() == null|| user.getUserPw().trim().isEmpty()){
			bindingResult.rejectValue("userPw", "password.empty");
		}
		if (bindingResult.hasErrors()) {
			LOGGER.info("validation error 발생");
			redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.user", bindingResult);
			redirectAttributes.addFlashAttribute("user", user);
			return "/login";
		}

		//로그인 정보 가져옴
		User loginUser = loginService.login(user.getUserId(),user.getUserPw());

		LOGGER.info("login access {}",loginUser);
		
		//검증로직 2: 해당 사용자가 없을 경우
		if (loginUser == null) {
			bindingResult.reject("login.failed", "아이디 또는 비밀번호가 올바르지 않습니다.");
			return "redirect:/login";
		}

		HttpSession session = request.getSession();
		session.setAttribute("loginUser", loginUser);

		return "redirect:/main";
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
		return "redirect:/login";
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
