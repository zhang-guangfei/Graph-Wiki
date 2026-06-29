<template>
  <div class="app-container">
    <el-row>
      <el-col :span="3">
        <el-card style="height:880px;">
          <div>
            <span>时间列表</span>
          </div>
          <ul>
            <li
              v-for="item in menuData.diffDateMenu"
              :key="item"
            >
              <el-link @click="searchByDateEvent(item)">
                {{ DateFormatter(item) }}
              </el-link>
            </li>
          </ul>
        </el-card>
      </el-col>
      <el-col :span="21">
        <el-card style="height:880px;">
          <!-- 搜索 -->
          <el-row :gutter="20">
            <el-col :span="4">
              <el-input
                v-model="queryCondition.condition.warehouseCode"
                placeholder="仓库代码"
                clearable
                @keyup.enter.native="getTableDataEvent"
                @clear="getTableDataEvent"
              />
            </el-col>
            <el-col :span="4">
              <el-input
                v-model="queryCondition.condition.modelno"
                placeholder="型号"
                clearable
                @keyup.enter.native="getTableDataEvent"
                @clear="getTableDataEvent"
              />
            </el-col>
            <el-col :span="4">
              <template>
                <el-select
                  v-model="queryCondition.condition.inventoryStatus"
                  placeholder="库存状态"
                  clearable
                  @change="getTableDataEvent"
                >
                  <el-option
                    v-for="item in menuData.inventoryStatusMenu"
                    :key="item.code"
                    :label="item.desc"
                    :value="item.code"
                  />
                </el-select>
              </template>
            </el-col>
            <el-col :span="4">
              <template>
                <el-select
                  v-model="queryCondition.condition.qaStatus"
                  placeholder="质量状态"
                  clearable
                  @change="getTableDataEvent"
                >
                  <el-option
                    v-for="item in menuData.qualityStatusMenu"
                    :key="item.code"
                    :label="item.desc"
                    :value="item.code"
                  />
                </el-select>
              </template>
            </el-col>
            <el-col :span="4">
              <el-button
                icon="el-icon-search"
                @click="getTableDataEvent"
              />
            </el-col>
          </el-row>
          <el-divider />
          <!-- 表格区域 -->
          <el-table
            ref="multipleTable"
            :data="tableData.list"
            :cell-style="{padding: '10px'}"
            border
            stripe
            height="710"
          >
            <!-- 表头字段 -->
            <el-table-column
              v-for="column in tableColumns"
              :key="column.prop"
              :prop="column.prop"
              :label="column.label"
              :width="column.width"
              :formatter="column.formatter"
            />

          </el-table>
          <!-- 分页工具 -->
          <el-pagination
            :current-page="tableData.pageNum"
            :page-sizes="[ 10, 15,30, 50, 100]"
            :page-size="tableData.pageSize"
            :total="tableData.total"
            background
            layout="total, sizes, prev, pager, next"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { findDiffInventory, finddiffdateList } from '@/api/warehouseManage'
import { getDataByPid } from '@/api/dictionary'
import { Constants } from '@/utils/optionUtils'
import moment from 'moment'
export default {
  name: 'DiffInventory',
  data() {
    return {
      // 菜单数据
      menuData: {
        // 差异时间菜单
        diffDateMenu: [],
        // 质量状态菜单
        qualityStatusMenu: [],
        // 库存状态菜单
        inventoryStatusMenu: []
      },
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
          label: '批次日期',
          prop: 'batchDate',
          formatter(row, column, cellValue, index) {
            return moment(cellValue).format('YYYY-MM-DD')
          }
        },
        {
          label: '仓库代码',
          prop: 'warehouseCode'
        },

        {
          label: '库存状态',
          prop: 'inventoryStatus',
          formatter: this.inventoryStatusFormatter
        },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '质量状况',
          prop: 'qaStatus',
          formatter: this.qualityStatusFormatter
        },
        {
          label: 'OPS在库数',
          prop: 'oQuantity'
        },
        {
          label: 'WMS在库数',
          prop: 'wQuantity'
        }

      ],
      // 数据表格
      tableData: {}

    }
  },
  created() {
    this.initData()
  },
  methods: {
    // 菜单数据初始化
    initData() {
      // 从数据库中获取时间列表信息
      finddiffdateList().then(res => {
        this.menuData.diffDateMenu = res.data
        console.log('获取时间列表：')
        console.log(this.menuData.diffDateMenu)
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      // 从数据字典中获取库存状态菜单信息
      getDataByPid(Constants.inventoryStatusList).then(res => {
        res.forEach(element => {
          this.menuData.inventoryStatusMenu.push({ code: element.extcode, desc: element.name })
        })
        console.log('获取库存状态菜单信息：')
        console.log(this.menuData.inventoryStatusMenu)
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      // 从数据字典中获取质量状态菜单信息
      getDataByPid(Constants.qualityStatusList).then(res => {
        res.forEach(element => {
          this.menuData.qualityStatusMenu.push({ code: element.extcode, desc: element.name })
        })
        console.log('获取质量状态菜单信息：')
        console.log(this.menuData.inventoryStatusMenu)
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    // 点击日期列表触发事件
    searchByDateEvent(date) {
      this.queryCondition.condition = {}
      this.queryCondition.condition.batchDate = date
      this.getTableDataEvent()
    },
    // 获取表格数据事件
    getTableDataEvent() {
      console.log('查询条件：')
      console.log(this.queryCondition)
      findDiffInventory(this.queryCondition).then(res => {
        console.log('查询到数据：')
        console.log(res)
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
    // 日期格式化
    DateFormatter(date) {
      return moment(date).format('YYYY-MM-DD')
    },
    // 库存状态格式化
    inventoryStatusFormatter(row, column, cellValue, index) {
      return this.menuData.inventoryStatusMenu.find(item => item.code === cellValue).desc
    },
    //  质量状态格式化
    qualityStatusFormatter(row, column, cellValue, index) {
      return this.menuData.qualityStatusMenu.find(item => item.code === cellValue).desc
    }
  }
}
</script>
<style scoped>
.el-card {
  margin: 5px;
}
.el-row {
  margin: 0 10px !important;
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

