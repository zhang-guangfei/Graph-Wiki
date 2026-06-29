<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="approvalIndex">
        <el-card>
          <el-form label-position="left">
            <el-row>
              <el-col :span="3">
                <el-form-item style="margin-bottom:0px;" label-width="28px" label="ID:">
                  {{ leaveData.leaveuserId }}
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item style="margin-bottom:0px;" label-width="55px" label="姓名:">
                  {{ leaveData.leaveusername }}
                </el-form-item>
              </el-col>
              <el-col :span="3">
                <el-form-item style="margin-bottom:0px;" label-width="100px" label="请假天数:">
                  {{ leaveData.leaveday }}
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item style="margin-bottom:0px;" label-width="100px" label="请假原因:">
                  {{ leaveData.reason }}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </div>
      <hr size="1" color="#5EA7D3">
      <div class="bottom">
        <el-card style="border-radius: 15px;border:2px">
          <div style="margin-top:20px">
            <vxe-table
              :align="allAlign"
              :data="approveList"
              :loading="loading"
              show-header-overflow
              border
              show-overflow
              size="mini"
              round
              highlight-hover-row>
              <vxe-table-column type="seq" width="60px" title="序号"/>
              <vxe-table-column field="approvalType" width="200px" title="审批类型"/>
              <vxe-table-column field="approvalOpinions" min-width="300px" title="审批意见"/>
              <vxe-table-column field="approverId" min-width="300px" title="审批人ID"/>
              <vxe-table-column field="approverName" min-width="150px" title="审批人姓名"/>
              <vxe-table-column field="approvalTime" min-width="200px" title=" 审批时间"/>
            </vxe-table>
            <el-form style="margin-bottom:20px">
              <el-form-item label-width="auto" style="margin-bottom:0px;">
                <el-input
                  v-model="textarea"
                  :rows="4"
                  style="width:600px;margin-top:7px;border-radius:30px;"
                  type="textarea"
                  placeholder="请输入您的审批意见"
                  @keyup.enter.native="sendMessage()"
                />
                <el-button size="small" type="primary" @click="agree()">同意</el-button>
                <el-button size="small" type="primary" @click="refuse()">拒绝</el-button>
                <el-button size="small" type="primary" @click="rejectEvent()">驳回</el-button>
                <el-select v-model="rejectAssignNode" placeholder="节点信息" size="small">
                  <el-option v-for="item in rejectAssignNodeList" :key="item.value" :label="item.label" :value="item.value"/>
                </el-select>
                <el-button size="small" type="primary" @click="rejectToDesignatedNode()">驳回至指定节点</el-button>
                <el-input v-model="userId" style="width:199px;" size="small" placeholder="请输入工号"/>
                <el-button size="small" type="primary" @click="delegateEvent()">委派</el-button>
                <el-button size="small" type="primary" @click="transferTaskEvent()">转办</el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
      </div>
    </div>
  </div>
</template>
<script>
import { detail, leaveApproval } from '@/api/leave'
import { findApprovalDetailByBusinessKey, rejectAssignNodeAggregate, rejectAssignNode, reject, delegate, transferTask, resolve } from '@/api/approvalCenter'
export default {
  name: 'LeaveApproval',
  data() {
    return {
      //  可驳回的节点集合
      rejectAssignNodeList: [],
      allAlign: 'center',
      leaveData: {},
      rejectAssignNode: '',
      textarea: '',
      approveList: [],
      loading: false,
      userId: ''
    }
  },
  created() {
    this.getRejectAssignNodeAggregate()
    this.getLeaveDetail()
    this.getApprovalDetailByBusinessKey()
  },
  methods: {
    //  获取可驳回的节点集合
    getRejectAssignNodeAggregate() {
      var processInstanceId = {
        processInstanceId: this.$route.query.processInstanceId
      }
      rejectAssignNodeAggregate(processInstanceId).then((data) => {
        for (var key in data.content) {
          this.rejectAssignNodeList.push({ label: data.content[key], value: key })
        }
      }).catch((e) => {
        console.log(e)
      })
    },
    getLeaveDetail() {
      var leaveId = {
        leaveId: this.$route.query.businessKey
      }
      detail(leaveId).then((data) => {
        if (data.content === null) {
          this.leaveData = {}
        }
        this.leaveData = data.content
        this.loading = false
      }).catch((e) => {
        this.loading = false
      })
    },
    getApprovalDetailByBusinessKey() {
      var businessKey = this.$route.query.businessKey
      findApprovalDetailByBusinessKey(businessKey).then((data) => {
        this.approveList = data.content
        this.loading = false
      }).catch((e) => {
        this.loading = false
      })
    },
    // 同意审批
    agree() {
      var activitiId = this.$route.query.activitiId
      // 审批实体
      var approvalBean = {
        taskId: this.$route.query.taskId,
        businessKey: this.$route.query.businessKey,
        approvalType: '同意',
        approvalOpinions: this.textarea,
        approverId: this.$store.getters.info.userId,
        approverName: this.$store.getters.info.psnName,
        positionId: this.$store.getters.position.positionId,
        positionName: this.$store.getters.position.positionName
      }
      if (this.$route.query.owner) {
        resolve({ taskId: this.$route.query.taskId }).then((response) => {
          this.$notify({
            title: '成功',
            message: '处理成功',
            type: 'success',
            duration: 2000
          })
          // 审批后，刷新
          // this.getApprovalDetailByBusinessKey(this.$route.query.businessKey)
          this.$router.push({
            path: '/workflow/todo/todoTask'
          })
        }).catch(() => {
          this.$notify.error({
            title: '错误',
            message: '处理失败'
          })
        })
        return
      }
      leaveApproval(approvalBean, activitiId).then((response) => {
        this.$notify({
          title: '成功',
          message: '处理成功',
          type: 'success',
          duration: 2000
        })
        // 审批后，刷新
        this.getApprovalDetailByBusinessKey(this.$route.query.businessKey)
        this.textarea = ''
        this.$router.push({
          path: '/workflow/todo/todoTask'
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '处理失败'
        })
      })
    },
    // 拒绝
    refuse() {
      const approve = { b: '管理本部 电算室', c: '李七', d: '拒绝', e: '2020-10-30 10:33', f: this.textarea }
      this.approveList.push(approve)
      this.textarea = ''
    },
    // 驳回
    rejectEvent() {
      var approvalBean = {
        taskId: this.$route.query.taskId,
        businessKey: this.$route.query.businessKey,
        approvalType: '驳回',
        approvalOpinions: this.textarea,
        approverId: this.$store.getters.info.userId,
        approverName: this.$store.getters.info.psnName,
        positionId: this.$store.getters.position.positionId,
        positionName: this.$store.getters.position.positionName
      }
      reject(approvalBean, this.$route.query.processInstanceId).then((response) => {
        this.$notify({
          title: '成功',
          message: '驳回成功',
          type: 'success',
          duration: 2000
        })
        // 驳回后，刷新
        // this.getApprovalDetailByBusinessKey(this.$route.query.businessKey)
        this.textarea = ''
        this.$router.push({
          path: '/workflow/todo/todoTask'
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '驳回失败'
        })
      })
    },
    // 驳回到指定节点
    rejectToDesignatedNode() {
      var approvalBean = {
        taskId: this.$route.query.taskId,
        businessKey: this.$route.query.businessKey,
        approvalType: '驳回到指定节点',
        approvalOpinions: this.textarea,
        approverId: this.$store.getters.info.userId,
        approverName: this.$store.getters.info.psnName,
        positionId: this.$store.getters.position.positionId,
        positionName: this.$store.getters.position.positionName
      }
      rejectAssignNode(approvalBean, this.rejectAssignNode).then((response) => {
        this.$notify({
          title: '成功',
          message: '驳回成功',
          type: 'success',
          duration: 2000
        })
        // 审批后，刷新
        // this.getApprovalDetailByBusinessKey(this.$route.query.businessKey)
        this.textarea = ''
        this.$router.push({
          path: '/workflow/todo/todoTask'
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '驳回失败'
        })
      })
    },
    // 委派任务: 被委派人办理完后，回到委派人节点
    delegateEvent() {
      var params = {
        taskId: this.$route.query.taskId,
        userId: this.userId
      }
      delegate(params).then((response) => {
        this.$notify({
          title: '成功',
          message: '委派成功',
          type: 'success',
          duration: 2000
        })
        // 审批后，刷新
        // this.getApprovalDetailByBusinessKey(this.$route.query.businessKey)
        this.$router.push({
          path: '/workflow/todo/todoTask'
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '委派失败'
        })
      })
    },
    // 转办任务：被委派人办理完后，进入下一个节点
    transferTaskEvent() {
      var params = {
        taskId: this.$route.query.taskId,
        userId: this.userId
      }
      transferTask(params).then((response) => {
        this.$notify({
          title: '成功',
          message: '转办任务成功',
          type: 'success',
          duration: 2000
        })
        // 审批后，刷新
        // this.getApprovalDetailByBusinessKey(this.$route.query.businessKey)
        this.$router.push({
          path: '/workflow/todo/todoTask'
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '转办任务失败'
        })
      })
    }
  }
}
</script>
<style scoped>
  .bottom{
    margin-bottom: 50px;
  }
</style>
