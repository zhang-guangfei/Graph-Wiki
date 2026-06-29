<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button type="primary" icon="el-icon-circle-plus-outline" @click="insertEvent()">新增</el-button>
      <vxe-grid
        ref="xGrid"
        :loading="loading"
        :pager-config="tablePage"
        :data="tableData"
        :columns="tableColumn"
        border
        resizable
        row-key
        height="680"
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
      <terminal-detail ref="detail"/>
    </div>
  </div>
</template>
<script>
import { query, deleteTerminal } from '@/api/terminal'
import terminal from './terminal'
import terminalDetail from './terminalDetail'
export default {
  components: {
    'terminal-detail': terminalDetail,
    'edit-form': terminal
  },
  data() {
    return {
      loading: false,
      tableData: [],
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
      tableColumn: [
        { field: 'clientId', title: '客户端', width: 300 },
        { field: 'authorizedGrantTypes', title: '授权模式', minWidth: 430 },
        { field: 'scope', title: '域', width: 180, align: 'center' },
        { field: 'autoApprove', title: '自动放行', width: 120, align: 'center' },
        { field: 'accessTokenValidity', title: '令牌时效', width: 120, align: 'center' },
        { field: 'refreshTokenValidity', title: '刷新时效', width: 120, align: 'center' },
        { field: 'additionalInformation', title: '备注', width: 250, align: 'center' },
        { title: '操作', width: 150, slots: { default: 'operate' }, align: 'center', fixed: 'right' }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      var params = {
        pageNumber: this.tablePage.currentPage,
        pageSize: this.tablePage.pageSize
      }
      query(params).then((data) => {
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
      this.getList()
    },
    detailRowEvent(row) {
      this.$refs.detail.dialogTerminalDetailVisible = true
      this.$refs.detail.formData = row
    },
    editEvent(row) {
      this.$refs.edit.windowVisible = true
      this.$refs.edit.getRow(row)
    },
    removeEvent(row) {
      this.$confirm('您确定要删除选中的数据吗?').then(type => {
        deleteTerminal(row.clientId).then((data) => {
          this.$notify({
            title: '成功',
            type: 'success',
            message: '删除成功！'
          })
          this.getList()
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
      this.getList()
    }
  }
}
</script>
