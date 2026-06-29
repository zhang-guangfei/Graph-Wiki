<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryForm" :inline="true" size="mini" >
      <el-form-item label="" prop="applyNo" style="margin-bottom: 0px;" >
        <el-input v-model="queryForm.applyNo" placeholder="请输入申请号" clearable @change="changeApplyNo" />
      </el-form-item>
      <el-form-item label="" prop="status" style="margin-bottom: 0px;" >
        <el-select v-model="queryForm.statuss" multiple placeholder="请选择申请状态" clearable style="width: 100%">
          <el-option
            v-for="item in applyStatusList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="applyType" style="margin-bottom: 0px;" >
        <el-select v-model="queryForm.applyType" placeholder="请选择申请类型" clearable style="width: 100%">
          <el-option
            v-for="item in applyTypeList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="replType" style="margin-bottom: 0px;" >
        <el-select v-model="queryForm.replType" placeholder="请选择申请类型" clearable style="width: 100%">
          <el-option
            v-for="item in replTypeList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" style="margin-bottom: 0px;">
        <el-col :span="12">
          <el-form-item label="" prop="fromTime" label-width="0">
            <el-date-picker
              v-model="queryForm.fromTime"
              type="date"
              placeholder="请选择申请起始日期"
              value-format="yyyy-MM-dd"
              style="width:100%"
              clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="toTime" label-width="0">
            <el-date-picker
              v-model="queryForm.toTime"
              type="date"
              placeholder="请选择申请结束日期"
              value-format="yyyy-MM-dd"
              style="width:100%"
              clearable/>
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item label="" prop="stockType" style="margin-bottom: 0px;">
        <el-select v-model="queryForm.stockType" placeholder="请选择备库类型" clearable style="width:100%">
          <el-option
            v-for="item in inventoryTypeList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="warehouseCode" style="margin-bottom: 0px;">
        <el-select v-model="queryForm.warehouseCode" placeholder="请选择仓库" clearable style="width:100%">
          <el-option
            v-for="item in warehouseList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="applyPsn" style="margin-bottom: 0px;" >
        <el-input v-model="queryForm.applyPsn" placeholder="请输入申请人工号或姓名" clearable />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="onQuery">查询</el-button>
        <el-button type="primary" size="mini" icon="el-icon-search" @click="openDetailQueryPage">详情查询</el-button>
      </el-form-item>
    </el-form>

    <el-row class="row-button" >
      <el-button type="primary" size="mini" icon="el-icon-refresh" @click="initData">刷新</el-button>
      <el-button :loading="approveLoading" type="primary" size="mini" icon="el-icon-check" @click="onApprove">审核确认</el-button>
      <el-button :loading="exportLoading" type="primary" size="mini" @click="exportShikomiFile">生成日本SHIKOMI文件</el-button>
      <el-button :loading="autoProcessLoading" type="primary" size="mini" @click="onAutoProcess">自动处理</el-button>
      <el-button type="primary" size="mini" @click="newApply" >新增</el-button>
    </el-row>

    <el-table
      v-loading="tableLoading"
      ref="applyTable"
      :data="tableData"
      :cell-style="{ padding: '2px', 'text-align': 'center' }"
      :header-cell-style="{ padding: '2px', 'font-size': '14px', 'text-align': 'center' }"
      border
      stripe
      element-loading-text="正在加载中..."
      element-loading-spinner="el-icon-loading"
      size="mini"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="50"/>
      <el-table-column
        prop="applyNo"
        label="申请号"
        width="135">
        <template slot-scope="scope">
          <span>{{ scope.row.applyNo }}</span>
          <el-button style="float:right;" type="text" size="mini" icon="el-icon-view" @click="goPreStockDetailPage(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="80">
        <template slot-scope="scope">
          {{ getApplyStatus(scope.row.status) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="applyType"
        label="申请类型"
        width="100">
        <template slot-scope="scope">
          {{ getApplyType(scope.row.applyType) }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="replType"
        label="提案类型"
        width="100">
        <template slot-scope="scope">
          {{ getReplType(scope.row.replType) }}
        </template>
      </el-table-column>
      <el-table-column
        prop="stockType"
        label="备库类型"
        width="120">
        <template slot-scope="scope">
          {{ getStockType(scope.row.stockType) }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="warehouseCode"
        label="仓库"
        width="160">
        <template slot-scope="scope">
          {{ getWarehouseName(scope.row.warehouseCode) }}
        </template>
      </el-table-column>
      <el-table-column
        show-overflow-tooltip
        prop="deptNo"
        label="申请部门"
        width="160" />
      <el-table-column
        prop="applyPsn"
        label="申请人"
        width="80" />
      <el-table-column
        prop="applyTime"
        label="申请日期"
        width="95"/>
      <el-table-column
        show-overflow-tooltip
        prop="customerNo"
        label="客户"
        width="150" />
      <el-table-column
        show-overflow-tooltip
        prop="userNo"
        label="用户"
        width="150" />
      <el-table-column
        show-overflow-tooltip
        prop="reason"
        label="申请原因"/>
      <el-table-column
        show-overflow-tooltip
        prop="remark"
        label="备注"/>
    </el-table>
    <pagination v-if="total>0" :total="total" :page-sizes="[20, 50, 100, 200, 500]" :page.sync="queryForm.pageNum" :limit.sync="queryForm.pageSize" style="margin-top: 0px" @pagination="initData" />
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import { listApply, autoHandleApply, exportJPShikomiFile, approveApply } from '@/api/stock/prestock'

export default {
  name: 'PreStockIndex',
  components: { Pagination },
  data() {
    return {
      queryForm: {
        applyNo: '',
        statuss: ['2', '3'],
        modelNo: '',
        applyType: '',
        replType: '',
        fromTime: '',
        toTime: '',
        stockType: '',
        warehouseCode: '',
        applyPsn: '',
        pageNum: 1,
        pageSize: 20
      },
      warehouseList: [],
      applyStatusClassCode: 2005,
      applyTypeClassCode: 2013,
      inventoryTypeClassCode: 2001,
      replTypeClassCode: 2019,
      applyStatusList: [],
      applyTypeList: [],
      inventoryTypeList: [],
      replTypeList: [],
      tableLoading: false,
      tableData: [],
      total: 0,
      selection: [],
      applyIds: [],
      deptNoList: [],
      exportLoading: false,
      approveLoading: false,
      autoProcessLoading: false
    }
  },
  created() {
    this.initApplyStatusList()
    this.initApplyTypeList()
    this.initInventoryTypeList()
    this.initWarehouseList()
    this.initData()
  },
  methods: {
    initApplyStatusList() {
      getDataCodesTree(this.applyStatusClassCode).then(res => {
        if (res.success) {
          this.applyStatusList = res.content
        }
      })
    },
    initApplyTypeList() {
      getDataCodesTree(this.applyTypeClassCode).then(res => {
        if (res.success) {
          this.applyTypeList = res.content
        }
      })
      getDataCodesTree(this.replTypeClassCode).then(res => {
        if (res.success) {
          this.replTypeList = res.content
        }
      })
    },
    initInventoryTypeList() {
      getDataCodesTree(this.inventoryTypeClassCode).then(res => {
        if (res.success) {
          this.inventoryTypeList = res.content
        }
      })
    },
    initWarehouseList() {
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
    onQuery() {
      this.queryForm.pageNum = 1
      this.initData()
    },
    refresh() {
      // 刷新-默认查处理中的申请
      this.queryForm.status = '3'
    },
    initData() {
      this.tableLoading = true
      listApply(this.queryForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
        this.tableLoading = false
      }).catch(error => {
        console.error(error)
        this.tableLoading = false
      })
    },
    onAutoProcess() {
      // 过滤出可处理的申请 （排除shikomi类型申请）
      this.filterProcessSelection()
      if (!this.applyIds || this.applyIds.length === 0) {
        this.$message.warning('请选择申请')
        return
      }
      this.autoProcessLoading = true
      const params = { 'applyIds': this.applyIds }
      autoHandleApply(params).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.initData()
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.autoProcessLoading = false
      }).catch(error => {
        console.error(error)
        this.autoProcessLoading = false
      })
    },
    exportShikomiFile() {
      this.exportLoading = true
      exportJPShikomiFile().then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.exportLoading = false
      }).catch(error => {
        console.error(error)
        this.exportLoading = false
      })
    },
    onApprove() {
      // 过滤出可审核确认的申请
      this.filterApproveSelection()
      if (!this.applyIds || this.applyIds.length === 0) {
        this.$message.warning('请选择申请')
        return
      }
      this.approveLoading = true
      const params = { 'applyIds': this.applyIds }
      approveApply(params).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.initData()
        } else {
          this.$message.error(res.message)
        }
        this.approveLoading = false
      }).catch(error => {
        console.error(error)
      })
    },
    filterProcessSelection() {
      const that = this
      const applyIds = []
      if (that.selection.length > 0) {
        that.selection.forEach((row, index) => {
          if (row.applyType !== '2' && row.status === '3') {
            applyIds.push(row.id)
          } else {
            this.$refs.applyTable.toggleRowSelection(row, false)
          }
        })
      }
      this.applyIds = applyIds
    },
    // 过滤出可审核确认的申请行
    filterApproveSelection() {
      const that = this
      const applyIds = []
      if (that.selection.length > 0) {
        that.selection.forEach((row, index) => {
          if (row.status === '2') {
            applyIds.push(row.id)
          } else {
            this.$refs.applyTable.toggleRowSelection(row, false)
          }
        })
      }
      this.applyIds = applyIds
    },
    handleSelectionChange(val) {
      this.selection = val
    },
    changeApplyNo() {
      if (this.queryForm.applyNo) {
        this.queryForm.statuss = []
      }
    },
    newApply() {
      this.$router.push({
        path: '/stock/prestock/detail',
        query: { 'applyId': 0, 'passkey': null }
      })
    },
    goPreStockDetailPage(row) {
      this.$router.push({
        path: '/stock/prestock/detail',
        query: { 'applyId': row.id, 'passkey': row.passkey }
      })
    },
    openDetailQueryPage() {
      this.$router.push({
        path: '/stock/prestock/detailQuery'
      })
    },
    getApplyStatus(val) {
      const obj = this.applyStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getApplyType(val) {
      const obj = this.applyTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getReplType(val) {
      const obj = this.replTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getStockType(val) {
      const obj = this.inventoryTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getWarehouseName(val) {
      const obj = this.warehouseList.filter(item => item.code === val)[0]
      return obj ? '【' + obj.code + '】' + obj.codeName : ''
    }
  }
}
</script>
<style scoped>
.el-form-item--mini.el-form-item {
  margin-bottom: 10px;
}
.row-button {
  margin-bottom: 10px;
}
</style>
