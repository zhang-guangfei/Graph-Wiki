<template>
  <div>
    <input ref="excel-upload-input" class="excel-upload-input" type="file" accept=".xlsx, .xls" @change="handleClick">
    <div class="drop" @drop="handleDrop" @dragover="handleDragover" @dragenter="handleDragover">
      拖拽Excel文件至此处或
      <el-button :loading="loading" style="margin-left:16px;" size="mini" type="primary" @click="handleUpload">选择Excel文件</el-button>
      <el-button style="margin-left:16px;" size="mini" type="primary" @click="downloadTemplate">下载模板</el-button>
    </div>
  </div>
</template>

<script>
import XLSX from 'xlsx'
import { downloadTemplate } from '@/utils/downLoadAndUpLoad'

export default {
  props: {
    beforeUpload: Function, // eslint-disable-line
    onSuccess: Function, // eslint-disable-line
    pageType: {
      type: String,
      default: function() {
        return {}
      }
    }
  },
  data() {
    return {
      loading: false,
      excelData: {
        header: null,
        results: null
      }
    }
  },
  methods: {
    generateData({ header, results }) {
      this.excelData.header = header
      this.excelData.results = results
      this.onSuccess && this.onSuccess(this.excelData)
    },
    downloadTemplate() {
      var params = {}
      var fileName = ''
      if (this.pageType === 'specialPriceApply') {
        params = { 'templateType': '特价明细模板' }
        fileName = '特价明细模板' + '.xlsx'
      } else if (this.pageType === 'quotationApply') {
        params = { 'templateType': '特价明细模板' }
        fileName = '报价明细模板' + '.xlsx'
      } else if (this.pageType === 'customersInStockApply') {
        params = { 'templateType': '在库补库明细模板' }
        fileName = '在库补库明细模板' + '.xlsx'
      } else if (this.pageType === 'orderEnquiry') {
        params = { 'templateType': 'InqA问询业务单号批量导入模板' }
        fileName = 'InqA问询业务单号批量导入模板' + '.xlsx'
      } else {
        params = { 'templateType': '特价明细模板' }
        fileName = '型号批量导入模板' + '.xlsx'
      }
      downloadTemplate(params, fileName)
    },
    handleDrop(e) {
      e.stopPropagation()
      e.preventDefault()
      if (this.loading) return
      const files = e.dataTransfer.files
      if (files.length !== 1) {
        this.$message.error('仅支持单个文件上传!')
        return
      }
      const rawFile = files[0] // only use files[0]

      if (!this.isExcel(rawFile)) {
        this.$message.error('仅支持 .xlsx, .xls, .csv 类型文件上传!')
        return false
      }
      this.upload(rawFile)
      e.stopPropagation()
      e.preventDefault()
    },
    handleDragover(e) {
      e.stopPropagation()
      e.preventDefault()
      e.dataTransfer.dropEffect = 'copy'
    },
    handleUpload() {
      this.$refs['excel-upload-input'].click()
    },
    handleClick(e) {
      const files = e.target.files
      const rawFile = files[0] // only use files[0]
      if (!rawFile) return
      this.upload(rawFile)
    },
    upload(rawFile) {
      this.$refs['excel-upload-input'].value = null // fix can't select the same excel

      if (!this.beforeUpload) {
        this.readerData(rawFile)
        return
      }
      const before = this.beforeUpload(rawFile)
      if (before) {
        this.readerData(rawFile)
      }
    },
    readerData(rawFile) {
      this.loading = true
      return new Promise((resolve, reject) => {
        const reader = new FileReader()
        reader.onload = e => {
          const data = e.target.result
          const fixedData = this.fixData(data)
          const workbook = XLSX.read(btoa(fixedData), { type: 'base64' })
          const firstSheetName = workbook.SheetNames[0]
          const worksheet = workbook.Sheets[firstSheetName]
          const header = this.getHeaderRow(worksheet)
          const results = XLSX.utils.sheet_to_json(worksheet)
          this.generateData({ header, results })
          this.loading = false
          resolve()
        }
        reader.readAsArrayBuffer(rawFile)
      })
    },
    fixData(data) {
      let o = ''
      let l = 0
      const w = 10240
      for (; l < data.byteLength / w; ++l) o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w, l * w + w)))
      o += String.fromCharCode.apply(null, new Uint8Array(data.slice(l * w)))
      return o
    },
    getHeaderRow(sheet) {
      const headers = []
      const range = XLSX.utils.decode_range(sheet['!ref'])
      let C
      const R = range.s.r
      /* start in the first row */
      for (C = range.s.c; C <= range.e.c; ++C) { /* walk every column in the range */
        const cell = sheet[XLSX.utils.encode_cell({ c: C, r: R })]
        /* find the cell in the first row */
        let hdr = 'UNKNOWN ' + C // <-- replace with your desired default
        if (cell && cell.t) hdr = XLSX.utils.format_cell(cell)
        headers.push(hdr)
      }
      return headers
    },
    isExcel(file) {
      return /\.(xlsx|xls|csv)$/.test(file.name)
    }
  }
}
</script>

<style scoped>
.excel-upload-input{
  display: none;
  z-index: -9999;
}
.drop{
  border: 2px dashed #bbb;
  width: 600px;
  height: 160px;
  line-height: 160px;
  margin: 0 auto;
  font-size: 24px;
  border-radius: 5px;
  text-align: center;
  color: #bbb;
  position: relative;
}
</style>
