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

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "qlsp_products")
public class Product extends Common{

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue(generator = "bigid")
    @GenericGenerator(name = "bigid",strategy = "com.DuAnJV.common.IDGenerator")
	private Long id;
	
	@Column
	private String tensanpham;
	
	@Column
	private Integer soluong;
	
	@Column
	private Double giatien;
	
	@Column
	private String ram;
	
	@Column
	private String manhinh;
	
	@Column
	private Integer trangthai;
	
	@Column
	private String image;
	
	@Column
	private String mota;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "hangsxid")
	private Hangsx hangsx;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "categoryid")
	private Category category;
	
	
	public Product() {
		super();
	}

	public Product(Long id, String tensanpham, Integer soluong, Double giatien, String ram, String manhinh,
			Integer trangthai, String image, String mota, Hangsx hangsx, Category category) {
		super();
		this.id = id;
		this.tensanpham = tensanpham;
		this.soluong = soluong;
		this.giatien = giatien;
		this.ram = ram;
		this.manhinh = manhinh;
		this.trangthai = trangthai;
		this.image = image;
		this.mota = mota;
		this.hangsx = hangsx;
		this.category = category;
	}
	
	public Product(Long id, String tensanpham, Integer soluong, Double giatien, String ram, String manhinh,
			Integer trangthai, String image, String mota) {
		super();
		this.id = id;
		this.tensanpham = tensanpham;
		this.soluong = soluong;
		this.giatien = giatien;
		this.ram = ram;
		this.manhinh = manhinh;
		this.trangthai = trangthai;
		this.image = image;
		this.mota = mota;
	}

	public Product(Byte isdelete, String creatby, Date creatday, String updateby, Date updateday, Long id,
			String tensanpham, Integer soluong, Double giatien, String ram, String manhinh,
			Integer trangthai, String image, String mota, Hangsx hangsx, Category category) {
		super(isdelete, creatby, creatday, updateby, updateday);
		this.id = id;
		this.tensanpham = tensanpham;
		this.soluong = soluong;
		this.giatien = giatien;
		this.ram = ram;
		this.manhinh = manhinh;
		this.trangthai = trangthai;
		this.image = image;
		this.mota = mota;
		this.hangsx = hangsx;
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTensanpham() {
		return tensanpham;
	}

	public void setTensanpham(String tensanpham) {
		this.tensanpham = tensanpham;
	}

	public Integer getSoluong() {
		return soluong;
	}

	public void setSoluong(Integer soluong) {
		this.soluong = soluong;
	}

	public Double getGiatien() {
		return giatien;
	}

	public void setGiatien(Double giatien) {
		this.giatien = giatien;
	}

	public String getRam() {
		return ram;
	}

	public void setRam(String ram) {
		this.ram = ram;
	}

	public String getManhinh() {
		return manhinh;
	}

	public void setManhinh(String manhinh) {
		this.manhinh = manhinh;
	}

	public Integer getTrangthai() {
		return trangthai;
	}

	public void setTrangthai(Integer trangthai) {
		this.trangthai = trangthai;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getMota() {
		return mota;
	}

	public void setMota(String mota) {
		this.mota = mota;
	}

	public Hangsx getHangsx() {
		return hangsx;
	}

	public void setHangsx(Hangsx hangsx) {
		this.hangsx = hangsx;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
}
