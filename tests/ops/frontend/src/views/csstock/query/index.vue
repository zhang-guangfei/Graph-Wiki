<template>
  <div class="app-container">
    <el-tabs v-model="activeTabName" type="card">
      <!--收货查询-->
      <el-tab-pane label="入库查询" name="pane1">
        <div>
          <el-row>
            <el-form
              ref="impQueryForm"
              :inline="true"
              :model="impQueryForm"
              size="mini"
            >
              <el-form-item label="客户代码">
                <el-input
                  v-model="impQueryForm.agentNo"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="客户代码"
                />
              </el-form-item>

              <el-form-item label="仓库代码">
                <!-- <el-input
                  v-model="impQueryForm.warehouseCode"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="仓库代码"
                /> -->
                <el-autocomplete
                  v-model.trim="impQueryForm.warehouseCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  :popper-append-to-body="false"
                  popper-class="el-autocomplete-suggestion"
                  highlight-first-item
                  style="width: 120px"
                  type="text"
                  size="mini"
                  placeholder="请输入库房代码"
                  class="select"
                  clearable
                  @clear="clearAgentInput(1)"
                  @select="handleSelect">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete>
                <el-button type="primary" size="mini" @click="selectWarehouse(3)">选择</el-button>
              </el-form-item>

              <el-form-item label="订单号">
                <el-input
                  v-model="impQueryForm.orderNo"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="订单号"
                />
              </el-form-item>
              <el-form-item label="型号">
                <el-input
                  v-model="impQueryForm.modelNo"
                  style="width: 140px"
                  clearable
                  size="mini"
                  placeholder="型号"
                />
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="impQueryForm.status" placeholder="状态" style="width: 100px" size="mini" clearable>
                  <el-option
                    v-for="item in statusData"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="impQueryForm.dateType" placeholder="请选择" style="width: 100px" size="mini" clearable>
                  <el-option
                    v-for="item in dateTypeData"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
                <el-date-picker
                  v-model="impTime"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 250px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"/>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['990567']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handlerQueryListImpData"
                >查询</el-button
                >
              </el-form-item>
              <el-form-item>
                <el-button
                  :loading="exportLoading"
                  type="primary"
                  icon="el-icon-download"
                  size="mini"
                  @click="exportImpData"
                >导出</el-button
                >
              </el-form-item>

            </el-form>
          </el-row>
          <el-divider />
          <el-table
            v-loading="listLoading"
            ref="multipleTable"
            :data="impList"
            tooltip-effect="dark"
            border
            size="mini"
            style="width: 100%"
            element-loading-text="正在加载中..."
            element-loading-spinner="el-icon-loading"
            stripe
          >
            <!-- 表头字段 -->
            <el-table-column fixed prop="agentNo" label="客户代码" />
            <el-table-column :formatter="warehouseNameformatter" :show-overflow-tooltip="true" fixed prop="stockCode" label="归属库房"/>
            <el-table-column :show-overflow-tooltip="true" prop="orderNo" label="订单号"/>
            <el-table-column :show-overflow-tooltip="true" prop="modelNo" label="型号"/>
            <el-table-column prop="quantity" label="数量" />
            <el-table-column :formatter="getApplyType" prop="applyType" label="申请类型" />
            <!-- <el-table-column prop="returnQty" label="退货数" />
            <el-table-column prop="expQty" label="出库数" /> -->
            <el-table-column :show-overflow-tooltip="true" prop="barcode" label="条码"/>
            <el-table-column :show-overflow-tooltip="true" prop="caseNo" label="箱号"/>
            <el-table-column :show-overflow-tooltip="true" prop="deliveryNo" label="出货单号" />
            <el-table-column :show-overflow-tooltip="true" prop="userNo" label="用户代码"/>
            <el-table-column :show-overflow-tooltip="true" prop="pplNo" label="PPL"/>
            <el-table-column :show-overflow-tooltip="true" prop="projectNo" label="项目号"/>
            <el-table-column label="入库时间" >
              <template slot-scope="scope">
                <span > {{ momentformatter( scope.row.receiveTime ) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="发货日期" >
              <template slot-scope="scope">
                <span > {{ momentformatter( scope.row.shipDate ) }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="getStatusDta" prop="status" label="状态" />
            <el-table-column prop="createUser" label="创建人" />
            <el-table-column :formatter="createTimeformatter" prop="createTime" label="创建时间" />
            <el-table-column :formatter="updateTimeformatter" prop="updateTime" label="更新时间" />

          </el-table>
          <pagination
            v-show="impTotal > 10"
            :total="impTotal"
            :page.sync="impQueryForm.pageNum"
            :limit.sync="impQueryForm.pageSize"
            :page-sizes="[20, 50, 100, 200, 500]"
            @pagination="handlerQueryListImpData()"
          />

        </div>

        <el-divider />
      </el-tab-pane>
      <!--出荷查询-->
      <el-tab-pane label="出库查询" name="pane2">
        <div>
          <el-row>
            <el-form
              ref="expQueryForm"
              :inline="true"
              :model="expQueryForm"
              size="mini"
            >
              <el-form-item label="客户代码">
                <el-input
                  v-model="expQueryForm.agentNo"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="客户代码"
                />
              </el-form-item>
              <el-form-item label="出荷单号">
                <el-input
                  v-model="expQueryForm.expOrderNo"
                  style="width: 140px"
                  clearable
                  size="mini"
                  placeholder="出荷单号"
                />
              </el-form-item>
              <el-form-item label="型号">
                <el-input
                  v-model="expQueryForm.modelNo"
                  style="width: 130px"
                  clearable
                  size="mini"
                  placeholder="型号"
                />
              </el-form-item>
              <el-form-item label="仓库代码">
                <!-- <el-input
                  v-model="expQueryForm.warehouseCode"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="仓库代码"
                /> -->
                <el-autocomplete
                  v-model.trim="expQueryForm.warehouseCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  :popper-append-to-body="false"
                  popper-class="el-autocomplete-suggestion"
                  highlight-first-item
                  style="width: 120px"
                  type="text"
                  size="mini"
                  placeholder="请输入库房代码"
                  class="select"
                  clearable
                  @clear="clearAgentInput(2)"
                  @select="handleSelect2">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete>
                <el-button type="primary" size="mini" @click="selectWarehouse(1)">选择</el-button>
              </el-form-item>
              <el-form-item label="状态">
                <el-select v-model="expQueryForm.status" placeholder="状态" style="width: 100px" size="mini" clearable>
                  <el-option
                    v-for="item in orderStatusData"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="expQueryForm.dateType" placeholder="请选择" style="width: 100px" size="mini" clearable>
                  <el-option
                    v-for="item in dateTypeData2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
                <el-date-picker
                  v-model="expTime"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 250px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"/>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['990567']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handlerQueryListExpData"
                >查询</el-button
                >
              </el-form-item>
              <el-form-item>
                <el-button
                  :loading="exportLoading"
                  type="primary"
                  icon="el-icon-download"
                  size="mini"
                  @click="exportExpDetailData"
                >导出</el-button
                >
              </el-form-item>

            </el-form>
          </el-row>
          <el-divider />

        </div>

        <el-table
          v-loading="listLoading"
          ref="multipleTable"
          :data="expList"
          tooltip-effect="dark"
          border
          size="mini"
          style="width: 100%"
          element-loading-text="正在加载中..."
          element-loading-spinner="el-icon-loading"
        >
          <!-- 表头字段 -->
          <el-table-column fixed prop="agentNo" label="客户代码" />
          <el-table-column fixed prop="userNo" label="用户代码" />
          <el-table-column :show-overflow-tooltip="true" :formatter="warehouseNameformatter" fixed prop="stockCode" label="归属库房"/>
          <!-- <el-table-column :show-overflow-tooltip="true" prop="inOrderNo" label="入库单号"/> -->
          <el-table-column :show-overflow-tooltip="true" prop="expOrderNo" label="出荷单号"/>
          <el-table-column prop="modelNo" label="型号" />
          <el-table-column prop="expQty" label="数量" />
          <el-table-column prop="price" label="单价" />
          <el-table-column prop="pplNo" label="PPL" />
          <el-table-column prop="projectNo" label="项目号" />
          <el-table-column label="出库时间" align="center" >
            <template slot-scope="scope">
              <span > {{ momentformatter( scope.row.expTime ) }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="getOrderStatusDta" prop="status" label="状态" />
          <el-table-column prop="createUser" label="创建人" />
          <el-table-column :formatter="createTimeformatter" prop="createTime" label="创建时间" />
          <el-table-column :formatter="updateTimeformatter" prop="updateTime" label="更新时间" />

        </el-table>
        <pagination
          v-show="expTotal > 10"
          :total="expTotal"
          :page.sync="expQueryForm.pageNum"
          :limit.sync="expQueryForm.pageSize"
          :page-sizes="[20, 50, 100, 200, 500]"
          @pagination="handlerQueryListExpData()"
        />
      </el-tab-pane>
      <!-- 仓库选择弹窗 -->
      <el-dialog :visible.sync="dialogFormVisible" title="仓库" width="550px">
        <el-form ref="form" :inline="true" :model="warehouseForm" size="small">
          <el-form-item >
            <el-input v-model="warehouseForm.warehouseCode" placeholder="仓库代码" style="width:100px" clearable @clear="listWarehouseinfo"/>
          </el-form-item>
          <el-form-item >
            <el-input v-model="warehouseForm.warehouseName" placeholder="仓库名称" clearable @clear="listWarehouseinfo"/>
          </el-form-item>
          <!-- <el-form-item >
            <el-select v-model="warehouseForm.warehouseType" placeholder="仓库类别" style="width:100px" clearable @change="listWarehouseinfo">
              <el-option v-for="item in warehousetypeList" :key="item.code" :label="item.codeName" :value="item.code" clearable/>
            </el-select>
          </el-form-item> -->
          <el-form-item>
            <el-button icon="el-icon-search" size="small" @click="listWarehouseinfo"/>
          </el-form-item>
        </el-form>
        <el-table :data="warehouseData.filter(data => !warehouseForm.warehouseName || data.warehouseName.includes(warehouseForm.warehouseName))">
          <el-table-column property="warehouseCode" label="仓库代码" width="100px"/>
          <el-table-column property="warehouseName" label="仓库名称" width="250px"/>
          <!-- <el-table-column :formatter="getWarehouseType" property="warehouseType" label="仓库类别" width="100px"/> -->
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="edit(scope.row)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

      <el-dialog :visible.sync="balanceDialog" title="月结" width="650px">
        <el-form ref="form" :model="form">
          <el-table
            :data="balanceDateData"
            stripe
            size="mini"
            style="width: 100%;">
            <el-table-column :formatter="balanceDateFormatter3" property="monthDate" label="月份" width="100px"/>
            <el-table-column
              prop="fromTime"
              label="月结开始时间"
              width="150">
              <template slot-scope="scope">
                <el-form-item v-show="scope.row.status==1" :prop="'balanceDateData.' + scope.$index + '.fromTime'" label-width="0">
                  <el-date-picker
                    v-model="scope.row.fromTime"
                    type="date"
                    size="mini"
                    style="width: 140px"
                    placeholder="选择日期"/>
                </el-form-item>
                <span v-show="scope.row.status!==1">{{ balanceDateFormatter2(scope.row) }}</span>
              </template>
            </el-table-column>
            <el-table-column
              :formatter="balanceDateFormatter1"
              prop="toTime"
              label="月结结束时间"
              width="150">
              <template slot-scope="scope">
                <el-form-item v-show="scope.row.status==1" :prop="'balanceDateData.' + scope.$index + '.toTime'" label-width="0">
                  <el-date-picker
                    v-model="scope.row.toTime"
                    type="date"
                    size="mini"
                    style="width: 140px"
                    placeholder="选择日期"/>
                </el-form-item>
                <span v-show="scope.row.status!==1">{{ balanceDateFormatter1(scope.row) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button v-if="scope.row.status == 1" type="primary" size="mini" @click="setBalanceDate(scope.row)">设置</el-button>
                <el-button v-if="scope.row.status == 1" type="primary" size="mini" @click="editCsBalance(scope.row)">确认</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-form>
      </el-dialog>
      <!--月结查询-->
      <el-tab-pane label="月结查询" name="pane3">
        <div>
          <el-row>
            <el-form
              ref="balQueryForm"
              :inline="true"
              :model="balQueryForm"
              size="mini"
              label-width="80px"
            >
              <el-form-item>
                <el-button v-permission="['762099']" type="primary" size="mini" @click="balanceDialog = true;getCalcbanceEndDate()">月结设置</el-button>
              </el-form-item>
              <el-form-item>
                <el-button v-permission="['762099']" :loading="balanceLoading" type="primary" icon="el-icon-search" size="mini" @click="csBalance">月结计算</el-button>
              </el-form-item>
              <el-form-item label="客户代码">
                <el-input
                  v-model="balQueryForm.agentNo"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="客户代码"
                />
              </el-form-item>
              <el-form-item label="型号">
                <el-input
                  v-model="balQueryForm.modelNo"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="型号"
                />
              </el-form-item>
              <el-form-item label="库房代码">
                <!-- <el-input
                  v-model="balQueryForm.warehouseCode"
                  style="width: 100px"
                  clearable
                  size="mini"
                  placeholder="库房代码"
                /> -->
                <el-autocomplete
                  v-model.trim="balQueryForm.warehouseCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  :popper-append-to-body="false"
                  popper-class="el-autocomplete-suggestion"
                  highlight-first-item
                  style="width: 120px"
                  type="text"
                  size="mini"
                  placeholder="请输入库房代码"
                  class="select"
                  clearable
                  @clear="clearAgentInput(3)"
                  @select="handleSelect3">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete>
                <el-button type="primary" size="mini" @click="selectWarehouse(2)">选择</el-button>
              </el-form-item>
              <el-form-item label="月份">
                <el-date-picker
                  v-model="balQueryForm.monthDate"
                  type="month"
                  placeholder="选择月份"
                  style="width: 130px"/>
                  <!-- <el-input
                  v-model="balQueryForm.monthDate"
                  style="width: 130px"
                  clearable
                  size="mini"
                  placeholder="月份"
                /> -->
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['990567']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handlerQueryListBalData"
                >查询</el-button
                >
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['762099']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handlePrintBalance"
                >打印</el-button
                >
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['762099']"
                  :loading="exportLoading"
                  type="primary"
                  icon="el-icon-download"
                  size="mini"
                  @click="exportBalance"
                >导出</el-button
                >
              </el-form-item>

            </el-form>
          </el-row>
          <el-row>
            <el-checkbox v-permission="['762099']" v-model="checked" label="财务结算" border size="mini" @change="costChange"/>
          </el-row>
          <el-divider />
        </div>
        <el-table
          v-loading="listLoading"
          ref="multipleTable"
          :data="balList"
          tooltip-effect="dark"
          border
          size="mini"
          style="width: 100%"
          element-loading-text="正在加载中..."
          element-loading-spinner="el-icon-loading"
        >
          <!-- 表头字段 -->
          <el-table-column fixed prop="agentNo" label="客户代码" />
          <el-table-column :show-overflow-tooltip="true" :formatter="warehouseNameformatter" fixed prop="stockCode" label="归属库房"/>
          <el-table-column :formatter="dateformatter" :show-overflow-tooltip="true" prop="monthDate" label="年月"/>
          <el-table-column :show-overflow-tooltip="true" prop="modelNo" label="型号"/>
          <el-table-column v-if="!costShow" :show-overflow-tooltip="true" prop="openingQty" label="期初数量"/>
          <el-table-column v-if="!costShow" :show-overflow-tooltip="true" prop="inQty" label="入库数"/>
          <el-table-column v-if="!costShow" :show-overflow-tooltip="true" prop="outQty" label="出荷数"/>
          <el-table-column v-if="!costShow" :show-overflow-tooltip="true" prop="returnQty" label="退货数"/>
          <el-table-column v-if="!costShow" :show-overflow-tooltip="true" prop="balanceQty" label="期末数量"/>
          <el-table-column v-if="!costShow" :show-overflow-tooltip="true" prop="onhandQty" label="库存数"/>
          <el-table-column v-if="!costShow" :show-overflow-tooltip="true" prop="remark" label="备注"/>
          <el-table-column v-if="costShow" :show-overflow-tooltip="true" prop="qtyOpeningCost" label="财务期初数量"/>
          <el-table-column v-if="costShow" :show-overflow-tooltip="true" prop="qtyShip" label="发货数量"/>
          <el-table-column v-if="costShow" :show-overflow-tooltip="true" prop="qtyInvoice" label="销售数量"/>
          <el-table-column v-if="costShow" :show-overflow-tooltip="true" prop="qtyBalanceCost" label="财务期末数量"/>
          <el-table-column key="status" :formatter="statusFormatter" prop="status" label="状态"/>
          <el-table-column key="confirmTime" min-width="110px" prop="confirmTime" label="确认时间"/>
          <el-table-column key="confirmUser" prop="confirmUser" label="确认人"/>
          <el-table-column v-if="!costShow" prop="createUser" label="创建人" />
          <el-table-column v-if="!costShow" :formatter="createTimeformatter" prop="createTime" label="创建时间" />
          <el-table-column v-if="!costShow" :formatter="updateTimeformatter" prop="updateTime" label="更新时间" />

        </el-table>
        <pagination
          v-show="balTotal > 10"
          :total="balTotal"
          :page.sync="balQueryForm.page.pageNumber"
          :limit.sync="balQueryForm.page.pageSize"
          :page-sizes="[20, 50, 100, 200, 500]"
          @pagination="handlerQueryListBalData()"
        />
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
import Pagination from '@/components/Pagination'
import { listImpData, listExpData, listBalData, printBalance, calcBalance, exportCsBalanceData, getCalcbanceDate, updateBalanceDate, updateMothDate, exportCsImpData, exportCsExpData } from '@/api/stock/csQuery.js'
import { listWarehourseStockCode } from '@/api/stock/csStockApply.js'
import { getDictDataByPid, listWarehouse } from '@/api/common/dict'

export default {
  name: 'CSStockSearch',
  components: { Pagination },
  data() {
    return {
      activeTabName: 'pane1',
      dialogdetialVisible: false,
      dialogFormVisible: false,
      balanceLoading: false,
      exportLoading: false,
      costShow: false,
      checked: false,
      balanceDialog: false,
      warehouseForm: {
        warehouseCode: '',
        warehouseName: '',
        warehouseType: ''
      },
      warehousetypeList: [],
      warehouseData: [],
      // 表格数据
      impList: [],
      impTime: '',
      expTime: '',
      balaceDefaultTime: [],
      impQueryForm: {
        agentNo: '',
        beginDate: '',
        endDate: '',
        dateType: 2,
        warehouseCode: '',
        status: '',
        orderNo: '',
        modelNo: '',
        pageNum: 0,
        pageSize: 20
      },
      dateTypeData: [
        { value: 1, label: '发货日期' },
        { value: 2, label: '入库日期' }
      ],
      dateTypeData2: [
        { value: 1, label: '创建时间' },
        { value: 2, label: '出库时间' }
      ],
      expList: [],
      expQueryForm: {
        dateType: 2,
        beginDate: '',
        endDate: '',
        expOrderNo: '',
        warehouseCode: '',
        status: '',
        agentNo: '',
        pageNum: 0,
        pageSize: 20
      },
      // 月结查询
      balQueryForm: {
        agentNo: '',
        warehouseCode: '',
        modelNo: '',
        monthDate: '', // 按月
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      balanceStartDate: '',
      balanceDateData: [],
      balanceEndDate: '',
      balanceForm: {
        startTime: '',
        endTime: ''
      },
      balanceDate: '',
      impTotal: 0,
      expTotal: 0,
      balTotal: 0, // 月结
      wareType: 0,
      listLoading: false,
      stockCodeOptions: [],
      deptInfos: [], // 营业所部门信息
      // 寄售申请状态
      applyTypeClassCode: '2026',
      applyTypeCode: '2020',
      statusClassCode: '2055',
      orderStatusCodee: '2056',
      statusData: [],
      orderStatusData: [],
      applyTypeData: [],
      baseInfoVisible: true
    }
  },
  created() {
    this.initstatusdesc()
    // this.initstDeptInfo()
    this.listWarehouseinfo()
  },
  mounted() {
    this.getCalcbanceEndDate()
  },
  methods: {
    // 申请状态初始化
    initstatusdesc() {
      getDictDataByPid(this.applyTypeClassCode).then(result => {
        console.log(result)
        this.applyStatusDesc = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.orderStatusCodee).then(result => {
        console.log(result)
        this.orderStatusData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.statusClassCode).then(result => {
        this.statusData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.applyTypeCode).then(result => {
        this.applyTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('4001').then(result => {
        this.warehousetypeList = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    querySearchStockCodeAsync(warehouseCode, cb) {
      const warehouseCodeParams = { 'warehouseCode': warehouseCode, 'warehouseType': 'WT' }
      listWarehourseStockCode(warehouseCodeParams).then(result => {
        console.log(result)
        this.stockCodeOptions = result.content
        cb(result.content)
      }).catch(error => {
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.impTime === null) {
        this.impQueryForm.beginDate = ''
        this.impQueryForm.endDate = ''
      } else {
        this.impQueryForm.beginDate = this.impTime[0]
        this.impQueryForm.endDate = this.impTime[1]
      }
      if (this.expTime === null) {
        this.expQueryForm.beginDate = ''
        this.expQueryForm.endDate = ''
      } else {
        this.expQueryForm.beginDate = this.expTime[0]
        this.expQueryForm.endDate = this.expTime[1]
      }
    },
    exportBalance() {
      this.exportLoading = true
      this.$message.success('开始导出，请耐心等待')
      exportCsBalanceData(this.balQueryForm).then(res => {
        const fileName = '月结详细.xlsx'
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
        console.log(error)
        this.exportLoading = false
      })
    },
    exportImpData() {
      this.exportLoading = true
      this.$message.success('开始导出，请耐心等待')
      exportCsImpData(this.impQueryForm).then(res => {
        const fileName = '服务备库入库.xlsx'
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
        console.log(error)
        this.exportLoading = false
      })
    },
    exportExpDetailData() {
      this.exportLoading = true
      this.$message.success('开始导出，请耐心等待')
      exportCsExpData(this.expQueryForm).then(res => {
        const fileName = '服务备库出库.xlsx'
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
        console.log(error)
        this.exportLoading = false
      })
    },
    handlerQueryListImpData() {
      this.setDateCondition()
      this.listLoading = true
      listImpData(this.impQueryForm).then(result => {
        this.impList = result.content.list
        this.impTotal = result.content.total
        console.log('返回：' + result.list)
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    getCalcbanceEndDate() {
      getCalcbanceDate().then(res => {
        this.balanceDateData = res.content
        console.log(this.balanceDateData)
      })
    },
    editCsBalance(row) {
      this.$confirm('确认后月结不可重新计算, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updateBalanceDate(row.id).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.getCalcbanceEndDate()
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    csBalance() {
      if (this.balQueryForm.monthDate === '' || this.balQueryForm.monthDate === null) {
        this.$message.warning('请选择月结月份')
        return
      }
      // const startDate = this.dayjs(this.balanceStartDate).format('YYYY/MM/DD HH:mm')
      // const endDate = this.dayjs(this.balanceEndDate).format('YYYY/MM/DD HH:mm')
      this.balanceLoading = true
      const monthDate = this.dayjs(this.balQueryForm.monthDate).format('YYYY/MM/DD')
      calcBalance(monthDate).then(res => {
        if (res.success) {
          this.$message.success('计算完成')
        } else {
          this.$message.error(res.message)
        }
        this.balanceLoading = false
      }).catch(error => {
        this.balanceLoading = false
        console.log(error)
      })
    },
    // 查询出库清单
    handlerQueryListExpData() {
      this.setDateCondition()
      this.listLoading = true
      listExpData(this.expQueryForm).then(result => {
        console.log(result)
        this.expList = result.content.list
        this.expTotal = result.content.total
        console.log('返回：' + result.content)
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 查询月结清单
    handlerQueryListBalData() {
      this.listLoading = true
      if (this.balQueryForm.monthDate !== null && this.balQueryForm.monthDate !== '') {
        this.balQueryForm.monthDate = this.dayjs(this.balQueryForm.monthDate).format('YYYY-MM-DD')
      }
      listBalData(this.balQueryForm).then(result => {
        console.log(result)
        this.balList = result.content.list
        this.balTotal = result.content.total
        console.log('返回：' + result.content)
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    clearAgentInput(val) {
      if (val === 1) {
        this.impQueryForm.agentNo = ''
        this.impQueryForm.warehouseCode = ''
      }
      if (val === 2) {
        this.expQueryForm.agentNo = ''
        this.expQueryForm.warehouseCode = ''
      }
      if (val === 3) {
        this.balQueryForm.agentNo = ''
        this.balQueryForm.warehouseCode = ''
      }
    },
    handleSelection(val) {
      // this.multipleSelection = val
    },
    handleSelect(item) {
      this.impQueryForm.agentNo = item.customerNo
      this.impQueryForm.warehouseCode = item.warehouseCode
    },
    handleSelect2(item) {
      this.expQueryForm.agentNo = item.customerNo
      this.expQueryForm.warehouseCode = item.warehouseCode
    },
    handleSelect3(item) {
      this.balQueryForm.agentNo = item.customerNo
      this.balQueryForm.warehouseCode = item.warehouseCode
    },
    costChange() {
      this.costShow = !this.costShow
    },
    selectWarehouse(val) {
      this.wareType = val
      this.dialogFormVisible = true
      this.listWarehouseinfo()
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: 'WT',
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    setBalanceDate(row) {
      updateMothDate(row).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    edit(row) {
      if (this.wareType === 2) {
        this.balQueryForm.warehouseCode = row.warehouseCode
      }
      if (this.wareType === 1) {
        this.expQueryForm.warehouseCode = row.warehouseCode
      }
      if (this.wareType === 3) {
        this.impQueryForm.warehouseCode = row.warehouseCode
      }
      this.dialogFormVisible = false
    },
    warehouseNameformatter(row) {
      return this.descFormatter(this.stockCodeOptions, row.warehouseCode)
    },
    statusFormatter(row) {
      if (row.status == null) {
        return null
      }
      if (row.status === 1) {
        return '未结'
      }
      if (row.status === 2) {
        return '已结'
      }
    },
    descFormatter(data, id) {
      if (data === null) {
        return id
      }
      if (id === null) {
        return id
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === id.toString()) {
          return item.codeName
        }
      }
      return id
    },
    balanceDateFormatter1(row) {
      if (row.fromTime != null) {
        return this.dayjs(row.toTime).format('YYYY-MM-DD HH:mm')
      }
    },
    balanceDateFormatter2(row) {
      if (row.fromTime != null) {
        return this.dayjs(row.fromTime).format('YYYY-MM-DD HH:mm')
      }
    },
    balanceDateFormatter3(row) {
      if (row.fromTime != null) {
        return this.dayjs(row.toTime).format('YYYY-MM')
      }
    },
    updateTimeformatter(row) {
      if (row.updateTime != null) {
        return this.dayjs(row.updateTime).format('YYYY-MM-DD HH:mm')
      }
    },
    createTimeformatter(row) {
      if (row.createTime != null) {
        return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
      }
    },
    applyTimeformatter(row) {
      if (row.applyTime != null) {
        return this.dayjs(row.applyTime).format('YYYY-MM-DD HH:mm')
      }
    },
    dateformatter(row) {
      if (row.monthDate != null) {
        return this.dayjs(row.monthDate).format('YYYY-MM')
      }
    },
    momentformatter(date) { // 日期格式化
      if (date != null) {
        return this.dayjs(date).format('YYYY-MM-DD')
      }
      return ''
    },
    monthDateformatter(date) {
      if (date != null) {
        return this.dayjs(date).format('YYYY-MM-DD')
      }
      return ''
    },
    applyStatusformatter(row) {
      if (row != null) {
        return this.descFormatter(this.applyStatusDesc, row.status)
      }
    },
    applyStatusDec(status) {
      if (status != null) {
        return this.descFormatter(this.applyStatusDesc, status)
      }
    },
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    getApplyType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.applyTypeData, cellValue)
    },
    getStatusDta(row, column, cellValue, index, menu) {
      return this.descFormatter(this.statusData, cellValue)
    },
    getOrderStatusDta(row, column, cellValue, index, menu) {
      return this.descFormatter(this.orderStatusData, cellValue)
    },
    // 打印月次报告
    handlePrintBalance() {
      printBalance(this.balQueryForm).then(result => {
        if (this.balQueryForm == null || this.balQueryForm.agentNo === '' || this.balQueryForm.warehouseCode === '' || this.balQueryForm.monthDate === '') {
          this.$message.error('请输入客户代码、库房代码以及月份')
          return
        }
        const fileName = result.type
        const blob = new Blob([result], { type: result.type })
        this.printFile(blob, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    printFile(blob, fileName) {
      if (window.navigator.msSaveOrOpenBlob) { // 适配IE10
        navigator.msSaveBlob(blob, fileName)
      } else {
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        window.open(link.href, '_blank')
      }
    }
  }
}
</script>
<style scoped>
.line {
  text-align: center;
  margin-left: 5%;
}
.select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
  }
</style>
