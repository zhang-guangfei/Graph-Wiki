<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <div>
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="请购单号">
              <el-input v-model="form.orderno" placeholder="请输入请购单号" style="width=100%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="型号">
              <el-input v-model="form.modelno" placeholder="请输入型号" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="请购单类别" >
              <el-select v-model="form.purchasetype" placeholder="请选择请购单类别" clearable size="small">
                <el-option
                  v-for="item in DictData.type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="营业所">
              <department style="margin-left: -1px; width: 200px" @handleScopeChange="handleScopeChange"/>
            </el-form-item>
            <!-- bug 7538,增加供应商可以多选-->
            <el-form-item label="供应商">
              <el-select
                v-model="form.supplierids"
                multiple
                collapse-tags
                placeholder="请选择供应商"
                clearable
                size="small">
                <el-option
                  v-for="item in suppilyList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
            <!-- 新增按拦截原因查询-->
            <el-form-item>
              <span class="operation-button">
                <!-- <el-tooltip effect="light" content="重置" placement="top">
                <el-button type="info" icon="el-icon-close" size="medium" circle @click="resetForm()"/>
              </el-tooltip> -->
                <el-button v-permission="['710105']" class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList()">查询</el-button>
                <!-- <el-tooltip effect="light" content="跳转至所有请购单查询" placement="top">
                  <el-button class="filter-item" type="primary" size="mini" icon="el-icon-s-promotion" @click="toUntreated()"/>
                </el-tooltip> -->
                <el-tooltip effect="light" content="展开" placement="top">
                  <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
                </el-tooltip>
              </span>
            </el-form-item>
          </el-form>
        </div>
        <div v-if="searchMoreForm" class="search" style="margin-top: 7px">
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="运输方式" >
              <el-select v-model="form.transtype" placeholder="请选择运输方式" clearable size="small">
                <el-option
                  v-for="item in DictData.transtype"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="订单类别" >
              <el-select v-model="form.ordtype" placeholder="请选择订单类别" clearable size="small">
                <el-option
                  v-for="item in DictData.ordtype"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="客户单号">
              <el-input v-model="form.corderno" placeholder="请输入客户单号" style="width=100%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="采购仓库" >
              <el-select v-model="form.purchasewarehouseid" placeholder="请选择采购仓库" clearable size="small">
                <el-option
                  v-for="item in warehouseList"
                  :key="item.warehouseCode"
                  :label="item.warehouseName"
                  :value="item.warehouseCode"/>
              </el-select>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <el-button v-permission="['182331']" :disabled="selectList.length <1" :loading = "editloading" size="mini" type="primary" icon="el-icon-edit-outline" style="margin-top:5px;margin-bottom:5px;float:right;" @click="editBatch()">批量编辑</el-button>
      <el-button v-permission="['182331']" :disabled="selectList.length <1" size="mini" type="primary" icon="el-icon-s-claim" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:right;" @click="infoData(row)">批量处理</el-button>
      <el-tooltip effect="light" content="按筛选条件，处理全部数据~" placement="right-start">
        <el-button v-permission="['182331']" :disabled="tableData.length <1" size="mini" type="primary" icon="el-icon-success" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="confirmAllData()">全部处理</el-button>
      </el-tooltip>
      <el-tooltip effect="light" content="按筛选条件,直接发送采购~" placement="right-start">
        <el-button v-permission="['182331']" :disabled="tableData.length <1" size="mini" type="primary" icon="el-icon-s-unfold" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="requestToPurchase()">直发采购</el-button>
      </el-tooltip>
      <!--bug11997,增加手工拦截功能-->
      <el-tooltip effect="light" content="暂不处理,手工进行拦截~" placement="right-start">
        <el-button v-permission="['182331']" :disabled="tableData.length <1" size="mini" type="primary" icon="el-icon-scissors" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="unHandle()">暂不处理</el-button>
      </el-tooltip>
      <div style="margin-top:20px;">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '12px'}"
          :cell-style="{padding: '2px 3px'}"
          element-loading-text="拼命加载中"
          border
          fit
          highlight-current-row
          height="660"
          style="width:100%"
          @sort-change="sortChange"
          @selection-change="selectChangeEvent"
          @select-all="selectAllEvent"
        >
          <el-table-column
            type="selection"
            width="55"/>
          <!-- <el-table-column type="index" show-overflow-tooltip align="center" size="small"/> -->
          <el-table-column label="序号" align="center" fixed="left" prop="id" min-width="70">
            <template slot-scope="scope">
              <!-- <el-tag size="medium">
                <span>{{ scope.$index + 1 }}</span>
              </el-tag> -->
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <!--bug12361 OPS采购查询 营业所字段数据错误-->
          <!-- <el-table-column :formatter="deptFormatter" prop="applyDeptNo" fixed="left" show-overflow-tooltip label="申请部门" min-width="90" align="center"/> -->
          <el-table-column :formatter="deptFormatter" prop="deptno" show-overflow-tooltip label="营业所" min-width="110" align="center"/>
          <el-table-column show-overflow-tooltip sortable="custom" label="请购单号" prop= "orderno" align="left" min-width="150">
            <template slot-scope="scope">
              <span v-if="scope.row.splititemno==null">
                {{ scope.row.orderno+"-"+scope.row.itemno }}
              </span>
              <span v-if="scope.row.splititemno!==null">
                {{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}
              </span>
              <!-- <span>{{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}</span> -->
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip sortable="custom" label="型号" prop= "modelno" align="left" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.modelno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购数量" sortable="custom" prop= "quantity" align="left" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="suppilyFormatter" prop="supplierid" show-overflow-tooltip label="供应商" min-width="110" align="center"/>
          <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" align="center" min-width="90"/>
          <el-table-column show-overflow-tooltip label="制造出荷日" prop="hopeexportdate" sortable="custom" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="smcCode" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.smccode }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="ordtypeFormatter" prop="ordtype" label="订单类别" min-width="140" align="center"/>
          <el-table-column label="请购单类别" align="center" min-width="110">
            <template slot-scope="scope">
              <span>{{ scope.row.purchasetype | ordTypeFormat }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="重量" align="center">
            <template slot-scope="scope">
              <span>{{ (scope.row.netweight * scope.row.quantity).toFixed(4) }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="SHIKOMI" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.shikomianswerno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="准备订单号" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.prepareorderno }}</span>
            </template>
          </el-table-column>
          <el-table-column label="供应商在库" min-width="90" align="center">
            <!-- 需新增字段 -->
            <template slot-scope="scope">
              <span>{{ scope.row.supplierinventory }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="specmarkFormatter" prop="specmark" show-overflow-tooltip label="组装标识" min-width="90" align="center"/>
          <!--bug11841,请购处理增加加*订货功能-->
          <el-table-column :formatter="producttagFormatter" prop="producttag" show-overflow-tooltip label="特殊标识" min-width="90" align="center"/>
          <el-table-column show-overflow-tooltip label="业务备注" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.serverremark }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购日期" prop="requesttime" sortable="custom" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.requesttime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="SHIKOMI" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.shikomianswerno }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="warehouseFormatter" prop="requestwarehouseid" show-overflow-tooltip label="请购仓库" align="center" min-width="100"/>
          <el-table-column show-overflow-tooltip label="客户单号" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.corderno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="操作人" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.operator }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="supplierAssignTypeFormatter" prop="supplierAssignType" show-overflow-tooltip label="指定供应商" align="center" min-width="130"/>
          <el-table-column show-overflow-tooltip label="是否确认" min-width="100" align="center">
            <template slot-scope="scope">
              <span v-if="scope.row.isedited===false">
                {{ "未处理" }}
                <i class="el-icon-warning" style="color: #FF5809; cursor: pointer; font-size: 10px;"/>
              </span>
              <span v-if="scope.row.isedited===true">
                {{ "已确认" }}
                <i class="el-icon-success" style="color: #00BB00; cursor: pointer; font-size: 10px;"/>
              </span>
            </template>
          </el-table-column>
          <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width" min-width="200">
            <template slot-scope="scope">
              <el-tooltip effect="light" content="编辑" placement="top">
                <el-button v-permission="['182331']" :loading = "editloading" type="primary" size="small" circle icon="el-icon-edit" @click="edit(scope.row)"/>
              </el-tooltip>
              <el-tooltip effect="light" content="确认" placement="top">
                <el-button v-permission="['182331']" type="primary" size="small" circle icon="el-icon-success" @click="infoDataOne(scope.row)"/>
              </el-tooltip>
              <!--bug11997,增加手工拦截功能-->
              <el-tooltip effect="light" content="手工拦截" placement="top">
                <el-button v-permission="['182331']" type="primary" size="small" circle icon="el-icon-scissors" @click="interceptHandle(scope.row)"/>
              </el-tooltip>
              <el-tooltip effect="light" content="订单取消" placement="top">
                <span>
                  <!--bug 12991 请购画面 放开删单的限制 v-if="['3','20','21','24','25'].includes(scope.row.ordtype)"-->
                  <!--bug 12141 DR/CR补采购订单，请购处理界面增加取消按钮-->
                  <el-button v-permission="['182331']" size="small" circle icon="el-icon-delete-solid" @click="cancelVisible(scope.row)"/>
                </span>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog :visible.sync="editFormVisible" title="修改数据" align="center" width="40%">
      <el-card>
        <el-form ref="updateForm" :rules="rules" :model="updateRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="请购单号" prop="orderno">
            <el-input v-model="updateRequestData.orderno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="供应商" prop="supplierid">
            <el-select v-model="updateRequestData.supplierid" placeholder="请选择供应商" style="margin-left:20px;margin-bottom:5px;width:300px" clearable @change="transSelect(updateRequestData, false)">
              <el-option
                v-for="item in canSelectsupplierList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="指定供应商" prop="supplierAssignType">
            <el-select v-model="updateRequestData.supplierAssignType" placeholder="请选择指定供应商类别" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.supplierAssignType"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="运输方式" prop="transtype">
            <el-select v-model="updateRequestData.transtype" placeholder="请选择运输方式" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)">
              <el-option
                v-for="item in canSelectTransList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="指定出荷日" prop="hopeexportdate">
            <el-date-picker
              v-model="updateRequestData.hopeexportdate"
              style="margin-left:20px;margin-bottom:5px;width:300px"
              size="middle"/>
          </el-form-item>
          <!--bug11841,请购处理增加加*订货功能-->
          <el-form-item label="特殊标识" prop="producttag">
            <el-select v-model="updateRequestData.producttag" placeholder="请选择特殊标识" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.producttag"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="业务备注" prop="serverremark">
            <el-input v-model="updateRequestData.serverremark" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="editFormVisible = false">取消</el-button>
        <el-button :loading="buttonloading" type="primary" @click="updateData()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="editFormBatchVisible" title="批量修改数据" align="center" width="40%">
      <el-card>
        <el-form ref="updateFormBatch" :rules="batchRules" :model="updateRequestDataBatch" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="请购单号" prop="orderno">
            <el-input v-model="updateRequestDataBatch.orderno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <!--@change="transSelect(updateRequestDataBatch)"-->
          <el-form-item label="供应商" prop="supplierid">
            <el-select v-model="updateRequestDataBatch.supplierid" placeholder="请选择供应商" style="margin-left:20px;margin-bottom:5px;width:300px" clearable @change="transSelect(updateRequestDataBatch, true)">
              <el-option
                v-for="item in canSelectsupplierList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="指定供应商" prop="supplierAssignType">
            <el-select v-model="updateRequestDataBatch.supplierAssignType" placeholder="请选择指定供应商类别" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.supplierAssignType"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="运输方式" prop="transtype">
            <el-select v-model="updateRequestDataBatch.transtype" placeholder="请选择运输方式" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)">
              <el-option
                v-for="item in canSelectTransList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item v-if="batchCarrierShow" label="承运商" prop="carrier" >
            <el-select v-model="updateRequestDataBatch.carrier" placeholder="请选择运输方式" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.carrier"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="指定出荷日" prop="hopeexportdate">
            <el-date-picker
              v-model="updateRequestDataBatch.hopeexportdate"
              style="margin-left:20px;margin-bottom:5px;width:300px"
              size="middle"/>
          </el-form-item>
          <!--bug11841,请购处理增加加*订货功能-->
          <el-form-item label="特殊标识" prop="producttag">
            <el-select v-model="updateRequestDataBatch.producttag" placeholder="请选择特殊标识" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.producttag"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="业务备注" prop="serverremark">
            <el-input v-model="updateRequestDataBatch.serverremark" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="editFormBatchVisible = false">取消</el-button>
        <el-button :loading="buttonloading" type="primary" @click="updateDataBatch()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="cancelDialog.show" :close-on-click-modal="false" title="取消订单" width="42%" append-to-body>
      <el-form :model="cancelRequestData">
        <el-row>
          <el-col :span="9">
            <el-form-item label="取消原因：">
              <el-cascader
                v-model="cancelDialog.reason"
                :options="DictData.calcelReason"
                @change="cancelMenu"
              />
            </el-form-item>
          </el-col>
          <el-col v-show="cancelDialog.other" :span="12">
            <el-form-item label="输入原因：" label-width="100px">
              <el-input
                v-model="cancelDialog.otherReason"
                :autosize="{minRows: 2 }"
                type="textarea"
                style="width: 350px"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" size="small" @click="cancelDialog.show = false">取消</el-button>
        <el-button :loading="cancelDialog.loading" type="primary" size="small" @click="cancelData()">确认</el-button>
      </div>
    </el-dialog>
    <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getList()" />
  </div>
</template>
<script>
import { findList, updateSuppily, requestConfirm, requestConfirmOne, updateRequestBatch, confirmAll, cancelDatas, toPurchase, unHandleRequest } from '@/api/requestPurchase'
import { purchaseDel, deleteReasonOptions, getTransIds } from '@/api/purchaseOrder'
import { findDeptDict } from '@/api/warehouseManage'
import { getSuppily, getWarehouse } from '@/api/intercept'
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import { getDataCodesTree } from '@/api/common/dict'
export default {
  name: 'RequestPurchaseQuery',
  components: { Pagination, Department },
  data() {
    const validateBatchRemark = ({ row, cellValue }) => {
      return new Promise((resolve, reject) => {
        if (this.updateRequestDataBatch.supplierid == null) {
          reject(new Error('请先校验正确订单'))
          return
        }
        if (this.updateRequestDataBatch.supplierid === 'JP') {
          // 正则说明：^[a-zA-Z ,.!?-]{1,24}$
          // 允许字符：英文字母(大小写)、空格、逗号、句号、问号、感叹号、连字符
          // 长度限制：1-24个字符
          // const jpRegex = /^[a-zA-Z ,.!?-]{1,24}$/
          const jpRegex = /^[a-zA-Z0-9 ,.!?()-]*$/
          if (!jpRegex.test(this.updateRequestDataBatch.serverremark)) {
            reject(new Error('日本订单备注必须英文允许基础标点'))
            return
          }
          if (new TextEncoder().encode(this.updateRequestData.serverremark).length > 24) {
            reject(new Error('不可多余24字节'))
            return
          }
          resolve()
          return
        }
        if (this.updateRequestDataBatch.supplierid === 'CN' || this.updateRequestDataBatch.supplierid === 'CM' || this.updateRequestDataBatch.supplierid === 'HK' ||
          this.updateRequestDataBatch.supplierid === 'YZ' || this.updateRequestDataBatch.supplierid === 'CT' || this.updateRequestDataBatch.supplierid === 'GZ' ||
          this.updateRequestDataBatch.supplierid === 'TZ' || this.updateRequestDataBatch.supplierid === 'TW' || this.updateRequestDataBatch.supplierid === 'GN') {
          resolve()
          return
        }
        // 允许字符：英文字母(大小写)、空格、逗号、句号、问号、感叹号、连字符
        const jpRegex = /^[a-zA-Z0-9 ,.!?()-]*$/
        if (!jpRegex.test(this.updateRequestDataBatch.serverremark)) {
          reject(new Error('海外订单备注必须为英文字符允许基础标点'))
          return
        }
        resolve()
      })
    }
    const validateRemark = ({ row, cellValue }) => {
      return new Promise((resolve, reject) => {
        if (this.updateRequestData.supplierid == null) {
          reject(new Error('请先校验正确订单'))
          return
        }
        if (this.updateRequestData.supplierid === 'JP') {
          // 正则说明：^[a-zA-Z ,.!?-]{1,24}$
          // 允许字符：英文字母(大小写)、空格、逗号、句号、问号、感叹号、连字符
          // 长度限制：1-24个字符
          // const jpRegex = /^[a-zA-Z ,.!?-]{1,24}$/
          const jpRegex = /^[a-zA-Z0-9 ,.!?()-]*$/
          if (!jpRegex.test(this.updateRequestData.serverremark)) {
            reject(new Error('日本订单备注必须英文允许基础标点'))
            return
          }
          if (new TextEncoder().encode(this.updateRequestData.serverremark).length > 24) {
            reject(new Error('不可多余24字节'))
            return
          }
          resolve()
          return
        }
        if (this.updateRequestData.supplierid === 'CN' || this.updateRequestData.supplierid === 'CM' || this.updateRequestData.supplierid === 'HK' ||
          this.updateRequestData.supplierid === 'YZ' || this.updateRequestData.supplierid === 'CT' || this.updateRequestData.supplierid === 'GZ' ||
          this.updateRequestData.supplierid === 'TZ' || this.updateRequestData.supplierid === 'TW' || this.updateRequestData.supplierid === 'GN') {
          resolve()
          return
        }
        // 允许字符：英文字母(大小写)、空格、逗号、句号、问号、感叹号、连字符
        const jpRegex = /^[a-zA-Z0-9 ,.!?()-]*$/
        if (!jpRegex.test(this.updateRequestData.serverremark)) {
          reject(new Error('海外订单备注必须为英文字符允许基础标点'))
          return
        }
        resolve()
      })
    }
    return {
      batchCarrierShow: false,
      props: { multiple: true },
      DictData: {
        type: [],
        department: [],
        ordtype: [],
        specmark: [],
        producttag: [],
        // 取消菜单
        calcelReason: [],
        transtype: [],
        supplierAssignType: [] // bug19540 WTSR2025001597-配合PMS分单系统OPS系统发单功能新增
      },
      scopeOptions: [],
      searchMoreForm: false,
      cpnysData: [],
      selectList: [],
      tableData: [],
      editFormVisible: false,
      editFormBatchVisible: false,
      listLoading: true,
      updateRequestData: {},
      updateRequestDataBatch: {},
      deptNoOptions: [],
      total: 0,
      suppilyList: [],
      canSelectTransList: [],
      canSelectsupplierList: [],
      queryCondition: {},
      confirmCondition: {},
      warehouseList: [],
      page: {
        pageNumber: 1,
        pageSize: 50
      },
      form: {
      },
      rules: {
        supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        transtype: [{ required: true, message: '请选择运输方式', trigger: 'blur' }],
        hopeexportdate: [{ required: true, message: '请选择出库日', trigger: 'blur' }],
        serverremark: [{ validator: validateRemark, trigger: 'blur' }],
        supplierAssignType: [{ required: true, message: '请选择指定供应商类别', trigger: 'blur' }]
      },
      batchRules: {
        supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        transtype: [{ required: true, message: '请选择运输方式', trigger: 'blur' }],
        hopeexportdate: [{ required: true, message: '请选择出库日', trigger: 'blur' }],
        serverremark: [{ validator: validateBatchRemark, trigger: 'blur' }],
        supplierAssignType: [{ required: true, message: '请选择指定供应商类别', trigger: 'blur' }]
      },
      // 窗口：取消订单
      cancelDialog: {
        show: false, // 弹出层显示
        loading: false,
        cancelObject: {}, // 取消的对象
        reason: '',
        other: false, // 其他原因输入框显示
        otherReason: '' // 其他原因的文本
      },
      cancelCondition: {},
      purchaseCheckCondition: {},
      cancelRequestData: {},
      tableDataRequest: [],
      // 通用
      buttonloading: false,
      editloading: false
    }
  },
  mounted() {
    this.DictData.calcelReason = deleteReasonOptions()
  },
  created() {
    this.initData()
    this.getList()
    this.getSuppilyTrans()
    this.getWarehouseList()
    // 营业所
    findDeptDict().then(result => {
      result.forEach(dict => {
        this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
      })
    })
  },
  methods: {
    change() {
      this.$forceUpdate()
    },
    handleScopeChange(val) {
      this.form.deptNos = val
    },
    // 排序
    sortChange(column, prop, order) {
      // this.queryCondition.orderBy = column.prop
      if (column.order === 'descending') {
        this.queryCondition.orderBy = column.prop + ' desc'
      }
      if (column.order === 'ascending') {
        this.queryCondition.orderBy = column.prop + ' asc'
      }
      this.getList()
    },
    initData() {
      getDataCodesTree('2031').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.type.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      getDataCodesTree('1002').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.ordtype.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      // bug 10526 修正组装标识前端显示
      getDataCodesTree('4401').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.specmark.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
        this.$message.error(error.message)
      })
      // bug11841,请购处理增加加*订货功能
      getDataCodesTree('2080').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.producttag.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
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
            this.DictData.transtype.push({ value: dict.id, label: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      // bug19540 请购处理增加指定供应商类别
      getDataCodesTree('2094').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.supplierAssignType.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // bug11997 单条拦截不处理
    interceptHandle(row) {
      row.operator = this.$store.getters.position.psnsmcId
      this.selectList = []
      this.selectList.push(row)
      this.unHandle()
    },
    // bug11997 暂不处理，将订单还原到拦截状态，不参与自动发单
    unHandle() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要手工拦截请购单'
        })
        return
      }
      this.$confirm('此操作将手工拦截该请购单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        unHandleRequest(this.selectList).then(res => {
          if (res.success === true) {
            this.$notify({
              title: '成功',
              message: '手工拦截成功',
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
            this.listLoading = false
          }
        }).catch(error => {
          this.listLoading = false
          console.info(error)
          this.$message.error(error.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消手工拦截该订单'
        })
      })
      this.listLoading = false
    },
    // 勾选确认数据，变更请购单状态为待处理
    infoData() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要批量确认的请购单'
        })
        return
      }
      requestConfirm(this.selectList).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: '批量处理成功',
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
          this.listLoading = false
        }
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    infoDataOne(row) {
      // bug10483 修改更新人直接从前端获取，B91717
      row.operator = this.$store.getters.position.psnsmcId
      requestConfirmOne(row).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: '处理成功',
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
          this.listLoading = false
        }
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 列表复选框改变事件
    selectChangeEvent(records) {
      this.selectList = []
      records.forEach(element => {
        element.operator = this.$store.getters.position.psnsmcId
        this.selectList.push(element)
      })
    },
    // 列表全选事件
    selectAllEvent(records) {
      this.selectList = []
      records.forEach(element => {
        element.operator = this.$store.getters.position.psnsmcId
        this.selectList.push(element)
      })
    },
    rowStyle({ row, rowIndex }) {
      return 'height:2px'
    },
    // 营业所字典格式化
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 订单类别转换
    ordtypeFormatter(row, column, cellValue) {
      const item = this.DictData.ordtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // bug 10526 修正组装标识前端显示
    specmarkFormatter(row, column, cellValue, index) {
      const item = this.DictData.specmark.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // bug11841,请购处理增加加*订货功能,特殊标识设置字典
    producttagFormatter(row, column, cellValue) {
      const item = this.DictData.producttag.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    /**
     * 供应商运输方式联动
     */
    transSelect(data, batchFlag) {
      if (batchFlag) {
        this.updateRequestDataBatch.serverremark = ''
        this.updateRequestDataBatch.transtype = ''
        this.$refs.updateFormBatch.clearValidate(['serverremark'])
      } else {
        this.updateRequestData.transtype = ''
        this.updateRequestData.serverremark = ''
        this.$refs.updateForm.clearValidate(['serverremark'])
      }
      this.canSelectTransList = []
      var transParam = {}
      transParam.supplierId = data.supplierid
      transParam.modelNo = data.modelno
      transParam.orderQty = data.quantity
      transParam.ordType = data.ordtype
      transParam.warehouse = data.purchasewarehouseid
      getTransIds(transParam).then(res => {
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
    getSuppilyTrans() {
      this.getSuppily()
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 重置表单
    resetForm() {
      // localStorage.removeItem(this.condition)
      // localStorage.removeItem(this.page)
      this.form = {}
      this.page = {
        pageNumber: 1,
        pageSize: 100
      }
      // this.$refs.form.resetFields()
      // this.form.orderno = ''
      // this.form.modelno = ''
      // this.form.ordtype = ''
    },
    // 查询所有数据
    getList() {
      this.listLoading = true
      if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.purchasetype) ||
      !this.isEmpty(this.form.transtype) || !this.isEmpty(this.form.supplierids) || !this.isEmpty(this.form.deptNos) ||
       !this.isEmpty(this.form.ordtype) || !this.isEmpty(this.form.corderno) || !this.isEmpty(this.form.purchasewarehouseid)) {
        this.queryCondition.condition = this.form
      }
      // this.queryCondition = Object.assign({}, this.form)
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = this.page.pageSize
      findList(this.queryCondition).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 请购单直接转到采购表，不经过合并采购页面
    requestToPurchase() {
      this.$confirm('此操作将会直接发送采购,不经过合并采购,是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.purchasetype) || !this.isEmpty(this.form.corderno) ||
        !this.isEmpty(this.form.transtype) || !this.isEmpty(this.form.supplierids) || !this.isEmpty(this.form.deptNos) || !this.isEmpty(this.form.ordtype)) {
          this.confirmCondition = this.form
          // bug10483 修改更新人直接从前端获取，B91717
          this.confirmCondition.operator = this.$store.getters.position.psnsmcId
          toPurchase(this.confirmCondition).then(res => {
            if (res.success === true) {
              this.$notify({
                title: '成功',
                message: '批量采购成功',
                type: 'success',
                duration: 2000
              })
              this.listLoading = false
              this.getList()
            } else {
              this.$message({
                dangerouslyUseHTMLString: true,
                showClose: true,
                message: res.message,
                type: 'error'
              })
              this.listLoading = false
            }
          }).catch(error => {
            this.listLoading = false
            console.info(error)
            this.$message.error(error.message)
          })
        } else {
          this.$message({
            showClose: true,
            message: '未添加筛选条件，此操作将直接发送采购，请添加筛选条件后重试',
            type: 'warning'
          })
        }
      }).catch(() => {
        this.listLoading = false
        this.$message({
          type: 'info',
          message: '取消直接发送采购'
        })
      })
    },
    // 根据查询条件，批量确认全部数据
    confirmAllData() {
      this.listLoading = true
      // bug10483 修改更新人直接从前端获取，B91717
      this.confirmCondition.operator = this.$store.getters.position.psnsmcId
      if (this.isEmpty(this.confirmCondition.operator)) {
        this.$message({
          showClose: true,
          message: '当前登录人信息为空，请重新登录后重试',
          type: 'error'
        })
        return
      }
      if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.purchasetype) || !this.isEmpty(this.form.corderno) ||
      !this.isEmpty(this.form.transtype) || !this.isEmpty(this.form.supplierids) || !this.isEmpty(this.form.deptNos) || !this.isEmpty(this.form.ordtype)) {
        this.confirmCondition = this.form
        confirmAll(this.confirmCondition).then(res => {
          if (res.success === true) {
            this.$notify({
              title: '成功',
              message: '批量处理成功',
              type: 'success',
              duration: 2000
            })
            this.listLoading = false
            this.getList()
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: res.message,
              type: 'error',
              duration: 0
            })
            this.listLoading = false
          }
        }).catch(error => {
          this.listLoading = false
          console.info(error)
          this.$message.error(error.message)
        })
      } else {
        this.$confirm('未添加筛选条件，此操作将会确认全部请购单, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          confirmAll(this.confirmCondition).then(res => {
            if (res.success === true) {
              this.$notify({
                title: '成功',
                message: '批量处理成功',
                type: 'success',
                duration: 2000
              })
              this.listLoading = false
              this.getList()
            } else {
              this.$message({
                dangerouslyUseHTMLString: true,
                showClose: true,
                message: res.message,
                type: 'error',
                duration: 0
              })
              this.listLoading = false
            }
          }).catch(error => {
            this.listLoading = false
            console.info(error)
            this.$message.error(error.message)
          })
        }).catch(() => {
          this.listLoading = false
          this.$message({
            type: 'info',
            message: '取消全部确认'
          })
        })
      }
    },
    // 修改数据
    updateData() {
      this.$refs['updateForm'].validate((valid) => {
        if (valid) {
          // bug10483 修改更新人直接从前端获取，B91717
          this.updateRequestData.operator = this.$store.getters.position.psnsmcId
          this.buttonloading = true
          updateSuppily(this.updateRequestData).then(res => {
            this.editFormVisible = false
            if (res.success === true) {
              this.editFormVisible = false
              this.$notify({
                title: '成功',
                message: '修改成功',
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
              this.editFormVisible = false
            }
            this.buttonloading = false
          }).catch(error => {
            this.buttonloading = false
            console.info(error)
            this.$message.error(error.message)
          })
        }
      })
    },
    updateDataBatch() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要批量修改的请购单'
        })
        return
      }
      this.selectList.forEach(element => {
        element.supplierid = this.updateRequestDataBatch.supplierid
        element.transtype = this.updateRequestDataBatch.transtype
        element.hopeexportdate = this.updateRequestDataBatch.hopeexportdate
        element.producttag = this.updateRequestDataBatch.producttag
        element.serverremark = this.updateRequestDataBatch.serverremark
      })
      const that = this
      this.$refs['updateFormBatch'].validate((valid) => {
        if (valid) {
          that.buttonloading = true
          updateRequestBatch(that.selectList).then(res => {
            if (res.success === true) {
              this.$notify({
                title: '成功',
                message: '批量处理成功',
                type: 'success',
                duration: 2000
              })
              this.editFormBatchVisible = false
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
            that.buttonloading = false
          }).catch(error => {
            that.buttonloading = false
            console.info(error)
            this.$message.error(error.message)
          })
        }
      })
    },
    edit(row) {
      this.editloading = true
      this.canSelectsupplierList = []
      this.canSelectTransList = []
      // bug13941,校验当前订单的specmark字段，specmark<>'0'时，查询可选择供应商时增加supplier表中specmark_flag=1的判断
      if (row.specmark === '0') {
        this.canSelectsupplierList = this.suppilyList
      } else {
        this.canSelectsupplierList = this.suppilyList.filter(dict => dict.specmarkFlag === true)
      }
      var transParam = {}
      transParam.supplierId = row.supplierid
      transParam.modelNo = row.modelno
      transParam.orderQty = row.quantity
      transParam.ordType = row.ordtype
      transParam.warehouse = row.purchasewarehouseid
      getTransIds(transParam).then(res => {
        this.editloading = false
        if (res.success && res.data.length > 0) {
          this.canSelectTransList = res.data
        } else {
          this.editloading = false
          this.DictData.transtype.forEach(dict => {
            this.canSelectTransList.push({ id: dict.value, name: dict.label })
          })
          return
        }
      }).catch(error => {
        this.editloading = false
        this.$message.error(error.message)
      })
      this.editFormVisible = true
      this.updateRequestData = Object.assign({}, row)
    },
    editBatch() {
      this.editloading = true
      this.canSelectsupplierList = []
      this.canSelectTransList = []
      // bug13941,校验当前订单的specmark字段，specmark<>'0'时，查询可选择供应商时增加supplier表中specmark_flag=1的判断
      if (this.selectList[0].specmark === '0') {
        this.canSelectsupplierList = this.suppilyList
      } else {
        this.canSelectsupplierList = this.suppilyList.filter(dict => dict.specmarkFlag === true)
      }
      var transParam = {}
      transParam.supplierId = this.selectList[0].supplierid
      transParam.modelNo = this.selectList[0].modelno
      transParam.orderQty = this.selectList[0].quantity
      transParam.ordType = this.selectList[0].ordtype
      transParam.warehouse = this.selectList[0].purchasewarehouseid
      getTransIds(transParam).then(res => {
        this.editloading = false
        if (res.success && res.data.length > 0) {
          this.canSelectTransList = res.data
        } else {
          this.editloading = false
          this.DictData.transtype.forEach(dict => {
            this.canSelectTransList.push({ id: dict.value, name: dict.label })
          })
          return
        }
      }).catch(error => {
        this.editloading = false
        this.$message.error(error.message)
      })
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要批量修改的请购单'
        })
        return
      }
      this.editFormBatchVisible = true
      this.updateRequestDataBatch = Object.assign({}, this.selectList[0])
    },
    // 详情跳转方法
    toDetail(value) {
      this.$router.push({
        path: '/purchase/requestPurChaseDeail',
        query: { orderno: value }
      })
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    // 展开更多选项
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    // 跳转至3未处理请购单
    toUntreated() {
      // 详情跳转方法
      this.$router.push({
        path: '/purchase/untreatedQuery'
      })
    },
    // 取消订单的弹窗
    cancelVisible(row) {
      this.tableDataRequest = []
      this.tableDataRequest.push(row)
      this.cancelDialog.show = true
      this.cancelRequestData = Object.assign({}, row)
    },
    // 取消订单，调用wm接口
    cancelData() {
      this.$confirm('此操作将删除该请购单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.cancelDialog.loading = true
        // 调用后台校验接口
        this.purchaseCheckCondition.orderno = this.cancelRequestData.orderno
        this.purchaseCheckCondition.itemno = this.cancelRequestData.itemno
        this.purchaseCheckCondition.splititemno = this.cancelRequestData.splititemno
        this.purchaseCheckCondition.requestpurchaselist = this.tableDataRequest
        purchaseDel(this.purchaseCheckCondition).then(result => {
          if (result.success === true) {
            this.cancelCondition.orderno = this.cancelRequestData.orderno
            this.cancelCondition.itemno = this.cancelRequestData.itemno
            this.cancelCondition.splititemno = this.cancelRequestData.splititemno
            this.cancelCondition.mergeflag = this.cancelRequestData.mergeflag
            this.cancelCondition.statecode = this.cancelRequestData.statecode
            this.cancelCondition.canceltype = '0'
            // bug10483 修改更新人直接从前端获取，B91717
            this.cancelCondition.operator = this.$store.getters.position.psnsmcId
            // bug12344 采购删单新增 删单原因，记录到删单表中
            var reason = this.cancelDialog.reason[1] === '其他' ? this.cancelDialog.otherReason : this.cancelDialog.reason[1]
            this.cancelCondition.reason = reason
            if (this.isEmpty(this.cancelCondition.operator)) {
              this.$message({
                showClose: true,
                message: '当前登录人信息为空，请重新登录后重试',
                type: 'error'
              })
              return
            }
            cancelDatas(this.cancelCondition).then(() => {
              this.$notify({
                title: '成功',
                message: '删除成功',
                type: 'success',
                duration: 2000
              })
              // bug21025 重复提交问题
              this.cancelDialog.loading = false
              this.cancelDialog.show = false
              this.getList()
            }).catch(error => {
              this.cancelDialog.loading = false
              console.info(error)
              this.$message.error(error.message)
            })
          } else {
            // bug21025 重复提交问题
            this.cancelDialog.loading = false
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: result.message,
              type: 'error',
              duration: 0
            })
          }
          // bug21025 重复提交问题
          // this.cancelDialog.loading = false
        }).catch(error => {
          this.cancelDialog.loading = false
          console.info(error)
          this.$message.error(error.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除该订单'
        })
        this.cancelDialog.loading = false
      })
    },
    // 取消选项弹出输入框
    cancelMenu() {
      this.cancelDialog.other = this.cancelDialog.reason[1] === '其他'
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
    transtypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.transtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    supplierAssignTypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.supplierAssignType.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    }
  }
}
</script>
<style scoped>
/* .app-container .filter-containers .divHeader{
  position: relative;
  text-align: left;
} */
.filter-container {
  padding-bottom: 10px;
}
.filter-container .filter-item {
    display: inline-block;
    vertical-align: middle;
}
.el-form-item {
   margin-bottom: 4px;
}
/* .el-table th{
  color: rgba(253, 253, 253, 0.938);
  background-color: rgb(117, 144, 168);
  padding: 6px;
  font-size: 14px;
}
.el-table td{
  padding: 2px;
} */
.el-button--primary {
  color: #337AB7;
  background-color: #fff;
  border:1px solid #337AB7;
}
.el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #337AB7;
  color: #fff;
}
</style>

