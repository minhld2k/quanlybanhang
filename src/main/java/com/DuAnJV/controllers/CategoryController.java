package com.DuAnJV.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Category;
import com.DuAnJV.models.User;
import com.DuAnJV.services.CategoryService;
import com.DuAnJV.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping("/category")
public class CategoryController {

	@Autowired
	CategoryService categoryService;

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
			return "redirect:/category/search";
		}
		return "redirect:/category/list";
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
				long count = this.categoryService.countAll();
				session.setAttribute("COUNT", count);
				if (0 == count) {
					session.setAttribute("MESLIST", "Không có Danh mục");
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
					model.addAttribute("baseUrl", "/category/list?page=");
					model.addAttribute("CATEGORIES", this.categoryService.findAll(pageSize, (pageNumber - 1) * pageSize));
				}
				return "view-category";
			}
		}
	}

	@RequestMapping("/dataSearch")
	public String search(HttpSession session, @RequestParam("keywords") String categoryname,
			@RequestParam(name = "keysearch", required = false) String categorykey) {
		session.setAttribute("KEYSEARCH", categorykey);
		session.setAttribute("KEYWORDVIEW", categoryname);
		if (null == categoryname || categoryname.equals("")) {
			if (null == categorykey || categorykey.equals("")) {
				return "redirect:/category/list";
			} else {
				session.setAttribute("SEARCH", 3);
				return "redirect:/category/search";
			}
		} else {
			if (categorykey != null) {
				categoryname = replaceDemo.replace(categoryname);
				session.setAttribute("KEYWORD", categoryname);
				session.setAttribute("SEARCH", 1);
				return "redirect:/category/search";
			} else {
				categoryname = replaceDemo.replace(categoryname);
				session.setAttribute("KEYWORD", categoryname);
				session.setAttribute("SEARCH", 2);
				return "redirect:/category/search";
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
		String keysearch = (String) session.getAttribute("KEYSEARCH");
		List<Category> ls = null;
		switch (temp) {
		case 1:
			count = this.categoryService.countByNameAndKey(name, keysearch);
			ls = this.categoryService.findAllByNameAndKey(name, keysearch, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 2:
			count = this.categoryService.countByName(name);
			ls = this.categoryService.findAllByName(name, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 3:
			count = this.categoryService.countByKey(keysearch);
			ls = this.categoryService.findAllByKey(keysearch, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		default:
			break;
		}
		if (0 == count) {
			session.setAttribute("MESLIST", "Không tìm thấy Danh mục yêu cầu");
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
			model.addAttribute("baseUrl", "/category/search?page=");
			model.addAttribute("CATEGORIES", ls);
		}
		return "view-category";
	}

	@RequestMapping(value = "/addNew", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String add(@RequestParam("id") Long id, @RequestParam("categorykey") String categorykey,
			@RequestParam("categoryname") String categoryname, HttpSession session) {
		
		Category category = new Category(id, categoryname, categorykey);
		category.setIsdelete((byte) 0);
		category.setCreatby((String) session.getAttribute("USERNAME"));
		category.setCreatday(new Timestamp(new Date().getTime()));
		category.setUpdateby((String) session.getAttribute("USERNAME"));
		category.setUpdateday(new Timestamp(new Date().getTime()));
		this.categoryService.add(category);
		return "redirect:/category/list";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String update(@RequestParam("id") Long id, @RequestParam("categorykey") String categorykey,
			@RequestParam("categoryname") String categoryname, HttpSession session) {
		
		Category category = this.categoryService.findById(id);
		category.setCategorykey(categorykey);
		category.setCategoryname(categoryname);
		category.setIsdelete((byte) 0);
		category.setUpdateby((String) session.getAttribute("USERNAME"));
		category.setUpdateday(new Timestamp(new Date().getTime()));
		this.categoryService.update(category);
		return "redirect:/category/list";
	}
	
	@GetMapping("/oneCategory")
	@ResponseBody
	public Category findbyId(long id, Model model) {
		return this.categoryService.findById(id);
	}

	@RequestMapping(value = "/deleteAll", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET })
	public String deleteAll(@RequestParam("id[]") List<Long> id, HttpSession session) {
		for (Long lg : id) {
			Category category = this.categoryService.findById(lg);
			category.setIsdelete((byte) 1);
			category.setUpdateby((String) session.getAttribute("USERNAME"));
			category.setUpdateday(new Timestamp(new Date().getTime()));
			this.categoryService.update(category);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/category/search";
		}
		return "redirect:/category/list";
	}

	@RequestMapping(value = "/findByKey", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String searchKey(@RequestParam("categorykey") String categorykey) {
		Category cn = this.categoryService.findByKey(categorykey);
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
		System.out.println("=>" + ajaxResponse);
		return ajaxResponse;
	}

}
