<template>
  <div class="app-container">
    <el-card style="height:880px;">
      <!-- 搜索 -->
      <el-row :gutter="10">
        <el-form>
          <el-col :span="10">
            <el-col :span="5">
              <el-select
                v-model="queryCondition.condition.warehouseType"
                placeholder="仓库类型"
                clearable
                @change="getHouseNameListByType">
                <el-option
                  v-for="item in menuData.warehouseTypeMenu"
                  :key="item.code"
                  :value="item.code"
                  :label="item.desc" />
              </el-select>
            </el-col>
            <el-col :span="5">
              <el-select
                v-model="queryCondition.condition.warehouseCode"
                placeholder="仓库名称"
                clearable
                allow-create
                filterable>
                <el-option
                  v-for="warehouse in menuData.warehouseNameMenu"
                  :key="warehouse.warehouseName"
                  :value="warehouse.warehouseCode"
                  :label="warehouse.warehouseName" />
              </el-select>
            </el-col>
            <el-col :span="5">
              <el-select v-model="queryCondition.condition.inventoryTypeCode" placeholder="库存类别" clearable>
                <el-option
                  v-for="item in menuData.inventoryTypeMenu"
                  :key="item.code"
                  :value="item.code"
                  :label="item.desc" />
              </el-select>
            </el-col>
            <el-col :span="8">
              <el-input
                v-model="queryCondition.condition.modelno"
                placeholder="型号"
                clearable
                style="width: 240px"
                @keyup.enter.native="getTableDataEvent">
                <el-button slot="suffix" type="text" @click="fuzzy = !fuzzy">
                  <i :class="{ fuzzyBTN: fuzzy }" class="el-icon-setting" />
                </el-button>
              </el-input>
            </el-col>
          </el-col>
          <el-col :span="10" style="padding-left: 60px;">
            <el-col :span="5">
              <el-input
                v-model="queryCondition.condition.customerNo"
                placeholder="客户代码"
                clearable
                @keyup.enter.native="getTableDataEvent" />
            </el-col>
            <el-col :span="5">
              <el-input
                v-model="queryCondition.condition.groupCustomerNo"
                placeholder="客户群号"
                clearable
                @keyup.enter.native="getTableDataEvent" />
            </el-col>
            <el-col :span="5">
              <el-input
                v-model="queryCondition.condition.ppl"
                placeholder="PPL"
                clearable
                @keyup.enter.native="getTableDataEvent" />
            </el-col>
            <el-col :span="5">
              <el-input
                v-model="queryCondition.condition.projectCode"
                placeholder="PJ"
                clearable
                @keyup.enter.native="getTableDataEvent" />
            </el-col>
            <el-col :span="4">
              <el-input
                v-model="queryCondition.condition.salesInfoNo"
                placeholder="情报号"
                clearable
                @keyup.enter.native="getTableDataEvent" />
            </el-col>
          </el-col>
          <el-col :span="2">
            <span style="font-size: 15px ;line-height: 40px">有效记录</span>
            <el-switch v-model="queryCondition.condition.available" active-color="#13ce66" inactive-color="#ff4949" />
          </el-col>
          <el-col :span="1">
            <el-button v-permission="['634631', '337017']" icon="el-icon-search" @click="getTableDataEvent" />
          </el-col>
        </el-form>
      </el-row>
      <el-row :gutter="10" style="margin-top: 10px;margin-left: 0px;">
        <el-col :span="2">
          <el-select v-model="queryCondition.condition.rflag" placeholder="风险在库" clearable>
            <el-option
              v-for="item in menuData.rflagMenu"
              :key="item.code"
              :value="item.code"
              :label="item.desc" />
          </el-select>
        </el-col>
        <el-col :span="2" style="margin-left: 25px;">
          <el-button :loading="downLoadInvExcelBtuLoad" icon="el-icon-download" @click="downloadInvExcel" >库存数据</el-button>
        </el-col>
        <el-col :span="2" style="margin-left: 25px;">
          <el-button :loading="downLoadExcelBtuLoad" icon="el-icon-download" @click="downloadExcel" >库存关联</el-button>
        </el-col>
      </el-row>

      <el-divider />
      <!-- 表格区域 -->
      <el-table
        v-loading="loading"
        ref="multipleTable"
        :data="tableData.list"
        :header-cell-style="{ padding: '1px' }"
        :cell-style="{ padding: '5px 5px' }"
        border
        stripe
        height="650">
        <!-- 库存维度字段 -->
        <el-table-column
          v-for="column in tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
          :align="column.align"
          header-align="center" />
        <!-- 库存数量字段 -->
        <el-table-column label="正常在库" header-align="center">
          <el-table-column label="有效" align="right" header-align="center"> <template slot-scope="scope">
            <span>{{ scope.row.qtyn - scope.row.pqtyn }}</span>
          </template>
          </el-table-column>
          <el-table-column prop="qtyn" label="实物" align="right" header-align="center" />
          <el-table-column prop="pqtyn" label="预占" align="right" header-align="center" />
        </el-table-column>
        <el-table-column label="到货未入库" header-align="center">
          <el-table-column prop="qtyw" label="数量" align="right" header-align="center" />
          <el-table-column prop="pqtyw" label="预占" align="right" header-align="center" />
        </el-table-column>
        <el-table-column label="调拨在途" header-align="center">
          <el-table-column prop="qtyd" label="数量" align="right" header-align="center" />
          <el-table-column prop="pqtyd" label="预占" align="right" header-align="center" />
        </el-table-column>
        <el-table-column label="采购在途" header-align="center">
          <el-table-column prop="qtyc" label="数量" align="right" header-align="center" />
          <el-table-column prop="pqtyc" label="预占" align="right" header-align="center" />
        </el-table-column>
        <el-table-column label="生产在途" header-align="center">
          <el-table-column prop="qtyp" label="数量" align="right" header-align="center" />
          <el-table-column prop="pqtyp" label="预占" align="right" header-align="center" />
        </el-table-column>
        <el-table-column label="退货在途" header-align="center">
          <el-table-column prop="qtyr" label="数量" align="right" header-align="center" />
          <el-table-column prop="pqtyr" label="预占" align="right" header-align="center" />
        </el-table-column>
      </el-table>
      <!-- 分页工具 -->
      <el-pagination
        :current-page="tableData.pageNum"
        :page-sizes="[10, 15, 30, 50, 100]"
        :page-size="tableData.pageSize"
        :total="tableData.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange" />
    </el-card>
  </div>
</template>

<script>
import { findInventory, findInventoryType, findHouseCodeAndNameByType, findDeptDict, downLoadInventoryExcel, downloadInvExcel } from
  '@/api/warehouseManage'
import { getDataByPid } from '@/api/dictionary'
import { Constants } from '@/utils/optionUtils'

export default {
  name: 'InventorySearch',
  data() {
    return {
      // 菜单数据
      menuData: {
        // 仓库类别菜单
        warehouseTypeMenu: [],
        // 仓库名称菜单
        warehouseNameMenu: [],
        // 库存类别菜单
        inventoryTypeMenu: [],
        // 风险在库菜单
        rflagMenu: [{ code: 1, desc: '是' }, { code: 0, desc: '否' }],
        // 营业所
        department: []
      },
      // 查询条件
      queryCondition: {
        condition: {
          warehouseCode: '',
          warehouseType: '',
          inventoryTypeCode: '',
          modelno: '',
          customerNo: '',
          groupCustomerNo: '',
          ppl: '',
          projectCode: '',
          salesInfoNo: '',
          available: false,
          rflag: null
        },
        pageNumber: 1,
        pageSize: 30,
        orderBy: 'modelno'
      },
      // 表头字段
      tableColumns: [
        {
          label: '仓库代码',
          prop: 'warehouseCode'
        },
        {
          label: '仓库名称',
          prop: 'warehouseName',
          width: 150
        },
        {
          label: '库存类别',
          prop: 'inventoryTypeCode',
          width: 140,
          formatter: this.inventoryTypeFormatter
        },
        {
          label: '型号',
          prop: 'modelno',
          width: 200
        },
        {
          label: '客户代码',
          prop: 'customerNo'
        },
        {
          label: '客户群代码',

          prop: 'groupCustomerNo',
          width: 100
        },
        {
          label: 'PPL',
          prop: 'ppl'
        },
        {
          label: 'PJ',
          prop: 'projectCode'
        },
        {
          label: '情报号',
          prop: 'salesInfoNo',
          width: 150
        },
        {
          label: '营业所',
          prop: 'deptcode',
          formatter: this.deptFormatter
        },
        {
          label: '风险在库',
          prop: 'rflag',
          formatter: this.riskFormatter
        },
        {
          label: '风险在库数量',
          prop: 'rqtyAvailable'
        }
        /*    {
          label: '大口数量',
          prop: 'safeqty',
          align: 'right'
        },
        {
          label: 'bin数量',
          prop: 'qtybin',
          align: 'right'
        },
        {
          label: 'bin数',
          prop: 'bincell',
          align: 'right'
        }*/
      ],
      // 数据表格
      tableData: {},
      loading: false,
      fuzzy: false,
      downLoadExcelBtuLoad: false,
      downLoadInvExcelBtuLoad: false
    }
  },
  created() {
    // 营业所
    findDeptDict().then(result => {
      console.log(result)
      result.forEach(dict => {
        this.menuData.department.push({ code: dict.deptId, desc: dict.deptName })
      })
    })
    this.initData()
    this.getHouseNameListByType()
  },

  methods: {
    // 菜单数据初始化
    initData() {
      // 从数据字典中获取仓库类别菜单信息
      getDataByPid(Constants.warehouseTypeList).then(res => {
        res.forEach(element => {
          this.menuData.warehouseTypeMenu.push({ code: element.extcode, desc: element.name })
        })
        console.log('获取仓库类别菜单信息：')
        console.log(this.menuData.warehouseTypeMenu)
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      // 从数据库中获取库存类别菜单信息
      findInventoryType().then(res => {
        res.data.forEach(element => {
          this.menuData.inventoryTypeMenu.push({ code: element.inventoryTypeCode, desc: element.description })
        })
        console.log('获取库存类别菜单信息：')
        console.log(this.menuData.inventoryTypeMenu)
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    // 通过仓库类型菜单动态获取仓库名称菜单信息
    getHouseNameListByType() {
      // 清空name菜单
      this.queryCondition.condition.warehouseCode = ''
      findHouseCodeAndNameByType(this.queryCondition.condition.warehouseType).then(res => {
        this.menuData.warehouseNameMenu = res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },

    // 导出库存数据 库存关联关系数据
    downloadExcel() {
      this.$message.success('开始下载，请耐心等待(大约1分钟)')
      this.downLoadExcelBtuLoad = true
      downLoadInventoryExcel(this.queryCondition.condition).then(res => {
        this.downLoadExcelBtuLoad = false
        const fileName = '库存关联关系数据.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.$message.success('下载完成')
      }).catch(error => {
        this.downLoadExcelBtuLoad = false
        this.$message.warning('导出失败' + error)
      })
    },
    // downloadInvExcel
    // 导出库存数据
    downloadInvExcel() {
      this.$message.success('开始下载，请耐心等待(大约1分钟)。最大导出2万条数据')
      this.downLoadInvExcelBtuLoad = true
      downloadInvExcel(this.queryCondition.condition).then(res => {
        this.downLoadInvExcelBtuLoad = false
        const fileName = '库存数据.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.$message.success('下载完成')
      }).catch(error => {
        this.downLoadInvExcelBtuLoad = false
        this.$message.warning('导出失败' + error)
      })
    },

    // 获取表格数据事件
    getTableDataEvent() {
      console.log('查询条件：')
      console.log(this.queryCondition)
      this.loading = true
      this.queryCondition.condition.fuzzy = this.fuzzy
      findInventory(this.queryCondition).then(res => {
        console.log('查询到数据：')
        console.log(res)
        this.tableData = res.data
        this.loading = false
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
        this.loading = false
      })
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
    // 库存类别格式化
    inventoryTypeFormatter(row, column, cellValue, index) {
      var item = this.menuData.inventoryTypeMenu.find(item => item.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 营业所字典格式化
    deptFormatter(row, column, cellValue) {
      const item = this.menuData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 风险在库格式化
    riskFormatter(row, column, cellValue) {
      return cellValue === 1 ? '是' : '否'
    }
  }
}
</script>
<style scoped>
.el-divider {
  height: 2px;
  margin: 20px 0;
}

.fuzzyBTN:before {
  content: "\e7ac"
}
</style>
