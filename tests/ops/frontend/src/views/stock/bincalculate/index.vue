<template>
  <div class="app-container">
    <el-tabs
      v-model="activeTab"
      type="card"
      @tab-click="handleTabClick">
      <el-tab-pane
        v-for="(tab, index) in tabList"
        :key="index"
        :label="tab.label"
        :name="tab.name" />
    </el-tabs>
    <keep-alive>
      <component :is="activeTab" />
    </keep-alive>
  </div>
</template>

<script>
export default {
  name: 'BinCalculate',
  components: {
    salesratePage: resolve => {
      require(['./salesrate.vue'], resolve)
    },
    binJobPage: resolve => {
      require(['./binjob.vue'], resolve)
    },
    configurePage: resolve => {
      require(['./configure.vue'], resolve)
    },
    detailPage: resolve => {
      require(['./detail.vue'], resolve)
    }
  },
  data() {
    return {
      modelNo: '',
      activeTab: 'salesratePage',
      tabList: [
        {
          name: 'salesratePage',
          label: '基础数据'
        },
        {
          name: 'binJobPage',
          label: '任务管理'
        },
        {
          name: 'configurePage',
          label: '基本配置'
        },
        {
          name: 'detailPage',
          label: '计算结果'
        }
      ]
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal && newVal.name === 'BinCalculate' && this.$route.query.active != null) {
          this.activeTab = this.$route.query.active.toString()
        }
      }
    }
  },
  methods: {
    handleTabClick(tab, event) {
      this.activeTab = tab.name
    }
  }
}
</script>
