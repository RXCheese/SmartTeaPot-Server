package com.springboot.smartteapot.repository;

import com.springboot.smartteapot.bean.Admin;
import net.bytebuddy.implementation.bytecode.assign.TypeCasting;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends BasicRepository<Admin> {

	Admin findByUsername(String username);
	Admin findByPhone(String phone);
	Admin findByEmail(String email);

	boolean existsByUsername(String username);
	boolean existsByEmail(String email);
	boolean existsByPhone(String phone);

}
