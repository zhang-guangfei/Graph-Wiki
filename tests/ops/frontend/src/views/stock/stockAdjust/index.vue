<template>
  <div>
    <el-container>
      <el-main style="margin-top:10px">
        <div>
          <el-form ref="condition" :inline="true" :model="condition" size="mini">
            <el-form-item prop="groupNo">
              <el-input v-model="condition.invoiceNo" size="small" style="width: 150px" placeholder="请输入发票号" clearable/>
            </el-form-item>
            <el-form-item prop="customerNo">
              <el-input v-model="condition.fullOrderNo" size="small" style="width: 150px" placeholder="订单号" clearable/>
            </el-form-item>
            <el-form-item prop="modelNo">
              <el-input v-model="condition.modelNo" size="small" style="width: 150px" placeholder="型号" clearable/>
            </el-form-item>
            <el-form-item prop="optCode">
              <el-select v-model="condition.optCode" placeholder="状态" style="width: 100px" size="small" clearable>
                <el-option v-for="item in adjustStatusData" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
            </el-form-item>
            <el-form-item prop="orderType">
              <el-select v-model="condition.adjustType" placeholder="调整类型" style="width: 150px" size="small" clearable>
                <el-option v-for="item in adjustTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                v-model="dateTime"
                type="daterange"
                range-separator="至"
                start-placeholder="调整日期"
                style="width: 250px"
                end-placeholder="调整日期"/>
            </el-form-item>
            <el-form-item>
              <!-- <el-button type="success" plain icon="el-icon-refresh" size="small" @click="flash('condition')">刷新</el-button> -->
              <el-button v-permission="['640692']" type="primary" plain icon="el-icon-search" size="small" @click="getList(1)">查询</el-button>
              <el-button v-permission="['907732']" type="success" plain icon="el-icon-upload2" size="small" @click="uploadData()">导入 </el-button>
              <el-button v-permission="['907732']" :loading="exportLoading" type="primary" plain icon="el-icon-download" size="small" @click="exportData()">导出</el-button>
              <el-button v-permission="['907732']" type="primary" plain icon="el-icon-check" size="small" @click="adjust()">确定</el-button>
              <el-button v-permission="['907732']" type="warning" plain icon="el-icon-edit-outline" size="small" @click="update">编辑</el-button>
              <el-button v-permission="['907732']" type="danger" plain icon="el-icon-delete-solid" size="small" @click="deleteData">删除</el-button>
            </el-form-item>
          </el-form>
          <el-table
            ref="multipleTable"
            :data="tableData"
            tooltip-effect="dark"
            class="multipleTable"
            border
            height="350px"
            style="width: 100%"
            size="small"
            stripe>
            <el-table-column
              type="selection"
              width="55"/>
            <el-table-column
              prop="invoiceNo"
              label="发票号"/>

            <el-table-column
              prop="fullOrderNo"
              label="订单号"/>

            <el-table-column
              prop="modelNo"
              label="型号"/>

            <el-table-column
              prop="quantity"
              label="调整数量"/>

            <el-table-column
              prop="currency"
              label="货币"/>

            <el-table-column
              prop="price"
              label="单价"/>

            <el-table-column
              prop="exchangeRate"
              label="汇率"/>

            <el-table-column
              prop="amount"
              label="金额"/>

            <el-table-column
              :formatter="optCodeFormatter"
              prop="optCode"
              label="状态"/>

            <el-table-column
              :formatter="formatAdjustCode"
              prop="adjustType"
              label="调整类型"/>

            <el-table-column
              prop="reason"
              label="调整原因"/>

            <el-table-column
              prop="adjustDate"
              label="调整时间"/>

            <el-table-column
              prop="supplierCode"
              label="供应商"/>

            <el-table-column
              prop="warehouseCode"
              label="仓库代码"/>

            <el-table-column
              :formatter="formatInventoryCode"
              prop="inventoryTypeCode"
              label="库存类型"/>

            <el-table-column
              prop="customerNo"
              label="客户代码"/>

            <el-table-column
              prop="pplNo"
              label="PPL"/>

            <el-table-column
              prop="projectNo"
              label="项目号"/>

            <el-table-column
              prop="groupCustomerNo"
              label="集团号"/>
            <el-table-column
              prop="resultMsg"
              label="错误信息"/>
            <el-table-column
              prop="createTime"
              label="创建时间"
              show-overflow-tooltip="true"/>
          </el-table>
        </div>
        <el-pagination
          :page-sizes="[20, 50, 100, 200, 500]"
          :current-page="tablePage.pageNumber"
          :page-size="tablePage.pageSize"
          :total="tablePage.total"
          background
          style="margin-top: 10px"
          layout="total, sizes, prev, pager, next"
          @size-change="handlePageSizeChange"
          @current-change="handlePageChange"/>
        <el-divider class="divc" />
        <div style="width:100%;height:290px">
          <el-form ref="addForm" :model="addForm" :rules="rule" label-width="100px" size="mini" style="margin-top:40px;margin-left:5%;">
            <el-row type="flex">
              <el-form-item prop="invoiceNo" label="发票号">
                <el-input v-model="addForm.invoiceNo" type="text" maxlength="30" show-word-limit placeholder="发票号" style="width:100px"/>
                <el-button type="primary" size="mini" @click="getInvoiceNo()">获取发票号</el-button>
              </el-form-item>
              <el-form-item prop="adjustType" label="调整类型">
                <el-select v-model="addForm.adjustType" multiple placeholder="调整类型" size="mini" style="width: 160px">
                  <el-option v-for="item in adjustTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item prop="warehouseCode" label="仓库代码">
                <el-select v-model="addForm.warehouseCode" placeholder="仓库" size="mini" style="width: 160px" clearable>
                  <el-option v-for="item in warehouseData" :key="item.warehouseCode" :label="item.warehouseCode+item.warehouseName" :value="item.warehouseCode"/>
                </el-select>
                <!-- <el-input v-model="addForm.warehouseCode" placeholder="请输入仓库代码" style="width:100px"/> -->
                <!-- <el-button type="primary" size="mini" @click="selectWarehouse">选择</el-button> -->
              </el-form-item>
              <el-form-item prop="adjustDate" label="调整日期">
                <el-date-picker v-model="addForm.adjustDate" style="width:160px" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"/>
              </el-form-item>
              <el-form-item prop="reason" label="调整原因">
                <el-input :autosize="{ minRows: 1, maxRows: 2}" v-model="addForm.reason" type="textarea" style="width:160px"/>
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item prop="supplierCode" label="供应商">
                <el-select v-model="addForm.supplierCode" placeholder="供应商" size="mini" style="width: 160px" clearable>
                  <el-option v-for="item in supplierData" :key="item.id" :label="item.name" :value="item.id">
                    <span style="float: left">{{ item.name }}</span>
                    <span style="float: right; color: #8492a6; font-size: 13px">{{ item.id }}</span>
                  </el-option>
                </el-select>
              </el-form-item>
              <el-form-item prop="orderNo" label="订单号" style="margin-left:2%">
                <el-input v-model="addForm.orderNo" type="text" maxlength="16" show-word-limit placeholder="请输入订单号" style="width:160px" @blur="getOrderData"/>
              </el-form-item>
              <el-form-item prop="modelNo" label="型号">
                <el-input v-model="addForm.modelNo" placeholder="请输入型号" style="width:160px" />
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item prop="quantity" label="数量">
                <el-input v-model="addForm.quantity" placeholder="请输入数量" style="width:160px" @blur="getAmout"/>
              </el-form-item>
              <el-form-item prop="currency" label="货币" style="margin-left:2%">
                <el-select v-model="addForm.currency" placeholder="货币" size="mini" style="width: 160px" clearable>
                  <el-option v-for="item in currencyData" :key="item.code" :label="item.code+item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item prop="exchangeRate" label="汇率">
                <el-input v-model="addForm.exchangeRate" placeholder="汇率" style="width:160px"/>
              </el-form-item>
              <el-form-item prop="price" label="单价">
                <el-input v-model="addForm.price" placeholder="请输入单价" style="width:160px" @blur="getAmout"/>
              </el-form-item>
              <el-form-item prop="amount" label="金额">
                <el-input v-model="addForm.amount" disabled placeholder="请输入金额" style="width:160px"/>
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item prop="inventoryTypeCode" label="库存类型">
                <el-select v-model="addForm.inventoryTypeCode" placeholder="库存类型" size="mini" clearable style="width: 160px">
                  <el-option v-for="item in inventoryData" :key="item.code" :label="item.code+item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item prop="customerNo" label="客户代码" style="margin-left:2%">
                <el-autocomplete
                  v-model="addForm.customerNo"
                  :fetch-suggestions="querySearchAsync"
                  :debounce="0"
                  :popper-append-to-body="false"
                  popper-class="el-autocomplete-suggestion"
                  highlight-first-item
                  type="text"
                  size="mini"
                  style="width:160px"
                  placeholder="客户代码"
                  class="select"
                  @select="Changeselect">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.customerNo + ',' + item.name }}</div>
                  </template>
                </el-autocomplete>
                <!-- <el-input v-model="addForm.customerNo" style="width:180px"/> -->
              </el-form-item>
              <el-form-item prop="pplNo" label="PPL号">
                <el-input v-model="addForm.pplNo" placeholder="请输入PPL号" style="width:160px" />
              </el-form-item>
              <el-form-item prop="projectNo" label="项目号">
                <el-input v-model="addForm.projectNo" placeholder="请输入项目号" style="width:160px" autocomplete="off"/>
              </el-form-item>
              <el-form-item prop="groupCustomerNo" label="集团号">
                <el-input v-model="addForm.groupCustomerNo" placeholder="请输入集团号" style="width:160px"/>
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item>
                <el-button v-permission="['907732']" type="primary" size="mini" plain icon="el-icon-plus" @click="addData()">新增</el-button>
                <el-button v-permission="['907732']" :loading="loadadd" type="primary" size="mini" plain icon="el-icon-check" @click="addAdjustData()">保存</el-button>
              </el-form-item>
            </el-row>
          </el-form>
        </div>

        <!-- 仓库选择弹窗 -->
        <el-dialog :visible.sync="dialogFormVisible" title="仓库" width="650px">
          <el-form ref="warehouseForm" :inline="true" :model="warehouseForm" size="small">
            <el-form-item >
              <el-input v-model="warehouseForm.warehouseCode" placeholder="仓库代码" style="width:100px" clearable @clear="listWarehouseinfo"/>
            </el-form-item>
            <el-form-item >
              <el-input v-model="warehouseForm.warehouseName" placeholder="仓库名称" clearable @clear="listWarehouseinfo"/>
            </el-form-item>
            <el-form-item>
              <el-button icon="el-icon-search" size="small" @click="listWarehouseinfo"/>
            </el-form-item>
          </el-form>
          <el-table :data="warehouseData.filter(data => !warehouseForm.warehouseName || data.warehouseName.includes(warehouseForm.warehouseName))">
            <el-table-column property="warehouseCode" label="仓库代码" width="100px"/>
            <el-table-column property="warehouseName" label="仓库名称" width="300px"/>
            <el-table-column :formatter="getWarehouseType" property="warehouseType" label="仓库类别" width="100px"/>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="edit(scope.row)">选择</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>

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
              @click="downloadTemplate('../../static/template/downLoadTemplate/StockAdjust.xlsx')">下载模板</el-button>
            <el-button :loading="uploadLoading" type="success" @click="importData">上传文件</el-button>
          </span>
        </el-dialog>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import { getDictDataByPid, listWarehouse } from '@/api/common/dict'
import { saveAdjustdata, listAdjustdata, exportAdjustData, deleteAdjustData, createInvoiceNo, getOrderInfoForImpAdjuest, importStockAdjustData, determineStockAdjust } from '@/api/order/stockAdjust'
import { getCustomerByNo } from '@/api/common/customer'
import { findSupplierByIdOrName } from '@/api/common/supplier'

export default {
  name: 'StockAdjust',
  components: {
  },
  data() {
    return {
      tableData: [],
      adjustTypeOptions: [],
      warehouseData: [],
      supplierData: [],
      currencyData: [],
      tablePage: {
        pageSize: 20,
        pageNumber: 1,
        total: 20
      },
      condition: {
        invoiceNo: '',
        fullOrderNo: '',
        modelNo: '',
        adjustType: '',
        startTime: '',
        endTime: '',
        optCode: '1'
      },
      warehouseForm: {
        warehouseCode: '',
        warehouseName: '',
        warehouseType: ''
      },
      addForm: {
        id: '',
        invoiceNo: '',
        orderNo: '',
        adjustType: [],
        modelNo: '',
        quantity: '',
        price: '',
        adjustDate: '',
        amount: '',
        warehouseCode: '',
        reason: '',
        customerNo: '',
        pplNo: '',
        projectNo: '',
        groupCustomerNo: '',
        inventoryTypeCode: '',
        currency: '',
        supplierCode: '',
        exchangeRate: ''
      },
      actionUrl: '',
      file: null,
      rule: {
        invoiceNo: [
          { required: true, message: '请输入发票号', trigger: 'blur' }
        ],
        orderNo: [
          { required: true, message: '请输入订单号', trigger: 'blur' }
        ],
        adjustType: [
          { required: true, message: '请选择调整类型', trigger: 'change' }
        ],
        warehouseCode: [
          { required: true, message: '请选择仓库', trigger: 'change' }
        ],
        adjustDate: [
          { required: true, message: '请选择调整日期', trigger: 'change' }
        ],
        reason: [
          { required: true, message: '请输入调整原因', trigger: 'blur' }
        ],
        modelNo: [
          { required: true, message: '请输入型号', trigger: 'blur' }
        ],
        quantity: [
          { required: true, message: '请输入数量', trigger: 'blur' }
        ]
      },
      loadadd: false,
      exportLoading: false,
      uploadFileDialogVisible: false,
      uploadLoading: false,
      dialogFormVisible: false,
      inventoryData: [],
      adjustStatusData: [],
      warehousetypeList: [],
      dateTime: '',
      warehouseType: '4001',
      adjustTypeCode: '4007',
      adjustStatusCode: '4008',
      inventoryClassCode: '2001',
      currencyClassCode: '4303'
    }
  },
  created() {
    this.initData()
    this.getList()
    this.listWarehouseinfo()
    this.listSupplierinfo()
  },
  methods: {
    initData() {
      getDictDataByPid(this.adjustTypeCode).then(result => {
        this.adjustTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.inventoryClassCode).then(result => {
        this.inventoryData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.adjustStatusCode).then(result => {
        this.adjustStatusData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.warehouseType).then(result => {
        console.log(result)
        this.warehousetypeList = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.currencyClassCode).then(result => {
        console.log(result)
        this.currencyData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    addData() {
      this.$refs.addForm.resetFields()
      this.getList()
    },
    setDateCondition() {
      if (this.dateTime === null || this.dateTime === '') {
        this.condition.startTime = ''
        this.condition.endTime = ''
      } else {
        this.condition.startTime = this.dateTime[0]
        this.condition.endTime = this.dateTime[1]
      }
    },
    getList(val) {
      if (val === 1) {
        this.tablePage.pageNumber = 1
      }
      this.setDateCondition()
      const form = {
        invoiceNo: this.condition.invoiceNo,
        fullOrderNo: this.condition.fullOrderNo,
        adjustType: this.condition.adjustType,
        modelNo: this.condition.modelNo,
        optCode: this.condition.optCode,
        startTime: this.condition.startTime,
        endTime: this.condition.endTime,
        pageNum: this.tablePage.pageNumber,
        pageSize: this.tablePage.pageSize
      }
      listAdjustdata(form).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.tablePage.total = res.content.total
        }
      })
    },
    listSupplierinfo(val) {
      const parm = { 'companyId': '', 'name': '' }
      findSupplierByIdOrName(parm).then(res => {
        this.supplierData = res.content
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    addAdjustData() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          // <!--add by WuWeiDong 20230907  bug 12009   入库调整发票号+订单号长度限制 -->
          // <!--add by WuWeiDong 20230925  bug 12166   入库调整发票号+订单号长度限制 -->
          if (this.addForm.invoiceNo.length > 30 || this.addForm.orderNo.length > 16) {
            this.$message.error('发票号不超过30或订单号长度不能超过16！')
            return false
          }
          this.loadadd = true
          saveAdjustdata(this.addForm).then(res => {
            if (res.success) {
              this.$message.success(res.content)
              this.getList()
            } else {
              this.$message.error(res.message)
            }
            this.loadadd = false
          }).catch(error => {
            this.loadadd = false
            console.log(error)
          })
        } else {
          this.$message.error('请输入必填选项！')
          return false
        }
      })
    },
    selectWarehouse() {
      this.dialogFormVisible = true
    },
    edit(row) {
      this.addForm.warehouseCode = row.warehouseCode
      this.dialogFormVisible = false
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: this.warehouseForm.warehouseType,
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        for (let i = 0; i < res.content.length; i++) {
          if (res.content[i].warehouseType !== 'WT') {
            this.warehouseData.push(res.content[i])
          }
        }
        // this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    adjust() {
      if (this.$refs.multipleTable.selection.length === 0) {
        this.$message.warning('请选择')
        return
      }
      if (this.$refs.multipleTable.selection.length === 1) {
        var invoiceNo = this.$refs.multipleTable.selection[0].invoiceNo
        this.adjustOne(invoiceNo)
      } else {
        this.$confirm('确定要处理多个发票吗？', '提示 ：处理最小维度是发票', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const invoiceNos = this.$refs.multipleTable.selection.map(item => item.invoiceNo)
          const uniqueInvoiceNos = [...new Set(invoiceNos)]
          console.log(uniqueInvoiceNos)
          for (const invoiceNo of uniqueInvoiceNos) {
            this.adjustOne(invoiceNo) // 在这里处理每个 invoiceNo
          }
        })
      }
    },
    adjustOne(invoiceNo) {
      determineStockAdjust(invoiceNo).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.getList()
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    update() {
      if (this.$refs.multipleTable.selection.length === 0) {
        return
      }
      if (this.$refs.multipleTable.selection[0].optCode === 2) {
        this.$message.error('已确认,不可编辑')
        return
      }
      const data = this.$refs.multipleTable.selection[0]
      this.addForm = JSON.parse(JSON.stringify(data))
      this.addForm.orderNo = data.fullOrderNo
      var val = this.$refs.multipleTable.selection[0].adjustType
      if (val > 100) {
        this.addForm.adjustType = ['1', '2', '3']
      }
      if (val > 10 && val < 100) {
        var item = val / 10
        var b = (item + '').split('.')[0]
        var c = (item + '').split('.')[1]
        this.addForm.adjustType = [b, c]
      }
      if (val < 10) {
        this.addForm.adjustType = [val + '']
      }
    },
    uploadData() {
      this.uploadFileDialogVisible = true
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
      importStockAdjustData(formData)
        .then(res => {
          if (res.success) {
            this.file = null
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: res.content,
              type: 'success'
            })
            // this.$message.success(res.content)
            this.uploadFileDialogVisible = false
            this.getList()
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
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
    },
    getAmout() {
      if (this.addForm.quantity === '' || this.addForm.price === '') {
        return
      }

      let m = 0
      const s1 = this.addForm.quantity.toString()
      const s2 = this.addForm.price.toString()
      // 获取所有参数小数位长度之和
      try { m += s1.split('.')[1].length } catch (e) { console.log(e) }
      try { m += s2.split('.')[1].length } catch (e) { console.log(e) }
      // 替换掉小数点转为数字相乘再除以10的次幂值
      const amount = Number(s1.replace('.', '')) * Number(s2.replace('.', '')) / Math.pow(10, m)
      this.addForm.amount = amount.toFixed(2)
    },
    deleteData() {
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
          deleteAdjustData(list[i].id).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.getList()
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
    getInvoiceNo() {
      createInvoiceNo().then(res => {
        if (res.success) {
          this.addForm.invoiceNo = res.content
        } else {
          this.$message.error('生成失败')
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getOrderData() {
      if (this.addForm.orderNo === '') {
        return
      }
      getOrderInfoForImpAdjuest(this.addForm.orderNo).then(res => {
        if (res.success) {
          this.addForm.modelNo = res.content.modelNo
          this.addForm.price = res.content.price
          this.addForm.warehouseCode = res.content.warehouseCode
          this.addForm.quantity = res.content.quantity
          this.getAmout()
        } else {
          this.$message.warning(res.message)
        }
      })
    },
    exportData() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportAdjustData(this.condition).then(res => {
        const fileName = '入库调整信息.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportLoading = false
      }).catch(error => {
        console.error(error)
        this.exportLoading = false
      })
    },
    // 换页
    handlePageChange(newCurrent) {
      this.tablePage.pageNumber = newCurrent
      this.getList()
    },
    // 改变每页条数
    handlePageSizeChange(newSize) {
      this.tablePage.pageSize = newSize
      this.getList()
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
    optCodeFormatter(row, column, cellValue, index, menu) {
      for (const i in this.adjustStatusData) {
        var item = this.adjustStatusData[i]
        if (item.code === cellValue.toString()) {
          return item.codeName
        }
      }
    },
    formatAdjustCode(row, column, cellValue, index, menu) {
      if (cellValue === null) {
        return
      }
      var a = parseInt(cellValue)
      if (a > 100) {
        var value = ''
        for (let i = 2; i <= 3; i++) {
          if (value === '') {
            value = this.descFormatter(this.adjustTypeOptions, 1)
          }
          value = value + ',' + this.descFormatter(this.adjustTypeOptions, i)
        }
        return value
      }
      if (a > 10 && a < 100) {
        var item = a / 10
        var b = (item + '').split('.')[0]
        var c = (item + '').split('.')[1]
        return this.descFormatter(this.adjustTypeOptions, b) + ',' + this.descFormatter(this.adjustTypeOptions, c)
      }
      if (a < 10) {
        return this.descFormatter(this.adjustTypeOptions, cellValue)
      }
    },
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    formatInventoryCode(row, column, cellValue, index, menu) {
      return this.descFormatter(this.inventoryData, cellValue)
    },
    querySearchAsync(customerNo, cb) {
      // const cus = { customerNo: this.form.customerNo }
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray
        cb(results)
      })
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.customerNo.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    Changeselect(item) {
      this.addForm.customerNo = item.customerNo
    }
  }
}
</script>

<style scoped>
  .divc{
    height: 5px;
    /* background-color: #dcdfe6; */
    margin-top: 30px;
  }
  .select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
  }
</style>
