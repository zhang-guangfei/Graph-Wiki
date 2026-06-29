<template>
  <div class="app-container">
    <div class="filter-container">
      <el-card style="font-size: 35px">{{ heading }}</el-card>
      <hr size="5" color="#0066cc">
      <el-card>
        <label>附件：</label>
        <el-tag
          v-for="tag in fileTags"
          :key="tag"
          :disable-transitions="false"
          type="warning"
          style="margin-top: 5px; margin-left: 5px;">
          {{ tag }}
        </el-tag>
        <el-button :disabled="fileTags.length > 0 ? false : true" icon="el-icon-download" type="primary" style="margin-top: 5px; margin-left: 5px;" @click="downLoadNoticeAttachedFile()" >下载附件</el-button>
      </el-card>
      <hr size="5" color="#0066cc">
      <el-card style="font-size: 18px; min-height: 500px;">
        <div style="word-wrap: break-word; word-break: break-all;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{{ noticeContent }}</div>
      </el-card>
    </div>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'// Secondary package based on el-pagination
import { getNoticeById, getAttachedFileByNoticeId } from '@/api/notice'
import { downLoadAttachedFile } from '@/utils/downLoadAndUpLoad'
export default {
  name: 'Index',
  components: { Pagination },
  data() {
    return {
      heading: '',
      noticeContent: '',
      attachedFile: '',
      fileTags: []
    }
  },
  created() {
    this.initNoticeContent()
  },
  methods: {
    initNoticeContent() {
      const id = { id: this.$route.query.id }
      var that = this
      getNoticeById(id)
        .then(data => {
          that.noticeContent = data.noticeContent
          that.heading = data.heading
        })
        .catch(err => {
          console.log(err)// 错误信息
        })
      const noticeId = { noticeId: this.$route.query.id }
      getAttachedFileByNoticeId(noticeId)
        .then(data => {
          console.log(data)
          data.forEach(element => {
            that.fileTags.push(element.fileName)
          })
        })
        .catch(err => {
          console.log(err)// 错误信息
        })
    },
    downLoadNoticeAttachedFile() {
      const params = { noticeId: this.$route.query.id }
      const fileName = '附件.zip'
      downLoadAttachedFile(params, fileName)
    }
  }
}
</script>

