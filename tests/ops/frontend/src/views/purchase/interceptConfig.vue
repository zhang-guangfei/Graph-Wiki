<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <el-form ref="form" :model="form" :inline="true">
          <el-form-item label="关键字:">
            <!-- <el-input v-model="form.rulekey" placeholder="请输入关键字" clearable size="mini" @keyup.enter.native="getList"/> -->
            <el-input
              :rows="3"
              v-model="form.rulekey"
              style="width:130px"
              autocomplete="off"
              type="textarea"
              placeholder="回车换行进行批量查询"
              size="mini"
              clearable
            />
          </el-form-item>
          <el-form-item label="担当:">
            <el-input v-model="form.operator" placeholder="请输入录入担当" clearable size="mini" @keyup.enter.native="getList"/>
          </el-form-item>
          <el-form-item label="匹配类型: " >
            <el-select v-model="form.typeid" placeholder="请选择匹配类型" clearable size="mini">
              <el-option
                v-for="item in typeIdSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="是否有效: " >
            <el-select v-model="form.enable" placeholder="请选择匹配类型" clearable size="mini">
              <el-option
                v-for="item in enableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="备注:">
            <el-input v-model="form.remark" placeholder="请输入备注" clearable size="mini" @keyup.enter.native="getList" />
          </el-form-item> -->
          <el-form-item label="拦截原因:">
            <el-input v-model="form.reason" placeholder="请输入拦截原因" clearable size="mini" @keyup.enter.native="getList" />
          </el-form-item>
          <el-form-item>
            <span class="operation-button">
              <el-button v-permission="['392336']" class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList()">查询</el-button>
              <el-button v-permission="['306601']" class="filter-item" type="primary" size="mini" icon="el-icon-circle-plus" @click="addChange()">新增</el-button>
              <el-button class="filter-item" type="primary" size="mini" icon="el-icon-top" @click="openSonItemExport">导出</el-button>
              <el-button class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="impData()">导入</el-button>
            </span>
          </el-form-item>
        </el-form>
      </el-card>
      <el-button :disabled="selectList.length <1" size="mini" type="primary" icon="el-icon-edit-outline" style="margin-top:5px;margin-bottom:5px;float:left;" @click="editBatch()">批量编辑</el-button>
      <el-button :disabled="selectList.length <1" size="mini" type="danger" icon="el-icon-s-claim" style="margin-top:5px;margin-right:5px;margin-bottom:5px;float:left;" @click="deleteAllData(row)">批量删除</el-button>
      <div style="margin-top:20px;">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :row-style="rowStyle"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
          :cell-style="{padding: '2px 3px'}"
          element-loading-text="拼命加载中"
          size="mini"
          border
          fit
          highlight-current-row
          style="width:100%"
          @selection-change="selectChangeEvent"
          @select-all="selectAllEvent">
          <el-table-column
            type="selection"
            width="55"/>
          <el-table-column label="序号" align="center" fixed="left" min-width="50">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="匹配类型" align="center" min-width="100">
            <template slot-scope="scope">
              <span>{{ scope.row.typeid | interceptFormat }}</span>
            </template>
          </el-table-column>
          <el-table-column label="关键字" show-overflow-tooltip align="center" min-width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.rulekey }}</span>
            </template>
          </el-table-column>
          <el-table-column label="自定义客户" show-overflow-tooltip align="center" min-width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.ruleKey1 }}</span>
            </template>
          </el-table-column>
          <el-table-column label="自定义数量" show-overflow-tooltip align="center" min-width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.ruleKey2 }}</span>
            </template>
          </el-table-column>
          <!-- <el-table-column label="备注" show-overflow-tooltip align="center" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.remark }}</span>
            </template>
          </el-table-column> -->
          <el-table-column show-overflow-tooltip label="拦截原因" min-width="150" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.reason }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="是否自动出库存" align="center" min-width="100">
            <template slot-scope="scope">
              <span>{{ showDictValue(scope.row.autoStockOut,autoStockOutOption) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="是否生效" align="center" min-width="100">
            <template slot-scope="scope">
              <span>{{ showDictValue(scope.row.enable,enableOption) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="追加担当" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.operator }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="追加日期" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.updatetime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="140">
            <template slot-scope="scope">
              <el-button v-permission="['306601']" type="primary" size="mini" @click="editChange(scope.row)">修改</el-button>
              <el-button v-permission="['306601']" type="primary" size="mini" @click="deleteChange(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog :visible.sync="editFormVisible" title="修改数据" align="center" width="40%">
      <el-card>
        <el-form ref="updateForm" :rules="rules" :model="updateRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="关键字" prop="rulekey">
            <el-input v-model="updateRequestData.rulekey" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="匹配类别" prop="typeid">
            <el-select v-model="updateRequestData.typeid" placeholder="请选择匹配类别" style="margin-left:20px;margin-bottom:5px;width:300px" clearable @change="typeChange(updateRequestData)">
              <el-option
                v-for="item in typeIdSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item v-if="zdyShow" label="客户" prop="ruleKey1">
            <el-input v-model="updateRequestData.ruleKey1" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)"/>
          </el-form-item>
          <el-form-item v-if="zdyShow" label="数量（>=）" prop="ruleKey2">
            <el-input v-model="updateRequestData.ruleKey2" clearable type="number" onkeyup="value=value.replace(/[^\d]/g,'')" style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)" />
          </el-form-item>
          <el-form-item label="自动出库存" prop="autoStockOut">
            <el-select v-model="updateRequestData.autoStockOut" placeholder="请选择是否生效" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @change="change($event)">
              <el-option
                v-for="item in autoStockOutOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="是否生效" prop="enable">
            <el-select v-model="updateRequestData.enable" placeholder="请选择是否生效" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in enableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="默认请购状态" prop="defaultaction">
            <el-select v-model="updateRequestData.defaultaction" placeholder="请选择默认请购状态" disabled clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.statecodeOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="追加原因" prop="reason">
            <el-input v-model="updateRequestData.reason" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click = "editConfirm()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="editFormVisibleBatch" title="批量修改数据" align="center" width="40%">
      <el-card>
        <el-form ref="updateForm" :rules="rules" :model="updateRequestDataBatch" label-position="right" label-width="120px" style="width: 400px;">
          <!-- <el-form-item label="关键字" prop="rulekey">
            <el-input v-model="updateRequestDataBatch.rulekey" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="匹配类别" prop="typeid">
            <el-select v-model="updateRequestDataBatch.typeid" placeholder="请选择匹配类别" style="margin-left:20px;margin-bottom:5px;width:300px" clearable >
              <el-option
                v-for="item in typeIdSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="限制条件" prop="remark">
            <el-input v-model="updateRequestDataBatch.remark" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item> -->
          <el-form-item label="是否生效" prop="enable">
            <el-select v-model="updateRequestDataBatch.enable" placeholder="请选择是否生效" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in enableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="追加原因" prop="reason">
            <el-input v-model="updateRequestDataBatch.reason" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="editFormVisibleBatch = false">取消</el-button>
        <el-button type="primary" @click = "editConfirmBatch()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="addFormVisible" title="新增拦截清单" align="center" width="40%">
      <el-card>
        <el-form ref="addForm" :rules="rules" :model="addRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="关键字" prop="rulekey">
            <el-input v-model="addRequestData.rulekey" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="匹配类别" prop="typeid">
            <el-select v-model="addRequestData.typeid" placeholder="请选择匹配类别" style="margin-left:20px;margin-bottom:5px;width:300px" clearable @change="typeChange(addRequestData)">
              <el-option
                v-for="item in typeIdSelect"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item v-if="zdyShow" label="客户" prop="ruleKey1">
            <el-input v-model="addRequestData.ruleKey1" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)" />
          </el-form-item>
          <el-form-item v-if="zdyShow" label="数量（>=）" prop="ruleKey2">
            <el-input v-model="addRequestData.ruleKey2" clearable type="number" onkeyup="value=value.replace(/[^\d]/g,'')" style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)" />
          </el-form-item>
          <el-form-item label="自动出库存" prop="autoStockOut">
            <el-select v-model="addRequestData.autoStockOut" placeholder="请选择是否生效" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @change="change($event)">
              <el-option
                v-for="item in autoStockOutOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="是否生效" prop="enable">
            <el-select v-model="addRequestData.enable" placeholder="请选择是否生效" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in enableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="默认请购状态" prop="defaultaction">
            <el-select v-model="addRequestData.defaultaction" placeholder="请选择默认请购状态" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.statecodeOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="限制条件" prop="remark">
            <el-input v-model="addRequestData.remark" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item> -->
          <el-form-item label="追加原因" prop="reason">
            <el-input v-model="addRequestData.reason" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="addFormVisible = false">取消</el-button>
        <el-button type="primary" @click = "addData()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="批量导入"
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
        <el-button size="mini" @click="closeClickDialog()">取 消</el-button>
        <el-button :loading="uploadLoading" size="mini" type="primary" @click="impinterceptData">导 入</el-button>
      </div>
    </el-dialog>
    <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getList()" />
    <exportExcel ref="exportExcelVue"/>
  </div>
</template>
<script>
import { findList, editData, editDataBatch, addDatas, deleteData, deleteDataBatch } from '@/api/interceptConfig'
import Pagination from '@/components/Pagination'
import { downloadTemplate, importIntercepterData } from '@/api/product'
import { getDataCodesTree } from '@/api/common/dict'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'InterceptConfig',
  components: { Pagination, exportExcel },
  data() {
    return {
      zdyShow: false,
      actionUrl: '',
      file: '',
      DictData: {
        type: [],
        status: [],
        transtype: [],
        statecodeOption: []
      },
      selectList: [],
      tableData: [],
      editFormVisible: false,
      addFormVisible: false,
      listLoading: false,
      uploadFileDialogVisible: false,
      uploadLoading: false,
      updateRequestData: {},
      updateRequestDataBatch: {},
      editFormVisibleBatch: false,
      addRequestData: {
        rulekey: '',
        typeid: '',
        enable: '',
        defaultaction: '',
        reason: ''
      },
      updateRequestDataDelivery: {},
      total: 0,
      queryCondition: {
        condition: {
          enable: true,
          rulekeys: [],
          operator: '',
          reason: '',
          typeid: ''
        }
      },
      page: {
        pageNumber: 1,
        pageSize: 20
      },
      form: {
        enable: true,
        rulekey: '',
        operator: '',
        reason: '',
        typeid: ''
      },
      rules: {
        rulekey: [{ required: true, message: '请输入关键字', trigger: 'blur' }],
        typeid: [{ required: true, message: '请选择匹配模式', trigger: 'blur' }],
        enable: [{ required: true, message: '请选择是否生效', trigger: 'blur' }],
        defaultaction: [{ required: true, message: '请选择默认请购状态', trigger: 'blur' }],
        // remark: [{ required: true, message: '请输入备注信息', trigger: 'blur' }],
        reason: [{ required: true, message: '请输入追加原因', trigger: 'blur' }]
      },
      ruleDelivery: {
        hopedeliverydate: [{ required: true, message: '请选择交货期', trigger: 'blur' }]
      },
      // 拦截类别匹配
      typeIdSelect: [
        {
          value: '0',
          label: '按型号匹配'
        }, {
          value: '1',
          label: '按客户匹配'
        }, {
          value: '2',
          label: '自定义匹配'
        }
      ],
      typeidOption: [
        {
          value: '1',
          label: '模糊匹配'
        }, {
          value: '0',
          label: '完全匹配'
        }
      ],
      enableOption: [
        {
          value: true,
          label: '有效'
        }, {
          value: false,
          label: '失效'
        }
      ],
      autoStockOutOption: [
        {
          value: true,
          label: '是'
        }, {
          value: false,
          label: '否'
        }
      ]
    }
  },
  created() {
    this.initData()
    this.getList()
  },
  methods: {
    change() {
      this.$forceUpdate()
    },
    typeChange(data) {
      this.zdyShow = data.typeid === '2'
    },
    closeClickDialog() {
      this.uploadFileDialogVisible = false
      this.closeClick()
    },
    resetForm() {
      this.zdyShow = false
      this.addRequestData.rulekey = ''
      this.addRequestData.ruleKey1 = ''
      this.addRequestData.ruleKey2 = ''
      this.addRequestData.typeid = ''
      this.addRequestData.autoStockOut = ''
      this.addRequestData.enable = ''
      this.addRequestData.defaultaction = ''
      this.addRequestData.reason = ''
    },
    impinterceptData() {
      this.uploadLoading = true
      if (this.file == null) {
        this.uploadLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('optUser', this.$store.getters.position.psnsmcId)
        importIntercepterData(formData).then(res => {
          this.uploadLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.getList()
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
          this.$message.error('导入失败:' + error)
        })
      }
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    downLoadExcel() {
      this.$message.success('已开始下载，请耐心等待...')
      downloadTemplate().then(res => {
        const fileName = '模板.xlsx'
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
    impData() {
      this.uploadFileDialogVisible = true
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    // 处理状态
    initData() {
      getDataCodesTree('2031').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.type.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('2033').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.status.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // bug 12850 默认请购状态修改为拦截，不进行自动删单
      getDataCodesTree('2083').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.statecodeOption.push({ value: dict.code, label: dict.codeName })
          })
        }
      })
    },
    // 列表复选框改变事件
    selectChangeEvent(records) {
      this.selectList = []
      records.forEach(element => {
        element.operator = this.$store.getters.position.psnsmcId
        this.selectList.push(element)
      })
    },
    // 列表全选事件
    selectAllEvent(records) {
      this.selectList = []
      records.forEach(element => {
        element.operator = this.$store.getters.position.psnsmcId
        this.selectList.push(element)
      })
    },
    rowStyle({ row, rowIndex }) {
      return 'height:10px'
    },
    // 查询所有数据
    getList() {
      this.queryCondition = {
        condition: {
          enable: true,
          rulekeys: [],
          operator: '',
          reason: '',
          typeid: ''
        }
      }
      this.listLoading = true
      if (!this.isEmpty(this.form.typeid)) {
        this.queryCondition.condition.typeid = this.form.typeid
      }
      if (!this.isEmpty(this.form.rulekey)) {
        this.queryCondition.condition.rulekeys = this.form.rulekey.split('\n')
      }
      if (!this.isEmpty(this.form.operator)) {
        this.queryCondition.condition.operator = this.form.operator
      }
      if (!this.isEmpty(this.form.reason)) {
        this.queryCondition.condition.reason = this.form.reason
      }
      this.queryCondition.condition.enable = this.form.enable
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = this.page.pageSize
      findList(this.queryCondition).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 详情跳转方法
    toDetail(value) {
      this.$router.push({
        path: '/purchase/purchaseDeail',
        query: { id: value }
      })
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    addChange() {
      this.resetForm()
      this.addFormVisible = true
    },
    // 新增拦截规则
    addData() {
      this.$refs['addForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将新增一条拦截数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.addRequestData.operator = this.$store.getters.info.userId
            addDatas(this.addRequestData).then(() => {
              this.addFormVisible = false
              this.$notify({
                title: '成功',
                message: '新增成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            }).catch(error => {
              this.listLoading = false
              console.log(error)
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消该操作'
            })
          })
        }
      })
    },
    // bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
    deleteAllData() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要处理的数据'
        })
        return
      }
      this.$confirm('将删除勾选的拦截配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDataBatch(this.selectList).then(res => {
          if (res.success === true) {
            this.$notify({
              title: '成功',
              message: '批量删除成功',
              type: 'success',
              duration: 2000
            })
            this.getList()
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: res.message,
              type: 'error',
              duration: 0
            })
            this.listLoading = false
          }
        }).catch(error => {
          this.listLoading = false
          console.info(error)
          this.$message.error(error.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消该操作'
        })
      })
    },
    deleteChange(row) {
      this.$confirm('将删除该拦截配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteData(row).then(() => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
        }).catch(error => {
          this.listLoading = false
          console.log(error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消该操作'
        })
      })
    },
    // 批量编辑操作
    editBatch() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要批量修改的数据'
        })
        return
      }
      this.editFormVisibleBatch = true
      this.updateRequestDataBatch = Object.assign({}, this.selectList[0])
    },
    editChange(row) {
      this.editFormVisible = true
      this.updateRequestData = Object.assign({}, row)
      this.zdyShow = this.updateRequestData.typeid === '2'
    },
    // bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
    editConfirmBatch() {
      this.selectList.forEach(element => {
        element.enable = this.updateRequestDataBatch.enable
        element.reason = this.updateRequestDataBatch.reason
      })
      editDataBatch(this.selectList).then(res => {
        if (res.success === true) {
          this.editFormVisibleBatch = false
          this.$notify({
            title: '成功',
            message: '修改成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 修改确认
    editConfirm() {
      this.$refs['updateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将变更数据, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.updateRequestData.operator = this.$store.getters.position.psnsmcId
            editData(this.updateRequestData).then(() => {
              this.editFormVisible = false
              this.$notify({
                title: '成功',
                message: '修改成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            }).catch(error => {
              this.listLoading = false
              console.log(error)
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消该操作'
            })
          })
        }
      })
    },
    // bug 10559 拦截配置 增加筛选，批量修改，删除，导出功能 B91717
    openSonItemExport() {
      const tableColumn = [
        { field: 'typeid', title: '匹配类型' },
        { field: 'rulekey', title: '关键字' },
        { field: 'ruleKey1', title: '自定义客户' },
        { field: 'ruleKey2', title: '自定义数量' },
        { field: 'autoStockOut', title: '是否自动出库存' },
        // { field: 'remark', title: '备注' },
        { field: 'reason', title: '拦截原因' },
        { field: 'enable', title: '是否生效' },
        { field: 'operator', title: '追加担当' },
        { field: 'updatetime', title: '追加日期' },
        { field: 'defaultaction', title: '默认请购状态' }
      ]
      this.queryCondition = {
        condition: {
          enable: true,
          rulekeys: [],
          operator: '',
          reason: '',
          typeid: ''
        }
      }
      if (!this.isEmpty(this.form.typeid)) {
        this.queryCondition.condition.typeid = this.form.typeid
      }
      if (!this.isEmpty(this.form.rulekey)) {
        this.queryCondition.condition.rulekeys = this.form.rulekey.split('\n')
      }
      if (!this.isEmpty(this.form.operator)) {
        this.queryCondition.condition.operator = this.form.operator
      }
      if (!this.isEmpty(this.form.reason)) {
        this.queryCondition.condition.reason = this.form.reason
      }
      this.queryCondition.condition.enable = this.form.enable
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = 10000
      findList(this.queryCondition).then(res => {
        if (res.success) {
          for (let i = 0; i < res.data.list.length; i++) {
            res.data.list[i].updatetime = this.formatDateStr(res.data.list[i].updatetime)
            if (res.data.list[i].typeid === '0') {
              res.data.list[i].typeid = '按型号匹配'
            } else if (res.data.list[i].typeid === '1') {
              res.data.list[i].typeid = '按客户匹配'
            } else if (res.data.list[i].typeid === '2') {
              res.data.list[i].typeid = '自定义匹配'
            }
            if (res.data.list[i].defaultaction === '4') {
              res.data.list[i].defaultaction = '业务拦截'
            }
            if (res.data.list[i].enable === true) {
              res.data.list[i].enable = '有效'
            } else {
              res.data.list[i].enable = '无效'
            }
            if (res.data.list[i].autoStockOut === true) {
              res.data.list[i].autoStockOut = '是'
            } else {
              res.data.list[i].autoStockOut = '否'
            }
          }
          this.$refs.exportExcelVue.initExportExcel(res.data.list, tableColumn)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // 动态字典翻译
    showDictValue(val, dictArr) {
      if (val !== '') {
        const item = dictArr.find(dict => dict.value === val)
        return item ? item.label : val
      }
    },
    formatDateStr(dateString) {
      const date = new Date(dateString)
      const year = date.getFullYear()
      const month = String(date.getMonth() + 1).padStart(2, '0')
      const day = String(date.getDate()).padStart(2, '0')
      const hours = String(date.getHours()).padStart(2, '0')
      const minutes = String(date.getMinutes()).padStart(2, '0')
      const seconds = String(date.getSeconds()).padStart(2, '0')

      return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`
    }
  }
}
</script>
<style scoped>
.filter-container {
  padding-bottom: 10px;
}
.filter-container .filter-item {
    display: inline-block;
    vertical-align: middle;
  }
.el-form-item {
   margin-bottom: 4px;
}
.el-button--primary {
  color: #337AB7;
  background-color: #fff;
  border:1px solid #337AB7;
}
.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #337AB7;
  color: #fff;
}
</style>

