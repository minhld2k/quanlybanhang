package com.DuAnJV.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Customer;
import com.DuAnJV.models.Giohang;
import com.DuAnJV.models.Product;
import com.DuAnJV.models.User;
import com.DuAnJV.services.GioHangService;
import com.DuAnJV.services.ProductService;

@Controller
@RequestMapping("/giohang")
public class GioHangController {
	@Autowired
	GioHangService gioHangService;
	
	@Autowired
	ProductService productService;

	@RequestMapping("/list")
	public String listUser(ModelMap model, HttpSession session) {
		
		Customer customer = (Customer) session.getAttribute("UserHome");
		
		if (customer == null) {
			return "redirect:/login";
		}else {
			long count = this.gioHangService.count(customer.getId());
			session.setAttribute("CountGioHang", count);
			if (0 == count) {
				session.setAttribute("MesListGioHang", "Không có sản phẩm nào");
			} else {
				model.addAttribute("GIOHANGS",replaceDemo.converttoDTO(this.gioHangService.findAll(customer.getId(), 0, 0)));
			}
			String sum = String.valueOf(replaceDemo.SUM(this.gioHangService.findAll(customer.getId(), 0, 0)));
			session.setAttribute("SUM", sum);
			return "trangchu/view-giohang";
		}
		
	}
	
	@RequestMapping(value = "/save", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String updateAjax(@RequestParam("id")Long id,@RequestParam("soluong")Integer soluong,
			@RequestParam("productid")long productid,HttpSession session) {
		Giohang giohang = null;
		Product product = null;
		String ajaxResponse = "";
		if (productid != 0) {
			product = this.productService.findById(productid);
		}
		Customer customer = (Customer) session.getAttribute("UserHome");
		if (customer != null) {
			if (id == null) {
				giohang = new Giohang();
				giohang.setProduct(product);
				giohang.setCustomer(customer);
				giohang.setSoluong(soluong);
				giohang.setTrangthai(0);
				giohang.setGiatien(product.getGiatien());
			}else {
				giohang = this.gioHangService.findById(id);
				giohang.setSoluong(soluong);
			}

			this.gioHangService.save(giohang);
			String sum = String.valueOf(replaceDemo.SUM(this.gioHangService.findAll(customer.getId(), 0, 0)));
			session.setAttribute("SUM", sum);
			ajaxResponse = sum;
			return ajaxResponse;
		}else {
			return "-1";
		}
		
	}
	
	@RequestMapping(value = "/deleteAll", method = { RequestMethod.DELETE, RequestMethod.PUT, RequestMethod.GET })
	public String deleteAll(@RequestParam("id[]") List<Long> id, HttpSession session) {
		for (Long lg : id) {
			Giohang giohang = this.gioHangService.findById(lg);
			this.gioHangService.delete(giohang);

		}
		return "redirect:/giohang/list";
	}
	
}
