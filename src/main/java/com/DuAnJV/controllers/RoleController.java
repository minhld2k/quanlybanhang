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
@RequestMapping("/role")
public class RoleController {
	@Autowired
	RoleService roleService;

	@Autowired
	UserService userService;

	@Autowired
	ChucnangService chucnangService;

	@RequestMapping("/list")
	public String listRole(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber,
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
				long count = this.roleService.count();
				session.setAttribute("COUNT", count);
				if (0 == count) {
					session.setAttribute("MESLIST", "Không có role");
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
					model.addAttribute("baseUrl", "/role/list?page=");
					model.addAttribute("ROLES", this.roleService.findByIsDelete(pageSize, (pageNumber - 1) * pageSize));

				}
				session.setAttribute("KEYWORDVIEW", "");
				session.setAttribute("KEYSEARCH", "");
				return "view-role";
			}
		}
	}

	@RequestMapping("/dataSearch")
	public String search(HttpSession session, @RequestParam("keywords") String name,
			@RequestParam(name = "keysearch", required = false) String keysearch) {
		session.setAttribute("KEYWORDVIEW", name);
		session.setAttribute("KEYSEARCH", keysearch);
		if (null == name || name.equals("")) {
			if (null == keysearch || keysearch.equals("")) {
				return "redirect:/role/list";
			} else {
				session.setAttribute("SEARCH", 3);
				return "redirect:/role/search";
			}
		} else {
			if (null == keysearch || keysearch.equals("")) {
				name = replaceDemo.replace(name);
				session.setAttribute("KEYWORD", name);
				session.setAttribute("SEARCH", 2);
				return "redirect:/role/search";
			} else {
				name = replaceDemo.replace(name);
				session.setAttribute("KEYWORD", name);
				session.setAttribute("SEARCH", 1);
				return "redirect:/role/search";
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
		String name = (String) session.getAttribute("KEYWORD");
		String keysearch = (String) session.getAttribute("KEYSEARCH");
		List<Role> ls = null;
		switch (temp) {
		case 1:
			count = this.roleService.countByNameAndKey(name, keysearch);
			ls = this.roleService.findAllByRolenameAndRolekey(name, keysearch, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 2:
			count = this.roleService.countByRolename(name);
			ls = this.roleService.findByRoleNameAndIsDelete(name, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		case 3:
			count = this.roleService.countByRolekey(keysearch);
			ls = this.roleService.findAllByRolekey(keysearch, pageSize, (pageNumber - 1) * pageSize);
			session.setAttribute("COUNT", count);
			break;
		default:
			break;
		}
		if (0 == count) {
			session.setAttribute("MESLIST", "Không tìm thấy Role yêu cầu");
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
			model.addAttribute("baseUrl", "/role/search?page=");
			model.addAttribute("ROLES", ls);
		}
		return "view-role";
	}

	@RequestMapping(value = "/addNew", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String add(@RequestParam("id") Long id, @RequestParam("rolekey") String rolekey,
			@RequestParam("rolename") String rolename, HttpSession session,
			@RequestParam(name = "chucnang[]") List<Long> chucnangid) {
		Role role = new Role(id, rolekey, rolename);
		List<Chucnang> ls = new ArrayList<Chucnang>();
		System.out.println("=>" + chucnangid);
		for (Long long1 : chucnangid) {
			ls.add(this.chucnangService.findById(long1).get());
		}
		role.setChucnang(ls);
		if (null == role.getId() || role.getId().equals("")) {
			role.setId(ThreadLocalRandom.current().nextLong(0, 900000000));
		}
		role.setIsdelete((byte) 0);
		role.setCreatby((String) session.getAttribute("USERNAME"));
		role.setCreatday(new Timestamp(new Date().getTime()));
		role.setUpdateby((String) session.getAttribute("USERNAME"));
		role.setUpdateday(new Timestamp(new Date().getTime()));
		this.roleService.add(role);
		return "redirect:/role/list";
	}

	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String update(@RequestParam("id") Long id, @RequestParam("rolekey") String rolekey,
			@RequestParam("rolename") String rolename, HttpSession session,
			@RequestParam(name = "chucnang[]") List<Long> chucnangid) {
		Role role = new Role(id, rolekey, rolename);
		List<Chucnang> ls = new ArrayList<Chucnang>();
		System.out.println("=>" + chucnangid);
		for (Long long1 : chucnangid) {
			ls.add(this.chucnangService.findById(long1).get());
		}
		role.setChucnang(ls);
		if (null == role.getId() || role.getId().equals("")) {
			role.setId(ThreadLocalRandom.current().nextLong(0, 900000000));
		}
		role.setIsdelete((byte) 0);
		role.setUpdateby((String) session.getAttribute("USERNAME"));
		role.setUpdateday(new Timestamp(new Date().getTime()));
		this.roleService.update(role);
		return "redirect:/role/list";
	}

	@GetMapping(value = "/oneRole", produces = "application/json")
	@ResponseBody
	public Map<String, String> findbyId(Long id, ModelMap model) {
		Role role = this.roleService.findById(id);
		List<Long> ls = this.roleService.findCNSById(id);
		// model.addAttribute("ALLCN",ls);
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", role.getId().toString());
		map.put("rolename", role.getRolename());
		map.put("rolekey", role.getRolekey());
		map.put("chucnang", ls.toString());
		return map;
	}

	@RequestMapping(value = "/deleteAll", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET })
	public String deleteAll(@RequestParam("id[]") List<Long> id, HttpSession session) {
		for (Long lg : id) {
			Role role = this.roleService.findById(lg);
			role.setIsdelete((byte) 1);
			role.setUpdateby((String) session.getAttribute("USERNAME"));
			role.setUpdateday(new Timestamp(new Date().getTime()));
			this.roleService.update(role);

		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/role/search";
		}
		return "redirect:/role/list";
	}

	@RequestMapping(value = "/findByKey", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String searchRoleKey(@RequestParam("rolekey") String rolekey) {
		Role role = this.roleService.findByRoleKeyAndIsDelete(rolekey, (byte) 0);
		ObjectMapper mapper = new ObjectMapper();
		String ajaxResponse = "";
		try {
			if (role != null) {
				ajaxResponse = mapper.writeValueAsString(role.getId());
			} else {
				ajaxResponse = "-1";
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println("=>" + ajaxResponse);
		return ajaxResponse;
	}

	@RequestMapping("/pagesize")
	public String size(@RequestParam(name = "pagesize") int pagesize, HttpSession session) {
		if (0 == pagesize) {
			session.setAttribute("PAGESIZE", 10);
		} else {
			session.setAttribute("PAGESIZE", pagesize);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/role/search";
		}
		return "redirect:/role/list";
	}

	@ModelAttribute(name = "CNS")
	public List<Chucnang> findChucnang() {
		return this.chucnangService.findAllChucnang(100, 0);
	}

}
