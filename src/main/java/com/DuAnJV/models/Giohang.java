package com.DuAnJV.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="qlsp_giohangs")
public class Giohang {
	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.DuAnJV.common.IDGenerator")
	private Long id;
	
	@Column
	private Integer soluong;
	
	@Column
	private Integer trangthai;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.TIMESTAMP)
	private Date ngayadd;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid")
	private Product product;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerid")
	private Customer customer;
	
	@Column
	private Long giatien;

	public Giohang() {
		super();
	}

	public Giohang(Long id, Integer soluong, Integer trangthai, Date ngayadd, Product product, Customer customer,Long giatien) {
		super();
		this.id = id;
		this.soluong = soluong;
		this.trangthai = trangthai;
		this.ngayadd = ngayadd;
		this.product = product;
		this.customer = customer;
		this.giatien = giatien;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getSoluong() {
		return soluong;
	}

	public void setSoluong(Integer soluong) {
		this.soluong = soluong;
	}

	public Integer getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(Integer trangthai) {
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
