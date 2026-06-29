<template>
  <div>
    <el-form :inline="true" :model="searchform" class="demo-form-inline" size="mini" style="margin: 5px">
      <el-form-item label="型号">
        <el-input
          :rows="3"
          v-model="searchform.modelNo"
          style="width:130px"
          autocomplete="off"
          type="textarea"
          placeholder="回车换行进行批量查询"
          size="mini"
          clearable
        />
      </el-form-item>
      <el-form-item label="机种">
        <el-input
          :rows="3"
          v-model="searchform.modelSort"
          style="width:130px"
          autocomplete="off"
          type="textarea"
          placeholder="回车换行进行批量查询"
          size="mini"
          clearable
        />
      </el-form-item>
      <el-form-item label="生产工厂">
        <el-select v-model="searchform.prodFactorys" collapse-tags style="width: 120px" multiple placeholder="生产工厂">
          <el-option
            v-for="item in prodFactoryList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="searchform.states" style="width: 120px" collapse-tags multiple placeholder="状态">
          <el-option
            v-for="item in stateList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="更新开始日期">
        <el-date-picker
          v-model="updateTimeStr"
          :default-time="['00:00:00', '23:59:59']"
          :picker-options="pickerOptions"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="-"
          end-placeholder="-"
          style="width: 220px"
        />
      </el-form-item>
      <el-form-item label="量产开始日">
        <el-date-picker
          v-model="batchProdStartTimeStr"
          :default-time="['00:00:00', '23:59:59']"
          :picker-options="pickerOptions"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="至"
          start-placeholder="-"
          end-placeholder="-"
          style="width: 220px"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="searchList()">查询</el-button>
        <el-button :loading="exportLoading" type="primary" @click="exportKdInfoData()">导出</el-button>
      </el-form-item>
    </el-form>
    <vxe-table
      ref="multipleTable"
      :data="tableData"
      border
      height="595">
      <vxe-table-column type="seq" width="60"/>
      <vxe-table-column field="kdNo" title="kd编号" width="100px" show-overflow="tooltip"/>
      <vxe-table-column field="modelNo" title="型号" width="160px" show-overflow="tooltip"/>
      <vxe-table-column field="modelSort" title="机种" width="120px" show-overflow="tooltip"/>
      <vxe-table-column field="expectEvalTimeCn" title="预计评价日(中国)" width="120px" show-overflow="tooltip"/>
      <vxe-table-column field="isJpEvalDesc" title="是否日本评价" width="100px" show-overflow="tooltip"/>
      <vxe-table-column field="expectBatchProdTime" title="预计量产日" width="120px" show-overflow="tooltip"/>
      <vxe-table-column field="changeNum" title="变更次数" width="70px" show-overflow="tooltip"/>
      <vxe-table-column field="stateDesc" title="状态" width="80px" show-overflow="tooltip"/>
      <vxe-table-column field="prodFactory" title="生产工厂" width="100px" show-overflow="tooltip"/>
      <vxe-table-column field="prodDept" title="生产部门" width="100px" show-overflow="tooltip"/>
      <vxe-table-column field="manager" title="负责人" width="90px" show-overflow="tooltip"/>
      <vxe-table-column field="batchProdStartTime" title="量产开始日" width="120px" show-overflow="tooltip"/>
      <vxe-table-column field="isActivityDesc" title="是否有效" width="120px" show-overflow="tooltip"/>
      <vxe-table-column field="createdBy" title="创建人" width="100px" show-overflow="tooltip"/>
      <vxe-table-column field="updatedBy" title="更新人" width="100px" show-overflow="tooltip"/>
      <vxe-table-column field="sourceCreatedTime" title="创建时间" width="180px" show-overflow="tooltip"/>
      <vxe-table-column field="sourceUpdatedTime" title="更新时间" width="180px" show-overflow="tooltip"/>
      <vxe-table-column field="remark" title="备注" width="180px" show-overflow="tooltip"/>
    </vxe-table>
    <pagination v-show="total>0" :total="total" :page.sync="searchform.page.pageNumber" :limit.sync="searchform.page.pageSize" @pagination="searchList()" />
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import { listKdInfos, exportKdInfoData } from '@/api/order/sampleOrder'
import { getDictDataByPid } from '@/api/common/dict'
export default {
  components: { Pagination },
  data() {
    return {
      searchform: {
        modelNo: '',
        modelSort: '',
        prodFactorys: [],
        states: [],
        updateTimeStart: '',
        updateTimeEnd: '',
        batchProdStartTimeStart: '',
        batchProdStartTimeEnd: '',
        modelNos: [],
        modelSorts: [],
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      total: 0,
      prodFactoryList: [],
      stateList: [],
      tableData: [],
      exportLoading: false,
      updateTimeStr: [],
      batchProdStartTimeStr: [],
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
    this.getstateList()
    this.getFactoryList()
    this.searchList()
  },
  methods: {
    exportKdInfoData() {
      if (this.searchform.modelNo === '' || this.searchform.modelNo == null) {
        this.searchform.modelNos = []
      }
      if (this.searchform.modelSort === '' || this.searchform.modelSort == null) {
        this.searchform.modelSorts = []
      }
      if (this.searchform.modelNo !== '') {
        this.searchform.modelNos = this.searchform.modelNo.split('\n')
      }
      if (this.searchform.modelSort !== '') {
        this.searchform.modelSorts = this.searchform.modelSort.split('\n')
      }
      if (this.updateTimeStr !== null && this.updateTimeStr.length > 0) {
        this.searchform.updateTimeStart = this.dayjs(this.updateTimeStr[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchform.updateTimeEnd = this.dayjs(this.updateTimeStr[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.searchform.updateTimeStart = ''
        this.searchform.updateTimeEnd = ''
      }
      if (this.batchProdStartTimeStr !== null && this.batchProdStartTimeStr.length > 0) {
        this.searchform.batchProdStartTimeStart = this.dayjs(this.batchProdStartTimeStr[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchform.batchProdStartTimeEnd = this.dayjs(this.batchProdStartTimeStr[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.searchform.batchProdStartTimeStart = ''
        this.searchform.batchProdStartTimeEnd = ''
      }
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportKdInfoData(this.searchform).then(res => {
        const fileName = 'KD查询数据导出.xlsx'
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
    getstateList() {
      getDictDataByPid('6006').then(res => {
        if (res.success) {
          this.stateList = res.content
        } else {
          this.stateList = []
        }
      })
    },
    getFactoryList() {
      getDictDataByPid('6005').then(res => {
        if (res.success) {
          this.prodFactoryList = res.content
        } else {
          this.prodFactoryList = []
        }
      })
    },
    searchList() {
      console.log('参数=>', this.searchform)
      if (this.searchform.modelNo === '' || this.searchform.modelNo == null) {
        this.searchform.modelNos = []
      }
      if (this.searchform.modelSort === '' || this.searchform.modelSort == null) {
        this.searchform.modelSorts = []
      }
      if (this.searchform.modelNo !== '') {
        this.searchform.modelNos = this.searchform.modelNo.split('\n')
      }
      if (this.searchform.modelSort !== '') {
        this.searchform.modelSorts = this.searchform.modelSort.split('\n')
      }
      if (this.updateTimeStr !== null && this.updateTimeStr.length > 0) {
        this.searchform.updateTimeStart = this.dayjs(this.updateTimeStr[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchform.updateTimeEnd = this.dayjs(this.updateTimeStr[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.searchform.updateTimeStart = ''
        this.searchform.updateTimeEnd = ''
      }
      if (this.batchProdStartTimeStr !== null && this.batchProdStartTimeStr.length > 0) {
        this.searchform.batchProdStartTimeStart = this.dayjs(this.batchProdStartTimeStr[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchform.batchProdStartTimeEnd = this.dayjs(this.batchProdStartTimeStr[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.searchform.batchProdStartTimeStart = ''
        this.searchform.batchProdStartTimeEnd = ''
      }
      listKdInfos(this.searchform).then(res => {
        console.log('==>', res)
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
      })
    }
  }
}
</script>
<style scoped>
</style>
