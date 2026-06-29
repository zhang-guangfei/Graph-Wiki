<template>
  <div class="app-container">
    <div class="filter-container">
      <div class="elTree">
        <el-tree
          ref="xTree"
          :data="treeData"
          :highlight-current="true"
          :expand-on-click-node="false"
          :default-expanded-keys="idTrees"
          node-key="id">
          <span slot-scope="{ node, data }" class="custom-tree-node">
            <span>{{ node.label }} <i size="small" title="查看节点code" class="el-icon-view" style="font-color: gray" @click="() => getCode(data)"/></span>
            <span>
              <el-button size="small" title="新增子节点" type="info" icon="el-icon-plus" circle @click="() => append(data)"/>
              <el-button size="small" title="修改该节点" type="primary" icon="el-icon-edit" circle @click="() => update(data)"/>
              <el-button size="small" title="删除该节点" type="danger" icon="el-icon-delete" circle @click="() => remove(node, data)"/>
            </span>
          </span>
        </el-tree>
        <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false" :title="selectRow ? '编辑&保存' : '新增&保存'" width="400px">
          <el-form ref="form" :model="form" :rules="formRules" >
            <el-form-item label="名称" prop="name">
              <el-input v-model="form.name" autocomplete="off" style="width:250px" placeholder="请输入名称"/>
            </el-form-item>
            <!-- TODO -->
            <el-form-item label="代码" prop="extcode">
              <el-input v-model="form.extcode" autocomplete="off" style="width:250px" placeholder="请输入extcode，非必填"/>
            </el-form-item>
            <el-form-item prop="description">
              <label slot="label">&nbsp;&nbsp;备注</label>
              <el-input v-model="form.description" autocomplete="off" style="width:250px" placeholder="请输入备注"/>
            </el-form-item>
            <el-form-item prop="sort">
              <label slot="label">sort</label>
              <el-input-number v-model="form.sort" :min="1" :max="100" controls-position="right"/>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogFormVisible = false">取 消</el-button>
            <el-button :disabled="flag" type="primary" @click="submitEvent">确 定</el-button>
          </div>
        </el-dialog>
        <el-dialog :visible.sync="dialogFormCodeVisible" :close-on-click-modal="false" title="节点code信息" width="300px">
          <div align="center">
            <el-form ref="codeForm" :model="codeForm">
              <el-form-item label="节点code:">
                <span> {{ codeForm.code }} </span>
              </el-form-item>
            </el-form>
          </div>
        </el-dialog>
      </div>
    </div>
  </div>
</template>
<script>
import { queryAllList, addRecord, deleteRecord, updateRecord } from '@/api/dictionary'
import { arrayToTree } from '@/utils'
export default {
  name: 'Index',
  data() {
    return {
      tableData: [],
      treeData: [],
      dialogFormVisible: false,
      formRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        sort: [
          { required: true, message: '请输入顺序', trigger: 'blur' }
        ]
      },
      form: {
        id: '',
        extcode: '',
        name: '',
        description: '',
        pid: '',
        sort: ''
      },
      selectRow: null,
      flag: false,
      dialogFormCodeVisible: false,
      codeForm: {
        name: '',
        description: '',
        code: ''
      },
      see: false,
      idTrees: []
    }
  },
  created() {
    this.getList()
  },
  methods: {
    // 页面初始化函数，将数组转换为树形结构
    getList() {
      queryAllList().then(data => {
        this.treeData = arrayToTree(data)
        this.treeData.forEach(m => {
          this.idTrees.push(m.id)
        })
      })
    },
    // 查看节点code
    getCode(data) {
      this.codeForm = data
      this.dialogFormCodeVisible = true
    },
    // 增加节点，将该节点的id赋给新增节点作为pid
    append(data) {
      this.form = {}
      this.dialogFormVisible = true
      this.form.pid = data.id
      this.selectRow = null
      this.flag = false
    },
    update(data) {
      this.form = data
      this.selectRow = data
      this.dialogFormVisible = true
      this.flag = false
    },
    remove(node, data) {
      const delList = []
      delList.push(data.id)
      // 如果该节点下有子节点，将子节点的id全部增加到删除列表
      if (data.children) {
        data.children.forEach(element => {
          delList.push(element.id)
        })
      }
      this.$confirm('此操作将永久删除该节点及该节点下的所有节点, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteRecord(delList).then((data) => {
          this.$notify({
            title: '成功',
            message: '删除成功',
            type: 'success',
            duration: 2000
          })
          node.remove(data)
        }).catch(() => {
          this.$notify.error({
            title: '错误',
            message: '删除失败'
          })
        })
      }).catch(() => {
        this.$notify.info({
          title: '消息',
          message: '已取消删除'
        })
      })
    },
    // 新增、更新按钮的提交事件
    submitEvent() {
      this.$refs.form.validate((valid) => {
        if (valid) {
          this.dialogFormVisible = false
          // 判断是增加数据还是更新数据
          if (this.selectRow) {
            // 更新数据，传参id和修改后的name
            const saveData = { id: this.form.id, name: this.form.name, extcode: this.form.extcode, description: this.form.description, sort: this.form.sort }
            updateRecord(saveData).then(data => {
              const selectNode = this.$refs.xTree.getCurrentNode()
              this.$notify({
                title: '成功',
                message: '更新成功',
                type: 'success'
              })
              // this.getList()
              this.flag = true
              /**
               * 更新节点 */
              selectNode.name = data.name
            }).catch(() => {
              this.$notify.error({
                title: '错误',
                message: '更新失败'
              })
              this.flag = true
            })
          } else {
            // 新增数据，传参name和pid
            // TODO
            const saveData = { name: this.form.name, extcode: this.form.extcode, description: this.form.description, sort: this.form.sort, pid: this.form.pid }
            addRecord(saveData).then(data => {
              const selectNode = this.$refs.xTree.getCurrentNode()
              const newChild = { id: data.id, label: data.name, name: data.name, children: [] }
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
                message: '新增成功',
                type: 'success'
              })
              // this.getList()
              this.flag = true
              selectNode.children.push(newChild)
            }).catch(() => {
              this.$notify.error({
                title: '错误',
                message: '新增失败'
              })
              this.flag = true
            })
          }
        } else {
          return false
        }
      })
    }
  }
}
</script>
<style scoped>
.row-flex-center{
	display: flex;
	flex-direction: row;
  justify-content: center;
  align-content: center;
  -webkit-justify-content: center;
}
.elTree{
	/* height: 498px;
	width: 1000px; */
	border:1px solid gainsboro;
	overflow-y: auto;
}
.flex-center{
	height: 100%;
	width: 100%;
	display: flex;
	align-items: center;
	justify-content: center;
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
<style >
/**修改element的默认样式,加elTree是防止在单页应用中污染全局的样式**/
.elTree .el-tree-node__content{
	height: 50px;
	border-bottom: 1px solid gainsboro;
}
/* 鼠标悬浮的当前节点  */
.el-tree-node__content:hover {
  background: #b1d5fd!important;
}
</style>
