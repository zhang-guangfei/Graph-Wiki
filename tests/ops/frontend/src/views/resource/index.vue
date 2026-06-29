<template>
  <div>
    <div style="width:45%;float:left;margin-left:20px">
      <div class="custom-tree-container" style="width:600px">
        <p>菜单管理</p>
        <div class="block">
          <el-tree
            ref="menutree"
            :data="menus"
            :expand-on-click-node="false"
            :render-content="renderContentMenu"
            :props="defaultProps"
            :default-expanded-keys="idMenus"
            show-checkbox
            highlight-current
            node-key="id"/>
        </div>
      </div>
    </div>
    <div style="width:45%;float:left">
      <div class="custom-tree-container" style="margin-left:100px;width:400px">
        <p>资源管理</p>
        <el-tree
          ref="resourceTree"
          :data="resources"
          :expand-on-click-node="false"
          :render-content="renderContent"
          :props="defaultProps"
          :default-expanded-keys="idResources"
          show-checkbox
          highlight-current
          node-key="id"/>
      </div>
    </div>
    <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false" title="添加资源" width="400px">
      <el-form ref="form" :model="form" :rules="formRules" >
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" style="width:250px"/>
        </el-form-item>
        <el-form-item label="模式" prop="pattern">
          <el-input v-model="form.pattern" style="width:250px"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button :loading="loading" type="primary" @click="saveResource">确 定</el-button>
      </div>
      <el-input v-show="false" v-model="form.id"/>
      <el-input v-show="false" v-model="form.pid"/>
    </el-dialog>

    <el-dialog :visible.sync="dialogMenuFormVisible" title="添加菜单" width="450px">
      <el-form ref="menuform" :model="menuform" :rules="menuRules" label-width="120px">
        <el-form-item label="path" prop="path">
          <el-input v-model="menuform.path" placeholder="请输入path,唯一" />
        </el-form-item>
        <el-form-item label="component" prop="component">
          <el-input v-model="menuform.component" placeholder="请输入component"/>
        </el-form-item>
        <el-form-item label="name" prop="name">
          <el-input v-model="menuform.name"/>
        </el-form-item>
        <el-form-item label="title" prop="title">
          <el-input v-model="menuform.title"/>
        </el-form-item>
        <el-form-item label="icon" prop="icon">
          <el-input v-model="menuform.icon"/>
        </el-form-item>
        <el-form-item label="hidden" prop="hidden">
          <el-radio-group v-model="menuform.hidden">
            <el-radio label="1" >是</el-radio>
            <el-radio label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="sort" prop="sort">
          <el-input-number v-model="menuform.sort" :min="1" :max="100" controls-position="right" style="width:280px" />
        </el-form-item>
        <el-form-item prop="pid">
          <el-input v-show="false" v-model="menuform.pid"/>
          <el-input v-show="false" v-model="menuform.id"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogMenuFormVisible = false">取 消</el-button>
        <el-button :loading="loading" type="primary" @click="saveMenu">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { fetchList, createResource, updateResource, deleteResource } from '@/api/resource'
import { fetchList as fetchMenuList, createMenu, updateMenu, deleteMenu } from '@/api/menu'
import { arrayToTree } from '@/utils'

export default {
  name: 'Resource',
  data() {
    const resources = []
    const menus = []
    return {
      resources: resources,
      menus: menus,
      dialogTableVisible: false,
      dialogFormVisible: false,
      loading: false,
      isEdit: false,
      idMenus: [],
      idResources: [],
      formRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        pattern: [
          { required: true, message: '请输入模式', trigger: 'blur' }
        ]
      },
      form: {
        id: '',
        pid: '',
        name: '',
        pattern: ''
      },
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      menuform: {
        id: '',
        name: '',
        title: '',
        sort: '',
        path: '',
        component: '',
        hidden: '0',
        pid: ''
      },
      dialogMenuFormVisible: false,
      menuRules: {
        path: [
          { required: true, message: '请输入path', trigger: 'blur' }
        ],
        component: [
          { required: true, message: '请输入component', trigger: 'blur' }
        ],
        name: [
          { required: true, message: '请输入name', trigger: 'blur' }
        ],
        hidden: [
          { required: true, message: '请选择hidden', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '请输入title', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入顺序', trigger: 'blur' }
        ]
      }
    }
  },

  created() {
    this.getList()
  },

  methods: {
    getList() {
      fetchList().then(data => {
        this.resources = arrayToTree(data)
        this.resources.forEach(m => {
          this.idResources.push(m.id)
        })
      })

      fetchMenuList().then(data => {
        this.menus = arrayToTree(data)
        this.menus.forEach(m => {
          this.idMenus.push(m.id)
        })
      })
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
      this.form = data
    },

    remove(node, data) {
      console.log('资源-> ', data)
      this.$confirm('此操作将永久删除该资源, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        deleteResource({ code: data.code }).then((response) => {
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
    saveResource() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.isEdit) {
            updateResource(this.form).then((data) => {
              const selectNode = this.$refs.resourceTree.getCurrentNode()
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
              selectNode.pattern = data.pattern
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
            createResource(this.form).then((data) => {
              const selectNode = this.$refs.resourceTree.getCurrentNode()
              const newChild = { id: data.id, pid: data.pid, name: data.name, pattern: data.pattern, children: [] }
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
    },
    addMenu(data) {
      this.isEdit = false
      this.menuform = {}
      this.menuform.pid = data.id
      this.menuform.level = data.level + 1
      this.dialogMenuFormVisible = true
    },

    editMenu(data) {
      this.menuform = {}
      this.isEdit = true
      this.dialogMenuFormVisible = true
      this.menuform = data
    },

    removeMenu(node, data) {
      this.$confirm('此操作将永久删除该资源, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.loading = true
        deleteMenu({ code: data.code }).then((response) => {
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
    saveMenu() {
      this.$refs.menuform.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.isEdit) {
            updateMenu(this.menuform).then((data) => {
              this.loading = false
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              const treeNode = this.$refs.menutree.getCurrentNode()
              /**
               * 更新节点 */
              treeNode.path = data.path
              treeNode.component = data.component
              treeNode.hidden = data.hidden
              treeNode.name = data.name
              treeNode.title = data.title
              treeNode.level = data.level
              treeNode.icon = data.icon
              treeNode.sort = data.sort
              this.dialogMenuFormVisible = false
            }).catch(() => {
              this.loading = false
              this.$notify.error({
                title: '错误',
                message: '保存失败'
              })
            })
          } else {
            createMenu(this.menuform).then((data) => {
              this.loading = false
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              /**
               * 添加子节点 */
              const treeNode = this.$refs.menutree.getCurrentNode()
              if (!this.isEdit) {
                /**
                 * 与后端联调时去掉此段代码 结束 */
                this.$refs.menutree.append(data, treeNode)
                this.dialogMenuFormVisible = false
                return
              }
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

    renderContentMenu(h, { node, data, store }) {
      return (
        <span class='custom-tree-node'>
          <span>{data.title}</span>
          <span>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.addMenu(data) }>添加</el-button>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.editMenu(data) }>修改</el-button>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.removeMenu(node, data) }>删除</el-button>
          </span>
        </span>)
    },

    renderContent(h, { node, data, store }) {
      return (
        <span class='custom-tree-node'>
          <span>{node.label}</span>
          <span>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.add(data) }>添加</el-button>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.edit(data) }>修改</el-button>
            <el-button size='mini' type='text' style='font-size: 16px;' on-click={ () => this.remove(node, data) }>删除</el-button>
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
    font-size: 14px;
    padding-right: 8px;
  }
</style>
