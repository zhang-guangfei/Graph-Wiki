<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <div>
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="采购单号">
              <el-input v-model="form.orderno" placeholder="请输入采购单号" style="width=80%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="型号">
              <el-input v-model="form.modelno" placeholder="请输入型号" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="营业所">
              <department style="width: 200px" @handleScopeChange="handleScopeChange"/>
            </el-form-item>
            <el-form-item label="采购状态" >
              <el-select v-model="form.statecode" :style="{width: '91%'}" placeholder=" 请选择采购单状态" clearable size="small">
                <el-option
                  v-for="item in DictData.status"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="订货日期">
              <el-date-picker
                v-model="purchaseDate"
                :picker-options="pickerOptions"
                :default-time="['00:00:00', '23:59:59']"
                type="daterange"
                align="right"
                size="small"
                unlink-panels
                style="width: 230px"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"/>
            </el-form-item>
            <el-form-item>
              <span class="operation-button">
                <el-button v-permission="['713346']" class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList()">查询</el-button>
                <el-button v-permission="['249917']" class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="openSonItemExport">导出</el-button>
                <el-tooltip effect="light" content="展开" placement="top">
                  <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
                </el-tooltip>
                <!-- <el-tooltip effect="light" content="重置" placement="top">
                  <el-button type="info" icon="el-icon-close" size="mini" circle @click="resetForm()"/>
                </el-tooltip> -->
              </span>
            </el-form-item>
          </el-form>
        </div>
        <div v-if="searchMoreForm" class="search">
          <el-form ref="form" :model="form" :inline="true">
            <el-form-item label="采购类别" >
              <el-select v-model="form.purchasetype" placeholder="请选择采购单类别" clearable size="small">
                <el-option
                  v-for="item in DictData.type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="客户">
              <el-input v-model="form.customerno" placeholder="请输入客户代码" clearable size="small" style="width=80%" @keyup.enter.native="getList" />
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
            <el-form-item label="供应商" prop="supplierid">
              <el-select v-model="form.supplierid" placeholder="请选择供应商" clearable size="small" >
                <el-option
                  v-for="item in suppilyList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
            <el-form-item label="客户单号">
              <el-input v-model="form.corderno" placeholder="请输入客户单号" style="width=100%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="收货仓库" >
              <el-select v-model="form.receivewarehouseid" placeholder="请选择收货仓库" clearable size="small">
                <el-option
                  v-for="item in warehouseList"
                  :key="item.warehouseCode"
                  :label="item.warehouseName"
                  :value="item.warehouseCode"/>
              </el-select>
            </el-form-item>
            <!--bug 14609 新增批次号筛选条件-->
            <el-form-item label="批次号">
              <el-input v-model="form.sendversion" placeholder="请输入批次号" style="width=80%" clearable size="small" @keyup.enter.native="getList" />
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <div style="margin-top:20px;">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :row-style="rowStyle"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
          :cell-style="{padding: '2px 3px'}"
          element-loading-text="拼命加载中"
          border
          fit
          height="660"
          highlight-current-row
          style="width:100%"
          @selection-change="selectChangeEvent"
          @select-all="selectAllEvent">
          <!-- <el-table-column
            type="selection"
            width="55"/> -->
          <el-table-column label="序号" align="center" fixed="left" min-width="70">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <!--bug12361 OPS采购查询 营业所字段数据错误-->
          <!-- <el-table-column :formatter="deptFormatter" prop="applyDeptNo" fixed="left" show-overflow-tooltip label="申请部门" min-width="90" align="center"/> -->
          <el-table-column :formatter="deptFormatter" prop="deptno" fixed="left" show-overflow-tooltip label="营业所" min-width="110" align="center"/>
          <el-table-column label="采购单号" align="center" fixed="left" min-width="150">
            <template slot-scope="scope">
              <el-tag size="mini">
                <!-- <span v-if="scope.row.statecode!=='9' && scope.row.statecode!=='7' && scope.row.statecode!=='A' "> -->
                <span v-if="scope.row.statecode<='5' || scope.row.statecode === 'B' ">
                  <a style="color: blue" @click="toDetail(scope.row)">
                    <span v-if="scope.row.splititemno==null">
                      {{ scope.row.orderno+"-"+scope.row.itemno }}
                    </span>
                    <span v-if="scope.row.splititemno!==null">
                      {{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}
                    </span>
                  </a>
                </span>
                <span v-else>
                  <span v-if="scope.row.splititemno==null">
                    {{ scope.row.orderno+"-"+scope.row.itemno }}
                  </span>
                  <span v-if="scope.row.splititemno!==null">
                    {{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}
                  </span>
                </span>
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column :formatter="ordtypeFormatter" show-overflow-tooltip prop="ordtype" label="订单类别" min-width="140" align="center"/>
          <el-table-column :formatter="purchaseTypeFormatter" prop="purchasetype" label="采购类别" min-width="110" align="center"/>
          <el-table-column :formatter="statecodeFormatter" prop="statecode" label="状态" align="center" min-width="120"/>
          <el-table-column show-overflow-tooltip label="型号" min-width="150" align="left">
            <template slot-scope="scope">
              <span>{{ scope.row.modelno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="采购数量" align="left" min-width="100">
            <template slot-scope="scope">
              <span>{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="客户" align="center" min-width="80">
            <template slot-scope="scope">
              <span>{{ scope.row.customerno }}</span>
            </template>
          </el-table-column>
          <!--
          <el-table-column show-overflow-tooltip label="用户" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.userno }}</span>
            </template>
          </el-table-column>
        -->
          <el-table-column show-overflow-tooltip label="最终用户" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.endUser }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="采购日期" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.purchasedate | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="suppilyFormatter" prop="supplierid" show-overflow-tooltip label="供应商" min-width="120" align="center"/>
          <!-- bug9571 采购查询增加显示国别代码 -->
          <el-table-column show-overflow-tooltip label="国别代码" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.smccode }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="制造出荷日" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="期望货期" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopedeliverydate | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" align="center" min-width="100"/>
          <el-table-column :formatter="warehouseFormatter" prop="receivewarehouseid" show-overflow-tooltip label="收货仓库" align="center" min-width="100"/>
          <el-table-column show-overflow-tooltip label="供应商订单号" align="center" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.replyorderno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="到货数量" align="center" min-width="100">
            <template slot-scope="scope">
              <span>{{ scope.row.qtyreceive }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="返信纳期" align="center" min-width="100">
            <template slot-scope="scope">
              <el-popover
                placement="bottom"
                trigger="click"
                visible-arrow="false"
                @show="getDlvDate(scope.row)">
                <div slot="reference">
                  <!--20240920,前端增加判断，当srcDeliveryTime有值时，显示srcDeliveryTime，否则显示dlvmoddateStr-->
                  <span v-if="scope.row.srcDeliveryTime!==null">
                    <span>{{ scope.row.srcDeliveryTime }}</span>
                  </span>
                  <span v-else>
                    <span>{{ scope.row.dlvmoddateStr }}</span>
                  </span>
                  <!--20240723,先注释掉，等交付上线后再改回去-->
                  <!-- <span>{{ scope.row.dlvmoddateStr }}</span> -->
                </div>
                <vxe-table
                  v-if="dlvDate && dlvDate.length > 0"
                  :data="dlvDate"
                  min-height="50px"
                  border
                  fit
                  size="mini"
                >
                  <vxe-table-column width="100" title="变更交货期">
                    <template slot-scope="scope">
                      <span>{{ scope.row.dlvModDateStr }}</span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column width="150" title="变更时间">
                    <template slot-scope="scope">
                      <span>{{ scope.row.updateTime | formatDate }}</span>
                    </template>
                  </vxe-table-column>
                  <vxe-table-column width="200" title="变更原因" show-overflow="tooltip">
                    <template slot-scope="scope">
                      <span>{{ scope.row.reasonremark }}</span>
                    </template>
                  </vxe-table-column>
                </vxe-table>
              </el-popover>
            </template>
          </el-table-column>
          <!-- <el-table-column show-overflow-tooltip label="供应商处理状态" align="center" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.qtyreceive }}</span>
            </template>
          </el-table-column> -->
          <el-table-column show-overflow-tooltip label="完成日期" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.finishdate | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="特殊标识" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.greencode }}</span>
            </template>
          </el-table-column>
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
          <el-table-column show-overflow-tooltip label="删单原因" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.deletereason }}</span>
            </template>
          </el-table-column>
          <!--相关备注修改名称-->
          <el-table-column show-overflow-tooltip label="履历信息" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.information }}</span>
            </template>
          </el-table-column>
          <!--bug12494 采购查询界面增加vipcode字段及serverRemark字段的显示-->
          <el-table-column show-overflow-tooltip label="业务备注" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.serverremark }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="vipcode" min-width="80" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.vipcode }}</span>
            </template>
          </el-table-column>
          <!--bug11997 采购自动发单的前端界面修改 -->
          <el-table-column show-overflow-tooltip label="批次号" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.sendversion }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="INQB号" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.inqno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="shikomi" align="center" min-width="100" prop="shikomiAnswerNo" />
          <el-table-column show-overflow-tooltip label="准备订单号" align="center" min-width="120" prop="prepareorderno" />
          <el-table-column :formatter="supplierAssignTypeFormatter" prop="supplierAssignType" show-overflow-tooltip label="指定供应商" align="center" min-width="130"/>
          <el-table-column show-overflow-tooltip label="发单文件" min-width="80" fixed="right" align="center">
            <template slot-scope="scope">
              <el-tooltip effect="light" content="点击下载发单文件" placement="right-start">
                <el-button v-if="scope.row.filepath !== null && scope.row.filepath !== ''" size="mini" type="text" icon="el-icon-download" @click="handledownload(scope.row)"/>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" min-width="150">
            <template slot-scope="scope">
              <el-tooltip effect="light" content="转定" placement="top">
                <span>
                  <el-button v-permission="['249917']" v-if="['1','2'].includes(scope.row.statecode)" :loading="buttonloading" circle icon="el-icon-help" type="primary" size="small" @click="transform(scope.row)"/>
                </span>
              </el-tooltip>
              <el-tooltip effect="light" content="接单" placement="top">
                <span>
                  <el-button v-permission="['249917']" v-if="scope.row.statecode === '1'" type="primary" size="small" circle icon="el-icon-sold-out" @click="apiDelivery(scope.row)"/>
                </span>
              </el-tooltip>
              <el-tooltip effect="light" content="完纳" placement="top">
                <span>
                  <el-button v-permission="['255329']" v-if="['2','3'].includes(scope.row.statecode)" type="primary" size="small" circle icon="el-icon-finished" @click="completePopup(scope.row)"/>
                </span>
              </el-tooltip>
              <el-tooltip effect="light" content="订单取消" placement="top">
                <span>
                  <el-button v-permission="['249917']" v-if="scope.row.statecode === '2'" type="primary" size="small" circle icon="el-icon-delete-solid" @click="cancelVisible(scope.row)"/>
                </span>
              </el-tooltip>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <el-dialog :close-on-click-modal="false" :visible.sync="deliveryVisible" title="变更指定交货期" align="center" width="40%">
      <el-card>
        <el-form ref="deliveryForm" :rules="ruleDelivery" :model="updateRequestDataDelivery" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="指定交货期" prop="hopedeliverydate">
            <el-date-picker
              v-model="updateRequestDataDelivery.hopedeliverydate"
              style="margin-left:20px;margin-bottom:5px;width:300px"
              size="middle"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="deliveryVisible = false">取消</el-button>
        <el-button type="primary" @click = "updateDelivery()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog :close-on-click-modal="false" :visible.sync="editFormVisible" title="采购单转订" align="center" width="40%">
      <el-card>
        <el-form ref="updateForm" :rules="rules" :model="updateRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <!--bug 13515 采购转订单号显示完整的pono单号 -->
          <el-form-item label="采购单号" prop="pono">
            <el-input v-model="updateRequestData.pono" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="供应商" prop="supplierid">
            <!-- @change="transSelect(updateRequestData)"-->
            <el-select v-model="updateRequestData.supplierid" placeholder="请选择供应商" style="margin-left:20px;margin-bottom:5px;width:300px" clearable @change="transSelect(updateRequestData)">
              <el-option
                v-for="item in canSelectsupplierList"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
          </el-form-item>
          <el-form-item label="运输方式" prop="transtype">
            <el-select v-model="updateRequestData.transtype" placeholder="请选择运输方式" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @input="change($event)">
              <!-- <el-option
                v-for="item in DictData.transtype"
                :key="item.value"
                :label="item.label"
                :value="item.value"/> -->
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
          <el-form-item label="转订原因" prop="information">
            <el-input v-model="updateRequestData.information" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <!--bug11841,请购处理增加加*订货功能-->
          <el-form-item label="特殊标识" prop="greencode">
            <el-select v-model="updateRequestData.greencode" placeholder="请选择特殊标识" clearable style="margin-left:20px;margin-bottom:5px;width:300px">
              <el-option
                v-for="item in DictData.producttag"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="editFormVisible = false">取消</el-button>
        <el-button :loading="transloading" type="primary" @click="transData()">确认</el-button>
      </div>
    </el-dialog>
    <!--手工调用后端api接口-->
    <el-dialog :close-on-click-modal="false" :visible.sync="apiVisible" title="调用接单接口" align="center" width="40%">
      <el-card>
        <el-form ref="apiForm" :model="apiRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="pono:" prop="pono">
            <el-input v-model="apiRequestData.pono" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="lineitem" prop="lineitem">
            <el-input v-model="apiRequestData.lineitem" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="供应商订单号:" prop="replyorderno">
            <el-input v-model="apiRequestData.replyorderno" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
        <el-button type="primary" @click="apiVisible = false">取消</el-button>
        <el-button :loading="orderloading" type="primary" @click="apiSend()">确认</el-button>
      </div>
    </el-dialog>
    <!--强制完纳的弹窗-->
    <el-dialog :close-on-click-modal="false" :visible.sync="completeVisible" title="采购单完纳" align="center" width="40%">
      <el-card>
        <el-form ref="completeForm" :rules="rulescomplete" :model="completeRequestData" label-position="right" label-width="120px" style="width: 400px;">
          <el-form-item label="采购单号:" prop="pFullNo">
            <el-input v-model="completeRequestData.pFullNo" :disabled="true" style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="型号:" prop="modelNo">
            <el-input v-model="completeRequestData.modelNo" :disabled="true" style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="数量:" prop="pQty">
            <el-input v-model="completeRequestData.pQty" :disabled="true" style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="已发数量:" prop="arrQty">
            <el-input v-model="completeRequestData.arrQty" :disabled="true" style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="完纳数量:" prop="finishPoQty">
            <el-input v-model="completeRequestData.finishPoQty" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
          </el-form-item>
          <el-form-item label="完纳原因:">
            <el-cascader
              v-model="completeDialog.reason"
              :options="DictData.calcelReason"
              style="margin-left:20px;margin-bottom:5px;width:300px;margin-top:15px"
              @change="completeMenu"
            />
          </el-form-item>
          <el-form-item v-if="completeDialog.other" label="输入完纳原因:">
            <el-input
              v-model="completeDialog.otherReason"
              :autosize="{minRows: 2 }"
              type="textarea"
              style="margin-left:20px;margin-bottom:5px;width:300px"
            />
          </el-form-item>
        </el-form>
      </el-card>
      <div slot="footer" style="text-align:center">
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
        <!-- bug 12446，加入 按钮loading限制，同时加入清空 请求参数-->
        <el-button type="primary" size="small" @click="cancelClose">取消</el-button>
        <el-button :loading="cancelDialog.loading" type="primary" size="small" @click="cancelData()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      title="提示"
      width="30%"
      height="200px">
      <span><h2>{{ msg }}</h2></span>
    </el-dialog>
    <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getList()" />
    <exportExcel ref="exportExcelVue"/>
  </div>
</template>
<script>
import { findList, transOrder, updateDeliveryData, apiRepo, getDlvdate, downloadFile, purchaseWn, getFinishPo, deleteReasonOptions, purchaseDel, findRequestByOrder, getTransIds } from '@/api/purchaseOrder'
import { findDeptDict } from '@/api/warehouseManage'
// import moment from 'moment'
import { getSuppily, getWarehouse, getTrans } from '@/api/intercept'
import Pagination from '@/components/Pagination'
import { getDataCodesTree } from '@/api/common/dict'
import Department from '@/components/Department'
import exportExcel from '@/components/ExportExcel/index'
import { cancelDatas } from '@/api/requestPurchase'
import { shortcuts } from '@/utils/dataFormatPurchase'
export default {
  name: 'PurchaseQuery',
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
        transtype: [],
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
        calcelReason: [],
        supplierAssignType: []
      },
      pono: '',
      lineitems: 1,
      selectList: [],
      warehouseList: [],
      cpnysData: [],
      tableData: [],
      editFormVisible: false,
      deliveryVisible: false,
      apiVisible: false,
      listLoading: false,
      updateRequestData: {},
      updateRequestDataDelivery: {},
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
        supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        transtype: [{ required: true, message: '请选择运输方式', trigger: 'blur' }],
        serverremark: [{ required: true, message: '请选择转订原因', trigger: 'blur' }],
        hopedeliverydate: [{ required: true, message: '请选择出库日', trigger: 'blur' }]
      },
      ruleDelivery: {
        hopedeliverydate: [{ required: true, message: '请选择交货期', trigger: 'blur' }]
      },
      // 强制完纳弹窗校验
      rulescomplete: {
        finishPoQty: [{ required: true, message: '请输入完纳数量', trigger: 'blur' }]
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
      // 接单按钮事件
      orderloading: false,
      pickerOptions: shortcuts,
      // 转定弹窗，显示可选择的供应商列表
      canSelectTrans: []
    }
  },
  mounted() {
    this.DictData.calcelReason = deleteReasonOptions()
  },
  created() {
    this.initData()
    // bug 12763 采购查询页面初始进入时，不自动加载数据
    // this.getList()
    this.getSuppilyTrans()
    this.getWarehouseList()
    // this.findDeptNos()
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
    getDlvDate(purchase) {
      this.dlvDate = []
      getDlvdate(purchase).then(res => {
        if (res.success) {
          this.dlvDate = res.data
        }
      })
    },
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 处理状态
    initData() {
      getDataCodesTree('2031').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.type.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
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
            this.DictData.transtype.push({ value: dict.id, label: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      getDataCodesTree('2033').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.status.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      getDataCodesTree('1002').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.ordtype.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
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
    // 列表复选框改变事件
    selectChangeEvent(records) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element)
      })
    },
    // 列表全选事件
    selectAllEvent(records) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element)
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
      getTrans().then(res => {
        this.transList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
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
    // 重置表单
    resetForm() {
      this.form = {
        orderno: '',
        modelno: '',
        purchasetype: '',
        statecode: '',
        customerno: '',
        deptno: ''
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
      if (this.isEmpty(this.form.orderno) && this.isEmpty(this.form.purchaseDateStart) && this.isEmpty(this.form.statecode)) {
        this.$message({
          showClose: true,
          message: '查询条件不能为空，请补充单号或采购时间',
          type: 'error'
        })
        return
      }
      this.listLoading = true
      if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.purchasetype) ||
      !this.isEmpty(this.form.statecode) || !this.isEmpty(this.form.customerno) || !this.isEmpty(this.form.deptNos) ||
      !this.isEmpty(this.form.supplierid) || !this.isEmpty(this.form.purchaseDateStart) || !this.isEmpty(this.form.corderno) || !this.isEmpty(this.form.ordtype)) {
        this.queryCondition.condition = this.form
      }
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = this.page.pageSize
      findList(this.queryCondition).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    openSonItemExport() {
      const tableColumn = [
        { field: 'orderno', title: '订单号' },
        { field: 'itemno', title: '项号' },
        { field: 'splititemno', title: '拆分项号' },
        { field: 'ordtype', title: '订单类别' },
        { field: 'purchasetype', title: '采购类别' },
        { field: 'statecode', title: '状态' },
        { field: 'quantity', title: '数量' },
        { field: 'modelno', title: '型号' },
        { field: 'customerno', title: '客户代码' },
        // { field: 'userno', title: '用户代码' },
        { field: 'endUser', title: '最终用户' },
        { field: 'supplierid', title: '供应商' },
        { field: 'purchasedate', title: '采购日期' },
        { field: 'hopeexportdate', title: '制造出荷日' },
        { field: 'hopedeliverydate', title: '期望货期' },
        { field: 'receivewarehouseid', title: '收货仓库' },
        { field: 'transtype', title: '运输方式' },
        { field: 'qtyreceive', title: '到货数量' },
        { field: 'replyorderno', title: '供应商接单号' },
        { field: 'finishdate', title: '完成日期' },
        { field: 'deptno', title: '营业所' },
        { field: 'applyDeptNo', title: '申请部门' },
        { field: 'information', title: '履历信息' },
        { field: 'sendversion', title: '批次号' },
        { field: 'inqno', title: 'INQB号' },
        { field: 'shikomiAnswerNo', title: 'shikomi' },
        { field: 'prepareorderno', title: '准备订单号' },
        { field: 'supplierAssignType', title: '指定供应商' }
      ]
      this.setDateCondition()
      if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.purchasetype) ||
      !this.isEmpty(this.form.statecode) || !this.isEmpty(this.form.customerno) || !this.isEmpty(this.form.deptNos) ||
      !this.isEmpty(this.form.supplierid) || !this.isEmpty(this.form.purchaseDateStart) || !this.isEmpty(this.form.corderno) || !this.isEmpty(this.form.ordtype) || !this.isEmpty(this.form.sendversion)) {
        this.queryCondition.condition = this.form
      }
      // 同时增加字典的转换
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = 3000
      findList(this.queryCondition).then(res => {
        if (res.success) {
          // bug19540 请购处理增加指定供应商类别
          res.data.list.forEach(item => {
            item.supplierAssignType = this.exportFormmater('supplierAssignType', item.supplierAssignType)
          })
          this.$refs.exportExcelVue.initExportExcel(res.data.list, tableColumn)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // 导出翻译成中文
    exportFormmater(type, val) {
      const item = this.DictData[type].find(dict => dict.value === val)
      return item ? item.label : val
    },
    // 详情跳转方法
    toDetail(row) {
      this.$router.push({
        path: '/purchase/purchaseDeail',
        query: { id: row.id, orderno: row.orderno, itemno: row.itemno, splititemno: row.splititemno, statecode: row.statecode, modelno: row.modelno, quantity: row.quantity }
      })
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    transform(row) {
      this.buttonloading = true
      this.canSelectTransList = []
      this.canSelectsupplierList = []
      // bug13941,校验当前订单的specmark字段，specmark<>'0'时，查询可选择供应商时增加supplier表中specmark_flag=1的判断
      if (row.specmark === '0') {
        this.canSelectsupplierList = this.suppilyList
      } else {
        this.canSelectsupplierList = this.suppilyList.filter(dict => dict.specmarkFlag === true)
      }
      console.log(row)
      var transParam = {}
      transParam.supplierId = row.supplierid
      transParam.modelNo = row.modelno
      transParam.orderQty = row.quantity
      transParam.ordType = row.ordtype
      transParam.warehouse = row.receivewarehouseid
      getTransIds(transParam).then(res => {
        this.buttonloading = false
        if (res.success && res.data.length > 0) {
          this.canSelectTransList = res.data
        } else {
          this.buttonloading = false
          this.DictData.transtype.forEach(dict => {
            this.canSelectTransList.push({ id: dict.value, name: dict.label })
          })
          return
        }
      }).catch(error => {
        this.buttonloading = false
        this.$message.error(error.message)
      })
      this.editFormVisible = true
      this.updateRequestData = Object.assign({}, row)
      // bug 13515 采购转订单号显示完整的pono单号
      if (row.splititemno == null) {
        this.updateRequestData.pono = row.orderno + '-' + row.itemno
      } else {
        this.updateRequestData.pono = row.orderno + '-' + row.itemno + '-' + row.splititemno
      }
    },
    // 采购单转订
    transData() {
      this.$refs['updateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将转订该采购单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.transloading = true
            // bug10483 修改更新人直接从前端获取，B91717
            this.updateRequestData.operator = this.$store.getters.position.psnsmcId
            if (this.isEmpty(this.updateRequestData.operator)) {
              this.$message({
                showClose: true,
                message: '当前登录人信息为空，请重新登录后重试',
                type: 'error'
              })
              this.transloading = false
              return
            }
            transOrder(this.updateRequestData).then(res => {
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
            }).catch(error => {
              this.transloading = false
              console.info(error)
              this.$message.error(error.message)
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消该订单转订'
            })
          })
        }
      })
    },
    // 订单类型字典格式化
    purchaseTypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.type.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    statecodeFormatter(row, column, cellValue, index) {
      const item = this.DictData.status.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // 订单类别转换
    ordtypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.ordtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // 运输方式转换
    transtypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.transtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // bug19540 指定供应商类别转换
    supplierAssignTypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.supplierAssignType.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    changeDelivery(row) {
      this.deliveryVisible = true
      this.updateRequestDataDelivery = Object.assign({}, row)
    },
    updateDelivery() {
      this.$refs['deliveryForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将更改交货期, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            // bug10483 修改更新人直接从前端获取，B91717
            this.updateRequestDataDelivery.operator = this.$store.getters.position.psnsmcId
            if (this.isEmpty(this.updateRequestDataDelivery.operator)) {
              this.$message({
                showClose: true,
                message: '当前登录人信息为空，请重新登录后重试',
                type: 'error'
              })
              return
            }
            updateDeliveryData(this.updateRequestDataDelivery).then(() => {
              this.deliveryVisible = false
              this.$notify({
                title: '成功',
                message: '变更交货期成功',
                type: 'success',
                duration: 2000
              })
              this.getList()
            }).catch(error => {
              this.listLoading = false
              console.log(error)
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消该操作'
            })
          })
        }
      })
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
      this.FinishPoDto.pOrderNo = row.orderno
      this.FinishPoDto.pOrderItem = row.itemno
      this.FinishPoDto.pSplitNo = row.splititemno
      getFinishPo(this.FinishPoDto).then(res => {
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
      // this.completeRequestData = Object.assign({}, row)
      // if (this.isEmpty(row.splititemno)) {
      //   this.completeRequestData.pono = row.orderno + '-' + row.itemno
      // } else {
      //   this.completeRequestData.pono = row.orderno + '-' + row.itemno + '-' + row.splititemno
      // }
    },

    /**
     * 强制完纳
     */
    completeData(row) {
      this.$refs['completeForm'].validate((valid) => {
        if (valid) {
          this.completeDialog.loading = true
          // 1.先校验完纳数量之间的关系
          const finishPoQty = (this.completeRequestData.finishPoQty) // 完纳数量
          const pQty = (this.completeRequestData.pQty) // 采购数量&订单数量
          const arrQty = this.completeRequestData.arrQty === null ? 0 : this.completeRequestData.arrQty// 已发数量
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
            var reason = this.completeDialog.reason[1] === '其他' ? this.completeDialog.otherReason : this.completeDialog.reason[1]
            this.completeRequestData.finishMsg = reason
            this.completeRequestData.operator = this.$store.getters.position.psnsmcId
            // 2.执行po完纳
            purchaseWn(this.completeRequestData).then(res => {
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
            }).catch(error => {
              console.info(error)
              this.$message.error(error.message)
              this.completeClose()
            })
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: '完纳数量必须大于等于已发数量，且完纳数量必须小于订单数量',
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
    // 调用请购api接口
    apiDelivery(row) {
      this.apiVisible = true
      this.apiRequestData = Object.assign({}, row)
      if (row.splititemno == null) {
        this.apiRequestData.pono = row.orderno + '-' + row.itemno
      } else {
        this.apiRequestData.pono = row.orderno + '-' + row.itemno + '-' + row.splititemno
      }
      this.apiRequestData.lineitem = 1
    },
    apiSend() {
      this.$refs['apiForm'].validate((valid) => {
        if (valid) {
          this.orderloading = true
          this.apiRequestData.operator = this.$store.getters.position.psnsmcId
          if (this.isEmpty(this.apiRequestData.operator)) {
            this.$message({
              showClose: true,
              message: '当前登录人信息为空，请重新登录后重试',
              type: 'error'
            })
            this.orderloading = false
            return
          } else {
            apiRepo(this.apiRequestData).then(res => {
              if (res.success === true) {
                this.$notify({
                  title: '成功',
                  message: '调用成功',
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
              this.orderloading = false
              this.apiVisible = false
            }).catch(error => {
              this.orderloading = false
              this.apiVisible = false
              console.log(error)
            })
          }
        }
      })
    },
    transSelect() {
      this.updateRequestData.transtype = ''
      this.canSelectTransList = []
      console.log(this.updateRequestData)
      var transParam = {}
      transParam.supplierId = this.updateRequestData.supplierid
      transParam.modelNo = this.updateRequestData.modelno
      transParam.orderQty = this.updateRequestData.quantity
      transParam.ordType = this.updateRequestData.ordtype
      transParam.warehouse = this.updateRequestData.receivewarehouseid
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
      if (this.transList.length === 0) {
        this.getTrans()
      }
    },
    // bug11997 下载发单文件
    handledownload(row) {
      this.dialogVisible = true
      this.msg = '正在下载发单文件，请耐心等待..'
      const filepath = row.filepath
      downloadFile(filepath).then(res => {
        if (res.size === 0) {
          this.$message.warning('暂无发单文件')
          this.dialogVisible = false
          return
        }
        // 获取文件名
        const fileName = filepath.substring(filepath.lastIndexOf('/') + 1, filepath.length)
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.dialogVisible = false
      }).catch(error => {
        this.dialogVisible = true
        this.msg = '发单文件下载失败,失败原因:' + error
      })
    },
    // 取消选项弹出输入框
    cancelMenu() {
      this.cancelDialog.other = this.cancelDialog.reason[1] === '其他'
    },
    completeMenu() {
      this.completeDialog.other = this.completeDialog.reason[1] === '其他'
    },
    cancelVisible(row) {
      findRequestByOrder(row.id).then(res => {
        this.tableDataRequest = res.data
      }).catch(error => {
        console.log(error)
      })
      this.cancelDialog.show = true
      this.cancelRequestData = Object.assign({}, row)
    },
    // 取消订单，调用wm接口
    cancelData() {
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
          this.cancelCondition.canceltype = '1'
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
            this.cancelClose()
            return
          }
          cancelDatas(this.cancelCondition).then(res => {
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
          }).catch(error => {
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
      }).catch(error => {
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
