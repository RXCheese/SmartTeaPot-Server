package com.springboot.smartteapot.repository;


import com.springboot.smartteapot.hardware.entity.openapi.SharingConfig;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SharingConfigRepository extends BasicRepository<SharingConfig> {

    boolean existsByConfigName(String name);
    SharingConfig findByConfigName(String name);

    Long countByConstantTime(String time);
    Long countByLeaf(String leaf);
    Long countByTaste(String taste);

    @Query(value = "SELECT count(temperature) FROM SharingConfig WHERE temperature>=:temp1 AND temperature<=:temp2")
    Long countByTemperature(@Param("temp1")String temp1, @Param("temp2")String temp2);
}
