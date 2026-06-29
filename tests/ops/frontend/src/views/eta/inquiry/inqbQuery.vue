<template>
  <div class="base-index">
    <div class="public-container">
      <el-card shadow="hover">
        <el-form ref="form" :model="condition" :inline="true" :style="moreVisible === false? 'height:30px;': 'height:100%;'" label-suffix=":" size="small">
          <div class="operation-button">
            <el-form-item>
              <el-button size="mini" type="primary" @click="queryList()">查询</el-button>
            </el-form-item>
            <el-form-item>
              <!-- <el-button size="mini" type="primary" @click="openSonItemExport()">导出</el-button> -->
              <el-button :loading="exportLoadingBtn" size="mini" type="primary" @click="exportExcelFile()">导出</el-button>
            </el-form-item>
            <el-form-item>
              <i :title="moreVisible === false? '展开': '收起'" :class="(moreVisible === false?'el-icon-arrow-down' : 'el-icon-arrow-up') +' el-icon-class'" @click="moreVisible = !moreVisible"/>
            </el-form-item>
          </div>
          <el-form-item label="申请号">
            <el-input
              v-model="condition.inqbApplyNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入申请号"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="门户申请号">
            <el-input
              v-model="condition.sourcesApplyNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入门户申请号"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="催促状态">
            <el-select :style="{width: '95%'}" v-model="condition.inqbStatus" size="mini" placeholder="请选择催促状态" clearable @change="queryStatusChange()">
              <el-option
                v-for="item in DictData.inqbStatus"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="有效状态">
            <el-select :style="{width: '95%'}" v-model="condition.inqbValidity" size="mini" placeholder="请选择有效状态" clearable @change="queryStatusChange()">
              <el-option
                v-for="item in DictData.inqbValidityList"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="申请人">
            <el-input
              v-model="condition.inqbUser"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入申请人员工编号或姓名"
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
              placeholder="请输入最终用户代码"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="催促型号">
            <el-input
              v-model="condition.modelNo"
              :style="{width: '95%'}"
              type="text"
              size="mini"
              placeholder="请输入催促型号"
              round
              clearable
              @keyup.enter.native="queryList"/>
          </el-form-item>
          <el-form-item label="申请部门">
            <department :style="{width: '95%'}" @handleScopeChange="handleScopeChange" />
          </el-form-item>
          <el-form-item label="供应商">
            <el-select :style="{width: '95%'}" v-model="condition.supplierCodeList" size="mini" clearable placeholder="请选择供应商" multiple collapse-tags>
              <el-option
                v-for="item in suppilyList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
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
        </el-form>
      </el-card>
      <div style="margin-top:10px;">
        <!-- @page-change="handlePageChangeOrder" -->
        <!-- :pager-config="orderTablePage" -->
        <vxe-grid
          ref="inqbQueryTable"
          :loading="listLoading"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '13px'}"
          :align="allAlign"
          :data="tableData"
          :columns="mainColumns"
          :expand-config="{accordion: true, lazy: true, loadMethod: toggleExpandMethod}"
          highlight-current-row
          height="660"
          border
          resizable
          auto-resize
          highlight-hover-row
          highlight-hover-column
          show-overflow
          footer-align="center"
          stripe
          size="small"
          class="mytable-scrollbar"
        >
          <!-- 添加子列表 -->
          <template v-slot:expand="{ row }">
            <el-form label-position="left" label-suffix=":" inline size="small" class="demo-table-expand">
              <vxe-grid
                :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '13px'}"
                :expand-config="{key: 'inqbApplyNo'}"
                :data="row.opsInqbDetailList"
                :columns="detailColumns"
                :loading="childloading"
                :align="allAlign"
                border
                show-overflow
                footer-align="center"
                stripe
                size="small"
                class="detailtable-scrollbar"
                style="margin-left: 180px;"/>
            </el-form>
          </template>
        </vxe-grid>
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
import { fetchList, findAllReason, exportExcelData } from '@/api/inquiry/inqbQuery'
import { getSuppily } from '@/api/intercept'
import exportExcel from '@/components/ExportExcel/index'
// import { getAllReason } from '../../../api/inquiry/apply'
export default {
  name: 'InqbQuery',
  components: { Department, Pagination, exportExcel },
  data() {
    return {
      exportLoadingBtn: false,
      applyDateRange: [
        moment().startOf('day').subtract(1, 'months'),
        moment().startOf('day')
      ],
      allAlign: 'center',
      selectedMainRow: null, // 当前选中的主列表项
      moreVisible: true,
      DictData: {
        inqbStatus: [],
        replyDept: [],
        reasonType: [],
        inqbValidityList: [
          { label: '不可用', value: '0' },
          { label: '可用', value: '1' }
        ],
        inqbDetailStatusList: [
          { label: '未使用', value: '0' },
          { label: '已使用', value: '1' }
        ],
        inqbDetailHandleList: [
          { label: '否', value: '0' },
          { label: '是', value: '1' }
        ],
        splitTypeStatusList: [
          { label: '不拆分', value: '0' },
          { label: '数量拆分', value: '1' },
          { label: '型号拆分', value: '2' }
        ],
        stockTypeList: [
          { label: '在库', value: 'N' },
          { label: '采购', value: 'CG' },
          { label: '生产在途', value: 'P' },
          { label: '采购在途', value: 'T' }
        ]
      },
      total: 0,
      condition: {
        inqbStatus: '1'
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
      inqbDeptList: [],
      mainColumns: [
        { type: 'seq', fixed: 'left', width: 60 },
        { field: 'inqbApplyNo', title: '申请号', fixed: 'left', width: 120 },
        { type: 'expand', width: 40, title: '*', fixed: 'left', align: 'center', slots: { content: 'expand' }},
        { field: 'sourcesApplyNo', title: '门户申请号', fixed: 'left', width: 110 },
        // { field: 'dataSources', title: '来源', fixed: 'left', width: 60 },
        { field: 'modelNo', title: '型号', minWidth: 150, showOverflow: true },
        { field: 'quantity', title: '数量', minWidth: 60, showOverflow: true },
        { field: 'endUser', title: '最终用户', minWidth: 90 },
        // { field: 'userDept', title: '用户所属所', minWidth: 120 },
        { field: 'inqbStatus', title: '催促状态', minWidth: 90, formatter: this.inquiryStatusFormatter },
        { field: 'inqbValidity', title: '有效状态', minWidth: 90, formatter: this.inqbValidityFormatter },
        { field: 'expirationDate', title: '有效期', minWidth: 120, formatter: this.dateTimeformatter },
        { field: 'expectedDeliveryDate', title: '期望货期', minWidth: 90, showOverflow: true },
        { field: 'inqbClassify', title: '催促原因', minWidth: 120, showOverflow: true, formatter: this.inquiryReasonFormatter },
        { field: 'description', title: '催促描述', minWidth: 100, showOverflow: true },
        { field: 'inqbDate', title: '催促日期', minWidth: 140, showOverflow: true, formatter: this.dateTimeformatter },
        // { field: 'replyDept', title: '供应商', minWidth: 100, showOverflow: true, formatter: this.suppilyFormatter },
        // { field: 'inqbDept', title: '催促部门', minWidth: 120, showOverflow: true },
        { field: 'inqbUserName', title: '催促人', minWidth: 90, showOverflow: true },
        { field: 'replyNo', title: '回复号', minWidth: 90, showOverflow: true },
        { field: 'replyDeliveryDays', title: '回复货期', minWidth: 90, showOverflow: true },
        { field: 'replyTime', title: '回复时间', minWidth: 140, showOverflow: true, formatter: this.dateTimeformatter }
      ],
      // 子项列表
      detailColumns: [
        // { field: 'inqbApplyNo', title: '申请号', fixed: 'left', minWidth: 120 },
        { field: 'itemNo', title: '子项号', fixed: 'left', minWidth: 80 },
        { field: 'splitType', title: '拆分类型', minWidth: 100, formatter: this.splitTypeFormatter },
        { field: 'supplierCode', title: '期望供应商', minWidth: 110, formatter: this.suppilyFormatter },
        { field: 'stockCode', title: '出库区分', minWidth: 90, formatter: this.stockTypeFormatter },
        { field: 'handleResult', title: '是否催促', minWidth: 90, formatter: this.detailHandleFormatter },
        { field: 'modelNo', title: '型号', minWidth: 120 },
        { field: 'quantity', title: '数量', minWidth: 90 },
        { field: 'useQty', title: '使用数量', minWidth: 90 },
        { field: 'expectedDeliveryDate', title: '期望货期', minWidth: 90 },
        { field: 'expirationDate', title: '有效期', minWidth: 120, formatter: this.dateTimeformatter },
        { field: 'replySupplierDept', title: '回复部门', minWidth: 100 },
        { field: 'replyNo', title: '回复号', minWidth: 100, showOverflow: true },
        { field: 'replyDeliveryDays', title: '回复货期', minWidth: 90, showOverflow: true },
        // { field: 'replyAcceptHl', title: '接收HL', minWidth: 90, showOverflow: true },
        { field: 'replyUser', title: '回复人', minWidth: 90, showOverflow: true },
        { field: 'replyTime', title: '回复时间', minWidth: 120, formatter: this.dateTimeformatter, showOverflow: true },
        { field: 'replyReason', title: '回复原因', minWidth: 100, showOverflow: true },
        { field: 'replyRemark', title: '回复备注', minWidth: 100, showOverflow: true }
        // ... 其他子列表列
      ]
    }
  },
  // watch: {
  //   '$route': {
  //     handler(val) {
  //       const query = val.query
  //       if (query.hasOwnProperty('refresh') && query.refresh === '1') {
  //         this.queryList()
  //         this.$router.push({
  //           path: this.$route.path
  //         })
  //       }
  //     }
  //   }
  // },
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
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
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
    queryStatusChange() {
      this.queryList()
    },
    handleScopeChange(val) {
      this.condition.inqbDeptList = val
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
      getDataCodesTree('6007').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.inqbStatus.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      findAllReason().then(result => {
        result.content.forEach(dict => {
          this.DictData.reasonType.push({ value: dict.opsReasonCode, label: dict.opsReasonDesc })
        })
        console.log(this.DictData.reasonType)
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 催促状态转换字典
    inquiryStatusFormatter({ cellValue }) {
      const item = this.DictData.inqbStatus.find(dict => dict.value === cellValue.toString())
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
        inqbStatus: '1'
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
    suppilyFormatter({ cellValue }) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    inqbValidityFormatter({ cellValue }) {
      const item = this.DictData.inqbValidityList.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    detailStatusFormatter({ cellValue }) {
      const item = this.DictData.inqbDetailStatusList.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    detailHandleFormatter({ cellValue }) {
      const item = this.DictData.inqbDetailHandleList.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    splitTypeFormatter({ cellValue }) {
      const item = this.DictData.splitTypeStatusList.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    deptFormatter({ cellValue }) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    stockTypeFormatter({ cellValue }) {
      const item = this.DictData.stockTypeList.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    inquiryReasonFormatter({ cellValue }) {
      const item = this.DictData.reasonType.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
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
        { field: 'inqbApplyNo', title: '催促申请号' },
        { field: 'sourcesApplyNo', title: '门户申请号' },
        { field: 'modelNo', title: '型号' },
        { field: 'quantity', title: '数量' },
        { field: 'endUser', title: '最终用户' },
        { field: 'userDept', title: '用户所属部门' },
        { field: 'inqbStatus', title: '催促状态' },
        { field: 'inqbValidity', title: '有效状态' },
        { field: 'expirationDate', title: '有效期' },
        { field: 'expectedDeliveryDate', title: '期望货期' },
        { field: 'description', title: '催促描述' },
        { field: 'inqbDate', title: '催促日期' },
        { field: 'inqbClassify', title: '催促原因' },
        { field: 'inqbDept', title: '催促部门' },
        { field: 'inqbUser', title: '催促人' },
        { field: 'inqbUserName', title: '催促人姓名' },
        { field: 'replyDept', title: '供应商' },
        { field: 'replySupplierDept', title: '回复部门' },
        // { field: 'replyAcceptHl', title: 'replyAcceptHl' },
        { field: 'replyNo', title: '回复号' },
        { field: 'replyDeliveryDays', title: '回复货期' },
        { field: 'replyUser', title: '回复人' },
        { field: 'replyTime', title: '回复时间' },
        { field: 'replyReasonCode', title: '回复原因代码' },
        { field: 'replyReason', title: '回复原因' },
        { field: 'replyRemark', title: '回复备注' }
      ]
      this.setDateCondition()
      fetchList(this.condition, this.exportPage).then(res => {
        if (res.success) {
          res.content.list.forEach((item) => {
            item.inqbStatus = this.exportFormmater('inqbStatus', String(item.inqbStatus))
            item.inqbClassify = this.exportFormmater('reasonType', String(item.inqbClassify))
            item.inqbValidity = this.exportFormmater('inqbValidityList', String(item.inqbValidity))
          })
          this.$refs.exportExcelVue.initExportExcel(res.content.list, tableColumn)
          // this.$refs.exportExcelVue.initExportExcel(res.content.list, tableColumn)
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
    },
    // 懒加载获取回复信息
    toggleExpandMethod({ row }) {
      return new Promise(resolve => {
        this.childrenLoading = true
        this.selectedMainRow = this.tableData.find(item => item.inqbApplyNo === row.inqbApplyNo)
        this.childrenLoading = false
        resolve()
      })
    },
    dateTimeformatter({ cellValue }) {
      if (cellValue === null) {
        return
      }
      var value = new Date(cellValue)
      var year = value.getFullYear()
      var padDate = function(va) {
        va = va < 10 ? '0' + va : va
        return va
      }
      var month = padDate(value.getMonth() + 1)
      var day = padDate(value.getDate())
      var hour = padDate(value.getHours())
      var minutes = padDate(value.getMinutes())
      var seconds = padDate(value.getSeconds())
      return year + '-' + month + '-' + day + ' ' + hour + ':' + minutes + ':' + seconds
    }
  }
}
</script>
<style>
  .detailtable-scrollbar {
    min-height: 100;
    /* 设置最小高度 */
    max-height: 600px;
    /* 设置最大高度 */
    overflow-y: auto;
    /* 当内容超出最大高度时显示垂直滚动条 */
  }
  .public-container .el-form .el-form-item .el-input {
    width: 96%;
  }
</style>
