<template>
  <div class="container">
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="年度报表" name="first">
        <div class="header">
          <el-form :inline="true" :model="searchForm" size="mini" class="demo-form-inline">
            <el-form-item >
              <el-input v-model="searchForm.modelNo" clearable placeholder="请输入型号" />
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
            <el-form-item>
              <el-button type="primary" plain @click="initData()">查询</el-button>
              <el-button :loading="markPdReportBtu" type="primary" plain @click="markPdReport()">生成当期报表</el-button>
              <!-- <el-button :loading="exportTwoPdReportBtu" type="primary" plain @click="exportTwoPdReport()">导出两方报表</el-button> -->
              <el-button :loading="exportThreePdReportBtu" type="primary" plain @click="exportThreePdReport()">导出三方报表</el-button>
            </el-form-item>
          </el-form>
        </div>
        <div class="body">
          <vxe-table
            :data="tableData"
            :column-config="{resizable: true}"
            max-height="550"
            border
            size="mini"
            @checkbox-change="selectChangeEvent"
          >
            <vxe-table-column type="checkbox" fixed="left" width="50"/>
            <vxe-table-column field="pdBatchNo" title="盘点批次号" fixed="left" width="90" show-overflow />
            <vxe-table-column field="modelNo" title="型号" fixed="left" width="150" show-overflow />
            <vxe-table-column field="remark" title="差异类型" width="120" show-overflow/>
            <vxe-table-column field="balanceQty" title="财务结存" width="100"/>
            <vxe-table-column field="wmsSumQty" title="物流盘点" width="100"/>
            <vxe-table-column field="opsSumQty" title="业务账簿数" width="100"/>
            <vxe-table-column field="sampleNoOver" title="样品未结转" width="100"/>
            <vxe-table-column field="shippedNoFinance" title="已出库未推财务" width="120"/>
            <vxe-table-column field="financeCompensate" title="财务补偿数据" width="100"/>
            <vxe-table-column field="wmsBorrow" title="借库数" width="100"/>
            <vxe-table-column field="opsTranOnway" title="OPS调拨在途" width="100"/>
            <vxe-table-column field="opsManuOnway" title="OPS制造在途" width="100"/>
            <vxe-table-column field="opsInventroyQty" title="OPS库存数据" width="100"/>
            <vxe-table-column field="opsPurchaseNoIn" title="OPS采购到货未入" width="120"/>
            <vxe-table-column field="opsReturnNoIn" title="OPS退货到货未入" width="120"/>
            <vxe-table-column field="opsTranNoIn" title="OPS调拨到货未入" width="120"/>
            <vxe-table-column field="opsReturnOnway" title="OPS退货在途" width="100"/>
            <vxe-table-column field="opsZhZt" title="OPS组换在途" width="100"/>
            <vxe-table-column field="opsZhDhwr" title="OPS组换到货未入" width="100"/>
            <vxe-table-column field="wmsTranNoIn" title="WMS到货未入" width="100"/>
            <vxe-table-column field="wmsTranOnway" title="WMS调拨在途" width="100"/>
            <vxe-table-column field="wmsReturnOnway" title="WMS退货在途" width="100"/>
            <vxe-table-column field="wmsManuOnway" title="WMS制造在途" width="100"/>
            <vxe-table-column field="wmsZhZt" title="WMS组换在途" width="100"/>
            <vxe-table-column field="wmsZhDhwr" title="WMS组换到货未入" width="100"/>
            <vxe-table-column field="wlCw" title="物流-财务" width="100"/>
            <vxe-table-column field="ywCw" title="业务-财务" width="100"/>
            <vxe-table-column field="wlYw" title="物流-业务" width="100"/>
            <vxe-table-column field="kbjYwQty" title="北京业务数" width="100"/>
            <vxe-table-column field="kshYwQty" title="上海业务数" width="100"/>
            <vxe-table-column field="kgzYwQty" title="广州业务数" width="100"/>
            <vxe-table-column field="sczYwQty" title="常州业务数" width="100"/>
            <vxe-table-column field="swhYwQty" title="武汉业务数" width="100"/>
            <vxe-table-column field="sjnYwQty" title="济南业务数" width="100"/>
            <vxe-table-column field="ssyYwQty" title="沈阳业务数" width="100"/>
            <vxe-table-column field="styYwQty" title="太原业务数" width="100"/>
            <vxe-table-column field="swfYwQty" title="潍坊业务数" width="100"/>
            <vxe-table-column field="scdYwQty" title="成都业务数" width="100"/>
            <vxe-table-column field="wtYwQty" title="寄售业务数" width="100"/>
            <vxe-table-column field="kbjQty" title="北京盘点数" width="100"/>
            <vxe-table-column field="kshQty" title="上海盘点数" width="100"/>
            <vxe-table-column field="kgzQty" title="广州盘点数" width="100"/>
            <vxe-table-column field="sczQty" title="常州盘点数" width="100"/>
            <vxe-table-column field="swhQty" title="武汉盘点数" width="100"/>
            <vxe-table-column field="sjnQty" title="济南盘点数" width="100"/>
            <vxe-table-column field="ssyQty" title="沈阳盘点数" width="100"/>
            <vxe-table-column field="styQty" title="太原盘点数" width="100"/>
            <vxe-table-column field="swfQty" title="潍坊盘点数" width="100"/>
            <vxe-table-column field="scdQty" title="成都盘点数" width="100"/>
            <vxe-table-column field="wtQty" title="寄售盘点数" width="100"/>
            <vxe-table-column field="kbjwlYwQty" title="北京物流-业务" width="100"/>
            <vxe-table-column field="kshwlYwQty" title="上海物流-业务" width="100"/>
            <vxe-table-column field="kgzwlYwQty" title="广州物流-业务" width="100"/>
            <vxe-table-column field="sczwlYwQty" title="常州物流-业务" width="100"/>
            <vxe-table-column field="swhwlYwQty" title="武汉物流-业务" width="100"/>
            <vxe-table-column field="sjnwlYwQty" title="济南物流-业务" width="100"/>
            <vxe-table-column field="ssywlYwQty" title="沈阳物流-业务" width="100"/>
            <vxe-table-column field="stywlYwQty" title="太原物流-业务" width="100"/>
            <vxe-table-column field="swfwlYwQty" title="潍坊物流-业务" width="100"/>
            <vxe-table-column field="scdwlYwQty" title="成都物流-业务" width="100"/>
            <vxe-table-column field="wtwlYwQty" title="寄售物流-业务" width="100"/>
            <vxe-table-column field="lastInitialQuantity" title="库存期初" width="100"/>
            <vxe-table-column field="eprice" title="E价" width="100"/>
          </vxe-table>
          <pagination
            v-show="total > 10"
            :total="total"
            :page-sizes="[10, 20, 100, 150, 200]"
            :page.sync="searchForm.page.pageNumber"
            :limit.sync="searchForm.page.pageSize"
            @pagination="initData()"/>
        </div>
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
      </el-tab-pane>
      <el-tab-pane label="月度报表" name="second">
        <el-form :inline="true" :model="searchForm" size="mini" class="demo-form-inline">
          <el-form-item>
            <el-select
              v-model="searchYdPdParam.pdBatchNo"
              :remote-method="remoteMethodYdPdBatchNo"
              :loading="ydselLoading"
              filterable
              remote
              clearable
              placeholder="盘点批次号">
              <el-option
                v-for="item in ydPdBatchNoList"
                :key="item.pdBatchNo"
                :label="item.lable"
                :value="item.pdBatchNo"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-select v-model="searchYdPdParam.warehouseType" clearable placeholder="仓库类别" @change="selTypeChange">
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
              v-model="searchYdPdParam.warehouseList"
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
          <el-form-item >
            <el-input v-model="searchYdPdParam.modelNo" clearable placeholder="请输入型号" />
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="listYdPdThreeReportWare()">查询</el-button>
            <el-button :loading="createYdReportBtu" type="primary" @click="createYdReport()">生成当期月度报表</el-button>
            <el-button :loading="exportYDPdReportBtu" type="primary" @click="exportYdPdReport()">导出月度报表</el-button>
          </el-form-item>
        </el-form>
        <vxe-table
          :data="ydPdtableData"
          :column-config="{resizable: true}"
          max-height="550"
          border
          size="mini"
          @checkbox-change="selectChangeEvent"
        >
          <vxe-column type="seq" fixed="left" width="60"/>
          <vxe-column field="pdBatchNo" fixed="left" title="批次日期" width="100"/>
          <vxe-column field="warehouseCode" fixed="left" title="库房" width="100"/>
          <vxe-column field="modelNo" fixed="left" title="型号" width="100"/>
          <vxe-column field="balanceQty" title="财务结存" width="100"/>
          <vxe-column field="sampleNoOver" title="样品未结转" width="100"/>
          <vxe-column field="shippedNoFinance" title="已发货未推财务" width="120"/>
          <vxe-column field="financeCompensate" title="财务补偿数据" width="100"/>
          <vxe-column field="opsTranOnway" title="ops调拨在途" width="100"/>
          <vxe-column field="opsManuOnway" title="ops制造在途" width="100"/>
          <vxe-column field="opsReturnOnway" title="ops退货在途" width="100"/>
          <vxe-column field="opsPurchaseNoIn" title="ops采购未入" width="100"/>
          <vxe-column field="opsTranNoIn" title="ops调拨未入" width="100"/>
          <vxe-column field="opsReturnNoIn" title="ops退货未入" width="100"/>
          <vxe-column field="opsInventroyQty" title="ops库存数量" width="100"/>
          <vxe-table-column field="opsZhZt" title="ops组换在途" width="100"/>
          <vxe-table-column field="opsZhDhwr" title="ops组换到货未入" width="100"/>
          <vxe-column field="opsSumQty" title="ops合计数量" width="100"/>
          <vxe-column field="wmsBorrow" title="wms线下借库" width="100"/>
          <vxe-column field="wmsTranOnway" title="wms调拨在途" width="100"/>
          <vxe-column field="wmsManuOnway" title="wms制造在途" width="100"/>
          <vxe-column field="wmsReturnOnway" title="wms退货在途" width="100"/>
          <vxe-table-column field="wmsZhZt" title="wms组换在途" width="100"/>
          <vxe-column field="wmsPurchaseNoIn" title="wms采购未入" width="120"/>
          <vxe-column field="wmsTranNoIn" title="wms调拨未入" width="100"/>
          <vxe-column field="wmsReturnNoIn" title="wms退货未入" width="100"/>
          <vxe-table-column field="wmsZhDhwr" title="wms组换到货未入" width="100"/>
          <vxe-column field="wmsInventoryIntensive" title="wms集约待交接" width="100"/>
          <vxe-column field="wmsInventoryAct" title="wms库存数量" width="100"/>
          <vxe-column field="wmsSumQty" title="wms合计数量" width="100"/>
          <vxe-column field="lastInitialQuantity" title="上期盘点期初数" width="110"/>
          <vxe-column field="eprice" title="E价" width="110"/>
        </vxe-table>
        <pagination
          v-show="ydtotal > 10"
          :total="ydtotal"
          :page-sizes="[10, 20, 100, 150, 200]"
          :page.sync="searchYdPdParam.page.pageNumber"
          :limit.sync="searchYdPdParam.page.pageSize"
          @pagination="listYdPdThreeReportWare()"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>
import { pdReportList, markPdReport, exportThreePdReport, exportTwoPdReport, findBatchNo, getPdBatchNoListFromReportWare, listYdPdThreeReportWare, exportYdPdReport, getBatchNoWithIsActive, makePdReport } from '@/api/pd/pd'
import { getDictDataByPid, findWareHouseInfoWithLike } from '@/api/common/dict'
import Pagination from '@/components/Pagination'
export default {
  name: 'PdReport',
  components: { Pagination },
  data() {
    return {
      searchForm: {
        modelNo: '',
        pdBatchNo: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      warehouseTypeClassCode: '4001',
      selLoading: false,
      ydselLoading: false,
      exportYDPdReportBtu: false,
      createYdReportBtu: false,
      exportThreePdReportBtu: false,
      exportTwoPdReportBtu: false,
      markPdReportBtu: false,
      tableData: [],
      pdBatchNoList: [],
      ydPdtableData: [],
      ydPdBatchNoList: [],
      warehouseTypeList: [],
      selWareHouseCodeLoading: false,
      wareHouseCodeList: [],
      total: 0,
      dialogVisible: false,
      msg: '',
      activeName: 'first',
      ydtotal: 0,
      searchYdPdParam: {
        warehouseType: '',
        warehouseList: [],
        pdBatchNo: '',
        modelNo: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      }
    }
  },
  created() {
    this.getCurBatchNo()
    this.remoteMethod('')
    this.remoteMethodYdPdBatchNo('')
    this.remoteMethodByWareHouseCode()
    this.getDataCodesTreeBywarehouseType()
  },
  methods: {
    createYdReport() {
      this.createYdReportBtu = true
      makePdReport().then(res => {
        this.createYdReportBtu = false
        if (res.success) {
          this.$notify({
            title: '成功',
            message: '生成完毕',
            type: 'success'
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
          this.createYdReportBtu = false
        }
      }).catch(error => {
        this.createYdReportBtu = false
        console.log(error)
      })
    },
    getCurBatchNo() {
      getBatchNoWithIsActive().then(res => {
        this.searchForm.pdBatchNo = res.content.pdBatchNo
        this.initData()
      })
    },
    exportYdPdReport() {
      if (this.searchYdPdParam.pdBatchNo === undefined || this.searchYdPdParam.pdBatchNo === '') {
        this.$message.error('请选择盘点批次号再进行导出')
        return
      }
      this.exportYDPdReportBtu = true
      this.$notify({
        title: '成功',
        message: '报表正在导出,导出完毕之后会以邮件形式发送给您,请留意接收',
        type: 'success'
      })
      exportYdPdReport(this.searchYdPdParam).then(res => {
        this.exportYDPdReportBtu = false
      })
    },
    listYdPdThreeReportWare() {
      listYdPdThreeReportWare(this.searchYdPdParam).then(res => {
        if (res.success) {
          this.ydPdtableData = res.content.list
          this.ydtotal = res.content.total
        } else {
          this.ydPdtableData = []
          this.ydtotal = 0
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
    remoteMethodByWareHouseCode(code) {
      var warehouseCode = ''
      if (code === undefined) {
        warehouseCode = ''
      } else {
        warehouseCode = code
      }
      this.selWareHouseCodeLoading = true
      findWareHouseInfoWithLike(warehouseCode, this.searchYdPdParam.warehouseType).then(res => {
        if (res.success) {
          this.wareHouseCodeList = res.content
        } else {
          this.wareHouseCodeList = []
        }
        this.selWareHouseCodeLoading = false
      })
    },
    selTypeChange() {
      this.remoteMethodByWareHouseCode('')
    },
    remoteMethodYdPdBatchNo(pdBatchNo) {
      this.ydselLoading = true
      getPdBatchNoListFromReportWare(pdBatchNo).then(res => {
        if (res.success) {
          this.ydPdBatchNoList = res.content
        } else {
          this.ydPdBatchNoList = []
        }
        this.ydselLoading = false
      })
    },
    handleClick(tab, event) {
      if (tab.name === 'first') {
        this.initData()
      }
      if (tab.name === 'second') {
        this.listYdPdThreeReportWare()
      }
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
    exportTwoPdReport() {
      this.exportTwoPdReportBtu = true
      this.$notify({
        title: '成功',
        message: '报表正在导出,导出完毕之后会以邮件形式发送给您,请留意接收',
        type: 'success'
      })
      exportTwoPdReport().then(res => {
        this.exportTwoPdReportBtu = false
      })
    },
    exportThreePdReport() {
      if (this.searchForm.pdBatchNo === '' || this.searchForm.pdBatchNo === undefined) {
        this.$notify({
          title: '警告',
          message: '请选择导出批次号',
          type: 'warn'
        })
        return
      }
      this.exportThreePdReportBtu = true
      this.$notify({
        title: '成功',
        message: '报表正在导出,导出完毕之后会以邮件形式发送给您,请留意接收',
        type: 'success'
      })
      exportThreePdReport(this.searchForm).then(res => {
        this.exportThreePdReportBtu = false
      })
    },
    initData() {
      pdReportList(this.searchForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        } else {
          this.tableData = []
          this.total = 0
        }
      })
    },
    markPdReport() {
      this.msg = '正在生成盘点报表,大概所需8分钟左右'
      this.dialogVisible = true
      markPdReport().then(res => {
        this.dialogVisible = true
        if (res.success) {
          this.dialogVisible = true
          this.msg = res.content
        } else {
          this.dialogVisible = true
          this.msg = res.message
        }
      })
    },
    selectChangeEvent(val) {
      console.log(val.records)
    }
  }
}
</script>
<style scoped>
.header{
  margin: 5px;
}
</style>
