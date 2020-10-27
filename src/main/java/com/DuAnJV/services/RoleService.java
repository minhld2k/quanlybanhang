package com.DuAnJV.services;

import java.util.List;

import com.DuAnJV.models.Role;

public interface RoleService {

	long count();

	Role findById(Long id);

	List<Role> findByRoleNameAndIsDelete(String roleName,Integer pagesize,Integer offset);

	List<Role> findByIsDelete(Integer pagesize,Integer offset);

	Role findByRoleKeyAndIsDelete(String roleKey, Byte isDelete);

	void update(Role role);

	void add(Role role);

	long countByNameAndKey(String rolename, String rolekey);

	List<Role> findAllByRolenameAndRolekey(String rolename, String rolekey, Integer pagesize, Integer offset);

	long countByRolekey(String rolekey);

	List<Role> findAllByRolekey(String rolekey, Integer pagesize, Integer offset);

	long countByRolename(String roleName);

	List<Long> findCNSById(Long id);

	List<Role> findAllRoleByUserId(long id);

}
