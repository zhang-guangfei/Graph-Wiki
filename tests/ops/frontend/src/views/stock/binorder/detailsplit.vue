<template>
  <div class="app-container">
    <div>
      <el-form ref="form" :inline="true" :model="form" size="small" label-width="100px">
        <el-form-item>
          <el-input v-model="form.appId" size="mini" style="width: 90px" placeholder="申请号" clearable />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.modelNo" size="mini" style="width: 180px" placeholder="型号" clearable />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.orderNo" size="mini" style="width: 160px" placeholder="订单号" clearable />
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.orderType" placeholder="订单类型" style="width:125px" size="mini">
            <el-option
              v-for="item in orderTypeData"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <!-- <el-form-item>
          <el-input v-model="form.supplierCode" size="mini" style="width: 160px" placeholder="供应商" />
        </el-form-item> -->
        <el-form-item>
          <el-input v-model="form.supplierCode" size="mini" style="width: 80px" placeholder="供应商" clearable />
          <el-select v-model="form.selectsupplierCode" placeholder="供应商" clearable style="width: 130px" @change="changeSupplierCode">
            <el-option v-for="item in supplierCodeOptions" :key="item.id" :label="item.name" :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['732139']" icon="el-icon-search" title="搜索" size="mini" @click="initData">查询</el-button>
        </el-form-item>
        <el-form-item style="float:right">
          <el-button v-permission="['362393']" v-loading="exportLoading" type="primary" icon="el-icon-upload2" size="mini" @click="exportBinDetailSplit">导出</el-button>
        </el-form-item>
      </el-form>
      <!--申请表-->
      <el-table
        ref="multipleTable"
        :data="appData"
        :cell-style="{ padding: '5px' }"
        size="mini"
        border
        stripe
        style="width: 100%;margin-top: 5px"
        height="60vh"
      >
        <!-- 表头字段 -->
        <!-- <el-table-column type="selection" width="55" /> -->

        <!-- <el-table-column label="申请号">
          <template slot-scope="scope">
            <el-link type="primary" @click="querycalculate(scope.row)">{{ scope.row.appId }}</el-link>
          </template>
        </el-table-column> -->
        <el-table-column
          v-for="column in applyTableHeader"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
        />
      </el-table>

      <!-- 分页工具 -->
      <pagination
        v-show="form.total > 0"
        :total="form.total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="form.pageNum"
        :limit.sync="form.pageSize"
        @pagination="initData"
      />
    </div>
  </div>
</template>

<script>
import { getDictDataByPid } from '@/api/common/dict'
import { listBinDetailSplit, exportBinDetailSplit } from '@/api/stock/binorder'
import { findSupplier } from '@/api/warehouseManage'
import Pagination from '@/components/Pagination'
import { codeFormatter } from '@/api/common/comm'
import { getTransIds } from '@/api/purchaseOrder'

export default {
  components: { Pagination },
  data() {
    return {
      transTypeData: [],
      orderTypeData: [],
      appData: [],
      statusdesc: [],
      exportLoading: false,
      form: {
        appId: '',
        modelNo: '',
        orderNo: '',
        orderType: '',
        supplierCode: '',
        selectsupplierCode: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      supplierCodeOptions: [],
      // 申请表头
      applyTableHeader: [
        {
          label: '申请号',
          prop: 'appId'
        },
        {
          label: '订单号',
          prop: 'orderNo'
        },
        {
          label: '项号',
          prop: 'itemNo'
        },
        {
          label: '状态',
          prop: 'status',
          formatter: this.splitstatusformatter
        },
        {
          label: '订单类型',
          prop: 'orderType'
        },
        {
          label: '型号',
          prop: 'modelNo'
        },
        {
          label: '数量',
          prop: 'confirmQty'
        },
        {
          label: '供应商',
          prop: 'supplierCode'
        },
        {
          label: '交货期',
          prop: 'dlvDate'
        },
        {
          label: '运输方式',
          prop: 'transType',
          formatter: this.transTypeFormatter
        },
        {
          label: '更新时间',
          prop: 'updateTime'
        }
      ]
    }
  },
  created() {
    this.initData()
    this.findSupplierData()
    this.initsdesc()
  },

  methods: {
    initsdesc() {
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
            this.transTypeData.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      getDictDataByPid('2029').then(result => {
        this.orderTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2028').then(result => {
        this.statusdesc = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    changeSupplierCode() {
      this.form.supplierCode = this.form.selectsupplierCode
      this.initData()
    },
    initData() {
      listBinDetailSplit(this.form).then(result => {
        this.appData = result.content.list
        this.form.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    exportBinDetailSplit() {
      this.exportLoading = true
      const expdate = this.dayjs(new Date()).format('YYYY-MM')
      const fileName = expdate + '.xlsx'
      exportBinDetailSplit(this.form).then(res => {
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
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
    findSupplierData() {
      findSupplier().then(res => {
        this.supplierCodeOptions = res.content
      })
    },
    transTypeFormatter(row) {
      if (row.transType != null) {
        return codeFormatter(this.transTypeData, row.transType)
      }
    },
    splitstatusformatter(row) {
      return codeFormatter(this.statusdesc, row.status)
    },
    orderTypeFormatter(row) {
      if (row.orderType != null) {
        if (row.orderType === '9') {
          return '拆分单'
        }
        return codeFormatter(this.orderTypeData, row.orderType)
      }
    }

  }
}
</script>
