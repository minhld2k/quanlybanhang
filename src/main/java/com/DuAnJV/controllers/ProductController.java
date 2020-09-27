package com.DuAnJV.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.dto.ProductDTO;
import com.DuAnJV.models.Category;
import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.Hangsx;
import com.DuAnJV.models.Product;
import com.DuAnJV.models.Role;
import com.DuAnJV.models.User;
import com.DuAnJV.services.CategoryService;
import com.DuAnJV.services.HangsxService;
import com.DuAnJV.services.ProductService;
import com.DuAnJV.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(value="/sanpham")
public class ProductController {
	@Autowired
	ProductService productService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	HangsxService hangsxService;
	
	@RequestMapping("/pagesize")
	public String size(@RequestParam(name = "pagesize") int pagesize, HttpSession session) {
		if (0 == pagesize) {
			session.setAttribute("PAGESIZE", 10);
		} else {
			session.setAttribute("PAGESIZE", pagesize);
		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/sanpham/search";
		}
		return "redirect:/sanpham/list";
	}

	@RequestMapping("/list")
	public String listProduct(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber,
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
				long count = this.productService.countAll();
				session.setAttribute("COUNT", count);
				if (0 == count) {
					session.setAttribute("MESLIST", "Không có sản phẩm");
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
					model.addAttribute("baseUrl", "/sanpham/list?page=");
					model.addAttribute("PRODUCTS", this.productService.findAll(pageSize, (pageNumber - 1) * pageSize));
				}
				session.setAttribute("EMAILFIND", "");
				session.setAttribute("ADDRESSFIND", "");
				session.setAttribute("NAMEFIND", "");
				return "view-product";
			}
		}
	}
	
	@RequestMapping(value = "/addNew", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String add(ModelMap model,HttpSession session,@RequestParam("id")Long id,
			@RequestParam("tensanpham")String tensanpham,@RequestParam("soluong")Integer soluong,
			@RequestParam("giatien")Double giatien,@RequestParam("ram")String ram,
			@RequestParam("manhinh")String manhinh,@RequestParam("trangthai")Integer trangthai,
			@RequestParam("image")MultipartFile image,@RequestParam("mota")String mota,
			@RequestParam("category")Long categoryid,@RequestParam("hangsx")Long hangsxid) {
		String imageDefault = "login2.jpg";
		Path path = Paths.get("uploads/");
		
		if (!image.isEmpty()) {
			try {
				InputStream inputStream = image.getInputStream();
				Files.copy(inputStream, path.resolve(image.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				imageDefault = image.getOriginalFilename().toString();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		Product product = new Product(id, tensanpham,soluong, giatien,ram
				,manhinh, trangthai,imageDefault, mota);
		if (null == product.getId() || product.getId().equals("")) {
			product.setId(ThreadLocalRandom.current().nextLong(0, 900000000));
		}
		product.setIsdelete((byte) 0);
		product.setCreatby((String) session.getAttribute("USERNAME"));
		product.setCreatday(new Timestamp(new Date().getTime()));
		product.setUpdateby((String) session.getAttribute("USERNAME"));
		product.setUpdateday(new Timestamp(new Date().getTime()));
		product.setCategory(this.categoryService.findById(categoryid));
		product.setHangsx(this.hangsxService.findById(hangsxid));
		this.productService.add(product);
		return "redirect:/sanpham/list";
	}
	
	@RequestMapping(value = "/save", method = { RequestMethod.POST, RequestMethod.PUT, RequestMethod.GET })
	public String update(@RequestParam("id")Long id,ModelMap model,HttpSession session,
			@RequestParam("tensanpham")String tensanpham,@RequestParam("ram")String ram,
			@RequestParam("manhinh")String manhinh,@RequestParam("trangthai")Integer trangthai,
			@RequestParam(name="image",required = false)MultipartFile image,@RequestParam("mota")String mota,
			@RequestParam("category")Long categoryid,@RequestParam("hangsx")Long hangsxid) {
		String imageDefault = "login2.jpg";
		Path path = Paths.get("uploads/");
		Product product = this.productService.findById(id);
		if (null == image) {
			imageDefault = product.getImage();
		} else {
			try {
				InputStream inputStream = image.getInputStream();
				Files.copy(inputStream, path.resolve(image.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				imageDefault = image.getOriginalFilename().toString();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
		Product product1 = new Product(id, tensanpham,product.getSoluong(), product.getGiatien(),ram
				,manhinh, trangthai,imageDefault, mota);
		product1.setIsdelete((byte) 0);
		product1.setUpdateby((String) session.getAttribute("USERNAME"));
		product1.setUpdateday(new Timestamp(new Date().getTime()));
		product1.setCategory(this.categoryService.findById(categoryid));
		product1.setHangsx(this.hangsxService.findById(hangsxid));
		this.productService.update(product1);
		return "redirect:/sanpham/list";
	}
	
	@GetMapping(value = "/oneProduct", produces = "application/json")
	@ResponseBody
	public Map<String, String> findbyId(Long id, ModelMap model) {
		Product product = this.productService.findById(id);
		Map<String, String> map = new HashMap<>();
		MultipartFile multiImage = null;
		
		if (product != null) {
			File file = new File("uploads/" + product.getImage());
			FileInputStream input;
			try {
				input = new FileInputStream(file);
				multiImage = new MockMultipartFile("file", file.getName(), "text/plain",
						IOUtils.toByteArray(input));
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		map.put("id", product.getId().toString());
		map.put("tensanpham", product.getTensanpham());
		map.put("soluong", product.getSoluong().toString());
		map.put("giatien", product.getGiatien().toString());
		map.put("ram", product.getRam());
		map.put("manhinh", product.getManhinh());
		map.put("trangthai", product.getTrangthai().toString());
		map.put("image", multiImage.toString());
		map.put("mota", product.getMota());
		map.put("categoryid", product.getCategory().getId().toString());
		map.put("hangsxid", product.getHangsx().getId().toString());
		return map;
	}
	
	@RequestMapping(value = "/updateAjax", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String updateAjax(@RequestParam("idAjax")Long id,@RequestParam("soluongAjax")Integer soluong,
			@RequestParam("giatienAjax")Double giatien,HttpSession session) {
		Product product = this.productService.findById(id);
		String ajaxResponse = "";
		try {
			if (product != null) {
				if (soluong != null) {
					product.setSoluong(soluong);
				}
				if (giatien != null) {
					product.setGiatien(giatien);
				}
				product.setUpdateby((String) session.getAttribute("USERNAME"));
				product.setUpdateday(new Timestamp(new Date().getTime()));
				this.productService.update(product);
				ajaxResponse = "Cập nhật thành công";
			} else {
				ajaxResponse = "Cập nhật thất bại";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ajaxResponse;
	}
	
	@RequestMapping(value = "/deleteAll", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET })
	public String deleteAll(@RequestParam("id[]") List<Long> id, HttpSession session) {
		for (Long lg : id) {
			Product product = this.productService.findById(lg);
			product.setIsdelete((byte) 1);
			product.setUpdateby((String) session.getAttribute("USERNAME"));
			product.setUpdateday(new Timestamp(new Date().getTime()));
			this.productService.update(product);

		}
		if (session.getAttribute("KEYWORD") != null) {
			return "redirect:/sanpham/search";
		}
		return "redirect:/sanpham/list";
	}
	
	@ModelAttribute(name = "CATEGORIES")
	public List<Category> findChucnang() {
		return this.categoryService.findAll(100, 0);
	}
	
	@ModelAttribute(name = "HANGSXES")
	public List<Hangsx> findHSX() {
		return this.hangsxService.findAll(100, 0);
	}
}
