<template>
  <el-container>
    <el-main>
      <el-form ref="searchForm" :model="searchForm" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="客户名称/编码">
          <!-- <el-input v-model="searchForm.customerNo" placeholder="请输入客户名称/编码" clearable style="width: 180px" /> -->
          <el-select
            :remote-method="remoteMethod"
            :loading="inputLoading"
            v-model.trim="searchForm.customerNo"
            filterable
            remote
            reserve-keyword
            clearable
            placeholder="请输入客户名称/编码"
          >
            <el-option
              v-for="item in options"
              :key="item.customerNo"
              :label="item.name"
              :value="item.customerNo"/>
          </el-select>
        </el-form-item>
        <el-button
          v-permission="['394257']"
          type="primary"
          size="mini"
          style="margin-left: 10px;"
          @click="searchData()">查询
        </el-button>
        <el-button v-permission="['178887']" type="primary" size="mini" @click="addCustomerFili()">新增</el-button>
        <el-button
          v-permission="['178887']"
          type="warning"
          size="mini"
          style="margin-left: 10px;"
          @click="batchAddCustomerBili()">批量新增
        </el-button>
        <el-button
          v-permission="['178887']"
          :loading="exportLoading"
          type="primary"
          size="mini"
          style="margin-left: 10px;"
          @click="exportData()">导出
        </el-button>
        <el-button
          v-permission="['178887']"
          type="danger"
          size="mini"
          style="margin-left: 10px;"
          @click="delCustomerFili()">删除
        </el-button>
      </el-form>
      <el-table
        :data="tableData"
        stripe
        style="width: 90%"
        @selection-change="handleSelectionChange">

        <el-table-column
          text="选择"
          type="selection"
          width="60"
          align="center"/>

        <el-table-column
          prop="deptNo"
          label="部门"
          width="180"/>

        <el-table-column
          prop="customerNo"
          label="客户代码"
          width="100"/>

        <el-table-column
          prop="customerName"
          label="客户名称"
          width="180"
          show-overflow-tooltip/>

        <el-table-column
          prop="psnSmcId"
          label="担当代码"
          width="100"/>

        <el-table-column
          prop="psnSmcIdName"
          label="担当名称"
          width="120"
          show-overflow-tooltip/>

        <el-table-column
          prop="wldTypeName"
          label="备案类型"
          width="180"
          show-overflow-tooltip/>

        <el-table-column
          prop="modifyTime"
          label="维护时间"
          width="120"
          show-overflow-tooltip/>

        <el-table-column
          prop="modifier"
          label="维护人"
          width="180"/>

      </el-table>
      <pagination
        v-show="total > 10"
        :total="total"
        :page-sizes="[20, 50, 100, 150, 200]"
        :page.sync="searchForm.page.pageNumber"
        :limit.sync="searchForm.page.pageSize"
        @pagination="findList()"/>
    </el-main>
    <div class="dialog">
      <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false" title="新增备案">
        <el-form ref="form" :model="form" :rules="rules" size="mini">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="客户代码" prop="customerNo" style="margin-left: -8px">
                <el-input
                  v-model="form.customerNo"
                  autocomplete="off"
                  clearable
                  placeholder="请输入"
                  style="width: 120px"
                  @change="changInput()"/>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="2">
              <el-form-item label="备案类型" prop="isWldate">
                <el-select v-model="form.isWldate" clearable placeholder="备案类型" size="mini" style="width: 140px">
                  <el-option
                    v-for="item in typeCodeData"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="24">
            <el-col :span="12">
              <el-form-item label="客户名称">
                <el-input v-model="form.customerName" autocomplete="off" disabled style="width: 220px"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="营 业 所" style="margin-left: -63px">
                <el-input v-model="form.deptNo" autocomplete="off" disabled style="width: 140px"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="担当代码">
                <el-input v-model="form.psnSmcId" autocomplete="off" disabled style="width: 120px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="2">
              <el-form-item label="担当名称" style="margin-left: 8px">
                <el-input v-model="form.psnSmcIdName" autocomplete="off" disabled style="width: 120px"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="维护时间">
                <el-input v-model="form.modifyTime" autocomplete="off" disabled style="width: 120px"/>
              </el-form-item>
            </el-col>
            <el-col :span="12" :offset="2">
              <el-form-item label=" 维 护 人" style="margin-left: 12px">
                <el-input v-model="form.modifier" autocomplete="off" disabled style="width: 120px"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" size="mini" @click="submitForm('form')">新 增</el-button>
        </div>
      </el-dialog>
    </div>
    <div class="batchAddDialog">
      <el-dialog
        :visible.sync="batchAddDialogFormVisible"
        :close-on-click-modal="false"
        title="批量新增"
        size="mini"
        style="width: 60%; margin-left: 130px">
        <h4 class="warnTex">备案类型为 1:指定时间发货,客户代码用回车隔开</h4>
        <el-upload
          ref="upload"
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          class="upload-demo">
          <el-button size="small" type="primary">选取文件</el-button>
          <div slot="tip" class="el-upload__tip">仅支持excel文件,一次选取一个</div>
        </el-upload>
        <el-input
          :autosize="{ minRows: 4, maxRows: 25}"
          :disabled="file !== null"
          v-model="batchAddForm.inputStr"
          type="textarea"
          placeholder="格式：客户代码,备案类型  多个客户用回车隔开"
        />
        <div slot="footer" class="dialog-footer">
          <el-button size="small" type="success" class="downLoadExcel" @click="downLoadExcel">下载模板</el-button>
          <el-button size="mini" @click="batchAddDialogFormVisible = false">取 消</el-button>
          <el-button :loading="saveCustomerWld" size="mini" type="primary" @click="addCustomerWld()">保 存</el-button>
        </div>
      </el-dialog>
    </div>
  </el-container>
</template>
<script>

import {
  findCustWldList,
  delCustomerWldByCustomerNos,
  getCustomerInfoByCustomerNo,
  exportCustomerWldList,
  downLoadExcel,
  addCustomerWldInfo,
  batchCustomerWld,
  importCustomerWld,
  getAllCustomerInfo
} from '@/api/common/customer'
import { getDictDataByPid } from '@/api/common/dict'
import Pagination from '@/components/Pagination'

export default {
  components: { Pagination },
  data() {
    return {
      searchForm: {
        customerNo: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      total: 1,
      form: {
        customerNo: '',
        isWldate: '1',
        customerName: '',
        deptNo: '',
        psnSmcId: '',
        psnSmcIdName: '',
        modifyTime: '',
        modifier: ''
      },
      batchAddForm: {
        inputStr: '',
        loginUserNo: '',
        customerNos: []
      },
      addCustomerWldByFile: {
        loginUserNo: '',
        file: ''
      },
      saveCustomerWld: false,
      customerWldDel: {
        customerNoList: [],
        loginUserNo: ''
      },
      tableData: [],
      multipleSelection: [],
      typeCodeData: [],
      options: [],
      value: [],
      list: [],
      inputLoading: false,
      classCode: '1023',
      exportLoading: false,
      dialogFormVisible: false,
      batchAddDialogFormVisible: false,
      file: null,
      actionUrl: 'https://jsonplaceholder.typicode.com/posts/',
      rules: {
        customerNo: [
          { required: true, message: '请输入客户名称', trigger: 'blur' }
        ],
        isWldate: [
          { required: true, message: '请选择备案类型', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.findList()
    this.getDictDataByPid()
  },
  methods: {
    searchData() {
      this.searchForm.page.pageNumber = 1
      this.findList()
    },
    findList() {
      console.log('www=> ', this.searchForm)
      findCustWldList(this.searchForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
      })
    },
    remoteMethod(query) {
      getAllCustomerInfo(query).then(res => {
        this.options = res.content
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    addCustomerFili(form) {
      this.dialogFormVisible = true
      this.resetForm()
    },
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          this.form.modifier = this.$store.getters.position.psnsmcId
          this.dialogFormVisible = false
          addCustomerWldInfo(this.form).then(res => {
            if (res.success) {
              this.$message({
                message: res.content,
                type: 'success'
              })
              this.searchData()
            } else {
              this.$message({
                message: res.message,
                type: 'error'
              })
            }
          }).catch((error) => {
            this.$message.error(error)
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    changInput() {
      getCustomerInfoByCustomerNo(this.form.customerNo).then(res => {
        if (res.success) {
          this.form.customerName = res.content.customerName
          this.form.deptNo = res.content.deptNo
          this.form.psnSmcId = res.content.psnSmcId
          this.form.psnSmcIdName = res.content.psnSmcIdName
          this.form.modifyTime = res.content.modifyTime
          this.form.modifier = '[' + this.$store.getters.position.psnsmcId + ']' + this.$store.getters.position.psnName
        }
      })
    },
    getDictDataByPid() {
      getDictDataByPid(this.classCode).then(res => {
        if (res.success) {
          this.typeCodeData = res.content
        }
      })
    },
    batchAddCustomerBili() {
      this.batchAddDialogFormVisible = true
      this.batchAddForm.inputStr = null
      this.file = null
    },
    addCustomerWld() {
      this.saveCustomerWld = true
      if (this.batchAddForm.inputStr === undefined || this.batchAddForm.inputStr === null ||
          this.batchAddForm.inputStr === '') {
        if (this.file == null) {
          this.saveCustomerWld = false
          this.$message.warning('请选择文件或者输入客户编码进行新增')
        } else {
          const formData = new FormData() // form表单格式提交数据
          formData.append('file', this.file)
          formData.append('loginUser', this.$store.getters.position.psnsmcId)
          importCustomerWld(formData).then(res => {
            this.saveCustomerWld = false
            this.batchAddDialogFormVisible = false
            if (res.success) {
              this.$message({
                showClose: true,
                message: res.content,
                duration: 8000,
                type: 'success'
              })
              this.searchData()
              this.$refs.upload.clearFiles()
              this.file = null
            } else {
              this.$message.error(res.message)
              this.$refs.upload.clearFiles()
              this.file = null
            }
          }).catch(error => {
            this.saveCustomerWld = false
            this.batchAddDialogFormVisible = false
            this.$refs.upload.clearFiles()
            this.$message.error('导入发生错误:' + error)
          })
        }
      } else {
        this.batchAddForm.loginUserNo = this.$store.getters.position.psnsmcId
        var arrStr = this.batchAddForm.inputStr.split('\n')
        // 将arrStr的每个元素都用逗号隔开，并校验隔开后的数组大小是否为2，然后组装成 { customerNo: '',isWldate: int }
        var validateSuccess = true
        arrStr.forEach(item => {
          var customerInfo = item.split(',')
          if (customerInfo.length !== 2 || customerInfo[0] === '') {
            this.$message.warning('编码格式不正确，请用逗号隔开客户单号和类型')
            this.saveCustomerWld = false
            validateSuccess = false
          }
          if (isNaN(customerInfo[1]) || customerInfo[1] === null) {
            this.$message.warning('编码格式不正确，类型必须为数字')
            this.saveCustomerWld = false
            validateSuccess = false
          }
          this.batchAddForm.customerNos.push({ customerNo: customerInfo[0], isWldate: parseInt(customerInfo[1]) })
        })
        if (!validateSuccess) {
          return
        }
        batchCustomerWld(this.batchAddForm).then(res => {
          this.saveCustomerWld = false
          this.batchAddDialogFormVisible = false
          if (res.success) {
            this.$message.success(res.content)
            this.searchData()
            this.batchAddForm.inputStr = ''
            this.batchAddForm.customerNos = []
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.saveCustomerWld = false
          this.batchAddDialogFormVisible = false
          this.$message.error('新增发生错误:' + error)
        })
      }
    },
    exportData() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportCustomerWldList(this.searchForm).then(res => {
        const fileName = '客户备案数据信息导出.xlsx'
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
    downLoadExcel() {
      this.$message.success('已开始下载，请耐心等待...')
      downLoadExcel().then(res => {
        const fileName = '客户备案数据信息导出模板.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
      }).catch(error => {
        this.$message.warning('导出失败' + error)
      })
    },
    delCustomerFili() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: '请选中需要删除的数据',
          type: 'warning'
        })
      }
      var newArr = this.multipleSelection.map((item, index) => {
        return { customerNo: item.customerNo, isWldate: item.isWldate }
      })
      this.customerWldDel.customerNoList = newArr
      this.customerWldDel.loginUserNo = this.$store.getters.position.psnsmcId
      delCustomerWldByCustomerNos(this.customerWldDel).then(res => {
        if (res.success) {
          this.$message({
            message: res.content,
            type: 'success'
          })
          this.searchData()
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          })
        }
      }).catch((error) => {
        this.$message.error(error)
      })
    },
    beforeUploadFile(file) {
      this.file = file
      this.batchAddForm = {
        inputStr: '',
        wldType: 1,
        loginUserNo: '',
        customerNos: []
      }
    },
    handleExceed(files, fileList) {
      this.$message.warning('当前限制选择 1 个文件')
    },
    onRemove(file, fileList) {
      return this.$confirm('确定移除' + file.name + '嘛?')
    },
    resetForm() {
      this.form = {
        customerNo: '',
        isWldate: '1',
        customerName: '',
        deptNo: '',
        psnSmcId: '',
        psnSmcIdName: '',
        modifyTime: '',
        modifier: ''
      }
    }
  }
}
</script>
<style scoped>
.warnTex {
  margin-top: -15px;
  font: menu;
}

/* .downLoadExcel{
  position: absolute;
  margin-left: 110px;
  margin-top: -57px;
} */
</style>
