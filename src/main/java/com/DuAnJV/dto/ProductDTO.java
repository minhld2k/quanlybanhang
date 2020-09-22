package com.DuAnJV.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

public class ProductDTO {
	
	private Long id;
	private String tensanpham;
	private Integer soluong;
	private Double giatien;
	private String ram;
	private String manhinh;
	
	private Integer trangthai;
	private MultipartFile image;
	private String mota;
	
	private Long hangsxid;
	private Long categoryid;
	public ProductDTO() {
		super();
	}
	public ProductDTO(Long id, String tensanpham, Integer soluong, Double giatien, String ram, String manhinh,
			Date ngaythem, Integer trangthai, MultipartFile image, String mota, Long hangsxid, Long categoryid) {
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
		this.hangsxid = hangsxid;
		this.categoryid = categoryid;
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
	
	public String getMota() {
		return mota;
	}
	public void setMota(String mota) {
		this.mota = mota;
	}
	public Long getHangsxid() {
		return hangsxid;
	}
	public void setHangsxid(Long hangsxid) {
		this.hangsxid = hangsxid;
	}
	public Long getCategoryid() {
		return categoryid;
	}
	public void setCategoryid(Long categoryid) {
		this.categoryid = categoryid;
	}
	public MultipartFile getImage() {
		return image;
	}
	public void setImage(MultipartFile image) {
		this.image = image;
	}
	
}
