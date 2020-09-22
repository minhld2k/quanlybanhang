package com.DuAnJV.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.DuAnJV.models.Chucnang;
import com.DuAnJV.models.Role;
import com.DuAnJV.repositories.RoleRepository;

@Service
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	RoleRepository roleRepository;
	
	@Override
	public void add(Role role) {
		this.roleRepository.add(role.getId(), role.getRolename(), role.getRolekey(), role.getCreatday(),
				role.getCreatby(), role.getUpdateday(), role.getUpdateby(), role.getIsdelete());
		for (Chucnang cn : role.getChucnang()) {
			this.roleRepository.addCNS(role.getId(), cn.getId());
		}
	}

	@Override
	public int update(Role role) {
		this.roleRepository.update(role.getRolename(), role.getRolekey(), role.getUpdateday(),
				role.getUpdateby(), role.getIsdelete(), role.getId());
		this.roleRepository.deleteCNS(role.getId());
		for (Chucnang cn : role.getChucnang()) {
			this.roleRepository.addCNS(role.getId(), cn.getId());
		}
		return 1;
		
	}

	@Override
	public Role findByRoleKeyAndIsDelete(String roleKey, Byte isDelete) {
		return this.roleRepository.findByRolekeyAndIsdelete(roleKey, isDelete);
	}

	@Override
	public List<Role> findByIsDelete(Integer pagesize,Integer offset) {
		return this.roleRepository.findAll(pagesize,offset);
	}

	@Override
	public List<Role> findByRoleNameAndIsDelete(String roleName,Integer pagesize,Integer offset) {
		return this.roleRepository.findByRolename(roleName,pagesize,offset);
	}

	@Override
	public Role findById(Long id) {
		return this.roleRepository.findByid(id);
	}

	@Override
	public long count() {
		return this.roleRepository.count();
	}

//	@Override
//	public boolean checkExits(Role role) {
//
//		Role u = this.findByRoleKeyAndIsDelete(role.getRolekey(), (byte) 0);
//		if (this.findById(role.getId()).isPresent() || u != null ) {
//			return false;
//		}
//		return true;
//	}

	@Override
	public long countByRolename(String roleName) {
		return this.roleRepository.countByRolename(roleName);
	}

	@Override
	public List<Role> findAllByRolekey(String rolekey, Integer pagesize, Integer offset) {
		return this.roleRepository.findAllByRolekey("%"+rolekey+"%", pagesize, offset);
	}

	@Override
	public long countByRolekey(String rolekey) {
		return this.roleRepository.countByRolekey("%"+rolekey+"%");
	}

	@Override
	public List<Role> findAllByRolenameAndRolekey(String rolename, String rolekey, Integer pagesize, Integer offset) {
		return this.roleRepository.findAllByRolenameAndRolekey(rolename, "%"+rolekey+"%", pagesize, offset);
	}

	@Override
	public long countByNameAndKey(String rolename, String rolekey) {
		return this.roleRepository.countByNameAndKey(rolename, "%"+rolekey+"%");
	}

	@Override
	public List<Long> findCNSById(Long id) {
		return this.roleRepository.findCNSById(id);
	}

}
