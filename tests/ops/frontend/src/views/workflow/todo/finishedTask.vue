<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="form" style="height:40px">
        <el-form-item>
          <el-input v-model="form.processDefinitionKey" placeholder="流程定义key" style="width: 165px" size="small" class="filter-item" @keyup.enter.native="getByCondition"/>
        </el-form-item>
        <el-form-item>
          <el-tooltip content="重置" placement="top">
            <el-button type="info" icon="el-icon-close" size="small" circle @click="clearFormInline()"/>
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-button id="query" type="primary" size="small" class="filter-item" style="margin-bottom:3px" @click="getByCondition">查询</el-button>
        </el-form-item>
      </el-form>
      <vxe-grid
        ref="xGrid"
        :loading="loading"
        :pager-config="tablePage"
        :data="tableData"
        :columns="tableColumn"
        border
        size="mini"
        show-overflow
        resizable
        row-key
        highlight-hover-row
        @page-change="handlePageChange">
        <template v-slot:operate="{ row }">
          <!-- <el-button v-if="row.taskAssignee" size="mini" icon="el-icon-s-check" title="处理" circle @click="detailEvent(row)"/>
          <el-button v-else size="mini" icon="el-icon-check" title="签收" circle @click="signEvent(row)"/> -->
          <el-button size="mini" icon="el-icon-view" title="查看" circle @click="checkEvent(row)"/>
        </template>
      </vxe-grid>
    </div>
  </div>
</template>
<script>
import { done } from '@/api/approvalCenter'
export default {
  name: 'FinishedTask',
  data() {
    return {
      // v-if="row.assignee" v-else
      loading: false,
      tableData: [],
      form: {},
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 100,
        align: 'right',
        pageSizes: [100, 500, 1000],
        layouts: [
          'Sizes',
          'PrevJump',
          'PrevPage',
          'Number',
          'NextPage',
          'NextJump',
          'FullJump',
          'Total'
        ],
        perfect: true
      },
      tableColumn: [
        { field: 'processDefineName', title: '业务', width: 200, align: 'center' },
        { field: 'columnOne', title: '标题1', width: 200, align: 'center' },
        { field: 'columnTwo', title: '标题2', width: 200, align: 'center' },
        { field: 'taskName', title: '当前任务节点', midth: 200, align: 'center' },
        { field: 'taskCreatedDate', title: '任务创建时间', midth: 200, align: 'center' },
        // { field: 'processInstanceStatus', title: '流程状态', midth: 150, align: 'center' },
        { title: '操作', width: 150, slots: { default: 'operate' }, align: 'center', fixed: 'right' }
      ]
    }
  },
  created() {
    this.getByCondition()
    // console.log(this.$store.getters)
    // const dataDemo = [
    //   {
    //     title1: '代办1',
    //     title2: '代办1-1',
    //     task: '审批',
    //     taskNode: '1',
    //     processState: '进行中',
    //     taskCreatedTime: '2020-10-12'
    //   }
    // ]
    // this.tableData = dataDemo
    // this.loading = false
  },
  methods: {
    // 进入审核界面
    checkEvent(row) {
      var taskId = row.taskId
      var customerNo = row.columnTwo
      var paymentApplyNo = row.columnOne
      var businessKey = row.columnOne
      var toPath = 'toFinishedTask'
      this.$router.push({
        path: row.detailViewPath,
        query: { taskId, businessKey, customerNo, paymentApplyNo, toPath }
      })
    },
    getByCondition() {
      this.loading = true
      this.form.userId = this.$store.getters.info.userId
      this.form.pageNumber = this.tablePage.currentPage
      this.form.pageSize = this.tablePage.pageSize
      this.form.firstResult = (this.tablePage.currentPage - 1) * this.tablePage.pageSize
      this.form.maxResults = this.tablePage.currentPage * this.tablePage.pageSize
      const conditions = this.form
      done(conditions).then((data) => {
        this.tableData = data.content
        this.tablePage.total = data.totalItems
        this.loading = false
      }).catch((e) => {
        this.loading = false
      })
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getByCondition()
    },
    clearFormInline() {
      this.form = {}
    }
  }
}
</script>
