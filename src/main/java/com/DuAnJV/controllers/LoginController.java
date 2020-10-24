package com.DuAnJV.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.User;
import com.DuAnJV.services.ChucnangService;
import com.DuAnJV.services.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userservice;

	@Autowired
	ChucnangService chucnangService;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/loginadmin")
	public String showLogin(HttpServletRequest request,HttpSession session) {
		return "loginadmin";
	}

	@PostMapping("/checkLogin")
	public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			ModelMap model, HttpSession session) {
		if (this.userservice.checkLogin(username, password)) {
			logger.info("login success with email "+ username);
			List<Chucnang> ls = this.chucnangService.findAllChucnangByEmail(username);
			User user = this.userservice.findUserByEmail(username);
			session.setAttribute("MENU", ls);
			session.setAttribute("USERNAME", username);
			session.setAttribute("USERLOGIN", user);
			return "redirect:/user/list";
		} else {
			{
				logger.error("Error: Tài khoản hoặc mật khẩu không chính xác");
				session.setAttribute("ERROR", "Tài khoản hoặc mật khẩu không chính xác");
				return "redirect:/loginadmin";
			}
		}
	}
	
	
	@GetMapping(value = "/check", produces = "application/json")
	@ResponseBody
	public String checkQuyen(String url, ModelMap model, HttpSession session) {
		String username = (String) session.getAttribute("USERNAME");
		User userLogin = this.userservice.findUserByEmail(username);
		if (replaceDemo.checkQuyen(userLogin, url)) {
			return "1";
		} else {
			return "-1";
		}
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model) {
		String message = "ACCESS DENIED";
		model.addAttribute("message", message);
		return "403Page";
	}

	@RequestMapping("/logout")
	public String logout(HttpSession session, ModelMap model) {
		session.removeAttribute("ERROR");
		session.removeAttribute("MENU");
		session.removeAttribute("USERNAME");
		session.removeAttribute("USERLOGIN");
		return "redirect:/loginadmin";
	}
}
