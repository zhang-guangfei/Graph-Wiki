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
                  v-model.trim="queryCondition.condition.planNo"
                  clearable
                  size="mini"
                  placeholder="计划号"/>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryCondition.condition.orderFno"
                  clearable
                  size="mini"
                  placeholder="订单号"/>
              </el-form-item>
              <el-form-item>
                <el-select
                  v-model="queryCondition.condition.status"
                  style="width: 180px;"
                  clearable
                  size="mini"
                  placeholder="状态"
                >
                  <el-option
                    v-for="item in DictData.status"
                    :key="item.code"
                    :value="item.code"
                    :label="item.desc"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryCondition.condition.orderModelNo"
                  clearable
                  size="mini"
                  placeholder="订单型号"/>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model.trim="queryCondition.condition.modelNo"
                  clearable
                  size="mini"
                  placeholder="计划型号"/>
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
      <!-- 通知发货计划表 -->
      <el-container id="ops-table-rcv">
        <!-- 通知发货计划表表 -->
        <el-main id="rcv-main" style="overflow: hidden">
          <!-- 操作 -->
          <vxe-toolbar custom>
            <template #buttons>
              <el-button :loading="exportTemplateLoading" size="mini" type="primary" plain @click="exportExcelResultList">导出</el-button>
              <el-button
                type="primary"
                size="mini"
                plain
                @click="openAddWindow()">新增
              </el-button>
              <!-- <el-button :loading="exportTemplateLoading" size="mini" type="primary" plain @click="exportTemplate">模板</el-button>
              <el-upload
                :multiple="false"
                :before-upload="beforeUploadFile"
                :show-file-list="false"
                accept=".xlsx"
                action=""
                class="upload-demo">
                <el-button slot="trigger" :loading="importDetailLoading" size="mini" plain type="primary">上传</el-button>
              </el-upload>
              <el-button :loading="importDetailLoading" size="mini" type="primary" plain @click="importByTemplate()">{{ importDetailLoading ? '导入中...' : '导入' }}</el-button>
              <div v-if="file !== null" class="el-upload__text">
                <span>{{ file.name }}</span>
              </div> -->
              <el-button :loading="importDetailLoading" size="mini" type="primary" plain @click="impDialog">导入</el-button>
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
            stripe
            @cell-click="cellClickPOAndPlanNo"
            @selection-change="handleSelectionChange">
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
      <!-- 分割线 -->
      <div id="divider"/>
      <!-- 通知发货计划明细表 -->
      <div id="ops-table-rcv-item" ref="ops-table-rcv-item">
        <vxe-table
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
          <vxe-table-column
            v-for="column in tableColumnsItem"
            :key="column.prop"
            :field="column.field"
            :title="column.title"
            :width="column.width"
            :type="column.type"
            :formatter="column.formatter"
            :align="column.align"
            header-align="center"
            show-overflow
          />
        </vxe-table>
      </div>
    </div>
    <exportExcel ref="exportExcelVue"/>
    <!-- 新增页面 -->
    <AddDialog :visible.sync="addDialog.show" :order-data="addDialog.data" @handleSuccess="addSuccess"/>
    <!-- excel导入信息 -->
    <!--订单附件清单下载-->
    <el-dialog
      :visible.sync="importDialog.show"
      :title="importDialog.msg"
      :show-close="false"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      width="30%"
    >
      <el-divider content-position="left" class="divider_margin">错误信息</el-divider>
      <el-table :data="importDialog.result" max-height="250" border>
        <!-- 表头字段 -->
        <el-table-column prop="orderFno" show-overflow-tooltip label="订单号" />
        <el-table-column prop="modelNo" show-overflow-tooltip label="型号" />
        <el-table-column prop="remark" show-overflow-tooltip label="错误信息" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="importClose">关 闭</el-button>
      </span>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="选择文件"
      width="420px"
    >
      <div class="upload">
        <el-upload
          :mutiple="false"
          :before-upload="beforeUploadFile"
          accept=".xlsx"
          action=""
          class="upload-demo"
          drag
        >
          <div class="el-upload__text" style="color:red">支持xlsx格式</div>
          <div v-if="file !== null" class="el-upload__text">
            {{ file.name }}
          </div>
          <div v-else class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
        </el-upload>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button :loading="exportTemplateLoading" type="primary" @click="exportTemplate" >下载模板</el-button>
        <el-button type="success" @click="importByTemplate">
          <span v-loading="importDetailLoading" element-loading-text="拼命上传中..." element-loading-spinner="el-icon-loading" element-loading-background="#66CDAA">{{
            importDetailLoading ? "拼命上传中..." : "上传文件"
          }}</span>
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getNotifyShipmentPlanList, getNotifyShipmentItemPlanList, getWLDate, getCreateVerify, save, importData, exportData } from '@/api/notifyShipmentPlan'
import moment from 'moment'
import exportExcel from '@/components/ExportExcel/index'
import AddDialog from '@/views/notifyShipmentPlan/add'

export default {
  name: 'NotifyShipmentPlan',
  components: { exportExcel, AddDialog },
  data() {
    return {
      uploadFileDialogVisible: false,
      DictData: {
        status: [
          {
            code: 0,
            desc: '待分配'
          },
          {
            code: 1,
            desc: '分配中'
          },
          {
            code: 2,
            desc: '分配完成'
          }
        ]
      },
      file: null,
      exportTemplateLoading: false,
      importDetailLoading: false,
      // NotifyShipmentPlan表查询条件
      queryCondition: {
        condition: {
          status: 0
        },
        pageNumber: 1,
        pageSize: 20,
        orderBy: ' p.id desc'
      },
      addDialog: {
        show: false,
        data: {}
      },
      // 订单附件下载
      importDialog: {
        show: false,
        result: [],
        msg: ''
      },
      search: {
      },
      multipleSelection: [],
      // 计划表表头字段
      tableColumns: [
        {
          title: '计划号',
          field: 'planNo',
          align: 'center',
          width: 160
        },
        {
          title: '订单号',
          field: 'orderFno',
          width: 120
        },
        {
          title: '订单数量',
          field: 'orderQty',
          width: 120
        },
        {
          title: '订单型号',
          field: 'orderModelNo',
          width: 160
        },
        {
          title: '计划型号',
          field: 'modelNo',
          width: 160
        },
        {
          title: '计划数量',
          field: 'planQty',
          width: 120
        },
        {
          title: '分配数量',
          field: 'matchQty',
          width: 120
        },
        {
          title: '发货数量',
          field: 'outQty',
          width: 120
        },
        {
          title: '客户货期',
          field: 'hopeDate',
          formatter: this.dateTimeFormatter,
          width: 120
        },
        {
          title: '物流货期',
          field: 'wlDate',
          formatter: this.dateTimeFormatter,
          width: 120
        },
        {
          title: '计划状态',
          field: 'statusMsg',
          width: 120
        },
        {
          title: '发货状态',
          field: 'outStatus',
          width: 120
        },
        {
          title: '创建人',
          field: 'creator',
          width: 120
        },
        {
          title: '创建时间',
          field: 'createTime',
          formatter: this.dateTimeFormatter,
          width: 120
        },
        {
          title: '备注',
          field: 'remark',
          width: 120
        }
      ],
      // 通知发货计划表数据
      tableData: {},
      tableDataLoading: false,
      // 计划明细表明细表字段
      tableColumnsItem: [
        {
          title: '计划号',
          field: 'planNo'
        },
        {
          title: '指令号',
          field: 'doId'
        },
        {
          title: '数量',
          field: 'qty'
        },
        {
          title: '发货仓',
          field: 'warehouseCode'
        },
        {
          title: '物流状态',
          field: 'status'
        }
      ],
      // 订单明细表数据
      tableDataItem: [],
      tableDataItemLoading: false,
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
    impDialog() {
      this.uploadFileDialogVisible = true
    },
    // 获取计划表格数据
    getTableData() {
      this.tableDataLoading = true
      this.tableDataItem = []
      getNotifyShipmentPlanList(this.queryCondition).then(res => {
        this.tableDataLoading = false
        if (res.success) {
          this.tableData = res.data
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(res => {
        this.tableDataLoading = false
        this.smcErrorMsg(res.message)
      })
    },
    // 打开订单还原弹窗
    openAddWindow() {
      this.addDialog.show = true
      // this.addDialog.data = row
    },
    showNotifyShipmentPlanItem(planNo) {
      this.tableDataItemLoading = true
      getNotifyShipmentItemPlanList(planNo).then(res => {
        this.tableDataItem = res.data
        this.tableDataItemLoading = false
      }).catch(res => {
        this.tableDataItemLoading = false
        this.smcErrorMsg(res.message)
      })
    },
    getWlDateByHopeDate(data) {
      getWLDate(data).then(res => {
        // res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    getCreVerify(data) {
      getCreateVerify(data).then(res => {
        // res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    addSuccess() {
      this.getTableData()
    },
    saveNotifyShipment(data) {
      save(data).then(res => {
        // res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    exportTemplate() {
      this.exportTemplateLoading = true
      window.open('../../static/template/downLoadTemplate/通知发货导入模版.xlsx')
      this.exportTemplateLoading = false
    },
    beforeUploadFile(file) {
      this.file = file
      return false
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
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    cellClickPOAndPlanNo({ row, column }) {
      this.showNotifyShipmentPlanItem(row.planNo)
    },
    // 日期格式化
    dateTimeFormatter(data) {
      return data.cellValue ? moment(data.cellValue).format('YYYY-MM-DD') : ''
    },
    // 导入
    async importByTemplate() {
      const file = this.file
      if (!file) {
        return this.$message.warning('请选择文件')
      }
      const fileType = file.name.substring(file.name.lastIndexOf('.') + 1)
      if (fileType !== 'xlsx') {
        return this.$message.warning('文件格式错误，请重新导入(.xlsx)')
      }
      this.uploadFileDialogVisible = false
      this.importDetailLoading = true
      // 导入申请项
      const formData = new FormData()
      formData.append('file', file)
      formData.append('userName', this.$store.getters.name)
      importData(formData).then(res => {
        this.importDetailLoading = false
        if (res.success) {
          var size = res.data.size
          var errorSize = res.data.errList.length
          this.importDialog.msg = '成功:' + (size - errorSize) + '条;\n  共:' + size + '条数据; '
          if (errorSize > 0) {
            this.importDialog.result = res.data.errList
          }
          this.importDialog.show = true
        } else {
          this.smcErrorMsg(res.message)
        }
        this.file = null
      }).catch(error => {
        this.importDetailLoading = false
        this.smcErrorMsg(error)
      })
    },
    // 导出结果
    exportExcelResultList() {
      const tableColumn = [
        { field: 'planNo', title: '计划号' },
        { field: 'orderFno', title: '订单号' },
        { field: 'orderQty', title: '订单数量' },
        { field: 'orderModelNo', title: '订单型号' },
        { field: 'modelNo', title: '计划型号' },
        { field: 'planQty', title: '计划数量' },
        { field: 'matchQty', title: '分配数量' },
        { field: 'outQty', title: '发货数量' },
        { field: 'hopeDate', title: '客户货期' },
        { field: 'wlDate', title: '物流指定发货日' },
        { field: 'statusMsg', title: '计划状态' },
        { field: 'outStatus', title: '发货状态' },
        { field: 'creator', title: '创建人' },
        { field: 'createTime', title: '创建时间' }
      ]
      exportData(this.queryCondition.condition).then(res => {
        if (res.success) {
          this.$refs.exportExcelVue.initExportExcel(res.data, tableColumn)
        } else {
          console.log('无数据')
        }
        this.importDetailLoading = false
      }).catch(error => {
        this.importDetailLoading = false
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
