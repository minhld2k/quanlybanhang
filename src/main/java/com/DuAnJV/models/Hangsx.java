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

@Entity
@Table(name = "qtht_hangsx")
public class Hangsx extends Common {

	private static final long serialVersionUID = 1L;
	
	@Id
	private Long id;
	
	@Column
	private String key;
	
	@Column
	private String name;
	
	@Column
	private String image;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	@JoinTable(name = "category_hangsx",joinColumns = @JoinColumn(name ="hangsxid"),
	inverseJoinColumns = @JoinColumn(name="categoryid"))
	private List<Category> categories;

	public Hangsx() {
		super();
	}

	public Hangsx(Long id, String key, String name, String image, List<Category> categories) {
		super();
		this.id = id;
		this.key = key;
		this.name = name;
		this.image = image;
		this.categories = categories;
	}

	public Hangsx(Byte isdelete, String creatby, Date creatday, String updateby, Date updateday, Long id, String key,
			String name, String image, List<Category> categories) {
		super(isdelete, creatby, creatday, updateby, updateday);
		this.id = id;
		this.key = key;
		this.name = name;
		this.image = image;
		this.categories = categories;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

}
