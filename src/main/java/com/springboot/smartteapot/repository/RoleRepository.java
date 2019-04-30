package com.springboot.smartteapot.repository;

import com.springboot.smartteapot.bean.Role;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends BasicRepository<Role> {

    Role findByName(String roleName);

    boolean existsByName(String roleName);
}
