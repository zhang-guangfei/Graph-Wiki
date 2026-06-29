<template>
  <div class="container">
    <div class="searchHead">
      <el-form :inline="true" :model="searchForm" class="demo-form-inline" size="mini">
        <el-form-item>
          <el-input v-model="searchForm.orderNo" placeholder="请输入订单号" style="width: 160px" size="mini" clearable/>
        </el-form-item>
        <el-form-item>
          <el-input v-model="searchForm.modelNo" placeholder="型号" size="mini" clearable class="modelClass" style="width: 115px"/>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchForm.stateCode" clearable placeholder="状态代码" multiple collapse-tags size="mini" class="elsecect" @change="changeStateCode">
            <el-option
              v-for="item in stateCodeOptions"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select v-model="searchForm.purchaseTypes" clearable placeholder="采购类型" multiple collapse-tags size="mini" class="elsecect" @change="changeStateCode">
            <el-option
              v-for="item in purchaseTypeOptions"
              :key="item.code"
              :label="item.code"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <!--add by LiYingChao from bugId 8699 in 2022-11-17-->
        <el-form-item class="supplierInput">
          <el-select v-model="searchForm.supplierCodes" placeholder="请选择供应商" multiple collapse-tags filterable clearable @change="changeStateCode">
            <el-option
              v-for="item in supplieData"
              :key="item.id"
              :label="item.name"
              :value="item.id">
              <span style="float: left">{{ item.name }}</span>
              <span style="float: right; color: #8492a6; font-size: 13px">{{ item.id }}</span>
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="supplierInput">
          <el-select v-model="searchForm.orderTypes" clearable placeholder="订单类型" multiple collapse-tags size="mini" style="width: 140px" @change="changeStateCode">
            <el-option
              v-for="item in typeCodeData"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item class="supplierInput">
          <el-select v-model="searchForm.warehouseCodes" clearable placeholder="收货仓库" multiple collapse-tags size="mini" style="width: 140px" @change="changeStateCode">
            <el-option
              v-for="item in warehouseData"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['147894']" type="primary" size="mini" class="searchBtu" @click="searchList(1)" >查询</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['147894']" type="primary" size="mini" class="searchBtu" @click="resetSearchForm()" >重置</el-button>
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['147894']" :loading="exportLoading" type="primary" size="mini" icon="el-icon-bottom" class="handleBtu" @click="exportOrderState()">导出</el-button>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
        </el-form-item>
      </el-form>
    </div>
    <div>
      <div v-if="searchMoreForm" class="search">
        <el-form ref="form" :inline="true" :model="searchForm" size="mini" clearable class="demo-form-inline">
          <el-form-item>
            <!--add by LiYingChao from 20221102 bugID 8540-->
            <el-input
              :rows="3"
              v-model="searchForm.moreOrderNo"
              style="width:128px"
              autocomplete="off"
              type="textarea"
              placeholder="订单号"
              size="mini"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <!--add by LiYingChao from 20221201 bugID 8855-->
            <el-input
              :rows="3"
              v-model="searchForm.moreSupplierNo"
              style="width:128px"
              autocomplete="off"
              type="textarea"
              placeholder="手配号"
              size="mini"
              clearable
            />
          </el-form-item>
          <el-form-item>
            <!-- <el-cascader
              ref="cascaderHandle"
              :options="deptNoOptions"
              :props="{ value: 'id', label: 'name', multiple: true }"
              :show-all-levels="false"
              placeholder="选择营业所"
              collapse-tags
              filterable
              clearable
              style="width: 120px"
              size="mini"
              @change="handleChange" /> -->
            <department @handleScopeChange="handleChange"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchForm.customerNo" style="width: 100px" clearable class="customerCode" placeholder="客户代码"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchForm.corderNo" style="width: 105px" clearable placeholder="客户订单号" class="customerOrder"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchForm.cmodelNo" style="width: 100px" clearable placeholder="客户型号" class="customerModelNo"/>
          </el-form-item>
          <el-form-item>
            <el-input v-model="searchForm.userNo" style="width: 100px" clearable class="userNo" placeholder="用户代码"/>
          </el-form-item>
          <el-form-item class="day">
            <el-select v-model="daySelectVal" style="width: 120px" clearable placeholder="请选择:">
              <el-option label="客户交货期" value="custDlvDate"/>
              <el-option label="指定工厂出荷日" value="poDlvDate"/>
              <el-option label="指定物流发货日" value="shipDate"/>
              <el-option label="工厂纳期" value="poReplyDate"/>
              <el-option label="实际出厂日" value="poFacExpdate"/>
              <el-option label="供应商发出日" value="poShipDate"/>
              <el-option label="预计到达日期" value="esarrivalDate"/>
              <el-option label="更新时间" value="updateTime"/>
              <el-option label="供应商接单日期" value="supplierRcvTime"/>
              <el-option label="订单接单日期" value="orderDate"/>
              <el-option label="货期变化日期" value="dlvUpdTime"/>
            </el-select>
            <el-date-picker
              v-model="orderStateDate"
              :picker-options="pickerOptions"
              :default-time="['00:00:00', '23:59:59']"
              type="daterange"
              align="right"
              unlink-panels
              style="width: 250px"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
          </el-form-item>
          <el-form-item>
            <el-select v-model="searchForm.specQryType" clearable placeholder="特定筛选" collapse-tags size="mini" class="elsecect">
              <el-option
                v-for="item in specQueryTypes"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"/>
            </el-select>
          </el-form-item>
        </el-form>
      </div>
      <el-dialog :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
        <product-search v-if="productVisible" ref="ProductSearch"/>
      </el-dialog>
      <el-table
        :data="tableData"
        :row-style="{height: '0'}"
        :cell-style="{padding: '0'}"
        :row-key="getRowKeys"
        :expand-row-keys="expands"
        border
        stripe
        height="520"
        size="mini"
        class="tab"
        @expand-change="clickSubTab">
        <el-table-column fixed label="订单号" align="left" width="140" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link type="primary" @click="handdle(scope.row)">{{ scope.row.orderNo }}</el-link>
          </template>
        </el-table-column>
        <el-table-column fixed type="expand" label="*" align="left" width="30" show-overflow-tooltip>
          <template>
            <el-table
              :data="subTabDataList"
              :row-style="{height: '0'}"
              :cell-style="{padding: '0'}"
              :header-cell-style="{background: '#e6e6fa' ,coLor:'#fff'}"
              :loading="subTabLoading"
              stripe
              border
              size="mini"
              style="width: 100%">
              <el-table-column
                fixed
                align="left"
                show-overflow-tooltip/>
              <el-table-column fixed label="订单号" align="left" width="140" show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-link type="primary" @click="handdle(scope.row)">{{ scope.row.orderNo }}</el-link>
                </template>
              </el-table-column>
              <el-table-column
                :formatter="stateCodeFormatter"
                fixed
                prop="stateCode"
                align="left"
                label="状态"
                show-overflow-tooltip/>
              <el-table-column
                fixed
                prop="stateDes"
                align="left"
                label="状态描述"
                width="200"
                show-overflow-tooltip/>
              <el-table-column
                fixed
                prop="modelNo"
                label="型号"
                width="140"
                align="left"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-link :underline="false" @click="handleClick(scope.row)">{{ scope.row.modelNo }}</el-link>
                </template>
              </el-table-column>
              <el-table-column
                fixed
                prop="quantity"
                label="数量"
                align="left"
                width="60"/>
              <el-table-column
                fixed
                prop="supplierCode"
                align="left"
                label="供应商"
                width="85"
                show-overflow-tooltip/>
              <el-table-column
                prop="warehouseCode"
                align="left"
                label="收货仓库"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="custShipDate"
                label="指定物流发货日"
                width="110"
                show-overflow-tooltip/>
              <el-table-column
                prop="custDlvDate"
                label="客户交货期"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="poDlvDate"
                label="指定工厂出荷日"
                width="110"
                show-overflow-tooltip/>
              <el-table-column
                prop="expectedProductionCompletionDateH"
                label="工厂预计完工日"
                width="110"
                show-overflow-tooltip/>
              <el-table-column
                prop="expectedLogisticsArrivalDateW"
                label="工厂预计入库日"
                width="110"
                show-overflow-tooltip/>
              <el-table-column
                prop="poReplyDateStr"
                label="最新工厂返信"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="poReplyDateA"
                align="left"
                label="工厂纳期变更1"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="poReplyDateB"
                align="left"
                label="工厂纳期变更2"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="poReplyDateC"
                align="left"
                label="工厂纳期变更3"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="supplierRcvTime"
                align="left"
                label="供应商接单日"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="orderDate"
                align="left"
                label="订单接单日"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="beginProduceDate"
                align="left"
                label="开始生产日"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="poFacExpdate"
                align="left"
                label="实际出厂日"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                :formatter="poExpTypeFormatter"
                prop="poExpType"
                label="出库区分"
                width="50"/>
              <el-table-column
                prop="supplierOrderNo"
                align="left"
                label="手配号"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="transType"
                align="left"
                label="运输方式"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="poTransType"
                align="left"
                label="原运输方式"
                width="100"
                show-overflow-tooltip/>
              <!-- <el-table-column
                prop="warehouseCode"
                align="left"
                width="85"
                label="收货仓库"
                show-overflow-tooltip/> -->
              <el-table-column
                prop="poInvoiceNo"
                align="left"
                label="供应商发票"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="customerNo"
                align="left"
                label="客户代码"
                show-overflow-tooltip/>
              <el-table-column
                prop="userNo"
                align="left"
                label="用户代码"
                show-overflow-tooltip/>
              <el-table-column
                prop="cmodelNo"
                align="left"
                label="客户型号"
                width="140"
                show-overflow-tooltip/>
              <el-table-column
                prop="corderNo"
                align="left"
                label="客户PO"
                width="140"
                show-overflow-tooltip/>
              <el-table-column
                prop="deptNo"
                align="left"
                label="营业所代码"
                width="140"
                show-overflow-tooltip/>
              <el-table-column
                prop="shikomiNo"
                align="left"
                label="shikomi"
                show-overflow-tooltip/>
              <el-table-column
                prop="poDelayDay"
                width="80"
                align="left"
                label="延误天数"/>
              <el-table-column
                prop="arrivalDelayDay"
                align="left"
                width="95"
                label="延误到达天数"/>
              <el-table-column
                prop="esArrivalDate"
                align="left"
                label="预计到达日期"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                :formatter="orderTypeFormatter"
                prop="orderType"
                align="left"
                width="90"
                label="订单类型"
                show-overflow-tooltip/>
              <el-table-column
                prop="purchaseType"
                align="left"
                width="90"
                label="采购类型"
                show-overflow-tooltip/>
              <el-table-column
                prop="provider"
                align="left"
                label="数据来源"/>
              <el-table-column
                prop="createTime"
                align="left"
                label="创建时间"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="updateTime"
                align="left"
                label="变更时间"
                width="120"
                show-overflow-tooltip/>
              <el-table-column
                fixed="right"
                label="操作"
                width="100">
                <template slot-scope="scope">
                  <el-link v-permission="['147894']" type="primary" @click="queryOrderLog(scope.row)">查看日志</el-link>
                </template>
              </el-table-column>
            </el-table>
          </template>
        </el-table-column>
        <el-table-column
          :formatter="stateCodeFormatter"
          fixed
          prop="stateCode"
          align="left"
          label="状态"
          show-overflow-tooltip/>
        <el-table-column
          fixed
          prop="stateDes"
          align="left"
          label="状态描述"
          width="200"
          show-overflow-tooltip/>
        <el-table-column
          fixed
          prop="modelNo"
          label="型号"
          width="140"
          align="left"
          show-overflow-tooltip>
          <template slot-scope="scope">
            <el-link :underline="false" @click="handleClick(scope.row)">{{ scope.row.modelNo }}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          fixed
          prop="quantity"
          label="数量"
          align="left"
          width="60"/>
        <el-table-column
          fixed
          prop="supplierCode"
          align="left"
          label="供应商"
          width="85"
          show-overflow-tooltip/>
        <el-table-column
          prop="warehouseCode"
          align="left"
          label="收货仓库"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="custShipDate"
          label="指定物流发货日"
          width="110"
          show-overflow-tooltip/>
        <el-table-column
          prop="custDlvDate"
          label="客户交货期"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="poDlvDate"
          label="指定工厂出荷日"
          width="110"
          show-overflow-tooltip/>
        <el-table-column
          prop="expectedProductionCompletionDateH"
          label="工厂预计完工日"
          width="110"
          show-overflow-tooltip/>
        <el-table-column
          prop="expectedLogisticsArrivalDateW"
          label="工厂预计入库日"
          width="110"
          show-overflow-tooltip/>
        <el-table-column
          prop="poReplyDateStr"
          label="最新工厂返信"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="poReplyDateAStr"
          align="left"
          label="工厂纳期变更1"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="poReplyDateBStr"
          align="left"
          label="工厂纳期变更2"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="poReplyDateCStr"
          align="left"
          label="工厂纳期变更3"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="supplierRcvTime"
          align="left"
          label="供应商接单日"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="orderDate"
          align="left"
          label="订单接单日"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="beginProduceDate"
          align="left"
          label="开始生产日"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="poFacExpdate"
          align="left"
          label="实际出厂日"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="poShipDate"
          align="left"
          label="供应商发出日"
          width="100"
          show-overflow-tooltip/>
        <!--add by LiYingChao 20221025 bug 8456-->
        <el-table-column
          :formatter="poExpTypeFormatter"
          prop="poExpType"
          label="出库区分"
          width="50"/>
        <el-table-column
          prop="supplierOrderNo"
          align="left"
          label="手配号"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="transType"
          align="left"
          label="运输方式"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="poTransType"
          align="left"
          label="原运输方式"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="warehouseCode"
          align="left"
          width="85"
          label="收货仓库"
          show-overflow-tooltip/>
        <el-table-column
          prop="poInvoiceNo"
          align="left"
          label="供应商发票"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="customerNo"
          align="left"
          label="客户代码"
          show-overflow-tooltip/>
        <el-table-column
          prop="userNo"
          align="left"
          label="用户代码"
          show-overflow-tooltip/>
        <el-table-column
          prop="cmodelNo"
          align="left"
          label="客户型号"
          width="140"
          show-overflow-tooltip/>
        <el-table-column
          prop="corderNo"
          align="left"
          label="客户PO"
          width="140"
          show-overflow-tooltip/>
        <el-table-column
          prop="deptNo"
          align="left"
          label="营业所代码"
          width="140"
          show-overflow-tooltip/>
        <el-table-column
          prop="shikomiNo"
          align="left"
          label="shikomi"
          show-overflow-tooltip/>
        <el-table-column
          prop="poDelayDay"
          width="80"
          align="left"
          label="延误天数"/>
        <el-table-column
          prop="arrivalDelayDay"
          align="left"
          width="95"
          label="延误到达天数"/>
        <el-table-column
          prop="esArrivalDate"
          align="left"
          label="预计到达日期"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          :formatter="orderTypeFormatter"
          prop="orderType"
          align="left"
          width="90"
          label="订单类型"
          show-overflow-tooltip/>
        <el-table-column
          prop="purchaseType"
          align="left"
          width="90"
          label="采购类型"
          show-overflow-tooltip/>
        <el-table-column
          prop="provider"
          align="left"
          label="数据来源"
          show-overflow-tooltip=""/>
        <!-- <el-table-column label="创建时间" align="left" show-overflow-tooltip>
          <template slot-scope="scope">
            {{ scope.row.createTime | formatDate }}
          </template>
        </el-table-column> -->
        <el-table-column
          prop="createTime"
          align="left"
          label="创建时间"
          width="100"
          show-overflow-tooltip/>
        <el-table-column
          prop="updateTime"
          align="left"
          label="变更时间"
          width="120"
          show-overflow-tooltip/>
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <!-- <el-button @click="handleClick(scope.row)" type="text" size="small">查看</el-button> -->
            <el-link v-permission="['147894']" type="primary" @click="queryOrderLog(scope.row)">查看日志</el-link>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <pagination
      v-show="total >= 10"
      :total="total"
      :page-sizes="[20, 30, 50, 100, 200, 500]"
      :page.sync="searchForm.page.pageNumber"
      :limit.sync="searchForm.page.pageSize"
      @pagination="queryDataList()"/>
    <!--dialog 点击行-->
    <el-dialog :visible.sync="dialogTableVisible" title="期货状态明细" width="65%">
      <el-table :data="gridData" :row-style="{height: '0'}" class="dialogTable">
        <el-table-column property="orderNo" align="center" label="订单号" width="140" />
        <el-table-column property="dateName" align="center" label="变更内容"/>
        <el-table-column property="changeTimes" align="center" label="变更次数"/>
        <el-table-column :formatter="dateFormatterForStateDate" property="stateDate" align="center" label="状态时间" width="130" show-overflow-tooltip/>
        <el-table-column property="stateDes" align="center" label="状态说明" width="100" show-overflow-tooltip/>
        <el-table-column :formatter="dateFormatterForFirstDate" property="firstDate" align="center" label="首次时间" width="130" show-overflow-tooltip/>
        <el-table-column :formatter="dateFormatterForMinDate" property="minDate" align="center" label="最早时间" width="130" show-overflow-tooltip/>
        <el-table-column :formatter="dateFormatterForMaxDate" property="maxDate" align="center" label="最晚时间" width="130" show-overflow-tooltip/>
        <el-table-column property="provider" align="center" label="状态来源" />
        <el-table-column property="delayDay" align="center" label="与上次延迟天数" width="130"/>
        <el-table-column :formatter="dateFormatterForCreateTime" property="createTime" align="center" label="创建时间" width="130" show-overflow-tooltip/>
        <el-table-column :formatter="dateFormatterForUpdateTime" property="updateTime" align="center" label="修改时间" width="130" show-overflow-tooltip/>
        <!-- <el-table-column
          align="center"
          label="操作">
          <template slot-scope="scope">
            <el-link type="danger" @click="cancel(scope.$index, scope.row)">取消</el-link>
          </template>
        </el-table-column> -->
      </el-table>
    </el-dialog>
    <el-dialog :visible.sync="orderLogTableVisible" title="订单日志" width="50%">
      <el-table :data="orderLogData" :row-style="{height: '0'}" class="dialogTable">
        <el-table-column property="orderNo" align="center" label="订单号" width="140" />
        <el-table-column property="description" align="center" label="操作内容"/>
        <el-table-column property="optUserName" align="center" label="操作人姓名" show-overflow-tooltip/>
        <el-table-column :formatter="dateFormatterForOptTime" property="optTime" align="center" label="操作时间" width="130" show-overflow-tooltip/>
      </el-table>
    </el-dialog>
  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import { codeFormatter } from '@/api/common/comm'
import { findSupplierByIdOrName } from '@/api/common/supplier'
import { getDictDataByPid, listWarehouseNoWT } from '@/api/common/dict'
import { shortcuts, formatDate } from '@/utils/dataFormat'
import { fetchList, queryByOrderNo, exportOrderState, getSplitOrderState } from '@/api/order/orderstate'
import { findOrderLog } from '@/api/order/orderlog'
import ProductSearch from '../../product/index'
export default {
  name: 'OrderState',
  components: { Pagination, ProductSearch, Department },
  filters: {
    formatDate(time) {
      if (time != null) {
        const date = new Date(time)
        return formatDate(date, 'yyyy-MM-dd hh:mm')
      }
    },
    formatDate2(time) {
      if (time != null) {
        const date = new Date(time)
        return formatDate(date, 'yyyy-MM-dd')
      }
    }
  },
  data() {
    return {
      searchForm: {
        orderNo: '',
        modelNo: '',
        stateCode: [],
        deptNo: '',
        deptCode: [],
        customerNo: '',
        corderNo: '',
        userNo: '',
        purchaseType: [],
        cmodelNo: '',
        orderTypes: [],
        custDlvDateStart: '', // 客户交货期
        custDlvDateEnd: '',
        poDlvDateStart: '', // 指定工厂出荷日
        poDlvDateEnd: '',
        shipDateStart: '', // 指定物流发送日
        shipDateEnd: '',
        poReplyDateStart: '', // 工厂纳期
        poReplyDateEnd: '',
        poShipDateStart: '', // 供应商发出日
        poShipDateEnd: '',
        poFacExpdateStart: '', // 实际出厂日
        poFacExpdateEnd: '',
        esArrivalDateStart: '', // 预计到达日期
        esArrivalDateEnd: '',
        updateTimeStart: '', // 更新时间
        updateTimeEnd: '',
        supplierRcvTimeStart: '', // 供应商接单日
        supplierRcvTimeEnd: '',
        orderDateStart: '', // 订单接单日
        orderDateEnd: '',
        dlvUpdTimeStart: '',
        dlvUpdTimeEnd: '',
        supplierCodes: [],
        warehouseCodes: [],
        supplierOrderNo: '',
        specQryType: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        },
        moreOrderNo: '',
        orderNos: [],
        supplierOrderNos: [],
        moreSupplierNo: ''
      },
      orderLogRequest: {
        orderNo: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      // 分页
      total: 1,
      pickerOptions: shortcuts,
      supplierData: [], // 供应商
      daySelectVal: '', // 选择的查询时间类型
      orderStateDate: '',
      classCode: '',
      orderNo: '',
      tableData: [],
      subTabDataList: [],
      expands: [], // 通过该属性设置Table目前的展开行，需要设置row-key属性才能使用，该属性为展开行的keys数组
      getRowKeys(row) { // 行数据的Key
        return row.id
      },
      subTabLoading: false,
      gridData: [], // 对话框
      orderLogData: [],
      deptNoOptions: [], // 部门集合
      deptParam: {
        temp: '',
        ids: ''
      },
      supplierForm: {
        companyId: '',
        name: ''
      },
      supplieData: [],
      cpnysData: [],
      purchaseTypeOptions: [],
      stateCodeOptions: [], // 货期状态类型下拉框
      typeCodeData: [],
      warehouseData: [],
      specQueryTypes: [],
      dialogTableVisible: false,
      orderLogTableVisible: false,
      searchMoreForm: false,
      loadingButton: false,
      productVisible: false,
      exportLoading: false,
      isShowSubTab: false,
      poExpTypes: []
    }
  },
  mounted() {
    this.getTypeCodeDataList()
    this.findTypeCodeForSupplier()
    this.queryDataList()
    // this.getScopeOptions()
    this.getStateCodeDataList()
    this.getPurchaseTypeList()
    this.getWareHouseListData()
    this.listSupplierinfo()
    this.getSpecQryTypes()
    this.getPOExpTypes()
    this.total = this.tableData.length
  },
  methods: {
    handleChange(val) {
      console.log('所选营业所=> ', val)
      this.searchForm.deptCode = val
    },
    getStateCodeDataList() {
      this.classCode = '1004'
      getDictDataByPid(this.classCode).then(res => {
        this.stateCodeOptions = res.content
      })
    },
    getPurchaseTypeList() {
      this.classCode = '2031'
      getDictDataByPid(this.classCode).then(res => {
        this.purchaseTypeOptions = res.content
      })
    },
    getTypeCodeDataList() {
      this.classCode = '1002'
      getDictDataByPid(this.classCode).then(res => {
        this.typeCodeData = res.content
      })
    },
    getSpecQryTypes() {
      this.classCode = '1094'
      getDictDataByPid(this.classCode).then(res => {
        this.specQueryTypes = res.content
      })
    },
    getPOExpTypes() {
      this.classCode = '2074'
      getDictDataByPid(this.classCode).then(res => {
        this.poExpTypes = res.content
      })
    },
    exportOrderState() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      if (this.searchForm.moreOrderNo !== '') {
        this.searchForm.orderNos = this.searchForm.moreOrderNo.split('\n')
        console.log(this.searchForm.orderNos)
      }
      if (this.searchForm.moreSupplierNo !== '') {
        this.searchForm.supplierOrderNos = this.searchForm.moreSupplierNo.split('\n')
        console.log(this.searchForm.supplierOrderNos)
      }
      exportOrderState(this.searchForm).then(res => {
        const fileName = '货期状态数据.xlsx'
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
    getWareHouseListData() {
      listWarehouseNoWT().then(res => {
        if (res.success) {
          this.warehouseData = res.content
        } else {
          this.warehouseData = []
        }
      }).catch(() => {
        this.warehouseData = []
      })
    },
    // table每次只能展开一行
    clickSubTab(row, expandedRows) {
      this.subTabDataList = []
      this.expands = []
      if (expandedRows.length > 0) {
        row ? this.expands.push(row.id) : ''
      }
      this.subTabLoading = true
      if (row.splitNo === 0) {
        getSplitOrderState(row.rorderNo, row.itemNo).then(res => {
          this.subTabDataList = res.content
          this.subTabLoading = false
        })
      }
    },
    queryDataList(val) {
      if (val === 1) {
        this.searchForm.page.pageNumber = 1
      }
      if (this.searchForm.moreOrderNo !== '') {
        this.searchForm.orderNos = this.searchForm.moreOrderNo.split('\n')
        console.log(this.searchForm.orderNos)
      }
      if (this.searchForm.moreSupplierNo !== '') {
        this.searchForm.supplierOrderNos = this.searchForm.moreSupplierNo.split('\n')
        console.log(this.searchForm.supplierOrderNos)
      }
      fetchList(this.searchForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        }
        this.searchForm.orderNos = []
        this.searchForm.supplierOrderNos = []
      })
    },
    searchList(val) {
      this.searchTime()
      this.queryDataList(val)
    },
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    handdle(row, event, column) {
      this.dialogTableVisible = true
      this.orderNo = row.orderNo
      queryByOrderNo(this.orderNo).then(res => {
        this.gridData = []
        this.gridData = res.content
      })
    },
    handdleOptBtu(row, event, column) {
      this.isShowSubTab = true
    },
    changeStateCode() {
      this.searchList()
    },
    queryOrderLog(row) {
      this.orderLogTableVisible = true
      this.orderLogRequest.orderNo = row.orderNo
      this.orderLogRequest.page.pageNumber = 1
      this.orderLogRequest.page.pageSize = 10
      findOrderLog(this.orderLogRequest).then(res => {
        this.orderLogData = []
        this.orderLogData = res.content.list
      })
    },
    listSupplierinfo() {
      const parm = { 'companyId': this.supplierForm.companyId, 'name': this.supplierForm.name }
      findSupplierByIdOrName(parm).then(res => {
        this.supplieData = res.content
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 查找供应商
    findTypeCodeForSupplier() {
      this.classCode = '2002'
      getDictDataByPid(this.classCode).then(res => {
        this.supplierData = res.content
      })
    },
    handleClick(row) {
      const item = { modelno: row.modelNo }
      this.productVisible = true
      this.$nextTick(() => {
        this.$refs.ProductSearch.handleSelect(item)
        this.$refs.ProductSearch.productSearchChange()
      })
    },
    orderTypeFormatter(row) {
      return this.valFormatter(this.typeCodeData, row.orderType + '')
    },
    stateCodeFormatter(row) {
      return this.valFormatter(this.stateCodeOptions, row.stateCode + '')
    },
    poExpTypeFormatter(row) {
      return codeFormatter(this.poExpTypes, row.poExpType)
    },
    valFormatter(list, val) {
      for (var i = 0; i < list.length; i++) {
        if (list[i].code === val) {
          return list[i].codeName
        }
      }
    },
    searchTime() {
      if (this.daySelectVal === 'custDlvDate') {
        this.resetDate()
        this.searchForm.custDlvDateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.custDlvDateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'poDlvDate') {
        this.resetDate()
        this.searchForm.poDlvDateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.poDlvDateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'shipDate') {
        this.resetDate()
        this.searchForm.shipDateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.shipDateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'poReplyDate') {
        this.resetDate()
        this.searchForm.poReplyDateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.poReplyDateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'poShipDate') {
        this.resetDate()
        this.searchForm.poShipDateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.poShipDateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'esarrivalDate') {
        this.resetDate()
        this.searchForm.esArrivalDateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.esArrivalDateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'updateTime') {
        this.resetDate()
        this.searchForm.updateTimeStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.updateTimeEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'supplierRcvTime') {
        this.resetDate()
        this.searchForm.supplierRcvTimeStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.supplierRcvTimeEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'orderDate') {
        this.resetDate()
        this.searchForm.orderDateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.orderDateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'poFacExpdate') {
        this.resetDate()
        this.searchForm.poFacExpdateStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.poFacExpdateEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'dlvUpdTime') {
        this.resetDate()
        this.searchForm.dlvUpdTimeStart = this.dayjs(this.orderStateDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.dlvUpdTimeEnd = this.dayjs(this.orderStateDate[1]).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    resetDate() {
      this.searchForm.custDlvDateStart = '' // 客户交货期
      this.searchForm.custDlvDateEnd = ''
      this.searchForm.poDlvDateStart = '' // 指定工厂出荷日
      this.searchForm.poDlvDateEnd = ''
      this.searchForm.shipDateStart = '' // 指定物流发送日
      this.searchForm.shipDateEnd = ''
      this.searchForm.poReplyDateStart = '' // 工厂纳期
      this.searchForm.poReplyDateEnd = ''
      this.searchForm.poShipDateStart = '' // 供应商发出日
      this.searchForm.poShipDateEnd = ''
      this.searchForm.poFacExpdateStart = '' // 实际出厂日
      this.searchForm.poFacExpdateEnd = ''
      this.searchForm.esArrivalDateStart = '' // 预计到达日期
      this.searchForm.esArrivalDateEnd = ''
      this.searchForm.supplierRcvTimeStart = ''
      this.searchForm.supplierRcvTimeEnd = ''
      this.searchForm.dlvUpdTimeStart = ''
      this.searchForm.dlvUpdTimeEnd = ''
      this.searchForm.orderDateStart = ''
      this.searchForm.orderDateEnd = ''
    },
    resetSearchForm() {
      this.searchForm = {
        orderNo: '',
        modelNo: '',
        stateCode: [],
        deptNo: '',
        deptCode: [],
        customerNo: '',
        corderNo: '',
        userNo: '',
        purchaseType: [],
        cmodelNo: '',
        orderTypes: [],
        custDlvDateStart: '', // 客户交货期
        custDlvDateEnd: '',
        poDlvDateStart: '', // 指定工厂出荷日
        poDlvDateEnd: '',
        shipDateStart: '', // 指定物流发送日
        shipDateEnd: '',
        poReplyDateStart: '', // 工厂纳期
        poReplyDateEnd: '',
        poShipDateStart: '', // 供应商发出日
        poShipDateEnd: '',
        poFacExpdateStart: '', // 实际出厂日
        poFacExpdateEnd: '',
        esArrivalDateStart: '', // 预计到达日期
        esArrivalDateEnd: '',
        updateTimeStart: '', // 更新时间
        updateTimeEnd: '',
        supplierRcvTimeStart: '', // 供应商接单日
        supplierRcvTimeEnd: '',
        orderDateStart: '', // 订单接单日
        orderDateEnd: '',
        supplierCodes: [],
        warehouseCodes: [],
        supplierOrderNo: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        },
        moreOrderNo: '',
        orderNos: [],
        supplierOrderNos: [],
        moreSupplierNo: ''
      }
      this.daySelectVal = ''
      this.orderStateDate = ''
      this.$refs.cascaderHandle.checkedValue = ''
    },
    dateFormatterForStateDate(val) {
      if (val.firstDate != null) {
        return this.dayjs(val.firstDate).format('YYYY-MM-DD HH:mm')
      }
    },
    dateFormatterForFirstDate(val) {
      if (val.stateDate != null) {
        return this.dayjs(val.stateDate).format('YYYY-MM-DD HH:mm')
      }
    },
    dateFormatterForOptTime(val) {
      if (val.optTime != null) {
        return this.dayjs(val.optTime).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dateFormatterForCreateTime(val) {
      if (val.createTime != null) {
        return this.dayjs(val.createTime).format('YYYY-MM-DD HH:mm')
      }
    },
    dateFormatterForMaxDate(val) {
      if (val.maxDate != null) {
        return this.dayjs(val.maxDate).format('YYYY-MM-DD HH:mm')
      }
    },
    dateFormatterForMinDate(val) {
      if (val.minDate != null) {
        return this.dayjs(val.minDate).format('YYYY-MM-DD HH:mm')
      }
    },
    dateFormatterForUpdateTime(val) {
      if (val.updateTime != null) {
        return this.dayjs(val.updateTime).format('YYYY-MM-DD HH:mm')
      }
    }
  }
}
</script>
<style scoped>
.searchHead{
  margin-top: 15px;
  margin-left: 15px;
}
.search{
  margin-left: 15px;
}
.elsecect {
  width: 150px;
}
.el-tag--light{
  position: absolute;
  margin-left: 85px;
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
  .dialogTable{
    height: 100px;
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
  .el-table /deep/ .el-table__body-wrapper{
    z-index: 2
  }
</style>
