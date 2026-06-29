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
  name: 'BinOrderCalc',
  components: {
    approvePage: resolve => {
      require(['./approve.vue'], resolve)
    },
    calculatePage: resolve => {
      require(['./calculate.vue'], resolve)
    },
    detailsplitPage: resolve => {
      require(['./detailsplit.vue'], resolve)
    }
  },
  data() {
    return {
      modelNo: '',
      activeTab: 'calculatePage',
      tabList: [
        {
          name: 'calculatePage',
          label: '计算'
        },
        {
          name: 'approvePage',
          label: '审批'
        },
        {
          name: 'detailsplitPage',
          label: '补货查询'
        }
      ]
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal && newVal.name === 'BinOrderCalc' && this.$route.query.active != null) {
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
