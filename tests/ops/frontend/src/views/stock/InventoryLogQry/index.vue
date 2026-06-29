<template>
  <el-container>
    <el-main>
      <el-form ref="form" :model="form" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="型号">
          <el-input v-model="form.modelNo" placeholder="请输入型号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="订单号">
          <el-input v-model="form.orderNo" placeholder="订单号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="发票号">
          <el-input v-model="form.invoiceNo" placeholder="发票号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="仓库">
          <el-select v-model="form.warehouseCode" placeholder="请选择" filterable clearable >
            <el-option
              v-for="(item,index) in inventoryNoWTList"
              :key="index"
              :label="item.warehouseName"
              :value="item.warehouseCode">
              <span style="float: left">{{ item.warehouseName }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.warehouseCode }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="voucherType" label="指令类型">
          <el-select v-model="form.voucherType" placeholder="指令类型" size="mini" clearable multiple collapse-tags style="width: 200px">
            <el-option v-for="(item,index) in voucherTypeData" :key="index" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item prop="type" label="出入库">
          <el-select v-model="form.type" size="mini" clearable style="width: 100px">
            <el-option v-for="item in typeData" :key="item.code" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label="操作时间">
          <el-date-picker
            :picker-options="pickerOptions"
            :default-value="startTimeAndEndTime"
            v-model="startTimeAndEndTime"
            type="daterange"
            align="center"
            unlink-panels
            range-separator="-"
            start-placeholder="时间开始"
            end-placeholder="时间结束"
            format="yyyy-MM-dd"
            style="width:250px"
            value-format="yyyy-MM-dd"
            size="mini"/>
        </el-form-item>
        <el-form-item label="库存ID">
          <el-input v-model="form.inventoryId" placeholder="库存ID" clearable style="width: 100px" />
        </el-form-item>
        <!-- <el-select v-model="form.sortRule" placeholder="选择排序" size="mini" style="width: 100px">
          <el-option
            v-for="item in sortOptions"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select> -->
        <el-checkbox v-model="form.checkedHistoryData">历史数据查询</el-checkbox>
        <el-button v-permission="['145762']" type="primary" size="mini" style="margin-left: 10px;" @click="findList(1)">查询</el-button>
        <el-button :loading="exportLoading" type="primary" size="mini" style="margin-left: 10px;" @click="exportInventoryLogData(1)">导出</el-button>
      </el-form>

      <el-table
        v-loading="listLoading"
        ref="multipleTable"
        :header-cell-style="{color:'#000000'}"
        :data="tableData"
        tooltip-effect="dark"
        class="multipleTable"
        border
        style="width: 100%"
        size="small"
        stripe
        @sort-change="tableSortChange">
        <el-table-column
          align="left"
          prop="modelno"
          label="型号"
          width="170"
          show-overflow-tooltip/>

        <el-table-column
          width="100"
          prop="warehouseCode"
          label="仓库代码"
          show-overflow-tooltip/>

        <el-table-column
          width="80"
          prop="quantity"
          label="变更数量"/>

        <el-table-column
          width="200"
          align="left"
          prop="voucherCode"
          label="指令号"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="formatVoucherType"
          align="left"
          width="120"
          prop="voucherType"
          show-overflow-tooltip
          label="指令类型"/>

        <el-table-column
          align="left"
          prop="invoiceNo"
          width="130"
          show-overflow-tooltip
          label="发票号"/>

        <el-table-column
          align="left"
          prop="orderNo"
          width="120"
          label="订单号"
          show-overflow-tooltip/>

        <el-table-column
          align="left"
          width="60"
          prop="itemNo"
          label="原项号"
          show-overflow-tooltip/>

        <el-table-column
          width="80"
          prop="customerNo"
          label="客户代码"
          show-overflow-tooltip/>

        <el-table-column
          align="left"
          width="100"
          prop="ppl"
          label="PPL号"
          show-overflow-tooltip/>

        <el-table-column
          align="left"
          width="100"
          prop="projectCode"
          label="项目号"
          show-overflow-tooltip/>

        <el-table-column
          align="left"
          width="100"
          prop="groupCustomerNo"
          label="客户群代码"
          show-overflow-tooltip/>

        <el-table-column
          align="left"
          width="100"
          prop="salesInfoNo"
          label="营业情报号"
          show-overflow-tooltip/>

        <!-- <el-table-column
          align="left"
          width="100"
          prop="inventoryId"
          label="库存id">
          <template slot-scope="scope">
            <el-popover
              placement="top-start"
              width="400"
              trigger="hover"
              @show="getInventoryData(scope.row.inventoryId)">
              <el-table :data="InventoryData" size="mini">
                <el-table-column width="150" property="inventoryTypeCode" label="库存分类"/>
                <el-table-column width="100" property="customerNo" label="客户"/>
                <el-table-column width="100" property="ppl" label="PPL号"/>
                <el-table-column width="100" property="projectCode" label="项目号"/>
                <el-table-column width="100" property="groupCustomerNo" label="客户群代码"/>
                <el-table-column width="100" property="salesInfoNo" label="营业情报号"/>
              </el-table>
              <span slot="reference">{{ scope.row.inventoryId }}</span>
            </el-popover>
          </template>
        </el-table-column> -->

        <el-table-column
          align="left"
          width="100"
          prop="inventoryId"
          label="库存id"
          sortable="custom"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="formatInventoryType"
          width="100"
          prop="inventoryTypeCode"
          label="库存分类"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="dateFormat"
          align="left"
          show-overflow-tooltip
          sortable="custom"
          prop="creTime"
          width="120"
          label="操作时间"/>
        <el-table-column
          prop="creator"
          label="创建人"
          show-overflow-tooltip/>

      </el-table>
      <div style="display:inline;float: left;">
        <el-pagination
          :page-sizes="[20, 50, 100, 200, 500]"
          :current-page="form.pageNum"
          :page-size="form.pageSize"
          :total="total"
          background
          layout="total, sizes, prev, pager, next"
          @size-change="handlePageSizeChange"
          @current-change="handlePageChange"/>
      </div>
      <div v-if="qtyShow" style="margin-left: 50%;margin-top:5px">
        <span>{{ "合计出入："+ quantity }}</span>
      </div>
      <!-- <el-divider/> -->

    </el-main>
  </el-container>
</template>

<script>
import { getDictDataByPid, listWarehouseNoWT } from '@/api/common/dict'
import { listInventoryLogData, getOpsInventoryProperty, exportInventoryLog } from '@/api/stock/InventoryLogQry'
// import { getTimeArea } from '@/utils/dataFormat'
export default {
  name: 'InventoryLogQry',
  components: {

  },
  data() {
    return {
      listLoading: false,
      qtyShow: false,
      exportLoading: false,
      total: 0,
      quantity: 0,
      startTimeAndEndTime: [],
      form: {
        invoiceNo: '',
        orderNo: '',
        modelNo: '',
        inventoryId: '',
        warehouseCode: '',
        voucherType: [],
        crtTimeStartStr: '',
        crtTimeEndStr: '',
        sortRule: 'desc',
        property: 'cre_time',
        pageNum: 1,
        pageSize: 20,
        checkedHistoryData: false
      },
      voucherTypeClassCode: '4009',
      inventoryClassCode: '2001',
      voucherTypeData: [],
      inventoryTypeData: [],
      tableData: [],
      InventoryData: [],
      inventoryNoWTList: [],
      typeData: [
        { code: 0, codeName: '出库' },
        { code: 1, codeName: '入库' }
      ],
      sortOptions: [
        { code: 'desc', codeName: '倒序' },
        { code: 'asc', codeName: '正序' }
      ],
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  mounted() {
    this.setDefaultDateRange()
    this.listWarehouseNoWT()
    this.iniData()
    this.findList()
  },
  methods: {
    formatDate(date) {
      const year = date.getFullYear()
      const month = (date.getMonth() + 1).toString().padStart(2, '0')
      const day = date.getDate().toString().padStart(2, '0')
      return `${year}-${month}-${day}`
    },
    setDefaultDateRange() {
      const endDate = new Date()
      const startDate = new Date()
      startDate.setMonth(startDate.getMonth() - 6)
      this.startTimeAndEndTime = [this.formatDate(startDate), this.formatDate(endDate)]
    },
    findList(val) {
      if (val === 1) {
        this.form.pageNum = 1
      }
      this.getTime()
      console.log(' this.form.crtTimeStartStr ', this.form.crtTimeStartStr)
      console.log(' this.form.crtTimeEndStr ', this.form.crtTimeEndStr)
      listInventoryLogData(this.form).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
          if (this.form.modelNo !== null && this.form.modelNo !== '') {
            this.qtyShow = true
            this.quantity = 0
            this.tableData.forEach(el => {
              if (el.type === 0) {
                this.quantity = this.quantity - el.quantity
              } else {
                this.quantity = this.quantity + el.quantity
              }
            })
          } else {
            this.qtyShow = false
          }
        }
      }).catch(error => {
        this.qtyShow = false
        console.log(error)
      })
    },
    tableSortChange(val) {
      if (val.order === 'descending') {
        this.form.property = val.prop
        this.form.sortRule = 'desc'
      } else {
        this.form.property = val.prop
        this.form.sortRule = 'asc'
      }
      this.findList()
    },
    getInventoryData(val) {
      getOpsInventoryProperty(val).then(res => {
        if (res.content.inventoryTypeCode != null) {
          this.InventoryData.push(res.content)
        }
      })
    },
    getTime() {
      if (this.startTimeAndEndTime === '' || this.startTimeAndEndTime === null) {
        this.form.crtTimeStartStr = ''
        this.form.crtTimeEndStr = ''
      } else {
        this.form.crtTimeStartStr = this.startTimeAndEndTime[0]
        this.form.crtTimeEndStr = this.startTimeAndEndTime[1]
      }
    },
    iniData() {
      getDictDataByPid(this.voucherTypeClassCode).then(result => {
        this.voucherTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.inventoryClassCode).then(result => {
        this.inventoryTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    daysBetween(date1, date2) {
      const msPerDay = 24 * 60 * 60 * 1000
      const timestamp1 = new Date(date1).getTime()
      const timestamp2 = new Date(date2).getTime()
      const diffInDays = Math.abs((timestamp2 - timestamp1) / msPerDay)
      return diffInDays
    },
    exportInventoryLogData() {
      this.getTime()
      const diff = this.daysBetween(this.form.crtTimeStartStr, this.form.crtTimeEndStr)
      console.log('diff==> ', diff)
      if (diff > 180) {
        this.$message({
          message: '操作日期范围不可超过180天.',
          type: 'warning'
        })
        return
      }
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待,请注意,导出上限50W条.')
      exportInventoryLog(this.form).then(res => {
        const fileName = '出入库' + this.dayjs(new Date()).format('YYYYMMDDHHmmss') + '.csv'
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
    listWarehouseNoWT() {
      listWarehouseNoWT().then(res => {
        if (res.success) {
          this.inventoryNoWTList = res.content
        }
      }).catch(error => {
        error = '仓库检索条件，返回数据格式有误！'
        this.smcErrorMsg(error)
      })
    },
    // 换页
    handlePageSizeChange(newSize) {
      this.form.pageSize = newSize
      this.findList()
    },
    handlePageChange(newCurrent) {
      this.form.pageNum = newCurrent
      this.findList()
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
    formatVoucherType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.voucherTypeData, cellValue)
    },
    formatInventoryType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.inventoryTypeData, cellValue)
    },
    dateFormat(row, fmt) {
      fmt = 'YYYY-mm-dd HH:MM'
      var date = row.creTime
      if (date == null) {
        return null
      }
      let ret = ''
      date = new Date(date)
      const opt = {
        'Y+': date.getFullYear().toString(), // 年
        'm+': (date.getMonth() + 1).toString(), // 月
        'd+': date.getDate().toString(), // 日
        'H+': date.getHours().toString(), // 时
        'M+': date.getMinutes().toString(), // 分
        'S+': date.getSeconds().toString() // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
      }
      for (const k in opt) {
        ret = new RegExp('(' + k + ')').exec(fmt)
        if (ret) {
          fmt = fmt.replace(
            ret[1],
            ret[1].length === 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
          )
        }
      }
      return fmt
    }
  }
}
</script>

<style scoped>

</style>
