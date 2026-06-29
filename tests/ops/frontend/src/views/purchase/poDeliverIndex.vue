<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <div>
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="采购单号">
              <el-input v-model="form.pono" :rows="1" type="textarea" placeholder="请输入采购单号" clearable size="small" style="width: 222px;" />
            </el-form-item>
            <el-form-item label="型号">
              <el-input
                v-model="form.modelNo"
                placeholder="请输入型号"
                clearable
                size="small"
                @keyup.enter.native="getList()" />
            </el-form-item>
            <el-form-item label="采购状态">
              <el-select
                v-model="form.stateCodeList"
                :style="{ width: '91%' }"
                multiple
                collapse-tags
                placeholder=" 请选择采购单状态"
                clearable
                size="small"
                @clear="form.stateCodeList=[]">
                <el-option v-for="item in DictData.status" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="详细状态">
              <el-select
                v-model="form.detailStatusCodeList"
                :style="{ width: '91%' }"
                multiple
                collapse-tags
                placeholder=" 请选择详细状态"
                clearable
                size="small"
                @clear="form.detailStatusCodeList=[]">
                <el-option v-for="item in DictData.statusDetail" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="订货日期">
              <el-date-picker
                v-model="purchaseDate"
                :picker-options="pickerOptions"
                :default-time="['00:00:00', '23:59:59']"
                type="daterange"
                style="width: 220px;"
                align="right"
                size="small"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期" />
            </el-form-item>
            <el-form-item>
              <span class="operation-button">
                <el-button
                  class="filter-item"
                  type="primary"
                  size="mini"
                  icon="el-icon-search"
                  @click="getList()">查询</el-button>
                <el-tooltip effect="light" content="展开" placement="top">
                  <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
                </el-tooltip>
              </span>
            </el-form-item>
          </el-form>
        </div>
        <div v-if="searchMoreForm" class="search">
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="采购类别">
              <el-select
                v-model="form.purchasetypeList"
                multiple
                collapse-tags
                placeholder="请选择采购单类别"
                clearable
                size="small">
                <el-option v-for="item in DictData.type" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="客户">
              <el-input
                v-model="form.customerno"
                placeholder="请输入客户代码"
                clearable
                size="small"
                @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="供应商接单号">
              <el-input
                :rows="1"
                v-model="form.replyOrderNo"
                type="textarea"
                placeholder="请输入供应商接单号"
                style="width: 170px;margin-right: 20px;"
                clearable
                size="small" />
            </el-form-item>
            <el-form-item label="订单类别">
              <el-select
                v-model="form.ordtypeList"
                :style="{ width: '91%' }"
                multiple
                collapse-tags
                placeholder="请选择订单类别"
                clearable
                size="small">
                <el-option v-for="item in DictData.ordtype" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
            <el-form-item label="供应商 " label-width="70px">
              <el-select
                v-model="form.supplierIdList"
                multiple
                collapse-tags
                placeholder="请选择供应商"
                clearable
                size="small">
                <el-option v-for="item in suppilyList" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <div>
        <vxe-toolbar ref="toolbarRef" custom />
        <vxe-table
          ref="poDeliveryTable"
          :loading="listLoading"
          :data="tableData"
          :header-cell-style="tableStyle.tableHeaderCellStyle"
          :cell-style="{ padding: '0px 1px', height: '10px' }"
          :column-config="{resizable: true, maxFixedSize: 0 }"
          :row-config="{ isCurrent: true, isHover: true}"
          :custom-config="{storage: true, checkMethod: checkColumnMethod}"
          stripe
          border
          resizable
          height="660"
        >
          <vxe-column title="序号" align="center" fixed="left" width="70">
            <template #default="{ row, rowIndex }">
              <span>{{ rowIndex + 1 }}</span>
            </template>
          </vxe-column>
          <vxe-column title="采购单号" align="center" fixed="left" width="180">
            <template #default="{ row, rowIndex }">
              <span v-if="row.splitItemNo == null">{{ row.orderNo + "-" + row.itemNo }}</span>
              <span v-else>{{ row.orderNo + "-" + row.itemNo + "-" + row.splitItemNo }}</span>
            </template>
          </vxe-column>
          <vxe-column
            :formatter="departmentFormatter"
            show-overflow
            title="营业所"
            width="120"
            align="center"
            field="deptNo"
          />
          <vxe-column title="供应商接单号" align="center" width="150">
            <template #default="{ row, rowIndex }">
              <span>{{ row.replyOrderNo }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="型号" width="150" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.modelNo }}</span>
            </template>
          </vxe-column>
          <vxe-column
            :formatter="suppilyFormatter"
            field="supplierId"
            show-overflow
            title="供应商"
            width="120"
            align="center" />
          <vxe-column
            :formatter="statecodeFormatter"
            field="stateCode"
            title="状态"
            align="center"
            width="100" />
          <vxe-column
            :formatter="stateDetailFormatter"
            field="detailStatusCode"
            title="详细状态"
            align="center"
            width="120" />
          <vxe-column
            title="状态描述"
            align="center"
            width="100"
            show-overflow="ellipsis"
          >
            <template #default="{ row, rowIndex }">
              <!--悬浮窗-->
              <el-popover
                ref="popover"
                placement="bottom-start"
                trigger="click"
              >
                <!--悬浮的展示表格-->
                <vxe-table
                  v-if="isJSON(row.statusDescription)"
                  :show-header="false"
                  :data="statusInfoData(row.statusDescription)"
                  :cell-style="popoverTableCellStyle"
                  min-height="100"
                >
                  <vxe-table-column field="label" width="120"/>
                  <vxe-table-column field="value" width="150"/>
                </vxe-table>
                <span v-else>{{ row.statusDescription }}</span>
                <!--触发器-->
                <div slot="reference" style="cursor: pointer">
                  <span>{{ formatStatusInfo(row.statusDescription) }}</span>
                </div>
              </el-popover>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="客户代码" width="150" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.customerNo }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="最终用户" width="150" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.endUser }}</span>
            </template>
          </vxe-column>
          <vxe-column
            :formatter="warehouseFormatter"
            field="requestWarehouseId"
            show-overflow
            title="请购仓"
            width="120"
            align="center" />
          <vxe-column
            :formatter="warehouseFormatter"
            field="purchaseWarehouseId"
            show-overflow
            title="采购仓"
            width="120"
            align="center" />
          <vxe-column show-overflow title="采购数量" align="left" width="80">
            <template #default="{ row, rowIndex }">
              <span>{{ row.quantity }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="完成数量" align="center" width="80" field="finishQty"/>
          <vxe-column show-overflow title="出库区分" align="center" width="100" field="produceFactory"/>
          <vxe-column :formatter="ordtypeFormatter" show-overflow title="订单类别" align="center" width="100" field="ordType"/>
          <vxe-column
            :formatter="transtypeFormatter"
            field="transType"
            show-overflow
            title="计划运输方式"
            align="center"
            width="120" />
          <vxe-column show-overflow title="采购日期" width="120" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.purchaseDate | formatDate }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="客户交货期" width="120" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.hopeDeliveryDate | formatDate2 }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="指定制造出荷日" width="120" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.hopeExportDate | formatDate2 }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="供应商接单日" width="120" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.replyOrderDate | formatDate2 }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="开始生产日" width="120" align="center">
            <template #default="{ row, rowIndex }">
              <span>{{ row.beginProduceDate | formatDate2 }}</span>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="返信变更记录" width="120" align="center">
            <template #default="{ row }">
              <el-popover
                placement="right"
                trigger="click">
                <vxe-table :data="[row]" border>
                  <vxe-column width="120" field="dlvModDate" title="变更交货期" >
                    <template #default="{ row }">
                      <span>{{ row.dlvModDate | formatDate2 }}</span>
                    </template>
                  </vxe-column>
                  <vxe-column width="180" field="dlvModDateTime" title="变更时间">
                    <template #default="{ row }">
                      <span>{{ row.dlvModDateTime | formatDate }}</span>
                    </template>
                  </vxe-column>
                  <vxe-column width="300" show-overflow field="reasonRemark" title="变更原因">
                    <template #default="{ row }">
                      <span>{{ row.reasonRemark }}</span>
                    </template>
                  </vxe-column>
                </vxe-table>
                <span slot="reference">{{ row.dlvModDate | formatDate2 }}</span>
              </el-popover>
            </template>
          </vxe-column>
          <vxe-column show-overflow title="shikomi" align="center" width="100" field="shikomiAnswerNo" />
          <vxe-column show-overflow title="准备订单号" align="center" width="120" field="prepareorderno" />

          <vxe-colgroup title="待交付数量" align="center">
            <vxe-column show-overflow title="生产中" align="center" width="80">
              <template #default="{ row }">
                <span v-if="row.produceQtyInfo!==null">
                  <el-tooltip :content="row.produceQtyInfo" placement="top">
                    <!-- <div slot="content">
                    <span>{{ row.produceQtyInfo }}</span>
                  </div> -->
                    <span>{{ row.produceQty }}</span>
                  </el-tooltip>
                </span>
                <span v-else>
                  <span>{{ row.produceQty }}</span>
                </span>
              </template>
            </vxe-column>
            <vxe-column show-overflow title="供应商仓库处理中" align="center" width="140" field="wQty" />
            <vxe-column show-overflow title="运输中" align="center" width="80" field="transQty" />
            <vxe-column show-overflow title="收货中" align="center" width="80" field="receivingQty" />
          </vxe-colgroup>

          <vxe-colgroup title="交付计划" align="center">
            <vxe-column show-overflow title="计划完工日" align="center" width="100">
              <template #default="{ row }">
                <span>{{ row.deliveryPlanH | formatDate2 }}</span>
              </template>
            </vxe-column>
            <vxe-column show-overflow title="计划入库日" align="center" width="100" field="deliveryPlanW" />
            <vxe-column show-overflow title="计划出库日" align="center" width="100" field="deliveryPlanA" />
            <vxe-column :formatter="warehouseFormatter" show-overflow title="发票预计来货仓库" align="center" width="150" field="hopeReceiveWarehouse" />
          </vxe-colgroup>

          <vxe-colgroup title="实际交付" align="center">
            <vxe-column show-overflow title="实际完工日" align="center" width="100">
              <template #default="{ row }">
                <span>{{ row.deliveryFactH | formatDate }}</span>
              </template>
            </vxe-column>
            <vxe-column show-overflow title="实际入库日" align="center" width="100">
              <template #default="{ row }">
                <span>{{ row.deliveryFactW | formatDate2 }}</span>
              </template>
            </vxe-column>
            <vxe-column show-overflow title="实际出库日" align="center" width="100">
              <template #default="{ row }">
                <span>{{ row.deliveryFactA | formatDate2 }}</span>
              </template>
            </vxe-column>
            <vxe-column show-overflow title="发票号" align="center" width="100" field="invoiceno" />
            <vxe-column
              :formatter="transtypeFormatter"
              show-overflow
              field="transtypefact"
              title="运输方式"
              align="center"
              width="100" />
            <vxe-column show-overflow title="到港日" align="center" width="100">
              <template #default="{ row }">
                <span>{{ row.portarrivedate | formatDate }}</span>
              </template>
            </vxe-column>
            <vxe-column show-overflow title="报关日" align="center" width="100">
              <template #default="{ row }">
                <span>{{ row.customsdate | formatDate }}</span>
              </template>
            </vxe-column>
            <!-- <vxe-column show-overflow title="预计到达日" align="center" width="100">
              <template #default="{ row }">
                <span>{{ row.preArriveDate }}</span>
              </template>
            </vxe-column> -->
            <vxe-column
              :formatter="preArriveDateFormatter"
              show-overflow
              field="preArriveDate"
              title="预计到物流日"
              align="center"
              width="150" />

            <vxe-column :formatter="warehouseFormatter" show-overflow title="实物实际入库仓库" align="center" width="150" field="receiveWarehouseId"/>

            <vxe-column show-overflow title="实际物流签收日" align="center" width="150">
              <template #default="{ row }">
                <span >{{ row.arriveDate | formatDate }}</span>
              </template>
            </vxe-column>
            <vxe-column show-overflow title="供应商发票日期" align="center" width="150">
              <template #default="{ row }">
                <span >{{ row.invoiceDate | formatDate2 }}</span>
              </template>
            </vxe-column>
          </vxe-colgroup>

          <vxe-column
            title="操作"
            align="center"
            class-name="small-padding fixed-width"
            fixed="right"
            width="150">
            <template #default="{ row }">
              <el-tooltip effect="light" content="转定" placement="top">
                <span>
                  <el-button
                    v-permission="['249917']"
                    v-if="['1', '2'].includes(row.stateCode)"
                    :loading="buttonloading"
                    circle
                    icon="el-icon-help"
                    type="primary"
                    size="small"
                    @click="transform(row)" />
                </span>
              </el-tooltip>
              <el-tooltip effect="light" content="完纳" placement="top">
                <span>
                  <el-button
                    v-permission="['255329']"
                    v-if="['2', '3'].includes(row.stateCode)"
                    type="primary"
                    size="small"
                    circle
                    icon="el-icon-finished"
                    @click="completePopup(row)" />
                </span>
              </el-tooltip>
              <el-tooltip effect="light" content="订单取消" placement="top">
                <span>
                  <el-button
                    v-permission="['249917']"
                    v-if="row.stateCode === '2'"
                    type="primary"
                    size="small"
                    circle
                    icon="el-icon-delete-solid"
                    @click="cancelVisible(row)" />
                </span>
              </el-tooltip>
            </template>
          </vxe-column>
        </vxe-table>
      </div>
    </div>
    <el-dialog :close-on-click-modal="false" :visible.sync="editFormVisible" title="采购单转订" align="center" width="40%">
      <el-card>
        <el-form
          ref="updateForm"
          :rules="rules"
          :model="updateRequestData"
          label-position="right"
          label-width="120px"
          style="width: 400px">
          <!--bug 13515 采购转订单号显示完整的pono单号 -->
          <el-form-item label="采购单号" prop="pono">
            <el-input
              v-model="updateRequestData.pono"
              :disabled="true"
              clearable
              style="margin-left: 20px; margin-bottom: 5px; width: 300px" />
          </el-form-item>
          <el-form-item label="供应商" prop="supplierid">
            <el-select
              v-model="updateRequestData.supplierid"
              placeholder="请选择供应商"
              style="margin-left: 20px; margin-bottom: 5px; width: 300px"
              clearable
              @change="transSelect()">
              <el-option v-for="item in canSelectsupplierList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="运输方式" prop="transtype">
            <el-select
              v-model="updateRequestData.transtype"
              placeholder="请选择运输方式"
              clearable
              style="margin-left: 20px; margin-bottom: 5px; width: 300px"
              @input="change($event)">
              <el-option v-for="item in canSelectTransList" :key="item.id" :label="item.name" :value="item.id" />
            </el-select>
          </el-form-item>
          <el-form-item label="指定出荷日" prop="hopeexportdate">
            <el-date-picker
              v-model="updateRequestData.hopeexportdate"
              style="margin-left: 20px; margin-bottom: 5px; width: 300px"
              size="middle"
              @input="$forceUpdate()" />
          </el-form-item>
          <el-form-item label="转订原因" prop="information">
            <el-input
              v-model="updateRequestData.information"
              clearable
              style="margin-left: 20px; margin-bottom: 5px; width: 300px" />
          </el-form-item>
          <!--bug11841,请购处理增加加*订货功能-->
          <el-form-item label="特殊标识" prop="greencode">
            <el-select
              v-model="updateRequestData.greencode"
              placeholder="请选择特殊标识"
              clearable
              style="margin-left: 20px; margin-bottom: 5px; width: 300px">
              <el-option
                v-for="item in DictData.producttag"
                :key="item.value"
                :label="item.label"
                :value="item.value" />
            </el-select>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align: center">
        <el-button type="primary" @click="editFormVisible = false">取消</el-button>
        <el-button :loading="transloading" type="primary" @click="transData()">确认</el-button>
      </div>
    </el-dialog>

    <!--强制完纳的弹窗-->
    <el-dialog :close-on-click-modal="false" :visible.sync="completeVisible" title="采购单完纳" align="center" width="40%">
      <el-card>
        <el-form
          ref="completeForm"
          :rules="rulescomplete"
          :model="completeRequestData"
          label-position="right"
          label-width="120px"
          style="width: 400px">
          <el-form-item label="采购单号:" prop="pFullNo">
            <el-input
              v-model="completeRequestData.pFullNo"
              :disabled="true"
              style="margin-left: 20px; margin-bottom: 5px; width: 300px;" />
          </el-form-item>
          <el-form-item label="型号:" prop="modelNo">
            <el-input
              v-model="completeRequestData.modelNo"
              :disabled="true"
              style="margin-left: 20px; margin-bottom: 5px; width: 300px;" />
          </el-form-item>
          <el-form-item label="数量:" prop="pQty">
            <el-input
              v-model="completeRequestData.pQty"
              :disabled="true"
              style="margin-left: 20px; margin-bottom: 5px; width: 300px;" />
          </el-form-item>
          <el-form-item label="已发数量:" prop="arrQty">
            <el-input
              v-model="completeRequestData.arrQty"
              :disabled="true"
              style="margin-left: 20px; margin-bottom: 5px; width: 300px;" />
          </el-form-item>
          <el-form-item label="完纳数量:" prop="finishPoQty">
            <el-input
              v-model="completeRequestData.finishPoQty"
              clearable
              style="margin-left: 20px; margin-bottom: 5px; width: 300px;" />
          </el-form-item>
          <el-form-item label="完纳原因:">
            <el-cascader
              v-model="completeDialog.reason"
              :options="DictData.calcelReason"
              style="
                margin-left: 20px;
                margin-bottom: 5px;
                width: 300px;
                margin-top: 15px;
              "
              @change="completeMenu" />
          </el-form-item>
          <el-form-item v-if="completeDialog.other" label="输入完纳原因:">
            <el-input
              v-model="completeDialog.otherReason"
              :autosize="{ minRows: 2 }"
              type="textarea"
              style="margin-left: 20px; margin-bottom: 5px; width: 300px;" />
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align: center">
        <!-- bug 12446，加入 按钮loading限制，同时加入清空 请求参数-->
        <el-button type="primary" @click="completeClose">取消</el-button>
        <el-button :loading="completeDialog.loading" type="primary" @click="completeData()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="cancelDialog.show" :close-on-click-modal="false" title="取消订单" width="42%" append-to-body>
      <el-form :model="cancelRequestData">
        <el-row>
          <el-col :span="9">
            <el-form-item label="取消原因：">
              <el-cascader v-model="cancelDialog.reason" :options="DictData.calcelReason" @change="cancelMenu" />
            </el-form-item>
          </el-col>
          <el-col v-show="cancelDialog.other" :span="12">
            <el-form-item label="输入原因：" label-width="100px">
              <el-input
                v-model="cancelDialog.otherReason"
                :autosize="{ minRows: 2 }"
                type="textarea"
                style="width: 350px" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" style="text-align: center">
        <!-- bug 12446，加入 按钮loading限制，同时加入清空 请求参数-->
        <el-button type="primary" size="small" @click="cancelClose">取消</el-button>
        <el-button :loading="cancelDialog.loading" type="primary" size="small" @click="cancelData()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogVisible" :close-on-click-modal="false" title="提示" width="30%" height="200px">
      <span>
        <h2>{{ msg }}</h2>
      </span>
    </el-dialog>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="page.pageNumber"
      :limit.sync="page.pageSize"
      @pagination="getList()" />
    <exportExcel ref="exportExcelVue" />
  </div>
</template>
<script>
import {
  transOrder,
  purchaseWn,
  getFinishPo,
  deleteReasonOptions,
  purchaseDel,
  findRequestByOrder,
  getTransIds
} from '@/api/purchaseOrder'
import { findList, findListDetail } from '@/api/po/poDeliver'
import { getSuppily, getTrans, getWarehouse } from '@/api/intercept'
import Pagination from '@/components/Pagination'
import { getDataCodesTree } from '@/api/common/dict'
import Department from '@/components/Department'
import exportExcel from '@/components/ExportExcel/index'
import { cancelDatas } from '@/api/requestPurchase'
import { shortcuts } from '@/utils/dataFormatPurchase'
import { findDeptDict } from '@/api/warehouseManage'
export default {
  name: 'PoDeliver',
  components: { Pagination, exportExcel, Department },
  data() {
    return {
      props: { multiple: true },
      purchaseDate: [
        // moment().startOf('day').subtract(1, 'months'),
        // moment().startOf('day')
      ],
      scopeOptions: [],
      DictData: {
        type: [],
        status: [],
        statusDetail: [],
        transType: [],
        ordtype: [],
        department: [],
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
        // 取消菜单
        calcelReason: []
      },
      pono: '',
      lineitems: 1,
      warehouseList: [],
      cpnysData: [],
      tableData: [],
      // 表格样式
      tableStyle: {
        tableHeaderCellStyle: {
          backgroundColor: 'rgb(117, 144, 168)',
          color: 'rgba(253, 253, 253, 0.938)',
          fontSize: '14px',
          padding: '1px'
        },
        tableCellStyle: { }
      },
      editFormVisible: false,
      deliveryVisible: false,
      apiVisible: false,
      listLoading: false,
      updateRequestData: {
        pono: '',
        supplierid: '',
        transtype: '',
        hopeexportdate: '',
        information: '',
        greencode: '',
        receivewarehouseid: ''
      },
      transParam: {},
      apiRequestData: {},
      // 强制完纳的表单
      completeRequestData: {},
      completeDialog: {
        show: false, // 弹出层显示
        loading: false,
        reason: '',
        other: false, // 其他原因输入框显示
        otherReason: '' // 其他原因的文本
      },
      completeVisible: false,
      // 窗口：取消订单
      cancelDialog: {
        show: false, // 弹出层显示
        loading: false,
        reason: '',
        other: false, // 其他原因输入框显示
        otherReason: '' // 其他原因的文本
      },
      cancelCondition: {},
      total: 0,
      suppilyList: [],
      transList: [],
      canSelectTransList: [],
      canSelectsupplierList: [],
      queryCondition: {},
      deptNoOptions: [],
      page: {
        pageNumber: 1,
        pageSize: 50
      },
      form: {},
      searchMoreForm: false,
      rules: {
        supplierId: [
          { required: true, message: '请选择供应商', trigger: 'blur' }
        ],
        transType: [
          { required: true, message: '请选择运输方式', trigger: 'blur' }
        ],
        serverremark: [
          { required: true, message: '请选择转订原因', trigger: 'blur' }
        ],
        hopedeliverydate: [
          { required: true, message: '请选择出库日', trigger: 'blur' }
        ]
      },
      ruleDelivery: {
        hopedeliverydate: [
          { required: true, message: '请选择交货期', trigger: 'blur' }
        ]
      },
      // 强制完纳弹窗校验
      rulescomplete: {
        finishPoQty: [
          { required: true, message: '请输入完纳数量', trigger: 'blur' }
        ]
      },
      dlvDate: [],
      dialogVisible: false,
      msg: null,
      FinishPoDto: {},
      purchaseCheckCondition: {},
      cancelRequestData: {},
      tableDataRequest: [],
      // 转定按钮事件
      transloading: false,
      buttonloading: false,
      pickerOptions: shortcuts,
      // 转定弹窗，显示可选择的供应商列表
      canSelectTrans: []
    }
  },
  mounted() {
    this.DictData.calcelReason = deleteReasonOptions()
    // 将表格和工具栏进行关联
    const $table = this.$refs.poDeliveryTable
    const $toolbar = this.$refs.toolbarRef
    if ($table && $toolbar) {
      $table.connect($toolbar)
    }
  },
  created() {
    this.initData()
    this.getSuppilyTrans()
    this.getWarehouse()
    this.getDepartments()
  },
  methods: {
    checkColumnMethod({ column }) {
      if (column.title === '序号' || column.title === '操作') {
        return false
      }
      return true
    },
    change() {
      this.$forceUpdate()
    },
    // 处理状态
    initData() {
      this.updateRequestData = {
        pono: '',
        supplierid: '',
        transtype: '',
        hopeexportdate: '',
        information: '',
        greencode: '',
        receivewarehouseid: ''
      }
      getDataCodesTree('2031')
        .then((result) => {
          if (result.code === '200') {
            result.content.forEach((dict) => {
              this.DictData.type.push({
                value: dict.code,
                label: dict.codeName
              })
            })
          }
        })
        .catch((error) => {
          console.log(error)
        })
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
            this.DictData.transType.push({ value: dict.id, label: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      getDataCodesTree('2033')
        .then((result) => {
          if (result.code === '200') {
            result.content.forEach((dict) => {
              this.DictData.status.push({
                value: dict.code,
                label: dict.codeName
              })
            })
          }
        })
        .catch((error) => {
          console.log(error)
        })
      getDataCodesTree('3018').then((result) => {
        if (result.code === '200') {
          result.content.forEach((dict) => {
            this.DictData.statusDetail.push({
              value: dict.code,
              label: dict.codeName
            })
          })
        }
      }).catch((error) => {
        console.log(error)
      })
      getDataCodesTree('1002').then((result) => {
        if (result.code === '200') {
          result.content.forEach((dict) => {
            this.DictData.ordtype.push({
              value: dict.code,
              label: dict.codeName
            })
          })
        }
      }).catch((error) => {
        console.log(error)
      })
    },
    rowStyle({ row, rowIndex }) {
      return 'height:10px'
    },
    getSuppilyTrans() {
      this.getSuppily()
      this.getTrans()
    },
    getTrans() {
      getTrans()
        .then((res) => {
          this.transList = res.data
        })
        .catch((error) => {
          console.log(error)
        })
    },
    getSuppily() {
      getSuppily()
        .then((res) => {
          this.suppilyList = res.data
        })
        .catch((error) => {
          console.log(error)
        })
    },
    getWarehouse() {
      getWarehouse()
        .then((res) => {
          this.warehouseList = res.data
        })
        .catch((error) => {
          console.log(error)
        })
    },
    getDepartments() {
      findDeptDict().then(result => {
        result.forEach(dict => {
          this.deptNoOptions.push({
            code: dict.deptId,
            codeName: dict.deptName
          })
        })
      })
    },
    // 重置表单
    resetForm() {
      this.form = {
        pono: '',
        modelNo: '',
        purchasetype: '',
        stateCode: '',
        customerno: '',
        replyOrderNo: ''
      }
      this.page = {
        pageNumber: 1,
        pageSize: 10
      }
    },
    // 查询所有数据
    getList() {
      this.setDateCondition()
      // bug 12763查询按钮增加校验，单号或者采购时间，必须输入其中之一
      if (
        this.isEmpty(this.form.pono) &&
          this.isEmpty(this.form.purchaseDateStart) &&
          this.isEmpty(this.form.stateCode) &&
          this.isEmpty(this.form.stateCodeList) &&
          this.isEmpty(this.form.detailStatusCode) &&
          this.isEmpty(this.form.detailStatusCodeList) &&
          this.isEmpty(this.form.replyOrderNo)
      ) {
        this.$message({
          showClose: true,
          message: '为避免查询数据量过大，请补充单号或采购时间',
          type: 'error'
        })
        return
      }
      var ponoList = this.form.pono
      if (!this.isEmpty(ponoList)) {
        // 如果ponoList长度大于0且大于500条
        var split = ponoList.split('\n')
        if (split.length > 500) {
          this.$message({
            showClose: true,
            message: '单号数量超过500条，请重新输入',
            type: 'error'
          })
          return
        }
      }
      this.listLoading = true

      if (
        !this.isEmpty(this.form.pono) ||
          !this.isEmpty(this.form.modelNo) ||
          !this.isEmpty(this.form.purchasetype) ||
          !this.isEmpty(this.form.purchasetypeList) ||
          !this.isEmpty(this.form.stateCode) ||
          !this.isEmpty(this.form.stateCodeList) ||
          !this.isEmpty(this.form.detailStatusCode) ||
          !this.isEmpty(this.form.detailStatusCodeList) ||
          !this.isEmpty(this.form.customerno) ||
          !this.isEmpty(this.form.replyOrderNo) ||
          !this.isEmpty(this.form.supplierId) ||
          !this.isEmpty(this.form.supplierIdList) ||
          !this.isEmpty(this.form.purchaseDateStart) ||
          !this.isEmpty(this.form.ordtype) ||
          !this.isEmpty(this.form.ordtypeList)
      ) {
        this.queryCondition.condition = this.form
      }
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = this.page.pageSize
      findList(this.queryCondition)
        .then((res) => {
          if (res.success) {
            if (!res.data.list || res.data.list.length === 0) {
              this.tableData = []
              this.total = 0
              this.listLoading = false
            } else {
              this.tableData = res.data.list
              this.total = res.data.total
              findListDetail(res.data.list).then((a) => {
                this.tableData = a.data
                this.listLoading = false
              })
            }
          } else {
            this.listLoading = false
            this.$message({
              showClose: true,
              message: res.message,
              type: 'error'
            })
            return
          }
        })
        .catch((error) => {
          this.tableData = []
          this.total = 0
          this.listLoading = false
          console.error('查询列表失败:', error)
        })
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        if (obj instanceof Array && obj.length === 0) {
          return true
        }
        return false
      }
    },
    transform(row) {
      this.updateRequestData = {}
      this.buttonloading = true
      this.canSelectTransList = []
      this.canSelectsupplierList = []
      // bug13941,校验当前订单的specmark字段，specmark<>'0'时，查询可选择供应商时增加supplier表中specmark_flag=1的判断
      if (row.specmark === '0') {
        this.canSelectsupplierList = this.suppilyList
      } else {
        this.canSelectsupplierList = this.suppilyList.filter(
          (dict) => dict.specmarkFlag === true
        )
      }
      this.editFormVisible = true
      this.updateRequestData.orderno = row.orderNo
      this.updateRequestData.itemno = row.itemNo
      this.updateRequestData.splititemno = row.splitItemNo
      this.updateRequestData.supplierid = row.supplierId
      this.updateRequestData.transtype = row.transType
      this.updateRequestData.hopeexportdate = row.hopeExportDate
      this.updateRequestData.receivewarehouseid = row.receiveWarehouseId
      // bug 13515 采购转订单号显示完整的pono单号
      if (row.splitItemNo == null) {
        this.updateRequestData.pono = row.orderNo + '-' + row.itemNo
      } else {
        this.updateRequestData.pono =
            row.orderNo + '-' + row.itemNo + '-' + row.splitItemNo
      }
      this.transParam = {}
      this.transParam.supplierId = row.supplierId
      this.transParam.modelNo = row.modelNo
      this.transParam.orderQty = row.quantity
      this.transParam.ordType = row.ordType
      this.transParam.warehouse = row.purchaseWarehouseId
      getTransIds(this.transParam).then(res => {
        this.buttonloading = false
        if (res.success && res.data.length > 0) {
          this.canSelectTransList = res.data
        } else {
          this.DictData.transtype.forEach(dict => {
            this.canSelectTransList.push({ id: dict.value, name: dict.label })
          })
          return
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    // 采购单转订
    transData() {
      this.$refs['updateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将转订该采购单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          })
            .then(() => {
              this.transloading = true
              // bug10483 修改更新人直接从前端获取，B91717
              this.updateRequestData.operator =
                  this.$store.getters.position.psnsmcId
              if (this.isEmpty(this.updateRequestData.operator)) {
                this.$message({
                  showClose: true,
                  message: '当前登录人信息为空，请重新登录后重试',
                  type: 'error'
                })
                this.transloading = false
                return
              }
              transOrder(this.updateRequestData)
                .then((res) => {
                  if (res.success === true) {
                    this.editFormVisible = false
                    this.$notify({
                      title: '成功',
                      message: '转订成功',
                      type: 'success',
                      duration: 2000
                    })
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
                  this.transloading = false
                })
                .catch((error) => {
                  this.transloading = false
                  console.info(error)
                  this.$message.error(error.message)
                })
            })
            .catch(() => {
              this.$message({
                type: 'info',
                message: '已取消该订单转订'
              })
            })
        }
      })
    },
    popoverTableCellStyle({ row, column, rowIndex, columnIndex }) {
      // 这里可以根据具体条件设置颜色
      return { padding: '0px 1px', height: '33px', color: row.color }
    },
    getSupplierName(value) {
      if (value === undefined || value === null || value === '') {
        return value
      }
      const item = this.suppilyList.find((dict) => String(dict.id) === String(value))
      return item ? item.name : value
    },
    getStatusInfoDisplayValue(key, value) {
      if (value === undefined || value === null || value === '') {
        return value
      }
      const normalizedKey = typeof key === 'string' ? key.trim() : key
      if (['供应商', 'supplier', 'supplierId', 'supplierid'].includes(normalizedKey)) {
        return this.getSupplierName(value)
      }
      return value
    },
    formatStatusInfo(value) {
      // 处理空值情况
      if (!value || value === '') {
        return value
      }

      try {
        const jsonObject = JSON.parse(value)
        // 创建一个空字符串来存储格式化后的结果
        let formattedString = ''
        // 遍历对象的每一个属性
        for (const key in jsonObject) {
          // 检查对象自身是否有此属性，防止来自原型链的属性干扰
          if (jsonObject.hasOwnProperty(key)) {
            // 将当前键值对添加到字符串中，用逗号分隔
            const value = jsonObject[key]
            var keyParam = key
            formattedString += `${keyParam}:${this.getStatusInfoDisplayValue(keyParam, value)},`
          }
        }
        // 移除最后一个逗号
        formattedString = formattedString.slice(0, -1)
        return formattedString
      } catch (e) {
        // console.error('格式化状态描述失败:', e)
        return value
      }
    },
    // 判断是否为合法的 JSON 字符串
    isJSON(str) {
      if (typeof str !== 'string') {
        return false
      }
      try {
        JSON.parse(str)
        return true
      } catch (e) {
        return false
      }
    },
    statusInfoData(str) {
      var tableData = []
      // 处理空值情况
      if (!str || str === '') {
        return tableData
      }
      try {
        var map = JSON.parse(str)
        for (const key in map) {
        // 检查对象自身是否有此属性，防止来自原型链的属性干扰
          if (map.hasOwnProperty(key)) {
          // 将当前键值对添加到字符串中，用逗号分隔
            var keyParam = key
            const displayValue = this.getStatusInfoDisplayValue(keyParam, map[key])
            var color = 'black'
            tableData.push({ label: keyParam, value: displayValue, color: color })
          }
        }
      } catch (e) {
        console.error('解析状态描述失败:', e)
        return tableData
      }

      return tableData
    },
    // 订单类型字典格式化
    purchaseTypeFormatter({ row, cellValue }) {
      const item = this.DictData.type.find((dict) => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    suppilyFormatter({ row, cellValue }) {
      return this.getSupplierName(cellValue)
    },

    warehouseFormatter({ row, cellValue }) {
      const item = this.warehouseList.find((dict) => dict.warehouseCode === cellValue)
      return item ? item.warehouseName : cellValue
    },
    departmentFormatter({ row, cellValue }) {
      const item = this.deptNoOptions.find((dict) => dict.code === cellValue)
      return item ? item.codeName : cellValue
    },
    statecodeFormatter({ row, cellValue }) {
      const item = this.DictData.status.find(
        (dict) => dict.value === cellValue
      )
      return item ? item.label : cellValue
    },
    stateDetailFormatter({ row, cellValue }) {
      const item = this.DictData.statusDetail.find(
        (dict) => dict.value === cellValue
      )
      return item ? item.label : cellValue
    },
    // 订单类别转换
    ordtypeFormatter({ row, cellValue }) {
      const item = this.DictData.ordtype.find(
        (dict) => dict.value === cellValue
      )
      return item ? item.label : cellValue
    },
    // 运输方式转换
    transtypeFormatter({ row, cellValue }) {
      const item = this.DictData.transType.find(
        (dict) => dict.value === cellValue
      )
      return item ? item.label : cellValue
    },
    preArriveDateFormatter({ row, cellValue }) {
      var preDateString = cellValue
      if (row.imdatetheoryafter != null) {
        preDateString = row.imdatetheoryafter
      }
      if (preDateString == null) {
        return ''
      }
      const preDate = new Date(preDateString)
      const year = preDate.getFullYear()
      const month = ('0' + (preDate.getMonth() + 1)).slice(-2)
      const day = ('0' + preDate.getDate()).slice(-2)
      const hour = preDate.getHours()
      var s = '下午'
      if (hour === 12) {
        s = '上午'
      }
      if (row.imdatetheoryafter != null) {
        return `*${year}-${month}-${day} ${s}`
      }
      return `${year}-${month}-${day} ${s}`
    },
    handleScopeChange(val) {
      this.form.deptNos = val
    },
    // 日期选择输入框
    setDateCondition() {
      if (this.purchaseDate === null) {
        this.form.purchaseDateStart = ''
        this.form.purchaseDateEnd = ''
      } else {
        this.form.purchaseDateStart = this.purchaseDate[0]
        this.form.purchaseDateEnd = this.purchaseDate[1]
      }
    },
    // 展开更多选项
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    // 强制完纳弹窗
    async completePopup(row) {
      // 展示PO单号
      this.completeVisible = true
      // 后台查询完纳信息
      this.FinishPoDto.pOrderNo = row.orderNo
      this.FinishPoDto.pOrderItem = row.itemNo
      this.FinishPoDto.pSplitNo = row.splitItemNo
      getFinishPo(this.FinishPoDto).then((res) => {
        if (res.success) {
          this.completeRequestData = res.data[0]
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
          this.completeVisible = false
        }
      })
    },
    /**
         * 强制完纳
         */
    completeData(row) {
      this.$refs['completeForm'].validate((valid) => {
        if (valid) {
          this.completeDialog.loading = true
          // 1.先校验完纳数量之间的关系
          const finishPoQty = this.completeRequestData.finishPoQty // 完纳数量
          const pQty = this.completeRequestData.pQty // 采购数量&订单数量
          const arrQty =
              this.completeRequestData.arrQty === null
                ? 0
                : this.completeRequestData.arrQty // 已发数量
          // 校验规则：完纳数量必须大于已发数量，且完纳数量必须小于订单数量
          if (pQty === arrQty) {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: '采购单已全部发出，不允许完纳',
              type: 'error',
              duration: 4000
            })
            this.completeClose()
            return
          }
          if (finishPoQty < pQty && finishPoQty >= arrQty) {
            var reason =
                this.completeDialog.reason[1] === '其他'
                  ? this.completeDialog.otherReason
                  : this.completeDialog.reason[1]
            this.completeRequestData.finishMsg = reason
            this.completeRequestData.operator =
                this.$store.getters.position.psnsmcId
            // 2.执行po完纳
            purchaseWn(this.completeRequestData)
              .then((res) => {
                this.completeClose()
                if (res.success === true) {
                  this.$notify({
                    title: '成功',
                    message: '采购完纳成功',
                    type: 'success',
                    duration: 4000
                  })
                  this.getList()
                } else {
                  this.$message({
                    dangerouslyUseHTMLString: true,
                    showClose: true,
                    message: res.message,
                    type: 'error',
                    duration: 4000
                  })
                }
              })
              .catch((error) => {
                console.info(error)
                this.$message.error(error.message)
                this.completeClose()
              })
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message:
                  '完纳数量必须大于等于已发数量，且完纳数量必须小于订单数量',
              type: 'error',
              duration: 4000
            })
            this.completeClose()
            return
          }
        }
      })
    },
    // bug 12446，加入 按钮loading限制，同时加入清空 请求参数
    // 采购完纳，清空上一次的结果值
    completeClose() {
      this.completeRequestData = {}
      this.completeDialog.loading = false
      this.completeDialog.show = false // 弹出层显示
      this.completeDialog.reason = ''
      this.completeDialog.other = false // 其他原因输入框显示
      this.completeDialog.otherReason = ''
      this.completeVisible = false
    },
    transSelect() {
      this.updateRequestData.transtype = ''
      this.canSelectTransList = []
      this.transParam.supplierId = this.updateRequestData.supplierid
      getTransIds(this.transParam).then(res => {
        if (res.success && res.data.length > 0) {
          this.canSelectTransList = res.data
        } else {
          this.DictData.transtype.forEach(dict => {
            this.canSelectTransList.push({ id: dict.value, name: dict.label })
          })
          return
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      if (this.transList.length === 0) {
        this.getTrans()
      }
    },
    // 取消选项弹出输入框
    cancelMenu() {
      this.cancelDialog.other = this.cancelDialog.reason[1] === '其他'
    },
    completeMenu() {
      this.completeDialog.other = this.completeDialog.reason[1] === '其他'
    },
    cancelVisible(row) {
      findRequestByOrder(row.id)
        .then((res) => {
          this.tableDataRequest = res.data
        })
        .catch((error) => {
          console.log(error)
        })
      this.cancelDialog.show = true
      // this.cancelRequestData = Object.assign({}, row)
      this.cancelRequestData.orderno = row.orderNo
      this.cancelRequestData.itemno = row.itemNo
      this.cancelRequestData.splititemno = row.splitItemNo
      this.cancelRequestData.mergeflag = row.mergeflag
      this.cancelRequestData.statecode = row.stateCode
    },
    // 取消订单，调用wm接口
    cancelData() {
      this.cancelDialog.loading = true
      // 调用后台校验接口
      this.purchaseCheckCondition.orderno = this.cancelRequestData.orderno
      this.purchaseCheckCondition.itemno = this.cancelRequestData.itemno
      this.purchaseCheckCondition.splititemno =
          this.cancelRequestData.splititemno
      this.purchaseCheckCondition.requestpurchaselist = this.tableDataRequest
      purchaseDel(this.purchaseCheckCondition)
        .then((result) => {
          if (result.success === true) {
            this.cancelCondition.orderno = this.cancelRequestData.orderno
            this.cancelCondition.itemno = this.cancelRequestData.itemno
            this.cancelCondition.splititemno =
                this.cancelRequestData.splititemno
            this.cancelCondition.mergeflag = this.cancelRequestData.mergeflag
            this.cancelCondition.statecode = this.cancelRequestData.statecode
            this.cancelCondition.canceltype = '1'
            // bug10483 修改更新人直接从前端获取，B91717
            this.cancelCondition.operator =
                this.$store.getters.position.psnsmcId
            // bug12344 采购删单新增 删单原因，记录到删单表中
            var reason =
                this.cancelDialog.reason[1] === '其他'
                  ? this.cancelDialog.otherReason
                  : this.cancelDialog.reason[1]
            this.cancelCondition.reason = reason
            if (this.isEmpty(this.cancelCondition.operator)) {
              this.$message({
                showClose: true,
                message: '当前登录人信息为空，请重新登录后重试',
                type: 'error'
              })
              this.cancelClose()
              return
            }
            cancelDatas(this.cancelCondition)
              .then((res) => {
                if (res.success === true) {
                  this.$notify({
                    title: '成功',
                    message: '采购删单成功',
                    type: 'success',
                    duration: 4000
                  })
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
                this.cancelClose()
              })
              .catch((error) => {
                console.info(error)
                this.$message.error(error.message)
              })
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: result.message,
              type: 'error',
              duration: 0
            })
            this.cancelClose()
          }
        })
        .catch((error) => {
          console.info(error)
          this.$message.error(error.message)
        })
    },
    // bug 12446，加入 按钮loading限制，同时加入清空 请求参数
    cancelClose() {
      this.cancelDialog.show = false
      this.cancelDialog.loading = false
      this.cancelDialog.reason = ''
      this.cancelDialog.other = false // 其他原因输入框显示
      this.cancelDialog.otherReason = ''
      this.cancelRequestData = {}
      this.purchaseCheckCondition = {}
      this.cancelCondition = {}
    }
  }
}
</script>
<style scoped>

  .el-form-item {
    margin-bottom: 4px;
  }

  .el-button--primary {
    color: #337ab7;
    background-color: #fff;
    border: 1px solid #337ab7;
  }

  .el-pagination.is-background .el-pager li:not(.disabled).active {
    background-color: #337ab7;
    color: #fff;
  }
</style>

