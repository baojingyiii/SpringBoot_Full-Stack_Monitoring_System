package com.baojingyi.oshi.controller;


import com.baojingyi.oshi.common.R;
import com.baojingyi.oshi.service.CpuLoadMetricsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RequestMapping ("/metrics")
@RestController
public class CpuLoadRestController {

    @Autowired
    CpuLoadMetricsService cpuLoadMetricsService;



    @GetMapping ("/cpuload")
    public R getCpuLoad(){
        double[] cpuLoad = cpuLoadMetricsService.getCpuLoad();
        return R.ok(cpuLoad);

    }





}
