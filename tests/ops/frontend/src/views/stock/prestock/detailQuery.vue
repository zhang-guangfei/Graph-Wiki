<template>
  <div class="app-container">
    <!-- 查询条件 -->
    <el-form ref="queryForm" :model="queryForm" :inline="true" size="mini" label-width="0">
      <el-form-item label="" prop="applyNo">
        <el-input v-model="queryForm.applyNo" placeholder="申请号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="deptNo">
        <el-input v-model="queryForm.deptNo" placeholder="营业所" clearable/>
      </el-form-item>
      <el-form-item label="" prop="customerNo">
        <el-input v-model="queryForm.customerNo" placeholder="客户代码" clearable/>
      </el-form-item>
      <el-form-item label="" prop="applyModelNo">
        <el-input v-model="queryForm.applyModelNo" placeholder="申请型号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="modelNo">
        <el-input v-model="queryForm.modelNo" placeholder="处理型号" clearable/>
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
      <el-form-item label="" prop="applyStatus" style="margin-bottom: 0px;" >
        <el-select v-model="queryForm.applyStatus" multiple placeholder="请选择申请状态" clearable style="width: 100%">
          <el-option
            v-for="item in applyStatusList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="detailStatus" style="margin-bottom: 0px;" >
        <el-select v-model="queryForm.detailStatus" multiple placeholder="请选择申请子项状态" clearable style="width: 100%">
          <el-option
            v-for="item in detailStatusList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="inventoryType" style="margin-bottom: 0px;">
        <el-select v-model="queryForm.inventoryType" placeholder="请选择库存类型" clearable style="width:100%">
          <el-option
            v-for="item in inventoryTypeList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
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
      <el-form-item label="" prop="pplNo">
        <el-input v-model="queryForm.pplNo" placeholder="PPL编号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="projectNo">
        <el-input v-model="queryForm.projectNo" placeholder="项目编号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="groupCustomerNo">
        <el-input v-model="queryForm.groupCustomerNo" placeholder="集团代码" clearable/>
      </el-form-item>
      <el-form-item label="" prop="shikomiClass">
        <el-input v-model="queryForm.shikomiClass" placeholder="shikomi类型" clearable/>
      </el-form-item>
      <el-form-item label="" prop="supplierCode">
        <!-- <el-input v-model="queryForm.supplierCode" placeholder="供应商" clearable /> -->
        <el-select v-model="queryForm.supplierCode" placeholder="供应商" clearable style="width:100%">
          <el-option
            v-for="item in supplieData"
            :key="item.id"
            :label="item.name"
            :value="item.id"/>
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="transferNo">
        <el-input v-model="queryForm.transferNo" placeholder="调库单号" clearable />
      </el-form-item>
      <el-form-item label="" prop="orderNo">
        <el-input v-model="queryForm.orderNo" placeholder="采购单号" clearable />
      </el-form-item>
      <el-form-item label="" prop="prepareOrders">
        <el-input v-model="queryForm.prepareOrders" placeholder="预占单号" clearable />
      </el-form-item>
      <el-form-item label="" >
        <el-col :span="4">
          <el-select v-model="queryForm.dateType" clearable placeholder="时间类型" style="width:100%" >
            <el-option v-for="item in dateTypeList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-col>
        <el-col :span="10">
          <el-date-picker v-model="queryForm.startTime" type="date" placeholder="起始时间" value-format="yyyy-MM-dd" style="width:100%" clearable/>
        </el-col>
        <el-col :span="10">
          <el-date-picker v-model="queryForm.endTime" type="date" placeholder="截止时间" value-format="yyyy-MM-dd" style="width:100%" clearable/>
        </el-col>
      </el-form-item>
      <el-form-item>
        <el-button :loading="tableLoading" type="primary" icon="el-icon-search" size="mini" @click="onQuery">查询</el-button>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="onQueryLotDialog(1)">批量查询</el-button>
        <el-button :loading="exportLoading" type="primary" icon="el-icon-download" size="mini" @click="exportData">导出</el-button>
      </el-form-item>
    </el-form>
    <el-row class="row-button">
      <el-button type="primary" size="mini" @click="updatePreStockDetailStatus('1')">转处理中</el-button>
      <el-button type="primary" size="mini" @click="updatePreStockDetailStatus('2')">取消备库</el-button>
      <el-button type="primary" size="mini" @click="autoHandlePreStockApply(true)">自动处理</el-button>
      <el-button type="primary" size="mini" @click="autoHandlePreStockApply(false)">自动处理（不拦截）</el-button>
      <el-button type="primary" size="mini" @click="showManualProcessDialog">手动处理</el-button>
    </el-row>
    <!-- 查询结果数据表单 -->
    <el-table
      v-loading="tableLoading"
      :data="tableData"
      :cell-style="{ padding: '5px', 'text-align': 'center' }"
      :header-cell-style="{ 'text-align': 'center' }"
      fit
      border
      stripe
      element-loading-text="正在加载中..."
      element-loading-spinner="el-icon-loading"
      size="mini"
      style="width: 100%"
      @select-all="selectAll"
      @select="selectionChange">
      <el-table-column key="selected" type="selection"/>
      <el-table-column fixed prop="applyNo" label="申请号" width="135" >
        <template slot-scope="scope">
          <span>{{ scope.row.applyNo }}</span>
          <el-button style="float:right;" type="text" size="mini" icon="el-icon-view" @click="goPreStockDetailPage(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column fixed prop="itemNo" label="申请项号" width="80" />
      <el-table-column fixed show-overflow-tooltip prop="applyModelNo" label="申请型号" width="160"/>
      <el-table-column fixed show-overflow-tooltip prop="modelNo" label="处理型号" width="160"/>
      <el-table-column prop="detailStatus" label="申请项状态" width = "90">
        <template slot-scope="scope">
          <span>{{ getDetailStatus(scope.row.detailStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="quantity" label="数量" width = "70"/>
      <el-table-column prop="qtyBin" label="发注点" width = "70"/>
      <el-table-column show-overflow-tooltip prop="newFlag" label="申请型号类型" width = "80">
        <template slot-scope="scope">
          <span>{{ getNewFlag(scope.row.newFlag) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="dlvDate" label="客户要求货期" width="100"/>
      <el-table-column prop="dlvDateJP" label="shikomi要求日本出厂日期" width="100"/>
      <el-table-column prop="supplierCode" label="供应商"/>
      <el-table-column key="eprice" prop="eprice" label="E价格" width="80"/>
      <el-table-column key="eamount" prop="eamount" label="E金额" width="120"/>
      <el-table-column show-overflow-tooltip prop="transferNos" label="调库单号" width="110"/>
      <!-- <el-table-column show-overflow-tooltip prop="zbNo" label="准备单号" width="110"/> -->
      <el-table-column show-overflow-tooltip prop="orderNos" label="采购单号" width="110"/>
      <el-table-column show-overflow-tooltip prop="prepareOrders" label="预占单号" width="110"/>
      <el-table-column prop="stockQty" label="调库数量" width = "70"/>
      <el-table-column prop="poQty" label="请购数量" width = "70"/>
      <el-table-column prop="applyType" label="申请类型" width="100">
        <template slot-scope="scope">
          <span>{{ getApplyType(scope.row.applyType) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="replType" label="提案类型">
        <template slot-scope="scope">
          <span>{{ getReplType(scope.row.replType) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="deptNo" label="申请部门" width="160">
        <template slot-scope="scope">
          <span>{{ '【' + scope.row.deptNo + '】' + scope.row.deptName }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="customerNo" label="客户" width = "160">
        <template slot-scope="scope">
          <span v-if="scope.row.customerNo" >{{ '【' + scope.row.customerNo + '】' + scope.row.customerName }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="userNo" label="用户" width = "160">
        <template slot-scope="scope">
          <span v-if="scope.row.userNo" >{{ '【' + scope.row.userNo + '】' + scope.row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="warehouseCode" label="备库仓库" width = "160">
        <template slot-scope="scope">
          <span>{{ getWarehouseName(scope.row.warehouseCode) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="stockType" label="库存类型" width = "110">
        <template slot-scope="scope">
          <span>{{ getStockType(scope.row.stockType) }}</span>
        </template>
      </el-table-column>
      <el-table-column show-overflow-tooltip prop="pplNo" label="PPL号" width = "130"/>
      <el-table-column show-overflow-tooltip prop="projectNo" label="项目号" width="100"/>
      <el-table-column show-overflow-tooltip prop="groupCustomerNo" label="集团编号"/>
      <el-table-column prop="applyStatus" label="申请状态">
        <template slot-scope="scope">
          <span>{{ getApplyStatus(scope.row.applyStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="applyTime" label="申请时间" width="90"/>
      <el-table-column prop="applyPsn" label="申请人"/>
      <el-table-column prop="confirmUser" label="确认人"/>
      <el-table-column prop="approveTime" label="审批时间" show-overflow-tooltip width="120"/>
    </el-table>
    <pagination v-show="total>0" :total="total" :page-sizes="[20, 50, 100, 200, 500]" :page.sync="queryForm.pageNum" :limit.sync="queryForm.pageSize" style="margin-top: 0px" @pagination="initData" />
    <!-- 批量查询窗口 -->
    <el-dialog :visible.sync="dialogLotQueryVisible" title="批量查询" width="350px">
      <div>
        <el-select v-model="queryForm.lotQueryType" clearable placeholder="请选择查询项">
          <el-option
            v-for="item in lotQueryOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"/>
        </el-select>
      </div>
      <el-tag type="danger">注：以回车间隔，或从Excel列复制</el-tag>
      <div>
        <el-input
          v-model="lotQueryContent"
          :maxlength="200"
          :autosize="{ minRows: 10, maxRows: 20}"
          type="textarea"
          placeholder="换行多项查询"
          show-word-limit
          style="width: 300px;"
          clearable
        />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="error" size="mini" @click="onQueryLotDialog(2)">退 回</el-button>
        <el-button type="primary" size="mini" @click="onQueryLotDialog(3)">查询</el-button>
      </div>
    </el-dialog>
    <!-- 手动处理的窗口 -->
    <ManualDialog ref="child" @onManualProcess="onManualProcess"/>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import { listDetail, exportDetail, updatePreStockDetailStatus, autoHandleApply } from '@/api/stock/prestock'
import { findSupplierByIdOrName } from '@/api/common/supplier'
import { findPriceByModelNo } from '@/api/product'
import ManualDialog from '@/views/stock/prestock/manualDialog'
import { downloadDataFile } from '@/api/common/comm'
export default {
  name: 'PreStockDetailView',
  components: { Pagination, ManualDialog },
  data() {
    return {
      queryForm: {
        applyNo: '',
        deptNo: '',
        customerNo: '',
        modelNo: '',
        applyModelNo: '',
        applyType: '',
        replType: '',
        applyStatus: '',
        detailStatus: '',
        inventoryType: '',
        warehouseCode: '',
        pplNo: '',
        projectNo: '',
        groupCustomerNo: '',
        shikomiClass: '',
        supplierCode: '',
        transferNo: '',
        orderNo: '',
        dateType: '',
        startTime: '',
        endTime: '',
        lotQueryType: '',
        lotQueryData: [],
        prepareOrders: '',
        pageNum: 1,
        pageSize: 20
      },
      total: 0,
      tableLoading: false,
      exportLoading: false,
      tableData: [],
      supplieData: [],
      dateTypeList: [
        { value: 1, label: '申请时间' },
        { value: 2, label: '审批时间' }
      ],
      applyStatusClassCode: 2005,
      replTypeClassCode: 2019,
      applyTypeClassCode: 2013,
      inventoryTypeClassCode: 2001,
      shikomiClassClassCode: 2022,
      newFlagClassCode: 2017,
      detailStatusClassCode: 2018,
      applyStatusList: [],
      applyTypeList: [],
      replTypeList: [],
      inventoryTypeList: [],
      shikomiClassList: [],
      detailStatusList: [],
      warehouseList: [],
      newFlagList: [],
      findWarehouseList: [],
      productPriceList: [],
      selection: [],
      appId: 0,
      detailId: 0,
      dialogLotQueryVisible: false,
      autoProcessLoading: false,
      onManualProcessLoading: false,
      dialogProcessResultVisible: false,
      isClicked: false,
      lotQueryContent: '',
      lotQueryOptions: [
        { value: '1', label: '申请号' },
        { value: '2', label: '处理型号' },
        { value: '3', label: '采购单号' },
        { value: '4', label: '调库单号' }
      ]
    }
  },
  created() {
    this.initApplyStatusList()
    this.initApplyTypeList()
    this.initInventoryTypeList()
    this.initShikomiClassList()
    this.initDetailStatusList()
    this.initWarehouseList()
    this.initNewFlagList()
    this.listSupplierinfo()
  },
  methods: {
    selectAll(selection) {
      this.selection = selection
    },
    selectionChange(selection, row) {
      this.selection = selection
    },
    initApplyStatusList() {
      getDataCodesTree(this.applyStatusClassCode).then(res => {
        if (res.success) {
          this.applyStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
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
      }).catch(error => {
        console.error(error)
      })
    },
    initShikomiClassList() {
      getDataCodesTree(this.shikomiClassClassCode).then(res => {
        if (res.success) {
          this.shikomiClassList = res.content
        }
      }).catch(error => {
        console.error(error)
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
    initDetailStatusList() {
      getDataCodesTree(this.detailStatusClassCode).then(res => {
        if (res.success) {
          this.detailStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initNewFlagList() {
      getDataCodesTree(this.newFlagClassCode).then(res => {
        if (res.success) {
          this.newFlagList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    onQuery() {
      this.lotQueryContent = ''
      this.queryForm.lotQueryType = ''
      this.queryForm.lotQueryData = []
      this.queryForm.pageNum = 1
      this.initData()
    },
    onQueryLotDialog(val) {
      switch (val) {
        case 1:
          this.lotQueryContent = ''
          this.queryForm.lotQueryType = '1'
          this.queryForm.lotQueryData = []
          this.dialogLotQueryVisible = true
          break
        case 2:
          this.queryForm.lotQueryType = ''
          this.queryForm.lotQueryData = []
          this.lotQueryContent = ''
          this.dialogLotQueryVisible = false
          break
        default:
          if (this.queryForm.lotQueryType && this.lotQueryContent) {
            this.queryForm.pageNum = 1
            this.initData()
            this.dialogLotQueryVisible = false
          } else if (!this.queryForm.lotQueryType) {
            this.$message.error('请选择批量查询项。')
          } else if (!this.lotQueryContent) {
            this.$message.error('请输入批量查询数据。')
          }
          break
      }
    },
    listSupplierinfo() {
      const parm = { 'companyId': '', 'name': '' }
      findSupplierByIdOrName(parm).then(res => {
        this.supplieData = res.content
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    async initData() {
      if (this.tableLoading) {
        return
      }
      this.tableLoading = true
      if (this.queryForm.lotQueryType && this.lotQueryContent) {
        const dataList = this.getQueryDataList(this.lotQueryContent)
        if (dataList) {
          this.queryForm.lotQueryData = dataList
        } else {
          this.$message.error('请输入批量查询数据。')
          this.tableLoading = false
        }
      }
      await listDetail(this.queryForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        } else {
          this.$message.error(res.message)
        }
        this.tableLoading = false
      }).catch(error => {
        console.error(error)
        this.tableLoading = false
      })
    },
    showManualProcessDialog() {
      if (this.selection.length !== 1) {
        this.$message.warning('请选择一个申请项,手动处理每次仅处理一个申请项')
        return
      }
      // 处理中,暂不处理
      if (!this.selection[0].detailStatus.startsWith('2') && !this.selection[0].detailStatus.startsWith('7') && !this.selection[0].detailStatus.startsWith('10')) {
        this.$message.warning('只能手动处理处理中，异常拦截或暂不处理的项。')
        return
      }
      this.detailId = this.selection[0].detailId
      this.appId = Number(this.selection[0].applyId)
      const param = {
        'applyId': this.appId,
        'detailId': this.detailId,
        'applyFormWarehouseCode': this.selection[0].warehouseCode
      }
      // 直接调用子组件的方法
      this.$refs.child.onGetResultDetailByParam(param)
      this.dialogProcessResultVisible = true
    },
    // 手动处理-执行处理项数据，子组件返回信息
    onManualProcess(val) {
      if (val) {
        // 刷新申请
        this.initData()
      }
    },
    // 自动处理-执行处理项数据
    autoHandlePreStockApply(isIntercept) {
      if (!this.selection || this.selection.length === 0) {
        this.$message.error('请选择处理项。')
        return
      }
      if (this.autoProcessLoading) {
        return
      }
      this.autoProcessLoading = true
      const applyIds = []
      const detailIds = []
      for (const index in this.selection) {
        // 处理中,异常拦截,暂不处理
        if (this.selection[index].detailStatus.startsWith('2') || this.selection[index].detailStatus.startsWith('7') || this.selection[index].detailStatus.startsWith('10')) {
          if (!applyIds.includes(this.selection[index].applyId)) {
            applyIds.push(this.selection[index].applyId)
          }
          detailIds.push(this.selection[index].detailId)
        }
      }
      if (!detailIds || detailIds.length === 0) {
        this.$message.error('请选择处理中项。')
        this.isClicked = false
        return
      }
      const params = {
        'applyIds': applyIds,
        'detailIds': detailIds,
        'isIntercept': isIntercept
      }
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
      })
    },
    exportData() {
      if (this.exportLoading) {
        return
      }
      this.exportLoading = true
      this.$message.success('正在导出，请耐心等待')
      exportDetail(this.queryForm).then(res => {
        downloadDataFile(this, res, '先行在库申请项清单.xlsx')
        this.exportLoading = false
      }).catch(error => {
        this.$message.error('错误：' + error)
        this.exportLoading = false
      })
    },
    updatePreStockDetailStatus(val) {
      if (!this.selection || this.selection.length === 0) {
        this.$message.error('请选择处理项。')
        return
      }
      if (this.isClicked) {
        return
      }
      this.isClicked = true
      const detailIds = []
      let newStatus = '2'
      let errMessage = '请选择项'
      for (const index in this.selection) {
        if (val === '1') {
          errMessage = '请选择退单项。'
          if (this.selection[index].detailStatus.startsWith('3')) {
            detailIds.push(this.selection[index].detailId)
          }
        } else {
          errMessage = '请选择退单（3）,处理中(2)，暂不处理(10)，异常拦截项(7)'
          if (this.selection[index].detailStatus.startsWith('3') || this.selection[index].detailStatus.startsWith('2') || this.selection[index].detailStatus.startsWith('10') || this.selection[index].detailStatus.startsWith('7')) {
            detailIds.push(this.selection[index].detailId)
          }
        }
      }
      if (!detailIds || detailIds.length === 0) {
        this.$message.error(errMessage)
        this.isClicked = false
        return
      }
      switch (val) {
        case '1':
          newStatus = '2'
          break
        case '2':
          newStatus = '9'
          break
        default:
          this.$message.error('操作类型错误！')
          this.isClicked = false
          return
      }
      const formData = new FormData()
      formData.append('detailIds', detailIds)
      formData.append('newStatus', newStatus)
      updatePreStockDetailStatus(formData).then(res => {
        if (res.success) {
          this.$message.info('执行完成！')
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.$message.error(error)
      })
      this.isClicked = false
      this.initData()
    },
    goPreStockDetailPage(row) {
      this.$router.push({
        path: '/stock/prestock/detail',
        query: { 'applyId': row.applyId, 'passkey': row.passkey }
      })
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
    async getPriceListByModel(modelNo) {
      await findPriceByModelNo(modelNo).then(res => {
        if (res.data) {
          this.productPriceList = res.data
        }
      }).catch(error => {
        console.error(error)
        this.tableLoading = false
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
    getShikomiClass(val) {
      const obj = this.shikomiClassList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getWarehouseName(val) {
      const obj = this.warehouseList.filter(item => item.code === val)[0]
      return obj ? '【' + obj.code + '】' + obj.codeName : ''
    },
    getDetailStatus(val) {
      const obj = this.detailStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getNewFlag(val) {
      const obj = this.newFlagList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getQueryDataList(content) {
      const rtnData = []
      if (content && content !== undefined && content.length > 0) {
        const data = content.split('\n')
        for (const val in data) {
          if (data[val] && data[val].length > 0) {
            if (!rtnData.includes(data[val])) {
              rtnData.push(data[val])
            }
          }
        }
      }
      return rtnData
    }
  }
}
</script>
<style scoped>
.el-form-item--mini.el-form-item {
  margin-bottom: 10px;
}
</style>
