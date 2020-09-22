package com.DuAnJV.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.Role;
import com.DuAnJV.models.User;
import com.DuAnJV.services.ChucnangService;
import com.DuAnJV.services.RoleService;
import com.DuAnJV.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	RoleService roleService;

	@Autowired
	ChucnangService chucnangService;

	@RequestMapping("/pagesize")
	public String size(@RequestParam(name = "pagesize") int pagesize, HttpSession session) {
		if (0 == pagesize) {
			session.setAttribute("PAGESIZE", 5);
		} else {
			session.setAttribute("PAGESIZE", pagesize);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/user/search";
		}
		return "redirect:/user/list";
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
					session.setAttribute("PAGESIZE", 5);
				}
				long count = this.userService.count();
				session.setAttribute("COUNT", count);
				if (0 == count) {
					session.setAttribute("MESLIST", "Không có user");
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
					model.addAttribute("baseUrl", "/user/list?page=");
					model.addAttribute("USERS", this.userService.findAll(pageSize, (pageNumber - 1) * pageSize));
				}
				session.setAttribute("EMAILFIND", "");
				session.setAttribute("ADDRESSFIND", "");
				session.setAttribute("NAMEFIND", "");
				return "view-user";
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
		if (null == emailfind || "".equals(emailfind)) {
			if (null == namefind || "".equals(namefind)) {
				if (null == addressfind || "".equals(addressfind)) {
					if (null == gender || gender.get(0) < 0) {
						return "redirect:/user/list";
					} else {
						// (find by gender)
						session.setAttribute("SEARCH", 1);
						return "redirect:/user/search";
					}
				} else {
					if (null == gender || gender.get(0) < 0) {
						// (find by address)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						session.setAttribute("SEARCH", 2);
						return "redirect:/user/search";
					} else {
						// (find by gender and address)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						session.setAttribute("SEARCH", 3);
						return "redirect:/user/search";
					}
				}
			} else {
				if (null == addressfind || "".equals(addressfind)) {
					if (null == gender || gender.get(0) < 0) {
						// (find by name)
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 4);
						return "redirect:/user/search";
					} else {
						// (find by gender and name)
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 5);
						return "redirect:/user/search";
					}
				} else {
					if (null == gender || gender.get(0) < 0) {
						// (find by address and name)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 6);
						return "redirect:/user/search";
					} else {
						// (find by gender and address and name)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 7);
						return "redirect:/user/search";
					}
				}
			}
		} else {
			if (null == namefind || "".equals(namefind)) {
				if (null == addressfind || "".equals(addressfind)) {
					if (null == gender || gender.get(0) < 0) {
						// (find by email)
						session.setAttribute("SEARCH", 8);
						return "redirect:/user/search";
					} else {
						// (find by gender and email)
						session.setAttribute("SEARCH", 9);
						return "redirect:/user/search";
					}
				} else {
					if (null == gender || gender.get(0) < 0) {
						// (find by address and email)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						session.setAttribute("SEARCH", 10);
						return "redirect:/user/search";
					} else {
						// (find by gender and address and email)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						session.setAttribute("SEARCH", 11);
						return "redirect:/user/search";
					}
				}
			} else {
				if (null == addressfind || "".equals(addressfind)) {
					if (null == gender || gender.get(0) < 0) {
						// (find by name and email)
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 12);
						return "redirect:/user/search";
					} else {
						// (find by gender and name and email)
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 13);
						return "redirect:/user/search";
					}
				} else {
					if (null == gender || gender.get(0) < 0) {
						// (find by address and name and email)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 14);
						return "redirect:/user/search";
					} else {
						// (find by gender and address and name and email)
						addressfind = replaceDemo.replace(addressfind);
						session.setAttribute("ADDRESS", addressfind);
						namefind = replaceDemo.replace(namefind);
						session.setAttribute("NAME", namefind);
						session.setAttribute("SEARCH", 15);
						return "redirect:/user/search";
					}
				}
			}
		}
	}

	@RequestMapping("/search")
	public String searchUser(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber,
			HttpSession session) {
		if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
		int temp = (Integer) session.getAttribute("SEARCH");
		int pageSize = (Integer) session.getAttribute("PAGESIZE");
		long count = 0;
		String namefind = (String) session.getAttribute("NAME");
		String addressfind = (String) session.getAttribute("ADDRESS");
		String emailfind = (String) session.getAttribute("EMAILFIND");
		@SuppressWarnings("unchecked")
		List<Byte> gender = (List<Byte>) session.getAttribute("GENDERFIND");
		List<User> ls = null;
		switch (temp) {
		case 1:
			count = this.userService.countByGender(gender);
			ls = this.userService.findByGender(gender, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 2:
			count = this.userService.countByAddress(addressfind);
			ls = this.userService.findByAddress(addressfind, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 3:
			count = this.userService.countByAddressAndGender(addressfind, gender);
			ls = this.userService.findByAddressAndGender(addressfind, gender, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 4:
			count = this.userService.countByFullname(namefind);
			ls = this.userService.findByFullname(namefind, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 5:
			count = this.userService.countByFullnameAndGender(namefind, gender);
			ls = this.userService.findByFullnameAndGender(namefind, gender, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 6:
			count = this.userService.countByFullnameAndAddress(namefind, addressfind);
			ls = this.userService.findByFullnameAndAddress(namefind, addressfind, pageSize,
					(pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 7:
			count = this.userService.countByFullnameAndAddressAndGender(namefind, addressfind, gender);
			ls = this.userService.findByFullnameAndAddressAndGender(namefind, addressfind, gender, pageSize,
					(pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 8:
			count = this.userService.countByEmails(emailfind);
			ls = this.userService.findByEmails(emailfind, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 9:
			count = this.userService.countByEmailsAndGender(emailfind, gender);
			ls = this.userService.findByEmailsAndGender(emailfind, gender, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 10:
			count = this.userService.countByEmailsAndAddress(addressfind, emailfind);
			ls = this.userService.findByEmailsAndAddress(addressfind, emailfind, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 11:
			count = this.userService.countByEmailsAndAddressAndGender(addressfind, emailfind, gender);
			ls = this.userService.findByEmailsAndAddressAndGender(addressfind, emailfind, gender, pageSize,
					(pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 12:
			count = this.userService.countByEmailsAndFullname(namefind, emailfind);
			ls = this.userService.findByEmailsAndFullname(namefind, emailfind, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 13:
			count = this.userService.countByEmailsAndFullnameAndGender(namefind, emailfind, gender);
			ls = this.userService.findByEmailsAndFullnameAndGender(namefind, emailfind, gender, pageSize,
					(pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 14:
			count = this.userService.countByEmailsAndFullnameAndAddress(namefind, addressfind, emailfind);
			ls = this.userService.findByEmailsAndFullnameAndAddress(namefind, addressfind, emailfind, pageSize,
					(pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 15:
			count = this.userService.countByEmailsAndFullnameAndAddressAndGender(namefind, addressfind, emailfind,
					gender);
			ls = this.userService.findByEmailsAndFullnameAndAddressAndGender(namefind, addressfind, emailfind, gender,
					pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		default:
			break;
		}
		if (0 == count) {
			session.setAttribute("MESLIST", "Không tìm thấy user yêu cầu");
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
			model.addAttribute("baseUrl", "/user/search?page=");
			model.addAttribute("USERS", ls);
		}
		return "view-user";
	}

	@RequestMapping(value = "/addNew", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String add(@RequestParam("id") Long id, @RequestParam("email") String email,
			@RequestParam("password") String password, HttpSession session, @RequestParam("fullname") String fullname,
			@RequestParam("address") String address, @RequestParam("phone") String phone,
			@RequestParam("birthday") String birthday, @RequestParam("gender") byte gender,
			@RequestParam(name = "chucnang[]", required = false) List<Long> chucnangid,
			@RequestParam(name = "role[]") List<Long> roleid) {
		String username = (String) session.getAttribute("USERNAME");
		Date date = null;
		if (birthday != null) {
			date = replaceDemo.todate(birthday, "dd-MM-yyyy");
		}

		User user = new User(id, email, fullname, address, phone, date, gender);
		user.setPassword(password);
		List<Chucnang> ls = new ArrayList<>();
		List<Role> lsrole = new ArrayList<>();
		if (chucnangid != null) {
			for (Long long1 : chucnangid) {
				ls.add(this.chucnangService.findById(long1).get());
			}
		}
		user.setChucnangs(ls);
		for (Long long1 : roleid) {
			lsrole.add(this.roleService.findById(long1));
		}
		user.setRoles(lsrole);
		if (null == user.getId() || user.getId().equals("")) {
			user.setId(ThreadLocalRandom.current().nextLong(0, 900000000));
		}
		user.setIsdelete((byte) 0);
		user.setCreatby(username);
		user.setCreatday(new Timestamp(new Date().getTime()));
		user.setUpdateby(username);
		user.setUpdateday(new Timestamp(new Date().getTime()));
		this.userService.add(user);
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String update(@RequestParam("id") Long id, @RequestParam("email") String email, HttpSession session,
			@RequestParam("fullname") String fullname, @RequestParam("address") String address,
			@RequestParam("phone") String phone, @RequestParam("birthday") String birthday,
			@RequestParam("gender") byte gender,
			@RequestParam(name = "chucnang[]", required = false) List<Long> chucnangid,
			@RequestParam(name = "role[]") List<Long> roleid) {
		Date date = new Date(0);
		if (birthday != null) {
			date = replaceDemo.todate(birthday, "dd-MM-yyyy");
		}
		User user = new User(id, email, fullname, address, phone, date, gender);
		List<Chucnang> ls = new ArrayList<>();
		List<Role> lsrole = new ArrayList<>();
		if (chucnangid != null) {
			for (Long long1 : chucnangid) {
				ls.add(this.chucnangService.findById(long1).get());
			}
		}
		user.setChucnangs(ls);
		for (Long long1 : roleid) {
			lsrole.add(this.roleService.findById(long1));
		}
		user.setRoles(lsrole);
		if (null == user.getId() || user.getId().equals("")) {
			user.setId(ThreadLocalRandom.current().nextLong(0, 900000000));
		}
		user.setIsdelete((byte) 0);
		user.setUpdateby((String) session.getAttribute("USERNAME"));
		user.setUpdateday(new Timestamp(new Date().getTime()));
		this.userService.update(user);
		return "redirect:/user/list";
	}

	@GetMapping(value = "/oneUser", produces = "application/json")
	@ResponseBody
	public Map<String, String> findbyId(Long id, ModelMap model) {
		User user = this.userService.findById(id);
		List<Long> lscn = this.userService.findCNSById(id);
		List<Long> lsrole = this.userService.findRoleById(id);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", user.getId().toString());
		map.put("email", user.getEmail());
		map.put("password", user.getPassword());
		map.put("fullname", user.getFullname());
		map.put("phone", user.getPhone());
		map.put("birthday", replaceDemo.toString(user.getBirthday(), "dd-MM-yyyy"));
		map.put("gender", user.getGender().toString());
		map.put("address", user.getAddress());
		map.put("chucnangs", lscn.toString());
		map.put("roles", lsrole.toString());
		return map;
	}

	@GetMapping(value = "/oneUserCNS", produces = "application/json")
	@ResponseBody
	public Map<String, String> oneUser(Long id, ModelMap model) {
		User user = this.userService.findById(id);
		List<Long> lscn = new ArrayList<Long>();
		List<Chucnang> lscns = this.chucnangService.findAllChucnangByEmail(user.getEmail());
		for (Chucnang chucnang : lscns) {
			lscn.add(chucnang.getId());
		}
		Map<String, String> map = new HashMap<String, String>();
		map.put("chucnang", lscn.toString());
		return map;
	}

	@RequestMapping(value = "/deleteAll", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET })
	public String deleteAll(@RequestParam("id[]") List<Long> id, HttpSession session) {
		for (Long lg : id) {
			User user = this.userService.findById(lg);
			user.setIsdelete((byte) 1);
			user.setUpdateby((String) session.getAttribute("USERNAME"));
			user.setUpdateday(new Timestamp(new Date().getTime()));
			this.userService.deleteById(user);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/user/search";
		}
		return "redirect:/user/list";
	}

	@RequestMapping(value = "/findByEmail", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String searchRoleKey(@RequestParam("email") String email) {
		User user = this.userService.findByEmail(email);
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			if (user != null) {
				ajaxResponse = mapper.writeValueAsString(user.getId());
			} else {
				ajaxResponse = "-1";
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ajaxResponse;
	}

	@ModelAttribute(name = "ROLES")
	public List<Role> findAllRole() {
		return this.roleService.findByIsDelete(100, 0);
	}

	@ModelAttribute(name = "CNS")
	public List<Chucnang> findChucnang() {
		return this.chucnangService.findAllChucnang(100, 0);
	}

}
