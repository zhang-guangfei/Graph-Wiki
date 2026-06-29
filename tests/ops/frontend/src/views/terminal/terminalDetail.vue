<template>
  <el-dialog :visible.sync="dialogTerminalDetailVisible" title="终端详情" width="750px" @close="dialogTerminalDetailVisible = false">
    <el-tag style="width: 710px;height:36px">编号：{{ formData.clientId }}</el-tag>
    <el-tag style="width: 710px;height:36px">域：{{ formData.scope }}</el-tag>
    <el-tag style="width: 710px;height:36px">自动放行：{{ formData.autoApprove }}</el-tag>
    <el-tag style="width: 710px;height:36px">授权模式：{{ formData.authorizedGrantTypes }}</el-tag>
    <el-tag style="width: 710px;height:36px">令牌时效：{{ formData.accessTokenValidity }}</el-tag>
    <el-tag style="width: 710px;height:36px">刷新时效：{{ formData.refreshTokenValidity }}</el-tag>
    <el-tag style="width: 710px;height:36px">回调地址：{{ formData.webServerRedirectUri }}</el-tag>
    <el-tag style="width: 710px;height:36px">权限：{{ formData.authorities }}</el-tag>
    <el-tag style="width: 710px;height:36px">扩展信息：{{ formData.additionalInformation }}</el-tag>
  </el-dialog>
</template>
<script>
export default{
  data() {
    return {
      dialogTerminalDetailVisible: false,
      formData: {
        clientId: '',
        clientSecret: '',
        scope: '',
        autoApprove: '',
        accessTokenValidity: '',
        refreshTokenValidity: '',
        authorizedGrantTypes: '',
        webServerRedirectUri: '',
        authorities: '',
        additionalInformation: ''
      }
    }
  }
  // methods: {
  //   dataInit(data) {
  //     if (data) {
  //       this.formData = data
  //       console.info(data)
  //     }
  //   }
  // }
}
</script>
