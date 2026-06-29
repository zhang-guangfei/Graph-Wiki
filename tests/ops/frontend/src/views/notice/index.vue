<template>
  <div class="app-container">
    <div class="filter-container">
      <el-card>
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
          <el-form-item label="标题：">
            <el-input v-model="formInline.heading" placeholder="请输入标题" clearable style="width: 190px" size="medium" />
          </el-form-item>
          <!-- <el-form-item label="接收人：">
            <el-radio-group v-model="formInline.editStatus">
              <el-radio-button label="发布状态"/>
              <el-radio-button label="编辑状态"/>
              <el-radio-button label="广州"/>
            </el-radio-group>
            <el-input v-model="formInline.editStatus" placeholder="请输入接收人姓名" clearable style="width: 180px" size="medium" />
          </el-form-item> -->
          <!-- <el-form-item label="发布人：">
            <el-input v-model="formInline.createName" placeholder="请输入发布人姓名" clearable style="width: 130px" size="medium"/>
          </el-form-item> -->
          <el-form-item label="发布日期：">
            <el-date-picker
              :picker-options="pickerOptions2"
              v-model="startTimeAndEndTime"
              type="daterange"
              style="width: 230px"
              align="center"
              unlink-panels
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd HH:mm:ss"
              size="medium"
            />
          </el-form-item>
          <el-form-item>
            <el-tooltip content="重置" placement="top">
              <el-button type="info" icon="el-icon-close" size="mini" circle @click="clearCondition()"/>
            </el-tooltip>
          </el-form-item>
          <el-form-item><el-button type="primary" size="medium" @click="getNoticeList()">查询</el-button></el-form-item>
        </el-form>
        <hr size="5" color="#0066cc">
      </el-card>
      <br>
      <el-table
        v-loading="listLoading"
        :data="list"
        border
        fit
        style="width: 100%"
        highlight-current-row
      >
        <!-- <el-table-column type="selection" width="50" align="center"/> -->
        <el-table-column type="index" fixed="left" label="序号" width="57" sortable="custom" align="center"/>
        <el-table-column fixed="left" min-width="300" label="标题" align="center">
          <template slot-scope="scope">
            <el-tooltip placement="right" trigger="hover" content="标题~">
              <div class="text-tohandle" @click="toDetail(scope.row.id)"><u>{{ scope.row.heading }}</u></div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column width="200" label="阅读情况" align="center">
          <template slot-scope="scope">
            <el-tooltip placement="right" trigger="hover" content="阅读情况~">
              <!-- <div style="cursor: pointer" @click="changeOpen(scope.row.id)">
                <span v-if="scope.row.totalNotice === null">
                  <el-tag type="warning">{{ scope.row.readedNum }} / 0</el-tag>
                </span>
                <span v-else> -->
              <el-tag type="warning">{{ scope.row.readedNum }} / {{ scope.row.totalNotice }}</el-tag>
              <!-- </span>
              </div> -->
            </el-tooltip>
          </template>
        </el-table-column>
        <!-- <el-table-column width="135" label="公告状态" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.editStatus === 'published'">
              <el-tag>已发布</el-tag>
            </span>
            <span v-else-if="scope.row.editStatus === 'draft'">
              <el-tag type="warning">草稿</el-tag>
            </span>
          </template>
        </el-table-column> -->
        <!-- <el-table-column prop="createName" width="200" show-overflow-tooltip label="发布人" align="center"/> -->
        <el-table-column prop="newsDate" width="135" label="发布时间" align="center">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.newsDate.substr(0,10) }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column width="135" label="操作" align="center">
          <template slot-scope="scope">
            <el-tooltip placement="top" content="删除">
              <el-button class="small-button" icon="el-icon-delete" size="mini" type="primary" circle @click="removeOpenDialog(scope.$index, scope.row.id)"/>
            </el-tooltip>
            <el-tooltip placement="top" content="编辑">
              <el-button class="small-button" size="mini" type="primary" icon="el-icon-edit" circle @click="updateNoticeDialog(scope.row.id)"/>
            </el-tooltip>
            <!-- <el-tooltip placement="top" content="修改">
              <el-button class="small-button" icon="el-icon-edit" size="mini" type="primary" circle @click="toEdit(scope.row.id)"/>
            </el-tooltip> -->
            <!-- <el-tooltip v-show="true" placement="left-start" content="发布">
              <el-button class="small-button" icon="el-icon-upload2" size="mini" type="primary" circle @click="scopeConfirm(scope.row.orderno)"/>
            </el-tooltip> -->
          </template>
        </el-table-column>
      </el-table>
      <!-- <el-dialog v-dialogDrag :visible.sync="unreadDialogVisable" title="未读公告人明细">
        <el-table
          :data="unreadUserList"
          label="供应商列表"
          border
          fit
          highlight-current-row>
          <el-table-column
            label="No."
            type="index"
            width="55px"
            align="center"/>
          <el-table-column label="用户名" class-name="status-col" min-width="100px" align="center">
            <template slot-scope="scope">
              <div>
                {{ scope.row.username }}
              </div>
            </template>
          </el-table-column>
          <el-table-column label="供应商" class-name="status-col" min-width="200px" align="center">
            <template slot-scope="scope">
              <div>
                {{ scope.row.supplierName }}
                <span v-if="scope.row.supplierName === null">
                  SMC INTERNAL MEMBERS
                </span>
              </div>
            </template>
          </el-table-column>
          <el-table-column label="读取状态" class-name="status-col" min-width="70px" align="center">
            <template slot-scope="scope">
              <div>
                <span v-if="scope.row.readStatus === 'readed'">
                  <svg-icon icon-class="readed" />
                </span>
                <span v-else-if="scope.row.readStatus === 'unread'">
                  <svg-icon icon-class="unread-r" />
                </span>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="unreadUserPage.total>0"
          :total="unreadUserPage.total"
          :page.sync="unreadUserPage.pageNumber"
          :limit.sync="unreadUserPage.pageSize"
          @pagination="getUnreadUserDetail()"
        />
      </el-dialog> -->
      <el-dialog :visible.sync="modifyDialogVisible" title="修改公告" align="center" width="45%">
        <el-input v-show="false" v-model="modifyNotice.noticeId"/>
        <el-form ref="noticeForm" :model="modifyNotice" label-position="left" label-width="90px">
          <el-form-item label="公告标题: " prop="heading" >
            <el-input v-model="modifyNotice.heading" size="small"/>
          </el-form-item>
          <el-row :gutter="5">
            <el-col :span="12">
              <el-form-item label="发布者: " prop="createName">
                <el-input v-model="modifyNotice.createName" :disabled="true" size="small"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="5">
            <el-col :span="12">
              <el-form-item label="发布日期: " prop="createName">
                <el-input v-model="modifyNotice.newsDate" :disabled="true" size="small"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="5">
            <el-col :span="8">
              <el-form-item label="公告状态：" prop="editStatus" >
                <el-select v-model="modifyNotice.editStatus" size="small" >
                  <el-option label="published" value="published"/>
                  <el-option label="outdated" value="outdated"/>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-form-item label="公告内容：" prop="noticeContent">
            <el-input v-model="modifyNotice.noticeContent" type="textarea" maxlength="300"/>
          </el-form-item>
          <el-card>
            <div class="upload">
              <el-upload ref="upload" :multiple="true" :auto-upload="false" :on-change="handleChange" :file-list="fileList" :on-remove="handleRemove" :http-request="uploadFile" action="String" >
                <el-button slot="trigger" icon="el-icon-plus" size="medium" type="primary">添加附件</el-button>
              </el-upload>
            </div>
          </el-card><br>
          <label>附件：</label>
          <el-tag
            v-for="files in noticeAttachedfile"
            :key="files.fileName"
            closable
            type=""
            style="margin-top: 5px; margin-left: 5px;"
            @close="handleClose(files.id,files.noticeId)">
            {{ files.fileName }}
          </el-tag>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="modifyDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="updateNotice(modifyNotice.noticeId)">确 定</el-button>
        </span>
      </el-dialog>
      <el-dialog
        :visible.sync="removeDialogVisible"
        title="温馨提示"
        width="30%"
        center>
        <span>确定要删除该公告？</span>
        <span slot="footer" class="dialog-footer">
          <el-button @click="removeDialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="removeNotice()">确 定</el-button>
        </span>
      </el-dialog>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="page.pageNumber"
        :limit.sync="page.pageSize"
        @pagination="getNoticeList()"
      />
    </div>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'// Secondary package based on el-pagination
import { getNotices, removeById, getAttachedfileNotice, deleteAttachedfileById, updateFile } from '@/api/notice'
import { shortcuts } from '@/utils/dataFormat'
export default {
  name: 'Index',
  components: { Pagination },
  data() {
    return {
      list: [],
      noticeAttachedfile: null,
      modifyDialogVisible: false,
      modifyNotice: {
        noticeId: '',
        createName: '',
        editStatus: '',
        heading: '',
        noticeContent: '',
        newsDate: ''
      },
      total: 0,
      listLoading: true,
      multipleTable: [],
      formInline: {
        heading: '',
        // readerName: '',
        editStatus: '',
        createName: '',
        startDate: '',
        endDate: ''
      },
      page: {
        pageNumber: 1,
        pageSize: 10
      },
      unreadUserPage: {
        pageNumber: 1,
        pageSize: 10,
        total: 0
      },
      fileList: [], // 文件列表
      // utils组件引入
      pickerOptions2: shortcuts,
      startTimeAndEndTime: '',
      removeDialogVisible: false,
      keyId: '',
      keyIndex: null,
      unreadUserList: [],
      unreadDialogVisable: false,
      unreadUserKeyId: ''
    }
  },
  created() {
    this.getNoticeList()
  },
  methods: {
    clearCondition() {
      this.startTimeAndEndTime = ''
      this.formInline = {}
    },
    getNoticeList() {
      if (this.startTimeAndEndTime === null) {
        this.formInline.startDate = ''
        this.formInline.endDate = ''
      } else {
        this.formInline.startDate = this.startTimeAndEndTime[0]
        this.formInline.endDate = this.startTimeAndEndTime[1]
      }
      this.formInline.createName = this.$store.getters.info.username
      var conditon = this.formInline
      var page = this.page
      var that = this
      getNotices(conditon, page)
        .then(data => {
          console.log(data)// 返回的数据
          that.total = data.total
          that.list = data.list
          that.listLoading = false
        })
        .catch(err => {
          console.log(err)// 错误信息
        })
    },
    // changeOpen(value) {
    //   this.unreadUserKeyId = value
    //   this.getUnreadUserDetail()
    //   this.unreadDialogVisable = true
    // },
    // getUnreadUserDetail() {
    //   var that = this
    //   var params = { noticeId: this.unreadUserKeyId, pageNumber: that.unreadUserPage.pageNumber, pageSize: that.unreadUserPage.pageSize }
    //   queryUnreadUserDetailByNoticeId(params)
    //     .then(data => {
    //       that.unreadUserList = data.list
    //       that.unreadUserPage.total = data.total
    //     })
    //     .catch(err => {
    //       console.log(err)// 错误信息
    //     })
    // },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    toDetail(value) {
      this.$router.push({
        path: '/notice/content',
        query: {
          id: value
        }
      })
    },
    toEdit(value) {
      this.$router.push({
        path: '/notice/create',
        query: {
          id: value
        }
      })
    },
    // 手动上传
    uploadFile() {
      const formData = new FormData()
      this.fileList.forEach(element => {
        if (element.status !== 'success') {
          formData.append('reportFile', element.raw, element.raw.name)
        }
      })
      // notice对象封装
      formData.append('heading', this.modifyNotice.heading)
      formData.append('noticeContent', this.modifyNotice.noticeContent)
      formData.append('createName', this.$store.getters.info.username)
      formData.append('readedNum', 0)
      formData.append('editStatus', this.modifyNotice.editStatus)
      formData.append('id', this.modifyNotice.noticeId)
      console.log('id是：' + this.modifyNotice.noticeId)
      updateFile(formData).then(data => {
        this.fileList.forEach(element => {
          element.status = 'success'
        })
        this.$notify({
          title: '成功',
          type: 'success',
          message: '公告发布成功!'
        })
      }).catch(response => {
        this.$notify.error({
          title: '错误',
          message: '公告发布失败！'
        })
      })
    },
    // 上传文件格式判断
    handleChange(file, fileList) {
      this.fileList = fileList
      if (this.fileList !== null) {
        this.isButtonDisabled = false
      }
    },
    updateNoticeDialog(noticeId) {
      this.list.forEach(element => {
        if (element.id === noticeId) {
          this.modifyNotice.noticeId = noticeId
          this.modifyNotice.newsDate = element.newsDate
          this.modifyNotice.createName = element.createName
          this.modifyNotice.editStatus = element.editStatus
          this.modifyNotice.heading = element.heading
          this.modifyNotice.noticeContent = element.noticeContent
          return false
        }
      })
      getAttachedfileNotice(noticeId).then(data => {
        // 获取公告附件
        this.noticeAttachedfile = data
      })
      this.modifyDialogVisible = true
    },
    /* 当点击删除文件的时候，删除文件 */
    handleClose(id, noticeId) {
      console.log(id)
      console.log(noticeId)
      deleteAttachedfileById(id).then(data => {
        console.log('已删除')
        console.log(data)
        if (data > 0) {
          this.queryAttachedFileFun(noticeId)
        }
      })
    },
    /* 该方法执行查询公告附件 */
    queryAttachedFileFun(noticeId) {
      getAttachedfileNotice(noticeId).then(data => {
        this.noticeAttachedfile = data
        console.log(data)
      })
    },
    /* 更新公告 */
    updateNotice(noticeId) {
      this.uploadFile()
      this.modifyDialogVisible = false
      this.$router.go(0)
    },
    removeOpenDialog(index, value) {
      this.keyIndex = index
      this.keyId = value
      this.removeDialogVisible = true
    },
    // 点击移除文件
    handleRemove(fileList) {
      console.log(fileList)
    },
    removeNotice() {
      this.removeDialogVisible = false
      var params = { id: this.keyId }
      removeById(params)
        .catch(err => {
          console.log(err)// 错误信息
        })
      this.list.splice(this.keyIndex, 1)
    }
  }
}
</script>

