<template>
  <div class="app-container">
    <div class="filter-container">
      <el-card style="font-size: 35px">公告一览</el-card>
      <hr size="5" color="#0066cc">
      <br>
      <el-table
        v-loading="listLoading"
        :data="list"
        border
        fit
        style="width: 100%"
        highlight-current-row
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="index" fixed="left" label="No." width="55" sortable="custom" align="center"/>
        <el-table-column fixed="left" min-width="300" label="标题" align="center">
          <template slot-scope="scope">
            <el-tooltip placement="right" trigger="hover" content="标题~">
              <div class="text-tohandle" @click="toDetail(scope.row)">
                <span v-if="scope.row.readStatus === 'unread'">
                  <el-badge is-dot class="item"><u>{{ scope.row.heading }}</u></el-badge>
                </span>
                <span v-else>
                  <u>{{ scope.row.heading }}</u>
                </span>
              </div>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column width="200" label="阅读情况" align="center">
          <template slot-scope="scope">
            <el-tooltip placement="right" trigger="hover" content="阅读情况~">
              <el-tag type="warning">{{ scope.row.readedNum }} / {{ scope.row.totalNotice }}</el-tag>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column prop="createName" width="200" show-overflow-tooltip label="发布人" align="center"/>
        <el-table-column prop="newsDate" width="135" label="发布时间" align="center">
          <template slot-scope="scope">
            <el-tag>{{ scope.row.newsDate.substr(0,10) }}</el-tag>
          </template>
        </el-table-column>
      </el-table>
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
import { queryNotices, updateNoticeReadById } from '@/api/notice'
export default {
  name: 'Index',
  components: { Pagination },
  data() {
    return {
      list: [],
      total: 0,
      listLoading: true,
      multipleTable: [],
      formInline: {
        heading: '',
        readerName: '',
        createName: '',
        startDate: '',
        endDate: '',
        editStatus: 'draft'
      },
      page: {
        pageNumber: 1,
        pageSize: 10
      },
      pickerOptions2: {
        shortcuts: [
          {
            text: '最近一周',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(
                start.getTime() - 3600 * 1000 * 24 * 7
              )
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近一个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(
                start.getTime() - 3600 * 1000 * 24 * 30
              )
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '最近三个月',
            onClick(picker) {
              const end = new Date()
              const start = new Date()
              start.setTime(
                start.getTime() - 3600 * 1000 * 24 * 90
              )
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      startTimeAndEndTime: ''
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
      var page = this.page
      var conditon = {}
      conditon.username = this.$store.getters.info.username
      var that = this
      queryNotices(conditon, page)
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
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    toDetail(obj) {
      if (obj.readStatus === 'unread' && obj.readedNum < obj.totalNotice) {
        var params = { id: obj.id, username: this.$store.getters.info.username }
        updateNoticeReadById(params)
          .catch(err => {
            console.log(err)// 错误信息
          })
        this.$router.push({
          path: '/notice/content',
          query: {
            id: obj.id
          }
        })
      } else {
        this.$router.push({
          path: '/notice/content',
          query: {
            id: obj.id
          }
        })
      }
    },
    toEdit(value) {
      this.$router.push({
        path: '/notice/create',
        query: {
          id: value
        }
      })
    }
  }
}
</script>
