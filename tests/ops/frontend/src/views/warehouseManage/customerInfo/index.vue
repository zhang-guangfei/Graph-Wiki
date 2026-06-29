<template>
  <div class="app-container">
    <el-card style="height:880px;">
      <el-row :gutter="10" >
        <el-form>
          <el-form-item label="客户名称：" prop="condition" style="margin-bottom: 5px;">
            <el-autocomplete
              v-model="queryCondition.customerName"
              :fetch-suggestions="querySearchAsync"
              placeholder="输入客户关键字有提示~"
              clearable
              style="width: 330px"
              @select="handleBlurCustomerName(queryCondition.customerName)">
              <template slot-scope="{ item }">
                <div>
                  <span style="float: left">{{ item.cstmName }}</span>
                  <span style="color: #8492a6; font-size: 13px">【{{ item.customerNo }}】</span>
                </div>
              </template>
            </el-autocomplete>
            <el-button v-permission="['324074']" icon="el-icon-search" @click="getTableDataEvent(queryCondition.condition)"/>
          </el-form-item>
        </el-form>
      </el-row>
      <el-divider/>
      <el-table
        v-loading="loading"
        :data="tableData.list"
        :row-style="{height:'43px'}"
        :header-cell-style="{padding: '1px'}"
        :cell-style="{padding: '5px 5px'}"
        border
        stripe
        height="730">
        <el-table-column
          v-for="column in tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
          :align="column.align"
          header-align="center"
          show-overflow-tooltip
        />
      </el-table>
      <!-- 分页工具 -->
      <el-pagination
        :current-page="tableData.pageNum"
        :page-sizes="[ 10,15, 30, 50, 100]"
        :page-size="tableData.pageSize"
        :total="tableData.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
  </div>
</template>

<script>
import { findCustomerInfoByCustomerNoOrName, findCustomerBycustomerNoOrName } from '@/api/warehouseManage'
export default {
  name: 'CustomerInfo',
  data() {
    return {
      queryCondition: {
        condition: '',
        cstmName: '',
        pageNumber: 1,
        pageSize: 10,
        orderBy: ''
      },
      customerCodesOptions: [],
      page: { pageNumber: 1, pageSize: 10 },
      tableColumns: [
        {
          label: '客户代码',
          prop: 'customerNo',
          align: 'center',
          width: 100
        },
        {
          label: '客户名称',
          prop: 'customerName',
          width: 200,
          style: 'show-overflow-tooltip'
        },
        {
          label: '部门代码',
          prop: 'hrUnitName',
          width: 140,
          align: 'center'
        },
        {
          label: '客户担当',
          prop: 'userId',
          width: 100,
          align: 'center'
        },
        {
          label: '代理店代码',
          prop: 'agentNo',
          width: 100,
          align: 'center'
        },
        {
          label: '客户类型',
          prop: 'customerType',
          width: 100,
          align: 'center'
        },
        {
          label: '集团内客户Id',
          width: 140,
          prop: 'SMCGroupId'
        },
        {
          label: '交易主体',
          prop: 'tradeSubjectId'
        },
        {
          label: '税号',
          prop: 'taxNo'
        },
        {
          label: '账号',
          prop: 'accountNo'
        },
        {
          label: '开票地址',
          prop: 'invoiceAddress'
        },
        {
          label: '开票电话',
          prop: 'invoicePhoneNo'
        }
      ],
      // 数据表格
      tableData: {},
      loading: false
    }
  },
  created() {
    this.getTableDataEvent()
  },
  methods: {
    // 获取表格数据事件
    getTableDataEvent() {
      this.loading = true
      if (!this.queryCondition.customerName) { this.queryCondition.condition = '' }
      findCustomerInfoByCustomerNoOrName(this.queryCondition).then(res => {
        this.tableData = res
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
    // 联动检索
    querySearchAsync(customerNoOrName, cb) {
      if (customerNoOrName) {
        findCustomerBycustomerNoOrName(customerNoOrName).then(data => {
          const customerCodesAllOptions = []
          for (const key in data) {
            customerCodesAllOptions[key] = {}
            customerCodesAllOptions[key].customerNo = data[key].customerNo
            customerCodesAllOptions[key].cstmName = data[key].customerName
          }
          this.customerCodesOptions = customerCodesAllOptions.filter((item) => {
            if (!!~item.customerNo.toUpperCase().indexOf(customerNoOrName.toUpperCase()) || !!~item.cstmName.indexOf(customerNoOrName) || !!~item.cstmName.toUpperCase().indexOf(customerNoOrName.toUpperCase())) {
              return true
            }
          })
          var restaurants = this.customerCodesOptions
          restaurants.forEach(element => {
            element.value = element.cstmName + '【' + element.customerNo + '】'
          })
          var results = customerNoOrName ? restaurants.filter(this.createStateFilter(customerNoOrName)) : restaurants
          cb(results)
        })
      } else {
        this.customerCodesOptions = []
        var restaurants = this.customerCodesOptions
        if (restaurants.length > 0) {
          restaurants.forEach(element => {
            element.value = element.cstmName
          })
        }
        var results = customerNoOrName ? restaurants.filter(this.createStateFilter(customerNoOrName)) : restaurants
        cb(results)
      }
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.cstmName.toLowerCase().indexOf(queryString.toLowerCase()) === 0 || state.cstmName.toLowerCase().indexOf(queryString.toLowerCase()) > 0 || state.customerNo.toLowerCase().indexOf(queryString.toLowerCase()) >= 0)
      }
    },
    handleBlurCustomerName(val) {
      if (this.customerCodesOptions.length > 0) {
        this.customerCodesOptions.forEach(element => {
          if (val === element.cstmName + '【' + element.customerNo + '】') {
            this.queryCondition.condition = element.customerNo
            this.queryCondition.cstmName = element.customerName
          }
        })
      }
    }
  }
}
</script>
