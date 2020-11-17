package com.DuAnJV.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.DuAnJV.dto.CategoryDTO;
import com.DuAnJV.models.Category;
import com.DuAnJV.services.CategoryService;
import com.DuAnJV.services.ProductService;

@Controller
public class HomeController {
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	ProductService productService;
	
	@RequestMapping(value = "/")
	public String index(HttpSession session,ModelMap model) {
		List<Category> lsCate = this.categoryService.findAll(100, 0);
		for (Category category : lsCate) {
			String key = category.getCategorykey();
			model.addAttribute(key, this.productService.findProductByCategory(category.getCategorykey(), 4, 0));
		}
		session.setAttribute("PRODUCTSNEW", this.productService.findProductNew(4, 0));
		return "trangchu/index";
	}
	
    @ModelAttribute(name = "Categorys")
    public List<CategoryDTO> findAllCategory(Model model){
    	List<Category> lsCate = this.categoryService.findAll(100, 0);
    	List<CategoryDTO> ls = new ArrayList<CategoryDTO>();
    	for (Category cate : lsCate) {
			CategoryDTO dto = new CategoryDTO(cate.getId(), cate.getCategorykey(), cate.getCategoryname(), this.productService.findProductByCategory(cate.getCategorykey(), 4, 0));
			ls.add(dto);
		}
    	return ls;
    }
    
    @RequestMapping(value = "/{categorykey}")
    public String findProductByCategoryKey(@PathVariable String categorykey,Model model
    		,@RequestParam(name = "page", required = false) Integer pageNumber, HttpSession session) {
    	if (null == pageNumber || pageNumber < 1) {
			pageNumber = 1;
		}
    	long count = this.productService.findProductByCategory(categorykey,-1,-1).size();
    	int pageSize = 9;
    	if (0 == count) {
			session.setAttribute("MESLIST", "Không có sản phẩm nào");
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
			model.addAttribute("baseUrl", "/"+categorykey+"?page=");
			model.addAttribute("lsProByCate",this.productService.findProductByCategory(categorykey, pageSize, (pageNumber - 1) * pageSize));

		}
		session.setAttribute("CountProByCate", count);
    	session.setAttribute("Title", this.categoryService.findByKey(categorykey).getCategoryname());
    	return "trangchu/view-product-cate";
    }
    
    @GetMapping(value = "/detail/{id}")
    public String productDetail( @PathVariable(name = "id")long id,HttpSession session) {
    	session.setAttribute("ProductDetail",this.productService.findById(id));
    	return "trangchu/preview";
    }
    @RequestMapping("/preview")
    public String preview() {
    	return "trangchu/preview";
    }
}
