package com.springboot.smartteapot.controller;

import com.springboot.smartteapot.bean.dto.SharingCondition;
import com.springboot.smartteapot.hardware.entity.openapi.SharingConfig;
import com.springboot.smartteapot.service.SharingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/sharing")
public class SharingController {

    @Autowired
    private SharingService sharingService;

    @GetMapping
    public Page<SharingConfig> query(SharingCondition sharingCondition, Pageable pageable){
        return sharingService.query(sharingCondition,pageable);
    }

    @GetMapping("/{id}")
    public SharingConfig findById(@PathVariable Long id){
        return sharingService.findOne(id);
    }

    @PostMapping
    public SharingConfig create(@RequestBody SharingConfig sharingConfig){
        return sharingService.saveSharing(sharingConfig);
    }

    @PutMapping("/{id}")
    public SharingConfig update(@RequestBody SharingConfig sharingConfig){
        return sharingService.updateSharing(sharingConfig);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        sharingService.delete(id);
    }

    @GetMapping("/existConfig")
    public Map<String,Object> existConfig (@RequestParam(value = "configName") String name){
        Map<String,Object> map = new HashMap<>();
        if(sharingService.existsByConfigName(name))
            map.put("result","true");
        else
            map.put("result","false");
        return map;
    }

    @GetMapping("/countAllSharing")
    public Map<String,Long> getAllSharing(){
        Map<String,Long> result = new HashMap<>();
        result.put("leaf1",sharingService.countLeaf("碧螺春"));
        result.put("leaf2",sharingService.countLeaf("信阳毛尖"));
        result.put("leaf3",sharingService.countLeaf("西湖龙井"));
        result.put("leaf4",sharingService.countLeaf("君山银针"));
        result.put("leaf5",sharingService.countLeaf("黄山毛峰"));
        result.put("leaf6",sharingService.countLeaf("武夷岩茶"));
        result.put("leaf7",sharingService.countLeaf("祁门红茶"));
        result.put("leaf8",sharingService.countLeaf("都匀毛尖"));
        result.put("leaf9",sharingService.countLeaf("铁观音"));
        result.put("leaf10",sharingService.countLeaf("六安瓜片"));
        result.put("leaf11",sharingService.countLeaf("其他"));

        result.put("taste1",sharingService.countTaste("偏淡"));
        result.put("taste2",sharingService.countTaste("适中"));
        result.put("taste3",sharingService.countTaste("偏浓"));

        Long t100 = sharingService.countTemp("100","100");
        Long t0 = sharingService.countTemp("0","0");

        result.put("temp",sharingService.countTemp("1","69")-t100+t0);
        result.put("temp70",sharingService.countTemp("70","79"));
        result.put("temp80",sharingService.countTemp("80","89"));
        result.put("temp90",sharingService.countTemp("90","99")+t100);

        result.put("time1",sharingService.countTime("2分钟"));
        result.put("time2",sharingService.countTime("3分钟"));
        result.put("time3",sharingService.countTime("4分钟"));
        result.put("time4",sharingService.countTime("5分钟"));

        return result;
    }

}
