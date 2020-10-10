package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Product;

public interface ProductService {

	Product findById(long id);

	long countAll();

	List<Product> findAll(Integer limit, Integer offset);

	void update(Product product);

	void add(Product product);

	List<Object[]> findAllProduct(String tensanpham, List<Long> category, List<Long> hangsx, List<Integer> trangthai, int limit, int offset);

	long countAllProduct(String tensanpham, List<Long> category, List<Long> hangsx, List<Integer> trangthai);

}
