package com.baojingyi.oshi.service;


import org.springframework.stereotype.Service;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.HardwareAbstractionLayer;

@Service
public class CpuLoadMetricsService {
    //OSHI提供的获取所有数据的入口
    SystemInfo si = new SystemInfo();
    public double[] getCpuLoad() {
        HardwareAbstractionLayer hardware = si.getHardware();

        CentralProcessor processor = hardware.getProcessor();
        double[] cpuLoad = processor.getProcessorCpuLoad(1000);
        return cpuLoad;

    }


}
