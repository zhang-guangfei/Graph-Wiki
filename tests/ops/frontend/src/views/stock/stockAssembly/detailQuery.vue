<template>
  <div>
    <!-- 查询条件 -->
    <el-form ref="queryForm" :model="queryForm" :inline="true" size="mini" label-width="0">
      <el-form-item label="" prop="applyNo">
        <el-input v-model="queryForm.applyNo" placeholder="申请号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="modelNo">
        <el-input v-model="queryForm.modelNo" placeholder="型号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="assembleType">
        <el-select v-model="queryForm.assembleType" placeholder="申请目的" style="width: 100%" clearable >
          <el-option v-for="item in assembleTypeList" :key="item.code" :label="item.codeName" :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="status">
        <el-select v-model="queryForm.status" placeholder="申请状态" style="width:100%" clearable >
          <el-option v-for="item in applyStatusList" :key="item.code" :label="item.codeName" :value="item.code" />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="warehouseCode" style="margin-bottom: 0px;">
        <el-select :filter-method="filterWarehouse" v-model="queryForm.warehouseCode" filterable placeholder="请选择仓库" clearable style="width:100%">
          <el-option
            v-for="item in findWarehouseList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" >
        <el-col :span="4">
          <el-select v-model="queryForm.dateType" clearable placeholder="日期类型" style="width:100%" >
            <el-option v-for="item in dateTypeList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-col>
        <el-col :span="10">
          <el-date-picker v-model="queryForm.fromDate" type="date" placeholder="起始日期" value-format="yyyy-MM-dd" style="width:100%" clearable/>
        </el-col>
        <el-col :span="10">
          <el-date-picker v-model="queryForm.toDate" type="date" placeholder="结束日期" value-format="yyyy-MM-dd" style="width:100%" clearable/>
        </el-col>
      </el-form-item>
      <el-form-item label="" prop="applyPsn">
        <el-input v-model="queryForm.applyPsn" placeholder="申请人工号" clearable/>
      </el-form-item>
      <el-form-item>
        <el-button v-permission="['496485']" type="primary" icon="el-icon-search" @click="onQuery">查询</el-button>
        <el-button v-permission="['496485']" :loading="exportLoading" type="primary" icon="el-icon-download" size="mini" @click="exportData">导出</el-button>
      </el-form-item>
    </el-form>
    <!-- 查询结果数据表 -->
    <el-table
      v-loading="tableLoading"
      :data="tableData"
      :cell-style="{ padding: '5px' }"
      :header-cell-style="{ 'text-align': 'center' }"
      border
      stripe
      element-loading-text="正在加载中..."
      element-loading-spinner="el-icon-loading"
      size="mini"
      style="width: 100%">
      <el-table-column fixed prop="applyNo" label="申请号" width="140" >
        <template slot-scope="scope">
          <span>{{ scope.row.applyNo }}</span>
          <el-button type="text" size="mini" icon="el-icon-view" @click="goStockAssemblyDetailPage(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column prop="deptNo" label="申请部门" width="110" />
      <el-table-column prop="assembleType" label="申请目的" width="120">
        <template slot-scope="scope">
          <span>{{ getAssembleType(scope.row.assembleType) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="isTransOut" label="调库类型" width="80" >
        <template slot-scope="scope">
          <span>{{ scope.row.isTransOut ? '调出' : '调入' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="modelNo" label="型号" width="180" />
      <el-table-column prop="quantity" label="数量" width="80" />
      <el-table-column prop="optCode" label="申请项状态" width="95" >
        <template slot-scope="scope">
          <span>{{ getDetailStatusName(scope.row.optCode) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="detailWarehouseCode" label="调库仓库" width="150">
        <template slot-scope="scope">
          <span>{{ getWarehouseName(scope.row.detailWarehouseCode) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="inventoryTypeCode" label="库存类型" width="110" >
        <template slot-scope="scope">
          <span>{{ getInventoryTypeName(scope.row.inventoryTypeCode) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="customerNo" label="客户代码" width="120" >
        <template slot-scope="scope">
          <span>{{ scope.row.customerNo ? '【' + scope.row.customerNo + '】' + scope.row.customerName : '' }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="groupCustomerNo" label="集团代码" width="90"/>
      <el-table-column prop="ppl" label="PPL" width="100" />
      <el-table-column prop="projectCode" label="项目编号" width="100"/>
      <el-table-column prop="status" label="申请状态" width="80" >
        <template slot-scope="scope">
          <span>{{ getApplyStatus(scope.row.status) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="dlvDate" label="希望交货期" width="95" />
      <el-table-column prop="expr1" label="备注" width="150" />
      <el-table-column prop="applyPsn" label="申请人" width="90" />
      <el-table-column prop="applyDate" label="申请时间" width="95" />
      <el-table-column prop="approvePsn" label="审批人" width="90" />
      <el-table-column prop="answerDate" label="答复时间" width="95" />
      <el-table-column prop="answerText" label="答复内容" width="120" />
      <el-table-column prop="transPsn" label="调库人" width="90" />
      <el-table-column prop="transTime" label="调库时间" width="95" />
      <el-table-column prop="receivePsn" label="接收人" width="90" />
      <el-table-column prop="receiveTime" label="接收时间" width="95" />
    </el-table>
    <pagination v-show="total>0" :total="total" :page-sizes="[20, 50, 100, 200, 500]" :page.sync="queryForm.pageNum" :limit.sync="queryForm.pageSize" @pagination="initData" />
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import { downloadDataFile } from '@/api/common/comm'
import { listDetail, exportDetail } from '@/api/stock/stockAssembly'

export default {
  name: 'StockAssemblyDetailView',
  components: { Pagination },
  data() {
    return {
      queryForm: {
        applyNo: '',
        modelNo: '',
        assembleType: '',
        warehouseCode: '',
        applyPsn: '',
        dateType: 1,
        fromDate: '',
        toDate: '',
        pageNum: 1,
        pageSize: 20
      },
      applyStatusClassCode: 2007,
      assembleTypeClassCode: 2008,
      inventoryTypeClassCode: 2001,
      detailStatusClassCode: 2058,
      applyStatusList: [],
      detailStatusList: [],
      assembleTypeList: [],
      warehouseList: [],
      inventoryTypeList: [],
      dateTypeList: [
        { value: 1, label: '申请时间' },
        { value: 2, label: '审核时间' },
        { value: 3, label: '调库时间' },
        { value: 4, label: '接收时间' }
      ],
      exportLoading: false,
      findWarehouseList: [],
      tableLoading: false,
      tableData: [],
      total: 0
    }
  },
  created() {
    this.initApplyStatusList()
    this.initAssembleTypeList()
    this.initWarehouseinfo()
    this.initDetailStatusList()
    this.initInventoryTypeList()
  },
  methods: {
    initApplyStatusList() {
      getDataCodesTree(this.applyStatusClassCode).then(res => {
        if (res.success) {
          this.applyStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initAssembleTypeList() {
      getDataCodesTree(this.assembleTypeClassCode).then(res => {
        if (res.success) {
          this.assembleTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initWarehouseinfo() {
      const warehouseForm = {
        warehouseCode: '',
        warehouseName: '',
        warehouseType: ''
      }
      listWarehouse(warehouseForm).then(res => {
        const warehouseData = res.content
        this.warehouseList = []
        for (const item of warehouseData) {
          this.warehouseList.push({
            code: item.warehouseCode,
            codeName: item.warehouseName
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },
    initInventoryTypeList() {
      getDataCodesTree(this.inventoryTypeClassCode).then(res => {
        if (res.success) {
          this.inventoryTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initDetailStatusList() {
      getDataCodesTree(this.detailStatusClassCode).then(res => {
        if (res.success) {
          this.detailStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    onQuery() {
      this.queryForm.pageNum = 1
      this.initData()
    },
    initData() {
      this.tableLoading = true
      listDetail(this.queryForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        } else {
          this.$message.error(res.message)
        }
        this.tableLoading = false
      }).catch(error => {
        this.$message.error('错误' + error)
        this.tableLoading = false
      })
    },
    exportData() {
      this.exportLoading = true
      // this.$message.info('正在导出，请耐心等待')
      exportDetail(this.queryForm).then(res => {
        const fileName = '组换调库申请项清单.xlsx'
        // const blob = new Blob([res], { type: res.type })
        // const link = document.createElement('a')
        // link.style.display = 'none'
        // link.href = window.URL.createObjectURL(blob)
        // link.download = fileName
        // document.body.appendChild(link)
        // link.click()
        // window.URL.revokeObjectURL(link.href)
        // document.body.removeChild(link)
        downloadDataFile(this, res, fileName)
        this.exportLoading = false
      }).catch(error => {
        console.error(error)
        this.exportLoading = false
      })
    },
    goStockAssemblyDetailPage(row) {
      const params = {
        applyId: row.applyId
      }
      this.$emit('goApplyDetailPageEvent', params)
    },
    filterWarehouse(val) {
      if (val) {
        this.findWarehouseList = this.warehouseList.filter((item) => {
          if (!!~item.code.indexOf(val.toUpperCase()) || !!~item.codeName.indexOf(val)) {
            return true
          }
        })
      } else {
        this.findWarehouseList = this.warehouseList
      }
    },
    getApplyStatus(val) {
      const obj = this.applyStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getAssembleType(val) {
      const obj = this.assembleTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getWarehouseName(val) {
      const obj = this.warehouseList.filter(item => item.code === val)[0]
      return obj ? '【' + obj.code + '】' + obj.codeName : ''
    },
    getInventoryTypeName(val) {
      const obj = this.inventoryTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getDetailStatusName(val) {
      const obj = this.detailStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    }
  }
}
</script>
<style scoped>
.el-form-item--mini.el-form-item {
  margin-bottom: 10px;
}
</style>
