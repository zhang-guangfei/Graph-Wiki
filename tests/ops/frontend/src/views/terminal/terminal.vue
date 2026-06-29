<template>
  <el-dialog :visible.sync="windowVisible" :close-on-click-modal="false" append-to-body @close="close">
    <el-form ref="formData" :model="formData" :rules="rules" label-width="80px">
      <el-form-item label="编号" prop="clientId">
        <el-input v-model="formData.clientId" readonly @input="change($event)"/>
      </el-form-item>
      <el-form-item label="密钥" prop="clientSecret">
        <el-input v-model="formData.clientSecret" readonly @input="change($event)"/>
      </el-form-item>
      <el-form-item label="授权模式" prop="authorizedGrantTypeList">
        <el-checkbox-group v-model="formData.authorizedGrantTypeList">
          <el-checkbox v-for="item in authorizedGrantList" :label="item.value" :key="item.value">{{ item.label }}</el-checkbox>
          <!-- <el-checkbox label="authorization_code">授权码模式</el-checkbox>
          <el-checkbox label="client_credentials">客户端模式</el-checkbox>
          <el-checkbox label="refresh_token" >刷新模式</el-checkbox>
          <el-checkbox label="implicit" >简化模式</el-checkbox> -->
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="域" prop="scope">
        <el-input v-model="formData.scope" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="自动放行" prop="autoApprove">
        <el-radio-group v-model="formData.autoApprove">
          <el-radio label="true">是</el-radio>
          <el-radio label="false">否</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item label="令牌时效" prop="accessTokenValidity">
        <el-input v-model="formData.accessTokenValidity" type="number" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="刷新时效" prop="refreshTokenValidity">
        <el-input v-model="formData.refreshTokenValidity" type="number" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="回调地址" prop="webServerRedirectUri">
        <el-input v-model="formData.webServerRedirectUri">
          <template slot="prepend">http://</template>
        </el-input>
      </el-form-item>
      <el-form-item label="权限" prop="authorities">
        <el-input v-model="formData.authorities" @input="change($event)"/>
      </el-form-item>
      <el-form-item label="扩展信息" prop="additionalInformation">
        <el-input v-model="formData.additionalInformation" type="textarea" @input="change($event)"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="onSubmit">提交</el-button>
        <el-button @click="close">取消</el-button>
      </el-form-item>
    </el-form>
  </el-dialog>
</template>
<script>
import { findClientId } from '@/api/terminal'
import { addTerminal, updateTerminal } from '@/api/terminal'
export default {
  name: 'Terminal',
  data() {
    return {
      updateFlag: false,
      windowVisible: false,
      submitLoading: false,
      formData: {
        clientId: '',
        clientSecret: '',
        scope: '',
        autoApprove: 'true',
        accessTokenValidity: '',
        refreshTokenValidity: '',
        authorizedGrantTypeList: ['authorization_code', 'refresh_token'],
        webServerRedirectUri: '',
        authorities: '',
        additionalInformation: ''
      },
      rules: {
        webServerRedirectUri: [{ type: 'url', message: '请输入正确的回调地址', trigger: ['blur', 'change'] }]
      },
      authorizedGrantList: [
        { value: 'password', label: '密码模式' },
        { value: 'authorization_code', label: '授权码模式' },
        { value: 'client_credentials', label: '客户端模式' },
        { value: 'refresh_token', label: '刷新模式' },
        { value: 'implicit', label: '简化模式' }
      ]
    }
  },
  methods: {
    onSubmit() {
      this.submitLoading = true
      const that = this
      var formData1 = that.formData
      const authorizedGrantTypes = formData1.authorizedGrantTypeList.join(',')
      // 复制一个表单对象 formDate
      const formData = JSON.parse(JSON.stringify(this.formData))
      formData.authorizedGrantTypes = authorizedGrantTypes
      this.$delete(formData, 'authorizedGrantTypeList')
      if (this.updateFlag) {
        updateTerminal(formData).then(data => {
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
        addTerminal(formData).then(data => {
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
    getRow(row) {
      this.updateFlag = true
      setTimeout(() => {
        this.formData = {
          ...row,
          authorizedGrantTypeList: row.authorizedGrantTypes.split(',')
        }
      }, 200)
    },
    toAdd() {
      findClientId().then(data => {
        this.formData.clientId = data.content.clientId
        this.formData.clientSecret = data.content.clientSecret
      })
    },
    change(e) {
      this.$forceUpdate()
    }
  }
}
</script>

