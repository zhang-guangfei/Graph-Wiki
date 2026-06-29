<template>
  <div class="app-container">
    <el-card>
      <el-form ref="form" :inline="true" :model="form" size="small" label-width="100px">
        <el-form-item label="用户编码：">
          <el-input v-model="form.id" clearable/>
        </el-form-item>
        <el-form-item label="用户名称：">
          <el-input v-model="form.name" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" @click="handleFilter">查询</el-button>
        </el-form-item>
      </el-form>
      <hr size="5" color="#0066cc">
    </el-card>
    <div style="margin-top:10px;">
      <div style="width:50%;float:left">
        <el-table
          v-loading="listLoading"
          :data="list"
          :row-style="rowStyle"
          element-loading-text="Loading"
          size="small"
          border
          fit
          highlight-current-row
          @row-click="rowClick">
          <el-table-column label="用户编码" min-width="200" align="center">
            <template slot-scope="scope">
              {{ scope.row.id }}
            </template>
          </el-table-column>
          <el-table-column label="真实姓名" min-width="200" align="center">
            <template slot-scope="scope">
              {{ scope.row.name }}
            </template>
          </el-table-column>
          <!-- <el-table-column label="岗位" min-width="250" align="center">
            <template slot-scope="scope">
              {{ scope.row.positionname }}
            </template>
          </el-table-column>
          <el-table-column label="部门名称" min-width="200" align="center">
            <template slot-scope="scope">
              {{ scope.row.unitname }}
            </template>
          </el-table-column> -->
          <el-table-column fixed="right" class-name="status-col" label="操作" min-width="150" align="center">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" style="margin-left: 10px;" icon="el-icon-refresh" @click="bindRole(scope.row)">绑定角色</el-button>
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="fetchData" />
      </div>
      <div style="width:20%;padding-top:0px;float:left;margin-left:30px">
        <el-table
          ref="roleTable"
          :data="rolelist"
          :row-style="rowStyle"
          element-loading-text="Loading"
          size="small"
          border
          fit
          @selection-change="handleSelectionChange">
          <el-table-column
            type="selection"
            width="40"
            align="center"/>
          <el-table-column label="角色名称" min-width="210" align="center">
            <template slot-scope="scope">
              {{ scope.row.name }}
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { getInternalList, findBindRoleById, bindRole, findAllRole } from '@/api/user'
export default {
  name: 'InnerUser',
  components: { Pagination },
  data() {
    return {
      list: null,
      rolelist: null,
      listLoading: true,
      total: 0,
      isBind: false,
      form: {
        id: '',
        name: ''
      },
      page: {
        pageNumber: 0,
        pageSize: 10
      },
      multipleSelection: []
    }
  },
  created() {
    this.fetchData()
    this.fetchRoleData()
  },
  methods: {
    rowStyle({ row, rowIndex }) {
      return 'height:38px'
    },
    fetchData() {
      this.listLoading = true
      getInternalList(this.form, this.page).then(result => {
        var data = result.content
        this.list = data.list
        this.total = data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    toggleSelection(rows) {
      this.$refs.roleTable.clearSelection()
      if (rows.length) {
        rows.forEach(row => {
          this.$nextTick(function() {
            this.$refs.roleTable.toggleRowSelection(this.rolelist.find(d => d.id === row.id), true)
          })
        })
      }
    },
    fetchRoleData() {
      findAllRole().then(data => {
        this.rolelist = data
      }).catch(error => {
        console.log(error)
      })
    },
    handleSelectionChange(val) {
      console.log('this select val ->', val)
      this.multipleSelection = val
    },
    rowClick(row, event, column) {
      if (this.isBind) {
        return
      }
      findBindRoleById(row.id).then(data => {
        this.toggleSelection(data)
      }).catch(error => {
        console.log(error)
      })
    },
    handleFilter() {
      this.pageNumber = 1
      this.fetchData()
    },
    bindRole(row) {
      this.isBind = true
      this.$confirm('确认绑定, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log('this role IdS ->>', this.multipleSelection)
        const roleIds = []
        this.multipleSelection.forEach(item => {
          roleIds.push(item.id)
        })
        const data = { id: row.id, psnname: row.name, roleIds: roleIds.join(',') }
        bindRole(data).then((data) => {
          this.smcSuccessMsg('绑定成功！')
          this.listLoading = false
          this.isBind = false
        }).catch(() => {
          this.listLoading = false
          this.isBind = false
          this.smcErrorMsg('绑定失败')
        })
      }).catch(() => {
        this.isBind = false
        /* this.$message({
          type: 'info',
          message: '已取消绑定'
        }) */
      })
    }
  }
}
</script>
<style scoped>
.line{
  text-align: center;
  margin-left: 5%;
}
</style>

