<template>
  <el-container>
    <el-main>
      <el-form ref="form" :model="form" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="部门代码:">
          <el-input v-model="form.deptNo" placeholder="部门代码" clearable style="width: 100px" />
        </el-form-item>
        <el-form-item label="部门名称:">
          <el-input v-model="form.deptName" placeholder="请输入部门名称" clearable style="width: 150px" />
        </el-form-item>
        <el-button type="success" size="mini" style="margin-left: 10px;" @click="addDept">新增</el-button>
        <el-button type="primary" size="mini" style="margin-left: 10px;" @click="findList">查询</el-button>
        <el-button :disabled="update" type="warning" size="mini" style="margin-left: 10px;" @click="updateInfo">修改</el-button>
        <el-button type="danger" size="mini" style="margin-left: 10px;" @click="deletCondition">删除</el-button>
      </el-form>

      <el-table
        v-loading="listLoading"
        ref="deptTable"
        :data="deptData"
        border
        fit
        class="deptTable"
        highlight-current-row
        @selection-change="handleSelectionChange">
        <el-table-column fixed="left" type="selection" width="50" align="center"/>
        <el-table-column v-if="false" prop="id">
          <template slot-scope="scope">
            <span>{{ scope.row.id }}</span>
          </template>
        </el-table-column>
        <el-table-column label="部门代码" prop="deptNo" align="center" width="100px">
          <template slot-scope="scope">
            <span>{{ scope.row.deptNo }}</span>
          </template>
        </el-table-column>
        <el-table-column label="部门名称" width="180px">
          <template slot-scope="scope">
            <span>{{ scope.row.deptName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="地址" width="250px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.address }}</span>
          </template>
        </el-table-column>
        <el-table-column label="电话" width="120px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.teleNo }}</span>
          </template>
        </el-table-column>
        <el-table-column label="通用邮箱地址" width="200px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.emailAddr }}</span>
          </template>
        </el-table-column>
        <el-table-column label="所长邮箱地址" width="200px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.emailDirector }}</span>
          </template>
        </el-table-column>
        <el-table-column label="报表接收邮箱" width="150px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.reportEmail }}</span>
          </template>
        </el-table-column>
        <el-table-column label="客户订单邮箱" width="150px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.emailOrder }}</span>
          </template>
        </el-table-column>
        <el-table-column label="库存订单邮箱" width="150px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.emailStock }}</span>
          </template>
        </el-table-column>
        <el-table-column label="服务备库邮箱" width="150px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.emailCSStock }}</span>
          </template>
        </el-table-column>
        <el-table-column label="分库在库邮箱" width="150px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.emailUserStock }}</span>
          </template>
        </el-table-column>
        <el-table-column label="专备邮箱" width="150px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span>{{ scope.row.emailSubStock }}</span>
          </template>
        </el-table-column>
        <el-table-column label="上级部门" align="center" width="80px">
          <template slot-scope="scope">
            <span>{{ scope.row.parentDeptNo }}</span>
          </template>
        </el-table-column>
        <el-table-column label="默认出库物流中心" align="center" width="80px">
          <template slot-scope="scope">
            <span>{{ scope.row.warehouseCode }}</span>
          </template>
        </el-table-column>
        <el-table-column label="默认出库分仓" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ scope.row.subWarehouseCode }}</span>
          </template>
        </el-table-column>
        <el-table-column label="SMC交易主体" align="center" width="80">
          <template slot-scope="scope">
            <span>{{ getCompanyCode(scope.row.tradeCompanyid) }}</span>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        :page-sizes="[10, 20, 50, 100]"
        :current-page="page.pageNumber"
        :page-size="page.pageSize"
        :total="page.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"/>
      <el-divider />

      <el-dialog :visible.sync="dialogaddVisible" :close-on-click-modal="false" title="新增" width="620px" @close="closeDialog">
        <el-form ref="addForm" :model="addForm" size="small" label-width="130px">
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="deptNo" label="部门代码">
              <el-input v-model="addForm.deptNo" style="width:150px" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="oldDeptNo" label="旧部门代码">
              <el-input v-model="addForm.oldDeptNo" style="width:150px" autocomplete="off" />
            </el-form-item>
          </el-row>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="parentDeptNo" label="上级部门">
              <el-input v-model="addForm.parentDeptNo" style="width:150px" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="deptName" label="部门名称">
              <el-input v-model="addForm.deptName" style="width:150px" autocomplete="off" />
            </el-form-item>
          </el-row>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="address" label="地址">
              <el-input v-model="addForm.address" style="width:150px" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="teleNo" label="电话">
              <el-input v-model="addForm.teleNo" style="width:150px" autocomplete="off" />
            </el-form-item>
          </el-row>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="tradeCompanyid" label="交易主体">
              <el-input v-model="addForm.tradeCompanyid" style="width:150px" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="emailAddr" label="通用邮箱">
              <el-input :rows="1" v-model="addForm.emailAddr" type="textarea" style="width:150px" autocomplete="off"/>
            </el-form-item>
          </el-row>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="emailDirector" label="所长邮箱">
              <el-input :rows="1" v-model="addForm.emailDirector" type="textarea" style="width:150px" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="emailUserStock" label="专备邮箱">
              <el-input :rows="1" v-model="addForm.emailUserStock" type="textarea" style="width:150px" autocomplete="off"/>
            </el-form-item>
          </el-row>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="emailOrder" label="客户订单邮箱">
              <el-input :rows="1" v-model="addForm.emailOrder" type="textarea" style="width:150px" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="emailStock" label="库存订单邮箱">
              <el-input :rows="1" v-model="addForm.emailStock" type="textarea" style="width:150px" autocomplete="off"/>
            </el-form-item>
          </el-row>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="emailCSStock" label="服务备库邮箱">
              <el-input :rows="1" v-model="addForm.emailCSStock" type="textarea" style="width:150px" autocomplete="off"/>
            </el-form-item>
            <el-form-item prop="emailSubStock" label="分库在库邮箱">
              <el-input :rows="1" v-model="addForm.emailSubStock" type="textarea" style="width:150px" autocomplete="off"/>
            </el-form-item>
          </el-row>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="warehouseCode" label="默认出库物流中心">
              <el-input v-model="addForm.warehouseCode" style="width:150px" autocomplete="off" />
            </el-form-item>
            <el-form-item prop="subWarehouseCode" label="默认出库分仓">
              <el-input v-model="addForm.subWarehouseCode" style="width:150px" autocomplete="off" />
            </el-form-item>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogaddVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogaddVisible = false;saveData('addForm')">保存</el-button>
        </div>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import { findDepartmentData, saveDepartmentInfo, deleteDeptById } from '@/api/common/department'
import { getDictDataByPid } from '@/api/common/dict'
export default {
  name: 'Dept',
  components: {

  },
  data() {
    return {
      listLoading: true,
      update: true,
      dialogaddVisible: false,
      form: {
        deptNo: '',
        deptName: ''
      },
      addForm: {
        deptNo: '',
        oldDeptNo: '',
        deptName: '',
        address: '',
        teleNo: '',
        emailAddr: '',
        gpslng: '',
        gpslat: '',
        parentDeptNo: '',
        warehouseCode: '',
        subWarehouseCode: '',
        tradeCompanyid: '',
        emailOrder: '',
        emailStock: '',
        emailCSStock: '',
        emailSubStock: ''
      },
      page: {
        total: 0,
        pageNumber: 1,
        pageSize: 20
      },
      companyClassCode: '1003',
      companyData: [],
      deptData: []
    }
  },

  created() {
    this.iniData()
    this.findList()
  },
  methods: {
    findList() {
      this.listLoading = true
      findDepartmentData(this.form, this.page).then(res => {
        this.deptData = res.list
        this.page.total = res.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    iniData() {
      getDictDataByPid(this.companyClassCode).then(result => {
        this.companyData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    updateInfo() {
      this.dialogaddVisible = true
      this.$nextTick(() => {
        this.addForm = this.$refs.deptTable.selection[0]
        console.log('修改参数 => ', this.addForm)
      })
    },
    // 换页
    handlePageSizeChange(newSize) {
      this.page.pageSize = newSize
      this.findList()
    },
    handlePageChange(newCurrent) {
      this.page.pageNumber = newCurrent
      this.findList()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      if (val.length > 1) {
        this.$refs.deptTable.clearSelection()
        this.$refs.deptTable.toggleRowSelection(val.pop())
      }
      if (this.multipleSelection.length > 0) {
        this.update = false
      } else {
        this.update = true
      }
    },
    addDept() {
      this.addForm.id = ''
      this.addForm.oldDeptNo = ''
      this.$nextTick(() => {
        this.$refs.addForm.resetFields()
      })
      this.dialogaddVisible = true
    },
    deletCondition() {
      if (this.$refs.deptTable.selection.length <= 0) {
        this.$message.error('请选择要删除的数据')
        return null
      }
      this.$confirm('确认要删除这些数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var id = this.$refs.deptTable.selection[0].id
        deleteDeptById(id).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.findList()
          } else {
            this.$message.error(res.content)
          }
        }).catch(error => {
          console.log(error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    getCompanyCode(val) {
      const obj = this.companyData.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    closeDialog() {
      this.findList()
    },
    saveData() {
      saveDepartmentInfo(this.addForm).then(res => {
        console.log('结果=> ', res)
        if (res.success) {
          this.$message.success(res.content)
          this.findList()
        } else {
          this.$message.error(res.content)
        }
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>

<style scoped>

  .deptTable /deep/ thead th .el-checkbox{
    visibility: hidden;
  }
</style>
