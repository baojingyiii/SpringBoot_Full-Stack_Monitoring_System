<script setup>
import {IconDown} from '@arco-design/web-vue/es/icon';


let emit = defineEmits(['selectedTimeRange'])

function timeOption(value) {
  emit("selectedTimeRange", Number(value));  //Number(value):把字符串转换成数字
  // console.log("时间", Number(value));  //测试
}

</script>

<template>
  <a-space size="large">

    <a-dropdown-button>
      监控范围选择
      <template #icon>
        <icon-down/>
      </template>
      <template #content>
        <a-doption @click="timeOption(10)">10s</a-doption>
        <a-doption @click="timeOption(30)">30s</a-doption>
        <a-doption @click="timeOption(60)">1min</a-doption>
      </template>
    </a-dropdown-button>
  </a-space>

</template>

<style scoped>
.arco-dropdown-open .arco-icon-down {
  transform: rotate(180deg);
}


</style>
