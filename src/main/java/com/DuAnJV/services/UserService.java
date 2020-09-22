package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.User;

public interface UserService {

	long count();

	List<User> findAll(Integer pagesize, Integer offset);

	void update(User user);

	void add(User user);

	User findById(Long id);
	
	boolean checkLogin(String email,String password);

	List<Long> findCNByRole(List<Long> roleid);

	List<Long> findCNSById(Long id);

	List<Long> findRoleById(Long id);

	void deleteById(User user);

	User findByEmail(String email);

	long countByEmailsAndFullnameAndAddressAndGender(String fullname, String address, String email, List<Byte> gender);

	List<User> findByEmailsAndFullnameAndAddressAndGender(String fullname, String address, String email, List<Byte> gender, Integer pagesize,
			Integer offset);

	long countByFullnameAndAddressAndGender(String fullname, String address, List<Byte> gender);

	List<User> findByFullnameAndAddressAndGender(String fullname, String address, List<Byte> gender, Integer pagesize, Integer offset);

	long countByEmailsAndAddressAndGender(String address, String email, List<Byte> gender);

	List<User> findByEmailsAndAddressAndGender(String address, String email, List<Byte> gender, Integer pagesize, Integer offset);

	long countByEmailsAndFullnameAndGender(String fullname, String email, List<Byte> gender);

	List<User> findByEmailsAndFullnameAndGender(String fullname, String email, List<Byte> gender, Integer pagesize, Integer offset);

	long countByEmailsAndFullnameAndAddress(String fullname, String address, String email);

	List<User> findByEmailsAndFullnameAndAddress(String fullname, String address, String email, Integer pagesize, Integer offset);

	long countByAddressAndGender(String address, List<Byte> gender);

	List<User> findByAddressAndGender(String address, List<Byte> gender, Integer pagesize, Integer offset);

	long countByFullnameAndGender(String fullname, List<Byte> gender);

	List<User> findByFullnameAndGender(String fullname, List<Byte> gender, Integer pagesize, Integer offset);

	long countByFullnameAndAddress(String fullname, String address);

	List<User> findByFullnameAndAddress(String fullname, String address, Integer pagesize, Integer offset);

	long countByEmailsAndGender(String email, List<Byte> gender);

	List<User> findByEmailsAndGender(String email, List<Byte> gender, Integer pagesize, Integer offset);

	long countByEmailsAndAddress(String address, String email);

	List<User> findByEmailsAndAddress(String address, String email, Integer pagesize, Integer offset);

	long countByEmailsAndFullname(String fullname, String email);

	List<User> findByEmailsAndFullname(String fullname, String email, Integer pagesize, Integer offset);

	long countByGender(List<Byte> gender);

	List<User> findByGender(List<Byte> gender, Integer pagesize, Integer offset);

	long countByAddress(String address);

	List<User> findByAddress(String address, Integer pagesize, Integer offset);

	long countByFullname(String fullname);

	List<User> findByFullname(String fullname, Integer pagesize, Integer offset);

	long countByEmails(String email);

	List<User> findByEmails(String email, Integer pagesize, Integer offset);

	User findUserByEmail(String email);
}
