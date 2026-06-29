<template>
  <div class="app-container">
    <div class="filter-container">
      <el-form :inline="true" :model="form" style="height:40px">
        <el-form-item>
          <!-- <el-input v-model="form.processDefinitionKey" placeholder="流程定义key" style="width: 165px" size="small" class="filter-item" @keyup.enter.native="getByCondition"/> -->
          <el-select v-model="form.processDefinitionKey" size="mini" placeholder="流程定义key" clearable @change="getByCondition">
            <el-option
              v-for="item in processDefinitionKeyList"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-tooltip content="重置" placement="top">
            <el-button type="info" icon="el-icon-close" size="small" circle @click="clearFormInline()"/>
          </el-tooltip>
        </el-form-item>
        <el-form-item>
          <el-button id="query" type="primary" size="small" class="filter-item" style="margin-bottom:3px" @click="getByCondition">查询</el-button>
        </el-form-item>
      </el-form>
      <vxe-grid
        ref="xGrid"
        :loading="loading"
        :pager-config="tablePage"
        :data="tableData"
        :columns="tableColumn"
        border
        size="mini"
        show-overflow
        resizable
        row-key
        highlight-hover-row
        @page-change="handlePageChange">
        <template v-slot:operate="{ row }">
          <el-button v-if="row.taskAssignee" size="mini" icon="el-icon-s-check" title="处理" circle @click="detailEvent(row)"/>
          <el-button v-else size="mini" icon="el-icon-check" title="签收" circle @click="signEvent(row)"/>
          <!-- <el-button size="mini" icon="el-icon-view" title="查看" circle @click="checkEvent(row)"/> -->
        </template>
        <template v-slot:fmtDate="{ row }">
          <span>{{ dateFormat('YYYY-mm-dd HH:MM',row.taskCreatedDate) }}</span>
        </template>
      </vxe-grid>
    </div>
  </div>
</template>
<script>
import { query, sign, detail, findProcessDefinitionKeyList } from '@/api/approvalCenter'
export default {
  name: 'TodoTask',
  data() {
    return {
      loading: false,
      tableData: [],
      form: {},
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 100,
        align: 'right',
        pageSizes: [100, 500, 1000],
        layouts: [
          'Sizes',
          'PrevJump',
          'PrevPage',
          'Number',
          'NextPage',
          'NextJump',
          'FullJump',
          'Total'
        ],
        perfect: true
      },
      tableColumn: [
        { field: 'processDefineName', title: '业务', width: 200, align: 'center' },
        { field: 'columnOne', title: '标题1', width: 200, align: 'center' },
        { field: 'columnTwo', title: '标题2', width: 200, align: 'center' },
        { field: 'taskName', title: '当前任务节点', midth: 200, align: 'center' },
        { field: 'taskCreatedDate', title: '任务创建时间', midth: 200, align: 'center', slots: { default: 'fmtDate' }},
        { field: 'processInstanceStatus', title: '流程状态', midth: 150, align: 'center' },
        { title: '操作', width: 150, slots: { default: 'operate' }, align: 'center', fixed: 'right' }
      ],
      processDefinitionKeyList: []
    }
  },
  watch: {
    '$route': {
      handler(val, oldval) {
        if (val.name === 'TodoTask') {
          this.getByCondition()
        }
      }
    }
  },
  mounted() {
    this.getProcessDefinitionKeyList()
  },
  created() {
    this.getByCondition()
  },
  methods: {
    // 任务签收
    signEvent(row) {
      this.$confirm('此操作将签收任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        var params = {
          taskId: row.taskId,
          userId: this.$store.getters.info.userId
        }
        sign(params).then((response) => {
          this.smcSuccessMsg('签收成功！')
          this.loading = false
          this.getByCondition()
        }).catch(() => {
          this.loading = false
        })
      })
    },
    // 进入审核界面
    detailEvent(row) {
      var params = {
        taskId: row.taskId
      }
      var taskOwner = row.taskOwner
      var processInstanceId = row.processInstanceId
      detail(params).then((data) => {
        var path = data.content.path
        var taskId = data.content.taskId
        var businessKey = data.content.businessKey
        // 如果是特价变更则需要传递不同的bussinessKey为了显示特价相关详情
        if (row.processDefineKey === 'specialPriceChange') {
          var specialPriceChangeBusinessKey = data.content.businessKey
          businessKey = row.columnOne
        }
        var processDefinitionKey = data.content.processDefinitionKey
        var activitiId = data.content.activitiId
        var paymentApplyNo = row.columnOne
        var applyNo = row.columnOne
        var customerNo = row.columnTwo
        if (processDefinitionKey === 'order-delete') {
          this.$router.push({
            path: path,
            query: {
              orderNo: row.columnOne
            }
          })
          return
        }
        this.$router.push({
          path: path,
          query: { taskId, businessKey, specialPriceChangeBusinessKey, processDefinitionKey, activitiId, processInstanceId, taskOwner, customerNo, paymentApplyNo, applyNo }
        })
      })
    },
    // 查看
    // checkEvent(row) {
    //   this.$confirm('此操作将查看任务, 是否继续?', '提示', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     type: 'warning'
    //   }).then(() => {
    //     this.loading = true
    //     var params = {
    //       executionId: row.task.executionId,
    //       processInstanceId: row.task.processInstanceId
    //     }
    //     check(params).then((response) => {
    //       this.$notify({
    //         title: '成功',
    //         message: '查看成功',
    //         type: 'success',
    //         duration: 2000
    //       })
    //       this.loading = false
    //       this.getByCondition()
    //     }).catch(() => {
    //       this.loading = false
    //       this.$notify.error({
    //         title: '错误',
    //         message: '查看失败'
    //       })
    //     })
    //   })
    // },
    getProcessDefinitionKeyList() {
      findProcessDefinitionKeyList().then((data) => {
        data.forEach(element => {
          this.processDefinitionKeyList.push({ value: element.processDefinitionKey, label: element.processDefinitionName })
        })
      }).catch((e) => {
      })
    },
    getByCondition() {
      this.loading = true
      this.form.userId = this.$store.getters.info.userId
      this.form.pageNumber = this.tablePage.currentPage
      this.form.pageSize = this.tablePage.pageSize
      this.form.firstResult = (this.tablePage.currentPage - 1) * this.tablePage.pageSize
      this.form.maxResults = this.tablePage.currentPage * this.tablePage.pageSize
      const conditions = this.form
      query(conditions).then((data) => {
        this.tableData = data.content
        this.tablePage.total = data.totalItems
        this.loading = false
      }).catch((e) => {
        this.loading = false
      })
    },
    dateFormat(fmt, date) {
      let ret = ''
      date = new Date(date)
      const opt = {
        'Y+': date.getFullYear().toString(), // 年
        'm+': (date.getMonth() + 1).toString(), // 月
        'd+': date.getDate().toString(), // 日
        'H+': date.getHours().toString(), // 时
        'M+': date.getMinutes().toString(), // 分
        'S+': date.getSeconds().toString() // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
      }
      for (const k in opt) {
        ret = new RegExp('(' + k + ')').exec(fmt)
        if (ret) {
          fmt = fmt.replace(
            ret[1],
            ret[1].length === 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
          )
        }
      }
      return fmt
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.getByCondition()
    },
    clearFormInline() {
      this.form = {}
    }
  }
}
</script>
