package com.springboot.smartteapot.service;

import com.springboot.smartteapot.bean.dto.StatusCondition;
import com.springboot.smartteapot.hardware.entity.openapi.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface StatusService {

    Status saveStatus(Status status);

    Status getStatus(Long id);

    void delete(Long id);

    Page<Status> query(Pageable pageable);

    boolean existsByTime(String time);

    Status getLastOne();

    Page<Status> getTempData(Pageable pageable);

    List<Status> findAll();

    Page<Status> findAll(StatusCondition condition, Pageable pageable);

}
