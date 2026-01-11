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

onMounted(() => {
  // drawCpuLoad();
  initChart();

  getCpuData();

})

const cpuAllData = ref([])  //16核cpu的所有数据，每一核是一个数组，数组中保存的是每秒的数据

const selectedTimeRange = ref(30); // 默认30秒
function time(newTime) {
  selectedTimeRange.value = newTime;
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
    if (!cpuAllData.value[i]) {
      cpuAllData.value[i] = [];
    }    //（相当于初始化）
    const selectedTime = selectedTimeRange.value;
    // 先添加新数据
    cpuAllData.value[i].push(data[i]);
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


const drawCpuLoad = (cpuIndex, cpuData) => {
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


