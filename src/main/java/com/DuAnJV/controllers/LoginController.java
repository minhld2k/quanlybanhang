package com.DuAnJV.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

	@RequestMapping("/loginadmin")
	public String showLogin(HttpServletRequest request) {
		return "loginadmin";
	}

	@RequestMapping("/admin")
	public String show() {
		return "layouts/layout-admin";
	}

	@PostMapping("/checkLogin")
	public String checkLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			ModelMap model, HttpSession session) {
		if (this.userservice.checkLogin(username, password)) {
			System.out.println("login thanh cong");
			List<Chucnang> ls = this.chucnangService.findAllChucnangByEmail(username);
			User user = this.userservice.findUserByEmail(username);
			session.setAttribute("MENU", ls);
			session.setAttribute("USERNAME", username);
			session.setAttribute("USERLOGIN", user);
			return "redirect:/user/list";
		} else {
			{
				System.out.println("login that bai");
				model.addAttribute("ERROR", "Username and password not exist");
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
		session.removeAttribute("USERNAME");
		return "redirect:/loginadmin";
	}
}
