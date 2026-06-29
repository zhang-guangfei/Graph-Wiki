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
                  v-model.trim="queryForm.condition.invoiceNo"
                  style="width: 120px"
                  clearable
                  size="mini"
                  placeholder="发票号"/>
              </el-form-item>
              <el-form-item prop="supplierCodeList">
                <el-select v-model="queryForm.condition.supplierCodeList" style="width: 120px" placeholder="请选择供应商" clearable size="small" multiple>
                  <el-option v-for="item in suppilyList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryForm.condition.orderNo"
                  style="width: 120px"
                  clearable
                  size="mini"
                  placeholder="订单号"/>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryForm.condition.modelNo"
                  style="width: 120px"
                  clearable
                  size="mini"
                  placeholder="型号"/>
              </el-form-item>
              <el-form-item prop="errorTypeList">
                <el-select v-model="queryForm.condition.errorTypeList" style="width: 120px" placeholder="请选择错误原因" clearable size="small" multiple>
                  <el-option v-for="item in errorTypeList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
              </el-form-item>
              <el-form-item label="忽略时间">
                <el-date-picker
                  v-model="queryForm.condition.startTime"
                  type="datetime"
                  size="mini"
                  placeholder="开始日期"
                  value-format="yyyy-MM-dd HH:mm"
                  style="width: 130px"
                />
                <el-date-picker
                  v-model="queryForm.condition.endTime"
                  type="datetime"
                  size="mini"
                  placeholder="结束日期"
                  value-format="yyyy-MM-dd HH:mm"
                  style="width: 130px"
                />
              </el-form-item>
            </el-col>
            <el-col :span="1">
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="mimi"
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
      <!-- 通知发货计划表 -->
      <el-container id="ops-table-rcv">
        <!-- 通知发货计划表表 -->
        <el-main id="rcv-main" style="overflow: hidden">
          <!-- 操作 -->
          <vxe-toolbar custom>
            <template #buttons>
              <el-button :loading="exportTemplateLoading" size="mini" type="primary" @click="exportExcelResultList">导出</el-button>
            </template>
          </vxe-toolbar>
          <vxe-table
            v-loading="tableDataLoading"
            ref="multipleTable"
            :data="tableData.list"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
            :row-style="{height:'33px'}"
            highlight-current-row
            border
            stripe
          >
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
            :page-sizes="[10, 20, 50, 100]"
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
  </div>
</template>

<script>
import { getSuppily } from '@/api/intercept'
import { getErrorType, exportData, findList } from '@/api/po/invoiceError'
import moment from 'moment'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'InvoiceError',
  components: { exportExcel },
  data() {
    return {
      suppilyList: [],
      errorTypeList: [],
      queryForm: {
        condition: {
          invoiceNo: '',
          supplierCodeList: [],
          modelNo: '',
          orderNo: '',
          errorTypeList: [],
          startTime: '',
          endTime: ''
        },
        pageNumber: 1,
        pageSize: 10,
        orderBy: ' e.id desc'
      },
      exportTemplateLoading: false,
      // 计划表表头字段
      tableColumns: [
        {
          title: '发票ID',
          field: 'invoiceId',
          align: 'center',
          width: 160
        },
        {
          title: '发票号',
          field: 'invoiceNo',
          width: 120
        },
        {
          title: '发票供应商',
          field: 'supplierCode',
          width: 120
        },
        {
          title: '发票订单号',
          field: 'orderNo',
          width: 160
        },
        {
          title: '入库型号',
          field: 'modelNo',
          width: 160
        },
        {
          title: '采购型号',
          field: 'poModelNo',
          width: 120
        },
        {
          title: '发票数量',
          field: 'qty',
          width: 120
        },
        {
          title: '采购剩余数量',
          field: 'poQty',
          width: 120
        },
        {
          title: '分包数量',
          field: 'packQty',
          width: 120
        },
        {
          title: '错误原因',
          field: 'errorText',
          width: 120
        },
        {
          title: '原采购仓',
          field: 'poWarehouseCode',
          width: 120
        },
        {
          title: '是否忽略差异',
          field: 'ignoreError',
          formatter: this.ignoreErrorFormatter,
          width: 120
        },
        {
          title: '忽略人',
          field: 'ignorePsn',
          width: 120
        },
        {
          title: '忽略时间',
          field: 'ignoreTime',
          formatter: this.dateTimeFormatter,
          width: 120
        },
        {
          title: '忽略原因',
          field: 'ignoreReason',
          width: 120
        },
        {
          title: '备注',
          field: 'remark',
          width: 120
        },
        {
          title: '创建时间',
          field: 'createTime',
          formatter: this.dateTimeFormatter,
          width: 120
        }
      ],
      tableData: {
        list: [],
        total: 0,
        pageNum: 1,
        pageSize: 10
      },
      tableDataLoading: false,
      // 表格大小布局
      size: {
        search: false,
        rcvTable: 400,
        rcvItemTable: 0
      }
    }
  },
  created() {
    this.getTableData()
    this.getSuppily()
    this.getErrorType()
  },
  methods: {
    // 获取错误类型列表
    getErrorType() {
      getErrorType()
        .then((res) => {
          this.errorTypeList = res.content
        })
        .catch((error) => {
          console.log(error)
        })
    },
    // 获取供应商列表
    getSuppily() {
      getSuppily()
        .then((res) => {
          this.suppilyList = res.data
        })
        .catch((error) => {
          console.log(error)
        })
    },
    // 获取表格数据
    getTableData() {
      this.tableDataLoading = true
      findList(this.queryForm).then(res => {
        this.tableDataLoading = false
        if (res.success) {
          this.tableData = res.content
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
      this.queryForm.pageSize = newSize
      this.getTableData()
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.queryForm.pageNumber = newCurrent
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

    // 是否忽略差异
    ignoreErrorFormatter(data) {
      return data.cellValue === 1 ? '是' : '否'
    },

    // 日期格式化
    dateTimeFormatter(data) {
      return data.cellValue ? moment(data.cellValue).format('YYYY-MM-DD HH:mm') : ''
    },
    // 导出结果
    exportExcelResultList() {
      const tableColumn = [
        {
          title: '发票ID',
          field: 'invoiceId'
        },
        {
          title: '发票号',
          field: 'invoiceNo'
        },
        {
          title: '发票供应商',
          field: 'supplierCode'
        },
        {
          title: '发票订单号',
          field: 'orderNo'
        },
        {
          title: '入库型号',
          field: 'modelNo'
        },
        {
          title: '采购型号',
          field: 'poModelNo'
        },
        {
          title: '发票数量',
          field: 'qty'
        },
        {
          title: '采购剩余数量',
          field: 'poQty'
        },
        {
          title: '分包数量',
          field: 'packQty'
        },
        {
          title: '错误原因',
          field: 'errorText'
        },
        {
          title: '原采购仓',
          field: 'poWarehouseCode'
        },
        {
          title: '忽略差异（0-否，1-是）',
          field: 'ignoreError',
          formatter: this.ignoreErrorFormatter
        },
        {
          title: '忽略人',
          field: 'ignorePsn'
        },
        {
          title: '忽略时间',
          field: 'ignoreTime',
          formatter: this.dateTimeFormatter
        },
        {
          title: '忽略原因',
          field: 'ignoreReason'
        },
        {
          title: '备注',
          field: 'remark'
        },
        {
          title: '创建时间',
          field: 'createTime',
          formatter: this.dateTimeFormatter
        }
      ]
      exportData(this.queryForm.condition).then(res => {
        if (res.success) {
          this.$refs.exportExcelVue.initExportExcel(res.content, tableColumn)
        } else {
          console.log('无数据')
        }
        this.exportTemplateLoading = false
      }).catch(error => {
        this.exportTemplateLoading = false
        console.error(error)
      })
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
.white-blue-btn {
  background-color: #ffffff;
  color: #409eff; /* Element UI的primary蓝色 */
  border: 1px solid #409eff;
}
</style>
