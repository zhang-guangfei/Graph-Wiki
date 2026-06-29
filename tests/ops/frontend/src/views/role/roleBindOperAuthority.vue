<template>
  <el-dialog :visible.sync="dialogBindOperAuthorityVisible" title="功能权限" width="580px" @close="dialogBindOperAuthorityVisible = false">
    <div class="custom-tree-container" style="margin-left:20px">
      <el-tag type="info" color="#7590a8" style="width: 500px;color:rgba(253, 253, 253, 0.938);font-size:12px">功能权限</el-tag>
      <div class="block" style="width: 500px;">
        <el-tree
          ref="authorityTree"
          :data="authorities"
          :props="defaultProps"
          :expand-on-click-node="false"
          :default-expanded-keys="idAuthorities"
          highlight-current
          node-key="id"
          show-checkbox/>
      </div>
    </div>
    <div slot="footer" class="dialog-footer" >
      <el-button @click="dialogBindOperAuthorityVisible = false">取消</el-button>
      <el-button :loading="loadingButton" type="primary" @click="bind(id)">确认</el-button>
    </div>
  </el-dialog>
</template>
<style>
.el-dialog__body {
    padding: 10px 20px
}
</style>
<script>

import { bindAuthority, findAuthorityById } from '@/api/role'

export default {
  data() {
    return {
      loading: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      loadingButton: false,
      id: '',
      authorities: [],
      authorityIds: [],
      idAuthorities: [],

      dialogBindOperAuthorityVisible: false
    }
  },
  methods: {
    findAuthorityById(id) {
      this.id = id
      if (this.$refs.authorityTree) {
        this.$refs.authorityTree.setCheckedKeys([])
      }
      findAuthorityById({ id: id }).then((data) => {
        console.log('权限 data', data)
        this.$refs.authorityTree.setCheckedKeys(['99cdd124a8fc4e5db27ed12ceb942ae0'])
        if (data.length === 0) {
          return
        }
        data.forEach((item, index) => {
          this.$refs.authorityTree.setChecked(item.authorityId, true)
        })
        //  const selecteds = []
        // for (var i = 0; i < data.length; i++) {
        //   selecteds.push(data[i].authorityId)
        // }
        // this.$refs.authorityTree.setCheckedKeys(selecteds)
      })
    },
    // 绑定功能权限的确认按钮
    bind(id) {
      console.log('点击确定传的id', id)
      this.loadingButton = true
      this.loading = true
      let authorityIds = this.$refs.authorityTree.getCheckedKeys()
      console.log('authorityIds -> ', authorityIds)
      authorityIds = authorityIds.concat(this.$refs.authorityTree.getHalfCheckedKeys())
      console.log('所选id ->', authorityIds)
      // const data = { id: this.id, authorityIds: authorityIds.join(',') }
      // this.authorityIds = data.authorityIds
      bindAuthority(this.id, authorityIds).then((response) => {
        console.log('传给接口的角色roleid ', this.id, '权限id 组 ', authorityIds)
        this.loading = false
        this.dialogBindOperAuthorityVisible = false
        this.$notify({
          title: '成功',
          message: '绑定成功',
          type: 'success',
          duration: 2000
        })
        this.loadingButton = false
      }).catch(() => {
        this.loading = false
        this.dialogBindOperAuthorityVisible = false
        this.$notify.error({
          title: '错误',
          message: '绑定失败'
        })
        this.loadingButton = false
      })
    }
  }
}
</script>
