<template>
  <div class="app-container">
    <div class="filter-container">
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" @click="addTopNode">添加顶级节点</el-button>
      <el-button v-show="isShow" class="filter-item" style="margin-left: 10px;" type="primary" @click="addChildNode">添加子节点</el-button>
    </div>
    <el-container>
      <el-aside width="350px">
        <el-tree
          ref="tree"
          :data="menus"
          :props="defaultProps"
          highlight-current
          node-key="id"
          @node-click="handleNodeClick"/>
      </el-aside>
      <el-container>
        <el-main>
          <el-form ref="form" :model="form" :rules="menuRules" label-width="180px">
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name"/>
            </el-form-item>
            <el-form-item label="标题" prop="title">
              <el-input v-model="form.title"/>
            </el-form-item>
            <el-form-item label="图片" prop="icon">
              <el-input v-model="form.icon"/>
            </el-form-item>
            <el-form-item label="路径" prop="path">
              <el-input v-model="form.path"/>
            </el-form-item>
            <el-form-item label="顺序" prop="sort">
              <el-input-number v-model="form.sort" :min="1" :max="100" controls-position="right"/>
            </el-form-item>
            <el-form-item class="buttons">
              <el-button :loading="loading" type="primary" @click="saveNode">保存</el-button>
            </el-form-item>
            <el-form-item prop="pid">
              <el-input v-show="false" v-model="form.pid" />
              <el-input v-show="false" v-model="form.id" />
            </el-form-item>
          </el-form>
        </el-main>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import { fetchList, createMenu, updateMenu } from '@/api/menu'
import { arrayToTree } from '@/utils'

export default {
  data() {
    return {
      form: {
        id: '',
        name: '',
        title: '',
        sort: '',
        path: '',
        pid: ''
      },
      loading: false,
      isShow: false,
      isEdit: true,
      menus: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      menuRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        title: [
          { required: true, message: '请输入标题', trigger: 'blur' }
        ],
        path: [
          { required: true, message: '请选择路径', trigger: 'blur' }
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
        this.menus = arrayToTree(data)
      })
    },
    saveNode() {
      this.$refs.form.validate(valid => {
        if (valid) {
          this.loading = true
          if (this.isEdit) {
            updateMenu(this.form).then((data) => {
              this.loading = false
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              const treeNode = this.$refs.tree.getCurrentNode()
              /**
               * 更新节点 */
              treeNode.name = data.name
              treeNode.title = data.title
              treeNode.icon = data.icon
              treeNode.path = data.path
              treeNode.sort = data.sort
            }).catch(() => {
              this.loading = false
              this.$notify.error({
                title: '错误',
                message: '保存失败'
              })
            })
          } else {
            createMenu(this.form).then((data) => {
              this.loading = false
              this.$notify({
                title: '成功',
                message: '保存成功',
                type: 'success',
                duration: 2000
              })
              /**
               * 添加顶级节点 */
              if (!this.isShow) {
                /**
                 * 与后端联调时去掉此段代码 结束 */
                this.menus.push(data)
                return
              }
              /**
               * 添加子节点 */
              const treeNode = this.$refs.tree.getCurrentNode()
              if (!this.isEdit) {
                /**
                 * 与后端联调时去掉此段代码 结束 */
                this.$refs.tree.append(data, treeNode)
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
    addTopNode() {
      this.isShow = false
      this.form = {}
      this.isEdit = false
    },
    addChildNode() {
      const pid = this.form.id
      this.form = {}
      this.form.pid = pid
      this.isEdit = false
    },
    handleNodeClick(data) {
      this.isEdit = true
      this.isShow = true
      const [id, name, title, path, sort, pid, icon] = [data.id, data.name, data.title, data.path, data.sort, data.pid, data.icon]
      this.form = { id, name, title, path, sort, pid, icon }
    }
  }
}
</script>

<style>
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  .el-aside {
    color: #333;
  }
</style>
