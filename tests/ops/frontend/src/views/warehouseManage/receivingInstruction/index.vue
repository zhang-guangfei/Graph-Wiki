<template>
  <div class="app-container">

    <el-card style="height:600px;">
      <!-- 搜索栏 -->
      <el-row :gutter="50">
        <el-col :span="5">
          <template>
            <el-select v-model="queryCondition.condition.roType" placeholder="入库类型" style="width:100%" clearable @change="getTableDataEvent">
              <el-option v-for="item in menuData.roTypeMenu" :key="item.code" :value="item.code" :label="item.desc" />
            </el-select>
          </template>
        </el-col>
        <el-col :span="5">
          <template>
            <el-select v-model="queryCondition.condition.roStatus" placeholder="状态" style="width:100%" clearable @change="getTableDataEvent" >
              <el-option v-for="item in menuData.roStatusMenu" :key="item.code" :value="item.code" :label="item.desc" />
            </el-select>
          </template>
        </el-col>
        <el-col :span="5" :offset="1">
          <el-input v-model="queryCondition.condition.roId" placeholder="入库指令号" clearable @keyup.enter.native="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
        <el-col :span="5">
          <el-input v-model="queryCondition.condition.invoiceNo" placeholder="发票号" clearable @keyup.enter.native="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
      </el-row>
      <el-row :gutter="50">
        <el-col :span="5">
          <template>
            <el-select v-model="queryCondition.condition.warehouseCode" placeholder="仓库" style="width:76%" clearable @change="getTableDataEvent">
              <el-option v-for="item in menuData.warehouseNameMenu" :key="item.code" :value="item.code" :label="item.codeName"/>
            </el-select>
            <el-button type="primary" @click="selectWarehouse">选择</el-button>
          </template>
        </el-col>
        <el-col :span="5">
          <template>
            <el-select v-model="queryCondition.condition.supplierId" placeholder="供应商" style="width:100%" clearable @change="getTableDataEvent">
              <el-option v-for="item in menuData.supplierIdMenu" :key="item.code" :value="item.code" :label="item.desc" />
            </el-select>
          </template>
        </el-col>
        <el-col :span="5" :offset="1">
          <el-date-picker v-model="queryCondition.condition.beginTime" placeholder="开始时间" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" type="datetime" clearable @change="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
        <el-col :span="5">
          <el-date-picker v-model="queryCondition.condition.endTime" placeholder="结束时间" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" type="datetime" clearable @change="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" @click="getTableDataEvent" >搜索</el-button>
        </el-col>
      </el-row>
      <el-divider />
      <!-- 表格区域 -->
      <el-scrollbar style="height:100%">
        <el-table
          ref="multipleTable"
          :data="tableData.list"
          :header-cell-style="{padding: '5px'}"
          :cell-style="{padding: '5px'}"
          border
          highlight-current-row
          stripe
          height="400px"
          @cell-click="showDeliveryOrderDetail">
          <!-- 表头字段 -->
          <el-table-column v-for="column in tableColumns" :key="column.prop" :prop="column.prop" :label="column.label" :width="column.width" :formatter="column.formatter" align="center" />
        </el-table>
      </el-scrollbar>
      <!-- 分页工具 -->
      <el-pagination :current-page="tableData.pageNum" :page-sizes="[ 5,10, 30, 50, 100]" :page-size="tableData.pageSize" :total="tableData.total" background layout="total, sizes, prev, pager, next" @size-change="handleSizeChange" @current-change="handleCurrentChange" />
    </el-card>
    <!-- 仓库选择弹窗 -->
    <el-dialog :visible.sync="dialogFormVisible" title="仓库" width="650px">
      <el-form ref="form" :inline="true" :model="warehouseForm" size="small">
        <el-form-item >
          <el-input v-model="warehouseForm.warehouseCode" placeholder="仓库代码" style="width:100px" clearable />
        </el-form-item>
        <el-form-item >
          <el-input v-model="warehouseForm.warehouseName" placeholder="仓库名称" clearable />
        </el-form-item>
        <el-form-item >
          <el-select v-model="warehouseForm.warehouseType" placeholder="仓库类别" style="width:100px" clearable @change="listWarehouseinfo">
            <el-option v-for="item in warehousetypeList" :key="item.code" :label="item.codeName" :value="item.code" clearable/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="small" @click="listWarehouseinfo"/>
        </el-form-item>
      </el-form>
      <el-table :data="warehouseData.filter(data => !warehouseForm.warehouseName || data.warehouseName.includes(warehouseForm.warehouseName))">
        <el-table-column property="warehouseCode" label="仓库代码" width="100px"/>
        <el-table-column property="warehouseName" label="仓库名称" width="300px"/>
        <el-table-column :formatter="getWarehouseType" property="warehouseType" label="仓库类别" width="100px"/>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="edit(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-card style="height:250px;">
      <el-tabs v-model="tabName" style="margin: -10px 10px 30px 10px" type="card">
        <el-tab-pane label="明细" name="item" >
          <!-- 表格区域 -->
          <el-table :data="itemTableData" border stripe>
            <!-- 表头字段 -->
            <el-table-column v-for="column in itemTableColumns" :key="column.prop" :prop="column.prop" :label="column.label" :formatter="column.formatter" align="center"/>
          </el-table>
        </el-tab-pane>
        <el-tab-pane label="操作记录" name="log" >
          <!-- 表格区域 -->
          <el-table :data="logTableData" border stripe height="180px">
            <!-- 表头字段 -->
            <el-table-column v-for="column in logTableColumns" :key="column.prop" :prop="column.prop" :label="column.label" :formatter="column.formatter" align="center"/>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script>
import { findReceivingInstruction, findReceivingInstructionItem, findReceivingInstructionOperationRecord } from '@/api/warehouseManage'
import { getDataByPid } from '@/api/dictionary'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import { Constants } from '@/utils/optionUtils'
import moment from 'moment'
export default {
  name: 'ReceivingInstruction',
  data() {
    return {
      warehouseClassCode: 4001,
      dialogFormVisible: false,
      warehouseForm: {
        warehouseName: '',
        warehouseCode: '',
        warehouseType: ''
      },
      warehousetypeList: [],
      warehouseData: [],
      // 菜单数据
      menuData: {
        warehouseNameMenu: [],
        roTypeMenu: [],
        roStatusMenu: [],
        supplierIdMenu: []
      },
      // 查询条件
      queryCondition: {
        condition: {
          warehouseCode: '',
          roId: '',
          invoiceNo: ''
        },
        pageNumber: 1,
        pageSize: 50,
        orderBy: 'cre_time'
      },
      // 表头字段
      tableColumns: [
        {
          label: '到货发票号',
          prop: 'invoiceNo'
        },
        {
          label: '入库指令号',
          prop: 'roId',
          width: 180
        },
        {
          label: '关联单号',
          prop: 'bindOrderId'
        },
        {
          label: '客户号',
          prop: 'customerNo'
        },
        {
          label: '入库类型',
          prop: 'roType',
          formatter: this.typeFormatter
        },
        {
          label: '10位订单号',
          prop: 'subOrderId'
        },
        {
          label: '仓库编号',
          prop: 'warehouseCode'
        },
        {
          label: '状态',
          prop: 'roStatus',
          formatter: this.statusFormatter
        },
        {
          label: '运输方式',
          prop: 'transType'
        },
        {
          label: '承运商',
          prop: 'carried'
        },
        {
          label: '运单号',
          prop: 'expressCode'
        },
        {
          label: '供应商',
          prop: 'supplierId',
          formatter: this.supplierIdFormatter
        },
        {
          label: '备注',
          prop: 'remark'
        },
        {
          label: '创建时间',
          prop: 'creTime',
          width: 150,
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '' : moment(cellValue).format('YYYY-MM-DD HH:mm')
          }
        },
        {
          label: '修改时间',
          prop: 'modifyTime',
          width: 150,
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '' : moment(cellValue).format('YYYY-MM-DD HH:mm')
          }
        }
      ],
      // 表格数据
      tableData: {},
      // 展示的表名
      tabName: 'item',

      // 明细表头字段
      itemTableColumns: [
        {
          label: '行项目',
          prop: 'roItem'
        },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '数量',
          prop: 'qty'
        },
        {
          label: '已收货数量',
          prop: 'recQty'
        },
        {
          label: '备注',
          prop: 'remark'
        },
        {
          label: '创建时间',
          prop: 'creTime',
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '无' : moment(cellValue).format('YYYY-MM-DD HH:mm')
          }
        }
      ],
      // 明细表数据
      itemTableData: [],
      // 日志表头字段
      logTableColumns: [
        {
          label: '操作内容',
          prop: 'logStatusCode',
          formatter: this.statusFormatter
        },
        {
          label: '员工',
          prop: 'creator'
        }, {
          label: '备注',
          prop: 'remark'
        },
        {
          label: '操作时间',
          prop: 'creTime',
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '无' : moment(cellValue).format('YYYY-MM-DD HH:mm')
          }
        }
      ],
      // 日志表数据
      logTableData: []
    }
  },
  created() {
    this.initData()
    this.getTableDataEvent()
    this.initwarehousetypeList()
    this.listWarehouseinfo()
  },

  methods: {

    // 菜单数据初始化
    initData() {
      // 从数据字典中获取入库指令
      getDataByPid(Constants.roTypeList).then(res => {
        res.forEach(element => {
          this.menuData.roTypeMenu.push({ code: element.extcode, desc: element.name })
        })
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      // 从数据字典中获取仓库名
      // getDataByPid(Constants.warehouseNameList).then(res => {
      //   res.forEach(element => {
      //     this.menuData.warehouseNameMenu.push({ code: element.extcode, desc: element.name })
      //   })
      // }).catch(res => {
      //   this.smcErrorMsg(res.message)
      // })
      // 从数据字典中获取收货状态
      getDataByPid(Constants.roStatusList).then(res => {
        res.forEach(element => {
          this.menuData.roStatusMenu.push({ code: element.extcode, desc: element.name })
        })
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      // 从数据字典中获取供应商
      getDataByPid(Constants.supplierIdList).then(res => {
        res.forEach(element => {
          this.menuData.supplierIdMenu.push({ code: element.extcode, desc: element.name })
        })
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    // 获取表格数据
    getTableDataEvent() {
      findReceivingInstruction(this.queryCondition).then(res => {
        this.tableData = res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    initwarehousetypeList() {
      getDataCodesTree(this.warehouseClassCode).then(res => {
        if (res.success) {
          this.warehousetypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: this.warehouseForm.warehouseType,
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    edit(row) {
      this.queryCondition.condition.warehouseCode = row.warehouseCode
      this.menuData.warehouseNameMenu = []
      const list = { code: row.warehouseCode, codeName: row.warehouseName }
      this.menuData.warehouseNameMenu.push(list)
      this.dialogFormVisible = false
      this.getTableDataEvent()
    },
    selectWarehouse(row) {
      this.dialogFormVisible = true
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
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    // 改变每页条数
    handleSizeChange(newSize) {
      this.queryCondition.pageSize = newSize
      this.getTableDataEvent()
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.queryCondition.pageNumber = newCurrent
      this.getTableDataEvent()
    },
    typeFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.roTypeMenu.find(item => parseInt(item.code) === parseInt(cellValue)) != null) {
        return this.menuData.roTypeMenu.find(item => parseInt(item.code) === parseInt(cellValue)).desc
      }
    },
    statusFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.roStatusMenu.find(item => parseInt(item.code) === parseInt(cellValue)) != null) {
        return this.menuData.roStatusMenu.find(item => parseInt(item.code) === parseInt(cellValue)).desc
      }
    },
    supplierIdFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.supplierIdMenu.find(item => parseInt(item.code) === parseInt(cellValue)) != null) {
        return this.menuData.supplierIdMenu.find(item => parseInt(item.code) === parseInt(cellValue)).desc
      }
    },
    showDeliveryOrderDetail(row, column, event, cell) {
      findReceivingInstructionItem(row.roId).then(res => {
        this.itemTableData = res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      findReceivingInstructionOperationRecord(row.roId).then(res => {
        this.logTableData = res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    }
  }
}
</script>
<style scoped>
.el-dialog {
  display: flex;
  flex-direction: column;
  margin: 0 !important;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  /*height:600px;*/
  max-height: calc(100% - 30px);
  max-width: calc(100% - 30px);
}
.el-dialog .el-dialog__body {
  flex: 1;
  overflow: auto;
}

.el-card {
  margin: 5px;
}
.el-row {
  margin: 10px !important;
}
.el-card .el-table {
  margin: 10px;
  width: 100%;
}
.el-form {
  margin: 0;
}
div /deep/ .el-input__inner {
  width: 100%;
}
/* input的宽度*/
input.el-input__inner {
  width: 100% !important;
}
/* select选择框宽度 */
/* .el-select {
  width: 100% !important;
} */
/*时间选择框宽度*/
.el-date-editor.el-input {
  width: 100% !important;
}
/*超链接字体大小*/
.el-link {
  font-size: 18px;
}
/*input的lable的字体高度*/
.el-form-item .el-form-item__label {
  margin-top: 15px;
}

</style>

