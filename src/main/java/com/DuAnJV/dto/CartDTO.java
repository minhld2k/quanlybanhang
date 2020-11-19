package com.DuAnJV.dto;

import com.DuAnJV.models.Customer;

public class CartDTO {
	private long id;
	private Customer customer;
	private String trangthai;
	private Double tongtien;
	
	public CartDTO() {
		super();
	}
	public CartDTO(long id, Customer customer, String trangthai,Double tongtien) {
		super();
		this.id = id;
		this.customer = customer;
		this.trangthai = trangthai;
		this.tongtien = tongtien;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public Double getTongtien() {
		return tongtien;
	}
	public void setTongtien(Double tongtien) {
		this.tongtien = tongtien;
	}
	
}
