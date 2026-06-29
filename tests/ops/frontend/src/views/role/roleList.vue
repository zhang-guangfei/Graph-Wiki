<template>
  <div>
    <vxe-grid
      ref="xGrid"
      :loading="loading"
      :edit-rules="validRules"
      :pager-config="tablePage"
      :columns="tableColumn"
      :data="tableData"
      :edit-config="{trigger: 'manual', mode: 'row', showStatus: true, icon: 'fa fa-file-text-o'}"
      :toolbar="tableToolbar"
      highlight-current-row
      border
      show-overflow
      resizable
      keep-source
      size="medium"
      @page-change="handlePageChange">
      <template v-slot:toolbar_buttons>
        <el-input v-model="listQuery.name" placeholder="角色名称" size="medium" style="width: 200px;" clearable @keyup.enter.native="searchEvent()"/>
        <el-button v-permission="['000000001000']" size="medium" icon="el-icon-search" title="查询" @click="searchEvent()"/>
        <el-button v-permission="['000000001001']" size="medium" icon="el-icon-circle-plus-outline" title="添加" style="margin-left: 10px;" @click="insertEvent()"/>
        <el-button v-permission="['000000001001']" size="medium" icon="el-icon-circle-check" title="保存" style="margin-left: 10px;" @click="saveAllRowEvent()"/>
      </template>
      <template v-slot:operate="{ row }">
        <el-row>
          <el-button size="mini" icon="el-icon-delete" title="删除" circle @click="removeRowEvent(row)"/>
          <template v-if="$refs.xGrid.isActiveByRow(row)">
            <el-button size="mini" icon="el-icon-check" type="primary" title="保存" circle @click="saveRowEvent(row)"/>
          </template>
          <template v-else>
            <el-button size="mini" icon="el-icon-edit" title="编辑" circle @click="editRowEvent(row)"/>
          </template>
          <el-button size="mini" icon="el-icon-view" title="详情" circle @click="detailRowEvent(row)"/>
          <el-button size="mini" icon="el-icon-link" title="功能权限" circle @click="operAuthorityEvent(row)"/>
          <el-button size="mini" icon="el-icon-connection" title="数据权限" circle @click="dataAuthorityEvent(row)"/>
        </el-row>
      </template>
    </vxe-grid>
    <role-bind-oper-authority ref="roleBindOperAuthority"/>
    <role-bind-detail ref="roleBindDetail"/>
    <role-bind-data-authority ref="roleBindDataAuthority"/>
  </div>
</template>
<script>
import { fetchList, createRole, updateRole, deleteRole } from '@/api/role'
import { fetchDeptsList } from '@/api/holon'
import { fetchList as fetchAuthorityList } from '@/api/authority'
import { arrayToTree } from '@/utils'
import RoleBindDetail from './roleBindDetail'
import RoleBindOperAuthority from './roleBindOperAuthority'
import RoleBindDataAuthority from './roleBindDataAuthority'
export default {
  name: 'RoleList',
  components: {
    'role-bind-detail': RoleBindDetail,
    'role-bind-oper-authority': RoleBindOperAuthority,
    'role-bind-data-authority': RoleBindDataAuthority
  },
  data() {
    return {
      id: '',
      deptData: [],
      loading: false,
      listQuery: {
        name: '',
        pageNumber: 1,
        pageSize: 50
      },
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 50,
        align: 'right',
        pageSizes: [50, 200, 500, 1000, 2000, 5000],
        layouts: ['PrevJump', 'PrevPage', 'JumpNumber', 'NextPage', 'NextJump', 'Sizes', 'FullJump', 'Total'],
        perfect: true
      },
      tableColumn: [
        { type: 'checkbox', width: 50, align: 'center' },
        { field: 'name', title: '名称', minWidth: 250, remoteSort: true, editRender: { name: 'input' }},
        { field: 'description', title: '描述', minWidth: 250, showOverflow: true, editRender: { name: 'input' }},
        { title: '操作', minWwidth: 250, slots: { default: 'operate' }, align: 'center' }
      ],
      tableData: [],
      tableToolbar: {
        perfect: true,
        import: {
          icon: 'el-icon-upload2'
        },
        export: {
          icon: 'el-icon-download'
        },
        print: {
          icon: 'el-icon-printer'
        },
        custom: {
          icon: 'el-icon-s-grid'
        },
        slots: {
          buttons: 'toolbar_buttons'
        }
      },
      validRules: {
        name: [
          { required: true, message: '名称必须填写' }
        ]
      },
      sortConfig: {
        trigger: 'cell'
      }
    }
  },
  created() {
    this.findList()
    // 获取所有部门
    this.findDeptsList()
    // 查询通用功能权限
    this.findAllAuthority()
  },
  methods: {
    // 查询所有部门
    findDeptsList() {
      fetchDeptsList().then((data) => {
        this.deptData = data
        console.log('所有部门->', this.deptData)
      }).catch(() => {
      })
    },
    // 查询所有功能权限
    findAllAuthority() {
      fetchAuthorityList({ type: '' }).then((data) => { // type:  common
        console.log('通用的功能权限 ->', data)
        this.$refs.roleBindOperAuthority.authorities = arrayToTree(data) // 数组转为tree
        console.log(arrayToTree('权限转为tree -> ', data))
        this.$refs.roleBindOperAuthority.authorities.forEach(m => {
          this.$refs.roleBindOperAuthority.idAuthorities.push(m.id)
        })
        console.log('需要展开的id ', this.$refs.roleBindOperAuthority.idAuthorities)
      }).catch(() => {
      })
    },
    findList() {
      this.loading = true
      fetchList(this.listQuery).then(data => {
        this.tableData = data.list
        this.tablePage.total = data.total
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    searchEvent() {
      this.listQuery.pageNumber = 1
      this.tablePage.currentPage = 1
      this.findList()
    },
    handlePageChange({ currentPage, pageSize }) {
      this.listQuery.pageSize = pageSize
      this.listQuery.pageNumber = currentPage
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.findList()
    },
    async insertEvent() {
      this.$refs.xGrid.insert().then(({ row }) => {
        console.log('row ->', row)
        this.$refs.xGrid.setCurrentRow(row)
        this.$refs.xGrid.setActiveRow(row)
        this.$refs.xGrid.validate(row).catch(errMap => errMap)
      })
    },
    async editRowEvent(row) {
      this.$refs.xGrid.setCurrentRow(row)
      this.$refs.xGrid.setActiveRow(row)
      this.$refs.xGrid.validate(row).catch(errMap => errMap)
    },
    async saveRowEvent(row) {
      if (row) {
        console.log('点击+之后的 表格->', row)
        const errMap = await this.$refs.xGrid.validate(row).catch(errMap => errMap)
        if (errMap) {
          return
        }
        this.$refs.xGrid.clearActived().then(() => {
          this.loading = true
          updateRole(row).then(() => {
            console.log('update rowdata ->', row)
            this.loading = false
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success',
              duration: 2000
            })
          }).catch(() => {
            this.loading = false
          })
        })
      }
    },
    async saveAllRowEvent() {
      const errMap = await this.$refs.xGrid.validate(true).catch(errMap => errMap)
      if (errMap) {
        return
      }
      const { insertRecords } = this.$refs.xGrid.getRecordset()
      if (insertRecords.length === 0) {
        this.$notify({
          title: '警告',
          message: '请添加角色',
          type: 'warning'
        })
        return
      }
      const roleList = []
      let role = {}
      insertRecords.forEach((item, index) => {
        role = {}
        role.id = ''
        role.name = item.name
        role.description = item.description
        roleList.push(role)
      })
      this.loading = true
      createRole(roleList).then(() => {
        this.loading = false
        this.$notify({
          title: '成功',
          message: '创建成功',
          type: 'success',
          duration: 2000
        })
        this.findList()
      }).catch(() => {
        this.loading = false
      })
    },
    removeRowEvent(row) {
      this.$confirm('此操作将永久删除该角色, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        deleteRole({ id: row.id }).then((response) => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          this.loading = false
          this.findList()
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '删除失败'
          })
        })
      }).catch(() => {
      })
    },
    detailRowEvent(row) {
      this.$refs.roleBindDetail.dialogRoleBindDetailVisible = true
      this.$refs.roleBindDetail.findByRoleId(row.id)
      this.$refs.roleBindDetail.findDataFilterByRoleId(row.id)
    },
    operAuthorityEvent(row) {
      console.log('this auth  row ', row)
      this.$refs.roleBindOperAuthority.dialogBindOperAuthorityVisible = true
      this.$refs.roleBindOperAuthority.findAuthorityById(row.id)
    },
    dataAuthorityEvent(row) {
      this.$refs.roleBindDataAuthority.dialogBindDataAuthorityVisible = true
      this.$refs.roleBindDataAuthority.isShow = false
      this.$refs.roleBindDataAuthority.id = row.id
      this.$refs.roleBindDataAuthority.initDeptTree()
    }
  }
}
</script>
