<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card style="margin-bottom: -1px;margin-top:-5px">
        <el-row :gutter="20" type="flex">
          <el-col :span="6">
            <div class="cusbox">
              <el-form label-position="left" class="demo-table-expand">
                <el-form-item style="margin-bottom:0px;" label="采购单号:">
                  {{ purchaseInfo.orderno }}
                </el-form-item>
                <el-form-item style="margin-bottom:0px;" label="型号:">
                  {{ purchaseInfo.modelno }}
                </el-form-item>
                <el-form-item style="margin-bottom:0px;" label="供应商接单号:">
                  {{ purchaseInfo.replyorderno }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <el-form label-position="left" class="demo-table-expand">
                <el-form-item style="margin-bottom:0px;" label="采购日期:">
                  {{ purchaseInfo.purchasedate | formatDate }}
                </el-form-item>
                <el-form-item style="margin-bottom:0px;" label="采购数量:">
                  {{ purchaseInfo.quantity }}
                </el-form-item>
                <!--bug 12944-->
                <el-form-item style="margin-bottom:0px;" label="已入库数量:">
                  {{ purchaseInfo.qtyreceive }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <el-form label-position="left" class="demo-table-expand">
                <el-form-item style="margin-bottom:0px;" label="到货仓库:">
                  {{ getWarehouseName(purchaseInfo.receivewarehouseId) }}
                </el-form-item>
                <el-form-item style="margin-bottom:0px;" label="完成日期:">
                  {{ purchaseInfo.finishdate | formatDate }}
                </el-form-item>
                <el-form-item style="margin-bottom:0px;" label="变更交货期:">
                  {{ purchaseInfo.dlvmoddate | formatDate }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="6">
            <div>
              <el-form label-position="left" class="demo-table-expand">
                <el-form-item style="margin-bottom:0px;" label="状态:">
                  {{ purchaseInfo.statecode | purchaseTypeFormat }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="6">
            <div style = "text-align:right;margin-top:5px;">
              <el-form label-position="left">
                <el-button type="primary" icon="el-icon-back" size="mini" @click="goBack()">返回列表</el-button>
                <!-- <el-button v-if="purchaseInfo.statecode === '2'" type="danger" size="mini" @click="cancelVisible()">取消</el-button> -->
              </el-form>
            </div>
          </el-col>
        </el-row>
      </el-card>
      <div style="margin-top:10px;">
        <el-tabs type="border-card" @tab-click="handleClick">
          <el-tab-pane label="到货信息">
            <el-table
              ref="multipleTable"
              :data="tableDataGoods"
              :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
              :cell-style="{padding: '1px 3px'}"
              highlight-current-row
              border
              stripe
              @row-click="orderView">
              <el-table-column
                v-for="column in tableColumnGoods"
                :key="column.prop"
                :prop="column.prop"
                :label="column.label"
                :width="column.width"
                :type="column.type"
                :formatter="column.formatter"
                header-align="center"
                align="center"
                show-overflow-tooltip
              />
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="请购信息">
            <el-table
              v-loading="purchaselistLoading"
              :data="tableDataRequest"
              :header-cell-style="{'text-align':'center','background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
              :header-row-style="{'padding': '2px'}"
              :row-style="rowStyle"
              element-loading-text="拼命加载中"
              border
              fit
              highlight-current-row
              style="width:100%">
              <!-- <el-table-column
                type="selection"
                width="55"/> -->
              <el-table-column label="请购单号" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.orderno }}</span>
                  <!-- <div style="padding: 4px;color: #409EFF;cursor: pointer" @click="toDetail(scope.row.orderno)"><u>{{ scope.row.orderno }}</u></div> -->
                </template>
              </el-table-column>
              <el-table-column label="子项号" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.itemno }}</span>
                </template>
              </el-table-column>
              <el-table-column label="拆分" align="center" min-width="50">
                <template slot-scope="scope">
                  <span>{{ scope.row.splititemno }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip label="型号" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.modelno }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip label="请购数量" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.quantity }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip label="SHIKOMI" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.shikomianswerno }}</span>
                </template>
              </el-table-column>
              <el-table-column :formatter="warehouseFormatter" prop="requestwarehouseid" show-overflow-tooltip label="入库仓库" align="center"/>
              <el-table-column :formatter="suppilyFormatter" prop="supplierid" show-overflow-tooltip label="供应商" align="center"/>
              <el-table-column show-overflow-tooltip label="请购日期" min-width="120" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.requesttime | formatDate }}</span>
                </template>
              </el-table-column>
              <el-table-column show-overflow-tooltip label="指定制造出荷日" min-width="120" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
                </template>
              </el-table-column>
              <el-table-column label="请购单类别" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.purchasetype | ordTypeFormat }}</span>
                </template>
              </el-table-column>
              <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" align="center"/>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="预约信息">
            <!-- <div style="margin-top: 48px;margin-bottom:20px">
              <el-divider content-position="left">
                <el-tag>预约信息：</el-tag>
              </el-divider>
            </div> -->
            <el-table
              v-loading="appointmentLoading"
              ref="multipleTable"
              :data="appointmentData"
              :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
              :cell-style="{padding: '1px 3px'}"
              highlight-current-row
              border
              stripe
              @row-click="orderView">
              <el-table-column
                v-for="column in appointmentColumn"
                :key="column.prop"
                :prop="column.prop"
                :label="column.label"
                :width="column.width"
                :type="column.type"
                :formatter="column.formatter"
                header-align="center"
                align="center"
                show-overflow-tooltip
              />
            </el-table>
          </el-tab-pane>
        </el-tabs>
        <!-- <div style="margin-top:25px;margin-bottom:25px">
        <el-divider/>
      </div> -->
      </div>
    </div>
  </div>
</template>
<script>
import { findPurchaseByid, findRequestByOrder, findGoods, wmSerch, findGoodsByOrder } from '@/api/purchaseOrder'
import { getSuppily, getWarehouse } from '@/api/intercept'
import { getTransIds } from '@/api/purchaseOrder'
import moment from 'moment'
export default {
  name: 'PurchaseDetail',
  inject: ['reload'],
  data() {
    return {
      id: '',
      orderno: '',
      itemno: '',
      splititemno: '',
      statecode: '',
      modelno: '',
      quantity: '',
      listLoading: false,
      purchaselistLoading: false,
      appointmentLoading: false,
      appointmentData: [],
      appointmentRequest: {},
      tableData: [],
      tableDataGoods: [],
      tableDataRequest: [],
      warehouseList: [],
      headParam: {},
      purchaseInfo: {
        orderno: '',
        ordernoold: '',
        itemno: '',
        splititemno: '',
        modelno: '',
        purchasedate: '',
        quantity: '',
        receivewarehouseId: '',
        statecode: '',
        replyorderno: '',
        qtyreceive: '',
        dlvmoddate: '',
        finishdate: ''
      },
      DictData: {
        impdataStatus: [
          { id: '0', name: '待收货' },
          { id: '1', name: '签收待处理' },
          { id: '2', name: '已入库' },
          { id: '9', name: '异常' }
        ],
        stockType: [
          { id: 'N', name: '在库' },
          { id: 'Tx/P', name: '在途' },
          { id: 'CG', name: '采购' }
        ],
        transtype: []
      },
      suppilyList: [],
      appointmentColumn: [
        {
          label: '客户单号',
          prop: 'orderNo'
        },
        {
          label: '客户项号',
          prop: 'orderItem'
        },
        // {
        //   label: '出库区分',
        //   prop: 'stockType',

        //   formatter: this.stockTypeFormatter
        // },
        // {
        //   label: '库房代码',
        //   prop: 'stockCode'
        // },
        // {
        //   label: '库存类别',
        //   prop: 'inventoryTypeCode'
        // },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '数量',
          prop: 'quantity'
        },
        {
          label: '变更日期',
          prop: 'updateTime',
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '' : moment(cellValue).format('YYYY-MM-DD HH:mm')
          }
        }
      ],
      tableColumnGoods: [
        {
          label: '发票号',
          prop: 'invoiceno',
          width: 100
        },
        {
          label: '发票ID',
          prop: 'invoiceid',
          width: 120
        },
        {
          label: '数量',
          prop: 'quantity',
          width: 90
        },
        {
          label: '到货日期',
          prop: 'receivedate',
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '' : moment(cellValue).format('YYYY-MM-DD')
          }
        },
        {
          label: '入库日期',
          prop: 'imptime',
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '' : moment(cellValue).format('YYYY-MM-DD HH:mm')
          }
        },
        {
          label: '签收库房',
          prop: 'receivewarehouseid',
          formatter: this.warehouseFormatter
        },

        {
          label: '供应商',
          prop: 'supplierid',
          formatter: this.suppilyFormatter
        },
        {
          label: '箱号',
          prop: 'caseno'
        },
        {
          label: 'Barcode',
          prop: 'barcode'
        },
        {
          label: 'rohs',
          prop: 'greencode'
        },
        {
          label: '状态',
          prop: 'statecode',
          formatter: this.impdataFormatter
        },
        {
          label: '转运状态',
          prop: 'transferStatus'
        },
        {
          label: '转运承运商',
          prop: 'transferCarried'
        },
        {
          label: '转运运单号',
          prop: 'transferExpressCode'
        }
      ],
      purchaseCheckCondition: {}
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (oldVal && newVal && newVal.name === 'PurchaseDetail') {
          // this.newApply() // 重置页面
          this.id = this.$route.query.id
          this.orderno = this.$route.query.orderno
          this.itemno = this.$route.query.itemno
          this.splititemno = this.$route.query.splititemno
          this.statecode = this.$route.query.statecode
          this.modelno = this.$route.query.modelno
          this.quantity = this.$route.query.quantity
          this.findData()
          this.findRequestData()
          this.findGoodsData()
          this.findAppointment()
        }
      }
    }
  },
  created() {
    this.id = this.$route.query.id
    this.orderno = this.$route.query.orderno
    this.itemno = this.$route.query.itemno
    this.splititemno = this.$route.query.splititemno
    this.statecode = this.$route.query.statecode
    this.modelno = this.$route.query.modelno
    this.quantity = this.$route.query.quantity
    this.getSuppilyTrans()
    this.findData()
    this.findRequestData()
    this.findGoodsData()
    this.initData()
    // this.findAppointment()
  },
  methods: {
  // 取消完成之后的重置操作
    refresh() {
      this.getSuppilyTrans()
      this.reload()
      // this.$router.go(0) // 会出现一段空白页，用户体验不好
    },
    handleClick() {
      this.findData()
      this.findRequestData()
      this.findAppointment()
    },
    orderView(row) {
      var condition = {
        orderId: row.invoiceno
      }
      // 得到发票号，去调用后台接口查询物流信息
      console.log(condition)
    },
    rowStyle({ row, rowIndex }) {
      return 'height:37px'
    },
    async findData() {
      if (this.statecode === 'B') {
        if (this.splititemno == null) {
          this.purchaseInfo.orderno = this.orderno + '-' + this.itemno
        } else {
          this.purchaseInfo.orderno = this.orderno + '-' + this.itemno + '-' + this.splititemno
        }
        this.purchaseInfo.ordernoold = this.orderno
        this.purchaseInfo.itemno = this.itemno
        this.purchaseInfo.splititemno = this.splititemno
        this.purchaseInfo.modelno = this.modelno
        this.purchaseInfo.purchasedate = null
        this.purchaseInfo.quantity = this.quantity
        this.purchaseInfo.dlvmoddate = null
        this.purchaseInfo.finishdate = null
        this.purchaseInfo.statecode = this.statecode
        return
      }
      this.listLoading = true
      findPurchaseByid(this.id).then(res => {
        // this.tableData = res
        if (res.splititemno == null) {
          this.purchaseInfo.orderno = res.orderno + '-' + res.itemno
        } else {
          this.purchaseInfo.orderno = res.orderno + '-' + res.itemno + '-' + res.splititemno
        }
        this.purchaseInfo.ordernoold = res.orderno
        this.purchaseInfo.itemno = res.itemno
        this.purchaseInfo.splititemno = res.splititemno
        this.purchaseInfo.mergeflag = res.mergeflag
        // this.purchaseInfo.orderno = res.orderno
        this.purchaseInfo.modelno = res.modelno
        this.purchaseInfo.purchasedate = res.purchasedate
        this.purchaseInfo.quantity = res.quantity
        this.purchaseInfo.receivewarehouseId = res.receivewarehouseid
        this.purchaseInfo.statecode = res.statecode
        this.purchaseInfo.replyorderno = res.replyorderno
        this.purchaseInfo.qtyreceive = res.qtyreceive
        this.purchaseInfo.dlvmoddate = res.dlvmoddate
        this.purchaseInfo.finishdate = res.finishdate
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 查询请购信息
    async findRequestData() {
      if (this.statecode === 'B') {
        return
      }
      this.purchaselistLoading = true
      findRequestByOrder(this.id).then(res => {
        this.tableDataRequest = res.data
        this.purchaselistLoading = false
      }).catch(error => {
        this.purchaselistLoading = false
        console.log(error)
      })
    },
    // 预约
    findAppointment() {
      if (this.statecode === 'B') {
        return
      }
      this.appointmentLoading = true
      this.appointmentRequest.orderno = this.orderno
      this.appointmentRequest.itemno = this.itemno
      if (this.splititemno == null) {
        this.appointmentRequest.splititemno = null
      } else {
        this.appointmentRequest.splititemno = this.splititemno
      }
      // this.appointmentRequest.orderno = this.purchaseInfo.ordernoold
      // this.appointmentRequest.itemno = this.purchaseInfo.itemno
      // if (this.purchaseInfo.splititemno == null) {
      //   this.appointmentRequest.splititemno = null
      // } else {
      //   this.appointmentRequest.splititemno = this.purchaseInfo.splititemno
      // }
      wmSerch(this.appointmentRequest).then(res => {
        if (res.success === true) {
          this.appointmentData = res.data
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.appointmentLoading = false
      }).catch(error => {
        this.appointmentLoading = false
        console.log(error)
      })
    },
    // 查找到货信息
    async findGoodsData() {
      if (this.statecode === 'B') {
        var goodsParam = {}
        goodsParam.orderno = this.orderno
        goodsParam.itemno = this.itemno
        goodsParam.splititemno = this.splititemno
        findGoodsByOrder(goodsParam).then(res => {
          this.tableDataGoods = res.data
        }).catch(error => {
          console.log(error)
        })
      } else {
        findGoods(this.id).then(res => {
          this.tableDataGoods = res.data
        }).catch(error => {
          console.log(error)
        })
      }
    },
    goBack() {
      // this.$router.push({ path: '/purchase/puchaseQuery' })
      this.$router.back()
    },
    getSuppilyTrans() {
      this.getSuppily()
      this.getWarehouseList()
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    getWarehouseList() {
      getWarehouse().then(res => {
        this.warehouseList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    warehouseFormatter(row, column, cellValue, index) {
      const item = this.warehouseList.find(dict => dict.warehouseCode === cellValue)
      return item ? item.warehouseName : cellValue
    },
    // bug 10711增加仓库代码中文显示
    getWarehouseName(val) {
      const obj = this.warehouseList.filter(item => item.warehouseCode === val)[0]
      return obj ? obj.warehouseName : ''
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    impdataFormatter(row, column, cellValue, index) {
      const item = this.DictData.impdataStatus.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    stockTypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.stockType.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    // 运输方式转换
    transtypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.transtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    // 处理状态
    initData() {
      var transParam = {}
      transParam.supplierId = null
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      transParam.translation = true
      getTransIds(transParam).then(res => {
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.DictData.transtype.push({ value: dict.id, label: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    }
  }
}
</script>
