<template>
  <div class="app-container">
    <el-card style="height:880px">
      <!-- 搜索栏 -->
      <el-row :gutter="20">
        <el-col :span="3">
          <el-input
            v-model="queryCondition.condition.warehouseCode"
            placeholder="仓库代码"
            size="small"
            clearable
            @clear="getTableDataEvent"
            @keyup.enter.native="getTableDataEvent"
          />
        </el-col>
        <el-col :span="3">
          <!-- <el-cascader
            ref ="deptCascader"
            :props="props"
            :options="scopeOptions"
            :show-all-levels="false"
            collapse-tags
            placeholder="营业所代码"
            style="min-width: 200px"
            size="small"
            filterable
            clearable
            @change="handleScopeChange"
          /> -->
          <department @handleScopeChange="handleScopeChange"/>
        </el-col>
        <el-col :span="4">
          <el-button
            v-permission="['762905']"
            size="small"
            icon="el-icon-search"
            @click="getTableDataEvent()"
          />
        </el-col>
      </el-row>
      <el-divider />
      <!-- 操作按钮 -->
      <el-row :gutter="20">
        <el-col :span="1.5">
          <el-button
            v-permission="['150875']"
            type="primary"
            @click="showAddDialog()"
          >添加</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            v-permission="['150875']"
            type="primary"
            @click="deletesEvent()"
          >删除</el-button>
        </el-col>

      </el-row>
      <!-- 表格区域 -->
      <el-table
        ref="multipleTable"
        :data="tableData.list"
        :header-cell-style="{padding: '1px'}"
        :cell-style="{padding: '5px 5px'}"
        border
        stripe
        height="650"
      >
        <!-- 表头字段 -->
        <template v-for="column in tableColumns">
          <el-table-column
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :type="column.type"
            :width="column.width"
            :formatter="column.formatter"
            :fixed="column.fixed"
          />
        </template>
        <!--操作栏 -->
        <el-table-column
          label="操作"
          width="140px"
        >
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-col :span="12">
              <el-button
                v-permission="['150875']"
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row)"
              />
            </el-col>
            <!-- 删除 -->
            <el-col :span="12">
              <el-button
                v-permission="['150875']"
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="deleteEvent(scope.row)"
              />
            </el-col>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页工具 -->
      <el-pagination
        :current-page="tableData.pageNum"
        :page-sizes="[15, 30, 50, 100]"
        :page-size="tableData.pageSize"
        :total="tableData.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>

    <!-- 弹出层区域 -->
    <!-- 添加 -->
    <el-dialog
      :visible.sync="addDialog.display"
      title="添加"
      width="500px"
      @close="addFormReset"
    >
      <el-form
        ref="addFormRef"
        :rules="rules"
        :model="addDialog.addForm"
        label-width="auto"
      >
        <el-row :gutter="20">

          <el-col :span="22">
            <el-form-item
              label="仓库代码: "
              prop="warehouseCode"
            >
              <el-input v-model="addDialog.addForm.warehouseCode" />
            </el-form-item>
          </el-col>

          <el-col :span="22">
            <el-form-item
              label="营业所代码: "
              prop="salesBranchId"
            >
              <el-input v-model="addDialog.addForm.salesBranchId" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="优先级: "
              prop="priority"
            >
              <el-input v-model.number="addDialog.addForm.priority" />
            </el-form-item>
          </el-col>

          <el-col :span="22">
            <el-form-item
              label="交货日期: "
              prop="deliveryDay"
            >
              <el-input v-model.number="addDialog.addForm.deliveryDay" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="描述: "
              prop="description"
            >
              <el-input v-model="addDialog.addForm.description" />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="addButton('addFormRef')"
        >确 定</el-button>
        <el-button
          type="info"
          @click="addDialog.display = false"
        >取 消</el-button>
      </span>
    </el-dialog>
    <!-- 修改 -->
    <el-dialog
      :visible.sync="editDialog.display"
      title="修改"
      width="500px"
      @close="editFormReset"
    >
      <el-form
        ref="editFormRef"
        :rules="rules"
        :model="editDialog.editForm"
        label-width="auto"
      >
        <el-row :gutter="20">

          <el-col :span="22">
            <el-form-item
              label="仓库代码: "
              prop="warehouseCode"
            >
              <el-input v-model="editDialog.editForm.warehouseCode" />
            </el-form-item>
          </el-col>

          <el-col :span="22">
            <el-form-item
              label="营业所代码: "
              prop="salesBranchId"
            >
              <el-input v-model="editDialog.editForm.salesBranchId" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="优先级: "
              prop="priority"
            >
              <el-input v-model.number="editDialog.editForm.priority" />
            </el-form-item>
          </el-col>

          <el-col :span="22">
            <el-form-item
              label="交货日期: "
              prop="deliveryDay"
            >
              <el-input v-model.number="editDialog.editForm.deliveryDay" />
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="描述: "
              prop="description"
            >
              <el-input v-model="editDialog.editForm.description" />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="editButton('editFormRef')"
        >确 定</el-button>
        <el-button
          type="info"
          @click="editDialog.display = false"
        >取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { findAllHouse, findDeptDict, findhouse_branch, addhouse_branch, edithouse_branch, deletehouse_branch, deleteshouse_branch } from '@/api/warehouseManage'
import Department from '@/components/Department'
export default {
  name: 'HouseBranch',
  components: { Department },
  data() {
    return {
      DictData: {
        scopeOptions: [],
        department: [],
        warehouse: []
      },
      props: { multiple: true },
      // 查询条件
      queryCondition: {
        condition: {},
        pageNumber: 1,
        pageSize: 15,
        orderBy: ''
      },
      // 表头字段
      tableColumns: [
        {
          type: 'selection',
          label: '选择框',
          width: '50'
        },
        {
          label: '营业所代码',
          prop: 'salesBranchId'
        },
        {
          label: '营业所',
          formatter: row => {
            const item = this.DictData.department.find(dict => dict.code === row.salesBranchId)
            return item ? item.desc : row.salesBranchId
          }
        },

        {
          label: '仓库代码',
          prop: 'warehouseCode'
        },
        {
          label: '仓库 ',
          formatter: row => {
            const item = this.DictData.warehouse.find(dict => dict.code === row.warehouseCode)
            return item ? item.desc : row.warehouse
          }
        },
        {
          label: '描述',
          prop: 'description'
        },
        {
          label: '优先级',
          prop: 'priority',
          width: 100
        },
        {
          label: '标准运输时间',
          prop: 'deliveryDay',
          width: 120,
          formatter: row => {
            return row.deliveryDay + '天'
          }
        }

      ],
      // 数据表格
      tableData: {
        total: 0,
        pageNumber: 0,
        pageSize: 0,
        list: []
      },
      // 添加对话框
      addDialog: {
        display: false,
        addForm: {}
      },
      // 修改对话框
      editDialog: {
        display: false,
        editForm: {}
      },
      // 校验规则
      rules: {
        warehouseCode: [
          { required: true, message: '请输入仓库代码', trigger: 'blur' }
        ],
        salesBranchId: [
          { required: true, message: '请输入营业所代码', trigger: 'blur' }
        ],
        priority: [
          { required: true, message: '请输入优先级', trigger: 'blur' },
          { type: 'integer', message: '优先级格式为数字', trigger: 'blur' }
        ]
      }
    }
  },
  created() {
    // 营业所
    findDeptDict().then(result => {
      console.log(result)
      result.forEach(dict => {
        this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
      })
    })
    // 仓库
    findAllHouse().then(result => {
      console.log(result)
      result.data.forEach(dict => {
        this.DictData.warehouse.push({ code: dict.warehouseCode, desc: dict.warehouseName })
      })
    })
    this.getTableDataEvent()
  },

  methods: {
    handleScopeChange(val) {
      console.log('所选营业所=>', val)
      this.queryCondition.condition.salesBranchId = val
    },

    // 查询数据
    getTableDataEvent() {
      console.log('查询条件：')
      console.log(this.queryCondition)
      findhouse_branch(this.queryCondition).then(res => {
        console.log('获取表格数据')
        console.log(res)
        this.tableData.list = res.data.list
        this.tableData.total = res.data.total
        this.tableData.pageNum = res.data.pageNum
        this.tableData.pageSize = res.data.pageSize
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 改变每页条数
    handleSizeChange(newSize) {
      this.queryCondition.pageSize = newSize
      this.getTableDataEvent()
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.queryCondition.pageNumber = newCurrent
      this.getTableDataEvent()
    },
    // 显示添加对话框
    showAddDialog() {
      this.addDialog.display = true
    },
    // 重置表单
    addFormReset() {
      this.addDialog.addForm = {}
    },
    // 增添事件
    addEvent() {
      addhouse_branch(this.addDialog.addForm).then(res => {
        this.addDialog.display = false
        this.getTableDataEvent()
        console.log(res)
        this.smcSuccessMsg(res.message)
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 添加按钮事件
    addButton(formNameRef) {
      this.$refs[formNameRef].validate((valid) => {
        if (valid) {
          this.addEvent()
        } else {
          this.smcErrorMsg('表单校验失败，请输入正确的格式')
        }
      })
    },

    // 显示修改对话框
    showEditDialog(row) {
      this.editDialog.editForm = Object.assign({}, row)
      console.log(this.editDialog.editForm)
      this.editDialog.display = true
    },

    // 重置表单
    editFormReset() {
      this.editDialog.editForm = {}
    },

    // 修改事件
    editEvent() {
      edithouse_branch(this.editDialog.editForm).then(res => {
        this.editDialog.display = false
        this.getTableDataEvent()
        console.log(res)
        this.smcSuccessMsg(res.message)
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },

    // 修改按钮事件
    editButton(formNameRef) {
      this.$refs[formNameRef].validate((valid) => {
        if (valid) {
          this.editEvent()
        } else {
          this.smcErrorMsg('表单校验失败，请输入正确的格式')
        }
      })
    },
    // 删除本行事件
    deleteEvent(row) {
      this.$confirm('此操作将永久删除本条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deletehouse_branch(row.id).then(res => {
          this.getTableDataEvent()
          console.log(res)
          this.smcSuccessMsg(res.message)
        }).catch(res => {
          console.log(res)
          this.smcErrorMsg(res.message)
        })
      })
    },
    // 删除多行事件
    deletesEvent() {
      this.$confirm('此操作将永久删除选中的多条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var list = []
        for (const item of this.$refs.multipleTable.selection) {
          list.push(item.id)
        }
        console.log(list)
        deleteshouse_branch(list).then(res => {
          this.getTableDataEvent()
          console.log(res)
          this.smcSuccessMsg(res.message)
        }).catch(res => {
          console.log(res)
          this.smcErrorMsg(res.message)
        })
      })
    }

  }
}
</script>
<style lang="scss">
.el-cascader__search-input{
  min-width: 10px;
}
</style>
<style scoped>

.el-card {
  margin: 5px;
}
.el-row {
  margin: 10px !important;
}
.el-card .el-table {
  margin: 10px;
  width: auto;
}
.el-form {
  margin: 0;
}
div /deep/ .el-input__inner {
  width: 100%;
}
/* input的宽度*/
input.el-input__inner {
  width: 100%;
}
/* select选择框宽度 */
.el-select {
  width: 100%;
}
/*时间选择框宽度*/
.el-date-editor.el-input {
  width: 100%;
}
/*超链接字体大小*/
.el-link {
  font-size: 18px;
}
/*input的lable的字体高度*/
.el-form-item .el-form-item__label {
  margin-top: 15px;
}
ul,
li {
  list-style-type: none;
  padding: 5px;
}
</style>

