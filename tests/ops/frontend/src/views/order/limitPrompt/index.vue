<template>
  <div class="app-container">
    <!-- 查询 -->
    <el-card style="border-radius: 15px; margin-top: 5px">
      <el-row >
        <!--查询界面-->
        <el-form :inline="true">
          <!--第一行-->
          <el-row>
            <el-col :span="16" >
              <el-form-item>
                <el-input
                  v-model.trim="queryCondition.condition.rorderFno"
                  clearable
                  size="mini"
                  placeholder="订单号"/>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryCondition.condition.modelNo"
                  clearable
                  size="mini"
                  placeholder="型号"/>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryCondition.condition.customerNo"
                  clearable
                  size="mini"
                  placeholder="客户"/>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryCondition.condition.endUser"
                  clearable
                  size="mini"
                  placeholder="最终用户"/>
              </el-form-item>
              <el-form-item>
                <el-select
                  v-model="queryCondition.condition.orderType"
                  style="width: 200px;"
                  clearable
                  size="mini"
                  placeholder="订单类型"
                >
                  <el-option
                    v-for="item in DictData.OrderType"
                    :key="item.code"
                    :value="item.code"
                    :label="item.desc"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <department
                  class="menu-department"
                  style="margin-left: 0px;margin-top: 4px;width: 208px;"
                  @handleScopeChange="changeDeptMenuEvent" />
              </el-form-item>
              <el-form-item>
                <el-select
                  v-model="queryCondition.condition.remindType"
                  style="width: 208px;"
                  clearable
                  size="mini"
                  placeholder="提醒类型"
                >
                  <el-option
                    v-for="item in DictData.OrderLimItTypeData"
                    :key="item.code"
                    :value="item.code"
                    :label="item.desc"/>
                </el-select>
              </el-form-item>
              <el-form-item label="提醒日期">
                <el-date-picker
                  v-model="queryCondition.condition.remindDateArray"
                  :picker-options="pickerOptions"
                  type="daterange"
                  size="small"
                  range-separator="--"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  default-time
                  style="width: 225px"/>
              </el-form-item>
            </el-col>
            <el-col :span="1">
              <el-form-item>
                <el-button
                  type="primary"
                  size="mimi"
                  plain
                  style="padding : 7px 15px"
                  @click="searchEvent()">查询
                </el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </el-row>
    </el-card>
    <!-- 界面 -->
    <div id="ops-card" ref="ops-card" >
      <el-container id="ops-table-rcv">
        <el-main id="rcv-main" style="overflow: hidden">
          <!-- 操作 -->
          <vxe-toolbar custom>
            <template #buttons>
              <el-button :loading="exportTemplateLoading" size="mini" type="primary" plain @click="exportExcelResultList">导出</el-button>
              <el-button
                type="primary"
                size="mini"
                plain
                @click.stop="openOrderLimitConfigDialog">配置
              </el-button>
            </template>
          </vxe-toolbar>
          <vxe-table
            v-loading="tableDataLoading"
            ref="multipleTable"
            :data="tableData.list"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
            :row-style="{height:'33px'}"
            :height="size.rcvTable"
            highlight-current-row
            border
            stripe>
            <!-- 表头字段 -->
            <!-- <el-table-column
              :selectable="false"
              text="选择"
              type="selection"
              width="40"
              align="center"/> -->
            <vxe-table-column
              v-for="column in tableColumns"
              :fixed="column.fixed"
              :key="column.prop"
              :field="column.field"
              :title="column.title"
              :width="column.width"
              :type="column.type"
              :formatter="column.formatter"
              :align="column.align"
              :sortable="true"
              header-align="center"
              show-overflow
            />
          </vxe-table>
        </el-main>
        <!-- 分页按钮 -->
        <el-footer style="height: auto;padding: 0">
          <el-pagination
            ref="ops-table-page"
            :current-page="tableData.pageNum"
            :page-sizes="[20, 50, 100, 200]"
            :page-size="tableData.pageSize"
            :total="tableData.total"
            background
            layout="total, sizes, prev, pager, next"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-footer>
      </el-container>
    </div>
    <exportExcel ref="exportExcelVue"/>
    <OrderLimitConfigDialog :visible.sync="orderLimitConfigDialog.show" />
  </div>
</template>

<script>
import { searchStockTransferPlanByPage, exportData } from '@/api/order/limitPrompt'
import { findDeptDict } from '@/api/warehouseManage'
import { getDataCodesTree } from '@/api/common/dict'
import moment from 'moment'
import exportExcel from '@/components/ExportExcel/index'
import OrderLimitConfigDialog from '@/views/order/limitPrompt/OrderLimitConfigDialog'
import Department from '@/components/Department'

export default {
  name: 'OrderLimitPrompt',
  components: { exportExcel, OrderLimitConfigDialog, Department },
  data() {
    return {
      orderLimitConfigDialog: {
        show: false
      },
      // 时间选取组件
      pickerOptions: {
        shortcuts: [
          {
            text: '一个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment().startOf('day').subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '三个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment().startOf('day').subtract(3, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '半年',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment().startOf('day').subtract(6, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '一年',
            onClick(picker) {
              const end = moment().format('YYYY-MM-DD')
              const start = moment().startOf('day').subtract(1, 'year')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '向前一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).subtract(1, 'months')
              const start = moment(picker.value[0]).subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '向后一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).add(1, 'months')
              const start = moment(picker.value[0]).add(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '扩大一个月',
            onClick(picker) {
              const end = moment(picker.value[1])
              const start = moment(picker.value[0]).subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '缩小一个月',
            onClick(picker) {
              const end = moment(picker.value[1])
              const start = moment(picker.value[0]).add(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      DictData: {
        department: [],
        expDlvType: [],
        OrderType: [
          {
            code: '1',
            desc: '销售订单'
          },
          {
            code: '11',
            desc: '国内集团销售订单'
          },
          {
            code: '12',
            desc: '一般贸易订单'
          },
          {
            code: '20',
            desc: 'BIN补库订单'
          },
          {
            code: '21',
            desc: '先行补库订单'
          },
          {
            code: '24',
            desc: 'DR采购订单'
          },
          {
            code: '25',
            desc: 'CR采购订单'
          },
          {
            code: '3',
            desc: '服务备库订单'
          },
          {
            code: '6',
            desc: '调拨单'
          },
          {
            code: '9',
            desc: '样品订单'
          }
        ],
        OrderLimItTypeData: [
          {
            code: 'OL',
            desc: '限制订单'
          }
        ]
      },
      exportTemplateLoading: false,
      queryCondition: {
        condition: {
          status: 0,
          remindDateArray: [
            moment().startOf('day'),
            moment()
          ]
        },
        pageNumber: 1,
        pageSize: 20,
        orderBy: ' r.id desc'
      },
      search: {
      },
      // 计划表表头字段
      tableColumns: [
        {
          title: '营业所',
          field: 'deptNo',
          formatter: this.deptFormatter,
          align: 'center',
          width: 160
        },
        {
          title: '订单号',
          field: 'rorderFno',
          width: 120
        },
        {
          title: '型号',
          field: 'modelNo',
          width: 120
        },
        {
          title: '数量',
          field: 'quantity',
          width: 120
        },
        {
          title: '客户代码',
          field: 'customerNo',
          width: 120
        },
        {
          title: '最终用户',
          field: 'endUser',
          width: 120
        },
        {
          title: '接单日期',
          field: 'allotTime',
          formatter: this.dateTimeFormatter,
          width: 120
        },
        {
          title: '客户交货期',
          field: 'dlvDate',
          formatter: this.dateTimeFormatter,
          width: 120
        },
        {
          title: '订单类型',
          field: 'orderType',
          formatter: this.orderTypeFormatter,
          width: 120
        },
        {
          title: '特殊标识',
          field: 'expDlvType',
          formatter: this.expDlvTypeFormatter
        },
        {
          title: '提醒类型',
          field: 'remindType',
          formatter: this.orderLimItTypeFormatter
        },
        {
          title: '提醒日期',
          field: 'remindDate',
          formatter: this.dateTimeFormatter
        },
        {
          title: '提示备注',
          field: 'remindNote'
        }
      ],
      // 通知发货计划表数据
      tableData: {},
      tableDataLoading: false,
      // 订单明细表数据
      tableDataItem: [],
      tableDataItemLoading: false,
      // 表格大小布局
      size: {
        search: false,
        rcvTable: 500,
        rcvItemTable: 0
      }
    }
  },
  created() {
    this.getTableData()
    // 营业所
    findDeptDict().then(result => {
      result.forEach(dict => {
        this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
      })
    })
    // 特殊标识
    getDataCodesTree('1017').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.expDlvType.push({ code: dict.code, desc: dict.codeName })
        })
      }
    }).catch(error => {
      console.log(error)
    })
  },
  methods: {
    // 特殊标识格式化
    expDlvTypeFormatterExcel(data) {
      if (!data) {
        return '无'
      }
      var arr = []
      for (const i of this.DictData.expDlvType) {
        if (this.expDlvTypeInclude(data, Number(i.code))) {
          arr.push(i.desc)
        }
      }
      return arr.join()
    },
    // 特殊标识格式化
    expDlvTypeFormatter(data) {
      if (!data.cellValue) {
        return '无'
      }
      var arr = []
      for (const i of this.DictData.expDlvType) {
        if (this.expDlvTypeInclude(data.cellValue, Number(i.code))) {
          arr.push(i.desc)
        }
      }
      return arr.join()
    },
    expDlvTypeInclude(value, type) {
      if (value) {
        if ((value & type) === type) {
          return true
        }
      }
      return false
    },
    // 营业所字典格式化
    deptFormatterExcel(data) {
      const item = this.DictData.department.find(dict => dict.code === data)
      return item ? item.desc : data
    },
    // 营业所字典格式化
    deptFormatter(data) {
      const item = this.DictData.department.find(dict => dict.code === data.cellValue)
      return item ? item.desc : data.cellValue
    },
    // 提醒类型
    orderLimItTypeFormatter(data) {
      const item = this.DictData.OrderLimItTypeData.find(dict => dict.code === data.cellValue)
      return item ? item.desc : data.cellValue
    },
    // 订单类型
    orderTypeFormatter(data) {
      const item = this.DictData.OrderType.find(dict => dict.code === data.cellValue)
      return item ? item.desc : data.cellValue
    },
    // 【营业所选择组件】
    changeDeptMenuEvent(val) {
      this.queryCondition.condition.deptNoArray = val
    },
    openOrderLimitConfigDialog() {
      this.orderLimitConfigDialog.show = true
    },
    // 获取计划表格数据
    getTableData() {
      this.tableDataLoading = true
      this.tableDataItem = []
      searchStockTransferPlanByPage(this.queryCondition).then(res => {
        this.tableDataLoading = false
        if (res.success) {
          this.tableData = res.content
          this.$forceUpdate()
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(res => {
        this.tableDataLoading = false
        this.smcErrorMsg(res.message)
      })
    },
    // 改变每页条数
    handleSizeChange(newSize) {
      this.queryCondition.pageSize = newSize
      this.getTableData()
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.queryCondition.pageNumber = newCurrent
      this.getTableData()
    },
    // 刷新计划
    refreshTable() {
      this.getTableData()
    },
    // 查询计划
    searchEvent() {
      this.getTableData()
    },
    // 日期格式化
    dateTimeFormatter(data) {
      return data.cellValue ? moment(data.cellValue).format('YYYY-MM-DD') : ''
    },
    // 导出结果
    exportExcelResultList() {
      const tableColumn = [
        { field: 'deptNo', title: '营业所' },
        { field: 'rorderFno', title: '订单号' },
        { field: 'modelNo', title: '型号' },
        { field: 'quantity', title: '数量' },
        { field: 'customerNo', title: '客户代码' },
        { field: 'endUser', title: '最终用户' },
        { field: 'allotTime', title: '接单日期' },
        { field: 'dlvDate', title: '客户交货期' },
        { field: 'orderType', title: '订单类型' },
        { field: 'expDlvType', title: '特殊标识' },
        { field: 'remindType', title: '提醒类型' },
        { field: 'remindDate', title: '提醒日期' },
        { field: 'remindNote', title: '提醒备注' }
      ]
      exportData(this.queryCondition.condition).then(res => {
        if (res.success) {
          res.content.forEach((item) => {
            item.expDlvType = this.expDlvTypeFormatterExcel(item.expDlvType)
            item.deptNo = this.deptFormatterExcel(item.deptNo)
          })
          this.$refs.exportExcelVue.initExportExcel(res.content, tableColumn)
        } else {
          console.log('无数据')
        }
      }).catch(error => {
        console.error(error)
      })
    },
    importClose() {
      this.importDialog.result = []
      this.importDialog.show = false
      this.importDialog.msg = ''
      this.getTableData()
    }
  }
}
</script>

<style scoped lang="scss">

.el-row {
  margin-bottom: 5px;
}

.el-form-item {
  margin: 0 5px;
}
.upload-demo {
  margin-left: 12px;
  margin-right: 12px;
}
.el-upload__text{
  margin-left: 12px;
}
#divider {
  height: 4px;
  background: #dcdde6;
  cursor: row-resize;
  margin: 10px 0;
}
#rcv-main {
  padding: 0;
}

.divider_margin {
  margin: 20px 0;
  font-size: 50px;
}

/*运单图标*/
/deep/ .el-timeline-item__node--large {
  left: -7px;
  width: 25px;
  height: 25px;
}

.font-size-large {
  font-size: 18px;
  font-family: "微软雅黑";
  padding: 1px;
}

.rcv-search-menu-open /deep/ .el-form-item  {
  .el-select , .el-input , .searchDeptNoClass{
    width: 160px !important;
  }
}

.rcv-search-menu-close /deep/ .el-form-item  {
  .el-select , .el-input , .searchDeptNoClass {
    width: 180px !important;
  }
}

.searchDeptNoClass /deep/ .el-input__inner{
  height: 30px!important;
}

.menu-department /deep/ .el-cascader__search-input{
  min-width: 1px!important;
  height: 0px!important;
}

</style>
