<template>
  <div>
    <vxe-table
      :data="execStepDataList"
      :edit-config="{trigger: 'click', mode: 'cell'}"
      :keyboard-config="{isArrow: true,isEnter: true,isEdit: true,isTab: true}"
      :mouse-config="{selected: true}"
      auto-resize
      height="600"
      border
      size="mini"
      style="margin: 25px"
    >
      <vxe-table-column type="checkbox" width="50"/>
      <vxe-table-column field="stepName" title="步骤名称"/>
      <vxe-table-column field="execSeq" title="执行顺序" show-overflow/>
      <vxe-table-column field="execTime" title="执行时间" show-overflow/>
      <vxe-table-column field="statusName" title="执行结果" show-overflow/>
      <vxe-table-column field="updateTime" title="更新时间" show-overflow/>
      <vxe-table-column field="remark" title="备注" show-overflow/>
      <vxe-table-column field="opt" title="操作">
        <template #default="{ row }">
          <!-- <vxe-button type="text" status="primary" content="手动执行" @click="uiExecYdPd(row)" /> -->
          <vxe-button
            v-if="!row.isExecuting"
            type="text"
            status="primary"
            content="手动执行"
            @click="uiExecYdPd(row)"
          />
          <vxe-button
            v-else
            :disabled="true"
            type="text"
            status="primary"
            icon="vxe-icon-indicator roll"
            content="执行中"
          />
          <vxe-button v-if="row.stepCode !== 'new_yd_pd'&& row.stepCode !== 'yd_data_extract' && row.stepCode !== 'wms_dhwr_extract' && row.stepCode !== 'insert_pd_bill' && row.stepCode !== 'output_pd_report' " type="text" status="primary" content="导出" @click="exportYdPdStep(row)" />
          <!-- <vxe-button type="text" status="primary" content="删除" @click="delOtherDataById(row)" /> -->
        </template>
      </vxe-table-column>
    </vxe-table>
    <el-dialog
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      title="提示"
      width="30%"
      height="200px">
      <span><h2>{{ msg }}</h2></span>
    </el-dialog>
  </div>
</template>
<script>
import { uiExecYdPd, getExecStepList, exportYdPdStep } from '@/api/pd/pd'
export default {
  name: 'PdExecStepManage',
  data() {
    return {
      execStepDataList: [],
      dialogVisible: false,
      msg: ''
    }
  },
  mounted() {
    this.getExecStepList()
  },
  methods: {
    getExecStepList() {
      getExecStepList().then(res => {
        if (res.success) {
          // 为每一行添加独立的加载状态字段
          this.execStepDataList = res.content.map(item => ({
            ...item,
            isExecuting: false // 初始化为未加载状态
          }))
        } else {
          this.execStepDataList = []
        }
      })
    },
    exportYdPdStep(row) {
      this.dialogVisible = true
      this.msg = '已开始导出,请耐心等待'
      var methodCode = row.stepCode
      exportYdPdStep(methodCode).then(res => {
        console.log('数据导出响应=>', res)
        if (res.size === 0) {
          this.dialogVisible = true
          this.msg = '暂无数据导出'
          return
        }
        var fileName = row.stepName + '导出清单.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.dialogVisible = false
      }).catch(error => {
        console.error(error)
        this.dialogVisible = true
        this.msg = '导出失败: ' + error
      })
    },
    // getExecStepList() {
    //   getExecStepList().then(res => {
    //     if (res.success) {
    //       this.execStepDataList = res.content
    //     } else {
    //       this.execStepDataList = []
    //     }
    //   })
    // },
    uiExecYdPd(Row) {
      // 设置当前行加载状态
      Row.isExecuting = true
      console.log(Row)
      uiExecYdPd(Row.stepCode).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.getExecStepList()
        } else {
          this.$message.error(res.message)
        }
        // 无论成功失败，都结束当前行加载状态
        Row.isExecuting = false
      }).catch(error => {
        console.error(error)
        // 错误时结束加载状态
        Row.isExecuting = false
      })
    }
  }
}
</script>
<style scoped>
</style>
