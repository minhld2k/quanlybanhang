package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Cart;
import com.DuAnJV.repositories.CartRepository;

@Service
public class CartServiceImpl implements CartService{
	@Autowired
	CartRepository cartRepository;

	@Override
	public List<Cart> findAll(int limit, int offset) {
		return cartRepository.findAll(limit, offset);
	}

	@Override
	public long countAll() {
		return cartRepository.countAll();
	}

	@Override
	public Cart findById(long id) {
		return cartRepository.findById(id);
	}

	@Override
	public Cart save(Cart entity) {
		return cartRepository.save(entity);
	}

}
