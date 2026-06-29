<template>
  <el-dialog :visible.sync="windowVisible" :close-on-click-modal="false" append-to-body @close="close">
    <el-form ref="formData" :model="formData" label-width="90px">
      <el-form-item label="请假人ID:" prop="leaveuserId">
        <el-input v-model="formData.leaveuserId" @input="change($event)"/>
      </el-form-item>
      <el-form-item :label="label.name" prop="leaveusername">
        <el-input v-model="formData.leaveusername" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="岗位编号:" prop="positionId">
        <el-input v-model="formData.positionId" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="请假原因:" prop="reason">
        <el-input v-model="formData.reason" :rows="2" type="textarea" placeholder="请输入内容" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="请假天数:" prop="leaveday">
        <el-input v-model="formData.leaveday" type="number" @input="change($event)"/>
      </el-form-item>
      <!-- <el-form-item label="审批人ID:" prop="approverId">
        <el-input v-model="formData.approverId" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="审批人姓名:" prop="approverName">
        <el-input v-model="formData.approverName" @input="change($event)"/>
      </el-form-item> -->
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="close">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
// import { findClientId } from '@/api/leave'
import { addLeave, updateLeave } from '@/api/leave'
export default {
  data() {
    return {
      updateFlag: false,
      windowVisible: false,
      submitLoading: false,
      formData: {},
      label: {
        name: '姓' + '\xa0\xa0\xa0\xa0\xa0\xa0\xa0' + '名:'
      }
    }
  },
  methods: {
    onSubmit() {
      this.submitLoading = true
      // const that = this
      // formData1 = that.formData
      // const authorizedGrantTypes = formData1.authorizedGrantTypeList.join(',')
      // 复制一个表单对象 formDate
      // const formData = JSON.parse(JSON.stringify(this.formData))
      // formData.authorizedGrantTypes = authorizedGrantTypes
      // this.$delete(formData, 'authorizedGrantTypeList')
      if (this.updateFlag) {
        updateLeave(this.formData).then(data => {
          this.$notify({
            title: '成功',
            type: 'success',
            message: '更新成功!'
          })
          this.loading = false
          this.windowVisible = false
          // 操作完成后，触发父组件的close-form事件
          this.$refs.formData.resetFields()
          this.$emit('close-form')
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '修改失败！'
          })
        })
      } else {
        addLeave(this.formData).then(data => {
          this.$notify({
            title: '成功',
            type: 'success',
            message: '保存成功!'
          })
          this.$refs.formData.resetFields()
          this.loading = false
          this.windowVisible = false
          this.$emit('close-form')
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '添加失败！'
          })
        })
      }
    },
    close() {
      this.windowVisible = false
      this.$refs.formData.resetFields()
      this.$emit('close-form')
    },
    change(e) {
      this.$forceUpdate()
    },
    getRow(row) {
      this.updateFlag = true
      setTimeout(() => {
        this.formData = {
          ...row
          // authorizedGrantTypeList: row.authorizedGrantTypes.split(',')
        }
      }, 200)
    },
    toAdd() {
      this.formData.leaveuserId = this.$store.getters.info.userId
      this.formData.leaveusername = this.$store.getters.info.psnName
      this.formData.positionId = this.$store.getters.position.positionId
    }
  }
}
</script>

