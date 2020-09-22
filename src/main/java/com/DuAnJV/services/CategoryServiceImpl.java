package com.DuAnJV.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Category;
import com.DuAnJV.repositories.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	CategoryRepository categoryRepository;

	@Override
	public void add(Category category) {
		this.categoryRepository.add(category.getId(), category.getCategoryname(), category.getCategorykey()
				, category.getCreatday(), category.getCreatby(), category.getUpdateday(), category.getUpdateby(),category.getIsdelete());
	}

	@Override
	public int update(Category category) {
		return this.categoryRepository.update(category.getCategoryname(), category.getCategorykey(), 
				category.getUpdateday(), category.getUpdateby(), category.getIsdelete(), category.getId());
	}

	@Override
	public List<Category> findAll(int limit, int offset) {
		return this.categoryRepository.findAll(limit, offset);
	}

	@Override
	public long countAll() {
		return this.categoryRepository.countAll();
	}

	@Override
	public Category findById(long id) {
		return this.categoryRepository.findById(id);
	}

	@Override
	public List<Category> findAllByKey(String categorykey, int limit, int offset) {
		return this.categoryRepository.findAllByKey(categorykey, limit, offset);
	}

	@Override
	public long countByKey(String categorykey) {
		return this.categoryRepository.countByKey(categorykey);
	}

	@Override
	public List<Category> findAllByName(String categoryname, int limit, int offset) {
		return this.categoryRepository.findAllByName(categoryname, limit, offset);
	}

	@Override
	public long countByName(String categoryname) {
		return this.categoryRepository.countByName(categoryname);
	}

	@Override
	public List<Category> findAllByNameAndKey(String categoryname, String categorykey, int limit, int offset) {
		return this.categoryRepository.findAllByNameAndKey(categoryname, categorykey, limit, offset);
	}

	@Override
	public long countByNameAndKey(String categoryname, String categorykey) {
		return this.categoryRepository.countByNameAndKey(categoryname, categorykey);
	}

	@Override
	public Category findByKey(String categorykey) {
		return this.categoryRepository.findByKey(categorykey);
	}
	
}
