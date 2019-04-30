package com.springboot.smartteapot.service;

import com.springboot.smartteapot.bean.dto.SharingCondition;
import com.springboot.smartteapot.hardware.entity.openapi.SharingConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface SharingService {

    SharingConfig saveSharing(SharingConfig sharing);

    SharingConfig getSharing(Long id);

    SharingConfig updateSharing(SharingConfig sharingConfig);

    SharingConfig findOne(Long id);

    void delete(Long id);

    Page<SharingConfig> query(SharingCondition condition, Pageable pageable);

    boolean existsByConfigName(String name);

    Long countLeaf(String leaf);
    Long countTemp(String temp1,String temp2);
    Long countTaste(String taste);
    Long countTime(String time);
}
