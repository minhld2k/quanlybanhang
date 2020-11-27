package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.common.replaceDemo;
import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.User;
import com.DuAnJV.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired 
	RoleService roleService;
	
	@Autowired 
	ChucnangService chucNangService;

	@Override
	public void add(User user) {
		this.userRepository.save(user);
	}

	@Override
	public void update(User user) {
		this.userRepository.save(user);
	}

	@Override
	public List<User> findAll(Integer pagesize, Integer offset) {
		return this.userRepository.findAll(pagesize, offset);
	}

	@Override
	public long count() {
		return this.userRepository.count();
	}

	@Override
	public User findById(Long id) {
		return this.userRepository.findByid(id);
	}

	@Override
	public List<Long> findCNByRole(List<Long> roleid) {
		return this.userRepository.findCNByRole(roleid);
	}

	@Override
	public List<Long> findCNSById(Long id) {
		return this.userRepository.findCNSById(id);
	}

	@Override
	public List<Long> findRoleById(Long id) {
		return this.userRepository.findRoleById(id);
	}

	@Override
	public User findByEmail(String email) {
		return this.userRepository.findByEmail(email);
	}

	@Override
	public List<User> findByEmails(String email, Integer pagesize, Integer offset) {
		return this.userRepository.findByEmails("%"+email+"%", pagesize, offset);
	}

	@Override
	public long countByEmails(String email) {
		return this.userRepository.countByEmails("%"+email+"%");
	}

	@Override
	public List<User> findByFullname(String fullname, Integer pagesize, Integer offset) {
		return this.userRepository.findByFullname(fullname, pagesize, offset);
	}

	@Override
	public long countByFullname(String fullname) {
		return this.userRepository.countByFullname(fullname);
	}

	@Override
	public List<User> findByAddress(String address, Integer pagesize, Integer offset) {
		return this.userRepository.findByAddress(address, pagesize, offset);
	}

	@Override
	public long countByAddress(String address) {
		return this.userRepository.countByAddress(address);
	}

	@Override
	public List<User> findByGender(List<Byte> gender, Integer pagesize, Integer offset) {
		return this.userRepository.findByGender(gender, pagesize, offset);
	}

	@Override
	public long countByGender(List<Byte> gender) {
		return this.userRepository.countByGender(gender);
	}

	@Override
	public List<User> findByEmailsAndFullname(String fullname, String email, Integer pagesize, Integer offset) {
		return this.userRepository.findByEmailsAndFullname(fullname, "%"+email+"%", pagesize, offset);
	}

	@Override
	public long countByEmailsAndFullname(String fullname, String email) {
		return this.userRepository.countByEmailsAndFullname(fullname, "%"+email+"%");
	}

	@Override
	public List<User> findByEmailsAndAddress(String address, String email, Integer pagesize, Integer offset) {
		return this.userRepository.findByEmailsAndAddress(address, "%"+email+"%", pagesize, offset);
	}

	@Override
	public long countByEmailsAndAddress(String address, String email) {
		return this.userRepository.countByEmailsAndAddress(address, "%"+email+"%");
	}

	@Override
	public List<User> findByEmailsAndGender(String email, List<Byte> gender, Integer pagesize, Integer offset) {
		return this.userRepository.findByEmailsAndGender("%"+email+"%", gender, pagesize, offset);
	}

	@Override
	public long countByEmailsAndGender(String email, List<Byte> gender) {
		return this.userRepository.countByEmailsAndGender("%"+email+"%", gender);
	}

	@Override
	public List<User> findByFullnameAndAddress(String fullname, String address, Integer pagesize, Integer offset) {
		return this.userRepository.findByFullnameAndAddress(fullname, address, pagesize, offset);
	}

	@Override
	public long countByFullnameAndAddress(String fullname, String address) {
		return this.userRepository.countByFullnameAndAddress(fullname, address);
	}

	@Override
	public List<User> findByFullnameAndGender(String fullname, List<Byte> gender, Integer pagesize, Integer offset) {
		return this.userRepository.findByFullnameAndGender(fullname, gender, pagesize, offset);
	}

	@Override
	public long countByFullnameAndGender(String fullname, List<Byte> gender) {
		return this.userRepository.countByFullnameAndGender(fullname, gender);
	}

	@Override
	public List<User> findByAddressAndGender(String address, List<Byte> gender, Integer pagesize, Integer offset) {
		return this.userRepository.findByAddressAndGender(address, gender, pagesize, offset);
	}

	@Override
	public long countByAddressAndGender(String address, List<Byte> gender) {
		return this.userRepository.countByAddressAndGender(address, gender);
	}

	@Override
	public List<User> findByEmailsAndFullnameAndAddress(String fullname, String address, String email, Integer pagesize,
			Integer offset) {
		return this.userRepository.findByEmailsAndFullnameAndAddress(fullname, address, "%"+email+"%", pagesize, offset);
	}

	@Override
	public long countByEmailsAndFullnameAndAddress(String fullname, String address, String email) {
		return this.userRepository.countByEmailsAndFullnameAndAddress(fullname, address, "%"+email+"%");
	}

	@Override
	public List<User> findByEmailsAndFullnameAndGender(String fullname, String email, List<Byte> gender, Integer pagesize,
			Integer offset) {
		return this.userRepository.findByEmailsAndFullnameAndGender(fullname, "%"+email+"%", gender, pagesize, offset);
	}

	@Override
	public long countByEmailsAndFullnameAndGender(String fullname, String email, List<Byte> gender) {
		return this.userRepository.countByEmailsAndFullnameAndGender(fullname, "%"+email+"%", gender);
	}

	@Override
	public List<User> findByEmailsAndAddressAndGender(String address, String email, List<Byte> gender, Integer pagesize,
			Integer offset) {
		return this.userRepository.findByEmailsAndAddressAndGender(address, "%"+email+"%", gender, pagesize, offset);
	}

	@Override
	public long countByEmailsAndAddressAndGender(String address, String email, List<Byte> gender) {
		return this.userRepository.countByEmailsAndAddressAndGender(address, "%"+email+"%", gender);
	}

	@Override
	public List<User> findByFullnameAndAddressAndGender(String fullname, String address, List<Byte> gender, Integer pagesize,
			Integer offset) {
		return this.userRepository.findByFullnameAndAddressAndGender(fullname, address, gender, pagesize, offset);
	}

	@Override
	public long countByFullnameAndAddressAndGender(String fullname, String address, List<Byte> gender) {
		return this.userRepository.countByFullnameAndAddressAndGender(fullname, address, gender);
	}

	@Override
	public List<User> findByEmailsAndFullnameAndAddressAndGender(String fullname, String address, String email,
			List<Byte> gender, Integer pagesize, Integer offset) {
		return this.userRepository.findByEmailsAndFullnameAndAddressAndGender(fullname, address, "%"+email+"%", gender, pagesize,
				offset);
	}

	@Override
	public long countByEmailsAndFullnameAndAddressAndGender(String fullname, String address, String email,
			List<Byte> gender) {
		return this.userRepository.countByEmailsAndFullnameAndAddressAndGender(fullname, address, "%"+email+"%", gender);
	}

	@Override
	public User findUserByEmail(String email) {
		User user = this.findByEmail(email);
		List<Chucnang> lscn = this.chucNangService.findAllChucnangByEmail(email);
		user.setChucnangs(lscn);
		return user;
	}

	@Override
	public boolean checkLogin(String email, String password) {
		User user = this.findByEmail(email);
		if (null != user && replaceDemo.checkBcrypt(password, user.getPassword())) {
			return true;
		}	
		return false;
	}

}
