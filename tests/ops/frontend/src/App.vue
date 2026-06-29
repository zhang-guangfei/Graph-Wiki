<template>
  <div id="app">
    <router-view v-if="isReload"/>
    <el-backtop :visibility-height="100" :bottom="100" target="#app">
      <div style="top-circle">
        <i class="el-icon-caret-top"/>
        <!-- UP -->
      </div>
    </el-backtop>
  </div>
</template>

<script>
export default {
  name: 'App',
  provide() {
    return {
      reload: this.reload
    }
  },
  data() {
    return {
      isReload: true
    }
  },
  methods: {
    reload() {
      this.isReload = false
      this.$nextTick(() => {
        this.isReload = true
      })
    }
  }
}
</script>
<style>
#app {
 /* overflow-y: scroll; */
  overflow-y: visible;
  height: 100%;
}
.top-circle {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  text-align: center;
  box-shadow: 0 0 6px rgb(0 0 0 / 12%);
  line-height: 40px;
  color: #1989fa;
}
</style>
