package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Product;

public interface ProductService {

	Product findById(long id);

	long countAll();

	List<Product> findAll(Integer limit, Integer offset);

	void update(Product product);

	void add(Product product);

}
