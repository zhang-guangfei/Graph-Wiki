<template>
  <div>
    <el-form ref="queryForm" :model="queryForm" :inline="true" size="mini" label-width="0" >
      <el-form-item label="" prop="applyNo">
        <el-input v-model="queryForm.applyNo" placeholder="申请号" clearable @change="changeApplyNo" />
      </el-form-item>
      <el-form-item label="" prop="needModelNo">
        <el-input v-model="queryForm.needModelNo" placeholder="所需型号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="needForOrderNo">
        <el-input v-model="queryForm.needForOrderNo" placeholder="订单号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="billNo">
        <el-input v-model="queryForm.billNo" placeholder="票号" clearable/>
      </el-form-item>
      <el-form-item label="" prop="deptNo">
        <el-input v-model="queryForm.deptNo" placeholder="申请部门" clearable/>
      </el-form-item>
      <el-form-item label="" prop="status">
        <el-select v-model="queryForm.status" placeholder="状态" style="width:100%" clearable >
          <el-option v-for="item in applyStatusList" :key="item.code" :label="item.codeName" :value="item.code" />
        </el-select>
      </el-form-item>
      <el-form-item label="" prop="assembleType">
        <el-select v-model="queryForm.assembleType" placeholder="申请目的" style="width: 100%" clearable >
          <el-option v-for="item in assembleTypeList" :key="item.code" :label="item.codeName" :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="">
        <el-col :span="4">
          <el-select v-model="queryForm.dateType" clearable placeholder="日期类型" style="width:100%" >
            <el-option v-for="item in dateTypeList" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-col>
        <el-col :span="10">
          <el-form-item label="" prop="fromDate">
            <el-date-picker v-model="queryForm.fromDate" type="date" placeholder="起始日期" value-format="yyyy-MM-dd" style="width:100%" clearable/>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="" prop="toDate">
            <el-date-picker v-model="queryForm.toDate" type="date" placeholder="结束日期" value-format="yyyy-MM-dd" style="width:100%" clearable/>
          </el-form-item>
        </el-col>
      </el-form-item>
      <el-form-item label="">
        <el-button v-permission="['496485']" type="primary" icon="el-icon-search" @click="onQuery">查询</el-button>
      </el-form-item>
    </el-form>

    <el-row>
      <el-button v-permission="['325476']" type="primary" size="mini" icon="el-icon-check" @click="beforeHandle('approve')">审核确认</el-button>
      <el-button v-permission="['752635']" type="primary" size="mini" @click="beforeHandle('handle')" >批量执行处理</el-button>
      <el-button v-permission="['752635']" type="danger" size="mini" icon="el-icon-delete" @click="cancelApply">取消申请</el-button>
    </el-row>
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
      style="width: 100%"
      @selection-change="handleSelectionChange">>
      <el-table-column
        type="selection"
        align="center"
        width="50"/>
      <el-table-column
        prop="applyNo"
        label="申请号"
        width="140" >
        <template slot-scope="scope">
          <span>{{ scope.row.applyNo }}</span>
          <el-button type="text" size="mini" icon="el-icon-view" @click="goStockAssemblyDetailPage(scope.row)"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="deptNo"
        label="申请部门"
        width="110" />
      <el-table-column
        prop="status"
        label="状态"
        width="80">
        <template slot-scope="scope">
          <span>{{ getApplyStatus(scope.row.status) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="needForOrderNo"
        label="订单号"
        width="90" />
      <el-table-column
        prop="needModelNo"
        label="型号"
        width="120" />
      <el-table-column
        prop="dlvDate"
        label="希望交货期"
        width="95" />
      <el-table-column
        prop="assembleType"
        label="申请目的"
        width="90">
        <template slot-scope="scope">
          <span>{{ getAssembleType(scope.row.assembleType) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        prop="applyDate"
        label="申请日期"
        width="95" />
      <el-table-column
        prop="applyPsn"
        label="申请人"
        width="80" />
      <el-table-column
        prop="approveDate"
        label="审批日期"
        width="95" />
      <el-table-column
        show-overflow-tooltip
        prop="answerText"
        label="回复内容"
        width="100" />
      <el-table-column
        prop="billNo"
        label="票号"
        width="80" />
      <el-table-column
        show-overflow-tooltip
        prop="remark"
        label="备注"
        width="100" />
      <el-table-column
        prop="transDate"
        label="调库时间"
        width="95" />
      <el-table-column
        prop="transPsn"
        label="调库人"
        width="100" />
      <el-table-column
        prop="receiveTime"
        label="接收时间"
        width="95" />
      <el-table-column
        prop="receivePsn"
        label="接收人"
        width="80" />
    </el-table>
    <pagination v-show="total>0" :total="total" :page-sizes="[20, 50, 100, 200, 500]" :page.sync="queryForm.pageNum" :limit.sync="queryForm.pageSize" style="margin-top: 0px" @pagination="initData" />

    <!-- 批量审核确认弹窗 -->
    <el-dialog :visible.sync="dialogApproveVisible" title="审核确认" width="320px" >
      <span style="font-size: 14px; color: #606266; font-weight: 700;">回复说明:</span>
      <el-input v-model="answerText" :maxlength="50" type="textarea" show-word-limit/>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="approveLoading" type="error" size="mini" @click="onApprove('4')">退 回</el-button>
        <el-button :loading="approveLoading" type="primary" size="mini" @click="onApprove('1')">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 批量执行处理弹窗 -->
    <el-dialog :visible.sync="dialogHandleVisible" title="执行处理" width="320px" >
      <span style="font-size: 14px; color: #606266; font-weight: 700;">回复说明:</span>
      <el-input v-model="answerText" :maxlength="50" type="textarea" show-word-limit/>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="handleLoading" type="error" size="mini" @click="onHandle('4')">退 回</el-button>
        <el-button :loading="handleLoading" type="primary" size="mini" @click="onHandle('1')">处 理</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { getDataCodesTree } from '@/api/common/dict'
import { listApply, approveApply, handleApply, removeApply } from '@/api/stock/stockAssembly'

export default {
  name: 'StockAssemblyQuery',
  components: { Pagination },
  data() {
    return {
      queryForm: {
        applyNo: '',
        needModelNo: '',
        needForOrderNo: '',
        billNo: '',
        deptNo: '',
        status: '',
        assembleType: '',
        dateType: 1,
        fromDate: '',
        toDate: '',
        pageNum: 1,
        pageSize: 20
      },
      applyStatusClassCode: 2007,
      assembleTypeClassCode: 2008,
      applyStatusList: [],
      assembleTypeList: [],
      total: 0,
      tableLoading: false,
      tableData: [],
      selection: [],
      applyIds: [],
      deptNoList: [],
      dateTypeList: [
        { value: 1, label: '申请日期' },
        { value: 2, label: '处理日期' }
      ],
      dialogApproveVisible: false,
      dialogHandleVisible: false,
      answerText: '',
      approveLoading: false,
      handleLoading: false
    }
  },
  created() {
    this.initApplyStatusList()
    this.initAssembleTypeList()
  },
  methods: {
    async initApplyStatusList() {
      getDataCodesTree(this.applyStatusClassCode).then(res => {
        if (res.success) {
          this.applyStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initAssembleTypeList() {
      getDataCodesTree(this.assembleTypeClassCode).then(res => {
        if (res.success) {
          this.assembleTypeList = res.content
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
    onApprove(handleType) {
      this.approveLoading = true
      const params = {
        'applyIds': this.applyIds,
        'answerText': this.answerText,
        'handleType': handleType
      }
      approveApply(params).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success(res.content)
          this.dialogApproveVisible = false
        } else {
          this.$message.error(res.message)
        }
        this.approveLoading = false
      }).catch(error => {
        console.error(error)
      })
    },
    onHandle(handleType) {
      this.handleLoading = true
      const params = {
        'applyIds': this.applyIds,
        'answerText': this.answerText,
        'handleType': handleType
      }
      handleApply(params).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success(res.content)
          this.dialogHandleVisible = false
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.handleLoading = false
      }).catch(error => {
        console.error(error)
      })
    },
    beforeHandle(type) {
      if (!this.selection || this.selection.length === 0) {
        this.$message.warning('请选择申请')
        return
      }
      this.answerText = ''
      if (type === 'approve') {
        this.dialogApproveVisible = true
      }
      if (type === 'handle') {
        this.dialogHandleVisible = true
      }
    },
    cancelApply() {
      if (!this.selection || this.selection.length === 0) {
        this.$message.warning('请选择申请')
        return
      }
      const params = { 'applyIds': this.applyIds.join(',') }
      removeApply(params).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
        this.initData()
      })
    },
    handleSelectionChange(val) {
      this.selection = val
      const applyIds = []
      if (val.length > 0) {
        this.selection.forEach((row, index) => {
          applyIds.push(row.id)
        })
      }
      this.applyIds = applyIds
    },
    changeApplyNo() {
      if (this.queryForm.applyNo) {
        this.queryForm.status = ''
      }
    },
    goStockAssemblyDetailPage(row) {
      const params = {
        applyId: row.id
      }
      this.$emit('goApplyDetailPageEvent', params)
    },
    getApplyStatus(val) {
      const obj = this.applyStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getAssembleType(val) {
      const obj = this.assembleTypeList.filter(item => item.code === val)[0]
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
