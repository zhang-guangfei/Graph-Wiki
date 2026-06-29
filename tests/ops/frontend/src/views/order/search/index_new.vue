<template>
  <div class="app-container">
    <!-- 界面 -->
    <el-card>
      <div id="ops-card" ref="ops-card">
        <!-- 接单查询表 -->
        <el-container id="ops-table-rcv">
          <!-- 菜单栏 可左右伸缩-->
          <el-header
            :class="{ 'rcv-search-menu-close': isCollapse, 'rcv-search-menu-open': !isCollapse }"
            style="height: auto;padding: 0 10px">
            <el-row>
              <!--查询界面-->
              <el-form :inline="true">
                <!--第一行-->
                <el-row>
                  <el-col :span="20">
                    <el-form-item>
                      <el-input
                        v-model.trim="searchMenuData.condition.rorderFno"
                        clearable
                        size="small"
                        placeholder="订单号"
                        @keyup.down.native="menuClearCondition"
                        @keyup.enter.native="menuSearchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model.trim="searchMenuData.condition.modelNo"
                        clearable
                        size="small"
                        placeholder="型号"
                        @keyup.enter.native="menuSearchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="searchMenuData.condition.status"
                        style="width: 180px;"
                        clearable
                        size="small"
                        placeholder="处理状态"
                      >
                        <el-option
                          v-for="item in DictData.status"
                          :key="item.id"
                          :value="item.code"
                          :label="item.desc"/>
                      </el-select>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="searchMenuData.condition.intercept"
                        style="width: 180px;"
                        clearable
                        size="small"
                        placeholder="信用拦截"
                      >
                        <el-option
                          key="0"
                          :value="false"
                          label="未拦截"/>
                        <el-option
                          key="1"
                          :value="true"
                          label="已拦截"/>
                      </el-select>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="searchMenuData.condition.prodFlag"
                        clearable
                        size="small"
                        placeholder="拆分类型"
                      >
                        <el-option
                          key="0"
                          value="0"
                          label="不拆分"
                        />
                        <el-option
                          key="1"
                          value="1"
                          label="拆分数量"
                        />
                        <el-option
                          key="2"
                          value="2"
                          label="拆分型号"
                        />

                      </el-select>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="searchMenuData.condition.orderType"
                        clearable
                        size="small"
                        placeholder="订单类型"
                      >
                        <el-option
                          v-for="item in DictData.orderType"
                          :key="item.id"
                          :value="item.code"
                          :label="item.desc"/>
                      </el-select>
                    </el-form-item>
                    <el-form-item label="接单日期">
                      <el-date-picker
                        v-model="searchMenuData.condition.rorddate"
                        :picker-options="pickerOptions"
                        type="daterange"
                        size="small"
                        range-separator="--"
                        start-placeholder="开始日期"
                        end-placeholder="结束日期"
                        default-time
                        style="width: 225px"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4" style="text-align: right">
                    <el-button
                      v-permission="['306452','493846']"
                      type="primary"
                      size="small"
                      @click="menuSearchEvent">搜索
                    </el-button>
                    <el-button
                      v-permission="['306452','493846']"
                      type="primary"
                      size="small"
                      @click="openBatchSearchWindow"
                    >批量
                    </el-button>
                    <el-button
                      v-permission="['306452','493846']"
                      type="primary"
                      size="small"
                      @click="menuResetCondition">重置
                    </el-button>
                    <!--
                    <el-button
                      v-permission="['132053']"
                      type="primary"
                      size="small"
                    >导出
                    </el-button>-->
                    <el-button
                      v-permission="['306452','493846']"
                      type="primary"
                      size="small"
                      @click="changeSearchSize">展开
                    </el-button>
                  </el-col>
                </el-row>
                <!--第二行-->
                <el-row v-if="size.search">
                  <el-col :span="24">
                    <el-form-item>
                      <el-input
                        v-model="searchMenuData.condition.customerNo"
                        clearable
                        size="small"
                        placeholder="客户代码"
                        @keyup.enter.native="menuSearchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model="searchMenuData.condition.userNo"
                        clearable
                        size="small"
                        placeholder="用户代码"
                        @keyup.enter.native="menuSearchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model="searchMenuData.condition.cproductNo"
                        clearable
                        size="small"
                        placeholder="产品代码"
                        @keyup.enter.native="menuSearchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model="searchMenuData.condition.corderNo"
                        clearable
                        size="small"
                        placeholder="客户订单号"
                        @keyup.enter.native="menuSearchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="searchMenuData.condition.stockType"
                        clearable
                        size="small"
                        placeholder="出库区分类别"
                      >
                        <el-option
                          v-for="item in DictData.stockType"
                          :key="item.id"
                          :value="item.code"
                          :label="item.desc"/>
                      </el-select>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="searchMenuData.condition.stockCode"
                        clearable
                        filterable
                        size="small"
                        placeholder="出库区分代码"
                      >
                        <el-option
                          v-for="item in DictData.stockCode"
                          :key="item.id"
                          :value="item.code"
                          :label="item.desc"/>
                      </el-select>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="searchMenuData.condition.dlvEntire"
                        clearable
                        filterable
                        size="small"
                        placeholder="出库方式"
                      >
                        <el-option
                          v-for="item in DictData.dlvEntire"
                          :key="item.id"
                          :value="item.code"
                          :label="item.desc"/>
                      </el-select>
                    </el-form-item>
                    <el-form-item>
                      <department
                        class="menu-department"
                        style="margin-left: 0px;margin-top: 4px;"
                        @handleScopeChange="changeDeptMenuEvent" />
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-form>
            </el-row>
          </el-header>
          <!-- 接单表 -->
          <el-main id="rcv-main" style="overflow: hidden">
            <el-table
              v-loading="tableDataLoading"
              ref="multipleTable"
              :data="tableData.list"
              :cell-class-name="tableCellClassName"
              :header-cell-style="tableStyle"
              :row-style="{height:'33px'}"
              :height="size.rcvTable"
              highlight-current-row
              border
              stripe
              @row-click="refreshOrderItem"
            >
              <!-- 表头字段 -->
              <el-table-column
                v-for="column in tableColumns"
                :fixed="column.fixed"
                :key="column.prop"
                :prop="column.prop"
                :label="column.label"
                :width="column.width"
                :type="column.type"
                :formatter="column.formatter"
                :align="column.align"
                :sortable="true"
                :cell-class-name="column.cellClassName"
                header-align="center"
                show-overflow-tooltip
              />
              <!-- 操作栏 -->
              <el-table-column
                fixed="right"
                label="操作"
                width="180"
                align="center"
                header-align="center">
                <template slot-scope="scope">
                  <el-row>
                    <el-col :span="6">
                      <el-tooltip effect="light" content="订单还原" placement="top">
                        <el-button
                          v-permission="['889622','120794']"
                          v-if="scope.row.enable && scope.row.prodFlag==='0'"
                          size="small"
                          circle
                          icon="el-icon-refresh-left"
                          @click.stop="openResetOrderWindow(scope.row)"/>
                      </el-tooltip>
                    </el-col>
                    <el-col :span="6">
                      <el-tooltip effect="light" content="修改发货方式为随发分批" placement="top">
                        <el-button
                          v-permission="['889622','120794']"
                          v-if="scope.row.enable && scope.row.dlvEntire!== '2' && scope.row.dlvEntire!== '5'"
                          size="small"
                          icon="el-icon-edit"
                          circle
                          @click.stop="modifyDlvEntire(scope.row)"/>
                      </el-tooltip>
                    </el-col>
                    <!-- <el-col :span="6">
                      <el-tooltip effect="light" content="修改子项特发" placement="top">
                        <el-button
                          v-if="scope.row.prodFlag==='2' && ['2','5'].includes(scope.row.dlvEntire)"
                          size="small"
                          icon="el-icon-scissors"
                          circle
                          @click.stop="modifyDlvSplit(scope.row)"/>
                      </el-tooltip>
                    </el-col> -->
                    <el-col :span="6">
                      <el-tooltip effect="light" content="订单取消" placement="top">
                        <el-button
                          v-if="scope.row.enable"
                          size="small"
                          circle
                          icon="el-icon-delete-solid"
                          @click.stop="openCancelOrderWindow(scope.row)"/>
                      </el-tooltip>
                    </el-col>
                    <el-col :span="6">
                      <el-tooltip effect="light" content="订单完纳" placement="top">
                        <el-button
                          v-permission="['533117','590103']"
                          v-if="scope.row.prodFlag !== '2' && scope.row.expQty > 0 && scope.row.expQty < scope.row.quantity"
                          size="small"
                          circle
                          icon="el-icon-switch-button"
                          @click.stop="openFinishOrderWindow(scope.row)"/>
                      </el-tooltip>
                    </el-col>
                    <el-col :span="6">
                      <el-tooltip effect="light" content="附件清单" placement="top">
                        <el-button
                          v-if="scope.row.hasLowPrice === 1"
                          size="small"
                          circle
                          icon="el-icon-files"
                          @click.stop="getFilesInfo(scope.row)"/>
                      </el-tooltip>
                    </el-col>
                    <el-col :span="6">
                      <el-tooltip effect="light" content="新建计划" placement="top">
                        <el-button
                          v-if="scope.row.dlvEntire==='5'"
                          size="small"
                          circle
                          icon="el-icon-plus"
                          @click.stop="openCreateNotifyPlanWindow(scope.row)"/>
                      </el-tooltip>
                    </el-col>
                  </el-row>
                </template>
              </el-table-column>
            </el-table>
          </el-main>
          <!-- 分页按钮 -->
          <el-footer style="height: auto;padding: 0">
            <el-pagination
              ref="ops-table-page"
              :current-page="tableData.pageNum"
              :page-sizes="[20, 50, 100, 200, 500]"
              :page-size="tableData.pageSize"
              :total="tableData.total"
              background
              layout="total, sizes, prev, pager, next"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </el-footer>
        </el-container>
        <!-- 分割线 -->
        <div id="divider"/>
        <!-- 接单详细信息 -->
        <div id="ops-table-rcv-item" ref="ops-table-rcv-item">
          <OrderStatusTable :height="size.rcvItemTable" :order-no="rcvItemCondition" @openReorderWindow="openReorderWindow"/>
        </div>

      </div>
    </el-card>

    <!-- 弹出层 【订单号批量查询】【订单还原，随发分批，订单取消，订单完纳，订单附件下载】【库存转订，发运单信息】  -->
    <!-- 订单号批量查询 -->
    <el-dialog
      :visible.sync="batchSearchDialog.show"
      title="请输入订单号，以回车为间隔"
      width="400px"
    >
      <el-row>
        <el-input
          v-model="batchSearchDialog.text"
          :autosize="{minRows: 10 ,maxRows: 20}"
          type="textarea"
        />
      </el-row>
      <el-row style="text-align: center">
        <el-button
          v-permission="['306452']"
          type="primary"
          size="small"
          @click="batchSearchEvent"
        >查询
        </el-button>
      </el-row>
    </el-dialog>

    <!-- 订单还原-->
    <ResetOrderDialog :visible.sync="orderResetDialog.show" :order-data="orderResetDialog.data" @handleSuccess="resetSuccess"/>

    <!-- 订单取消 -->
    <CancelOrderDialog :visible.sync="orderCancelDialog.show" :order-data="orderCancelDialog.data" @handleSuccess="cancelSuccess"/>

    <!-- 完纳 -->
    <el-dialog :visible.sync="orderFinishDialog.show" width="70%" title="订单完纳" top="10vh">
      <el-divider content-position="left" class="divider_margin">完纳原因</el-divider>
      <!-- 完纳原因 -->
      <el-row style="margin: 10px 10px; min-height: 60px">
        <el-col :span="9">
          <el-cascader
            v-model="orderFinishDialog.reason"
            :options="DictData.cancelReason"
            placeholder="选择原因："
            style="width: 250px;"
            @change="finishReasonSelect"
          />
        </el-col>
        <el-col v-show="orderFinishDialog.other" :span="12">
          <el-input
            v-model="orderFinishDialog.otherReason"
            :autosize="{minRows: 2 }"
            placeholder="输入原因："
            type="textarea"
            style="width: 500px"
          />
        </el-col>
      </el-row>
      <el-divider content-position="left" class="divider_margin">订单状态：</el-divider>
      <el-table
        :data="orderFinishDialog.order.tableData"
        :header-cell-style="tableStyle"
        :cell-style="{padding: '1px 2px'}"
        height="15vh"
        border
        highlight-current-row
      >
        <el-table-column
          v-for="column in orderFinishDialog.order.tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
          :align="column.align"
          header-align="center"
          show-overflow-tooltip
        />
      </el-table>
      <el-divider content-position="left" class="divider_margin">采购单状态</el-divider>
      <el-table
        v-loading="orderFinishDialog.Loading"
        :data="orderFinishDialog.po.tableData"
        :header-cell-style="tableStyle"
        height="25vh"
        highlight-current-row
        border
      >
        <el-table-column
          v-for="column in orderFinishDialog.po.tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :type="column.type"
          :align="column.align"
          :formatter="column.formatter"
          header-align="center"
        />
        <el-table-column
          label="采购单处理"
          align="center"
          header-align="center">
          <template slot-scope="scope">
            <el-checkbox-group v-model="scope.row.doStatus" @change="finishPoChange(scope.row)">
              <el-checkbox :disabled="!scope.row.canDelete" :true-label="1" false-label="0" name="doStatus">删除</el-checkbox>
              <el-checkbox :disabled="!scope.row.canFinish" :true-label="2" false-label="0" name="doStatus">完纳</el-checkbox>
            </el-checkbox-group>
          </template>
        </el-table-column>
        <el-table-column align="center" width="100" label="可完纳数量" >
          <template slot-scope="scope">
            <div v-if="scope.row.checkMsgFlag" class="item-po-msg" > {{ scope.row.arrQty }}~{{ scope.row.pQty }}</div>
            <el-input v-model="scope.row.finishPoQty" :disabled="scope.row.inputDisabled" controls-position="right" type="number" @input="value => finishQtyCheck(scope.row.pQty,scope.row.arrQty,value,scope.row)"/>
          </template>
        </el-table-column>
      </el-table>
      <el-row style="text-align: center; margin-top: 20px;">
        <el-button :loading="orderFinishDialog.finishOrderBtn" type="primary" size="small" style="font-size: 15px" @click="finishOrderExe()">订单完纳</el-button>
        <el-button type="primary" size="small" style="font-size: 15px" @click="closeFinishOrderWindow">取消
        </el-button>
      </el-row>
    </el-dialog>

    <!--订单附件清单下载-->
    <el-dialog
      :visible.sync="orderFileDialog.show"
      :show-close="false"
      :close-on-click-modal="false"
      :close-on-press-escape="false"
      width="30%"
    >
      <el-table :data="orderFileDialog.uploadedFiles" max-height="250" border>
        <el-table-column prop="realFileName" show-overflow-tooltip label="文件名" />
        <el-table-column prop="fileType" show-overflow-tooltip label="文件类型" />
        <el-table-column prop="createTime" show-overflow-tooltip label="上传日期" />
        <el-table-column
          fixed="right"
          label="操作"
          width="100">
          <template slot-scope="scope">
            <el-button type="text" size="small" @click="downLoadFile(scope.row)" >下载</el-button>
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" size="mini" @click="orderFileDialog.show = false">关 闭</el-button>
      </span>
    </el-dialog>

    <!-- 转定 -->
    <el-dialog :visible.sync="reorderDialog.show" width="70%" title="转订库存" top="10vh" @close="closeReorderWindow">
      <el-divider content-position="left" class="divider_margin">当前占用库存</el-divider>
      <el-table
        :data="reorderDialog.InvOriginal.tableData"
        :header-cell-style="tableStyle"
        :cell-style="{padding: '1px 2px'}"
        height="15vh"
        border
        highlight-current-row
        @row-click="selectOriginalInventory"
      >
        <el-table-column align="center" width="55" label="选择">
          <template slot-scope="scope">
            <!-- 可以手动的修改label的值，从而控制选择哪一项 -->
            <el-radio
              v-model="reorderDialog.invOriginalSelection"
              :label="scope.row.index"
              class="radio"
              @click.native.prevent="()=>{}"
            >&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column
          label="删请购单"
          align="center"
          header-align="center">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.deletePo" :disabled="scope.row.lock" @change="handlePoClick(scope.row)"/>
          </template>
        </el-table-column>
        <el-table-column
          label="是否调入专备"
          align="center"
          header-align="center">
          <template slot-scope="scope">
            <el-checkbox v-model="scope.row.transfer" :disabled="scope.row.transferLock" @click.native.prevent="changeHandle(scope.row)"/>
          </template>
        </el-table-column>
        <el-table-column
          label="调入客户代码"
          align="center"
          width="120"
          header-align="center">
          <template slot-scope="scope">
            <el-input v-model="scope.row.endUser" :disabled=" scope.row.transfer===false" clearable />
          </template>
        </el-table-column>
        <el-table-column
          v-for="column in reorderDialog.InvOriginal.tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
          :align="column.align"
          header-align="center"
          show-overflow-tooltip
        />
      </el-table>
      <el-divider content-position="left" class="divider_margin">可用库存</el-divider>
      <el-table
        v-loading="reorderDialog.Loading"
        :data="reorderDialog.InvAvailable.tableData"
        :header-cell-style="tableStyle"
        height="25vh"
        highlight-current-row
        border
        @row-click="selectAvailableInventory">
        <el-table-column align="center" width="55" label="选择">
          <template slot-scope="scope">
            <!-- 可以手动的修改label的值，从而控制选择哪一项 -->
            <el-radio
              v-model="reorderDialog.InvAvailableSelection"
              :label="scope.row.index"
              class="radio"
            >&nbsp;</el-radio>
          </template>
        </el-table-column>
        <el-table-column
          v-for="column in reorderDialog.InvAvailable.tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :type="column.type"
          :align="column.align"
          :formatter="column.formatter"
          header-align="center"
        />
      </el-table>
      <el-row style="text-align: center; margin-top: 20px;">
        <el-button :loading="reorderDialog.reorderOrderBtn" type="primary" size="small" style="font-size: 15px" @click="reorderPoEvent()">转订采购</el-button>
        <el-button :loading="reorderDialog.reorderOrderBtn" type="primary" size="small" style="font-size: 15px" @click="reorderEvent()">转订库存</el-button>
        <el-button type="primary" size="small" style="font-size: 15px" @click="closeReorderWindow">取消
        </el-button>
      </el-row>
    </el-dialog>

    <!-- 发运单信息 -->
    <el-dialog
      :visible.sync="expressInfoDialog.show"
      width="35%"
      title="运单信息"
      top="10vh"
      @close="closeExpressInfoWindow">
      <div style="min-height: 30vh;">
        <el-timeline v-if="expressInfoDialog.content.length>0" :reverse="expressInfoDialog.reverse">
          <el-timeline-item
            v-for="(item, index) in expressInfoDialog.content"
            :key="index"
            :icon="item.icon"
            :type="item.type"
            :color="item.color"
            :size="item.size"
            :timestamp="item.time"
            :class="item.font">
            {{ item.content }}
          </el-timeline-item>
        </el-timeline>
        <el-result v-if="expressInfoDialog.content.length==0" icon="info" title="抱歉" sub-title="没有查询到运单信息"/>
      </div>
    </el-dialog>

    <!-- 通知发货 -->
    <AddDialog :visible.sync="notifyPlanDialog.show" :default-order-fno.sync="notifyPlanDialog.orderFno" :order-data="notifyPlanDialog.data" @handleSuccess="addNotifyPlanSuccess"/>

  </div>
</template>

<script>
import {
  enableCancelRcv,
  findAllHouse,
  findDeptDict,
  findSupplier,
  findReceiveView,
  findInventoryType,
  findRequestPurchaseBySqlitNo,
  showItemZD,
  getRoPartStatus,
  showInvByItemQtyZDV1,
  handleZDToPo,
  handleZD,
  getExpressInfo,
  askOrderFinish,
  exeFinish
} from '@/api/warehouseManage'

import { modifyOrder } from '@/api/order/search'
import { getDataCodesTree } from '@/api/common/dict'
import moment from 'moment'
import Department from '@/components/Department'
import { mapGetters } from 'vuex'
import CancelOrderDialog from '@/views/order/search/CancelOrderDialog'
import ResetOrderDialog from '@/views/order/search/ResetOrderDialog'
import { findAttacheFiledManageInfo, dowmLoadAttacheFileManageInfo } from '@/api/order/sampleOrder'
import OrderStatusTable from '@/views/order/search/OrderStatusTable'
import axios from 'axios'
import AddDialog from '@/views/notifyShipmentPlan/add'

export default {
  name: 'ReceiveOrderSearch',
  components: {
    Department,
    CancelOrderDialog,
    ResetOrderDialog,
    OrderStatusTable,
    AddDialog
  },
  data() {
    return {
      // 【常量】 转定用
      cancelTokenSource: null,
      // 接单查询请求取消句柄
      searchCancelTokenSource: null,
      // 时间选取组件
      pickerOptions: {
        shortcuts: [
          {
            text: '一个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment()
                .startOf('day')
                .subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '三个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment()
                .startOf('day')
                .subtract(3, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '半年',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment()
                .startOf('day')
                .subtract(6, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '一年',
            onClick(picker) {
              const end = moment().format('YYYY-MM-DD')
              const start = moment()
                .startOf('day')
                .subtract(1, 'year')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '向前一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).subtract(1, 'months')
              const start = moment(picker.value[0]).subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '向后一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).add(1, 'months')
              const start = moment(picker.value[0]).add(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '扩大一个月',
            onClick(picker) {
              const end = moment(picker.value[1])
              const start = moment(picker.value[0]).subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          },
          {
            text: '缩小一个月',
            onClick(picker) {
              const end = moment(picker.value[1])
              const start = moment(picker.value[0]).add(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }
        ]
      },
      // 字典数据
      DictData: {
        cancelReason: [
          // 取消菜单
          {
            value: '客户',
            label: '客户',
            children: [
              {
                value: '项目取消',
                label: '项目取消'
              },
              {
                value: '选型错误',
                label: '选型错误'
              },
              {
                value: '对手替换',
                label: '对手替换'
              },
              {
                value: '客户货期提前',
                label: '客户货期提前'
              },
              {
                value: '制造货期延迟',
                label: '制造货期延迟'
              },
              {
                value: '客户订单错误',
                label: '客户订单错误'
              },
              {
                value: '欠款问题',
                label: '欠款问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '代理店',
            label: '代理店',
            children: [
              {
                value: '选型错误',
                label: '选型错误'
              },
              {
                value: '客户订单错误',
                label: '客户订单错误'
              },
              {
                value: '欠款问题',
                label: '欠款问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '营业所',
            label: '营业所',
            children: [
              {
                value: '选型错误',
                label: '选型错误'
              },
              {
                value: '客户订单错误',
                label: '客户订单错误'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '业务处理中心',
            label: '业务处理中心',
            children: [
              {
                value: 'SMC内部订单错误',
                label: 'SMC内部订单错误'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '中国制造通知',
            label: '中国制造通知',
            children: [
              {
                value: '制造货期延迟',
                label: '制造货期延迟'
              },
              {
                value: '错误型号',
                label: '错误型号'
              },
              {
                value: '收敛品',
                label: '收敛品'
              },
              {
                value: '贩卖限制品',
                label: '贩卖限制品'
              },
              {
                value: '产地问题',
                label: '产地问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '日本通知',
            label: '日本通知',
            children: [
              {
                value: '制造货期延迟',
                label: '制造货期延迟'
              },
              {
                value: '组装问题',
                label: '组装问题'
              },
              {
                value: '错误型号',
                label: '错误型号'
              },
              {
                value: '收敛品',
                label: '收敛品'
              },
              {
                value: '贩卖限制品',
                label: '贩卖限制品'
              },
              {
                value: '申请特注',
                label: '申请特注'
              },
              {
                value: 'SHIKOMI不足',
                label: 'SHIKOMI不足'
              },
              {
                value: '产地问题',
                label: '产地问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '业务课',
            label: '业务课',
            children: [
              {
                value: '错误型号',
                label: '错误型号'
              },
              {
                value: '收敛品',
                label: '收敛品'
              },
              {
                value: '贩卖限制品',
                label: '贩卖限制品'
              },
              {
                value: '申请特注',
                label: '申请特注'
              },
              {
                value: '海关不可进口',
                label: '海关不可进口'
              },
              {
                value: 'SHIKOMI不足',
                label: 'SHIKOMI不足'
              },
              {
                value: '产地问题',
                label: '产地问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          }
        ],
        status: [],
        orderType: [],
        department: [],
        dlvEntire: [],
        stockType: [],
        stockCode: [],
        warehouse: [],
        supplier: [],
        warehouseAndSupplier: [],
        warehouseName: [],
        specMark: [],
        expDlvType: [],
        inventoryType: [],
        inventoryStatus: [],
        requestStatus: [],
        purchaseStatus: []
      },

      // 【rcv查询菜单】
      // 菜单数据
      searchMenuData: {
        condition: {
          orderType: '1',
          status: '2',
          intercept: null,
          rorddate: [
            moment()
              .startOf('day')
              .subtract(1, 'months'),
            moment().startOf('day')
          ]
        },
        pageNumber: 1,
        pageSize: 20,
        orderBy: 'rorddate desc, rorder_item asc '
      },

      // 【rcv表数据】
      // rcv查询条件暂存
      searchParam: {},
      // rcv表表头字段
      tableColumns: [
        {
          label: '营业所',
          prop: 'deptNo',
          fixed: 'left',
          formatter: this.deptFormatter,
          width: 120,
          align: 'center'
        },
        {
          label: '接单号',
          prop: 'rorderFno',
          width: 120,
          fixed: 'left'
        },
        {
          label: '型号',
          prop: 'modelNo',
          width: 150
        },
        {
          label: '数量',
          prop: 'quantity',
          width: 80,
          align: 'right'
        },
        {
          label: '状态',
          prop: 'status',
          width: 90,
          formatter: this.statusFormatter
        },
        {
          label: '信用拦截',
          prop: 'intercept',
          width: 120,
          formatter: this.interceptFormatter,
          cellClassName: ({ row }) => {
            if (row.intercept) {
              return 'intercept-red'
            }
            return ''
          }
        },
        {
          label: '客户代码',
          prop: 'customerNo',
          width: 120
        },
        {
          label: '用户代码',
          prop: 'userNo',
          width: 120
        },
        {
          label: '最终用户',
          prop: 'endUser',
          width: 120
        },
        {
          label: '出库区分',
          width: 120,
          formatter: row => {
            if (!row.stockType) {
              return ''
            }
            var stockType = this.stockTypeFormatter(row.stockType)
            if (row.stockCode) {
              return stockType + '【' + row.stockCode + '】'
            } else if (row.stockType === 'NA') {
              return ''
            } else if (row.stockType) {
              return stockType
            } else {
              return ''
            }
          }
        },
        {
          label: '拆分',
          width: 120,
          formatter: row => {
            if (row.prodFlag === '0') {
              return '不拆分'
            } else if (row.prodFlag === '1') {
              return '拆分数量'
            } else if (row.prodFlag === '2') {
              return '拆分型号'
            } else {
              return ''
            }
          }
        },
        {
          label: '货齐数',
          prop: 'readyQty',
          width: 100,
          align: 'right'
        },
        {
          label: '已发数',
          prop: 'expQty',
          width: 100,
          align: 'right'
        },
        {
          label: '已退数',
          prop: 'returnedQty',
          width: 100,
          align: 'right'
        },
        {
          label: '开票数',
          prop: 'invoiceQty',
          width: 100,
          align: 'right'
        },
        {
          label: '订单类型',
          prop: 'orderType',
          width: 150,
          formatter: this.orderTypeFormatter
        },
        {
          label: '接单日期',
          prop: 'rorddate',
          width: 120,
          formatter: this.dateTimeFormatter
        },
        {
          label: '交货日期',
          prop: 'dlvDate',
          width: 120,
          formatter: this.dateFormatter
        },
        {
          label: '出货方式',
          prop: 'dlvEntire',
          width: 150,
          formatter: this.dlvEntireFormatter
        },
        /* {
          label: '货齐时间',
          prop: 'readyTime',
          width: 120,
          formatter: this.dateTimeFormatter
        },*/
        {
          label: '预计出库时间',
          prop: 'expectedDeliveryTime',
          width: 150,
          formatter: this.dateFormatter
        },
        {
          label: '发货日期',
          prop: 'shipTime',
          width: 120,
          formatter: this.dateFormatter
        },
        {
          label: '物流出库日',
          prop: 'wmsDlvDate',
          width: 120,
          formatter: this.dateFormatter
        },
        /* {
          label: '预计送达日',
          prop: 'estimatedDeliveryDay',
          width: 120,
          formatter: this.dateFormatter
        },*/
        {
          label: '是否组装',
          prop: 'specMark',
          width: 120,
          formatter: this.specMarkFormatter
        },
        {
          label: '特殊标识',
          prop: 'expDlvType',
          width: 120,
          formatter: this.expDlvTypeFormatter
        },
        {
          label: '订单备注',
          width: 120,
          prop: 'remark'
        },
        {
          label: '交易归属',
          prop: 'tradeCompanyid',
          width: 200
        },
        {
          label: 'ppl号',
          prop: 'pplNo',
          width: 140
        },
        {
          label: 'project号',
          prop: 'projectNo',
          width: 140
        },
        {
          label: 'shikomi号',
          prop: 'shikomiNo',
          width: 140
        },
        {
          label: '营业情报号',
          prop: 'salesInfoNo',
          width: 140
        },
        {
          label: '客户订单号',
          prop: 'corderNo',
          width: 140
        }
      ],
      // rcv表数据
      tableData: {},
      tableDataLoading: false,

      // 订单明细表查询条件
      rcvItemCondition: {},

      // 【弹出层数据】
      // rcv表批量查询
      batchSearchDialog: {
        show: false,
        text: ''
      },

      // 通知发货
      notifyPlanDialog: {
        show: false,
        orderFno: '',
        data: {}
      },
      // 订单还原
      orderResetDialog: {
        show: false,
        data: {}
      },
      // 订单取消
      orderCancelDialog: {
        show: false,
        data: {}
      },
      // 订单完纳
      orderFinishDialog: {
        show: false,
        reason: '',
        finishOrderBtn: false,
        other: false, // 其他原因输入框显示
        otherReason: '', // 其他原因的文本
        order: {
          query: {},
          tableData: [],
          tableColumns: [
            {
              label: '订单号',
              prop: 'orderFoNo',
              width: 200
            },
            {
              label: '型号',
              prop: 'modelNO',
              width: 200
            },
            {
              label: '数量',
              prop: 'orderQty',
              width: 100
            },
            {
              label: '已发数量',
              prop: 'outQty',
              width: 100
            },
            {
              label: '可完纳数量',
              prop: 'finishQty',
              width: 100
            }
          ]
        },
        po: {
          query: {},
          tableData: [],
          tableColumns: [
            {
              label: '采购单号',
              prop: 'pFullNo',
              width: 200
            },
            {
              label: '型号',
              prop: 'modelNo'
            },
            {
              label: '数量',
              prop: 'pQty',
              width: 100
            },
            {
              label: '已发数量',
              prop: 'arrQty',
              width: 100
            },
            {
              label: '供应商',
              prop: 'supplierId',
              width: 100
            },
            {
              label: '状态',
              prop: 'poStatus'
            },
            {
              label: '提示',
              prop: 'msg'
            }
          ]
        }
      },
      // 订单附件下载
      orderFileDialog: {
        show: false,
        uploadedFiles: [],
        info: {
          filePath: '',
          randomFileName: ''
        },
        opsAttachedFileManageVO: {
          businessKeyValue: ''
        }
      },

      // 库存转订弹框
      reorderDialog: {
        show: false,
        deletePo: false,
        transfer: false,
        InvAvailableSelection: '',
        reorderOrderBtn: false,
        InvOriginal: {
          query: {},
          tableData: [],
          tableColumns: [
            {
              type: 'index',
              label: '序号',
              prop: 'index',
              align: 'right',
              width: 50
            },
            {
              label: '型号',
              prop: 'modelNo',
              width: 200
            },
            {
              label: '库存状态',
              prop: 'pageShowInvTableType',
              formatter: this.inventoryStatusFormatter
            },
            {
              label: '仓库',
              prop: 'warehouseCode',
              formatter: this.stockCodeFormatter,
              width: 150
            },
            {
              label: '供应商',
              prop: 'supplierId',
              width: 120,
              formatter: this.supplierFormatter
            },
            {
              label: '库存属性',
              prop: 'inventoryTypeCode',
              formatter: this.inventoryTypeFormatter
            },
            {
              label: '是否可转定',
              prop: 'actionMsg',
              width: 200
            },
            {
              label: '占用数量',
              prop: 'qty',
              align: 'right'
            },
            {
              label: '发票号',
              prop: 'invoiceno',
              width: 150
            },
            {
              label: '采购单号',
              prop: 'associateNo',
              width: 150
            },
            {
              label: '采购单项号',
              prop: 'associateNoItem',
              width: 150
            },
            {
              label: '采购单拆分号',
              prop: 'associateNoSplitno',
              width: 150
            }
          ]
        },
        InvAvailable: {
          query: {},
          tableData: [],
          tableColumns: [
            {
              label: '序号',
              prop: 'index',
              align: 'right',
              width: 50
            },
            {
              label: '仓库名称',
              prop: 'warehouseName',
              width: 200
            },
            {
              label: '型号',
              prop: 'modelno',
              width: 200
            },
            {
              label: '库存状态',
              prop: 'inventoryStatus',
              formatter: this.inventoryStatusFormatter
            },
            {
              label: '库存属性',
              prop: 'inventoryTypeCode',
              formatter: this.inventoryTypeFormatter
            },
            {
              label: '大口',
              prop: 'dk'
            },
            {
              label: '客户代码',
              prop: 'customerNo'
            },
            {
              label: 'ppl',
              prop: 'ppl'
            },
            {
              label: '项目代码',
              prop: 'projectCode'
            },
            {
              label: '集团客户代码',
              prop: 'groupCustomerNo',
              width: 120
            },
            {
              label: '营业情报号',
              prop: 'salesInfoNo',
              width: 120
            },
            {
              label: '发票号',
              prop: 'invoiceNo'
            },
            {
              label: '预计到货日期',
              prop: 'preReceiveDate',
              width: 120,
              formatter: this.dateFormatter
            },
            {
              label: '关联单号',
              width: 120,
              formatter: row => {
                var associate = ''
                if (row.associateNo !== null) {
                  associate = row.associateNo
                } else {
                  return associate
                }
                if (row.associateItemNo !== null) {
                  associate = associate + '-' + row.associateItemNo
                }
                if (row.associateSplitNo !== null && row.associateSplitNo !== 0) {
                  associate = associate + '-' + row.associateSplitNo
                }
                return associate
              }
            },

            {
              label: '可用数量',
              prop: 'availableQuantity',
              align: 'right'
            }
          ]
        },
        reorderObject: {}
      },

      // 发运订单弹框信息
      expressInfoDialog: {
        show: false,
        content: [],
        reverse: false
      },

      // 表格大小布局
      size: {
        search: false,
        rcvTable: '50vh',
        rcvItemTable: 0
      },
      tableStyle: {
        backgroundColor: 'rgb(117, 144, 168)',
        color: 'rgba(253, 253, 253, 0.938)',
        padding: '6px',
        fontSize: '14px'
      }
    }
  },
  computed: {
    ...mapGetters(['sidebar']),
    isCollapse() {
      return !this.sidebar.opened
    },
    dynamicWidth() {
      return this.isCollapse() ? '180px' : '150px'
    }
  },
  watch: {
    'searchMenuData.condition.stockType': {
      handler: function(newObj) {
        this.DictData.stockCode = []
        var stockCodeMenu = []
        if (newObj === 'CG') {
          stockCodeMenu = this.DictData.supplier
        } else if (newObj === 'T') {
          stockCodeMenu = this.DictData.warehouse
        } else if (newObj === 'N') {
          stockCodeMenu = this.DictData.warehouse
        }
        stockCodeMenu.forEach(item => {
          this.DictData.stockCode.push({ code: item.code, desc: item.desc + '【' + item.code + '】' })
        })
      },
      immediate: true // 调用handler方法以初始化表格数据
    }
  },
  created() {
    // 字典初始化
    // -- rcv字典  --
    // rcv接单状态
    getDataCodesTree('1001')
      .then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            // 过滤掉信用拦截状态(code=12)，信用拦截已独立为单独筛选条件
            if (dict.code !== '12') {
              this.DictData.status.push({ code: dict.code, desc: dict.codeName })
            }
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
    // 订单类型
    getDataCodesTree('1002')
      .then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.orderType.push({
              code: dict.code,
              desc: dict.codeName
            })
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
    // 营业所
    findDeptDict().then(result => {
      result.forEach(dict => {
        this.DictData.department.push({
          code: dict.deptId,
          desc: dict.deptName
        })
      })
    })
    // 出货方式
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
    // 出库区分类别
    getDataCodesTree('4306')
      .then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.stockType.push({
              code: dict.code,
              desc: dict.codeName
            })
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
    // 供应商
    findSupplier().then(result => {
      result.content.forEach(dict => {
        this.DictData.supplier.push({ code: dict.id, desc: dict.name })
        this.DictData.warehouseAndSupplier.push({
          code: dict.id,
          desc: dict.name
        })
      })
    })
    // 仓库名称
    findAllHouse().then(result => {
      result.data.forEach(dict => {
        this.DictData.warehouse.push({
          code: dict.warehouseCode,
          desc: dict.warehouseName
        })
        this.DictData.warehouseAndSupplier.push({
          code: dict.warehouseCode,
          desc: dict.warehouseName
        })
      })
    })
    // 是否组装（阀与汇流板标识）
    getDataCodesTree('4401')
      .then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.specMark.push({
              code: dict.code,
              desc: dict.codeName
            })
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
    // 特殊标识
    getDataCodesTree('1017')
      .then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.expDlvType.push({
              code: dict.code,
              desc: dict.codeName
            })
          })
        }
      })
      .catch(error => {
        console.log(error)
      })
    // --弹窗字典--
    // 库存类别
    findInventoryType()
      .then(res => {
        res.data.forEach(dict => {
          this.DictData.inventoryType.push({
            code: dict.inventoryTypeCode,
            desc: dict.description
          })
        })
      })
      .catch(res => {
        this.smcErrorMsg(res.message)
      })
    // 库存状态
    getDataCodesTree('4010')
      .then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.inventoryStatus.push({
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
  mounted() {
    // 调节明细表高度
    const _this = this
    this.$nextTick(() => {
      const divider = document.getElementById('divider')
      _this.size.rcvItemTable = document.getElementById('ops-card').offsetHeight - divider.offsetTop - 10
      divider.onmousedown = function(e) {
        document.onmousemove = function(e) {
          const newY = e.offsetY - 10
          document.getElementById('ops-table-rcv').style.height = newY + 'px'
          return false
        }
        document.onmouseup = function() {
          const rcvMain = document.getElementById('rcv-main')
          _this.size.rcvTable = rcvMain.offsetHeight
          _this.size.rcvItemTable = document.getElementById('ops-card').offsetHeight - divider.offsetTop - 10
          document.onmousemove = null
          document.onmouseup = null
        }
        return false
      }
    })
  },
  beforeDestroy() {
    this.cancelSearchRequest('页面销毁，取消接单查询')
  },
  methods: {
    cancelSearchRequest(message = '取消上一次接单查询') {
      if (this.searchCancelTokenSource) {
        this.searchCancelTokenSource.cancel(message)
        this.searchCancelTokenSource = null
      }
    },
    // 【查询菜单】
    // 清除其他属性
    menuClearCondition() {
      // 只保留完整订单号条件
      this.searchMenuData.condition = {
        rorderFno: this.searchMenuData.condition.rorderFno
      }
    },
    // 重置查询表单
    menuResetCondition() {
      // 只保留接单日期条件
      this.searchMenuData.condition = {
        rorddate: [new Date().getTime() - 3600 * 1000 * 24 * 30, new Date()]
      }
    },

    // 【查询参数和结果回更】
    // 菜单查询客户订单
    menuSearchEvent() {
      // 查询前校验，获取查询条件：接单日期和完整订单号
      const date = this.searchMenuData.condition.rorddate
      const rorderFno = this.searchMenuData.condition.rorderFno
      const emptyDate = date === undefined || date === null || date.length === 0
      const emptyOrderFno = rorderFno == null || rorderFno === '' || rorderFno.length < 7
      // 要么有查询日期，要么有接单号大于七位
      if (emptyOrderFno && emptyDate) {
        this.smcInfoMsg('请填写至少七位订单号或选择接单日期')
        return false
      }
      // 构造查询条件
      /** 此为菜单组件所在的数据对象
       * searchParam: {
       *         condition: {
       *           orderType: '1',
       *           status: '2',
       *           rorddate: [
       *             moment().startOf('day').subtract(1, 'months'),
       *             moment().startOf('day')
       *           ]
       *         },
       *         pageNumber: 1,
       *         pageSize: 20,
       *         orderBy: 'rorddate desc, rorder_item asc '
       *       },
       */
      var condition = JSON.parse(JSON.stringify(this.searchMenuData.condition))
      // 信用拦截筛选条件直接从下拉框取值，无需额外转换
      // intercept: null=未选择, false=未拦截, true=已拦截
      // 清理空字符串（clearable可能产生''），统一转为null
      if (condition.intercept === '' || condition.intercept === undefined) {
        condition.intercept = null
      }
      // 暂存到接口查询条件
      this.searchParam = {
        condition,
        pageNumber: this.searchMenuData.pageNumber,
        pageSize: this.searchMenuData.pageSize,
        orderBy: this.searchMenuData.orderBy
      }
      this.getRcvTableData()
      this.clearOrderItem()
    },
    // 批量查询操作
    batchSearchEvent() {
      const text = this.batchSearchDialog.text
      if (!text || text.replace(/\s*/g, '').length < 7) {
        this.smcInfoMsg('请填写至少一个订单号')
        return false
      }
      // 用回车分割
      const orderFno = text.split('\n')
      var condition = { rorderFnoList: orderFno }
      this.searchParam = this.searchParam = {
        // 暂存到接口查询条件
        condition,
        pageNumber: this.searchMenuData.pageNumber,
        pageSize: this.searchMenuData.pageSize,
        orderBy: this.searchMenuData.orderBy
      }
      this.batchSearchDialog.show = false
      this.getRcvTableData()
      this.clearOrderItem()
    },
    // 查询rcv数据，需要查询条件已准备好
    getRcvTableData() {
      /** 报文格式
       * searchParam: {
       *   "condition": {},
       *   "pageNumber": 0,
       *   "pageSize": 0,
       *   "orderBy": ""
       * }
       */
      this.cancelSearchRequest('发起新的接单查询')
      const currentCancelSource = axios.CancelToken.source()
      this.searchCancelTokenSource = currentCancelSource
      this.tableDataLoading = true
      // 订单信息查询接口
      findReceiveView(this.searchParam, currentCancelSource.token)
        .then(async res => {
          if (this.searchCancelTokenSource !== currentCancelSource) {
            return
          }
          if (res.success) {
            const tableData = res.data || {}
            const list = Array.isArray(tableData.list) ? tableData.list : []
            list.forEach(item => {
              item.enable = 'loading'
            })
            this.tableData = tableData
            // 查询能否删单标识
            for (const item of list) {
              const enable = await this.enableCancel(item)
              if (this.searchCancelTokenSource !== currentCancelSource) {
                return
              }
              item.enable = enable
            }
          } else {
            this.smcErrorMsg(res.message)
          }
        })
        .catch(error => {
          if (this.searchCancelTokenSource !== currentCancelSource || axios.isCancel(error)) {
            return
          }
          this.smcErrorMsg('接单查询请求失败：' + error.toString())
        })
        .finally(() => {
          if (this.searchCancelTokenSource === currentCancelSource) {
            this.tableDataLoading = false
            this.searchCancelTokenSource = null
          }
        })
    },
    // 能否删单判断
    async enableCancel(rcv) {
      let result = false
      await enableCancelRcv(rcv).then(res => {
        if (res.success) {
          result = res.data
        }
      })
      return result
    },

    // 点击rcv行【刷新接单明细表】
    refreshOrderItem(row) {
      this.rcvItemCondition = {
        orderId: row.rorderNo,
        orderItem: row.rorderItem,
        dlvEntire: row.dlvEntire
      }
    },
    clearOrderItem() {
      this.rcvItemCondition = {}
    },
    // 刷新接单表数据
    refreshRcvTable() {
      this.getRcvTableData()
      this.refreshOrderItem(this.rcvItemCondition)
    },

    // 【弹出层】
    // 【批量查询】
    openBatchSearchWindow() {
      this.batchSearchDialog.show = true
    },

    openCreateNotifyPlanWindow(row) {
      this.notifyPlanDialog.show = true
      this.notifyPlanDialog.orderFno = row.rorderFno
    },

    addNotifyPlanSuccess() {},

    // 【还原】
    // 打开订单还原弹窗
    openResetOrderWindow(row) {
      this.orderResetDialog.show = true
      this.orderResetDialog.data = row
    },
    // 还原成功后刷新数据
    resetSuccess(msg) {
      this.refreshRcvTable()
    },
    modifyDlvSplit(rcv) {
      var data = {
        orderId: rcv.rorderNo,
        orderItem: rcv.rorderItem,
        master: false,
        dlvSplit: true,
        userDto: {
          userName: '接单查询子项特发'
        },
        reason: '接单查询子项特发'
      }

      var msg = '是否将' + rcv.rorderFno + '变更为子项特发?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        modifyOrder(data).then(res => {
          if (res.success) {
            this.smcInfoMsg(res.message)
            this.getRcvTableData()
            this.clearOrderItem()
          } else {
            this.smcErrorMsg(res.message)
          }
        })
      })
    },

    // 【修改随发分批】
    modifyDlvEntire(rcv) {
      var data = {
        orderId: rcv.rorderNo,
        master: true,
        dlvEntire: '2',
        userDto: {
          userName: '接单查询修改发货方式'
        },
        reason: '接单查询修改发货方式'
      }

      var msg = '是否将主单：' + rcv.rorderNo + '的发货方式变更为随发分批?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        modifyOrder(data).then(res => {
          if (res.success) {
            this.smcInfoMsg(res.message)
            this.getRcvTableData()
            this.clearOrderItem()
          } else {
            this.smcErrorMsg(res.message)
          }
        })
      })
    },
    // 【取消】
    // 打开订单取消弹窗
    openCancelOrderWindow(row) {
      this.orderCancelDialog.show = true
      this.orderCancelDialog.data = row
    },
    // 取消成功后刷新数据
    cancelSuccess(msg) {
      this.refreshRcvTable()
    },
    // 【完纳】
    openFinishOrderWindow(row) {
      this.orderFinishDialog.Loading = false
      this.orderFinishDialog.order.query = {}
      this.orderFinishDialog.order.tableData = []
      this.orderFinishDialog.po.query = {}
      this.orderFinishDialog.po.tableData = []
      this.orderFinishDialog.show = true
      this.orderFinishDialog.order.finishQty = null
      this.orderFinishDialog.order.msg = null
      this.orderFinishDialog.otherReason = null
      this.orderFinishDialog.other = false
      this.orderFinishDialog.reason = null
      const cancelFinishForOrderDto = {
        orderFulNo: row.rorderFno,
        orderNo: row.rorderNo,
        orderItem: String(row.rorderItem),
        modelNo: row.modelNo
      }

      askOrderFinish(cancelFinishForOrderDto)
        .then(res => {
          if (res.success) {
            this.orderFinishDialog.order.tableData.push(res.data)
            this.orderFinishDialog.po.tableData = res.data.poList
          } else {
            this.smcErrorMsg(res.message)
          }
        })
        .catch(() => {
          this.smcErrorMsg('请求失败')
        })
    },
    finishOrderExe() {
      // 执行订单完纳按钮
      if (this.orderFinishDialog.order.tableData[0] == null) {
        this.smcErrorMsg('无可完纳订单')
        return
      }
      if (this.orderFinishDialog.reason == null) {
        this.smcErrorMsg('请填写完纳原因')
        return
      }
      const fQty = this.orderFinishDialog.order.tableData[0].finishQty
      const oQty = this.orderFinishDialog.order.tableData[0].orderQty
      const outQty = this.orderFinishDialog.order.tableData[0].outQty
      var reason = this.orderFinishDialog.reason[1] === '其他' ? this.orderFinishDialog.otherReason : this.orderFinishDialog.reason[1]
      this.orderFinishDialog.order.tableData[0].msg = reason
      var exeFlag = true
      var poFlag = false
      if (fQty < outQty || fQty >= oQty) {
        exeFlag = false
      } else {
        if (this.orderFinishDialog.po.tableData != null) {
          this.orderFinishDialog.po.tableData.forEach(item => {
            if (item.doStatus === 2) {
              // 完纳采购单
              const pQty = item.pQty
              const arrQty = item.arrQty
              const fQty = item.finishPoQty
              if (fQty >= arrQty && fQty < pQty) {
                item.finishMsg = reason
                poFlag = true
              } else {
                exeFlag = false
              }
            }
            if (item.doStatus === 1) {
              // 删采购单
              poFlag = true
            }
          })
        }
      }
      if (!exeFlag) {
        this.smcErrorMsg('订单完纳,完纳数量不在区间范围')
      } else {
        var cancelFinishForOrderDto = this.orderFinishDialog.order.tableData[0]
        cancelFinishForOrderDto.userName = this.$store.getters.name
        cancelFinishForOrderDto.poList = null
        if (poFlag) {
          cancelFinishForOrderDto.poList = this.orderFinishDialog.po.tableData
        }
        this.orderFinishDialog.finishOrderBtn = true
        exeFinish(cancelFinishForOrderDto)
          .then(res => {
            this.orderFinishDialog.finishOrderBtn = false
            if (res.success) {
              this.smcInfoMsg(res.message)
              this.closeFinishOrderWindow()
            } else {
              this.smcErrorMsg(res.message)
            }
          })
          .catch(() => {
            this.smcErrorMsg('请求失败')
          })
      }
    },
    finishPoChange(row) {
      // 采购完纳列表 复选框
      if (row.doStatus === 2) {
        row.inputDisabled = false
      } else {
        row.inputDisabled = true
        row.finishPoQty = null
        row.finishMsg = null
        row.checkMsgFlag = false
      }
    },
    finishQtyCheck(oQty, outQty, value, row) {
      // 完纳数量校验
      if (value < oQty && value >= outQty) {
        row.checkMsgFlag = false
      } else {
        row.checkMsgFlag = true
      }
    },
    // 取消选项弹出输入框
    finishReasonSelect() {
      this.orderFinishDialog.other = this.orderFinishDialog.reason[1] === '其他'
    },
    closeFinishOrderWindow() {
      // 完纳取消弹框
      this.orderFinishDialog.order.query = {}
      this.orderFinishDialog.order.tableData = []
      this.orderFinishDialog.po.query = {}
      this.orderFinishDialog.po.tableData = []
      this.orderFinishDialog.show = false
      this.orderFinishDialog.order.finishQty = null
      this.orderFinishDialog.order.msg = null
      this.orderFinishDialog.otherReason = null
      this.orderFinishDialog.other = false
      this.orderFinishDialog.reason = null
    },
    // 【附件清单】
    getFilesInfo(row) {
      this.orderFileDialog.opsAttachedFileManageVO.businessKeyValue = row.rorderFno
      findAttacheFiledManageInfo(this.orderFileDialog.opsAttachedFileManageVO).then(res => {
        if (res.success) {
          this.orderFileDialog.uploadedFiles = res.content
        }
      })
      this.orderFileDialog.show = true
    },
    downLoadFile(row) {
      console.log('row ==> ', row)
      this.$message.success('已开始下载，请耐心等待')
      this.orderFileDialog.info.randomFileName = row.randomFileName
      this.orderFileDialog.info.filePath = row.filePath
      dowmLoadAttacheFileManageInfo(this.info)
        .then(res => {
          console.log('size=> ', res)
          if (res.size === 0 || res === undefined) {
            this.$message({
              duration: 4000,
              message: '下载失败',
              type: 'error'
            })
            return
          }
          const fileName = row.realFileName
          const blob = new Blob([res], { type: res.type })
          const link = document.createElement('a')
          link.style.display = 'none'
          link.href = window.URL.createObjectURL(blob)
          link.download = fileName
          document.body.appendChild(link)
          link.click()
          window.URL.revokeObjectURL(link.href)
          document.body.removeChild(link)
        })
        .catch(error => {
          console.error(error)
        })
    },
    // 【转订】
    // 打开订单转订弹窗
    openReorderWindow(row, index) {
      this.reorderDialog.Loading = false
      this.reorderDialog.invOriginalSelection = 0
      this.reorderDialog.InvAvailableSelection = 0
      this.reorderDialog.InvOriginal.query = {}
      this.reorderDialog.InvOriginal.tableData = []
      this.reorderDialog.InvAvailable.query = {}
      this.reorderDialog.InvAvailable.tableData = []
      this.reorderDialog.reorderObject = {}
      this.reorderDialog.show = true
      this.reorderDialog.purchaseTableData = []
      this.reorderDialog.poAllTableData = []
      this.reorderDialog.InvOriginal.query = row
      showItemZD(row)
        .then(res => {
          if (res.success) {
            this.reorderDialog.InvOriginal.tableData = res.data
            // 采购取消相关
            // 传入十三位单号 等待采购更新接口
            var cgSplitNo = 0
            if (row.splitType === '2') {
              cgSplitNo = row.pcoItem
            } else {
              cgSplitNo = row.splitNo
            }
            const poObj = {
              orderId: row.orderId,
              orderItem: row.orderItem,
              splitNo: cgSplitNo
            }
            // 采购删除复选框
            findRequestPurchaseBySqlitNo(poObj)
              .then(res => {
                if (!res.success) {
                  this.reorderDialog.InvOriginal.tableData.forEach(item => {
                    item.index = index++
                    item.deletePo = false
                    item.lock = true
                    item.transfer = false
                    item.transferLock = true
                    this.$forceUpdate()
                  })
                }
                if (res.success) {
                  if (res.data === null || res.data.length === 0) {
                    this.reorderDialog.InvOriginal.tableData.forEach(item => {
                      item.index = index++
                      item.deletePo = false
                      item.lock = true
                      item.transfer = false
                      item.transferLock = true
                      this.$forceUpdate()
                    })
                  } else {
                    this.reorderDialog.poAllTableData = res.data
                    this.reorderDialog.InvOriginal.tableData.forEach(item => {
                      item.index = index++
                      item.deletePo = false // 采购复选框默认不选择中
                      item.transferLock = true
                      item.transfer = false // 调库计划复选框默认不选择中
                      item.lock = true // 采购复选框不可选
                      this.$forceUpdate()
                      // 可转定
                      if (item.actionFlag) {
                        this.reorderDialog.poAllTableData.forEach(poItem => {
                          if (poItem.requestSplitno == null) {
                            poItem.requestSplitno = 0
                          }
                          var cgNo = 0
                          if (item.assModleFlag) {
                            cgNo = item.assNum
                          } else {
                            cgNo = item.num
                          }
                          if (cgNo === poItem.requestSplitno) {
                            // 无po 可删 处理中
                            if (poItem.status === '1' && poItem.purchaseno == null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = true // 采购复选框默认不选择中
                              item.transferLock = true
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 无po 可删 处理中
                            if (poItem.status === '2' && poItem.purchaseno == null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = true // 采购复选框默认不选择中
                              item.transferLock = true
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 无po 可删 拦截
                            if (poItem.status === '4' && poItem.purchaseno == null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = true // 采购复选框默认不选择中
                              item.transferLock = true
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 无po 可删 拦截
                            if (poItem.status === '5' && poItem.purchaseno == null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = true // 采购复选框默认不选择中
                              item.transferLock = true
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 有po 已发送 不可删
                            if (poItem.status === '1' && poItem.purchaseno != null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = false
                              item.transfer = false // 调库计划复选框默认不选择
                            }
                            // 有po 已接单 可选择删除
                            if (poItem.status === '2' && poItem.purchaseno != null) {
                              item.lock = false // 采购复选框不可选
                              item.deletePo = true // 采购复选框默认不选择中
                              item.transferLock = true
                              item.transfer = false // 调库计划复选框默认不选择
                            }
                            // 有po 运输中 不可选择
                            if (poItem.status === '3' && poItem.purchaseno != null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = false
                              item.transfer = false // 调库计划复选框默认不选择
                            }
                            // 有po 已完成 不可选择
                            if (poItem.status === '5' && poItem.purchaseno != null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = false
                              item.transfer = false // 调库计划复选框默认不选择
                            }
                            //  如果为合并采购，不可删
                            if (poItem.merge) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = false
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 有po 运输中 判断ro是否是部分收货，如果是部分收货，那么不可调库
                            if (poItem.status === '3' && poItem.purchaseno != null) {
                              getRoPartStatus(poItem).then(res => {
                                if (res.success && res.data) {
                                  item.transferLock = true
                                  item.transfer = false // 调库计划复选框默认不选择
                                  this.$forceUpdate()
                                }
                              })
                            }
                            // 非合并采购 如果采购单号和订单号不匹配不可删单不可勾选
                            if (!poItem.merge && item.associateNo != null && item.orderId !== poItem.requestno) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = true
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                          }
                          // 关联在库不可删 不可调专备
                          if (item.invTableType === 'N') {
                            item.lock = true // 采购复选框不可选
                            item.deletePo = false // 采购复选框默认不选择中
                            item.transferLock = true
                            item.transfer = false // 调库计划复选框默认不选择中
                          }
                        })
                      }
                    })
                    this.$forceUpdate()
                  }
                }
              })
              .catch()
          } else {
            this.smcErrorMsg(res.message)
          }
        })
        .catch(() => {
          this.smcErrorMsg('请求失败')
        })
    },
    closeReorderWindow() {
      this.reorderDialog.show = false
      this.reorderDialog.InvOriginal.query = {}
      this.reorderDialog.InvOriginal.tableData = []
      this.reorderDialog.InvAvailable.query = {}
      this.reorderDialog.InvAvailable.tableData = []
      this.reorderDialog.reorderObject = {}
      if (this.cancelTokenSource) {
        this.cancelTokenSource.cancel('用户关闭了弹框')
        this.cancelTokenSource = null
      }
    },
    selectOriginalInventory(row) {
      // 选择当前行不操作
      if (this.reorderDialog.invOriginalSelection === row.index) {
        return
      }
      // 转定点击原始转定关联条目
      this.reorderDialog.invOriginalSelection = row.index
      this.reorderDialog.reorderObject.fromDto = {}
      this.reorderDialog.InvAvailable.query.orderId = row.orderId
      this.reorderDialog.InvAvailable.query.orderItem = row.orderItem
      this.reorderDialog.InvAvailable.query.qty = row.qty
      this.reorderDialog.InvAvailable.query.modelNo = row.modelNo
      this.reorderDialog.reorderObject.fromDto = row
      if (row.actionFlag) {
        // 选择可转库存
        this.reorderDialog.Loading = true
        this.cancelTokenSource = axios.CancelToken.source()
        showInvByItemQtyZDV1(this.reorderDialog.InvAvailable.query, this.cancelTokenSource.token).then(res => {
          if (res.success) {
            this.reorderDialog.Loading = false
            if (res.data.length > 0) {
              this.reorderDialog.InvAvailable.tableData = res.data
              // 过滤掉当前占用库存
              const invId = row.invId
              const invTableType = row.invTableType
              if (invId != null) {
                this.reorderDialog.InvAvailable.tableData = res.data.filter(item => {
                  if (item.inventoryTableType === invTableType) {
                    return (item.inventoryId !== invId)
                  } else {
                    return true
                  }
                })
              }
              if (this.reorderDialog.InvAvailable.tableData.length === 0) {
                this.smcErrorMsg('没有可用库存')
                this.reorderDialog.InvAvailable.tableData = []
              } else {
                var index = 1
                this.reorderDialog.InvAvailable.tableData.forEach(item => {
                  item.index = index++
                })
              }
            } else {
              this.smcErrorMsg('没有可用库存')
              this.reorderDialog.InvAvailable.tableData = []
            }
          }
        }).catch(error => {
          this.reorderDialog.Loading = false
          if (axios.isCancel(error)) {
            console.log('请求被取消:', error.message)
          } else {
            this.smcErrorMsg('请求失败' + error.toString())
          }
        })
      } else {
        this.reorderDialog.InvAvailable.query = {}
        this.reorderDialog.InvAvailable.tableData = []
        this.smcErrorMsg(row.actionMsg)
      }
    },
    selectAvailableInventory(row) {
      // 点击转定库存
      console.log(row)
      this.reorderDialog.reorderObject.fromDto.toInvId = row.inventoryId
      this.reorderDialog.reorderObject.fromDto.toInvRiskFlag = row.invRisk
      this.reorderDialog.reorderObject.fromDto.toInvTableType = row.inventoryTableType
      this.reorderDialog.InvAvailableSelection = row.index
    },
    // 点击供应商同意删单
    handlePoClick(row) {
      if (row.deletePo) {
        row.deletePo = true
        row.transfer = false
        row.transferLock = true
      } else {
        row.transferLock = false
        row.deletePo = false
      }
      this.$forceUpdate()
    },
    // 点击是否调入专备
    changeHandle(row) {
      if (!row.transferLock) {
        if (row.transfer) {
          row.transfer = false
        } else {
          row.transfer = true
        }
        this.$forceUpdate()
      }
    },
    reorderPoEvent() {
      // 转定按钮
      var paramObj = this.reorderDialog.reorderObject.fromDto
      if (!paramObj) {
        this.smcErrorMsg('请选择一个占用库存')
        return
      }
      paramObj.userName = this.$store.getters.name
      paramObj.delPoFlag = paramObj.deletePo
      this.reorderDialog.reorderOrderBtn = true
      handleZDToPo(paramObj).then(res => {
        this.reorderDialog.reorderOrderBtn = false
        if (res.success) {
          this.smcInfoMsg(res.message)
          this.closeReorderWindow()
          this.rcvItemCondition.rorderNo = this.rcvItemCondition.orderId
          this.rcvItemCondition.rorderItem = this.rcvItemCondition.orderItem
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(err => {
        console.log(err)
      })
    },
    reorderEvent() {
      // 转定按钮
      var paramObj = this.reorderDialog.reorderObject.fromDto
      if (!paramObj) {
        this.smcErrorMsg('请选择一个占用库存')
        return
      }
      if (!paramObj.toInvId) {
        this.smcErrorMsg('请选择一个可用库存')
        return
      }
      paramObj.userName = this.$store.getters.name
      paramObj.delPoFlag = paramObj.deletePo
      // 采购取消复选框,赋值采购数据
      this.reorderDialog.poAllTableData.forEach(poItem => {
        if (poItem.requestSplitno == null) {
          poItem.requestSplitno = 0
        }
        var cgNo = 0
        if (paramObj.assModleFlag) {
          cgNo = paramObj.assNum
        } else {
          cgNo = paramObj.num
        }
        if (cgNo === poItem.requestSplitno) {
          this.reorderDialog.purchaseTableData.push(poItem)
        }
      })
      if (paramObj.delPoFlag) {
        if (this.reorderDialog.purchaseTableData.length === 0) {
          this.smcErrorMsg('无采购数据')
          return
        }
        if (this.reorderDialog.purchaseTableData.length > 1) {
          this.smcErrorMsg('采购数据存在多条')
          return
        }
        if (this.reorderDialog.purchaseTableData.length === 1) {
          paramObj.poInfo = this.reorderDialog.purchaseTableData[0]
          paramObj.poInfo.transfer = paramObj.transfer
        }
      } else {
        if (this.reorderDialog.purchaseTableData.length === 1) {
          paramObj.poInfo = this.reorderDialog.purchaseTableData[0]
          paramObj.poInfo.transfer = paramObj.transfer
          paramObj.poInfo.endUser = paramObj.endUser
        }
      }
      this.reorderDialog.reorderOrderBtn = true
      handleZD(paramObj)
        .then(res => {
          this.reorderDialog.reorderOrderBtn = false
          if (res.success) {
            this.smcInfoMsg(res.message)
            this.closeReorderWindow()
            this.rcvItemCondition.rorderNo = this.rcvItemCondition.orderId
            this.rcvItemCondition.rorderItem = this.rcvItemCondition.orderItem
          } else {
            this.smcErrorMsg(res.message)
          }
        })
        .catch(err => {
          console.log(err)
        })
    },

    // 【运单信息】
    // 点击明细表的运单号连接
    openExpressInfoWindow(expressno, carrierid) {
      console.log('查询运单号:' + expressno + ',' + carrierid)
      this.expressInfoDialog.content = []
      this.expressInfoDialog.show = true
      getExpressInfo(expressno, carrierid)
        .then(res => {
          if (res.code === 'OK') {
            if (res.expressContentList.size !== 0) {
              res.expressContentList.forEach(item => {
                this.expressInfoDialog.content.push({
                  time: item.time,
                  content: item.content,
                  size: 'large',
                  icon: 'el-icon-location',
                  color: '#409EFF',
                  font: 'font-size-small'
                })
              })
            } else {
              this.expressInfoDialog.content = []
            }
          }
        })
        .catch()
    },
    closeExpressInfoWindow() {
      this.expressInfoDialog.show = false
      this.expressInfoDialog.content = []
    },

    // 【营业所选择组件】
    changeDeptMenuEvent(val) {
      this.searchMenuData.condition.deptNo = val
    },
    // 【分页组件】
    // 改变每页条数
    handleSizeChange(newSize) {
      // 更新显示值
      this.searchMenuData.pageSize = newSize
      // 更新查询参数值
      this.searchParam.pageSize = this.searchMenuData.pageSize
      this.getRcvTableData()
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.searchMenuData.pageNumber = newCurrent
      this.searchParam.pageNumber = this.searchMenuData.pageNumber
      this.getRcvTableData()
    },
    // 【分割线组件】
    // 搜索栏展开/隐藏
    changeSearchSize() {
      this.size.search = !this.size.search
    },

    tableCellClassName({ row, column }) {
      const tableColumn = this.tableColumns.find(item => item.prop === column.property)
      if (!tableColumn || !tableColumn.cellClassName) {
        return ''
      }
      if (typeof tableColumn.cellClassName === 'function') {
        return tableColumn.cellClassName({ row, column }) || ''
      }
      return tableColumn.cellClassName
    },

    // 【格式化】
    // 日期格式化
    dateTimeFormatter(row, column, cellValue) {
      return cellValue ? moment(cellValue).format('YYYY-MM-DD HH:mm:ss') : ''
    },
    dateFormatter(row, column, cellValue) {
      return cellValue ? moment(cellValue).format('YYYY-MM-DD') : ''
    },
    // 营业所字典格式化
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 状态字典格式化
    statusFormatter(row, column, cellValue) {
      const item = this.DictData.status.find(dict => parseInt(dict.code) === cellValue)
      return item ? item.desc : cellValue
    },
    // 信用拦截字典格式化
    interceptFormatter(row, column, cellValue) {
      if (cellValue) {
        return '信用拦截'
      }
      return '正常'
    },
    // 订单类型字典格式化
    orderTypeFormatter(row, column, cellValue) {
      const item = this.DictData.orderType.find(dict => parseInt(dict.code) === cellValue)
      return item ? item.desc : cellValue
    },
    // 出货方式字典格式化
    dlvEntireFormatter(row, column, cellValue) {
      const item = this.DictData.dlvEntire.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 是否组装（阀与汇流板标识）格式化
    specMarkFormatter(row, column, cellValue) {
      const item = this.DictData.specMark.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 特殊标识格式化
    expDlvTypeFormatter(row, column, cellValue) {
      if (!cellValue) {
        return '无'
      }
      var arr = []
      for (const i of this.DictData.expDlvType) {
        if (this.expDlvTypeInclude(cellValue, Number(i.code))) {
          arr.push(i.desc)
        }
      }
      return arr.join()
    },
    expDlvTypeInclude(value, type) {
      if (value) {
        if ((value & type) === type) {
          return true
        }
      }
      return false
    },
    // 出库区分代码字典格式化
    stockTypeFormatter(cellValue) {
      const item = this.DictData.stockType.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 供应商名称字典格式化
    supplierFormatter(row, column, cellValue) {
      const item = this.DictData.supplier.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 库存状态字典格式化
    inventoryStatusFormatter(row, column, cellValue) {
      const item = this.DictData.inventoryStatus.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 库存类别字典格式化
    inventoryTypeFormatter(row, column, cellValue) {
      const item = this.DictData.inventoryType.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },

    stockCodeFormatter(row, column, cellValue) {
      const item = this.DictData.warehouseAndSupplier.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    }
  }
}
</script>
<style scoped lang="scss">
.el-row {
  margin-bottom: 5px;
}

.el-form-item {
  margin: 0 5px;
  padding-top: 0px;
}

#divider {
  height: 4px;
  background: #dcdfe6;
  cursor: row-resize;
  margin: 10px 0;
}

#ops-card {
  /*height: 840px;*/
  height: calc(100vh - 130px);
  position: relative;
}

#rcv-main {
  padding: 0;
}

.divider_margin {
  margin: 20px 0;
  font-size: 50px;
}

/*运单图标*/
/deep/ .el-timeline-item__node--large {
  left: -7px;
  width: 25px;
  height: 25px;
}

.font-size-large {
  font-size: 18px;
  font-family: '微软雅黑';
  padding: 1px;
}

.rcv-search-menu-open /deep/ .el-form-item {
  .el-select,
  .el-input,
  .searchDeptNoClass {
    width: 160px !important;
  }
}

.rcv-search-menu-close /deep/ .el-form-item {
  .el-select,
  .el-input,
  .searchDeptNoClass {
    width: 180px !important;
  }
}

.searchDeptNoClass /deep/ .el-input__inner {
  height: 30px !important;
}

.menu-department /deep/ .el-cascader__search-input {
  min-width: 1px !important;
  height: 0px !important;
}

/* 信用拦截红色高亮 */
.intercept-red {
  color: #F56C6C;
  font-weight: bold;
}
</style>
<style lang="scss">
/* 信用拦截红色高亮 */
.intercept-red {
  color: #F56C6C;
  font-weight: bold;
}
</style>
