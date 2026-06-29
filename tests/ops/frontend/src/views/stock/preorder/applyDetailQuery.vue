<template>
  <div>
    <el-form ref="queryForm" :inline="true" :model="queryForm" size="mini">
      <el-form-item label="订单号" size="mini">
        <el-input v-model="queryForm.orderNo" style="width: 150px" size="mini" clearable placeholder="订单号" />
      </el-form-item>
      <el-form-item label="决算申请号" size="mini">
        <el-input v-model="queryForm.accountApplyNo" style="width: 150px" size="mini" clearable placeholder="订单号" />
      </el-form-item>
      <el-form-item label="申请号" size="mini">
        <el-input v-model="queryForm.applyNo" style="width: 150px" size="mini" clearable placeholder="订单号" />
      </el-form-item>
      <el-form-item label="决算状态" size="mini">
        <el-select v-model="queryForm.status" style="width: 150px" multiple collapse-tags clearable placeholder="请选择状态">
          <el-option
            v-for="item in statusDesc"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="决算库房" size="mini">
        <el-select v-model="queryForm.warehouseCodes" multiple collapse-tags clearable placeholder="请选择库房">
          <el-option
            v-for="item in warehouseData"
            :key="item.warehouseCode"
            :label="item.warehouseName"
            :value="item.warehouseCode"/>
        </el-select>
      </el-form-item>
      <el-form-item label="客户" size="mini">
        <el-input v-model="queryForm.customerNo" size="mini" clearable placeholder="客户代码" />
      </el-form-item>
      <el-form-item label="型号" size="mini">
        <el-input v-model="queryForm.modelNo" size="mini" clearable placeholder="型号" />
      </el-form-item>
      <el-form-item label="担  当" size="mini">
        <el-input v-model="queryForm.charger" size="mini" clearable placeholder="担当" />
      </el-form-item>
      <el-form-item label="调库号" size="mini">
        <el-input v-model="queryForm.transNo" size="mini" clearable placeholder="调库号" />
      </el-form-item>
      <el-form-item label="营业所" size="mini">
        <department @handleScopeChange="handleDeptNoChange" />
      </el-form-item>
      <el-form-item label="群代码" size="mini">
        <el-input v-model="queryForm.groupCustomerNo" size="mini" clearable placeholder="群代码" />
      </el-form-item>
      <el-form-item label="PPL" size="mini">
        <el-input v-model="queryForm.ppl" size="mini" clearable placeholder="ppl" />
      </el-form-item>
      <el-form-item label="项目号" size="mini">
        <el-input v-model="queryForm.projectCode" style="width: 150px" size="mini" clearable placeholder="项目号" />
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
      <el-form-item label="决算日期" size="mini">
        <el-date-picker
          v-model="queryDate"
          :picker-options="pickerOptions"
          :default-time="['00:00:00', '23:59:59']"
          type="daterange"
          align="right"
          size="small"
          unlink-panels
          style="width: 230px"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"/>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="queryForm.transQtyGtZero" label="清算数大于0" size="mini" border/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="initData">查询</el-button>
        <el-button v-loading="exportLoading" type="primary" icon="el-icon-upload2" size="mini" @click="exportPreOrderApplyDetail">导出</el-button>
      </el-form-item>
      <!-- <el-button :loading="handleTransactionLoading" type="primary" icon="el-icon-check" size="mini" @click="handleTransaction">处理</el-button> -->
    </el-form>
    <el-table
      v-loading="listLoading"
      ref="multipleTable"
      :data="preOrderDetail"
      :cell-style="{ padding: '5px' }"
      size="mini"
      border
      stripe
      style="width: 100%;margin-top: 5px"
      height="60vh"
      @selection-change="handleSelectionChange">
      <!-- 表头字段 -->
      <el-table-column type="selection" width="55" />
      <el-table-column prop="companyName" label="分公司" width="100" show-overflow-tooltip/>
      <el-table-column prop="parentName" label="营业部/行业" width="100" show-overflow-tooltip/>
      <el-table-column prop="salesName" label="营业所/子行业" width="100" show-overflow-tooltip/>
      <el-table-column prop="warehouseCode" label="库房" show-overflow-tooltip/>
      <el-table-column prop="inventoryTypeCode" label="库存类型" width="130" show-overflow-tooltip/>
      <el-table-column prop="modelNo" label="型号" width="150" show-overflow-tooltip/>
      <el-table-column prop="customerNo" label="客户" show-overflow-tooltip/>
      <el-table-column prop="groupCustomerNo" label="群代码" show-overflow-tooltip/>
      <el-table-column prop="ppl" label="PPL" show-overflow-tooltip/>
      <el-table-column prop="projectCode" label="PJ" show-overflow-tooltip/>
      <el-table-column prop="accountApplyNo" label="决算申请号" show-overflow-tooltip/>
      <el-table-column prop="applyNo" label="申请号" show-overflow-tooltip/>
      <el-table-column prop="applyItemNo" label="项号" show-overflow-tooltip/>
      <el-table-column prop="orderNo" label="订单号" width="130" show-overflow-tooltip/>
      <el-table-column prop="roQty" label="入库数量"/>
      <el-table-column prop="roDate" label="入库日期" width="100" show-overflow-tooltip/>
      <el-table-column prop="avaQty" label="有效在库数" width="100"/>
      <el-table-column prop="requirementDate" label="需求日期" width="90"/>
      <el-table-column prop="countDate" label="决算日期" width="90" show-overflow-tooltip/>
      <el-table-column prop="statusName" label="决算状态"/>
      <el-table-column prop="pushQty" label="推送决算数" width="100"/>
      <!-- <el-table-column prop="approveCountQty" label="审批中数"/> -->
      <el-table-column prop="sureAccountQty" label="确认决算数" width="100"/>
      <el-table-column prop="accountDesc" label="决算说明" show-overflow-tooltip/>
      <el-table-column prop="delayQty" label="确认延期数" width="100"/>
      <el-table-column prop="delayDate" label="延期日期" width="90"/>
      <el-table-column prop="delayDesc" label="延期说明" show-overflow-tooltip/>
      <el-table-column prop="factDelayQty" label="延期中数"/>
      <el-table-column prop="transQty" label="清算数"/>
      <el-table-column prop="transNo" label="调库号"/>
      <el-table-column prop="eprice" label="E价格"/>
      <el-table-column prop="eamount" label="E金额"/>
      <el-table-column prop="binStr" label="BIN"/>
      <el-table-column prop="charger" label="担当"/>
    </el-table>
    <!-- 分页工具 -->
    <pagination
      v-show="queryForm.total > 0"
      :total="queryForm.total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="queryForm.pageNum"
      :limit.sync="queryForm.pageSize"
      @pagination="listPreOrderApplyDetail"
    />
    <el-dialog :visible.sync="handPreAccountOrderDialog" :close-on-click-modal="false" title="处理">
      <el-form :inline="true" :model="handPreAccountForm">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="库房:">
              {{ handPreAccountForm.warehouseCode }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="库存类型:">
              {{ handPreAccountForm.inventoryTypeCode }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="型号:">
              {{ handPreAccountForm.modelNo }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="客户:">
              {{ handPreAccountForm.customerNo }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="群代码:">
              {{ handPreAccountForm.groupCustomerNo }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="PPL:">
              {{ handPreAccountForm.ppl }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="P J :">
              {{ handPreAccountForm.projectCode }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="有效在库数:">
              {{ handPreAccountForm.avaQty }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="确认决算数:">
          <el-input v-model="handPreAccountForm.sureAccountQty" style="width: 100px" size="mini"/>
        </el-form-item>
        <el-form-item label="决算说明:">
          <el-input v-model="handPreAccountForm.accountDesc" type="textarea" style="width: 400px" />
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="handPreAccountOrderDialog = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="handlePreOrderAccount()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import Warehouse from '@/components/Warehouse'
import { listWarehouseNoWT, getDictDataByPid } from '@/api/common/dict'
import { findInventoryType } from '@/api/warehouseManage'
import { listPreOrderApplyDetail, exportPreOrderApplyDetail, handlePreOrderAccountByIds } from '@/api/stock/preorder'
export default {
  components: { Pagination, Department, Warehouse },
  data() {
    return {
      queryForm: {
        deptNo: [],
        modelNo: '',
        applyNo: '',
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
        transNo: '',
        transQtyGtZero: true,
        pageSize: 20,
        total: 0
      },
      aaa: 'wwww',
      handPreAccountOrderDialog: false,
      handPreAccountForm: {
        warehouseCode: '',
        inventoryTypeCode: '',
        modelNo: '',
        customerNo: '',
        groupCustomerNo: '',
        ppl: '',
        projectCode: '',
        avaQty: '',
        sureAccountQty: '',
        accountDesc: ''
      },
      handlePreOrderAccountParam: {
        ids: [],
        optUser: ''
      },
      handleTransactionLoading: false,
      searchMoreForm: false,
      exportLoading: false,
      listLoading: false,
      inventoryTypeCodeList: [],
      warehouseData: [],
      preOrderDetail: [],
      queryDate: [],
      multipleSelection: [],
      statusDesc: [],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  created() {
    this.findInventoryType()
    this.getWareHouseListData()
    this.listStatusDesc()
    this.initData()
  },
  methods: {
    handlePreOrderAccount() {},
    handleDeptNoChange(val) {
      console.log('所选部门=>', val)
      this.queryForm.deptNos = val
    },
    handleWarhouseChange(val) {
      console.log('所选=>', val)
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
        } else {
          return code
        }
      }
    },
    listStatusDesc() {
      getDictDataByPid('1097').then(result => {
        this.statusDesc = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    getStatusDescFormatter: function(row) {
      console.log('row ==> ', row)
      const code = row.status.toString()
      for (const item of this.statusDesc) {
        if (item.code === code) {
          return item.codeName
        } else {
          return code
        }
      }
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
      if (this.queryDate != null && this.queryDate.length !== 0) {
        this.queryForm.beginDateStr = this.dayjs(this.queryDate[0]).format('YYYY-MM-DD')
        this.queryForm.endDateStr = this.dayjs(this.queryDate[1]).format('YYYY-MM-DD')
      } else {
        this.queryForm.beginDateStr = ''
        this.queryForm.endDateStr = ''
      }
      this.listPreOrderApplyDetail()
    },
    listPreOrderApplyDetail() {
      if (this.listLoading) {
        return
      }
      this.listLoading = true
      this.preOrderDetail = []
      console.log('this.queryForm => ', this.queryForm)
      listPreOrderApplyDetail(this.queryForm).then(res => {
        if (res.success) {
          this.preOrderDetail = res.content.list
          this.queryForm.total = res.content.total
        } else {
          this.$message.error(res.message)
        }
        this.listLoading = false
      }).catch(error => {
        this.$message.error(error)
        this.listLoading = false
      })
    },
    exportPreOrderApplyDetail() {
      if (this.exportLoading) {
        return
      }
      this.exportLoading = true
      const expdate = '先行在库决算详细明细数据' + this.dayjs(new Date()).format('YYYYMMDDHHmmss')
      this.fileName = expdate + '.xlsx'
      exportPreOrderApplyDetail(this.queryForm).then(res => {
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
    handleTransaction() {
      this.handPreAccountOrderDialog = true
      this.handleTransactionLoading = true
      this.handlePreOrderAccountParam.ids = []
      const statusArr = []
      if (this.multipleSelection.length > 0) {
        this.multipleSelection.forEach((row, index) => {
          this.handlePreOrderAccountParam.ids.push(row.id)
          statusArr.push(row.status)
        })
      }
      const uniqueArr = [...new Set(statusArr)]
      console.log('uniqueArr=>', uniqueArr)
      if (uniqueArr.length > 1) {
        this.$message.error('批次处理时,请选择相同状态的数据')
        this.handleTransactionLoading = false
        return
      }
      if (this.multipleSelection.length > 0 && uniqueArr[0] === 6) {
        this.$message.error('待处理的数据,不支持批次处理')
        this.handleTransactionLoading = false
        return
      }
      if (!this.handlePreOrderAccountParam.ids || this.handlePreOrderAccountParam.ids.length === 0) {
        this.$message.error('请选择处理项。')
        this.handleTransactionLoading = false
        return
      }
      if (uniqueArr[0] !== 3 || uniqueArr[0] !== 6) {
        this.$message.error('待处理以及待清算状态的数据才可进行处理操作')
        this.handleTransactionLoading = false
        return
      }
      this.handlePreOrderAccountParam.optuser = this.$store.getters.position.psnsmcId
      handlePreOrderAccountByIds(this.handlePreOrderAccountParam).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
        this.listLoading = false
      }).catch(error => {
        console.error(error)
        this.listLoading = false
      })
    }
  }
}
</script>

<style scoped>

</style>
