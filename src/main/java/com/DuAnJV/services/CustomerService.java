package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Customer;

public interface CustomerService {

	Customer findByEmail(String email);

	Customer findByid(Long id);

	long count();

	List<Customer> findAll(Integer pagesize, Integer offset);

	List<Object[]> findAllCustomer(String email, String name, String address, List<Byte> gender, int limit, int offset);

	Customer save(Customer customer);

	boolean checkLogin(String email, String password);

}
