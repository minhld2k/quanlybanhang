package com.DuAnJV.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
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
import com.DuAnJV.models.Customer;
import com.DuAnJV.models.User;
import com.DuAnJV.services.ChucnangService;
import com.DuAnJV.services.CustomerService;
import com.DuAnJV.services.UserService;

@Controller
public class LoginController {

	@Autowired
	UserService userservice;
	
	@Autowired
	CacheManager cacheManager;

	@Autowired
	ChucnangService chucnangService;
	
	@Autowired
	CustomerService customerService;
	
	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@RequestMapping("/loginadmin")
	public String showLoginAdmin(HttpServletRequest request,HttpSession session) {
		return "loginadmin";
	}
	
	@RequestMapping("/login")
	public String showLogin(HttpServletRequest request,HttpSession session) {
		return "trangchu/login";
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
			session.removeAttribute("ERROR");
			if (!replaceDemo.checkQuyen(user, "/user/list")) {
				return "redirect:/user/oneUserCNS";
			}else {
				return "redirect:/user/list";
			}
		} else {
			{
				logger.error("Error: Tài khoản hoặc mật khẩu không chính xác");
				session.setAttribute("ERROR", "Tài khoản hoặc mật khẩu không chính xác");
				return "redirect:/loginadmin";
			}
		}
	}
	
	@PostMapping("/checkLoginHome")
	public String checkLoginHome(@RequestParam("username") String username, @RequestParam("password") String password,
			ModelMap model, HttpSession session) {
		if (this.customerService.checkLogin(username, password)) {
			logger.info("login success with email "+ username);
			Customer cus = this.customerService.findByEmail(username);
			session.setAttribute("UserHome", cus);
			session.removeAttribute("ERROR");
			return "redirect:/";
		} else {
			logger.error("Error: Tài khoản hoặc mật khẩu không chính xác");
			session.setAttribute("ERROR", "Tài khoản hoặc mật khẩu không chính xác");
			return "redirect:/login";
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
		clearCache();
		return "redirect:/loginadmin";
	}
	
	// clear all cache using cache manager
    @RequestMapping(value = "clearCache")
	public void clearCache(){
        for(String name:cacheManager.getCacheNames()){
            cacheManager.getCache(name).clear();            // clear cache by name
        }
    }
    
}
