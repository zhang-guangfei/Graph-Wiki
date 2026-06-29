<template>
  <div class="app-container">
    <el-tabs v-model="activeTabName" type="card">
      <el-tab-pane label="主表数据" name="pane1">
        <!-- <i class="el-icon-coin" style="color: #FFA500;" />
        <span style="font-weight: 800; font-size: 13px;">&nbsp;&nbsp;主表数据</span>-->
        <div>
          <el-row>
            <el-form ref="queryForm" :inline="true" :model="queryForm" size="mini">
              <el-form-item>
                <el-input
                  v-model="queryForm.invoiceNo"
                  style="width: 120px"
                  clearable
                  placeholder="发票号"
                />
              </el-form-item>
              <el-form-item>
                <el-select
                  v-model="queryForm.status"
                  placeholder="状态"
                  style="width: 120px"
                  clearable
                  size="mini"
                  @change="searchData"
                >
                  <el-option
                    v-for="item in statusCodes"
                    :key="item.code"
                    :value="item.code"
                    :label="item.codeName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select
                  v-model="queryForm.supplierCode"
                  placeholder="供应商"
                  clearable
                  filterable
                  style="width: 160px"
                  @change="searchData"
                  @blur="numeblur()"
                >
                  <el-option
                    v-for="item in supplierCodeOptions"
                    :key="item.id"
                    :label="item.id+'-'+item.name"
                    :value="item.id"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <!-- <span>结算日期</span> -->
                <el-select
                  v-model="queryForm.qryDateType"
                  placeholder="请选择"
                  style="width:120px"
                  size="mini"
                >
                  <el-option
                    v-for="item in dateTypeOptions"
                    :key="item.code"
                    :value="item.code"
                    :label="item.codeName"
                  />
                </el-select>
                <el-date-picker
                  v-model="queryForm.fromDate"
                  type="date"
                  size="mini"
                  placeholder="开始日期"
                  value-format="yyyy-MM-dd"
                  style="width: 130px"
                />
                <el-date-picker
                  v-model="queryForm.toDate"
                  type="date"
                  size="mini"
                  placeholder="结束日期"
                  value-format="yyyy-MM-dd"
                  style="width: 130px"
                />
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['579106']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="searchData">查询
                </el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" style="margin-bottom:3px" @click="searchMoreData">···</el-button>
              </el-form-item>
              <el-form-item style="float:right">
                <el-date-picker
                  v-model="queryForm.costDate"
                  type="date"
                  size="mini"
                  placeholder="结算日期"
                  value-format="yyyy-MM-dd"
                  style="width: 130px"
                />
                <el-button v-permission="['280159']" type="primary" size="mini" @click="ExportDataToCost">成本结算
                </el-button>
              </el-form-item>
            </el-form>
            <el-form v-if="query" ref="queryForm" :inline="true" :model="queryForm" size="mini">
              <el-form-item label-width="100px" size="mini">
                <el-select
                  v-model="queryForm.currencyCode"
                  placeholder="币种"
                  style="width: 100px"
                  clearable
                  size="mini"
                  @change="searchData"
                >
                  <el-option
                    v-for="item in currencyCodeCodes"
                    :key="item.code"
                    :value="item.code"
                    :label="item.codeName"
                  />
                </el-select>
                <!-- <el-input v-model="updForm.currencyCode" style="width: 100px" autocomplete="off"/> -->
              </el-form-item>
              <el-form-item label-width="100px" size="mini">
                <el-select
                  v-model="queryForm.invoiceType"
                  placeholder="发票类型"
                  style="width: 100px"
                  clearable
                  size="mini"
                  @change="searchData"
                >
                  <el-option
                    v-for="item in invoiceTypedesc"
                    :key="item.code"
                    :value="item.code"
                    :label="item.codeName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['280159']"
                  v-loading="exportLoading"
                  type="primary"
                  icon="el-icon-upload"
                  size="mini"
                  @click="exportData">导出
                </el-button>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['280159']"
                  v-loading="exportOtherLoading"
                  type="primary"
                  icon="el-icon-upload"
                  size="mini"
                  @click="exportOtherInvoiceData"
                >导出月次报表
                </el-button>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['280159']"
                  type="primary"
                  icon="el-icon-refresh-right"
                  size="mini"
                  @click="redoCostInvoice">重新结转
                </el-button>
              </el-form-item>
            </el-form>
          </el-row>
        </div>
        <el-dialog
          :visible.sync="dialogupdVisible"
          :close-on-click-modal="false"
          title="更新"
          width="760px"
        >
          <el-form ref="addForm" :inline="true" :model="updForm">
            <el-form-item label-width="100px" label="发票号" size="mini">
              <el-input
                v-model="updForm.invoiceNo"
                style="width: 110px"
                disabled
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item label-width="100px" label="币种" size="mini">
              <el-select
                v-model="updForm.currencyCode"
                placeholder="请选择"
                style="width: 110px"
                clearable
                size="mini"
              >
                <el-option
                  v-for="item in currencyCodeCodes"
                  :key="item.code"
                  :value="item.code"
                  :label="item.codeName"
                />
              </el-select>
              <!-- <el-input v-model="updForm.currencyCode" style="width: 100px" autocomplete="off"/> -->
            </el-form-item>
            <el-form-item label-width="100px" label="汇率" size="mini">
              <el-input v-model="updForm.exchangeRate" style="width: 80px" @change="caculAllFee"/>
              <el-button size="mini" type="primary" @click="getExchangeRate">获取</el-button>
            </el-form-item>
            <el-form-item label-width="100px" label="原发票金额" size="mini">
              <el-input v-model="updForm.amount" style="width: 110px" @change="caculAllFee"/>
            </el-form-item>
            <!-- <el-form-item label-width="100px" label="调整金额" size="mini">
          <el-input v-model="updForm.amountadjust" style="width: 100px" @change="caculAllFee"/>
            </el-form-item>-->
            <el-form-item label-width="100px" label="发票金额RMB" size="mini">
              <el-input
                v-model="updForm.amountRmb"
                :readonly="true"
                disabled
                style="width: 110px"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item label-width="100px" label="关税" size="mini">
              <el-input v-model="updForm.customsFee" style="width: 110px" disabled @change="caculAllFee"/>
            </el-form-item>
            <el-form-item label-width="100px" label="运费" size="mini">
              <el-input v-model="updForm.transFee" style="width: 110px" disabled @change="caculAllFee"/>
            </el-form-item>
            <el-form-item label-width="100px" label="消费税" size="mini">
              <el-input v-model="updForm.excisetax" style="width: 110px" disabled @change="caculAllFee"/>
            </el-form-item>
            <el-form-item label-width="100px" label="其他费用" size="mini">
              <el-input v-model="updForm.otherFee" style="width: 110px" disabled @change="caculAllFee"/>
            </el-form-item>
            <el-form-item label-width="100px" label="总金额" size="mini">
              <el-input
                v-model="updForm.allFee"
                :readonly="true"
                style="width: 110px"
                autocomplete="off"
              />
            </el-form-item>
            <el-form-item label-width="100px" label="物流签收日期" size="mini">
              <el-date-picker
                v-model="updForm.receiveTime"
                type="date"
                size="mini"
                placeholder="物流签收日期"
                value-format="yyyy-MM-dd"
                style="width: 130px"
              />
            </el-form-item>
            <el-form-item label-width="80px" label="发票日期" size="mini">
              <el-date-picker
                v-model="updForm.invoiceDate"
                type="date"
                size="mini"
                placeholder="发票日期"
                value-format="yyyy-MM-dd"
                style="width: 130px"
              />
            </el-form-item>
          </el-form>
          <!-- <el-checkbox v-model="updForm.isFinishCost" size="mini">是否完成结算</el-checkbox> -->
          <el-switch
            v-model="updForm.isFinishCost"
            active-color="#13ce66"
            active-text="是否完成结算"
            inactive-color="#ff4949"
          />
          <el-date-picker
            v-model="updForm.costTime"
            type="date"
            size="mini"
            placeholder="结算日期"
            value-format="yyyy-MM-dd"
            style="width: 130px"
          />
          <div style="float:right">
            <el-button size="mini" @click="dialogupdVisible = false">取 消</el-button>
            <el-button size="mini" type="primary" @click="saveProperty">更新</el-button>
          </div>
        </el-dialog>
        <div>
          <div style="font-size:13px;float:right">合计：{{ amountTotal | numberToCurrency }}</div>
          <el-table
            ref="multipleTable"
            :data="listdata"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '2px','font-size': '13px'}"
            :row-style="{height:'33px'}"
            highlight-current-row
            border
            stripe
            size="mini"
            style="width: 100%"
            height="70vh"
            @selection-change="handleSelection"
          >
            <!-- 表头字段 -->
            <el-table-column fixed type="selection" width="40"/>
            <!-- <el-table-column fixed prop="id" label="ID" /> -->
            <el-table-column fixed prop="invoiceId" label="发票ID" width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.invoiceId }}</span>
                <el-button
                  type="text"
                  size="mini"
                  title="查询明细"
                  icon="el-icon-view"
                  @click="selShowDetail(scope.$index, scope.row)"/>
              </template>
            </el-table-column>
            <el-table-column fixed prop="supplierCode" label="供应商" width="60" show-overflow-tooltip/>
            <el-table-column fixed prop="invoiceNo" label="发票号" width="110" show-overflow-tooltip/>
            <el-table-column prop="amount" label="原发票金额" width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.amount | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="amountRmb" label="原发票金额rmb" width="120" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.amountRmb | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="amounttotal" label="总金额" width="100" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.amounttotal | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="customsFee" label="关税" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.customsFee | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="transFee" label="运保费" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.transFee | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="excisetax" label="消费税" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.excisetax | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="otherFee" label="其他费用" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.otherFee | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="vatFee" label="增值税" show-overflow-tooltip>
              <template slot-scope="scope">
                {{ scope.row.vatFee | numberToFixedTwo }}
              </template>
            </el-table-column>
            <el-table-column prop="exchangeRate" label="汇率" show-overflow-tooltip/>
            <el-table-column prop="currencyCode" label="币种" show-overflow-tooltip/>
            <el-table-column prop="bargainType" label="交易成交方式" min-width="100" show-overflow-tooltip />
            <el-table-column
              :formatter="statusCodeNameformatter"
              prop="status"
              width="100"
              label="状态"
              show-overflow-tooltip/>
            <el-table-column prop="costTime" label="成本结算日期" width="100" show-overflow-tooltip/>
            <el-table-column prop="invoiceDate" label="发票日期" show-overflow-tooltip/>
            <el-table-column prop="impDate" label="入库日期" show-overflow-tooltip/>
            <!-- <el-table-column prop="shipDate" label="发出日期" show-overflow-tooltip/> -->
            <el-table-column prop="amountadjust" label="原金额调整金额" width="120" show-overflow-tooltip/>
            <el-table-column prop="arrivedWarehouseCode" label="收货仓库" show-overflow-tooltip/>
            <el-table-column prop="shipDate" label="发货日期" show-overflow-tooltip/>
            <el-table-column
              :formatter="receiveTimeformatter"
              prop="receiveTime"
              label="物流签收日期"
              width="100"
              show-overflow-tooltip
            />
            <el-table-column prop="customsDate" label="通关日期" show-overflow-tooltip/>
            <el-table-column prop="createTime" label="数据导入时间" width="120" show-overflow-tooltip/>
            <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip/>
            <el-table-column prop="updateUser" label="更新人" show-overflow-tooltip/>
            <el-table-column fixed="right" label="操作" align="center" width="130">
              <template slot-scope="scope">
                <!-- <el-button
                  v-if="!scope.row.isEditShow"
                  type="success"
                  size="mini"
                  title="查看明细"
                  icon="el-icon-view"
                  @click="selShowDetail(scope.$index, scope.row)"
                /> -->
                <el-button
                  v-permission="['280159']"
                  v-if="scope.row.amount == 0"
                  type="success"
                  title="导入明细"
                  size="mini"
                  icon="el-icon-upload"
                  style="padding-left: 8px;padding-right: 8px;margin-left: 1px;margin-right: 1px;"
                  @click="confirmPOInvoiceDetail(scope.row)"
                />
                <el-button
                  v-permission="['280159']"
                  v-if="scope.row.status == 2"
                  type="warning"
                  title="修改"
                  size="mini"
                  icon="el-icon-edit"
                  style="padding-left: 8px;padding-right: 8px;margin-left: 1px;margin-right: 1px;"
                  @click="showEditable(scope.row)"
                />
                <el-button
                  v-permission="['280159']"
                  v-if="scope.row.status < 3"
                  type="danger"
                  title="清除关务明细"
                  size="mini"
                  icon="el-icon-folder-remove"
                  style="padding-left: 8px;padding-right: 8px;margin-left: 1px;margin-right: 1px;"
                  @click="clearPOInvoiceDetail(scope.row)"
                />
                <!-- <div v-else>
              <el-button type="primary" plain size="mini" @click="saveProperty(scope.row)">保存</el-button>
              <el-button size="mini" @click="cancelProperty(scope.row)">取消</el-button>
                </div>-->
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="queryForm.total > 0"
            :total="queryForm.total"
            :page.sync="queryForm.pageNum"
            :limit.sync="queryForm.pageSize"
            @pagination="searchData"
          />
        </div>
        <!-- <div style="margin-top:20px;margin-bottom:15px">
          <el-divider />
        </div>-->
        <!-- <i class="el-icon-coin" style="color: #FFA500;" />
        <span style="font-weight: 800; font-size: 13px">&nbsp;&nbsp;明细数据</span>-->
      </el-tab-pane>
      <el-tab-pane label="明细数据" name="pagePoDetail" type="border-card">
        <!-- <div style="margin-top:0px;margin-bottom:5px" /> -->
        <div v-show="baseInfoVisible === true" class="product-base-content-body">
          <el-form ref="detailForm" :inline="true" :model="detailForm" size="mini">
            <el-form-item>
              <el-input v-model="detailForm.invoiceId" style="width: 120px" placeholder="发票ID"/>
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="detailForm.orderNo"
                style="width: 140px"
                clearable
                placeholder="订单号"
              />
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="detailForm.modelNo"
                style="width: 180px"
                clearable
                placeholder="型号"
              />
            </el-form-item>
            <!-- <el-form-item label="条码">
          <el-input v-model="detailForm.barcode" style="width: 120px" clearable placeholder="条码" />
            </el-form-item>-->
            <el-form-item>
              <el-button
                v-permission="['579106']"
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="showDetail(0)">查询
              </el-button>
            </el-form-item>
            <el-form-item>
              <el-button
                v-permission="['280159']"
                v-loading="exportDetailLoading"
                type="primary"
                icon="el-icon-upload"
                size="mini"
                @click="exportDetail">导出
              </el-button>
            </el-form-item>
            <el-form-item style="float:right" label="合计金额">
              <div>{{ detailAmount.amounttotal | numberToCurrency }}</div>
            </el-form-item>
            <el-form-item
              style="float:right"
              label="其他费"
            >{{ detailAmount.otherFee | numberToCurrency }}
            </el-form-item>
            <el-form-item
              style="float:right"
              label="运费"
            >{{ detailAmount.transFee | numberToCurrency }}
            </el-form-item>
            <el-form-item
              style="float:right"
              label="关税"
            >{{ detailAmount.customsFee | numberToCurrency }}
            </el-form-item>
            <el-form-item
              style="float:right"
              label="消费税"
            >{{ detailAmount.excisetax | numberToCurrency }}
            </el-form-item>
            <el-form-item
              style="float:right"
              label="发票金额RMB"
            >{{ detailAmount.amountRmb | numberToCurrency }}
            </el-form-item>
            <el-form-item
              style="float:right"
              label="原发票金额"
            >{{ detailAmount.amount | numberToCurrency }}
            </el-form-item>
          </el-form>
        </div>
        <el-dialog
          :visible="dialogupdCostVisible"
          :close-on-click-modal="true"
          title="更新明细成本"
          width="760px"
          @close="dialogupdCostVisible=false">
          <el-form ref="updateCostForm" :inline="true" :model="updCostFrom">
            <el-form-item label-width="100px" label="发票号Id" size="mini">
              <el-input v-model="updCostFrom.invoiceId" style="width: 110px" disabled auto-complete="off"/>
            </el-form-item>
            <el-form-item label-width="100px" label="发票号" size="mini">
              <el-input v-model="updCostFrom.invoiceNo" style="width: 110px" disabled auto-complete="off"/>
            </el-form-item>

            <el-form-item label-width="100px" label="订单号" size="mini">
              <el-input v-model="updCostFrom.orderNo" style="width: 110px" disabled auto-complete="off"/>
            </el-form-item>
            <el-form-item label-width="100px" label="数量" size="mini">
              <el-input v-model="updCostFrom.quantity" style="width: 110px" disabled auto-complete="off"/>
            </el-form-item>
            <el-form-item label-width="100px" label="原单价" size="mini">
              <el-input v-model="updCostFrom.price" style="width: 110px" disabled @change="calcOrderCost(1)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="原金额" size="mini">
              <el-input v-model="updCostFrom.amount" style="width: 110px" @change="calcOrderCost(2)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="原单价(RMB)" size="mini">
              <el-input v-model="updCostFrom.priceRmb" style="width: 110px" disabled @change="calcOrderCost(3)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="原金额(RMB)" size="mini">
              <el-input v-model="updCostFrom.amountRmb" style="width: 110px" disabled @change="calcOrderCost(4)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="关税" size="mini">
              <el-input v-model="updCostFrom.customsFee" style="width: 110px" disabled @change="calcOrderCost(9)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="运保费" size="mini">
              <el-input v-model="updCostFrom.transFee" style="width: 110px" disabled @change="calcOrderCost(9)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="消费税" size="mini">
              <el-input v-model="updCostFrom.exciseTax" style="width: 110px" disabled @change="calcOrderCost(9)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="其他费" size="mini">
              <el-input v-model="updCostFrom.otherFee" style="width: 110px" disabled @change="calcOrderCost(9)"/>
            </el-form-item>
            <el-form-item label-width="100px" label="汇率" size="mini">
              <el-input v-model="updCostFrom.exchangeRate" style="width: 110px" disabled auto-complete="off"/>
            </el-form-item>
            <el-form-item label-width="100px" label="合计单价" size="mini">
              <el-input v-model="updCostFrom.priceTotal" style="width: 110px" disabled auto-complete="off"/>
            </el-form-item>
            <el-form-item label-width="100px" label="合计金额" size="mini">
              <el-input v-model="updCostFrom.amountTotal" style="width: 110px" disabled auto-complete="off"/>
            </el-form-item>
            <el-form-item label-width="100px" label="无商业价值" size="mini">
              <el-input v-model="updCostFrom.nonCommercial" style="width: 60px" maxlength="1" show-word-limit @change="changeNonCommercial()"/>
            </el-form-item>
            <dlv style="float:right">
              <el-button size="mini" @click="updateDetailCost">更新明细成本</el-button>
              <el-button size="mini" @click="dialogupdCostVisible=false">取消</el-button>
            </dlv>
          </el-form>

        </el-dialog>
        <div style="margin-top: 0px">
          <el-table
            ref="multipledetalTable"
            :data="poInvoiceDetail"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '13px'}"
            :row-style="{height:'33px'}"
            highlight-current-row
            border
            stripe
            size="mini"
            style="width: 100%"
            height="70vh"
          >
            <el-table-column v-if="false" prop="id" label="id" show-overflow-tooltip/>
            <el-table-column prop="invoiceId" label="发票id" show-overflow-tooltip/>
            <el-table-column prop="invoiceNo" label="发票号" show-overflow-tooltip/>
            <el-table-column prop="orderNo" label="订单号" show-overflow-tooltip/>
            <el-table-column prop="modelNo" label="型号" show-overflow-tooltip/>
            <el-table-column prop="quantity" label="数量" show-overflow-tooltip/>
            <el-table-column prop="price" label="原单价" show-overflow-tooltip>
              <!-- <template slot-scope="scope">
                {{ scope.row.vatFee | numberToFixedFour }}
              </template> -->
            </el-table-column>
            <el-table-column prop="amount" label="原金额" show-overflow-tooltip/>
            <el-table-column prop="amountRmb" label="原金额(RMB)" width="100" show-overflow-tooltip/>
            <el-table-column prop="customsFee" label="关税" show-overflow-tooltip/>
            <el-table-column prop="transFee" label="运保费" show-overflow-tooltip/>
            <el-table-column prop="exciseTax" label="消费税" show-overflow-tooltip/>
            <el-table-column prop="otherFee" label="其他费" show-overflow-tooltip/>
            <el-table-column prop="priceTotal" label="合计单价" show-overflow-tooltip/>
            <el-table-column prop="amountTotal" label="合计金额" show-overflow-tooltip/>
            <el-table-column prop="prodCountry" label="原产地" show-overflow-tooltip/>
            <el-table-column prop="weight" label="重量" show-overflow-tooltip/>
            <el-table-column prop="nonCommercial" label="无商业价值" width="100" show-overflow-tooltip/>
            <el-table-column prop="overseaInvoiceNo" label="原发票号" show-overflow-tooltip/>
            <el-table-column prop="ecode" label="Ecode" show-overflow-tooltip/>
            <el-table-column prop="remark" label="备注" show-overflow-tooltip/>
            <el-table-column label="操作" width="50" fixed="right" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-button
                  v-permission="['280159']"
                  v-if="invoiceStatus <= 2"
                  title="修改"
                  size="mini"
                  icon="el-icon-edit-outline"
                  @click="showOrderCost(scope.row)"
                />
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="detailForm.total > 0"
            :total="detailForm.total"
            :page.sync="detailForm.pageNum"
            :limit.sync="detailForm.pageSize"
            @pagination="showDetail(1)"
          />
        </div>
      </el-tab-pane>
      <el-tab-pane label="增值税发票入库" name="pageimpInvoice" type="border-card">
        <div class="product-base-content-body">
          <el-form ref="impinvoiceForm" :inline="true" :model="impinvoiceForm" size="mini">
            <el-form-item label="发票号">
              <el-input
                v-model="impinvoiceForm.invoiceNo"
                style="width: 130px"
                clearable
                placeholder="加%号模糊查询"
              />
            </el-form-item>
            <el-form-item label="状态">
              <el-select
                v-model="impinvoiceForm.status"
                placeholder="请选择"
                style="width: 125px"
                clearable
                size="mini"
                @change="searchImpinvoice"
              >
                <el-option
                  v-for="item in vatstatusCodes"
                  :key="item.code"
                  :value="item.code"
                  :label="item.codeName"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-select
                v-model="impinvoiceForm.supplierCode"
                placeholder="供应商"
                clearable
                style="width: 160px"
                @change="searchImpinvoice"
              >
                <el-option
                  v-for="item in supplierCodeOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="发票日期">
              <el-date-picker
                v-model="invoiceDate"
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
              <el-button
                v-permission="['579106']"
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="searchImpinvoice">查询
              </el-button>
            </el-form-item>
            <el-form-item>
              <el-button
                v-permission="['280159']"
                type="primary"
                icon="el-icon-plus"
                size="mini"
                @click="costInvoice">转成本
              </el-button>
            </el-form-item>
            <el-form-item>
              <el-button
                v-permission="['280159']"
                v-loading="exportValueLoading"
                type="primary"
                icon="el-icon-upload"
                size="mini"
                @click="exportValueImpinvoice"
              >导出月次统计
              </el-button>
            </el-form-item>
          </el-form>
        </div>
        <div>
          <el-table
            v-loading="listImpLoading"
            ref="multipleImpTable"
            :data="listimpinvoice"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
            :row-style="{height:'33px'}"
            highlight-current-row
            stripe
            border
            size="mini"
            style="width: 100%"
            height="68vh"
            @selection-change="handleSelection"
          >
            <!-- 表头字段 -->
            <el-table-column type="selection" width="55"/>
            <el-table-column prop="id" label="发票ID" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-popover placement="right" width="200">
                  <el-table
                    :data="impDetailData"
                    :row-style="{height: '0'}"
                    style="width: 100%;font-size:10px"
                    size="mini">
                    <!-- <el-table-column property="invoiceId" align="center" label="发票ID" show-overflow-tooltip/>
                    <el-table-column property="fullOrderNo" align="center" label="订单号" show-overflow-tooltip/>
                    <el-table-column property="modelNo" align="center" label="型号" show-overflow-tooltip/>
                    <el-table-column property="quantity" align="center" label="数量" show-overflow-tooltip/>
                    <el-table-column property="price" align="center" label="价格" show-overflow-tooltip/> -->
                    <el-table-column property="overseaInvoiceNo" align="center" label="原发票号" show-overflow-tooltip/>
                    <el-table-column property="amount" align="center" label="金额" show-overflow-tooltip/>
                    <!-- <el-table-column :formatter="detailstatusformatter" property="status" align="center" label="状态" show-overflow-tooltip/> -->
                  </el-table>
                  <span
                    slot="reference"
                    title="查看详情"
                    style="cursor: pointer;color:#337AB7;"
                    @click="showimpDetail(scope.row)"
                  >{{ scope.row.id }}</span>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column prop="invoiceNo" label="发票号" show-overflow-tooltip>
              <!-- <template slot-scope="scope">
                <el-link
                  :underline="false"
                  @click="selShowDetail(scope.$index, scope.row)"
                >{{ scope.row.invoiceNo }}</el-link>
              </template> -->
            </el-table-column>
            <el-table-column
              :formatter="impstatusCodeNameformatter"
              prop="status"
              label="状态"
              show-overflow-tooltip
            />
            <el-table-column prop="supplierCode" label="供应商" show-overflow-tooltip/>
            <el-table-column
              :formatter="invoiceDateformatter"
              prop="invoiceDate"
              label="发票日期"
              show-overflow-tooltip
            />
            <el-table-column
              :formatter="receiveformatter"
              prop="receiveWarehouseCode"
              label="收货仓库"
              show-overflow-tooltip
            />
            <el-table-column prop="amount" label="不含税金额" show-overflow-tooltip/>
            <el-table-column prop="vatFee" label="增值税" show-overflow-tooltip/>
            <el-table-column prop="shipAmount" label="发货金额" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.shipAmount }}</span>
                <el-button type="text" size="mini" icon="el-icon-refresh" @click="updImpShipAmount(scope.row)">
                  <span
                    v-loading="updshipAmountming"
                    element-loading-text="更新发货金额中.."
                    element-loading-spinner="el-icon-loading"/>
                </el-button>
              </template>
            </el-table-column>
            <el-table-column
              :formatter="createTimeformatter"
              prop="createTime"
              label="数据导入时间"
              show-overflow-tooltip
            />
            <el-table-column
              :formatter="updateTimeformatter"
              prop="updateTime"
              label="更新时间"
              show-overflow-tooltip
            />
            <el-table-column prop="remark" label="备注" show-overflow-tooltip/>
          </el-table>
          <pagination
            v-show="impinvoiceForm.total > 0"
            :total="impinvoiceForm.total"
            :page.sync="impinvoiceForm.pageNum"
            :limit.sync="impinvoiceForm.pageSize"
            @pagination="searchImpinvoice"
          />
        </div>
        <!-- 转成本弹窗 -->
        <el-dialog :visible.sync="dialogcostinvoice" title="转成本" width="500px">
          <el-form ref="costForm" :model="costForm" :inline="true" label-width="150px">
            <el-form-item prop="costDate" label="成本结算日期">
              <el-date-picker
                v-model="costForm.costDate"
                style="width:200px"
                type="date"
                placeholder="选择日期"
              />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogcostinvoice = false">取 消</el-button>
            <el-button type="primary" @click="dialogcostinvoice = false ;costInvoiceData()">确定</el-button>
          </div>
        </el-dialog>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {
  listPoinvoice,
  listPoInvoiceDetail,
  updatePoinvoice,
  exportDataToCost,
  exportPoinvoice,
  getPoInvoiceDetailAmount,
  getExchangeRate,
  exportPoInvoiceDetail,
  confirmPOInvoiceDetail,
  clearPOInvoiceDetail,
  exportOtherInvoiceData,
  listImpInvoiceData,
  impInvoiceToCost,
  exportValueImpinvoice,
  updImpShipAmount,
  redoCostInvoice,
  listOverseaInvoiceData,
  updatePoInvoiceDetailCost,
  getOpsPoInvoice
} from '@/api/invoicedata'
import { getDictDataByPid, listWarehouse } from '@/api/common/dict'
import { downloadDataFile } from '@/api/common/comm'
import { findSupplier } from '@/api/warehouseManage'
import { numberToFixedTwo, numberToFixedFour, multiply, divide, addMore } from '@/utils/numberToCurrency'

export default {
  name: 'PoInvoice',
  components: { Pagination },
  data() {
    return {
      activeTabName: 'pane1',
      listLoading: true,
      baseInfoVisible: true,
      uploadStatus: false,
      dialogupdVisible: false,
      dialogupdCostVisible: false,
      orderCostChange: false,
      updshipAmountming: false,
      amountTotal: 0,
      listdata: [],
      supplierCodeOptions: [],
      detailAmount: {
        amount: 0,
        amountRmb: 0,
        customsFee: 0,
        excisetax: 0,
        vatFee: 0,
        transFee: 0,
        otherFee: 0,
        amounttotal: 0
      },
      impinvoiceForm: {
        invoiceNo: '',
        status: '4',
        supplierCode: '',
        qryDateType: '',
        fromDate: '',
        toDate: '',
        invoiceType: 5,
        pageNum: 0,
        pageSize: 10,
        total: 0,
        isEditable: false
      },
      dateTypeOptions: [{
        code: 1,
        codeName: '入库日期'
      },
      {
        code: 2,
        codeName: '成本结算日期'
      },
      {
        code: 3,
        codeName: '物流签收日期'
      },
      {
        code: 4,
        codeName: '发票日期'
      }],
      queryForm: {
        invoiceNo: '',
        status: '2',
        supplierCode: '',
        qryDateType: 1,
        costDate: '',
        fromDate: '',
        toDate: '',
        currencyCode: '',
        invoiceType: '',
        pageNum: 0,
        pageSize: 20,
        total: 0
      },
      detailForm: {
        invoiceId: '',
        invoiceNo: '',
        orderNo: '',
        modelNo: '',
        sumAmount: '',
        pageNum: 0,
        pageSize: 20,
        total: 0
      },
      oldinvoiceId: '',
      updForm: {
        id: 0,
        invoiceId: 0,
        invoiceNo: '',
        currencyCode: '',
        exchangeRate: 0,
        amount: 0,
        amountadjust: 0,
        amountRmb: 0,
        customsFee: 0,
        otherFee: 0,
        transFee: 0,
        excisetax: 0,
        allFee: 0,
        invoiceDate: '',
        receiveTime: '',
        isFinishCost: false,
        costTime: ''
      },
      updCostFrom: {
        id: 0,
        invoiceId: 0,
        invoiceNo: '',
        orderNo: '',
        quantity: 0,
        price: 0,
        amount: 0,
        priceRmb: 0,
        amountRmb: 0,
        customsFee: 0,
        transFee: 0,
        exciseTax: 0,
        otherFee: 0,
        priceTotal: 0,
        amountTotal: 0,
        exchangeRate: 1,
        nonCommercial: ''
      },
      poInvoiceDetail: [],
      statusCodes: [],
      currencyCodeCodes: [],
      multipleSelection: [],
      listimpinvoice: [],
      listImpLoading: false,
      impstatusCodes: [],
      vatstatusCodes: [],
      warehouseData: [],
      impDetailData: [],
      costForm: {
        costDate: '',
        ids: []
      },
      dialogcostinvoice: false,
      exportLoading: false,
      exportOtherLoading: false,
      exportDetailLoading: false,
      exportValueLoading: false,
      invoiceDate: '',
      detailstatusdesc: [],
      invoiceTypedesc: [],
      query: false,
      exchangeRate: 0,
      invoiceStatus: 0

    }
  },
  created() {
    this.initCodedesc()
    this.searchData()
    this.listWarehouseinfo()
  },
  methods: {
    numeblur() {
      //   this.impinvoiceForm.supplierCode = this.$refs.tableName.selelecteLable
      this.impinvoiceForm.supplierCode = ''
    },
    initCodedesc() {
      getDictDataByPid('2043').then(result => {
        this.statusCodes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2045').then(result => {
        this.detailstatusdesc = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('4303').then(result => {
        this.currencyCodeCodes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2040').then(result => {
        this.impstatusCodes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2065').then(result => {
        this.vatstatusCodes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2064').then(result => {
        this.invoiceTypedesc = result.content
      }).catch(error => {
        console.log(error)
      })
      findSupplier().then(res => {
        this.supplierCodeOptions = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: '',
        warehouseType: '',
        keywords: ''
      }
      listWarehouse(formData).then(res => {
        this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    searchMoreData() {
      this.query = !this.query
    },
    searchData() {
      this.listLoading = true
      this.amountTotal = 0
      listPoinvoice(this.queryForm).then(result => {
        this.listdata = result.content.list
        for (const i in this.listdata) {
          this.amountTotal = this.amountTotal + (this.listdata[i].amounttotal === null ? 0 : this.listdata[i].amounttotal)
          this.$set(this.listdata[i], 'isEditShow', false)
        }
        this.amountTotal = this.amountTotal.toFixed(2)
        this.queryForm.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    selShowDetail(index, row) {
      if (row.id) {
        this.detailForm.invoiceId = row.invoiceId
        this.detailForm.invoiceNo = row.invoiceNoOrg
        this.exchangeRate = row.exchangeRate
        this.invoiceStatus = row.status
        this.showDetail(0)
      }
    },
    saveProperty() {
      if (this.updForm.isFinishCost && this.updForm.costTime === '') {
        this.$message.error('选择是完成结算必须要填好结算日期！')
        return
      }
      updatePoinvoice(this.updForm)
        .then(res => {
          if (res.success) {
            this.$message.success(res.message)
            this.dialogupdVisible = false
            this.searchData()
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    updImpShipAmount(row) {
      if (this.updshipAmountming === true) {
        this.$message.error('不能重复点击！')
      }
      this.updshipAmountming = true
      updImpShipAmount(row.id)
        .then(res => {
          if (res.success) {
            this.updshipAmountming = false
            this.$message.success(res.message)
            this.searchImpinvoice()
          } else {
            this.updshipAmountming = false
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.updshipAmountming = false
          this.$message.error(error + '请稍后再常试')
        })
    },
    handleSelection(val) {
      this.multipleSelection = val
    },
    redoCostInvoice() {
      const data = this.$refs.multipleTable.selection
      if (data.length <= 0) {
        this.$message.warning('请选择一个发票')
        return
      }
      if (data[0].status !== 3) {
        this.$message.warning('请选择已结转的发票')
        return
      }
      redoCostInvoice(data[0].invoiceId).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      })
    },
    ExportDataToCost() {
      this.uploadStatus = true
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选择列表数据！'
        })
        this.uploadStatus = false
        return
      }
      if (this.queryForm.costDate === '') {
        this.$message({
          type: 'warning',
          message: '请选择结算日期！'
        })
        this.uploadStatus = false
        return
      }
      const invoiceIds = []
      this.multipleSelection.forEach((item, index) => {
        invoiceIds.push(item.invoiceId)
      })
      const formData = { invoiceIds: invoiceIds, costDate: this.queryForm.costDate }
      exportDataToCost(formData)
        .then(res => {
          if (res.success) {
            this.smcInfoMsg(res.message)
            this.uploadStatus = false
          } else {
            this.smcErrorMsg(res.message)
            this.uploadStatus = false
          }
          this.searchData()
        }).catch(error => {
          this.smcErrorMsg(error + '请稍后再常试')
          this.uploadStatus = false
        })
    },
    exportData() {
      this.exportLoading = true
      exportPoinvoice(this.queryForm).then(res => {
        downloadDataFile(this, res, 'OpsPoInvoice.xlsx')
        this.exportLoading = false
      }).catch(error => {
        this.$message.error('错误：' + error)
        this.exportLoading = false
      })
    },
    exportOtherInvoiceData() {
      this.exportOtherLoading = true
      exportOtherInvoiceData(this.queryForm).then(res => {
        downloadDataFile(this, res, 'OtherInvoiceData.xlsx')
        this.exportOtherLoading = false
      }).catch(error => {
        console.error(error)
        this.$message.error('错误：' + error)
        this.exportOtherLoading = false
      })
    },
    exportDetail() {
      if (!this.detailForm.invoiceId) {
        this.$message.error('请先选择发票单号')
        return
      }
      this.exportDetailLoading = true
      exportPoInvoiceDetail(this.detailForm).then(res => {
        downloadDataFile(this, res, 'PoInvoiceDetail.xlsx')
        this.exportDetailLoading = false
      }).catch(error => {
        console.error(error)
        this.$message.error('错误：' + error)
        this.exportDetailLoading = false
      })
    },
    exportValueImpinvoice() {
      this.exportValueLoading = true
      this.setDateCondition()
      if (this.invoiceDate === null) {
        this.$message.error('请先选择发票日期')
        this.exportValueLoading = false
        return
      }
      exportValueImpinvoice(this.impinvoiceForm).then(res => {
        downloadDataFile(this, res, 'ValueImpinvoice.xlsx')
        this.exportValueLoading = false
      }).catch(error => {
        console.error(error)
        this.$message.error('错误：' + error)
        this.exportValueLoading = false
      })
    },
    showEditable(row) {
      //   row.isEditShow = true
      this.dialogupdVisible = true
      this.updForm.id = row.id
      this.updForm.invoiceId = row.invoiceId
      this.updForm.invoiceNo = row.invoiceNo
      this.updForm.currencyCode = row.currencyCode
      this.updForm.exchangeRate = row.exchangeRate
      this.updForm.amount = row.amount
      //   this.updForm.amountadjust = row.amountadjust
      this.updForm.amountRmb = row.amountRmb
      this.updForm.customsFee = row.customsFee
      this.updForm.otherFee = row.otherFee
      this.updForm.transFee = row.transFee
      this.updForm.excisetax = row.excisetax
      this.updForm.invoiceDate = row.invoiceDate
      this.updForm.receiveTime = this.dayjs(row.receiveTime).format('YYYY-MM-DD')
      //   const pkinput = document.querySelector('#receiveTimepk')
      //   console.log(pkinput)
      //   pkinput.disabled = true
      this.updForm.costTime = row.costTime
      // this.updForm.amountRmb = numberToFixedTwo(multiply(this.updForm.amount , this.updForm.exchangeRate))
      // let  allFee =add(this.updForm.amountRmb ,this.updForm.customsFee)
      // allFee =add(allFee,this.updForm.excisetax)
      // allFee =add(allFee,this.updForm.transFee)
      // allFee =add(allFee,this.updForm.otherFee)
      // this.updForm.allFee = numberToFixedTwo(allFee)
      this.updForm.invoiceNo = row.invoiceNo
      this.caculAllFee()
    },
    confirmPOInvoiceDetail(row) {
      confirmPOInvoiceDetail(row.invoiceId)
        .then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.searchData()
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    clearPOInvoiceDetail(row) {
      clearPOInvoiceDetail(row.invoiceId).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          this.searchData()
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.$message.error(error + '请稍后再常试')
      })
    },
    caculAllFee() {
      this.updForm.amountRmb = numberToFixedTwo(multiply(this.updForm.amount, this.updForm.exchangeRate))
      const allFee = addMore(this.updForm.amountRmb, this.updForm.customsFee, this.updForm.excisetax, this.updForm.transFee, this.updForm.otherFee)
      this.updForm.allFee = numberToFixedTwo(allFee)
      // this.updForm.amountRmb = Number(this.updForm.amount * this.updForm.exchangeRate).toFixed(2)
      // this.updForm.allFee = (Number(this.updForm.amountRmb) + Number(this.updForm.customsFee) + Number(this.updForm.excisetax) + Number(this.updForm.transFee) + Number(this.updForm.otherFee)).toFixed(2)
    },
    // <!--add by WuWeiDong 20230109 bug 9276 -->
    showOrderCost(row) {
      this.dialogupdCostVisible = true
      this.orderCostChange = false
      this.updCostFrom.id = row.id
      this.updCostFrom.invoiceId = row.invoiceId
      this.updCostFrom.invoiceNo = row.invoiceNo
      this.updCostFrom.orderNo = row.orderNo
      this.updCostFrom.quantity = row.quantity
      this.updCostFrom.price = row.price
      this.updCostFrom.amount = row.amount
      this.updCostFrom.priceRmb = row.priceRmb
      this.updCostFrom.amountRmb = row.amountRmb
      this.updCostFrom.customsFee = row.customsFee
      this.updCostFrom.transFee = row.transFee
      this.updCostFrom.exciseTax = row.exciseTax
      this.updCostFrom.otherFee = row.otherFee
      this.updCostFrom.priceTotal = row.priceTotal
      this.updCostFrom.amountTotal = row.amountTotal
      this.updCostFrom.exchangeRate = this.exchangeRate
    },
    // <!--add by WuWeiDong 20230109 bug 9276 -->
    calcOrderCost(chagetype) {
      if (this.exchangeRate === 0) {
        this.$message.error('此发票没有汇率数据，请获取汇率数据之后，再修改。')
        return
      }
      // <!--edit by WuWeiDong 20230428 bug 10637 -->
      // 1：原单价，2:原金额，3：原单价RMB，4：原金额RMB，9：关税，运保费，消费税其他费
      try {
        switch (chagetype) {
          case 1: // 1：原单价
            this.updCostFrom.price = numberToFixedFour(this.updCostFrom.price)
            this.updCostFrom.amount = numberToFixedTwo(multiply(this.updCostFrom.price, this.updCostFrom.quantity))
            this.updCostFrom.amountRmb = numberToFixedTwo(multiply(this.updCostFrom.amount, this.updCostFrom.exchangeRate))
            this.updCostFrom.priceRmb = numberToFixedFour(divide(this.updCostFrom.amountRmb, this.updCostFrom.quantity))
            break
          case 2: //  2:原金额
            this.updCostFrom.amount = numberToFixedTwo(this.updCostFrom.amount)
            this.updCostFrom.price = numberToFixedFour(divide(this.updCostFrom.amount, this.updCostFrom.quantity))
            this.updCostFrom.amountRmb = numberToFixedTwo(multiply(this.updCostFrom.amount, this.updCostFrom.exchangeRate))
            this.updCostFrom.priceRmb = numberToFixedFour(divide(this.updCostFrom.amountRmb, this.updCostFrom.quantity))

            break
          case 3: //  3：原单价RMB
            this.updCostFrom.priceRmb = numberToFixedFour(this.updCostFrom.priceRmb)
            this.updCostFrom.amountRmb = numberToFixedTwo(multiply(this.updCostFrom.priceRmb, this.updCostFrom.quantity))
            this.updCostFrom.amount = numberToFixedTwo(divide(this.updCostFrom.amountRmb, this.updCostFrom.exchangeRate))
            this.updCostFrom.price = numberToFixedFour(divide(this.updCostFrom.amount, this.updCostFrom.quantity))
            break
          case 4: //  4：原金额RMB
            this.updCostFrom.amountRmb = numberToFixedTwo(this.updCostFrom.amountRmb)
            this.updCostFrom.priceRmb = numberToFixedFour(divide(this.updCostFrom.amountRmb, this.updCostFrom.quantity))
            this.updCostFrom.amount = numberToFixedTwo(divide(this.updCostFrom.amountRmb, this.updCostFrom.exchangeRate))
            this.updCostFrom.price = numberToFixedFour(divide(this.updCostFrom.amount, this.updCostFrom.quantity))
            break
        }
        const amountTotal = addMore(this.updCostFrom.amountRmb, this.updCostFrom.customsFee, this.updCostFrom.transFee, this.updCostFrom.exciseTax, this.updCostFrom.otherFee)
        this.updCostFrom.amountTotal = numberToFixedTwo(amountTotal)
        this.updCostFrom.priceTotal = numberToFixedFour(divide(this.updCostFrom.amountTotal, this.updCostFrom.quantity))
        this.orderCostChange = true
      } catch (e) {
        this.$message.error('计算错误，可能输入有问题：' + e)
      }
    },
    changeNonCommercial() {
      this.orderCostChange = true
    },
    // <!--add by WuWeiDong 20230109 bug 9276 -->
    updateDetailCost() {
      if (this.orderCostChange) {
        updatePoInvoiceDetailCost(this.updCostFrom).then(res => {
          if (res.success) {
            this.$message.success(res.message)
            this.dialogupdCostVisible = false
            this.orderCostChange = false
            this.showDetail(1)
          } else {
            this.$message.error(res.message)
          }
        })
          .catch(error => {
            console.log(error)
            this.$message.error(error.message)
          })
      } else {
        this.$message.error('数据没有变动！请修改后再更新！')
      }
    },
    getExchangeRate() {
      if (this.updForm.currencyCode === '' || this.updForm.receiveTime === '' || this.updForm.receiveTime === undefined || this.updForm.receiveTime === null) {
        this.$message.error('币种和物流签收日期不能为空')
        return
      }
      const ratequry = {
        currency: this.updForm.currencyCode,
        monthDate: this.dayjs(this.updForm.receiveTime).format('YYYY-MM-DD')
      }
      getExchangeRate(ratequry).then(result => {
        if (result.success && result.content != null) {
          this.updForm.exchangeRate = result.content
        } else {
          this.$message.error(result.message)
        }
      }).catch(error => {
        console.log(error)
        this.$message.error(error.message)
      })
    },
    cancelProperty(row) {
      row.isEditShow = false
    },
    showimpDetail(row) {
      //   const data = {
      //     invoiceId: row.id,
      //     invoiceNo: '',
      //     orderNo: '',
      //     modelNo: '',
      //     barcode: '',
      //     overseaInvoiceNo: '',
      //     pageNum: 0,
      //     pageSize: 1000,
      //     total: 0
      //   }
      listOverseaInvoiceData(row.id).then(result => {
        if (result.success) {
          this.impDetailData = result.content
          //   for (let i = 0; i < result.content.length; i++) {
          //     const data = { overseaInvoiceNo: result.content[i] }
          //     this.impDetailData.push(data)
          //   }
        }
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    showDetail(type) {
      if (!this.detailForm.invoiceId) {
        this.$message.error('请先选择发票单号')
        return
      }
      this.activeTabName = 'pagePoDetail'
      this.listLoading = true
      if (type !== 1) {
        this.detailAmount = this.$options.data().detailAmount
      }
      listPoInvoiceDetail(this.detailForm).then(result => {
        if (result.success) {
          this.poInvoiceDetail = result.content.list
          this.detailForm.total = result.content.total
          this.listLoading = false
          if (type !== 1) {
            this.getPoInvoiceDetailAmount()
          }
          this.oldinvoiceId = this.detailForm.invoiceId
        }
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
      if (this.exchangeRate === 0 || this.invoiceStatus === 0) {
        const invoiceId = Number(this.detailForm.invoiceId)

        getOpsPoInvoice(invoiceId).then(res => {
          if (res.success) {
            if (res.content != null) {
              // this.listdata[i].amounttotal === null ? 0 : this.listdata[i].amounttotal
              this.exchangeRate = res.content.exchangeRate === null ? 0 : res.content.exchangeRate
              this.invoiceStatus = res.content.status === null ? 0 : res.content.status
            } else {
              this.exchangeRate = 0
              this.invoiceStatus = 0
              console.info('没有汇率数据，请获取汇率数据。')
              // this.$message.error("没有汇率数据，请获取汇率数据。")
            }
          } else {
            this.exchangeRate = 0
            this.invoiceStatus = 0
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.exchangeRate = 0
          this.invoiceStatus = 0
          this.$message.error(error)
          console.info(error)
        })
      }
    },
    getPoInvoiceDetailAmount() {
      getPoInvoiceDetailAmount(this.detailForm.invoiceId).then(result => {
        if (result.success && result.content != null) {
          this.detailAmount = result.content
        }
      }).catch(error => {
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.invoiceDate === null) {
        this.impinvoiceForm.invoiceDateStart = ''
        this.impinvoiceForm.invoiceDateEnd = ''
      } else {
        this.impinvoiceForm.invoiceDateStart = this.invoiceDate[0]
        this.impinvoiceForm.invoiceDateEnd = this.invoiceDate[1]
      }
    },
    searchImpinvoice() {
      this.listImpLoading = true
      this.setDateCondition()
      this.impinvoiceForm.invoiceType = 5
      console.log(this.impinvoiceForm)
      listImpInvoiceData(this.impinvoiceForm).then(result => {
        if (result.success) {
          this.listimpinvoice = result.content.list
          this.impinvoiceForm.total = result.content.total
        }
        this.listImpLoading = false
      }).catch(error => {
        this.listImpLoading = false
        console.log(error)
      })
    },
    descFormatter(data, id) {
      if (data === null) {
        return id
      }
      for (const i in data) {
        var item = data[i]
        if (item.code.toString() === id.toString()) {
          return item.codeName
        }
      }
      return id
    },
    costInvoice() {
      if (this.$refs.multipleImpTable.selection.length <= 0) {
        this.$message.warning('请选择需要转成本的数据')
        return
      }
      this.dialogcostinvoice = true
      this.costForm.ids = []
      for (var i = 0; i < this.$refs.multipleImpTable.selection.length; i++) {
        this.costForm.ids.push(this.$refs.multipleImpTable.selection[i].id)
      }
    },
    costInvoiceData() {
      if (this.costForm.costDate === '' || this.costForm.costDate == null) {
        this.$message.error('请选择成本结算日期!')
        return
      }
      impInvoiceToCost(this.costForm).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    statusCodeNameformatter(row) {
      return this.descFormatter(this.statusCodes, row.status)
    },
    detailstatusformatter(row) {
      return this.descFormatter(this.detailstatusdesc, row.status)
    },
    impstatusCodeNameformatter(row) {
      return this.descFormatter(this.impstatusCodes, row.status)
    },
    receiveformatter(row) {
      if (row.receiveWarehouseCode === null || row.receiveWarehouseCode === '') {
        return ''
      }
      const data = this.warehouseData
      for (const i in data) {
        var item = data[i]
        if (item.warehouseCode === row.receiveWarehouseCode.toString()) {
          return item.warehouseName
        }
      }
      return ''
    },
    updateTimeformatter(row) {
      if (row.updateTime == null) {
        return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
      }
      return this.dayjs(row.updateTime).format('YYYY-MM-DD HH:mm')
    },
    preArriveDateformatter(row) {
      if (row.preArriveDate != null) {
        return this.dayjs(row.preArriveDate).format('YYYY-MM-DD')
      }
    },
    arriveDateformatter(row) {
      if (row.arriveDate != null) {
        return this.dayjs(row.arriveDate).format('YYYY-MM-DD')
      }
    },
    createTimeformatter(row) {
      return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
    },
    ignoreTimeformatter(row) {
      return this.dayjs(row.ignoreTime).format('YYYY-MM-DD HH:mm')
    },
    shipDateformatter(row) {
      if (row.shipDate != null) {
        return this.dayjs(row.shipDate).format('YYYY-MM-DD')
      }
    },
    receiveDateformatter(row) {
      if (row.receiveDate != null) {
        return this.dayjs(row.receiveDate).format('YYYY-MM-DD')
      }
    },
    customsDateformatter(row) {
      if (row.customsDate != null) {
        return this.dayjs(row.customsDate).format('YYYY-MM-DD')
      }
    },
    invoiceDateformatter(row) {
      if (row.invoiceDate != null) {
        return this.dayjs(row.invoiceDate).format('YYYY-MM-DD')
      }
    },
    customsShipdateformatter(row) {
      if (row.customsShipdate != null) {
        return this.dayjs(row.customsShipdate).format('YYYY-MM-DD HH:mm')
      }
    },
    portArrivedateformatter(row) {
      if (row.portArrivedate != null) {
        return this.dayjs(row.portArrivedate).format('YYYY-MM-DD HH:mm')
      }
    },
    dataTypeformatter(row) {
      return this.descFormatter(this.dataTypedes, row.dataType)
    },
    receiveTimeformatter(row) {
      return this.dayjs(row.receiveTime).format('YYYY-MM-DD')
    }
  // updateTimeformatter(row) {
  //   if (row.updateTime == null) {
  //     return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
  //   }
  //   return this.dayjs(row.updateTime).format('YYYY-MM-DD HH:mm')
  // }
  }
}
</script>
