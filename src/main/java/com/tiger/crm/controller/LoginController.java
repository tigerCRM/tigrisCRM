package com.tiger.crm.controller;

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

@Controller
public class LoginController
{
	@Autowired
	private UserService userService;

	private Logger LOGGER = LoggerFactory.getLogger(getClass());


	@RequestMapping(value = {"/login"}, method = RequestMethod.GET)
	public String main(String userId, String userEmpNo, Model model, HttpServletRequest request, HttpServletResponse response, RedirectAttributes rttr)
	{
		rttr.addFlashAttribute("userId", 	userId);
		rttr.addFlashAttribute("userEmpNo", userEmpNo);
		return "redirect:/intro";
	}
}
