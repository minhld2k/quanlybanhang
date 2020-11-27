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
@Table(name = "cart_detail")
public class CartDetail {
	
	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.DuAnJV.common.IDGenerator")
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cartid")
	private Cart cart;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "productid")
	private Product product;
	
	@Column
	private Integer soluong;
	
	@Column
	private Long giatien;
	
	public CartDetail() {
		super();
	}

	public CartDetail(Long id, Cart cart, Product product, Integer soluong,Long giatien) {
		super();
		this.id = id;
		this.cart = cart;
		this.product = product;
		this.soluong = soluong;
		this.giatien = giatien;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getSoluong() {
		return soluong;
	}

	public void setSoluong(Integer soluong) {
		this.soluong = soluong;
	}

	public Long getGiatien() {
		return giatien;
	}

	public void setGiatien(Long giatien) {
		this.giatien = giatien;
	}
	
}
