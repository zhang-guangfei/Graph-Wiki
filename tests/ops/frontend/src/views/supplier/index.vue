<template>
  <el-container>
    <el-main>
      <el-form ref="searchForm" :model="searchForm" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="供应商编码">
          <el-input v-model="searchForm.id" placeholder="请输入编码" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="供应商名称">
          <el-input v-model="searchForm.name" placeholder="请输入名称" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="最快运输方式">
          <el-select v-model="searchForm.fstTransTypeId" clearable placeholder="请选择运输方式">
            <el-option
              v-for="item in transTypeOptions"
              :key="item.code"
              :label="item.codeName"
              :value="item.code" />
          </el-select>
        </el-form-item>
        <el-button v-permission="['394257']" type="primary" size="mini" style="margin-left: 10px;" @click="initList()">查询</el-button>
        <el-button v-permission="['178887']" type="primary" size="mini" @click="addSupplier()">新增</el-button>
        <el-button v-permission="['178887']" type="warning" size="mini" style="margin-left: 10px;" @click="editSupplier()">编辑</el-button>
        <el-button v-permission="['178887']" type="danger" size="mini" style="margin-left: 10px;" @click="deleteSupplier()">删除</el-button>
      </el-form>
      <el-table
        :data="tableData"
        :default-sort = "{prop: 'sort', order: 'ascending'}"
        border
        stripe
        style="width: 90%"
        @selection-change="handleSelectionChange">

        <el-table-column
          text="选择"
          type="selection"
          width="40"
          align="center"/>

        <el-table-column
          prop="id"
          label="供应商编码"
          width="100" />

        <el-table-column
          prop="name"
          label="名称"
          width="180" />

        <el-table-column
          prop="stdDeliveryDay"
          label="标准交货期" />

        <el-table-column
          prop="fcostcode"
          label="财务成本系统供应商分类id"
          width="190"/>

        <el-table-column
          :formatter="transCurrencyFormatter"
          prop="transCurrency"
          label="交易币种" />

        <el-table-column
          prop="paymentDay"
          label="结算天数" />

        <el-table-column
          :formatter="treeTypeFormatter"
          prop="fstTransTypeId"
          label="最快交付运输方式" />

        <el-table-column
          prop="email"
          label="供应商接收邮箱" />

        <el-table-column
          :formatter="autoSendFormatter"
          prop="isAutoSend"
          label="是否自动发单" />

        <el-table-column
          sortable
          prop="sort"
          width="80"
          label="排序" />

      <!-- <el-table-column
          prop="enableStdDeliveryDay"
          label="是否启用标准货期进行计算"
          width="190" /> -->
      </el-table>
      <el-dialog :title="title" :visible.sync="dialogFormVisible">
        <el-form ref="form" :model="form" :rules="rules" label-width="130px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="供应商编码" prop="id" >
                <el-input v-model="form.id" @change ="changeSupplierCode" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="供应商名称" prop="name" >
                <el-input v-model="form.name" />
              </el-form-item>
            </el-col>
            <!-- <el-col :span="8">
              <el-form-item label="国别代码" prop="countryCode">
                <el-input v-model="form.countryCode" />
              </el-form-item>
            </el-col> -->
            <el-col :span="8">
              <el-form-item label="标准交货期">
                <el-input v-model="form.stdDeliveryDay" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="结算天数">
                <el-input v-model="form.paymentDay" />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="交易币种">
                <el-select v-model="form.transCurrency" clearable placeholder="请选择交易币种">
                  <el-option
                    v-for="item in transCurrencyOptions"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="最快交付运输方式">
                <el-select v-model="form.fstTransTypeId" clearable placeholder="请选择运输方式">
                  <el-option
                    v-for="item in getTransIds"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code" />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="财务成本系统供应商分类id" prop="fcostcode">
                <el-input v-model.number="form.fcostcode" style="width: 180px"/>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="供应商接收邮箱">
                <el-input v-model="form.email" placeholder="请用逗号隔开"/>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="排序">
                <el-input v-model="form.sort" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-form-item label="是否自动发单" prop="isAutoSend">
              <el-select v-model="form.isAutoSend" placeholder="请选择" style="width: 180px">
                <el-option
                  v-for="item in options"
                  :key="item.code"
                  :label="item.codeName"
                  :value="item.code" />
              </el-select>
            </el-form-item>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogFormVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('form')">确 定</el-button>
        </div>
      </el-dialog>
      <pagination
        v-show="total > 10"
        :total="total"
        :page-sizes="[20, 50, 100, 150, 200]"
        :page.sync="searchForm.page.pageNumber"
        :limit.sync="searchForm.page.pageSize"
        @pagination="initList()"/>
    </el-main>
  </el-container>
</template>
<script>

import Pagination from '@/components/Pagination'
import { getDictDataByPid } from '@/api/common/dict'
import { findSupplierList, addSupplier, editSupplier, deleteSupplierById } from '@/api/common/supplier'
import { getTransIds } from '@/api/purchaseOrder'

export default {
  components: { Pagination },
  data() {
    return {
      searchForm: {
        id: '',
        name: '',
        fstTransTypeId: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      total: 1,
      transTypeOptions: [],
      getTransIds: [],
      tableData: [],
      dialogFormVisible: false,
      title: '',
      form: {
        id: '',
        name: '',
        countryCode: '',
        stdDeliveryDay: '',
        fcostcode: '',
        transCurrency: '',
        paymentDay: '',
        fstTransTypeId: '',
        email: '',
        isAutoSend: '',
        sort: 1
      },
      transCurrencyOptions: [],
      rules: {
        id: [
          { required: true, message: '请输入供应商编码', trigger: 'blur' },
          { min: 1, max: 2, message: '长度为2 个字符', trigger: 'blur' }
        ],
        name: [{ required: true, message: '请输入供应商名称', trigger: 'blur' }],
        countryCode: [{ min: 0, max: 3, message: '长度最大为3 个字符', trigger: 'blur' }],
        fcostcode: [
          { required: true, message: '请输入财务成本系统供应商分类id', trigger: 'blur' },
          { type: 'number', message: '财务成本系统供应商分类id为数字值' }
        ],
        isAutoSend: [{ required: true, message: '请选择是否自动发单', trigger: 'change' }]
      },
      options: [
        { code: 1, codeName: '是' },
        { code: 0, codeName: '否' }
      ],
      multipleSelection: [],
      idAdd: true
    }
  },
  mounted() {
    this.getTransTypeId()
    this.initList()
    this.total = this.tableData.length
    this.getTransCurrencyOptions()
  },
  methods: {
    changeSupplierCode() {
      this.getTransIds = []
      this.form.fstTransTypeId = ''
      var transParam = {}
      transParam.supplierId = this.form.id
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      getTransIds(transParam).then(res => {
        this.editloading = false
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.getTransIds.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          this.getTransIds = this.transTypeOptions
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    getTransTypeId() {
      var transParam = {}
      transParam.supplierId = null
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      transParam.translation = true
      getTransIds(transParam).then(res => {
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.transTypeOptions.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    getTransCurrencyOptions() {
      getDictDataByPid('4303').then(res => {
        if (res.success) {
          this.transCurrencyOptions = res.content
        } else {
          this.transCurrencyOptions = []
        }
      }).catch(() => {
        this.transCurrencyOptions = []
      })
    },
    initList() {
      findSupplierList(this.searchForm).then(res => {
        console.log(res)
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
      })
    },
    treeTypeFormatter(row) {
      return this.valFormatter(this.transTypeOptions, row.fstTransTypeId + '')
    },
    transCurrencyFormatter(row) {
      return this.valFormatter(this.transCurrencyOptions, row.transCurrency + '')
    },
    autoSendFormatter(row) {
      return this.valFormatter(this.options, row.isAutoSend)
    },
    valFormatter(list, val) {
      for (var i = 0; i < list.length; i++) {
        if (list[i].code === val) {
          return list[i].codeName
        }
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
      console.log('this.multipleSelection =>', this.multipleSelection)
    },
    addSupplier() {
      this.getTransIds = []
      this.isAdd = true
      this.resetForm()
      this.title = '新增供应商'
      var transParam = {}
      transParam.supplierId = null
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      getTransIds(transParam).then(res => {
        this.editloading = false
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.getTransIds.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          this.getTransIds = this.transTypeOptions
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      this.dialogFormVisible = true
    },
    editSupplier() {
      this.getTransIds = []
      this.idAdd = false
      this.title = '编辑供应商'
      if (this.multipleSelection.length === 1) {
        this.form = this.multipleSelection[0]
        this.form.fcostcode = parseInt(this.form.fcostcode)
        // 修改人
        this.form.updator = this.$store.getters.name
        var transParam = {}
        transParam.supplierId = this.form.id
        transParam.modelNo = null
        transParam.orderQty = null
        transParam.ordType = null
        transParam.warehouse = null
        getTransIds(transParam).then(res => {
          this.editloading = false
          if (res.success && res.data.length > 0) {
            res.data.forEach(dict => {
              this.getTransIds.push({ code: dict.id, codeName: dict.name })
            })
          } else {
            this.getTransIds = this.transTypeOptions
          }
        }).catch(error => {
          this.$message.error(error.message)
        })
        this.dialogFormVisible = true
      } else {
        this.$notify({
          title: '警告',
          message: '请选择一条数据进行编辑',
          type: 'warning'
        })
      }
    },
    deleteSupplier() {
      var newArr = this.multipleSelection.map((item, index) => {
        return item.id
      })
      deleteSupplierById(newArr).then(res => {
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.message,
            type: 'success'
          })
          this.initList()
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '操作失败,发生异常'
        })
      })
    },
    submitForm(form) {
      this.$refs[form].validate((valid) => {
        if (valid) {
          console.log('this from =>', this.form)
          if (this.idAdd) {
            addSupplier(this.form).then(res => {
              this.dialogFormVisible = false
              if (res.success) {
                this.$notify({
                  title: '成功',
                  message: res.message,
                  type: 'success'
                })
                this.initList()
              } else {
                this.$notify.error({
                  title: '错误',
                  message: res.message
                })
              }
            }).catch(() => {
              this.$notify.error({
                title: '错误',
                message: '操作失败,发生异常'
              })
            })
          } else {
            editSupplier(this.form).then(res => {
              this.dialogFormVisible = false
              if (res.success) {
                this.$notify({
                  title: '成功',
                  message: res.message,
                  type: 'success'
                })
                this.initList()
              } else {
                this.$notify.error({
                  title: '错误',
                  message: res.message
                })
              }
            }).catch(() => {
              this.$notify.error({
                title: '错误',
                message: '操作失败,发生异常'
              })
            })
          }
        } else {
          return false
        }
      })
    },
    resetForm() {
      this.form = {
        id: '',
        name: '',
        countryCode: '',
        stdDeliveryDay: '',
        fcostcode: '',
        transCurrency: '',
        paymentDay: '',
        fstTransTypeId: '',
        email: '',
        isAutoSend: '',
        sort: 1
      }
    }
  }
}
</script>
<style scoped>
</style>
