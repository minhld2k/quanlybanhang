package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		this.productRepository.add(product.getId(), product.getTensanpham(), product.getSoluong(), product.getGiatien()
				, product.getRam(), product.getManhinh(), product.getTrangthai(), product.getCreatby(), product.getCreatday(),
				product.getUpdateby(), product.getUpdateday(), product.getIsdelete(), product.getHangsx().getId()
				, product.getCategory().getId(), product.getImage(), product.getMota());
	}

	@Override
	public void update(Product product) {
		this.productRepository.update(product.getTensanpham(), product.getSoluong(), product.getGiatien()
				, product.getRam(), product.getManhinh(), product.getTrangthai(),
				product.getUpdateby(), product.getUpdateday(), product.getIsdelete(), product.getHangsx().getId()
				, product.getCategory().getId(), product.getImage(), product.getMota(),product.getId());
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
