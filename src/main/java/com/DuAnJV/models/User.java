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
@Table(name = "qtht_users")
public class User {
	
	@Id
	private Long id;
	@Column
	private String email;
	@Column
	private String password;
	@Column
	private String fullname;
	
	@Column
	private String address;
	
	@Column
	private String phone;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date birthday;
	
	@Column
	private Byte gender;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date creatday;
	
	@Column
	private String creatby;
	
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Temporal(TemporalType.DATE)
	private Date updateday;
	
	@Column
	private String updateby;
	@Column
	private Byte isdelete;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "user_role",joinColumns = @JoinColumn(name ="userid"),
	inverseJoinColumns = @JoinColumn(name="roleid"))
	private List<Role> roles ;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "user_chucnang",joinColumns = @JoinColumn(name ="userid"),
	inverseJoinColumns = @JoinColumn(name="chucnangid"))
	private List<Chucnang> chucnangs ;
	
	
	public User() {
		super();
	}

	public User(Long id, String email, String fullname, String address, String phone, Date birthday,
			Byte gender) {
		super();
		this.id = id;
		this.email = email;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
	}



	public User(Long id, String email, String password, String fullname, String address, String phone, Date birthday,
			Byte gender, Date creatday, String creatby, Date updateday, String updateby, Byte isdelete,
			List<Role> roles,List<Chucnang> chucnangs) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.fullname = fullname;
		this.address = address;
		this.phone = phone;
		this.birthday = birthday;
		this.gender = gender;
		this.creatday = creatday;
		this.creatby = creatby;
		this.updateday = updateday;
		this.updateby = updateby;
		this.isdelete = isdelete;
		this.roles = roles;
		this.chucnangs = chucnangs;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPhone() {
		return phone;
	}



	public void setPhone(String phone) {
		this.phone = phone;
	}



	public Date getBirthday() {
		return birthday;
	}



	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}



	public Byte getGender() {
		return gender;
	}



	public void setGender(Byte gender) {
		this.gender = gender;
	}



	public List<Role> getRoles() {
		return roles;
	}



	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<Chucnang> getChucnangs() {
		return chucnangs;
	}

	public void setChucnangs(List<Chucnang> chucnangs) {
		this.chucnangs = chucnangs;
	}

	
}
