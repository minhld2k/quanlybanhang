package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.CartDetail;

public interface CartDetailService {

	CartDetail findById(long id);

	List<CartDetail> findAllByCartId(long cartid);

	CartDetail save(CartDetail entity);

}
