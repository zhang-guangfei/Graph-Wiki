<template>
  <div>
    <el-form ref="queryForm" :inline="true" :model="queryForm" size="small" label-width="100px">
      <!--<el-form-item >
        <Warehouse @handleScopeChange="handleScopeChange" />
      </el-form-item> -->
      <el-form-item label-width="150px" label="任务编号" size="mini">
        <el-input v-model="queryForm.jobNo" size="mini" style="width: 170px" clearable placeholder="任务编号" />
      </el-form-item>
      <el-form-item size="mini">
        <el-select v-model="queryForm.warehouseCode" clearable placeholder="请选择仓库">
          <el-option
            v-for="item in warehouseData"
            :key="item.warehouseCode"
            :label="item.warehouseName"
            :value="item.warehouseCode"/>
        </el-select>
      </el-form-item>
      <el-form-item label="型号" size="mini">
        <el-input v-model="queryForm.modelNo" :maxlength="200" type="textarea" size="mini" style="width:200px" clearable placeholder="换行多项查询" show-word-limit/>
      </el-form-item>
      <el-form-item prop="modelType">
        <el-select
          v-model="queryForm.modelType"
          placeholder="请选择型号类型"
          clearable
          style="width:110px"
          size="mini"
        >
          <el-option
            v-for="item in modelTypeOptions"
            :key="item.code"
            :value="item.code"
            :label="item.codeName"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button v-permission="['966764']" type="primary" icon="el-icon-search" size="mini" @click="initData">查询</el-button>
        <el-button v-permission="['966764']" v-loading="exportLoading" type="primary" icon="el-icon-upload2" size="mini" @click="exporBinTrialSalesBranchDetail">导出</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="detailLoading"
      ref="multipleTable"
      :data="detailData"
      :cell-style="{ padding: '5px' }"
      :header-cell-style="{'text-align':'center',background:'rgb(117, 144, 168)',color:'#FFFFFF',fontSize:'12px' }"
      border
      stripe
      size="mini"
      style="width: 100%;font-size:10px"
      height="60vh"
    >
      <!-- 表头字段 -->
      <!-- <el-table-column type="selection" width="55" /> -->

      <el-table-column fixed prop="modelNo" label="型号" width="150" show-overflow-tooltip/>
      <el-table-column fixed prop="warehouseCode" label="仓库"/>
      <el-table-column
        :formatter="modelTypeFormatter"
        fixed
        prop="modelType"
        label="型号类别"/>
      <el-table-column
        fixed
        prop="classCode"
        label="最新级别"/>
      <el-table-column
        fixed
        prop="modelClass"
        label="型号分类"/>
      <!-- <el-table-column
        fixed
        prop="productType"
        label="产品类别"/> -->
      <el-table-column :formatter="designTypeFormatter" prop="designType" label="产品类别"/>
      <el-table-column :formatter="productTypeFormatter" prop="productType" label="产品区分"/>
      <el-table-column prop="lastOrdMonth" label="最后下单月份"/>
      <el-table-column prop="productSeries" label="产品系列"/>
      <el-table-column prop="mainOrigin" label="供应商"/>
      <el-table-column prop="secondOrigin" label="原产地"/>
      <el-table-column prop="eprice" label="E价"/>
      <el-table-column prop="ecode" label="ECode"/>
      <el-table-column prop="setMean" label="设定平均"/>
      <el-table-column prop="setClassCode" label="设定级别"/>
      <el-table-column prop="variation" label="变动系数"/>
      <el-table-column :formatter="moveRate1Formatter" prop="moveRate1" label="移动平均变量1"/>
      <el-table-column :formatter="moveRate2Formatter" prop="moveRate2" label="移动平均变量2"/>
      <el-table-column :formatter="moveRate3Formatter" prop="moveRate3" label="移动平均变量3"/>
      <el-table-column prop="setAvgQty" label="最新月均"/>
      <el-table-column align="center" label="最近8个月">
        <el-table-column
          prop="monthsOf8"
          label="下单月数"/>
        <el-table-column
          prop="customersOf8"
          label="客户数"/>
        <el-table-column
          prop="qtyOf8"
          label="下单数量"/>
        <el-table-column
          prop="avgQtyOf8"
          label="月平均数量"/>
        <el-table-column
          prop="maxCustomerOf8"
          label="最多下单客户"/>
        <el-table-column :formatter="maxCustomerDeptOf8formatter" prop="maxCustomerDeptOf8" label="营业所"/>
        <el-table-column
          :formatter="maxCustomerRateOf8Formatter"
          prop="maxCustomerRateOf8"
          label="最多客户的比例"/>
        <el-table-column prop="maxCustomerQtyOf8" label="最多下单客户数量"/>
        <el-table-column prop="ordersOf8" label="订单项数"/>
      </el-table-column>
      <el-table-column align="center" label="最近12个月">
        <el-table-column
          prop="monthsOf12"
          label="下单月数"/>
        <el-table-column
          prop="customersOf12"
          label="客户数"/>
        <el-table-column
          prop="qtyOf12"
          label="下单数量"/>
        <el-table-column
          prop="avgQtyOf12"
          label="月平均数量"/>
        <el-table-column
          prop="maxCustomerOf12"
          label="最多下单客户"/>
        <el-table-column :formatter="maxCustomerDeptOf12formatter" prop="maxCustomerDeptOf12" label="营业所"/>
        <el-table-column
          :formatter="maxCustomerRateOf12Formatter"
          prop="maxCustomerRateOf12"
          label="最多客户的比例"/>
        <el-table-column
          prop="maxCustomerQtyOf12"
          label="最多下单客户数量"/>
        <el-table-column prop="ordersOf12" label="订单项数"/>
      </el-table-column>
      <el-table-column align="center" label="最近24个月">
        <el-table-column
          prop="monthsOf24"
          label="下单月数"/>
        <el-table-column
          prop="customersOf24"
          label="客户数"/>
        <el-table-column
          prop="qtyOf24"
          label="下单数量"/>
        <el-table-column
          prop="avgQtyOf24"
          label="月平均数量"/>
        <el-table-column
          prop="maxCustomerOf24"
          label="最多下单客户"/>
        <el-table-column :formatter="maxCustomerDeptOf24formatter" prop="maxCustomerDeptOf24" label="营业所"/>
        <el-table-column
          :formatter="maxCustomerRateOf24Formatter"
          prop="maxCustomerRateOf24"
          label="最多客户的比例"/>
        <el-table-column
          prop="maxCustomerQtyOf24"
          label="最多下单客户数量"/>
        <el-table-column prop="ordersOf24" label="订单项数"/>
      </el-table-column>
      <el-table-column align="center" label="最近36个月">
        <el-table-column
          prop="monthsOf36"
          label="下单月数"/>
        <el-table-column
          prop="customersOf36"
          label="客户数"/>
        <el-table-column
          prop="qtyOf36"
          label="下单数量"/>
        <el-table-column
          prop="avgQtyOf36"
          label="月平均数量"/>
        <el-table-column
          prop="maxCustomerOf36"
          label="最多下单客户"/>
        <el-table-column :formatter="maxCustomerDeptOf36formatter" prop="maxCustomerDeptOf36" label="营业所"/>
        <el-table-column
          :formatter="maxCustomerRateOf36Formatter"
          prop="maxCustomerRateOf36"
          label="最多客户的比例"/>
        <el-table-column
          prop="maxCustomerQtyOf36"
          label="最多下单客户数量"/>
        <el-table-column prop="ordersOf36" label="订单项数"/>
      </el-table-column>
      <el-table-column prop="m1" label="M1"/>
      <el-table-column prop="m2" label="M2"/>
      <el-table-column prop="m3" label="M3"/>
      <el-table-column prop="m4" label="M4"/>
      <el-table-column prop="m5" label="M5"/>
      <el-table-column prop="m6" label="M6"/>
      <el-table-column prop="m7" label="M7"/>
      <el-table-column prop="m8" label="M8"/>
      <el-table-column prop="m9" label="M9"/>
      <el-table-column prop="m10" label="M10"/>
      <el-table-column prop="m11" label="M11"/>
      <el-table-column prop="m12" label="M12"/>
      <el-table-column prop="m13" label="M13"/>
      <el-table-column prop="m14" label="M14"/>
      <el-table-column prop="m15" label="M15"/>
      <el-table-column prop="m16" label="M16"/>
      <el-table-column prop="m17" label="M17"/>
      <el-table-column prop="m18" label="M18"/>
      <el-table-column prop="m19" label="M19"/>
      <el-table-column prop="m20" label="M20"/>
      <el-table-column prop="m21" label="M21"/>
      <el-table-column prop="m22" label="M22"/>
      <el-table-column prop="m23" label="M23"/>
      <el-table-column prop="m24" label="M24"/>
      <el-table-column prop="m25" label="M25"/>
      <el-table-column prop="m26" label="M26"/>
      <el-table-column prop="m27" label="M27"/>
      <el-table-column prop="m28" label="M28"/>
      <el-table-column prop="m29" label="M29"/>
      <el-table-column prop="m30" label="M30"/>
      <el-table-column prop="m31" label="M31"/>
      <el-table-column prop="m32" label="M32"/>
      <el-table-column prop="m33" label="M33"/>
      <el-table-column prop="m34" label="M34"/>
      <el-table-column prop="m35" label="M35"/>
      <el-table-column prop="m36" label="M36"/>
      <!-- <el-table-column
        v-for="column in applyTableHeader"
        :key="column.prop"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
        :formatter="column.formatter"
      /> -->
    </el-table>

    <pagination
      v-show="queryForm.total>0"
      :total="queryForm.total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="queryForm.pageNum"
      :limit.sync="queryForm.pageSize"
      @pagination="initData" />

    <!--dialog 点击行-->
    <el-dialog :visible.sync="dialogDetialVisible" title="销售明细" width="630px">
      <el-table v-loading="listDetailLoading" :data="modelData" :row-style="{height: '0'}" border mini style="width: 100%;font-size:10px">
        <el-table-column property="warehouseCode" align="center" label="仓库"/>
        <el-table-column property="modelNo" align="center" label="型号" width="180"/>
        <el-table-column property="monthDate" align="center" label="月份" />
        <el-table-column property="cstmQty" align="center" label="客户数" />
        <el-table-column property="qty" align="center" label="数量" />
        <el-table-column property="orderNumber" align="center" label="下单数" />
      </el-table>
    </el-dialog>

  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import Warehouse from '@/components/Warehouse'
import { codeFormatter, codeAndFormatter } from '@/api/common/comm'
import { listWarehouseNoWT, getDictDataByPid } from '@/api/common/dict'
import { findDepartments } from '@/api/common/department'
import { listBinTrialSalesBranchDetail, exporBinTrialSalesBranchDetail, listModeldetailByJob } from '@/api/stock/binorder'

export default {
  components: { Pagination, Warehouse },
  data() {
    return {
      modelTypeOptions: [{
        code: '1',
        codeName: '基础型号'
      },
      {
        code: '2',
        codeName: '订货型号'
      }],
      queryForm: {
        jobNo: '',
        warehouseCode: '',
        modelNo: '',
        modelType: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      detailLoading: false,
      exportLoading: false,
      dialogDetialVisible: false,
      listDetailLoading: false,
      detailData: [],
      modelData: [],
      mainData: [],
      stockTypeData: [],
      designTypeData: [],
      productTypeData: [],
      modelNos: [],
      departs: [],
      warehouseData: []
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal && newVal.name === 'BinCalculate' && this.$route.query.active === 'detailPage') {
          this.queryForm.jobNo = this.$route.query.jobNo
          this.queryForm.warehouseCode = ''
          this.queryForm.modelNo = ''
          this.queryForm.modelType = ''
          this.initData()
        }
      }
    }
  },
  mounted() {

  },
  created() {
    this.initDictData()
    this.getWareHouseListData()
  },
  methods: {
    showDetail(row) {
      const data = {
        modelNo: row.modelNo,
        stockCode: row.warehouseCode
      }
      this.dialogDetialVisible = true
      this.listDetailLoading = true
      listModeldetailByJob(data).then(result => {
        this.modelData = result.content
        this.listDetailLoading = false
      }).catch(error => {
        this.listDetailLoading = false
        console.log(error)
      })
    },
    initData() {
      this.detailData = []
      this.detailLoading = true
      const data = this._getQueryParam()
      listBinTrialSalesBranchDetail(data).then(res => {
        if (res.success) {
          this.detailData = res.content.list
          this.queryForm.total = res.content.total
          this.detailLoading = false
        } else {
          this.$message.error(res.message)
          this.detailLoading = false
        }
      }).catch(error => {
        this.$message.error(error)
        this.detailLoading = false
      })
    },
    exporBinTrialSalesBranchDetail() {
      this.exportLoading = true
      const expdate = 'BinTrialSalesBranchDetail' + this.dayjs(new Date()).format('YYYYMMDDHH')
      this.fileName = expdate + '.zip'
      const data = this._getQueryParam()
      exporBinTrialSalesBranchDetail(data).then(res => {
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = this.fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportLoading = false
      }).catch(error => {
        console.error(error)
        this.exportLoading = false
      })
    },
    _getQueryParam() {
      this.modelNos = []
      if (this.queryForm.modelNo !== null && this.queryForm.modelNo !== undefined && this.queryForm.modelNo.length > 0) {
        const model = this.queryForm.modelNo.split('\n')
        for (const val in model) {
          if (model[val].length > 0) {
            this.modelNos.push(model[val])
          }
        }
      }
      const data = {
        jobNo: this.queryForm.jobNo,
        warehouseCode: this.queryForm.warehouseCode,
        modelNos: this.modelNos,
        modelType: this.queryForm.modelType,
        pageNum: this.queryForm.pageNum,
        pageSize: this.queryForm.pageSize,
        total: this.queryForm.total
      }
      return data
    },
    getWareHouseListData() {
      this.warehouseData = []
      this.warehouseMaster = []
      listWarehouseNoWT().then(res => {
        if (res.success) {
          this.warehouseData = res.content
        } else {
          this.warehouseData = []
        }
      }).catch(error => {
        console.error(error)
        this.warehouseData = []
      })
    },
    handleScopeChange(val) {
      // console.log('所选物流中心 => ', val)
      this.queryForm.warehouseCode = val
    },
    initDictData() {
      getDictDataByPid('2042').then(result => {
        this.stockTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2063').then(result => {
        this.designTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2062').then(result => {
        this.productTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      findDepartments().then(result => {
        this.departs = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    stockTypeFormatter(row) {
      return codeFormatter(this.stockTypeData, row.stockType)
    },
    modelTypeFormatter(row) {
      return codeFormatter(this.modelTypeOptions, row.modelType)
    },
    designTypeFormatter(row) {
      return codeFormatter(this.designTypeData, row.designType)
    },
    productTypeFormatter(row) {
      return codeAndFormatter(this.productTypeData, row.productType)
    },
    moveRate1Formatter(row) {
      if (row.moveRate1 !== null && row.moveRate1 !== '') {
        return row.moveRate1.toFixed(1)
      } else {
        return ''
      }
    },
    moveRate2Formatter(row) {
      if (row.moveRate2 !== null && row.moveRate2 !== '') {
        return row.moveRate2.toFixed(1)
      } else {
        return ''
      }
    },
    moveRate3Formatter(row) {
      if (row.moveRate3 !== null && row.moveRate3 !== '') {
        return row.moveRate3.toFixed(1)
      } else {
        return ''
      }
    },
    maxCustomerRateOf8Formatter(row) {
      if (row.maxCustomerRateOf8 !== null && row.maxCustomerRateOf8 !== '') {
        return (parseFloat(row.maxCustomerRateOf8) * 100).toFixed(0) + '%'
      } else {
        return ''
      }
    },
    maxCustomerRateOf12Formatter(row) {
      if (row.maxCustomerRateOf12 !== null && row.maxCustomerRateOf12 !== '') {
        return (parseFloat(row.maxCustomerRateOf12) * 100).toFixed(0) + '%'
      } else {
        return ''
      }
    },
    maxCustomerRateOf36Formatter(row) {
      if (row.maxCustomerRateOf36 !== null && row.maxCustomerRateOf36 !== '') {
        return (parseFloat(row.maxCustomerRateOf36) * 100).toFixed(0) + '%'
      } else {
        return ''
      }
    },
    maxCustomerRateOf24Formatter(row) {
      if (row.maxCustomerRateOf24 !== null && row.maxCustomerRateOf24 !== '') {
        return (parseFloat(row.maxCustomerRateOf24) * 100).toFixed(0) + '%'
      } else {
        return ''
      }
    },
    maxCustomerDeptOf8formatter: function(row) {
      return codeFormatter(this.departs, row.maxCustomerDeptOf8)
    },
    maxCustomerDeptOf12formatter: function(row) {
      return codeFormatter(this.departs, row.maxCustomerDeptOf12)
    },
    maxCustomerDeptOf24formatter: function(row) {
      return codeFormatter(this.departs, row.maxCustomerDeptOf24)
    },
    maxCustomerDeptOf36formatter: function(row) {
      return codeFormatter(this.departs, row.maxCustomerDeptOf36)
    }

  }
}
</script>
