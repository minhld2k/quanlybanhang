package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Cart;

public interface CartService {

	Cart save(Cart entity);

	Cart findById(long id);

	long countAll();

	List<Cart> findAll(int trangthai,int limit, int offset);

	List<Cart> findCartByCusIdandTrangThai(long cusid, int trangthai);

}
