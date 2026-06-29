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
  name: 'PredOrderAccount',
  components: {
    queryPage: resolve => {
      require(['./query.vue'], resolve)
    },
    detailQueryPage: resolve => {
      require(['./detailQuery.vue'], resolve)
    },
    applyDetailQueryPage: resolve => {
      require(['./applyDetailQuery.vue'], resolve)
    },
    delayPage: resolve => {
      require(['./delayDayConfig.vue'], resolve)
    }
  },
  data() {
    return {
      modelNo: '',
      activeTab: 'queryPage',
      tabList: [
        {
          name: 'queryPage',
          label: '汇总查询'
        },
        {
          name: 'detailQueryPage',
          label: '明细查询'
        },
        {
          name: 'applyDetailQueryPage',
          label: '申请记录查询'
        },
        {
          name: 'delayPage',
          label: '延期设定'
        }
      ]
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal && newVal.name === 'predOrderAccount' && this.$route.query.active != null) {
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
