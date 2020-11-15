package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Customer;
import com.DuAnJV.models.User;
import com.DuAnJV.repositories.CommonRepository;
import com.DuAnJV.repositories.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	CommonRepository com = new CommonRepository();

	@Override
	public List<Customer> findAll(Integer pagesize, Integer offset) {
		return customerRepository.findAll(pagesize, offset);
	}

	@Override
	public long count() {
		return customerRepository.count();
	}

	@Override
	public Customer findByid(Long id) {
		return customerRepository.findByid(id);
	}

	@Override
	public Customer findByEmail(String email) {
		return customerRepository.findByEmail(email);
	}

	@Override
	public List<Object[]> findAllCustomer(String email, String name, String address, List<Byte> gender, int limit,
			int offset) {
		return com.findAllCustomer(email, name, address, gender, limit, offset);
	}

	@Override
	public Customer save(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public boolean checkLogin(String email, String password) {
		Customer cus = this.customerRepository.findByEmail(email);
		if (null != cus && replaceDemo.checkBcrypt(password, cus.getPassword())) {
			return true;
		}	
		return false;
	}
	
	
}
