<template>
  <div class="app-container" style="margin-top: 10px">
    <i class="el-icon-coin" style="color: #FFA500;" />
    <span style="font-weight: 800; font-size: 13px;">&nbsp;&nbsp;主表数据</span>
    <div>
      <el-row>
        <el-form ref="queryForm" :inline="true" :model="queryForm" size="mini">
          <el-form-item label="发票号">
            <el-input
              v-model="queryForm.invoiceNo"
              style="width: 120px"
              clearable
              placeholder="发票号"
            />
          </el-form-item>
          <el-form-item label="状态">
            <el-select
              v-model="queryForm.status"
              placeholder="请选择"
              style="width: 120px"
              clearable
              size="mini"
            >
              <el-option
                v-for="item in statusCodes"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="供应商">
            <el-input v-model="queryForm.supplierCode" clearable placeholder="供应商" style="width: 90px"/>
          </el-form-item>
          <el-form-item>
            <el-select v-model="queryForm.supplierCode" placeholder="供应商" clearable style="width: 160px" @change="searchData">
              <el-option v-for="item in suppliers" :key="item.id" :label="item.name" :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <!-- <span>结算日期</span> -->
            <el-select
              v-model="queryForm.qryDateType"
              placeholder="请选择"
              style="width:120px"
              size="mini"
            >
              <el-option
                v-for="item in dateTypeOptions"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
            <el-date-picker
              v-model="queryForm.fromDate"
              type="date"
              size="mini"
              placeholder="开始日期"
              value-format="yyyy-MM-dd"
              style="width: 130px"
            />
            <el-date-picker
              v-model="queryForm.toDate"
              type="date"
              size="mini"
              placeholder="结束日期"
              value-format="yyyy-MM-dd"
              style="width: 130px"
            />
          </el-form-item>
          <el-form-item label="收货仓库">
            <el-select v-model="queryForm.receiveWarehouseCode" placeholder="请选择" filterable clearable >
              <el-option
                v-for="(item,index) in inventoryNoWTList"
                :key="index"
                :label="item.warehouseName"
                :value="item.warehouseCode">
                <span style="float: left">{{ item.warehouseName }}</span>
                <span style="float: right; color: #8492a6; font-size: 13px">{{ item.warehouseCode }}</span>
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label-width="100px" size="mini">
            <el-select
              v-model="queryForm.invoiceType"
              placeholder="发票类型"
              style="width: 100px"
              clearable
              size="mini"
            >
              <el-option
                v-for="item in invoiceTypedesc"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button v-permission="['636350']" type="primary" icon="el-icon-search" size="mini" @click="searchData">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button v-permission="['684142']" type="primary" icon="el-icon-search" size="mini" @click="exportData">导出</el-button>
          </el-form-item>
          <!-- <el-form-item style="float:right">
            <el-date-picker
              v-model="queryForm.costDate"
              type="date"
              size="mini"
              placeholder="结算日期"
              value-format="yyyy-MM-dd"
              style="width: 130px"
            />
            <el-button type="primary" size="mini" @click="ExportDataToCost">成本结算</el-button>
          </el-form-item> -->
        </el-form>
      </el-row>
    </div>
    <div>
      <el-table
        ref="multipleTable"
        :data="listdata"
        :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
        :row-style="{height:'33px'}"
        highlight-current-row
        border
        stripe
        size="mini"
        style="width: 100%"
        height="18vh"
        @selection-change="handleSelection"
      >
        <!-- 表头字段 -->
        <!-- <el-table-column fixed type="selection" width="55" /> -->
        <!-- <el-table-column fixed prop="id" label="ID" /> -->
        <el-table-column prop="invoiceId" label="发票ID" />
        <el-table-column prop="invoiceNo" label="发票号" />
        <!-- <el-table-column prop="amount" label="原发票金额" /> -->
        <el-table-column prop="currencyCode" label="币种" />
        <!-- <el-table-column prop="exchangeRate" label="汇率" /> -->
        <!-- <el-table-column prop="amountRmb" label="原发票金额rmb" /> -->
        <!-- <el-table-column prop="customsFee" label="关税" />
        <el-table-column prop="excisetax" label="消费税" />
        <el-table-column prop="transFee" label="运保费">
          <template slot-scope="scope">
            <span v-if="scope.row.isEditShow">
              <el-input v-model="scope.row.transFee" size="mini" placeholder="运输费" />
            </span>
            <span v-else>{{ scope.row.transFee }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="amounttotal" label="总金额" />
        <el-table-column prop="vatFee" label="增值税" /> -->
        <el-table-column prop="supplierCode" label="供应商" show-overflow-tooltip/>
        <el-table-column :formatter="invoiceTypeformatter" prop="invoiceType" label="发票类型" show-overflow-tooltip/>
        <el-table-column prop="receiveWarehouseCode" label="收货仓库" show-overflow-tooltip/>
        <el-table-column prop="impDate" label="入库日期" show-overflow-tooltip/>
        <!-- <el-table-column prop="shipDate" label="发出日期" show-overflow-tooltip/> -->
        <!-- <el-table-column prop="otherFee" label="其他费用">
          <template slot-scope="scope">
            <span v-if="scope.row.isEditShow">
              <el-input v-model="scope.row.otherFee" size="mini" placeholder="其他费用" />
            </span>
            <span v-else>{{ scope.row.otherFee }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="amountadjust" label="原金额调整金额" /> -->
        <el-table-column :formatter="statusCodeNameformatter" prop="status" label="状态" />
        <el-table-column prop="shipDate" label="发货日期" show-overflow-tooltip/>
        <el-table-column :formatter="receiveTimeformatter" prop="receiveTime" label="物流签收日期" show-overflow-tooltip/>
        <el-table-column prop="invoiceDate" label="发票日期" show-overflow-tooltip/>
        <el-table-column prop="customsDate" label="通关日期" show-overflow-tooltip/>
        <el-table-column prop="costTime" label="成本结算日期" show-overflow-tooltip/>
        <el-table-column prop="createTime" label="数据导入时间" show-overflow-tooltip/>
        <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip/>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              v-permission="['636350']"
              type="success"
              size="mini"
              title="查看明细"
              icon="el-icon-view"
              @click="selShowDetail(scope.$index, scope.row)"
            />
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="queryForm.total > 0"
        :total="queryForm.total"
        :page.sync="queryForm.pageNum"
        :limit.sync="queryForm.pageSize"
        @pagination="searchData"
      />
    </div>
    <i class="el-icon-coin" style="color: #FFA500;" />
    <span style="font-weight: 800; font-size: 13px;">&nbsp;&nbsp;入库数据</span>
    <div>
      <el-form ref="form" :inline="true" :model="form" size="small" label-width="100px">
        <el-form-item>
          <el-input v-model="form.invoiceNo" size="mini" style="width: 100px" placeholder="发票号" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.orderNo" size="mini" style="width: 120px" placeholder="订单号" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.modelNo" size="mini" style="width: 130px" placeholder="型号" />
        </el-form-item>
        <el-form-item>
          <!-- <el-input v-model="form.supplierCode" size="mini" style="width: 90px" placeholder="供应商" /> -->
          <el-select v-model="form.supplierCode" placeholder="供应商" size="mini" clearable @change="initData">
            <el-option
              v-for="item in suppliers"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <span>入库日期</span>
          <el-date-picker
            v-model="form.fromdate"
            type="date"
            size="mini"
            placeholder="开始时间"
            value-format="yyyy-MM-dd"
            style="width: 130px"
          />
          <el-date-picker
            v-model="form.todate"
            type="date"
            size="mini"
            placeholder="结束时间"
            value-format="yyyy-MM-dd"
            style="width: 130px"
          />
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.optCode" placeholder="请选择" style="width:125px" size="mini" clearable @change="initData">
            <el-option
              v-for="item in statusdesc"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['636350']" icon="el-icon-search" title="搜索" size="mini" @click="initData">查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['636350']" icon="el-icon-search" title="导出" size="mini" @click="exportExcel">导出</el-button>
        </el-form-item>
        <el-form-item>
          <el-button
            v-permission="['684142']"
            type="primary"
            icon="el-icon-plus"
            size="mini"
            @click="confirmPOInvoice">
          <span v-loading="confirming" element-loading-text="发送入库数据.." element-loading-spinner="el-icon-loading">{{ "发送入库数据" }}</span></el-button>
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
        style="width: 100%"
        height="60vh"
        @selection-change="handleSelection"
      >
        <!-- 表头字段 -->
        <el-table-column type="selection" width="55" />
        <el-table-column
          v-for="column in applyTableHeader"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
          show-overflow-tooltip
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
    <exportExcel ref="exportExcelVue"/>
  </div>
</template>

<script>
import { listNoImpInvoiceDetailPack, listNoImpInvoiceDetailPackExcel, handimpInvoiceDetailPack, listPoinvoice, exportPoinvoice } from '@/api/invoicedata'
import { findSupplier } from '@/api/warehouseManage'
import { getDictDataByPid, listWarehouseNoWT } from '@/api/common/dict'
import { codeFormatter } from '@/api/common/comm'
import Pagination from '@/components/Pagination'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'ImpdataQuery',
  components: { Pagination, exportExcel },
  data() {
    return {
      inventoryNoWTList: [],
      invoiceTypedesc: [],
      appData: [],
      suppliers: [],
      statusdesc: [],
      listdata: [],
      statusCodes: [],
      queryForm: {
        invoiceNo: '',
        status: '1',
        supplierCode: '',
        qryDateType: 1,
        costDate: '',
        fromDate: '',
        toDate: '',
        pageNum: 0,
        pageSize: 5,
        total: 0
      },
      dateTypeOptions: [{
        code: 1,
        codeName: '入库日期'
      },
      {
        code: 2,
        codeName: '成本结算日期'
      },
      {
        code: 3,
        codeName: '物流签收日期'
      }],
      listLoading: false,
      confirming: false,
      multipleSelection: [],
      form: {
        invoiceNo: '',
        orderNo: '',
        modelNo: '',
        supplierCode: '',
        fromdate: '',
        todate: '',
        optCode: '2',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      statusOptions: [{
        id: 1,
        name: '未处理'
      },
      {
        id: 2,
        name: '入库中'
      },
      {
        id: 6,
        name: '已入库'
      }],
      // 申请表头
      applyTableHeader: [
        {
          label: '发票id',
          prop: 'invoiceId'
        },
        {
          label: '发票号',
          prop: 'invoiceNo'
        },
        // {
        //   label: '客户代码',
        //   prop: 'customerNo'
        // },
        {
          label: '供应商代码',
          prop: 'supplierCode'
        },
        {
          label: '状态',
          prop: 'status',
          formatter: this.statusformatter
        },
        {
          label: '入库异常信息',
          prop: 'expmsg'
        },
        // {
        //   label: '入库类别',
        //   prop: 'imptype'
        // },
        {
          label: '订单号',
          prop: 'fullOrderNo',
          width: '110'
        },
        {
          label: '型号',
          prop: 'modelNo',
          width: '120'
        },
        {
          label: '数量',
          prop: 'quantity'
        },
        {
          label: '箱号',
          prop: 'caseNo'
        },
        {
          label: '条码',
          prop: 'barcode',
          width: '110'
        },
        {
          label: '产地',
          prop: 'origin'
        },
        {
          label: '订单类别',
          prop: 'orderType'
        },
        {
          label: '备注',
          prop: 'remark'
        },
        {
          label: '创建人',
          prop: 'createUser'
        },
        {
          label: '创建日期',
          prop: 'createTime',
          formatter: this.createTimeformatter
        },
        {
          label: '更新人',
          prop: 'updateUser'
        },
        {
          label: '更新日期',
          prop: 'updateTime',
          formatter: this.updateTimeformatter
        }
      ]
    }
  },
  created() {
    this.initData()
    this.initsupplierCode()
    this.initDictData()
    this.searchData()
  },

  methods: {
    initDictData() {
      getDictDataByPid('2045').then(result => {
        this.statusdesc = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2043').then(result => {
        this.statusCodes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2064').then(result => {
        this.invoiceTypedesc = result.content
      }).catch(error => {
        console.log(error)
      })
      listWarehouseNoWT().then(res => {
        if (res.success) {
          this.inventoryNoWTList = res.content
        }
      }).catch(error => {
        error = '仓库检索条件，返回数据格式有误！'
        this.smcErrorMsg(error)
      })
    },
    initsupplierCode() {
      findSupplier().then(result => {
        this.suppliers = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    searchData() {
      listPoinvoice(this.queryForm).then(result => {
        this.listdata = result.content.list
        this.queryForm.total = result.content.total
      }).catch(error => {
        console.log(error)
      })
    },
    exportData() {
      exportPoinvoice(this.queryForm).then(data => {
        console.log(data)
        var fileName = 'OpsPoInvoice.xls'
        // const content = data
        const blob = new Blob([data], { type: 'application/octet-stream' })
        if ('download' in document.createElement('a')) { // 非IE下载
          const link = document.createElement('a')
          link.download = fileName
          link.style.display = 'none'
          link.href = URL.createObjectURL(blob)
          document.body.appendChild(link)
          link.click()
          URL.revokeObjectURL(link.href) // 释放URL 对象
          document.body.removeChild(link)
        } else { // IE10+下载
          navigator.msSaveBlob(blob, fileName)
        }
      })
    },
    selShowDetail(index, row) {
      if (row.id) {
        this.form.invoiceNo = row.invoiceNo
        this.form.optCode = ''
        this.initData()
      }
    },
    // 菜单数据初始化
    initData() {
      listNoImpInvoiceDetailPack(this.form).then(result => {
        this.appData = result.content.list
        this.form.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 导出
    exportExcel() {
      const tableColumn = [
        {
          title: '发票id',
          field: 'invoiceId'
        },
        {
          title: '发票号',
          field: 'invoiceNo'
        },
        {
          title: '供应商代码',
          field: 'supplierCode'
        },
        {
          title: '状态',
          field: 'status'
        },
        {
          title: '入库异常信息',
          field: 'expmsg'
        },
        {
          title: '订单号',
          field: 'fullOrderNo',
          width: '110'
        },
        {
          title: '型号',
          field: 'modelNo',
          width: '120'
        },
        {
          title: '数量',
          field: 'quantity'
        },
        {
          title: '箱号',
          field: 'caseNo'
        },
        {
          title: '条码',
          field: 'barcode',
          width: '110'
        },
        {
          title: '产地',
          field: 'origin'
        },
        {
          title: '订单类别',
          field: 'orderType'
        },
        {
          title: '备注',
          field: 'remark'
        },
        {
          title: '创建人',
          field: 'createUser'
        },
        {
          title: '创建日期',
          field: 'createTime'
        },
        {
          title: '更新人',
          field: 'updateUser'
        },
        {
          title: '更新日期',
          field: 'updateTime'
        }
      ]
      listNoImpInvoiceDetailPackExcel(this.form).then(res => {
        if (res.success) {
          res.content.forEach(item => {
            item.status = this.statusformatter(item)
            item.createTime = this.createTimeformatter(item)
            item.updateTime = this.updateTimeformatter(item)
          })
          this.$refs.exportExcelVue.initExportExcel(res.content, tableColumn)
        } else {
          console.log('无数据')
        }
      })
    },

    confirmPOInvoice() {
      if (this.confirming) {
        this.$message.warning('不要重复点击！')
      }
      this.confirming = true
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择需要发票入库的数据')
        this.confirming = false
        return
      }
      //   var id = []
      //   for (var i = 0; i < this.$refs.multipleTable.selection.length; i++) {
      const params = {
        invoiceNo: this.$refs.multipleTable.selection[0].invoiceNo,
        invoiceId: this.$refs.multipleTable.selection[0].invoiceId
      }
      console.log(params)
      handimpInvoiceDetailPack(params).then(res => {
        console.log(res)
        if (!res.success) {
          this.$message.error(res.message)
          this.confirming = false
          return
        } else {
          this.$message.success(res.message)
          this.confirming = false
          this.initData()
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
        return
      })
      //   }
    },
    handleSelection(val) {
      this.multipleSelection = val
    },
    createTimeformatter(row) {
      return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
    },
    updateTimeformatter(row) {
      return this.dayjs(row.updateTime).format('YYYY-MM-DD HH:mm')
    },
    receiveTimeformatter(row) {
      if (row.receiveTime != null) {
        return this.dayjs(row.receiveTime).format('YYYY-MM-DD')
      }
    },
    statusformatter(row) {
      return codeFormatter(this.statusdesc, row.status)
    },
    statusCodeNameformatter(row) {
      return codeFormatter(this.statusCodes, row.status)
    },
    invoiceTypeformatter(row) {
      return codeFormatter(this.invoiceTypedesc, row.invoiceType)
    }
  }
}
</script>
