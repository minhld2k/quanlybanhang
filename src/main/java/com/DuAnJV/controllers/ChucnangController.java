package com.DuAnJV.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.User;
import com.DuAnJV.services.ChucnangService;
import com.DuAnJV.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/chucnang")
public class ChucnangController {

	@Autowired
	ChucnangService chucnangService;

	@Autowired
	UserService userService;

	@RequestMapping("/list")
	public String listRole(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber,
			HttpSession session, HttpServletRequest request) {
		System.out.println("listRole: "+chucnangService.findAllChucnang(10, 0).get(0));
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
				long count = this.chucnangService.count();
				
				if (0 == count) {
					session.setAttribute("MESLIST", "Không có Chức năng");
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
					model.addAttribute("baseUrl", "/chucnang/list?page=");
					model.addAttribute("CHUCNANGS",
							this.chucnangService.findAllChucnang(pageSize, (pageNumber - 1) * pageSize));

				}
				session.setAttribute("COUNT", count);
				session.setAttribute("KEYWORDVIEW", "");
				session.setAttribute("KEYSEARCH", "");
				return "view-chucnang";
			}
		}
	}

	@RequestMapping(value = "/addNew", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String add(Chucnang chucnang, HttpSession session) {
		chucnang.setIsdelete((byte) 0);
		chucnang.setCreatby((String) session.getAttribute("USERNAME"));
		chucnang.setCreatday(new Timestamp(new Date().getTime()));
		chucnang.setUpdateby((String) session.getAttribute("USERNAME"));
		chucnang.setUpdateday(new Timestamp(new Date().getTime()));
		this.chucnangService.add(chucnang);
		return "redirect:/chucnang/list";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String update(Chucnang chucnang, HttpSession session) {
		chucnang.setIsdelete((byte) 0);
		chucnang.setUpdateby((String) session.getAttribute("USERNAME"));
		chucnang.setUpdateday(new Timestamp(new Date().getTime()));
		this.chucnangService.update(chucnang);
		return "redirect:/chucnang/list";
	}

	@GetMapping("/oneChucnang")
	@ResponseBody
	public Optional<Chucnang> findbyId(Long id, Model model) {
		return this.chucnangService.findById(id);
	}

	@RequestMapping(value = "/deleteAll", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET })
	public String deleteAll(@RequestParam("id[]") List<Long> id, HttpSession session) {
		for (Long lg : id) {
			Chucnang chucnang = this.chucnangService.findById(lg).get();
			if (chucnang.getChucnangcha().getId() > 0) {
				chucnang.setIsdelete((byte) 1);
				chucnang.setUpdateby((String) session.getAttribute("USERNAME"));
				chucnang.setUpdateday(new Timestamp(new Date().getTime()));
				this.chucnangService.update(chucnang);
			}
		}
		for (Long long1 : id) {
			Chucnang chucnang = this.chucnangService.findById(long1).get();
			if (chucnang.getChucnangcha().getId() < 0 && 0 == this.chucnangService.count(chucnang.getId())) {
				chucnang.setIsdelete((byte) 1);
				chucnang.setUpdateby((String) session.getAttribute("USERNAME"));
				chucnang.setUpdateday(new Timestamp(new Date().getTime()));
				this.chucnangService.update(chucnang);
			}
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/chucnang/search";
		}
		return "redirect:/chucnang/list";
	}

	@RequestMapping(value = "/findByKey", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String searchKey(@RequestParam("key") String key) {
		Chucnang cn = this.chucnangService.findByKey(key);
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			if (null != cn) {
				ajaxResponse = mapper.writeValueAsString(cn.getId());
			} else {
				ajaxResponse = "-1";
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return ajaxResponse;
	}

	@RequestMapping("/dataSearch")
	public String search(HttpSession session, @RequestParam("keywords") String name,
			@RequestParam(name = "keysearch", required = false) String keysearch) {
		session.setAttribute("KEYSEARCH", keysearch);
		session.setAttribute("KEYWORDVIEW", name);
		if (null == name || name.equals("")) {
			if (null == keysearch || keysearch.equals("")) {
				return "redirect:/chucnang/list";
			} else {
				session.setAttribute("SEARCH", 3);
				return "redirect:/chucnang/search";
			}
		} else {
			if (keysearch != null) {
				name = replaceDemo.replace(name);
				session.setAttribute("KEYWORD", name);
				session.setAttribute("SEARCH", 1);
				return "redirect:/chucnang/search";
			} else {
				name = replaceDemo.replace(name);
				session.setAttribute("KEYWORD", name);
				session.setAttribute("SEARCH", 2);
				return "redirect:/chucnang/search";
			}
		}
	}

	@RequestMapping("/search")
	public String searchUser(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber
			, HttpSession session) {
		if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
		int temp = (Integer) session.getAttribute("SEARCH");
		int pageSize = (Integer) session.getAttribute("PAGESIZE");
		long count = 0;
		String name = (String) session.getAttribute("KEYWORD");
		String keysearch = (String) session.getAttribute("KEYSEARCH");
		List<Chucnang> ls = null;
		switch (temp) {
		case 1:
			count = this.chucnangService.countByNameAndKey(name, keysearch);
			ls = this.chucnangService.findAllByNameAndKey(name, keysearch, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 2:
			count = this.chucnangService.countByName(name, (byte) 0);
			ls = this.chucnangService.findByNameAndIsdelete(name, (byte) 0, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 3:
			count = this.chucnangService.countByKey(keysearch);
			ls = this.chucnangService.findAllByKey(keysearch, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		default:
			break;
		}
		if (0 == count) {
			session.setAttribute("MESLIST", "Không tìm thấy Chức năng yêu cầu");
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
			model.addAttribute("baseUrl", "/chucnang/search?page=");
			model.addAttribute("CHUCNANGS", ls);
		}
		return "view-chucnang";
	}

	@RequestMapping("/pagesize")
	public String size(@RequestParam(name = "pagesize") int pagesize, HttpSession session) {
		if (0 == pagesize) {
			session.setAttribute("PAGESIZE", 10);
		} else {
			session.setAttribute("PAGESIZE", pagesize);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/chucnang/search";
		}
		return "redirect:/chucnang/list";
	}

	@ModelAttribute(name = "CNCS")
	public List<Chucnang> findall(Model model) {
		return this.chucnangService.findAllChucnangcha();
	}

	@ModelAttribute(name = "ALLKEY")
	public List<String> allKey(Model model) {
		return this.chucnangService.AllKey();
	}
}
