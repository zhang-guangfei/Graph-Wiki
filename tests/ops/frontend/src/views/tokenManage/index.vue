<template>
  <div>
    <vxe-grid
      ref="xGrid"
      :loading="loading"
      :pager-config="tablePage"
      :columns="tableColumn"
      :data="tableData"
      :toolbar="tableToolbar"
      highlight-current-row
      border
      show-overflow
      resizable
      keep-source
      size="medium"
      @page-change="handlePageChange">
      <template v-slot:toolbar_buttons>
        <el-input v-model="form.username" placeholder="员工号" size="medium" style="width: 200px;" clearable @keyup.enter.native="searchEvent()"/>
        <el-button size="medium" icon="el-icon-search" title="查询" @click="searchEvent()"/>
      </template>
      <template v-slot:operate="{ row }">
        <el-row>
          <el-button size="mini" icon="el-icon-delete" title="删除" circle @click="removeRowEvent(row)"/>
        </el-row>
      </template>
    </vxe-grid>
  </div>
</template>
<script>
import { fetchList, deleteToken } from '@/api/tokenManage'
export default {
  data() {
    return {
      id: '',
      loading: false,
      form: {
        username: '',
        pageNumber: 1,
        pageSize: 50
      },
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 50,
        align: 'right',
        pageSizes: [50, 200, 500, 1000, 2000, 5000],
        layouts: ['PrevJump', 'PrevPage', 'JumpNumber', 'NextPage', 'NextJump', 'Sizes', 'FullJump', 'Total'],
        perfect: true
      },
      tableColumn: [
        { field: 'username', title: '用户名', width: 200, align: 'center' },
        { field: 'access_token', title: '令牌', minWidth: 250, align: 'center' },
        { field: 'token_type', title: '类型', minWidth: 250, align: 'center' },
        { field: 'expires_in', title: '过期时间', minWidth: 250, align: 'center' },
        { title: '操作', minWwidth: 250, slots: { default: 'operate' }, align: 'center' }
      ],
      tableData: [],
      tableToolbar: {
        perfect: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      }
    }
  },
  created() {
    this.findList()
  },
  methods: {
    findList() {
      this.loading = true
      fetchList(this.form).then(data => {
        this.tableData = data.list
        this.tablePage.total = data.total
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    handlePageChange({ currentPage, pageSize }) {
      this.form.pageSize = pageSize
      this.form.pageNumber = currentPage
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.findList()
    },
    searchEvent() {
      this.form.pageNumber = 1
      this.tablePage.currentPage = 1
      this.findList()
    },
    removeRowEvent(row) {
      this.$confirm('此操作将永久删除该令牌, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        deleteToken(row.access_token).then((response) => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
          this.findList()
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '删除失败'
          })
        })
      }).catch(() => {
      })
    },
    detailRowEvent(row) {
      alert('sssssss')
    }
  }
}
</script>
