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
            <el-button type="info" icon="el-icon-close" size="mini" circle @click="clearFormInline()"/>
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-button id="query" type="primary" size="small" class="filter-item" style="margin-bottom:3px" @click="getByCondition">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button id="add" type="primary" size="small" class="filter-item" style="margin-bottom:3px" @click="insertEvent()">新增</el-button>
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
          <el-button size="mini" icon="el-icon-edit" title="编辑" circle @click="editEvent(row)"/>
          <el-button size="mini" icon="el-icon-delete" title="删除" circle @click="removeEvent(row)"/>
          <el-button size="mini" icon="el-icon-view" title="详情" circle @click="detailRowEvent(row)"/>
        </template>
      </vxe-grid>
      <!-- @closeForm="closeForm" 这个方法来控制提交表单后，刷新表格和关闭表单-->
      <edit-form ref="edit" @close-form="closeForm"/>
      <leave-detail ref="detail"/>
    </div>
  </div>
</template>
<script>
import { query, deleteLeave } from '@/api/leave'
import { findApprovalDetailByBusinessKey } from '@/api/approvalCenter'
import leave from './leave'
import leaveDetail from './leaveDetail'
export default {
  name: 'LeaveIndex',
  components: {
    'leave-detail': leaveDetail,
    'edit-form': leave
  },
  data() {
    return {
      loading: false,
      tableData: [],
      form: {},
      // 分页参数
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
        { field: 'leaveusername', title: '姓名', width: 200, align: 'center' },
        { field: 'leaveday', title: '请假天数', width: 180, align: 'center' },
        { field: 'reason', title: '请假原因', minWidth: 300, align: 'center' },
        { title: '操作', width: 150, slots: { default: 'operate' }, align: 'center', fixed: 'right' }
      ]
    }
  },
  created() {
    this.getByCondition()
    // console.log(this.$store.getters)
  },
  methods: {
    getByCondition() {
      // console.log(this.$store.getters)
      this.loading = true
      this.form.pageNumber = this.tablePage.currentPage
      this.form.pageSize = this.tablePage.pageSize
      const conditions = this.form
      query(conditions).then((data) => {
        this.tableData = data.list
        this.tablePage.total = data.total
        this.loading = false
      }).catch((e) => {
        this.loading = false
      })
    },
    insertEvent() {
      this.$refs.edit.windowVisible = true
      this.$refs.edit.toAdd()
    },
    closeForm() {
      this.getByCondition()
    },
    clearFormInline() {
      this.form = {}
    },
    detailRowEvent(row) {
      var leaveId = row.leaveId
      this.$refs.detail.dialogTerminalDetailVisible = true
      this.$refs.detail.formData = row
      this.getDetail(leaveId)
    },
    getDetail(leaveId) {
      findApprovalDetailByBusinessKey(leaveId).then((data) => {
        this.$nextTick(() => {
          // this.$refs.detail.formData = row
          this.$refs.detail.approvalDetailData = data.content
        })
      })
    },
    editEvent(row) {
      this.$refs.edit.windowVisible = true
      this.$refs.edit.getRow(row)
    },
    removeEvent(row) {
      this.$confirm('您确定要删除选中的数据吗?').then(type => {
        deleteLeave(row.leaveId).then((data) => {
          this.$notify({
            title: '成功',
            type: 'success',
            message: '删除成功！'
          })
          this.getByCondition()
        }).catch(() => {
          this.$notify.error({
            title: '错误',
            message: '删除失败！'
          })
        })
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
<style scoped>
.search-title-content {
  padding: 0 20px 0px 20px;
  text-align: left;
}
</style>
