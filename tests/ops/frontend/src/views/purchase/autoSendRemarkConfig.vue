<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <el-form ref="form" :model="form" :inline="true">
          <el-form-item label="配置型号:">
            <el-input v-model="form.modelno" placeholder="请输入配置型号" clearable size="mini" @keyup.enter.native="getList"/>
          </el-form-item>
          <el-form-item label="追加备注:">
            <el-input v-model="form.remark" placeholder="请输入追加备注" clearable size="mini" @keyup.enter.native="getList" />
          </el-form-item>
          <el-form-item label="担当:">
            <el-input v-model="form.insertUser" placeholder="请输入录入担当" clearable size="mini" style="width:130px" @keyup.enter.native="getList"/>
          </el-form-item>
          <el-form-item label="营业所">
            <department style="min-width: 200px;margin-left: 1px" @handleScopeChange="handleScopeChange" />
          </el-form-item>
          <el-form-item label="供应商分类:">
            <el-select v-model="form.supplierClass" placeholder="请选择供应商分类" style="width:150px" clearable size="mini">
              <el-option
                v-for="item in DictData.suppily"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="删除状态">
            <el-select v-model="form.deleted" placeholder="请选择删除状态" style="width:130px" clearable size="mini">
              <el-option
                v-for="item in enableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item>
            <span class="operation-button">
              <el-button class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList()">查询</el-button>
              <!-- <el-button class="filter-item" type="primary" size="mini" icon="el-icon-circle-plus" @click="addChange()">新增</el-button>
              <el-button class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="openSonItemExport">导出</el-button>
              <el-button class="filter-item" type="primary" size="mini" icon="el-icon-upload2" @click="impdataBatch()">导入</el-button> -->
            </span>
          </el-form-item>
        </el-form>
      </el-card>
      <el-button size="mini" icon="el-icon-circle-plus" plain type="primary" style="margin-top:5px;margin-right:5px;margin-bottom:5px;float:left;" @click="addChange()">新增</el-button>
      <el-button size="mini" icon="el-icon-upload2" plain type="warning" style="margin-top:5px;margin-right:5px;margin-bottom:5px;float:left;" @click="impdataBatch()">导入</el-button>
      <el-button size="mini" plain type="info" icon="el-icon-download" style="margin-top:5px;margin-right:5px;margin-bottom:5px;float:left;" @click="openSonItemExport">导出</el-button>
      <el-button :disabled="selectList.length <1" size="mini" plain type="danger" icon="el-icon-delete" style="margin-top:5px;margin-right:5px;margin-bottom:5px;float:left;" @click="deleteAllData()">批量删除</el-button>
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
          <el-table-column :formatter="deptFormatter" prop="deptno" show-overflow-tooltip label="营业所" min-width="100" align="center"/>
          <el-table-column label="配置型号" show-overflow-tooltip align="center" min-width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.modelno }}</span>
            </template>
          </el-table-column>
          <el-table-column label="备注信息" show-overflow-tooltip align="center" min-width="150">
            <template slot-scope="scope">
              <span>{{ scope.row.remark }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="suppilyFormatter" prop="supplierClass" show-overflow-tooltip label="供应商分类" min-width="110" align="center"/>
          <el-table-column label="客户代码" show-overflow-tooltip align="center" min-width="100">
            <template slot-scope="scope">
              <span>{{ scope.row.customerNo }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="追加担当" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.insertUser }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="追加日期" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.insertTime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="更新人" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.updateUser }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="更新时间" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.updateTime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="isDeletedFormatter" prop="isDeleted" show-overflow-tooltip label="删除状态" align="center" min-width="100"/>
          <!-- <template slot-scope="scope">
              <span>{{ showDictValue(scope.row.isDeleted,enableOption) }}</span>
            </template>
          </el-table-column> -->
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" min-width="140">
            <template slot-scope="scope">
              <el-button v-permission="['306601']" type="primary" icon="el-icon-edit" size="mini" @click="editChange(scope.row)">修改</el-button>
              <el-button v-permission="['306601']" v-if="!scope.row.isDeleted" type="primary" icon="el-icon-delete-solid" size="mini" @click="deleteChange(scope.row)">删除</el-button>
              <el-button v-permission="['306601']" v-if="scope.row.isDeleted" type="primary" icon="el-icon-refresh" size="mini" @click="restoreChange(scope.row)">恢复</el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog :visible.sync="editFormVisible" :close-on-click-modal="false" title="修改发单备注配置" align="center" width="40%">
      <el-card>
        <el-form ref="updateForm" :rules="rules" :model="updateRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="配置型号" prop="modelno">
            <el-input v-model="updateRequestData.modelno" placeholder="请输入配置型号" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="追加备注" prop="remark">
            <el-input v-model="updateRequestData.remark" placeholder="请输入追加备注" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item ref="deptnoEdit" label="营业所" prop="deptno">
            <!-- <department style="margin-left:20px;margin-bottom:5px;width:300px" @handleScopeChange="deptUpdateChange" /> -->
            <el-input v-model="updateRequestData.deptno" :formatter="deptFormatter" placeholder="请输入营业名称" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="供应商分类" prop="supplierClass">
            <el-select v-model="updateRequestData.supplierClass" placeholder="请选择供应商分类" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.suppily"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="客户代码" prop="customerNo">
            <el-input v-model="updateRequestData.customerNo" placeholder="请输入客户代码" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <!-- <el-form-item v-if="updateRequestData.isDeleted" label="删除状态" prop="isDeleted">
            <el-select v-model="updateRequestData.isDeleted" placeholder="请选择删除状态" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in enableOption"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item> -->
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="editFormVisible = false">取消</el-button>
        <el-button type="primary" @click = "editConfirm()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="addFormVisible" :close-on-click-modal="false" title="新增发单备注配置" align="center" width="40%">
      <el-card>
        <el-form ref="addForm" :rules="rules" :model="addRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="配置型号" prop="modelno">
            <el-input v-model="addRequestData.modelno" placeholder="请输入配置型号" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="追加备注" prop="remark">
            <el-input v-model="addRequestData.remark" placeholder="请输入追加备注" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="营业所" prop="deptno">
            <!-- <department style="margin-left:20px;margin-bottom:5px;width:300px" @handleScopeChange="deptInsertChange"/> -->
            <el-input v-model="addRequestData.deptno" :formatter="deptFormatter" placeholder="请输入营业名称" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="供应商分类" prop="supplierClass">
            <el-select v-model="addRequestData.supplierClass" placeholder="请选择供应商分类" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.suppily"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="客户代码" prop="customerNo">
            <el-input v-model="addRequestData.customerNo" placeholder="请输入客户代码" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
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
      :visible.sync="importDataDialog"
      :before-close="closeClick3"
      title="批量导入发单配置数据"
      width="400px"
      append-to-body
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
        <el-button size="small" type="success" @click="downloadTemplate('../../../static/template/downLoadTemplate/采购发单配置模板.xlsx')">下载模板</el-button>
        <el-button :loading="importDataLoad" size="mini" type="primary" @click="importRemarkData()">导 入</el-button>
        <el-button size="mini" @click="importDataDialog = false">取 消</el-button>
      </div>
    </el-dialog>
    <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getList()" />
    <exportExcel ref="exportExcelVue"/>
    <!-- <Department ref="departmentVue" /> -->
  </div>
</template>
<script>
import { findList, editData, addDatas, deleteData, deleteDataBatch, importBatchData, getDepartment, restoreData } from '@/api/po/poAutoSendRemark'
import Department from '@/components/Department'
// import { findDeptDict } from '@/api/warehouseManage'
import Pagination from '@/components/Pagination'
import exportExcel from '@/components/ExportExcel/index'
import { getDataCodesTree } from '@/api/common/dict'
export default {
  name: 'AutoSendRemarkConfig',
  components: { Pagination, exportExcel, Department },
  data() {
    return {
      DictData: {
        type: [],
        status: [],
        transtype: [],
        department: [],
        suppily: []
      },
      optionsValue: [],
      selectList: [],
      tableData: [],
      templateData: [],
      editFormVisible: false,
      addFormVisible: false,
      importDataDialog: false,
      importDataLoad: false,
      file: null,
      actionUrl: '',
      listLoading: false,
      updateRequestData: {},
      updateRequestDataBatch: {},
      editFormVisibleBatch: false,
      addRequestData: {},
      total: 0,
      queryCondition: {},
      page: {
        pageNumber: 1,
        pageSize: 20
      },
      form: {
        deleted: false
      },
      rules: {
        // modelno: [{ required: true, message: '请输入配置型号', trigger: 'blur' }],
        // deptno: [{ required: true, message: '请选择所属部门', trigger: 'blur' }],
        remark: [{ required: true, message: '请输入备注信息', trigger: 'blur' }]
      },
      // 删除状态字典
      enableOption: [
        {
          value: false,
          label: '正常'
        },
        {
          value: true,
          label: '删除'
        }
      ]
    }
  },
  created() {
    this.initData()
    this.getList()
  },
  methods: {
    handleScopeChange(val) {
      console.log('所选营业所=>', val)
      this.form.deptNos = val
    },
    // 营业所字典格式化
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    suppilyFormatter(row, column, cellValue) {
      const item = this.DictData.suppily.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    isDeletedFormatter(row, column, cellValue) {
      const item = this.enableOption.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // 加载字典转义
    initData() {
      getDataCodesTree('2084').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.suppily.push({ value: dict.code, label: dict.codeName })
          })
        }
      })
      // 营业所代码翻译为中文
      getDepartment().then(result => {
        result.forEach(dict => {
          this.DictData.department.push({ code: dict.id, desc: dict.name })
        })
      })
    },
    // 列表复选框改变事件
    selectChangeEvent(records) {
      this.selectList = []
      records.forEach(element => {
        // element.updateUser = this.$store.getters.position.psnsmcId
        this.selectList.push(element)
      })
    },
    // 列表全选事件
    selectAllEvent(records) {
      this.selectList = []
      records.forEach(element => {
        // element.updateUser = this.$store.getters.position.psnsmcId
        this.selectList.push(element)
      })
    },
    rowStyle({ row, rowIndex }) {
      return 'height:10px'
    },
    // 查询所有数据
    getList() {
      this.listLoading = true
      if (!this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.remark) || !this.isEmpty(this.form.insertUser) ||
      !this.isEmpty(this.form.deptNos) || !this.isEmpty(this.form.supplierClass) || !this.isEmpty(this.form.deleted)) {
        this.queryCondition.condition = this.form
      }
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
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    addChange() {
      // 每次写入时，需要重新加载部门选择框
      // this.$refs.departmentVue.handleDownload()
      // this.addRequestData = {}
      this.addFormVisible = true
    },
    // 新增
    addData() {
      this.$refs['addForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将新增一条发单备注配置, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.addRequestData.insertUser = this.$store.getters.info.userId
            if (!this.isEmpty(this.addRequestData.deptno)) {
              const deptcode = this.deptCodeTrans(this.addRequestData.deptno)
              if (this.isEmpty(deptcode) || deptcode === this.addRequestData.deptno) {
                this.$message({
                  dangerouslyUseHTMLString: true,
                  showClose: true,
                  message: '输入的营业所未找到匹配的 营业所代码，请检查后输入',
                  type: 'error',
                  duration: 4000
                })
                return
              }
              this.addRequestData.deptno = deptcode
            }
            addDatas(this.addRequestData).then(res => {
              if (res.success) {
                this.$notify({
                  title: '成功',
                  message: '新增成功',
                  type: 'success',
                  duration: 4000
                })
                this.addRequestData = {}
                this.getList()
              } else {
                this.$message({
                  dangerouslyUseHTMLString: true,
                  showClose: true,
                  message: res.message,
                  type: 'error',
                  duration: 4000
                })
              }
              this.addFormVisible = false
            }).catch(error => {
              this.addFormVisible = false
              this.$message.error(error.message)
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
    deleteAllData() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要处理的数据'
        })
        return
      }
      this.$confirm('将删除勾选的发单配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.selectList.forEach((item) => {
          item.updateUser = this.$store.getters.position.psnsmcId
        })
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
      this.$confirm('将删除该发单配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        row.updateUser = this.$store.getters.position.psnsmcId
        deleteData(row).then(res => {
          if (res.success) {
            this.$notify({
              title: '成功',
              message: '删除成功',
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
          console.log(error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消该操作'
        })
      })
    },
    restoreChange(row) {
      this.$confirm('将恢复该发单配置, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        row.updateUser = this.$store.getters.position.psnsmcId
        restoreData(row).then(res => {
          if (res.success) {
            this.$notify({
              title: '成功',
              message: '恢复成功',
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
      // this.resert()
      row.deptno = this.deptnoTrans(row.deptno)
      this.updateRequestData = Object.assign({}, row)
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
            this.updateRequestData.updateUser = this.$store.getters.position.psnsmcId
            if (!this.isEmpty(this.updateRequestData.deptno)) {
              const deptcode = this.deptCodeTrans(this.updateRequestData.deptno)
              if (this.isEmpty(deptcode) || deptcode === this.updateRequestData.deptno) {
                this.$message({
                  dangerouslyUseHTMLString: true,
                  showClose: true,
                  message: '输入的营业所未找到匹配的 营业所代码，请检查后输入',
                  type: 'error',
                  duration: 4000
                })
                return
              }
              this.updateRequestData.deptno = deptcode
            }
            editData(this.updateRequestData).then(res => {
              if (res.success) {
                this.editFormVisible = false
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
    openSonItemExport() {
      const tableColumn = [
        { field: 'modelno', title: '配置型号' },
        { field: 'deptno', title: '营业所' },
        { field: 'remark', title: '追加备注' },
        { field: 'supplierClass', title: '供应商分类' },
        { field: 'customerNo', title: '客户代码' },
        { field: 'insertUser', title: '追加担当' },
        { field: 'insertTime', title: '追加日期' },
        { field: 'updateUser', title: '更新人' },
        { field: 'updateTime', title: '更新时间' }
      ]
      if (!this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.remark) || !this.isEmpty(this.form.insertUser) ||
      !this.isEmpty(this.form.deptNos) || !this.isEmpty(this.form.supplierClass) || !this.isEmpty(this.form.deleted
      )) {
        this.queryCondition.condition = this.form
      }
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = 1000
      findList(this.queryCondition).then(res => {
        if (res.success) {
          res.data.list.forEach((item) => {
            item.deptno = this.deptnoTrans(item.deptno)
            item.insertTime = this.dateFormatter(item.insertTime)
            item.updateTime = this.dateFormatter(item.updateTime)
          })
          this.$refs.exportExcelVue.initExportExcel(res.data.list, tableColumn)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // 日期格式转换
    dateFormatter(val) {
      if (val === null) {
        return
      }
      var value = new Date(val)
      var year = value.getFullYear()
      var padDate = function(va) {
        va = va < 10 ? '0' + va : va
        return va
      }
      var month = padDate(value.getMonth() + 1)
      var day = padDate(value.getDate())
      var hour = padDate(value.getHours())
      var minutes = padDate(value.getMinutes())
      var seconds = padDate(value.getSeconds())
      return year + '-' + month + '-' + day + ' ' + hour + ':' + minutes + ':' + seconds
    },
    // bug 10558 导出营业所别翻译成中文
    deptnoTrans(val) {
      const item = this.DictData.department.find(dict => dict.code === val)
      return item ? item.desc : val
    },
    deptCodeTrans(val) {
      const item = this.DictData.department.find(dict => dict.desc === val)
      return item ? item.code : val
    },
    // 动态字典翻译
    showDictValue(val, dictArr) {
      if (val !== '') {
        const item = dictArr.find(dict => dict.value === val)
        return item ? item.label : val
      }
    },
    closeClick3() {
      this.file = null
      this.importDataDialog = false
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    impdataBatch() {
      this.importDataDialog = true
      this.file = null
    },
    importRemarkData() {
      this.importDataLoad = true
      if (this.file == null) {
        this.importDataLoad = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        importBatchData(formData).then(res => {
          this.importDataLoad = false
          this.file = null
          if (res.success) {
            this.$notify({
              title: '导入成功',
              message: res.message,
              type: 'success'
            })
            this.getList()
            this.importDataDialog = false
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.importDataLoad = false
          this.file = null
          this.$message.error('导入失败:' + error)
        })
      }
    },
    // 模板下载
    downloadTemplate(url) {
      const index = url.lastIndexOf('/') + 1
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.download = url.substring(index)
      link.click()
      this.$message({
        message: `正在下载~`,
        type: 'success'
      })
    },
    downLoadHistorySampleOrderManageExcel() {
      const tableColumn = [
        { field: 'modelno', title: '配置型号' },
        { field: 'deptno', title: '营业所' },
        { field: 'remark', title: '追加备注' }
      ]
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = 1
      findList(this.queryCondition).then(res => {
        if (res.success) {
          res.data.list.forEach((item) => {
            item.modelno = ''
            item.deptno = ''
            item.remark = '编辑时，请覆盖此行数据'
          })
          this.$refs.exportExcelVue.initExportExcel(res.data.list, tableColumn)
          this.$refs.exportExcelVue.drawer.filename = '采购发单配置模板'
          this.$refs.exportExcelVue.drawerVisible = false
          this.$refs.exportExcelVue.handleDownload()
        }
      }).catch(error => {
        console.log(error)
      })
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

