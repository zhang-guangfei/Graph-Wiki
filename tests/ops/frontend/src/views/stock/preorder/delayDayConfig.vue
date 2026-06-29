<template>
  <div>
    <el-form ref="queryFrom" :inline="true" :model="queryForm" size="small">
      <el-form-item label="库存类型" size="mini">
        <el-select v-model="queryForm.inventoryTypeCode" clearable placeholder="请选择库存类型">
          <el-option
            v-for="item in inventoryTypeCodeList"
            :key="item.code"
            :label="item.name"
            :value="item.code"/>
        </el-select>
      </el-form-item>
      <el-form-item label="客户代码" size="mini">
        <el-input v-model="queryForm.customerNo" size="mini" clearable placeholder="客户代码" />
      </el-form-item>
      <el-form-item label="客户群代码" size="mini">
        <el-input v-model="queryForm.groupCustomerNo" size="mini" clearable placeholder="客户群代码" />
      </el-form-item>
      <el-form-item label="PPL代码" size="mini">
        <el-input v-model="queryForm.pplNo" size="mini" clearable placeholder="PPL代码" />
      </el-form-item>
      <el-form-item label="P J" size="mini">
        <el-input v-model="queryForm.projectCode" size="mini" clearable placeholder="项目号" />
      </el-form-item>
      <el-row class="row-button">
        <el-button v-permission="['966764']" type="primary" icon="el-icon-search" size="mini" @click="queryPreOrderConfig">查询</el-button>
        <el-button type="primary" size="mini" @click="editConfigure()">新增</el-button>
        <el-button v-permission="['966764']" type="primary" icon="el-icon-upload2" size="mini" @click="exportPreOrderConfig">导出</el-button>
        <el-button :loading="importLoading" type="primary" icon="el-icon-upload2" size="mini" @click="importData">导入</el-button>
      </el-row>
    </el-form>
    <el-table
      v-loading="listLoading"
      ref="multipleTable"
      :data="preOrderConfig"
      :cell-style="{ padding: '5px' }"
      size="mini"
      border
      stripe
      style="width: 100%;margin-top: 5px"
      height="60vh">

      <!-- 表头字段 -->
      <el-table-column prop="priority" label="优先级" sortable width="80"/>
      <el-table-column prop="inventoryTypeCode" label="库存类型" width="80"/>
      <el-table-column prop="inventoryTypeCodeName" label="库存描述" show-overflow-tooltip/>
      <el-table-column prop="customerNo" label="客户代码"/>
      <el-table-column prop="customerName" label="客户名称" width="200" show-overflow-tooltip/>
      <el-table-column prop="groupCustomerNo" label="群代码"/>
      <el-table-column prop="ppl" label="PPL"/>
      <el-table-column prop="projectCode" label="PJ" width="200"/>
      <el-table-column prop="isDelayStr" label="是否延迟" sortable/>
      <el-table-column prop="delayMaxDay" label="延期MAX天数"/>
      <!--操作栏 -->
      <el-table-column label="操作" width="150">
        <template slot-scope="scope">
          <el-button-group>
            <el-button type="primary" size="mini" @click="UpdatePreOrderAccountConfig(scope.row)">修改</el-button>
            <el-button type="primary" size="mini" @click="deleteConfig(scope.row)">删除</el-button>
          </el-button-group>
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
      @pagination="queryPreOrderConfig"
    />
    <!--dialog 编辑点 -->
    <el-dialog
      :visible.sync="dialogAddEdit"
      :close-on-click-modal="true"
      :title="dialogTitle"
      width="500px"
      @close="dialogAddEdit=false">
      <el-form ref="accountConfigForm" :inline="true" :model="addForm">
        <el-form-item label-width="80px" label="优先级" size="mini">
          <el-input v-model="addForm.priority" style="width: 190px" auto-complete="off"/>
          <span>*值越小，优先级越高</span>
        </el-form-item>
        <el-form-item :required="true" label-width="160px" label="库存类型" size="mini">
          <el-select v-model="addForm.inventoryTypeCode" placeholder="请选择库存类型" >
            <el-option
              v-for="item in inventoryTypeCodeList"
              :key="item.code"
              :label="item.name"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label-width="160px" label="客户代码" size="mini">
          <el-input v-model="addForm.customerNo" style="width: 190px" />
        </el-form-item>
        <el-form-item label-width="160px" label="ppl" size="mini">
          <el-input v-model="addForm.ppl" style="width: 190px" />
        </el-form-item>
        <el-form-item label-width="160px" label="项目号" size="mini">
          <el-input v-model="addForm.projectCode" style="width: 190px" />
        </el-form-item>
        <el-form-item label-width="160px" label="群代码" size="mini">
          <el-input v-model="addForm.groupCustomerNo" style="width: 190px" />
        </el-form-item>
        <el-form-item label-width="160px" label="是否延迟" size="mini">
          <el-switch v-model="addForm.isDelay" active-text="是" inactive-text="否"/>
        </el-form-item>
        <el-form-item label-width="160px" label="延期MAX天数" size="mini">
          <el-input v-model="addForm.delayMaxDay" style="width: 190px" />
        </el-form-item>
        <el-row>
          <dlv style="float:right">
            <el-button :loading="saveLoading" :disabled="saveLoading" size="mini" @click="saverAccountConfig">保存</el-button>
            <el-button size="mini" @click="dialogAddEdit=false">取消</el-button>
          </dlv>
        </el-row>
      </el-form>
    </el-dialog>
    <!--dialog 上传 -->
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="上传"
      width="420px"
    >
      <div class="upload">
        <el-upload
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          :on-success="uploadSuccess"
          style="margin-top:10px"
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
        <el-button @click="closeClick">关闭</el-button>
        <el-button
          :loading="dowmLoading"
          type="primary"
          @click="downloadTemplate()">下载模板</el-button>
        <el-button type="success" @click="importPreOrderAccountConfig">
          <span :loading="uploadStatus">{{
            uploadStatus ? "上传中..." : "上传文件"
          }}
          </span>
        </el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { findInventoryType } from '@/api/warehouseManage'
import { getCustomerNameByNo } from '@/api/common/customer'
import { listPreOrderAccountConfig, writeAndUpdatePreOrderAccountConfig, exportPreOrderAccountConfig, importPreOrderAccountConfig, downYQPZTemplate, deletePreOrderAccountConfigById } from '@/api/stock/preorder'
export default {
  name: 'DelayDayConfig',
  components: { Pagination },
  data() {
    return {
      queryForm: {
        deptNos: [],
        status: [],
        inventoryTypeCode: '',
        customerNo: '',
        ppl: '',
        projectCode: '',
        groupCustomerNo: '',
        salesInfoNo: '',
        warehouseCodes: [],
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      dowmLoading: false,
      searhForm: {
        id: ''
      },
      addForm: {
        id: '',
        priority: 1,
        inventoryTypeCode: '',
        customerNo: '',
        ppl: '',
        projectCode: '',
        groupCustomerNo: '',
        isDelay: true,
        delayMaxDay: 180,
        delFlag: false
      },
      file: '',
      actionUrl: '',
      exportLoading: false,
      listLoading: false,
      dialogAddEdit: false,
      importLoading: false,
      saveLoading: false,
      uploadStatus: false,
      uploadFileDialogVisible: false,
      dialogTitle: '新增',
      inventoryTypeCodeList: [],
      preOrderConfig: []
    }
  },
  created() {
    this.findInventoryType()
    this.queryPreOrderConfig()
  },
  methods: {
    deleteConfig(row) {
      deletePreOrderAccountConfigById(row.id).then(res => {
        if (res.success) {
          this.queryPreOrderConfig()
        }
      })
    },
    downloadTemplate() {
      this.dowmLoading = true
      console.log('downloadTemplate => ')
      const expdate = '延期设定模板' + this.dayjs(new Date()).format('YYYYMMDDHHmmss')
      this.fileName = expdate + '.xlsx'
      downYQPZTemplate().then(res => {
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = this.fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.dowmLoading = false
      }).catch(error => {
        console.error(error)
        this.dowmLoading = false
      })
    },
    // 字典：库存类别
    findInventoryType() {
      findInventoryType().then(res => {
        res.data.forEach(dict => {
          this.inventoryTypeCodeList.push({ code: dict.inventoryTypeCode, name: dict.description })
        })
      }).catch(res => {
        this.$message.error(res.message)
      })
    },
    initData() {
      this.queryForm.status = []
      this.queryForm.pageNum = 1
      this.queryForm.pageSize = 20
      this.queryPreOrderConfig()
    },
    queryPreOrderConfig() {
      if (this.listLoading) {
        return
      }
      this.listLoading = true
      this.preOrderConfig = []
      listPreOrderAccountConfig(this.queryForm).then(res => {
        if (res.success) {
          this.preOrderConfig = res.content.list
          this.queryForm.total = res.content.total
        } else {
          this.$message.error(res.message)
        }
        this.listLoading = false
      }).catch(error => {
        this.$message.error(error)
        this.listLoading = false
      })
    },
    exportPreOrderConfig() {
      if (this.exportLoading) {
        return
      }
      this.exportLoading = true
      const expdate = '先行在库决算延期设置' + this.dayjs(new Date()).format('YYYYMMDDHHmm')
      this.fileName = expdate + '.xlsx'
      exportPreOrderAccountConfig(this.queryForm).then(res => {
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
    showEditable(row) {
      this.addForm.id = ''
      this.addForm.priority = 1
      this.addForm.inventoryTypeCode = ''
      this.addForm.customerNo = ''
      this.addForm.ppl = ''
      this.addForm.projectCode = ''
      this.addForm.groupCustomerNo = ''
      this.addForm.isDelay = true
      this.addForm.delayMaxDay = 180
      this.dialogTitle = '新增'
      row.isEditable = true
    },
    async saverAccountConfig() {
      if (!this.addForm.inventoryTypeCode) {
        this.$message.error('请输入内容！')
      }
      if (this.saveLoading) {
        return
      }
      this.saveLoading = true
      await writeAndUpdatePreOrderAccountConfig(this.addForm).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.dialogAddEdit = false
          this.queryPreOrderConfig()
        } else {
          this.$message.error(res.message)
        }
        this.saveLoading = false
      }).catch(error => {
        this.$message.error(error)
        this.saveLoading = false
      })
    },
    UpdatePreOrderAccountConfig(row) {
      this.addForm.id = row.id
      this.addForm.priority = row.priority
      this.addForm.inventoryTypeCode = row.inventoryTypeCode
      this.addForm.customerNo = row.customerNo
      this.addForm.ppl = row.ppl
      this.addForm.projectCode = row.projectCode
      this.addForm.groupCustomerNo = row.groupCustomerNo
      this.addForm.isDelay = row.isDelay
      this.addForm.delayMaxDay = row.delayMaxDay
      this.addForm.delFlag = row.delFlag
      this.dialogAddEdit = true
      this.dialogTitle = '修改'
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    importData() {
      this.uploadFileDialogVisible = true
    },
    uploadSuccess(response, file, fileList) {
      // console.log(response)
      // console.log(file)
      // console.log(fileList)
    },
    async importPreOrderAccountConfig() {
      const file = this.file
      if (!file) {
        return this.$message.warning('请选择文件')
      }
      const fileType = file.name.substring(file.name.lastIndexOf('.') + 1)
      if (fileType !== 'xlsx') {
        return this.$message.warning('文件格式错误，请重新导入(.xlsx)')
      }
      this.importLoading = true
      const formData = new FormData()
      formData.append('file', file)
      await importPreOrderAccountConfig(formData).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success('导入完毕')
          this.uploadFileDialogVisible = false
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.importLoading = false
      }).catch(error => {
        this.importLoading = false
        console.error(error)
      })
    },
    inventoryTypeNameFormatter: function(row) {
      return this.inventoryTypeName(row.inventoryTypeCode)
    },
    inventoryTypeName(code) {
      for (const item of this.inventoryTypeCodeList) {
        if (item.code === code) {
          return item.name
        }
      }
    },
    isDelayFormastter: function(row) {
      return row.isDelay ? '是' : '否'
    },
    customerNameFormatter: function(row) {
      return this.getCustomerName(row.customerNo)
    },
    async getCustomerName(customerNo) {
      if (!customerNo) {
        return
      }
      const params = { 'customerNo': customerNo }
      console.log('customerNo:' + customerNo)
      await getCustomerNameByNo(params).then(res => {
        if (res.success) {
          console.log(res.content)
          return 'res.content'
        } else {
          return
        }
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    editConfigure() {
      if (!this.dialogAddEdit) {
        this.addForm.id = ''
        this.addForm.priority = 1
        this.addForm.inventoryTypeCode = ''
        this.addForm.customerNo = ''
        this.addForm.ppl = ''
        this.addForm.projectCode = ''
        this.addForm.groupCustomerNo = ''
        this.addForm.isDelay = true
        this.addForm.delayMaxDay = 180
        this.dialogTitle = '新增'
        this.dialogAddEdit = true
        this.addForm.delFlag = false
      }
    }
  }
}
</script>

<style scoped>

</style>
