<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <el-form ref="form" :model="form" :inline="true">
          <el-form-item label="请购单号：">
            <el-input v-model="form.orderno" placeholder="请输入请购单号" style="width=100%" clearable size="medium" />
          </el-form-item>
          <el-form-item label="型号：">
            <el-input v-model="form.modelno" placeholder="请输入型号" clearable size="medium" />
          </el-form-item>
          <el-form-item label="请购单类别: " >
            <el-select v-model="form.ordtype" placeholder="请选择请购单类别" clearable size="medium">
              <el-option
                v-for="item in ordTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="拦截分类: " >
            <el-select v-model="form.ordtype" placeholder="请选择拦截分类" clearable size="medium">
              <el-option
                v-for="item in ordTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item> -->
          <span class="operation-button">
            <el-form-item>
              <el-tooltip effect="light" content="重置" placement="top">
                <el-button type="info" icon="el-icon-close" size="medium" circle @click="resetForm()"/>
              </el-tooltip>
              <el-button class="filter-item" type="primary" size="medium" icon="el-icon-search" @click="getList()">查询</el-button>
            </el-form-item>
          </span>
        </el-form>
      </el-card>
      <div style="margin-top:20px;">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :row-style="rowStyle"
          element-loading-text="拼命加载中"
          border
          fit
          highlight-current-row
          style="width:100%">
          <el-table-column label="序号" align="center" min-width="40">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="请购单号" align="center">
            <template slot-scope="scope">
              <el-tag size="mini"><a style="color: blue" @click="toDetail(scope.row.orderno)">{{ scope.row.orderno }}</a></el-tag>
              <!-- <span>{{ scope.row.orderno }}</span> -->
              <!-- <div style="padding: 4px;color: #409EFF;cursor: pointer" @click="toDetail(scope.row.orderno)"><u>{{ scope.row.orderno }}</u></div> -->
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="型号" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.modelno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购数量" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="SHIKOMI" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.shikomianswerno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="INQ-B" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.inqno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="订单类型" prop="ordtype" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.ordtype | ordTypeFormat }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="订货日期" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.orderdate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="期望货期" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopedeliverydate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="入库仓库" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.requestwarehouseid }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="供应商" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.supplierid }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="指定出荷日" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopeexportdate }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="transTypeFormat" show-overflow-tooltip label="运输方式" align="center">
            <!-- <template slot-scope="scope">
              <span>{{ scope.row.transtype }}</span>
            </template> -->
          </el-table-column>
          <el-table-column show-overflow-tooltip label="拦截原因" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.interceptmsg }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column show-overflow-tooltip label="请购单状态" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.statecode }}</span>
              <span v-if="scope.row.statecode==='4'">
                <el-tooltip placement="top-start" effect="light" content="拦截原因">
                  <i class="el-icon-warning" style="color: #FFD700; cursor: pointer; font-size: 15px;"/>
                </el-tooltip>
              </span>
            </template>
          </el-table-column> -->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="160">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="edit(scope.row)">放行</el-button>
              <!-- <el-button type="danger" size="mini" @click="handleDelete(scope.row)">删除</el-button> -->
              <!-- <el-button v-if="scope.row.statecode === '4'" type="warning" size="mini" @click="updateStateCode(scope.row)">放行</el-button> -->
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog :visible.sync="editFormVisible" title="订单放行" align="center" width="40%">
      <el-card>
        <el-form ref="updateForm" :rules="rules" :model="updateRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="请购单号" prop="orderno">
            <el-input v-model="updateRequestData.orderno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="SHIKOMI" prop="shikomianswerno">
            <el-select v-model="updateRequestData.shikomianswerno" placeholder="请选择可用SHIKOMI" style="margin-left:20px;margin-bottom:5px;width:300px" clearable >
              <el-option
                v-for="item in suppilyList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="供应商" prop="supplierid">
            <el-select v-model="updateRequestData.supplierid" :disabled="suppilyVisible" placeholder="请选择供应商" style="margin-left:20px;margin-bottom:5px;width:300px" clearable >
              <el-option
                v-for="item in suppilyList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="放行原因" prop="interceptmsg">
            <el-input v-model="updateRequestData.interceptmsg" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button @click="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click = "updateData()">放行</el-button>
      </div>
    </el-dialog>
    <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getList()" />
  </div>
</template>
<script>
import { orderPass } from '@/api/requestPurchase'
import { getSuppily } from '@/api/intercept'
import { findList, updateShikomiRequest } from '@/api/interceptShikomi'
import Pagination from '@/components/Pagination'
export default {
  // shikomi拦截页面
  name: 'InterceptShikomi',
  components: { Pagination },
  filters: {
  },
  data() {
    return {
      tableData: [],
      editFormVisible: false,
      suppilyVisible: true,
      listLoading: true,
      updateRequestData: {},
      total: 0,
      queryCondition: {},
      page: {
        pageNumber: 1,
        pageSize: 10
      },
      form: {
      },
      suppilyList: [],
      rules: {
        // supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        // shikomianswerno: [{ required: true, message: '请选择SHIKOMI', trigger: 'blur' }],
        passreason: [{ required: true, message: '请选择放行原因', trigger: 'blur' }]
      },
      ordTypeList: [
        {
          value: '0',
          label: '销售订单'
        },
        {
          value: '1',
          label: 'BIN补库'
        },
        {
          value: '2',
          label: '先行在库补库'
        },
        {
          value: '3',
          label: '其他'
        }
      ]
    }
  },
  created() {
    this.getList()
  },
  methods: {
    rowStyle({ row, rowIndex }) {
      return 'height:10px'
    },
    // 重置表单
    resetForm() {
      // this.$refs.form.resetFields()
      this.form.orderno = ''
      this.form.modelno = ''
      this.form.ordtype = ''
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 查询所有数据
    getList() {
      this.listLoading = true
      if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.ordtype)) {
        this.queryCondition.condition = this.form
      }
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = this.page.pageSize
      findList(this.queryCondition).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    updateData() {
      this.$refs['updateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将放行该SHIKOMI拦截请购单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            updateShikomiRequest(this.updateRequestData).then(() => {
              this.editFormVisible = false
              this.$notify({
                title: '成功',
                message: 'SHIKOMI放行成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            }).catch(() => {
              this.loading = false
              this.$notify.error({
                title: '错误',
                message: 'SHIKOMI放行失败'
              })
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消SHIKOMI放行'
            })
          })
        }
      })
    },
    // 变更拦截状态
    updateStateCode(row) {
      this.$confirm('此操作将放行该请购单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        orderPass(row.orderno).then((response) => {
          if (response.data > 0) {
            this.$notify({
              title: '成功',
              message: '放行成功',
              type: 'success',
              duration: 2000
            })
            this.loading = false
            this.getList()
          }
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '放行失败'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消放行'
        })
      })
    },
    handleDelete(row) {
    },
    edit(row) {
      this.editFormVisible = true
      this.updateRequestData = Object.assign({}, row)
    },
    // 详情跳转方法
    toDetail(value) {
      this.$router.push({
        path: '/purchase/requestPurChaseDeail',
        query: { orderno: value }
      })
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    transTypeFormat(row, column) {
      if (row.transtype === '0') {
        return '空运'
      } else if (row.transtype === '1') {
        return '海运'
      }
      this.sele
    },
    selectDictLabel(datas, value) {
      var actions = []
      Object.keys(datas).some(key => {
        if (datas[key].dictValue === '' + value) {
          actions.push(datas[key].dictLabel)
          return true
        }
      })
      return actions.join('')
    }
  }
}
</script>
<style scoped>
/* .app-container .filter-containers .divHeader{
  position: relative;
  text-align: left;
} */
.filter-container {
  padding-bottom: 10px;
}
.filter-container .filter-item {
    display: inline-block;
    vertical-align: middle;
  }
.el-form-item {
   margin-bottom: 4px;
}
/* .el-table th{
  color: rgba(253, 253, 253, 0.938);
  background-color: rgb(117, 144, 168);
  padding: 6px;
  font-size: 14px;
 }
 .el-table td{
  padding: 2px;
} */
.el-button--primary {
  color: #337AB7;
  background-color: #fff;
  border:1px solid #337AB7;
}
.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #337AB7;
  color: #fff;
}
</style>

