<template>
  <div class="app-container">
    <!-- 加工指令 -->
    <el-card style="height:600px;">
      <!-- 搜索栏 -->
      <el-row :gutter="50">
        <el-col :span="5">
          <template>
            <el-select v-model="queryCondition.condition.pcoSource" placeholder="加工来源" clearable @change="getTableDataEvent">
              <el-option v-for="item in menuData.pcoSourceMenu" :key="item.code" :value="item.code" :label="item.desc" />
            </el-select>
          </template>
        </el-col>
        <el-col :span="5">
          <template>
            <el-select v-model="queryCondition.condition.pcoStatus" placeholder="状态" style="width: 100%" clearable @change="getTableDataEvent" >
              <el-option v-for="item in menuData.pcoStatusMenu" :key="item.code" :value="item.code" :label="item.desc" />
            </el-select>
          </template>
        </el-col>
        <el-col :span="5" :offset="1">
          <el-input v-model="queryCondition.condition.pcoId" placeholder="加工指令号" clearable @keyup.enter.native="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
      </el-row>
      <el-row :gutter="50">
        <el-col :span="5">
          <template>
            <el-select v-model="queryCondition.condition.warehouseCode" placeholder="仓库" clearable style="width:63%" @change="getTableDataEvent">
              <el-option v-for="item in menuData.warehouseNameMenu" :key="item.code" :value="item.code" :label="item.codeName"/>
            </el-select>
            <el-button type="primary" @click="selectWarehouse">选择</el-button>
          </template>
        </el-col>
        <el-col :span="5">
          <el-input v-model="queryCondition.condition.subOrderId" placeholder="10位订单号" clearable @keyup.enter.native="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
        <el-col :span="5" :offset="1">
          <el-date-picker v-model="queryCondition.condition.beginTime" placeholder="开始时间" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" type="datetime" clearable @change="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
        <el-col :span="5">
          <el-date-picker v-model="queryCondition.condition.endTime" placeholder="结束时间" value-format="yyyy-MM-dd HH:mm" format="yyyy-MM-dd HH:mm" type="datetime" clearable @change="getTableDataEvent" @clear="getTableDataEvent"/>
        </el-col>
        <el-col :span="1">
          <el-button v-permission="['272974']" icon="el-icon-search" @click="getTableDataEvent" >搜索</el-button>
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
import { findMachiningInstruction, findMachiningInstructionItem, findMachiningInstructionOperationRecord } from '@/api/warehouseManage'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import moment from 'moment'
export default {
  name: 'MachiningInstruction',
  data() {
    return {
      warehouseClassCode: 4001,
      dialogFormVisible: false,
      warehouseForm: {},
      warehouseData: [],
      warehousetypeList: [],
      // 菜单数据 1.加工来源 2.状态 3.仓库 4加工类型 5 标识
      menuData: {
        pcoSourceMenu: [],
        pcoStatusMenu: [],
        warehouseNameMenu: [],
        pcoTypeMenu: [],
        prodTypeMenu: []
      },
      // 查询条件
      queryCondition: {
        condition: {
          warehouseCode: '',
          pcoId: '',
          subOrderId: ''
        },
        pageNumber: 1,
        pageSize: 50,
        orderBy: 'cre_time'
      },
      // 表头字段主表
      tableColumns: [
        {
          label: '加工指令号',
          prop: 'pcoId',
          width: 150
        },
        {
          label: '加工来源',
          prop: 'pcoSource',
          formatter: this.pcoSourceFormatter
        },
        {
          label: '仓库',
          prop: 'warehouseCode',
          formatter: this.warehouseCodeFormatter
        },
        {
          label: '客户号',
          prop: 'customerNo'
        },
        {
          label: '10位订单号',
          prop: 'subOrderId'
        },
        {
          label: '型号',
          prop: 'modelNo',
          width: 200
        },
        {
          label: '数量',
          prop: 'qty'
        },
        {
          label: '状态',
          prop: 'pcoStatus',
          formatter: this.pcoStatusFormatter
        },
        {
          label: '加工类型',
          prop: 'pcoType',
          formatter: this.pcoTypeFormatter
        },
        {
          label: '标识',
          prop: 'prodType',
          formatter: this.prodTypeFormatter
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
          prop: 'pcoItem'
        },
        {
          label: '子型号',
          prop: 'subModelno'
        },
        {
          label: '子数量',
          prop: 'subQty'
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
          formatter(row, column, cellValue, index) {
            var roStatus = [
              { code: '1', desc: '初始' },
              { code: '2', desc: '已下发WMS' },
              { code: '3', desc: 'WMS打印' },
              { code: '4', desc: 'WMS拣货' },
              { code: '5', desc: 'WM打包' },
              { code: '6', desc: 'WMS称重' },
              { code: '7', desc: 'WMS交接' },
              { code: '8', desc: 'WMS发货' },
              { code: '9', desc: 'WMS签收' }
            ]
            return roStatus.find(item => item.code === cellValue).desc
          }
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
    this.listWarehouseinfo()
    this.initwarehousetypeList()
  },

  methods: {

    // 菜单数据初始化 1加工来源 主动加工，销售加工 2.状态 等待加工、加工中、加工完成
    initData() {
      this.menuData.pcoSourceMenu = [
        { code: 1, desc: '主动加工' },
        { code: 2, desc: '销售加工' }
      ]
      this.menuData.pcoStatusMenu = [
        { code: 1, desc: '等待加工' },
        { code: 2, desc: '加工中' },
        { code: 3, desc: '加工完成' }
      ]
      // this.menuData.warehouseNameMenu = [
      //   { code: 'KBJ', desc: '北京仓' },
      //   { code: 'KGZ', desc: '广州仓' },
      //   { code: 'KSH', desc: '上海仓' }
      // ]
      this.menuData.pcoTypeMenu = [
        { code: 1, desc: '拆分' },
        { code: 2, desc: '组装' }
      ]
      this.menuData.prodTypeMenu = [
        { code: 1, desc: '同捆' },
        { code: 2, desc: '组立' },
        { code: 3, desc: '装配生成' }
      ]
    },
    // 获取表格数据
    getTableDataEvent() {
      findMachiningInstruction(this.queryCondition).then(res => {
        this.tableData = res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    selectWarehouse(row) {
      this.dialogFormVisible = true
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
    initwarehousetypeList() {
      getDataCodesTree(this.warehouseClassCode).then(res => {
        if (res.success) {
          this.warehousetypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
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
    pcoSourceFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.pcoSourceMenu.find(item => item.code === cellValue) != null) {
        return this.menuData.pcoSourceMenu.find(item => item.code === cellValue).desc
      }
    },
    pcoStatusFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.pcoStatusMenu.find(item => item.code === cellValue) != null) {
        return this.menuData.pcoStatusMenu.find(item => item.code === cellValue).desc
      }
    },
    warehouseNameFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.warehouseNameMenu.find(item => item.code === cellValue) != null) {
        return this.menuData.warehouseNameMenu.find(item => item.code === cellValue).desc
      }
    },
    pcoTypeFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.pcoTypeMenu.find(item => item.code === cellValue) != null) {
        return this.menuData.pcoTypeMenu.find(item => item.code === cellValue).desc
      }
    },
    prodTypeFormatter(row, column, cellValue, index, menu) {
      if (this.menuData.prodTypeMenu.find(item => item.code === cellValue) != null) {
        return this.menuData.prodTypeMenu.find(item => item.code === cellValue).desc
      }
    },
    showDeliveryOrderDetail(row, column, event, cell) {
      findMachiningInstructionItem(row.pcoId).then(res => {
        this.itemTableData = res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      findMachiningInstructionOperationRecord(row.pcoId).then(res => {
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
ul,
li {
  list-style-type: none;
  padding: 5px;
}
</style>

