<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="form" style="height:40px">
        <el-form-item>
          <el-input v-model="form.leaveuserId" placeholder="请输入ID" style="width: 165px" size="small" class="filter-item" @keyup.enter.native="getByCondition"/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.leaveusername" placeholder="请输入姓名" style="width: 165px" size="small" class="filter-item" @keyup.enter.native="getByCondition"/>
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.approverStatus" placeholder="审批状态" style="width: 165px" size="small" class="filter-item">
            <el-option v-for="item in approverStatusList" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
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
        show-header-overflow
        resizable
        highlight-hover-row
        @page-change="handlePageChange">
        <template v-slot:operate="{ row }">
          <el-button size="mini" icon="el-icon-check" title="审批" circle @click="approvalEvent(row)"/>
        </template>
      </vxe-grid>
    </div>
  </div>
</template>
<script>
import { queryApproval } from '@/api/leave'
export default {
  name: 'ApprovalIndex',
  data() {
    return {
      loading: false,
      tableData: [],
      form: {},
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        align: 'right',
        pageSizes: [10, 20, 50, 100, 200, 500],
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
      approverStatusList: [
        { value: '未签收', label: '未签收' },
        { value: '办理中', label: '办理中' },
        { value: '已完成', label: '已完成' },
        { value: '已退回', label: '已退回' }
      ],
      tableColumn: [
        { field: 'leaveuserId', title: 'ID', width: 200, align: 'center' },
        { field: 'leaveusername', title: '姓名', width: 100, align: 'center' },
        { field: 'leaveday', title: '请假天数', width: 100, align: 'center' },
        { field: 'reason', title: '请假原因', minWidth: 200, align: 'center' },
        { field: 'processState', title: '流程状态', width: 100, align: 'center' },
        { field: 'taskState', title: '任务状态', width: 100, align: 'center' },
        { field: 'approver', title: '审批人', width: 100, align: 'center' },
        { title: '操作', width: 120, slots: { default: 'operate' }, align: 'center', fixed: 'right' }
      ]
    }
  },
  created() {
    this.getByCondition()
  },
  methods: {
    approvalEvent(row) {
      this.$router.push({
        path: '/activiti/approval/leaveApproval'
      })
    },
    getByCondition() {
      this.loading = true
      this.form.pageNumber = this.tablePage.currentPage
      this.form.pageSize = this.tablePage.pageSize
      const conditions = this.form
      queryApproval(conditions).then((data) => {
        this.tableData = data.list
        this.tablePage.total = data.total
        this.loading = false
      }).catch((e) => {
        this.loading = false
      })
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getByCondition()
    }
  }
}
</script>
