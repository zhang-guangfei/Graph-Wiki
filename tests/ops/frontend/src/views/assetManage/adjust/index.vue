<template>
  <div class="container">
    <el-form :inline="true" :model="searchForm" class="demo-form-inline searchFormClass" size="mini">
      <el-form-item>
        <el-select
          v-model="searchForm.pdBatchNo"
          :remote-method="remoteMethod"
          :loading="selLoading"
          filterable
          remote
          clearable
          placeholder="盘点批次号">
          <el-option
            v-for="item in pdBatchNoList"
            :key="item.pdBatchNo"
            :label="item.lable"
            :value="item.pdBatchNo"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.warehouseType" clearable placeholder="仓库类别" @change="selTypeChange">
          <el-option
            v-for="item in warehouseTypeList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select
          v-model="searchForm.warehouseCodes"
          :remote-method="remoteMethodByWareHouseCode"
          :loading="selWareHouseCodeLoading"
          filterable
          multiple
          collapse-tags
          remote
          clearable
          placeholder="仓库代码"
          style="width: 220px"
          size="mini">
          <el-option
            v-for="item in wareHouseCodeList"
            :key="item.warehouseCode"
            :label="'【'+item.warehouseCode+'】'+item.warehouseName"
            :value="item.warehouseCode"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-select v-model="searchForm.handStatus" clearable style="width: 120px" placeholder="请选择状态">
          <el-option
            v-for="item in statusOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="searchForm.modelNo" placeholder="请输入型号"/>
      </el-form-item>
      <el-form-item>
        <el-button size="mini" type="primary" @click="searchData()">查询</el-button>
      </el-form-item>
      <el-form-item>
        <el-button :loading="pdAdjustLoading" :disabled="disabledBtu" type="primary" @click="createPdAdjust()">生成调账单</el-button>
        <el-button :loading="exportLoading" type="primary" @click="exportData()">导出调账单</el-button>
        <el-button :disabled="disabledBtu" :loading="confirmBtu" type="primary" @click="confirmPdAccount()">确认调账单</el-button>
        <el-button type="primary" @click="pdAdjust()">单条调账</el-button>
        <el-button type="primary" @click="batchPdAdjust()">批量调账</el-button>
      </el-form-item>
    </el-form>
    <vxe-table
      ref="xTable1"
      :data="adjustTableData"
      :row-config="{height: 25}"
      :column-config="{resizable: true}"
      show-overflow
      border
      height="570"
      size="mini"
      @checkbox-all="selectChangeEvent"
      @checkbox-change="selectChangeEvent">
      <vxe-column type="checkbox" width="60"/>
      <vxe-column field="pdBatchNo" title="盘点批次号" width="100px" show-overflow/>
      <vxe-column field="warehouseCode" title="仓库代码" width="100px" show-overflow/>
      <vxe-column field="adjustInvoiceNo" title="调账票号" width="100px" show-overflow/>
      <vxe-column field="adjustNo" title="调账单号" width="140px" show-overflow/>
      <vxe-column field="adjustItemNo" title="调账单项号" width="80px" show-overflow/>
      <vxe-column field="modelNo" title="型号" width="140px" show-overflow/>
      <vxe-column field="adjustQty" title="调整数量" width="100px" show-overflow/>
      <vxe-column field="inventoryType" title="库存类型" width="120px" show-overflow/>
      <vxe-column field="customerNo" title="客户代码" width="100px" show-overflow/>
      <vxe-column field="PPL" title="PPL" width="100px" show-overflow/>
      <vxe-column field="projectNo" title="项目号" width="100px" show-overflow/>
      <vxe-column field="groupCustomerNo" title="客户群代码" width="80px" show-overflow/>
      <vxe-column field="salesInfoNo" title="情报号" width="100px" show-overflow/>
      <vxe-column field="createUser" title="创建人" width="110px" show-overflow/>
      <vxe-column field="createTimeStr" title="创建时间" width="100px" show-overflow/>
      <vxe-column field="handStatusName" title="调账状态" width="100px" show-overflow/>
      <vxe-column field="confirmUser" title="确认人" width="100px" show-overflow/>
      <vxe-column field="confirmTimeStr" title="确认时间" width="120px" show-overflow/>
      <vxe-column field="remark" title="备注" width="200px" show-overflow/>
    </vxe-table>
    <pagination
      v-show="total > 10"
      :total="total"
      :page-sizes="[10, 20, 100, 150, 200]"
      :page.sync="searchForm.page.pageNumber"
      :limit.sync="searchForm.page.pageSize"
      @pagination="searchData()"/>
    <el-dialog :visible.sync="dialogFormVisible" title="盘点调账">
      <el-form ref="adjustform" :inline="true" :model="adjustform" :rules="rules" size="minii">
        <el-row :gutter="20">
          <el-form-item prop="warehouseCode" label="仓库代码">
            <el-select v-model="adjustform.warehouseCode" placeholder="仓库" size="mini" style="width: 150px" clearable>
              <el-option v-for="item in warehouseData" :key="item.warehouseCode" :label="item.warehouseCode+item.warehouseName" :value="item.warehouseCode"/>
            </el-select>
          </el-form-item>
          <el-form-item label="调账票号" prop="adjustInvoiceNo">
            <el-input v-model="adjustform.adjustInvoiceNo" disabled size="mini" style="width:150px" />
            <el-button type="primary" size="mini" @click="getAdjustInvoiceNo()">获取调账票号</el-button>
          </el-form-item>
          <el-form-item label="型号" prop="modelNo">
            <el-input v-model="adjustform.modelNo" size="mini" style="width:150px" />
          </el-form-item>
        </el-row>
        <el-row :gutter="20">
          <el-form-item label="调账单号" prop="adjustNo">
            <el-input v-model="adjustform.adjustNo" disabled size="mini" style="width:160px" />
          </el-form-item>
          <el-form-item label="客户代码">
            <el-autocomplete
              v-model="adjustform.customerNo"
              :fetch-suggestions="querySearchAsync"
              :debounce="0"
              :popper-append-to-body="false"
              popper-class="el-autocomplete-suggestion"
              highlight-first-item
              type="text"
              size="mini"
              placeholder="客户代码"
              class="select"
              @select="Changeselect">
              <template slot-scope="{ item }">
                <div style="width: 200px" class="name">{{ item.customerNo + ',' + item.name }}</div>
              </template>
            </el-autocomplete>
          </el-form-item>
          <el-form-item prop="inventoryType" label="库存类型">
            <el-select v-model="adjustform.inventoryType" placeholder="库存类型" size="mini" clearable style="width: 160px">
              <el-option v-for="item in inventoryData" :key="item.code" :label="item.code+item.codeName" :value="item.code"/>
            </el-select>
          </el-form-item>
        </el-row>
        <el-row :gutter="20">
          <el-form-item prop="adjustQty" label="调账数量">
            <el-input v-model.number="adjustform.adjustQty" size="mini" style="width:160px" />
          </el-form-item>
          <el-form-item label="PPL">
            <el-input v-model="adjustform.ppl" size="mini" style="width:160px" />
          </el-form-item>
          <el-form-item label="项目号">
            <el-input v-model="adjustform.projectNo" size="mini" style="width:160px" />
          </el-form-item>
        </el-row>
        <el-row :gutter="20">
          <el-form-item label="情报号">
            <el-input v-model="adjustform.salesInfoNo" size="mini" style="width:160px" />
          </el-form-item>
          <el-form-item label="客户群代码">
            <el-input v-model="adjustform.groupCustomerNo" size="mini" style="width:160px" />
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisible = false" >取 消</el-button>
        <el-button type="primary" size="mini" @click="createAdjustform('adjustform')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="批量调账"
      width="420px"
    >
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
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="success" class="downLoadExcel" @click="downLoadExcel" >下载模板</el-button>
        <el-button size="mini" @click="uploadFileDialogVisible = false">取 消</el-button>
        <el-button :loading="uploadLoading" size="mini" type="primary" @click="importData()">导 入</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { getDictDataByPid, findWareHouseInfoWithLike, listWarehouse } from '@/api/common/dict'
import { findPdAdjustList, exportPdAdjustData, createPdAdjust, confirmPdAccount, createAdjustNo, createAdjustform, dowmBatchAdjusExcel, batchAdjustData, getBatchNoWithIsActive, findAdjustDoList } from '@/api/pd/pd'
import Pagination from '@/components/Pagination'
import { getCustomerByNo } from '@/api/common/customer'
export default {
  name: 'PdAdjust',
  components: { Pagination },
  data() {
    return {
      wareHouseCodeList: [],
      adjustTableData: [],
      selChangeDataList: [],
      searchForm: {
        handStatus: '',
        warehouseType: 'MASTER',
        warehouseCodes: [],
        modelNo: '',
        pdBatchNo: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      statusOptions: [{
        value: '0',
        label: '未调账'
      }, {
        value: '1',
        label: '已调账'
      }, {
        value: '2',
        label: '调账失败'
      }],
      handlePreOrderAccountParam: {
        ids: [],
        optUser: ''
      },
      disabledBtu: false,
      total: 0,
      exportLoading: false,
      pdAdjustLoading: false,
      warehouseTypeList: {},
      pdTypeCode: '5003',
      warehouseTypeClassCode: '4001',
      inventoryClassCode: '2001',
      selWareHouseCodeLoading: false,
      dialogFormVisible: false,
      uploadLoading: false,
      uploadFileDialogVisible: false,
      confirmBtu: false,
      adjustform: {
        warehouseCode: '',
        adjustInvoiceNo: '',
        modelNo: '',
        adjustNo: '',
        inventoryType: '',
        customerNo: '',
        adjustQty: '',
        ppl: '',
        projectNo: '',
        salesInfoNo: '',
        groupCustomerNo: '',
        createUser: ''
      },
      selLoading: false,
      warehouseData: [],
      inventoryData: [],
      warehouseForm: {
        warehouseCode: '',
        warehouseName: '',
        warehouseType: ''
      },
      actionUrl: '',
      file: '',
      rules: {
        warehouseCode: [
          { required: true, message: '请选择仓库', trigger: 'blur' }
        ],
        adjustInvoiceNo: [
          { required: true, message: '请生成调账票号', trigger: 'blur' }
        ],
        modelNo: [
          { required: true, message: '请输入型号', trigger: 'blur' }
        ],
        adjustNo: [
          { required: true, message: '生成调账票号会一并生成单号', trigger: 'blur' }
        ],
        inventoryType: [
          { required: true, message: '请选择库存类型', trigger: 'blur' }
        ],
        adjustQty: [
          { required: true, message: '请输入调账数量', trigger: 'blur' }
        ]
      }
    }
  },
  mounted() {
    this.getCurBatchNo()
    this.getDataCodesTreeBywarehouseType()
    this.remoteMethodByWareHouseCode()
    // this.searchData()
    this.listWarehouseinfo()
    this.initData()
    this.getBatchNoWithIsActive()
    this.remoteMethod('')
  },
  methods: {
    getCurBatchNo() {
      getBatchNoWithIsActive().then(res => {
        this.searchForm.pdBatchNo = res.content.pdBatchNo
        this.searchData()
      })
    },
    remoteMethod(batchNo) {
      this.selLoading = true
      findAdjustDoList(batchNo).then(res => {
        if (res.success) {
          this.pdBatchNoList = res.content
        } else {
          this.pdBatchNoList = []
        }
        this.selLoading = false
      })
    },
    getBatchNoWithIsActive() {
      getBatchNoWithIsActive().then(res => {
        if (res.success) {
          if (res.content.pdState === '12') {
            this.disabledBtu = true
          }
        } else {
          this.disabledBtu = false
        }
      })
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    importData() {
      this.uploadLoading = true
      if (this.file == null) {
        this.uploadLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        batchAdjustData(formData).then(res => {
          this.uploadLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.searchData()
            this.file = null
            this.uploadFileDialogVisible = false
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.$message.error('调账失败:' + error)
        })
      }
    },
    downLoadExcel() {
      this.$message.success('已开始下载，请耐心等待...')
      dowmBatchAdjusExcel().then(res => {
        const fileName = '批量调账模板.xlsx'
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
    batchPdAdjust() {
      this.uploadFileDialogVisible = true
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    getAdjustInvoiceNo() {
      if (this.adjustform.warehouseCode === null || this.adjustform.warehouseCode === '') {
        this.$notify({
          title: '警告',
          message: '请先选择仓库',
          type: 'warning'
        })
        return
      }
      createAdjustNo(this.adjustform.warehouseCode).then(res => {
        if (res.success) {
          this.adjustform.adjustInvoiceNo = res.content.adjustInvoiceNo
          this.adjustform.adjustNo = res.content.adjustNo
          this.adjustform.pdBatchNo = res.content.pdBatchNo
          this.adjustform.createUser = this.$store.getters.position.psnsmcId
        } else {
          this.$notify({
            title: '错误',
            message: '获取失败' + res.message,
            type: 'error'
          })
        }
      })
    },
    createAdjustform(adjustform) {
      this.$refs[adjustform].validate((valid) => {
        if (valid) {
          createAdjustform(this.adjustform).then(res => {
            console.log('==.', res)
            if (res.success) {
              this.$refs[adjustform].resetFields()
              this.dialogFormVisible = false
            }
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    Changeselect(item) {
      this.adjustform.customerNo = item.customerNo
    },
    querySearchAsync(customerNo, cb) {
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray
        cb(results)
      })
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.customerNo.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    initData() {
      getDictDataByPid(this.inventoryClassCode).then(result => {
        this.inventoryData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: this.warehouseForm.warehouseType,
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        for (let i = 0; i < res.content.length; i++) {
          if (res.content[i].warehouseType !== 'WT') {
            this.warehouseData.push(res.content[i])
          }
        }
        // this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    pdAdjust() {
      this.dialogFormVisible = true
    },
    confirmPdAccount() {
      this.$confirm('将确认调账单进行调账, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.confirmBtu = true
        this.handlePreOrderAccountParam.isd = []
        this.handlePreOrderAccountParam.optuser = this.$store.getters.position.psnsmcId
        if (this.selChangeDataList.length > 0) {
          this.selChangeDataList.forEach((row, index) => {
            this.handlePreOrderAccountParam.ids.push(row.id)
          })
        }
        confirmPdAccount(this.handlePreOrderAccountParam).then(res => {
          this.confirmBtu = false
          if (res.success) {
            this.$message({
              type: 'success',
              message: '调账完毕'
            })
            this.getCurBatchNo()
          } else {
            this.$notify({
              title: '错误',
              message: '获取失败' + res.message,
              type: 'error'
            })
          }
        }).catch(() => {
          this.confirmBtu = false
        })
      }).catch(() => {
        this.confirmBtu = false
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    createPdAdjust() {
      this.pdAdjustLoading = true
      this.$notify({
        title: '成功',
        message: '调账单正在导出,生成完毕之后会以邮件形式发送给您异常数据,请留意接收',
        type: 'success'
      })
      createPdAdjust(this.$store.getters.position.psnsmcId).then(res => {
        if (!res.success) {
          this.$notify({
            title: '提示',
            message: res.message,
            type: 'warning'
          })
        }
        this.pdAdjustLoading = false
      })
    },
    exportData() {
      this.exportLoading = true
      this.$notify({
        title: '成功',
        message: '数据正在导出,导出完毕之后会以邮件形式发送给您,请留意接收',
        type: 'success'
      })
      exportPdAdjustData(this.searchForm).then(res => {
        this.exportLoading = false
      })
    },
    searchData() {
      findPdAdjustList(this.searchForm).then(res => {
        if (res.success) {
          this.adjustTableData = res.content.list
          this.total = res.content.total
        } else {
          this.adjustTableData = []
          this.total = 0
        }
      })
    },
    selectChangeEvent(row) {
      this.selChangeDataList = row.records
      console.log('this.selChangeDataList=>', this.selChangeDataList)
    },
    onSubmit() {
      console.log('=>')
    },
    getDataCodesTreeBywarehouseType() {
      getDictDataByPid(this.warehouseTypeClassCode).then(res => {
        if (res.success) {
          this.warehouseTypeList = res.content
        } else {
          this.warehouseTypeList = []
        }
      })
    },
    selTypeChange() {
      this.remoteMethodByWareHouseCode('')
    },
    remoteMethodByWareHouseCode(code) {
      var warehouseCode = ''
      if (code === undefined) {
        warehouseCode = ''
      } else {
        warehouseCode = code
      }
      this.selWareHouseCodeLoading = true
      findWareHouseInfoWithLike(warehouseCode, this.searchForm.warehouseType).then(res => {
        if (res.success) {
          this.wareHouseCodeList = res.content
        } else {
          this.wareHouseCodeList = []
        }
        this.selWareHouseCodeLoading = false
      })
    }
  }
}
</script>

<style scoped>
.searchFormClass{
  margin: 20px;
}
.select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
}
</style>
