<template>
  <div class="container">
    <!-- 查询表单区域 -->
    <div class="query-form">
      <div class="form-row">
        <div class="form-item">
          <label>准备订单号：</label>
          <vxe-input v-model="queryForm.orderFno" clearable placeholder="请输入" />
        </div>
        <div class="form-item">
          <label>申请号：</label>
          <vxe-input v-model="queryForm.applyNo" clearable placeholder="请输入"/>
        </div>
        <div class="form-item">
          <label>备库地点：</label>
          <vxe-select v-model="queryForm.manuFactSupplierCode" clearable placeholder="下拉菜单">
            <vxe-option v-for="item in suppilyList" :key="item.id" :value="item.id" :label="item.name"/>
          </vxe-select>
        </div>
        <div class="form-item">
          <label>型号：</label>
          <vxe-input v-model="queryForm.modelNo" clearable placeholder="请输入"/>
        </div>
        <div class="form-item">
          <label>状态：</label>
          <vxe-select v-model="queryForm.statusList" multiple clearable placeholder="下拉菜单">
            <vxe-option v-for="item in statusDesc" :key="item.code" :value="item.code" :label="item.codeName"/>
          </vxe-select>
        </div>
        <div class="form-item">
          <label>客户代码：</label>
          <vxe-input v-model="queryForm.customerNo" clearable placeholder="请输入"/>
        </div>
        <div class="form-item">
          <label>PPL号：</label>
          <vxe-input v-model="queryForm.ppl" clearable placeholder="请输入"/>
        </div>
      </div>
      <div class="form-row">
        <div class="form-item">
          <label>制造接单号：</label>
          <vxe-input v-model="queryForm.manuOrderNo" clearable placeholder="请输入" />
        </div>
        <div class="form-item">
          <label>申请部门：</label>
          <department @handleScopeChange="handleDeptNoChange" />
        </div>
        <div class="form-item">
          <label>项目号：</label>
          <vxe-input v-model="queryForm.projectCode" clearable placeholder="请输入"/>
        </div>
        <div class="form-item">
          <label>客户群代码：</label>
          <vxe-input v-model="queryForm.groupCustomerNo" clearable placeholder="请输入"/>
        </div>
        <div class="form-item date-range">
          <label>申请日期：</label>
          <vxe-input v-model="queryForm.startApplyDate" placeholder="开始" clearable type="date"/>
          <span class="separator">-</span>
          <vxe-input v-model="queryForm.endApplyDate" placeholder="结束" clearable type="date"/>
        </div>
        <div class="form-item button-group">
          <vxe-button @click="handleQuery">查询</vxe-button>
          <vxe-button v-loading="exportLoading" @click="exportPrepareOrder">导出</vxe-button>
        </div>
      </div>
    </div>

    <!-- 操作按钮区域 -->
    <div class="action-buttons">
      <!-- <vxe-button @click="handleCreate">新建</vxe-button> -->
      <vxe-button @click="handleEdit">编辑</vxe-button>
      <!-- <vxe-button @click="handleCancel">取消</vxe-button> -->
      <vxe-button @click="setDlvVal()">设置交货期天数</vxe-button>
    </div>

    <!-- 主表格区域 -->
    <div class="main-table">
      <vxe-table
        ref="mainTable"
        :data="tableData"
        :cell-style="{ padding: '0px 1px', height: '33px' }"
        max-height="380"
        border
        highlight-current-row
        @cell-click="cellClickEvent"
        @checkbox-change="selectChangeEvent"
      >
        <vxe-column type="checkbox" width="60" />
        <vxe-column field="orderFno" title="准备订单号" show-overflow resizable width="120" />
        <vxe-column field="manuFactSupplierCodeName" title="备库地点" show-overflow resizable width="150" />
        <vxe-column field="modelNo" title="型号" show-overflow resizable width="200" />
        <vxe-column field="statusName" title="状态" resizable show-overflow width="100" />
        <vxe-column field="sendTime" title="采购日期" show-overflow resizable width="160" />
        <vxe-column field="applyQty" title="申请数量" resizable show-overflow width="100" />
        <vxe-column field="remainQty" title="残数" resizable show-overflow width="80" />
        <vxe-column field="preQty" title="已预约数量" resizable show-overflow width="110" />
        <vxe-column field="useQty" title="已使用数量" resizable show-overflow width="110" />
        <vxe-column field="manuOrderNo" title="制造接单号" show-overflow resizable width="180" />
        <vxe-column field="supplierOperateTime" title="制造接单时间" resizable show-overflow width="130" />
        <vxe-column field="dlvDate" title="客户交货期" show-overflow resizable width="120" />
        <vxe-column field="hopeExportDate" title="指定制造出荷日" show-overflow resizable width="150" />
        <vxe-column field="manuDlvDate" title="返信时间" show-overflow resizable width="120" />
        <vxe-column field="supplierExpinvCode" title="出库区分" show-overflow resizable width="120" />
        <vxe-column field="verificationDate" title="核销日期" resizable show-overflow width="120" />
        <vxe-column field="terminateDate" title="终止时间" resizable show-overflow width="120" />
        <vxe-column field="finalAccountDate" title="决算时间" resizable show-overflow width="120" />
        <vxe-column field="liquidationDate" title="清算时间" resizable show-overflow width="120" />
        <vxe-column field="inventoryTypeCode" title="库存类型" resizable show-overflow width="120" />
        <vxe-column field="customerNo" title="客户代码" resizable width="120" />
        <vxe-column field="availableCustomers" title="可用客户" show-overflow resizable width="130" />
        <vxe-column field="ppl" title="PPL号" resizable show-overflow width="180" />
        <vxe-column field="projectCode" title="项目号" resizable show-overflow width="180" />
        <vxe-column field="groupCustomerNo" title="客户群代码" resizable width="120" />
        <vxe-column field="rohs" title="ROHS" resizable show-overflow width="120" />
        <vxe-column field="remark" title="备注" resizable show-overflow width="120" />
        <vxe-column field="applyPsn" title="申请人" show-overflow resizable width="120" />
        <vxe-column field="applyNo" title="申请号" resizable width="120" />
        <vxe-column field="applyDeptNo" title="申请部门" show-overflow resizable width="120" />
        <vxe-column field="applyTime" title="申请日期" resizable width="160" />
        <vxe-column field="updateTime" title="更新时间" resizable width="160" />
      </vxe-table>
      <!-- 分页工具 -->
      <pagination
        v-show="total > 0"
        :total="total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="queryForm.pageNum"
        :limit.sync="queryForm.pageSize"
        @pagination="handleQuery"
      />
    </div>

    <!-- 标签页区域 -->
    <div class="tabs">
      <div class="tab-headers">
        <div
          v-for="tab in tabs"
          :key="tab.key"
          :class="['tab-header', { active: activeTab === tab.key }]"
          @click="handleTabClick(tab.key)"
        >
          {{ tab.label }}
        </div>
      </div>
      <div class="tab-content">
        <!-- 拆分明细 -->
        <div v-show="activeTab === 'detail'">
          <vxe-table
            :data="detailData"
            :span-method="mergeRowMethod"
            :cell-style="{ padding: '0px 1px', height: '33px' }"
            border
          >
            <vxe-column type="seq" title="序号" resizable width="160" />
            <vxe-column field="modelNo" title="型号" resizable width="160" />
            <vxe-column field="quantity" title="数量" resizable width="160" />
          </vxe-table>
        </div>
        <!-- 核销记录 -->
        <div v-show="activeTab === 'verify'">
          <vxe-table
            :data="verifyData"
            :cell-style="{ padding: '0px 1px', height: '33px' }"
            border
          >
            <vxe-column type="seq" title="序号" resizable width="160" />
            <vxe-column field="poOrderNo" title="采购单号" resizable width="160" />
            <vxe-column field="poItemNo" title="采购单项号" resizable width="160" />
            <vxe-column field="quantity" title="采购数量" resizable width="160" />
            <vxe-column field="endUser" title="采购最终用户" resizable width="160" />
            <vxe-column field="useTime" title="采购日期" resizable width="160" />
          </vxe-table>
        </div>

        <!-- 决算清单 -->
        <div v-show="activeTab === 'info'">
          <vxe-table
            :data="accountData"
            :cell-style="{ padding: '0px 1px', height: '33px' }"
            border
          >
            <vxe-column type="seq" title="序号" resizable width="160" />
            <vxe-column field="modelNo" title="待决算型号" resizable width="160" />
            <vxe-column field="quantity" title="待决算数量" resizable width="160" />
            <vxe-column title="操作" width="160" show-overflow>
              <template #default="{ row }">
                <vxe-button mode="text" icon="vxe-icon-edit" @click="editEvent(row)"/>
                <vxe-button mode="text" icon="vxe-icon-delete"/>
              </template>
            </vxe-column>
          </vxe-table>
        </div>

        <!-- 清算结果 -->
        <div v-show="activeTab === 'liquidation'">
          <vxe-table
            :data="liquidationData"
            :cell-style="{ padding: '0px 1px', height: '33px' }"
            border
          >
            <vxe-column type="seq" title="序号" resizable width="160" />
            <vxe-column field="modelNo" title="清算型号" resizable width="160" />
            <vxe-column field="handleWayName" title="清算方式" resizable width="160" />
            <vxe-column field="quantity" title="清算数量" resizable width="160" />
          </vxe-table>
        </div>
      </div>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" title="编辑" style="margin-left: 55vh;width: 100vh;">
      <el-form :model="editForm" label-position="left" size="mini">
        <el-form-item label="准备订单号">
          <el-input v-model="editForm.orderFno" disabled style="width: 150px" autocomplete="off"/>
        </el-form-item>
        <el-form-item label="可用客户">
          <el-input :rows="2" v-model="editForm.availableCustomers" style="width: 200px" type="textarea" placeholder="多个客户用逗号隔开" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="sureEdit()">确 定</el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dlvDialogVisible" title="设置交货期天数" width="400px">
      <el-form :model="dlvForm" label-position="left" size="mini">
        <el-form-item :rules="[{ required: true, message: '请输入交货期天数', trigger: 'blur' }]" label="交货期天数">
          <el-input
            v-model="dlvForm.dlvDays"
            type="number"
            placeholder="请输入正整数"
            style="width: 200px"
            min="1"
            @input="validateDlvDays"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dlvDialogVisible = false">取 消</el-button>
        <el-button :loading="dlvLoading" size="mini" type="primary" @click="sureSetDlv">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getSuppily } from '@/api/intercept'
import Department from '@/components/Department'
import { queryPrepareOrderList, exportPrepareOrder, editPrepareOrderAvailableUser, getPrepareOrderBomDetail, getPrepareOrderVerifyInfo, getPrepareOrderAccountInfo,
  getPrepareOrderLiquidationInfo, getDlvDays, updateDlvDays } from '@/api/prepareOrder/prepareOrder'
import { getDictDataByPid } from '@/api/common/dict'
import Pagination from '@/components/Pagination'

export default {
  name: 'PrepareOrderManage',
  components: { Pagination, Department },
  data() {
    return {
      queryForm: {
        orderFno: '',
        manuFactSupplierCode: '',
        modelNo: '',
        status: '',
        statusList: [],
        customerNo: '',
        applyNo: '',
        applyDeptNo: '',
        applyDeptNos: [],
        startApplyDate: '',
        endApplyDate: '',
        ppl: '',
        projectCode: '',
        groupCustomerNo: '',
        pageNum: 1,
        pageSize: 20
      },
      editForm: {
        orderFno: '',
        availableCustomers: '',
        optUser: ''
      },
      dlvForm: {
        dlvDays: ''
      },
      total: 1,
      exportLoading: false,
      dialogFormVisible: false,
      dlvDialogVisible: false,
      dlvLoading: false,
      records: [],
      statusDesc: [],
      suppilyList: [],
      tableData: [],
      tabs: [
        { key: 'detail', label: '拆分明细' },
        { key: 'verify', label: '核销记录' },
        { key: 'info', label: '决算清单' },
        { key: 'liquidation', label: '清算结果' }
      ],
      activeTab: 'detail',
      detailData: [],
      verifyData: [],
      accountData: [],
      liquidationData: []
    }
  },
  mounted() {
    // this.handleQuery()
    // this.listStatusDesc()
    // this.getSuppily()
  },
  created() {
    this.handleQuery()
    this.listStatusDesc()
    this.getSuppily()
  },
  methods: {
    sureSetDlv() {
      if (!this.dlvForm.dlvDays) {
        this.$message.warning('请输入交货期天数')
        return
      }
      const dlvDays = Number(this.dlvForm.dlvDays)
      if (!Number.isInteger(dlvDays) || dlvDays <= 0) {
        this.$message.warning('交货期天数必须为正整数')
        return
      }
      this.dlvLoading = true
      updateDlvDays(this.dlvForm.dlvDays).then(res => {
        if (res.success) {
          this.$message.success('设置成功')
        } else {
          this.$message.error(res.message)
        }
      }).catch(err => {
        this.$message.error(err.message)
      }).finally(() => {
        this.dlvLoading = false
        this.dlvDialogVisible = false
      })
    },
    validateDlvDays(val) {
      this.dlvForm.dlvDays = val.replace(/[^0-9]/g, '')
    },
    setDlvVal() {
      getDlvDays().then(res => {
        if (res.success) this.dlvForm.dlvDays = res.content
      })
      this.dlvDialogVisible = true
    },
    editEvent() {
      alert('二期需求,预留功能')
    },
    mergeRowMethod({ row, rowIndex, column, data }) {
      const mergeConfig = { backUpMethodName: true }
      const field = column.field
      if (mergeConfig[field]) {
        const prevRow = data[rowIndex - 1]
        if (prevRow && prevRow[field] === row[field]) {
          return { rowspan: 0, colspan: 0 }
        } else {
          let r = 1
          for (let i = rowIndex + 1; i < data.length && data[i][field] === row[field]; i++) r++
          return { rowspan: r, colspan: 1 }
        }
      }
    },
    handleTabClick(tabKey) {
      this.activeTab = tabKey
    },
    sureEdit() {
      editPrepareOrderAvailableUser(this.editForm).then(res => {
        if (res.success) {
          this.$message.success('编辑成功')
          this.dialogFormVisible = false
          this.handleQuery()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    selectChangeEvent() {
      this.records = this.$refs.mainTable.getCheckboxRecords()
    },
    exportPrepareOrder() {
      if (this.exportLoading) return
      this.exportLoading = true
      const name = '准备订单' + this.dayjs().format('YYYYMMDDHHmmss') + '.xlsx'
      exportPrepareOrder(this.queryForm).then(res => {
        const blob = new Blob([res], { type: res.type })
        const a = document.createElement('a')
        a.href = URL.createObjectURL(blob)
        a.download = name
        a.click()
        URL.revokeObjectURL(a.href)
      }).finally(() => {
        this.exportLoading = false
      })
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data.filter(e => e.supplierClass === 'MANU' || e.supplierClass === 'GZ')
      })
    },
    handleDeptNoChange(val) {
      this.queryForm.applyDeptNos = val
    },
    listStatusDesc() {
      getDictDataByPid('2093').then(r => { this.statusDesc = r.content })
    },
    handleQuery() {
      console.log(this.queryForm)
      queryPrepareOrderList(this.queryForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
      })
    },
    handleCreate() { alert('新建') },
    handleEdit() {
      if (this.records.length !== 1) return this.$message.warning('请选择一条数据')
      const row = this.records[0]
      this.editForm = {
        orderFno: row.orderFno,
        availableCustomers: row.availableCustomers,
        optUser: this.$store.getters.position.psnsmcId
      }
      this.dialogFormVisible = true
    },
    handleCancel() { alert('取消操作') },
    cellClickEvent({ row }) {
      getPrepareOrderBomDetail(row.orderFno).then(r => r.success && (this.detailData = r.content))
      getPrepareOrderVerifyInfo(row.orderFno).then(r => r.success && (this.verifyData = r.content))
      getPrepareOrderAccountInfo(row.orderFno).then(r => r.success && (this.accountData = r.content))
      getPrepareOrderLiquidationInfo(row.orderFno).then(r => r.success && (this.liquidationData = r.content))
    }
  }
}
</script>

<style scoped>
.container {
  padding: 15px;
  font-family: 'Microsoft YaHei', sans-serif;
}
.query-form {
  background: #f5f7fa;
  padding: 12px 10px;
  border-radius: 4px;
  margin-bottom: 15px;
}
.form-row {
  display: flex;
  flex-wrap: nowrap !important;
  align-items: center;
  gap: 6px;
  margin-bottom: 8px;
}
.form-row:last-child {
  margin-bottom: 0;
}
.form-item {
  display: flex;
  align-items: center;
  flex-shrink: 0;
  height: 36px;
}
.form-item label {
  min-width: 68px;
  margin-right: 4px;
  text-align: right;
  font-size: 13px;
}
.date-range {
  display: flex;
  align-items: center;
}
.separator {
  margin: 0 4px;
}
.button-group {
  margin-left: auto;
  display: flex;
  gap: 6px;
}
.button-group .vxe-button,
.action-buttons .vxe-button {
  color: #fff;
  background: #337ab7;
  padding: 6px 10px;
  font-size: 13px;
}
.action-buttons {
  margin-bottom: 12px;
  display: flex;
  gap: 8px;
}
.main-table {
  margin-bottom: 15px;
}
.tabs {
  border: 1px solid #dcdfe6;
  border-radius: 4px;
}
.tab-headers {
  display: flex;
  background: #f5f7fa;
  border-bottom: 1px solid #dcdfe6;
}
.tab-header {
  padding: 10px 14px;
  cursor: pointer;
  border-right: 1px solid #dcdfe6;
  font-size: 13px;
}
.tab-header.active {
  background: #fff;
  color: #409eff;
  border-bottom: 2px solid #409eff;
}
.tab-content {
  padding: 10px;
}
.vxe-input,
.vxe-select {
  width: 155px !important;
}
.date-range .vxe-input {
  width: 170px !important;
}
</style>
