<template>
  <el-container>
    <el-main>
      <el-form ref="form" :model="form" :inline="true" size="mini" class="demo-form-inline">
        <el-form-item label="型号">
          <el-input v-model="form.modelNo" placeholder="请输入型号" clearable style="width: 150px" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select v-model="form.status" placeholder="状态" style="width: 115px">
            <el-option v-for="item in statusTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item label="时间" prop="status">
          <el-select v-model="form.dateType" placeholder="请选择时间类型" style="width: 130px" clearable="">
            <el-option v-for="item in dateTypeOptions" :key="item.value" :label="item.label" :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-date-picker
            v-model="startTimeAndEndTime"
            type="daterange"
            align="center"
            unlink-panels
            range-separator="-"
            start-placeholder="时间开始"
            end-placeholder="时间结束"
            format="yyyy-MM-dd"
            value-format="yyyy-MM-dd HH:mm:ss"
            style="width: 260px"
            size="mini"/>
        </el-form-item>
        <!-- <el-button type="success" size="mini" style="margin-left: 10px;" @click="addDept">新增</el-button> -->
        <el-button v-permission="['397214']" type="primary" size="mini" style="margin-left: 10px;" @click="findList">查询</el-button>
        <el-button v-permission="['201412']" type="primary" size="mini" @click="importSlowMoving()">导入</el-button>
        <el-button v-permission="['201412']" type="warning" size="mini" style="margin-left: 10px;" @click="updateInfo">编辑</el-button>
        <el-button v-permission="['201412']" type="danger" size="mini" style="margin-left: 10px;" @click="deletCondition">删除</el-button>
      </el-form>

      <el-dialog :visible.sync="updateDialog" :close-on-click-modal="false" title="修改" width="620px" >
        <el-form ref="slowModel" :inline="true" :model="slowModelData" label-width="100px" size="mini">
          <el-row type="flex">
            <el-form-item prop="modelNo" label="型号">
              <el-input v-model="slowModelData.modelNo" disabled style="width:150px"/>
            </el-form-item>
            <el-form-item prop="quantity" label="数量" >
              <el-input v-model="slowModelData.quantity" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item prop="warehouseCode" label="仓库代码">
              <el-input v-model="slowModelData.warehouseCode" style="width:150px"/>
            </el-form-item>
            <el-form-item prop="productName" label="品名" >
              <el-input v-model="slowModelData.productName" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item prop="eprice" label="E价">
              <el-input v-model="slowModelData.eprice" style="width:150px"/>
            </el-form-item>
            <el-form-item prop="lotPrice" label="LOT价格" >
              <el-input v-model="slowModelData.lotPrice" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item prop="status" label="状态">
              <el-input v-model="slowModelData.status" style="width:150px"/>
            </el-form-item>
            <el-form-item prop="remark" label="备注" >
              <el-input v-model="slowModelData.remark" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item prop="lastOutDate" label="上次出库时间">
              <el-date-picker
                v-model="slowModelData.lastOutDate"
                type="date"
                style="width:150px"
                placeholder="上次出库时间"
              />
            </el-form-item>
            <el-form-item prop="lastInDate" label="上次入库时间" >
              <el-date-picker
                v-model="slowModelData.lastInDate"
                type="date"
                style="width:150px"
                placeholder="上次入库时间"
              />
            </el-form-item>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="updateDialog = false">取 消</el-button>
          <el-button type="primary" @click="updateDialog = false;saveSlowModelData()">保存</el-button>
        </div>
      </el-dialog>

      <el-dialog v-dialogDrag :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
        <product-search v-if="productVisible" ref="ProductSearch"/>
      </el-dialog>

      <el-table
        v-loading="listLoading"
        ref="multipleTable"
        :header-cell-style="{color:'#000000'}"
        :data="tableData"
        tooltip-effect="dark"
        class="multipleTable"
        border
        size="small"
        stripe>
        <el-table-column
          type="selection"
          width="55"/>
        <el-table-column
          align="center"
          prop="modelNo"
          width="150"
          label="型号"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link :underline="false" type="primary" @click="handleClick(scope.row)">{{ scope.row.modelNo }}</el-link>
          </template>
        </el-table-column>

        <el-table-column
          align="center"
          prop="quantity"
          label="期初数"/>

        <el-table-column
          align="center"
          prop="leftQty"
          label="在库数"/>

        <el-table-column
          :formatter="formatStatus"
          align="center"
          prop="status"
          label="状态"/>

        <el-table-column
          align="center"
          prop="warehouseCode"
          width="100"
          label="仓库代码"/>

        <el-table-column
          align="center"
          prop="supplier"
          width="100"
          label="产地"/>

        <el-table-column
          align="center"
          prop="eprice"
          label="E单价"/>

        <el-table-column
          align="center"
          prop="lotPrice"
          label="LOT单价"/>

        <el-table-column
          align="center"
          prop="1"
          label="系列"/>

        <el-table-column
          align="center"
          prop="productName"
          width="100"
          label="品名"
          show-overflow-tooltip/>

        <el-table-column
          :formatter="formatProductFlag"
          align="center"
          prop="designTypeId"
          width="100"
          label="产品类别"/>

        <el-table-column
          align="center"
          prop="lastInDate"
          width="120"
          label="最近入库日期"/>

        <el-table-column
          align="center"
          prop="lastOutDate"
          width="120"
          label="最近出库日期"/>

        <el-table-column
          align="center"
          prop="remark"
          label="备注"/>

        <el-table-column
          align="center"
          prop="createTime"
          width="120"
          label="创建时间"/>

        <el-table-column
          align="center"
          prop="updateTime"
          width="120"
          label="更新时间"/>

        <el-table-column
          align="center"
          prop="createUser"
          label="创建人"/>
      </el-table>
      <el-pagination
        :page-sizes="[20, 50, 100, 200, 500]"
        :current-page="form.pageNum"
        :page-size="form.pageSize"
        :total="total"
        background
        style="margin-top: 10px"
        layout="total, sizes, prev, pager, next"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"/>
      <el-divider />

      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadFileDialogVisible"
        :before-close="closeClick"
        title="上传"
        width="420px"
      >
        <div class="upload">
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
            class="upload-demo"
            drag
            multiple
          >
            <div class="el-upload__text">支持xlsx格式</div>
            <div v-if="file !== null" class="el-upload__text">
              {{ file.name }}
            </div>
            <div v-else class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button
            type="primary"
            @click="downloadTemplate('../../static/template/downLoadTemplate/SlowMovingModelData.xlsx')">下载模板</el-button>
          <el-button :loading="uploadLoading" type="success" @click="importData">上传文件</el-button>
        </span>
      </el-dialog>
    </el-main>
  </el-container>
</template>

<script>
import { getDictDataByPid } from '@/api/common/dict'
import { uploadFile, listSlowData, updateSlowModelData, delSlowModelData } from '@/api/stock/slowMoving'
import ProductSearch from '../../product/index'
export default {
  name: 'SlowMoving',
  components: {
    ProductSearch
  },
  data() {
    return {
      uploadFileDialogVisible: false,
      updateDialog: false,
      listLoading: false,
      uploadLoading: false,
      productVisible: false,
      actionUrl: '',
      startTimeAndEndTime: '',
      file: null,
      total: 0,
      dateType: '1',
      form: {
        status: '1',
        modelNo: '',
        startTime: '',
        endTime: '',
        pageNum: 1,
        pageSize: 20
      },
      slowModelData: {
        id: '',
        modelNo: '',
        quantity: '',
        status: '',
        lastInDate: '',
        lastOutDate: '',
        remark: '',
        warehouseCode: '',
        productName: '',
        eprice: '',
        lotPrice: ''
      },
      dateTypeOptions: [
        { label: '更新时间', value: '1' },
        { label: '最近入库时间', value: '2' },
        { label: '最近出库时间', value: '3' }
      ],
      tableData: [],
      statusClassCode: '2039',
      productFlag: '2063',
      productFlagData: [],
      statusTypeOptions: []
    }
  },

  created() {
    this.iniData()
    this.findList()
  },
  methods: {
    findList() {
      this.setDateCondition()
      listSlowData(this.form).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
      }).catch(error => {
        console.log(error)
      })
    },
    iniData() {
      getDictDataByPid(this.statusClassCode).then(result => {
        this.statusTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.productFlag).then(result => {
        this.productFlagData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.dateType === '' || this.startTimeAndEndTime === '') {
        return
      } else {
        this.form.startTime = this.startTimeAndEndTime[0]
        this.form.endTime = this.startTimeAndEndTime[1]
      }
    },
    updateInfo() {
      if (this.$refs.multipleTable.selection.length === 0) {
        this.$message.warning('请选择要编辑的数据')
        return
      }
      this.slowModelData = JSON.parse(JSON.stringify(this.$refs.multipleTable.selection[0]))
      this.updateDialog = true
    },
    handleClick(row) {
      const item = { modelno: row.modelNo }
      this.productVisible = true
      this.$nextTick(() => {
        this.$refs.ProductSearch.handleSelect(item)
        this.$refs.ProductSearch.productSearchChange()
      })
    },
    saveSlowModelData() {
      updateSlowModelData(this.slowModelData).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.findList()
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    deletCondition() {
      var list = this.$refs.multipleTable.selection
      if (list.length <= 0) {
        this.$message.warning('请选择要删除的数据~')
        return null
      }
      this.$confirm('确认要删除这些数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        for (let i = 0; i < list.length; i++) {
          delSlowModelData(list[i].id).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.findList()
            } else {
              this.$message.error(res.message)
            }
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    importSlowMoving() {
      this.uploadFileDialogVisible = true
    },
    // 换页
    handlePageSizeChange(newSize) {
      this.form.pageSize = newSize
      this.findList()
    },
    handlePageChange(newCurrent) {
      this.form.pageNum = newCurrent
      this.findList()
    },
    descFormatter(data, value) {
      if (value === null) {
        return value
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === value.toString()) {
          return item.codeName
        }
      }
      return value
    },
    formatStatus(row, column, cellValue, index, menu) {
      return this.descFormatter(this.statusTypeOptions, cellValue)
    },
    formatProductFlag(row, column, cellValue, index, menu) {
      return this.descFormatter(this.productFlagData, cellValue)
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    importData() {
      const file = this.file
      if (!this.file) { // 防止空传
        this.$message.error('请选择文件上传')
        return false
      }
      const formData = new FormData() // form表单格式提交数据
      formData.append('file', file)
      this._importEmployeeAssistant(formData)
    },
    _importEmployeeAssistant(formData) {
      this.uploadLoading = true
      uploadFile(formData)
        .then(res => {
          if (res.success) {
            this.file = null
            this.$message.success(res.content)
            this.uploadFileDialogVisible = false
            this.findList()
          } else {
            // this.$message.error(res.message)
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error',
              duration: 0
            })
          }
          this.uploadLoading = false
        }).catch(error => {
          this.uploadLoading = false
          this.$message.error(error + '请稍后再常试')
        })
    },
    downloadTemplate(url) {
      const index = url.lastIndexOf('/') + 1
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.download = url.substring(index)
      link.click()
      this.$message({
        message: `正在下载~`,
        type: 'success'
      })
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    }
  }
}
</script>

<style scoped>
  .dialog {
    display: flex;
    justify-content: center;
    align-items: Center;
    overflow: hidden;
  }
  .dialog /deep/ .el-dialog {
        margin: 0 auto !important;
        height: 70%;
        overflow: hidden;
  }
  .dialog /deep/ .el-dialog .el-dialog__body {
            position: absolute;
            left: 0;
            top: 54px;
            bottom: 0;
            right: 0;
            padding: 0;
            z-index: 1;
            overflow: hidden;
            overflow-y: auto;
  }
</style>
