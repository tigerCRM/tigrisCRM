package com.example.crm.controller;

import com.example.crm.core.context.ConfigProperties;
import com.example.crm.core.crypto.AES128Utils;
import com.example.crm.repository.dto.user.UserSearchDto;
import com.example.crm.service.user.UserService;

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
import org.springframework.web.servlet.support.RequestContextUtils;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.Map;

@Controller
public class MainController
{

	@Autowired
	private ConfigProperties config;
	@Autowired
	private UserService userService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());


	@RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
	public String main(String userId, String userEmpNo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr)
	{
		rttr.addFlashAttribute("userId", 	userId);
		rttr.addFlashAttribute("userEmpNo", userEmpNo);
		return "redirect:/intro";
	}

	@RequestMapping(value="/intro")
	public String intro(Model model, HttpServletRequest request, HttpServletResponse response)
	{
		String email 		= "";
		String userEmpNo 	= "";
		String userEmpNoStr = "";

		Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
		if(flashMap != null)
		{
			if(flashMap.get("email") != null) 		{ email = flashMap.get("email").toString(); }
			if(flashMap.get("userEmpNo") != null) 	{ userEmpNo = flashMap.get("userEmpNo").toString(); }
		}

		// intro 화면에서 새로고침 한 경우
		UserSearchDto cookieInfo = userService.getCookie(request);
		if( (cookieInfo != null) && (email.equals("") && userEmpNo.equals("")))	{
			return "/intro";
		}

		if(!email.equals("") && userEmpNo.equals("")) {
			try
			{
				AES128Utils aes128Utils = new AES128Utils(config.getAes128SecretKey());
				userEmpNoStr = aes128Utils.decrypt(userEmpNo);
			}
			catch (UnsupportedEncodingException | GeneralSecurityException e) {
				e.printStackTrace();
				LOGGER.info("*****************************************");
				LOGGER.info("  Decrypt Fail.. [aes128] ");
				LOGGER.info("  email : " + email);
				LOGGER.info("*****************************************");
				return "redirect:/disallowed";
			}

			UserSearchDto userInfo = userService.getUserInfoByLoginEmail(email);

			// DB에 존재하지 않는 사용자
			if(userInfo == null) {
				LOGGER.info("*****************************************");
				LOGGER.info("  Disallowed Users.. [tigris] ");
				LOGGER.info("  Not Exist DB.. ");
				LOGGER.info("  email : " + email);
				LOGGER.info("*****************************************");
				return "redirect:/disallowed";
			}
		} else if(!userEmpNo.equals("") && email.equals("")) {
			try	{
				AES128Utils aes128Utils = new AES128Utils(config.getAes128SecretKey());
				userEmpNoStr = aes128Utils.decrypt(userEmpNo);
			} catch (UnsupportedEncodingException | GeneralSecurityException e)	{
				e.printStackTrace();
				LOGGER.info("*****************************************");
				LOGGER.info("  Decrypt Fail.. [aes128] ");
				LOGGER.info("  userEmpNo : " + userEmpNo);
				LOGGER.info("*****************************************");
				return "redirect:/disallowed";
			}

//			 UserSearchDto userInfo = userService.getUserInfoByUserEmpNo(userEmpNoStr);

			// DB에 존재하지 않는 사용자
//			if(userInfo == null)
//			{
//				LOGGER.info("*****************************************");
//				LOGGER.info("  Disallowed Users.. [tigris] ");
//				LOGGER.info("  Not Exist DB.. ");
//				LOGGER.info("  userEmpNo : " + userEmpNoStr);
//				LOGGER.info("*****************************************");
//				return "redirect:/disallowed";
//			}

			// 게시판 리스트
//			 List<TCommunity> communityList = communityService.getCommunityList(new CommunityDto());
//			 model.addAttribute("communityList", communityList);
		} else {
			LOGGER.info("*****************************************");
			LOGGER.info("  Disallowed Users.. [tigris] ");
			LOGGER.info("  Not Exist Parameter ");
			LOGGER.info("  userEmpNo : " + userEmpNo);
			LOGGER.info("*****************************************");
			return "redirect:/disallowed";
		}

		LOGGER.info("*****************************************");
		LOGGER.info("  User Login.. [tigris] ");
		LOGGER.info("  email     : " + email);
		LOGGER.info("  userEmpNo : " + userEmpNoStr);
		LOGGER.info("*****************************************");

		return "intro";
	}

//	@RequestMapping("/disallowed")
//	public String disallowed(Model model)
//	{
//		return "/disallowed";
//	}
}
