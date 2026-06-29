<template>
  <div class="app-container">
    <el-card style="margin-bottom: 10px">
      <el-form v-loading="loading" ref="form" :inline="true" :model="form">
        <el-row :gutter="20" >
          <el-form-item label="部门选择:">
            <el-cascader
              v-model="departmentScope"
              :options="scopeOptions"
              :show-all-levels="false"
              :props="{ checkStrictly: true }"
              placeholder="请选择部门"
              style="min-width: 200px"
              size="small"
              clearable
              filterable/>
          </el-form-item>
          <el-form-item label="岗位名称:">
            <el-input
              v-model.trim="positionName"
              placeholder="请输入"
              clearable
              style="width: 140px"
              size="small" />
            <el-checkbox v-model="accurate">精确</el-checkbox>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" size="small" @click="fetchData()">查询</el-button>
            <el-button type="primary" size="small" @click="bindRole()">绑定</el-button>
          </el-form-item>
        </el-row>
      </el-form>
      <hr size="5" color="#0066cc">
    </el-card>
    <div>
      <div style="float:left;width:45%">
        <p>岗位列表</p>
        <el-table
          ref="positionTable"
          :data="positionlist"
          :row-style="rowStyle"
          :span-method="objectSpanMethod"
          row-key="id"
          type="selection"
          border
          fit
          highlight-current-row
          @row-click="rowClick"
          @selection-change="handlePositoinSelectionChange">
          <el-table-column
            type="selection"
            width="45"
            align="center"/>
          <el-table-column label="部门名称" min-width="120px">
            <template slot-scope="scope">
              <span>{{ scope.row.fullName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="岗位名称(在岗人数)" min-width="100px">
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div style="float:left;width:45%;margin-left:30px">
        <p>角色列表</p>
        <el-table
          ref="multipleTable"
          :data="rolelist"
          :row-style="rowStyle"
          row-key="id"
          type="selection"
          border
          fit
          highlight-current-row
          @selection-change="handleSelectionChange">
          <el-table-column
            type="selection"
            width="45"
            align="center"/>
          <el-table-column label="名称" min-width="100px">
            <template slot-scope="scope">
              <span>{{ scope.row.name }}</span>
            </template>
          </el-table-column>
          <el-table-column label="状态" class-name="status-col" width="70">
            <template slot-scope="scope">
              <span>{{ scope.row.status }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
import { fetchList, createGroup, updateGroup, deleteGroup, bindRole, findBindRoleById } from '@/api/group'
import { fetchListAll } from '@/api/role'
import { queryOrgPosition, fetchDeptsList } from '@/api/holon'
import { arrayToTree } from '@/utils'

export default {
  data() {
    const groups = []
    const rolelist = []
    const positionlist = []
    return {
      groups: groups,
      rolelist: rolelist,
      departmentScope: [],
      positionName: null,
      positionlist: positionlist,
      dialogTableVisible: false,
      dialogFormVisible: false,
      listLoading: true,
      scopeOptions: null,
      loading: false,
      isEdit: false,
      isBind: false,
      accurate: false,
      multipleSelection: [],
      multiplePositionSelection: [],
      idGroups: [],
      formRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ]
      },
      form: {
        id: '',
        pid: '',
        name: ''
      },
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      i: 0
    }
  },

  created() {
    this.createScopeOptions()
    this.getList()
  },

  methods: {
    getList() {
      fetchList().then(data => {
        this.groups = arrayToTree(data)
        this.groups.forEach(m => {
          this.idGroups.push(m.id)
        })
      })

      fetchListAll().then(data => {
        this.rolelist = data
      })

      const unitId = this.$store.getters.position.unitId
      const orgPosition = { unitId }
      queryOrgPosition(orgPosition).then(data => {
        this.positionlist = data
      })
    },

    fetchData() {
      const unitIdLen = this.departmentScope.length
      var unitId = ''
      if (unitIdLen) {
        unitId = this.departmentScope[unitIdLen - 1]
      }
      const positionName = this.positionName
      const accurate = this.accurate
      const orgPosition = { unitId, positionName, accurate }
      console.log('orgPosition -> ', orgPosition)
      queryOrgPosition(orgPosition).then(data => {
        this.positionlist = data
      })
    },

    setTable() {
      const spanOneArr = []
      const spanTwoArr = []
      let concatOne = 0
      this.positionlist.forEach((item, index) => {
        if (index === 0) {
          spanOneArr.push(1)
          spanTwoArr.push(1)
        } else {
          if (item.fullName === this.positionlist[index - 1].fullName) {
            spanOneArr[concatOne] += 1
            spanOneArr.push(0)
          } else {
            spanOneArr.push(1)
            concatOne = index
          }
        }
      })
      return {
        one: spanOneArr,
        two: spanTwoArr
      }
    },

    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 1) {
        const _row = (this.setTable(this.positionlist).one)[rowIndex]
        const _col = _row > 0 ? 1 : 0
        return {
          rowspan: _row,
          colspan: _col
        }
      }
    },

    handlePositoinSelectionChange(val) {
      this.multiplePositionSelection = val
    },

    handleSelectionChange(val) {
      this.multipleSelection = val
    },

    rowStyle({ row, rowIndex }) {
      return 'height:37px'
    },

    findBindRoleById(code) {
      findBindRoleById({ id: code }).then((data) => {
        if (data === '') {
          return
        }
        data.forEach((item, index) => {
          const role = this.rolelist.find(d => d.id === item.roleId)
          if (role) {
            this.$refs.multipleTable.toggleRowSelection(role, true)
          }
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '获取绑定的菜单失败'
        })
      })
    },

    createScopeOptions() {
      fetchDeptsList().then(data => {
        this.scopeOptions = arrayToTree(data)
      }).catch(() => {
      })
    },

    rowClick(row, event, column) {
      if (this.isBind) {
        return
      }
      this.$refs.multipleTable.clearSelection()
      this.findBindRoleById(row.id)
    },

    add(data) {
      this.isEdit = false
      this.form = {}
      this.form.pid = data.id
      this.dialogFormVisible = true
    },

    edit(data) {
      this.form = {}
      this.isEdit = true
      this.dialogFormVisible = true
      const { id, name, type, pid } = data
      this.form = { id, name, type, pid }
    },

    remove(node, data) {
      this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        deleteGroup({ code: data.code }).then((data) => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
          node.remove(data)
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '删除失败'
          })
        })
      }).catch(() => {
        /* this.$message({
          type: 'info',
          message: '已取消删除'
        }) */
      })
    },

    bindRole(positionId) {
      this.isBind = true
      const positionIds = []
      const positionNames = []
      const roleIds = []
      if (this.multiplePositionSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中岗位'
        })
        return
      }
      this.multiplePositionSelection.forEach((item, index) => {
        positionIds.push(item.id)
        positionNames.push(item.fullName + '_' + item.name.substring(0, item.name.lastIndexOf('(')))
      })
      this.multipleSelection.forEach((item, index) => {
        roleIds.push(item.id)
      })
      const temp = { groupIds: positionIds, groupNames: positionNames, roleIds: roleIds }
      this.$confirm('确认绑定, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        bindRole(temp).then((data) => {
          this.$notify({
            title: '成功',
            message: '绑定成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
          this.isBind = false
        }).catch(() => {
          this.loading = false
          this.isBind = false
          this.$notify.error({
            title: '错误',
            message: '绑定失败'
          })
        })
      }).catch(() => {
        this.isBind = false
        this.$message({
          type: 'info',
          message: '已取消绑定'
        })
      })
    },

    saveGroup() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.isEdit) {
            updateGroup(this.form).then((data) => {
              const selectNode = this.$refs.groupTree.getCurrentNode()
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.loading = false
              this.dialogFormVisible = false
              /**
               * 更新节点 */
              selectNode.name = data.name
            }).catch((e) => {
              this.loading = false
              this.$notify.error({
                title: '错误',
                message: '保存失败'
              })
            })
            return
          }
          if (!this.isEdit) {
            createGroup(this.form).then((data) => {
              const selectNode = this.$refs.groupTree.getCurrentNode()
              const newChild = { id: data.id, label: data.name, name: data.name, type: data.type, children: [] }
              if (!selectNode.children) {
                this.$set(selectNode, 'children', [])
              }
              /**
               * 与后端联调时去掉此段代码 开始 */
              if (newChild.id === undefined) {
                newChild.id = Math.ceil(Math.random() * 100).toString()
              }
              /**
               * 与后端联调时去掉此段代码 结束 */
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.loading = false
              this.dialogFormVisible = false
              selectNode.children.push(newChild)
            }).catch(() => {
              this.loading = false
              this.$notify.error({
                title: '错误',
                message: '保存失败'
              })
            })
          }
        }
      })
    }
  }
}
</script>

<style>
  body>.el-container{
    margin-bottom: 40px;
  }
  .custom-tree-node {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: space-between;
    font-size: 16px;
    padding-right: 8px;
  }
</style>
