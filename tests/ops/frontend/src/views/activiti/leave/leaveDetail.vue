<template>
  <div>
    <el-dialog :visible.sync="dialogTerminalDetailVisible" title="请假详情" width="850px" @close="dialogTerminalDetailVisible = false">
      <el-tag style="width: 810px;height:36px">请假人ID:{{ formData.leaveuserId }}</el-tag>
      <el-tag style="width: 810px;height:36px">姓名:{{ formData.leaveusername }}</el-tag>
      <el-tag style="width: 810px;height:36px">请假原因:{{ formData.reason }}</el-tag>
      <el-tag style="width: 810px;height:36px">请假天数:{{ formData.leaveday }}</el-tag>
      <!-- <el-tag style="width: 710px;height:36px">审批类型:{{ approvalDetailData.approvalType }}</el-tag>
      <el-tag style="width: 710px;height:36px">审批意见:{{ approvalDetailData.approvalOpinions }}</el-tag>
      <el-tag style="width: 710px;height:36px">审批人ID:{{ approvalDetailData.approverId }}</el-tag>
      <el-tag style="width: 710px;height:36px">审批人姓名:{{ approvalDetailData.approverName }}</el-tag>
      <el-tag style="width: 710px;height:36px">审批时间:{{ approvalDetailData.approvalTime }}</el-tag> -->
      <div style="margin-top:20px">
        <vxe-grid
          :align="allAlign"
          :data="approvalDetailData"
          :loading="loading"
          show-overflow="title"
          border
          size="mini"
          round
          highlight-hover-row>
          <vxe-table-column field="approvalType" width="100px" title="审批类型"/>
          <vxe-table-column field="approvalOpinions" min-width="300px" title="审批意见"/>
          <vxe-table-column field="approverId" min-width="150px" title="审批人ID"/>
          <vxe-table-column field="approverName" min-width="100px" title="审批人姓名"/>
          <vxe-table-column field="approvalTime" min-width="150px" title=" 审批时间"/>
        </vxe-grid>
      </div>
    </el-dialog>
  </div>
</template>
<script>
export default{
  data() {
    return {
      allAlign: 'left',
      loading: false,
      dialogTerminalDetailVisible: false,
      formData: {},
      approvalDetailData: [
        // approvalType: '',
        // approvalOpinions: '',
        // approverId: '',
        // approverName: '',
        // approvalTime: ''
      ]
    }
  }
}
</script>
