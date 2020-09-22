package com.DuAnJV.models;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "qtht_roles")
public class Role {
	
	@Id
	private Long id;
	
	@Column
	private String rolekey;
	
	@Column
	private String rolename;
	
	@Column
	private String creatby;
	
	@Column
	private String updateby;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date creatday;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date updateday;
	
	@Column
	private byte isdelete;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "vaitrovachucnang",joinColumns = @JoinColumn(name ="roleid"),
	inverseJoinColumns = @JoinColumn(name="chucnangid"))
	private List<Chucnang> chucnang;
	
	public Role() {
		super();
	}


	public Role(Long id, String rolekey, String rolename) {
		super();
		this.id = id;
		this.rolekey = rolekey;
		this.rolename = rolename;
	}

	public Role(Long id, String rolekey, String rolename, Date creatday, String creatby, Date updateday,
			String updateby, Byte isdelete, List<Chucnang> chucnang) {
		super();
		this.id = id;
		this.rolekey = rolekey;
		this.rolename = rolename;
		this.creatday = creatday;
		this.creatby = creatby;
		this.updateday = updateday;
		this.updateby = updateby;
		this.isdelete = isdelete;
		this.chucnang = chucnang;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getRolekey() {
		return rolekey;
	}


	public void setRolekey(String rolekey) {
		this.rolekey = rolekey;
	}


	public String getRolename() {
		return rolename;
	}


	public void setRolename(String rolename) {
		this.rolename = rolename;
	}


	public Date getCreatday() {
		return creatday;
	}


	public void setCreatday(Date creatday) {
		this.creatday = creatday;
	}


	public String getCreatby() {
		return creatby;
	}


	public void setCreatby(String creatby) {
		this.creatby = creatby;
	}


	public Date getUpdateday() {
		return updateday;
	}


	public void setUpdateday(Date updateday) {
		this.updateday = updateday;
	}


	public String getUpdateby() {
		return updateby;
	}


	public void setUpdateby(String updateby) {
		this.updateby = updateby;
	}


	public Byte getIsdelete() {
		return isdelete;
	}


	public void setIsdelete(Byte isdelete) {
		this.isdelete = isdelete;
	}


	public List<Chucnang> getChucnang() {
		return chucnang;
	}


	public void setChucnang(List<Chucnang> chucnang) {
		this.chucnang = chucnang;
	}
	
	

}
