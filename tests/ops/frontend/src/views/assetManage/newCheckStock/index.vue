<template>
  <div class="container">
    <el-tabs v-model="activeName" @tab-click="handleClickTab">
      <el-tab-pane label="到货未入数据准备" name="first">
        <el-form :inline="true" :model="searchForm" class="demo-form-inline" size="mini">
          <el-form-item>
            <el-select v-model="searchForm.warehouseType" clearable placeholder="仓库类别" @change="selTypeChange">
              <el-option
                v-for="item in warehouseTypeList"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select
              v-model="searchForm.warehouseCodes"
              :remote-method="remoteMethodByWareHouseCode"
              :loading="selWareHouseCodeLoading"
              filterable
              multiple
              collapse-tags
              remote
              clearable
              placeholder="仓库代码"
              style="width: 220px">
              <el-option
                v-for="item in wareHouseCodeList"
                :key="item.warehouseCode"
                :label="'【'+item.warehouseCode+'】'+item.warehouseName"
                :value="item.warehouseCode"
              />
            </el-select>
          </el-form-item>
          <!-- <el-form-item>
            <el-input v-model="searchForm.invoiceNo" clearable placeholder="请输入发票号" />
          </el-form-item> -->
          <el-form-item>
            <el-select
              v-model="searchForm.invoiceNo"
              :remote-method="queryWmsInvoiceASync"
              :loading="selWmsInvoice"
              filterable
              remote
              clearable
              placeholder="请输入发票号"
              style="width: 220px">
              <el-option
                v-for="item in wmsInvoiceList"
                :key="item.wmsSysInvoiceNo"
                :label="item.wmsSysInvoiceNo"
                :value="item.wmsSysInvoiceNo"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="searchForm.logisticsConfirm" clearable placeholder="物流确认结果" size="mini">
              <el-option
                v-for="item in confirmList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select
              v-model="searchForm.pdBatchNo"
              :remote-method="remoteMethod"
              :loading="selLoading"
              filterable
              remote
              clearable
              placeholder="盘点批次号">
              <el-option
                v-for="item in pdBatchNoList"
                :key="item.pdBatchNo"
                :label="item.lable"
                :value="item.pdBatchNo"
              />
            </el-select>
          </el-form-item>
          <el-form-item style="margin-left: 70px">
            <el-button type="primary" @click="findList()">查询</el-button>
          </el-form-item>
        </el-form>
        <el-button-group>
          <el-button :loading="expotToArriveNotInByWmsDataBtu" type="primary" size="mini" @click="expotToArriveNotInByWmsData()">到货未入数据准备</el-button>
          <el-button type="primary" size="mini" @click="exportDataBtu()">查询数据导出</el-button>
          <el-button type="primary" size="mini" @click="impData()">导入物流确认到货未入数据</el-button>
          <el-button type="primary" size="mini" @click="sureData()">确认数据</el-button>
        </el-button-group>
        <el-tabs v-model="activeTableName" @tab-click="handleTableClick">
          <el-tab-pane label="发票数据" name="tableFirst">
            <div>
              <vxe-table
                :data="invoiceTableData"
                auto-resize
                border
                size="mini"
                @checkbox-change="handleInvoiceSelectionChange"
              >
                <vxe-table-column type="checkbox" width="50"/>
                <vxe-table-column field="pdBatchNo" title="盘点批次号"/>
                <vxe-table-column field="wmsSysWarehouseCode" title="wms仓库名称" show-overflow/>
                <vxe-table-column field="wmsSysInvoiceNo" title="wms提供发票号" show-overflow/>
                <vxe-table-column field="wmsSysIsAll" title="wms是否已入完"/>
                <vxe-table-column field="lwarehouseCode" title="物流仓库名称" show-overflow/>
                <vxe-table-column field="linvoiceNo" title="物流发票号" show-overflow/>
                <vxe-table-column field="lisAll" title="物流是否已入完"/>
                <vxe-table-column field="logisticsConfirm" title="物流确认结果"/>
              </vxe-table>
            </div>
            <pagination
              v-show="total > 10"
              :total="total"
              :page-sizes="[10, 50, 100, 150, 200]"
              :page.sync="searchForm.page.pageNumber"
              :limit.sync="searchForm.page.pageSize"
              @pagination="searchData()"/>
          </el-tab-pane>
          <el-tab-pane label="明细数据" name="tableSecond">
            <div>
              <vxe-table
                :data="detailTableData"
                auto-resize
                border
                size="mini"
                @checkbox-change="handleDetailDataSelectionChange"
              >
                <vxe-table-column type="checkbox" width="50"/>
                <vxe-table-column field="pdBatchNo" title="盘点批次号"/>
                <vxe-table-column field="wmsSysWarehouseCode" title="wms仓库名称" show-overflow/>
                <vxe-table-column field="wmsSysInvoiceNo" title="wms发票号" show-overflow/>
                <vxe-table-column field="lwarehouseCode" title="物流仓库名称" show-overflow/>
                <vxe-table-column field="linvoiceNo" title="物流发票号" show-overflow/>
                <vxe-table-column field="caseNo" title="拍号"/>
                <vxe-table-column field="orderNo" title="订单号"/>
                <vxe-table-column field="modelNo" title="型号" show-overflow/>
                <vxe-table-column field="billQty" title="数量"/>
                <vxe-table-column field="barcode" title="Barcode"/>
                <vxe-table-column field="logisticsConfirm" title="物流确认结果"/>
                <vxe-table-column field="createTime2" title="数据抽取时间" show-overflow/>
              </vxe-table>
            </div>
            <pagination
              v-show="total > 10"
              :total="total"
              :page-sizes="[10, 50, 100, 150, 200]"
              :page.sync="searchForm.page.pageNumber"
              :limit.sync="searchForm.page.pageSize"
              @pagination="searchData()"/>
          </el-tab-pane>
        </el-tabs>
      </el-tab-pane>
      <el-tab-pane label="票据生成及打印" name="second">
        <el-form :inline="true" :model="createBillForm" size="mini" class="demo-form-inline">
          <el-form-item label="盘点批次" class="fontSty">
            {{ createBillForm.batchNo }}
          </el-form-item>
          <el-form-item label="盘点数据">
            <el-select v-model="createBillForm.pdDataType" clearable placeholder="请选择" @change="pdDataTypeChange()">
              <el-option
                v-for="item in pdDataTypeList"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="盘点票形式">
            <el-select v-model="createBillForm.pdType" clearable placeholder="请选择">
              <el-option
                v-for="item in pdTypeList"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button :loading="surePdTypeLoad" type="primary" @click="sure()">确定</el-button>
            <el-button type="primary" @click="creBill()">生成盘点票</el-button>
            <el-button type="primary" size="mini" @click="reflush()">查询</el-button>
            <el-button :loading="loadExportBillCount" type="primary" size="mini" @click="exportBillCount()">导出</el-button>
            <el-button :loading="loadShelvesBtu" type="primary" size="mini" @click="exportShelvesData()">导出货架号</el-button>
          </el-form-item>
          <vxe-button v-permission="['628964']" status="success" size="small" transfer>
            <template #default>打印盘点票据</template>
            <template #dropdowns>
              <vxe-button type="text" content="打印现品票" @click="printCurProBill()" />
              <vxe-button type="text" content="打印货架盘点空白票" @click="printBlankBill()"/>
              <vxe-button type="text" content="打印到货未入空白票" @click="printArriveNotInBlankBill()"/>
              <vxe-button type="text" content="打印立会票" @click="printStandBill()"/>
              <vxe-button type="text" content="打印数据票" @click="printDataBill()"/>
              <vxe-button type="text" content="打印到货未入清单票" @click="printArriNoEnterBill()"/>
              <!-- <vxe-button type="text" content="打印寄售库存盘点票" @click="printWtDataBill()"/> -->
            </template>
          </vxe-button>
        </el-form>
        <div>
          <p>
            <vxe-input v-model="filterName" type="search" clearable placeholder="库房搜索" size="mini" />
          </p>
          <vxe-table
            :loading="loadingTab"
            :data="createBillTableData.filter(data => !filterName || data.warehouseCode.toLowerCase().includes(filterName.toLowerCase()))"
            auto-resize
            border
            size="mini"
            height="500"
            @checkbox-change="handleCreateBillSelectionChange"
          >
            <vxe-table-column type="checkbox" width="50"/>
            <vxe-table-column field="warehouseCodeName" title="库房名称" show-overflow/>
            <vxe-table-column field="modelTypeCount" title="预计盘点型号种类数"/>
            <vxe-table-column field="modelNoQtyCount" title="预计盘点型号总数量" />
            <vxe-table-column field="xpModelNoCount" title="预计实盘型号总数量"/>
            <vxe-table-column field="gdModelNoCount" title="正式过渡库位型号总数量" width="160"/>
            <vxe-table-column field="jyModelNoCount" title="集约待交接区型号总数量" width="160"/>
            <vxe-table-column field="creXPBillCount" title="预计生成现品票"/>
            <vxe-table-column field="creLhBillCount" title="预计生成立会票"/>
            <vxe-table-column field="creDataBillCount" title="预计生成数据票"/>
            <vxe-table-column field="creArrveNotInCount" title="预计生成到货未入清单票" width="160"/>
          </vxe-table>
        </div>
        <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false" label-position="right" style="width: 1000px;margin-left: 200px" title="设置空白票张数">
          <el-form ref="creBlankForm" :model="creBlankForm" size="mini" style="margin-left: 100px">
            <el-form-item label="物流中心仓">
              <el-input v-model.number="creBlankForm.masterWarehouse" autocomplete="off" class="inputCreBlank" />  张
            </el-form-item>
            <el-form-item label="分库">
              <el-input v-model.number="creBlankForm.subWarehouse" autocomplete="off" style="margin-left: 40px" class="inputCreBlank" />  张
            </el-form-item>
            <el-form-item label="寄售备库">
              <el-input v-model.number="creBlankForm.wtWarehouse" autocomplete="off" style="margin-left: 12px" class="inputCreBlank" />  张
            </el-form-item>
            <el-form-item label="到货未入">
              <el-input v-model.number="creBlankForm.arriveNotINWarehouse" :disabled="isCanInput" autocomplete="off" style="margin-left: 12px" class="inputCreBlank" />  张
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button size="mini" @click="dialogFormVisible = false">取 消</el-button>
            <el-button :loading="creBlankBillBtu" size="mini" type="primary" @click="creBlankBill('creBlankForm')">确 定</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>
      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadFileDialogVisible"
        :before-close="closeClick"
        title="到货未入数据导入"
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
        <div slot="footer" class="dialog-footer">
          <el-button :loading="downLoadExcelBtuLoad" size="small" type="success" class="downLoadExcel" @click="downLoadExcel" >下载模板</el-button>
          <el-button size="mini" @click="uploadFileDialogVisible = false">取 消</el-button>
          <el-button :loading="uploadLoading" size="mini" type="primary" @click="importData()">导 入</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="exportDialog" :close-on-click-modal="false" label-position="right" style="width: 1000px;margin-left: 200px" title="请选择导出类型">
        <el-form :model="searchForm" size="mini" style="margin-left: 10px">
          <el-form-item label="导出类型">
            <el-select v-model="searchForm.expType" clearable placeholder="导出类型" size="mini">
              <el-option
                v-for="item in expTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button size="mini" @click="exportDialog = false">取 消</el-button>
          <el-button :loading="expArriveNotInBtuLoad" size="mini" type="primary" @click="exportData()">导 出</el-button>
        </div>
      </el-dialog>
      <el-dialog
        :visible.sync="dialogVisible"
        :close-on-click-modal="false"
        title="提示"
        width="30%"
        height="200px">
        <span><h2>{{ msg }}</h2></span>
        <!-- <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
          <el-button size="mini" type="primary" @click="dialogVisible = false">确 定</el-button>
        </span> -->
      </el-dialog>
    </el-tabs>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import { getDictDataByPid, findWareHouseInfoWithLike } from '@/api/common/dict'
import { exportArriveNotIn, getExportMsgPrompt, downImpExel, batchImpArriverNotIn, expotToArriveNotInByWmsData, listArriveNotInWithGroup,
  listArriveNotInDetail, arriveNotInInsertToBillData, getBatchNoWithIsActive, surePdType, createPdBill, printPdXpBillDataWithPdf, printPdXpBlankBillDataWithPdf,
  findModelNoCount, printLhBillDataWithPdf, printPdDataBillDataWithPdf, printPdArriveNotInWithPdf, printWtBillDataWithPdf,
  getPdStatusFromRedis, remoteInvoiceWithArriveNotIn, printPdArriveNotInBlankBillDataWithPdf, isCreDhwrBlankBill, getPdSureDateType, findBatchNo, exportShelvesData, exportBillCountData } from '@/api/pd/pd'
export default {
  name: 'NewCheckStockAdd',
  components: { Pagination },
  data() {
    // var validateQty = (rule, value, callback) => {
    //   if (value === '') {
    //     callback(new Error('请输入数量(正整数)'))
    //   } else {
    //     if (isNaN(Number(value)) || value <= 0) {
    //       callback(new Error('请输入正确数值(正整数大于0)'))
    //     }
    //     callback()
    //   }
    // }
    return {
      searchForm: {
        warehouseType: 'MASTER',
        invoiceNo: '',
        pdBatchNo: '',
        warehouseCodes: [],
        logisticsConfirm: '',
        expType: '',
        page: {
          pageNumber: 1,
          pageSize: 10
        }
      },
      createBillForm: {
        batchNo: '',
        pdType: '2',
        pdDataType: '1'

      },
      loadShelvesBtu: false,
      total: 0,
      page: {
        pageNumber: 1,
        pageSize: 20
      },
      isCanInput: true,
      wmsInvoiceList: [],
      pdBatchNoList: [],
      filterName: '',
      activeName: 'first',
      activeTableName: 'tableFirst',
      warehouseTypeClassCode: '4001',
      pdTypeCode: '5003',
      surePdTypeLoad: false,
      wareHouseCodeList: [],
      warehouseTypeList: [],
      stockPdTypeList: [],
      pdTypeList: [],
      // table数据集
      invoiceTableData: [],
      detailTableData: [],
      createBillTableData: [],
      loadingTab: true,
      pdDataTypeList: [
        {
          code: '1',
          codeName: '到货未入'
        },
        {
          code: '2',
          codeName: '正式过渡库位库存'
        },
        {
          code: '3',
          codeName: '集约待交接区'
        }
      ],
      dialogFormVisible: false,
      exportShelvesDataBtu: false,
      selWareHouseCodeLoading: false,
      expArriveNotInBtuLoad: false,
      selLoading: false,
      uploadFileDialogVisible: false,
      downLoadExcelBtuLoad: false,
      uploadLoading: false,
      dialogVisible: false,
      selWmsInvoice: false,
      msg: '',
      exportDialog: false,
      expotToArriveNotInByWmsDataBtu: false,
      creBlankBillBtu: false,
      loadExportBillCount: false,
      actionUrl: '',
      file: null,
      confirmList: [
        {
          value: '1',
          label: '是'
        },
        {
          value: '0',
          label: '否'
        }
      ],
      expTypeList: [
        {
          value: '0',
          label: '发票数据'
        },
        {
          value: '1',
          label: '明细数据'
        }
      ],
      creBlankForm: {
        masterWarehouse: '',
        subWarehouse: '',
        wtWarehouse: '',
        arriveNotINWarehouse: ''
      }
      // creBlankFormRules: {
      //   masterWarehouse: [{ required: true, validator: validateQty, trigger: 'blur' }],
      //   subWarehouse: [{ required: true, validator: validateQty, trigger: 'blur' }],
      //   wtWarehouse: [{ required: true, validator: validateQty, trigger: 'blur' }]
      //   // arriveNotINWarehouse: [{ required: true, validator: validateQty, trigger: 'blur' }]
      // }
    }
  },
  mounted() {
    this.getDataCodesTreeBywarehouseType()
    this.remoteMethodByWareHouseCode()
    this.searchData()
    this.getBatchNoWithIsActive()
    this.getPdType()
    this.findModelNoCount()
    this.queryWmsInvoiceASync('')
    this.getSureDataType()
    this.remoteMethod('')
  },
  methods: {
    exportShelvesData() {
      this.loadShelvesBtu = true
      this.$notify({
        title: '成功',
        message: '数据正在导出,导出完毕之后会以邮件形式发送给您,请留意接收',
        type: 'success'
      })
      exportShelvesData().then(res => {
        this.loadShelvesBtu = false
      })
    },
    remoteMethod(batchNo) {
      this.selLoading = true
      findBatchNo(batchNo).then(res => {
        console.log(res)
        if (res.success) {
          this.pdBatchNoList = res.content
        } else {
          this.pdBatchNoList = []
        }
        this.selLoading = false
      })
    },
    getSureDataType() {
      getPdSureDateType().then(res => {
        if (res.success) {
          var aa = res.content.split(';')
          this.createBillForm.pdType = aa[0]
          this.createBillForm.pdDataType = aa[1]
          console.log(this.createBillForm)
        }
      })
    },
    queryWmsInvoiceASync(queryString) {
      var invoice = ''
      if (queryString === undefined || queryString === '') {
        invoice = ''
      } else {
        invoice = queryString
      }
      this.selWmsInvoice = true
      remoteInvoiceWithArriveNotIn(invoice).then(res => {
        this.selWmsInvoice = false
        if (res.success) {
          if (res.content.length === 1) {
            if (res.content[0].wmsSysInvoiceNo === '' || res.content[0].wmsSysInvoiceNo === null) {
              this.wmsInvoiceList = []
            } else {
              this.wmsInvoiceList = res.content
            }
          } else {
            this.wmsInvoiceList = res.content
          }
        } else {
          this.wmsInvoiceList = []
        }
      })
    },
    reflush() {
      this.loadingTab = true
      this.findModelNoCount()
    },
    selTypeChange() {
      this.remoteMethodByWareHouseCode('')
    },
    getBatchNoWithIsActive() {
      getBatchNoWithIsActive().then(res => {
        if (res.success) {
          this.createBillForm.batchNo = res.content.pdBatchNo
        } else {
          this.createBillForm.batchNo = ''
        }
      })
    },
    creBlankBill(formName) {
      this.$message.success('已经开始生成,请耐心等待...')
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.creBlankBillBtu = true
          createPdBill(this.creBlankForm).then(res => {
            this.creBlankBillBtu = false
            this.dialogFormVisible = false
            if (res.success) {
              // this.$notify.success({
              //   title: '成功',
              //   message: res.content
              // })
              this.msg = res.content
              this.dialogVisible = true
              this.findModelNoCount()
            } else {
              this.msg = res.message
              this.dialogVisible = true
              // this.$notify.error({
              //   title: '错误',
              //   message: res.message,
              //   duration: 0
              // })
            }
          }).catch(res => {
            this.dialogFormVisible = false
            this.creBlankBillBtu = false
          })
        } else {
          this.dialogFormVisible = false
          console.log('error submit!!')
          this.creBlankBillBtu = false
          return false
        }
      })
    },
    pdDataTypeChange() {
      console.log('==>', this.createBillForm.pdDataType)
      this.findModelNoCount()
    },
    exportBillCount() {
      this.loadExportBillCount = true
      this.msg = '已开始导出,请耐心等待'
      exportBillCountData(this.createBillForm.pdDataType).then(res => {
        console.log('数据导出响应=>', res)
        if (res.size === 0) {
          this.loadExportBillCount = false
          this.msg = '暂无数据导出'
          return
        }
        var fileName = '导出清单.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.loadExportBillCount = false
      }).catch(error => {
        console.error(error)
        this.loadExportBillCount = false
        this.msg = '导出失败: ' + error
      })
    },
    findModelNoCount() {
      findModelNoCount(this.createBillForm.pdDataType).then(res => {
        if (res.success) {
          this.loadingTab = false
          this.createBillTableData = res.content
        } else {
          this.loadingTab = false
          this.createBillTableData = []
        }
      })
      // this.createBillTableData = []
      // findModelNoCountByMaster(this.createBillForm.pdDataType).then(res => {
      //   if (res.success) {
      //     for (var i = 0; i < res.content.length; i++) {
      //       this.createBillTableData.push(res.content[i])
      //     }
      //   }
      // })
      // findModelNoCountBySUB(this.createBillForm.pdDataType).then(res => {
      //   if (res.success) {
      //     for (var i = 0; i < res.content.length; i++) {
      //       this.createBillTableData.push(res.content[i])
      //     }
      //   }
      // })
      // findModelNoCountByWT(this.createBillForm.pdDataType).then(res => {
      //   if (res.success) {
      //     for (var i = 0; i < res.content.length; i++) {
      //       this.createBillTableData.push(res.content[i])
      //     }
      //   }
      // })
    },
    importData() {
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      this.uploadLoading = true
      if (this.file == null) {
        this.uploadLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        batchImpArriverNotIn(formData).then(res => {
          this.uploadLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              duration: 5000,
              type: 'success'
            })
            this.searchData()
            this.file = null
            this.uploadFileDialogVisible = false
          } else {
            this.$notify({
              duration: 0,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.uploadLoading = false
          this.$message.error('操作失败:' + error)
        })
      }
    },
    getPdType() {
      getDictDataByPid(this.pdTypeCode).then(res => {
        if (res.success) {
          this.pdTypeList = res.content.filter(item => {
            return item.extNote3 !== '1'
          })
        } else {
          this.pdTypeList = []
        }
      })
    },
    getDataCodesTreeBywarehouseType() {
      getDictDataByPid(this.warehouseTypeClassCode).then(res => {
        if (res.success) {
          this.warehouseTypeList = res.content
        } else {
          this.warehouseTypeList = []
        }
      })
    },
    exportDataBtu() {
      this.exportDialog = true
    },
    remoteMethodByWareHouseCode(code) {
      var warehouseCode = ''
      if (code === undefined) {
        warehouseCode = ''
      } else {
        warehouseCode = code
      }
      this.selWareHouseCodeLoading = true
      findWareHouseInfoWithLike(warehouseCode, this.searchForm.warehouseType).then(res => {
        if (res.success) {
          this.wareHouseCodeList = res.content
        } else {
          this.wareHouseCodeList = []
        }
        this.selWareHouseCodeLoading = false
      })
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    handleClickTab(tab, event) {
      console.log(tab.name)
    },

    handleTableClick(tab, event) {
      this.searchForm.page.pageNumber = 1
      this.searchData()
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    downLoadExcel() {
      this.$message.success('已开始下载，请耐心等待...')
      this.downLoadExcelBtuLoad = true
      downImpExel().then(res => {
        this.downLoadExcelBtuLoad = false
        const fileName = '到货未入数据导入.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
      }).catch(error => {
        this.downLoadExcelBtuLoad = false
        this.$message.warning('导出失败' + error)
      })
    },
    findList() {
      this.searchForm.page.pageNumber = 1
      this.searchData()
    },
    searchData() {
      if (this.activeTableName === 'tableFirst') {
        listArriveNotInWithGroup(this.searchForm).then(res => {
          if (res.success) {
            this.invoiceTableData = res.content.list
            this.total = res.content.total
          } else {
            this.invoiceTableData = []
            this.total = 0
          }
        })
      } else {
        listArriveNotInDetail(this.searchForm).then(res => {
          if (res.success) {
            this.detailTableData = res.content.list
            this.total = res.content.total
          } else {
            this.detailTableData = []
            this.total = 0
          }
        })
      }
    },
    exportData() {
      if (this.searchForm.expType === '' || this.searchForm.expType === null || this.searchForm.expType === undefined) {
        this.$message.error('请在检索条件处选择导出类型.')
        return
      }
      this.$message.success('已开始导出，请耐心等待')
      this.expArriveNotInBtuLoad = true
      exportArriveNotIn(this.searchForm).then(res => {
        if (res.size === 0) {
          this.expArriveNotInBtuLoad = false
          this.getExportMsgPrompt()
          return
        }
        var fileName = ''
        if (this.searchForm.expType === '1') {
          fileName = '到货未入数据导出-明细数据.xlsx'
        } else {
          fileName = '到货未入数据导出-发票数据.xlsx'
        }
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.expArriveNotInBtuLoad = false
        this.getExportMsgPrompt()
        this.exportDialog = false
      }).catch(error => {
        console.error(error)
        this.expArriveNotInBtuLoad = false
      })
    },
    getExportMsgPrompt() {
      getExportMsgPrompt().then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    impData() {
      this.uploadFileDialogVisible = true
      this.file = null
    },
    sureData() {
      this.$confirm('此操作会进行数据确认, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        arriveNotInInsertToBillData().then(res => {
          if (res.success) {
            this.searchData()
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    handleInvoiceSelectionChange(val) {
      console.log(val.records)
    },
    handleDetailDataSelectionChange() {},
    handleCreateBillSelectionChange() {},
    fetchListForInvoice() {},
    creBill() {
      isCreDhwrBlankBill().then(res => {
        if (!res.success) {
          this.isCanInput = false
        }
      })
      this.dialogFormVisible = true
    },
    printCurProBill() {
      this.$confirm('此操作将打印现品票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$message.success('已开始下载，请耐心等待...')
        this.dialogVisible = true
        this.msg = '正在打印现品票,大概需要5分钟,请耐心等待..'
        const title = '营业盘点现品票--在库'
        printPdXpBillDataWithPdf(title).then(res => {
          if (res.size === 0) {
            // this.$message.warning('暂无所需盘点现品票可打印')
            this.msg = '暂无所需盘点现品票可打印'
            return
          }
          const fileName = title + '.pdf'
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
          this.dialogVisible = false
          // this.msg = '打印现品票完毕'
        }).catch(error => {
          // this.$message.warning('打印失败' + error)
          this.dialogVisible = true
          this.msg = '打印现品票失败,失败原因:' + error
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    printBlankBill() {
      this.$confirm('此操作将打印货架实盘空白票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$message.success('已开始下载，请耐心等待...')
        this.dialogVisible = true
        this.msg = '正在打印货架实盘空白票,大概需要5分钟,请耐心等待..'
        const title = '营业盘点现品票--手写票'
        printPdXpBlankBillDataWithPdf(title).then(res => {
          if (res.size === 0) {
            // this.$message.warning('暂无所需货架实盘空白票可打印')
            this.msg = '暂无所需货架实盘空白票可打印'
            return
          }
          const fileName = title + '.pdf'
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
          this.dialogVisible = false
          // this.msg = '打印货架实盘空白票完毕'
        }).catch(error => {
          // this.$message.warning('打印失败' + error)
          this.dialogVisible = true
          this.msg = '打印货架实盘空白票失败,失败原因:' + error
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    printArriveNotInBlankBill() {
      this.$confirm('此操作将打印到货未入空白票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$message.success('已开始下载，请耐心等待...')
        this.dialogVisible = true
        this.msg = '正在打印到货未入空白票,大概需要5分钟,请耐心等待..'
        const title = '营业盘点现品票--手写票'
        printPdArriveNotInBlankBillDataWithPdf(title).then(res => {
          if (res.size === 0) {
            // this.$message.warning('暂无所需到货未入空白票可打印')
            this.msg = '暂无所需到货未入空白票可打印'
            return
          }
          const fileName = title + '.pdf'
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
          this.dialogVisible = false
          // this.msg = '打印到货未入空白票完毕'
        }).catch(error => {
          // this.$message.warning('打印失败' + error)
          this.dialogVisible = true
          this.msg = '打印到货未入空白票失败,失败原因: ' + error
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    printStandBill() {
      this.$confirm('此操作将打印立会票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$message.success('已开始下载，请耐心等待...')
        this.dialogVisible = true
        this.msg = '正在打印立会票,大概需要5分钟,请耐心等待..'
        const title = '营业盘点立会票--在库'
        printLhBillDataWithPdf(title).then(res => {
          if (res.size === 0) {
            // this.$message.warning('暂无所需立会票可打印')
            this.msg = '暂无所需立会票可打印'
            return
          }
          const fileName = title + '.pdf'
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
          this.dialogVisible = false
          // this.msg = '打印立会票完毕'
        }).catch(error => {
          // this.$message.warning('打印失败' + error)
          this.dialogVisible = true
          this.msg = '打印立会票失败,失败原因: ' + error
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    printDataBill() {
      this.$confirm('此操作将打印数据票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$message.success('已开始下载，请耐心等待...')
        this.dialogVisible = true
        this.msg = '正在打印数据票,大概需要5分钟,请耐心等待..'
        const title = '营业数据盘点票'
        printPdDataBillDataWithPdf(title).then(res => {
          if (res.size === 0) {
            // this.$message.warning('暂无所需营业数据盘点票可打印')
            this.msg = '暂无所需营业数据盘点票可打印'
            return
          }
          const fileName = title + '.pdf'
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
          this.dialogVisible = false
          // this.msg = '打印数据票完毕'
        }).catch(error => {
          // this.$message.warning('打印失败' + error)
          this.dialogVisible = true
          this.msg = '打印数据票失败,失败原因: ' + error
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    printArriNoEnterBill() {
      this.$confirm('此操作将打印到货未入清单票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // this.$message.success('已开始下载，请耐心等待...')
        this.dialogVisible = true
        this.msg = '正在打印到货未入清单票,大概需要5分钟,请耐心等待..'
        const title = '清单盘点票'
        printPdArriveNotInWithPdf(title).then(res => {
          if (res.size === 0) {
            // this.$message.warning('暂无所需清单盘点票可打印')
            this.msg = '暂无所需清单盘点票可打印'
            return
          }
          const fileName = title + '.pdf'
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
          this.dialogVisible = false
          // this.msg = '打印到货未入清单票完毕'
        }).catch(error => {
          // this.$message.warning('打印失败' + error)
          this.dialogVisible = true
          this.msg = '打印到货未入清单票失败,失败原因: ' + error
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    printWtDataBill() {
      this.$confirm('此操作将打印寄售库存盘点票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$message.success('已开始下载，请耐心等待...')
        const warehouseCode = 'WA6P16'
        printWtBillDataWithPdf(warehouseCode).then(res => {
          console.log('打印寄售库存盘点票', res)
          if (res.size === 0) {
            this.$message.warning('暂无所需寄售库存盘点票可打印')
            return
          }
          const fileName = warehouseCode + '.pdf'
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
          this.dialogVisible = false
        }).catch(error => {
          this.$message.warning('打印失败' + error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    expotToArriveNotInByWmsData() {
      this.$confirm('此操作将导出到货未入库数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.expotToArriveNotInByWmsDataBtu = true
        expotToArriveNotInByWmsData().then(res => {
          this.expotToArriveNotInByWmsDataBtu = false
          if (res.success) {
            this.$message.success(res.content)
            this.searchData()
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    sure() {
      if (this.createBillForm.pdDataType === '') {
        this.$message.error('请选择盘点数据')
        return
      }
      if (this.createBillForm.pdType === '') {
        this.$message.error('请选择盘点形式')
        return
      }
      var pdTypeName = ''
      var pdTypeDataName = ''
      for (const i in this.pdTypeList) {
        if (this.pdTypeList[i].code === this.createBillForm.pdType) {
          pdTypeName = this.pdTypeList[i].codeName
        }
      }
      for (const i in this.pdDataTypeList) {
        if (this.pdDataTypeList[i].code === this.createBillForm.pdDataType) {
          pdTypeDataName = this.pdDataTypeList[i].codeName
        }
      }
      const msg = '此操作将进行盘点形式确认,您选择了 按照数据' + pdTypeDataName + '-' + pdTypeName + '的方式 , 是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        getPdStatusFromRedis().then(res => {
          if (res.success) {
            this.surePdTypeLoad = true
            surePdType(this.createBillForm.pdType, this.createBillForm.pdDataType).then(res => {
              this.surePdTypeLoad = false
              if (res.success) {
                this.$message.success(res.content)
              } else {
                this.$message.error(res.message)
              }
            })
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    }
  }
}
</script>
<style scoped>
.fontSty{
    font-size: large;
    color: red;
    margin-left: 10px;
}
.inputCreBlank{
  width: 100px;
}
</style>
