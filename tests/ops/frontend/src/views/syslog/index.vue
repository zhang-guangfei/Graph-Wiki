<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form ref="form" :model="form" :inline="true" class="demo-form-inline">
        <el-form-item label="操作名称：">
          <el-input v-model="form.title" placeholder="请输入操作名称" clearable style="width: 180px" size="medium" />
        </el-form-item>
        <el-form-item label="操作人：">
          <el-input v-model="form.createId" placeholder="请输入操作人" clearable style="width: 130px" size="medium"/>
        </el-form-item>
        <el-form-item label="操作时间：">
          <el-date-picker
            :picker-options="pickerOptions2"
            v-model="startTimeAndEndTime"
            type="daterange"
            align="center"
            unlink-panels
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            size="medium"/>
        </el-form-item>
        <el-button type="primary" style="margin-left: 10px;" @click="handleFilter">查询</el-button>
        <el-button :disabled="deleteBatchDisabled" type="warning" style="margin-left: 10px;" @click="deleteConfirm">删除</el-button>
        <el-button type="danger" style="margin-left: 10px;" @click="deleteCondition">清空</el-button>
      </el-form>
    </div>

    <el-table
      v-loading="listLoading"
      :key="tableKey"
      :data="list"
      border
      fit
      row-style="height:37px"
      highlight-current-row
      @row-click="rowClick"
      @selection-change="handleSelectionChange">
      <el-table-column fixed="left" type="selection" width="50" align="center"/>
      <el-table-column label="序号" prop="id" align="center" width="80px">
        <template slot-scope="scope">
          <span>{{ scope.$index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="名称" min-width="60px">
        <template slot-scope="scope">
          <span>{{ scope.row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="IP地址" min-width="60px">
        <template slot-scope="scope">
          <span>{{ scope.row.remoteAddr }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作人" min-width="50px">
        <template slot-scope="scope">
          <span>{{ scope.row.createId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作时间" min-width="70px">
        <template slot-scope="scope">
          <span>{{ scope.row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" min-width="50px">
        <template slot-scope="scope">
          <span>{{ scope.row.status }}</span>
        </template>
      </el-table-column>
      <el-table-column label="参数" min-width="200px">
        <template slot-scope="scope">
          <span>{{ scope.row.params }}</span>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.pageNumber" :limit.sync="listQuery.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { fetchList, deleteAll, deleteBatchById } from '@/api/syslog'
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination' // Secondary package based on el-pagination
import { shortcuts } from '@/utils/dataFormat'

export default {
  name: 'ComplexTable',
  components: { Pagination },
  isBind: false,
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        pageNumber: 1,
        pageSize: 10
      },
      startTimeAndEndTime: '',
      multipleSelection: [],
      form: {
        title: '',
        createId: '',
        startDate: '',
        endDate: ''
      },
      deleteBatchDisabled: true,
      pickerOptions2: shortcuts
    }
  },
  created() {
    this.getList()
  },
  methods: {
    rowClick(e) {
      console.log(e)
    },
    getList() {
      this.listLoading = true
      fetchList(this.form, this.listQuery).then(data => {
        this.list = data.list
        this.total = data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.startTimeAndEndTime === null) {
        this.form.startDate = ''
        this.form.endDate = ''
      } else {
        this.form.startDate = this.startTimeAndEndTime[0]
        this.form.endDate = this.startTimeAndEndTime[1]
      }
    },
    deleteConfirm() {
      this.$confirm('此操作要删除所选日志, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        deleteBatchById(this.multipleSelection).then(() => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
          this.listLoading = false
        }).catch(() => {
          this.listLoading = false
          this.$notify.error({
            title: '错误',
            message: '删除失败'
          })
        })
      })
    },
    deleteCondition() {
      this.$confirm('此操作要清空所筛选日志, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        deleteAll(this.form).then(() => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
          this.listLoading = false
        }).catch(() => {
          this.listLoading = false
          this.$notify.error({
            title: '错误',
            message: '删除失败'
          })
        })
      })
    },
    handleSelectionChange(val) {
      var i = 0
      for (var syslog of val) {
        console.log(syslog)
        const id = syslog.id
        this.multipleSelection[i] = id
        i++
      }
      if (val.length > 0) {
        this.deleteBatchDisabled = false
      } else {
        this.deleteBatchDisabled = true
      }
    },
    handleFilter() {
      this.listQuery.pageNumber = 1
      this.setDateCondition()
      this.getList()
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
