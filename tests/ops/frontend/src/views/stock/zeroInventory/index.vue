<template>
  <el-container>
    <el-main>
      <el-form ref="form" :model="form" :inline="true" size="mini">
        <el-form-item label="型号">
          <el-input v-model="form.modelNo" placeholder="型号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="仓库">
          <!-- <el-input v-model="form.warehouseCode" placeholder="仓库" clearable style="width: 100px" /> -->
          <el-select v-model="form.warehouseCode" style="width:130px" clearable>
            <el-option
              v-for="item in warehouseData"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label="缺货天数">
          <el-select v-model="form.dayType" style="width:70px" class="select">
            <el-option
              v-for="item in dayTypeData"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
          <!-- <span>≥ </span> -->
          <el-input v-model="form.days" placeholder="缺货天数" clearable style="width: 100px" />
        </el-form-item>
        <!-- <el-form-item label="起始和计算日期">
          <el-date-picker
            v-model="startTimeAndEndTime"
            type="daterange"
            align="center"
            unlink-panels
            range-separator="-"
            start-placeholder="时间开始"
            end-placeholder="时间结束"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 250px"/>
        </el-form-item> -->
        <el-form-item>
          <el-select v-model="form.dateType" placeholder="请选择" style="width: 100px" size="mini" clearable>
            <el-option
              v-for="item in dateTypeData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-date-picker
            v-model="startTimeAndEndTime"
            type="daterange"
            align="center"
            unlink-panels
            range-separator="-"
            start-placeholder="时间开始"
            end-placeholder="时间结束"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 250px"/>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="form.stockType" size="mini" border>库存小于0</el-checkbox>
        </el-form-item>
        <el-button v-permission="['576218']" type="primary" size="mini" style="margin-left: 10px;" @click="listData(1)">查询</el-button>
        <el-button v-permission="['341751']" :loading="exportLoading" type="primary" size="mini" plain icon="el-icon-download" @click="downFile()">导出</el-button>
      </el-form>

      <el-dialog v-dialogDrag :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
        <product-search v-if="productVisible" ref="ProductSearch"/>
      </el-dialog>

      <el-table
        ref="multipleTable"
        :data="tableData"
        tooltip-effect="dark"
        class="multipleTable"
        border
        style="width: 100%"
        size="mini"
        stripe>
        <el-table-column
          width="70"
          prop="warehouseCode"
          label="仓库"/>

        <el-table-column
          width="180"
          prop="modelNo"
          label="型号">
          <template slot-scope="scope">
            <el-link :underline="false" type="primary" @click="handleClick(scope.row)">{{ scope.row.modelNo }}</el-link>
          </template>
        </el-table-column>

        <el-table-column
          width="100"
          prop="days"
          label="缺货天数"/>

        <el-table-column
          width="100"
          prop="qtyBin"
          label="Bin数量"/>

        <el-table-column
          width="100"
          prop="binCell"
          label="BIN数"/>

        <el-table-column
          width="100"
          prop="mean"
          label="设定平均"/>

        <el-table-column
          width="80"
          prop="modelClass"
          label="等级"/>

        <el-table-column
          width="100"
          prop="supplier"
          label="供应商"/>

        <el-table-column
          width="120"
          prop="loginDate"
          label="登录时间"/>

        <el-table-column
          width="100"
          prop="ordingQty"
          label="订货中数量"/>

        <el-table-column
          width="100"
          prop="transQty"
          label="调拨中数量"/>

        <el-table-column
          width="120"
          prop="minTDlvDate"
          label="最早调拨货期"/>

        <el-table-column
          width="120"
          prop="minOrdDate"
          label="最早采购日期"/>

        <el-table-column
          width="100"
          prop="minDlvDate"
          label="最早交货期"/>

        <el-table-column
          width="120"
          prop="fromDate"
          label="起始日期"/>

        <el-table-column
          width="120"
          prop="calcDate"
          label="计算日期"/>

        <el-table-column
          width="70"
          prop="stockQty"
          label="有效在库"/>

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
import { getDictDataByPid } from '@/api/common/dict'
import { listZeroInventoryData, exportZeroInventory } from '@/api/stock/zeroInventory'
import ProductSearch from '../../product/index'
export default {
  name: 'ZeroInventory',
  components: {
    ProductSearch
  },
  data() {
    return {
      tableData: [],
      exportLoading: false,
      productVisible: false,
      warehouseClassCode: '2012',
      warehouseData: [],
      total: 0,
      startTimeAndEndTime: '',
      form: {
        modelNo: '',
        warehouseCode: '',
        days: '',
        stratTime: '',
        endTime: '',
        dateType: 2,
        dayType: 1,
        pageNum: 1,
        pageSize: 20,
        stockType: false
      },
      dayTypeData: [
        { value: 1, label: '=' },
        { value: 2, label: '>' },
        { value: 3, label: '<' }
      ],
      dateTypeData: [
        { value: 1, label: '起始日期' },
        { value: 2, label: '计算日期' }
      ]
    }
  },

  created() {
    this.listData()
    this.iniData()
  },
  methods: {
    iniData() {
      getDictDataByPid(this.warehouseClassCode).then(result => {
        console.log(result)
        this.warehouseData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    getDateInfo() {
      if (this.startTimeAndEndTime == null) {
        this.form.stratTime = ''
        this.form.endTime = ''
      } else {
        this.form.stratTime = this.startTimeAndEndTime[0]
        this.form.endTime = this.startTimeAndEndTime[1]
      }
    },
    handleClick(row) {
      const item = { modelno: row.modelNo }
      this.productVisible = true
      this.$nextTick(() => {
        this.$refs.ProductSearch.handleSelect(item)
        this.$refs.ProductSearch.productSearchChange()
      })
    },
    downFile() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportZeroInventory(this.form).then(res => {
        const fileName = '零库存信息.xlsx'
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
    listData(val) {
      if (val === 1) {
        this.form.pageNum = 1
      }
      this.getDateInfo()
      listZeroInventoryData(this.form).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
      }).catch(error => {
        console.log(error)
      })
    },
    handlePageSizeChange(newSize) {
      this.form.pageSize = newSize
      this.listData()
    },
    handlePageChange(newCurrent) {
      this.form.pageNum = newCurrent
      this.listData()
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
    formatWarehouseCode(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehouseData, cellValue)
    }
  }
}
</script>

<style scoped>
  .dialog {
    display: flex;
    justify-content: center;
    align-items: Center;
    overflow: hidden;
  }
  .dialog /deep/ .el-dialog {
        margin: 0 auto !important;
        height: 70%;
        overflow: hidden;
  }
  .dialog /deep/ .el-dialog .el-dialog__body {
            position: absolute;
            left: 0;
            top: 54px;
            bottom: 0;
            right: 0;
            padding: 0;
            z-index: 1;
            overflow: hidden;
            overflow-y: auto;
  }
</style>
