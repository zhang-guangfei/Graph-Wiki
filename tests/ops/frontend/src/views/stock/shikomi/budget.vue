<template>
  <div>
    <div class="header">
      <el-form :inline="true" :model="searchForm" class="searchFormClass" size="mini">
        <el-form-item label="决算批次号">
          <el-select
            v-model="searchForm.batchNo"
            :remote-method="remoteMethod"
            :loading="selLoading"
            filterable
            remote
            clearable
            style="width:150px"
            placeholder="决算批次号">
            <el-option
              v-for="item in batchNoList"
              :key="item.batchNo"
              :label="item.lable"
              :value="item.batchNo"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="备库地点">
          <el-select v-model="searchForm.supplierCode" style="width:150px" clearable multiple collapse-tags placeholder="请选择备库地点">
            <el-option
              v-for="item in supplierData"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="型号">
          <el-input v-model="searchForm.modelNo" style="width:150px" clearable placeholder="请输入型号" />
        </el-form-item>
        <el-form-item label="SHIKOMI号">
          <el-input v-model="searchForm.shikomiNo" style="width:150px" clearable placeholder="请输入SHIKOMI号" />
        </el-form-item>
        <el-form-item label="归属客户">
          <el-autocomplete
            v-model="searchForm.belongCustomerNo"
            :fetch-suggestions="querySearchAsync"
            :debounce="0"
            :popper-append-to-body="false"
            popper-class="el-autocomplete-suggestion"
            highlight-first-item
            type="text"
            size="mini"
            style="width: 100px"
            placeholder="归属客户"
            class="select"
            @select="ChangeInput">
            <template slot-scope="{ item }">
              <div class="name">{{ item.customerNo + ',' + item.name }}</div>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="营业所">
          <DepartmentHL @change="handleDeptChange" />
        </el-form-item>
        <el-form-item label="行业代码">
          <el-input v-model="searchForm.indCode" style="width:100px" clearable placeholder="请输入" />
        </el-form-item>
        <el-form-item label="SHIKOMI客户范围">
          <el-select v-model="searchForm.classCode" multiple collapse-tags placeholder="请选择" style="width:120px" clearable>
            <el-option v-for="item in classCodes" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="申请号">
          <el-input v-model="searchForm.applyNo" style="width:150px" clearable placeholder="请输入" />
        </el-form-item>
        <el-form-item label="申请担当">
          <el-input v-model="searchForm.applicantNo" style="width:120px" clearable placeholder="请输入" />
        </el-form-item>
        <el-form-item label="点检状态">
          <el-select v-model="searchForm.inspectStatus" style="width:120px" multiple collapse-tags clearable placeholder="请选择">
            <el-option v-for="item in inspectStatus" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="点检类别">
          <el-select v-model="searchForm.inspectType" style="width:120px" multiple collapse-tags clearable placeholder="请选择">
            <el-option v-for="item in inspectType" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="营业所点检确认">
          <el-select v-model="searchForm.inspectConfirmType" style="width:120px" multiple collapse-tags clearable placeholder="请选择">
            <el-option v-for="item in inspectConfirmType" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchData()">查询</el-button>
          <el-button :loading="exportLoadBtu" type="primary" @click="exportData()">导出</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table
        :data="tableData"
        border
        height="570"
        style="width: 100%">
        <el-table-column
          prop="batchNo"
          label="决算批次号"
          show-overflow-tooltip
          width="120" />
        <el-table-column
          prop="deptNo"
          label="营业所"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="supplierCode"
          show-overflow-tooltip
          width="120"
          label="备库地点"/>
        <el-table-column
          prop="modelNo"
          show-overflow-tooltip
          width="120"
          label="型号"/>
        <el-table-column
          prop="shikomiNo"
          show-overflow-tooltip
          width="120"
          label="SHIKOMI号"/>
        <el-table-column
          prop="applyNo"
          show-overflow-tooltip
          width="120"
          label="申请号"/>
        <el-table-column
          prop="applyTime"
          width="120"
          show-overflow-tooltip
          label="申请时间"/>
        <el-table-column
          prop="applyQty"
          show-overflow-tooltip
          width="80"
          label="申请数量"/>
        <el-table-column
          prop="remainQty"
          width="110"
          show-overflow-tooltip
          label="SHIKOMI残数"/>
        <el-table-column
          prop="classCode"
          width="140"
          show-overflow-tooltip
          label="SHIKOMI客户范围"/>
        <el-table-column
          prop="avliableCustomerNo"
          width="140"
          show-overflow-tooltip
          label="有权限订货的客户"/>
        <el-table-column
          prop="belongCustomerNo"
          width="100"
          show-overflow-tooltip
          label="归属客户"/>
        <el-table-column
          prop="indCode"
          width="100"
          show-overflow-tooltip
          label="行业代码"/>
        <el-table-column
          prop="qtyNoord"
          width="100"
          show-overflow-tooltip
          label="未订货月数"/>
        <el-table-column
          prop="avgOrdQty"
          width="140"
          show-overflow-tooltip
          label="近6个月订货数量"/>
        <el-table-column
          prop="inspectStatusName"
          show-overflow-tooltip
          width="100"
          label="点检状态"/>
        <el-table-column
          prop="inspectTypeName"
          show-overflow-tooltip
          width="100"
          label="点检类型"/>
        <el-table-column
          prop="applicationName"
          show-overflow-tooltip
          width="100"
          label="申请担当"/>
        <el-table-column
          prop="inspectConfirmTypeName"
          width="120"
          show-overflow-tooltip
          label="营业所点检确认"/>
        <el-table-column
          prop="lastUseDate"
          width="120"
          show-overflow-tooltip
          label="最晚消耗日期"/>
        <el-table-column
          prop="remark"
          width="130"
          show-overflow-tooltip
          label="负责人审批备注"/>
      </el-table>
      <pagination
        v-show="total > 0"
        :total="total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="searchForm.page.pageNumber"
        :limit.sync="searchForm.page.pageSize"
        @pagination="searchData"
      />
    </div>
  </div>
</template>
<script>
import { getShikomiSupplier, findShikomiBudget, exportShikomiBudget, findAdjustBatchNo } from '@/api/stock/shikomi'
import { getDictDataByPid } from '@/api/common/dict'
import { getCustomerByNo } from '@/api/common/customer'
import Pagination from '@/components/Pagination'
import DepartmentHL from '@/components/departmentHL'
export default {
  name: 'BudgetIndex',
  components: { DepartmentHL, Pagination },
  data() {
    return {
      searchForm: {
        batchNo: 'JS' + this.formatDateToYearMonth(new Date()),
        supplierCode: [],
        modelNo: '',
        shikomiNo: '',
        belongCustomerNo: '',
        deptNo: [],
        indCode: '',
        classCode: [],
        applyNo: '',
        applicantNo: '',
        inspectStatus: [],
        inspectType: [],
        inspectConfirmType: [],
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      exportLoadBtu: false,
      selLoading: false,
      total: 0,
      tableData: [],
      batchNoList: [],
      supplierData: [],
      classCodes: [],
      inspectStatus: [],
      inspectType: [],
      inspectConfirmType: [],
      classCode_typeid: '2022',
      inspectStatus_classCode: '2090', // 点检状态
      inspectType_classCode: '2091', // 点检类型
      inspectConfirmType_classCode: '2092' // 营业所点检确认
    }
  },
  mounted() {
    this.findDeptNos()
    this.initData()
    this.searchData()
    this.remoteMethod('')
  },
  methods: {
    handleDeptChange(deptNos) {
      this.searchForm.deptNo = deptNos
    },
    formatDateToYearMonth(date) {
      // 获取年份
      const year = date.getFullYear()

      // 获取月份，注意月份从0开始，所以需要加1
      const month = date.getMonth() + 1

      // 格式化月份，确保是两位数
      const formattedMonth = month < 10 ? '0' + month : month

      // 返回格式化的字符串
      return `${year}${formattedMonth}`
    },
    remoteMethod(batchNo) {
      this.selLoading = true
      findAdjustBatchNo(batchNo).then(res => {
        console.log('aaa=>', res)
        if (res.success) {
          this.batchNoList = res.content
        } else {
          this.batchNoList = []
        }
        this.selLoading = false
      })
    },
    ChangeInput(item) {
      this.searchForm.belongCustomerNo = item.customerNo
    },
    querySearchAsync(customerNo, cb) {
      // const cus = { customerNo: this.form.customerNo }
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray

        if (results.length <= 0) {
          results = customerNo ? customerArray.filter(this.createFilter(customerNo)) : customerArray
        }
        cb(results)
        // console.log(results)
      })
    },
    createFilter(queryString) {
      return (state) => {
        return (state.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.customerNo.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    searchData() {
      findShikomiBudget(this.searchForm).then(res => {
        if (res.success) {
          console.log('res', res)
          this.tableData = res.content.list
          this.total = res.content.total
        }
      })
    },
    exportData() {
      this.exportLoadBtu = true
      this.$message.success('已开始导出,请耐心等待')
      exportShikomiBudget(this.searchForm).then(res => {
        console.log('===> ', res)
        if (res.size === 0) {
          this.exportLoadBtu = false
          this.$message.success('暂无数据导出')
          return
        }
        var fileName = 'SHIKOMI决算.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportLoadBtu = false
        this.searchData()
      }).catch(error => {
        console.error(error)
        this.exportLoadBtu = false
        this.$message.error('导出失败: ' + error)
      })
    },
    handleScopeChange(val) {
      console.log('所选部门=>', val)
      this.searchForm.deptNo = val
    },
    initData() {
      // SHIKOMI客户范围
      getDictDataByPid(this.classCode_typeid).then(result => {
        this.classCodes = result.content
      }).catch(error => {
        console.log(error)
      })
      // 获取备库地点
      getShikomiSupplier().then(res => {
        this.supplierData = res.content
      })
      // 点检状态
      getDictDataByPid(this.inspectStatus_classCode).then(result => {
        this.inspectStatus = result.content
      }).catch(error => {
        console.log(error)
      })
      // 点检类型
      getDictDataByPid(this.inspectType_classCode).then(result => {
        this.inspectType = result.content
      }).catch(error => {
        console.log(error)
      })
      // 营业所点检确认
      getDictDataByPid(this.inspectConfirmType_classCode).then(result => {
        this.inspectConfirmType = result.content
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>
<style scoped>
.searchFormClass{
  margin: 20px;
}
 .menu-department /deep/ .el-cascader__search-input{
  min-width: 1px!important;
  height: 0px!important;
}
.select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
}
</style>
