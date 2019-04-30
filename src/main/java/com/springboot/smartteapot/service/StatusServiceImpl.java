package com.springboot.smartteapot.service;

import com.springboot.smartteapot.bean.dto.StatusCondition;
import com.springboot.smartteapot.hardware.entity.openapi.Status;
import com.springboot.smartteapot.repository.StatusRepository;
import com.springboot.smartteapot.repository.spec.StatusSpec;
import com.springboot.smartteapot.repository.support.QueryResultConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepository;


    @Override
    public Status saveStatus(Status status) {
        statusRepository.save(status);
        return status;
    }

    @Override
    public Status getStatus(Long id) {
        return statusRepository.findById(id).get();
    }

    @Override
    public void delete(Long id) {
        statusRepository.deleteById(id);
    }

    @Override
    public Page<Status> query(Pageable pageable) {
        Page<Status> statuses = statusRepository.findAll(pageable);
        return QueryResultConverter.convert(statuses,Status.class,pageable);
    }

    @Override
    public boolean existsByTime(String time) {
        return statusRepository.existsByUpdatedAt(time);
    }

    @Override
    public Status getLastOne() {
        return statusRepository.findFirstByOrderByIdDesc();
    }

    @Override
    public Page<Status> getTempData(Pageable pageable) {
        return statusRepository.findAll(pageable);
    }

    @Override
    public Page<Status> findAll(StatusCondition condition,Pageable pageable) {
        Page<Status> statuses;
        statuses = statusRepository.findAll(new StatusSpec(condition),pageable);

        return QueryResultConverter.convert(statuses, Status.class,statuses.getPageable());
    }

    @Override
    public List<Status> findAll(){
        return statusRepository.findAll();
    }

}
