<template>
  <div class="app-container">
    <el-tabs v-model="activeTabName" type="card">
      <el-tab-pane label="未入库发票" name="pane1">
        <div>
          <el-row>
            <el-form
              ref="queryForm"
              :inline="true"
              :model="queryForm"
              size="mini"
              label-width="80px"
            >
              <el-form-item label="发票号">
                <el-input
                  v-model="queryForm.invoiceNo"
                  style="width: 130px"
                  clearable
                  placeholder="加%号模糊查询"
                />
              </el-form-item>
              <el-form-item>
                <el-select
                  v-model="queryForm.status"
                  placeholder=""
                  style="width: 125px"
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
              <!-- <el-form-item label="供应商">
                  <el-input v-model="queryForm.supplierCode" style="width:120px" clearable placeholder="供应商" />
                  <el-button type="primary" size="mini" @click="selectSupplier">···</el-button>
              </el-form-item>-->
              <el-form-item>
                <el-select
                  v-model="queryForm.supplierCode"
                  placeholder="供应商"
                  clearable
                  style="width: 160px"
                  @change="searchData"
                >
                  <el-option
                    v-for="item in supplierCodeOptions"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"
                  />
                </el-select>
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
                <el-button v-permission="['419271']" type="primary" icon="el-icon-search" size="mini" @click="searchData">
                  查询
                </el-button>
              </el-form-item>
              <el-form-item>
                <el-tooltip effect="light" content="更多查询" placement="top">
                  <el-button type="primary" style="margin-bottom:3px" @click="searchMoreData">···</el-button>
                </el-tooltip>
              </el-form-item>
            </el-form>
            <el-form
              v-if="query"
              ref="queryForm"
              :inline="true"
              :model="queryForm"
              size="mini"
              label-width="100px"
            >
              <el-form-item label="发货日期" style="margin-left: 0px">
                <el-date-picker
                  v-model="sendTime"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 250px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                />
              </el-form-item>
              <el-form-item label="预计到货日期">
                <el-date-picker
                  v-model="prearriveDate"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 250px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                />
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
              <el-form-item label="更新时间" style="margin-left: 0px">
                <el-date-picker
                  v-model="updateTime"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 250px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                />
              </el-form-item>
            </el-form>
          </el-row>
          <el-row class="row-button" >
            <el-button v-permission="['336426']" type="primary" size="mini" @click="uploadFileDialogVisible = true">从关务导入</el-button>
            <el-button v-permission="['336426']" type="primary" size="mini" @click="updateMasterNoStorage">无需入库</el-button>
            <el-button v-permission="['336426']" type="primary" size="mini" icon="el-icon-check" @click="addInvoice">新增</el-button>
            <el-button v-permission="['336426']" type="primary" size="mini" @click="confirmPOInvoice">
              <span
                v-loading="confirming"
                element-loading-text="发票入库.."
                element-loading-spinner="el-icon-loading"
              >{{ "发票入库" }}</span>
            </el-button>
            <el-button v-permission="['336426']" type="primary" size="mini" @click="costInvoice">转成本</el-button>
            <el-button v-permission="['336426']" :loading="exportImpInvoiceMasterLoading" type="primary" size="mini" @click="exportImpInvoiceMaster" >导出</el-button>
            <el-label > 合计 <span type="primary"> {{ amountTotal | numberToCurrency }}</span></el-label>
          </el-row>
        </div>
        <div>
          <el-table
            ref="multipleTable"
            :data="listdata"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '13px'}"
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
            <el-table-column fixed type="selection" width="55"/>
            <el-table-column fixed prop="id" label="发票ID" show-overflow-tooltip/>
            <el-table-column fixed prop="invoiceNo" width="100" label="发票号" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.invoiceNo }}</span>
                <el-button
                  size="mini"
                  icon="el-icon-view"
                  type="text"
                  @click="selShowDetail(scope.$index, scope.row)"
                />
              </template>
            </el-table-column>
            <el-table-column
              :formatter="statusCodeNameformatter"
              fixed
              prop="status"
              label="状态"
              show-overflow-tooltip
            />
            <el-table-column prop="supplierCode" fixed label="供应商" show-overflow-tooltip/>
            <el-table-column :formatter="invoiceTypeformatter" prop="invoiceType" fixed label="发票类型" show-overflow-tooltip/>
            <el-table-column
              :formatter="invoiceDateformatter"
              min-width="90"
              prop="invoiceDate"
              label="发票日期"
              show-overflow-tooltip
            />
            <el-table-column
              :formatter="shipDateformatter"
              min-width="90"
              prop="shipDate"
              label="发出日期"
              show-overflow-tooltip
            />
            <el-table-column
              :formatter="preArriveDateformatter"
              min-width="120"
              prop="preArriveDate"
              label="预计到达"
              show-overflow-tooltip
            />
            <el-table-column
              :formatter="portArrivedateformatter"
              min-width="100"
              prop="portArrivedate"
              label="到港时间"
              show-overflow-tooltip
            />
            <!-- <el-table-column :formatter="receiveDateformatter" prop="receiveDate" label="关务发出日期" /> -->
            <el-table-column
              :formatter="customsDateformatter"
              prop="customsDate"
              label="报关日期"
              show-overflow-tooltip
            />
            <!-- <el-table-column
              :formatter="customsShipdateformatter"
              prop="customsShipdate"
              label="海关发出时间"
              show-overflow-tooltip
            /> -->
            <el-table-column
              :formatter="receiveDateformatter"
              min-width="120"
              prop="receiveDate"
              label="到厂日期"
              show-overflow-tooltip
            />
            <el-table-column
              :formatter="arriveDateformatter"
              min-width="100"
              prop="arriveDate"
              label="物流签收时间"
              show-overflow-tooltip
            />
            <el-table-column prop="bargainType" label="交易成交方式" min-width="100" show-overflow-tooltip />
            <el-table-column prop="cinvoiceNo" label="关务发票号" min-width="100" show-overflow-tooltip />
            <!-- <el-table-column :formatter="arrivedformatter" prop="arrivedWarehouseCode" label="到货仓库" /> -->
            <el-table-column
              :formatter="receiveformatter"
              prop="receiveWarehouseCode"
              label="收货仓库"
              show-overflow-tooltip
            />
            <el-table-column prop="amount" label="原发票金额" min-width="100" show-overflow-tooltip />
            <el-table-column prop="exchangeRate" label="汇率" show-overflow-tooltip/>
            <el-table-column prop="amountRmb" label="原发票金额rmb" min-width="120" show-overflow-tooltip />
            <el-table-column prop="transFee" label="运保费" show-overflow-tooltip/>
            <el-table-column prop="customsFee" label="关税" show-overflow-tooltip/>
            <el-table-column prop="exciseTax" label="消费税" show-overflow-tooltip/>
            <el-table-column prop="vatFee" label="增值税" show-overflow-tooltip/>
            <el-table-column prop="currency" label="币种" show-overflow-tooltip/>
            <el-table-column prop="otherFee" label="其他费用" show-overflow-tooltip/>
            <el-table-column prop="orderQty" label="发票订单数" min-width="100" show-overflow-tooltip />
            <el-table-column prop="totalQty" label="总数量" show-overflow-tooltip/>
            <el-table-column prop="boxQty" label="总箱数" show-overflow-tooltip/>
            <el-table-column prop="weight" label="总重量" show-overflow-tooltip/>
            <el-table-column prop="transType" label="运输方式" show-overflow-tooltip/>
            <el-table-column
              :formatter="dataTypeformatter"
              prop="dataType"
              label="数据类型"
              show-overflow-tooltip
            />
            <el-table-column
              :formatter="createTimeformatter"
              min-width="120"
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
            <el-table-column
              :formatter="confirmDateformatter"
              min-width="100"
              prop="confirmDate"
              label="发票入库时间"
              show-overflow-tooltip
            />
            <el-table-column prop="remark" label="备注" show-overflow-tooltip/>
            <el-table-column
              fixed="right"
              label="操作"
              align="center"
              width="140"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <!-- <el-button
                  size="mini"
                  icon="el-icon-view"
                  @click="selShowDetail(scope.$index, scope.row)"
                /> -->
                <el-button
                  v-permission="['336426']"
                  v-if="scope.row.status==10 || scope.row.status==1 || scope.row.status==2"
                  size="mini"
                  icon="el-icon-edit-outline"
                  @click="showEditDetail(scope.$index, scope.row)"
                />
                <!-- <el-button v-if="scope.row.status==1" size="mini" icon="el-icon-edit" @click="dialogPreArriveVisible = true;showEditPreArriveDate(scope.$index, scope.row)" /> -->
                <el-button
                  v-permission="['336426']"
                  v-if="scope.row.status==1 || scope.row.status==10 || scope.row.status==2"
                  size="mini"
                  icon="el-icon-delete"
                  @click="cancelInvoiceData(scope.$index, scope.row)"
                />
                <el-button
                  v-permission="['336426']"
                  v-if="scope.row.status==2"
                  size="mini"
                  icon="el-icon-success"
                  @click="sendImpData(scope.row)"
                />
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

        <!--供应商弹窗-->
        <el-dialog :visible.sync="dialogFormVisible" title="供应商" width="650px">
          <el-form ref="warehouseForm" :inline="true" :model="supplierForm" size="small">
            <el-form-item>
              <el-input
                v-model="supplierForm.companyId"
                placeholder="供应商代码"
                style="width:100px"
                clearable
              />
            </el-form-item>
            <el-form-item>
              <el-input v-model="supplierForm.name" placeholder="供应商名称" clearable/>
            </el-form-item>
            <el-form-item>
              <el-button icon="el-icon-search" size="small" @click="listSupplierinfo"/>
            </el-form-item>
          </el-form>
          <el-table
            :data="supplieData.filter(data => !supplierForm.name || data.name.includes(supplierForm.name))"
          >
            <!-- <el-table-column property="companyId" label="供应商代码" width="100px"/>
            <el-table-column property="name" label="供应商名称" width="200px"/>-->
            <el-table-column prop="companyId" label="供应商代码">
              <template slot-scope="scope">
                <span v-if="scope.row.isEditSupplierShow">
                  <el-input v-model="scope.row.companyId" size="mini" placeholder="供应商代码"/>
                </span>
                <span v-else>{{ scope.row.companyId }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="name" label="供应商名称">
              <template slot-scope="scope">
                <span v-if="scope.row.isEditSupplierShow">
                  <el-input v-model="scope.row.name" size="mini" placeholder="供应商名称"/>
                </span>
                <span v-else>{{ scope.row.name }}</span>
              </template>
            </el-table-column>
            <el-table-column
              :formatter="supplierfomatter"
              property="updateTime"
              label="修改时间"
              width="200px"
            />
            <el-table-column label="操作" align="center" width="150">
              <template slot-scope="scope">
                <el-button
                  v-if="!scope.row.isEditSupplierShow"
                  type="primary"
                  size="mini"
                  @click="updateSupplier(scope.row,scope.$index)"
                >修改
                </el-button>
                <el-button
                  v-if="scope.row.isEditSupplierShow"
                  type="primary"
                  size="mini"
                  @click="saveSupplier(scope.row,scope.$index)"
                >保存
                </el-button>
                <el-button
                  v-if="scope.row.isEditSupplierShow"
                  type="primary"
                  size="mini"
                  @click="cancelSupplier(scope.row,scope.$index)"
                >取消
                </el-button>
                <el-button
                  v-if="!scope.row.isEditSupplierShow"
                  type="primary"
                  size="mini"
                  @click="edit(scope.row)"
                >选择
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>

        <!--登记预计到达-->
        <el-dialog
          :visible.sync="dialogPreArriveVisible"
          :close-on-click-modal="false"
          title="登记预计到达"
          width="420px"
        >
          <el-form ref="arriveForm" :model="arriveForm">
            <el-form-item label="ID">
              <el-input
                v-model="arriveForm.id"
                style="width: 120px"
                disabled
                placeholder="id"
                size="mini"
              />
            </el-form-item>
            <el-form-item label="发票号">
              <el-input
                v-model="arriveForm.invoiceNo"
                disabled
                style="width: 120px"
                placeholder="invoiceNo"
                size="mini"
              />
            </el-form-item>
            <el-form-item label="预计到达日期">
              <el-date-picker
                v-model="arriveForm.preArriveDate"
                type="date"
                size="mini"
                placeholder="登记日期"
                value-format="yyyy-MM-dd"
                style="width: 130px"
              />
            </el-form-item>
            <el-form-item label="关务发出日期">
              <el-date-picker
                v-model="arriveForm.arriveDate"
                type="date"
                size="mini"
                placeholder="登记日期"
                value-format="yyyy-MM-dd"
                style="width: 130px"
              />
            </el-form-item>

            <el-form-item prop="remark" label="备注">
              <el-input
                :rows="4"
                v-model="arriveForm.remark"
                style="width:380px"
                autocomplete="off"
                type="textarea"
              />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button style="float:left" @click="dialogPreArriveVisible = false;">退回</el-button>
            <el-button
              type="primary"
              @click="dialogPreArriveVisible = false;updateImpInvoicePreArriveDate()"
            >登记
            </el-button>
          </div>
        </el-dialog>
        <el-dialog
          :visible.sync="dialogaddinvoice"
          title="新增/修改发票数据"
          width="720px"
          @close="closeDialog"
        >
          <el-form ref="addForm" :model="addForm" :inline="true" label-width="110px" size="mini">
            <el-form-item v-if="false" prop="id">
              <el-input v-model="addForm.id"/>
            </el-form-item>
            <el-form-item :required="true" prop="invoiceNo" label="发票号">
              <el-input :disabled="invoiceNoShow" v-model="addForm.invoiceNo" style="width:200px"/>
            </el-form-item>
            <el-form-item :required="true" prop="supplierCode" label="供应商">
              <el-select
                v-model="addForm.supplierCode"
                placeholder="供应商"
                style="width: 200px"
                @change="setSupplierInfo"
              >
                <el-option
                  v-for="item in supplierCodeOptions"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
            <!-- <el-form-item prop="amount" label="原发票金额">
              <el-input :disabled="invoiceNoShow" v-model="addForm.amount" style="width:200px"/>
            </el-form-item>-->
            <!-- <el-form-item prop="currency" label="币种">
              <el-select v-model="addForm.currency" placeholder="货币种类" style="width: 200px">
                <el-option v-for="item in currencyCodeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
            </el-form-item>
            <el-form-item prop="exchangeRate" label="汇率">
              <el-input v-model="addForm.exchangeRate" style="width:200px"/>
            </el-form-item>-->
            <!-- <el-form-item prop="amountRmb" label="发票金RMB">
              <span slot="label">
                <span class="span-box">
                  <el-tooltip class="item" effect="dark" content="输入汇率自动生成" placement="top-start">
                    <i class="el-icon-warning-outline" />
                  </el-tooltip>
                  <span class="font">发票金RMB</span>
                </span>
              </span>
              <el-input v-model="addForm.amountRmb" style="width:200px"/>
            </el-form-item>-->
            <el-form-item :required="true" prop="shipDate" label="发出日期">
              <el-date-picker
                v-model="addForm.shipDate"
                style="width:200px"
                type="date"
                placeholder="选择日期"
              />
            </el-form-item>
            <el-form-item :required="true" prop="preArriveDate" label="预计到达">
              <el-date-picker
                v-model="addForm.preArriveDate"
                style="width:200px"
                type="date"
                placeholder="选择日期"
              />
            </el-form-item>
            <!-- <el-form-item prop="status" label="状态">
              <el-select v-model="addForm.status" placeholder="状态" style="width: 200px">
                <el-option v-for="item in statusCodes" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
            </el-form-item>-->
            <!-- <el-form-item :required="true" prop="arrivedWarehouseCode" label="到货仓库">
              <el-select v-model="addForm.arrivedWarehouseCode" placeholder="到货仓库" style="width: 140px">
                <el-option v-for="item in arrivedWarehouseData" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
              <el-button type="primary" size="mini" @click="selectWarehouse(selectTyepe=1)">选择</el-button>
            </el-form-item>-->
            <el-form-item :required="true" prop="receiveWarehouseCode" label="收货仓库">
              <el-select
                v-model="addForm.receiveWarehouseCode"
                placeholder="收货仓库"
                style="width: 140px"
              >
                <el-option
                  v-for="item in receiveWarehouseData"
                  :key="item.code"
                  :label="item.codeName"
                  :value="item.code"
                />
              </el-select>
              <el-button type="primary" size="mini" @click="selectWarehouse(selectTyepe=2)">选择</el-button>
            </el-form-item>
            <el-form-item prop="transType" label="运输方式">
              <el-select v-model="addForm.transType" placeholder="运输方式" style="width: 200px">
                <el-option
                  v-for="item in getTransIds"
                  :key="item.code"
                  :label="item.codeName"
                  :value="item.code"
                />
              </el-select>
            </el-form-item>
            <el-form-item prop="customsFee" label="关税">
              <el-input v-model="addForm.customsFee" style="width:200px"/>
            </el-form-item>
            <el-form-item prop="vatFee" label="增值税">
              <el-input v-model="addForm.vatFee" style="width:200px"/>
            </el-form-item>
            <el-form-item prop="otherFee" label="其它费用">
              <el-input v-model="addForm.otherFee" style="width:200px"/>
            </el-form-item>
            <el-form-item prop="remark" label="备注">
              <el-input v-model="addForm.remark" style="width:200px"/>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogaddinvoice = false">取 消</el-button>
            <el-button type="primary" @click="saveInvoice()">保存</el-button>
          </div>
        </el-dialog>
        <!-- 仓库选择弹窗 -->
        <el-dialog :visible.sync="dialogWarehouse" title="仓库" width="650px">
          <el-form ref="warehouseForm" :inline="true" :model="warehouseForm" size="small">
            <el-form-item>
              <el-input
                v-model="warehouseForm.warehouseCode"
                placeholder="仓库代码"
                style="width:100px"
                clearable
                @clear="listWarehouseinfo"
              />
            </el-form-item>
            <el-form-item>
              <el-input
                v-model="warehouseForm.warehouseName"
                placeholder="仓库名称"
                clearable
                @clear="listWarehouseinfo"
              />
            </el-form-item>
            <el-form-item>
              <el-select
                v-model="warehouseForm.warehouseType"
                placeholder="仓库类别"
                style="width:100px"
                clearable
                @change="listWarehouseinfo"
              >
                <el-option
                  v-for="item in warehousetypeList"
                  :key="item.code"
                  :label="item.codeName"
                  :value="item.code"
                  clearable
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button icon="el-icon-search" size="small" @click="listWarehouseinfo"/>
            </el-form-item>
          </el-form>
          <el-table
            :data="warehouseData.filter(data => !warehouseForm.warehouseName || data.warehouseName.includes(warehouseForm.warehouseName))"
          >
            <el-table-column property="warehouseCode" label="仓库代码" width="100px"/>
            <el-table-column property="warehouseName" label="仓库名称" width="300px"/>
            <el-table-column
              :formatter="getWarehouseType"
              property="warehouseType"
              label="仓库类别"
              width="100px"
            />
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button type="primary" size="mini" @click="editWarehouse(scope.row)">选择</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-dialog>
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
            <el-button type="primary" @click="dialogcostinvoice = false;costInvoiceData()">确定</el-button>
          </div>
        </el-dialog>
        <!-- 发票入库选择弹窗 -->
        <!-- <el-dialog :visible.sync="dialogcostinvoice" title="转成本" width="500px" >
          <el-form ref="costForm" :model="costForm" :inline="true" label-width="150px">
            <el-checkbox v-model="costForm.onlyipm" size="mini">只发票入库</el-checkbox>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogcostinvoice = false">取 消</el-button>
            <el-button type="primary" @click="dialogcostinvoice = false;costInvoiceData()">确定</el-button>
          </div>
        </el-dialog>-->
      </el-tab-pane>
      <el-tab-pane label="发票明细" name="pageDetail" type="border-card">
        <el-card>
          <div>
            <span
              class="collapse-title-class"
              style="font-size:13px;"
              @click="baseInfoVisible = !baseInfoVisible"
            >
              <i class="el-icon-tickets" style="color:#008080;"/>
              基础信息
              <i
                :title="baseInfoVisible === false? '展开': '收起'"
                :class="baseInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"
              />
            </span>
          </div>
          <div v-show="baseInfoVisible === true" class="product-base-content-body">
            <el-form label-width="80px" style="width:100%" label-suffix size="mini">
              <!--第一行-->
              <el-row type="flex">
                <el-form-item style="width:260px">
                  <span slot="label">
                    <span class="span-box">
                      <span>发票号</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.invoiceNo }} ({{ impDetaildata.impInvoiceMaster.id }})</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>状态</span>
                    </span>
                  </span>
                  <span>{{ statusFormatter(impDetaildata.impInvoiceMaster.status) }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>供应商</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.supplierCode }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>运输</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.transType }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>发出日期</span>
                    </span>
                  </span>
                  <span>{{ momentformatter(impDetaildata.impInvoiceMaster.shipDate) }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>预计到达</span>
                    </span>
                  </span>
                  <span>{{ momentformatter(impDetaildata.impInvoiceMaster.preArriveDate ) }}</span>
                </el-form-item>
              </el-row>
              <!--第二行-->
              <el-row type="flex">
                <el-form-item style="width:260px">
                  <span slot="label">
                    <span class="span-box">
                      <span>订单项数</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.orderQty }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>总金额</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.amount }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>总数量</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.totalQty }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>发货箱数</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.boxQty }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>收货仓库</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.receiveWarehouseCode }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>导入日期</span>
                    </span>
                  </span>
                  <span>{{ momentformatter(impDetaildata.impInvoiceMaster.createTime) }}</span>
                </el-form-item>
              </el-row>
              <!--第三行-->
              <el-row type="flex">
                <el-form-item style="width:880px">
                  <span slot="label" class="span-box">
                    <span>
                      <span>备注</span>
                    </span>
                  </span>
                  <span>{{ impDetaildata.impInvoiceMaster.remark }}</span>
                </el-form-item>
                <el-form-item v-if="impDetaildata.impInvoiceMaster.status===10" style="margin-left: -80px">
                  <el-button
                    type="primary"
                    icon="el-icon-finished"
                    size="mini"
                    @click="finishImpInvoiceDeatailAdd()"
                  >
                    <span
                      v-loading="finishImping"
                      element-loading-text="完成录入.."
                      element-loading-spinner="el-icon-loading"
                    >{{ "完成录入" }}</span>
                  </el-button>
                </el-form-item>
                <el-form-item v-if="impDetaildata.impInvoiceMaster.status===2" style="margin-left: -80px">
                  <el-button
                    type="primary"
                    icon="el-icon-upload2"
                    size="mini"
                    @click="sendImpData(sendDataInfo)"
                  >
                    <span
                      v-loading="sendImping"
                      element-loading-text="发票入库.."
                      element-loading-spinner="el-icon-loading"
                    >{{ "发票入库" }}</span>
                  </el-button>
                </el-form-item>
              </el-row>
            </el-form>
          </div>
        </el-card>

        <el-dialog :visible.sync="dialogError" title="忽略差异" width="500px">
          <el-form :inline="true" label-width="150px">
            <el-form-item prop="ignoreReason" label="忽略原因">
              <el-input
                v-model="diffErrorForm.ignoreReason"
                :autosize="{ minRows: 2, maxRows: 4}"
                type="textarea"
                placeholder="请输入内容"
                size="small"
                style="width:200px"
              />
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="dialogError = false">取 消</el-button>
            <el-button type="primary" @click="dialogError = false;updateImpInvoiceIgnoreError()">确定</el-button>
          </div>
        </el-dialog>
        <!-- 新增发票明细弹窗 -->
        <el-dialog :visible.sync="dialogaddinvoiceDetail" title="新增发票明细" width="720px">
          <div style="margin-left: 80px;margin-bottom:20px;">
            <el-radio v-model="invRadio" label="1">发票明细</el-radio>
            <el-radio v-model="invRadio" label="2">发票分包</el-radio>
          </div>
          <el-tabs>
            <el-tab-pane label="新增">
              <el-form
                ref="invoiceDetailForm"
                :model="invoiceDetailForm"
                :inline="true"
                label-width="100px"
                size="mini"
                @submit.native.prevent
              >
                <el-form-item prop="invoiceNo" label="发票号">
                  <el-input v-model="invoiceDetailForm.invoiceNo" disabled style="width:200px"/>
                </el-form-item>
                <el-form-item prop="barcode" label="条码">
                  <el-input v-model="invoiceDetailForm.barcode" style="width:200px"/>
                </el-form-item>
                <el-form-item :required="true" prop="fullOrderNo" label="完整订单号">
                  <el-input
                    v-model="invoiceDetailForm.fullOrderNo"
                    style="width:200px"
                    @keyup.enter.native="getopspurchaseInvoice(1)"
                  />
                </el-form-item>
                <el-form-item v-if="invRadio === '2'" label style="width:200px"/>
                <el-form-item v-if="invRadio === '1'" prop="nonCommercial" label="无商业价值">
                  <el-input v-model="invoiceDetailForm.nonCommercial" maxlength="1" style="width:200px"/>
                </el-form-item>
                <el-form-item :required="true" prop="poNo" label="采购单号">
                  <el-input v-model="invoiceDetailForm.poNo" style="width:200px"/>
                </el-form-item>
                <el-form-item :required="true" prop="poItemNo" label="采购项号">
                  <el-input v-model="invoiceDetailForm.poItemNo" style="width:200px" @keyup.enter.native="getopspurchaseInvoice(2)"/>
                </el-form-item>
                <!-- <el-form-item :required="true" prop="orderNo" label="订单号">
                  <el-input v-model="invoiceDetailForm.orderNo" style="width:200px"/>
                </el-form-item>
                <el-form-item :required="true" prop="itemNo" label="项号">
                  <el-input v-model="invoiceDetailForm.itemNo" style="width:200px"/>
                </el-form-item>-->
                <el-form-item :required="true" prop="modelNo" label="型号">
                  <el-input v-model="invoiceDetailForm.modelNo" style="width:200px"/>
                </el-form-item>
                <el-form-item :required="true" prop="quantity" label="数量">
                  <el-input v-model="invoiceDetailForm.quantity" style="width:200px"/>
                </el-form-item>
                <el-form-item :required="true" prop="price" label="价格">
                  <el-input v-model="invoiceDetailForm.price" style="width:200px"/>
                </el-form-item>
                <el-form-item prop="caseNo" label="箱号">
                  <el-input v-model="invoiceDetailForm.caseNo" style="width:200px"/>
                </el-form-item>
                <el-form-item prop="origin" label="原产地">
                  <el-input v-model="invoiceDetailForm.origin" style="width:200px"/>
                </el-form-item>
                <el-form-item prop="weight" label="重量">
                  <el-input v-model="invoiceDetailForm.weight" style="width:200px"/>
                </el-form-item>
                <el-form-item prop="impOrderNo" label="原单号">
                  <el-input v-model="invoiceDetailForm.impOrderNo" style="width:200px"/>
                </el-form-item>
                <el-form-item prop="remark" label="备注">
                  <el-input v-model="invoiceDetailForm.remark" style="width:200px"/>
                </el-form-item>
              </el-form>
              <div class="dialog-footer">
                <el-button
                  size="mini"
                  type="primary"
                  style="float:right"
                  @click="addInvoiceDetail()"
                >保存
                </el-button>
              </div>
            </el-tab-pane>
            <el-tab-pane label="批量新增">
              <el-form ref="detailsForm" :model="detailsForm" size="mini">
                <el-form-item prop="details">
                  <el-input
                    :rows="16"
                    v-model="detailsForm.details"
                    type="textarea"
                    placeholder="请按：完整订单号，数量，单价或excel复制；数量单价可以不输入;多条数据换行"
                    show-word-limit
                  />
                </el-form-item>
              </el-form>
              <div class="dialog-footer">
                <el-button type="primary" size="mini" style="float:right" @click="addDetails">添加</el-button>
              </div>
            </el-tab-pane>
          </el-tabs>
        </el-dialog>
        <el-card>
          <div>
            <span
              class="collapse-title-class"
              style="font-size:13px"
              @click="baseInfoVisible = !baseInfoVisible"
            >
              <i class="el-icon-tickets" style="color:#008080;"/>
              查询条件
              <i
                :title="baseInfoVisible === false? '展开': '收起'"
                :class="'el-icon-d-arrow-down' +' el-icon-class' "
              />
            </span>
          </div>
          <div v-show="baseInfoVisible === true" class="product-base-content-body">
            <el-form
              ref="impDetailForm"
              :inline="true"
              :model="impDetailForm"
              size="mini"
              label-width="100px"
            >
              <el-form-item>
                <el-input
                  v-model="impDetailForm.invoiceId"
                  style="width: 120px"
                  placeholder="发票Id"
                />
              </el-form-item>
              <el-form-item>
                <el-input v-model="impDetailForm.invoiceNo" style="width: 120px" placeholder="发票号"/>
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model="impDetailForm.orderNo"
                  style="width: 130px"
                  clearable
                  placeholder="订单号"
                />
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model="impDetailForm.modelNo"
                  style="width: 120px"
                  clearable
                  placeholder="型号"
                />
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model="impDetailForm.barcode"
                  style="width: 120px"
                  clearable
                  placeholder="条码"
                />
              </el-form-item>
              <el-form-item>
                <el-input
                  v-model="impDetailForm.overseaInvoiceNo"
                  style="width: 120px"
                  clearable
                  placeholder="原发票号"
                />
              </el-form-item>
              <el-form-item prop="errorTypeList">
                <el-select v-model="impDetailForm.errorTypeList" placeholder="请选择错误原因" clearable size="small" multiple>
                  <el-option v-for="item in errorTypeList" :key="item.id" :label="item.name" :value="item.id" />
                </el-select>
              </el-form-item>

              <el-form-item>
                <el-button
                  v-permission="['419271']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="showDetail(selectType=1)"
                >查询
                </el-button>
              </el-form-item>
              <el-form-item>
                <el-button v-permission="['336426']" type="primary" icon="el-icon-plus" size="mini" @click="newDetail()">
                  新增
                </el-button>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
        <!--清单明细tab-->
        <div style="margin-top: 0px">
          <el-tabs v-model="activeDetailTabName" type="card" @tab-click="tabchange">
            <el-tab-pane label="发票明细" name="tabDetail">
              <el-button
                v-permission="['336426']"
                type="primary"
                size="mini"
                style="margin-bottom: 5px;"
                @click="updToInvoicedetailPack"
              >
                <span
                  v-loading="updToing"
                  element-loading-text="复制中..."
                  element-loading-spinner="el-icon-loading"
                >
                  {{
                    updToing ? "复制中..." : "复制到分包数据"
                  }}
                </span>
              </el-button>
              <el-button
                v-permission="['336426']"
                :loading="exportDetailLoading"
                type="primary"
                size="mini"
                @click="exportImpInvoiceDetail"
              >导出
              </el-button>
              <el-table
                ref="multiple"
                :data="impDetaildata.impInvoiceDetail"
                tooltip-effect="dark"
                border
                stripe
                size="mini"
                style="width: 100%"
              >
                <!-- <el-table-column fixed type="selection" width="55" /> -->
                <!-- <el-table-column fixed prop="invoiceId" label="发票id" /> -->
                <el-table-column prop="invoiceNo" label="发票号"/>
                <el-table-column prop="fullOrderNo" width="100" show-overflow-tooltip label="订单号"/>
                <!-- <el-table-column prop="orderNo" label="订单号" >
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.orderNo" size="mini" placeholder="订单号" />
                    </span>
                    <span v-else>{{ scope.row.orderNo }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="itemNo" label="子项" >
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.itemNo" size="mini" placeholder="子项" />
                    </span>
                    <span v-else>{{ scope.row.itemNo }}</span>
                  </template>
                </el-table-column>-->
                <el-table-column prop="modelNo" width="100" show-overflow-tooltip label="型号">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.modelNo" size="mini" placeholder="型号"/>
                    </span>
                    <span v-else>{{ scope.row.modelNo }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="quantity" label="数量">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.quantity" size="mini" placeholder="数量"/>
                    </span>
                    <span v-else>{{ scope.row.quantity }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="价格">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.price" size="mini" placeholder="价格"/>
                    </span>
                    <span v-else>{{ scope.row.price }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="barcode" label="条码">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.barcode" size="mini" placeholder="条码"/>
                    </span>
                    <span v-else>{{ scope.row.barcode }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="caseNo" label="箱号">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.caseNo" size="mini" placeholder="箱号"/>
                    </span>
                    <span v-else>{{ scope.row.caseNo }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="origin" label="原产地">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.origin" size="mini" placeholder="原产地"/>
                    </span>
                    <span v-else>{{ scope.row.origin }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="weight" label="重量">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.weight" size="mini" placeholder="重量"/>
                    </span>
                    <span v-else>{{ scope.row.weight }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="overseaInvoiceNo" label="原发票号"/>
                <el-table-column prop="impOrderNo" width="100" show-overflow-tooltip label="原单号"/>
                <el-table-column prop="impModelNo" width="110" show-overflow-tooltip label="供应商型号"/>
                <el-table-column prop="nonCommercial" label="无商业价值"/>
                <el-table-column prop="remark" label="备注">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditPropertyShow">
                      <el-input v-model="scope.row.remark" size="mini" placeholder="请输入内容"/>
                    </span>
                    <span v-else>{{ scope.row.remark }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="操作" align="center" fixed="right" width="140">
                  <template slot-scope="scope">
                    <el-button
                      v-permission="['336426']"
                      v-if="!scope.row.isEditPropertyShow"
                      size="mini"
                      icon="el-icon-edit"
                      @click="editProperty(scope.row,scope.$index)"
                    />
                    <div v-else>
                      <el-button plain size="small" @click="saveProperty(scope.row,scope.$index)">保存</el-button>
                      <el-button size="small" @click="cancelProperty(scope.row,scope.$index)">取消</el-button>
                    </div>

                    <el-button
                      v-permission="['336426']"
                      v-if="!scope.row.isEditPropertyShow"
                      size="mini"
                      icon="el-icon-delete"
                      @click="delDetail(scope.row,scope.$index)"
                    />
                  </template>
                </el-table-column>
              </el-table>
              <pagination
                v-show="impDetailForm.total > 0"
                :total="impDetailForm.total"
                :page.sync="impDetailForm.pageNum"
                :limit.sync="impDetailForm.pageSize"
                @pagination="showDetail"
              />
            </el-tab-pane>
            <el-tab-pane label="分包明细" name="tabDetailPack">
              <el-button
                v-permission="['336426']"
                :loading="exportPackLoading"
                type="primary"
                size="mini"
                @click="exportImpInvoiceDetailPack"
              >导出
              </el-button>
              <el-table
                ref="multiple"
                :data="impDetaildata.impInvoiceDetailPack"
                tooltip-effect="dark"
                border
                stripe
                size="mini"
                style="width: 100%"
              >
                <el-table-column prop="invoiceId" label="发票id"/>
                <el-table-column prop="invoiceNo" label="发票号"/>
                <el-table-column prop="fullOrderNo" width="100" show-overflow-tooltip label="订单号"/>
                <!-- <el-table-column prop="orderNo" label="订单号" >
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.orderNo" size="mini" placeholder="订单号" />
                    </span>
                    <span v-else>{{ scope.row.orderNo }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="itemNo" label="子项" >
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.itemNo" size="mini" placeholder="子项" />
                    </span>
                    <span v-else>{{ scope.row.itemNo }}</span>
                  </template>
                </el-table-column>-->
                <el-table-column :formatter="detailstatusformatter" prop="status" label="状态"/>
                <el-table-column prop="modelNo" width="120" show-overflow-tooltip label="型号">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.modelNo" size="mini" placeholder="型号"/>
                    </span>
                    <span v-else>{{ scope.row.modelNo }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="quantity" label="数量">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.quantity" size="mini" placeholder="条码"/>
                    </span>
                    <span v-else>{{ scope.row.quantity }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="price" label="价格">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.price" size="mini" placeholder="价格"/>
                    </span>
                    <span v-else>{{ scope.row.price }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="barcode" width="110" show-overflow-tooltip label="条码">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.barcode" size="mini" placeholder="条码"/>
                    </span>
                    <span v-else>{{ scope.row.barcode }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="caseNo" label="箱号">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.caseNo" size="mini" placeholder="箱号"/>
                    </span>
                    <span v-else>{{ scope.row.caseNo }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="origin" label="原产地">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.origin" size="mini" placeholder="原产地"/>
                    </span>
                    <span v-else>{{ scope.row.origin }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="weight" label="重量">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.weight" size="mini" placeholder="重量"/>
                    </span>
                    <span v-else>{{ scope.row.weight }}</span>
                  </template>
                </el-table-column>
                <el-table-column prop="overseaInvoiceNo" label="原发票号"/>
                <el-table-column prop="impOrderNo" width="100" show-overflow-tooltip label="原单号"/>
                <el-table-column prop="roHSCode" label="rohs代码"/>
                <el-table-column prop="remark" label="备注">
                  <template slot-scope="scope">
                    <span v-if="scope.row.isEditDetailPackShow">
                      <el-input v-model="scope.row.remark" size="mini" placeholder="请输入内容"/>
                    </span>
                    <span v-else>{{ scope.row.remark }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="操作" fixed="right" align="center" width="130">
                  <template slot-scope="scope">
                    <el-button
                      v-permission="['336426']"
                      v-if="!scope.row.isEditDetailPackShow"
                      size="mini"
                      icon="el-icon-edit"
                      aria-label="编辑"
                      @click="editDetailPack(scope.row,scope.$index)"
                    />
                    <!-- <div v-else>
                      <el-button plain size="small" @click="saveDetailPack(scope.row,scope.$index)">保存</el-button>
                      <el-button size="small" @click="cancelDetailPack(scope.row,scope.$index)">取消</el-button>
                    </div>-->

                    <el-button
                      v-permission="['336426']"
                      v-if="!scope.row.isEditDetailPackShow"
                      size="mini"
                      icon="el-icon-delete"
                      aria-label="取消"
                      @click="delDetailPack(scope.row,scope.$index)"
                    />
                  </template>
                </el-table-column>
              </el-table>
              <pagination
                v-show="impDetailForm.total > 0"
                :total="impDetailForm.total"
                :page.sync="impDetailForm.pageNum"
                :limit.sync="impDetailForm.pageSize"
                @pagination="showDetailPack"
              />
            </el-tab-pane>
            <el-tab-pane label="发票差异" name="tabDetailDiff">
              <el-button v-permission="['336426']" size="mini" type="primary" @click="checkImpInvoiceError()">检查差异 </el-button>
              <el-button v-permission="['336426']" size="mini" type="primary" @click="showError()">忽略差异</el-button>
              <!--add by WuWeiDong 20221026 bug xx -->
              <el-button v-permission="['336426']" :disabled="addDeletePurchaseDisabled" size="mini" type="primary" @click="UpdateDeleteDetailPack()">
                登记删除订单
              </el-button>
              <el-button v-permission="['336426']" :disabled="addDeletePurchaseDisabled" size="mini" type="primary" @click="UpdateDeleteDetailPackByCancel()">
                取消登记删除
              </el-button>
              <el-button
                v-permission="['336426']"
                :loading="exportDiffLoading"
                type="primary"
                size="mini"
                @click="exportImpInvoiceError"
              >导出
              </el-button>
              <el-table
                ref="InvoiceErrorTable"
                :data="impDetaildata.impInvoiceDetailDiff"
                tooltip-effect="dark"
                border
                size="mini"
                style="width: 100%"
                @selection-change="SelectionDataDiff"
              >
                <el-table-column type="selection" align="center"/>
                <!-- <el-table-column prop="invoiceId" label="发票id"/> -->
                <el-table-column prop="invoiceNo" label="发票号"/>
                <el-table-column prop="orderNo" width="110" show-overflow-tooltip label="发票订单号"/>
                <el-table-column prop="modelNo" width="110" show-overflow-tooltip label="入库型号"/>
                <el-table-column prop="poModelNo" label="采购型号"/>
                <el-table-column prop="qty" label="发票数量"/>
                <el-table-column prop="poQty" label="采购剩余数量"/>
                <el-table-column prop="packQty" label="分包数量"/>
                <!-- <el-table-column prop="amount" label="发票金额" /> -->
                <el-table-column prop="errorText" label="错误原因"/>
                <el-table-column prop="poWarehouseCode" label="原采购仓库"/>
                <el-table-column prop="ignoreError" label="是否忽略差异"/>
                <el-table-column prop="ignorePsn" label="忽略人"/>
                <el-table-column :formatter="ignoreTimeformatter" prop="ignoreTime" show-overflow-tooltip label="忽略时间"/>
                <el-table-column prop="ignoreReason" label="忽略原因"/>
                <el-table-column prop="remark" label="备注"/>
                <el-table-column :formatter="createTimeformatter" prop="createTime" show-overflow-tooltip label="创建时间"/>
              </el-table>
              <pagination
                v-show="diffparams.total > 0"
                :total="diffparams.total"
                :page.sync="diffparams.pageNum"
                :limit.sync="diffparams.pageSize"
                @pagination="showDetailDiff"
              />
            </el-tab-pane>
          </el-tabs>
        </div>
      </el-tab-pane>
      <!-- <el-divider /> -->
      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadFileDialogVisible"
        :before-close="closeClick"
        title="从关务导入"
        width="420px"
      >
        <div>
          <el-form ref="importGW" :inline="true" :model="importGW" size="mini" label-width="100px">
            <el-form-item label="关务发票号">
              <el-input v-model="importGW.invNo" style="width: 150px" clearable placeholder="发票号"/>
            </el-form-item>
            <el-form-item label="开始时间">
              <el-date-picker v-model="importGW.startTime" type="datetime" placeholder="开始时间"/>
            </el-form-item>
            <el-form-item label="结束时间">
              <el-date-picker v-model="importGW.endTime" type="datetime" placeholder="结束时间"/>
            </el-form-item>
          </el-form>
          <span class="dialog-footer">
            <span style="margin-left:80px;margin-bottom:2px;color:red">注：关务发票号和时间范围必须输一个</span>
            <el-button
              type="success"
              style="margin-left:100px;margin-bottom:10px"
              size="mini"
              @click="importInvoiceFromGW()"
            >从关务导入</el-button>
          </span>
        </div>
        <!-- <div class="upload">
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
            :on-success="uploadSuccess"
            class="upload-demo"
            drag
            multiple
          >
            <div class="el-upload__text">支持txt格式</div>
            <div v-if="file !== null" class="el-upload__text">
              {{ file.name }}
            </div>
            <div v-else class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button size="mini" @click="closeClick">关闭</el-button>
          <el-button size="mini" type="success" @click="importData">
            <span :loading="uploadStatus">{{
              uploadStatus ? "上传中..." : "上传文件"
            }}</span>
          </el-button>
        </span>-->
      </el-dialog>
    </el-tabs>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import {
  listImpInvoiceData,
  cancelImpInvoiceDataById,
  listImpInvoiceDetailByInvoiceId,
  listImpInvoiceDetailPackByInvoiceId,
  listImpInvoiceErrorByInvoiceId,
  updateImpInvoicePreArriveDate,
  uploadFile,
  updateImpInvoiceDetail,
  delImpInvoiceDetail,
  ImpInvoiceDetailToImpData,
  addInvoiceMasterData,
  addInvoiceDetailData,
  impInvoiceToCost,
  updateMasterNoStorage,
  updToInvoicedetailPack,
  finishImpInvoiceDeatailAdd,
  checkImpInvoiceError,
  updateImpInvoiceIgnoreError,
  exportImpInvoiceDetail,
  exportImpInvoiceMaster,
  exportImpInvoiceDetailPack,
  UpdateDeleteDetailPack,
  exportImpInvoiceError,
  addDeletePurchase,
  addInvoiceDetailPackData,
  delImpInvoiceDetailPack,
  updateImpInvoiceDetailPack,
  impImportInvoiceInfo,
  getopspurchaseInvoice,
  getImpInvoiceAmountTotal,
  confirmPOInvoices
} from '@/api/invoicedata'
import { getDictDataByPid, listWarehouse } from '@/api/common/dict'
import { findSupplierByIdOrName, updateSupplierData } from '@/api/common/supplier'
// import { getSuppily } from '@/api/intercept'
import { findSupplier } from '@/api/warehouseManage'
import { getErrorType } from '@/api/po/invoiceError'
import { getTransIds } from '@/api/purchaseOrder'

export default {
  name: 'ImportInvoiceData',
  components: { Pagination },
  data() {
    return {
      errorTypeList: [],
      activeTabName: 'pane1',
      invRadio: '1',
      activeDetailTabName: 'tabDetail',
      listLoading: true,
      dialogaddinvoice: false,
      dialogaddinvoiceDetail: false,
      dialogcostinvoice: false,
      invoiceNoShow: false,
      updToing: false,
      confirming: false,
      sendImping: false,
      finishImping: false,
      query: false,
      sendData: false,
      isEdit: false,
      dialogFormVisible: false,
      dialogWarehouse: false,
      dialogError: false,
      exportDiffLoading: false,
      exportDetailLoading: false,
      exportPackLoading: false,
      addDeletePurchaseDisabled: false,
      UpdateDeleteDetailPackDisabled: false,
      UpdateDeleteDetailPackByCancelDisabled: false,
      selectType: 0,
      supplierForm: {
        companyId: '',
        name: ''
      },
      supplieData: [],
      warehouseData: [],
      warehouseForm: {},
      selectTyepe: '',
      warehousetypeList: [],
      arrivedWarehouseData: [],
      receiveWarehouseData: [],
      sendDataInfo: {},
      supplierInfo: {},
      errorForm: {},
      actionUrl: '',
      sendTime: '',
      prearriveDate: '',
      updateTime: '',
      invoiceDate: '',
      listdata: [],
      implistdata: [],
      amountTotal: 0,
      importGW: {
        plantMark: 'AM',
        invNo: '',
        startTime: '',
        endTime: ''
      },
      addForm: {
        id: '',
        amount: '',
        currency: '',
        exchangeRate: '',
        amountRmb: '',
        invoiceNo: '',
        supplierCode: '',
        shipDate: '',
        preArriveDate: '',
        status: '10',
        customsFee: '',
        vatFee: '',
        transType: '0',
        otherFee: '',
        remark: '',
        arrivedWarehouseCode: '',
        receiveWarehouseCode: ''
      },
      costForm: {
        costDate: '',
        ids: []
      },
      invoiceDetailForm: {
        modelNo: '',
        poNo: '',
        poItemNo: '',
        orderNo: '',
        itemNo: '',
        splitItemNo: '',
        quantity: '',
        price: '',
        caseNo: '',
        origin: '',
        weight: '',
        impOrderNo: '',
        remark: '',
        nonCommercial: ''
      },
      supplierCodeOptions: [],
      shipMethodOptions: [],
      getTransIds: [],
      currencyCodeOptions: [],
      diffErrorForm: {
        ids: [],
        invoiceId: '',
        ignoreReason: ''
      },
      queryForm: {
        invoiceNo: '',
        status: '1',
        supplierCode: '',
        invoiceType: '',
        qryDateType: '',
        fromDate: '',
        toDate: '',
        pageNum: 0,
        pageSize: 20,
        total: 0,
        isEditable: false
      },
      arriveForm: {
        id: '',
        arriveFlag: '',
        invoiceNo: '',
        arriveDate: '',
        preArriveDate: '',
        status: '',
        remark: ''
      },
      impDetaildata: {
        impInvoiceMaster: '',
        impInvoiceDetail: [],
        impInvoiceDetailPack: [],
        impInvoiceDetailDiff: []
      },
      impDetailForm: {
        invoiceId: '',
        invoiceNo: '',
        orderNo: '',
        modelNo: '',
        barcode: '',
        overseaInvoiceNo: '',
        errorTypeList: [],
        pageNum: 0,
        pageSize: 10,
        total: 0
      },
      packDetail: {
        invoiceId: 0,
        orderNo: ''
      },
      packDetails: [],
      diffparams: {
        invoiceId: '',
        invoiceNo: '',
        orderNo: '',
        ignoreReason: '',
        errorTypeList: [],
        pageNum: 0,
        pageSize: 10,
        total: 0
      },
      detailsForm: {
        details: ''
      },
      uploadFileDialogVisible: false,
      dialogPreArriveVisible: false,
      baseInfoVisible: true,
      uploadStatus: false,
      file: null,
      statusCodes: [],
      dataTypedes: [],
      statusOptions: [],
      // 发票状态
      statusCodeClassCode: '2040',
      supplierCodeClassCode: '2002',
      currencyClassCode: '4303',
      warehouseType: '4001',
      multipleSelection: [],
      invoiceTypedesc: [],
      datadiffSelection: [],
      poInvoice: {
        invoiceId: 0,
        invoiceNo: '',
        fullOrderNo: ''
      },
      poInvoiceLists: [],
      exportImpInvoiceMasterLoading: false
      //   multipleDetailSelection: []
    }
  },
  created() {
    this.initStatusCodedesc()
    this.initdataTypedesdesc()
    // this.initSupplierCodedesc()
    this.findSupplierData()
    this.initshipMethodCodedesc()
    this.initWarehouse()
    this.initcurrencyClassCodedesc()
    this.searchData()
    this.listWarehouseinfo()
    this.getErrorType()
  },
  methods: {
    // 获取错误类型列表
    getErrorType() {
      getErrorType()
        .then((res) => {
          this.errorTypeList = res.content
        })
        .catch((error) => {
          console.log(error)
        })
    },
    initStatusCodedesc() {
      getDictDataByPid(this.statusCodeClassCode).then(result => {
        this.statusCodes = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    initdataTypedesdesc() {
      getDictDataByPid('2044').then(result => {
        this.dataTypedes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2045').then(result => {
        this.statusOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2064').then(result => {
        this.invoiceTypedesc = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    SelectionDataDiff(val) {
      this.datadiffSelection = val
      console.info('SelectionDataDiff')
      console.info(this.datadiffSelection)
    },
    // initSupplierCodedesc() {
    //   getDictDataByPid(this.supplierCodeClassCode).then(result => {
    //     this.supplierCodeOptions = result.content
    //   }).catch(error => {
    //     console.log(error)
    //   })
    // },
    initshipMethodCodedesc() {
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
            this.shipMethodOptions.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    initcurrencyClassCodedesc() {
      getDictDataByPid(this.currencyClassCode).then(result => {
        this.currencyCodeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    initWarehouse() {
      getDictDataByPid(this.warehouseType).then(result => {
        console.log(result)
        this.warehousetypeList = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    getopspurchaseInvoice(type) {
      var poNo = ''
      var poItemNo = ''
      if (type === 1) {
        if (this.invoiceDetailForm.fullOrderNo === '') {
          this.$message.error('订单完整好不能为空')
          return
        }
        poNo = this.invoiceDetailForm.fullOrderNo
      } else {
        if (this.invoiceDetailForm.poNo === '' || this.invoiceDetailForm.poItemNo === '') {
          this.$message.error('采购号和采购项号不能为空')
          return
        }
        poNo = this.invoiceDetailForm.poNo
        poItemNo = this.invoiceDetailForm.poItemNo
      }
      getopspurchaseInvoice(poNo, poItemNo).then(result => {
        if (result.success && result.content !== null) {
          this.invoiceDetailForm.modelNo = result.content.modelNo
          //   this.invoiceDetailForm.orderNo = result.content.orderNo
          //   this.invoiceDetailForm.itemNo = result.content.itemNo
          if (type === 1) {
            this.invoiceDetailForm.poNo = result.content.poNo
            this.invoiceDetailForm.poItemNo = result.content.lineItem
          } else {
            this.invoiceDetailForm.fullOrderNo = result.content.fullOrderNo
          }
          this.invoiceDetailForm.quantity = result.content.quantity
          //   this.invoiceDetailForm.price = result.content.stdPrice
          this.invoiceDetailForm.caseNo = result.content.caseNo
          this.invoiceDetailForm.origin = result.content.supplierId
          this.dialogaddinvoiceDetail = false
          this.dialogaddinvoiceDetail = true
        }
      }).catch(error => {
        console.log(error)
      })
    },
    clearInvoiceDetail() {
      this.invoiceDetailForm.modelNo = ''
      this.invoiceDetailForm.poNo = ''
      this.invoiceDetailForm.poItemNo = ''
      this.invoiceDetailForm.orderNo = ''
      this.invoiceDetailForm.itemNo = ''
      this.invoiceDetailForm.splitItemNo = ''
      this.invoiceDetailForm.quantity = ''
      this.invoiceDetailForm.price = ''
      this.invoiceDetailForm.caseNo = ''
      this.invoiceDetailForm.origin = ''
      this.invoiceDetailForm.weight = ''
      this.invoiceDetailForm.impOrderNo = ''
      this.invoiceDetailForm.remark = ''
      this.dialogaddinvoiceDetail = false
      this.dialogaddinvoiceDetail = true
    },
    tabchange() {
      if (this.activeDetailTabName === 'tabDetailPack') {
        this.showDetailPack()
      }
      if (this.activeDetailTabName === 'tabDetail') {
        this.showDetail()
      }
      if (this.activeDetailTabName === 'tabDetailDiff') {
        this.showDetailDiff()
      }
    },
    setDateCondition() {
      if (this.sendTime === null) {
        this.queryForm.sendTimeStart = ''
        this.queryForm.sendTimeEnd = ''
      } else {
        this.queryForm.sendTimeStart = this.sendTime[0]
        this.queryForm.sendTimeEnd = this.sendTime[1]
      }
      if (this.prearriveDate === null) {
        this.queryForm.prearriveDateStart = ''
        this.queryForm.prearriveDateEnd = ''
      } else {
        this.queryForm.prearriveDateStart = this.prearriveDate[0]
        this.queryForm.prearriveDateEnd = this.prearriveDate[1]
      }
      if (this.updateTime === null) {
        this.queryForm.updateTimeStart = ''
        this.queryForm.updateTimeEnd = ''
      } else {
        this.queryForm.updateTimeStart = this.updateTime[0]
        this.queryForm.updateTimeEnd = this.updateTime[1]
      }
      if (this.invoiceDate === null) {
        this.queryForm.invoiceDateStart = ''
        this.queryForm.invoiceDateEnd = ''
      } else {
        this.queryForm.invoiceDateStart = this.invoiceDate[0]
        this.queryForm.invoiceDateEnd = this.invoiceDate[1]
      }
    },
    searchData() {
      this.listLoading = true
      this.setDateCondition()
      listImpInvoiceData(this.queryForm).then(result => {
        if (result.success) {
          this.listdata = result.content.list
          this.queryForm.total = result.content.total
          this.getImpInvoiceAmountTotal()
        }
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    getImpInvoiceAmountTotal() {
      getImpInvoiceAmountTotal(this.queryForm).then(result => {
        if (result.success && result.content != null) {
          this.amountTotal = result.content
        }
      }).catch(error => {
        console.log(error)
      })
    },
    findSupplierData() {
      findSupplier().then(res => {
        this.supplierCodeOptions = res.content
      })
    },
    cancelInvoiceData(index, row) {
      if (row.id) {
        // 1.如果detailId不为空,则先调用后台接口执行删除
        const params = { 'invoiceId': row.id }
        cancelImpInvoiceDataById(params).then(res => {
          if (!res.success) {
            this.$message.error(res.message)
            return
          }
          // 2.删除前端数组数据项
          // this.listdata.splice(index, 1)
          this.listdata[index].status = 9
          this.$message.success('取消成功')
        }).catch(error => {
          console.info(error)
          this.$message.error(error.message)
        })
      }
    },
    showEditPreArriveDate(index, row) {
      if (row.id) {
        this.arriveForm.id = row.id
        this.arriveForm.invoiceNo = row.invoiceNo
        this.arriveForm.arriveFlag = 1
        this.arriveForm.preArriveDate = this.momentformatter(row.preArriveDate)
        this.arriveForm.arriveDate = this.momentformatter(row.arriveDate)
        this.arriveForm.status = row.status
        this.arriveForm.remark = row.remark
      }
    },
    selectSupplier() {
      this.dialogFormVisible = true
      this.listSupplierinfo()
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
    edit(row) {
      this.queryForm.supplierCode = row.companyId
      this.dialogFormVisible = false
    },
    updateSupplier(row, index) {
      this.$set(this.supplieData[index], 'isEditSupplierShow', true)
    },
    saveSupplier(row, index) {
      updateSupplierData(this.supplieData[index]).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        this.$set(this.supplieData[index], 'isEditSupplierShow', false)
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    cancelSupplier(row, index) {
      // row.companyId = this.supplieData[index].companyId
      this.$set(this.supplieData[index], 'isEditSupplierShow', false)
    },
    selectWarehouse() {
      this.dialogWarehouse = true
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: this.warehouseForm.warehouseType,
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    editWarehouse(row) {
      if (this.selectTyepe === 1) {
        this.addForm.arrivedWarehouseCode = row.warehouseCode
        this.arrivedWarehouseData = []
        const list = { code: row.warehouseCode, codeName: row.warehouseName }
        this.arrivedWarehouseData.push(list)
        this.dialogWarehouse = false
      }
      if (this.selectTyepe === 2) {
        this.addForm.receiveWarehouseCode = row.warehouseCode
        this.receiveWarehouseData = []
        const list = { code: row.warehouseCode, codeName: row.warehouseName }
        this.receiveWarehouseData.push(list)
        this.dialogWarehouse = false
      }
    },
    getArrivedWarehouse(val) {
      if (val === '' || val == null) {
        this.arrivedWarehouseData = []
        return
      }
      const stu = val + ''
      const obj = this.warehouseData.filter(item => item.warehouseCode === stu)[0]
      this.arrivedWarehouseData = []
      const list = { code: obj.warehouseCode, codeName: obj.warehouseName }
      this.arrivedWarehouseData.push(list)
    },
    getReceiveWarehouse(val) {
      if (val === '' || val == null) {
        this.receiveWarehouseData = []
        return
      }
      const stu = val + ''
      const obj = this.warehouseData.filter(item => item.warehouseCode === stu)[0]
      this.receiveWarehouseData = []
      const list = { code: obj.warehouseCode, codeName: obj.warehouseName }
      this.receiveWarehouseData.push(list)
    },
    selShowDetail(index, row) {
      if (row.id) {
        this.impDetailForm.invoiceId = row.id
        this.impDetailForm.invoiceNo = row.invoiceNo
        this.impDetaildata.impInvoiceMaster = row
        this.showDetail()
        this.showDetailPack()
        this.showDetailDiff()
      }
      this.isEdit = false
      this.sendData = false
      if (row.status === 2) {
        this.sendData = true
        this.sendDataInfo = row
      } else if (row.status === 10) {
        this.isEdit = true
      }
    },
    newDetail() {
      this.dialogaddinvoiceDetail = true
      this.invoiceDetailForm = this.$options.data().invoiceDetailForm
      this.invoiceDetailForm.invoiceNo = this.impDetaildata.impInvoiceMaster.invoiceNo
    },
    showDetail() {
      // if (!this.impDetailForm.invoiceId) {
      //   this.$message.error('请先选择发票单号')
      //   return
      // }
      this.listLoading = true
      if (this.selectType === 1) {
        this.showDetailPack()
        this.showDetailDiff()
      }
      this.selectType = 0
      listImpInvoiceDetailByInvoiceId(this.impDetailForm).then(result => {
        this.activeTabName = 'pageDetail'
        this.impDetaildata.impInvoiceDetail = result.content.list
        this.impDetailForm.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    showDetailPack() {
      // if (!this.impDetailForm.invoiceId) {
      //   this.$message.error('请先选择发票单号')
      //   return
      // }
      this.listLoading = true
      listImpInvoiceDetailPackByInvoiceId(this.impDetailForm).then(result => {
        this.activeTabName = 'pageDetail'
        this.impDetaildata.impInvoiceDetailPack = result.content.list
        this.impDetailForm.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 发票数据对比
    showDetailDiff() {
      // if (!this.impDetailForm.invoiceId) {
      //   this.$message.error('请先选择发票单号')
      //   return
      // }

      this.diffparams.invoiceId = this.impDetailForm.invoiceId
      this.diffparams.orderNo = this.impDetailForm.orderNo
      this.diffparams.errorTypeList = this.impDetailForm.errorTypeList

      //   const diffparams = {
      //     invoiceId: this.impDetailForm.invoiceId,
      //     pageNum: 1,
      //     pageSize: 100,
      //     total: 0
      //   }
      this.listLoading = true
      listImpInvoiceErrorByInvoiceId(this.diffparams).then(result => {
        this.activeTabName = 'pageDetail'
        this.impDetaildata.impInvoiceDetailDiff = result.content.list
        this.diffparams.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    supperFormatter(data, code) {
      if (data === null) {
        return null
      }
      for (const i in data) {
        var item = data[i]
        if (item.id === (code === null ? '' : code.toString())) {
          return item
        }
      }
      return null
    },
    setSupplierInfo() {
      this.supplierInfo = this.supperFormatter(this.supplierCodeOptions, this.addForm.supplierCode)
      if (this.supplierInfo === null) {
        return
      }
      this.addForm.currency = this.supplierInfo.transcurrency
      this.addForm.exchangeRate = this.supplierInfo.exchangeRate
      if (this.supplierInfo.countryCode === 'CN') {
        this.addForm.transType = '3'
      }
      this.getTransIds = []
      this.addForm.transType = ''
      var transParam = {}
      transParam.supplierId = this.addForm.supplierCode
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      getTransIds(transParam).then(res => {
        this.editloading = false
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.getTransIds.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          this.getTransIds = this.shipMethodOptions
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    // 新增发票数据
    saveInvoice() {
      if (this.addForm.invoiceNo === '' || this.addForm.supplierCode === '' || this.addForm.shipDate === '' || this.addForm.preArriveDate === '' || this.addForm.receiveWarehouseCode === '') {
        this.$message.error('带*号的不能为空！')
        return
      }
      if (this.addForm.supplierCode === 'JP') {
        this.addForm.transType = ''
      }
      addInvoiceMasterData(this.addForm).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.queryForm.invoiceNo = this.addForm.invoiceNo
          listImpInvoiceData(this.queryForm).then(result => {
            if (result.success && result.content.list.length > 0) {
              this.listdata = result.content.list
              this.queryForm.total = result.content.total
              this.impDetailForm.invoiceId = this.listdata[0].id
              this.impDetailForm.invoiceNo = this.listdata[0].invoiceNo
              this.impDetaildata.impInvoiceMaster = this.listdata[0]
              this.showDetail()
              this.invoiceDetailForm = this.$options.data().invoiceDetailForm
              this.invoiceDetailForm.invoiceNo = this.addForm.invoiceNo
              this.dialogaddinvoice = false
              this.dialogaddinvoiceDetail = true
            }
          }).catch(error => {
            console.log(error)
          })
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      //   this.$nextTick(() => {
      //     this.searchData()
      //   })
    },
    addInvoice() {
      // this.$nextTick(() => {
      //   this.$refs.addForm.$options.resetFields()
      // })
      this.getTransIds = []
      this.addForm.transType = ''
      var transParam = {}
      transParam.supplierId = this.addForm.supplierCode
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      getTransIds(transParam).then(res => {
        this.editloading = false
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.getTransIds.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          this.getTransIds = this.shipMethodOptions
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      this.addForm.shipDate = ''
      this.addForm.preArriveDate = ''
      this.invRadio = '1'
      this.addForm = this.$options.data().addForm
      this.dialogaddinvoice = true
      this.invoiceNoShow = false
    },
    checkImpInvoiceError() {
      checkImpInvoiceError(this.diffparams.invoiceId).then(res => {
        if (res.success) {
          this.$message.success('检查到差异数据' + res.content + '条')
        } else {
          this.$message.error(res.message)
        }
        this.showDetailDiff()
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    updateImpInvoiceIgnoreError() {
      this.diffErrorForm.invoiceId = this.diffparams.invoiceId
      updateImpInvoiceIgnoreError(this.diffErrorForm).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
      this.showDetailDiff()
    },
    // 修改发票数据
    showEditDetail(index, row) {
      this.dialogaddinvoice = true
      this.getArrivedWarehouse(row.arrivedWarehouseCode)
      this.getReceiveWarehouse(row.receiveWarehouseCode)
      this.invoiceNoShow = true
      this.addForm = row
      this.addForm.transType = row.transType.trim()
      this.addForm.status = row.status.toString()
      this.getTransIds = []
      var transParam = {}
      transParam.supplierId = this.addForm.supplierCode
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      getTransIds(transParam).then(res => {
        this.editloading = false
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.getTransIds.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          this.getTransIds = this.shipMethodOptions
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    addDeletePurchase() { // 登记删除订单(dong)
      if (this.impDetailForm.invoiceId === null || this.impDetailForm.invoiceId === '') {
        this.$message.error('请输入发票号')
        return
      }
      const parm = {
        invoiceNo: this.impDetailForm.invoiceNo,
        invoiceId: this.impDetailForm.invoiceId
      }
      // <!--add by WuWeiDong 20221026 bug xx -->
      this.addDeletePurchaseDisabled = true
      addDeletePurchase(parm).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          this.addDeletePurchaseDisabled = false
          return
        }
        this.$message.success('登记成功！')
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
        this.addDeletePurchaseDisabled = false
      })
    },
    showError() {
      this.dialogError = true
      this.diffErrorForm.ids = []
      const list = this.$refs.InvoiceErrorTable.selection
      for (let i = 0; i < list.length; i++) {
        this.diffErrorForm.ids.push(list[i].id)
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
    // <!--add by WuWeiDong 20240304 bug 12279 更新[无需入库]-->
    updateMasterNoStorage() {
      if (this.multipleSelection.length === 0) {
        this.$message.error({
          title: '错误',
          message: '请选择数据。'
        })
        return
      }
      this.$confirm('确认更为【无需入库】状态?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const newArr = []
        this.multipleSelection.forEach((item, index) => {
          newArr.push(item.id)
        })
        const data = { 'ids': newArr.join(',') }
        updateMasterNoStorage(data).then(res => {
          if (res.success) {
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
        }).catch(err => {
          this.$message.error(err)
        })
      })
    },
    CheckRMB(row) {
      if (this.addForm.exchangeRate === '') {
        return
      } else {
        let m = 0
        const s1 = this.addForm.amount.toString()
        const s2 = this.addForm.exchangeRate.toString()
        // 获取所有参数小数位长度之和
        try {
          m += s1.split('.')[1].length
        } catch (e) {
          console.log(e)
        }
        try {
          m += s2.split('.')[1].length
        } catch (e) {
          console.log(e)
        }
        // 替换掉小数点转为数字相乘再除以10的次幂值
        const amount = Number(s1.replace('.', '')) * Number(s2.replace('.', '')) / Math.pow(10, m)
        this.addForm.amountRmb = amount.toFixed(2)
      }
    },
    searchMoreData() {
      this.query = !this.query
    },
    updToInvoicedetailPack() {
      //   if (this.multipleDetailSelection.length === 0) {
      //     this.$message({
      //       type: 'warning',
      //       message: '请选中明细数据'
      //     })
      //     return
      //   }
      //   this.packDetails = []
      //   this.multipleDetailSelection.forEach((item, index) => {
      //     this.packDetail.invoiceId = item.invoiceId
      //     this.packDetail.orderNo = item.orderNo
      //     this.packDetails.push(this.packDetail)
      //   })
      this.updToing = true
      if (!this.impDetailForm.invoiceId) {
        this.$message.error('请先选择发票单号')
        this.updToing = false
        return
      }
      const params = {
        invoiceId: this.impDetailForm.invoiceId
      }
      updToInvoicedetailPack(params).then(res => {
        if (res.success) {
          this.$message.success(res.message)
          this.updToing = false
          this.showDetailPack()
          this.showDetailDiff()
        } else {
          this.$message.error(res.message)
          this.updToing = false
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
        this.updToing = false
      })
    },
    costInvoice() {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择需要转成本的数据')
        return
      }
      this.dialogcostinvoice = true
      this.costForm.ids = []
      for (var i = 0; i < this.$refs.multipleTable.selection.length; i++) {
        this.costForm.ids.push(this.$refs.multipleTable.selection[i].id)
      }
      // console.log(this.$refs.multipleTable.selection)
    },
    confirmPOInvoice() {
      if (this.confirming) {
        this.$message.warning('不要重复点击！')
      }
      this.confirming = true
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择需要发票入库的数据')
        this.confirming = false
        return
      }
      //   const params = this.$refs.multipleTable.selection
      const ids = []
      for (var i = 0; i < this.$refs.multipleTable.selection.length; i++) {
        ids.push(this.$refs.multipleTable.selection[i].id)
      }
      confirmPOInvoices(ids).then(res => {
        if (!res.success) {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
          this.confirming = false
          return
        } else {
          this.smcInfoMsg(res.message)
          this.confirming = false
          this.searchData()
        }
      }).catch(error => {
        console.info(error)
        this.$message({
          dangerouslyUseHTMLString: true,
          showClose: true,
          message: error.message,
          type: 'error',
          duration: 0
        })
        this.confirming = false
        return
      })
    },
    closeDialog() {
      this.searchData()
    },
    addInvoiceDetail() {
      this.invoiceDetailForm.invoiceId = this.impDetailForm.invoiceId
      if (this.invoiceDetailForm.invoiceId == null || this.invoiceDetailForm.invoiceId === '') {
        // this.$message.error('请先选择发票单号')
        return
      }
      if (this.invoiceDetailForm.poNo === '' || this.invoiceDetailForm.poNo === undefined || this.invoiceDetailForm.poItemNo === '' || this.invoiceDetailForm.poItemNo === undefined ||
        this.invoiceDetailForm.fullOrderNo === '' || this.invoiceDetailForm.fullOrderNo === undefined ||
        this.invoiceDetailForm.modelNo === '' || this.invoiceDetailForm.modelNo === undefined || this.invoiceDetailForm.quantity === '' || this.invoiceDetailForm.quantity === undefined ||
        this.invoiceDetailForm.price === '' || this.invoiceDetailForm.price === undefined) {
        this.$message.error('带*号的不能为空！')
        return
      }
      const orders = this.invoiceDetailForm.fullOrderNo.split('-')
      if (orders.length === 2) {
        this.invoiceDetailForm.orderNo = orders[0]
        this.invoiceDetailForm.itemNo = orders[1]
        this.invoiceDetailForm.splitItemNo = null
      } else if (orders.length > 2) {
        this.invoiceDetailForm.orderNo = orders[0]
        this.invoiceDetailForm.itemNo = orders[1]
        this.invoiceDetailForm.splitItemNo = orders[2]
      } else {
        this.invoiceDetailForm.orderNo = this.invoiceDetailForm.fullOrderNo
      }
      console.log('this.invoiceDetailForm=>', this.invoiceDetailForm)
      if (this.invRadio === '1') {
        // console.log(this.invoiceDetailForm.id)
        if (this.invoiceDetailForm.id === undefined) {
          addInvoiceDetailData(this.invoiceDetailForm).then(res => {
            if (res.success) {
              this.$message.success(res.content)
              this.showDetail()
            } else {
              this.$message.error(res.message)
            }
          }).catch(error => {
            console.info(error)
            this.$message.error(error.message)
          })
        } else {
          updateImpInvoiceDetail(this.invoiceDetailForm).then(res => {
            if (!res.success) {
              this.$message.error(res.message)
              this.showDetail()
              return
            }
            this.$message.success('保存成功')
          }).catch(error => {
            console.info(error)
            this.$message.error(error.message)
          })
        }
      } else {
        if (this.invoiceDetailForm.id === undefined) {
          addInvoiceDetailPackData(this.invoiceDetailForm).then(res => {
            if (res.success) {
              this.$message.success(res.content)
              this.showDetailPack()
            } else {
              this.$message.error(res.message)
            }
          }).catch(error => {
            console.info(error)
            this.$message.error(error.message)
          })
        } else {
          updateImpInvoiceDetailPack(this.invoiceDetailForm).then(res => {
            if (!res.success) {
              this.$message.error(res.message)
              this.showDetailPack()
              return
            }
            this.$message.success('保存成功')
          }).catch(error => {
            console.info(error)
            this.$message.error(error.message)
          })
        }
      }
    },
    addDetails() {
      this.invoiceDetailForm.invoiceId = this.impDetailForm.invoiceId
      if (this.invoiceDetailForm.invoiceId == null || this.invoiceDetailForm.invoiceId === '') {
        return
      }
      let details = this.detailsForm.details
      let counts = 0
      details = (details.substring(details.length - 1) === '\n') ? details.substring(0, details.length - 1) : details
      const infos = details.split('\n')
      infos.forEach(el => {
        el.replace('\t', ',')
        const info = el.split(',')
        const item = {
          invoiceId: this.impDetailForm.invoiceId,
          invoiceNo: this.impDetailForm.invoiceNo,
          fullOrderNo: info[0],
          orderNo: '',
          itemNo: '',
          poNo: '',
          poItemNo: '',
          modelNo: '',
          quantity: 0,
          price: 0,
          caseNo: ''
        }
        const splitinfos = item.fullOrderNo.split('-')
        if (splitinfos.length === 2) {
          item.orderNo = splitinfos[0]
          item.itemNo = splitinfos[1]
          item.poNo = splitinfos[0]
          item.poItemNo = splitinfos[1]
        } else if (splitinfos.length > 2) {
          item.orderNo = splitinfos[0]
          item.itemNo = splitinfos[1]
          item.splitItemNo = splitinfos[2]
          item.poNo = splitinfos[0]
          item.poItemNo = splitinfos[1]
        } else {
          item.orderNo = item.fullOrderNo
          //   item.itemNo = 1
          item.poNo = item.fullOrderNo
          //   item.poItemNo = 1
        }
        getopspurchaseInvoice(item.fullOrderNo).then(result => {
          if (result.success && result.content !== null) {
            item.modelNo = result.content.modelNo
            // item.orderNo = result.content.orderNo
            // item.itemNo = result.content.itemNo
            item.poNo = result.content.poNo
            item.poItemNo = result.content.lineItem
            item.quantity = result.content.quantity
            item.price = result.content.stdPrice
            item.caseNo = result.content.caseNo
          } else {
            const splitinfos = item.fullOrderNo.split('-')
            if (splitinfos.length > 1) {
              item.orderNo = splitinfos[0]
              item.itemNo = splitinfos[1]
              item.poNo = splitinfos[0]
              item.poItemNo = splitinfos[1]
            } else {
              item.orderNo = item.fullOrderNo
              item.itemNo = 1
              item.poNo = item.fullOrderNo
              item.poItemNo = 1
            }
          }
          if (info.length === 2) {
            item.quantity = info[1]
          } else if (info.length > 2) {
            item.quantity = info[1]
            item.price = info[2]
          }
          if (this.invRadio === '1') {
            addInvoiceDetailData(item).then(result => {
              if (result.success) {
                counts = counts + 1
              } else {
                this.$message.error(result.message)
                return
              }
            }).catch(error => {
              console.log(error)
            })
          } else {
            addInvoiceDetailPackData(item).then(result => {
              if (result.success) {
                counts = counts + 1
              } else {
                this.$message.error(result.message)
                return
              }
            }).catch(error => {
              console.log(error)
            })
          }
        }).catch(error => {
          console.log(error)
        })
      })
      this.showDetail()
      this.showDetailPack()
      this.$message.success('批量添加成功！')
    },
    updateImpInvoicePreArriveDate() {
      updateImpInvoicePreArriveDate(this.arriveForm).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        // 查询
        this.searchData()
        this.$message.success('登记时间成功')
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    uploadSuccess(response, file, fileList) {
      // console.log(response)
      // console.log(file)
      // console.log(fileList)
    },
    exportImpInvoiceMaster() {
      this.exportImpInvoiceMasterLoading = true
      this.setDateCondition()
      const expdate = '发票主要数据' + this.dayjs(new Date()).format('YYYY-MM-DD')

      exportImpInvoiceMaster(this.queryForm).then(res => {
        console.info('exportImpInvoiceMaster')
        const fileName = expdate + '.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportImpInvoiceMasterLoading = false
      }).catch(error => {
        console.error('error')
        console.error(error)
        this.exportImpInvoiceMasterLoading = false
      })
    },
    exportImpInvoiceDetail() {
      this.exportDetailLoading = true
      const expdate = '发票明细' + this.dayjs(new Date()).format('YYYY-') + this.impDetailForm.invoiceNo
      exportImpInvoiceDetail(this.impDetailForm).then(res => {
        const fileName = expdate + '.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportDetailLoading = false
      }).catch(error => {
        console.error(error)
        this.exportDetailLoading = false
      })
    },
    exportImpInvoiceDetailPack() {
      this.exportPackLoading = true
      const expdate = '发票分包' + this.dayjs(new Date()).format('YYYY-') + this.impDetailForm.invoiceNo
      exportImpInvoiceDetailPack(this.impDetailForm).then(res => {
        const fileName = expdate + '.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportPackLoading = false
      }).catch(error => {
        console.error(error)
        this.exportPackLoading = false
      })
    },
    UpdateDeleteDetailPack() {
      this.UpdateDeleteDetailPackDisabled = true
      this.$prompt('登记删除订单', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        inputPlaceholder: '专备客户代码'
      }).then(({ value }) => {
        // 执行后续操作，如 API 请求等
        this.exeDeleteDetailPack(value)
      }).catch(() => {
        // 用户点击取消或关闭对话框
        this.UpdateDeleteDetailPackDisabled = false
      })
    },
    exeDeleteDetailPack(endUser) {
      // <!--add by WuWeiDong 20221130 bug 8614 -->
      if (this.datadiffSelection.length === 0) {
        this.$message.error('请选择订单数据！')
        return
      }
      this.poInvoiceLists = []
      this.UpdateDeleteDetailPackDisabled = true
      this.datadiffSelection.forEach((item, index) => {
        const poInvoice = {
          invoiceId: item.invoiceId,
          invoiceNo: item.invoiceNo,
          fullOrderNo: item.orderNo
        }
        this.poInvoiceLists.push(poInvoice)
      })
      const parm = {
        poInvoiceDTOS: this.poInvoiceLists,
        doType: 1,
        endUser: endUser
      }
      console.info(parm)
      UpdateDeleteDetailPack(parm).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          this.UpdateDeleteDetailPackDisabled = false
          return
        }
        this.$message.success('【登记删除订单】登记成功！')
      }).catch(error => {
        console.info(error)
        this.UpdateDeleteDetailPackDisabled = false
        this.$message.error(error.message)
      })
    },
    UpdateDeleteDetailPackByCancel() {
      // <!--add by WuWeiDong 20221130 bug 8614 -->
      if (this.datadiffSelection.length === 0) {
        this.$message.error('请选择订单数据！')
        return
      }
      this.poInvoiceLists = []
      this.UpdateDeleteDetailPackByCancelDisabled = true
      this.datadiffSelection.forEach((item, index) => {
        const poInvoice = {
          invoiceId: item.invoiceId,
          invoiceNo: item.invoiceNo,
          fullOrderNo: item.orderNo
        }
        this.poInvoiceLists.push(poInvoice)
      })
      const parm = {
        poInvoiceDTOS: this.poInvoiceLists,
        doType: 2,
        endUser: ''
      }
      UpdateDeleteDetailPack(parm).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          this.UpdateDeleteDetailPackByCancelDisabled = false
          return
        }
        this.$message.success('【取消登记删除】登记成功！')
      }).catch(error => {
        console.info(error)
        this.UpdateDeleteDetailPackByCancelDisabled = false
        this.$message.error(error.message)
      })
    },
    exportImpInvoiceError() {
      this.exportDiffLoading = true
      this.diffparams.invoiceId = this.impDetailForm.invoiceId
      this.diffparams.orderNo = this.impDetailForm.orderNo
      this.diffparams.errorTypeList = this.impDetailForm.errorTypeList
      const expdate = '发票差异' + this.dayjs(new Date()).format('YYYY-') + this.impDetailForm.invoiceNo
      exportImpInvoiceError(this.diffparams).then(res => {
        const fileName = expdate + '.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportDiffLoading = false
      }).catch(error => {
        console.error(error)
        this.exportDiffLoading = false
      })
    },
    importInvoiceFromGW() {
      if (this.importGW.startTime !== null && this.importGW.startTime !== '') {
        this.importGW.startTime = this.dayjs(this.importGW.startTime).format('YYYY-MM-DD HH:mm:ss')
      }
      if (this.importGW.endTime !== null && this.importGW.endTime !== '') {
        this.importGW.endTime = this.dayjs(this.importGW.endTime).format('YYYY-MM-DD HH:mm:ss')
      }
      if (this.importGW.startTime === null) {
        this.importGW.startTime = ''
      }
      if (this.importGW.endTime === null) {
        this.importGW.endTime = ''
      }
      impImportInvoiceInfo(this.importGW).then(res => {
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
    importData() {
      this.uploadStatus = true
      const file = this.file
      if (!this.file) { // 防止空传
        this.uploadStatus = false
        this.$message.error('请选择文件上传')
        return false
      }
      const formData = new FormData() // form表单格式提交数据
      formData.append('file', file)
      this._importEmployeeAssistant(formData)
      this.uploadStatus = false
    },
    _importEmployeeAssistant(formData) {
      uploadFile(formData)
        .then(res => {
          if (res.success) {
            this.$message.success(res.message)
            this.file = null
            this.implistdata = res.content
            this.impform.total = res.content.length
            this.uploadFileDialogVisible = false
          } else {
            this.$message.error(res.content)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    descFormatter(data, id) {
      if (data === null) {
        return id
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === (id === null ? '' : id.toString())) {
          return item.codeName
        }
      }
      return id
    },
    arrivedformatter(row) {
      if (row.arrivedWarehouseCode === null || row.arrivedWarehouseCode === '') {
        return ''
      }
      const data = this.warehouseData
      for (const i in data) {
        var item = data[i]
        if (item.warehouseCode === row.arrivedWarehouseCode.toString()) {
          return item.warehouseName
        }
      }
      return ''
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
    statusCodeNameformatter(row) {
      return this.descFormatter(this.statusCodes, row.status)
    },
    dataTypeformatter(row) {
      return this.descFormatter(this.dataTypedes, row.dataType)
    },
    detailstatusformatter(row) {
      return this.descFormatter(this.statusOptions, row.status)
    },
    updateTimeformatter(row) {
      if (row.updateTime == null) {
        return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
      }
      return this.dayjs(row.updateTime).format('YYYY-MM-DD HH:mm')
    },
    confirmDateformatter(row) {
      return this.dayjs(row.confirmDate).format('YYYY-MM-DD HH:mm')
    },
    invoiceTypeformatter(row) {
      return this.descFormatter(this.invoiceTypedesc, row.invoiceType)
    },
    preArriveDateformatter(row) {
      if (row.preArriveDate != null) {
        const preDate = new Date(row.preArriveDate)
        const year = preDate.getFullYear()
        const month = ('0' + (preDate.getMonth() + 1)).slice(-2)
        const day = ('0' + preDate.getDate()).slice(-2)
        const hour = preDate.getHours()
        var s = '下午'
        if (hour === 12) {
          s = '上午'
        }
        return `${year}-${month}-${day} ${s}`
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
      if (row.ignoreTime != null) {
        return this.dayjs(row.ignoreTime).format('YYYY-MM-DD HH:mm')
      }
    },
    shipDateformatter(row) {
      if (row.shipDate != null) {
        return this.dayjs(row.shipDate).format('YYYY-MM-DD')
      }
    },
    receiveDateformatter(row) {
      if (row.receiveDate != null) {
        const preDate = new Date(row.receiveDate)
        const year = preDate.getFullYear()
        const month = ('0' + (preDate.getMonth() + 1)).slice(-2)
        const day = ('0' + preDate.getDate()).slice(-2)
        const hour = preDate.getHours()
        var s = '下午'
        if (hour === 12) {
          s = '上午'
        }
        return `${year}-${month}-${day} ${s}`
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
    momentformatter(date) { // 日期格式化
      if (date != null) {
        return this.dayjs(date).format('YYYY-MM-DD')
      }
      return ''
    },
    supplierfomatter(row) {
      if (row.updateTime != null) {
        return this.dayjs(row.updateTime).format('YYYY-MM-DD HH:mm')
      }
      return ''
    },
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    // 修改子项属性
    editProperty(row, index) {
      // isEditPropertyShow为ture展示输入框
      this.invoiceDetailForm = this.impDetaildata.impInvoiceDetail[index]
      this.dialogaddinvoiceDetail = true
      this.invRadio = '1'
      //   this.$set(this.impDetaildata.impInvoiceDetail[index], 'isEditPropertyShow', true)
    },
    editDetailPack(row, index) {
      this.invoiceDetailForm = this.impDetaildata.impInvoiceDetailPack[index]
      this.dialogaddinvoiceDetail = true
      this.invRadio = '2'
      //   console.log(index)
      //   this.$set(this.impDetaildata.impInvoiceDetailPack[index], 'isEditDetailPackShow', true)
    },
    // 保存子项属性
    saveProperty(row, index) {
      updateImpInvoiceDetail(this.impDetaildata.impInvoiceDetail[index]).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        this.impDetaildata.impInvoiceDetail[index].impOrderNo = row.orderNo + '-' + row.itemNo
        this.$set(this.impDetaildata.impInvoiceDetail[index], 'isEditPropertyShow', false)
        this.$message.success('保存成功')
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    saveDetailPack(row, index) {
      updateImpInvoiceDetailPack(this.impDetaildata.impInvoiceDetailPack[index]).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        this.impDetaildata.impInvoiceDetailPack[index].impOrderNo = row.orderNo + '-' + row.itemNo
        this.$set(this.impDetaildata.impInvoiceDetailPack[index], 'isEditDetailPackShow', false)
        this.$message.success('保存成功')
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 取消商品属性编辑
    cancelProperty(row, index) {
      if (sessionStorage.getItem('oldPropertValue') !== 'null') {
        this.$set(this.impDetaildata.impInvoiceDetail[index], 'propertyValue', sessionStorage.getItem('oldPropertValue'))
      } else {
        this.$set(this.impDetaildata.impInvoiceDetail[index], 'propertyValue', '')
      }
      this.$set(this.impDetaildata.impInvoiceDetail[index], 'isEditPropertyShow', false)
    },
    cancelDetailPack(row, index) {
      this.$set(this.impDetaildata.impInvoiceDetailPack[index], 'isEditDetailPackShow', false)
    },
    // 删除子项
    delDetail(row, index) {
      const params = {
        invoiceId: row.invoiceId,
        detailId: row.id
      }
      delImpInvoiceDetail(params).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        this.showDetail()
        // 删除行
        // this.impDetaildata.impInvoiceDetail.splice(index, 1)
        this.$message.success('删除成功')
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 删除子项
    delDetailPack(row, index) {
      const params = {
        invoiceId: row.invoiceId,
        detailId: row.id
      }
      delImpInvoiceDetailPack(params).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        this.showDetailPack()
        // 删除行
        // this.impDetaildata.impInvoiceDetail.splice(index, 1)
        this.$message.success('删除成功')
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    statusFormatter(status) {
      if (status != null) {
        for (const i in this.statusCodes) {
          var item = this.statusCodes[i]
          if (item.code === status.toString()) {
            return item.codeName
          }
        }
      }
      return ''
    },
    // 发送
    finishImpInvoiceDeatailAdd() {
      if (this.impDetaildata.impInvoiceMaster.id === null || this.impDetaildata.impInvoiceMaster.id === '') {
        this.$message.error('先查主项')
        return
      }
      if (this.finishImping) {
        this.$message.warning('不要重复点击！')
      }
      this.finishImping = true
      const params = {
        invoiceId: this.impDetaildata.impInvoiceMaster.id
      }
      finishImpInvoiceDeatailAdd(params).then(res => {
        if (!res.success) {
          this.smcErrorMsg(res.message)
          this.finishImping = false
          return
        } else {
          this.smcInfoMsg(res.message)
          this.isEdit = false
          this.searchData()
          this.finishImping = false
        }
      }).catch(error => {
        console.info(error)
        this.smcErrorMsg(error.message)
        this.finishImping = false
      })
    },
    // 发送
    sendImpData(row) {
      if (this.sendImping) {
        this.$message.warning('不要重复点击！')
      }
      this.sendImping = true
      const params = {
        invoiceId: row.id
      }
      ImpInvoiceDetailToImpData(params).then(res => {
        console.log(res)
        if (!res.success) {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
          this.sendImping = false
          return
        } else {
          this.searchData()
          this.showDetail()
        }
        // 删除行
        // this.impDetaildata.impInvoiceDetail.splice(index, 1)
        this.smcInfoMsg('发送成功')
        this.sendImping = false
      }).catch(error => {
        console.info(error)
        this.$message({
          dangerouslyUseHTMLString: true,
          showClose: true,
          message: error.message,
          type: 'error',
          duration: 0
        })
        this.sendImping = false
      })
    },
    handleSelection(val) {
      this.multipleSelection = val
    }
    // handleDetailSelection(val) {
    //   this.multipleDetailSelection = val
    // }

  }
}
</script>
<style scoped>
  /* .line {
    text-align: center;
    margin-left: 5%;
  }
  .tableCloumn {
    position: absolute;
    margin-top: 30px;
    padding-right: 10px;
    width: 99%;
  } */
</style>
