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
      <component :is="activeTab" :params="params" @goApplyDetailPageEvent="goApplyDetailPage" />
    </keep-alive>
  </div>
</template>

<script>
import queryPage from './query'
import applyPage from './apply'
import detailQueryPage from './detailQuery'
export default {
  name: 'StockAssembly',
  components: {
    queryPage,
    applyPage,
    detailQueryPage
  },
  data() {
    return {
      activeTab: 'queryPage',
      tabList: [
        {
          name: 'queryPage',
          label: '查询'
        },
        {
          name: 'detailQueryPage',
          label: '详情查询'
        },
        {
          name: 'applyPage',
          label: '申请'
        }
      ],
      params: {
        applyId: ''
      }
    }
  },
  methods: {
    handleTabClick(tab, event) {
      this.activeTab = tab.name
    },
    goApplyDetailPage(params) {
      this.params.applyId = params.applyId
      this.activeTab = 'applyPage'
    }
  }
}
</script>
