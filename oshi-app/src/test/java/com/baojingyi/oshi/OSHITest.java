package com.baojingyi.oshi;


import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import tools.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.List;

public class OSHITest {


    //1.创建 si 对象
    SystemInfo si = new SystemInfo();

//    @Test
//    void memory(){
//        HardwareAbstractionLayer hardware = si.getHardware();
//
//        //获取内存信息
//
//        GlobalMemory memory = hardware.getMemory();
//        ObjectMapper mapper = new ObjectMapper();
//        String string =mapper.writerWithDefaultPrettyPrinter().writeValueAsString(memory);
//        System.out.println(string);
//
//
//    }

    @Test
    @SneakyThrows
    void testMemory(){
        HardwareAbstractionLayer hardware = si.getHardware();

        while (true){
            Thread.sleep(1000);
            //获取内存信息
            GlobalMemory memory = hardware.getMemory();
//            ObjectMapper mapper = new ObjectMapper();
//            String string =mapper.writerWithDefaultPrettyPrinter().writeValueAsString(memory);
//            System.out.println(string);
            //打印可用内存
            System.out.println("可用内存："+memory.getAvailable());
        }


    }


    @Test
    void testCPU(){
        HardwareAbstractionLayer hardware = si.getHardware();
        //1.CentralProcessUnit 拿到cpu
        CentralProcessor processor =hardware.getProcessor();

        //2.获取cpu最大频率
        long maxFreq = processor.getMaxFreq();
        System.out.println("最大频率 =" + maxFreq);

        //3.获取CPU当前频率；返回所有逻辑CPU当前频率
        long[] currentFreq = processor.getCurrentFreq();
        System.out.println("当前频率 = " + Arrays.toString(currentFreq));

        //4.当前频率/最大频率 = 当前cpu占有率


        //5.获取cpu 1s 的使用情况
        double[] cpuLoad =processor.getProcessorCpuLoad(1000);
        System.out.println("couLoad = " + Arrays.toString(cpuLoad));

    }






    @Test
    void test01(){


        //2.获取操作系统对象
        OperatingSystem os = si.getOperatingSystem();
        ObjectMapper mapper = new ObjectMapper();

        List<OSProcess> processes = os.getProcesses();
        System.out.println("进程总数：" + os.getProcessCount());
        for (OSProcess process : processes) {
            String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(process);
            System.out.println(json);
        }


    }


}
