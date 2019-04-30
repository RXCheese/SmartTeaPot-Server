package com.springboot.smartteapot.controller;

import com.springboot.smartteapot.bean.dto.StatusCondition;
import com.springboot.smartteapot.common.String2date;
import com.springboot.smartteapot.hardware.entity.openapi.Status;
import com.springboot.smartteapot.hardware.openapi.GizwitsOpenApi;
import com.springboot.smartteapot.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/pot")
public class StatusController {


    @Autowired
    private StatusService statusService;

    @Autowired
    private GizwitsOpenApi gizwitsOpenApi;

    @GetMapping("/latest")
    @ResponseBody
    public Status getLatestStatus(){
        Status status;
        boolean online = gizwitsOpenApi.getDeviceOnlineStatus();
       // if(online){
            status = gizwitsOpenApi.getLatestStatus();

            String time = new String2date().String2date(status.getUpdatedAt());
            status.setUpdatedAt(time);

            if(online)
                status.setOnline("在线");
            else
                status.setOnline("离线");

            if(status.getHeatintSwitch().equals("1"))
                status.setHeatintSwitch("开");
            else if(status.getHeatintSwitch().equals("0"))
                status.setHeatintSwitch("关");
            else
                status.setHeatintSwitch("未知");

            if(!statusService.existsByTime(status.getUpdatedAt()))
            {
                statusService.saveStatus(status);
            }

       // }else {
            //status = statusService.getLastOne();

        //}

        status.setId(null);
        return status;
    }

    @GetMapping("/tempData")
    public Map<String,String[]> getTempData(){
        List<Status> statusList = statusService.getTempData(PageRequest.of(0,10, Sort.Direction.DESC,"id")).getContent();
        return datalist2map(statusList);
    }

    @PostMapping("/execute")
    public Map<String,Object> executeOp(@RequestBody Status status){

        Long temp;
        Map<String,Object> result = new HashMap<>();

        if(status.getTemperature() == null)
            temp = null;
        else
            temp = Long.parseLong(status.getTemperature());

        boolean res = gizwitsOpenApi.executeRequest(status.getHeatintSwitch(),status.getTaste(),status.getConstantTime(),temp);

        if(res)
            result.put("result","true");
        else
            result.put("result","false");

        return result;
    }

    @GetMapping("/isOnline")
    public Map<String,Object> isOnline(){
        Map<String,Object> result = new HashMap<>();
        if(gizwitsOpenApi.getDeviceOnlineStatus())
            result.put("online","true");
        else
            result.put("online","false");
        return result;
    }

    @GetMapping
    public Page<Status> getAllData(StatusCondition condition, Pageable pageable){
        return statusService.findAll(condition, pageable);
    }

    @GetMapping("/allTemp")
    public Map<String,String[]> getAllTempData(){
        List<Status> statusList = statusService.findAll();
        return datalist2map(statusList);
    }

    public Map<String,String[]> datalist2map(List<Status> statusList){
        Map<String,String[]> result = new HashMap<>();
        int size = statusList.size();
        int i = size-1;
        String[] data1 = new String[size];
        String[] data2 = new String[size];
        String[] data3 = new String[size];

        for (Status status:
                statusList) {
            data1[i] = status.getUpdatedAt();
            data2[i] = status.getTemperature();
            data3[i] = status.getTemp();
            i--;
        }
        result.put("time",data1);
        result.put("temperature", data2);
        result.put("temp", data3);

        return result;
    }
}
