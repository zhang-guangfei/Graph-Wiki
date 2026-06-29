<template>
  <div class="app-container">
    <el-card shadow="never" style="margin-top: 10px;">
      <el-row>
        <el-col :span="3">
          <department style="width:180px;margin-left: 0px;" @handleScopeChange="handleScopeChange"/>
        </el-col>
        <el-col :span="3">
          <el-select v-model="form.modifyType" clearable size="mini" placeholder="变更类别" style="width:180px" @change="initData" >
            <el-option v-for="item in modifyTypeOptions" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select v-model="form.status" clearable size="mini" placeholder="处理状态" style="width:180px" @change="initData" >
            <el-option v-for="item in approvestatusdesc" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select v-model="form.changeType" clearable size="mini" placeholder="变更内容" style="width:180px" @change="initData" >
            <el-option v-for="item in changeTypeList" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select v-model="form.remark" clearable size="mini" placeholder="请选择结果" style="width:180px" @change="initData" >
            <el-option v-for="item in remarkList" :key="item.code" :value="item.codeName" :label="item.codeName" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-select v-model="form.cancelStrategy" clearable size="mini" placeholder="删单对策" style="width:180px" @change="initData" >
            <el-option v-for="item in cancelStrategyList" :key="item.codeName" :value="item.codeName" :label="item.codeName" />
          </el-select>
        </el-col>
        <el-col :span="3">
          <el-button v-permission="['621273']" icon="el-icon-search" type="primary" title="搜索" size="mini" @click="initData()" >查询</el-button>
        </el-col>
      </el-row>
      <el-row style="margin-top: 10px;">
        <el-col :span="3">
          <el-input :rows="1" v-model="form.orderNo" style="width:180px" autocomplete="off" type="textarea" placeholder="订单号" size="mini" learable />
        </el-col>
        <el-col :span="3">
          <el-input v-model="form.applyNo" style="width:180px" size="mini" placeholder="申请号" clearable />
        </el-col>
        <el-col :span="3">
          <el-input v-model="form.modelNo" style="width:180px" size="mini" placeholder="型号" clearable />
        </el-col>
        <el-col :span="3">
          <el-select v-model="form.dateType" placeholder="日期类别" style="width:180px" size="mini" clearable >
            <el-option v-for="item in dateTypeOptions" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-col>
        <el-col :span="4">
          <el-date-picker
            v-model="datePicker"
            :picker-options="pickerOptions"
            :default-time="['00:00:00', '23:59:59']"
            type="daterange"
            align="right"
            link-panels
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            style="width: 270px"
            size="mini" />
        </el-col>

      </el-row>
    </el-card>
    <el-row style="margin-top:10px;">
      <vxe-toolbar ref="toolbarRef" >
        <template #buttons>
          <el-checkbox v-model="form.isGt500w" label="E金额总额>500万" style="width: 140px;margin-right: 10px;" size="mini" border @change="initData"/>
          <el-checkbox v-model="form.sendProcess" label="二次审批" style="width: 100px;margin-right: 10px;" size="mini" border @change="initData"/>
          <el-button v-permission="['907992']" type="success" icon="el-icon-check" size="mini" @click="conform">处理</el-button>
          <el-button v-permission="['907992']" size="mini" type="warning" icon="el-icon-thumb" @click="dialogreturnFormVisible = true">否决或暂不处理</el-button>
          <el-button v-permission="['907992']" :loading="exportLoading" type="primary" size="mini" icon="el-icon-bottom" class="handleBtu" @click="exportOrderModifyData()">导出</el-button>
          <el-button v-permission="['907992']" type="primary" size="mini" icon="el-icon-s-help" class="handleBtu" @click="handDelOrderStatusToMenHu()">回传门户状态</el-button>
          <el-button type="primary" size="mini" icon="el-icon-document-copy" class="handleBtu" @click="secondProcess()">二次审批</el-button>
        </template>
      </vxe-toolbar>
    </el-row>
    <el-table
      v-loading="listLoading"
      ref="multipleTable"
      :header-cell-style="tableStyle"
      :row-style="{height:'33px'}"
      :data="applyData"
      border
      stripe
      style="width: 100%;margin-top:2px;"
      height="60vh"
      @selection-change="handleapproveSelection"
    >

      <!-- 表头字段 -->
      <el-table-column type="selection" width="55" />
      <el-table-column
        prop="source"
        label="发起方"
        width="100px"
        show-overflow-tooltip
        fixed/>
      <el-table-column sortable prop="orderNo" label="业务单号" width="130px" show-overflow-tooltip fixed>
        <template slot-scope="scope">
          <el-popover :width="activeTabPane==='statusInfo' ? 1100 : 900" placement="right" >
            <el-tabs v-model="activeTabPane" type="card" @tab-click="handleTabsClick(scope.row.orderNo)">
              <el-tab-pane label="状态" name="statusInfo" >
                <el-table :data="splitOrderData" :row-style="{height: '0'}" style="width: 100%;font-size:10px" size="mini">
                  <el-table-column
                    v-for="column in tableColumnsItem"
                    :key="column.prop"
                    :prop="column.prop"
                    :label="column.label"
                    :width="column.width"
                    :type="column.type"
                    :formatter="column.formatter"
                    :align="column.align"
                    header-align="center"
                    show-overflow-tooltip
                  />
                </el-table>
              </el-tab-pane>
              <el-tab-pane label="分配" name="assignInfo" >
                <el-table :data="splitAssignData" :row-style="{height: '0'}" style="width: 100%;font-size:10px" size="mini">
                  <el-table-column
                    v-for="column in tableColumnsItemAssign"
                    :key="column.prop"
                    :prop="column.prop"
                    :label="column.label"
                    :width="column.width"
                    :type="column.type"
                    :formatter="column.formatter"
                    :align="column.align"
                    header-align="center"
                    show-overflow-tooltip
                  />
                </el-table>
              </el-tab-pane>
            </el-tabs>
            <span
              slot="reference"
              title="查看详情"
              style="cursor: pointer;color:#337AB7;"
              @click="findOrderItem(scope.row)"
            >{{ scope.row.orderNo }}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        prop="modifyTypeName"
        label="变更类型"
        width="100px"
        show-overflow-tooltip
        fixed/>
      <el-table-column
        prop="statusName"
        label="处理状态"
        width="120px"
        show-overflow-tooltip
        fixed/>
      <el-table-column
        prop="secondProcessName"
        label="二次审批"
        width="80px"
        show-overflow-tooltip
        fixed/>
      <el-table-column
        prop="modelNo"
        label="型号"
        width="130px"
        show-overflow-tooltip
        fixed/>
      <el-table-column
        prop="quantity"
        label="数量"
        width="60px"
        fixed/>
      <!-- <el-table-column
        prop="deptNo"
        label="营业所"
        width="160px"
        show-overflow-tooltip/> -->
      <el-table-column
        prop="customerDeptNo"
        label="客户对应营业所"
        width="160px"
        show-overflow-tooltip
        fixed/>
      <el-table-column
        prop="changeMessage"
        label="申请内容"
        width="180px"
        show-overflow-tooltip
        fixed/>
      <el-table-column
        prop="changeTypeName"
        label="变更内容"
        width="100px"
        show-overflow-tooltip
      />
      <el-table-column
        prop="responsibleParty"
        label="责任方"
        width="100px"
        show-overflow-tooltip
      />
      <el-table-column
        prop="cancelReason"
        label="删单原因"
        width="100px"
        show-overflow-tooltip
      />
      <el-table-column
        prop="reason"
        label="变更理由"
        width="280px"
        show-overflow-tooltip
      />
      <el-table-column
        prop="cancelStrategy"
        label="删单对策"
        width="120px"
        show-overflow-tooltip
      />
      <el-table-column
        prop="prodFlag"
        label="拆分标识"
        show-overflow-tooltip/>
      <el-table-column
        prop="applyNo"
        label="申请号"
        width="140px"
        show-overflow-tooltip/>
      <el-table-column
        prop="endUser"
        label="用户代码"
        show-overflow-tooltip/>
      <!-- <el-table-column
        prop="remark"
        label="备注"
        width="120px"
        show-overflow-tooltip/> -->
      <el-table-column
        prop="applyPersonNo"
        label="申请人"
        width="120px"
        show-overflow-tooltip/>
      <el-table-column
        prop="applyTime"
        label="申请时间"
        width="120px"
        show-overflow-tooltip/>
      <el-table-column
        prop="answerPns"
        label="业务处理人"
        width="120px"
        show-overflow-tooltip/>
      <el-table-column
        prop="answerText"
        label="业务审批回复内容"
        width="150px"
        show-overflow-tooltip/>
      <el-table-column
        prop="answerTime"
        label="业务审批时间"
        width="150px"
        show-overflow-tooltip/>
    </el-table>
    <el-dialog
      :visible.sync="productVisible"
      :modal="false"
      append-to-body
      title="型号信息"
      width="750px"
      class="dialog"
    >
      <product-search v-if="productVisible" ref="ProductSearch" />
    </el-dialog>
    <el-dialog
      :visible.sync="approveReplyDialog"
      title="业务审批回复"
      width="30%">
      <el-input
        :rows="2"
        v-model="zdUpInfo.answerText"
        type="textarea"
        placeholder="请输入回复内容"/>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="approveReplyDialog = false">取 消</el-button>
        <el-button :loading="upApproveReplayBtu" size="mini" type="primary" @click="upApproveReplay()">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      :visible.sync="poApproveReplyDialog"
      title="业务审批回复"
      width="30%">
      <el-form :inline="true" :model="zdUpInfo" :rules="rules" class="demo-form-inline">
        <!-- <el-form-item label="变更后单号" prop="newOrderNo">
          <el-input v-model="zdUpInfo.newOrderNo" size="mini" placeholder="变更后单号" />
        </el-form-item> -->
        <el-form-item label="回复内容">
          <el-input
            :rows="2"
            v-model="zdUpInfo.answerText"
            style="width: 300px;margin-left: 15px"
            type="textarea"
            placeholder="请输入回复内容"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="poApproveReplyDialog = false">取 消</el-button>
        <el-button :loading="upPoApproveReplayBtu" size="mini" type="primary" @click="upPoApproveReplay()">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      :visible.sync="secondProcessDialog"
      title="二次审批"
      width="30%">
      <el-form>
        <el-form-item label="请选择原因：">
          <el-select
            v-model="secondProcessForm.selectValue"
            style="width: 250px;"
          ><el-option
            v-for="item in DictData.secondProcessReason"
            :key="item.id"
            :value="item.code"
            :label="item.desc"/>
          </el-select>
        </el-form-item>
        <el-form-item v-show="secondProcessForm.selectValue&&secondProcessForm.selectValue==='其他'" label="输入原因：" label-width="100px">
          <el-input
            :rows="2"
            v-model="secondProcessForm.answerText"
            type="textarea"
            placeholder="请输入需要二次审批的理由"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="secondProcessDialog = false">取 消</el-button>
        <el-button :loading="secondProcessLoadBtu" size="mini" type="primary" @click="sureSecondProcess()">确 定</el-button>
      </span>
    </el-dialog>
    <!-- 分页工具 -->
    <pagination
      v-show="form.total > 0"
      :total="form.total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="form.pageNum"
      :limit.sync="form.pageSize"
      @pagination="initData"
    />

    <el-divider />
    <!-- 处理 -->
    <el-dialog
      :visible.sync="dialogapproveVisible"
      :close-on-click-modal="false"
      title="处理"
      width="420px"
    >
      <el-form ref="aproveForm" :model="aproveForm">
        回复说明
        <el-form-item prop="approveReason">
          <el-input
            :rows="4"
            v-model="aproveForm.approveReason"
            style="width:380px"
            autocomplete="off"
            type="textarea"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          style="float:left"
          size="mini"
          @click="dialogapproveVisible = false;approveData(4)"
        >退回</el-button>
        <el-button
          type="primary"
          size="mini"
          @click="dialogapproveVisible = false;approveData(5)"
        >处理中</el-button>
        <el-button
          type="primary"
          size="mini"
          @click="dialogapproveVisible = false;approveData(6)"
        >执行变更</el-button>
      </div>
    </el-dialog>
    <!-- 否决或暂不处理 -->
    <el-dialog
      :visible.sync="dialogreturnFormVisible"
      :close-on-click-modal="false"
      title="否决或暂不处理"
      width="420px"
    >
      <el-form ref="returnForm" :model="returnForm">
        理由
        <el-form-item prop="approveReason">
          <el-input
            :rows="4"
            v-model="returnForm.approveReason"
            style="width:380px"
            autocomplete="off"
            type="textarea"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button
          :loading="notHandBtuLoad"
          type="success"
          size="mini"
          @click="returnOrderModify(8)"
        >暂不处理</el-button>
        <el-button
          :loading="rejectBtuLoad"
          type="primary"
          size="mini"
          @click="returnOrderModify(7)"
        >否决</el-button>
      </div>
    </el-dialog>

    <CancelOrderDialog :visible.sync="orderCancelDialog.show" :order-data="orderCancelDialog.data" handle-success="cancelSuccess"/>
    <!-- 订单还原 申请界面-->
    <ResetOrderDialog :visible.sync="orderResetDialog.show" :order-data="orderResetDialog.data" handle-success="resetSuccess"/>

    <!-- 转定 -->
    <el-dialog :visible.sync="reorderDialog.show" width="70%" title="转订库存" top="10vh" @close="closeReorderWindow">
      <el-divider content-position="left" class="divider_margin">当前占用库存</el-divider>
      <el-table
        :data="reorderDialog.InvOriginal.tableData"
        :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
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
            <el-checkbox v-model="scope.row.transfer" :disabled=" scope.row.transferLock" @click.native.prevent="changeHandle(scope.row)"/>
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
        :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
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
        <el-button :loading="zdBtuLoading" type="primary" size="small" style="font-size: 15px" @click="reorderEvent()">转订库存</el-button>
        <el-button type="primary" size="small" style="font-size: 15px" @click="closeReorderWindow">取消
        </el-button>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import moment from 'moment'
import { getDataCodesTree, getDataTypeByParentCode } from '@/api/common/dict'
import { listRcvModifyOrders, listOrderModify, modifyOrders, approveOrderModify, returnOrderModify, exportOrderModifyData, handDelOrderStatusToMenHu, upApproveReplay, upPoApproveReplay, upPurOrderSupplier, zdOrderModifyHand, getOpsOrderStatusInfoByOrderFno, secondProcessWithUi } from '@/api/order/modifyorder'
import ProductSearch from '../../product/index'
import { findOrderByFno, findSupplier, findAllHouse, findInventoryType, showItemZD, getRoPartStatus, findRequestPurchaseBySqlitNo, showInvByItemQtyZDV1 } from '@/api/warehouseManage'
import { getSuppily, getTrans } from '@/api/intercept'
import { getOrderStatus, getOldOrderAssign } from '@/api/order/search'
import CancelOrderDialog from '@/views/order/modify/CancelOrderDialog.vue'
import ResetOrderDialog from '@/views/order/modify/ResetOrderDialog.vue'
import axios from 'axios'
export default {
  name: 'OrderModify',
  components: { ResetOrderDialog, CancelOrderDialog, Pagination, ProductSearch, Department },
  data() {
    return {
    // 业务单号展示标签切换
      activeTabPane: 'statusInfo',
      // 【常量】 转定用
      cancelTokenSource: null,
      // 订单取消组件变量
      orderCancelDialog: {
        show: false,
        data: []
      },
      // 订单还原组件变量
      orderResetDialog: {
        show: false,
        data: []
      },

      activeTabName: 'pane1',
      copyMessage: '',
      handDelOrderStatusVO: {
        ids: [],
        optUserNo: '',
        optUserName: ''
      },
      datePicker: [],
      orderModify: {},
      statusData: [],
      inventoryStatus: [],
      handBtuLoad: false,
      secondProcessLoadBtu: false,
      notHandBtuLoad: false,
      rejectBtuLoad: false,
      dialogreturnFormVisible: false,
      upApproveReplayBtu: false,
      transDataBtu: false,
      secondProcessDialog: false,
      returnForm: {
        ids: [],
        approveReason: '',
        status: 0
      },
      secondProcessForm: {
        optUser: '',
        answerText: '',
        selectValue: '',
        id: ''
      },
      aproveForm: {
        status: 0,
        approveReason: '',
        ids: [],
        approveItems: []
      },
      zdUpInfo: {
        answerText: '',
        batchNo: [],
        optUserNo: '',
        newOrderNo: ''
      },
      producttag: [
        {
          value: 'H',
          label: 'H'
        },
        {
          value: '1',
          label: '收敛品'
        },
        {
          value: '9',
          label: '其它'
        }
      ],
      upPoApproveReplayBtu: false,
      poApproveReplyDialog: false,
      approveReplyDialog: false,
      dialogapproveVisible: false,
      dialogmoreQueryVisible: false,
      productVisible: false,
      exportLoading: false,
      editFormVisible: false,
      zdBtuLoading: false,
      menuData: {
      },
      classCode: '1008',
      changeTypeList: [],
      cancelStrategyList: [],
      // 表格数据
      applyData: [],
      listLoading: true,
      loading: true,
      remarkList: [],
      form: {
        modifyType: 'C',
        status: '2',
        changeType: '',
        tradecompanyId: '',
        form: '',
        deptNoList: [],
        stockCode: '',
        orderNo: '',
        orderNos: [],
        applyNo: '',
        deptNo: '',
        modelNo: '',
        dateType: '',
        fromDateStr: '',
        toDateStr: '',
        remark: '',
        isGt500w: '',
        sendProcess: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      // 查询直接修改数据
      modifyform: {
        modifyType: '',
        orderNo: '',
        modelNo: '',
        customerNo: '',
        modifyReason: '',
        remark: '',
        modifyItems: []
      },
      // 选择后需要修改的数据
      modifyformdata: {
        modifyType: '',
        modifyReason: '',
        modifyRemark: '',
        modifyItems: []
      },
      dateTypeOptions: [{
        code: '1',
        codeName: '申请日期'
      },
      {
        code: '2',
        codeName: '审批日期'
      }],
      rorderNos: [],
      modifyreasonOptions: [{
        code: 1,
        codeName: '其它'
      }],
      reasonOptions: [{
        code: 1,
        codeName: '其它'
      }],
      modifyTypeOptions: [],
      multipleSelection: [],
      approveSelection: [],
      orderstatusdesc: [],
      transList: [],
      tempDeptData: {
        id: '200000',
        pid: '2000000',
        name: '自动化',
        flag: true,
        children: null
      },
      approvestatusdesc: [],
      deptNoOptions: [], // 部别下拉框
      cpnysData: [],
      departs: [],
      purchaseTableColumns: [
        {
          label: '请购单号',
          width: 150,
          formatter: row => {
            var requestNo = row.requestno
            if (row.requestItemno !== null) {
              requestNo = requestNo + '-' + row.requestItemno
            }
            if (row.requestSplitno !== null) {
              requestNo = requestNo + '-' + row.requestSplitno
            }
            return requestNo
          }
        },
        {
          label: '采购单号',
          width: 150,
          formatter: row => {
            var purchaseNo = row.purchaseno
            if (row.purchaseItemno !== null) {
              purchaseNo = purchaseNo + '-' + row.purchaseItemno
            }
            if (row.purchaseSplitno !== null) {
              purchaseNo = purchaseNo + '-' + row.purchaseSplitno
            }
            return purchaseNo
          }
        },
        {
          label: '是否合并',
          formatter: row => {
            if (row.merge) {
              return 'Y'
            }
            return 'N'
          },
          width: 90
        },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '数量',
          prop: 'quantity',
          width: 60
        },
        {
          label: '供应商',
          prop: 'supplierid',
          width: 120,
          formatter: this.supplierFormatter
        },
        {
          label: '状态',
          prop: 'status',
          width: 150,
          formatter: row => {
            if (row.purchaseno) {
              return this.purchaseStatusFormatter(row.status)
            } else {
              return this.requestStatusFormatter(row.status)
            }
          }
        },
        {
          label: 'BIN',
          formatter: row => {
            if (row.bin) {
              return 'Y'
            }
            return 'N'
          },
          width: 90
        }/*,
        {
          type: 'selection',
          label: '供应商同意删单',
          prop: 'deleteok',
          width: 100
        }*/

      ],
      resetDialog: {
        show: false, // 是否显示
        loading: true, // 查询中
        resetObject: {}, // 查询参数
        purchaseTableData: [], // 查询结果
        purchaseTableColumns: [
          {
            label: '请购单号',
            width: 150,
            formatter: row => {
              var requestNo = row.requestno
              if (row.requestItemno !== null) {
                requestNo = requestNo + '-' + row.requestItemno
              }
              if (row.requestSplitno !== null) {
                requestNo = requestNo + '-' + row.requestSplitno
              }
              return requestNo
            }
          },
          {
            label: '采购单号',
            width: 150,
            formatter: row => {
              var purchaseNo = row.purchaseno
              if (row.purchaseItemno !== null) {
                purchaseNo = purchaseNo + '-' + row.purchaseItemno
              }
              if (row.purchaseSplitno !== null) {
                purchaseNo = purchaseNo + '-' + row.purchaseSplitno
              }
              return purchaseNo
            }
          },
          {
            label: '是否合并',
            formatter: row => {
              if (row.merge) {
                return 'Y'
              }
              return 'N'
            },
            width: 90
          },
          {
            label: '型号',
            prop: 'modelno'
          },
          {
            label: '数量',
            prop: 'quantity',
            width: 60
          },
          {
            label: '供应商',
            prop: 'supplierid',
            width: 120,
            formatter: this.supplierFormatter
          },
          {
            label: '状态',
            prop: 'status',
            width: 150,
            formatter: row => {
              if (row.purchaseno) {
                return this.purchaseStatusFormatter(row.status)
              } else {
                return this.requestStatusFormatter(row.status)
              }
            }
          },
          {
            label: 'BIN',
            formatter: row => {
              if (row.bin) {
                return 'Y'
              }
              return 'N'
            },
            width: 90
          }
        ], // 查询字段
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
        } // 可用库存
      },
      reorderDialog: {
        InvAvailableSelection: '',
        show: false,
        deletePo: false,
        transfer: false,
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

      DictData: {
        supplier: [],
        warehouse: [],
        warehouseAndSupplier: [],
        stockCode: [],
        stateDetail: [],
        state: [],
        stockType: [],
        inventoryType: [],
        purchaseStatus: [],
        requestStatus: [],
        inventoryStatus: [],
        secondProcessReason: []
      },
      // 订单明细表字段
      tableColumnsItem: [
        {
          type: 'index'
        },
        /* {
          prop: 'itemNo',
          width: 40
        },*/
        {
          label: '发货仓',
          width: 60,
          prop: 'warehouseCode',
          formatter: this.warehouseFormatter
        },
        {
          label: '发货单号',
          width: 130,
          prop: 'wmOrderId'
        },
        {
          label: '型号',
          width: 130,
          prop: 'modelno'
        },
        {
          label: '数量',
          prop: 'qty',
          width: 60,
          align: 'right'
        },
        {
          label: '货齐数',
          prop: 'qtyIn',
          width: 80,
          align: 'right'
        },
        {
          label: '已发数',
          prop: 'qtyOut',
          width: 80,
          align: 'right'

        },
        {
          label: '状态',
          width: 130,
          prop: 'statusDesc',
          formatter: this.stateDescFormatter
        },
        {
          label: '状态信息',
          width: 130,
          prop: 'statusInfo'
        },
        {
          label: '关联单号',
          prop: 'associateNo',
          width: 180,
          align: 'right'
        }
      ],
      // 订单明细分配表字段
      tableColumnsItemAssign: [
        {
          type: 'index'
        },
        {
          label: '型号',
          width: 130,
          prop: 'modelno'
        },
        {
          label: '数量',
          prop: 'quantity',
          width: 60,
          align: 'right'
        },
        {
          label: '出库区分',
          width: 200,
          formatter: row => {
            var stockType = this.stockTypeFormatter2(row.stockType)
            var stockCode = this.stockCodeFormatter2(row.stockCode)
            var inventoryType = this.inventoryTypeFormatter2(row.inventoryTypeCode)
            if (row.inventoryTypeCode) {
              return stockType + '【' + stockCode + '-' + inventoryType + '】'
            } else if (row.stockCode) {
              return stockType + '【' + stockCode + '】'
            } else if (row.stockType) {
              return stockType
            } else {
              return ''
            }
          },
          align: 'right'
        },
        {
          label: '关联单号',
          prop: 'associateNo',
          width: 120,
          align: 'right'
        }
      ],
      splitOrderData: [],
      splitAssignData: [],
      updateRequestData: {
        orderno: '',
        supplierid: '',
        transtype: '',
        hopeexportdate: '',
        information: '',
        greencode: '',
        batchNo: '',
        applyNo: '',
        optUser: ''
      },
      suppilyList: [],
      morequery: false,
      rules: {
        newOrderNo: [
          { required: true, message: '请输入变更后单号', trigger: 'blur' }
        ],
        supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        transtype: [{ required: true, message: '请选择运输方式', trigger: 'blur' }],
        serverremark: [{ required: true, message: '请选择转订原因', trigger: 'blur' }],
        hopedeliverydate: [{ required: true, message: '请选择出库日', trigger: 'blur' }]
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      },
      // 表头样式
      tableStyle: {
        backgroundColor: 'rgb(117, 144, 168)',
        color: 'rgba(253, 253, 253, 0.938)',
        padding: '6px',
        fontSize: '14px'
      }
    }
  },
  created() {
    this.initData()
    this.initDesc()
    this.getStockCodeList()
    this.getSuppilyTrans()
  },
  methods: {
    // 业务单号展示标签tab页切换
    handleTabsClick(orderFno) {
      this.splitOrderData = []
      this.splitAssignData = []
      var rorderFno = orderFno
      // 如果rorderFno有两个-，则去掉最后一个杠和后面的内容
      if (rorderFno.split('-').length > 2) {
        rorderFno = rorderFno.split('-').slice(0, -1).join('-')
      }

      if (this.activeTabPane === 'statusInfo') {
        findOrderByFno(rorderFno).then(res => {
          if (res.code === 200) {
            var rcvdetail = res.data
            var condition = {
              orderId: rcvdetail.rorderNo,
              orderItem: rcvdetail.rorderItem + ''
            }
            getOrderStatus(condition.orderId, condition.orderItem).then(result => {
              this.splitOrderData = result.data
            }).catch(error => {
              console.log(error)
            })
          } else {
            this.smcErrorMsg('查询不到订单号：' + rorderFno)
          }
        })
      }
      if (this.activeTabPane === 'assignInfo') {
        findOrderByFno(rorderFno).then(res => {
          if (res.code === 200) {
            var rcvdetail = res.data
            var condition = {
              orderId: rcvdetail.rorderNo,
              orderItem: rcvdetail.rorderItem + ''
            }
            getOldOrderAssign(condition.orderId, condition.orderItem).then(result => {
              this.splitAssignData = result.data
            }).catch(error => {
              console.log(error)
            })
          } else {
            this.smcErrorMsg('查询不到订单号：' + rorderFno)
          }
        })
      }
    },

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
    sureSecondProcess() {
      this.secondProcessLoadBtu = true
      this.secondProcessForm.optUser = this.$store.getters.position.psnsmcId
      this.secondProcessForm.id = this.approveSelection[0].id
      if (this.secondProcessForm.selectValue !== '其他') {
        this.secondProcessForm.answerText = this.secondProcessForm.selectValue
      }
      secondProcessWithUi(this.secondProcessForm).then(res => {
        this.secondProcessLoadBtu = false
        if (res.success) {
          this.$message.success(res.content)
          this.initData()
          this.secondProcessDialog = false
        } else {
          this.$message.error(res.message)
        }
      }).catch(res => {
        this.secondProcessLoadBtu = false
      })
    },
    secondProcess() {
      if (this.approveSelection.length !== 1) {
        this.$message({
          type: 'warning',
          message: '请选择一条数据进行操作.'
        })
        return
      }
      console.log('二次审批选中数据=>', this.approveSelection[0])
      if (this.approveSelection[0].status !== 2 && this.approveSelection[0].status !== 8 && this.approveSelection[0].modifyType !== 'C') {
        this.$message({
          type: 'warning',
          message: '仅订单那类型为删除订单且状态是待处理和暂不处理支持二次审批功能.'
        })
        return
      }
      this.secondProcessForm.answerText = ''
      this.secondProcessDialog = true
    },
    // 打开取消订单
    async openCancelOrderWindow(orderModifyList) {
      const promises = orderModifyList.map(item => {
        return findOrderByFno(item.orderNo).then(res => {
          if (res.code === 200) {
            // 查询的是rcv表
            const rcvdetail = res.data
            const orderData = {
              orderFno: rcvdetail.rorderFno,
              orderId: rcvdetail.rorderNo,
              orderItem: rcvdetail.rorderItem,
              orderType: rcvdetail.orderType,
              orderModify: item
            }
            return orderData
          } else {
            this.smcErrorMsg('查询不到订单号：' + item.orderNo)
            return null // 或者可以根据需要处理错误情况
          }
        })
      })
      const orderCancelList = (await Promise.all(promises)).filter(item => item !== null)
      // 正式显示弹窗
      this.orderCancelDialog.data = orderCancelList
      this.orderCancelDialog.show = true
    },
    // 取消成功后刷新数据
    cancelSuccess(msg) {
      this.initData()
    },
    // 还原成功后刷新数据
    resetSuccess(msg) {
      this.initData()
    },

    // 打开订单还原弹窗
    async openResetOrderWindow(orderModifyList) {
      const promises = orderModifyList.map(item => {
        return findOrderByFno(item.orderNo).then(res => {
          if (res.code === 200) {
            // 查询的是rcv表
            const rcvdetail = res.data
            const orderData = {
              orderFno: rcvdetail.rorderFno,
              orderId: rcvdetail.rorderNo,
              orderItem: rcvdetail.rorderItem,
              orderType: rcvdetail.orderType,
              orderModify: item
            }
            return orderData
          } else {
            this.smcErrorMsg('查询不到订单号：' + item.orderNo)
            return null // 或者可以根据需要处理错误情况
          }
        })
      })
      const orderResetList = (await Promise.all(promises)).filter(item => item !== null)
      this.orderResetDialog.data = orderResetList
      this.orderResetDialog.show = true
    },
    selectAvailableInventory(row) {
      // 点击转定库存
      this.reorderDialog.reorderObject.fromDto.toInvId = row.inventoryId
      this.reorderDialog.reorderObject.fromDto.toInvRiskFlag = row.invRisk
      this.reorderDialog.reorderObject.fromDto.toInvTableType = row.inventoryTableType
      this.reorderDialog.InvAvailableSelection = row.index
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
      if (row.actionFlag) {
        this.reorderDialog.reorderObject.fromDto = row
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
    reorderEvent() {
      var info = this.approveSelection[0]
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
      paramObj.batchNo = info.batchNo
      paramObj.optUserNo = this.$store.getters.name
      paramObj.applyNo = info.applyNo
      this.zdBtuLoading = true
      zdOrderModifyHand(paramObj).then(res => {
        this.zdBtuLoading = false
        if (res.success) {
          this.smcInfoMsg(res.message)
          this.closeReorderWindow()
          this.initData()
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(err => {
        this.zdBtuLoading = true
        console.log(err)
      })
    },
    transData() {
      this.transDataBtu = true
      upPurOrderSupplier(this.updateRequestData).then(res => {
        this.transDataBtu = false
        if (res.success) {
          this.$message.success(res.message)
          this.initData()
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.transDataBtu = false
        this.$message.error(error)
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
    getSuppilyTrans() {
      this.getTrans()
      this.getSuppily()
    },
    getTrans() {
      getTrans().then(res => {
        this.transList = res.data
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    upPoApproveReplay() {
      if (this.approveSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      if (this.zdUpInfo.answerText.length > 100) {
        this.$message.error('请控制回复内容于100个汉字之内')
        return
      }
      this.upPoApproveReplayBtu = true
      this.zdUpInfo.batchNo = []
      this.approveSelection.forEach((item, index) => {
        this.zdUpInfo.batchNo.push(item.batchNo)
      })
      this.zdUpInfo.optUserNo = this.$store.getters.name
      upPoApproveReplay(this.zdUpInfo).then(res => {
        this.upPoApproveReplayBtu = false
        if (res.success) {
          this.$message.success(res.content)
          this.poApproveReplyDialog = false
          this.initData()
        } else {
          this.$message.error(res.message)
        }
      }).catch(res => {
        this.upPoApproveReplayBtu = false
        this.$message.error(res)
      })
    },
    upApproveReplay() {
      if (this.approveSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      if (this.zdUpInfo.answerText.length > 100) {
        this.$message.error('请控制回复内容于100个汉字之内')
        return
      }
      this.upApproveReplayBtu = true
      this.zdUpInfo.batchNo = []
      this.approveSelection.forEach((item, index) => {
        this.zdUpInfo.batchNo.push(item.batchNo)
      })
      this.zdUpInfo.optUserNo = this.$store.getters.name
      upApproveReplay(this.zdUpInfo).then(res => {
        this.upApproveReplayBtu = false
        if (res.success) {
          this.$message.success(res.content)
          this.approveReplyDialog = false
          this.initData()
        } else {
          this.$message.error(res.message)
        }
      }).catch(res => {
        this.upApproveReplayBtu = false
        this.$message.error(res)
      })
    },
    searchMoreData() {
      this.morequery = !this.morequery
    },
    getStockCodeList() {
      this.DictData.stockCode = this.DictData.warehouseAndSupplier
    },
    // 查询订单明细
    findOrderItem(row) {
      var rcvdetail = null
      this.activeTabPane = 'statusInfo'
      var rorderFno = row.orderNo
      // 如果rorderFno有两个-，则去掉最后一个杠和后面的内容
      if (rorderFno.split('-').length > 2) {
        rorderFno = rorderFno.split('-').slice(0, -1).join('-')
      }
      findOrderByFno(rorderFno).then(res => {
        if (res.code === 200) {
          rcvdetail = res.data
          var condition = {
            orderId: rcvdetail.rorderNo,
            orderItem: rcvdetail.rorderItem + ''
          }
          this.splitOrderData = []
          this.splitAssignData = []
          getOrderStatus(condition.orderId, condition.orderItem).then(result => {
            this.splitOrderData = result.data
          }).catch(error => {
            console.log(error)
          })
        } else {
          this.smcErrorMsg('查询不到订单号：' + row.orderNo)
        }
      })
    },
    getChangeTypeList() {
      getDataTypeByParentCode('A').then(res => {
        if (res.success) {
          this.changeTypeList = res.content
        } else {
          this.changeTypeList = []
        }
      })
    },
    initDesc() {
      this.getChangeTypeList()
      getDataCodesTree(this.classCode).then(result => {
        this.modifyTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('1012').then(result => {
        this.approvestatusdesc = result.content
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('1026').then(result => {
        this.remarkList = result.content
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('6040').then(result => {
        this.cancelStrategyList = result.content
      }).catch(error => {
        console.log(error)
      })
      // 供应商
      findSupplier().then(result => {
        result.content.forEach(dict => {
          this.DictData.supplier.push({ code: dict.id, desc: dict.name })
          this.DictData.warehouseAndSupplier.push({ code: dict.id, desc: dict.name, codeAnddesc: dict.name + '【' + dict.id + '】' })
        })
      })
      // 仓库名称
      findAllHouse().then(result => {
        result.data.forEach(dict => {
          this.DictData.warehouse.push({ code: dict.warehouseCode, desc: dict.warehouseName })
          this.DictData.warehouseAndSupplier.push({ code: dict.warehouseCode, desc: dict.warehouseName, codeAnddesc: dict.warehouseName + '【' + dict.warehouseCode + '】' })
        })
      })
      // 订单项状态
      getDataCodesTree('4501').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.stateDetail.push({ code: dict.code, desc: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // 订单项状态
      getDataCodesTree('6030').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.secondProcessReason.push({ code: dict.codeName, desc: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // 出库区分代码
      getDataCodesTree('4306').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.stockType.push({ code: dict.code, desc: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // 字典：库存类别
      findInventoryType().then(res => {
        res.data.forEach(dict => {
          this.DictData.inventoryType.push({ code: dict.inventoryTypeCode, desc: dict.description })
        })
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
      getDataCodesTree('2032').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.requestStatus.push({ code: dict.code, desc: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('2033').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.purchaseStatus.push({ code: dict.code, desc: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // 库存状态
      getDataCodesTree('4010').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.inventoryStatus.push({ code: dict.code, desc: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },
    showApplyDetail(row) {
    },
    inventoryStatusFormatter(row, column, cellValue) {
      const item = this.DictData.inventoryStatus.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 库存类别字典格式化
    inventoryTypeFormatter(row, column, cellValue) {
      const item = this.DictData.inventoryType.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    exportOrderModifyData() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportOrderModifyData(this.form).then(res => {
        const fileName = '订单修改数据导出.xlsx'
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
    handleScopeChange(val) {
      console.log('所选部门编码val ==> ', val)
      this.form.deptNoList = val
    },
    resetForm() {
      this.form = {
        modifyType: 'C',
        status: '',
        orderNos: [],
        tradecompanyId: '',
        form: '',
        areaDeptNo: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      }
    },
    renderHeader(h) {
      return (
        <div>
          <span>日本手配号</span>
          <el-button
            type='text'
            style='padding:3px;margin-left:5px'
            icon='el-icon-copy-document'
            size='mini'
            // 加入点击事件---------
            onClick={() => this.copy()}
            // ---------------------
          ></el-button></div>
      )
    },
    renderHeaderForOrderNo(h) {
      return (
        <div>
          <span>订单号</span>
          <el-button
            type='text'
            style='padding:3px;margin-left:5px'
            icon='el-icon-copy-document'
            size='mini'
            // 加入点击事件---------
            onClick={() => this.copyOrderNo()}
            // ---------------------
          ></el-button></div>
      )
    },
    copyOrderNo() {
      this.copyMessage = ''
      this.applyData.forEach(item => {
        if (item.orderNo != null && item.orderNo !== '') {
          if (this.copyMessage === '') {
            this.copyMessage = item.orderNo
          } else {
            this.copyMessage += '\n' + item.orderNo
          }
        }
      })
      // console.log(this.copyMessage)
      var textarea = document.createElement('textarea')
      textarea.value = this.copyMessage
      document.body.appendChild(textarea)
      textarea.select()
      document.execCommand('copy')
      this.$message.success('复制成功!')
      document.body.removeChild(textarea)
    },
    copy() {
      this.copyMessage = ''
      this.applyData.forEach(item => {
        if (item.supplierOrderNo != null && item.supplierOrderNo !== '') {
          if (this.copyMessage === '') {
            this.copyMessage = item.supplierOrderNo
          } else {
            this.copyMessage += '\n' + item.supplierOrderNo
          }
        }
      })
      // console.log(this.copyMessage)
      var textarea = document.createElement('textarea')
      textarea.value = this.copyMessage
      document.body.appendChild(textarea)
      textarea.select()
      document.execCommand('copy')
      // console.log(textarea)
      this.$message.success('复制成功!')
      document.body.removeChild(textarea)
    },
    getmodifyreasonOptions(type) {
      getDataCodesTree(type).then(result => {
        console.log(result)
        this.modifyreasonOptions = result.content
      }).catch(error => {
        console.log(error)
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
    onChangemodifyType() {
      this.modifyform.modifyReason = ''
      this.modifyreasonOptions = this.recursionFindObj(this.modifyTypeOptions, this.modifyform.modifyType)
    },
    searchData() {
      if (this.modifyform.orderNo == null || this.modifyform.orderNo === '') {
        this.$message.error('请输入订单号')
        return
      }
      this.rorderNos = this.modifyform.orderNo.split('\n')
      console.log(this.rorderNos)
      const formdata = {
        rorderNos: this.rorderNos
      }
      listRcvModifyOrders(formdata).then(result => {
        console.log(result)
        this.modifyform.modifyItems = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    openReorderWindow(info, index) {
      getOpsOrderStatusInfoByOrderFno(info.orderNo).then(res => {
        if (res.success) {
          var row = res.data
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
          showItemZD(row).then(res => {
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
              findRequestPurchaseBySqlitNo(poObj).then(res => {
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
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 有po 已接单 可选择删除
                            if (poItem.status === '2' && poItem.purchaseno != null) {
                              item.lock = false // 采购复选框不可选
                              item.deletePo = true // 采购复选框默认不选择中
                              item.transferLock = true
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 有po 运输中 不可选择
                            if (poItem.status === '3' && poItem.purchaseno != null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = false
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            // 有po  已完成 不可选择
                            if (poItem.status === '5' && poItem.purchaseno != null) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = false
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            //  如果为合并采购，不可删
                            if (poItem.merge) {
                              item.lock = true // 采购复选框不可选
                              item.deletePo = false // 采购复选框默认不选择中
                              item.transferLock = false
                              item.transfer = false // 调库计划复选框默认不选择中
                            }
                            if (this.approveSelection[0].transfer != null && this.approveSelection[0].transfer) {
                              if (item.deletePo === false) {
                                item.transfer = this.approveSelection[0].transfer
                              }
                              if (this.approveSelection[0].endUserForTransferSpecial != null) {
                                item.endUser = this.approveSelection[0].endUserForTransferSpecial
                              }
                              this.$forceUpdate()
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
              }).catch()
            } else {
              this.smcErrorMsg(res.message)
            }
          }).catch(() => {
            this.smcErrorMsg('请求失败')
          })
        }
        if (!res.success) {
          this.$message.error(res.message)
        }
      })
    },
    conform() {
      this.zdUpInfo.answerText = ''
      if (this.approveSelection.length > 0) {
        // 校验变更类型是否一致
        for (let i = 0; i < this.approveSelection.length; i++) {
          if (this.approveSelection[i].modifyType === '' ||
            this.approveSelection[i].modifyType === undefined ||
            this.approveSelection[i].modifyType === null) {
            this.$message({
              type: 'warning',
              message: '选择数据存在空的变更类型,请联系相关人员修正异常数据'
            })
            return
          }
        }
        const filteredArray = this.approveSelection.filter((item, index, self) =>
          index === self.findIndex((t) => t.modifyType === item.modifyType)
        )
        const filteredArray2 = this.approveSelection.filter((item, index, self) =>
          index === self.findIndex((t) => t.changeType === item.changeType)
        )
        if (filteredArray.length > 1) {
          this.$message({
            type: 'warning',
            message: '进行批量操作时,请确保变更类别为同一类.'
          })
          return
        }
        // 如果是转订，要求变更内容字段也一致
        if (this.approveSelection[0].modifyType === 'A') {
          if (filteredArray2.length > 1) {
            this.$message({
              type: 'warning',
              message: '进行批量操作时,请确保变更内容为同一类.'
            })
            return
          }
          // 订单转订&&变更后内容为“转订出库存”&&“转订出在途”
          if (this.approveSelection[0].changeType === 'A1' || this.approveSelection[0].changeType === 'A2') {
            this.openReorderWindow(this.approveSelection[0], 1)
            return
          }
          // 订单转订-> 业务审批回复
          if (this.approveSelection[0].modifyType.startsWith('A')) {
            this.approveReplyDialog = true
            return
          }
          if (this.approveSelection[0].modifyType.startsWith('T') || this.approveSelection[0].modifyType.startsWith('D')) {
            this.poApproveReplyDialog = true
            return
          }
        }
        const orderList = this.approveSelection
        const modifyType = this.approveSelection[0].modifyType
        if (modifyType === 'C') {
          // 取消订单
          this.openCancelOrderWindow(orderList)
          return
        } else if (modifyType === 'H') {
          // 订单还原
          this.openResetOrderWindow(orderList)
          return
        }
      }
      if (this.approveSelection.length !== 1) {
        this.$message({
          type: 'warning',
          message: '请选择单项'
        })
        return
      }
    },
    hasDuplicateProperty(arr, property) {
      const filteredArr = arr.filter((item, index, self) => {
        return self.findIndex(t => t[property] === item[property]) !== index
      })
      return filteredArr.length > 0
    },
    returnOrderModify(status) {
      if (this.approveSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      if (this.returnForm.approveReason.length > 100) {
        this.$message.error('请控制回复内容于100个汉字之内')
        return
      }
      if (status === 8) {
        this.notHandBtuLoad = true
      } else {
        this.rejectBtuLoad = true
      }
      this.returnForm.ids = []
      this.returnForm.status = status
      this.approveSelection.forEach((item, index) => {
        this.returnForm.ids.push(item.id)
      })
      returnOrderModify(this.returnForm)
        .then(res => {
          this.rejectBtuLoad = false
          this.notHandBtuLoad = false
          console.log(res)
          if (res.success) {
            this.file = null
            this.$message.success(res.message)
            this.dialogreturnFormVisible = false
          } else {
            this.$message.error(res.message)
          }
          this.initData()
          this.returnForm.approveReason = ''
        }).catch(error => {
          this.rejectBtuLoad = false
          this.notHandBtuLoad = false
          this.$message.error(error + '请稍后再常试')
          this.returnForm.approveReason = ''
        })
    },
    approveData(val) {
      this.aproveForm.status = val
      this.aproveForm.approveItems = this.approveSelection
      console.log(this.aproveForm)
      approveOrderModify(this.aproveForm)
        .then(res => {
          console.log(res)
          if (res.success) {
            this.file = null
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    toconfirm() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中需要修改的单'
        })
        return
      }
      this.modifyformdata.modifyType = this.modifyform.modifyType
      this.modifyformdata.modifyReason = this.modifyform.modifyReason
      this.modifyformdata.modifyRemark = this.modifyform.remark
      this.modifyformdata.modifyItems = this.multipleSelection
      // this.multipleSelection.forEach((item, index) => {
      // positionIds.push(item.id)
      // })
      modifyOrders(this.modifyformdata).then(res => {
        if (res.success) {
          this.file = null
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.$message.error(error + '请稍后再常试')
      })
    },
    initData() {
      if (this.form.orderNo == null || this.form.orderNo === '') {
        this.form.orderNo = null
        this.form.orderNos = null
      } else {
        this.form.orderNos = this.form.orderNo.split('\n')
      }
      if (this.datePicker != null && this.datePicker.length !== 0) {
        this.form.fromDateStr = this.dayjs(this.datePicker[0]).format('YYYY-MM-DD HH:mm:ss')
        this.form.toDateStr = this.dayjs(this.datePicker[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.form.fromDateStr = ''
        this.form.toDateStr = ''
      }
      this.listLoading = true

      listOrderModify(this.form).then(result => {
        if (!result.success) {
          this.$message.error(result.message)
          this.listLoading = false
          return
        }
        this.applyData = result.content.list
        this.form.total = result.content.total
        // this.total = data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },

    // 获取表格数据
    getapplyData() {
    },
    handleSelection(val) {
      this.multipleSelection = val
    },
    handleapproveSelection(val) {
      this.approveSelection = val
    },
    // 改变每页条数
    handlePageSizeChange(newSize) {
      // this.queryCondition.pageSize = newSize
      this.getapplyData()
    },
    // 换页
    handlePageIndexChange(newCurrent) {
      this.getapplyData()
    },
    dateFormatter(row, column, cellValue) {
      return cellValue ? moment(cellValue).format('YYYY-MM-DD') : ''
    },
    statusFormatter(row, column, cellValue, index, menu) {
      return this.statusData.find(item => item.code === cellValue).desc
    },
    stateFormatter2(value) {
      const item = this.DictData.state.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    stateDescFormatter(row, column, cellValue) {
      const item = this.DictData.stateDetail.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 出库区分代码
    stockTypeFormatter2(value) {
      const item = this.DictData.stockType.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    stockCodeFormatter(row, column, cellValue) {
      const item = this.DictData.warehouseAndSupplier.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    stockCodeFormatter2(value) {
      const item = this.DictData.warehouseAndSupplier.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    inventoryTypeFormatter2(value) {
      const item = this.DictData.inventoryType.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    supplierFormatter(row, column, cellValue) {
      const item = this.DictData.supplier.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    warehouseAndSupplierFormatter2(value) {
      const item = this.DictData.warehouseAndSupplier.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    requestStatusFormatter(value) {
      const item = this.DictData.requestStatus.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    purchaseStatusFormatter(value) {
      const item = this.DictData.purchaseStatus.find(dict => dict.code === value)
      return item ? item.desc : value
    },

    handleTabClick(tab, event) {
      console.log(tab, event)
    },
    // descFormatter(data, id) {
    //   if (data === null) {
    //     return id
    //   }
    //   for (const i in data) {
    //     var item = data[i]
    //     if (item.code === id.toString()) {
    //       return item.codeName
    //     }
    //   }
    //   return id
    // },
    // 遍历获取树状图数据
    recursionFindObj(data, id) {
      var result = null
      if (!data) {
        // return; 中断执行
        return
      }
      for (const i in data) {
        // 已经找到了属性值所在的对象就终止循环,以免后面的循环覆盖result,导致出错
        if (result !== null) {
          break
        }
        var item = data[i]
        if (item.code === id.toString()) {
          result = item.children
          break
        } else {
          result = null
        }
      }
      return result
    },
    // 回传门户删单状态
    handDelOrderStatusToMenHu() {
      console.log('len = >', this.approveSelection.length)
      if (this.approveSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中需要回传门户删单状态的单'
        })
        return
      }
      this.$confirm('此操作将进行手动回传删单状态给门户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.handDelOrderStatusVO = {
          ids: [],
          optUserNo: '',
          optUserName: ''
        }
        var att = []
        this.approveSelection.forEach((item, index) => {
          att.push(item.id)
        })
        console.log('att => ', att)
        this.handDelOrderStatusVO.ids = att
        this.handDelOrderStatusVO.optUserNo = this.$store.getters.position.psnsmcId
        this.handDelOrderStatusVO.optUserName = this.$store.getters.position.psnName
        console.log('handDelOrderStatusVO => ', this.handDelOrderStatusVO)
        handDelOrderStatusToMenHu(this.handDelOrderStatusVO).then(res => {
          if (res.success) {
            this.$notify({
              duration: 5000,
              type: 'success',
              message: res.content
            })
            this.initData()
          } else {
            this.$notify({
              duration: 5000,
              type: 'error',
              message: res.message
            })
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
.line {
  text-align: center;
  margin-left: 5%;
}
.el-table /deep/ .el-table__body-wrapper{
  z-index: 2
}
.el-table .warning-row {
  background: oldlace
}

</style>
