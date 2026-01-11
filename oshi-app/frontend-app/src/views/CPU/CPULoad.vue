<script setup>
import * as echarts from 'echarts';
import {onMounted, ref, watch} from "vue";
import {getCpuLoadApi} from "@/api/cpuLoadApi.js";
import DropdownButton from "@/views/dropdown-button.vue";

const chartDom = [];
const initChart = () => {  //初始化图表
  for (let i = 1; i <= 16; i++) {
    var Dom = document.getElementById('chart-' + i);
    var myChart = echarts.init(Dom);
    chartDom.push(myChart);
  }
}

//等待页面加载出来,所以需要用到onMounted,有div dom元素才可以执行div
onMounted(() => {
  // drawCpuLoad();
  initChart();

  getCpuData();

})

const cpuAllData = ref([])  //16核cpu的所有数据，每一核是一个数组，数组中保存的是每秒的数据

//下拉列表选择监控显示的时间范围：结合dropdown-button.vue
const selectedTimeRange = ref(30); // 默认30秒，单位：秒
function time(newTime) {
  selectedTimeRange.value = newTime;
  // console.log("接收时间点",newTime);  //测试
}

watch(selectedTimeRange, (newRange) => {
  // 当时间范围改变时，调整数据数组长度
  console.log('监听到时间范围变化:', newRange);
  cpuAllData.value = [];  //清空数组

});


const getCpuData = async () => {
  //1.拿到服务器真正的响应
  let resp = await getCpuLoadApi();
  //http/index.js里面修改return response.data;
  // console.log("响应数据", resp.data)
  //2.返回16核cpu，每个核当前的负载值
  let data = resp.data;
  for (let i = 0; i < 16; i++) {
    //i:哪个cpu, cpuData:cpu的数据(也就是data=resp.data)
    // drawCpuLoad(i, cpuData);
    // drawCpuLoad(i,data[i]);  //但是data[i]只是其中的一个数值。实际上需要获取第i核cpu，每一秒的数值（也就是一个数组）。
    if (!cpuAllData.value[i]) {  // 如果这个CPU的数组不存在
      cpuAllData.value[i] = [];   // 就创建一个空数组
    }    //（相当于初始化）

    // //设置数组最多存放30个数据
    // if(cpuAllData.value[i].length > 30) {
    //   let arr = cpuAllData.value[i].slice(-30);  //slice：切片。获取从数组末尾开始的30个元素。
    //   arr.push(data[i]);  //把data[i]放到arr的末尾
    //   cpuAllData.value[i] = arr;  //赋值，arr这个数组给cpuAllData.value[i]
    // }else{
    //   cpuAllData.value[i].push(data[i]);  //把请求到的第i核cpu的使用率放进自己的数组中
    // }

    //根据选择的时间范围，更改存放的点数
    const selectedTime = selectedTimeRange.value;
    // 先添加新数据
    cpuAllData.value[i].push(data[i]);  //把请求到的第i核cpu的使用率放进自己的数组中
    // 再检查并保持长度
    if (cpuAllData.value[i].length > selectedTime) {
      cpuAllData.value[i].shift();  // 删除第一个元素
    }

    drawCpuLoad(i + 1, cpuAllData.value[i]);

  }
  // await getCpuData();
  // 使用 setTimeout 而不是直接递归调用(getCpuData();)，避免堆栈溢出
  setTimeout(getCpuData, 100)


}

// const drawCpuLoad = (cpuIndex,cpuData) => {  //箭头函数对应前面的：drawCpuLoad(i+1,cpuAllData.value[i]);
const drawCpuLoad = (cpuIndex, cpuData) => {
  // var chartDom = document.getElementById('chart-' + cpuIndex);
  // var myChart = echarts.init(chartDom);
  var myChart = chartDom[cpuIndex - 1];
  var option;

  option = {
    title: {
      text: 'CPU' + cpuIndex,
      textStyle: {fontSize: 16}
    },
    grid: {
      left: "0",
      right: "0",
      top: "0"
    },
    xAxis: {
      type: 'category',
      // data: ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
    },
    yAxis: {
      show: false,
      type: 'value',
      min: 0,
      max: 1,
    },
    series: [
      {
        data: cpuData,
        type: 'line',
        areaStyle: {},
        symbol: "rect",
        symbolSize: "3",
        smooth: true,
      }
    ]
  };

  option && myChart.setOption(option);

}


</script>

<template>
  <h1>CPU监控</h1>
  <a-space wrap>
    <div :id="`chart-${i}`" style="height: 150px;width: 150px; border: 1px solid black;" v-for="i in 16"></div>
  </a-space>


  <div class="dropdown-button-layout">
    <DropdownButton @selectedTimeRange="time"/>
  </div>


</template>

<style scoped>
.dropdown-button-layout {
  position: fixed;
  top: 20px;
  right: 20px;
}
</style>


