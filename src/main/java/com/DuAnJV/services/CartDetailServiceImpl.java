package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.CartDetail;
import com.DuAnJV.repositories.CartDetailRepository;

@Service
public class CartDetailServiceImpl implements CartDetailService{
	@Autowired
	CartDetailRepository cartDetailRepository;

	@Override
	public List<CartDetail> findAllByCartId(long cartid) {
		return this.cartDetailRepository.findAllByCartId(cartid);
	}

	@Override
	public CartDetail findById(long id) {
		return this.cartDetailRepository.findById(id);
	}

	@Override
	public CartDetail save(CartDetail entity) {
		return this.cartDetailRepository.save(entity);
	}
	
}
