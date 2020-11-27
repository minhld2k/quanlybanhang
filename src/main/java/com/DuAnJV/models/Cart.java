package com.DuAnJV.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="carts")
public class Cart extends Common{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.DuAnJV.common.IDGenerator")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "customerid")
	private Customer customer;
	
	@Column()
	private Integer trangthai;
	
	@Column()
	private Long tongtien;

	public Cart() {
		super();
	}

	public Cart(Long id, Customer customer, Integer trangthai,Long tongtien) {
		super();
		this.id = id;
		this.customer = customer;
		this.trangthai = trangthai;
		this.tongtien = tongtien;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Integer getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(Integer trangthai) {
		this.trangthai = trangthai;
	}

	public Long getTongtien() {
		return tongtien;
	}

	public void setTongtien(Long tongtien) {
		this.tongtien = tongtien;
	}
	
}
