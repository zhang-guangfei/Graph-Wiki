<template>
  <el-container>
    <el-main>
      <el-form ref="form" :model="form" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="调库单号">
          <el-input v-model="form.transNo" placeholder="调库单号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="调库类型">
          <!-- <el-input v-model="form.transType" placeholder="调库单号" clearable style="width: 100px" /> -->
          <el-select v-model="form.transType" style="width:100px" clearable>
            <el-option
              v-for="item in transTypeData"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label="型号">
          <el-input v-model="form.modelNo" placeholder="请输入型号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.warhouseType" style="width:100px">
            <el-option
              v-for="item in warhouseTypeData"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
          <el-input v-model="form.warehouseCode" placeholder="仓库" clearable style="width: 120px" />
        </el-form-item>
        <el-form-item label="来源单号">
          <el-input v-model="form.fromNo" placeholder="来源单号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="来源类型">
          <!-- <el-input v-model="form.fromType" placeholder="请输入型号" clearable style="width: 150px" /> -->
          <el-select v-model="form.fromType" style="width:100px" clearable>
            <el-option
              v-for="item in fromData"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="form.status" style="width:100px" clearable>
            <el-option
              v-for="item in statusData"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>

        <el-form-item label="时间检索">
          <el-select v-model="form.dateType" placeholder="请选择" style="width: 100px" size="mini" clearable>
            <el-option
              v-for="item in dateTypeData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-date-picker
            v-model="impTime"
            type="daterange"
            align="right"
            unlink-panels
            style="width: 250px"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-form-item>
        <el-button v-permission="['875660']" type="primary" size="mini" style="margin-left: 10px;" @click="findList(1)">查询</el-button>
        <el-button v-permission="['229850']" type="danger" size="mini" style="margin-left: 10px;" @click="cancel()">取消</el-button>
        <el-button v-permission="['229850']" :loading="exportLoading" type="primary" size="mini" style="margin-left: 10px;" @click="exportTransData">导出</el-button>
        <el-button type="primary" size="mini" style="margin-left: 10px;" @click="openFinishOrderWindow()">完纳</el-button>

      </el-form>

      <el-dialog :visible.sync="cancelDialog" :close-on-click-modal="false" title="订单取消" width="420px" >
        <el-form ref="cancelForm" :model="cancelForm" :rules="rule">
          <span class="delTrans">{{ "正在执行订单"+transNo+"的删除操作" }}</span>
          <el-form-item prop="reason" label="取消原因">
            <el-input v-model="cancelForm.reason" type="textarea" autosize placeholder="请输入取消原因" style="width: 300px"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelDialog = false">取 消</el-button>
          <el-button type="primary" @click="cancelTransOrder()">确认</el-button>
        </div>
      </el-dialog>

      <!-- 订单完纳弹出页面 -->
      <el-dialog :visible.sync="finishOrderDialog.show" width="70%" title="订单完纳" top="10vh">
        <el-divider content-position="left" class="divider_margin">完纳原因</el-divider>
        <!-- 完纳原因 -->
        <el-row style="margin: 10px 10px; min-height: 60px">
          <el-col :span="12">
            <el-input
              v-model="finishOrderDialog.reason"
              :autosize="{minRows: 2 }"
              placeholder="输入原因："
              type="textarea"
              style="width: 500px"
            />
          </el-col>
        </el-row>
        <el-divider content-position="left" class="divider_margin">订单状态：</el-divider>
        <el-table
          :data="finishOrderDialog.order.tableData"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
          :cell-style="{padding: '1px 2px'}"
          height="15vh"
          border
          highlight-current-row
        >
          <el-table-column
            v-for="column in finishOrderDialog.order.tableColumns"
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :width="column.width"
            :formatter="column.formatter"
            :align="column.align"
            header-align="center"
            show-overflow-tooltip
          />
        </el-table>
        <el-row style="text-align: center; margin-top: 20px;">
          <el-button :loading="finishOrderDialog.finishOrderBtn" type="primary" size="small" style="font-size: 15px" @click="finishOrderExe()">完纳</el-button>
          <el-button type="primary" size="small" style="font-size: 15px" @click="closeFinishOrderWindow">取消
          </el-button>
        </el-row>
      </el-dialog>

      <el-table
        v-loading="listLoading"
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        class="multipleTable"
        border
        modal= "false"
        max-height="700"
        style="width: 100%"
        size="mini"
        stripe
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="55"/>
        <el-table-column
          fixed
          width="110"
          prop="transNo"
          label="调库单号"/>
        <el-table-column
          fixed
          width="110"
          prop="invoiceNo"
          label="发票号"/>

        <el-table-column
          fixed
          prop="itemNo"
          label="项号"/>

        <el-table-column
          :formatter="formatTransCode"
          fixed
          prop="transType"
          label="调库类型"/>

        <el-table-column
          fixed
          width="120"
          show-overflow-tooltip
          prop="modelNo"
          label="型号"/>

        <el-table-column
          prop="quantity"
          label="数量"/>

        <el-table-column
          prop="shipQty"
          label="发货数量"/>
        <el-table-column
          :formatter="shipDateformatter"
          prop="shipDate"
          label="发货时间"/>

        <el-table-column
          :formatter="formatStatusCode"
          prop="status"
          label="状态"/>
        <el-table-column
          show-overflow-tooltip
          prop="fromNo"
          label="来源单号"/>
        <el-table-column
          :formatter="formatFromCode"
          prop="fromType"
          label="来源类型"/>

        <!-- <el-table-column
          prop="fromInventoryPropertyId"
          label="行业代码"/> -->

        <el-table-column label="调出" align="center">
          <el-table-column
            prop="fromWarehouseCode"
            label="仓库"/>

          <el-table-column
            prop="fromInventoryTypeCode"
            label="库存代码"/>

          <el-table-column
            show-overflow-tooltip
            prop="fromPpl"
            label="PPL号"/>
          <el-table-column
            show-overflow-tooltip
            prop="fromProjectCode"
            label="项目号"/>
          <el-table-column
            show-overflow-tooltip
            prop="fromGroupCustomerNo"
            label="客户群号"/>

          <el-table-column
            show-overflow-tooltip
            prop="fromSalesInfoNo"
            label="营业情报号"/>

          <el-table-column
            prop="fromCustomerNo"
            label="客户代码"/>
        </el-table-column>

        <!-- <el-table-column
          prop="toInventoryPropertyId"
          show-overflow-tooltip
          label="备注"/> -->
        <el-table-column label="调入" align="center">
          <el-table-column
            prop="toWarehouseCode"
            label="仓库"/>

          <el-table-column
            prop="toInventoryTypeCode"
            label="库存代码"/>

          <el-table-column
            show-overflow-tooltip
            prop="toPpl"
            label="PPL号"/>

          <el-table-column
            show-overflow-tooltip
            prop="toProjectCode"
            label="项目号"/>
          <el-table-column
            show-overflow-tooltip
            prop="toGroupCustomerNo"
            label="客户群号"/>
          <el-table-column
            show-overflow-tooltip
            prop="toSalesInfoNo"
            label="营业情报号"/>
          <el-table-column
            prop="toCustomerNo"
            label="客户代码"/>
        </el-table-column>
        <el-table-column
          :formatter="createTimeformatter"
          show-overflow-tooltip
          prop="createTime"
          label="创建时间"/>
        <el-table-column
          show-overflow-tooltip
          prop="finishTime"
          label="完成时间"/>
        <el-table-column
          prop="createUser"
          label="创建人"/>

      </el-table>
      <el-pagination
        :page-sizes="[20, 50, 100, 200, 500]"
        :current-page="form.pageNum"
        :page-size="form.pageSize"
        :total="total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"/>
      <el-divider />

    </el-main>
  </el-container>
</template>

<script>
import { findTransOrder, cancelTransOrder, exportTrans, askOrderFinish, exeFinish } from '@/api/stock/transstock'
import { getDictDataByPid } from '@/api/common/dict'
export default {
  name: 'TransStockSearch',
  components: {

  },
  data() {
    return {
      listLoading: false,
      cancelDialog: false,
      exportLoading: false,
      total: 0,
      impTime: '',
      transNo: '',
      form: {
        transNo: '',
        transType: '',
        dateType: '',
        modelNo: '',
        fromNo: '',
        fromType: '',
        status: '',
        warehouseCode: '',
        beginDate: '',
        endDate: '',
        warhouseType: 1,
        pageNum: 1,
        pageSize: 20
      },
      cancelForm: {
        ids: [],
        transNo: '',
        transType: '',
        itemNo: '',
        reason: ''
      },
      rule: {
        reason: [
          { required: true, message: '请输入取消原因', trigger: 'blur' }
        ]
      },
      warhouseTypeData: [
        { value: 1, label: '调出' },
        { value: 2, label: '调入' }
      ],
      dateTypeData: [
        { value: 1, label: '创建时间' },
        { value: 2, label: '完成时间' },
        { value: 3, label: '发货时间' }
      ],
      addForm: {
        deptNo: '',
        oldDeptNo: '',
        deptName: '',
        address: '',
        teleNo: '',
        emailAddr: '',
        gpslng: '',
        gpslat: '',
        parentDeptNo: '',
        warehouseCode: '',
        subWarehouseCode: ''
      },
      companyClassCode: '1003',
      statusClassCode: '2036',
      transClassCode: '2037',
      fromClassCode: '2038',
      transTypeData: [],
      fromData: [],
      statusData: [],
      tableData: [],
      companyData: [],
      deptData: [],
      // 完纳
      finishOrderDialog: {
        show: false,
        reason: '',
        finishOrderBtn: false,
        other: false, // 其他原因输入框显示
        otherReason: '', // 其他原因的文本
        order: {
          query: {},
          tableData: [],
          tableColumns: [
            {
              label: '订单号',
              prop: 'orderFoNo',
              width: 200
            },
            {
              label: '型号',
              prop: 'modelNO',
              width: 200
            },
            {
              label: '数量',
              prop: 'orderQty',
              width: 100
            },
            {
              label: '已发数量',
              prop: 'outQty',
              width: 100
            },
            {
              label: '可完纳数量',
              prop: 'finishQty',
              width: 100
            }
          ]
        }
      }
    }
  },

  created() {
    this.iniData()
    this.findList()
  },
  methods: {
    findList(val) {
      this.listLoading = true
      if (val === 1) {
        this.form.pageNum = 1
      }
      this.setDateCondition()
      findTransOrder(this.form).then(res => {
        this.tableData = res.content.list
        this.total = res.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    iniData() {
      getDictDataByPid(this.transClassCode).then(result => {
        this.transTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.fromClassCode).then(result => {
        this.fromData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.statusClassCode).then(result => {
        this.statusData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.impTime === null) {
        this.form.beginDate = ''
        this.form.endDate = ''
      } else {
        this.form.beginDate = this.impTime[0]
        this.form.endDate = this.impTime[1]
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
      // if (val.length > 1) {
      //  this.$refs.multipleTable.clearSelection()
      //  this.$refs.multipleTable.toggleRowSelection(val.pop())
      // }
    },
    cancel() {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择一条数据')
        return
      }
      this.cancelForm.transNo = this.$refs.multipleTable.selection[0].transNo
      this.transNo = this.$refs.multipleTable.selection[0].transNo
      this.cancelForm.itemNo = this.$refs.multipleTable.selection[0].itemNo
      this.cancelForm.transType = this.$refs.multipleTable.selection[0].transType
      this.cancelForm.ids = this.$refs.multipleTable.selection.map((item, index) => {
        return item.id
      })
      console.log(this.cancelForm)
      this.cancelDialog = true
    },
    // 订单完纳页面
    openFinishOrderWindow() {
      if (this.$refs.multipleTable.selection.length !== 1) {
        this.$message.warning('请选择一条数据')
        return
      }
      this.finishOrderDialog.Loading = false
      this.finishOrderDialog.order.query = {}
      this.finishOrderDialog.order.tableData = []
      this.finishOrderDialog.show = true
      this.finishOrderDialog.order.finishQty = null
      this.finishOrderDialog.order.msg = null
      this.finishOrderDialog.otherReason = null
      this.finishOrderDialog.other = false
      this.finishOrderDialog.reason = null
      this.transNo = this.$refs.multipleTable.selection[0].transNo
      this.cancelForm.itemNo = this.$refs.multipleTable.selection[0].itemNo
      const cancelFinishForOrderDto = {
        orderFulNo: this.$refs.multipleTable.selection[0].transNo + '-' + this.$refs.multipleTable.selection[0].itemNo,
        orderNo: this.$refs.multipleTable.selection[0].transNo,
        orderItem: String(this.$refs.multipleTable.selection[0].itemNo),
        modelNo: this.$refs.multipleTable.selection[0].modelNo
      }

      askOrderFinish(cancelFinishForOrderDto).then(res => {
        if (res.success) {
          this.finishOrderDialog.order.tableData.push(res.data)
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(() => {
        this.smcErrorMsg('请求失败')
      })
    },
    finishOrderExe() {
      // 执行订单完纳按钮
      if (this.finishOrderDialog.order.tableData[0] == null) {
        this.smcErrorMsg('无可完纳订单')
        return
      }
      if (this.finishOrderDialog.reason == null) {
        this.smcErrorMsg('请填写完纳原因')
        return
      }
      const fQty = (this.finishOrderDialog.order.tableData[0].finishQty)
      const oQty = (this.finishOrderDialog.order.tableData[0].orderQty)
      const outQty = (this.finishOrderDialog.order.tableData[0].outQty)
      var reason = this.finishOrderDialog.reason
      this.finishOrderDialog.order.tableData[0].msg = reason
      var exeFlag = true
      if (fQty < outQty || fQty >= oQty) {
        exeFlag = false
      }
      if (!exeFlag) {
        this.smcErrorMsg('订单完纳,完纳数量不在区间范围')
      } else {
        var cancelFinishForOrderDto = this.finishOrderDialog.order.tableData[0]
        cancelFinishForOrderDto.userName = this.$store.getters.name
        this.finishOrderDialog.finishOrderBtn = true
        exeFinish(cancelFinishForOrderDto).then(res => {
          this.finishOrderDialog.finishOrderBtn = false
          if (res.success) {
            this.smcInfoMsg(res.message)
            this.closeFinishOrderWindow()
          } else {
            this.smcErrorMsg(res.message)
          }
        }).catch(() => {
          this.smcErrorMsg('请求失败')
        })
      }
    },
    closeFinishOrderWindow() {
      // 完纳取消弹框
      this.finishOrderDialog.order.query = {}
      this.finishOrderDialog.order.tableData = []
      this.finishOrderDialog.show = false
      this.finishOrderDialog.order.finishQty = null
      this.finishOrderDialog.order.msg = null
      this.finishOrderDialog.other = false
      this.finishOrderDialog.reason = null
    },
    cancelTransOrder() {
      this.$refs.cancelForm.validate((valid) => {
        if (valid) {
          this.cancelDialog = false
          cancelTransOrder(this.cancelForm).then(res => {
            if (res.success) {
              this.$message.success(res.message)
            } else {
              this.$message({
                dangerouslyUseHTMLString: true,
                showClose: true,
                message: res.message,
                type: 'error'
              })
            }
          }).catch(error => {
            console.log(error)
          })
        } else {
          this.$message.error('请输入必填选项！')
          return false
        }
      })
    },
    exportTransData() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportTrans(this.form).then(res => {
        const fileName = '调库.xlsx'
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
    formatStatusCode(row, column, cellValue, index, menu) {
      return this.descFormatter(this.statusData, cellValue)
    },
    formatTransCode(row, column, cellValue, index, menu) {
      return this.descFormatter(this.transTypeData, cellValue)
    },
    formatFromCode(row, column, cellValue, index, menu) {
      return this.descFormatter(this.fromData, cellValue)
    },
    shipDateformatter(row) {
      if (row.shipDate != null) {
        return this.dayjs(row.shipDate).format('YYYY-MM-DD HH:mm')
      }
    },
    createTimeformatter(row) {
      return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
    }
    // getCompanyCode(val) {
    //   const obj = this.companyData.filter(item => item.code === val)[0]
    //   return obj ? obj.codeName : ''
    // },
  }
}
</script>

<style scoped>
  .divider_margin {
    margin: 20px 0;
    font-size: 50px;
  }
  .deptTable /deep/ thead th .el-checkbox{
    visibility: hidden;
  }
  .delTrans {
    color: red;
    font-size: 7px;
  }
</style>
