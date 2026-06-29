<template>
  <div class="app-container">
    <el-card style="height:880px;">
      <!-- 搜索栏 -->
      <el-row :gutter="20">
        <el-col :span="3">
          <el-input
            v-model="queryCondition.condition.warehouseCode"
            placeholder="仓库代码"
            clearable
            @clear="getTableDataEvent"
            @keyup.enter.native="getTableDataEvent"
          />
        </el-col>
        <el-col :span="1.5">
          <el-button
            v-permission="['611878']"
            icon="el-icon-search"
            @click="getTableDataEvent()"
          />
        </el-col>
      </el-row>
      <el-divider/>
      <!-- 操作按钮 -->
      <el-row :gutter="20">
        <el-col :span="1.5">
          <el-button
            v-permission="['676964']"
            type="primary"
            @click="showAddDialog()"
          >添加
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            v-permission="['676964']"
            type="primary"
            @click="deletesEvent()"
          >删除
          </el-button>
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
            :v-show="true"
            show-overflow-tooltip
          />
        </template>
        <!--操作栏 -->
        <el-table-column
          label="操作"
          width="140px"
        >
          <template slot-scope="scope">
            <!-- 修改 -->
            <el-col :span="8">
              <el-button
                v-permission="['676964']"
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row)"
              />
            </el-col>
            <!-- 删除 -->
            <el-col :span="8">
              <el-button
                v-permission="['676964']"
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
        :page-sizes="[ 15, 20, 50, 100]"
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
      width="800px"
      @close="addFormReset"
    >
      <el-form
        ref="addFormRef"
        :rules="rules"
        :model="addDialog.addForm"
        label-width="90px"
      >
        <el-divider content-position="left">仓库信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="仓库代码: "
              prop="warehouseCode"
            >
              <el-input v-model="addDialog.addForm.warehouseCode"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="邮政编码: "
              prop="postCode"
            >
              <el-input v-model="addDialog.addForm.postCode"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="省市区: "
              prop="Areas"
            >
              <el-cascader
                v-model="addDialog.area"
                :options="menuData.Areas"
                style="width: 100%"
                @change="areaAddCode()"
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人:"
              prop="linkman"
            >
              <el-input v-model="addDialog.addForm.linkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人英文:"
            >
              <el-input v-model="addDialog.addForm.englishLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="联系邮箱:"
              prop="mail"
            >
              <el-input v-model="addDialog.addForm.mail" style="width:580px"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系电话:"
              prop="linkPhone"
            >
              <el-input v-model="addDialog.addForm.linkPhone"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系手机:"
              prop="linkMobile"
            >
              <el-input v-model="addDialog.addForm.linkMobile"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址: "
              prop="adress">
              <el-input v-model="addDialog.addForm.adress"/>
              <!-- <el-select v-model="priorityComplete" size="mini" placeholder="请选择" style="width: 120px;">
                <el-option :value="1" label="是"/>
              </el-select> -->
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址英文: "
              label-width="110px"
            >
              <el-input v-model="addDialog.addForm.englishAddress"/>
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
      width="800px"
      destroy-on-close
      @close="editFormReset"
    >
      <el-form
        ref="editFormRef"
        :rules="rules"
        :model="editDialog.editForm"
        label-width="90px"
      >
        <el-divider content-position="left">仓库信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="仓库代码: "
              prop="warehouseCode"
            >
              <el-input
                v-model="editDialog.editForm.warehouseCode"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="邮政编码: "
              prop="postCode"
            >
              <el-input v-model="editDialog.editForm.postCode"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="省市区: "
            >
              <el-cascader
                v-model="editDialog.area"
                :options="menuData.Areas"
                style="width: 100%"
                @change="areaEditCode()"
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人:"
              prop="linkman"
            >
              <el-input v-model="editDialog.editForm.linkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人英文:"
            >
              <el-input v-model="editDialog.editForm.englishLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="联系邮箱:"
              prop="mail"
            >
              <el-input v-model="editDialog.editForm.mail" style="width: 580px"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系电话:"
              prop="linkPhone"
            >
              <el-input v-model="editDialog.editForm.linkPhone"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系手机:"
              prop="linkMobile"
            >
              <el-input v-model="editDialog.editForm.linkMobile"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址: "
              prop="adress"
            >
              <el-input v-model="editDialog.editForm.adress"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址英文: "
              label-width="110px"
            >
              <el-input v-model="editDialog.editForm.englishAddress"/>
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
import { findHouseMulAddress, saveMultAddress, updateMultAddress, getMultAdressById, deleteMultAddress, deleteListMulAddress, checkAddressConfig, findAreas } from '@/api/warehouseManage'
export default {
  name: 'Warehouse',
  data() {
    return {
      // 菜单数据
      menuData: {
        // 仓库类别菜单
        Areas: [],
        warehouseTypeMenu: [],
        parentCode: [
        ]
      },
      // 查询条件
      queryCondition: {
        condition: {},
        pageNumber: 1,
        pageSize: 20,
        orderBy: ''
      },
      // 表头字段
      tableColumns: [
        {
          type: 'selection',
          label: '选择框'
        },
        {
          label: 'ID',
          prop: 'id',
          width: 80
        },
        {
          label: '仓库代码',
          prop: 'warehouseCode',
          width: 80
        },
        {
          label: '仓库地址',
          prop: 'adress',
          width: 300
        },
        {
          label: '联系人',
          prop: 'linkman',
          width: 120
        },
        {
          label: '联系固定电话',
          prop: 'linkPhone',
          width: 160
        },
        {
          label: '联系人移动电话',
          prop: 'linkMobile',
          width: 160
        },
        {
          label: '邮编',
          prop: 'postCode',
          width: 120
        },
        {
          label: '联系人邮箱',
          prop: 'mail'
        }
      ],
      // 数据表格
      tableData: {},

      // 添加对话框
      addDialog: {
        display: false,
        area: [],
        addForm: {
          disableFlag: false,
          assemblyFlag: false,
          autodispatchFlag: false,
          centralizeFlag: false,
          purchaseFlag: false,
          returnableFlag: false,
          transferFlag: false,
          prestockFlag: false

        }

      },
      // 修改对话框
      editDialog: {
        display: false,
        area: [],
        editForm: {
        }
      },
      // 校验规则
      rules: {
        warehouseCode: [
          { required: true, message: '请输入仓库代码', trigger: 'blur' }
        ],
        postCode: [
          { required: true, message: '此项不能为空', trigger: 'blur' }
        ],
        // Areas: [
        //   { type: 'array', required: true, message: '此项不能为空', trigger: 'change' }
        // ],
        adress: [
          { required: true, message: '此项不能为空', trigger: 'blur' }
        ],
        linkman: [
          { required: true, message: '此项不能为空', trigger: 'blur' }
        ],
        mail: [
          { required: true, message: '此项不能为空', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }
        ],
        linkPhone: [
          { required: true, message: '此项不能为空', trigger: 'blur' },
          { pattern: /^((0\d{2,3}(-)?\d{7,8})|(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8})((-)?\d{3,})?$/, message: '电话号格式不正确', trigger: ['blur'] }
        ],
        linkMobile: [
          { required: true, message: '此项不能为空', trigger: 'blur' },
          { pattern: /^1[34578]\d{9}$/, message: '手机号格式不正确', trigger: ['blur'] }
        ]
      }
    }
  },
  created() {
    this.initData()
    this.getAreas()
    this.getTableDataEvent()
  },
  methods: {
    // 菜单数据初始化
    initData() {
    },
    // 获取省市区根目录
    getAreas() {
      findAreas().then(res => {
        if (res.success) {
          this.menuData.Areas = res.data
        }
      })
    },
    areaAddCode() {
      this.addDialog.addForm.province = this.addDialog.area[0]
      this.addDialog.addForm.city = this.addDialog.area[1]
      this.addDialog.addForm.district = this.addDialog.area[2]
    },
    areaEditCode() {
      this.editDialog.editForm.province = this.editDialog.area[0]
      this.editDialog.editForm.city = this.editDialog.area[1]
      this.editDialog.editForm.district = this.editDialog.area[2]
    },
    // 获取表格数据事件
    getTableDataEvent() {
      findHouseMulAddress(this.queryCondition).then(res => {
        this.tableData = res.data
      }).catch(res => {
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
      this.addDialog.area = []
      this.addDialog.addForm = {}
      this.$refs['addFormRef'].resetFields()
    },
    // 添加按钮事件
    addButton(formNameRef) {
      // 表单校验
      this.$refs[formNameRef].validate((valid) => {
        if (valid) {
          this.addEvent()
        } else {
          this.smcErrorMsg('表单校验失败，请输入正确的格式')
        }
      })
    },
    // 增添事件
    addEvent() {
      if (this.addDialog.addForm.province == null) {
        this.smcErrorMsg('请填写省市区')
        return false
      }
      saveMultAddress(this.addDialog.addForm).then(res => {
        this.addDialog.display = false
        this.getTableDataEvent()
        this.smcSuccessMsg(res.message)
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },

    // 显示修改对话框
    showEditDialog(row) {
      this.editDialog.display = true
      // 赋值到表单上
      getMultAdressById(row.id).then(res => {
        this.editDialog.editForm = res.data
        this.editDialog.area = [this.editDialog.editForm.province, this.editDialog.editForm.city, this.editDialog.editForm.district]
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 重置表单
    editFormReset() {
      this.editDialog.editForm = {}
    },

    // 修改事件
    editEvent() {
      if (this.editDialog.editForm.province == null) {
        this.smcErrorMsg('请填写省市区')
        return false
      }
      if (this.editDialog.editForm.warehouseType !== 'WT') {
        this.editDialog.editForm.customerNo = ''
        this.editDialog.editForm.customerLinkman = ''
        this.editDialog.editForm.customerPhone = ''
        this.editDialog.editForm.customerMail = ''
      }
      updateMultAddress(this.editDialog.editForm).then(res => {
        this.editDialog.display = false
        this.getTableDataEvent()
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
        var deleteList = []
        deleteList.push(row.id)
        checkAddressConfig(deleteList).then(res => {
          if (res.success) {
            deleteMultAddress(row.id).then(res => {
              this.getTableDataEvent()
              this.smcSuccessMsg(res.message)
            }).catch(res => {
              this.smcErrorMsg(res.message)
            })
          } else {
            this.smcErrorMsg(res.message)
          }
        }).catch(res => {
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
        var deleteList = []
        this.$refs.multipleTable.selection.forEach(item => {
          deleteList.push(item.id)
        })
        checkAddressConfig(deleteList).then(res => {
          if (res.success) {
            deleteListMulAddress(deleteList).then(res => {
              this.getTableDataEvent()
              this.smcSuccessMsg(res.message)
            }).catch(res => {
              this.smcErrorMsg(res.message)
            })
          } else {
            this.smcErrorMsg(res.message)
          }
        }).catch(res => {
          this.smcErrorMsg(res.message)
        })
      })
    }
  }
}
</script>
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
