<template>
  <el-container>
    <el-main>
      <el-form ref="searchForm" :model="searchForm" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="订单号">
          <el-input v-model="searchForm.orderNo" placeholder="请输入订单号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="型号">
          <el-input v-model="searchForm.modelNo" placeholder="请输入型号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item class="samplebalDaySelVal">
          <el-select v-model="dayValSel" style="width: 120px" clearable placeholder="请选择日期类型:">
            <el-option label="结转日期" value="inDate"/>
            <el-option label="发货日期" value="shipDate"/>
            <el-option label="导入日期" value="creDate"/>
          </el-select>
          <el-date-picker
            v-model="selDateArray"
            :picker-options="pickerOptions"
            :default-time="['00:00:00', '23:59:59']"
            type="daterange"
            align="right"
            unlink-panels
            style="width: 230px"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @change="changeSelDate()"
          />
        </el-form-item>
        <department @handleScopeChange="handleScopeChange"/>
        <el-button type="primary" size="mini" style="margin-left: 10px;" @click="initList()">查询</el-button>
        <br>
        <el-button-group>
          <el-button type="primary" icon="el-icon-plus" size="mini" @click="add()">新增</el-button>
          <el-button type="primary" icon="el-icon-edit" size="mini" @click="edit()">编辑</el-button>
          <el-button :loading="exportLoading" type="primary" icon="el-icon-download" size="mini" @click="exportData()">查询数据导出</el-button>
          <el-button type="primary" icon="el-icon-download" size="mini" @click="exportZlzsOrderManage()" >盘点票导出</el-button>
          <el-button :loading="pushExportZsBalDataLoading" type="primary" icon="el-icon-upload" size="mini" @click="pushZlzsOrderBalance()" >发布盘点票</el-button>
          <el-button type="primary" icon="el-icon-remove" size="mini" @click="writeOff()">单次销账</el-button>
          <el-button type="primary" icon="el-icon-folder-add" size="mini" @click="batchWriteOff()" >批量销账</el-button>
          <el-button type="primary" icon="el-icon-edit-outline" size="mini" @click="upSampleOrderMagageDeptNo()" >单次变更实物所在部门</el-button>
          <el-button type="primary" icon="el-icon-folder-add" size="mini" @click="batchUpSampleOrderManageDeptNo()">批量变更实物所在部门</el-button>
          <el-button type="primary" icon="el-icon-folder-add" size="mini" @click="batchImportSampleOrderManageData()">历史盘点数据导入</el-button>
        </el-button-group>
      </el-form>
      <el-table
        :data="tableData"
        :default-sort = "{prop: 'sort', order: 'ascending'}"
        border
        stripe
        @selection-change="handleSelectionChange">

        <el-table-column
          text="选择"
          type="selection"
          width="40"
          align="center"/>

        <el-table-column
          prop="orderNo"
          label="订单号"
          width="160"
          show-overflow-tooltip />

        <el-table-column
          prop="modelNo"
          label="型号"
          width="160"
          show-overflow-tooltip />

        <el-table-column
          prop="impQty"
          label="数量"
          width="80" />

        <el-table-column
          prop="shipDate"
          label="发货日期"
          width="110"
          show-overflow-tooltip />

        <el-table-column
          prop="statusName"
          label="状态"
          width="80" />

        <el-table-column
          prop="deptName"
          label="所在营业所"
          width="160"
          show-overflow-tooltip/>

        <el-table-column
          prop="parentDeptName"
          label="管理营业所"
          width="160"
          show-overflow-tooltip/>

        <el-table-column
          prop="manageName"
          label="负责人"
          width="100"
          show-overflow-tooltip />

        <el-table-column
          prop="remark"
          label="备注"
          width="200"
          show-overflow-tooltip/>

        <el-table-column
          prop="updateUserName"
          label="操作人"
          width="100"
          show-overflow-tooltip />

        <el-table-column
          prop="outTime"
          label="结转时间"
          width="110"
          show-overflow-tooltip />

        <el-table-column
          prop="createTime"
          label="创建时间"
          width="160"
          show-overflow-tooltip />

        <el-table-column
          prop="updateTime"
          label="修改时间"
          width="160"
          show-overflow-tooltip />
      </el-table>
      <pagination
        v-show="total > 10"
        :total="total"
        :page-sizes="[20, 50, 100, 150, 200]"
        :page.sync="searchForm.page.pageNumber"
        :limit.sync="searchForm.page.pageSize"
        @pagination="initList()"/>
      <el-dialog :visible.sync="WriteOffZlzsDialog" :close-on-click-modal="false" title="单次展示品销账" class="WriteOffZlzsDialog">
        <el-form ref="sampleOrderManageVO" :model="sampleOrderManageVO" :rules="writeOffRules" label-position="right" size="mini">
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item label="订单号" label-width="70px">
                <el-input v-model="sampleOrderManageVO.orderNo" autocomplete="off" style="width: 150px" />
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="型 号">
                <el-input v-model="sampleOrderManageVO.modelNo" autocomplete="off" style="width: 150px" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="10">
              <el-form-item label="销账数量">
                <el-input v-model="sampleOrderManageVO.impQty" autocomplete="off" style="width:100px" />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="负责人" label-width="75px">
                <el-input v-model="sampleOrderManageVO.manager" autocomplete="off" style="width: 150px" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="WriteOffZlzsDialog = false">取 消</el-button>
          <el-button :loading="sureWriteOffBtuLoad" type="primary" size="mini" @click="sureWriteOff('sampleOrderManageVO')">确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadFileDialogVisible"
        :before-close="closeClick"
        title="批量展示品销账"
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
          <el-button :loading="downLoadExcelBtuLoad" size="small" type="success" class="downLoadExcel" @click="downLoadExcel" >下载模板</el-button>
          <el-button size="mini" @click="uploadFileDialogVisible = false">取 消</el-button>
          <el-button :loading="uploadLoading" size="mini" type="primary" @click="importData()">销 账</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="upZlzsRcvDeptNoDialog" :close-on-click-modal="false" title="变更展示品实物所在部门" style="width: 80%" >
        <el-form :model="sampleOrderManageVO" size="mini">
          <el-form-item label="订单号" style="margin-left: 83px" >
            <el-input v-model="sampleOrderManageVO.orderNo" :disabled="true" clearable style="width: 120px" autocomplete="off" placeholder="请输入订单号" />
          </el-form-item>
          <el-form-item label="展示品实物所在部门" >
            <el-select v-model="sampleOrderManageVO.deptNo" clearable filterable placeholder="展示品实物所在部门" size="mini" style="width: 160px">
              <el-option
                v-for="item in deptNoListData"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="upZlzsRcvDeptNoDialog = false">取 消</el-button>
          <el-button :loading="upDeptNoLoad" size="mini" type="primary" @click="sureUpSampleOrderManageDeptNo()">确定变更</el-button>
        </div>
      </el-dialog>
      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadFileDialogWithUpRcvDeptNo"
        :before-close="closeClick2"
        title="展览展示品批量变更实物所在部门"
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
          <el-button size="small" type="success" class="downLoadExcel" @click="downLoadExcelWithUpRcvDeptNo" >下载模板</el-button>
          <el-button size="mini" @click="uploadFileDialogWithUpRcvDeptNo = false">取 消</el-button>
          <el-button :loading="impUpRcvDeptNoLoading" size="mini" type="primary" @click="sureBatchUpSampleOrderManageDeptNo()">变 更</el-button>
        </div>
      </el-dialog>
      <el-dialog :title="title" :visible.sync="dialogFormVisible" :close-on-click-modal="false">
        <el-form ref="sampleOrderManageDoForm" :model="sampleOrderManageDoForm" :rules="formVerification" size="mini">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="订单号" label-width="80px" prop="orderNo">
                <el-input v-model="sampleOrderManageDoForm.orderNo" autocomplete="off" style="width: 140px" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="型 号" label-width="60px" prop="modelNo">
                <el-input v-model="sampleOrderManageDoForm.modelNo" autocomplete="off" style="width: 140px" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="结余数量" label-width="80px">
                <el-input v-model="sampleOrderManageDoForm.impQty" autocomplete="off" style="width: 80px" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="发货日期" label-width="80px">
                <el-date-picker
                  v-model="sampleOrderManageDoForm.shipDate"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy-MM-dd"
                  size="mini"
                  style="width: 130px"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="结转日期" label-width="80px" prop="outTime">
                <el-date-picker
                  v-model="sampleOrderManageDoForm.outTime"
                  type="date"
                  placeholder="选择日期"
                  format="yyyy-MM-dd"
                  size="mini"
                  style="width: 130px"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="负责人" label-width="80px">
                <el-input v-model="sampleOrderManageDoForm.manager" autocomplete="off" style="width: 80px" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="12">
              <el-form-item label="实物所在营业所" label-width="120px" prop="deptNo" >
                <el-select v-model="sampleOrderManageDoForm.deptNo" :disabled="isShowEditDetpNo" clearable filterable placeholder="展示品实物所在部门" size="mini" style="width: 160px">
                  <el-option
                    v-for="item in deptNoListData"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"/>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-switch
                v-model="statusBoolean"
                style="block: display"
                active-color="#13ce66"
                inactive-color="#ff4949"
                active-text="状态有效"
                inactive-text="状态无效"/>
            </el-col>
          </el-row>
          <el-form-item label="备注" label-width="80px">
            <el-input v-model="sampleOrderManageDoForm.remark" autocomplete="off" style="width: 90%" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogFormVisible = false">取 消</el-button>
          <el-button :loading="editBtuLoading" size="mini" type="primary" @click="sureMarkOpt('sampleOrderManageDoForm')" >确 定</el-button>
        </div>
      </el-dialog>
      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="batchImpHistoryDataDialog"
        :before-close="closeClick3"
        title="批量导入历史盘点票数据"
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
          <el-button :loading="downLoadHistoryBtuLoad" size="small" type="success" class="downLoadExcel" @click="downLoadHistorySampleOrderManageExcel" >下载模板</el-button>
          <el-button size="mini" @click="batchImpHistoryDataDialog = false">取 消</el-button>
          <el-button :loading="impHistoryDataBtuLoad" size="mini" type="primary" @click="sureImpHistoryData()">导 入</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="exportExcelForZLZS" title="盘点票导出" class="zlzsExportOrderBalClass">
        <span class="demonstration">请选择导出的结转日期范围</span>
        <el-date-picker
          v-model="zlzsExportOrderBalTime"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          unlink-panels
          size="mini"/>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="exportExcelForZLZS = false">取 消</el-button>
          <el-button :loading="sureExportBalLoading" type="primary" size="mini" @click="zlzsExportBal()">确 定</el-button>
        </div>
      </el-dialog>
    </el-main>
  </el-container>
</template>
<script>

import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import { listSampleOrderManage, exportSampleOrderManageData, exportZlzsManageData, pushZlzsSampleOrderManageForPdf, zlzsOrderWriteOff,
  downExcelForWriteOff, batchImportWriteOffData, upSampleOrderManageDeptNo, downExcelForUpRcvDeptNo, batchUpSampleOrderManageDeptNo,
  batchImportSampleOrderManageData, editSampleOrderManage, importSampleOrderManageData, downLoadHistorySampleOrderManageExcel, getZLZSExportTime } from '@/api/order/sampleOrder'
import { shortcuts } from '@/utils/dataFormat'
import { getDeptAllData } from '@/api/common/department'
export default {
  components: { Pagination, Department },
  data() {
    var validateQty = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入数量(正数)'))
      } else {
        if (isNaN(Number(value)) || value < 0) {
          callback(new Error('请输入正确数值(正数)'))
        }
        callback()
      }
    }
    return {
      selDateArray: [],
      dayValSel: '',
      searchForm: {
        orderNo: '',
        modelNo: '',
        shipDateStart: '',
        shipDateEnd: '',
        inDateStart: '',
        inDateEnd: '',
        creDateStart: '',
        creDateEnd: '',
        deptNos: [],
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      sampleOrderManageVO: {
        id: '',
        deptNo: '',
        orderNo: '',
        modelNo: '',
        impQty: '',
        manager: '',
        manageName: '',
        beforeUpDeptNo: ''
      },
      zlzsExportOrderBalTime: [],
      zlzsOrderBal: false,
      sureWriteOffBtuLoad: false,
      upDeptNoLoad: false,
      upZlzsRcvDeptNoDialog: false,
      sureExportBalLoading: false,
      uploadFileDialogVisible: false,
      downLoadExcelBtuLoad: false,
      uploadLoading: false,
      uploadFileDialogWithUpRcvDeptNo: false,
      impUpRcvDeptNoLoading: false,
      pushExportZsBalDataLoading: false,
      batchImpHistoryDataDialog: false,
      impHistoryDataBtuLoad: false,
      exportExcelForZLZS: false,
      isShowEditDetpNo: false,
      actionUrl: '',
      file: null,
      pickerOptions: shortcuts,
      scopeOptions: [],
      deptNoListData: [],
      dialogFormVisible: false,
      statusBoolean: true,
      editBtuLoading: false,
      downLoadHistoryBtuLoad: false,
      sampleOrderManageDoForm: {
        id: '',
        modelNo: '',
        orderNo: '',
        deptNo: '',
        impQty: '',
        manager: '',
        shipDate: '',
        outTime: '',
        status: '',
        remark: '',
        updateUser: ''
      },
      // 营业所树状菜单用
      propsDeptNo: { multiple: true },
      // 部门筛选=> 获取一级部门
      // tempDeptNo: [],
      total: 1,
      tableData: [],
      exportLoading: false,
      WriteOffZlzsDialog: false,
      deptClassCode: '9005',
      title: '',
      multipleSelection: [],
      writeOffRules: {
        impQty: [{ required: true, validator: validateQty, trigger: 'blur' }]
      },
      formVerification: {
        impQty: [{ required: true, validator: validateQty, trigger: 'blur' }],
        orderNo: { required: true, message: '请填写订单号', trigger: 'blur' },
        modelNo: { required: true, message: '请填写型号', trigger: 'blur' },
        deptNo: { required: true, message: '请选择实物所在部门', trigger: 'blur' },
        outTime: { required: true, message: '请选择结转日期', trigger: 'blur' }
      }
    }
  },
  mounted() {
    this.initList()
  },
  methods: {
    initList() {
      this.searchTime()
      listSampleOrderManage(this.searchForm, this.searchForm.page).then(res => {
        this.tableData = res.content.list
        this.total = res.content.total
      })
    },
    sureImpHistoryData() {
      this.impHistoryDataBtuLoad = true
      if (this.file == null) {
        this.impHistoryDataBtuLoad = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        batchImportSampleOrderManageData(formData).then(res => {
          this.impHistoryDataBtuLoad = false
          this.file = null
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.initList()
            this.batchImpHistoryDataDialog = false
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.impHistoryDataBtuLoad = false
          this.file = null
          this.$message.error('导入失败:' + error)
        })
      }
    },
    batchImportSampleOrderManageData() {
      this.batchImpHistoryDataDialog = true
      this.file = null
    },
    batchUpSampleOrderManageDeptNo() {
      this.uploadFileDialogWithUpRcvDeptNo = true
      this.file = null
    },
    add() {
      this.title = '新增盘点票'
      this.isShowEditDetpNo = false
      this.resetSampleOrderManageDoForm()
      this.getDeptAllData()
      this.dialogFormVisible = true
    },
    edit() {
      this.title = '编辑盘点票'
      this.isShowEditDetpNo = true
      if (this.multipleSelection.length !== 1) {
        this.$message.warning('请选择一条数据进行编辑.')
        return
      }
      this.getDeptAllData()
      this.dialogFormVisible = true
      this.resetSampleOrderManageDoForm()
      this.sampleOrderManageDoForm.id = this.multipleSelection[0].id
      this.sampleOrderManageDoForm.modelNo = this.multipleSelection[0].modelNo
      this.sampleOrderManageDoForm.orderNo = this.multipleSelection[0].orderNo
      this.sampleOrderManageDoForm.deptNo = this.multipleSelection[0].deptNo
      this.sampleOrderManageDoForm.impQty = this.multipleSelection[0].impQty
      this.sampleOrderManageDoForm.manager = this.multipleSelection[0].manager
      this.sampleOrderManageDoForm.shipDate = this.multipleSelection[0].shipDate
      this.sampleOrderManageDoForm.outTime = this.multipleSelection[0].outTime
      this.sampleOrderManageDoForm.remark = this.multipleSelection[0].remark
      if (this.multipleSelection[0].status === 1) {
        this.statusBoolean = true
      } else {
        this.statusBoolean = false
      }
    },
    sureMarkOpt(sampleOrderManageDoForm) {
      this.editBtuLoading = true
      console.log('==>', this.sampleOrderManageDoForm)
      this.$refs[sampleOrderManageDoForm].validate((valid) => {
        if (valid) {
          if (this.statusBoolean) {
            console.log('true')
            this.sampleOrderManageDoForm.status = 1
          } else {
            console.log('false')
            this.sampleOrderManageDoForm.status = 9
          }
          this.sampleOrderManageDoForm.updateUser = this.$store.getters.position.psnsmcId
          if (this.isEmptyStr(this.sampleOrderManageDoForm.id)) {
            this.addSampleOrderManageData(this.sampleOrderManageDoForm)
          } else {
            this.upSampleOrderManageData(this.sampleOrderManageDoForm)
          }
        } else {
          this.editBtuLoading = false
        }
      })
    },
    // 新增盘点票
    addSampleOrderManageData(data) {
      importSampleOrderManageData(data).then(res => {
        this.editBtuLoading = false
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.dialogFormVisible = false
          this.initList()
          this.multipleSelection = []
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      }).catch(error => {
        this.editBtuLoading = false
        console.log(error)
      })
    },
    // 修改盘点票
    upSampleOrderManageData(data) {
      editSampleOrderManage(data).then(res => {
        this.editBtuLoading = false
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.dialogFormVisible = false
          this.initList()
          this.multipleSelection = []
          this.resetSampleOrderManageDoForm()
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      }).catch(error => {
        this.editBtuLoading = false
        console.log(error)
      })
    },
    sureBatchUpSampleOrderManageDeptNo() {
      this.impUpRcvDeptNoLoading = true
      if (this.file == null) {
        this.impUpRcvDeptNoLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        batchUpSampleOrderManageDeptNo(formData).then(res => {
          this.impUpRcvDeptNoLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.initList()
            this.file = null
            this.uploadFileDialogWithUpRcvDeptNo = false
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.$message.error('变更失败:' + error)
        })
      }
    },
    closeClick2() {
      this.file = null
      this.uploadFileDialogWithUpRcvDeptNo = false
    },
    closeClick3() {
      this.file = null
      this.batchImpHistoryDataDialog = false
    },
    downLoadExcelWithUpRcvDeptNo() {
      this.$message.success('已开始下载，请耐心等待...')
      downExcelForUpRcvDeptNo().then(res => {
        const fileName = '批量变更展示品实物所在部门模板.xlsx'
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
    getDeptAllData() {
      var deptNo = ''
      getDeptAllData(deptNo).then(res => {
        this.deptNoListData = res.content
      })
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    batchWriteOff() {
      this.uploadFileDialogVisible = true
      this.file = null
    },
    sureUpSampleOrderManageDeptNo() {
      this.upDeptNoLoad = true
      upSampleOrderManageDeptNo(this.sampleOrderManageVO).then(res => {
        this.upDeptNoLoad = false
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.initList()
          this.upZlzsRcvDeptNoDialog = false
          this.multipleSelection = []
        } else {
          this.$notify({
            title: '失败',
            message: res.message,
            type: 'error'
          })
        }
      }).catch(error => {
        this.upDeptNoLoad = false
        console.log(error)
        this.$notify({
          title: '失败',
          message: '发送异常,变更失败',
          type: 'error'
        })
      })
    },
    upSampleOrderMagageDeptNo() {
      this.getDeptAllData()
      this.resetSampleOrderManageVO()
      if (this.multipleSelection.length === 0) {
        this.$message.warning('请先选择变更的数据.')
        return
      }
      if (this.multipleSelection.length !== 1) {
        this.$message.warning('一次只能操作一条,如需批量操作,请点击批量变更按钮通过文件进行变更.')
        return
      }
      console.log('this.multipleSelection=> ', this.multipleSelection[0])
      this.sampleOrderManageVO.id = this.multipleSelection[0].id
      this.sampleOrderManageVO.orderNo = this.multipleSelection[0].orderNo
      this.sampleOrderManageVO.beforeUpDeptNo = this.multipleSelection[0].deptNo
      this.upZlzsRcvDeptNoDialog = true
    },
    writeOff() {
      this.resetSampleOrderManageVO()
      if (this.multipleSelection.length > 1) {
        this.$message.error({
          title: '错误',
          message: '一次操作一条,不可同时操作多条'
        })
        return
      } else if (this.multipleSelection.length === 1) {
        this.sampleOrderManageVO.orderNo = this.multipleSelection[0].orderNo
        this.sampleOrderManageVO.modelNo = this.multipleSelection[0].modelNo
        this.sampleOrderManageVO.manageName = this.$store.getters.position.psnsmcId
      }
      this.WriteOffZlzsDialog = true
    },
    downLoadExcel() {
      this.$message.success('已开始下载，请耐心等待...')
      this.downLoadExcelBtuLoad = true
      downExcelForWriteOff().then(res => {
        console.log('展览展示品销账模板 res => ', res)
        this.downLoadExcelBtuLoad = false
        const fileName = '展览展示品销账模板.xlsx'
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
        this.downLoadExcelBtuLoad = false
        this.$message.warning('导出失败' + error)
      })
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
      this.uploadLoading = true
      if (this.file == null) {
        this.uploadLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        batchImportWriteOffData(formData).then(res => {
          this.uploadLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.initList()
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
          this.uploadLoading = false
          this.$message.error('销账失败:' + error)
        })
      }
    },
    sureWriteOff(sampleOrderManageVO) {
      this.sureWriteOffBtuLoad = true
      this.$refs[sampleOrderManageVO].validate((valid) => {
        if (valid) {
          zlzsOrderWriteOff(this.sampleOrderManageVO).then(res => {
            this.sureWriteOffBtuLoad = false
            if (res.success) {
              this.$notify({
                title: '成功',
                message: res.content,
                type: 'success'
              })
              this.WriteOffZlzsDialog = false
              this.initList()
              this.multipleSelection = []
            } else {
              this.$notify.error({
                title: '错误',
                message: res.message
              })
            }
          }).catch(error => {
            this.sureWriteOffBtuLoad = false
            console.log(error)
          })
        } else {
          this.sureWriteOffBtuLoad = false
        }
      })
    },
    resetSampleOrderManageVO() {
      this.sampleOrderManageVO = {
        id: '',
        deptNo: '',
        orderNo: '',
        modelNo: '',
        impQty: '',
        manager: '',
        manageName: ''
      }
    },
    // pushZlzsOrderBalance() {
    //   this.$confirm('此操作将进行盘点票发布, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     this.pushExportZsBalDataLoading = true
    //     this.$message.success('已开始发布，请耐心等待')
    //     pushZlzsSampleOrderManageForPdf().then(res => {
    //       this.pushExportZsBalDataLoading = false
    //       if (res.success) {
    //         this.$notify({
    //           title: '成功',
    //           message: res.content,
    //           type: 'success'
    //         })
    //       } else {
    //         this.$notify({
    //           title: '失败',
    //           message: res.message,
    //           type: 'error'
    //         })
    //       }
    //     }).catch(error => {
    //       this.pushExportZsBalDataLoading = false
    //       console.error(error)
    //       this.$message.error('发布失败' + error)
    //     })
    //   }).catch(() => {
    //     this.$message({
    //       type: 'info',
    //       message: '已取消'
    //     })
    //   })
    // },
    pushZlzsOrderBalance() {
      this.searchForm.inDateStart = ''
      this.searchForm.inDateEnd = ''
      this.pushExportZsBalDataLoading = true
      getZLZSExportTime().then(res => {
        if (res.success) {
          this.searchForm.inDateStart = res.content.split('=')[0]
          this.searchForm.inDateEnd = res.content.split('=')[1]
          if (this.isEmptyStr(this.searchForm.inDateStart) || this.isEmptyStr(this.searchForm.inDateEnd)) {
            this.$message.error('请先进行盘点票导出操作.')
          } else {
            const msg = '请核对盘点票的导出日期是否正确\n若不对请重新进行展示品盘点票导出操作\n日期范围: ' + this.searchForm.inDateStart + ' 至 ' + this.searchForm.inDateEnd + '\n是否继续?'
            this.$confirm(msg, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              pushZlzsSampleOrderManageForPdf(this.searchForm).then(res => {
                this.$message.success('正在发布展示品盘点票,请耐心等候...')
                this.pushExportZsBalDataLoading = false
                if (res.success) {
                  this.$notify({
                    title: '成功',
                    message: res.content,
                    type: 'success'
                  })
                } else {
                  this.$notify({
                    title: '失败',
                    message: res.message,
                    type: 'error'
                  })
                }
              })
            }).catch(() => {
              this.pushExportZsBalDataLoading = false
              this.$message({
                type: 'info',
                message: '已取消发布展示品盘点票'
              })
            })
          }
        } else {
          this.pushExportZsBalDataLoading = false
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.pushExportZsBalDataLoading = false
        console.log('获取导出盘点票的日期范围异常', error)
      })
    },
    isEmptyStr(s) {
      if (s === undefined || s === null || s === '') {
        return true
      }
      return false
    },
    exportZlzsOrderManage() {
      this.exportExcelForZLZS = true
      // this.$confirm('此操作将导出盘点, 是否继续?', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning'
      // }).then(() => {
      //   this.zlzsExportBal()
      // }).catch(() => {
      //   this.$message({
      //     type: 'info',
      //     message: '已取消'
      //   })
      // })
    },
    zlzsExportBal() {
      if (this.zlzsExportOrderBalTime.length !== 2) {
        this.$message({
          message: '请选择导出的结转日期范围.',
          type: 'warning'
        })
        return
      }
      this.sureExportBalLoading = true
      this.$message.success('已开始导出，请耐心等待...')
      this.searchForm.inDateStart = this.zlzsExportOrderBalTime[0]
      this.searchForm.inDateEnd = this.zlzsExportOrderBalTime[1]
      exportZlzsManageData(this.searchForm).then(res => {
        if (res.size === 0) {
          this.$message({
            duration: 4000,
            message: '根据您选择的结转时间范围未查出数据,请扩大时间范围.',
            type: 'error'
          })
          this.sureExportBalLoading = false
          return
        }
        const fileName = '样品管理-展览展示管理.zip'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.sureExportBalLoading = false
        this.exportExcelForZLZS = false
        this.zlzsOrderBal = false
      }).catch(error => {
        this.exportExcelForZLZS = false
        console.error(error)
        this.sureExportBalLoading = false
      })
    },
    // zlzsExportBal() {
    //   this.sureExportBalLoading = true
    //   this.$message.success('已开始导出，请耐心等待')
    //   exportZlzsManageData().then(res => {
    //     if (res.size === 0) {
    //       this.$message({
    //         duration: 4000,
    //         message: '根据您选择的结转时间范围未查出数据,请扩大时间范围.',
    //         type: 'error'
    //       })
    //       this.sureExportBalLoading = false
    //       return
    //     }
    //     const fileName = '样品管理-展览展示管理.zip'
    //     const blob = new Blob([res], { type: res.type })
    //     const link = document.createElement('a')
    //     link.style.display = 'none'
    //     link.href = window.URL.createObjectURL(blob)
    //     link.download = fileName
    //     document.body.appendChild(link)
    //     link.click()
    //     window.URL.revokeObjectURL(link.href)
    //     document.body.removeChild(link)
    //     this.sureExportBalLoading = false
    //     this.zlzsOrderBal = false
    //   }).catch(error => {
    //     console.error(error)
    //     this.sureExportBalLoading = false
    //   })
    // },
    exportData() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportSampleOrderManageData(this.searchForm).then(res => {
        const fileName = '展览展示品管理.xlsx'
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
    handleScopeChange(val) {
      console.log('所选部门编码val ==> ', val)
      this.searchForm.deptNos = val
    },
    // getDataCodesTree() {
    //   getDataCodesTree(this.deptClassCode).then(res => {
    //     if (!res.success) {
    //       this.$message.error('获取部门异常.')
    //       return
    //     }
    //     this.tempDeptNo = res.content.map(item => {
    //       return item.code
    //     })
    //   })
    // },
    searchTime() {
      if (this.selDateArray === null) {
        return
      }
      if (this.dayValSel === 'inDate') {
        this.searchForm.inDateStart = this.dayjs(this.selDateArray[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.inDateEnd = this.dayjs(this.selDateArray[1]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.creDateStart = ''
        this.searchForm.creDateEnd = ''
        this.searchForm.shipDateStart = ''
        this.searchForm.shipDateEnd = ''
      } else if (this.dayValSel === 'creDate') {
        this.searchForm.creDateStart = this.dayjs(this.selDateArray[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.creDateEnd = this.dayjs(this.selDateArray[1]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.inDateStart = ''
        this.searchForm.inDateEnd = ''
        this.searchForm.shipDateStart = ''
        this.searchForm.shipDateEnd = ''
      } else if (this.dayValSel === 'shipDate') {
        this.searchForm.shipDateStart = this.dayjs(this.selDateArray[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.shipDateEnd = this.dayjs(this.selDateArray[1]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.inDateStart = ''
        this.searchForm.inDateEnd = ''
        this.searchForm.creDateStart = ''
        this.searchForm.creDateEnd = ''
      } else {
        this.searchForm.inDateStart = ''
        this.searchForm.inDateEnd = ''
        this.searchForm.shipDateStart = ''
        this.searchForm.shipDateEnd = ''
        this.searchForm.creDateStart = ''
        this.searchForm.creDateEnd = ''
      }
    },
    changeSelDate() {
      if (this.selDateArray === null) {
        this.selDateArray = []
      }
      if (this.selDateArray.length === 0) {
        this.searchForm.inDateStart = ''
        this.searchForm.inDateEnd = ''
        this.searchForm.shipDateStart = ''
        this.searchForm.shipDateEnd = ''
        this.searchForm.creDateStart = ''
        this.searchForm.creDateEnd = ''
      }
    },
    resetSampleOrderManageDoForm() {
      this.sampleOrderManageDoForm = {
        id: '',
        modelNo: '',
        orderNo: '',
        deptNo: '',
        impQty: '',
        manager: '',
        shipDate: '',
        outTime: '',
        status: '',
        remark: '',
        updateUser: ''
      }
    },
    downLoadHistorySampleOrderManageExcel() {
      this.downLoadHistoryBtuLoad = true
      this.$message.success('已开始下载，请耐心等待...')
      downLoadHistorySampleOrderManageExcel().then(res => {
        this.downLoadHistoryBtuLoad = false
        const fileName = '批量导入历史盘点票数据模板.xlsx'
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
        this.downLoadHistoryBtuLoad = false
        this.$message.warning('导出失败' + error)
      })
    }
  }
}
</script>
<style scoped>
.zlzsExportOrderBalClass {
    width: 900px;
    margin-left: 20%;
  }
  .WriteOffZlzsDialog{
    width: 85%;
  }
</style>
