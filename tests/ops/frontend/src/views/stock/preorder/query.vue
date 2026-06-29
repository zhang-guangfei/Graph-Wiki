<template>
  <div>
    <el-form ref="queryForm" :inline="true" :model="queryForm" size="mini" label-position="right">
      <el-form-item label="营业所" label-width="60px" size="mini">
        <department @handleScopeChange="handleDeptNoChange" />
      </el-form-item>
      <el-form-item label="仓库" size="mini">
        <el-select v-model="queryForm.warehouseCodes" multiple collapse-tags clearable placeholder="请选择仓库">
          <el-option
            v-for="item in warehouseData"
            :key="item.warehouseCode"
            :label="item.warehouseName"
            :value="item.warehouseCode"/>
        </el-select>
      </el-form-item>
      <el-form-item label="库存类型" size="mini">
        <el-select v-model="queryForm.inventoryTypeCodes" multiple collapse-tags clearable placeholder="请选择库存类型">
          <el-option
            v-for="item in inventoryTypeCodeList"
            :key="item.code"
            :label="item.name"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="客户" size="mini">
        <el-input v-model="queryForm.customerNo" size="mini" clearable placeholder="客户代码" />
      </el-form-item>
      <el-form-item label="型号" size="mini">
        <el-input v-model="queryForm.modelNo" size="mini" clearable placeholder="型号" />
      </el-form-item>
      <el-form-item label="担当" size="mini">
        <el-input v-model="queryForm.charger" size="mini" clearable placeholder="担当" />
      </el-form-item>
      <el-form-item label="群代码" label-width="70px" size="mini">
        <el-input v-model="queryForm.groupCustomerNo" size="mini" style="width: 145px" clearable placeholder="群代码" />
      </el-form-item>
      <el-form-item label="PPL" size="mini">
        <el-input v-model="queryForm.ppl" size="mini" clearable placeholder="ppl" />
      </el-form-item>
      <el-form-item label="P J" size="mini">
        <el-input v-model="queryForm.projectCode" size="mini" clearable placeholder="项目号" />
      </el-form-item>
      <el-button type="primary" icon="el-icon-search" size="mini" @click="initData">查询</el-button>
      <el-button v-loading="exportLoading" type="primary" icon="el-icon-upload2" size="mini" @click="exportPreOrder">导出</el-button>
    </el-form>
    <el-table
      v-loading="listLoading"
      ref="multipleTable"
      :data="preOrderData"
      :cell-style="{ padding: '5px' }"
      size="mini"
      border
      stripe
      style="width: 100%;margin-top: 5px"
      height="500px"
    >
      <!-- 表头字段 -->
      <el-table-column prop="companyName" label="分公司" width="100" show-overflow-tooltip/>
      <el-table-column prop="parentName" label="营业部/行业" width="100" show-overflow-tooltip/>
      <el-table-column prop="salesName" label="营业所/子行业" width="100" show-overflow-tooltip/>
      <el-table-column prop="warehouseCode" label="库房"/>
      <el-table-column prop="inventoryTypeCode" label="库存类型" width="130" show-overflow-tooltip/>
      <el-table-column prop="modelNo" label="型号" width="150" show-overflow-tooltip />
      <el-table-column prop="customerNo" label="客户" />
      <el-table-column prop="groupCustomerNo" label="群代码"/>
      <el-table-column prop="ppl" label="PPL" show-overflow-tooltip/>
      <el-table-column prop="projectCode" label="PJ" show-overflow-tooltip/>
      <el-table-column prop="roQty" label="入库数量"/>
      <el-table-column prop="avaQty" label="有效在库"/>
      <el-table-column prop="pushQty" label="推送决算数" width="100"/>
      <el-table-column prop="approveQty" label="审批中数"/>
      <el-table-column prop="delayQty" label="延期中数"/>
      <el-table-column prop="transQty" label="清算数"/>
      <el-table-column prop="frequency12" label="订货频率12" width="110"/>
      <el-table-column prop="averageof12" label="订货月均12" width="110"/>
      <el-table-column prop="retentionMonth" label="保有月"/>
      <el-table-column prop="eamount" label="E金额"/>
      <el-table-column prop="binStr" label="BIN"/>
      <el-table-column prop="charger" label="担当" show-overflow-tooltip/>
    </el-table>
    <!-- 分页工具 -->
    <pagination
      v-show="queryForm.total > 0"
      :total="queryForm.total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="queryForm.pageNum"
      :limit.sync="queryForm.pageSize"
      @pagination="listPreOrderAccount"
    />

  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import Warehouse from '@/components/Warehouse'
import { findInventoryType } from '@/api/warehouseManage'
import { listWarehouseNoWT } from '@/api/common/dict'
import { getCustomerNameByNo } from '@/api/common/customer'
import { listPreOrderAccount, exportPreOrderAccount } from '@/api/stock/preorder'
export default {
  components: { Pagination, Department, Warehouse },
  data() {
    return {
      queryForm: {
        deptNo: [],
        modelNo: '',
        inventoryTypeCodes: [],
        customerNo: '',
        ppl: '',
        projectCode: '',
        groupCustomerNo: '',
        salesInfoNo: '',
        warehouseCodes: [],
        status: [],
        charger: '',
        beginDateStr: '',
        endDateStr: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      searchMoreForm: false,
      exportLoading: false,
      listLoading: false,
      multipleSelection: [],
      inventoryTypeCodeList: [],
      warehouseData: [],
      preOrderData: []
    }
  },
  created() {
    this.findInventoryType()
    this.getWareHouseListData()
    this.initData()
  },
  methods: {
    handleDeptNoChange(val) {
      this.queryForm.deptNos = val
    },
    handleWarhouseChange(val) {
      this.queryForm.warehouseCodes = val
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    // 字典：库存类别
    findInventoryType() {
      findInventoryType().then(res => {
        res.data.forEach(dict => {
          this.inventoryTypeCodeList.push({ code: dict.inventoryTypeCode, name: dict.description })
        })
      }).catch(res => {
        this.$message.error(res.message)
      })
    },
    inventoryTypeNameFormatter: function(row) {
      const code = row.inventoryTypeCode
      for (const item of this.inventoryTypeCodeList) {
        if (item.code === code) {
          return item.name
        }
      }
    },
    async getCustomerName(customerNo) {
      if (!customerNo) {
        return
      }
      const params = { 'customerNo': customerNo }
      await getCustomerNameByNo(params).then(res => {
        if (res.success) {
          console.log(res.content)
          return 'res.content'
        } else {
          return
        }
      })
    },
    getWareHouseListData() {
      this.warehouseData = []
      this.warehouseMaster = []
      listWarehouseNoWT().then(res => {
        if (res.success) {
          this.warehouseData = res.content
        } else {
          this.warehouseData = []
          this.warehouseMaster = []
        }
      }).catch(error => {
        console.error(error)
        this.warehouseData = []
        this.warehouseMaster = []
      })
    },
    searchMore() {
      this.searchMoreForm = !this.searchMoreForm
    },
    initData() {
      this.queryForm.pageNum = 1
      this.queryForm.pageSize = 20
      this.listPreOrderAccount()
    },
    listPreOrderAccount() {
      this.listLoading = true
      this.preOrderData = []
      console.log('listPreOrderAccount=>', this.queryForm)
      listPreOrderAccount(this.queryForm).then(res => {
        if (res.success) {
          this.preOrderData = res.content.list
          this.queryForm.total = res.content.total
          this.listLoading = false
        } else {
          this.$message.error(res.message)
          this.listLoading = false
        }
      }).catch(error => {
        this.$message.error(error)
        this.listLoading = false
      })
    },
    exportPreOrder() {
      this.exportLoading = true
      const expdate = '先行在库决算数据' + this.dayjs(new Date()).format('YYYYMMDDHHmmss')
      this.fileName = expdate + '.xlsx'
      exportPreOrderAccount(this.queryForm).then(res => {
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
    }
  }
}
</script>

<style scoped>

</style>
