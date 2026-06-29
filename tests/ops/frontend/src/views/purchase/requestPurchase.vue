<template>
  <div class="base-index">
    <div class="public-container">
      <el-card shadow="hover" >
        <el-form ref="form" :model="condition" :inline="true" :style="moreVisible === false? 'height:30px;': 'height:100%;'" label-suffix=":" size="small">
          <div class="operation-button">
            <el-form-item>
              <el-button type="info" icon="el-icon-close" title="重置" size="mini" circle @click="resetForm"/>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="getList">查询</el-button>
            </el-form-item>
            <el-form-item>
              <el-button v-permission="[' ']" size="mini" type="primary" @click="add">新建</el-button>
            </el-form-item>
            <el-form-item>
              <i :title="moreVisible === false? '展开': '收起'" :class="(moreVisible === false?'el-icon-arrow-down' : 'el-icon-arrow-up') +' el-icon-class'" @click="moreVisible = !moreVisible"/>
            </el-form-item>
          </div>
          <el-form-item label="特价号" prop="specialPriceNo">
            <el-input
              v-model="condition.specialPriceNo"
              type="text"
              size="mini"
              placeholder="特价号"
              clearable/>
          </el-form-item>
          <el-form-item label="型号" prop="modelNo">
            <el-input
              v-model="condition.modelNo"
              type="text"
              size="mini"
              placeholder="型号"
              clearable/>
          </el-form-item>
          <el-form-item label="客户" prop="customerNo">
            <el-select
              v-model="condition.customerNo"
              :remote-method="remoteFilter"
              :loading="userListloading"
              :no-data-text="noDataText"
              filterable
              clearable
              remote
              reserve-keyword
              size="mini"
              placeholder="客户名称/代码"
              @change="getUserInfo">
              <el-option v-for="item in customerCodesOptions" :key="item.customerNo" :label="'【' + item.customerNo + '】' + item.customerName" :value="item.customerNo">
                <span style="float: left">{{ item.customerName }}</span>
                <span style="padding-right:20px; color: #8492a6; font-size: 12px">【{{ item.customerNo }}】</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="客户担当" prop="customerTakeOnId">
            <el-select v-model="condition.customerTakeOnId" size="mini" placeholder="请选择" clearable>
              <el-option
                v-for="item in principalList"
                :key="item.psnsmcId"
                :label="item.psnName"
                :value="item.psnsmcId"/>
            </el-select>
          </el-form-item>
          <el-form-item label="特价状态" prop="status">
            <el-select v-model="condition.status" size="mini" placeholder="请选择" clearable >
              <el-option
                v-for="item in approvalStatusList"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="特价类型" prop="specialPriceType">
            <el-select v-model="condition.specialPriceType" size="mini" placeholder="请选择" clearable>
              <el-option
                v-for="item in specialPriceTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="申请日期" prop="applyDateRange">
            <el-date-picker
              :picker-options="pickerOptions"
              v-model="applyDateRange"
              type="daterange"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              size="mini"
              style="font-size:12px"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"/>
          </el-form-item>
        </el-form>
      </el-card>
    </div>
    <div class="public-table">
      <vxe-grid
        ref="xTable"
        :data="productConsultList"
        :loading="loading"
        :tooltip-config="{theme: 'light'}"
        :columns="tableColumn"
        :toolbar-config="tableToolbar"
        :sort-config="{trigger: 'cell', defaultSort: {field: 'createTime', order: 'desc'}, orders: ['desc', 'asc']}"
        align="center"
        border
        resizable
        auto-resize
        highlight-hover-column
        highlight-current-row
        highlight-hover-row
        show-overflow
        class="mytable-scrollbar"
        size="mini"
        @sort-change="sortChange">
        <template #toolbar_buttons>
          <vxe-button size="mini" type="primary" @click="exportEvent2">导出</vxe-button>
        </template>
        <template v-slot:detailOperate="{ row }">
          <el-tag size="mini">
            <a style="color: blue" @click="detail(row)">{{ row.specialPriceNo }}</a>
          </el-tag>
          <!-- <i v-if="row.status === '生效中'" size="small" title="可用特价号" class="el-icon-star-on" style="color: #FFA500; cursor: pointer; font-size: 12px;"/> -->
        </template>
        <template v-slot:customer="{ row }">
          {{ '【' + row.customerNo + '】' + row.customerName }}
        </template>
        <template v-slot:customerBear="{ row }">
          {{ '【' + row.customerTakeOnId + '】' + row.customerTakeOnName }}
        </template>
        <template v-slot:approvalInformation="{ row }">
          <span v-if="row.approvalId">
            {{ '【' + row.approvalId + '】' + row.approvalName + ',' + row.positionName }}
          </span>
        </template>
        <template v-slot:operate="{ row }">
          <el-tooltip effect="light" content="详情" placement="top">
            <el-button type="primary" size="mini" icon="el-icon-document" circle @click="detail(row)"/>
          </el-tooltip>
          <el-tooltip :content="row.status === '生效中' ? (row.isCanChangeQuantityApproval ? '特价数量变更' : '当前特价变更正在审批') : row.status + '状态特价不允许申请特价变更'" effect="light" placement="top">
            <span>
              <el-button v-permission="['000010000001012']" v-if="row.specialPriceType === '长期特价'" :disabled="!row.isCanChangeQuantityApproval" type="primary" size="mini" icon="el-icon-star-off" circle @click="specialInfo(row)"/>
            </span>
          </el-tooltip>
          <el-tooltip v-permission="['000010000001011']" effect="light" content="编辑" placement="top">
            <el-button v-if="row.status === '编辑中' || row.status === '已驳回'" type="primary" size="mini" icon="el-icon-edit" @click="edit(row)"/>
          </el-tooltip>
          <el-tooltip effect="light" content="作废" placement="top">
            <el-button v-permission="['000010000001014']" v-if="row.status === '生效中'" type="primary" size="mini" icon="el-icon-s-release" circle @click="nullify(row)"/>
          </el-tooltip>
        </template>
      </vxe-grid>
      <vxe-pager
        :current-page="tablePage.currentPage"
        :page-size="tablePage.pageSize"
        :total="tablePage.totalResult"
        :layouts="['PrevPage', 'JumpNumber', 'NextPage', 'FullJump', 'Sizes', 'Total']"
        border
        size="mini"
        @page-change="handlePageChange"/>
    </div>
    <!-- <specialNoInfo :special-no-info-visible="specialNoInfoVisible" :special-no-info-data="specialNoInfoData" @closeSpecialNoInfo="closeSpecialNoInfoChild"/> -->
    <exportExcel ref="exportExcelVue"/>
  </div>
</template>

<script>
import { transformOptions, Constants } from '@/utils/optionUtils'
import { getDataByPid } from '@/api/dictionary'
import { findCustomerBearList } from '@/api/customer'
import { customerFilter } from '@/utils/saleUtils/customerInfo'
// import { specialNotApprovedColumn, specialApprovedColumn, findSpecialPriceList, nullify } from '@/api/requestPurchase'
// import { findAvailableSpecialPrice } from '@/api/saleManageSystem/specialPriceApply'
// import specialNoInfo from '@/views/saleManageSystem/components/specialNoInfo/index'
import { addMonth } from '@/utils/dateHandle'
import { parseTime, varIsUnable } from '@/utils'
import exportExcel from '@/components/ExportExcel/index'
import XLSX from 'xlsx'
export default {
  name: 'RequestPurchaseIndex',
  components: {
    // specialNoInfo,
    exportExcel
  },
  data() {
    return {
      tableToolbar: {
        // zoom: true,
        custom: true,
        slots: {
          buttons: 'toolbar_buttons'
        }
      },
      condition: {
        // modelNo: '',
        // specialApprovalNo: ''
      },
      customerList: [],
      moreVisible: false,
      // 可用特价号状态
      specialNoInfoVisible: false,
      // 可用特价号数据
      specialNoInfoData: [],
      tablePage: {
        currentPage: 1,
        totalResult: 1,
        pageSize: 20,
        pageSizes: [20, 50, 100, 200, 500]
      },
      principalList: [],
      userList: [],
      tableProxy: {},
      noDataText: '无数据',
      noDataRequire: '请至少输入三个字符',
      noDataTextDefault: '无数据',
      userId: this.$store.getters.info.userId,
      customerCodesOptions: [],
      userListloading: false,
      loading: false,
      applyDateRange: [],
      approvalStatusList: [],
      // specialPriceStatusList: [],
      specialPriceTypeList: [],
      productConsultList: [],
      tableColumn: [],
      specialNotApprovedColumn: [],
      specialApprovedColumn: [],
      // 第一次选中时间
      pickerMinDate: '',
      pickerOptions: {
        onPick: obj => {
          this.pickerMinDate = new Date(obj.minDate).getTime()
        },
        disabledDate: time => {
          if (this.pickerMinDate) {
            const day1 = 182 * 24 * 3600 * 1000
            const maxTime = (this.pickerMinDate + day1) > new Date() ? new Date() : (this.pickerMinDate + day1)
            const minTime = this.pickerMinDate - day1
            return time.getTime() > maxTime || time.getTime() < minTime
          } else {
            return time.getTime() > new Date()
          }
        }
      }
    }
  },
  watch: {
    '$route': {
      handler(newVal, oldVal) {
        const flag = newVal.query.flag
        if (newVal && newVal.name === 'SpecialPriceApplyIndex' && flag === '1') {
          this.getList()
        }
      },
      immediate: true
    }
  },
  mounted() {
    const format = '{y}-{m}-{d}'
    var halfYearAgo = parseTime(addMonth(new Date(), -6), format)
    this.applyDateRange = [halfYearAgo, parseTime(new Date(), format)]
    this.condition.property = 'createTime'
    this.condition.order = 'desc'
    this.getApprovalOptions()
    // this.tableColumn = specialNotApprovedColumn()
    // this.specialNotApprovedColumn = specialNotApprovedColumn()
    // this.specialApprovedColumn = specialApprovedColumn()
    this.getList()
  },
  created() {
  },
  methods: {
    sortChange({ column, property, order }) {
      console.log(property, order)
      if (order === null) {
        return
      }
      this.condition.property = property
      this.condition.order = order
      this.getList()
    },
    // 重置表单
    resetForm() {
      this.$refs.form.resetFields()
      this.applyDateRange = []
    },
    // 获取客户列表
    remoteFilter(val) {
      // if (!val || val.length < 3) {
      //   this.noDataText = this.noDataRequire
      //   return []
      // }
      // this.noDataText = this.noDataTextDefault
      this.userListloading = true
      const that = this
      val = !val ? '' : val.toUpperCase()
      this.condition.customerNo = val
      if (!val || val.length < 1) {
        that.noDataText = that.noDataRequire
        return
      }
      that.noDataText = that.noDataTextDefault
      const userId = that.$store.getters.info.userId
      customerFilter(userId, val, '0').then(data => {
        that.customerCodesOptions = data.filterData
        that.userListloading = false
      }).catch(() => {
        that.userListloading = false
      })
      // findCustomerByCstmNoOrName(this.userId, val, '0').then(data => {
      //   this.userListloading = false
      //   const customerCodesAllOptions = []
      //   for (const key in data) {
      //     customerCodesAllOptions[key] = {}
      //     customerCodesAllOptions[key].customerNo = data[key].customerNo
      //     customerCodesAllOptions[key].customerName = data[key].cstmName
      //   }
      //   this.customerCodesOptions = customerCodesAllOptions.filter((item) => {
      //     if (!!~item.customerNo.toUpperCase().indexOf(val.toUpperCase()) || !!~item.customerName.indexOf(val) || !!~item.customerName.toUpperCase().indexOf(val.toUpperCase())) {
      //       return true
      //     }
      //   })
      // })
    },
    // 下拉框列表
    getApprovalOptions() {
      var self = this
      // 特价状态
      getDataByPid(Constants.approvalStatusList).then(data => {
        self.approvalStatusList = transformOptions(data)
      })
      // getDataByPid(Constants.specialPriceStatusList).then(data => {
      //   self.specialPriceStatusList = transformOptions(data)
      // })
      // 特价类型
      getDataByPid(Constants.specialPriceTypeList).then(data => {
        self.specialPriceTypeList = transformOptions(data)
      })
    },
    // 客户下拉框改变，获取客户担当列表
    getUserInfo(e) {
      this.principalList = []
      if (varIsUnable(e)) {
        return
      }
      const params = { customerNo: e }
      findCustomerBearList(params).then(data => {
        data.content.forEach(element => {
          this.principalList.push({ psnsmcId: element.userId, psnName: element.userName })
        })
        this.condition.customerTakeOnId = this.principalList[0].psnsmcId
      }).catch(error => {
        console.log(error)
      })
    },
    // 页码改变事件
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getList()
    },
    // 初始化查询
    getList() {
      this.loading = true
      this.condition.createId = this.$store.getters.info.userId
      this.condition.pageNumber = this.tablePage.currentPage
      this.condition.pageSize = this.tablePage.pageSize
      if (this.applyDateRange && this.applyDateRange.length > 0) {
        this.condition.startDate = this.applyDateRange[0] + ' 0:0:0'
        this.condition.endDate = this.applyDateRange[1] + ' 23:59:59'
      } else {
        this.condition.startDate = null
        this.condition.endDate = null
      }
      // 操作人
      this.condition.operator = this.$store.getters.info.userId
      // const params = this.condition
      // findSpecialPriceList(params).then(data => {
      //   this.productConsultList = data.content.list
      //   this.tablePage.totalResult = data.content.total
      //   this.loading = false
      // }).catch(error => {
      //   this.smcErrorMsg(error)
      //   this.loading = false
      // })
    },
    exportEvent2() {
      const that = this
      // const tableColumn = [
      //   { field: 'specialPriceNo', title: '特价号' },
      //   { field: 'specialPriceType', title: '特价类型' },
      //   { field: 'customerNo', title: '客户编码' },
      //   { field: 'customerName', title: '客户名称' },
      //   { field: 'customerTakeOnId', title: '客户担当Id' },
      //   { field: 'customerTakeOnName', title: '客户担当姓名' },
      //   { field: 'status', title: '审批状态' },
      //   { field: 'approvalId', title: '审批人Id' },
      //   { field: 'approvalName', title: '审批人姓名' },
      //   { field: 'positionName', title: '审批人职位' },
      //   { field: 'createTime', title: '申请日期' }
      // ]
      // const tmp = that.tablePage.pageSize
      that.loading = true
      that.condition.pageSize = 10000
      that.condition.startDate = ''
      that.condition.endDate = ''
      // 操作人
      that.condition.operator = that.$store.getters.info.userId
      // const params = that.condition
      // findSpecialPriceList(params).then(data => {
      //   if (data.success) {
      //     that.$refs.exportExcelVue.initExportExcel(data.content.list, tableColumn)
      //     // var sheet = XLSX.utils.json_to_sheet(data.content.list)
      //     // this.openDownloadDialog(this.sheet2blob(sheet), '导出.xlsx')
      //     // that.tablePage.pageSize = tmp
      //     that.loading = false
      //   }
      // }).catch((error) => {
      //   // that.tablePage.pageSize = tmp
      //   that.loading = false
      //   console.log(error)
      // })
    },
    openDownloadDialog(url, saveName) {
      if (typeof url === 'object' && url instanceof Blob) {
        url = URL.createObjectURL(url) // 创建blob地址
      }
      var aLink = document.createElement('a')
      aLink.href = url
      aLink.download = saveName || '' // HTML5新增的属性，指定保存文件名，可以不要后缀，注意，file:///模式下不会生效
      var event
      if (window.MouseEvent) event = new MouseEvent('click')
      else {
        event = document.createEvent('MouseEvents')
        event.initMouseEvent('click', true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null)
      }
      aLink.dispatchEvent(event)
    },
    sheet2blob(sheet, sheetName) {
      sheetName = sheetName || 'sheet1'
      var workbook = {
        SheetNames: [sheetName],
        Sheets: {}
      }
      workbook.Sheets[sheetName] = sheet
      // 生成excel的配置项
      var wopts = {
        bookType: 'xlsx', // 要生成的文件类型
        bookSST: false, // 是否生成Shared String Table，官方解释是，如果开启生成速度会下降，但在低版本IOS设备上有更好的兼容性
        type: 'binary'
      }
      var wbout = XLSX.write(workbook, wopts)
      var blob = new Blob([s2ab(wbout)], { type: 'application/octet-stream' })
      // 字符串转ArrayBuffer
      function s2ab(s) {
        var buf = new ArrayBuffer(s.length)
        var view = new Uint8Array(buf)
        for (var i = 0; i !== s.length; ++i) view[i] = s.charCodeAt(i) & 0xFF
        return buf
      }
      return blob
    },
    // 新建
    add() {
      this.$router.push({
        path: '/specialPrice/apply/specialPriceApply',
        query: {
          specialPriceApplyType: 'fromSpecialPriceCreate'
        }})
    },
    // 编辑
    edit(row) {
      row.specialPriceApplyType = 'fromSpecialPriceEdit'
      this.$router.push({
        path: '/specialPrice/apply/specialPriceApply',
        query: row
      })
    },
    // 作废
    nullify(row) {
      this.$confirm('是否作废此条特价信息?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // nullify({ specialPriceNo: row.specialPriceNo }).then(data => {
        //   this.smcSuccessMsg('作废成功')
        //   this.getList()
        // }).catch(error => {
        //   this.smcErrorMsg(error)
        // })
      }).catch(() => {
      })
    },
    // 详情
    detail(row) {
      this.$router.push({
        path: '/specialPrice/apply/detail',
        query: { businessKey: row.specialPriceNo }
      })
    },
    // 数量变更申请
    specialInfo(row) {
      // this.specialNoInfoData = []
      // findAvailableSpecialPrice({ customerNo: row.customerNo }).then(data => {
      //   data.content.forEach(element => {
      //     this.specialNoInfoData.push(element)
      //   })
      // }).catch(error => {
      //   console.log(error)
      // })
      // this.specialNoInfoVisible = true
      this.$router.push({
        path: '/specialPrice/apply/quantityChangeApply',
        query: { businessKey: row.specialPriceNo }
      })
    },
    // 可用特价号关闭
    closeSpecialNoInfoChild() {
      this.specialNoInfoVisible = false
    }
  }
}
</script>
<style scoped lang="scss">
.el-divider--horizontal {
  margin: 5px 0;
}
.col-class {
  height: 20px;
}
.el-form-item__label {
  justify-content: space-between;
}
.el-button--mini.is-round {
  padding: 5px 5px;
}
.el-button--medium.is-round {
  padding: 8px 8px;
}
.stock-content-body {
  margin-bottom: 100px;
}
a:link {color: black} /* 未被访问bai的链接 黑色 */
a:visited {color: blue; text-decoration: underline} /* 已被访问过的链接 蓝色下划线 */
a:hover {color: blue; text-decoration: underline} /* 鼠标悬浮在上的链接 蓝色下划线 */
a:active {color: blue} /* 鼠标点中激活链接 蓝色 */
</style>
