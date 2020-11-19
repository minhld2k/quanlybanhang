package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Cart;

public interface CartService {

	Cart save(Cart entity);

	Cart findById(long id);

	long countAll();

	List<Cart> findAll(int limit, int offset);

}
