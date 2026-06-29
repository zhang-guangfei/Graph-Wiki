<template>
  <div class="filter-container">
    <el-card :body-style="{padding: '10px'}" style="margin:10px;" >
      <!--查询界面-->
      <el-form :inline="true">
        <el-row>
          <!--营业所-->
          <el-form-item>
            <department
              class="menu-department"
              style="margin-left: 0px;margin-top: 4px;width: 200px;"
              @handleScopeChange="changeDeptMenuEvent" />
          </el-form-item>
          <!--订单号-->
          <el-form-item>
            <el-input
              v-model.trim="searchMenuData.orderFno"
              clearable
              size="small"
              placeholder="订单号"
              @keyup.enter.native="menuSearchEvent"/>
          </el-form-item>
          <!--型号-->
          <el-form-item>
            <el-input
              v-model.trim="searchMenuData.modelno"
              clearable
              size="small"
              placeholder="型号"
              @keyup.enter.native="menuSearchEvent"/>
          </el-form-item>
          <!--客户代码-->
          <el-form-item>
            <el-input
              v-model.trim="searchMenuData.customerNo"
              clearable
              size="small"
              placeholder="客户代码"
              @keyup.enter.native="menuSearchEvent"/>
          </el-form-item>
          <!--用户代码-->
          <!--
          <el-form-item>
            <el-input
              v-model.trim="searchMenuData.userNo"
              clearable
              size="small"
              placeholder="用户代码"
              @keyup.enter.native="menuSearchEvent"/>
          </el-form-item>
          -->
          <!--最终用户-->
          <el-form-item>
            <el-input
              v-model.trim="searchMenuData.endUser"
              clearable
              size="small"
              placeholder="最终用户"
              @keyup.enter.native="menuSearchEvent"/>
          </el-form-item>
          <!--异常反馈日期-->
          <el-form-item>
            <el-date-picker
              v-model="searchMenuData.inquiryDate"
              :picker-options="pickerOptions"
              :default-time="['00:00:00', '23:59:59']"
              style="width: 200px;"
              type="daterange"
              size="small"
              range-separator="-"
              start-placeholder="反馈日期"
              end-placeholder="反馈日期"/>
          </el-form-item>
          <el-form-item>
            <el-button class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="menuSearchEvent">查询</el-button>
            <el-button class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="openBatchSearchWindow">批量查询</el-button>
            <el-button class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="exportExcelSearched">导出</el-button>
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item>
            <el-select
              v-model="searchMenuData.unusualType"
              style="width: 200px;"
              clearable
              size="small"
              placeholder="异常订单类别"
            >
              <el-option value="0" label="接单前异常"/>
              <el-option value="1" label="接单后异常"/>
            </el-select>
          </el-form-item>
          <!--异常类型说明-->
          <el-form-item>
            <el-input
              v-model.trim="searchMenuData.unusualDescChn"
              clearable
              size="small"
              placeholder="异常类型说明"
              @keyup.enter.native="menuSearchEvent"/>
          </el-form-item>

          <!--异常提醒次数-->
          <el-form-item>
            <el-input
              v-model.number="searchMenuData.recordCount"
              type="number"
              clearable
              size="small"
              placeholder="异常提醒次数"
              @keyup.enter.native="menuSearchEvent"/>
          </el-form-item>
          <!--异常处理责任部门-->
          <el-form-item>
            <el-select
              v-model="searchMenuData.jobDeptName"
              style="width: 222px;"
              clearable
              size="small"
              placeholder="责任部门"
            >
              <el-option
                v-for="item in DictData.jobDeptName"
                :key="item.id"
                :value="item.code"
                :label="item.desc"/>
            </el-select>
          </el-form-item>
          <!--异常处理状态-->
          <el-form-item>
            <el-select
              v-model="searchMenuData.handleStatus"
              style="width: 222px;"
              clearable
              size="small"
              placeholder="处理状态"
            >
              <el-option
                v-for="item in DictData.handleStatus"
                :key="item.id"
                :value="item.code"
                :label="item.desc"/>
            </el-select>
          </el-form-item>
          <!-- <el-form-item>
            <el-button
              type="primary"
              size="small"
              @click="menuSearchEvent">搜索
            </el-button>
            <el-button
              type="primary"
              size="small"
              @click="openBatchSearchWindow">批量搜索
            </el-button>
          </el-form-item> -->

        </el-row>
      </el-form>
    </el-card>
    <div id="table" style="margin:10px;">
      <vxe-toolbar
        ref="toolbarRef"
        custom>
        <template #buttons>
          <el-button type="primary" size="small" @click="deleteChechBoxData">删除</el-button>
          <!-- 将勾选数据导出 -->
          <el-button type="primary" size="small" @click="exportExcelChecked">导出</el-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        ref="unusualTable"
        :key="page.tableKey"
        :loading="tableDataLoading"
        :data="tableData.list"
        :header-cell-style="tableStyle.tableHeaderCellStyle"
        :cell-style="tableStyle.tableCellStyle"
        :height="600"
        :column-config="{resizable: true}"
        :row-config="{ isCurrent: true, isHover: true}"
        :sort-config="sortConfig"
        class="mytable-scrollbar"
        border
        stripe
        resizable
        @checkbox-change="checkboxChangeEvent"
        @checkbox-all="checkboxChangeEvent"
        @sort-change="tableSortChange"
      >
        <vxe-column type="checkbox" width="60" fixed="left" align="center"/>
        <vxe-column field="deptNo" title="营业所" width="100" show-overflow fixed="left" sortable/>
        <vxe-column field= "orderFno" title="订单号" min-width="140" fixed="left" sortable/>
        <vxe-column field= "modelno" title="型号" min-width="150" show-overflow fixed="left" sortable/>
        <vxe-column field= "quantity" title="数量" width="80" fixed="left" sortable/>
        <vxe-column field= "customerNo" title="客户代码" width="120" sortable/>
        <!--<vxe-column field= "userNo" title="用户代码" width="120" sortable/>-->
        <vxe-column field= "endUser" title="最终用户" width="120" sortable/>
        <vxe-column field= "purchaseDate" title="采购日期" width="120" sortable/>
        <vxe-column field= "replyOrderNo" title="供应商接单号" width="150" sortable/>
        <vxe-column field= "dlvModdate" title="返信纳期" width="150" sortable/>
        <vxe-column field= "unusualType" title="异常订单类别" width="150" sortable>
          <template #default="{ row, column, rowIndex, columnIndex }">
            <el-tag v-if="row.unusualType" size="mini">{{ row.unusualType }}</el-tag>
          </template>
        </vxe-column>

        <!--<vxe-column field= "unusualDescEng" title="AS400异常信息" min-width="150" show-overflow sortable/>-->
        <vxe-column field= "unusualDescChn" title="异常类型说明" min-width="150" show-overflow="ellipsis" sortable>
          <template #default="{ row, column, rowIndex, columnIndex }">
            <el-popover
              ref="popover"
              placement="top-start"
              trigger="click"
            >
              <!--悬浮的展示表格-->
              <vxe-table
                :show-header="false"
                :data="unusualDescInfo(row)"
                :cell-style="tableStyle.tableCellStyle"
              >
                <vxe-column field="label" min-width="120"/>
                <vxe-column field="value" min-width="200"/>
              </vxe-table>
              <!--触发器-->
              <div slot="reference" style="cursor: pointer">
                <!--加粗的描述信息-->
                {{ row.unusualDescChn }}
              </div>
            </el-popover>
          </template>
        </vxe-column>
        <vxe-column field= "handleStatus" title="异常处理状态" width="150" sortable/>
        <vxe-column field= "recordCount" title="异常提醒次数" width="150" sortable/>
        <vxe-column field= "jobDeptName" title="异常处理担当部门" min-width="180" show-overflow sortable/>
        <vxe-column field= "replyDate" title="异常回复日期" width="140" show-overflow sortable/>
        <vxe-column field= "replyContent" title="异常回复内容" width="140" show-overflow sortable/>
        <vxe-column field= "inquiryDate" title="异常反馈日期" width="140" show-overflow sortable/>
        <vxe-column field= "replyJapDate" title="回复供应商日期" width="150" show-overflow sortable/>
        <vxe-column field= "handleUserName" title="异常回复供应商操作人" width="200" sortable/>
        <vxe-column field= "completeDate" title="异常处理完成时间" width="180" show-overflow sortable/>
        <vxe-column title="操作" width="120" fixed="right" >
          <template #default="{ row, column, rowIndex, columnIndex }">
            <el-button v-if="row.handleStatus === '待处理'" type="primary" size="small" @click="handleReplyJapan(row)">已回复日本</el-button>
          </template>
        </vxe-column>
        <template #loading>
          <!-- <img src="@/assets/img/loading.gif"> -->
        </template>
      </vxe-table>
      <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="searchTableData" />
    </div>
    <!-- 订单号批量查询 -->
    <el-dialog
      :visible.sync="batchSearchDialog.show"
      title="请输入订单号，以回车为间隔"
      width="400px"
    >
      <el-row>
        <el-input
          v-model="batchSearchDialog.text"
          :autosize="{minRows: 10 ,maxRows: 20}"
          type="textarea"
        />
      </el-row>
      <el-row style="text-align: center">
        <el-button
          type="primary"
          size="small"
          @click="batchSearchEvent"
        >查询
        </el-button>
      </el-row>
    </el-dialog>
    <exportExcel ref="exportExcelVue"/>
  </div>
</template>

<script>
import { findDeptDict } from '@/api/warehouseManage'
import { exportUnusual, searchUnusual, deleteUnusualByIds, updateUnusualById } from '@/api/unusual'
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import ExportExcel from '@/components/ExportExcel'
import moment from 'moment/moment'
export default {
  name: 'PurchaseUnusualQuery',
  components: { Pagination, Department, ExportExcel },
  data() {
    return {
      pickerOptions: {
        shortcuts: [
          {
            text: '一个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment()
                .startOf('day')
                .subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '三个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment()
                .startOf('day')
                .subtract(3, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '半年',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment()
                .startOf('day')
                .subtract(6, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '一年',
            onClick(picker) {
              const end = moment().format('YYYY-MM-DD')
              const start = moment()
                .startOf('day')
                .subtract(1, 'year')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '向前一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).subtract(1, 'months')
              const start = moment(picker.value[0]).subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '向后一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).add(1, 'months')
              const start = moment(picker.value[0]).add(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
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
      buttons: [],
      DictData: {
        department: [],
        handleStatus: [{ code: 1, desc: '待处理' }, { code: 2, desc: '已回复供应商' }, { code: 3, desc: '处理完成' }],
        unusualType: [{ code: '0', desc: '接单前异常' }, { code: '1', desc: '接单后异常' }],
        jobDeptName: [{ code: '业务课', desc: '业务课' }, { code: '在库企画课', desc: '在库企画课' }, { code: '该订单下单的部门/营业所', desc: '该订单下单的部门/营业所' }]
      },
      tableDataLoading: false,
      tableData: {
        tableColumn: [
          { field: 'deptNo', title: '营业所' },
          { field: 'orderFno', title: '订单号' },
          { field: 'modelno', title: '型号' },
          { field: 'quantity', title: '数量' },
          { field: 'customerNo', title: '客户代码' },
          // { field: 'userNo', title: '用户代码' },
          { field: 'endUser', title: '最终用户' },
          { field: 'purchaseDate', title: '采购日期' },
          { field: 'replyOrderNo', title: '供应商接单号' },
          { field: 'dlvModdate', title: '返信纳期' },
          { field: 'unusualType', title: '异常订单类别' },
          { field: 'unusualDescEng', title: 'AS400异常信息' },
          { field: 'unusualDescChn', title: '异常类型说明' },
          { field: 'jobDeptName', title: '异常处理担当部门' },
          { field: 'inquiryDate', title: '异常反馈日期' },
          { field: 'recordCount', title: '异常提醒次数' },
          { field: 'handleStatus', title: '异常处理状态' },
          { field: 'replyJapDate', title: '回复供应商日期' },
          { field: 'handleUserName', title: '异常回复供应商操作人' },
          { field: 'completeDate', title: '异常处理完成时间' },
          { field: 'replyDate', title: '异常回复日期' },
          { field: 'replyContent', title: '异常回复内容' }
        ],
        list: [],
        exportData: []
      },
      checkboxData: [],
      searchMenuData: {},
      searchCondition: {
        condition: {}
      },
      total: 0,
      page: {
        pageNumber: 1,
        pageSize: 50,
        orderBy: '',
        sortField: 'record_date',
        sortBy: 'asc'
      },
      // 表格样式
      tableStyle: {
        tableHeaderCellStyle: {
          backgroundColor: 'rgb(117, 144, 168)',
          color: 'rgba(253, 253, 253, 0.938)',
          fontSize: '14px',
          padding: '5px'
        },
        tableCellStyle: { padding: '0px 1px', height: '30px' }
      },
      batchSearchDialog: {
        show: false,
        text: ''
      }
    }
  },
  computed: {
    // 表格排序配置项（只会在表格初始化时被触发一次）所以每次触发排序要更新table-key 重新渲染表格
    sortConfig() {
      const obj = {
        remote: true, // 开启服务端排序
        defaultSort: {
          field: this.page.sortField,
          order: this.page.sortBy
        }
      }
      return obj
    }
  },
  watch: {},
  mounted() {
    // 将表格和工具栏进行关联
    const $table = this.$refs.unusualTable
    const $toolbar = this.$refs.toolbarRef
    if ($table && $toolbar) {
      $table.connect($toolbar)
    }
  },
  created() {
    // 营业所
    findDeptDict().then(result => {
      result.forEach(dict => {
        this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
      })
    })
  },
  methods: {
    // 导出查询数据，不是分页数据，而是所有的数据
    exportExcelSearched() {
      this.searchExportTableData()
    },
    // 查询要导出的数据
    searchExportTableData() {
      exportUnusual(this.searchCondition.condition).then(res => {
        if (res.success) {
          var list = res.data
          list.forEach(item => {
            item.deptNo = this.deptFormatter(item.deptNo)
            item.jobDeptName = this.deptFormatter(item.jobDeptName)
            item.unusualType = this.unusualTypeFormatter(item.unusualType)
            item.handleStatus = this.handleStatusFormatter(item.handleStatus)
            item.recordDate = this.dateTimeFormatter(item.recordDate)
            item.replyJapDate = this.dateTimeFormatter(item.replyJapDate)
            item.completeDate = this.dateTimeFormatter(item.completeDate)
            item.purchaseDate = this.dateFormatter(item.purchaseDate)
            item.replyDate = this.dateFormatter(item.replyDate)
          })
          this.tableData.exportData = list
          this.$refs.exportExcelVue.initExportExcel(this.tableData.exportData, this.tableData.tableColumn)
        } else {
          this.smcErrorMsg(res.message)
        }
      })
    },
    // 导出选中
    exportExcelChecked() {
      this.$refs.exportExcelVue.initExportExcel(this.checkboxData, this.tableData.tableColumn)
    },
    // 复选框事件
    checkboxChangeEvent(data) {
      this.checkboxData = data.records
    },
    // 处理按钮
    handleReplyJapan(row) {
      updateUnusualById(row.id, this.$store.getters.name).then(res => {
        if (res.success) {
          this.smcInfoMsg(res.message)
          this.menuSearchEvent()
        } else {
          this.smcErrorMsg('处理失败：' + res.message)
        }
      })
    },
    // 删除按钮
    deleteChechBoxData() {
      var ids = []
      this.checkboxData.forEach(item => {
        if (typeof item.id !== 'undefined') {
          ids.push(item.id)
        }
      })
      if (ids.length > 0) {
        this.$confirm('确定要删除勾选的' + ids.length + '行数据？', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          deleteUnusualByIds(ids).then(res => {
            if (res.success) {
              this.smcInfoMsg(res.message)
              this.menuSearchEvent()
            } else {
              this.smcErrorMsg('删除失败：' + res.message)
            }
          })
        })
      } else {
        this.smcWarningMsg('请先勾选要删除的数据')
      }
    },
    // 点击弹出异常信息说明
    unusualDescInfo(row) {
      var tableData = []
      var column = [{ code: 'unusualDescEng', desc: 'AS400异常信息' }, { code: 'unusualDescChn', desc: '异常类型说明' }]
      for (const key in row) {
        column.forEach(item => {
          if (item.code === key) {
            tableData.push({ label: item.desc, value: row[key] })
          }
        })
      }
      return tableData
    },
    // 打开批量搜索窗口
    openBatchSearchWindow() {
      this.batchSearchDialog.show = true
    },
    // 批量搜索事件
    batchSearchEvent() {
      const text = this.batchSearchDialog.text
      if (!text || text.replace(/\s*/g, '').length < 7) {
        this.smcInfoMsg('请填写至少一个订单号')
        return false
      }
      // 用回车分割
      const orderFno = text.split('\n')
      this.searchCondition.condition = { orderFnoList: orderFno }
      this.searchTableData()
      this.batchSearchDialog.show = false
    },
    // 菜单搜索事件
    menuSearchEvent() {
      this.searchCondition.condition = this.searchMenuData
      this.searchTableData()
    },
    // 单独的查询方法，不包括查询补充查询条件
    searchTableData() {
      this.tableData.exportData = []
      this.searchCondition.pageNumber = this.page.pageNumber
      this.searchCondition.pageSize = this.page.pageSize
      this.searchCondition.orderBy = this.page.orderBy
      this.tableDataLoading = true
      searchUnusual(this.searchCondition).then(res => {
        if (res.success) {
          this.total = res.data.total
          var list = res.data.list
          list.forEach(item => {
            item.deptNo = this.deptFormatter(item.deptNo)
            item.jobDeptName = this.deptFormatter(item.jobDeptName)
            item.unusualType = this.unusualTypeFormatter(item.unusualType)
            item.handleStatus = this.handleStatusFormatter(item.handleStatus)
            item.recordDate = this.dateTimeFormatter(item.recordDate)
            item.replyJapDate = this.dateTimeFormatter(item.replyJapDate)
            item.completeDate = this.dateTimeFormatter(item.completeDate)
            item.purchaseDate = this.dateFormatter(item.purchaseDate)
            item.inquiryDate = this.dateFormatter(item.inquiryDate)
          })
          this.tableData.list = list
        } else {
          this.smcErrorMsg(res.message)
        }
        this.tableDataLoading = false
      })
    },

    // 表格排序
    tableSortChange({ column, field, order }) {
      this.page.tableKey = Math.random()
      this.page.sortField = field
      this.page.sortBy = order
      this.page.orderBy = field + ' ' + order
      if (order === null) {
        this.page.orderBy = ''
      }
      this.menuSearchEvent()
    },

    handleStatusFormatter(value) {
      const item = this.DictData.handleStatus.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    unusualTypeFormatter(value) {
      const item = this.DictData.unusualType.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    // 【营业所选择组件】
    changeDeptMenuEvent(val) {
      this.searchMenuData.deptNo = val
    },
    // 营业所字典格式化
    deptFormatter(value) {
      const item = this.DictData.department.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    // 日期格式化
    dateTimeFormatter(value) {
      return value ? moment(value).format('YYYY-MM-DD HH:mm:ss') : ''
    },
    dateFormatter(value) {
      return value ? moment(value).format('YYYY-MM-DD') : ''
    }
  }
}
</script>
<style scoped>
.filter-container {
  padding-bottom: 10px;
}
.filter-container .filter-item {
    display: inline-block;
    vertical-align: middle;
    margin-bottom: 0px;
  }
.el-button--primary {
  color: #337AB7;
  background-color: #fff;
  border:1px solid #337AB7;
}
.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #337AB7;
  color: #fff;
}
</style>

<style scoped lang="scss">
.el-form-item {
  margin: 0 5px;
  padding-top: 0px;
}

/*滚动条整体部分*/
.mytable-scrollbar ::-webkit-scrollbar {
  width: 10px;
  height: 10px;
}
/*滚动条的轨道*/
.mytable-scrollbar ::-webkit-scrollbar-track {
  background-color: #ffffff;
}
/*滚动条里面的小方块，能向上向下移动*/
.mytable-scrollbar ::-webkit-scrollbar-thumb {
  background-color: #f1f1f1ff;
  border-radius: 5px;
  border: 1px solid #f1f1f1ff;
}
.mytable-scrollbar ::-webkit-scrollbar-thumb:hover {
  background-color: #a8a8a8;
}
.mytable-scrollbar ::-webkit-scrollbar-thumb:active {
  background-color: #787878;
}
/*边角，即两个滚动条的交汇处*/
.mytable-scrollbar ::-webkit-scrollbar-corner {
  background-color: #ffffff;
}
</style>
