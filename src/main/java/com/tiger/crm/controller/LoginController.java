package com.tiger.crm.controller;

import com.tiger.crm.common.validation.UserLoginValidator;
import com.tiger.crm.repository.dto.client.ClientManageDto;
import com.tiger.crm.repository.dto.user.UserLoginDto;
import com.tiger.crm.service.client.ClientManageService;
import com.tiger.crm.service.login.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class LoginController
{
	@Autowired
	private LoginService loginService;
	private final UserLoginValidator userLoginValidator;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	private ClientManageService clientManageService;
	private Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	/*
	* Validation Binder
	* 작성자 : 제예솔 
	* 설명 : 해당 바인더 설치, @Validated 로 validation 자동 적용
	* */
	@InitBinder
	public void init(WebDataBinder dataBinder) {
		LOGGER.info("init binder {}", dataBinder);
		dataBinder.addValidators(userLoginValidator);
	}

	/*로그인 페이지 이동
	* 작성자 : 제예솔
	* 설명 : 로그인 페이지로 이동
	* */
	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String loginPage(@ModelAttribute("user") UserLoginDto user, HttpServletRequest request, HttpServletResponse response)
	{
		return "login";
	}

	/*
	* 로그인(회원확인)
	* 작성자 : 제예솔
	* 설명 : 로그인 성공 -> 메인화면 / 로그인 실패 -> 로그인 페이지
	* */
	@PostMapping(value = {"/login"})
	public String login(@Validated @ModelAttribute("user") UserLoginDto user, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes, Model model)
	{

		//최초 인입된 dto 에 대해 validation 수행 후 반환
		if (bindingResult.hasErrors()) {
			LOGGER.info("validation error 발생={}",bindingResult);
			return "login";
		}

		//로그인 정보 가져옴
		UserLoginDto loginUser = loginService.login(user.getUserId(),user.getUserPw());
		LOGGER.info("login access {}",loginUser);
		
		//해당 사용자가 없을 경우 에러 발생
		if (loginUser == null) {
			bindingResult.reject("error.notExistMember");
			LOGGER.info("로그인 실패={}",bindingResult);
			return "login";
		}

		// 로그인 성공 시 세션에 사용자 정보 저장
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", loginUser);

		// 세션에서 리다이렉트 URL 가져오기
		String redirectUrl = (String) session.getAttribute("redirectUrl");

		// 리다이렉트 URL이 있으면 해당 페이지로 이동, 없으면 기본 메인 페이지로 이동
		if (redirectUrl != null && !redirectUrl.isEmpty()) {
			session.removeAttribute("redirectUrl");  // 리다이렉트 후 세션에서 URL 제거
			return "redirect:" + redirectUrl;  // 리다이렉트 URL로 이동
		}
		// 비밀번호 필수 변경해야할 경우
		if ("Y".equals(loginUser.getPwCh())) {
			//	bindingResult.reject("error.notExistMember");
			LOGGER.info("로그인 실패={}", bindingResult);
			model.addAttribute("showPasswordChangeModal", true);
			return "login";
		}
		// 리다이렉트 URL이 없다면 기본 메인 페이지로 이동
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
		return "redirect:login";
	}

	/*
	 * 비밀번호 초기화 이메일 발송
	 */
	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(UserLoginDto user) {
		try {
			// 임시 비밀번호로 업데이트 및 메일 발송
			loginService.resetPassword(user);

			return ResponseEntity.ok("Password reset email sent.");

		}catch (Exception e){
			System.out.println(e.getStackTrace());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred.");
		}

	}

	/*
	 * 비밀번호변경
	 */
	@PostMapping("/changePasswordLogin")
	@ResponseBody
	public Map<String, Object> changePasswordLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();
		try {
			// Check if the password is not empty or null
			if (userLoginDto.getUserPw() == null || userLoginDto.getUserPw().isEmpty()) {
				response.put("status", "error");
				response.put("message", "Password cannot be empty.");
				return response;
			}

			// Set the user ID and encrypted password
			ClientManageDto clientManageDto = new ClientManageDto();
			clientManageDto.setUserId(userLoginDto.getUserId());  // Use logged-in user's ID
			clientManageDto.setUserPw(passwordEncoder.encode(userLoginDto.getUserPw()));  // Encrypt new password

			// Call the service method to change the password
			clientManageService.changePassword(clientManageDto);

			// Return success response
			response.put("status", "success");

		} catch (Exception e) {
			// Log the error and send error response
			response.put("status", "error");
			response.put("message", "An error occurred while changing the password.");
			LOGGER.error("Error during password change", e);
		}
		return response;
	}



}
