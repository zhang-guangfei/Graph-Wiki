<template>
  <el-container>
    <el-main>
      <el-form ref="form" :model="form" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="型号">
          <el-input v-model="form.modelNo" placeholder="请输入型号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="SHIKOMI号">
          <el-input v-model="form.shikomiNo" placeholder="请输入shikomi号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="申请号">
          <el-input v-model="form.applyNo" placeholder="请输入申请号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="申请和继续类型" prop="status">
          <el-select v-model="form.applyType" placeholder="请选择" style="width: 130px" clearable="">
            <el-option v-for="item in applyTypeData" :key="item.code" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-button v-permission="['169172']" type="primary" size="mini" style="margin-left: 10px;" @click="findList">查询</el-button>
        <el-button v-permission="['293303']" type="success" size="mini" style="margin-left: 10px;" @click="exportShikomiInspection">导出</el-button>
        <el-button v-permission="['293303']" size="mini" type="primary" @click="handleInsepct">点检处理</el-button>
        <el-row>
          <el-form-item label="行业代码">
            <el-input v-model="form.indCode" placeholder="请输入行业代码" clearable style="width: 150px" />
          </el-form-item>
          <el-form-item label="客户代码">
            <el-input v-model="form.customerNo" placeholder="请输入客户代码" clearable style="width: 150px" />
          </el-form-item>
          <el-form-item label="点检状态">
            <el-select v-model="form.inspectStatus" placeholder="请选择" style="width: 130px" clearable="">
              <el-option v-for="item in statusTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
            </el-select>
          </el-form-item>
          <el-form-item label="保管期限">
            <el-date-picker
              v-model="searchTime"
              type="daterange"
              align="right"
              unlink-panels
              style="width: 250px"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"/>
          </el-form-item>
        </el-row>
      </el-form>

      <el-dialog :visible.sync="inspectionDialog" :close-on-click-modal="false" title="点检处理" width="620px" >
        <el-form ref="inspec" :model="handleData" :rules="inspection" size="mini">
          <el-form-item label-width="200px" prop="handleType" label="处理类型">
            <el-select v-model="handleData.handleType" placeholder="请选择" style="width:200px" size="small">
              <el-option v-for="item in handleInspectOptions" :key="item.value" :value="item.value" :label="item.label" />
            </el-select>
          </el-form-item>
          <el-form-item label-width="200px" prop="inspectAnswerText" label="说明">
            <el-input :autosize="{ minRows: 2, maxRows: 4}" v-model="handleData.inspectAnswerText" type="textarea" style="width:200px"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="inspectionDialog = false">取 消</el-button>
          <el-button type="primary" @click="handleInspection()">确认</el-button>
        </div>
      </el-dialog>

      <el-table
        v-loading="listLoading"
        ref="multipleTable"
        :header-cell-style="{color:'#000000'}"
        :data="tableData"
        :default-sort = "{prop: 'inspectStatus', order: 'ascending'}"
        tooltip-effect="dark"
        class="multipleTable"
        border
        style="width: 100%"
        size="small"
        stripe
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55"/>
        <el-table-column
          align="center"
          prop="shikomiNo"
          label="SHIKOMI号"/>

        <el-table-column
          align="center"
          prop="modelNo"
          label="型号"/>

        <el-table-column
          align="center"
          prop="applyNo"
          label="申请号"/>

        <el-table-column
          :formatter="applyTypeFormatter"
          align="center"
          prop="applyType"
          label="申请类型"/>

        <el-table-column
          align="center"
          prop="applyQty"
          label="申请数量"/>

        <el-table-column
          align="center"
          prop="applicantNo"
          label="申请担当"/>

        <el-table-column
          align="center"
          prop="applicantName"
          label="申请担当名称"/>

        <el-table-column
          align="center"
          prop="deptNo"
          label="申请部门">
          <template slot-scope="scope">
            <span> {{ scope.row.deptName }} </span>
          </template>
        </el-table-column>

        <el-table-column
          :formatter="applyDateformatter"
          align="center"
          prop="applyDate"
          label="申请时间"/>

        <el-table-column
          align="center"
          prop="customerNo"
          label="客户代码"/>

        <el-table-column
          align="center"
          prop="indCode"
          label="行业代码"/>

        <el-table-column
          :formatter="inspectStatusformatter"
          sortable
          align="center"
          prop="inspectStatus"
          label="处理状态"/>

        <el-table-column
          align="center"
          prop="remainQty"
          label="残数"/>

        <el-table-column
          align="center"
          prop="cancelQty"
          label="取消数量"/>

        <el-table-column
          align="center"
          prop="customerQty"
          label="客户购买数量"/>

        <el-table-column
          align="center"
          prop="qtyNoord"
          label="客户购买数量"/>

        <el-table-column
          align="center"
          prop="repairQty"
          label="维修备件数"/>

        <el-table-column
          align="center"
          prop="expirationHandle"
          label="保管期限到期后"/>

        <el-table-column
          :formatter="retentionformatter"
          align="center"
          prop="retentionDurationDate"
          label="保留期限"/>
      </el-table>
      <el-pagination
        :page-sizes="[20, 50, 100, 200, 500]"
        :current-page="form.pageNum"
        :page-size="form.pageSize"
        :total="total"
        background
        style="margin-top: 10px"
        layout="total, sizes, prev, pager, next"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"/>
      <el-divider />
    </el-main>
  </el-container>
</template>

<script>
import { getDictDataByPid } from '@/api/common/dict'
import { listShikomiInspection, exportShikomiInspection, inspectProcess } from '@/api/stock/shikomi'
export default {
  name: 'Inspection',
  components: {

  },
  data() {
    return {
      listLoading: false,
      exportLoading: false,
      inspectionDialog: false,
      total: 0,
      searchTime: '',
      form: {
        modelNo: '',
        shikomiNo: '',
        applyType: '',
        applyNo: '',
        indCode: '',
        customerNo: '',
        inspectStatus: '',
        retentionStartDate: '',
        retentionEndDate: '',
        pageNum: 1,
        pageSize: 20
      },
      handleData: {
        handleType: '',
        inspectAnswerText: '',
        shikomiNo: '',
        modelNo: '',
        applyNo: '',
        id: ''
      },
      handleInspectOptions: [
        { value: 1, label: '已发供应商' },
        { value: 2, label: '供应商已中止' },
        { value: 3, label: '供应商不能中止' },
        { value: 4, label: '中止取消' }
      ],
      inspection: {
        handleType: [
          { required: true, message: '请选择处理类型', trigger: 'change' }
        ]
      },
      tableData: [],
      statusClassCode: '2052',
      applyTypeClassCode: '2053',
      statusTypeOptions: [],
      applyTypeData: []
    }
  },

  created() {
    this.iniData()
    this.findList()
  },
  methods: {
    findList() {
      this.listLoading = true
      this.setDateCondition()
      listShikomiInspection(this.form).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.searchTime === null) {
        this.form.retentionStartDate = ''
        this.form.retentionEndDate = ''
      } else {
        this.form.retentionStartDate = this.searchTime[0]
        this.form.retentionEndDate = this.searchTime[1]
      }
    },
    iniData() {
      getDictDataByPid(this.statusClassCode).then(result => {
        this.statusTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.applyTypeClassCode).then(result => {
        this.applyTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    exportShikomiInspection() {
      this.exportLoading = true
      this.$message.success('正在导出~')
      exportShikomiInspection(this.form).then(res => {
        const fileName = 'SHIKOMI中止和继续.xlsx'
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
    // 换页
    handlePageSizeChange(newSize) {
      this.form.pageSize = newSize
      this.findList()
    },
    handlePageChange(newCurrent) {
      this.form.pageNum = newCurrent
      this.findList()
    },
    handleInsepct() {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择一个点检')
        return
      }
      this.inspectionDialog = true
    },
    handleInspection() {
      this.$refs.inspec.validate((valid) => {
        if (valid) {
          this.handleData.shikomiNo = this.$refs.multipleTable.selection[0].shikomiNo
          this.handleData.modelNo = this.$refs.multipleTable.selection[0].modelNo
          this.handleData.applyNo = this.$refs.multipleTable.selection[0].applyNo
          this.handleData.id = this.$refs.multipleTable.selection[0].id
          inspectProcess(this.handleData).then(res => {
            if (res.success) {
              this.$message.success(res.content)
            } else {
              this.$message.error(res.message)
            }
          })
          this.inspectionDialog = false
          this.findList()
        } else {
          return false
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    applyDateformatter(row) {
      if (row.applyDate != null) {
        return this.dayjs(row.applyDate).format('YYYY-MM-DD')
      }
    },
    retentionformatter(row) {
      if (row.retentionDurationDate != null) {
        return this.dayjs(row.retentionDurationDate).format('YYYY-MM-DD')
      }
    },
    descFormatter(data, value) {
      if (value === null) {
        return value
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === value.toString()) {
          return item.codeName
        }
      }
      return value
    },
    applyTypeFormatter(row, column, cellValue, index, menu) {
      return this.descFormatter(this.applyTypeData, cellValue)
    },
    inspectStatusformatter(row, column, cellValue, index, menu) {
      return this.descFormatter(this.statusTypeOptions, cellValue)
    }
  }
}
</script>

<style scoped>

</style>
