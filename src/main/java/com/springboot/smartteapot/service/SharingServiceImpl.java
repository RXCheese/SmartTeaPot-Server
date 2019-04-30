package com.springboot.smartteapot.service;

import com.springboot.smartteapot.bean.dto.SharingCondition;
import com.springboot.smartteapot.hardware.entity.openapi.SharingConfig;
import com.springboot.smartteapot.repository.SharingConfigRepository;
import com.springboot.smartteapot.repository.spec.SharingSpec;
import com.springboot.smartteapot.repository.support.QueryResultConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class SharingServiceImpl implements SharingService {

    @Autowired
    private SharingConfigRepository sharingConfigRepository;

    @Override
    public SharingConfig saveSharing(SharingConfig sharing) {
        return sharingConfigRepository.save(sharing);
    }

    @Override
    public SharingConfig getSharing(Long id) {
        return sharingConfigRepository.findById(id).get();
    }

    @Override
    public SharingConfig updateSharing(SharingConfig sharingConfig) {
        saveSharing(sharingConfig);
        return sharingConfig;
    }

    @Override
    public SharingConfig findOne(Long id) {
        return sharingConfigRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        sharingConfigRepository.deleteById(id);
    }

    @Override
    public Page<SharingConfig> query(SharingCondition condition, Pageable pageable) {
        Page<SharingConfig> sharingConfigPage = sharingConfigRepository.findAll(new SharingSpec(condition),pageable);

        return QueryResultConverter.convert(sharingConfigPage,SharingConfig.class,pageable);
    }

    @Override
    public boolean existsByConfigName(String name) {
        if(sharingConfigRepository.existsByConfigName(name))
            return true;
        else
            return false;
    }

    @Override
    public Long countLeaf(String leaf) {
        return sharingConfigRepository.countByLeaf(leaf);
    }

    @Override
    public Long countTemp(String temp1,String temp2) {
        return sharingConfigRepository.countByTemperature(temp1,temp2);
    }

    @Override
    public Long countTaste(String taste) {
        return sharingConfigRepository.countByTaste(taste);
    }

    @Override
    public Long countTime(String time) {
        return sharingConfigRepository.countByConstantTime(time);
    }


}
