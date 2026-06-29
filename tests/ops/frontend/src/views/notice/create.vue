<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="noticeform.heading" :disabled="contentVisable" placeholder="标题" clearable/>
      <!-- <el-card style="margin-top: 10px;">
        <el-form :inline="true">
          <el-form-item label="可查看用户：" style="margin-bottom: 0px">
            <el-checkbox :disabled="contentVisable" v-model="checked_in" label="内部"/>
            <el-checkbox :disabled="contentVisable" v-model="checked_out" label="外部(可选择)"/>
          </el-form-item>
          <el-form-item v-show="checked_out" label="供应商类型:" style="margin-bottom: 0px">
            <el-select :disabled="contentVisable" v-model="supplierType" filterable size="small" placeholder="请选择供应商类型">
              <el-option v-for="item in supplierTypeList" :key="item.value" :label="item.lable" :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item v-show="checked_out" label="供应商具体选择：" style="margin-bottom: 0px">
            <el-button :disabled="contentVisable" icon="el-icon-search" circle size="mini" @click="chooseSupplier()"/>
          </el-form-item>
        </el-form>
        <el-select v-model="readerTags" :disabled="contentVisable" multiple filterable allow-create default-first-option style="margin-top: 10px; width: 100%" placeholder="请选择可查看用户">
          <el-option v-for="item in options5" :key="item.value" :label="item.label" :value="item.value"/>
        </el-select>
      </el-card> -->
      <!-- <el-input v-model="users" placeholder="可查看用户" style="margin-top: 10px" clearable/> -->
      <hr size="5" color="#0066cc">
      <!-- 主公告内容 -->
      <el-card style="font-size: 20px">公告编辑
        <el-row style="float: right;">
          <el-button icon="el-icon-share" type="primary" @click="checkPublish()">发布</el-button>
          <!-- <el-button type="primary" @click="saveDialogVisible = true">草稿</el-button> -->
          <!-- <el-button type="primary" @click="changeContent()">修改</el-button> -->
        </el-row>
        <el-input v-model="noticeform.noticeContent" :rows="15" :disabled="contentVisable" type="textarea" style="margin-top: 10px; font-size: 18px" placeholder="请输入内容"/>
      </el-card>
      <hr size="5" color="#0066cc">
      <!-- 上传附件 -->
      <el-card>
        <div class="upload">
          <el-upload ref="upload" :multiple="true" :auto-upload="false" :on-change="handleChange" :file-list="fileList" :on-remove="handleRemove" :http-request="uploadFile" action="String" >
            <el-button slot="trigger" icon="el-icon-plus" size="medium" type="primary">添加附件</el-button>
            <!-- <el-button :disabled="isButtonDisabled" style="margin-left: 30px;" size="medium" type="primary" @click="submitUpload">确定上传</el-button>
            <hr size="5" color="#ccc"> -->
          </el-upload>
        </div>
      </el-card>
      <!-- <el-dialog v-dialogDrag :visible.sync="dialodVisable" title="选择可读联系人">
        <el-form :inline="true" class="demo-form-inline">
          <el-form-item label="供应商名称：">
            <el-input v-model="supplerform.supplierName" placeholder="请输入供应商名称" clearable style="width: 240px" class="filter-item"/>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="medium" class="filter-item" style="margin-left:10px" @click="getSupplerName()">查询</el-button>
          </el-form-item>
          <el-form-item>
            <el-button :disabled="addDisable" type="primary" size="medium" class="filter-item" style="margin-left:10px" @click="addSupplerName()">添加</el-button>
          </el-form-item>
        </el-form>
        <el-table
          v-loading="listLoading"
          ref="multipleTable"
          :data="supplerList"
          :row-style="rowStyle"
          label="供应商列表"
          element-loading-text="Loading"
          border
          fit
          highlight-current-row
          @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center"/>
          <el-table-column label="供应商名称" class-name="status-col" min-width="200px" align="center">
            <template slot-scope="scope">
              <div>
                {{ scope.row.supplierName }}
              </div>
            </template>
          </el-table-column>
        </el-table>
        <div v-show="supplerList.length >0">
          <pagination v-show="supplerTotal>0" :total="supplerTotal" :page.sync="supplerPage.pageNumber" :limit.sync="supplerPage.pageSize" style="text-align:left" @pagination="getSupplerName" />
        </div>
      </el-dialog> -->
      <!-- <el-dialog
        :visible.sync="saveDialogVisible"
        title="温馨提示"
        width="30%"
        center>
        <span>确定保存该公告草稿？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="saveDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="saveContent()">确 定</el-button>
        </span>
      </el-dialog> -->
      <el-dialog
        :visible.sync="createDialogVisible"
        title="温馨提示"
        width="30%"
        center>
        <span>确定发布该公告？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="createDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="publishNotice()">确 定</el-button>
        </span>
      </el-dialog>
    </div>
    <Notice v-if="isChild" />
  </div>
</template>
<script>
import Notice from './notice.vue'
import { addNotice, getNoticeById, reEdit, removeById, uploadFiles } from '@/api/notice'
// import { getSupplerList } from '@/api/user'
// import { supplierTypeList } from '@/utils/supplier'
import Pagination from '@/components/Pagination'
export default {
  components: {
    Pagination,
    'Notice': Notice
  },
  data() {
    return {
      id: '',
      // 选择联系人情况
      checked_in: false,
      checked_out: false,
      noticeform: {
        heading: '',
        noticeContent: ''
      },
      /* options5: [{ value: '内部全部', label: '内部全部' }, { value: '外部全部', label: '外部全部' }],
      supplierTypeList: [{ value: '外部全部', label: '外部全部' }, { value: '部材工号合格供应商', label: '部材工号合格供应商' },
        { value: '部材工号试用供应商', label: '部材工号试用供应商' }, { value: '部材零散采购供应商', label: '部材零散采购供应商' },
        { value: '副资材工号供应商', label: '副资材工号供应商' }, { value: '年度零散采购供应商', label: '年度零散采购供应商' },
        { value: '纯零散采购供应商', label: '纯零散采购供应商' }],
      readerTags: [],
      readervalues: [], */
      contentVisable: false,
      supplerList: [],
      multipleTable: [],
      supplerform: {
        supplierName: ''
      },
      supplerPage: {
        pageNumber: 1,
        pageSize: 10
      },
      supplierType: null,
      listLoading: false,
      dialodVisable: false,
      addDisable: true,
      saveDialogVisible: false,
      createDialogVisible: false,
      total: 0,
      supplerTotal: 0,
      fileList: [],
      isChild: false // 子组件是否刷新
    }
  },
  watch: {
    supplierType() {
      if (this.supplierType !== '') {
        let key = true
        this.readerTags.forEach(element => {
          if (element === this.supplierType) {
            key = false
          }
        })
        if (key === true) {
          this.readerTags.push(this.supplierType)
          this.readervalues.push(this.supplierType)
          key = false
        }
      }
    },
    checked_in() {
      if (this.checked_in) {
        this.readerTags.unshift('内部全部')
      } else {
        this.readerTags.shift()
      }
    }
  },
  created() {
    // this.initNoticeCreate()
    // this.getSupplerName()
  },
  methods: {
    initNoticeCreate() {
      const id = { id: this.$route.query.id }
      var that = this
      getNoticeById(id)
        .then(data => {
          that.id = data.id
          that.noticeform.noticeContent = data.noticeContent
          that.noticeform.heading = data.heading
        })
        .catch(err => {
          console.log(err)// 错误信息
        })
    },
    // 发布前校验
    checkPublish() {
      this.createDialogVisible = true
      // if (this.noticeform.heading !== '' && this.noticeform.noticeContent !== '') {
      //   if (this.readerTags.length > 0) {
      //     this.createDialogVisible = true
      //   } else {
      //     this.$message({
      //       showClose: true,
      //       message: '请选择公告可读用户~',
      //       type: 'error'
      //     })
      //   }
      // } else {
      //   this.$message({
      //     showClose: true,
      //     message: '请填写公告标题、公告内容~',
      //     type: 'error'
      //   })
      // }
    },
    // 发布公告
    publishNotice() {
      this.uploadFile()
      // this.createNotice()
      this.createDialogVisible = false
    },
    createNotice() {
      this.createDialogVisible = false
      var notice = this.noticeform
      notice.createName = this.$store.getters.info.username
      notice.readedNum = 0
      notice.readedableUser = this.readerTags[0]
      const readers = this.readerTags
      notice.editStatus = 'published'
      if (this.id === undefined || this.id === null || this.id === '') {
        addNotice(notice, readers)
          .then(data => {
            console.log(data)
          })
          .catch(err => {
            console.log(err)// 错误信息
          })
      } else {
        var that = this
        addNotice(notice, readers)
          .then(data => {
            var params = { id: that.id }
            removeById(params)
              .catch(err => {
                console.log(err)// 错误信息
              })
          })
          .catch(err => {
            console.log(err)// 错误信息
          })
      }
    },
    saveContent() {
      this.saveDialogVisible = false
      var notice = this.noticeform
      if (this.id === undefined || this.id === null || this.id === '') {
        notice.createName = this.$store.getters.info.username
        notice.readedNum = 0
        notice.readedableUser = this.readerTags[0]
        const readers = []
        notice.editStatus = 'draft'
        addNotice(notice, readers)
          .then(data => {
            console.log(data)
          })
          .catch(err => {
            console.log(err)// 错误信息
          })
      } else {
        notice.id = this.id
        reEdit(notice)
          .then(data => {
            console.log(data)
          })
          .catch(err => {
            console.log(err)// 错误信息
          })
      }
      // this.contentVisable = true
    },
    changeContent() {
      this.contentVisable = false
    },
    // getSupplerName() {
    //   this.listLoading = true
    //   const condition = this.supplerform
    //   const page = this.supplerPage
    //   getSupplerList(condition, page).then(data => {
    //     this.supplerList = data.list
    //     this.supplerTotal = data.total
    //     this.listLoading = false
    //   })
    // },
    addSupplerName() {
      this.multipleTable.forEach(element => {
        // var obj = { value: element.supplierId, label: element.supplierName }
        // this.options5.push(obj)
        this.readerTags.push(element.supplierName)
        this.readervalues.push(element.supplierId)
      })
      console.log(this.readerTags)
      console.log(this.readervalues)
    },
    rowStyle({ row, rowIndex }) {
      return 'height: 37px'
    },
    chooseSupplier() {
      this.dialodVisable = true
    },
    handleSelectionChange(val) {
      this.multipleTable = val
      if (val.length > 0) {
        this.addDisable = false
      } else {
        this.addDisable = true
      }
    },
    // // 点击上传事件
    // submitUpload(fileList) {
    //   this.uploadFile()
    //   this.isButtonDisabled = true
    // },
    // 上传文件格式判断
    handleChange(file, fileList) {
      this.fileList = fileList
      if (this.fileList !== null) {
        this.isButtonDisabled = false
      }
    },
    // 手动上传
    uploadFile() {
      const formData = new FormData()
      this.fileList.forEach(element => {
        if (element.status !== 'success') {
          formData.append('reportFile', element.raw, element.raw.name)
        }
      })
      // this.readerTags.forEach(element => {
      //   formData.append('readers', element)
      // })
      // notice对象封装
      formData.append('heading', this.noticeform.heading)
      formData.append('noticeContent', this.noticeform.noticeContent)
      formData.append('createName', this.$store.getters.info.username)
      formData.append('readedNum', 0)
      formData.append('editStatus', 'published')

      uploadFiles(formData).then(data => {
        this.fileList.forEach(element => {
          element.status = 'success'
        })
        this.$notify({
          title: '成功',
          message: '公告发布成功！',
          type: 'success'
        })
      }).catch(response => {
        // this.$message.error('公告发布失败！')
        this.$notify.error({
          title: '错误',
          message: '公告发布失败！'
        })
      })
      this.isChild = true
    },
    // 点击移除文件
    handleRemove(fileList) {
      console.log(fileList)
    }
  }
}
</script>

