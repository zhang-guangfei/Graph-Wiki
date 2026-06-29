<template>
  <div>
    <div style="width:40%;float:left;margin-left:20px">
      <div class="custom-tree-container" style="width:600px">
        <p>权限管理</p>
        <div class="block">
          <el-tree
            ref="authorityTree"
            :data="authorities"
            :expand-on-click-node="false"
            :render-content="renderContent"
            :props="defaultProps"
            :default-expanded-keys="idAuthorities"
            highlight-current
            node-key="id"
            @node-click="handleNodeClick"/>
        </div>
      </div>
    </div>
    <div style="width:30%;float:left;">
      <div class="custom-tree-container">
        <p>菜单管理</p>
        <div class="block">
          <el-tree
            ref="menuTree"
            :data="menus"
            :expand-on-click-node="false"
            :props="menuProps"
            :default-expanded-keys="idMenus"
            show-checkbox
            check-strictly
            node-key="id"
            @check-change="handleClick"/>
        </div>
      </div>
    </div>
    <div style="width:20%;float:left;">
      <div class="block" style="margin-left:20px;width:200px">
        <p>资源列表</p>
        <el-tree
          ref="resourceTree"
          :data="resources"
          :expand-on-click-node="false"
          :props="defaultProps"
          :default-expanded-keys="idResources"
          show-checkbox
          check-strictly
          node-key="id"/>
      </div>
    </div>
    <el-dialog :visible.sync="dialogFormVisible" title="添加权限" width="400px">
      <el-form ref="form" :model="form" :rules="formRules" >
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" autocomplete="off" style="width:250px"/>
        </el-form-item>
        <el-form-item label="类型" prop="type">
          <el-select v-model="form.type" placeholder="请选择类型" style="width:250px">
            <el-option label="菜单" value="menu"/>
            <el-option label="功能" value="resource"/>
            <el-option label="通用" value="common"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button :loading="loading" type="primary" @click="saveAuthority">确 定</el-button>
      </div>
      <el-input v-show="false" v-model="form.id"/>
      <el-input v-show="false" v-model="form.pid"/>
    </el-dialog>
  </div>
</template>
<script>
import { fetchList, createAuthority, updateAuthority, deleteAuthority, bindMenu, bindResource, findBindMenuById, findBindResourceById } from '@/api/authority'
import { fetchList as fetchResourceList } from '@/api/resource'
import { fetchList as fetchMenuList } from '@/api/menu'
import { arrayToTree } from '@/utils'

export default {
  data() {
    const authorities = []
    const menus = []
    const resources = []
    return {
      authorities: authorities,
      resources: resources,
      menus: menus,
      dialogTableVisible: false,
      dialogFormVisible: false,
      loading: false,
      isEdit: false,
      isBind: false,
      idAuthorities: [],
      idMenus: [],
      idResources: [],
      formRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        type: [
          { required: true, message: '请输入类型', trigger: 'blur' }
        ]
      },
      form: {
        id: '',
        pid: '',
        name: '',
        type: ''
      },
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      menuProps: {
        children: 'children',
        label: 'title'
      },
      i: 0
    }
  },

  created() {
    this.getList()
  },

  methods: {
    getList() {
      fetchList().then(data => {
        console.log('权限->', data)
        this.authorities = arrayToTree(data)
        console.log('权限（tree）->', this.authorities)
        this.authorities.forEach(m => {
          this.idAuthorities.push(m.id)
        })
      })

      fetchResourceList().then(data => {
        console.log('资源->', data)
        this.resources = arrayToTree(data)
        this.resources.forEach(m => {
          this.idResources.push(m.id)
        })
      })

      fetchMenuList().then(data => {
        console.log('菜单->', data)
        this.menus = arrayToTree(data)
        this.menus.forEach(m => {
          this.idMenus.push(m.id)
        })
      })
    },
    handleClick(data, checked, node) {
      this.i++
      if (this.i % 2 === 0) {
        if (checked) {
          this.$refs.menuTree.setCheckedNodes([])
          this.$refs.menuTree.setCheckedNodes([data])
        } else {
          this.$refs.menuTree.setCheckedNodes([])
        }
      }
    },
    findBindMenuById(data) {
      findBindMenuById({ id: data.id }).then((data) => {
        if (data === '') {
          return
        }
        this.$refs.menuTree.setCheckedKeys([data.menuId])
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '获取绑定的菜单失败'
        })
      })
    },
    findBindResourceById(data) {
      findBindResourceById({ id: data.id }).then((data) => {
        if (data.length === 0) {
          return
        }
        const arr = []
        data.forEach((item, index) => {
          arr.push(item.resourceId)
        })
        this.$refs.resourceTree.setCheckedKeys(arr)
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '获取绑定的资源失败'
        })
      })
    },

    handleNodeClick(data, v, k) {
      if (this.isBind) {
        return
      }
      this.$refs.resourceTree.setCheckedKeys([])
      this.$refs.menuTree.setCheckedKeys([])
      this.findBindResourceById(data)
      this.findBindMenuById(data)
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
      this.$confirm('此操作将永久删除该权限, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        deleteAuthority({ code: data.code }).then((data) => {
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
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },

    bindMenu(node, data) {
      /* if (data.type === 'resource') {
        this.$message({
          type: 'info',
          message: '节点类型为资源，无法绑定菜单'
        })
        return
      } */
      this.isBind = true
      const checkMenu = this.$refs.menuTree.getCheckedNodes()
      const menuIds = []
      checkMenu.forEach((item, index) => {
        menuIds.push(item.id)
      })
      const temp = { authorityId: data.id, menuId: menuIds[0] }
      this.$confirm('确认绑定, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        bindMenu(temp).then((data) => {
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

    bindResource(node, data) {
      this.isBind = true
      const checkResource = this.$refs.resourceTree.getCheckedNodes()
      const resourceIds = []
      checkResource.forEach((item, index) => {
        resourceIds.push(item.id)
      })
      const temp = { id: data.id, resourceIds: resourceIds.join(',') }
      this.$confirm('确认绑定, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        bindResource(temp).then((data) => {
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

    saveAuthority() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.isEdit) {
            updateAuthority(this.form).then((data) => {
              const selectNode = this.$refs.authorityTree.getCurrentNode()
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              this.loading = false
              this.dialogFormVisible = false
              console.log('update data ->', data)
              /**
               * 更新节点 */
              selectNode.name = data.name
              selectNode.type = data.type
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
            createAuthority(this.form).then((data) => {
              console.log('add return -> ', data)
              const selectNode = this.$refs.authorityTree.getCurrentNode()
              console.log('选择的节点 -> ', selectNode)
              const newChild = { id: data.id, label: data.name, name: data.name, type: data.type, children: [] }
              if (!selectNode.children) {
                this.$set(selectNode, 'children', [])
              }
              /**
               * 与后端联调时去掉此段代码 开始 */
              // if (newChild.id === undefined) {
              //   newChild.id = Math.ceil(Math.random() * 100).toString()
              // }
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
    },

    renderContent(h, { node, data, store }) {
      return (
        <span class='custom-tree-node'>
          <span>{data.label}</span>
          <span>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.add(data) }>添加</el-button>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.edit(data) }>修改</el-button>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.remove(node, data) }>删除</el-button>
            <el-tooltip class='item' effect='dark' style='font-size: 16px;' content='请选中菜单' placement='top-start'>
              <el-button size='mini' type='text' on-click={ () => this.bindMenu(node, data) }>绑定菜单</el-button>
            </el-tooltip>
            <el-tooltip class='item' effect='dark' style='font-size: 16px;' content='请选中资源' placement='top-start'>
              <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.bindResource(node, data) }>绑定资源</el-button>
            </el-tooltip>
          </span>
        </span>)
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
