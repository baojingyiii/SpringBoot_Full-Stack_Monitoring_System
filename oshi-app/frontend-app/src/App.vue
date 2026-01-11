<script setup>
import Menu from "@/views/Menu.vue";
// import DropdownButton from "@/views/dropdown-button.vue";
</script>

<template>
  <div class="layout-demo">
    <a-layout style="height: 100vh;width: 100vw">
      <a-layout-header>
        <Menu/>
      </a-layout-header>
      <a-layout-content>
        <RouterView/>
      </a-layout-content>
      <a-layout-footer>Footer</a-layout-footer>
    </a-layout>

  </div>

</template>
<style scoped>
html, body, #app {
  margin: 0;
  padding: 0;
}



.layout-demo :deep(.arco-layout-header),
.layout-demo :deep(.arco-layout-footer),
.layout-demo :deep(.arco-layout-sider-children),
.layout-demo :deep(.arco-layout-content) {
  display: flex;
  flex-direction: column;
  justify-content: center;
  color: var(--color-black);
  font-size: 16px;
  font-stretch: condensed;
  text-align: center;
}


.layout-demo :deep(.arco-layout-header),
.layout-demo :deep(.arco-layout-footer) {
  height: 64px;
  //background-color: var(--color-primary-light-4);
}

.layout-demo :deep(.arco-layout-sider) {
  width: 206px;
  background-color: var(--color-primary-light-3);
}

.layout-demo :deep(.arco-layout-content) {
  //background-color: rgb(var(--arcoblue-6));
}
</style>
