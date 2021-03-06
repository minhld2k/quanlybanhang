package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Product> findAll(Integer limit, Integer offset) {
		return this.productRepository.findAll(limit, offset);
	}

	@Override
	public long countAll() {
		return this.productRepository.countAll();
	}

	@Override
	public Product findById(long id) {
		return this.productRepository.findById(id);
	}
	
	@Cacheable(value = "findAllProduct")
	@Override
	public List<Object[]> findAllProduct(String tensanpham, List<Long> category, List<Long> hangsx,
			List<Integer> trangthai, int limit, int offset) {
		return this.com.findAllProduct(tensanpham, category, hangsx, trangthai, limit, offset);
	}

	@Cacheable(value = "countAllProduct")
	@Override
	public long countAllProduct(String tensanpham, List<Long> category, List<Long> hangsx, List<Integer> trangthai) {
		return this.com.countAllProduct(tensanpham, category, hangsx, trangthai);
	}

	@Cacheable(value = "findProductNew")
	@Override
	public List<Object[]> findProductNew(int limit, int offset) {
		return this.com.findProductNew(limit, offset);
	}

	@Cacheable(value = "findProductHot")
	@Override
	public List<Object[]> findProductHot(int limit, int offset) {
		return this.com.findProductHot(limit, offset);
	}

	@Cacheable(value = "findProductByCategory", key = "#categoryid")
	@Override
	public List<Object[]> findProductByCategory(String categoryid, int limit, int offset) {
		return this.com.findProductByCategory(categoryid, limit, offset);
	}

	@Cacheable(value = "findAllHangsxByCateKey", key = "#categorykey")
	@Override
	public List<Object[]> findAllHangsxByCateKey(String categorykey) {
		return this.com.findAllHangsxByCateKey(categorykey);
	}

	@Cacheable(value = "findAllProductByName" ,key = "#tensanpham")
	@Override
	public List<Object[]> findAllProductByName(String tensanpham, int limit, int offset) {
		return this.com.findAllProductByName(tensanpham, limit, offset);
	}
	
}
