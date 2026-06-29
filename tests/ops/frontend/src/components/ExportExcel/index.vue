<template>
  <div class="export-excel-sr">
    <el-drawer
      :visible.sync="drawerVisible"
      :direction="drawer.direction"
      :before-close="drawerClose">
      <el-divider content-position="center">
        <el-tag>{{ drawer.title }} <span>  (数据量：{{ createExcelVisable ? 0 : exportData.length }})</span></el-tag>
      </el-divider>
      <el-scrollbar ref="scroll_el_record" style="height: 800px">
        <div style="padding: 20px; padding-top: 0px">
          <el-form>
            <el-form-item>
              <el-button :disabled="createExcelVisable" :icon=" createExcelVisable ? 'el-icon-loading' : 'el-icon-download'" :type="createExcelVisable ? 'danger' : 'primary'" plain size="small" @click="handleDownload">
                {{ createExcelVisable ? '正在加载数据，请耐心等待...' : ((drawer.bookType === 'xlsx') ? '导出Excel' : '导出' + drawer.bookType ) }}
              </el-button>
            </el-form-item>
          </el-form>
          <el-divider content-position="center">
            <el-tag size="small">导出文件属性选择</el-tag>
          </el-divider>
          <el-form :inline="true">
            <el-form-item>
              <label>文件名：</label>
              <el-input v-model="drawer.filename" placeholder="请输入文件名默认（excel-list）" style="width:240px;" size="small" prefix-icon="el-icon-document"/>
            </el-form-item>
            <el-form-item>
              <label>是否列宽自适应内容：</label>
              <el-radio-group v-model="drawer.autoWidth" size="small">
                <el-radio-button :label="true">是</el-radio-button>
                <el-radio-button :label="false">否</el-radio-button>
              </el-radio-group>
            </el-form-item>
            <el-form-item>
              <label>导出文件类型：</label>
              <el-select v-model="drawer.bookType" style="width:120px;" size="small">
                <el-option
                  v-for="item in drawer.bookTypeOptions"
                  :key="item"
                  :label="item"
                  :value="item"/>
              </el-select>
            </el-form-item>
          </el-form>
          <el-divider content-position="center">
            <el-tag size="small">导出列选择</el-tag>
          </el-divider>
          <el-form>
            <el-form-item>
              <label>需要导出列：</label>
              <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
              <div style="margin: 15px 0;"/>
              <el-checkbox-group v-model="checkedExcelData" :min="1" @change="handlecheckedExcelDataChange">
                <el-checkbox v-for="(item, index) in fieldList" :label="item" :key="index" style="width: 150px; margin-right: 10px">{{ item.title ? item.title : (item.field ? item.field : 'column_' + index) }}</el-checkbox>
              </el-checkbox-group>
            </el-form-item>
          </el-form>
        </div>
      </el-scrollbar>
    </el-drawer>
  </div>
</template>

<script>
import { parseTime } from '@/utils/index'
export default {
  name: 'ExportExcel',
  data() {
    return {
      maxDataNum: 20000,
      drawerVisible: false,
      exportData: [],
      fieldList: [],
      drawer: {
        direction: 'rtl',
        title: '数据文件导出',
        filename: '数据导出-Excel文件',
        autoWidth: true,
        bookType: 'xlsx',
        bookTypeOptions: ['xlsx', 'csv', 'txt']
      },
      createExcelVisable: false,
      isIndeterminate: true,
      checkAll: false,
      checkedExcelData: [],
      tHeaderList: [],
      filterValueList: [],
      downloadLoading: false
    }
  },
  methods: {
    // 初始化函数
    initExportExcel(exportData, fieldData) {
      if (!(Array.isArray(exportData) && exportData.length > 0)) {
        this.$notify({
          title: '警告',
          message: '请传入要导出的数据',
          type: 'warning'
        })
        return
      }
      this.exportData = exportData
      if (fieldData && Array.isArray(fieldData)) {
        this.fieldList = fieldData
      } else {
        this.fieldList = Object.keys(exportData[0]).map(item => {
          return { field: item }
        })
      }
      this.checkedExcelData = this.fieldList
      this.createExcelVisable = false
      this.checkAll = true
      this.drawerVisible = true
    },
    // 抽屉
    drawerClose(done) {
      done()
      this.$emit('closeDrawer', false)
    },
    // 抽屉内选择 导出列得方法
    handleCheckAllChange(val) {
      this.checkedExcelData = val ? this.fieldList : [this.fieldList[0]]
      this.isIndeterminate = false
    },
    handlecheckedExcelDataChange(value) {
      var checkedCount = value.length
      this.checkAll = (checkedCount === this.fieldList.length)
      this.isIndeterminate = (checkedCount > 0 && checkedCount < this.fieldList.length)
      this.checkedExcelData = value
    },
    handleExcelData(val) {
      if (Array.isArray(val)) {
        this.filterValueList = []
        this.tHeaderList = []
        val.forEach(element => {
          this.fieldList.forEach((fieldItem, index) => {
            if (element.field === fieldItem.field) {
              this.filterValueList.push(fieldItem.field)
              this.tHeaderList.push(fieldItem.title ? fieldItem.title : (fieldItem.field ? fieldItem.field : 'column_' + index))
            }
          })
        })
      }
    },
    // Excel、CSV、Text导出
    handleDownload() {
      if (this.drawer.bookType === 'xlsx' && this.exportData.length > this.maxDataNum) {
        this.$notify({
          title: '警告',
          message: '所要导出数据超过Excel最大条数【' + this.maxDataNum + '】条，可使用csv或txt文件类型再试。',
          type: 'warning'
        })
        return
      }
      this.handleExcelData(Object.assign(this.checkedExcelData))
      this.downloadLoading = true
      const tHeader = Object.assign([], this.tHeaderList) // e.g. ['订单号']
      const filterVal = Object.assign([], this.filterValueList) // e.g. ['orderNo']  json { orderNo: 'XXXX' }
      import('./vendor/Export2Excel').then(excel => {
        const list = this.exportData
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.drawer.filename,
          autoWidth: this.drawer.autoWidth,
          bookType: this.drawer.bookType
        })
        this.downloadLoading = false
        this.drawer.drawer = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
        if (j === 'timestamp') {
          return parseTime(v[j])
        } else {
          return v[j]
        }
      }))
    }
  }
}
</script>
<style lang="scss">
.export-excel-sr {
  .el-form {
    .label {
      width: 200px
    }
  }
  .el-drawer__header {
    margin-bottom: 0px
  }
}
.export-excel-sr .el-scrollbar .el-scrollbar__wrap{
  overflow-x: hidden;
}
.el-drawer__body{
  padding-top: 20px;
}
.el-form-item{
  padding-top: 12px;
}
</style>
