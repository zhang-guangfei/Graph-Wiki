<template>
  <div class="container">
    <div class="searchInput">
      <el-form ref="searchRequest" :inline="true" :model="searchRequest" size="mini" class="demo-form-inline">
        <el-row :gutter="5">
          <el-col :span="4">
            <el-form-item prop="rorderFno">
              <el-input v-model="searchRequest.rorderFno" placeholder="请输入订单号" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item prop="modelNo">
              <el-input v-model="searchRequest.modelNo" style="width: 120px" placeholder="型号" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item prop="customerNo">
              <el-input v-model="searchRequest.customerNo" placeholder="客户代码" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item prop="userNo">
              <el-input v-model="searchRequest.userNo" placeholder="用户代码" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item prop="corderNo">
              <el-input v-model="searchRequest.corderNo" placeholder="客户订单号" />
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item prop="cproductNo">
              <el-input v-model="searchRequest.cproductNo" placeholder="客户型号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item prop="deptNo">
          <el-input v-model="searchRequest.deptNo" style="width: 120px" placeholder="输入营业所代码" />
        </el-form-item>
        <el-form-item prop="status">
          <el-select v-model="searchRequest.status" clearable style="width: 60%" placeholder="状态">
            <el-option
              v-for="item in statusOptions"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            :picker-options="pickerOptions2"
            v-model="startTimeAndEndTime"
            type="daterange"
            align="center"
            unlink-panels
            range-separator="-"
            start-placeholder="开始接单日期"
            end-placeholder="结束接单日期"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            class="sendDay"
            size="mini"/>
        </el-form-item>
        <el-form-item >
          <el-button class="search_btu" type="primary" @click="reset('searchRequest')">重置</el-button>
          <el-button type="primary" @click="searchData()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <!--table表格-->
    <div class="tableCloumn">
      <el-table
        ref="multipleTable"
        :data="tableData"
        :row-style="{height: '0'}"
        :cell-style="{padding: '0'}"
        :header-cell-style="rowClass"
        size="mini"
        tooltip-effect="dark"
        stripe
        border
        height="595"
        style="width: 100%"
        @row-click="handdle"
        @selection-change="handleSelectionChange">
        <el-table-column
          type="selection"
          width="40"/>

        <el-table-column
          fixed
          prop="rorderFno"
          align="center"
          label="接单号"
          width="130"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="statusformatter"
          prop="status"
          align="center"
          label="状态"
          width="65"
          show-overflow-tooltip/>

        <el-table-column
          prop="modelNo"
          align="center"
          label="型号"
          width="130"
          show-overflow-tooltip/>

        <el-table-column
          prop="quantity"
          align="center"
          label="数量"
          width="65"/>

        <el-table-column
          prop="allocatedQty"
          align="center"
          label="已分配数量"
          width="105"/>

        <el-table-column
          prop="readyQty"
          label="在库货齐数量"
          align="center"
          width="118"/>

        <el-table-column
          prop="expQty"
          align="center"
          label="已发数量"
          width="90"/>

        <el-table-column
          prop="returnedQty"
          align="center"
          label="已退货数量"
          width="105"/>

        <el-table-column
          prop="orderType"
          align="center"
          label="订单类型"
          width="90"
          show-overflow-tooltip/>

        <el-table-column
          prop="stockType"
          align="center"
          label="出货类型"
          width="90"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="rorDateFormatter"
          prop="rorddate"
          align="center"
          label="接单日期"
          width="90"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="dlvDateFormatter"
          prop="dlvDate"
          align="center"
          label="货齐时间"
          width="95"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="shipTimeDateFormatter"
          prop="shipTime"
          align="center"
          label="完成发货日期"
          width="118"
          show-overflow-tooltip/>

        <el-table-column
          prop="customerNo"
          align="center"
          label="客户代码"
          width="90"
          show-overflow-tooltip/>

        <el-table-column
          prop="userNo"
          align="center"
          label="用户代码"
          width="90"
          show-overflow-tooltip/>

        <el-table-column
          prop="deptNo"
          align="center"
          label="营业所"
          show-overflow-tooltip/>

        <el-table-column
          prop="dlvSite"
          align="center"
          label="出货方式"
          width="90"
          show-overflow-tooltip/>

        <el-table-column
          prop="addressNo"
          align="center"
          label="收货地址"
          width="90"
          show-overflow-tooltip/>

      </el-table>
      <pagination
        v-show="total >= 5"
        :total="total"
        :page.sync="page.pageNumber"
        :limit.sync="page.pageSize"
        @pagination="queryList()"
      />
    </div>
    <!--dialog 点击行-->
    <el-dialog :visible.sync="dialogTableVisible" title="订单库存分配情况" width="65%">
      <el-table :data="gridData" :row-style="{height: '0'}" class="dialogTable">
        <el-table-column property="orderId" align="center" label="订单号" width="105" />
        <el-table-column property="qty" align="center" label="数量" width="80"/>
        <el-table-column property="outQty" align="center" label="出库数量" width="100"/>
        <el-table-column property="warehouseCode" align="center" label="仓库编码" />
        <el-table-column property="modelno" align="center" label="型号" />
        <el-table-column property="waitType" align="center" label="等待类型" />
        <el-table-column property="finishTime" align="center" label="完成时间" />
        <!-- <el-table-column property="lssuedQuantity" align="center" label="发出数量" />
        <el-table-column property="expressNo" align="center" label="发出快递公司/单号" />
        <el-table-column property="shipmentNo" align="center" label="出货单号" />
        <el-table-column property="dlvDate" align="center" label="发货日期" /> -->
        <el-table-column
          align="center"
          label="操作">
          <template slot-scope="scope">
            <el-link type="danger" @click="cancel(scope.$index, scope.row)">取消</el-link>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import { fetchList, queryOrderItem } from '@/api/order/rcvorder'
import { getDictDataByPid } from '@/api/common/dict'
import { shortcuts } from '@/utils/dataFormat'
import Pagination from '@/components/Pagination'
export default {
  name: 'OrderSearch',
  components: { Pagination },
  data() {
    return {
      startTimeAndEndTime: '',
      dialogTableVisible: false,
      pickerOptions2: shortcuts,
      // 分页
      total: 1,
      page: {
        pageNumber: 1,
        pageSize: 30
      },
      searchRequest: {
        rorderFno: '', //  订单号
        modelNo: '', // 型号
        customerNo: '', // 客户代码
        status: '', // 处理状态
        rOrdDateStart: '', // 发货日期开始 rOrdDateStart
        rOrdDateEnd: '', // 发货日期结束 rOrdDateEnd
        userNo: '', // 用户代码
        corderNo: '', // 客户订单号
        cproductNo: '', // 客户型号（客户产品代码）
        deptNo: '' // 营业所代码
      },
      tableData: [],
      gridData: [
        {
          orderId: 'H00000010', // 接单号
          quantity: '5', // 数量
          status: '已接入', // 状态
          exWarehouse: '北京smc', // 出库仓库
          inventoryCategory: 'kucun', // 库存类别
          asociationCategory: 'test', // 关联类别
          associatedOrderNo: 'A0015487245', // 关联单号
          fullQuantity: '50', // 货齐数量
          lssuedQuantity: '45', // 发出数量
          expressNo: 'B0000011', // 发出快递公司/单号
          shipmentNo: 'T001-56525', // 出货单号
          dlvDate: '2021-10-20' // 发货日期
        }
      ],
      classCode: '',
      statusOptions: [],
      queryParams: {
        rorderNo: '',
        rorderItem: ''
      }
    }
  },
  created() {
    this.queryList()
    this.fingTypeCode()
  },
  methods: {
    /** 当选项发送改变时触发该方法 */
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handdle(row, event, column) {
      this.dialogTableVisible = true
      console.log('1212121', row)
      this.queryParams.rorderNo = row.rorderNo
      this.queryParams.rorderItem = row.rorderItem
      queryOrderItem(this.queryParams).then(res => {
        console.log('res', res)
        this.gridData = res.data
      })
    },
    reset(searchRequest) {
      this.startTimeAndEndTime = ''
      this.$refs[searchRequest].resetFields()
    },
    queryList() {
      this.setDateCondition()
      fetchList(this.searchRequest, this.page).then(res => {
        console.log('接单查询 ->', res)
        this.tableData = res.content.list
        this.total = res.content.total
      })
    },
    // 查找订单类型
    fingTypeCode() {
      this.classCode = '1001'
      getDictDataByPid(this.classCode).then(res => {
        this.statusOptions = res.content
      })
    },
    rorDateFormatter: function(row, column) {
      if (row.rorddate === null) {
        return ''
      } else {
        return this.dayjs(row.rorddate).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dlvDateFormatter: function(row, column) {
      if (row.dlvDate === null) {
        return ''
      } else {
        return this.dayjs(row.dlvDate).format('YYYY-MM-DD')
      }
    },
    shipTimeDateFormatter: function(row, column) {
      if (row.shipTime === null) {
        return ''
      } else {
        return this.dayjs(row.shipTime).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    statusformatter(row) {
      if (row.status === 1) {
        return '待处理'
      } else if (row.status === 2) {
        return '已分配'
      } else if (row.status === 3) {
        return '采购中'
      } else if (row.status === 4) {
        return '暂不处理'
      } else if (row.status === 5) {
        return '已货齐'
      } else if (row.status === 6) {
        return '已出库'
      } else if (row.status === 7) {
        return '已开票'
      } else if (row.status === 8) {
        return '已退货'
      } else if (row.status === 9) {
        return '取消'
      }
    },
    setDateCondition() {
      if (this.startTimeAndEndTime === null) {
        this.searchRequest.rOrdDateStart = ''
        this.searchRequest.rOrdDateEnd = ''
      } else {
        this.searchRequest.rOrdDateStart = this.startTimeAndEndTime[0]
        this.searchRequest.rOrdDateEnd = this.startTimeAndEndTime[1]
      }
    },
    searchData() {
      this.queryList()
    },
    cancel() {},
    rowClass({ rowIndex, columnIndex }) {
      if (rowIndex === 0) {
        if (columnIndex === 7 || columnIndex === 5 || columnIndex === 6) {
          return { color: 'red' }
        }
      }
    }
  }
}
</script>
<style scoped>
.searchInput{
  position: absolute;
  margin-top: 10px;
  margin-left: 5px;
}
.tableCloumn{
  position: absolute;
  margin-top: 100px;
  width: 100%;
}
.sendDay{
  margin-left: -16%;
}
.search_btu{
  margin-left: -35%;
}
</style>
