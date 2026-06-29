<template>
  <div class="app-container">
    <el-card style="height:750px;">
      <!-- 操作按钮 -->
      <el-row style="flex-wrap: wrap; display: flex;">
        <el-col :span="2">
          <el-button :loading="exportTemplateLoading" size="small" type="primary" icon="el-icon-download" @click="exportDetailTemplate">下载模板</el-button>
        </el-col>
        <el-col :span="2">
          <el-upload
            :multiple="false"
            :before-upload="beforeUploadFile"
            :show-file-list="false"
            accept=".xlsx"
            action=""
            class="upload-demo">
            <el-button slot="trigger" :loading="importDetailLoading" size="small" type="primary" icon="el-icon-plus">选择文件</el-button>
          </el-upload>
        </el-col>
        <el-col :span="2">
          <el-button :loading="importDetailLoading" size="small" type="primary" icon="el-icon-upload2" @click="importDetailByTemplate(false)">{{ importDetailLoading ? '计算中...' : '货期计算' }}</el-button>
        </el-col>
        <el-col :span="2">
          <el-button :loading="importDetailLoading" size="small" type="primary" icon="el-icon-upload2" @click="importDetailByTemplate(true)">{{ importDetailLoading ? '计算中...' : '指定算法' }}</el-button>
        </el-col>
        <el-col :span="2">
          <el-button :loading="exportTemplateLoading" size="small" type="primary" icon="el-icon-download" @click="exportExcelResultList">导出结果</el-button>
        </el-col>
        <el-col :span="2">
          <div v-if="file !== null" class="el-upload__text">
            <span>{{ file.name }}</span>
          </div>
        </el-col>
        <el-col :span="2">
          <div v-if="costTime.length > 0" class="el-upload__text">
            <span>{{ costTime }}</span>
          </div>
        </el-col>
        <el-col :span="2"/>
        <el-col :span="2"/>
        <!-- 搜索栏 -->
        <el-col :span="4" align="right">
          <el-input
            v-model="search.modelNo"
            placeholder="检索型号"
            clearable
            @input="search_modelNo_input"
          />
        </el-col>
      </el-row>
      <!-- 表格区域 -->
      <el-table
        ref="multipleTable"
        :data="pageData"
        :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
        :row-style="{height:'33px'}"
        :cell-style="ellStyle"
        border
        stripe
        height="600"
      >
        <!-- 表头字段 -->
        <el-table-column
          v-show="true"
          key="index"
          prop="index"
          label="序号"
          type="index"
          width="90"
          align="center"
          how-overflow-tooltip
        />
        <el-table-column
          key="modelNo"
          prop="modelNo"
          label="型号"
          width="230"
          align="center"
          how-overflow-tooltip
        />
        <el-table-column
          key="qty"
          prop="qty"
          label="数量"
          width="90"
          align="center"
          how-overflow-tooltip
        />
        <el-table-column
          key="deptNo"
          prop="deptNo"
          label="营业所"
          width="110"
          align="center"
          how-overflow-tooltip
        />
        <el-table-column
          key="customerNo"
          prop="customerNo"
          label="客户代码"
          width="110"
          align="center"
          how-overflow-tooltip
        />
        <el-table-column
          key="endUser"
          prop="endUser"
          label="最终用户"
          width="110"
          align="center"
          how-overflow-tooltip
        />
        <el-table-column label="参考货期（单位：天）" align="center">
          <el-table-column
            key="deliveryDays"
            prop="deliveryDays"
            label="标准"
            width="90"
            align="center"
            how-overflow-tooltip
          />
          <el-table-column
            prop="slowDeliveryDays"
            label="适正"
            width="90"
            align="center"
            how-overflow-tooltip
          />
        </el-table-column>
        <template v-for="column in tableColumns">
          <el-table-column
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :type="column.type"
            :width="column.width"
            :align="column.align"
            :formatter="column.formatter"
            :v-show="true"
            show-overflow-tooltip
          />
        </template>
        <!--操作栏 -->
        <el-table-column label="详情" width="100px" align="center">
          <template slot-scope="scope">
            <el-popover
              placement="bottom"
              trigger="click"
            >
              <el-table
                ref="multipleTable"
                :data="scope.row.detail"
                :span-method="detailSpanMethod"
                :cell-style="detailCellStyle"
                :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
                border
                stripe>
                <!-- 表头字段 -->
                <el-table-column
                  v-show="true"
                  key="index"
                  prop="index"
                  label="序号"
                  width="90"
                  align="center"
                  how-overflow-tooltip
                />
                <el-table-column
                  key="modelNO"
                  prop="modelNO"
                  label="型号"
                  width="220"
                  align="center"
                  how-overflow-tooltip
                />
                <el-table-column
                  key="quantity"
                  prop="quantity"
                  label="数量"
                  width="110"
                  align="center"
                  how-overflow-tooltip
                />
                <el-table-column
                  key="deliveryDays"
                  prop="deliveryDays"
                  label="标准参考货期"
                  width="110"
                  align="center"
                  how-overflow-tooltip
                />
                <el-table-column
                  key="isStockInfo"
                  prop="isStockInfo"
                  label="出库区分"
                  width="110"
                  align="center"
                  how-overflow-tooltip
                />
                <el-table-column
                  key="supplierInventory"
                  :formatter="hFormatter"
                  prop="supplierInventory"
                  label="供应商库存数"
                  width="110"
                  align="center"
                  how-overflow-tooltip
                />
                <el-table-column label="采购周期（单位：天）" align="center">
                  <el-table-column
                    key="produceDay"
                    :formatter="hFormatter"
                    prop="produceDay"
                    label="生产"
                    width="100"
                    align="center"
                    how-overflow-tooltip
                  />
                  <el-table-column
                    key="calDesc"
                    :formatter="hFormatter"
                    prop="calDesc"
                    label="货期数据来源"
                    width="240"
                    align="center"
                    how-overflow-tooltip
                  />
                  <el-table-column
                    key="transportWayName"
                    :formatter="wayFormatter"
                    prop="transportWayName"
                    label="运输方式"
                    width="100"
                    align="center"
                    how-overflow-tooltip
                  />
                  <el-table-column
                    key="transportDay"
                    :formatter="hFormatter"
                    prop="transportDay"
                    label="运输"
                    width="100"
                    align="center"
                    how-overflow-tooltip
                  />
                </el-table-column>
                <el-table-column label="仓库作业周期（单位：天）" align="center">
                  <el-table-column
                    key="roDays"
                    :formatter="hFormatter"
                    prop="roDays"
                    label="收货"
                    width="100"
                    align="center"
                    how-overflow-tooltip
                  />
                  <el-table-column
                    key="dbDays"
                    :formatter="hFormatter"
                    prop="dbDays"
                    label="调拨"
                    width="100"
                    align="center"
                    how-overflow-tooltip
                  />
                  <el-table-column
                    key="warehouseToDeptDays"
                    prop="warehouseToDeptDays"
                    width="100"
                    label="送达"
                    align="center"
                    how-overflow-tooltip
                  />
                </el-table-column>
              </el-table>
              <!-- <el-button slot="reference">详情</el-button> -->
              <span slot="reference" title="查看详情" style="cursor: pointer;color:#337AB7;" @click="modelNoinit(scope.row)" > 查看</span>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页工具 -->
      <!--分页-->
      <el-pagination
        :total="total"
        :current-page="pageNumber"
        :page-sizes="[15,30,50,100]"
        :page-size="pageSize"
        background
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        @prev-click="pageNumber -= 1"
        @next-click="pageNumber += 1"
      />
    </el-card>
    <exportExcel ref="exportExcelVue"/>
  </div>
</template>

<script>
import { importData } from '@/api/eta/eta'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'Eta',
  components: { exportExcel },
  data() {
    return {
      // 表头字段
      tableColumns: [
        {
          label: '发货仓',
          prop: 'gatherWarehouseCode',
          width: 100,
          align: 'center'
        },
        {
          label: '出库区分',
          prop: 'isStockInfo',
          width: 100,
          align: 'center'
        },
        {
          label: '拆分',
          prop: 'doSource',
          width: 100,
          align: 'center'
        },
        {
          label: '执行结果',
          prop: 'instock',
          formatter: row => {
            if (row.instock === 0 || row.instock === 1) {
              return '成功'
            }
            return '失败'
          },
          width: 100,
          align: 'center'
        },
        {
          label: '货期数据来源',
          prop: 'calDesc',
          formatter: this.hFormatter,
          width: 172,
          align: 'center'
        },
        {
          label: '文字表述',
          prop: 'msg',
          width: 172,
          align: 'center'
        }
      ],
      file: null,
      exportTemplateLoading: false,
      importDetailLoading: false,
      search: {},
      // 数据表格
      tableData: [],
      // 数据表格
      allTableData: [],
      selectData: {},
      costTime: {},
      modelArr: [],
      modelPos: 0,
      pageData: [],
      pageNumber: 1,
      pageSize: 15,
      total: 0
    }
  },
  created() {
  },
  methods: {
    detailCellStyle({ row, column, rowIndex, columnIndex }) {
      if (columnIndex !== 0 && columnIndex !== 1 && row.colorFlag === 0) {
        return { backgroundColor: '#C7EDCC' }
      }
    },
    ellStyle({ row, column, rowIndex, columnIndex }) {
      if (this.selectData != null && this.selectData === row.index && columnIndex !== 11) {
        return { color: 'blue' }
      }
      if (columnIndex === 11) {
        if (row.instock === 2) {
          return { color: 'red' }
        } else {
          return { color: 'green' }
        }
      }
    },
    // 分页方法
    paging() {
      this.pageData = this.tableData.slice((this.pageNumber - 1) * this.pageSize, (this.pageNumber - 1) * this.pageSize + this.pageSize)
    },
    handleSizeChange(val) {
      this.pageSize = val
      this.paging()
    },
    handleCurrentChange(val) {
      this.pageNumber = val
      this.paging()
    },
    // 项目类别
    modelNoinit(row) {
      this.selectData = row.index
      const data = row.detail
      if (data != null) {
        this.modelArr = []
        this.modelPos = 0
        for (let i = 0; i < data.length; i++) {
          if (i === 0) {
            this.modelArr.push(1)
            this.modelPos = 0
          } else {
            if (data[i].index === data[i - 1].index) {
              this.modelArr[this.modelPos] += 1
              this.modelArr.push(0)
            } else {
              this.modelArr.push(1)
              this.modelPos = i
            }
          }
        }
      }
    },
    detailSpanMethod({ row, column, rowIndex, columnIndex }) {
      // 第一列和第一列
      if (columnIndex === 0 || columnIndex === 1) {
        const row1 = this.modelArr[rowIndex]
        const col1 = row1 > 0 ? 1 : 0
        return {
          rowspan: row1,
          colspan: col1
        }
      }
    },
    wayFormatter(row, column, cellValue) {
      if (row.transportDay === null || row.transportDay === 0) {
        return '-'
      } else {
        return cellValue
      }
    },
    hFormatter(row, column, cellValue) {
      if (cellValue === null || cellValue === 0) {
        return '-'
      } else {
        return cellValue
      }
    },
    deliveryDaysFormatter(row, column, cellValue) {
      if (cellValue === null) {
        return null
      } else {
        return cellValue + 2
      }
    },
    search_modelNo_input() {
      if (this.search.modelNo.length === 0) {
        this.tableData = this.allTableData
        this.total = this.tableData.length
      } else {
        this.$forceUpdate()
        this.tableData = this.allTableData
        // 通过modelNo字段进行筛选
        this.tableData = this.tableData.filter(data => !this.search ||
        data.modelNo.toLowerCase().includes(this.search.modelNo.toLowerCase()))
        this.total = this.tableData.length
      }
      this.paging()
    },
    exportDetailTemplate() {
      this.exportTemplateLoading = true
      window.open('../../static/template/downLoadTemplate/查询交货期模板.xlsx')
      this.exportTemplateLoading = false
    },
    async importDetailByTemplate(appoint) {
      const file = this.file
      if (!file) {
        return this.$message.warning('请选择文件')
      }
      const fileType = file.name.substring(file.name.lastIndexOf('.') + 1)
      if (fileType !== 'xlsx') {
        return this.$message.warning('文件格式错误，请重新导入(.xlsx)')
      }
      this.importDetailLoading = true
      // 导入申请项
      const formData = new FormData()
      formData.append('file', file)
      formData.append('appoint', appoint)
      importData(formData).then(res => {
        console.log(res.data)
        this.tableData = []
        this.allTableData = []
        this.costTime = ''
        if (res.success) {
          this.tableData = res.data.resultListData
          this.allTableData = res.data.resultListData
          this.total = res.data.resultListData.length
          this.paging()
          // this.costTime = res.data.cost
          this.smcInfoMsg(res.data.cost)
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.importDetailLoading = false
      }).catch(error => {
        this.importDetailLoading = false
        console.error(error)
      })
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    // 导出结果
    exportExcelResultList() {
      const tableColumn = [
        { field: 'index', title: '序号' },
        { field: 'modelNo', title: '型号' },
        { field: 'qty', title: '数量' },
        { field: 'deptNo', title: '营业所' },
        { field: 'customerNo', title: '客户代码' },
        { field: 'endUser', title: '最终用户' },
        { field: 'quantity', title: '数量' },
        { field: 'deliveryDays', title: '参考货期（单位：天）' },
        { field: 'gatherWarehouseCode', title: '发货仓' },
        { field: 'isStockInfo', title: '出库区分' },
        { field: 'doSource', title: '拆分' },
        { field: 'calDesc', title: '货期数据来源' },
        { field: 'msg', title: '文字表述' }
      ]
      this.$refs.exportExcelVue.initExportExcel(this.allTableData, tableColumn)
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
.el-button--primary {
    color: #FFFFFF;
    background-color: #0076BD;
    border-color: #0076BD;
}
</style>
