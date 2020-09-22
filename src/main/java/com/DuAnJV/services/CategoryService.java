package com.DuAnJV.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.DuAnJV.models.Category;

public interface CategoryService {

	int update(Category category);

	void add(Category category);

	long countByNameAndKey(String categoryname, String categorykey);

	List<Category> findAllByNameAndKey(String categoryname, String categorykey, int limit, int offset);

	long countByName(String categoryname);

	List<Category> findAllByName(String categoryname, int limit, int offset);

	long countByKey(String categorykey);

	List<Category> findAllByKey(String categorykey, int limit, int offset);

	Category findById(long id);

	long countAll();

	List<Category> findAll(int limit, int offset);

	Category findByKey(String categorykey);
	
}
