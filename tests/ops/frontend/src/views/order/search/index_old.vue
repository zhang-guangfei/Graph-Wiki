<template>
  <div class="app-container">
    <!-- 界面 -->
    <el-card>
      <div id="ops-card" ref="ops-card">
        <!-- 接单查询表 -->
        <el-container id="ops-table-rcv">
          <!-- 菜单栏 -->
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
                        v-model.trim="queryCondition.condition.rorderFno"
                        clearable
                        size="small"
                        placeholder="订单号"
                        @keyup.down.native="clearOther"
                        @keyup.enter.native="searchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model.trim="queryCondition.condition.modelNo"
                        clearable
                        size="small"
                        placeholder="型号"
                        @keyup.enter.native="getTableDataEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="queryCondition.condition.status"
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
                        v-model="queryCondition.condition.intercept"
                        style="width: 180px;"
                        clearable
                        size="small"
                        placeholder="信用拦截"
                      >
                        <el-option
                          key=""
                          :value="null"
                          label="未选择"/>
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
                        v-model="queryCondition.condition.prodFlag"
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
                        v-model="queryCondition.condition.orderType"
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
                    <el-form-item>
                      <department
                        class="menu-department"
                        style="margin-left: 0px;margin-top: 4px;"
                        @handleScopeChange="handleScopeChange" />
                    </el-form-item>
                    <el-form-item label="接单日期">
                      <el-date-picker
                        v-model="queryCondition.condition.rorddate"
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
                      v-permission="['306452']"
                      type="primary"
                      size="small"
                      @click="searchEvent()">搜索
                    </el-button>
                    <el-button
                      v-permission="['306452']"
                      type="primary"
                      size="small"
                      @click="openBatchSearch"
                    >批量
                    </el-button>
                    <el-button
                      v-permission="['306452']"
                      type="primary"
                      size="small"
                      @click="resetQueryCondition">重置
                    </el-button>
                    <!--
                    <el-button
                      v-permission="['132053']"
                      type="primary"
                      size="small"
                    >导出
                    </el-button>-->
                    <el-button
                      v-permission="['306452']"
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
                        v-model="queryCondition.condition.customerNo"
                        clearable
                        size="small"
                        placeholder="客户代码"
                        @keyup.enter.native="searchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model="queryCondition.condition.userNo"
                        clearable
                        size="small"
                        placeholder="用户代码"
                        @keyup.enter.native="searchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model="queryCondition.condition.cproductNo"
                        clearable
                        size="small"
                        placeholder="产品代码"
                        @keyup.enter.native="searchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-input
                        v-model="queryCondition.condition.corderNo"
                        clearable
                        size="small"
                        placeholder="客户订单号"
                        @keyup.enter.native="searchEvent"/>
                    </el-form-item>
                    <el-form-item>
                      <el-select
                        v-model="queryCondition.condition.stockType"
                        clearable
                        size="small"
                        placeholder="出库区分类别"
                        @change="getStockCodeList(queryCondition.condition.stockType)"
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
                        v-model="queryCondition.condition.stockCode"
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
                        v-model="queryCondition.condition.dlvEntire"
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
              :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
              :row-style="{height:'33px'}"
              :height="size.rcvTable"
              highlight-current-row
              border
              stripe
              @row-click="findOrderItem"
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
                          v-permission="['889622']"
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
                          v-permission="['889622']"
                          v-if="scope.row.enable && scope.row.dlvEntire!== '2'"
                          size="small"
                          icon="el-icon-edit"
                          circle
                          @click.stop="modifyDlvEntire(scope.row)"/>
                      </el-tooltip>
                    </el-col>
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
                          v-permission="['533117']"
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
        <!-- 库存占用表格 -->
        <div id="ops-table-rcv-item" ref="ops-table-rcv-item">
          <el-table
            v-loading="tableDataItemLoading"
            :data="tableDataItem"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '1px','font-size': '14px'}"
            :cell-style="{padding: '0px 1px'}"
            :row-style="{height:'33px'}"
            :default-sort="{prop: 'splitNo'}"
            :height="size.rcvItemTable"
            highlight-current-row
            border
            stripe
          >
            <!-- 表头字段 -->
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
            <el-table-column
              label="运单号"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <el-tag v-if="scope.row.expressno" size="mini">
                  <a style="color: blue" @click="openExpressInfoWindow(scope.row.expressno,scope.row.carrierid)">
                    <span>
                      {{ scope.row.expressno }}
                    </span>
                  </a>
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column
              label="承运商"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.carrierid }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="预计出库时间"
              width="120"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ dateFormat(scope.row.expectedDeliveryTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="出库时间"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ dateTimeFormat(scope.row.handoverTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作时间"
              show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ dateTimeFormat(scope.row.modifyTime) }}</span>
              </template>
            </el-table-column>

            <!--操作栏 -->
            <el-table-column
              label="操作"
              width="80"
            >
              <template slot-scope="scope">
                <el-button
                  v-permission="['132053']"
                  v-if="scope.row.status !=='CANCEL'&&
                    scope.row.status !=='WM'&&
                    scope.row.status !=='OUT'&&
                  scope.row.status !=='FINISH'"
                  type="text"
                  size="small"
                  @click.stop="openReorderWindow(scope.row,1)">转订
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-dialog
          :visible.sync="uploadFileDialogVisible"
          :show-close="false"
          :close-on-click-modal="false"
          :close-on-press-escape="false"
          width="30%"
        >
          <el-table :data="uploadedFiles" max-height="250" border>
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
            <el-button type="primary" size="mini" @click="uploadFileDialogVisible = false">关 闭</el-button>
          </span>
        </el-dialog>
      </div>
    </el-card>
    <!-- 订单取消 -->
    <CancelOrderDialog :visible.sync="orderCancelDialog.show" :order-data="orderCancelDialog.data" @handleSuccess="cancelSuccess"/>

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
    <!-- 订单还原 申请界面-->
    <ResetOrderDialog :visible.sync="orderResetDialog.show" :order-data="orderResetDialog.data" @handleSuccess="resetSuccess"/>

    <!-- 转定 -->
    <el-dialog :visible.sync="reorderDialog.show" width="70%" title="转订库存" top="10vh">
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
        <el-button :loading="reorderDialog.reorderOrderBtn" type="primary" size="small" style="font-size: 15px" @click="reorderEvent()">转订库存</el-button>
        <el-button type="primary" size="small" style="font-size: 15px" @click="closeReorderWindow">取消
        </el-button>
      </el-row>
    </el-dialog>
    <!-- 订单完纳弹出页面 -->
    <el-dialog :visible.sync="finishOrderDialog.show" width="70%" title="订单完纳" top="10vh">
      <el-divider content-position="left" class="divider_margin">完纳原因</el-divider>
      <!-- 完纳原因 -->
      <el-row style="margin: 10px 10px; min-height: 60px">
        <el-col :span="9">
          <el-cascader
            v-model="finishOrderDialog.reason"
            :options="DictData.calcelReason"
            placeholder="选择原因："
            style="width: 250px;"
            @change="finishReasonSelect"
          />
        </el-col>
        <el-col v-show="finishOrderDialog.other" :span="12">
          <el-input
            v-model="finishOrderDialog.otherReason"
            :autosize="{minRows: 2 }"
            placeholder="输入原因："
            type="textarea"
            style="width: 500px"
          />
        </el-col>
      </el-row>
      <el-divider content-position="left" class="divider_margin">订单状态：</el-divider>
      <el-table
        :data="finishOrderDialog.order.tableData"
        :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
        :cell-style="{padding: '1px 2px'}"
        height="15vh"
        border
        highlight-current-row
      >
        <el-table-column
          v-for="column in finishOrderDialog.order.tableColumns"
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
        v-loading="finishOrderDialog.Loading"
        :data="finishOrderDialog.po.tableData"
        :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
        height="25vh"
        highlight-current-row
        border
      >
        <el-table-column
          v-for="column in finishOrderDialog.po.tableColumns"
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
        <el-button :loading="finishOrderDialog.finishOrderBtn" type="primary" size="small" style="font-size: 15px" @click="finishOrderExe()">订单完纳</el-button>
        <el-button type="primary" size="small" style="font-size: 15px" @click="closeFinishOrderWindow">取消
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
  </div>
</template>

<script>
import {
  enableCancelRcv,
  findAllHouse,
  findDeptDict,
  findSupplier,
  findReceiveView,
  findOrderInv,
  findInventoryType,
  findRequestPurchaseBySqlitNo,
  showItemZD,
  getRoPartStatus,
  showInvByItemQtyZD,
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
import axios from 'axios'

export default {
  name: 'ReceiveOrderSearch',
  components: { Department, CancelOrderDialog, ResetOrderDialog },
  data() {
    return {
      searchCancelTokenSource: null,
      uploadedFiles: [],
      uploadFileDialogVisible: false,
      orderCancelDialog: {
        show: false,
        data: {}
      },
      info: {
        filePath: '',
        randomFileName: ''
      },
      opsAttachedFileManageVO: {
        businessKeyValue: ''
      },
      orderResetDialog: {
        show: false,
        data: {}
      },
      pickerOptions: {
        shortcuts: [
          {
            text: '一个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment().startOf('day').subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '三个月',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment().startOf('day').subtract(3, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '半年',
            onClick(picker) {
              const end = moment().startOf('day')
              const start = moment().startOf('day').subtract(6, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '一年',
            onClick(picker) {
              const end = moment().format('YYYY-MM-DD')
              const start = moment().startOf('day').subtract(1, 'year')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '向前一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).subtract(1, 'months')
              const start = moment(picker.value[0]).subtract(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
            text: '向后一个月',
            onClick(picker) {
              const end = moment(picker.value[1]).add(1, 'months')
              const start = moment(picker.value[0]).add(1, 'months')
              picker.$emit('pick', [start, end])
            }
          }, {
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
        calcelReason: [ // 取消菜单
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
        department: [],
        orderType: [],
        dlvEntire: [],
        dlvsite: [],
        dlvtype: [],
        warehouse: [],
        supplier: [],
        warehouseType: [],
        warehouseName: [],
        inventoryType: [],
        inventoryStatus: [],
        tableType: [],
        stateDetail: [],
        state: [],
        specMark: [],
        warehouseAndSupplier: [],
        stockType: [],
        stockCode: [],
        requestStatus: [],
        purchaseStatus: [],
        expDlvType: []
      },
      // rcv表查询条件
      queryCondition: {
        condition: {
          orderType: '1',
          status: '2',
          intercept: null,
          rorddate: [
            moment().startOf('day').subtract(1, 'months'),
            moment().startOf('day')
          ],
          rorderFnoList: []
        },
        pageNumber: 1,
        pageSize: 20,
        orderBy: 'rorddate desc, rorder_item asc '
      },
      search: {},
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
          width: 90,
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
            var stockType = this.stockTypeFormatter2(row.stockType)
            if (row.stockCode) {
              return stockType + '【' + row.stockCode + '】'
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
            } else if
            (row.prodFlag === '2') {
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
      // 订单明细表字段
      tableColumnsItem: [
        /*
        {
          type: 'index'
        },*/
        {
          label: '拆分单号',
          prop: 'splitNo',
          formatter: row => {
            var str = row.orderId + '-' + row.orderItem
            if (row.splitNo !== 0) {
              str = str + '-' + row.splitNo
            }
            return str
          },
          width: 120
        },
        {
          label: '发货仓',
          prop: 'deliveryWarehouseCode',
          formatter: this.warehouseFormatter
        },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '数量',
          prop: 'qty',
          width: 60,
          align: 'right'
        },
        {
          label: '详细状态',
          formatter: row => {
            var status = this.stateFormatter2(row.status)
            var statusDesc = this.stateTypeFormatter2(row.statusDesc)
            var statusPlace = this.warehouseAndSupplierFormatter2(row.statusPlace)
            if (row.intercept) {
              if (row.status !== 'OUT' && row.status !== 'CANCEL') {
                return '信用拦截'
              }
            }

            if (!row.statusDesc || row.statusDesc === '') {
              return status
            } else if (!row.statusPlace) {
              return status + '【' + statusDesc + '】'
            } else {
              return status + '【' + statusPlace + '-' + statusDesc + '】'
            }
          },
          width: 280
        },
        {
          label: '备注',
          prop: 'remark'
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
          label: '出库区分',
          width: 280,
          formatter: row => {
            var stockType = this.stockTypeFormatter2(row.stockType)
            var stockCode = this.stockCodeFormatter2(row.stockCode)
            var inventoryType = this.inventoryTypeFormatter2(row.inventoryTypeCode)
            if (row.inventoryTypeCode) {
              // if (row.inventoryTypeCode === 'CG') {
              //   return stockType + '【' + stockCode + '】'
              // }
              return stockType + '【' + stockCode + '-' + inventoryType + '】'
            } else if (row.stockCode) {
              return stockType + '【' + stockCode + '】'
            } else if (row.stockType) {
              return stockType
            } else {
              return ''
            }
          }
        },
        {
          label: '关联单号',
          formatter: row => {
            var number = ''
            if (row.associateNo !== null && row.associateNoItem !== '') {
              number = number + row.associateNo
              if (row.associateNoItem !== null && row.associateNoItem !== 0) {
                number = number + '-' + row.associateNoItem
                if (row.associateSplitNo !== null && row.associateSplitNo !== 0) {
                  number = number + '-' + row.associateSplitNo
                }
              }
            }
            return number
          }
        }

        /* {
          label: '承运商',
          prop: 'carrierid'
        },

        {
          label: '出库时间',
          prop: 'handoverTime',
          formatter: this.dateTimeFormatter
        },
        {
          label: '操作时间',
          prop: 'modifyTime',
          formatter: this.dateTimeFormatter
        }*/
      ],
      // 订单明细表数据
      tableDataItem: [],
      tableDataItemLoading: false,
      rcvItemCondition: {},
      rcvView: {},

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
      // 完纳
      finishOrderDialog: {
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
      // rcv表批量查询
      batchSearchDialog: {
        show: false
      },
      // 表格大小布局
      size: {
        search: false,
        rcvTable: '60vh',
        rcvItemTable: 0
      },
      // 发运订单弹框信息
      expressInfoDialog: {
        show: false,
        content: [],
        reverse: false
      }
    }
  },
  computed: {
    ...mapGetters([
      'sidebar'
    ]),
    isCollapse() {
      return !this.sidebar.opened
    },
    dynamicWidth() {
      return this.isCollapse() ? '180px' : '150px'
    }
  },
  created() {
    // 接单状态
    getDataCodesTree('1001').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          if (dict.code !== '12') {
            this.DictData.status.push({ code: dict.code, desc: dict.codeName })
          }
        })
      }
    }).catch(error => {
      console.log(error)
    })
    // 订单项状态
    getDataCodesTree('4304').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.stateDetail.push({ code: dict.code, desc: dict.codeName })
        })
      }
    }).catch(error => {
      console.log(error)
    })
    // 订单项状态
    getDataCodesTree('4305').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.state.push({ code: dict.code, desc: dict.codeName })
        })
      }
    }).catch(error => {
      console.log(error)
    })
    // 订单类型
    getDataCodesTree('1002').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.orderType.push({ code: dict.code, desc: dict.codeName })
        })
      }
    }).catch(error => {
      console.log(error)
    })
    // 营业所
    findDeptDict().then(result => {
      result.forEach(dict => {
        this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
      })
    })
    // 供应商
    findSupplier().then(result => {
      result.content.forEach(dict => {
        this.DictData.supplier.push({ code: dict.id, desc: dict.name })
        this.DictData.warehouseAndSupplier.push({ code: dict.id, desc: dict.name })
      })
    })
    // 仓库名称
    findAllHouse().then(result => {
      result.data.forEach(dict => {
        this.DictData.warehouse.push({ code: dict.warehouseCode, desc: dict.warehouseName })
        this.DictData.warehouseAndSupplier.push({ code: dict.warehouseCode, desc: dict.warehouseName })
      })
    })

    // 出货方式
    getDataCodesTree('4201').then(result => {
      if (result.code === '200') {
        result.content.sort((a, b) => a.sort - b.sort).forEach(dict => {
          this.DictData.dlvEntire.push({ code: dict.code, desc: dict.codeName })
        })
      }
    }).catch(error => {
      console.log(error)
    })
    // 分包方式
    getDataCodesTree('4202').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.dlvtype.push({ code: dict.code, desc: dict.codeName })
        })
      }
    }).catch(error => {
      console.log(error)
    })
    // 字典：仓库类别
    getDataCodesTree('4001').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.warehouseType.push({ code: dict.code, desc: dict.codeName })
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
    // 库存表类型
    getDataCodesTree('4002').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.tableType.push({ code: dict.code, desc: dict.codeName })
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
    // 是否组装（阀与汇流板标识）
    getDataCodesTree('4401').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.specMark.push({ code: dict.code, desc: dict.codeName })
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
    getDataCodesTree('1017').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.expDlvType.push({ code: dict.code, desc: dict.codeName })
        })
      }
    }).catch(error => {
      console.log(error)
    })
  },
  mounted() {
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
    getFilesInfo(row) {
      console.log(row)
      this.opsAttachedFileManageVO.businessKeyValue = row.rorderFno
      findAttacheFiledManageInfo(this.opsAttachedFileManageVO).then(res => {
        if (res.success) {
          this.uploadedFiles = res.content
        }
      })
      this.uploadFileDialogVisible = true
    },
    downLoadFile(row) {
      console.log('row ==> ', row)
      this.$message.success('已开始下载，请耐心等待')
      this.info.randomFileName = row.randomFileName
      this.info.filePath = row.filePath
      dowmLoadAttacheFileManageInfo(this.info).then(res => {
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
      }).catch(error => {
        console.error(error)
      })
    },
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
            this.getTableDataEvent()
          } else {
            this.smcErrorMsg(res.message)
          }
        })
      })
    },

    RecentOneMonth() {
      this.queryCondition.condition.rorddate[0] = moment().startOf('day').subtract(1, 'months')
      this.queryCondition.condition.rorddate[1] = moment().startOf('day')
    },
    getStockCodeList(value) {
      this.DictData.stockCode = []
      var stockCodeMenu = []
      if (value === 'CG') {
        stockCodeMenu = this.DictData.supplier
      } else if (value === 'T') {
        stockCodeMenu = this.DictData.warehouse
      } else if (value === 'N') {
        stockCodeMenu = this.DictData.warehouse
      }
      stockCodeMenu.forEach(item => {
        this.DictData.stockCode.push({ code: item.code, desc: item.desc + '【' + item.code + '】' })
      })
    },

    openExpressInfoWindow(expressno, carrierid) {
      console.log('查询运单号:' + expressno + ',' + carrierid)
      this.expressInfoDialog.content = []
      this.expressInfoDialog.show = true
      getExpressInfo(expressno, carrierid).then(res => {
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
      }).catch()
    },
    closeExpressInfoWindow() {
      this.expressInfoDialog.show = false
      this.expressInfoDialog.content = []
    },
    handleScopeChange(val) {
      this.queryCondition.condition.deptNo = val
    },
    // 查询客户订单
    searchEvent() {
      const date = this.queryCondition.condition.rorddate
      const emptyOrderFno = (
        this.queryCondition.condition.rorderFno == null ||
          this.queryCondition.condition.rorderFno === '' ||
          this.queryCondition.condition.rorderFno.length < 7
      )
      const emptyDate = date === undefined || date === null || date.length === 0
      if (emptyOrderFno && emptyDate) {
        this.smcInfoMsg('请填写至少七位订单号或选择接单日期')
        return false
      }
      var queryCondition = JSON.parse(JSON.stringify(this.queryCondition))
      if (queryCondition.condition.intercept === '' ||
          queryCondition.condition.intercept === undefined) {
        queryCondition.condition.intercept = null
      }
      queryCondition.condition.rorderFnoList = undefined
      this.search = queryCondition
      this.getTableDataEvent()
    },
    // 批量查询操作
    batchSearchEvent() {
      const text = this.batchSearchDialog.text
      if (typeof text === undefined || text === null || text === '' || text.replace(/\s*/g, '').length < 7) {
        this.smcInfoMsg('请填写至少一个订单号')
        return false
      }
      const orderFno = text.split('\n')
      this.queryCondition.condition.rorderFnoList = orderFno
      var queryCondition = JSON.parse(JSON.stringify(this.queryCondition))
      queryCondition.condition = { rorderFnoList: orderFno }
      this.search = queryCondition
      this.batchSearchDialog.show = false
      this.getTableDataEvent()
    },

    getTableDataEvent() {
      this.search.pageNumber = this.queryCondition.pageNumber
      this.search.pageSize = this.queryCondition.pageSize
      this.search.orderBy = this.queryCondition.orderBy
      this.tableDataLoading = true
      // 查询订单信息
      this.findReceive(this.search)
      this.tableDataItem = []
    },

    async findReceive(queryCondition) {
      this.cancelSearchRequest('发起新的接单查询')
      const currentCancelSource = axios.CancelToken.source()
      this.searchCancelTokenSource = currentCancelSource
      findReceiveView(queryCondition, currentCancelSource.token)
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
    async enableCancel(rcv) {
      let result = false
      await enableCancelRcv(rcv).then(res => {
        if (res.success) {
          result = res.data
        }
      })
      return result
    },
    // 订单号批量查询按钮事件
    openBatchSearch() {
      this.batchSearchDialog.show = true
    },

    // 重置查询表单
    resetQueryCondition() {
      this.queryCondition.condition = {
        rorddate: [new Date().getTime() - 3600 * 1000 * 24 * 30, new Date()]
      }
    },

    // 查询订单明细
    findOrderItem(row) {
      this.tableDataItemLoading = true
      this.rcvView = row
      var condition = {
        orderId: row.rorderNo,
        orderItem: row.rorderItem
      }
      this.rcvItemCondition = condition
      findOrderInv(condition).then(result => {
        this.tableDataItem = result.data
        this.tableDataItemLoading = false
      }).catch(error => {
        console.log(error)
      })
    },

    // 打开订单取消弹窗
    openCancelOrderWindow(row) {
      this.orderCancelDialog.show = true
      this.orderCancelDialog.data = row
    },
    // 取消成功后刷新数据
    cancelSuccess(msg) {
      this.refreshTable()
    },
    // 打开订单还原弹窗
    openResetOrderWindow(row) {
      this.orderResetDialog.show = true
      this.orderResetDialog.data = row
    },
    // 还原成功后刷新数据
    resetSuccess(msg) {
      this.refreshTable()
    },

    // 点击转定按钮弹框
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
      showItemZD(row).then(res => {
        if (res.success) {
          this.reorderDialog.InvOriginal.tableData = res.data
          // 采购取消相关
          // 传入十三位单号 等待采购更新接口
          const poObj = {
            orderId: row.orderId,
            orderItem: row.orderItem,
            splitNo: row.splitNo
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
                      if (item.num === poItem.requestSplitno) {
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
                        // 有po  已接单 可选择删除
                        if (poItem.status === '2' && poItem.purchaseno != null) {
                          item.lock = false // 采购复选框不可选
                          item.deletePo = true // 采购复选框默认不选择中
                          item.transferLock = true
                          item.transfer = false // 调库计划复选框默认不选择
                        }
                        // 有po  运输中 不可选择
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
          }).catch()
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(() => {
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
        showInvByItemQtyZD(this.reorderDialog.InvAvailable.query).then(res => {
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
          this.smcErrorMsg('请求失败' + error.toString())
        })
      } else {
        this.reorderDialog.InvAvailable.query = {}
        this.reorderDialog.InvAvailable.tableData = []
        this.smcErrorMsg(row.actionMsg)
      }
    },
    selectAvailableInventory(row) {
      // 点击转定库存
      this.reorderDialog.reorderObject.fromDto.toInvId = row.inventoryId
      this.reorderDialog.reorderObject.fromDto.toInvTableType = row.inventoryTableType
      this.reorderDialog.InvAvailableSelection = row.index
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
        if (paramObj.num === poItem.requestSplitno) {
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
      handleZD(paramObj).then(res => {
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
    // 订单完纳页面
    openFinishOrderWindow(row) {
      this.finishOrderDialog.Loading = false
      this.finishOrderDialog.order.query = {}
      this.finishOrderDialog.order.tableData = []
      this.finishOrderDialog.po.query = {}
      this.finishOrderDialog.po.tableData = []
      this.finishOrderDialog.show = true
      this.finishOrderDialog.order.finishQty = null
      this.finishOrderDialog.order.msg = null
      this.finishOrderDialog.otherReason = null
      this.finishOrderDialog.other = false
      this.finishOrderDialog.reason = null
      const cancelFinishForOrderDto = {
        orderFulNo: row.rorderFno,
        orderNo: row.rorderNo,
        orderItem: String(row.rorderItem),
        modelNo: row.modelNo
      }

      askOrderFinish(cancelFinishForOrderDto).then(res => {
        if (res.success) {
          this.finishOrderDialog.order.tableData.push(res.data)
          this.finishOrderDialog.po.tableData = res.data.poList
        } else {
          this.smcErrorMsg(res.message)
        }
      }).catch(() => {
        this.smcErrorMsg('请求失败')
      })
    },
    finishOrderExe() {
      // 执行订单完纳按钮
      if (this.finishOrderDialog.order.tableData[0] == null) {
        this.smcErrorMsg('无可完纳订单')
        return
      }
      if (this.finishOrderDialog.reason == null) {
        this.smcErrorMsg('请填写完纳原因')
        return
      }
      const fQty = (this.finishOrderDialog.order.tableData[0].finishQty)
      const oQty = (this.finishOrderDialog.order.tableData[0].orderQty)
      const outQty = (this.finishOrderDialog.order.tableData[0].outQty)
      var reason = this.finishOrderDialog.reason[1] === '其他' ? this.finishOrderDialog.otherReason : this.finishOrderDialog.reason[1]
      this.finishOrderDialog.order.tableData[0].msg = reason
      var exeFlag = true
      var poFlag = false
      if (fQty < outQty || fQty >= oQty) {
        exeFlag = false
      } else {
        if (this.finishOrderDialog.po.tableData != null) {
          this.finishOrderDialog.po.tableData.forEach(item => {
            if (item.doStatus === 2) { // 完纳采购单
              const pQty = (item.pQty)
              const arrQty = (item.arrQty)
              const fQty = (item.finishPoQty)
              if (fQty >= arrQty && fQty < pQty) {
                item.finishMsg = reason
                poFlag = true
              } else {
                exeFlag = false
              }
            }
            if (item.doStatus === 1) { // 删采购单
              poFlag = true
            }
          })
        }
      }
      if (!exeFlag) {
        this.smcErrorMsg('订单完纳,完纳数量不在区间范围')
      } else {
        var cancelFinishForOrderDto = this.finishOrderDialog.order.tableData[0]
        cancelFinishForOrderDto.userName = this.$store.getters.name
        cancelFinishForOrderDto.poList = null
        if (poFlag) {
          cancelFinishForOrderDto.poList = this.finishOrderDialog.po.tableData
        }
        this.finishOrderDialog.finishOrderBtn = true
        exeFinish(cancelFinishForOrderDto).then(res => {
          this.finishOrderDialog.finishOrderBtn = false
          if (res.success) {
            this.smcInfoMsg(res.message)
            this.closeFinishOrderWindow()
          } else {
            this.smcErrorMsg(res.message)
          }
        }).catch(() => {
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
      this.finishOrderDialog.other = this.finishOrderDialog.reason[1] === '其他'
    },
    closeFinishOrderWindow() {
      // 完纳取消弹框
      this.finishOrderDialog.order.query = {}
      this.finishOrderDialog.order.tableData = []
      this.finishOrderDialog.po.query = {}
      this.finishOrderDialog.po.tableData = []
      this.finishOrderDialog.show = false
      this.finishOrderDialog.order.finishQty = null
      this.finishOrderDialog.order.msg = null
      this.finishOrderDialog.otherReason = null
      this.finishOrderDialog.other = false
      this.finishOrderDialog.reason = null
    },
    refreshTable() {
      this.findOrderItem(this.rcvItemCondition)
      this.getTableDataEvent()
    },
    clearOther() {
      // 清除其他属性
      const orderFno = this.queryCondition.condition.rorderFno
      this.queryCondition.condition = {
        rorderFno: orderFno
      }
    },
    // 格式化

    // 日期格式化
    dateTimeFormatter(row, column, cellValue) {
      return cellValue ? moment(cellValue).format('YYYY-MM-DD HH:mm:ss') : ''
    },
    dateTimeFormat(value) {
      return value ? moment(value).format('YYYY-MM-DD HH:mm:ss') : ''
    },
    dateFormatter(row, column, cellValue) {
      return cellValue ? moment(cellValue).format('YYYY-MM-DD') : ''
    },
    dateFormat(value) {
      return value ? moment(value).format('YYYY-MM-DD') : ''
    },
    // 营业所字典格式化
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 仓库名称字典格式化
    warehouseFormatter(row, column, cellValue) {
      const item = this.DictData.warehouse.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    warehouseFormatter2(value) {
      const item = this.DictData.warehouse.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    // 状态字典格式化
    statusFormatter(row, column, cellValue) {
      const item = this.DictData.status.find(dict => parseInt(dict.code) === cellValue)
      return item ? item.desc : cellValue
    },
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
    // 出货方式  表格
    dlvEntireFormatter(row, column, cellValue) {
      const item = this.DictData.dlvEntire.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 库存表类型字典格式化
    tableTypeFormatter(row, column, cellValue) {
      const item = this.DictData.tableType.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
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
    inventoryTypeFormatter2(value) {
      const item = this.DictData.inventoryType.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    // 订单项的状态
    stateTypeFormatter(row, column, cellValue) {
      const item = this.DictData.stateDetail.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    stateFormatter(row, column, cellValue) {
      const item = this.DictData.state.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    stateFormatter2(value) {
      const item = this.DictData.state.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    stateTypeFormatter2(value) {
      const item = this.DictData.stateDetail.find(dict => dict.code === value)
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
    // 是否组装（阀与汇流板标识）
    specMarkFormatter(row, column, cellValue) {
      const item = this.DictData.specMark.find(dict => dict.code === cellValue)
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
    // 改变每页条数
    handleSizeChange(newSize) {
      this.queryCondition.pageSize = newSize
      this.getTableDataEvent()
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.queryCondition.pageNumber = newCurrent
      this.getTableDataEvent()
    },
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
    requestStatusFormatter(value) {
      const item = this.DictData.requestStatus.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    purchaseStatusFormatter(value) {
      const item = this.DictData.purchaseStatus.find(dict => dict.code === value)
      return item ? item.desc : value
    },
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
    }

  }

}
</script>
<style scoped lang="scss">

.el-row {
  margin-bottom: 5px;
}

.el-form-item {
  padding-top: 0px;
  margin: 0 5px;
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

.intercept-red {
  color: #F56C6C;
  font-weight: bold;
}

/*运单图标*/
/deep/ .el-timeline-item__node--large {
  left: -7px;
  width: 25px;
  height: 25px;
}

.font-size-large {
  font-size: 18px;
  font-family: "微软雅黑";
  padding: 1px;
}

.rcv-search-menu-open /deep/ .el-form-item  {
  .el-select , .el-input , .searchDeptNoClass{
    width: 160px !important;
  }
}

.rcv-search-menu-close /deep/ .el-form-item  {
  .el-select , .el-input , .searchDeptNoClass {
    width: 180px !important;
  }
}

.searchDeptNoClass /deep/ .el-input__inner{
  height: 30px!important;
}

.menu-department /deep/ .el-cascader__search-input{
  min-width: 1px!important;
  height: 0px!important;
}

</style>

<style lang="scss">

</style>
