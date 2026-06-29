<template>
  <div>
    <el-row>
      <el-form ref="queryForm" :inline="true" :model="queryForm" size="mini" label-width="80px">
        <el-form-item label="任务编号">
          <el-input v-model="queryForm.jobNo" style="..." clearable/>
        </el-form-item>
        <el-form-item label="任务名称">
          <el-input v-model="queryForm.jobName" style="..." clearable placeholder="加%号模糊查询"/>
        </el-form-item>
        <el-form-item label="仓库号">
          <el-select v-model="queryForm.warehouseCode" multiple clearable placeholder="请选择仓库">
            <el-option
              v-for="item in warehouseData"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryForm.status" clearable placeholder="请选择状态">
            <el-option
              v-for="item in statusDesc"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['419271']" type="primary" icon="el-icon-search" size="mini" @click="listBinTrialJobManageData">
            查询
          </el-button>
        </el-form-item>
      </el-form>
    </el-row>

    <el-row class="row-button">
      <el-button v-permission="['966764']" type="primary" size="mini" @click="createBinJob()">新建</el-button>
      <el-button v-permission="['966764']" type="primary" size="mini" @click="copyBinTrialJobManager()">复制</el-button>
      <el-button v-permission="['966764']" type="primary" size="mini" @click="editBinJob()">编辑</el-button>
      <el-button v-permission="['966764']" type="primary" size="mini" @click="deleteBinTrialJobManager()">取消</el-button>
      <el-button v-permission="['966764']" type="primary" size="mini" @click="sumitBinTrialJob()">提交计算</el-button>
    </el-row>
    <el-table
      v-loading="listLoading"
      ref="multipleTable"
      :data="binJob"
      :cell-style="{ padding: '5px' }"
      size="mini"
      border
      stripe
      style="width: 100%;margin-top: 5px"
      height="60vh"
      @selection-change="handleSelectionChange">
      <!--    @selection-change="handleSelection"-->

      <!-- 表头字段 -->
      <el-table-column type="selection" width="55" />
      <el-table-column label="任务编号" width="230">
        <template slot-scope="scope">
          <el-link type="primary" icon="el-icon-view" @click="queryConfigure(scope.row)">{{ scope.row.jobNo }}</el-link>
          <el-tooltip v-if="scope.row.status==2" class="item" effect="light" content="查看计算结果" placement="right-start">
            <el-link type="primary" icon="el-icon-tickets" @click="queryDetail(scope.row)"/>
          </el-tooltip>
        </template>
      </el-table-column>
      <el-table-column prop="jobName" label="任务名称"/>
      <el-table-column prop="jobDescription" label="任务说明"/>
      <el-table-column prop="createTime" label="创建时间"/>
      <el-table-column prop="createUser" label="创建人"/>
      <el-table-column :formatter="statusFormatter" prop="status" label="执行状态" width="100"/>
      <el-table-column prop="isDeleted" label="是否取消" width="100"/>
      <el-table-column prop="planExecuteDate" label="预计开始时间"/>
      <el-table-column prop="executeStartTime" label="开始时间"/>
      <el-table-column prop="executeEndTime" label="完成时间"/>
      <el-table-column prop="warehouseCode" label="计算第一仓库"/>
      <el-table-column prop="warehouseMaster" label="计算第一物流中心"/>
    </el-table>
    <!-- 分页工具 -->
    <pagination
      v-show="queryForm.total > 0"
      :total="queryForm.total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="queryForm.pageNum"
      :limit.sync="queryForm.pageSize"
      @pagination="listBinTrialJobManageData"
    />

    <!--dialog 编辑点 -->
    <el-dialog
      :visible.sync="dialogAddEdit"
      :close-on-click-modal="true"
      :title="dialogTitle"
      width="500px"
      @close="dialogAddEdit=false">
      <el-form ref="binJobAddForm" :inline="true" :model="addForm">
        <el-form-item label-width="160px" label="任务编号" size="mini">
          <el-input v-model="addForm.jobNo" style="width: 190px" disabled auto-complete="off"/>
        </el-form-item>
        <el-form-item :required="true" label-width="160px" label="任务名称" size="mini">
          <el-input v-model="addForm.jobName" :maxlength="50" style="width: 190px" />
        </el-form-item>
        <el-form-item label-width="160px" label="任务说明" size="mini">
          <el-input v-model="addForm.jobDescription" :maxlength="255" type="textarea" style="width: 190px" />
        </el-form-item>
        <el-form-item label-width="160px" label="预计开始时间" size="mini">
          <el-date-picker
            v-model="addForm.planExecuteDate"
            type="date"
            size="mini"
            placeholder="预计开始时间"
            value-format="yyyy-MM-dd"
            style="width: 130px"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="160px" label="计算第一仓库" size="mini">
          <el-select v-model="addForm.warehouseCode" multiple clearable placeholder="请选择仓库">
            <el-option
              v-for="item in warehouseData"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
        <el-form-item :required="true" label-width="160px" label="计算第一物流中心" size="mini">
          <el-select v-model="addForm.warehouseMaster" multiple clearable placeholder="请选择仓库">
            <el-option
              v-for="item in warehouseMaster"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>

        <el-row>
          <dlv style="float:right">
            <el-button :loading="saveLoading" :disabled="saveLoading" size="mini" @click="saveBinJobData">保存</el-button>
            <el-button size="mini" @click="dialogAddEdit=false">取消</el-button>
          </dlv>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { listWarehouseNoWT, getDictDataByPid } from '@/api/common/dict'
import { listBinTrialJobManageData, saveBinTrialJobManager, deleteBinTrialJobManager, getBinTrialJobManageData, copyBinTrialJobManager, sumitBinTrialJob } from '@/api/stock/binorder'
export default {
  components: { Pagination },
  data() {
    return {
      queryForm: {
        id: '',
        jobNo: '',
        jobName: '',
        warehouseCode: [],
        status: '',
        isDeleted: 0,
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      dialogTitle: '新建',
      dialogAddEdit: false,
      dialogAddEditLoading: false,
      listLoading: false,
      saveLoading: false,
      statusDesc: [],
      binJob: [],
      multipleSelection: [],
      warehouseData: [],
      warehouseMaster: [],
      addForm: {
        id: '',
        jobNo: '',
        jobName: '',
        jobDescription: '',
        warehouseCode: [],
        warehouseMaster: [],
        isDeleted: '0',
        planExecuteDate: ''
      }
    }
  },
  created() {
    this.initData()
    this.initsdesc()
    this.getWareHouseListData()
    this.addForm.planExecuteDate = new Date()
  },
  methods: {
    queryConfigure(row) {
      this.$router.push('')
      this.$router.push({
        name: 'BinCalculate',
        query: {
          active: 'configurePage',
          jobId: row.id,
          jobNo: row.jobNo
        }
      })
    },
    queryDetail(row) {
      this.$router.push('')
      this.$router.push({
        name: 'BinCalculate',
        query: {
          active: 'detailPage',
          jobId: row.id,
          jobNo: row.jobNo
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    createBinJob() {
      this.dialogAddEdit = true
      this.dialogTitle = '新建任务'
      // 初始化值
      this.addForm.id = ''
      this.addForm.jobNo = ''
      this.addForm.jobName = ''
      this.addForm.jobDescription = ''
      this.addForm.warehouseCode = []
      this.addForm.warehouseMaster = []
      this.addForm.isDeleted = '0'
      this.addForm.planExecuteDate = new Date()
    },
    // 显示修改对话框
    showEditDialog(row) {
      this.addForm = Object.assign({}, row)
      this.dialogAddEdit = true
      this.dialogTitle = '编辑任务'
    },
    // 选择编辑,显示修改对话框
    editBinJob() {
      if (this.multipleSelection.length === 0) {
        this.$message.error({
          title: '错误',
          message: '请选择数据。'
        })
        return
      }
      // 初始化值
      this.addForm = this.$options.data().addForm
      if (this.multipleSelection.length === 1) {
        this.addForm = this.multipleSelection[0]
        if (this.multipleSelection[0].warehouseCode.indexOf(',') !== -1) {
          this.addForm.warehouseCode = this.multipleSelection[0].warehouseCode.split(',')
        } else {
          this.addForm.warehouseCode = [this.multipleSelection[0].warehouseCode]
        }
        if (this.multipleSelection[0].warehouseMaster.indexOf(',') !== -1) {
          this.addForm.warehouseMaster = this.multipleSelection[0].warehouseMaster.split(',')
        } else {
          this.addForm.warehouseMaster = [this.multipleSelection[0].warehouseMaster]
        }
        this.dialogAddEdit = true
        this.dialogTitle = '编辑任务'
      } else {
        this.$notify({
          title: '警告',
          message: '请选择一条数据进行编辑.',
          type: 'warning'
        })
      }
    },
    initData() {
      this.queryForm.status = ''
      this.queryForm.pageNum = 1
      this.queryForm.pageSize = 20
      this.listBinTrialJobManageData()
    },
    listBinTrialJobManageData() {
      this.binJob = []
      this.listLoading = true
      listBinTrialJobManageData(this.queryForm).then(res => {
        if (res.success) {
          this.binJob = res.content.list
          this.queryForm.total = res.content.total
          this.listLoading = false
        } else {
          this.$message.error(res.message)
          this.listLoading = false
        }
      }).catch(error => {
        this.$message.error(error)
        this.listLoading = false
      })
    },
    saveBinJobData() {
      this.saveLoading = true
      const data = {
        id: this.addForm.id,
        jobName: this.addForm.jobName,
        jobDescription: this.addForm.jobDescription,
        warehouseCode: this.addForm.warehouseCode.join(','),
        warehouseMaster: this.addForm.warehouseMaster.join(','),
        isDeleted: '0',
        planExecuteDate: this.addForm.planExecuteDate
      }
      saveBinTrialJobManager(data).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success(res.content)
          this.dialogAddEdit = false
        } else {
          this.$message.error(res.message)
        }
        this.saveLoading = false
      }).catch(res => {
        this.smcErrorMsg(res.message)
        this.saveLoading = false
      })
    },
    deleteBinTrialJobManager() {
      if (this.multipleSelection.length === 0) {
        this.$message.error({
          title: '错误',
          message: '请选择数据。'
        })
        return
      }
      const newArr = []
      this.multipleSelection.forEach((item, index) => {
        newArr.push(item.id)
      })
      // const newArr = this.multipleSelection.map((item, index) => {
      //   return item.id
      // })
      const data = { 'ids': newArr.join(',') }
      this.$confirm('此操作将永久删除本条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBinTrialJobManager(data).then(res => {
          if (res.success) {
            this.initData()
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
        }).catch(err => {
          this.$message.error(err)
        })
      })
    },
    sumitBinTrialJob() {
      if (this.multipleSelection.length === 0) {
        this.$message.error({
          title: '错误',
          message: '请选择数据。'
        })
        return
      }
      const newArr = []
      this.multipleSelection.forEach((item, index) => {
        newArr.push(item.id)
      })
      const data = { 'ids': newArr.join(',') }
      this.$confirm('提交之后，不可以更新数据，并会定时进行计算, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        sumitBinTrialJob(data).then(res => {
          if (res.success) {
            this.initData()
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
        }).catch(err => {
          this.$message.error(err)
        })
      })
    },
    getBinTrialJobManageData() {
      this.queryForm.id = ''
      this.queryForm.jobName = ''
      this.queryForm.warehouseCode = ''
      this.queryForm.status = '0'
      this.queryForm.isDeleted = '0'
      getBinTrialJobManageData(this.queryForm).then(res => {
        if (res.success) {
          this.binJob = res.content
        } else {
          this.$message.error(res.message)
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    copyBinTrialJobManager() {
      if (this.multipleSelection.length === 0) {
        this.$message.error({
          title: '错误',
          message: '请选择数据。'
        })
        return
      }
      if (this.multipleSelection.length > 1) {
        this.$message.error({
          title: '错误',
          message: '一次操作一条,不可同时操作多条'
        })
        return
      }
      // const appIds = []
      // this.multipleSelection.forEach((item, index) => {
      //   appIds.push(item.appId)
      // })
      const data = {
        jobId: this.multipleSelection[0].id
      }
      copyBinTrialJobManager(data).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(err => {
        this.$message.error(err)
      })
    },
    closeDialog() {
      this.searchData()
    },
    initsdesc() {
      getDictDataByPid('1093').then(result => {
        this.statusDesc = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    statusFormatter(row) {
      return this._descFormatter(this.statusDesc, row.status)
    },
    getWareHouseListData() {
      this.warehouseData = []
      this.warehouseMaster = []
      listWarehouseNoWT().then(res => {
        if (res.success) {
          this.warehouseData = res.content
          for (const item of this.warehouseData) {
            if (item.warehouseName.indexOf('物流中心') >= 0) {
              this.warehouseMaster.push({ warehouseCode: item.warehouseCode, warehouseName: item.warehouseName })
            }
          }
        } else {
          this.warehouseData = []
          this.warehouseMaster = []
        }
      }).catch(error => {
        console.error(error)
        this.warehouseData = []
        this.warehouseMaster = []
      })
    },
    _descFormatter(data, id) {
      if (data === null) {
        return id
      }
      for (const i in data) {
        const item = data[i]
        if (item.code === (id === null ? '' : id.toString())) {
          return item.codeName
        }
      }
      return id
    }
  }
}
</script>
