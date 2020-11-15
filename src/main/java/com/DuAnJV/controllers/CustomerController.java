package com.DuAnJV.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.Customer;
import com.DuAnJV.models.Role;
import com.DuAnJV.models.User;
import com.DuAnJV.services.CustomerService;
import com.DuAnJV.services.UserService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	@Autowired
	CustomerService customerService;
	
	@Autowired
	UserService userService;
	
	@RequestMapping("/pagesize")
	public String size(@RequestParam(name = "pagesize") int pagesize, HttpSession session) {
		if (0 == pagesize) {
			session.setAttribute("PAGESIZE", 10);
		} else {
			session.setAttribute("PAGESIZE", pagesize);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/user/search";
		}
		return "redirect:/customer/list";
	}
	
	@RequestMapping("/list")
	public String listUser(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber,
			HttpSession session, HttpServletRequest request) {
		String username = (String) session.getAttribute("USERNAME");
		String url = request.getServletPath();
		User user = this.userService.findUserByEmail(username);
		if (null == username || "".equals(username)) {
			return "redirect:/loginadmin";
		} else {
			if (!replaceDemo.checkQuyen(user, url)) {
				return "redirect:/403";
			} else {
				if (null == pageNumber || pageNumber < 1) {
					pageNumber = 1;
				}
				if (null == session.getAttribute("PAGESIZE") || session.getAttribute("PAGESIZE").equals("")) {
					session.setAttribute("PAGESIZE", 10);
				}
				long count = this.customerService.count();
				session.setAttribute("COUNT", count);
				if (0 == count) {
					session.setAttribute("MESLIST", "Không có tài khoản khách nào");
				} else {
					int pageSize = (Integer) session.getAttribute("PAGESIZE");
					long end = 0;
					if (0 == count % pageSize) {
						end = count / pageSize;
					} else {
						end = count / pageSize + 1;
					}
					model.addAttribute("beginIndex", 1);
					model.addAttribute("endIndex", end);
					model.addAttribute("currentIndex", pageNumber);
					model.addAttribute("totalPageCount", end);
					model.addAttribute("baseUrl", "/customer/list?page=");
					model.addAttribute("CUSTOMERS", this.customerService.findAllCustomer("", "", "", null, pageSize, (pageNumber - 1) * pageSize));
				}
				session.setAttribute("EMAILFIND", "");
				session.setAttribute("ADDRESSFIND", "");
				session.setAttribute("NAMEFIND", "");
				return "view-customer";
			}
		}
	}
	
	@RequestMapping("/dataSearch")
	public String search(HttpSession session, @RequestParam("emailfind") String emailfind,
			@RequestParam("namefind") String namefind, @RequestParam("addressfind") String addressfind,
			@RequestParam(name = "genderfind", required = false) List<Byte> gender) {
		session.setAttribute("EMAILFIND", emailfind);
		session.setAttribute("ADDRESSFIND", addressfind);
		session.setAttribute("NAMEFIND", namefind);
		session.setAttribute("GENDERFIND", gender);
		if (null == gender && emailfind.isEmpty() && addressfind.isEmpty() && namefind.isEmpty()) {
			return "redirect:/customer/list";
		}else {
			if (!namefind.isEmpty()) {
				namefind = replaceDemo.replace(namefind);
				session.setAttribute("NAME", namefind);
			}else {
				session.setAttribute("NAME", namefind);
			}
			
			if (!addressfind.isEmpty()) {
				addressfind = replaceDemo.replace(addressfind);
				session.setAttribute("ADDRESS", addressfind);
			}else {
				session.setAttribute("ADDRESS", addressfind);
			}
			return "redirect:/customer/search";
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/search")
	public String searchCategory(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber
			, HttpSession session) {
		if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
		int pageSize = (Integer) session.getAttribute("PAGESIZE");
		String fullname = (String) session.getAttribute("NAME");
		String address = (String) session.getAttribute("ADDRESS");
		String email = (String) session.getAttribute("EMAILFIND");
		List<Byte> gender = (List<Byte>) session.getAttribute("GENDERFIND");
		long count = this.customerService.findAllCustomer(email, fullname, address, gender, -1, -1).size();
		List<Object[]> ls = this.customerService.findAllCustomer(email, fullname, address, gender, pageSize,(pageNumber - 1) * pageSize);
		if (0 == count) {
			session.setAttribute("MESLIST", "Không tìm thấy khách hàng yêu cầu");
		} else {
			long end = 0;
			if (0 == count % pageSize) {
				end = count / pageSize;
			} else {
				end = count / pageSize + 1;
			}
			model.addAttribute("beginIndex", 1);
			model.addAttribute("endIndex", end);
			model.addAttribute("currentIndex", pageNumber);
			model.addAttribute("totalPageCount", end);
			model.addAttribute("baseUrl", "/customer/search?page=");
			model.addAttribute("CUSTOMERS", ls);
		}
		return "view-customer";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String update(@RequestParam("id") Long id, @RequestParam("email") String email, HttpSession session,
			@RequestParam("fullname") String fullname, @RequestParam("address") String address,
			@RequestParam("phone") String phone, @RequestParam("birthday") String birthday,
			@RequestParam("gender") byte gender,
			@RequestParam("password") String password) {
		Date date = new Date(0);
		Customer cus = null;
		if (id != null) {
			cus = this.customerService.findByid(id);
			cus.setUpdateby("");
			cus.setUpdateday(new Timestamp(new Date().getTime()));
		}else {
			cus = new Customer();
			cus.setCreatby("");
			cus.setCreatday(new Timestamp(new Date().getTime()));
		}
		if (birthday != null) {
			date = replaceDemo.todate(birthday, "dd-MM-yyyy");
		}
		cus.setEmail(email);
		cus.setFullname(fullname);
		cus.setAddress(address);
		cus.setPhone(phone);
		cus.setBirthday(date);
		cus.setGender(gender);
		cus.setIsdelete((byte) 0);
		
		if (!password.isEmpty()) {
			cus.setPassword(replaceDemo.getBcrypt(password));
		}
		
		this.customerService.save(cus);
		if (id != null) {
			return "redirect:/customer/edit";
		}else {
			return "redirect:/login";
		}
	}
	
	@RequestMapping("/add")
	public String add(ModelMap model) {
		model.addAttribute("CUSTOMER",new Customer());
		model.addAttribute("TITLE","Đăng ký tài khoản");
		model.addAttribute("button","Đăng ký");
		return "trangchu/customer-detail";
	}
	
	@RequestMapping("/edit")
	public String edit(ModelMap model, HttpSession session) {
		Customer cus = (Customer) session.getAttribute("UserHome");
		String birthday =  replaceDemo.toString(cus.getBirthday(), "dd-MM-yyyy");
		cus.setBirthday(replaceDemo.todate(birthday, "dd-MM-yyyy"));
		model.addAttribute("CUSTOMER",cus);
		model.addAttribute("TITLE","Cập nhật tài khoản");
		model.addAttribute("button","Cập nhật");
		return "trangchu/customer-detail";
	}
}
