<script setup>

import {useRouter} from "vue-router";

const router = useRouter();
const MenuTo = (url) =>{
  router.push(url)

}

</script>

<template>
  <div class="menu-demo">
    <a-menu mode="horizontal" :default-selected-keys="['1']">
      <a-menu-item key="0" :style="{ padding: 0, marginRight: '38px' }" disabled>
        <div
            :style="{
            width: '80px',
            height: '30px',
            borderRadius: '2px',
            background: 'var(--color-fill-3)',
            cursor: 'text',
          }"
        />
      </a-menu-item>
      <a-menu-item key="/" @click="MenuTo('/')">首页</a-menu-item>
      <a-menu-item key="/CPU" @click="MenuTo('/CPU')">CPU监控</a-menu-item>
      <a-menu-item key="3">Cloud Service</a-menu-item>
      <a-menu-item key="4">Cooperation</a-menu-item>
    </a-menu>
  </div>
</template>
<style scoped>
.menu-demo {
  box-sizing: border-box;
  width: 100%;
  //background-color: var(--color-neutral-2);
}
</style>
