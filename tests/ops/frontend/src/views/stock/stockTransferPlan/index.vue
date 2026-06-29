<template>
  <div class="app-container">
    <!-- 界面 -->
    <el-card>
      <div id="ops-card" ref="ops-card">
        <!-- 调库计划表 -->
        <el-container id="ops-table-rcv">
          <!-- 菜单栏 -->
          <el-header
            style="height: auto;padding: 0 10px">
            <el-row>
              <!--查询界面-->
              <el-form :inline="true">
                <!--第一行-->
                <el-row>
                  <el-col :span="20">
                    <el-form-item>
                      <el-input
                        v-model.trim="queryCondition.condition.planNo"
                        clearable
                        size="small"
                        placeholder="计划号"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model.trim="queryCondition.condition.poNo"
                        clearable
                        size="small"
                        placeholder="采购单号"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model.trim="queryCondition.condition.modelno"
                        clearable
                        size="small"
                        placeholder="型号"/>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="queryCondition.condition.status"
                        style="width: 180px;"
                        clearable
                        size="small"
                        placeholder="状态"
                      >
                        <el-option
                          v-for="item in DictData.status"
                          :key="item.code"
                          :value="item.code"
                          :label="item.desc"/>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4" style="text-align: right">
                    <el-button
                      type="primary"
                      size="small"
                      @click="searchEvent()">查询
                    </el-button>
                    <el-button
                      type="primary"
                      size="small"
                      @click="delPlan()">删除
                    </el-button>
                  </el-col>
                </el-row>
              </el-form>
            </el-row>
          </el-header>
          <!-- 调库计划表表 -->
          <el-main id="rcv-main" style="overflow: hidden">
            <el-table
              v-loading="tableDataLoading"
              ref="multipleTable"
              :data="tableData.list"
              :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
              :row-style="{height:'33px'}"
              :cell-style="cellStyle"
              :height="size.rcvTable"
              highlight-current-row
              border
              stripe
              @cell-click="cellClickPOAndPlanNo"
              @selection-change="handleSelectionChange">
              >
              <!-- 表头字段 -->
              <el-table-column
                :selectable="checkSelectable"
                text="选择"
                type="selection"
                width="40"
                align="center"/>
              <el-table-column
                v-for="column in tableColumns"
                :fixed="column.fixed"
                :key="column.prop"
                :prop="column.prop"
                :label="column.label"
                :width="column.width"
                :type="column.type"
                :formatter="column.formatter"
                :align="column.align"
                :sortable="true"
                header-align="center"
                show-overflow-tooltip
              />
            </el-table>
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
        <!-- 分割线 -->
        <div id="divider"/>
        <!-- 调库计划明细表 -->
        <div id="ops-table-rcv-item" ref="ops-table-rcv-item">
          <el-table
            v-loading="tableDataItemLoading"
            :data="tableDataItem"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '1px','font-size': '14px'}"
            :cell-style="{padding: '0px 1px'}"
            :row-style="{height:'33px'}"
            :default-sort="{prop: 'splitNo'}"
            :height="size.rcvItemTable"
            highlight-current-row
            border
            stripe
          >
            <!-- 表头字段 -->
            <el-table-column
              v-for="column in tableColumnsItem"
              :key="column.prop"
              :prop="column.prop"
              :label="column.label"
              :width="column.width"
              :type="column.type"
              :formatter="column.formatter"
              :align="column.align"
              header-align="center"
              show-overflow-tooltip
            />
          </el-table>
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
import { getStockTransferPlanList, getStockTransferPlanItemList, delStockTransferPlan } from '@/api/stockTransferPlan'
import moment from 'moment'

export default {
  name: 'StockTransferPlan',
  data() {
    return {
      // 字典数据
      DictData: {
        status: [
          {
            code: 0,
            desc: '待处理'
          },
          {
            code: 1,
            desc: '处理中'
          },
          {
            code: 2,
            desc: '已完成'
          }
        ],
        invType: [
          {
            code: 'GK-PPL',
            desc: '顾客在库PPL'
          },
          {
            code: 'GK-PJ',
            desc: '顾客在库项目'
          },
          {
            code: 'GK-TY',
            desc: '顾客在库通用'
          },
          {
            code: 'TY',
            desc: '通用在库'
          },
          {
            code: 'QB_NO',
            desc: '情报在库'
          },
          {
            code: 'ZL-CP',
            desc: '战略在库（产品）'
          },
          {
            code: 'ZL-HY',
            desc: '战略在库（行业）'
          },
          {
            code: 'ZL-JT',
            desc: '战略在库（集团）'
          },
          {
            code: 'ZL-PJ',
            desc: '战略在库（PJ）'
          }
        ]
      },
      // rcv表查询条件
      queryCondition: {
        condition: {
        },
        pageNumber: 1,
        pageSize: 20,
        orderBy: 'rorddate desc, rorder_item asc '
      },
      search: {
      },
      multipleSelection: [],
      // 计划表表头字段
      tableColumns: [
        {
          label: '计划号',
          prop: 'planNo',
          align: 'center',
          formatter: this.planNoFormatter
        },
        {
          label: '采购单号',
          prop: 'associateNo',
          formatter: this.associateNoFormatter
        },
        {
          label: '调出库存分类',
          prop: 'initInvTypeCode',
          formatter: this.invTypeFormatter
        },
        {
          label: '调出客户代码',
          prop: 'initCustomerNo'
        },
        {
          label: '调入库存分类',
          prop: 'goalInvTypeCode',
          formatter: this.invTypeFormatter
        },
        {
          label: '调入客户代码',
          prop: 'goalCustomerNo'
        },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '计划数量',
          prop: 'planQty'
        },
        {
          label: '完成数量',
          prop: 'finishQty'
        },
        {
          label: '状态',
          prop: 'status',
          formatter: this.statusFormatter
        },
        {
          label: '创建时间',
          prop: 'createTime',
          formatter: this.dateTimeFormatter
        },
        {
          label: '创建人',
          prop: 'creator'
        },
        {
          label: '修改',
          prop: 'updator'
        }
      ],
      // rcv表数据
      tableData: {},
      tableDataLoading: false,
      // 订单明细表字段
      tableColumnsItem: [
        {
          label: '计划号',
          prop: 'planNo'
        },
        {
          label: '调库单号',
          prop: 'transferOrderNo'
        },
        {
          label: '调库项号',
          prop: 'transferOrderItem'
        },
        {
          label: '调库仓库',
          prop: 'warehouseCode'
        },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '完成数量',
          prop: 'finishQty'
        },
        {
          label: '创建时间',
          prop: 'createTime',
          formatter: this.dateTimeFormatter
        },
        {
          label: '创建人',
          prop: 'creator'
        }
      ],
      // 订单明细表数据
      tableDataItem: [],
      tableDataItemLoading: false,
      rcvItemCondition: {},
      rcvView: {},
      // 表格大小布局
      size: {
        search: false,
        rcvTable: '60vh',
        rcvItemTable: 0
      }
    }
  },
  created() {
    this.getTableData()
  },
  mounted() {
    const _this = this
    this.$nextTick(() => {
      const divider = document.getElementById('divider')
      _this.size.rcvItemTable = document.getElementById('ops-card').offsetHeight - divider.offsetTop - 10
      divider.onmousedown = function(e) {
        document.onmousemove = function(e) {
          const newY = e.offsetY - 10
          document.getElementById('ops-table-rcv').style.height = newY + 'px'
          return false
        }
        document.onmouseup = function() {
          const rcvMain = document.getElementById('rcv-main')
          _this.size.rcvTable = rcvMain.offsetHeight
          _this.size.rcvItemTable = document.getElementById('ops-card').offsetHeight - divider.offsetTop - 10
          document.onmousemove = null
          document.onmouseup = null
        }
        return false
      }
    })
  },
  methods: {
    // 获取计划表格数据
    getTableData() {
      this.tableDataLoading = true
      getStockTransferPlanList(this.queryCondition).then(res => {
        this.tableData = res.data
        this.tableDataLoading = false
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    showStockTransferPlanItem(planNo) {
      this.tableDataItemLoading = true
      getStockTransferPlanItemList(planNo).then(res => {
        this.tableDataItem = res.data
        this.tableDataItemLoading = false
      }).catch(res => {
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
    // 删除计划
    delPlan() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: '请选中需要删除的数据',
          type: 'warning'
        })
        return
      }
      var newArr = this.multipleSelection.map((item, index) => {
        return item.planNo
      })
      delStockTransferPlan(newArr, this.$store.getters.name).then(res => {
        if (res.success) {
          this.smcInfoMsg(res.message)
          this.refreshTable()
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    // 非初始状态不可勾选
    checkSelectable(row) {
      if (row.status > 0) {
        return false
      } else {
        return true
      }
    },
    // 刷新计划
    refreshTable() {
      this.getTableData()
    },
    // 查询计划
    searchEvent() {
      this.getTableData()
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    cellClickPOAndPlanNo(row, column, cell, event) {
      if (column.label === '计划号' || column.label === '采购单号') {
        // 执行逻辑
        this.showStockTransferPlanItem(row.planNo)
      }
    },
    // invType
    invTypeFormatter(row, column, cellValue, index, menu) {
      const item = this.DictData.invType.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    statusFormatter(row, column, cellValue, index, menu) {
      const item = this.DictData.status.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    planNoFormatter(row, column, cellValue, index, menu) {
      return cellValue
    },
    associateNoFormatter(row, column, cellValue, index, menu) {
      var assNo = row.associateNo + '-' + row.associateNoItem
      if (row.associateNoSplitno !== 0) {
        assNo = assNo + '-' + row.associateNoSplitno
      }
      return assNo
    },
    // 日期格式化
    dateTimeFormatter(row, column, cellValue) {
      return cellValue ? moment(cellValue).format('YYYY-MM-DD HH:mm:ss') : ''
    },
    cellStyle({ row, column, rowIndex, columnIndex }) {
      if (column.label === '计划号') {
        return 'color: blue;'
      }
      if (column.label === '采购单号') {
        return 'color: blue;'
      }
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

#divider {
  height: 4px;
  background: #dcdde6;
  cursor: row-resize;
  margin: 10px 0;
}

#ops-card {
  /*height: 840px;*/
  height: calc(100vh - 130px);
  position: relative;
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
