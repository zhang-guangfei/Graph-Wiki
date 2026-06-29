<template>
  <el-dialog :visible.sync="dialogRoleBindDetailVisible" title="角色详情" width="420px" @close="dialogRoleBindDetailVisible = false">
    <div class="custom-tree-container">
      <el-tag type="info" color="#7590a8" style="width: 380px;height:36px;color:rgba(253, 253, 253, 0.938);font-size:14px">已绑定的功能权限</el-tag>
      <div style="width:380px;height:200px;overflow-x:hidden">
        <el-tree
          ref="bindAuthorityTree"
          :data="bindAuthorities"
          :props="defaultProps"
          :expand-on-click-node="false"
          :default-expanded-keys="idBindAuthorities"
          highlight-current
          node-key="id"/>
      </div>
      <div class="custom-tree-container">
        <div class="tag-group">
          <el-tag type="info" color="#7590a8" style="width: 380px;height:36px;color:rgba(253, 253, 253, 0.938);font-size:14px">已绑定的数据权限</el-tag>
        </div>
        <div class="tag-group">
          <el-tag v-for="item in dataFilterTypeOptions" v-show = "dataFilter.filterType === item.value" :key="item.label" effect="plain" >
            {{ item.label }}授权
          </el-tag>
          <el-tag v-show = "dataFilter.readPermission === true || dataFilter.editPermission === true || dataFilter.deletePermission === true" effect="plain" >
            <span v-show = "dataFilter.readPermission === true">浏览</span> <span/> <span v-show = "dataFilter.editPermission === true">编辑</span> <span/> <span v-show = "dataFilter.deletePermission === true">删除</span>
          </el-tag>
        </div>
        <div v-show="dataFilter.filterType === '4'" class="block" style="width: 380px;height:200px;overflow-x:hidden">
          <el-tree
            ref="bindDeptsTree"
            :data="bindDepts"
            :props="defaultProps"
            :expand-on-click-node="false"
            :default-expanded-keys="idBindDepts"
            highlight-current
            node-key="id"/>
        </div>
      </div>
    </div>
  </el-dialog>
</template>
<style>
.el-dialog__body {
    padding: 10px 20px
}
</style>
<script>
import { findDataFilterByRoleId, findDataRoleByRoleId } from '@/api/role'
import { findByRoleId } from '@/api/authority'
import { arrayToTree } from '@/utils'
export default {
  data() {
    return {
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      dataFilter: {
        filterType: '',
        roleId: '',
        readPermission: false,
        editPermission: false,
        deletePermission: false,
        createId: '',
        creteName: ''
      },
      bindAuthorities: [],
      idBindAuthorities: [],
      dataFilterTypeOptions: [{ value: '2', label: '按员工' }, { value: '1', label: '按所在部门' }, { value: '4', label: '自定义部门' }, { value: '6', label: '自定义行业' }, { value: '3', label: '自定义客户' }, { value: '5', label: '全部' }],
      bindDepts: [],
      idBindDepts: [],
      dialogRoleBindDetailVisible: false
    }
  },
  methods: {
    // 根据角色id获取用户的功能权限
    findByRoleId(id) {
      this.bindAuthorities = []
      findByRoleId(id).then((data) => {
        if (data.length === 0) {
          return
        }
        this.bindAuthorities = arrayToTree(data)
        this.bindAuthorities.forEach(m => {
          this.idBindAuthorities.push(m.id)
        })
      })
    },
    // 通过roleId获取绑定的数据权限
    findDataFilterByRoleId(id) {
      console.log('参数 -> findDataFilterByRoleId', id)
      findDataFilterByRoleId(id).then((data) => {
        console.log('通过roleId获取绑定的数据权限 ->', this.data)
        if (data.length === 0) {
          this.dataFilter = {}
          return
        }
        this.dataFilter = data
        if (this.dataFilter.filterType === '4') {
          findDataRoleByRoleId(id).then((dataR) => {
            if (dataR.length === 0) {
              return
            }
            const deptArray = []
            const deptDatas = JSON.parse(JSON.stringify(this.$parent.deptData))
            deptDatas.forEach(m => {
              dataR.forEach(f => {
                if (m.code === f.objectId) {
                  deptArray.push(m)
                  return true
                }
              })
            })
            this.bindDepts = []
            this.idBindDepts = []
            this.bindDepts = arrayToTree(deptArray)
            this.bindDepts.forEach(m => {
              this.idBindDepts.push(m.id)
            })
          })
        }
      })
    }
  }
}
</script>
