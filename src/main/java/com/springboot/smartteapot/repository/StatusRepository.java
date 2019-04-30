package com.springboot.smartteapot.repository;


import com.springboot.smartteapot.hardware.entity.openapi.Status;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends BasicRepository<Status> {

    boolean existsByUpdatedAt(String updateAt);

    Status findFirstByOrderByIdDesc();

    //Page<Status> findAll(Pageable pageable);
}
