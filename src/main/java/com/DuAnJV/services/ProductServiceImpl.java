package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Product;
import com.DuAnJV.repositories.CommonRepository;
import com.DuAnJV.repositories.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService{
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CommonRepository com = new CommonRepository();

	@Override
	public void add(Product product) {
		this.productRepository.save(product);
	}

	@Override
	public void update(Product product) {
		this.productRepository.save(product);
	}

	@Override
	@CachePut(value="findAllProduct" , key = "#offset")
	public List<Product> findAll(Integer limit, Integer offset) {
		return this.productRepository.findAll(limit, offset);
	}

	@Override
	@CachePut(value="countAllProduct")
	public long countAll() {
		return this.productRepository.countAll();
	}

	@Override
	@CachePut(value="findProductById" , key = "#id")
	public Product findById(long id) {
		return this.productRepository.findById(id);
	}

	@Override
	public List<Object[]> findAllProduct(String tensanpham, List<Long> category, List<Long> hangsx,
			List<Integer> trangthai, int limit, int offset) {
		return this.com.findAllProduct(tensanpham, category, hangsx, trangthai, limit, offset);
	}

	@Override
	public long countAllProduct(String tensanpham, List<Long> category, List<Long> hangsx, List<Integer> trangthai) {
		return this.com.countAllProduct(tensanpham, category, hangsx, trangthai);
	}
	
}
