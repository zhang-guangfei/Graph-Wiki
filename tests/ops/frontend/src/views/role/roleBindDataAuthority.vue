<template>
  <el-dialog :visible.sync="dialogBindDataAuthorityVisible" title="数据权限" width="1050px">
    <div class="custom-tree-container">
      <el-tag type="info" color="#7590a8" style="width: 1000px;color:rgba(253, 253, 253, 0.938);font-size:12px;text-align:left;margin-bottom:5px">数据权限</el-tag>
      <template>
        <el-tag effect="plain">
          <el-radio v-for="opt in dataFilterTypeOptions" v-model="filterType" :key="opt.value" :label="String(opt.value)" style="width:138px" @change="changeHandler">
            {{ opt.label }}
          </el-radio>
        </el-tag>
      </template>
      <div v-if="isShow" align="center">
        <el-tag type="info" color="#7590a8" style="width: 915px;text-align:left;margin-top:10px;margin-bottom:10px;color:rgba(253, 253, 253, 0.938);font-size:12px">
          自定义部门数据权限
        </el-tag>
        <span>
          <el-checkbox v-model="deptsTreeCheckstrictly" label="关联选中" size="small" checked/>
        </span>
        <div class="el-dialog-div" style="text-align:left; margin-left:36px;overflow-x:hidden;height:400px">
          <el-input v-model="filterText" size="small" style="width:300px;text-align:left;" placeholder="请输入部门名称过滤" clearable @change="filterChange"/>
          <el-tree
            ref="deptsTree"
            :data="depts"
            :props="defaultProps"
            :expand-on-click-node="false"
            :default-expanded-keys="idDepts"
            :check-strictly="!deptsTreeCheckstrictly"
            :filter-node-method="filterNode"
            highlight-current
            node-key="id"
            show-checkbox/>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer" align="center" >
      <el-button type="primary" @click="dialogBindDataAuthorityVisible = false">取消</el-button>
      <el-button type="primary" @click="bindDataFilter(id)">确认</el-button>
    </div>
  </el-dialog>
</template>
<style>
.el-dialog__body {
    padding: 10px 20px
}
</style>
<script>
import { findDataRoleByRoleId, bindDataFilter, findDataFilterByRoleId } from '@/api/role'
import store from '@/store'
import { arrayToTree } from '@/utils'
export default {
  data() {
    return {
      loading: false,
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      id: '',
      filterType: '',
      filterText: '',
      depts: [],
      idDepts: [],
      isShow: false,
      dialogBindDataAuthorityVisible: false,
      deptsTreeCheckstrictly: false,
      dataFilterTypeOptions: [{ value: '2', label: '按员工' }, { value: '1', label: '按所在部门' }, { value: '4', label: '自定义部门' }, { value: '6', label: '自定义行业' }, { value: '3', label: '自定义客户' }, { value: '5', label: '全部' }]
    }
  },
  methods: {
    initDeptTree() {
      if (this.depts.length === 0) {
        this.depts = arrayToTree(this.$parent.deptData)
        console.log('过滤后的部门数据-》', this.depts)
        this.depts.forEach(m => {
          this.idDepts.push(m.id)
        })
      }
      this.filterType = ''
      this.findDataFilterByRoleId(this.id)
    },
    // 通过roleId获取绑定的数据权限
    findDataFilterByRoleId(id) {
      findDataFilterByRoleId(id).then((data) => {
        if (data.length === 0) {
          return
        }
        this.filterType = data.filterType
        if (this.filterType === '4') {
          this.isShow = true
          findDataRoleByRoleId(id).then((dataR) => {
            if (dataR.length === 0) {
              return
            }
            dataR.forEach((item, index) => {
              if (this.$refs.deptsTree) {
                this.$refs.deptsTree.setChecked(item.objectId, true)
              }
            })
          })
        }
      })
    },
    filterNode(value, data) {
      if (!value) return true
      return data.name.indexOf(value) !== -1
    },
    filterChange() {
      this.$refs.deptsTree.filter(this.filterText)
    },
    // redio复选框点击事件
    changeHandler(value) {
      if (value === '4' && this.filterType === '4') {
        this.isShow = true
        findDataRoleByRoleId(this.id).then((data) => {
          if (data.length === 0) {
            return
          }
          data.forEach((item, index) => {
            this.$refs.deptsTree.setChecked(item.objectId, true)
          })
        })
      } else {
        this.isShow = false
      }
    },
    bindDataFilter(id) {
      this.loading = true
      const dataFilter = {}
      dataFilter.roleId = id
      dataFilter.filterType = this.filterType
      dataFilter.createId = store.getters.info.username
      dataFilter.createName = store.getters.info.psnName
      let deptIds = []
      if (this.filterType === '4') {
        if (this.$refs.deptsTree.getCheckedKeys().length >= 1) {
          if (this.$refs.deptsTree) {
            deptIds = this.$refs.deptsTree.getCheckedKeys()
            deptIds = deptIds.concat(this.$refs.deptsTree.getHalfCheckedKeys())
          }
        } else {
          this.$message({
            type: 'warning',
            message: '请先选择部门再提交数据！'
          })
          return
        }
      }
      bindDataFilter(dataFilter, deptIds.join(',')).then((response) => {
        this.loading = false
        this.dialogBindDataAuthorityVisible = false
        this.$notify({
          title: '成功',
          message: '绑定成功',
          type: 'success',
          duration: 2000
        })
        this.loading = false
      }).catch(() => {
        this.loading = false
        this.$notify.error({
          title: '错误',
          type: 'warning',
          message: '绑定失败'
        })
      })
    }
  }
}
</script>
