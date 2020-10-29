package com.DuAnJV.controllers;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.DuAnJV.models.Category;
import com.DuAnJV.models.Hangsx;
import com.DuAnJV.models.User;
import com.DuAnJV.services.CategoryService;
import com.DuAnJV.services.HangsxService;
import com.DuAnJV.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value = "/hangsx")
public class HangsxController {

	@Autowired
	HangsxService hangsxService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping("/pagesize")
	public String size(@RequestParam(name = "pagesize") int pagesize, HttpSession session) {
		if (0 == pagesize) {
			session.setAttribute("PAGESIZE", 10);
		} else {
			session.setAttribute("PAGESIZE", pagesize);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/hangsx/search";
		}
		return "redirect:/hangsx/list";
	}
	
	@RequestMapping("/list")
	public String listCategory(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber,
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
				long count = this.hangsxService.countAll();
				session.setAttribute("COUNT", count);
				if (0 == count) {
					session.setAttribute("MESLIST", "Không có Hãng sản xuất");
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
					model.addAttribute("baseUrl", "/hangsx/list?page=");
					model.addAttribute("HANGSXS", this.hangsxService.findAll(pageSize, (pageNumber - 1) * pageSize));
				}
				return "view-hangsx";
			}
		}
	}
	
	@RequestMapping(value = "/addNew", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String add(@RequestParam("id") Long id, @RequestParam("key") String key,
			@RequestParam("name") String name, @RequestParam(value = "category[]",required = false) List<Long> categories, HttpSession session) {
		List<Category> lscate = new ArrayList<Category>();
		for (Long l : categories) {
			lscate.add(this.categoryService.findById(l));
		}
		
		Hangsx hagsx = new Hangsx(id, key, name, "", lscate);
		hagsx.setIsdelete((byte) 0);
		hagsx.setCreatby((String) session.getAttribute("USERNAME"));
		hagsx.setCreatday(new Timestamp(new Date().getTime()));
		hagsx.setUpdateby((String) session.getAttribute("USERNAME"));
		hagsx.setUpdateday(new Timestamp(new Date().getTime()));
		this.hangsxService.add(hagsx);
		return "redirect:/hangsx/list";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String update(@RequestParam("id") Long id, @RequestParam("key") String key,
			@RequestParam("name") String name, @RequestParam(value = "category[]",required = false) List<Long> categories, HttpSession session) {
		List<Category> lscate = new ArrayList<Category>();
		for (Long l : categories) {
			lscate.add(this.categoryService.findById(l));
		}
		
		Hangsx hagsx = this.hangsxService.findById(id);
		hagsx.setName(name);
		hagsx.setKey(key);
		hagsx.setCategories(lscate);
		hagsx.setIsdelete((byte) 0);
		hagsx.setUpdateby((String) session.getAttribute("USERNAME"));
		hagsx.setUpdateday(new Timestamp(new Date().getTime()));
		this.hangsxService.update(hagsx);
		return "redirect:/hangsx/list";
	}
	
	@GetMapping(value = "/oneHangsx", produces = "application/json")
	@ResponseBody
	public Map<String, String> findbyId(Long id, ModelMap model) {
		Hangsx hangsx = this.hangsxService.findById(id);
		List<Long> ls = this.hangsxService.findCategoryById(id);
		// model.addAttribute("ALLCN",ls);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", hangsx.getId().toString());
		map.put("name", hangsx.getName());
		map.put("key", hangsx.getKey());
		map.put("category", ls.toString());
		return map;
	}

	@RequestMapping(value = "/deleteAll", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET })
	public String deleteAll(@RequestParam("id[]") List<Long> id, HttpSession session) {
		for (Long lg : id) {
			Hangsx hangsx = this.hangsxService.findById(lg);
			hangsx.setIsdelete((byte) 1);
			hangsx.setUpdateby((String) session.getAttribute("USERNAME"));
			hangsx.setUpdateday(new Timestamp(new Date().getTime()));
			this.hangsxService.update(hangsx);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/hangsx/search";
		}
		return "redirect:/hangsx/list";
	}
	
	@RequestMapping(value = "/findByKey", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String searchKey(@RequestParam("key") String key) {
		Hangsx hangsx = this.hangsxService.findByKey(key);
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			if (null != hangsx) {
				ajaxResponse = mapper.writeValueAsString(hangsx.getId());
			} else {
				ajaxResponse = "-1";
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("=>" + ajaxResponse);
		return ajaxResponse;
	}
	
	@RequestMapping("/dataSearch")
	public String search(HttpSession session, @RequestParam("keywords") String name,
			@RequestParam(name = "categorySearch[]", required = false) List<Long> categoryid) {
		session.setAttribute("NAMESEARCH", name);
		session.setAttribute("CATE", categoryid);
		if (null == name || name.equals("")) {
			if (null == categoryid) {
				return "redirect:/hangsx/list";
			} else {
				session.setAttribute("SEARCH", 3);
				return "redirect:/hangsx/search";
			}
		} else {
			if (categoryid != null) {
				name = replaceDemo.replace(name);
				session.setAttribute("KEYWORD", name);
				session.setAttribute("SEARCH", 1);
				return "redirect:/hangsx/search";
			} else {
				name = replaceDemo.replace(name);
				session.setAttribute("KEYWORD", name);
				session.setAttribute("SEARCH", 2);
				return "redirect:/hangsx/search";
			}
		}
	}
	
	@RequestMapping("/search")
	public String searchCategory(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber
			, HttpSession session) {
		if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
		int temp = (Integer) session.getAttribute("SEARCH");
		int pageSize = (Integer) session.getAttribute("PAGESIZE");
		long count = 0;
		String name = (String) session.getAttribute("KEYWORD");
		@SuppressWarnings("unchecked")
		List<Long> categoryid = (List<Long>) session.getAttribute("CATE");
		List<Hangsx> ls = null;
		switch (temp) {
		case 1:
			count = this.hangsxService.countAllByNameAndDM(name, categoryid);
			ls = this.hangsxService.findAllByNameAndDM(name, categoryid, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 2:
			count = this.hangsxService.countAllByName(name);
			ls = this.hangsxService.findAllByName(name, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 3:
			count = this.hangsxService.countAllByDM(categoryid);
			ls = this.hangsxService.findAllByDM(categoryid, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		default:
			break;
		}
		if (0 == count) {
			session.setAttribute("MESLIST", "Không tìm thấy Hãng sản xuất yêu cầu");
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
			model.addAttribute("baseUrl", "/hangsx/search?page=");
			model.addAttribute("HANGSXS", ls);
		}
		return "view-hangsx";
	}

	
	@ModelAttribute(name="CATEGORIES")
	public List<Category> getAllCategory(){
		return this.categoryService.findAll(100, 0);
	}

}
