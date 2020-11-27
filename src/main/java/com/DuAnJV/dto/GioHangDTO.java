package com.DuAnJV.dto;

import java.util.Date;

import com.DuAnJV.models.Customer;
import com.DuAnJV.models.Product;

public class GioHangDTO {
	private long id;
	private int soluong;
	private String trangthai;
	private Date ngayadd;
	private Product product;
	private Customer customer;
	private Long giatien;
	public GioHangDTO() {
		super();
	}
	public GioHangDTO(long id, int soluong, String trangthai, Date ngayadd, Product product, Customer customer,Long giatien) {
		super();
		this.id = id;
		this.soluong = soluong;
		this.trangthai = trangthai;
		this.ngayadd = ngayadd;
		this.product = product;
		this.customer = customer;
		this.giatien = giatien;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getSoluong() {
		return soluong;
	}
	public void setSoluong(int soluong) {
		this.soluong = soluong;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public Date getNgayadd() {
		return ngayadd;
	}
	public void setNgayadd(Date ngayadd) {
		this.ngayadd = ngayadd;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Long getGiatien() {
		return giatien;
	}
	public void setGiatien(Long giatien) {
		this.giatien = giatien;
	}
	
}
