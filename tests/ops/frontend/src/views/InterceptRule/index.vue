<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-form ref="form" :model="form" :inline="true" size="mini" style="margin-top: 10px">
        <el-form-item label="名称关键字:">
          <el-input v-model="form.name" placeholder="请输入关键字" clearable size="medium" />
        </el-form-item>
        <el-form-item prop="modelNo" label="型号/系例">
          <el-input v-model="form.modelNo" placeholder="请输入型号或系例" clearable size="medium"/>
        </el-form-item>
        <el-form-item prop="status" label="状态">
          <el-select v-model="form.status" size="mini" clearable style="width: 100px">
            <el-option v-for="item in statusData" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item prop="applyType" label="拦截类型">
          <el-select v-model="form.applyType" size="mini" clearable style="width: 100px">
            <el-option v-for="item in applyTypeData" :key="item.code" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <span class="operation-button">
            <el-button v-permission="['978557']" class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList">查询</el-button>
            <el-button type="primary" size="mini" @click="showMultiLineInput">批量查询</el-button>
          </span>
        </el-form-item>
      </el-form>
      <el-divider />
      <el-row class="row-button">
        <el-button v-permission="['282532']" type="primary" size="mini" icon="el-icon-circle-plus" @click="addChange">新增</el-button>
        <el-button v-permission="['282532']" type="primary" size="mini" icon="el-icon-error" @click="deleteInterceptRuleByIds">选择取消</el-button>
        <el-button v-permission="['282532']" type="primary" size="mini" icon="el-icon-circle-close" @click="deleteInterceptRuleByCondition">条件取消</el-button>
        <el-button v-permission="['978557']" :loading="exportLoading" class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="exportProductInterceptRule">导出</el-button>
      </el-row>
      <div class="tableCloumn" style="margin-top: 5px">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :ref="tableData"
          :row-style="rowStyle"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
          :cell-style="{padding: '2px 3px'}"
          element-loading-text="拼命加载中"
          border
          fit
          highlight-current-row
          height="700"
          class="table-fixed"
          style="width:100%"
          @selection-change="selectChangeEvent"
          @select-all="selectAllEvent">
          <el-table-column type="selection" width="30" />
          <el-table-column label="序号" align="center" fixed="left" min-width="70">
            <template slot-scope="scope">
              <span>{{ scope.row.id }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed label="拦截名称" align="center" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column label="拦截类型" align="center" min-width="120">
            <template slot-scope="scope">
              <span>{{ getApplyCode(scope.row.applyType) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" min-width="100">
            <template slot-scope="scope">
              <span>{{ getStatusCode(scope.row.status) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="型号" min-width="180" align="left">
            <template slot-scope="scope">
              <span style="white-space: pre-wrap">{{ scope.row.modelNo }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="型号类型" align="center" min-width="100">
            <template slot-scope="scope">
              <span>{{ getModelType(scope.row.modelType) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="供应商" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.supplierId }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="原产地" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.origin }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="仓库" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.warehouseCode }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="许可客户" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.allowCustomerNos }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="最小数量" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.minQty }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="最大数量" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.maxQty }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="处理方式" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ getActionTypeType(scope.row.actionType) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="变更仓库" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.toWarehouseCode }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="变更供应商" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.toSupplierId }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="是否定时重新检查" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ getRestartFlag(scope.row.restartFlag) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="创新时间" min-width="150" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.createTime }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="更新时间" min-width="150" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.updateTime }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="更新人员" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.updateUser }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="备注" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.remark }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="150">
            <template slot-scope="scope">
              <el-button v-permission="['282532']" type="primary" size="mini" @click="editChange(scope.row)">修改</el-button>
              <el-button v-permission="['282532']" type="primary" size="mini" @click="deleteChange(scope.row)">取消</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <el-pagination
        :page-sizes="[20, 50, 100, 200, 500]"
        :current-page="page.pageNumber"
        :page-size="page.pageSize"
        :total="page.total"
        background
        style="margin-top: 10px"
        layout="total, sizes, prev, pager, next"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"/>
    </div>
    <el-dialog :visible.sync="addFormVisible" title="新增/修改拦截规则" width="750px">
      <el-form ref="addRequestData" :model="addRequestData" :inline="true" label-width="130px" size="mini">
        <el-form-item label="拦截名称" prop="name">
          <el-input v-model="addRequestData.name" clearable style="width:200px"/>
        </el-form-item>
        <el-form-item label="拦截申请" prop="applyType">
          <el-select v-model="addRequestData.applyType" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in applyTypeData"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-divider content-position="left" style="margin: 20px 0">匹配条件</el-divider>
        <el-form-item label="型号类型" prop="modelType">
          <el-select v-model="addRequestData.modelType" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in modelTypeData"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-row>
          <el-form-item label="型号" prop="modelNo">
            <span slot="label">
              <span class="span-box">
                <el-tooltip class="item" effect="dark" content="多个系列换行输入，模糊型号加特殊字符来匹配：#代表一个数字,*代表任意字符,?代表一个字符" placement="top-start">
                  <i class="el-icon-warning-outline" />
                </el-tooltip>
                <span class="font">型号</span>
              </span>
            </span>
            <el-input :rows="2" v-model="addRequestData.modelNo" autosize type="textarea" style="width: 200px"/>

          </el-form-item>

        </el-row>
        <el-divider content-position="left">拦截条件</el-divider>
        <el-form-item label="供应商" prop="supplierId">
          <el-select v-model="addRequestData.supplierId" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in supplierData"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="原产地" prop="origin">
          <el-select v-model="addRequestData.origin" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in supplierData"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="仓库" prop="warehouseCode">
          <el-select v-model="addRequestData.warehouseCode" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in warehouseData"
              :key="item.warehouseCode"
              :label="item.warehouseCode+','+item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="许可客户代码" prop="allowCustomerNos">
          <el-input v-model="addRequestData.allowCustomerNos" placeholder="多个客户代码用；分隔" clearable style="width:200px"/>
        </el-form-item>
        <el-form-item label="最小数量" prop="minQty">
          <el-input v-model="addRequestData.minQty" clearable style="width:200px"/>
        </el-form-item>
        <el-form-item label="最大数量" prop="maxQty">
          <el-input v-model="addRequestData.maxQty" clearable style="width:200px"/>
        </el-form-item>
        <el-divider content-position="left">处理方式/变更内容</el-divider>
        <el-form-item label="变更供应商" prop="toSupplierId">
          <el-select v-model="addRequestData.toSupplierId" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in supplierData"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="变更仓库" prop="toWarehouseCode">
          <el-select v-model="addRequestData.toWarehouseCode" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in warehouseData"
              :key="item.warehouseCode"
              :label="item.warehouseCode+','+item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="处理方式" prop="actionType">
          <el-select v-model="addRequestData.actionType" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in actionData"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label="是否定时重新检查" prop="restartFlag">
          <el-select v-model="addRequestData.restartFlag" placeholder="请选择" style="width:200px" clearable >
            <el-option
              v-for="item in restartFlagData"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input :rows="2" v-model="addRequestData.remark" type="textarea" placeholder="请输入内容" style="width:545px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click="addData()">确认</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :visible.sync="multiLineInputVisible"
      :before-close="handleCloseMultiQuery"
      title="型号/系例批量查询"
      width="500px"
      append-to-body>
      <el-input
        v-model="multiText"
        maxlength="500"
        rows="10"
        placeholder="请输入多行型号/系例数据"
        show-word-limit
        type="textarea"
      />
      <div class="dialog-footer">
        <el-button type="primary" @click="getMultiData">查询</el-button>
        <el-button @click="handleCloseMultiQuery">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import { listProductInterceptRuleByPage, addOrUpdateInterceptRule, deleteInterceptRule, exportProductInterceptRule, deleteInterceptRuleByIds, deleteInterceptRuleByCondition } from '@/api/stock/InterceptRule'
import { findSupplierByIdOrName } from '@/api/common/supplier'
import { listWarehouse, getDictDataByPid } from '@/api/common/dict'
import { downloadDataFile } from '@/api/common/comm'
export default {
  name: 'InterceptRule',
  components: { Pagination },
  data() {
    return {
      DictData: {
        type: [],
        status: [],
        transtype: []
      },
      selectList: [],
      tableData: [],
      warehouseData: [],
      supplierData: [],
      originData: [],
      editFormVisible: false,
      addFormVisible: false,
      listLoading: false,
      exportLoading: false,
      multiLineInputVisible: false,
      multiData: [],
      multiText: '',
      updateRequestData: {},
      addRequestData: {
        id: '',
        name: '',
        applyType: '',
        modelType: '',
        modelNo: '',
        supplierId: '',
        origin: '',
        warehouseData: '',
        maxQty: '',
        minQty: '',
        actionType: '',
        toSupplierId: '',
        toWarehouseCode: '',
        restartFlag: '',
        remark: ''
      },
      updateRequestDataDelivery: {},
      queryCondition: {},
      page: {
        pageNumber: 1,
        pageSize: 20,
        total: 20
      },
      form: {
        name: '',
        status: 1,
        modelNo: '',
        modelNos: [],
        applyType: '',
        pageNum: 1,
        pageSize: 20
      },
      applyTypeClassCode: '2046',
      actionTypeClassCode: '2047',
      applyTypeData: [],
      modelTypeData: [
        { value: 1, label: '完整型号' },
        { value: 2, label: '系列' }
      ],
      actionData: [],
      restartFlagData: [
        { value: '1', label: '是' },
        { value: '2', label: '否' }
      ],
      statusData: [
        { value: 1, label: '启用' },
        { value: 9, label: '取消' }
      ]
    }
  },
  created() {
    this.initData()
    this.listSupplierinfo()
    this.listWarehouse()
    this.getList()
  },
  methods: {
    // 处理状态
    initData() {
      getDictDataByPid(this.applyTypeClassCode).then(result => {
        this.applyTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.actionTypeClassCode).then(result => {
        this.actionData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    showMultiLineInput() {
      this.multiText = ''
      this.multiLineInputVisible = true
    },
    // 列表复选框改变事件
    selectChangeEvent(records) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element.id)
      })
    },
    // 列表全选事件
    selectAllEvent(records) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element.id)
      })
    },
    rowStyle({ row, rowIndex }) {
      return 'height:10px'
    },
    // 换页
    handlePageChange(newCurrent) {
      this.page.pageNumber = newCurrent
      this.getList()
    },
    // 改变每页条数
    handlePageSizeChange(newSize) {
      this.page.pageSize = newSize
      this.getList()
    },
    // 查询所有数据
    getList() {
      this.listLoading = true
      this.form.pageNum = this.page.pageNumber
      this.form.pageSize = this.page.pageSize
      listProductInterceptRuleByPage(this.form).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.page.total = res.content.total
        } else {
          this.$message.error(res.message)
        }
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    getMultiData() {
      try {
        if (this.multiText.indexOf('\n') === -1) {
          this.$message.error('请输入多行数据')
          return
        }
        this.multiLineInputVisible = false
        const rtnDatas = this.multiText.split(/\r\n|\n|\r/)
        this.form.modelNos = rtnDatas
        this.getList()
      } catch (error) {
        this.$message.error('错误：' + error)
      }
    },
    exportProductInterceptRule() {
      this.exportLoading = true
      exportProductInterceptRule(this.form).then(res => {
        const fileName = '特殊产品拦截规则清单.xlsx'
        downloadDataFile(this, res, fileName)
        this.exportLoading = false
      }).catch(error => {
        console.error(error)
        this.exportLoading = false
      })
    },
    addChange() {
      this.addFormVisible = true
      this.addRequestData.id = ''
      this.$nextTick(() => {
        this.$refs.addRequestData.resetFields()
      })
    },
    listSupplierinfo(val) {
      const parm = { 'companyId': '', 'name': '' }
      findSupplierByIdOrName(parm).then(res => {
        this.supplierData = res.content
      }).catch(error => {
        console.info(error)
      })
    },
    listWarehouse() {
      const data = { keywords: '', warehouseType: '', warehouseCode: '' }
      listWarehouse(data).then(res => {
        this.warehouseData = res.content
      }).catch(error => {
        console.info(error)
      })
    },
    // 新增拦截规则
    addData() {
      addOrUpdateInterceptRule(this.addRequestData).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
        this.addFormVisible = false
        this.getList()
      }).catch(error => {
        this.addFormVisible = false
        console.error(error)
      })
    },
    deleteChange(row) {
      this.$confirm('将取消该拦截配置, 是否继续?', '提示', {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning'
      }).then(() => {
        deleteInterceptRule(row.id).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.getList()
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          console.log(error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已放弃操作'
        })
      })
    },
    deleteInterceptRuleByIds() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要处理的数据'
        })
        return
      }
      this.$confirm('将取消该拦截配置, 是否继续?', '提示', {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning'
      }).then(() => {
        const formData = new FormData()
        formData.append('ids', this.selectList)
        deleteInterceptRuleByIds(formData).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.getList()
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          console.log(error)
        })
      })
    },
    deleteInterceptRuleByCondition() {
      this.$confirm('将取消该拦截配置, 是否继续?', '提示', {
        confirmButtonText: '是',
        cancelButtonText: '否',
        type: 'warning'
      }).then(() => {
        deleteInterceptRuleByCondition(this.form).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.getList()
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          console.log(error)
        })
      })
    },
    editChange(row) {
      this.addFormVisible = true
      this.$nextTick(() => {
        this.addRequestData = Object.assign({}, row)
        this.addRequestData.modelNo = this.addRequestData.modelNo.split('|').join('\n')
        this.addRequestData.applyType = row.applyType + ''
        this.addRequestData.actionType = row.actionType + ''
      })
    },
    getApplyCode(val) {
      val = val + ''
      const obj = this.applyTypeData.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getStatusCode(val) {
      const obj = this.statusData.filter(item => item.value === val)[0]
      return obj ? obj.label : ''
    },
    getModelType(val) {
      const obj = this.modelTypeData.filter(item => item.value === val)[0]
      return obj ? obj.label : ''
    },
    getActionTypeType(val) {
      val = val + ''
      const obj = this.actionData.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getRestartFlag(val) {
      const obj = this.restartFlagData.filter(item => item.value === val)[0]
      return obj ? obj.label : ''
    },
    handleCloseMultiQuery() {
      this.multiLineInputVisible = false
    }
  }
}
</script>
<style scoped>
.filter-container {
  padding-bottom: 10px;
}
.filter-container .filter-item {
    display: inline-block;
    vertical-align: middle;
  }
.row-button {
  margin-bottom: 10px;
}
.el-form-item {
   margin-bottom: 4px;
}
.el-button--primary {
  color: #337AB7;
  background-color: #fff;
  border:1px solid #337AB7;
}
.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #337AB7;
  color: #fff;
}
.el-divider--horizontal {
  margin: 20px 0;
}
</style>
