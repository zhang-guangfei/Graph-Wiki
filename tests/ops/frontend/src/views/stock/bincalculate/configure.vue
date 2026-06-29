<template>
  <div>
    <el-row>
      <el-form ref="queryForm" :inline="true" :model="queryForm" size="mini" label-width="80px">
        <el-form-item label-width="150px" label="任务编号" size="mini">
          <el-input v-model="queryForm.jobNo" style="width: 170px" clearable placeholder="任务编号" />
        </el-form-item>
        <el-form-item >
          <department @handleScopeChange="handleScopeChange" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="warehouseCode" clearable placeholder="请选择仓库">
            <el-option
              v-for="item in warehouseData"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="queryForm.warehouseMaster" clearable placeholder="请选择物流中心">
            <el-option
              v-for="item in warehouseMaster"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['419271']" type="primary" icon="el-icon-search" size="mini" @click="listBinTrialSalesBranchConfigData()">
            查询
          </el-button>
          <el-button v-permission="['966764']" v-loading="exportLoading" type="primary" icon="el-icon-upload2" size="mini" @click="exportBinConfigureData">导出</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <el-row class="row-button">
      <!-- <el-button v-permission="['966764']" type="primary" size="mini" @click="addConfigure()">新建</el-button>-->
      <!-- <el-button v-permission="['966764']" type="primary" size="mini" @click="editConfigure()">追加</el-button>-->
      <el-button v-permission="['966764']" type="primary" size="mini" @click="editConfigure()">编辑</el-button>
      <el-button v-permission="['966764']" type="primary" size="mini" @click="addConfigure()">保存</el-button>
      <el-button v-permission="['966764']" type="primary" icon="el-icon-upload2" size="small" @click="uploadData()">导入 </el-button>
      <el-button v-permission="['966764']" type="primary" size="small" @click="restoreConfig()">一键还原</el-button>

    </el-row>
    <el-table
      v-loading="listLoading"
      ref="multipleTable"
      :data="branchConfig"
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
      <el-table-column prop="jobNo" label="任务编号" width="150" />
      <el-table-column prop="salesBranchId" label="营业所代码" sortable width="100"/>
      <el-table-column prop="branchName" label="营业所名称" width="100"/>
      <el-table-column prop="companyName" label="所属分公司" sortable/>
      <el-table-column prop="areaName" label="所属营业部" sortable />
      <el-table-column prop="warehouseCodeUpdate" label="第一仓库" sortable>
        <template slot-scope="scope">
          <span
            v-show="scope.row.isEditable"
            :prop="'branchConfig.' + scope.$index + '.warehouseCode'"
            label-width="0">
            <el-select v-model="scope.row.warehouseCodeUpdate" placeholder="请选择仓库" >
              <el-option
                v-for="item in warehouseData"
                :key="item.warehouseCode"
                :label="item.warehouseName"
                :value="item.warehouseCode"/>
            </el-select>
          </span>
          <span
            v-show="!scope.row.isEditable"
            :style="{ color: scope.row.warehouseCodeUpdate !=scope.row.warehouseCode? 'red' : 'black' }">
            {{ warehouseNameFormatter(scope.row.warehouseCodeUpdate) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column prop="warehouseMasterUpdate" label="第一物流中心" sortable>
        <template slot-scope="scope">
          <span
            v-show="scope.row.isEditable"
            :prop="'branchConfig.' + scope.$index + '.warehouseMaster'"
            label-width="0">
            <el-select v-model="scope.row.warehouseMasterUpdate" placeholder="请选择物流中心" >
              <el-option
                v-for="item in warehouseMaster"
                :key="item.warehouseCode"
                :label="item.warehouseName"
                :value="item.warehouseCode"/>
            </el-select>
          </span>
          <span
            v-show="!scope.row.isEditable"
            :style="{ color: scope.row.warehouseMasterUpdate !=scope.row.warehouseMaster? 'red' : 'black' }">
            {{ warehouseNameFormatter(scope.row.warehouseMasterUpdate) }}
          </span>
        </template>
      </el-table-column>
      <!--操作栏 -->
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button v-permission="['966764']" v-show="scope.row.isEditable" type="success" size="mini" @click="saveBinTrialSalesBranchConfig(scope.row)">修改</el-button>
          <el-button v-permission="['966764']" v-show="!scope.row.isEditable" type="primary" size="mini" @click="showEditable(scope.row)">编辑</el-button>
          <el-button v-permission="['966764']" v-show="!scope.row.isEditable" type="primary" size="mini" @click="restoreConfigByRow(scope.row)">还原</el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 分页工具 -->
    <pagination
      v-show="queryForm.total > 0"
      :total="queryForm.total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="queryForm.pageNum"
      :limit.sync="queryForm.pageSize"
      @pagination="listBinTrialSalesBranchConfigData"
    />
    <!--dialog 导入设置营业所 -->
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="上传"
      width="420px"
    >
      <el-form ref="binConfigAddForm" :inline="true" :model="addForm">
        <el-form-item label-width="100px" size="mini">
          <el-select v-model="jobIdAdd" placeholder="请选择任务编号">
            <el-option
              v-for="item in binJobData"
              :key="item.id"
              :label="item.jobNo"
              :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div class="upload">
        <el-upload
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          class="upload-demo"
          drag
          multiple
        >
          <div class="el-upload__text">支持xlsx格式</div>
          <div v-if="file !== null" class="el-upload__text">
            {{ file.name }}
          </div>
          <div v-else class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
        </el-upload>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          @click="downloadTemplate('../../static/template/downLoadTemplate/BinCofigure.xlsx')">下载模板</el-button>
        <el-button :loading="uploadLoading" type="success" @click="importData">上传文件</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import { listWarehouseNoWT } from '@/api/common/dict'
import { listBinTrialSalesBranchConfigData, getBinTrialJobManageData, saveBinTrialSalesBranchConfig, restoreBinTrialSalesBranchConfig, importBinConfigureData, exportBinConfigureData } from '@/api/stock/binorder'
export default {
  name: 'BinTrialConfig',
  components: { Pagination, Department },
  data() {
    return {
      options: [],
      queryForm: {
        jobId: '',
        jobNo: '',
        salesBranchIds: [],
        warehouseCode: [],
        warehouseMaster: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      queryBinJob: {
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
      dialogAdd: false,
      dialogEdit: false,
      dialogAddEditLoading: false,
      uploadFileDialogVisible: false,
      listLoading: false,
      uploadLoading: false,
      exportLoading: false,
      editable: false,
      addForm: {
        jobId: '',
        branchConfig: []
      },
      editForm: {
        id: '',
        jobId: [],
        branchId: [],
        branchName: '',
        companyName: '',
        areaName: '',
        warehouseCode: '',
        warehouseMaster: ''

      },
      multipleSelection: [],
      branchConfig: [],
      warehouseData: [],
      warehouseMaster: [],
      warehouseSub: [],
      warehouseCodeAdd: '',
      binJobData: [],
      idJobNo: [], // 任务id 对应任务编号，以显示任务编号
      jobIdAdd: '',
      warehouseMasterCode: '',
      warehouseCode: '',
      actionUrl: '',
      file: null
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal && newVal.name === 'BinCalculate' && this.$route.query.active === 'configurePage') {
          this.queryForm.jobId = this.$route.query.jobId
          this.queryForm.jobNo = this.$route.query.jobNo
          this.listBinTrialSalesBranchConfigData()
        }
      }
    }
  },
  mounted() {
    this.getWareHouseListData()
  },
  methods: {
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    listBinTrialSalesBranchConfigData() {
      this.branchConfig = []
      this.listLoading = true
      this.queryForm.warehouseCode = []
      if (this.warehouseCode !== null && this.warehouseCode !== undefined && this.warehouseCode.length > 0) {
        this.queryForm.warehouseCode.push(this.warehouseCode)
      }
      listBinTrialSalesBranchConfigData(this.queryForm).then(res => {
        if (res.success) {
          this.branchConfig = res.content.list
          this.queryForm.total = res.content.total
          for (const i in this.branchConfig) {
            this.$set(this.branchConfig[i], 'isEditable', false)
          }
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
    async addConfigure() {
      let isSuccess = false
      for (const Key in this.branchConfig) {
        if (this.branchConfig[Key].warehouseCodeUpdate === this.branchConfig[Key].warehouseCode) {
          this.branchConfig[Key].isEditable = false
        } else {
          const obj = this.branchConfig[Key]
          const data = {
            id: obj.id,
            jobId: obj.jobId,
            warehouseCodeUpdate: obj.warehouseCodeUpdate,
            warehouseMasterUpdate: obj.warehouseMasterUpdate
          }
          await saveBinTrialSalesBranchConfig(data).then(res => {
            if (res.success) {
              this.branchConfig[Key].isEditable = false
              // this.$message.success(res.content)
              isSuccess = true
            } else {
              isSuccess = false
              this.$message.error(res.message)
            }
          }).catch(error => {
            isSuccess = false
            this.$message.error(error)
          })
        }
      }
      if (isSuccess) {
        this.$message.success('更新成功！')
      }
    },
    editConfigure() {
      this.editable = !this.editable
      for (let i = 0; i < this.branchConfig.length; i++) {
        this.branchConfig[i].isEditable = this.editable
      }
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    uploadData() {
      this.uploadFileDialogVisible = true
      this.jobIdAdd = ''
      this.getBinJobListData()
      // for (const item of this.binJobData) {
      //   if (item.jobNo === this.queryForm.jobNo) {
      //     this.jobIdAdd = item.id.toString()
      //     console.log('this.jobIdAdd', this.jobIdAdd)
      //     break
      //   }
      // }
    },
    importData() {
      this.uploadLoading = true
      const file = this.file
      if (!this.file) { // 防止空传
        this.$message.error('请选择文件上传')
        this.uploadLoading = false
        return false
      }
      const formData = new FormData() // form表单格式提交数据
      formData.append('jobId', this.jobIdAdd)
      formData.append('file', file)
      this._importBinConfigureData(formData)
      this.uploadLoading = false
    },
    _importBinConfigureData(formData) {
      this.uploadLoading = true
      importBinConfigureData(formData)
        .then(res => {
          if (res.success) {
            this.file = null
            this.$message.success(res.content)
            this.uploadFileDialogVisible = false
          } else {
            this.$message.error(res.message)
          }
          this.uploadLoading = false
        }).catch(error => {
          this.uploadLoading = false
          this.$message.error(error + '请稍后再常试')
        })
    },
    exportBinConfigureData() {
      this.exportLoading = true
      const expdate = 'BinConfigure' + this.dayjs(new Date()).format('YYYYMMDDHHmmss')
      this.fileName = expdate + '.csv'
      exportBinConfigureData(this.queryForm).then(res => {
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = this.fileName
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
    handleScopeChange(val) {
      // console.log('所选部门 => ', val)
      this.queryForm.salesBranchIds = val
    },
    restoreConfigByRow(row) {
      const data = {
        'jobId': this.queryForm.jobId,
        'ids': row.id
      }
      this._restoreBinTrialSalesBranchConfig(data)
    },
    restoreConfig() {
      const data = {
        'jobId': this.queryForm.jobId,
        'ids': ''
      }
      this._restoreBinTrialSalesBranchConfig(data)
    },
    _restoreBinTrialSalesBranchConfig(data) {
      restoreBinTrialSalesBranchConfig(data).then(res => {
        if (res.success) {
          this.listBinTrialSalesBranchConfigData()
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.$message.error(error)
      })
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
        }
      }).catch(error => {
        console.error(error)
        this.warehouseData = []
      })
    },
    showEditable(row) {
      row.isEditable = true
    },
    getBinJobListData() {
      this.binJobData = []
      this.queryBinJob.status = '0'
      this.queryBinJob.isDeleted = '0'
      getBinTrialJobManageData(this.queryBinJob).then(res => {
        if (res.success) {
          this.binJobData = res.content
        } else {
          this.binJobData = []
        }
      }).catch(error => {
        console.error(error)
        this.binJobData = []
      })
    },
    async saveBinTrialSalesBranchConfig(row) {
      const data = {
        id: row.id,
        jobId: row.jobId,
        warehouseCodeUpdate: row.warehouseCodeUpdate,
        warehouseMasterUpdate: row.warehouseMasterUpdate
      }
      await saveBinTrialSalesBranchConfig(data).then(res => {
        if (res.success) {
          row.isEditable = false
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.$message.error(error)
      })
    },
    warehouseNameFormatter(warehouseCodeUpdate) {
      for (const item of this.warehouseData) {
        if (item.warehouseCode === warehouseCodeUpdate) {
          return item.warehouseName
        }
      }
    },
    downloadTemplate(url) {
      const index = url.lastIndexOf('/') + 1
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.download = url.substring(index)
      link.click()
      this.$message({
        message: `正在下载,请稍后在本地查看`,
        type: 'success'
      })
    }
  }
}
</script>

