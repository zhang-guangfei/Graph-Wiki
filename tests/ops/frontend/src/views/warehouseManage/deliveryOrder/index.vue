<template>
  <div class="app-container">
    <el-card style="height:880px;">
      <!-- 搜索栏 -->
      <el-row :gutter="20">
        <el-col :span="3">
          <el-input
            v-model="queryCondition.condition.customerNo"
            style="width: auto"
            placeholder="客户号"
            clearable
            @keyup.enter.native="getTableDataEvent"
            @clear="getTableDataEvent"
          />
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryCondition.condition.warehouseCode"
            placeholder="仓库名称"
            clearable
            style="width:63%"
            @change="getTableDataEvent"
          >
            <el-option
              v-for="item in menuData.warehouseNameMenu"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
          <el-button type="primary" size="medium " @click="selectWarehouse">选择</el-button>
        </el-col>
        <el-col :span="3">
          <el-input
            v-model="queryCondition.condition.doId"
            style="width: auto"
            placeholder="出库指令号"
            clearable
            @keyup.enter.native="getTableDataEvent"
            @clear="getTableDataEvent"
          />
        </el-col>
        <el-col :span="3">
          <el-input
            v-model="queryCondition.condition.orderId"
            style="width: auto"
            placeholder="十位客户单号"
            clearable
            @keyup.enter.native="getTableDataEvent"
            @clear="getTableDataEvent"
          />
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryCondition.condition.doType"
            placeholder="出库类型"
            clearable
            @change="getTableDataEvent"
          >
            <el-option
              v-for="item in menuData.deliveryOrderTypeMenu"
              :key="item.code"
              :value="item.code"
              :label="item.desc"
            />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select
            v-model="queryCondition.condition.doStatus"
            placeholder="发货状态"
            clearable
            @change="getTableDataEvent"
          >
            <el-option
              v-for="item in menuData.deliveryOrderStatusMenu"
              :key="item.code"
              :value="item.code"
              :label="item.desc"
            />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="queryCondition.condition.beginTime"
            style="width: 180px"
            placeholder="开始时间"
            value-format="yyyy-MM-dd"
            type="date"
            clearable
            @change="getTableDataEvent"
            @clear="getTableDataEvent"
          />
        </el-col>
        <el-col :span="3">
          <el-date-picker
            v-model="queryCondition.condition.endTime"
            style="width: 180px"
            placeholder="结束时间"
            value-format="yyyy-MM-dd"
            type="date"
            clearable
            @change="getTableDataEvent"
            @clear="getTableDataEvent"
          />
        </el-col>
      </el-row>
      <el-divider/>
      <!-- 表格区域 -->
      <el-table
        ref="multipleTable"
        :data="tableData.list"
        :header-cell-style="{padding: '5px'}"
        :cell-style="{padding: '5px'}"
        :height="tableSize.height"
        highlight-current-row
        border
        stripe
        @row-click="showDeliveryOrderDetail"
      >
        <!-- 表头字段 -->
        <el-table-column
          v-for="column in tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
          show-overflow-tooltip
        />
        <!--操作栏
        <el-table-column
          label="操作"
          width="140px"
        >
          <template slot-scope="{}">
            <el-button
              type="primary"
              size="mini"
            >转定库存
            </el-button>
          </template>
        </el-table-column> -->
      </el-table>
      <!-- 分页工具 -->
      <el-pagination
        :current-page="tableData.pageNum"
        :page-sizes="[ 10,15, 20, 50, 100]"
        :page-size="tableData.pageSize"
        :total="tableData.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
      <el-divider><el-button :icon="tableSize.button" style="font-size: 25px" type="text" @click="changeTableHeight"/></el-divider>
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
      <!-- 详细信息 -->
      <el-tabs v-model="OrderDetail.tabName" type="card">
        <el-tab-pane label="明细" name="item" >
          <!-- 表格区域 -->
          <el-table
            :data="OrderDetail.itemTableData"
            :header-cell-style="{padding: '5px'}"
            :cell-style="{padding: '5px'}"
            height="230px"
            border
            stripe
          >
            <!-- 表头字段 -->
            <el-table-column
              v-for="column in OrderDetail.itemTableColumns"
              :key="column.prop"
              :prop="column.prop"
              :label="column.label"
              :formatter="column.formatter"
            />
          </el-table>

        </el-tab-pane>
        <el-tab-pane
          label="操作记录"
          name="log"
        >
          <!-- 表格区域 -->
          <el-table
            :data="OrderDetail.logTableData"
            border
            stripe
          >
            <!-- 表头字段 -->
            <el-table-column
              v-for="column in OrderDetail.logTableColumns"
              :key="column.prop"
              :prop="column.prop"
              :label="column.label"
              :formatter="column.formatter"
            />
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </el-card>

  </div>
</template>

<script>
import { findDeliveryOrder, findDeliveryOrderItem } from '@/api/warehouseManage'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import moment from 'moment'

export default {
  name: 'DeliveryOrder',
  data() {
    return {
      tableSize: {
        expand: false,
        height: '',
        button: ''
      },
      // 菜单数据
      menuData: {
        warehouseNameMenu: [],
        deliveryOrderTypeMenu: [],
        deliveryOrderStatusMenu: []
      },
      warehouseClassCode: 4001,
      dialogFormVisible: false,
      warehouseForm: {},
      warehousetypeList: [],
      warehouseData: [],
      // 查询条件
      queryCondition: {
        condition: {
          // customerNo: '',
          // warehouseCode: '',
          // doId: '',
          // subOrderId: '',
          // doType: '',
          // doStatus: null
        },
        pageNumber: 1,
        pageSize: 50,
        orderBy: ''
      },
      // 表头字段
      tableColumns: [
        {
          label: '出库指令号',
          prop: 'doId'
        }, {
          label: '订单号',
          prop: 'orderId'
        },
        {
          label: '项号',
          prop: 'orderItem'
        },
        {
          label: '指令类型',
          prop: 'doType',
          formatter: this.typeFormatter
        },
        {
          label: '状态',
          prop: 'doStatus',
          formatter: this.statusFormatter
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
          label: '备注',
          prop: 'remark'
        },
        {
          label: '创建时间',
          prop: 'creTime',
          formatter: this.dateFormatter
        },
        {
          label: '修改时间',
          prop: 'modifyTime',
          formatter: this.dateFormatter
        }
      ],
      // 表格数据
      tableData: {},
      // 弹窗数据
      OrderDetail: {
        // 默认展示的tab页
        tabName: 'item',
        // 明细表头字段
        itemTableColumns: [
          {
            label: '行项目',
            prop: 'doItem'
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
            label: '已出库数量',
            prop: 'outQty'
          },
          {
            label: '备注',
            prop: 'remark'
          },
          {
            label: '创建时间',
            prop: 'creTime',
            formatter(row, column, cellValue, index) {
              return cellValue == null ? '无' : moment(cellValue).format('YYYY-MM-DD')
            }
          }
        ],
        // 明细表数据
        itemTableData: [],
        // 日志表头字段
        logTableColumns: [
          {
            label: '操作内容',
            prop: 'content'
          },
          {
            label: '员工',
            prop: 'username'
          }, {
            label: '备注',
            prop: 'remark'
          },
          {
            label: '操作时间',
            prop: 'creTime',
            formatter(row, column, cellValue, index) {
              return cellValue == null ? '无' : moment(cellValue).format('YYYY-MM-DD')
            }
          }
        ],
        // 日志表数据
        logTableData: []
      }

    }
  },
  created() {
    this.initData()
    this.getTableDataEvent()
    this.changeTableHeight()
    this.initwarehousetypeList()
    this.listWarehouseinfo()
  },

  methods: {

    // 菜单数据初始化
    initData() {
      // this.menuData.warehouseNameMenu = [
      //   { code: 'KBJ', desc: '北京仓' },
      //   { code: 'KGZ', desc: '广州仓' },
      //   { code: 'KSH', desc: '上海仓' }
      // ]
      this.menuData.deliveryOrderTypeMenu = [
        { code: 'TKCK', desc: '调库出库' },
        { code: 'JYCK', desc: '客户销售' },
        { code: 'DBCK', desc: '调拨出库' },
        { code: 'ZKCK', desc: '组换出库' },
        { code: 'QBCK', desc: '情报占用单' }
      ]
      this.menuData.deliveryOrderStatusMenu = [
        { code: 1, desc: '未发货' },
        { code: 2, desc: '发货中' },
        { code: 3, desc: '发货完成' }
      ]
    },

    // 获取表格数据
    getTableDataEvent() {
      console.log('查询条件：')
      console.log(this.queryCondition)
      findDeliveryOrder(this.queryCondition).then(res => {
        console.log('查询到数据：')
        console.log(res)
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
    // 日期格式化
    dateFormatter(row, column, cellValue, index) {
      return cellValue ? moment(cellValue).format('YYYY-MM-DD HH:mm:SS') : ''
    },
    // 出库类型字典
    typeFormatter(row, column, cellValue, index, menu) {
      const item = this.menuData.deliveryOrderTypeMenu.find(item => item.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 状态字典
    statusFormatter(row, column, cellValue, index, menu) {
      const item = this.menuData.deliveryOrderStatusMenu.find(item => item.code === cellValue)
      return item ? item.desc : cellValue
    },

    showDeliveryOrderDetail(row) {
      findDeliveryOrderItem(row.doId).then(res => {
        console.log('查询到数据：')
        console.log(res.data)
        this.OrderDetail.itemTableData = res.data
        this.OrderDetail.show = true
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },

    changeTableHeight() {
      if (this.tableSize.expand) {
        this.tableSize.expand = false
        this.tableSize.height = '400px'
        this.tableSize.button = 'el-icon-arrow-down'
      } else {
        this.tableSize.expand = true
        this.tableSize.height = '700px'
        this.tableSize.button = 'el-icon-arrow-up'
      }
    }

  }
}
</script>
<style scoped>
.el-divider{
  height: 2px;
  margin: 20px 0;
}
.el-divider__text{
  padding: 0;
}
</style>

