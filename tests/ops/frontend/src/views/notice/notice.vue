<template>
  <div class="app-container">
    <el-dialog
      :visible="noOutDated && status && isRead"
      :show-close="showClo"
      title="通知公告"
      width="30%"
      center="center">
      <span style="text-align: center;display:block;font-weight:bold;color:#409EFF;font-size:15px">{{ notice === null ? null : notice.heading }}</span><br>
      <!--       发布者：<span> {{ notice.createName }} </span><br><br> -->
      发布日期：<span> {{ notice === null ? null : notice.newsDate }} </span><br><br>
      发布内容：<span style="color:red;font-size:20px"><br><br>{{ notice === null ? null : notice.noticeContent }}</span><br><br><br><br>
      <!-- 公告对象：<span>全体成员，祝大家工作愉快</span><br><br> -->
      <label>附件：</label>
      <el-tag
        v-for="files in noticeAttachedfile"
        :key="files.fileName"
        :disable-transitions="false"
        type="warning"
        style="margin-top: 5px; margin-left: 5px;">
        {{ files.fileName }}
      </el-tag>
      <el-button :disabled=" noticeAttachedfile === null ? 0 : noticeAttachedfile.length > 0 ? false : true" icon="el-icon-download" type="primary" style="margin-top: 5px; margin-left: 5px;" @click="downLoadNoticeAttachedFile()" >下载附件</el-button>
      <span slot="footer" class="dialog-footer">
        <el-button :disabled="isClick" type="primary" @click="readed(notice.id)">关闭</el-button>&nbsp;&nbsp;
        <el-checkbox v-model="tips">不再提示本条公告</el-checkbox>
      </span>
    </el-dialog>
  </div>
</template>
<script>
import { getLatestNotice, getLatestAttachedfileNotice, addNoticeReader, getNoticeReader } from '@/api/notice'
import { downLoadAttachedFile } from '@/utils/downLoadAndUpLoad'
export default {
  name: 'Notice',
  data() {
    return {
      isRead: true,
      tips: false,
      notice: null,
      noticeAttachedfile: null,
      showClo: false,
      noOutDated: true,
      downloadNoticeId: '',
      status: false,
      noticeReader: {
        id: '',
        noticeId: '',
        username: '',
        readStatus: ''
      },
      isClick: false
    }
  },
  created() {
    this.selectLatestNotice()
  },
  methods: {
    selectLatestNotice() {
      // 获取最新的公告列表
      // 公告列表显示的两个条件，第一，公告没有过时，第二，公告未读
      getLatestNotice().then(data => {
        this.notice = data
        if (this.notice.editStatus === 'outdated') {
          this.noOutDated = false
        }
        // 查看用户是否已阅公告
        if (JSON.stringify(this.notice) !== '""') {
          getNoticeReader(this.notice.id, this.$store.getters.info.username).then(data => {
            if (data != null && data.readStatus === 'readed') {
              this.status = false
            } else {
              this.status = true
            }
          })
        }
        // this.findNoticeReaders(this.notice)
      })
      // 获取公告附件
      getLatestAttachedfileNotice().then(data => {
        this.noticeAttachedfile = data
      })
    },
    // 点击已阅触发的方法
    readed(noticeId) {
      // 预防多次点击
      this.isClick = true
      setTimeout(() => {
        this.isClick = false
      }, 2000)
      this.noticeReader.noticeId = noticeId
      this.noticeReader.username = this.$store.getters.info.username
      if (this.tips) {
        this.noticeReader.readStatus = 'readed'
      } else {
        this.noticeReader.readStatus = 'readed1'
      }
      addNoticeReader(this.noticeReader).then(data => {
        // data为1表示增加成功
        console.log(data)
        if (data > 0 && this.tips) {
          this.$notify({
            title: '消息',
            message: '你已阅读公告，将不再提示!'
          })
        }
      })
      this.isRead = false
    },
    downLoadNoticeAttachedFile() {
      this.noticeAttachedfile.forEach(element => {
        this.downloadNoticeId = element.noticeId
        return false
      })
      const params = { noticeId: this.downloadNoticeId }
      const fileName = '附件.zip'
      downLoadAttachedFile(params, fileName)
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
.app-container {
 height: 80px;
 line-height: 20px;
}
</style>
