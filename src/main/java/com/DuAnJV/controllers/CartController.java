package com.DuAnJV.controllers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Cart;
import com.DuAnJV.models.CartDetail;
import com.DuAnJV.models.Customer;
import com.DuAnJV.models.Giohang;
import com.DuAnJV.models.User;
import com.DuAnJV.services.CartDetailService;
import com.DuAnJV.services.CartService;
import com.DuAnJV.services.CustomerService;
import com.DuAnJV.services.GioHangService;
import com.DuAnJV.services.UserService;

@Controller
public class CartController {
	@Autowired
	CartService cartService;
	
	@Autowired
	CartDetailService cartDetailService;
	
	@Autowired
	UserService userService;
	
	@Autowired
	CustomerService customerService;
	
	@Autowired
	GioHangService gioHangService;
	
	@RequestMapping("/donhang/pagesize")
	public String size(@RequestParam(name = "pagesize") int pagesize, HttpSession session) {
		if (0 == pagesize) {
			session.setAttribute("PAGESIZE", 10);
		} else {
			session.setAttribute("PAGESIZE", pagesize);
		}
		return "redirect:/donhang/list";
	}
	
	@RequestMapping("/donhang/list")
	public String listCart(ModelMap model, @RequestParam(name = "page", required = false) Integer pageNumber,
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
				long count = this.cartService.countAll();
				session.setAttribute("CountCart", count);
				if (0 == count) {
					session.setAttribute("MesListCart", "Không có đơn hàng ");
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
					model.addAttribute("baseUrl", "/donhang/list?page=");
					model.addAttribute("CARTS",replaceDemo.converCarttoDTO(this.cartService.findAll(0,pageSize, (pageNumber - 1) * pageSize)));
					model.addAttribute("TRANGTHAIS",replaceDemo.convertMapToList());
				}
				return "view-cart";
			}
		}
	}
	
	@RequestMapping("/cart/add")
	public String addCart(ModelMap model,HttpSession session) {
		Customer customer = (Customer) session.getAttribute("UserHome");
		List<Giohang> ls = this.gioHangService.findAll(customer.getId(), 0, 0);
		model.addAttribute("USERHOME",customer);
		model.addAttribute("ListProduct",ls);
		model.addAttribute("TongTien",replaceDemo.SUM(ls));
		return "trangchu/cart";
	}
	
	@RequestMapping("/cart/save")
	public String saveCart(HttpSession session,@RequestParam(value = "fullname")String fullname
			,@RequestParam(value = "phone") String phone,@RequestParam(value = "address")String address
			,@RequestParam(value = "tongtien")long tongtien) {
		Customer customer = (Customer) session.getAttribute("UserHome");
		List<Giohang> ls = this.gioHangService.findAll(customer.getId(), 0, 0);
		
		customer.setAddress(address);
		customer.setFullname(fullname);
		customer.setPhone(phone);
		this.customerService.save(customer);
		session.setAttribute("UserHome", customer);
		
		Cart cart = new Cart();
		cart.setCustomer(customer);
		cart.setTongtien(tongtien);
		cart.setIsdelete((byte) 0);
		cart.setTrangthai(0);
		cart.setCreatby((String) session.getAttribute("USERNAME"));
		cart.setCreatday(new Timestamp(new Date().getTime()));
		this.cartService.save(cart);
		
		for (Giohang giohang : ls) {
			CartDetail cartDetail = new CartDetail();
			cartDetail.setCart(cart);
			cartDetail.setProduct(giohang.getProduct());
			cartDetail.setSoluong(giohang.getSoluong());
			cartDetail.setGiatien(giohang.getGiatien());
			this.cartDetailService.save(cartDetail);
			giohang.setTrangthai(1);
			this.gioHangService.save(giohang);
		}
		
		return "redirect:/";
	}
	
	@RequestMapping(value = "/renderchitiet", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String renderChitiet(@RequestParam("cartid")Long cartid) {
		List<CartDetail> ls =  this.cartDetailService.findAllByCartId(cartid);
		return replaceDemo.renderChitiet(ls,this.cartService.findById(cartid));
	}
	
	@RequestMapping("/cart/capnhat")
	public String capNhatTrangThai(@RequestParam("cartid")long cartid,@RequestParam("trangthai")int trangthai) {
		Cart cart = this.cartService.findById(cartid);
		cart.setTrangthai(trangthai);
		this.cartService.save(cart);
		return "redirect:/donhang/list";
	}

}
