<template>
  <div class="base-index">
    <div class="public-container">
      <el-card shadow="hover">
        <el-form ref="form" :model="condition" :inline="true" :style="moreVisible === false? 'height:30px;': 'height:100%;'" label-suffix=":" size="small">
          <div class="operation-button">
            <!-- <el-form-item>
              <el-tooltip content="重置" placement="top">
                <el-button type="info" icon="el-icon-close" size="mini" circle />
              </el-tooltip>
            </el-form-item> -->
            <el-form-item>
              <el-button size="mini" type="primary" @click="queryList()">查询</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="inquiryCreate()">新建</el-button>
            </el-form-item>
            <el-form-item>
              <!-- <el-button size="mini" type="primary" @click="openSonItemExport()">导出</el-button> -->
              <el-button :loading="exportLoadingBtn" size="mini" type="primary" @click="exportExcelFile()">导出</el-button>
            </el-form-item>
            <el-form-item>
              <i :title="moreVisible === false? '展开': '收起'" :class="(moreVisible === false?'el-icon-arrow-down' : 'el-icon-arrow-up') +' el-icon-class'" @click="moreVisible = !moreVisible"/>
            </el-form-item>
          </div>
          <el-form-item label="业务单号">
            <el-input
              v-model="condition.orderNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入业务单号"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="申请号">
            <el-input
              v-model="condition.inquiryApplyNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入催促申请号"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="催促状态">
            <el-select v-model="condition.inquiryStatus" size="mini" placeholder="请选择催促状态" clearable @change="queryStatusChange()">
              <el-option
                v-for="item in DictData.inquiryStatus"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="申请日期">
            <el-date-picker
              v-model="applyDateRange"
              :picker-options="pickerOptions"
              :default-time="['00:00:00', '23:59:59']"
              type="daterange"
              align="right"
              size="mini"
              unlink-panels
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"/>
          </el-form-item>
          <el-form-item label="申请人">
            <el-input
              v-model="condition.inquiryUser"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入申请人ID或姓名"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="客户PO">
            <el-input
              v-model="condition.purchaseno"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入客户PO"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="客户代码">
            <el-input
              v-model="condition.customerNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入客户代码/名称"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="催促类型">
            <el-select v-model="condition.inquiryType" size="mini" clearable placeholder="请选择催促类型">
              <el-option
                v-for="item in DictData.inquirytype"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="供应商">
            <el-select v-model="condition.replyDeptList" size="mini" clearable placeholder="请选择供应商" multiple collapse-tags>
              <el-option
                v-for="item in suppilyList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="回复号">
            <el-input
              v-model="condition.replyNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入回复号"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="最终用户">
            <el-input
              v-model="condition.endUser"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入最终用户代码/名称"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="型号">
            <el-input
              v-model="condition.modelNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入型号"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="订单类型">
            <el-select v-model="condition.orderType" :style="{width: '25%'}" size="mini" clearable placeholder="请选择订单类型">
              <el-option
                v-for="item in DictData.ordtype"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="申请部门">
            <department :style="{width: '95%'}" @handleScopeChange="handleScopeChange" />
          </el-form-item>
        </el-form>
      </el-card>
      <div style="margin-top:10px;">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '13px'}"
          :cell-style="{padding: '2px 3px'}"
          :row-key="getRowKeys"
          :expand-row-keys="expands"
          element-loading-text="拼命加载中"
          border
          stripe
          fit
          highlight-current-row
          height="660"
          style="width:100%"
          @expand-change="clickSubTab"
        >
          <el-table-column label="序号" align="center" fixed="left" prop="id" min-width="50">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column fixed="left" prop="inquiryApplyNo" align="left" label="催促申请号" min-width="150" show-overflow-tooltip />
          <el-table-column fixed="left" type="expand" label="*" align="left" width="40" show-overflow-tooltip>
            <template slot-scope="props">
              <div style="margin-left:230px;">
                <el-table
                  v-loading="childloading"
                  :data="props.row.childData"
                  :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '13px'}"
                  element-loading-text="拼命加载中"
                  stripe
                  border
                  highlight-current-row
                  style="width:100%"
                >
                  <el-table-column label="序号" align="center" fixed="left" prop="id" width="50">
                    <template slot-scope="scope">
                      <span>{{ scope.$index + 1 }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column fixed prop="rorderSplitno" align="left" label="拆分单号" width="120" show-overflow-tooltip />
                  <el-table-column fixed prop="modelNo" align="left" label="型号" width="120" show-overflow-tooltip />
                  <el-table-column fixed prop="quantity" align="left" label="数量" width="100" show-overflow-tooltip />
                  <el-table-column fixed prop="orderStatusDesc" align="left" label="订单状态" width="100" show-overflow-tooltip />
                  <el-table-column fixed prop="dlvModdate" align="left" label="催促时返信" width="100" show-overflow-tooltip />
                  <!-- <el-table-column fixed prop="hopeDeliveryDate" align="left" label="期望出库日" width="100" show-overflow-tooltip /> -->
                  <el-table-column fixed prop="expectedDeliveryTime" align="left" label="工厂预计发货日" width="120" show-overflow-tooltip />
                  <el-table-column fixed prop="estimatedDeliveryDay" align="left" label="预计送达客户日" width="120" show-overflow-tooltip />
                  <el-table-column :formatter="suppilyFormatter" fixed prop="replyDept" align="left" label="供应商" width="140" show-overflow-tooltip />
                  <!-- <el-table-column fixed prop="replySupplierDept" align="left" label="回复部门" width="100" show-overflow-tooltip /> -->
                  <el-table-column fixed prop="supplierOrderNo" align="left" label="供应商接单号" width="120" show-overflow-tooltip />
                  <el-table-column fixed prop="replyNo" align="left" label="回复号" width="100" show-overflow-tooltip />
                  <el-table-column fixed prop="replyDeliveryDateSrc" align="left" label="回复货期" width="160" show-overflow-tooltip />
                  <el-table-column fixed prop="replyUser" align="left" label="回复人" width="100" show-overflow-tooltip />
                  <el-table-column fixed prop="replyTime" align="left" label="回复时间" width="160" show-overflow-tooltip />
                  <el-table-column fixed prop="replyDelayReason" align="left" label="回复延迟原因" width="140" show-overflow-tooltip />
                  <el-table-column fixed prop="replyRemark" align="left" label="回复备注" width="100" show-overflow-tooltip />
                  <el-table-column fixed prop="inquiryLevel" align="left" label="催促级别" width="100" show-overflow-tooltip />
                </el-table>
              </div>
            </template>
          </el-table-column>
          <el-table-column :formatter="inquiryTypeFormatter" prop="inquiryType" align="left" label="催促类型" min-width="100" show-overflow-tooltip />
          <el-table-column :formatter="ordtypeFormatter" prop="orderType" align="left" label="订单类型" min-width="100" show-overflow-tooltip />
          <el-table-column :formatter="inquiryStatusFormatter" prop="inquiryStatus" align="left" label="催促状态" min-width="90" show-overflow-tooltip />
          <el-table-column :formatter="suppilyFormatter" prop="replyDept" align="center" label="供应商" min-width="140" show-overflow-tooltip />
          <el-table-column prop="orderNo" align="left" label="业务单号" min-width="140" show-overflow-tooltip />
          <el-table-column prop="modelNo" align="left" label="型号" min-width="120" show-overflow-tooltip />
          <el-table-column prop="quantity" align="left" label="数量" min-width="80" show-overflow-tooltip />
          <el-table-column prop="hopeExportDate" align="left" label="指定出荷日" min-width="110" show-overflow-tooltip />
          <el-table-column prop="dlvModdate" align="left" label="返信纳期" min-width="100" show-overflow-tooltip />
          <el-table-column prop="hopeDeliveryDate" align="left" label="期望出库日" min-width="100" show-overflow-tooltip />
          <el-table-column prop="inquiryTime" align="center" label="催促时间" min-width="160" show-overflow-tooltip />
          <el-table-column prop="replyDeliveryDateSrc" align="left" label="回复货期" min-width="100" show-overflow-tooltip />
          <el-table-column prop="replyTime" align="center" label="回复时间" min-width="110" show-overflow-tooltip />
          <el-table-column prop="replyResultDesc" show-overflow-tooltip label="催促结果描述" min-width="100" align="center">
            <!-- <template slot-scope="scope">
              <span v-if="isEmpty(scope.row.replyDeliveryDate)">
                {{ " " }}
              </span>
              <span v-else-if="scope.row.replyDeliveryDate > scope.row.hopeDeliveryDate">
                <div style="color: #FF5809;"> {{ "超预期" }} </div>
              </span>
              <span v-else>
                <div style="color: #09ec2f;"> {{ "预期内" }} </div>
              </span>
            </template> -->
          </el-table-column>
          <el-table-column prop="inquiryReason" align="left" label="催促原因" min-width="150" show-overflow-tooltip />
          <el-table-column prop="inquiryReasonParam" align="left" label="催促原因参数" min-width="100" show-overflow-tooltip />
          <el-table-column prop="inquiryRemark" align="left" label="催促备注" width="100" show-overflow-tooltip />
          <el-table-column prop="customerNo" align="left" label="客户" width="80" show-overflow-tooltip />
          <el-table-column prop="endUser" align="left" label="最终用户" width="80" show-overflow-tooltip />
          <el-table-column prop="purchaseNo" align="left" label="客户PO" min-width="130" show-overflow-tooltip />
          <el-table-column prop="inquiryLevel" align="left" label="催促级别" width="100" show-overflow-tooltip />
          <el-table-column prop="inquiryUser" align="left" label="申请人员工编号" width="120" show-overflow-tooltip />
          <el-table-column prop="inquiryUserName" align="left" label="申请人" width="110" show-overflow-tooltip />
          <!-- <el-table-column prop="inquiryReasonType" align="left" label="催促分类码" min-width="100" show-overflow-tooltip />
            <el-table-column prop="supplierOrderNo" align="left" label="供应商接单号" min-width="100" show-overflow-tooltip />
            <el-table-column prop="replyNo" align="left" label="回复号" min-width="100" show-overflow-tooltip />
          <el-table-column :formatter="statecodeFormatter" prop="orderStatus" align="left" label="订单状态" width="100" show-overflow-tooltip />
          <el-table-column prop="inquiryDept" align="left" label="申请部门" width="100" show-overflow-tooltip />
          <el-table-column prop="inquiryUserName" align="left" label="申请人" width="110" show-overflow-tooltip /> -->
        </el-table>
      </div>
      <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="queryList()" />
      <exportExcel ref="exportExcelVue" />
    </div>
  </div>
</template>
<script>
import Department from '@/components/DepartmentOther'
import { shortcuts } from '@/utils/dataFormatPurchase'
import moment from 'moment'
import { getDataCodesTree } from '@/api/common/dict'
import Pagination from '@/components/Pagination'
import { fetchList, fetchReplyList, exportExcelData } from '@/api/inquiry/query'
import { getSuppily } from '@/api/intercept'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'InquiryQuery',
  components: { Department, Pagination, exportExcel },
  data() {
    return {
      exportLoadingBtn: false,
      applyDateRange: [
        moment().startOf('day').subtract(1, 'months'),
        moment().startOf('day')
      ],
      moreVisible: true,
      DictData: {
        inquiryStatus: [],
        purchaseStatus: [],
        orderStatus: [],
        ordtype: [],
        replyDept: [],
        inquirytype: [
          {
            value: '0',
            label: '采购催促'
          },
          {
            value: '1',
            label: '订单催促'
          }
        ]
      },
      total: 0,
      condition: {
        // inquiryType: '0' // 催促类别，一期默认为采购催促
        // inquiryStatus: '1'
      },
      page: {
        pageNumber: 1,
        pageSize: 50
      },
      exportPage: {
        pageNumber: 1,
        pageSize: 4000
      },
      pickerOptions: shortcuts, // 日期选择
      listLoading: false, // 表格时间
      childloading: false,
      tableData: [],
      detailTableData: [], // 子表格
      getRowKeys(row) { // 行数据的Key
        return row.inquiryApplyNo
      },
      expands: [],
      suppilyList: [],
      inquiryDeptList: []
    }
  },
  watch: {
    '$route': {
      handler(val) {
        const query = val.query
        if (query.hasOwnProperty('refresh') && query.refresh === '1') {
          this.queryList()
          this.$router.push({
            path: this.$route.path
          })
        }
      }
    }
  },
  created() {
    this.initData()
    this.queryList()
    this.getSuppily()
  },
  methods: {
    queryList() {
      this.listLoading = true
      this.setDateCondition()
      fetchList(this.condition, this.page).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
          this.listLoading = false
        }
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    inquiryCreate() {
      this.$router.push(
        {
          path: '/eta/inquiry/apply'
        })
    },
    // table每次只能展开一行
    clickSubTab(row, expandedRows) {
      // this.detailTableData = []
      this.expands = []
      if (expandedRows.length > 0) {
        row ? this.expands.push(row.inquiryApplyNo) : ''
      }
      // 未回复的不能查询到子项
      if (row.inquiryType === '1' || !this.isEmpty(row.replyTime)) {
        this.childloading = true
        fetchReplyList(row).then(res => {
          this.$set(row, 'childData', res.content)
          this.childloading = false
        })
      }
    },
    queryStatusChange() {
      this.queryList()
    },
    handleScopeChange(val) {
      // this.condition.inquiryDept = val[0]
      this.condition.inquiryDeptList = val
    },
    // 日期选择输入框
    setDateCondition() {
      if (this.applyDateRange && this.applyDateRange.length > 0) {
        this.condition.startDate = this.applyDateRange[0]
        this.condition.endDate = this.applyDateRange[1]
      } else {
        this.condition.startDate = null
        this.condition.endDate = null
      }
    },
    // 初始加载字典参数
    initData() {
      getDataCodesTree('2085').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.inquiryStatus.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      getDataCodesTree('2033').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.purchaseStatus.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      getDataCodesTree('1002').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.orderStatus.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      getDataCodesTree('1002').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.ordtype.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 催促状态转换字典
    inquiryStatusFormatter(row, column, cellValue, index) {
      const item = this.DictData.inquiryStatus.find(dict => dict.value === cellValue.toString())
      return item ? item.label : cellValue
    },
    // 采购单状态字典
    statecodeFormatter(row, column, cellValue, index) {
      const item = this.DictData.purchaseStatus.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // 订单状态转换字典
    orderStatusFormatter(row, column, cellValue, index) {
      const item = this.DictData.orderStatus.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    inquiryTypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.inquirytype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // 订单类别转换
    ordtypeFormatter(row, column, cellValue) {
      const item = this.DictData.ordtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    clearFormInline() {
      this.$refs.form.resetFields()
      this.condition = {
        // inquiryType: '0', // 催促类别，一期默认为采购催促
        inquiryStatus: '1'
      }
      this.setDateCondition()
    },
    // 比较时间大小,bb<aa（false） ;aa>=bb（true）
    checkEndTime(aa, bb) {
      var startTime = aa
      var start = new Date(startTime.replace('-', '/').replace('-', '/'))
      var endTime = bb
      var end = new Date(endTime.replace('-', '/').replace('-', '/'))
      if (end < start) {
        return false
      }
      return true
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data.filter(ele => ele.supplierClass === 'JP' || ele.supplierClass === 'MANU' ||
        ele.supplierClass === 'GZ')
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    // 导出文件
    exportExcelFile() {
      this.exportLoadingBtn = true
      this.setDateCondition()
      exportExcelData(this.condition).then(res => {
        console.log('size=> ', res.size)
        if (res.size === 0) {
          this.$message({
            duration: 4000,
            message: '暂无可导出数据',
            type: 'error'
          })
          this.exportLoadingBtn = false
          return
        }
        const fileName = '数据导出.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportLoadingBtn = false
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
        this.exportLoadingBtn = false
      })
    },
    openSonItemExport() {
      const tableColumn = [
        { field: 'inquiryApplyNo', title: '催促申请号' },
        { field: 'orderNo', title: '订单号' },
        { field: 'modelNo', title: '型号' },
        { field: 'quantity', title: '数量' },
        { field: 'customerNo', title: '客户代码' },
        { field: 'endUser', title: '最终用户' },
        { field: 'purchaseNo', title: '客户PO' },
        { field: 'orderStatus', title: '订单状态' },
        { field: 'orderType', title: '订单类型' },
        { field: 'inquiryStatus', title: '催促状态' },
        { field: 'hopeExportDate', title: '指定出荷日' },
        { field: 'dlvModdate', title: '返信纳期' },
        { field: 'hopeDeliveryDate', title: '期望出库日' },
        { field: 'inquiryReason', title: '催促原因' },
        { field: 'inquiryReasonParam', title: '催促原因参数' },
        { field: 'inquiryRemark', title: '催促备注' },
        { field: 'inquiryTime', title: '催促时间' },
        { field: 'inquiryDept', title: '申请部门' },
        { field: 'inquiryUserName', title: '申请人' },
        { field: 'replyDept', title: '供应商' },
        { field: 'supplierOrderNo', title: '供应商接单号' },
        { field: 'replyNo', title: '回复号' },
        { field: 'replyDeliveryDateSrc', title: '回复货期' },
        { field: 'replyUser', title: '回复人' },
        { field: 'replyTime', title: '回复时间' },
        { field: 'replyDelayReason', title: '供应商回复内容' },
        { field: 'replyRemark', title: '回复备注' },
        { field: 'inquiryLevel', title: '催促级别' },
        { field: 'replySupplierDept', title: '回复部门' },
        { field: 'result', title: '催促结果描述' }
      ]
      this.setDateCondition()
      fetchList(this.condition, this.exportPage).then(res => {
        if (res.success) {
          res.content.list.forEach((item) => {
            item.orderStatus = this.exportFormmater('purchaseStatus', String(item.orderStatus))
            item.orderType = this.exportFormmater('ordtype', item.orderType)
            item.inquiryStatus = this.exportFormmater('inquiryStatus', String(item.inquiryStatus))
            if (!this.isEmpty(item.replyDeliveryDate)) {
              if (item.replyDeliveryDate > item.hopeDeliveryDate) {
                item.result = '超预期'
              } else {
                item.result = '预期内'
              }
            }
          })
          this.$refs.exportExcelVue.initExportExcel(res.content.list, tableColumn)
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 导出翻译成中文
    exportFormmater(type, val) {
      const item = this.DictData[type].find(dict => dict.value === val)
      return item ? item.label : val
    }
  }
}
</script>
