<template>
  <div class="app-container">
    <el-container style="min-height: 700px; border: 1px solid #eee">
      <el-aside width="450px" style="padding: 3px 5px;margin: 0">
        <el-table
          :data="parentTableData.filter(data => !search || data.codeName.includes(search))"
          :row-class-name="tableRowClassName"
          size="mini"
          stripe
          height="84vh"
          @row-click="handdle">
          <el-table-column
            v-if="showColum"
            prop="id"
            label="ID"
            align="center"
          />
          <el-table-column
            prop="code"
            label="分类代码"
            align="center"
          />
          <el-table-column
            prop="codeName"
            label="分类名称"
            align="center"
            width="140px"
          />

          <el-table-column
            align="center"
            width="200">
            <template slot="header" slot-scope="scope" >
              <el-input
                v-model="search"
                size="mini"
                class="searchInput"
                placeholder="编码名称搜索"
                @click="useless(scope.row)"/>
              <el-button
                size="mini"
                type="primary"
                plain
                round
                icon="el-icon-plus"
                @click="headerAdd()"/>
            </template>
            <template slot-scope="scope">
              <el-button
                :disabled="scope.row.locked"
                size="mini"
                type="primary"
                plain
                round
                icon="el-icon-plus"
                @click="handleAddCode(scope.$index, scope.row)"/>
              <el-button
                :disabled="scope.row.locked"
                size="mini"
                type="primary"
                plain
                round
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"/>
              <el-button
                :disabled="scope.row.locked"
                size="mini"
                type="danger"
                round
                icon="el-icon-delete"
                @click="handleDelete(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          :total="total"
          :page.sync="page.pageNumber"
          :limit.sync="page.pageSize"
          :pager-count="5"
          :page-sizes="[30, 50, 100,200]"
          style="text-align: left;margin-top:5px;padding-top: 2px"
          layout="sizes, prev, pager, next"
          @pagination="queryClassList()"
        />
      </el-aside>
      <el-main>
        <div v-if="listQuery.classCode!='0'" class="bg-purple">{{ listQuery.classCode }}编码明细
          <el-button
            size="mini"
            type="primary"
            plain
            round
            icon="el-icon-plus"
            @click="handleHeaderAddCode()"/>
        </div>
        <el-table
          :data="childrenTableData"
          :row-class-name="tableRowClassName"
          size="mini"
          stripe
          height="80vh"
          width="100%"
        >
          <el-table-column
            label="编码"
            align="center"
            prop="code"
            width="100px" />

          <el-table-column
            label="名称"
            align="center"
            prop="codeName"
            width="150px" />
          <el-table-column
            label="任选1"
            align="center"
            prop="extNote1"
            width="100px" />
          <el-table-column
            label="任选2"
            align="center"
            prop="extNote2"
            width="100px" />
          <el-table-column
            label="任选3"
            align="center"
            prop="extNote3"
            width="100px" />
          <el-table-column
            label="任选4"
            align="center"
            prop="extNote4"
            width="100px" />
          <el-table-column
            label="父编码"
            align="center"
            prop="parentCode"
            width="100px" />
          <el-table-column
            :formatter="statusFormatter"
            label="状态"
            align="center"
            prop="status"
            width="60px" />

          <el-table-column
            align="left"
            width="180px">
            <template slot-scope="scope">
              <el-button
                :disabled="scope.row.locked"
                size="mini"
                type="primary"
                plain
                round
                icon="el-icon-plus"
                @click="handleAddChildCode(scope.$index, scope.row)"/>
              <el-button
                :disabled="scope.row.locked"
                size="mini"
                type="primary"
                plain
                round
                icon="el-icon-edit"
                @click="handleEdit(scope.$index, scope.row)"/>
              <el-button
                :disabled="scope.row.locked"
                size="mini"
                type="danger"
                round
                icon="el-icon-delete"
                @click="handleDelete(scope.$index, scope.row)"/>
            </template>
          </el-table-column>
        </el-table>
      </el-main>
    </el-container>

    <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false" title="字典操作" >
      <el-form ref="dictForm" :model="form" :inline="true" :rules="rulesForm" class="demo-form-inline">
        <el-form-item label-width="150px" label="分类编码" prop="classCode" >
          <el-input v-model="form.classCode" :disabled="inputClassCodeDisabled" autocomplete="off" placeholder="类编码" />
        </el-form-item>
        <el-form-item label-width="150px" label="父编码" >
          <el-input v-model="form.parentCode" autocomplete="off" placeholder="父编码"/>
        </el-form-item>
        <el-form-item label-width="150px" label="编码" prop="code">
          <el-input v-model="form.code" autocomplete="off" placeholder="编码"/>
        </el-form-item>
        <el-form-item label-width="150px" label="编码名称" prop="codeName">
          <el-input v-model="form.codeName" autocomplete="off" placeholder="编码名称"/>
        </el-form-item>
        <el-form-item label-width="150px" label="任选1">
          <el-input v-model="form.extNote1" autocomplete="off" placeholder="任选1"/>
        </el-form-item>
        <el-form-item label-width="150px" label="任选2">
          <el-input v-model="form.extNote2" autocomplete="off" placeholder="任选"/>
        </el-form-item>
        <el-form-item label-width="150px" label="任选3">
          <el-input v-model="form.extNote3" autocomplete="off" placeholder="任选3"/>
        </el-form-item>
        <el-form-item label-width="150px" label="任选4">
          <el-input v-model="form.extNote4" autocomplete="off" placeholder="任选4"/>
        </el-form-item>
        <el-form-item label-width="150px" label="编码状态">
          <el-select v-model="form.status" placeholder="请选择">
            <el-option label="有效" value="1"/>
            <el-option label="无效" value="0"/>
          </el-select>
        </el-form-item>
        <el-form-item label-width="150px" label="排序">
          <el-input v-model="form.sort" autocomplete="off" placeholder="排序"/>
        </el-form-item>
        <el-form-item label-width="180px" label="是否不可编辑" >
          <el-switch
            v-model="form.locked"
            active-color="#13ce66"
            inactive-color="#ff4949" />
        </el-form-item>
        <el-form-item label-width="150px" label="备注" >
          <el-input v-model="form.remark" type="textarea" style="width: 285%" autocomplete="off" placeholder="备注"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDialog()">取 消</el-button>
        <el-button :loading="loadingButton" type="primary" @click="onSubmit('dictForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { listClass, saveDict, deleteDict, listByClassCode } from '@/api/common/dict'
import { mapGetters } from 'vuex'
import Pagination from '@/components/Pagination'
export default {
  name: 'DictIndex',
  components: { Pagination },
  data() {
    return {
      parentTableData: [],
      childrenTableData: [],
      search: '',
      listQuery: {
        classCode: '0'
      },
      // 分页
      total: 1,
      page: {
        pageNumber: 1,
        pageSize: 30
      },
      dialogFormVisible: false, // 是否打开dialog false 不打开
      form: {
        id: '',
        classCode: '',
        code: '',
        extNote1: '',
        extNote2: '',
        extNote3: '',
        extNote4: '',
        codeName: '',
        parentCode: '',
        remark: '',
        status: '1',
        sort: '0',
        locked: false, // 是否不可编辑 1 true 不可编辑  0 false 可编辑
        createUser: '',
        updateUser: ''
      },
      noEdit: false,
      // disabledButton: false, // 操作按钮是否禁用  true 禁用 false 不禁用
      inputClassCodeDisabled: false, // 输入框 classCode 禁用标识
      isSave: false,
      showColum: false,
      loadingButton: false,
      // 表单验证
      rulesForm: {
        classCode: [{ required: true, message: '分类代码不可为空', trigger: 'blur' }],
        code: [{ required: true, message: '编码不可为空', trigger: 'blur' }],
        codeName: [{ required: true, message: '分类名称不可为空', trigger: 'blur' }]
      }
    }
  },
  computed: {
    ...mapGetters(['name'])
  },
  created() {
    this.queryClassList()
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (row.status === 0) {
        return 'warning-row'
      }
      return ''
    },
    queryClassList() {
      this.listQuery.classCode = '0'
      listClass(this.listQuery, this.page).then(res => {
        this.parentTableData = res.content.list
        this.total = res.content.total
      })
    },
    statusFormatter: function(row, column) {
      switch (row.status) {
        case 0:
          return '无效'
        case 1:
          return '有效'
        default:
          return ''
      }
    },
    dateFormatter: function(row, column) {
      if (row.createTime === null) {
        return ''
      } else {
        return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    handdle(row, event, column) {
      console.log('点击选中行的数据 ->', row.code)
      this.listQuery.classCode = row.code
      this.queryCodeList()
    },
    queryCodeList() {
      this.childrenTableData = []
      listByClassCode(this.listQuery).then(res => {
        this.childrenTableData = res
      })
    },
    handleAddCode(index, row) {
      this.reset()
      this.dialogFormVisible = true
      this.isSave = true
      this.form.createUser = this.name
      this.form.classCode = row.code
      this.inputClassCodeDisabled = true
    },
    handleHeaderAddCode() {
      this.reset()
      this.dialogFormVisible = true
      this.isSave = true
      this.form.createUser = this.name
      this.form.classCode = this.listQuery.classCode
      this.inputClassCodeDisabled = true
    },
    handleAddChildCode(index, row) {
      this.reset()
      this.dialogFormVisible = true
      this.isSave = true
      this.form.createUser = this.name
      this.selectClassCode = row.classCode
      this.form.classCode = row.classCode
      this.form.parentCode = row.code
      this.inputClassCodeDisabled = true
    },
    headerAdd() {
      this.reset()
      this.dialogFormVisible = true
      this.isSave = true
      this.form.createUser = this.name
      this.form.classCode = '0'
      this.inputClassCodeDisabled = true
    },
    handleEdit(index, row) {
      console.log(index, '选中编辑行的数据->', row)
      this.dialogFormVisible = true
      this.isSave = false
      this.form = row
      if (this.form.status === 1) {
        this.form.status = '有效'
      } else if (this.form.status === 0) {
        this.form.status = '无效'
      }
      this.form.updateUser = this.name
    },
    closeDialog() {
      this.dialogFormVisible = false
      console.log('close -> ', this.form)
      if (this.form.status === '有效') {
        this.form.status = 1
      } else if (this.form.status === '无效') {
        this.form.status = 0
      }
    },
    handleDelete(index, row) {
      const data = {
        id: row.id
      }
      this.$confirm('此操作将永久删除该字典参数, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteDict(data).then(res => {
          console.log('del -> ', res)
          if (res.success === true) {
            this.$notify({
              title: '成功',
              message: '删除成功' + row.classCode,
              type: 'success'
            })

            this.listQuery.classCode = row.classCode
            if (this.listQuery.classCode === '0') {
              this.queryClassList()
            } else {
              this.queryCodeList()
            }
          } else {
            this.$notify.error({
              title: '错误',
              message: '保存失败'
            })
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 提交
    onSubmit(dictForm) {
      this.$refs[dictForm].validate(valid => {
        this.loadingButton = true
        if (!valid) {
          this.loadingButton = false
          this.dialogFormVisible = true
          return
        }
        if (this.isSave) { // 保存
          saveDict(this.form).then(res => {
            console.log('save form ->', this.form)
            if (res.success === true) {
              this.$notify({
                title: '成功',
                message: '保存成功' + this.form.classCode,
                type: 'success'
              })
              this.listQuery.classCode = this.form.classCode
              if (this.listQuery.classCode === '0') {
                this.queryClassList()
              } else {
                this.queryCodeList()
              }
              this.reset()
              this.dialogFormVisible = false
              this.loadingButton = false
            }
          }).catch(() => {
            this.dialogFormVisible = true
            this.$notify.error({
              title: '错误',
              message: '保存失败'
            })
          })
          return
        }
        if (!this.isSave) {
          if (this.form.status === '有效') {
            this.form.status = 1
          } else if (this.form.status === '无效') {
            this.form.status = 0
          }
          saveDict(this.form).then(res => {
            console.log('update form ->', this.form)
            if (res.success === true) {
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success'
              })
              this.listQuery.classCode = this.form.classCode
              if (this.listQuery.classCode === '0') {
                this.queryClassList()
              } else {
                this.queryCodeList()
              }
              this.reset()
              this.dialogFormVisible = false
              this.loadingButton = false
            }
          }).catch(() => {
            this.dialogFormVisible = true
            this.$notify.error({
              title: '错误',
              message: '保存失败'
            })
          })
          return
        }
      })
    },
    reset() {
      this.form = {
        id: '',
        classCode: '',
        code: '',
        extNote1: '',
        extNote2: '',
        extNote3: '',
        extNote4: '',
        codeName: '',
        parentCode: '',
        remark: '',
        status: '1',
        sort: '0',
        locked: false, // 是否不可编辑 1 true 不可编辑  0 false 可编辑
        createUser: '',
        updateUser: ''
      }
    },
    useless(row) {
      console.log('----===--->', row)
    }
  }
}
</script>
<style scoped>
.el-table .warning-row {
    background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
.el-input {
  width: 80%;
}
.searchInput{
  width: 110px;
}
</style>
