<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <div>
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="请购单号">
              <el-input v-model="form.orderno" placeholder="请输入请购单号" style="width=80%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="型号">
              <el-input v-model="form.modelno" placeholder="请输入型号" style="width=80%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="请购单类别:" >
              <el-select v-model="form.purchasetype" placeholder="请选择请购单类别" clearable size="small">
                <el-option
                  v-for="item in DictData.type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>

            <el-form-item label="请购单状态" >
              <el-select v-model="form.statecode" placeholder="请选择采购单状态" clearable size="small">
                <el-option
                  v-for="item in DictData.status"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <!-- 新增按拦截原因查询-->
            <el-form-item>
              <span class="operation-button">
                <!-- <el-tooltip effect="light" content="重置" placement="top">
                <el-button type="info" icon="el-icon-close" size="medium" circle @click="resetForm()"/>
              </el-tooltip> -->
                <el-button v-permission="['501731']" class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList()">查询</el-button>
                <el-button v-permission="['586719']" class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="openSonItemExport">导出</el-button>
                <el-tooltip effect="light" content="展开" placement="top">
                  <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
                </el-tooltip>
              </span>
            </el-form-item>
          </el-form>
        </div>
        <div v-if="searchMoreForm" class="search">
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="供应商">
              <el-select v-model="form.supplierid" placeholder="请选择供应商" clearable size="small">
                <el-option
                  v-for="item in suppilyList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
            <el-form-item label="运输方式" >
              <el-select v-model="form.transtype" placeholder="请选择运输方式" clearable size="small">
                <el-option
                  v-for="item in DictData.transtype"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="订单类别" >
              <el-select v-model="form.ordtype" placeholder="请选择订单类别" clearable size="small">
                <el-option
                  v-for="item in DictData.ordtype"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="采购仓库  " >
              <el-select v-model="form.purchasewarehouseid" placeholder="请选择采购仓库" clearable size="small">
                <el-option
                  v-for="item in warehouseList"
                  :key="item.warehouseCode"
                  :label="item.warehouseName"
                  :value="item.warehouseCode"/>
              </el-select>
            </el-form-item>
            <el-form-item label="采购部门">
              <!-- <el-cascader
                ref ="deptCascader"
                :props="props"
                :options="scopeOptions"
                :show-all-levels="false"
                collapse-tags
                placeholder="营业所代码"
                style="min-width: 200px"
                size="small"
                filterable
                clearable
                @change="handleScopeChange"
              /> -->
              <department style="min-width: 200px;margin-left: 1px" @handleScopeChange="handleScopeChange"/>
            </el-form-item>
            <el-form-item label="批次号">
              <el-input v-model="form.sendversion" placeholder="请输入批次号" style="width=80%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="采购单号">
              <el-input v-model="form.poOrderNo" placeholder="请输入采购单号" style="width=80%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="请购日期">
              <el-date-picker
                v-model="requesTime"
                :picker-options="pickerOptions"
                :default-time="['00:00:00', '23:59:59']"
                type="daterange"
                align="right"
                size="small"
                unlink-panels
                style="width: 230px"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"/>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <div style="margin-top:20px;">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
          :cell-style="{padding: '2px 3px'}"
          element-loading-text="拼命加载中"
          border
          fit
          highlight-current-row
          height="640"
          style="width:100%"
          @sort-change="sortChange"
        >
          <el-table-column label="序号" align="center" fixed="left" prop="id" min-width="70">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <!--bug12361 OPS采购查询 营业所字段数据错误-->
          <!-- <el-table-column :formatter="deptFormatter" prop="applyDeptNo" fixed="left" show-overflow-tooltip label="申请部门" min-width="100" align="center"/> -->
          <el-table-column :formatter="deptFormatter" prop="deptno" show-overflow-tooltip label="营业所" min-width="110" align="center"/>
          <el-table-column :formatter="warehouseFormatter" prop="requestwarehouseid" show-overflow-tooltip label="请购仓库" align="center" min-width="100"/>
          <el-table-column show-overflow-tooltip sortable="custom" label="请购单号" prop= "orderno" align="left" min-width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.splititemno==null">
                {{ scope.row.orderno+"-"+scope.row.itemno }}
              </span>
              <span v-if="scope.row.splititemno!==null">
                {{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}
              </span>
              <!-- <span>{{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}</span> -->
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip sortable="custom" label="型号" prop= "modelno" align="left" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.modelno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购数量" sortable="custom" prop= "quantity" align="left" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="suppilyFormatter" prop="supplierid" show-overflow-tooltip label="供应商" min-width="110" align="center"/>
          <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" align="center" min-width="90"/>
          <el-table-column show-overflow-tooltip label="制造出荷日" prop="hopeexportdate" sortable="custom" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="smcCode" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.smccode }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="statecodeFormatter" prop="statecode" label="状态" align="center" min-width="120"/>
          <el-table-column :formatter="ordtypeFormatter" prop="ordtype" show-overflow-tooltip label="订单类别" min-width="110" align="center"/>
          <el-table-column label="请购单类别" align="center" min-width="110">
            <template slot-scope="scope">
              <span>{{ scope.row.purchasetype | ordTypeFormat }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="重量" align="center">
            <template slot-scope="scope">
              <span>{{ (scope.row.netweight * scope.row.quantity).toFixed(4) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="SHIKOMI" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.shikomianswerno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="准备订单号" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.prepareorderno }}</span>
            </template>
          </el-table-column>
          <el-table-column label="供应商在库" min-width="100" align="center">
            <!-- 需新增字段 -->
            <template slot-scope="scope">
              <span>{{ scope.row.supplierinventory }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="specmarkFormatter" prop="specmark" show-overflow-tooltip label="组装标识" min-width="90" align="center"/>
          <!-- <el-table-column show-overflow-tooltip label="组装标识" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.specmark | specmarkFormatter }}</span>
            </template>
          </el-table-column> -->
          <el-table-column show-overflow-tooltip label="特殊标识" min-width="90" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.producttag=='0'">
                {{ 'H' }}
              </span>
              <span v-if="scope.row.producttag=='9'">
                {{ '其它' }}
              </span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="业务备注" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.serverremark }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购日期" prop="requesttime" sortable="custom" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.requesttime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="入库数量" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.qtyimport }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="完成日期" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.finishtime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="操作人" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.operator }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="删单原因" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.deletereason }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="相关备注" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.information }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="采购单号" prop= "poOrderNo" align="left" min-width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.poOrderNo==null">
                {{ scope.row.poOrderNo }}
              </span>
              <span v-else>
                <span v-if="scope.row.poSplitNo==null">
                  {{ scope.row.poOrderNo+"-"+scope.row.poItemNo }}
                </span>
                <span v-if="scope.row.poSplitNo!==null">
                  {{ scope.row.poOrderNo+"-"+scope.row.poItemNo+"-"+scope.row.poSplitNo }}
                </span>
              </span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="批次号" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.sendversion }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getList()" />
    <exportExcel ref="exportExcelVue"/>
  </div>
</template>
<script>
import { preProccessList } from '@/api/requestPurchase'
import { findDeptDict } from '@/api/warehouseManage'
import { getSuppily, getWarehouse } from '@/api/intercept'
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import { getDataCodesTree } from '@/api/common/dict'
import exportExcel from '@/components/ExportExcel/index'
import { shortcuts } from '@/utils/dataFormatPurchase'
import { getTransIds } from '@/api/purchaseOrder'
export default {
  name: 'UntreatedQuery',
  components: { Pagination, exportExcel, Department },
  data() {
    return {
      props: { multiple: true },
      DictData: {
        type: [],
        department: [],
        ordtype: [],
        status: [],
        specmark: [],
        transtype: []
      },
      scopeOptions: [],
      searchMoreForm: false,
      cpnysData: [],
      selectList: [],
      tableData: [],
      editFormVisible: false,
      editFormBatchVisible: false,
      listLoading: true,
      updateRequestData: {},
      updateRequestDataBatch: {},
      deptNoOptions: [],
      total: 0,
      suppilyList: [],
      warehouseList: [],
      queryCondition: {},
      confirmCondition: {},
      page: {
        pageNumber: 1,
        pageSize: 50
      },
      exportPage: {
        pageNumber: 1,
        pageSize: 20000
      },
      form: {
      },
      rules: {
        supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        transtype: [{ required: true, message: '请选择运输方式', trigger: 'blur' }],
        hopeexportdate: [{ required: true, message: '请选择出库日', trigger: 'blur' }]
      },
      productTagOption: [
        {
          value: '0',
          label: '否'
        },
        {
          value: '1',
          label: '是'
        }
      ],
      pickerOptions: shortcuts,
      requesTime: []
    }
  },
  created() {
    this.initData()
    this.getList()
    this.getSuppilyTrans()
    this.getWarehouseList()
    // 营业所
    findDeptDict().then(result => {
      result.forEach(dict => {
        this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
      })
    })
  },
  methods: {
    handleScopeChange(val) {
      console.log('所选营业所=>', val)
      this.form.deptNos = val
    },
    // 排序
    sortChange(column, prop, order) {
      // this.queryCondition.orderBy = column.prop
      if (column.order === 'descending') {
        this.queryCondition.orderBy = column.prop + ' desc'
      }
      if (column.order === 'ascending') {
        this.queryCondition.orderBy = column.prop + ' asc'
      }
      this.getList()
    },
    initData() {
      getDataCodesTree('2031').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.type.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('1002').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.ordtype.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('2032').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.status.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // bug 10526 修正组装标识前端显示
      getDataCodesTree('4401').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.specmark.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      var transParam = {}
      transParam.supplierId = null
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      transParam.translation = true
      getTransIds(transParam).then(res => {
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.DictData.transtype.push({ value: dict.id, label: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    rowStyle({ row, rowIndex }) {
      return 'height:2px'
    },
    // 营业所字典格式化
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 订单类别转换
    ordtypeFormatter(row, column, cellValue) {
      const item = this.DictData.ordtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    statecodeFormatter(row, column, cellValue, index) {
      const item = this.DictData.status.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // bug 10526 修正组装标识前端显示
    specmarkFormatter(row, column, cellValue, index) {
      const item = this.DictData.specmark.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    getSuppilyTrans() {
      this.getSuppily()
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 重置表单
    resetForm() {
      // localStorage.removeItem(this.condition)
      // localStorage.removeItem(this.page)
      this.form = {}
      this.page = {
        pageNumber: 1,
        pageSize: 100
      }
      // this.$refs.form.resetFields()
      // this.form.orderno = ''
      // this.form.modelno = ''
      // this.form.ordtype = ''
    },
    // 查询所有数据
    getList() {
      this.setDateCondition()
      this.listLoading = true
      if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.purchasetype) || !this.isEmpty(this.form.transtype) || !this.isEmpty(this.form.supplierid) || !this.isEmpty(this.form.deptNos) || !this.isEmpty(this.form.ordtype) || !this.isEmpty(this.form.statecode) ||
      !this.isEmpty(this.form.purchasewarehouseid) || !this.isEmpty(this.form.sendversion) || !this.isEmpty(this.form.poOrderNo) || !this.isEmpty(this.form.requesTimeStart)) {
        this.queryCondition.condition = this.form
      }
      // this.queryCondition = Object.assign({}, this.form)
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = this.page.pageSize
      preProccessList(this.queryCondition).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    openSonItemExport() {
      // bug 14093 如果导出结果超过20000条，前端提示导出上限为20000条
      this.$alert('导出上限为20000条，导出结果超过20000时将截取到第20000条！', '提示', {
        confirmButtonText: '确定',
        type: 'warning',
        callback: action => {
          const tableColumn = [
            { field: 'orderno', title: '订单号' },
            { field: 'itemno', title: '项号' },
            { field: 'splititemno', title: '拆分项号' },
            { field: 'ordtype', title: '订单类别' },
            { field: 'purchasetype', title: '采购类别' },
            { field: 'statecode', title: '状态' },
            { field: 'quantity', title: '数量' },
            { field: 'modelno', title: '型号' },
            { field: 'customerno', title: '客户代码' },
            // { field: 'userno', title: '用户代码' },
            { field: 'endUser', title: '最终用户' },
            { field: 'supplierid', title: '供应商' },
            { field: 'requesttime', title: '请购日期' },
            { field: 'hopeexportdate', title: '制造出荷日' },
            { field: 'hopedeliverydate', title: '期望货期' },
            { field: 'purchasewarehouseid', title: '收货仓库' },
            { field: 'transtype', title: '运输方式' },
            { field: 'serverremark', title: '业务备注' },
            { field: 'finishtime', title: '完成日期' },
            { field: 'remark', title: '备注' },
            { field: 'sendversion', title: '批次号' },
            { field: 'poOrderNo', title: '采购单号' },
            { field: 'poItemNo', title: '采购项号' },
            { field: 'poSplitNo', title: '采购拆分项号' },
            { field: 'prepareorderno', title: '准备订单号' }
          ]
          if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.purchasetype) || !this.isEmpty(this.form.transtype) || !this.isEmpty(this.form.supplierid) || !this.isEmpty(this.form.deptNos) || !this.isEmpty(this.form.ordtype) || !this.isEmpty(this.form.statecode) ||
          !this.isEmpty(this.form.purchasewarehouseid) || !this.isEmpty(this.form.sendversion) ||
          !this.isEmpty(this.form.poOrderNo) || !this.isEmpty(this.form.requesTimeStart)) {
            this.queryCondition.condition = this.form
          }
          this.queryCondition.pageNumber = this.exportPage.pageNumber
          this.queryCondition.pageSize = this.exportPage.pageSize
          this.setDateCondition()
          preProccessList(this.queryCondition).then(res => {
            if (res.success) {
              this.$refs.exportExcelVue.initExportExcel(res.data.list, tableColumn)
            }
          }).catch(error => {
            console.log(error)
          })
        }
      })
    },
    edit(row) {
      this.editFormVisible = true
      this.updateRequestData = Object.assign({}, row)
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    // 展开更多选项
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    getWarehouseList() {
      getWarehouse().then(res => {
        this.warehouseList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    warehouseFormatter(row, column, cellValue, index) {
      const item = this.warehouseList.find(dict => dict.warehouseCode === cellValue)
      return item ? item.warehouseName : cellValue
    },
    // 运输方式转换
    transtypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.transtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // 日期选择输入框
    setDateCondition() {
      if (this.requesTime === null) {
        this.form.requesTimeStart = ''
        this.form.requesTimeEnd = ''
      } else {
        this.form.requesTimeStart = this.requesTime[0]
        this.form.requesTimeEnd = this.requesTime[1]
      }
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

