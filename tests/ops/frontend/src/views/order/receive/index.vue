<template>
  <div class="app-container">
    <el-card class="ecard" >
      <el-form ref="form" :inline="true" :model="form" size="small">
        <el-form-item label="" class="formitem">
          <!-- <el-select v-model="orderReceiveForm.tradecompanyId" clearable placeholder="请选择公司" style="width:120px" size="small" @change="selectDept">
            <el-option
              v-for="item in companyOptions"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select> -->
          <el-cascader
            :options="companyOptions"
            :props="{ value: 'id', label: 'name', checkStrictly: true }"
            placeholder="请选择：可搜索"
            filterable
            clearable
            size="mini"
            @change="handleChange" />
        </el-form-item>
        <el-form-item class="formitem">
          <el-card class="formcard" >
            待接订单<br>
            <el-form-item label="">
              <el-link :underline="true">{{ waitRcvOrder }}</el-link>
            </el-form-item>
          </el-card>
        </el-form-item>
        <el-form-item class="formitem">
          <el-card class="formcard">
            处理中订单<br>
            <el-form-item label="">
              <el-link>{{ handlingOrder }}</el-link>
            </el-form-item>
          </el-card>
        </el-form-item>
        <el-form-item class="formitem">
          <el-card class="formcard">
            暂不处理 <br>
            <el-form-item label="">
              <el-link>{{ waitHandleOrder }}</el-link>
            </el-form-item>
          </el-card>
        </el-form-item>
        <el-form-item class="formitem">
          <el-card class="formcard">
            异常处理 <br>
            <el-form-item label="">
              <el-link>{{ exceptionHandCount }}</el-link>
            </el-form-item>
          </el-card>
        </el-form-item>
        <el-form-item>
          <el-button size="small" type="primary" style="width:100px" @click="updateDataCount">更新({{ updatetime }})</el-button>
        </el-form-item>
      </el-form>
    </el-card>
    <el-dialog :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
      <product-search v-if="productVisible" ref="ProductSearch"/>
    </el-dialog>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="订单接入" name="first">
        <el-form :inline="true" :model="orderReceiveForm" class="demo-form-inline">
          <!-- <el-form-item label="公司">
            <el-select v-model="orderReceiveForm.tradecompanyId" placeholder="请选择" size="mini" clearable class="elsecect">
              <el-option
                v-for="item in options"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item> -->
          <el-form-item label="订单类别" >
            <el-select v-model="orderReceiveForm.typeCode" clearable placeholder="请选择" size="mini" class="elsecect" @change="orderTypeChange(orderReceiveForm.typeCode)">
              <el-option
                v-for="item in typeCodeOptions"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"/>
            </el-select>
          </el-form-item>
          <el-form-item >
            <el-input v-model="orderReceiveForm.fullOrderNo" size="small" clearable style="width:140px" placeholder="请输订单号"/>
          </el-form-item>
          <el-button size="small" class="searchBtu" plain @click="selectRcvOrder()">查看</el-button>
          <!-- <el-button :loading="loadingButton" size="small" class="flashBtu" plain @click="refresh()">刷新</el-button> -->
          <el-button :loading="isRcvOrder" size="small" class="recceiveBtu" plain @click="rcvOrder()">接收</el-button>
          <el-switch
            v-show="hasRole"
            v-model="isAutoRcvOrder"
            active-color="#13ce66"
            active-text="启动接入订单"
            inactive-color="#ff4949"
            class="elswitch"
            @change="handdle"/>
        </el-form>
        <el-table
          :data="orcvTableListData"
          border
          stripe
          size="mini"
          height="500"
          style="width: 100%"
          @selection-change="handleSelectionChangeRcvOrder">
          <el-table-column
            type="selection"
            width="45"/>
          <el-table-column
            :formatter="deptNoFormatter"
            prop="deptNo"
            label="营业所名称"
            align="center"
            width="90"/>
          <el-table-column
            prop="fullOrderNo"
            label="订单号"
            align="center"
            width="130"
            show-overflow-tooltip/>
          <el-table-column
            prop="modelNo"
            label="型号"
            align="center"
            width="120"
            show-overflow-tooltip/>
          <el-table-column
            prop="quantity"
            label="数量"
            width="70"
            align="center"/>
          <el-table-column
            prop="price"
            label="单价"
            align="center"
            width="80"/>
          <!-- <el-table-column
            :formatter="companyFormatter"
            prop="tradeCompanyId"
            label="交易主体"
            width="90"
            align="center"/> -->
          <el-table-column
            prop="customerNo"
            label="客户代码"
            width="110"
            align="center"/>
          <el-table-column
            prop="userNo"
            label="用户代码"
            width="90"
            align="center"/>
          <el-table-column
            :formatter="dateFormatterForCreateTime"
            prop="createTime"
            label="接入日期"
            width="120"
            align="center"
            show-overflow-tooltip/>
          <!-- <el-table-column
            :formatter="dateFormatterForWorkDay"
            prop="workday"
            label="下单日期"
            width="110"
            align="center"
            show-overflow-tooltip/> -->
          <el-table-column
            :formatter="dateFormatterForDlvDay"
            prop="dlvDate"
            label="客户交货期"
            width="110"
            align="center"
            show-overflow-tooltip/>
          <!-- <el-table-column
            prop="stockCode"
            label="是否组装"
            width="90"
            align="center"
            show-overflow-tooltip/> -->
          <el-table-column
            :formatter="typeCodeFormatter"
            prop="typeCode"
            label="订单类别"
            width="130"
            align="center"
            show-overflow-tooltip/>
          <el-table-column
            prop="expDlvTypeString"
            label="特殊标识"
            width="70"
            align="center"
            show-overflow-tooltip/>
          <el-table-column
            prop="remark"
            label="备注"
            width="300"
            align="center"
            show-overflow-tooltip/>
        </el-table>
        <pagination
          v-show="total >= 3"
          :total="total"
          :page-sizes="[20, 50, 100, 200, 500]"
          :page.sync="page.pageNumber"
          :limit.sync="page.pageSize"
          @pagination="queryOrderSalesList()"/>
      </el-tab-pane>
      <el-tab-pane label="接单处理" name="second">
        <div>
          <el-form ref="orderReceiveForm" :inline="true" :model="orderReceiveForm" size="small" label-width="115px" @submit.native.prevent>
            <el-form-item label="查询订单号">
              <el-input v-model="orderReceiveForm.rorderFno" size="small" clearable style="width:140px" placeholder="请输订单号" @keyup.enter.native="refreshRcvOrderHand(1)"/>
            </el-form-item>
            <el-switch
              v-model="isAutoOrderHandler"
              :active-text="'启动接单处理'+orderCount"
              active-color="#13ce66"
              inactive-color="#ff4949"
              class="elswitch"
              style="margin-top:12px"
              @change="isStartOrderProcess('other')"/>
            <!-- <el-switch
              v-model="isAutoOrderHandlerodd"
              active-color="#13ce66"
              active-text="启动接单处理1"
              inactive-color="#ff4949"
              class="elswitch"
              style="margin-top:12px"
              @change="isStartOrderProcess('odd')"/>
            <el-switch
              v-model="isAutoOrderHandlereven"
              active-color="#13ce66"
              active-text="启动接单处理2"
              inactive-color="#ff4949"
              class="elswitch"
              style="margin-top:12px"
              @change="isStartOrderProcess('even')"/> -->
            <el-switch
              v-model="isAutoOrderOneDayHandler"
              active-color="#13ce66"
              active-text="日次接单处理"
              inactive-color="#ff4949"
              class="elswitch"
              style="margin-top:12px"
              @change="isStartOrderOneDayProcess"/>
            <el-form-item style="margin-left: 50px">
              <el-button size="mini" type="primary" @click="refreshRcvOrderHand()">刷新</el-button>
            </el-form-item>
            <el-form-item>
              <el-button :loading="autoRcvOrderHand" size="mini" type="primary" @click="autoRcvOrderHanding">自动接单处理</el-button>
              <!-- <el-button size="small" type="primary" @click="modifyDlvEntire">修改通知发货</el-button> -->
            </el-form-item>
            <!-- <el-form-item class="formitem">
              <el-button size="small" type="primary" @click="findList">出在库</el-button>
            </el-form-item>
            <el-form-item class="formitem">
              <el-button size="small" type="primary" @click="findList">拆分型号</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="small" type="primary" style="margin-right: 1%" @click="findList">还原处理</el-button>
            </el-form-item>
            <el-form-item>
              <el-button size="small" type="primary" @click="findList">暂不处理</el-button>
            </el-form-item> -->
          </el-form>
        </div>
        <div style="width:100%;padding-top:0px;float:left;">
          <el-table
            ref="roleTable"
            :data="orderlist"
            element-loading-text="Loading"
            size="mini"
            border
            stripe
            max-height="500"
            @selection-change="handleSelectionChange">
            <el-table-column
              text="选择"
              type="selection"
              width="40"
              align="center"/>
            <el-table-column
              :formatter="deptNoFormatter"
              prop="deptNo"
              label="营业所名称"
              min-width="80%"
              align="center"/>
            <el-table-column label="订单号" min-width="80%" align="center">
              <template slot-scope="scope">
                {{ scope.row.rorderFno }}
              </template>
            </el-table-column>
            <el-table-column label="型号" min-width="80%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-link :underline="false" @click="modelNoClick(scope.row)">{{ scope.row.modelNo }}</el-link>
              </template>
            </el-table-column>
            <el-table-column label="数量" min-width="40%" align="center">
              <template slot-scope="scope">
                {{ scope.row.quantity }}
              </template>
            </el-table-column>
            <el-table-column label="单价" min-width="40%" align="center">
              <template slot-scope="scope">
                {{ scope.row.price }}
              </template>
            </el-table-column>
            <el-table-column label="发货方式" min-width="80%" align="center">
              <template slot-scope="scope">
                {{ dlvEntireFormatter(scope.row) }}
              </template>
            </el-table-column>
            <el-table-column label="客户代码" min-width="80%" align="center">
              <template slot-scope="scope">
                {{ scope.row.customerNo }}
              </template>
            </el-table-column>
            <el-table-column label="用户代码" min-width="60%" align="center">
              <template slot-scope="scope">
                {{ scope.row.userNo }}
              </template>
            </el-table-column>
            <el-table-column label="接入日期" min-width="88%" align="center">
              <template slot-scope="scope">
                {{ scope.row.createTime }}
              </template>
            </el-table-column>
            <el-table-column label="客户交货期" min-width="80%" align="center">
              <template slot-scope="scope">
                {{ scope.row.dlvDate }}
              </template>
            </el-table-column>
            <el-table-column label="是否组装" min-width="60%" align="center">
              <template slot-scope="scope">
                {{ scope.row.prodFlag }}
              </template>
            </el-table-column>
            <el-table-column
              :formatter="orderTypeFormatter"
              prop="typeCode"
              label="订单类别"
              min-width="80%"
              align="center"
              show-overflow-tooltip/>
            <el-table-column label="特殊标识" min-width="60%" align="center">
              <template slot-scope="scope">
                {{ scope.row.expDlvTypeName }}
              </template>
            </el-table-column>
            <el-table-column label="备注" min-width="80%" align="center" show-overflow-tooltip >
              <template slot-scope="scope">
                {{ scope.row.remark }}
              </template>
            </el-table-column>
            <!-- <el-table-column label="已分配数量" min-width="65%" align="center">
              <template slot-scope="scope">
                {{ scope.row.allocatedQty }}
              </template>
            </el-table-column> -->
            <!-- <el-table-column label="部门代码" min-width="60%" align="center">
              <template slot-scope="scope">
                {{ scope.row.deptNo }}
              </template>
            </el-table-column> -->
            <!-- <el-table-column label="用户代码" min-width="80%" align="center">
              <template slot-scope="scope">
                {{ scope.row.userNo }}
              </template>
            </el-table-column> -->
            <!-- <el-table-column
              :formatter="companyFormatter"
              prop="tradeCompanyId"
              label="交易主体"
              min-width="80%"
              align="center"/>
            <el-table-column label="客户交货期" min-width="80%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.cDlvDate | formatDate }}
              </template>
            </el-table-column> -->
            <!-- <el-table-column label="库存" min-width="40%" align="center">
              <template slot-scope="scope">
                {{ scope.row.bjQty }}
              </template>
            </el-table-column> -->
          </el-table>
          <pagination
            v-show="total >= 3"
            :total="total"
            :page-sizes="[20, 50, 100, 200, 500]"
            :page.sync="page.pageNumber"
            :limit.sync="page.pageSize"
            @pagination="queryRcvOrderList()"/>
        </div>
      </el-tab-pane>
      <el-tab-pane label="暂不处理" name="three">
        <div style="width:100%;padding-top:0px;float:left;">
          <div>
            <el-form ref="orderReceiveForm" :inline="true" :model="orderReceiveForm" size="small" label-width="115px" @submit.native.prevent>
              <el-form-item label="查询订单号">
                <el-input v-model="orderReceiveForm.rorderFno" size="small" clearable style="width:140px" placeholder="请输订单号" @keyup.enter.native="reflushForNoHandOrder()"/>
              </el-form-item>
              <el-form-item style="margin-left: 50px">
                <el-button type="primary" size="mini" icon="el-icon-refresh" @click="reflushForNoHandOrder()">刷新</el-button>
              </el-form-item>
              <el-form-item>
                <el-button :loading="isHandBtu" type="primary" size="mini" icon="el-icon-d-arrow-left" @click="convertToProcessing">转为处理</el-button>
              </el-form-item>
            </el-form>
          </div>
          <el-table
            ref="waitRcvDataList"
            :data="waitRcvDataList"
            element-loading-text="Loading"
            stripe
            size="mini"
            max-height="500"
            border
            @selection-change="handleSelectionChange">
            <el-table-column
              text="选择"
              type="selection"
              width="40"
              align="center"/>
            <el-table-column
              :formatter="deptNoFormatter"
              prop="deptNo"
              label="营业所名称"
              min-width="40%"
              align="center"/>
            <el-table-column label="订单号" min-width="60%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.rorderFno }}
              </template>
            </el-table-column>
            <el-table-column label="型号" min-width="60%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-link :underline="false" @click="modelNoClick(scope.row)">{{ scope.row.modelNo }}</el-link>
              </template>
            </el-table-column>
            <el-table-column label="数量" min-width="40%" align="center">
              <template slot-scope="scope">
                {{ scope.row.quantity }}
              </template>
            </el-table-column>
            <el-table-column label="单价" min-width="40%" align="center">
              <template slot-scope="scope">
                {{ scope.row.price }}
              </template>
            </el-table-column>
            <el-table-column label="客户代码" min-width="50%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.customerNo }}
              </template>
            </el-table-column>
            <el-table-column label="用户代码" min-width="60" align="center">
              <template slot-scope="scope">
                {{ scope.row.userNo }}
              </template>
            </el-table-column>
            <el-table-column label="交货期" min-width="80%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.dlvDate | formatDate }}
              </template>
            </el-table-column>
            <el-table-column label="备注" min-width="80%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.remark }}
              </template>
            </el-table-column>
            <el-table-column label="子订单项" min-width="60%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.sunOrder }}
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total >= 3"
            :total="total"
            :page-sizes="[20, 50, 100, 200, 500]"
            :page.sync="page.pageNumber"
            :limit.sync="page.pageSize"
            @pagination="queryRcvWaitHandleOrder()"/>
        </div>
      </el-tab-pane>
      <el-tab-pane label="异常处理" name="four">
        <div style="width:100%;padding-top:0px;float:left;">
          <div>
            <el-form ref="orderReceiveForm" :inline="true" :model="orderReceiveForm" size="small" label-width="115px" @submit.native.prevent>
              <el-form-item label="查询订单号">
                <el-input v-model="orderReceiveForm.rorderFno" size="small" clearable style="width:140px" placeholder="请输订单号" @keyup.enter.native="reflushForExceptionHand()"/>
              </el-form-item>
              <el-form-item style="margin-left: 50px">
                <el-button type="primary" size="mini" icon="el-icon-refresh" @click="reflushForExceptionHand()">刷新</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="mini" icon="el-icon-d-arrow-left" @click="convertToProcessing">转为处理</el-button>
              </el-form-item>
            </el-form>
          </div>
          <el-table
            ref="exceptionHandList"
            :data="exceptionHandList"
            :row-style="{height: '0'}"
            :cell-style="{padding: '0'}"
            element-loading-text="Loading"
            stripe
            size="mini"
            max-height="500"
            border
            @selection-change="handleSelectionChange">
            <el-table-column
              text="选择"
              type="selection"
              width="40"
              align="center"/>
            <el-table-column
              :formatter="deptNoFormatter"
              prop="deptNo"
              label="营业所名称"
              min-width="40%"
              align="center"/>
            <el-table-column label="订单号" min-width="60%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.rorderFno }}
              </template>
            </el-table-column>
            <el-table-column label="型号" min-width="60%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-link :underline="false" @click="modelNoClick(scope.row)">{{ scope.row.modelNo }}</el-link>
              </template>
            </el-table-column>
            <el-table-column label="数量" min-width="40%" align="center">
              <template slot-scope="scope">
                {{ scope.row.quantity }}
              </template>
            </el-table-column>
            <el-table-column label="单价" min-width="40%" align="center">
              <template slot-scope="scope">
                {{ scope.row.price }}
              </template>
            </el-table-column>
            <el-table-column label="客户代码" min-width="50%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.customerNo }}
              </template>
            </el-table-column>
            <el-table-column label="用户代码" min-width="60" align="center">
              <template slot-scope="scope">
                {{ scope.row.userNo }}
              </template>
            </el-table-column>
            <el-table-column label="接入日期" min-width="60" align="center">
              <template slot-scope="scope">
                {{ scope.row.createTime }}
              </template>
            </el-table-column>
            <el-table-column label="交货期" min-width="80%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.dlvDate | formatDate }}
              </template>
            </el-table-column>
            <el-table-column label="异常信息" min-width="80%" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.expMsg }}
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total >= 3"
            :total="total"
            :page-sizes="[20, 50, 100, 200, 500]"
            :page.sync="page.pageNumber"
            :limit.sync="page.pageSize"
            @pagination="queryExceptionHandList()"/>
        </div>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import { listOrderSales, fetchList, getOrderCountByDeptNo, rcvOrderByOrderSales, xxlJobAutoOrderHandlerStatus, xxlJobAutoOrderOneDayHandlerStatus, xxlJobAutoOrderHandlerSwitch, xxlJobAutoOrderOneDayHandlerSwitch, convertToProcessing, createByorder } from '@/api/order/rcvorder'
import { getDictDataByPid, getDictByClassCodeAndCode, saveDict } from '@/api/common/dict'
import { listDepartment } from '@/api/common/department'
import { findDeptByParentIdsToTrees } from '@/api/organ'
import { formatDate } from '@/utils/dataFormat'
import Pagination from '@/components/Pagination'
import { mapGetters } from 'vuex'
import ProductSearch from '../../product/index'
import { modifyOrder } from '@/api/order/search'
import { getDataCodesTree } from '@/api/common/dict'
export default {
  name: 'ReceiveOrderProcess',
  components: { Pagination, ProductSearch },
  filters: {
    formatDate(time) {
      if (time != null) {
        const date = new Date(time)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    }
  },
  data() {
    return {
      /**
       * 分页
       */
      total: 1,
      page: {
        pageNumber: 1,
        pageSize: 20
      },
      alldeptList: [],
      deptList: [],
      deptName: '',
      openRcvOrderRoles: [],
      hasRole: false,
      deptNoList: [],
      /**
       * 订单接入
       */
      orderReceiveForm: {
        tradecompanyId: '', // 公司 交易主体
        typeCode: '', // 订单类别
        rorderFno: '', // 订单号
        status: '',
        areaDeptNo: '',
        fullOrderNo: ''
      },
      companyCode: [],
      isAutoRcvOrder: true,
      orcvTableListData: [],
      companyOptions: [], // 公司下拉框
      cpnysData: [],
      typeCodeOptions: [], // 订单类型下拉框
      classCode: '',
      loadingButton: false, // 按钮延迟加载
      waitRcvOrder: '', // 待接订单总数量
      isRcvOrder: false, // 接收按钮延迟加载
      strRorderNo: '',
      orderSalesNo: {
        rorderNos: []
      },
      /**
       * 接单处理
       */
      handlingOrder: '', // 处理中订单总数量
      orderlist: [],
      orderCount: '',
      isAutoOrderHandler: false,
      isAutoOrderHandlerodd: false,
      isAutoOrderHandlereven: false,
      isAutoOrderOneDayHandler: false, // 日次接单处理
      tmpObj2: {
        rorderNo: '',
        rorderItem: ''
      },
      autoRcvOrderHand: false,
      isOk: false,
      dptName: '',
      /**
       * 暂不处理
       */
      waitHandleOrder: '', // 暂不处理订单总数量
      waitRcvDataList: [],
      isSuccess: false,
      /**
       * 异常处理
       */
      exceptionHandList: [],
      exceptionHandCount: '',
      isHandBtu: false,
      /**
       * 头部卡片
       */
      updatetime: '09:30',
      form: {
        qryOrderNo: '',
        name: ''
      },
      // 选项卡
      activeName: 'first',
      checked: false,
      isHave: false,
      multipleSelection: [],
      deptParam: {
        temp: '',
        ids: ''
      },
      ids: [],
      tempDeptData: {
        id: '200000',
        pid: '2000000',
        name: '自动化',
        flag: true,
        children: null
      },
      tempObj: {},
      productVisible: false,
      // 字典数据
      DictData: {
        dlvEntire: []
      }

    }
  },
  computed: {
    ...mapGetters(['roles'])
  },
  mounted() {
    this.listDepartment()
    this.getOpenRcvOrderRole()
    this.getcomponyCode()
    this.queryOrderSalesList()
    // 定时开关标识
    this.findDataCode()
    // 订单类型
    this.findTypeCode()
    // 部门
    this.findDeptsToTreeNode()
    this.getOrderCount(this.orderReceiveForm)
    const nowdata = new Date()
    this.updatetime = this.convert(nowdata.getHours()) + ':' + this.convert(nowdata.getMinutes())
    // 接单处理状态
    this.statusOrderProcess()
    // this.statusOrderProcessodd()
    // this.statusOrderProcesseven()
    // 日次接单处理状态
    this.statusOrderOneDayProcess()
  },
  created() {
    getDataCodesTree('4201')
      .then(result => {
        if (result.code === '200') {
          result.content.sort((a, b) => a.sort - b.sort).forEach(dict => {
            this.DictData.dlvEntire.push({
              code: dict.code,
              desc: dict.codeName
            })
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
  },
  methods: {
    statusOrderProcess() {
      xxlJobAutoOrderHandlerStatus('other').then(res => {
        if (res.content === '0') {
          this.isAutoOrderHandler = false
        } else if (res.content === '1') {
          this.isAutoOrderHandler = true
        } else {
          this.orderCount = res.content
          this.isAutoOrderHandler = true
        }
      })
    },
    statusOrderProcessodd() {
      xxlJobAutoOrderHandlerStatus('odd').then(res => {
        if (res.content === '0') {
          this.isAutoOrderHandlerodd = false
        } else {
          this.isAutoOrderHandlerodd = true
        }
      })
    },
    statusOrderProcesseven() {
      xxlJobAutoOrderHandlerStatus('even').then(res => {
        if (res.content === '0') {
          this.isAutoOrderHandlereven = false
        } else {
          this.isAutoOrderHandlereven = true
        }
      })
    },
    statusOrderOneDayProcess() {
      xxlJobAutoOrderOneDayHandlerStatus().then(res => {
        if (res.content === '0') {
          this.isAutoOrderOneDayHandler = false
        } else {
          this.isAutoOrderOneDayHandler = true
        }
      })
    },
    handleChange(value) {
      this.resetOrderReceiveForm()
      const t1 = value[0]
      getDictDataByPid('1003').then(res => {
        for (var i = 0; i < res.content.length; i++) {
          if (res.content[i].extNote1 === t1) {
            this.tempObj = res.content[i]
            this.orderReceiveForm.tradecompanyId = this.tempObj.code
            if (value.length >= 2) {
              this.orderReceiveForm.areaDeptNo = value[1]
            } else {
              this.orderReceiveForm.areaDeptNo = value[0]
            }
            // this.getOrderCount(this.orderReceiveForm)
          }
        }
      })
    },
    orderTypeChange(val) {
      this.queryOrderSalesList()
    },
    listDepartment() {
      listDepartment().then(res => {
        this.deptNoList = res.content
      })
    },
    deptNoFormatter(val) {
      if (val.deptNo === null) {
        return null
      }
      return this.dataConversion(this.deptNoList, val.deptNo)
    },
    dataConversion(deptNoList, val) {
      for (var i = 0; i < deptNoList.length; i++) {
        if (deptNoList[i].deptNo === val) {
          return deptNoList[i].deptName
        }
      }
    },
    dateFormatterForWorkDay(val) {
      if (val.workday != null) {
        return this.dayjs(val.workday).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dateFormatterForDlvDay(val) {
      if (val.dlvDate != null) {
        return this.dayjs(val.dlvDate).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dateFormatterForCreateTime(val) {
      if (val.createTime != null) {
        return this.dayjs(val.createTime).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dlvEntireFormatter(val) {
      if (val.dlvEntire != null) {
        const item = this.DictData.dlvEntire.find(dict => dict.code === val.dlvEntire)
        return item ? item.desc : val.dlvEntire
      }
      return val.dlvEntire
    },
    handdle(val) {
      getDictByClassCodeAndCode('9002', '2').then(res => {
        const dictObj = res.content
        if (val === true) {
          dictObj.extNote1 = '1'
        } else if (val === false) {
          dictObj.extNote1 = '0'
        }
        saveDict(dictObj).then(res => {
          if (res.success === true) {
            this.$notify({
              title: '成功',
              message: '操作成功',
              type: 'success'
            })
          } else {
            this.$notify.error({
              title: '错误',
              message: '操作失败'
            })
          }
        })
      })
    },
    isStartOrderProcess(val) {
      this.orderCount = ''
      var flag = false
      if (val === 'odd') {
        flag = this.isAutoOrderHandlerodd
      } else if (val === 'even') {
        flag = this.isAutoOrderHandlereven
      } else {
        flag = this.isAutoOrderHandler
      }
      xxlJobAutoOrderHandlerSwitch(flag, val).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success'
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: '操作失败'
          })
        }
      })
    },
    isStartOrderOneDayProcess() {
      xxlJobAutoOrderOneDayHandlerSwitch(this.isAutoOrderOneDayHandler).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success'
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: '操作失败'
          })
        }
      })
    },
    /**
     * 显示订单条数
     */
    getOrderCount(val) {
      getOrderCountByDeptNo(val).then(res => {
        this.waitRcvOrder = res.content.orderSalesCount
        this.handlingOrder = res.content.processingOrderCount
        this.waitHandleOrder = res.content.notHandleOrderCount
        this.exceptionHandCount = res.content.exceptionHandCount
      })
    },

    updateDataCount() {
      this.orderReceiveForm.typeCode = ''
      this.orderReceiveForm.rorderFno = ''
      this.orderReceiveForm.status = ''
      this.getOrderCount(this.orderReceiveForm)
      var aData = new Date()
      this.updatetime = this.convert(aData.getHours()) + ':' + this.convert(aData.getMinutes())
    },
    convert(val) {
      return val < 10 ? '0' + val : val
    },

    /**
     * 获取部门
     */
    findDeptsToTreeNode() {
      this.classCode = '1003'
      getDictDataByPid(this.classCode).then(res => {
        this.cpnysData = res.content
        const tempData = []
        for (var t = 0; t < this.cpnysData.length; t++) {
          tempData.push(this.cpnysData[t].extNote1)
        }
        this.deptParam = {
          temp: '',
          ids: ''
        }
        this.deptParam.temp = 'temp'
        this.deptParam.ids = tempData.join('-')
        findDeptByParentIdsToTrees(this.deptParam).then(res => {
          res.content.unshift(this.tempDeptData)
          this.companyOptions = res.content
          for (var j = 0; j < this.companyOptions.length; j++) {
            for (var k = 0; k < this.cpnysData.length; k++) {
              if (this.companyOptions[j].id === this.cpnysData[k].extNote1) {
                this.companyOptions[j].flag = true
              }
            }
          }
          for (var j2 = 0; j2 < this.companyOptions.length; j2++) {
            if (!this.companyOptions[j2].flag) {
              this.companyOptions.splice(j2, 1)
              j2--
            }
          }
        })
      })
    },
    modelNoClick(row) {
      const item = { modelno: row.modelNo }
      this.productVisible = true
      this.$nextTick(() => {
        this.$refs.ProductSearch.handleSelect(item)
        this.$refs.ProductSearch.productSearchChange()
      })
    },
    getcomponyCode() {
      this.classCode = '1003'
      getDictDataByPid(this.classCode).then(res => {
        this.companyCode = res.content
      })
    },
    /**
     * 订单接入 -------------------------------------------------------------------------------------
     */
    // 查 orderSales
    queryOrderSalesList() {
      this.orderReceiveForm.status = '0'
      listOrderSales(this.orderReceiveForm, this.page).then(res => {
        if (res.success) {
          this.isSuccess = true
        } else {
          this.isSuccess = false
        }
        this.orcvTableListData = res.content.list
        this.total = res.content.total
      })
    },
    selectRcvOrder() {
      this.queryOrderSalesList()
      // if (this.isSuccess) {
      //   this.$notify({
      //     title: '成功',
      //     message: '操作成功',
      //     type: 'success'
      //   })
      // } else {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '操作失败'
      //   })
      // }
    },
    handleSelectionChangeRcvOrder(val) {
      this.multipleSelection = val
    },
    newArr(arr) {
      return Array.from(new Set(arr))
    },
    rcvOrder() {
      const rcvOrderList = this.multipleSelection
      this.strRorderNo = ''
      rcvOrderList.forEach(item => {
        this.strRorderNo += item.rorderNo + ','
      })
      this.strRorderNo = this.strRorderNo.substring(0, this.strRorderNo.length - 1)
      this.orderSalesNo.rorderNos = this.strRorderNo.split(',')
      this.orderSalesNo.rorderNos = Array.from(new Set(this.orderSalesNo.rorderNos))
      this.isRcvOrder = true
      if (this.orderSalesNo.rorderNos.length === 0) {
        this.$message({
          message: '请选择需要接收的订单',
          type: 'warning'
        })
      }
      rcvOrderByOrderSales(this.orderSalesNo).then(res => {
        // this.isRcvOrder = false
        if (res.success === true) {
          this.isRcvOrder = false
          this.$notify({
            title: '成功',
            message: res.message,
            type: 'success'
          })
          this.queryOrderSalesList()
          this.updateDataCount()
        } else if (res.success === false) {
          this.isRcvOrder = false
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      }).catch(error => {
        this.isRcvOrder = false
        console.log(error)
      })
    },
    // 查找订单类型
    findTypeCode() {
      this.classCode = '1002'
      getDictDataByPid(this.classCode).then(res => {
        this.typeCodeOptions = res.content
      })
    },
    // 获取启动接入订单的权限
    getOpenRcvOrderRole() {
      getDictByClassCodeAndCode('9002', '3').then(res => {
        const roles = res.content.extNote1
        this.openRcvOrderRoles = roles.split(',')
        this.hasRole = this.openRcvOrderRoles.some(r => this.roles.includes(r))
      })
    },
    // 根据classCode code 获取具体的参数对象
    findDataCode() {
      getDictByClassCodeAndCode('9002', '2').then(res => {
        if (res.content.extNote1 === '1') {
          this.isAutoRcvOrder = true
        } else if (res.content.extNote1 === '0') {
          this.isAutoRcvOrder = false
        }
      })
    },
    // 刷新
    // refresh() {
    //   this.queryOrderSalesList()
    //   if (this.isSuccess) {
    //     this.$notify({
    //       title: '成功',
    //       message: '刷新成功',
    //       type: 'success'
    //     })
    //   } else {
    //     this.$notify.error({
    //       title: '错误',
    //       message: '刷新失败'
    //     })
    //   }
    // },
    typeCodeFormatter(row) {
      switch (row.typeCode) {
        case null:
          return ''
        default:
          return this.valFormatter(this.typeCodeOptions, row.typeCode.toString())
      }
    },
    orderTypeFormatter(row) {
      switch (row.orderType) {
        case null:
          return ''
        default:
          return this.valFormatter(this.typeCodeOptions, row.orderType.toString())
      }
    },
    // 交易主体
    companyFormatter(row) {
      return this.valFormatter(this.companyCode, row.tradeCompanyId)
    },
    valFormatter(list, val) {
      for (var i = 0; i < list.length; i++) {
        if (list[i].code === val) {
          return list[i].codeName
        }
      }
    },

    /**
     * 接单处理  ---------------------------------------------------------------------------------------------
     */
    // 查出接单的数据
    queryRcvOrderList(val) {
      if (val === 1) {
        this.page.pageNumber = 1
      }
      this.orderReceiveForm.status = '1'
      fetchList(this.orderReceiveForm, this.page).then(res => {
        if (res.success) {
          this.isSuccess = true
        } else {
          this.isSuccess = false
        }
        this.orderlist = res.content.list
        this.total = res.content.total
      })
    },
    refreshRcvOrderHand(val) {
      this.queryRcvOrderList(val)
      // if (this.isSuccess) {
      //   this.$notify({
      //     title: '成功',
      //     message: '刷新成功',
      //     type: 'success'
      //   })
      // } else {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '刷新失败'
      //   })
      // }
    },
    async modifyDlvEntire() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          message: '请选择需要修改的订单',
          type: 'warning'
        })
        return
      }
      // 返回this.multipleSelection的所有rorderNo字段去重并输出一个数组
      let rorderNoList = this.multipleSelection.map(item => {
        return item.rorderNo
      })
      // 对rorderNoList去重
      rorderNoList = Array.from(new Set(rorderNoList)).map(rorderNo => {
        return { rorderNo }
      })
      const promises = rorderNoList.map(item => {
        var data = {
          orderId: item.rorderNo,
          master: true,
          dlvEntire: '5',
          userDto: {
            userName: '接单处理修改发货方式'
          },
          reason: '接单处理修改发货方式'
        }
        return modifyOrder(data).then(res => {
          if (!res.success) {
            this.smcErrorMsg(res.message)
          }
          return res
        })
      })
      // 函数promises执行后执行
      await Promise.all(promises)
      this.$notify({
        title: '成功',
        message: '修改完成',
        type: 'success'
      })
      this.queryRcvOrderList()
    },
    /* 自动接单处理 */
    autoRcvOrderHanding() {
      for (var i = 0; i < this.multipleSelection.length; i++) {
        this.autoRcvOrderHand = true
        this.tmpObj2.rorderNo = this.multipleSelection[i].rorderNo
        this.tmpObj2.rorderItem = this.multipleSelection[i].rorderItem
        createByorder(this.tmpObj2).then(res => {
          if (res.success) {
            this.autoRcvOrderHand = false
            this.isOk = true
            this.queryRcvOrderList()
            this.updateDataCount()
          } else {
            this.isOk = false
            this.autoRcvOrderHand = false
          }
          if (res === undefined) {
            this.isOk = false
            this.autoRcvOrderHand = false
          }
        }).catch(() => {
          this.autoRcvOrderHand = false
        })
      }
      if (this.isOk) {
        this.$notify({
          title: '成功',
          message: '处理成功',
          type: 'success'
        })
      }
    },
    /**
     *  暂不处理 ---------------------------------------------------------------------------------------------
     */
    queryRcvWaitHandleOrder() {
      this.orderReceiveForm.status = '11'
      fetchList(this.orderReceiveForm, this.page).then(res => {
        if (res.success) {
          this.isSuccess = true
        } else {
          this.isSuccess = false
        }
        this.waitRcvDataList = res.content.list
        this.total = res.content.total
      })
    },
    reflushForNoHandOrder() {
      this.queryRcvWaitHandleOrder()
      // if (this.isSuccess) {
      //   this.$notify({
      //     title: '成功',
      //     message: '刷新成功',
      //     type: 'success'
      //   })
      // } else {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '刷新失败'
      //   })
      // }
    },
    /**
     * 异常处理
     */
    queryExceptionHandList() {
      this.orderReceiveForm.status = '10'
      fetchList(this.orderReceiveForm, this.page).then(res => {
        if (res.success) {
          this.isSuccess = true
        } else {
          this.isSuccess = false
        }
        this.exceptionHandList = res.content.list
        this.total = res.content.total
        if (!res.success) {
          this.total = 0
        }
      })
    },
    reflushForExceptionHand() {
      this.queryExceptionHandList()
      // if (this.isSuccess) {
      //   this.$notify({
      //     title: '成功',
      //     message: '刷新成功',
      //     type: 'success'
      //   })
      // } else {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '刷新失败'
      //   })
      // }
    },
    /** 转为处理订单 */
    convertToProcessing() {
      this.isHandBtu = true
      convertToProcessing(this.multipleSelection).then(res => {
        if (res.success) {
          this.isHandBtu = false
          this.queryExceptionHandList()
          this.updateDataCount()
          this.$notify({
            title: '成功',
            message: '操作成功',
            type: 'success'
          })
        } else {
          this.isHandBtu = false
          this.$notify.error({
            title: '错误',
            message: '操作失败'
          })
        }
      })
    },
    findList() {
    },
    handleClick(tab) {
      if (tab.name === 'second') {
        this.orderReceiveForm.typeCode = ''
        this.orderReceiveForm.rorderFno = ''
        this.multipleSelection = []
        this.queryRcvOrderList()
      } else if (tab.name === 'first') {
        this.multipleSelection = []
        this.orderReceiveForm.rorderFno = ''
        this.queryOrderSalesList()
      } else if (tab.name === 'three') {
        this.orderReceiveForm.rorderFno = ''
        this.orderReceiveForm.typeCode = ''
        this.multipleSelection = []
        this.queryRcvWaitHandleOrder()
      } else if (tab.name === 'four') {
        this.orderReceiveForm.rorderFno = ''
        this.orderReceiveForm.typeCode = ''
        this.multipleSelection = []
        this.queryExceptionHandList()
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = []
      this.multipleSelection = val
    },
    resetOrderReceiveForm() {
      this.orderReceiveForm = {
        tradecompanyId: '', // 公司 交易主体
        typeCode: '', // 订单类别
        rorderFno: '', // 订单号
        status: '',
        areaDeptNo: ''
      }
    }
  }
}
</script>
<style scoped>
.line{
  text-align: center;
  margin-left: 5%;
}
.formitem{
    margin-right: 4%;
}
.formcard{
  width:120px;
  height:120px;
  text-align: center
}
.ecard{
  height: 150px
}
.elsecect{
  width: 120px;
}
.elswitch{
  margin-left: 70px;
}
.unusualOrder{
  margin-left: 80px;
}
.handleBtu{
  margin-bottom: 8px;
  position: absolute;
  margin-top: -48px;
  margin-left: 280px;
}
.handleBtu2 {
  margin-bottom: 8px;
  position: absolute;
  margin-top: -48px;
  margin-left: 400;
}
.abnormalBtu{
  margin-bottom: 8px;
}
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
