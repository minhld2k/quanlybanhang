package com.DuAnJV.dto;

import com.DuAnJV.models.Customer;

public class CartDTO {
	private long id;
	private Customer customer;
	private String trangthai;
	private Long tongtien;
	private int trangthaiso;
	
	public CartDTO() {
		super();
	}
	public CartDTO(long id, Customer customer, String trangthai,Long tongtien,int trangthaiso) {
		super();
		this.id = id;
		this.customer = customer;
		this.trangthai = trangthai;
		this.tongtien = tongtien;
		this.trangthaiso = trangthaiso;
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
	public Long getTongtien() {
		return tongtien;
	}
	public void setTongtien(Long tongtien) {
		this.tongtien = tongtien;
	}
	public int getTrangthaiso() {
		return trangthaiso;
	}
	public void setTrangthaiso(int trangthaiso) {
		this.trangthaiso = trangthaiso;
	}
	
}
