package com.springboot.smartteapot.repository;

import com.springboot.smartteapot.bean.Resource;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends BasicRepository<Resource> {

	Resource findByName(String name);

}
