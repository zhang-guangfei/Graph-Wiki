<template>
  <div class="container" style="width: 98%">
    <el-dialog :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
      <product-search v-if="productVisible" ref="ProductSearch"/>
    </el-dialog>
    <div style="width: 100%">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="样品订单查询" name="first">
          <div class="searchHead">
            <el-form :inline="true" :model="searchForm" class="demo-form-inline" size="mini">
              <el-form-item>
                <el-input v-model="searchForm.orderNo" placeholder="请输入订单号" class="orderNo" style="width: 66%" size="mini" clearable/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="searchForm.applyNo" clearable placeholder="申请号" class="applyNo" size="mini" style="width: 64%"/>
              </el-form-item>
              <el-form-item>
                <el-select v-model="searchForm.status" clearable placeholder="订单状态" size="mini" class="state" style="width: 60%">
                  <el-option
                    v-for="item in statusOptions"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="searchForm.applyType" clearable placeholder="申请类型" size="mini" class="applyTypeClass" style="width: 122px">
                  <el-option
                    v-for="item in applyTypelist"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="searchForm.costType" clearable placeholder="结转方式" size="mini" class="costTypeClass" style="width: 122px">
                  <el-option
                    v-for="item in costTypeList"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item class="btu">
                <el-button type="primary" size="mini" class="searchBtu" icon="el-icon-search" @click="searchData(1)" >查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" size="mini" class="searchBtu" icon="el-icon-delete" @click="resetSearchForm()" >重置</el-button>
              </el-form-item>
              <el-form-item>
                <el-button :loading="loadingBtu" type="primary" size="mini" class="makeOrder" icon="el-icon-plus" @click="createOrders()" >生成订单</el-button>
              </el-form-item>
              <el-form-item>
                <el-button :loading="exportSampleApplyOrderLoading" type="primary" size="mini" class="makeOrder" icon="el-icon-upload2" @click="exportSampleOrderApplyData()" >导出</el-button>
              </el-form-item>
              <!-- <el-form-item>
                <el-button type="primary" size="mini" class="cancel" @click="aa" >取消</el-button>
              </el-form-item> -->
              <!-- <el-form-item>
                <el-button type="primary" size="mini" icon="el-icon-upload2" @click="aa" >导出</el-button>
              </el-form-item> -->
              <el-form-item>
                <el-button type="primary" size="mini" icon="el-icon-more" class="searchMoreBtu" @click="searchMoreData" />
              </el-form-item>
            </el-form>
          </div>
          <div v-if="searchMoreForm" class="search">
            <el-form ref="form" :inline="true" :model="searchForm" size="mini" class="searchform">
              <department style="margin-left: 0px;margin-top: 4px;" @handleScopeChange="handleScopeChange2"/>
              <department :showdesc="showdesc" style="margin-left: 0px;margin-top: 4px;" @handleScopeChange="handleScopeChangeDeptNos"/>
              <el-form-item>
                <el-input v-model="searchForm.modelNo" style="width: 115px" clearable placeholder="型号"/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="searchForm.corderNo" style="width: 110px;margin-left: -10px" clearable placeholder="客户订单号"/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="searchForm.cmodelNo" style="width: 100px;margin-left: -10px" clearable placeholder="客户型号"/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="searchForm.userNo" style="width: 100px;margin-left: -10px" clearable placeholder="用户代码"/>
              </el-form-item>
              <el-form-item class="daypick">
                <el-select v-model="daySelectVal" style="width: 120px" clearable placeholder="请选择:">
                  <el-option label="申请时间" value="applyTime"/>
                  <el-option label="生成订单时间" value="orderDate"/>
                  <el-option label="处理时间" value="answerTime"/>
                </el-select>
                <el-date-picker
                  v-model="sampleOrderDate"
                  :picker-options="pickerOptions"
                  :default-time="['00:00:00', '23:59:59']"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 230px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                />
              </el-form-item>
            </el-form>
          </div>
          <div class="tableStyle" style="width: 100%">
            <el-table
              :data="tableData"
              :row-style="{height: '0'}"
              :cell-style="{padding: '0'}"
              border
              stripe
              height="650"
              size="mini"
              style="width: 100%"
              class="tab"
              @selection-change="handleSelectionChange"
              @row-click="handdle">
              <el-table-column
                type="selection"
                width="45"/>
              <el-table-column
                prop="applyNo"
                fixed
                label="申请号"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="orderNo"
                fixed
                label="接单订单号"
                width="110"
                show-overflow-tooltip/>
              <el-table-column
                prop="itemNo"
                fixed
                label="项号"
                width="60"
                show-overflow-tooltip/>
              <el-table-column
                :formatter="statusformatter"
                prop="status"
                label="状态"
                width="100"
                show-overflow-tooltip/>
              <!-- <el-table-column
                :formatter="costStatusformatter"
                prop="costStatus"
                label="结转状态"
                show-overflow-tooltip/> -->
              <el-table-column
                prop="corderNo"
                label="客户单号"
                width="90"
                show-overflow-tooltip/>
              <el-table-column
                prop="customerNo"
                label="客户代码"
                show-overflow-tooltip/>
              <el-table-column
                prop="userNo"
                label="用户代码"
                show-overflow-tooltip/>
              <el-table-column
                prop="quantity"
                label="数量"/>
              <el-table-column
                prop="modelNo"
                label="型号"
                width="160"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-link :underline="false" @click="modelNoClick(scope.row)">{{ scope.row.modelNo }}</el-link>
                </template>
              </el-table-column>
              <el-table-column
                prop="cmodelNo"
                label="客户型号"
                show-overflow-tooltip/>
              <el-table-column
                prop="applyDeptNo"
                label="申请部门"
                width="150" />
              <el-table-column
                prop="applyTypeName"
                label="申请类型"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="costTypeName"
                label="结算类型"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="purpose"
                label="申请目的"
                width="100"
                show-overflow-tooltip/>
              <el-table-column
                prop="deptNo"
                label="费用承担部门"
                width="150" />
              <el-table-column
                prop="price"
                label="单价"/>
              <el-table-column
                prop="dlvEntire"
                label="出货方式"/>
              <el-table-column
                prop="dlvType1"
                label="出货类别1"/>
              <el-table-column
                prop="dlv_type2"
                label="出货类别2"/>
              <el-table-column
                prop="remark"
                label="备注"
                show-overflow-tooltip/>
              <el-table-column
                prop="addressType"
                label="收货地址类别"
                width="100"/>
              <el-table-column
                prop="addressNo"
                label="地址编号"/>
              <el-table-column
                :formatter="applyTimeformatter"
                prop="applyTime"
                label="申请时间"
                width="120"
                show-overflow-tooltip/>
              <el-table-column
                :formatter="orderDateformatter"
                prop="orderDate"
                label="生成订单时间"
                width="120"
                show-overflow-tooltip/>
              <el-table-column
                :formatter="esarrivalDateformatter"
                prop="esarrivalDate"
                label="希望货期"
                width="120"
                show-overflow-tooltip/>

              <el-table-column
                prop="answerPsn"
                label="处理人"/>
              <el-table-column
                :formatter="answerTimeformatter"
                prop="answerTime"
                label="处理时间"
                width="120"
                show-overflow-tooltip/>
              <el-table-column
                prop="applyPsn"
                label="申请人"
                show-overflow-tooltip/>
            </el-table>
            <pagination
              v-show="total > 2"
              :total="total"
              :page-sizes="[20, 50, 100, 200, 500]"
              :page.sync="page.pageNumber"
              :limit.sync="page.pageSize"
              @pagination="fetchList()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="样品订单结转申请确认" name="three">
          <div>
            <el-form :inline="true" :model="sampleBalApplySearchForm" class="demo-form-inline" size="mini">
              <el-form-item>
                <el-input v-model="sampleBalApplySearchForm.sampleBalApplyNo" clearable placeholder="请输入结转申请号" style="width: 150px" />
              </el-form-item>
              <el-form-item>
                <el-input v-model="sampleBalApplySearchForm.orderNo" clearable placeholder="请输入订单号" style="width: 150px" />
              </el-form-item>
              <el-form-item>
                <el-select v-model="sampleBalApplySearchForm.handStatus" style="width: 150px" clearable placeholder="处理状态">
                  <el-option
                    v-for="item in handStatusList"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item size="mini">
                <el-select v-model="sampleBalApplySearchForm.applyType" clearable placeholder="样品类型" size="mini" multiple collapse-tags style="width: 122px">
                  <el-option
                    v-for="item in applyTypelist"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="sampleBalApplySearchForm.balType" clearable placeholder="申请结转类型" size="mini" multiple collapse-tags style="width: 125px">
                  <el-option
                    v-for="item in costTypeList"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item size="mini">
                <department class="menu-department" @handleScopeChange="handleScopeChange3"/>
              </el-form-item>
              <el-form-item>
                <el-date-picker
                  :picker-options="pickerOptions"
                  v-model="sampleBalApplyTime"
                  type="daterange"
                  align="right"
                  unlink-panels
                  clearable
                  range-separator="至"
                  start-placeholder="申请开始日期"
                  end-placeholder="申请结束日期"
                  style="width: 200px"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="selSampleBalApplyData">查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button :loading="sureSampleBalLoad" type="primary" @click="sureSampleBalApply">确认结转</el-button>
              </el-form-item>
            </el-form>
            <div>
              <el-table
                :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
                :data="sampleBalApplyTableData"
                :row-style="{height:'33px'}"
                :height="size.rcvTable"
                border
                style="width: 100%"
                @selection-change="applyBalHandleSelectionChange">
                <el-table-column
                  type="selection"
                  width="55" />
                <el-table-column
                  prop="sampleBalApplyNo"
                  label="结转申请号"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="orderNo"
                  label="样品订单号"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="applyBalType"
                  label="申请结转类型"
                  width="130"/>
                <el-table-column
                  prop="handStatusName"
                  label="处理状态"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="forceBalFlag"
                  label="强制结转"
                  width="110"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="modelNo"
                  label="型号"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="quantity"
                  label="订单数量"
                  width="130"/>
                <el-table-column
                  prop="customerNo"
                  label="申请客户"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="userNo"
                  label="申请用户"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="applyBalQty"
                  label="申请结转数量"
                  width="130"/>
                <el-table-column
                  prop="price"
                  label="原单价"
                  width="130"/>
                <el-table-column
                  prop="applyBalPrice"
                  label="申请结转单价"
                  width="130"/>
                <!-- <el-table-column
                  prop="applyBalPrice"
                  label="结转日期"
                  width="130"
                  show-overflow-tooltip/> -->
                <el-table-column
                  prop="applyDeptNo"
                  label="申请营业所"
                  width="130"/>
                <el-table-column
                  prop="applyPsnNo"
                  label="申请人"
                  width="130"/>
                <el-table-column
                  prop="applyTime"
                  label="申请日期"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="applyReason"
                  label="申请理由"
                  width="130"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="backWarehource"
                  label="退回物流中心"
                  width="130"/>
                <el-table-column
                  prop="signQty"
                  label="签收数量"
                  width="130"/>
                <el-table-column
                  prop="signTime"
                  label="签收日期"
                  width="130"/>
                <el-table-column
                  fixed="right"
                  label="操作"
                  width="100">
                  <template slot-scope="scope">
                    <el-button type="text" size="small" @click="viewFiles(scope.row)">附件管理</el-button>
                  </template>
                </el-table-column>
              </el-table>
              <pagination
                v-show="sampleApplyBalTotal > 2"
                :total="sampleApplyBalTotal"
                :page-sizes="[15, 30, 50, 100, 200]"
                :page.sync="sampleBalApplySearchForm.page.pageNumber"
                :limit.sync="sampleBalApplySearchForm.page.pageSize"
                @pagination="selSampleBalApplyData()"/>
              <el-dialog
                :visible.sync="uploadFileApplyBalDialogVisible"
                :show-close="false"
                :close-on-click-modal="false"
                :close-on-press-escape="false"
                width="30%"
              >
                <el-upload
                  :on-preview="handlePreviewWithApplyBal"
                  :on-remove="handleRemoveWithApplyBal"
                  :file-list="fileList"
                  :auto-upload="false"
                  :before-upload="beforeUploadFileWithApplyBal"
                  :on-change="handleFileChange"
                  action=""
                >
                  <el-button slot="trigger" size="mini" type="success">文件上传</el-button>
                </el-upload>
                <br>
                <el-table :data="uploadedFiles" max-height="250" border>
                  <el-table-column prop="realFileName" show-overflow-tooltip label="文件名" />
                  <el-table-column prop="createTime" show-overflow-tooltip label="上传日期" />
                  <el-table-column
                    fixed="right"
                    label="操作"
                    width="100">
                    <template slot-scope="scope">
                      <el-button type="text" size="small" @click="downLoadFile(scope.row)" >下载</el-button>
                      <el-button type="text" size="small" @click="delFile(scope.row)">删除</el-button>
                    </template>
                  </el-table-column>
                </el-table>
                <span slot="footer" class="dialog-footer">
                  <el-button size="mini" @click="uploadFileApplyBalDialogVisible = false">取 消</el-button>
                  <el-button size="mini" type="primary" @click="uploadFileAttacheFileManageInfoToServer()">确 定</el-button>
                </span>
              </el-dialog>
            </div>
          </div>
        </el-tab-pane>
        <el-tab-pane label="样品订单处理" name="second">
          <div class="searchHead">
            <el-form :inline="true" :model="sampleBalForm" class="demo-form-inline" size="mini">
              <el-form-item>
                <el-input v-model="sampleBalForm.rorderNo" placeholder="请输入接单号" class="SampleBalOrderNo" style="width: 68%" size="mini" clearable/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="sampleBalForm.applyCode" clearable placeholder="申请号" class="SampleBalApplyNo" size="mini" style="width: 95%"/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="sampleBalForm.customerNo" placeholder="客户代码" class="SampleBalCustomerNo" style="width: 95%" size="mini" clearable/>
              </el-form-item>
              <el-form-item>
                <el-input v-model="sampleBalForm.modelNo" clearable placeholder="型号" class="SampleBalModelNo" size="mini" style="width: 125%"/>
              </el-form-item>
              <el-form-item>
                <el-select v-model="sampleBalForm.optCode" clearable placeholder="处理状态" size="small" class="handStatus" multiple collapse-tags style="width: 122px">
                  <el-option
                    v-for="item in optCodeOptions"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
            </el-form>
            <div class="salmpleBalBtu">
              <el-button type="primary" size="mini" class="searchBtu1" icon="el-icon-search" @click="searchSampleBalData()" >查询</el-button>
              <el-button type="primary" size="mini" class="searchBtu2" icon="el-icon-delete" @click="resetSampleBalForm()" >重置</el-button>
              <el-button type="primary" size="mini" icon="el-icon-more" class="moreSearchBtu" @click="moreSearchForm()" />
            </div>
          </div>
          <div v-if="isShowSearchSamBalFrom" class="search">
            <el-form :inline="true" :model="sampleBalForm" class="demo-form-inline" size="small">
              <el-form-item class="samplebalDaySelVal">
                <el-select v-model="samplebalDaySelVal" style="width: 120px" clearable placeholder="请选择:">
                  <el-option label="操作日期" value="optDate"/>
                  <el-option label="出库日期" value="expDate"/>
                  <el-option label="结转日期" value="inDate"/>
                  <el-option label="创建日期" value="creDate"/>
                </el-select>
                <el-date-picker
                  v-model="samplebalSearchDay"
                  :picker-options="pickerOptions"
                  :default-time="['00:00:00', '23:59:59']"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 230px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                />
              </el-form-item>
              <el-form-item>
                <el-select v-model="sampleBalForm.applyType" clearable placeholder="申请方式" size="small" class="searchMoreColumn" multiple collapse-tags style="width: 122px">
                  <el-option
                    v-for="item in applyTypelist"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-select v-model="sampleBalForm.balType" clearable placeholder="结转方式" size="small" class="searchMoreColumn" multiple collapse-tags style="width: 122px">
                  <el-option
                    v-for="item in costTypeList"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"/>
                </el-select>
              </el-form-item>
              <department @handleScopeChange="handleScopeChange"/>
            </el-form>
          </div>
          <!-- <el-button :loading="loadingCarryTurnBtu" type="primary" size="mini" icon="el-icon-finished" class="handleBtu" @click="orderCarryTurn()">免费结转</el-button> -->
          <vxe-button status="primary" size="small" transfer>
            <template #default>转销售操作</template>
            <template #dropdowns>
              <vxe-button type="text" content="转销售开票" @click="carryTurnSales()" />
              <vxe-button type="text" content="取消转销售" @click="cancelTurnSalesInvoice()"/>
            </template>
          </vxe-button>
          <vxe-button status="primary" size="small" transfer>
            <template #default>结转操作</template>
            <template #dropdowns>
              <vxe-button type="text" content="结转" @click="splitBtu()" />
              <vxe-button type="text" content="重新结转" @click="againBal()"/>
            </template>
          </vxe-button>
          <vxe-button status="primary" size="small" transfer>
            <template #default>导出操作</template>
            <template #dropdowns>
              <vxe-button type="text" content="查询数据导出" @click="exportSampleBalData()" />
              <vxe-button type="text" content="展示品盘点表导出" @click="exportZlzsOrderBalance()"/>
              <!-- <vxe-button type="text" content="展示品盘点表发布" @click="pushZlzsOrderBalance()"/> -->
              <vxe-button type="text" content="逾期未结转发布" @click="overdueBal()"/>
              <vxe-button type="text" content="下载逾期未结转发布清单" @click="downLoadNoBalFileByYearMonth()"/>
            </template>
          </vxe-button>
          <!-- <vxe-button status="primary" size="small" transfer>
            <template #default>展览展示品操作</template>
            <template #dropdowns>
              <vxe-button type="text" content="展览展示品销账(单条)" @click="writeOff()" />
              <vxe-button type="text" content="展览展示品销账(批量)" @click="batchWriteOff()" />
              <vxe-button type="text" content="变更展示品实物所在部门(单条)" @click="updateZSDeptNo()"/>
              <vxe-button type="text" content="变更展示品实物所在部门(批量)" @click="batchUpdateZSDeptNo()"/>
            </template>
          </vxe-button> -->
          <!-- <el-button type="primary" size="mini" icon="el-icon-finished" class="handleBtu" @click="carryTurnSales()">转销售开票</el-button>
          <el-button type="primary" size="mini" icon="el-icon-finished" class="handleBtu" @click="splitBtu()">结转</el-button>
          <el-button :loading="againBalBtu" type="primary" size="mini" icon="el-icon-finished" class="handleBtu" @click="againBal()">重新结转</el-button>
          <el-button :loading="exportLoading" type="primary" size="mini" icon="el-icon-bottom" class="handleBtu" @click="exportSampleBalData()">导出</el-button>
          <el-button :loading="exportNoBal" type="primary" size="mini" icon="el-icon-bottom" class="handleBtu" @click="overdueBal()">逾期未结转清单发布</el-button>
          <el-button :loading="cancelTurnSalesBtuLoading" type="primary" size="mini" icon="el-icon-finished" class="handleBtu" @click="cancelTurnSalesInvoice()">取消转销售</el-button>
          <el-button :loading="exportZlzsOrderBalanceLoading" type="primary" size="mini" icon="el-icon-bottom" class="handleBtu" @click="exportZlzsOrderBalance()">展示品结余导出</el-button> -->
          <el-form v-show="invoicingSales" ref="invoicingSalesParams" :inline="true" :model="invoicingSalesParams" :rules="invoicingSalesRules" class="demo-form-inline">
            <el-form-item label="单价(含税)" prop="price">
              <el-input v-model="invoicingSalesParams.price" size="mini" style="width: 100px" placeholder="单价/元" clearable/>
            </el-form-item>
            <el-form-item>
              <el-button :loading="loadingInvoiceSales" type="primary" size="mini" @click="billing('invoicingSalesParams')">开票</el-button>
            </el-form-item>
          </el-form>
          <div class="tableStyle" style="width: 100%">
            <el-form ref="cturnForm" :model="cturnForm" size="mini">
              <el-table
                :data="cturnForm.cturnTab"
                :row-style="{height: '0'}"
                :cell-style="{padding: '0'}"
                border
                stripe
                height="630"
                size="mini"
                style="width: 100%"
                @selection-change="handleSelectionChangeCturnTab"
                @row-click="handdleCturnTab">
                <el-table-column
                  type="selection"
                  width="45"/>
                <el-table-column
                  prop="rorderNo"
                  label="接单号"
                  width="120"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="applyCode"
                  label="申请号"
                  width="100"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="customerNo"
                  label="客户代码"
                  width="70"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="userNo"
                  label="用户"
                  width="70"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="modelNo"
                  label="型号"
                  width="110"
                  show-overflow-tooltip>
                  <template slot-scope="scope">
                    <el-link :underline="false" @click="modelNoClick(scope.row)">{{ scope.row.modelNo }}</el-link>
                  </template>
                </el-table-column>
                <el-table-column
                  prop="quantity"
                  label="数量"
                  width="70"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="hlCodeName"
                  label="HL所"
                  width="140"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="deptName"
                  label="营业所"
                  width="140"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="price"
                  label="单价"
                  width="70"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="priceApply"
                  label="申请单价"
                  width="80"
                  show-overflow-tooltip/>
                <el-table-column
                  :formatter="typeCodeFormatter"
                  prop="ordType"
                  label="订单类别"
                  width="72"
                  show-overflow-tooltip/>
                <el-table-column
                  :formatter="applyTypeFormatter"
                  prop="appType"
                  label="样品申请方式"
                  width="110"
                  show-overflow-tooltip/>
                <el-table-column
                  :formatter="balTypeFormatter"
                  prop="balType"
                  label="结转方式"
                  width="110"
                  show-overflow-tooltip/>
                <el-table-column
                  :formatter="optTimeformatter"
                  prop="optDate"
                  label="处理日期"
                  width="110"
                  show-overflow-tooltip/>
                <el-table-column
                  :formatter="optCodeformatter"
                  prop="optCode"
                  label="处理状态"
                  width="90"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="remark"
                  label="备注"
                  width="70"
                  show-overflow-tooltip/>
                <el-table-column
                  :formatter="expTimeformatter"
                  prop="expDate"
                  label="出库日期"
                  width="110"
                  show-overflow-tooltip/>
                <el-table-column
                  :formatter="inTimeformatter"
                  prop="inDate"
                  label="结转日期"
                  width="110"
                  show-overflow-tooltip/>
                <el-table-column
                  label="发票号"
                  disabled
                  width="200">
                  <template slot-scope="scope" style="height: 0">
                    <el-input v-model="scope.row.invoiceNo" size="mini" placeholder="发票号" disabled />
                  </template>
                </el-table-column>
                <el-table-column
                  prop="rcvDeptName"
                  label="实物所在营业所"
                  width="160"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="claimNo"
                  label="索赔号"
                  width="120"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="claimAmount"
                  label="索赔金额"
                  width="90"
                  show-overflow-tooltip/>
                <el-table-column
                  prop="expressCompany"
                  label="快递公司名称"
                  width="130"
                  show-overflow-tooltip/>
              </el-table>
            </el-form>
            <pagination
              v-show="total > 2"
              :total="total"
              :page-sizes="[20, 50, 100, 200, 500]"
              :page.sync="page.pageNumber"
              :limit.sync="page.pageSize"
              @pagination="fetchList2()"/>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false" title="订单回退说明">
      <el-form ref="SampleOrderParams" :model="SampleOrderParams" :rules="rules" >
        <el-form-item label="回退说明" prop="remark">
          <el-input v-model="SampleOrderParams.remark" placeholder="请输入回退说明" autocomplete="off" clearable />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="rebackOrders('SampleOrderParams')">提交</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogSplitFormVisible" title="结转">
      <el-form :model="splitForm" size="mini">
        <el-form-item label="结转数量" >
          <el-input v-model="splitForm.quantity" :disabled="disabled" clearable style="width: 120px" autocomplete="off" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="结转部门" >
          <el-select v-model="splitForm.deptNo" clearable filterable placeholder="结转部门" size="mini" style="width: 140px">
            <el-option
              v-for="item in deptNoListData"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
        <el-form-item label="结转类型" >
          <el-select v-model="splitForm.balType" clearable placeholder="结转类型" size="mini" style="width: 140px">
            <el-option
              v-for="item in costTypeList"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogSplitFormVisible = false">取 消</el-button>
        <el-button :loading="splitLoadBtu" size="mini" type="primary" @click="splitSampleBal()">确定结转</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="zlzsOrderBal" title="展示品结余导出" class="zlzsExportOrderBalClass">
      <span class="demonstration">请选择结转时间导出到盘点表</span>
      <el-date-picker
        v-model="zlzsExportOrderBalTime"
        type="daterange"
        range-separator="至"
        start-placeholder="开始日期"
        end-placeholder="结束日期"
        value-format="yyyy-MM-dd"
        unlink-panels
        size="mini"/>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="zlzsOrderBal = false">取 消</el-button>
        <el-button :loading="sureExportBalLoading" type="primary" size="mini" @click="zlzsExportBal2()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="overTimeNoBalDislog" title="逾期未结转清单下载" class="zlzsExportOrderBalClass">
      <span class="demonstration">请选择下载逾期未结转的月份</span>
      <div class="block">
        <span class="demonstration">年月</span>
        <el-date-picker
          v-model="overTimeNoBal"
          type="month"
          value-format="yyyyMM"
          placeholder="选择月" />
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="overTimeNoBalDislog = false">取 消</el-button>
        <el-button :loading="overTimeNoBalBtuLoading" type="primary" size="mini" @click="exceDownLoadOverTimeNoBalFile()">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="WriteOffZlzsDialog" title="展览展示品销账(单条)" class="WriteOffZlzsDialog">
      <el-form ref="WriteOffZlzsForm" :model="WriteOffZlzsForm" :rules="writeOffRules" label-position="right" size="mini">
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item label="订单号" label-width="70px">
              <el-input v-model="WriteOffZlzsForm.rorderNo" autocomplete="off" style="width: 150px" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="型 号">
              <el-input v-model="WriteOffZlzsForm.modelNo" autocomplete="off" style="width: 150px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="10">
            <el-form-item label="销账数量" prop="qty">
              <el-input v-model="WriteOffZlzsForm.qty" autocomplete="off" style="width:100px" />
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="盘点批次号">
              <el-input v-model="WriteOffZlzsForm.pcNo" autocomplete="off" style="width: 150px" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" label-width="70px">
          <el-input v-model="WriteOffZlzsForm.remark" autocomplete="off" style="width: 80%" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="WriteOffZlzsDialog = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="sureWriteOff('WriteOffZlzsForm')">确 定</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="展览展示品批量销账"
      width="420px"
    >
      <div class="upload">
        <el-upload
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          class="upload-demo"
          drag
          multiple
        >
          <div class="el-upload__text">支持xlsx格式</div>
          <div v-if="file !== null" class="el-upload__text">
            {{ file.name }}
          </div>
          <div v-else class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
        </el-upload>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="success" class="downLoadExcel" @click="downLoadExcel" >下载模板</el-button>
        <el-button size="mini" @click="uploadFileDialogVisible = false">取 消</el-button>
        <el-button :loading="uploadLoading" size="mini" type="primary" @click="importData()">导 入</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="upZlzsRcvDeptNoDialog" title="变更展示品实物所在部门" style="width: 80%" >
      <el-form :model="upZlzsRcvDeptNoForm" size="mini">
        <el-form-item label="订单号" style="margin-left: 83px" >
          <el-input v-model="upZlzsRcvDeptNoForm.rorderNo" :disabled="disabled" clearable style="width: 120px" autocomplete="off" placeholder="请输入订单号" />
        </el-form-item>
        <el-form-item label="展示品实物所在部门" >
          <el-select v-model="upZlzsRcvDeptNoForm.rcvDeptNo" clearable filterable placeholder="展示品实物所在部门" size="mini" style="width: 160px">
            <el-option
              v-for="item in deptNoListData"
              :key="item.id"
              :label="item.name"
              :value="item.id"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="upZlzsRcvDeptNoDialog = false">取 消</el-button>
        <el-button :loading="upRcvDeptNoLoad" size="mini" type="primary" @click="sureUpdateZlzsRcvDeptNo()">确定变更</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogWithUpRcvDeptNo"
      :before-close="closeClick2"
      title="展览展示品批量变更实物所在部门"
      width="420px"
    >
      <div class="upload">
        <el-upload
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          class="upload-demo"
          drag
          multiple
        >
          <div class="el-upload__text">支持xlsx格式</div>
          <div v-if="file !== null" class="el-upload__text">
            {{ file.name }}
          </div>
          <div v-else class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
        </el-upload>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="success" class="downLoadExcel" @click="downLoadExcelWithUpRcvDeptNo" >下载模板</el-button>
        <el-button size="mini" @click="uploadFileDialogWithUpRcvDeptNo = false">取 消</el-button>
        <el-button :loading="impUpRcvDeptNoLoading" size="mini" type="primary" @click="importDataWithUpRcvDeptNo()">导 入</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import { listSampleOrderData, listSampleBalData, splitCarryType, againBal, createOrder, rebackOrder, orderCarryTurn as ordCarryTurn, invoicingSales, exportSampleBalData,
  exportOverdueBalData, cancelTurnSalesInvoice, exportZlzsOrderBalance, downLoadNoBalFileByYearMonth, writeoffForZlzsOrder, getZLZSExportTime, pushZlzsOrderBalance, downExcelForWriteOff,
  importWriteOffData, upZlzsRcvDeptNo, downExcelForUpRcvDeptNo, batchUpRcvDeptNo, insertIntoSampleOrderManage, findSampleBalApplyInfoList, findAttacheFiledManageInfo, delAttacheFileManageInfo, dowmLoadAttacheFileManageInfo, uploadFileAttacheFileManageInfoToServer, sureApplySampleBal, exportSampleOrderApplyData } from '@/api/order/sampleOrder'
import { getDictDataByPid, getSampleBalTypeByApplyType } from '@/api/common/dict'
import { getDeptAllData } from '@/api/common/department'
import { shortcuts } from '@/utils/dataFormat'
import ProductSearch from '../../product/index'
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
export default {
  name: 'SampleOrder',
  components: { Pagination, ProductSearch, Department },
  data() {
    var validatePrice = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入单价'))
      } else {
        if (isNaN(Number(value))) {
          callback(new Error('请输入正确数值'))
        }
        callback()
      }
    }
    var validateQty = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入数量(正数)'))
      } else {
        if (isNaN(Number(value)) || value < 0) {
          callback(new Error('请输入正确数值(正数)'))
        }
        callback()
      }
    }
    return {
      sampleBalApplyTime: [],
      ids: [],
      uploadFileApplyBalDialogVisible: false,
      sampleBalApplySearchForm: {
        orderNo: '',
        sampleBalApplyNo: '',
        handStatus: [],
        applyDeptNo: [],
        applyType: [],
        balType: [],
        applyTimeStart: '',
        applyTimeEnd: '',
        page: {
          pageNumber: 1,
          pageSize: 15
        }
      },
      sampleApplyBalTotal: 0,
      size: {
        search: false,
        rcvTable: '60vh',
        rcvItemTable: 0
      },
      exportSampleApplyOrderLoading: false,
      handStatusList: [],
      showdesc: '费用承担部门',
      searchForm: {
        orderNo: '', // 订单号
        applyNo: '', // 申请号
        status: '', // 状态
        applyDeptNo: [], // 申请部门
        deptNos: [], // 费用承担部门
        modelNo: '', // 型号
        corderNo: '', // 客户订单号
        cmodelNo: '',
        userNo: '', // 用户代码
        applyTimeStart: '', // 申请时间
        applyTimeEnd: '',
        orderDateStart: '', // 订单生成时间
        orderDateEnd: '',
        answerTimeStart: '', // 处理时间
        answerTimeEnd: '',
        applyType: '',
        costType: ''
      },
      sampleBalForm: {
        customerNo: '',
        rorderNo: '', // 接单号
        modelNo: '',
        optDateStart: '',
        optDateEnd: '',
        inDateStart: '',
        inDateEnd: '',
        expDateStart: '',
        expDateEnd: '',
        creDateStart: '',
        creDateEnd: '',
        applyType: [],
        deptNos: [],
        balType: [],
        optCode: ['1'], // 处理状态
        applyCode: '' // 申请号
      },
      pickerOptions: shortcuts,
      daySelectVal: '',
      samplebalDaySelVal: '',
      tableData: [],
      OpsAttachedFileManageVO: {
        businessKeyValue: ''
      },
      deptNoOptions: [], // 部门集合
      applyTypelist: [],
      costTypeList: [],
      sampleBalList: [],
      sampleBalApplyTableData: [],
      cpnysData: [],
      cturnForm: {
        cturnTab: []
      }, // 结转table
      statusOptions: [],
      costStatusOptions: [],
      multipleSelection: [],
      multipleSelection2: [],
      SampleOrderParams: {
        applyIds: [],
        remark: ''
      },
      invoicingSalesParams: {
        price: '',
        id: ''
      },
      samplebal: {
        ids: []
      },
      applyIds: [],
      strApplyId: '',
      id: '',
      activeName: 'first',
      classCode: '',
      code: '',
      // 分页
      total: 1,
      page: {
        pageNumber: 1,
        pageSize: 20
      },
      sampleOrderDate: '',
      // 营业所树状菜单用
      propsDeptNo: { multiple: true },
      scopeOptions: [],
      disabled: false,
      samplebalSearchDay: '',
      searchMoreForm: false, // 是否显示 默认不显示
      loadingBtu: false, // 生成订单按钮 加载
      loadingBtuGoBack: false,
      loadingCarryTurnBtu: false, // 结转按钮 加载
      loadingInvoiceSales: false,
      cancelTurnSalesBtuLoading: false, // 取消转销售按钮加载标识
      isSuccess: false,
      // dialog
      dialogFormVisible: false,
      sureSampleBalLoad: false,
      invoicingSales: false,
      productVisible: false,
      isShowSearchSamBalFrom: false,
      uploadedFiles: [],
      optCodeOptions: [],
      deptNoListData: [],
      fileList: [],
      applyBalMultipleSelection: [],
      dialogSplitFormVisible: false,
      splitLoadBtu: false,
      exportLoading: false,
      againBalBtu: false,
      exportNoBal: false,
      sureExportBalLoading: false,
      exportZlzsOrderBalanceLoading: false,
      zlzsOrderBal: false,
      overTimeNoBalDislog: false,
      overTimeNoBalBtuLoading: false,
      WriteOffZlzsDialog: false,
      WriteOffZlzsForm: {
        rorderNo: '',
        modelNo: '',
        qty: '',
        remark: '',
        pcNo: ''
      },
      uploadFileDialogVisible: false,
      actionUrl: '',
      file: null,
      info: {
        filePath: '',
        randomFileName: ''
      },
      uploadLoading: false,
      upZlzsRcvDeptNoDialog: false,
      upRcvDeptNoLoad: false,
      delAttachedFileManageInfoVO: {
        ids: [],
        optUser: ''
      },
      impUpRcvDeptNoLoading: false,
      uploadFileDialogWithUpRcvDeptNo: false,
      upZlzsRcvDeptNoForm: {
        rorderNo: '',
        rcvDeptNo: '',
        optUserNo: ''
      },
      overTimeNoBal: '',
      zlzsExportRequest: {
        startExpDate: '',
        endExpDate: ''
      },
      zlzsExportOrderBalTime: [],
      appType: '',
      splitForm: {
        quantity: '', // 拆分数量
        balType: '', // 结转类型
        id: '',
        ids: [],
        deptNo: '',
        userNo: ''
      },
      rules: {
        remark: [{ required: true, message: '请输入回退说明', trigger: 'blur' }]
      },
      writeOffRules: {
        qty: [{ required: true, validator: validateQty, trigger: 'blur' }]
      },
      invoicingSalesRules: {
        price: [{ validator: validatePrice, trigger: 'blur' }]
      }
    }
  },
  created() {
    this.findTypeCode()
    this.findCostType()
    this.findApplyType()
    this.fetchList()
    this.findStatusCode()
    this.findCostStatusCode()
    this.findSampleBalApplyHandStatus()
    this.selSampleBalApplyData()
    // this.fetchUploadedFiles()
  },
  methods: {
    sureSampleBalApply() {
      this.sureSampleBalLoad = true
      const ids = this.applyBalMultipleSelection.map(item => item.id)
      console.log('ids', ids)
      sureApplySampleBal(ids).then(res => {
        console.log('==> ', res)
        this.sureSampleBalLoad = false
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.selSampleBalApplyData()
        } else {
          this.$notify({
            title: '失败',
            message: res.message,
            type: 'error'
          })
        }
      })
    },
    beforeUploadFileWithApplyBalFile() {
      console.log('beforeUploadFileWithApplyBalFile')
    },
    uploadFileAttacheFileManageInfoToServer() {
      this.formData = new FormData()
      if (this.fileList !== null && this.fileList.length > 0) {
        for (let i = 0; i < this.fileList.length; i++) {
          this.formData.append('fileList', this.fileList[i].raw)
        }
      }
      this.formData.append('keyValue', this.OpsAttachedFileManageVO.businessKeyValue)
      this.formData.append('createUser', this.$store.getters.position.psnsmcId)
      uploadFileAttacheFileManageInfoToServer(this.formData).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.uploadFileApplyBalDialogVisible = false
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
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
    delFile(row) {
      console.log('附件删除', row)
      this.delAttachedFileManageInfoVO.ids = []
      this.delAttachedFileManageInfoVO.ids.push(row.id + '')
      this.delAttachedFileManageInfoVO.optUser = this.$store.getters.position.psnsmcId
      console.log('==> ', this.delAttachedFileManageInfoVO)
      delAttacheFileManageInfo(this.delAttachedFileManageInfoVO).then(res => {
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.OpsAttachedFileManageVO.businessKeyValue = row.businessKeyValue
          findAttacheFiledManageInfo(this.OpsAttachedFileManageVO).then(res => {
            console.log('res =>> ', res)
            if (res.success) {
              this.uploadedFiles = res.content
            }
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: '删除异常.' + res.message
          })
        }
      })
    },
    handleRemoveWithApplyBal(file, fileList) {
      console.log(file, fileList)
    },
    handlePreviewWithApplyBal(file) {
      console.log(file)
    },
    beforeUploadFileWithApplyBal(file) {
      console.log(file)
    },
    handleFileChange(file, fileList) {
      console.log(file, fileList)
      this.fileList = fileList
    },
    applyBalHandleSelectionChange(val) {
      this.applyBalMultipleSelection = val
      console.log('applyBalMultipleSelection => ', this.applyBalMultipleSelection)
    },
    viewFiles(row) {
      this.OpsAttachedFileManageVO.businessKeyValue = row.sampleBalApplyNo
      findAttacheFiledManageInfo(this.OpsAttachedFileManageVO).then(res => {
        console.log('res =>> ', res)
        if (res.success) {
          this.uploadedFiles = res.content
        }
      })
      this.fileList = []
      this.uploadFileApplyBalDialogVisible = true
    },
    selSampleBalApplyData() {
      if (this.sampleBalApplyTime.length !== 0) {
        this.sampleBalApplySearchForm.applyTimeStart = this.dayjs(this.sampleBalApplyTime[0]).format('YYYY-MM-DD HH:mm:ss')
        this.sampleBalApplySearchForm.applyTimeEnd = this.dayjs(this.sampleBalApplyTime[1]).format('YYYY-MM-DD HH:mm:ss')
      }
      findSampleBalApplyInfoList(this.sampleBalApplySearchForm).then(res => {
        if (res.success) {
          console.log('res', res)
          this.sampleBalApplyTableData = res.content.list
          this.sampleApplyBalTotal = res.content.total
        }
      })
    },
    fetchList(val) {
      if (val === 1) {
        this.page.pageNumber = 1
      }
      this.searchTime()
      this.searchSampleBalTime()
      listSampleOrderData(this.searchForm, this.page).then(res => {
        if (res.success) {
          this.isSuccess = true
        } else {
          this.isSuccess = false
        }
        this.tableData = res.content.list
        this.total = res.content.total
      })
    },
    // 订单结转查询数据
    fetchList2() {
      this.searchTime()
      this.searchSampleBalTime()
      listSampleBalData(this.sampleBalForm, this.page).then(res => {
        if (res.success) {
          this.cturnForm.cturnTab = res.content.list
          this.total = res.content.total
        } else {
          this.$message({
            message: res.message,
            type: 'error'
          })
        }
      }).catch(error => {
        this.$message.error(error)
      })
    },
    splitSampleBal() {
      this.splitLoadBtu = true
      splitCarryType(this.splitForm).then(res => {
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.fetchList2()
          this.dialogSplitFormVisible = false
          this.splitLoadBtu = false
        } else {
          this.$notify.error({
            title: '错误',
            message: '结转有误.' + res.message
          })
          this.splitLoadBtu = false
        }
      }).catch(error => {
        this.$notify.error({
          title: '错误',
          message: '结转有误.' + error
        })
        this.splitLoadBtu = false
      })
    },
    overdueBal() {
      this.$confirm('此操作将进行逾期未结转数据清单发送给营业所, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.exportNoBal = true
        exportOverdueBalData().then(res => {
          if (res.success) {
            this.exportNoBal = false
            this.$notify({
              title: '成功',
              message: res.message,
              type: 'success'
            })
          } else {
            this.exportNoBal = false
            this.$notify.error({
              title: '错误',
              message: '发送有误.' + res.message
            })
          }
        }).catch(() => {
          this.exportNoBal = false
          this.$notify.error({
            title: '错误',
            message: '发送有误.'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    exportSampleOrderApplyData() {
      this.exportSampleApplyOrderLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportSampleOrderApplyData(this.searchForm).then(res => {
        console.log('结果打印:=> ', res)
        console.log('size=> ', res.size)
        if (res.size === 0) {
          this.$message({
            duration: 4000,
            message: '未查到数据.',
            type: 'error'
          })
          this.exportSampleApplyOrderLoading = false
          return
        }
        const fileName = '样品订单查询导出.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportSampleApplyOrderLoading = false
      }).catch(error => {
        console.error(error)
        this.exportSampleApplyOrderLoading = false
      })
    },
    handleScopeChange2(val) {
      console.log('所选部门=>', val)
      this.searchForm.applyDeptNo = val
    },
    handleScopeChangeDeptNos(val) {
      console.log('所选费用承担部门=>', val)
      this.searchForm.deptNos = val
    },
    handleScopeChange3(val) {
      console.log('所选部门=>', val)
      this.sampleBalApplySearchForm.applyDeptNo = val
    },
    findApplyType() {
      this.classCode = '1006'
      getDictDataByPid(this.classCode).then(res => {
        this.applyTypelist = res.content
      })
    },
    findCostType() {
      this.classCode = '1007'
      getDictDataByPid(this.classCode).then(res => {
        this.costTypeList = res.content
      })
    },
    findTypeCode() {
      this.classCode = '1002'
      getDictDataByPid(this.classCode).then(res => {
        this.typeCodeOptions = res.content
      })
    },
    findSampleBalApplyHandStatus() {
      this.classCode = '1027'
      getDictDataByPid(this.classCode).then(res => {
        this.handStatusList = res.content
      })
    },
    getDeptAllData() {
      var deptNo = ''
      getDeptAllData(deptNo).then(res => {
        this.deptNoListData = res.content
      })
    },
    valFormatter(list, val) {
      if (val === null) {
        return null
      }
      for (var i = 0; i < list.length; i++) {
        if (list[i].code === val) {
          return list[i].codeName
        }
      }
    },
    handleScopeChange(val) {
      console.log('样品结转画面所选部门=>', val)
      this.sampleBalForm.deptNos = val
    },
    againBal() {
      this.$confirm('此操作将进行数据重新结转, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.multipleSelection2.length > 1) {
          this.$message({
            message: '不可同时结转多条',
            type: 'warning'
          })
        } else if (this.multipleSelection2.length === 0) {
          this.$message({
            message: '请选择需要结转的数据.',
            type: 'warning'
          })
        } else {
          this.againBalBtu = true
          this.resetSplitForm()
          this.splitForm.id = this.multipleSelection2[0].id
          this.splitForm.userNo = this.$store.getters.position.psnsmcId
          againBal(this.splitForm).then(res => {
            if (res.success) {
              this.$notify({
                title: '成功',
                message: res.message,
                type: 'success'
              })
              this.fetchList2()
              this.againBalBtu = false
            } else {
              this.$notify.error({
                title: '错误',
                message: '结转有误.' + res.message
              })
              this.againBalBtu = false
            }
          }).catch(error => {
            this.$notify.error({
              title: '错误',
              message: '结转有误.' + error
            })
            this.againBalBtu = false
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作.'
        })
      })
    },
    splitBtu() {
      if (this.multipleSelection2.length > 1) {
        this.resetSplitForm()
        this.disabled = true
        this.dialogSplitFormVisible = true
        this.splitForm.ids = this.multipleSelection2.map(item => item.id + '')
        this.splitForm.deptNo = this.multipleSelection2[0].deptDesc
        this.splitForm.userNo = this.$store.getters.position.psnsmcId
        this.getDeptAllData()
      } else if (this.multipleSelection2.length === 0) {
        this.$message({
          message: '请选择需要结转的数据.',
          type: 'warning'
        })
      } else {
        this.resetSplitForm()
        this.disabled = false
        this.dialogSplitFormVisible = true
        this.splitForm.quantity = this.multipleSelection2[0].quantity
        this.splitForm.ids = this.multipleSelection2.map(item => item.id + '')
        this.splitForm.deptNo = this.multipleSelection2[0].deptDesc
        this.splitForm.userNo = this.$store.getters.position.psnsmcId
        this.getDeptAllData()
      }
    },
    // 取消转销售开票
    cancelTurnSalesInvoice() {
      this.$confirm('此操作将取消转销售开票, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.multipleSelection2.length === 0) {
          this.$message({
            message: '请选择需要取消的数据.',
            type: 'warning'
          })
        } else {
          this.resetSplitForm()
          this.splitForm.ids = this.multipleSelection2.map(item => item.id + '')
          this.cancelTurnSalesBtuLoading = true
          cancelTurnSalesInvoice(this.splitForm).then(res => {
            this.cancelTurnSalesBtuLoading = false
            if (res.success) {
              this.$notify({
                title: '成功',
                message: res.content,
                type: 'success'
              })
              this.fetchList2()
            } else {
              this.$notify.error({
                title: '错误',
                message: '取消有误.' + res.message
              })
            }
          }).catch(error => {
            console.log(error)
            this.$notify.error({
              title: '错误',
              message: '取消有误.'
            })
          })
        }
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '不进行操作.'
        })
      })
    },
    pushZlzsOrderBalance() {
      this.zlzsExportRequest.startExpDate = ''
      this.zlzsExportRequest.endExpDate = ''
      getZLZSExportTime().then(res => {
        console.log('获取展览展示品导出时间', res)
        if (res.success) {
          this.zlzsExportRequest.startExpDate = res.content.split('=')[0]
          this.zlzsExportRequest.endExpDate = res.content.split('=')[1]
          if (this.isEmptyStr(this.zlzsExportRequest.startExpDate) || this.isEmptyStr(this.zlzsExportRequest.endExpDate)) {
            this.$message.error('请先进行展示品盘点票导出操作.')
          } else {
            const msg = '请核对盘点票的导出日期是否正确\n若不对请重新进行展示品盘点票导出操作\n日期范围: ' + this.zlzsExportRequest.startExpDate + ' 至 ' + this.zlzsExportRequest.endExpDate + '\n是否继续?'
            this.$confirm(msg, '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              pushZlzsOrderBalance(this.zlzsExportRequest).then(res => {
                console.log('发布盘点票 => ', res)
                this.$message.success('正在发布展示品盘点票,请耐心等候...')
                if (res.success) {
                  this.$notify({
                    title: '成功',
                    message: res.content,
                    type: 'success'
                  })
                } else {
                  this.$notify({
                    title: '失败',
                    message: res.message,
                    type: 'error'
                  })
                }
              })
            }).catch(() => {
              this.$message({
                type: 'info',
                message: '已取消发布展示品盘点票'
              })
            })
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    isEmptyStr(s) {
      if (s === undefined || s === null || s === '') {
        return true
      }
      return false
    },
    exportZlzsOrderBalance() {
      this.zlzsOrderBal = true
    },
    zlzsExportBal() {
      if (this.zlzsExportOrderBalTime.length !== 2) {
        this.$message({
          message: '请选择导出的开始时间与结束数据.',
          type: 'warning'
        })
        return
      }
      this.sureExportBalLoading = true
      this.$message.success('已开始导出，请耐心等待')
      this.zlzsExportRequest.startExpDate = this.zlzsExportOrderBalTime[0]
      this.zlzsExportRequest.endExpDate = this.zlzsExportOrderBalTime[1]
      exportZlzsOrderBalance(this.zlzsExportRequest).then(res => {
        console.log('结果打印:=> ', res)
        console.log('size=> ', res.size)
        if (res.size === 0) {
          this.$message({
            duration: 4000,
            message: '根据您选择的时间范围未查出数据,请扩大时间范围.',
            type: 'error'
          })
          this.sureExportBalLoading = false
          return
        }
        const fileName = '样品管理-展览展示管理.zip'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.sureExportBalLoading = false
        this.zlzsOrderBal = false
      }).catch(error => {
        console.error(error)
        this.sureExportBalLoading = false
      })
    },
    zlzsExportBal2() {
      if (this.zlzsExportOrderBalTime.length !== 2) {
        this.$message({
          message: '请选择导出的开始时间与结束数据.',
          type: 'warning'
        })
        return
      }
      this.sureExportBalLoading = true
      this.$message.success('已开始导出，请耐心等待')
      this.zlzsExportRequest.startExpDate = this.zlzsExportOrderBalTime[0]
      this.zlzsExportRequest.endExpDate = this.zlzsExportOrderBalTime[1]
      insertIntoSampleOrderManage(this.zlzsExportRequest).then(res => {
        this.sureExportBalLoading = false
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.fetchList2()
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      }).catch(error => {
        this.sureExportBalLoading = false
        console.log(error)
      })
    },
    getSampleBalTypeByApplyType(applyType) {
      getSampleBalTypeByApplyType(applyType).then(res => {
        this.sampleBalList = res.content
      })
    },
    findStatusCode() {
      this.classCode = '1010'
      getDictDataByPid(this.classCode).then(res => {
        this.statusOptions = res.content
      })
    },
    findCostStatusCode() {
      this.classCode = '1011'
      getDictDataByPid(this.classCode).then(res => {
        this.costStatusOptions = res.content
        this.optCodeOptions = res.content
      })
    },
    applyTimeformatter(row) {
      return this.dayjs(row.applyTime).format('YYYY-MM-DD HH:mm:ss')
    },
    answerTimeformatter(row) {
      return this.dayjs(row.answerTime).format('YYYY-MM-DD HH:mm:ss')
    },
    orderDateformatter(row) {
      return this.dayjs(row.orderDate).format('YYYY-MM-DD HH:mm:ss')
    },
    esarrivalDateformatter(row) {
      return this.dayjs(row.esarrivalDate).format('YYYY-MM-DD HH:mm:ss')
    },
    orderNofromatter(row) {
      if (row.orderNo !== null && row.itemNo !== null) {
        return row.orderNo + '-' + row.itemNo
      }
    },
    optTimeformatter(row) {
      if (row.optDate === null) {
        return ''
      }
      return this.dayjs(row.optDate).format('YYYY-MM-DD HH:mm:ss')
    },
    inTimeformatter(row) {
      if (row.inDate === null) {
        return ''
      }
      return this.dayjs(row.inDate).format('YYYY-MM-DD HH:mm:ss')
    },
    expTimeformatter(row) {
      if (row.expDate === null) {
        return ''
      }
      return this.dayjs(row.expDate).format('YYYY-MM-DD HH:mm:ss')
    },
    balTypeFormatter(row) {
      return this.valFormatter(this.costTypeList, row.balType)
    },
    applyTypeFormatter(row) {
      if (row.appType === null) {
        return ''
      }
      return this.valFormatter(this.applyTypelist, row.appType.trim())
    },
    statusformatter(row) {
      return this.valFormatter(this.statusOptions, row.status + '')
    },
    costStatusformatter(row) {
      return this.valFormatter(this.costStatusOptions, row.costStatus + '')
    },
    optCodeformatter(row) {
      return this.valFormatter(this.costStatusOptions, row.optCode + '')
    },
    searchData(val) {
      this.page.pageNumber = 1
      this.fetchList(val)
      // this.reset()
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
    searchSampleBalData() {
      this.page.pageNumber = 1
      this.fetchList2()
    },
    typeCodeFormatter(row) {
      if (row.ordType === null) {
        return null
      }
      switch (row.ordType) {
        case null:
          return this.valFormatter(this.typeCodeOptions, row.ordType.toString())
        default:
          return this.valFormatter(this.typeCodeOptions, row.ordType.toString())
      }
    },
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    aa() {},
    handdle(row, event, column) {
      this.dialogTableVisible = true
    },
    reset() {
      this.searchForm = {
        // orderNo: '', // 订单号
        // applyNo: '', // 申请号
        // status: '', // 状态
        applyDeptNo: [], // 申请部门
        modelNo: '', // 型号
        corderNo: '', // 客户订单号
        cmodelNo: '',
        userNo: '', // 用户代码
        applyTimeStart: '', // 申请时间
        applyTimeEnd: '',
        orderDateStart: '', // 订单生成时间
        orderDateEnd: '',
        answerTimeStart: '', // 处理时间
        answerTimeEnd: ''
      }
      this.sampleOrderDate = ''
      this.daySelectVal = ''
    },
    searchTime() {
      if (this.daySelectVal === 'applyTime') {
        this.searchForm.applyTimeStart = this.dayjs(this.sampleOrderDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.applyTimeEnd = this.dayjs(this.sampleOrderDate[1]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.orderDateStart = ''
        this.searchForm.orderDateEnd = ''
        this.searchForm.answerTimeStart = ''
        this.searchForm.answerTimeEnd = ''
      } else if (this.daySelectVal === 'orderDate') {
        this.searchForm.orderDateStart = this.dayjs(this.sampleOrderDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.orderDateEnd = this.dayjs(this.sampleOrderDate[1]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.applyTimeStart = ''
        this.searchForm.applyTimeEnd = ''
        this.searchForm.answerTimeStart = ''
        this.searchForm.answerTimeEnd = ''
      } else if (this.daySelectVal === 'answerTime') {
        this.searchForm.orderDateStart = ''
        this.searchForm.orderDateEnd = ''
        this.searchForm.applyTimeStart = ''
        this.searchForm.applyTimeEnd = ''
        this.searchForm.answerTimeStart = this.dayjs(this.sampleOrderDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.answerTimeEnd = this.dayjs(this.sampleOrderDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.searchForm.applyTimeStart = ''
        this.searchForm.applyTimeEnd = ''
        this.searchForm.orderDateStart = ''
        this.searchForm.orderDateEnd = ''
        this.searchForm.answerTimeStart = ''
        this.searchForm.answerTimeEnd = ''
      }
    },
    searchSampleBalTime() {
      if (this.samplebalDaySelVal === 'optDate') {
        this.sampleBalForm.optDateStart = this.dayjs(this.samplebalSearchDay[0]).format('YYYY-MM-DD HH:mm:ss')
        this.sampleBalForm.optDateEnd = this.dayjs(this.samplebalSearchDay[1]).format('YYYY-MM-DD HH:mm:ss')
        this.sampleBalForm.inDateStart = ''
        this.sampleBalForm.inDateEnd = ''
        this.sampleBalForm.expDateStart = ''
        this.sampleBalForm.expDateEnd = ''
        this.sampleBalForm.creDateStart = ''
        this.sampleBalForm.creDateEnd = ''
      } else if (this.samplebalDaySelVal === 'expDate') {
        this.sampleBalForm.expDateStart = this.dayjs(this.samplebalSearchDay[0]).format('YYYY-MM-DD HH:mm:ss')
        this.sampleBalForm.expDateEnd = this.dayjs(this.samplebalSearchDay[1]).format('YYYY-MM-DD HH:mm:ss')
        this.sampleBalForm.optDateStart = ''
        this.sampleBalForm.optDateEnd = ''
        this.sampleBalForm.inDateStart = ''
        this.sampleBalForm.inDateEnd = ''
        this.sampleBalForm.creDateStart = ''
        this.sampleBalForm.creDateEnd = ''
      } else if (this.samplebalDaySelVal === 'inDate') {
        this.sampleBalForm.optDateStart = ''
        this.sampleBalForm.optDateEnd = ''
        this.sampleBalForm.expDateStart = ''
        this.sampleBalForm.expDateEnd = ''
        this.sampleBalForm.creDateStart = ''
        this.sampleBalForm.creDateEnd = ''
        this.sampleBalForm.inDateStart = this.dayjs(this.samplebalSearchDay[0]).format('YYYY-MM-DD HH:mm:ss')
        this.sampleBalForm.inDateEnd = this.dayjs(this.samplebalSearchDay[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.samplebalDaySelVal === 'creDate') {
        this.sampleBalForm.optDateStart = ''
        this.sampleBalForm.optDateEnd = ''
        this.sampleBalForm.expDateStart = ''
        this.sampleBalForm.expDateEnd = ''
        this.sampleBalForm.inDateStart = ''
        this.sampleBalForm.inDateEnd = ''
        this.sampleBalForm.creDateStart = this.dayjs(this.samplebalSearchDay[0]).format('YYYY-MM-DD HH:mm:ss')
        this.sampleBalForm.creDateEnd = this.dayjs(this.samplebalSearchDay[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.sampleBalForm.optDateStart = ''
        this.sampleBalForm.optDateEnd = ''
        this.sampleBalForm.expDateStart = ''
        this.sampleBalForm.expDateEnd = ''
        this.sampleBalForm.inDateStart = ''
        this.sampleBalForm.inDateEnd = ''
        this.sampleBalForm.creDateStart = ''
        this.sampleBalForm.creDateEnd = ''
      }
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    handleSelectionChangeCturnTab(val) {
      this.multipleSelection2 = val
    },
    handdleCturnTab() {},
    createOrders() {
      this.loadingBtu = true
      this.strApplyId = ''
      this.multipleSelection.forEach(s => {
        this.strApplyId += s.applyId + ','
      })
      this.strApplyId = this.strApplyId.substring(0, this.strApplyId.length - 1)
      this.SampleOrderParams.applyIds = this.strApplyId.split(',')
      createOrder(this.SampleOrderParams).then(res => {
        this.loadingBtu = false
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: res.message,
            type: 'success'
          })
          this.fetchList()
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      }).catch(() => {
        this.loadingBtu = false
      })
    },

    rebackOrders(SampleOrderParams) {
      this.$refs[SampleOrderParams].validate((valid) => {
        if (valid) {
          this.dialogFormVisible = false
          this.strApplyId = ''
          if (this.multipleSelection.length === 0) {
            this.$notify.error({
              title: '错误',
              message: '请选择回退订单!'
            })
          } else {
            this.multipleSelection.forEach(s => {
              this.strApplyId += s.applyId + ','
            })
            this.strApplyId = this.strApplyId.substring(0, this.strApplyId.length - 1)
            this.SampleOrderParams.applyIds = this.strApplyId.split(',')
            this.SampleOrderParams.applyIds = Array.from(new Set(this.SampleOrderParams.applyIds))
            this.loadingBtuGoBack = true
            rebackOrder(this.SampleOrderParams).then(res => {
              if (res.success === true) {
                this.$notify({
                  title: '成功',
                  message: res.message,
                  type: 'success'
                })
                this.loadingBtuGoBack = false
                this.fetchList()
              } else {
                this.$notify.error({
                  title: '错误',
                  message: res.message
                })
                this.loadingBtuGoBack = false
              }
            }).catch(() => {
              this.loadingBtuGoBack = false
            })
          }
        }
      })
    },

    goBack() {
      this.dialogFormVisible = true
      this.SampleOrderParams.remark = ''
    },
    handleClick(tab, event) {
      if (tab.name === 'second') {
        this.fetchList2()
      } else if (tab.name === 'first') {
        this.fetchList()
      } else if (tab.name === 'three') {
        this.selSampleBalApplyData()
      }
    },
    exportSampleBalData() {
      this.$confirm('此操作将进行查询数据导出操作, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.exportLoading = true
        this.$message.success('已开始导出，请耐心等待')
        exportSampleBalData(this.sampleBalForm).then(res => {
          const fileName = '样品结转数据.xlsx'
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
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作.'
        })
      })
    },
    downLoadNoBalFileByYearMonth() {
      this.overTimeNoBalDislog = true
    },
    exceDownLoadOverTimeNoBalFile() {
      this.overTimeNoBalBtuLoading = true
      this.$message.success('已开始下载，请耐心等待')
      downLoadNoBalFileByYearMonth(this.overTimeNoBal).then(res => {
        console.log('size=> ', res.size)
        if (res.size === 0) {
          this.$message({
            duration: 4000,
            message: '根据您选择的月份没有可下载清单',
            type: 'error'
          })
          this.overTimeNoBalBtuLoading = false
          return
        }
        const fileName = this.overTimeNoBal + '逾期未结转清单.zip'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.overTimeNoBalBtuLoading = false
        this.overTimeNoBalDislog = false
      }).catch(error => {
        console.error(error)
        this.overTimeNoBalBtuLoading = false
      })
    },
    orderCarryTurn() {
      this.loadingCarryTurnBtu = true
      this.strApplyId = ''
      this.multipleSelection2.forEach(s => {
        this.strApplyId += s.id + ','
      })
      this.strApplyId = this.strApplyId.substring(0, this.strApplyId.length - 1)
      this.samplebal.ids = this.strApplyId.split(',')
      ordCarryTurn(this.samplebal).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: res.message,
            type: 'success'
          })
          this.loadingCarryTurnBtu = false
          this.fetchList2()
        } else {
          this.loadingCarryTurnBtu = false
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      })
    },
    carryTurnSales() {
      console.log('转销售开票')
      this.invoicingSales = !this.invoicingSales
    },
    modelNoClick(row) {
      const item = { modelno: row.modelNo }
      this.productVisible = true
      this.$nextTick(() => {
        this.$refs.ProductSearch.handleSelect(item)
        this.$refs.ProductSearch.productSearchChange()
      })
    },
    billing(invoicingSalesParams) {
      this.$refs[invoicingSalesParams].validate((valid) => {
        if (valid) {
          this.loadingInvoiceSales = true
          this.id = ''
          this.multipleSelection2.forEach(s => {
            this.id += s.id + ','
          })
          this.id = this.id.substring(0, this.id.length - 1)
          this.invoicingSalesParams.id = this.id.split(',')
          invoicingSales(this.invoicingSalesParams).then(res => {
            if (res.success === true) {
              this.$notify({
                title: '成功',
                message: res.message,
                type: 'success'
              })
              this.loadingInvoiceSales = false
              this.fetchList2()
            } else {
              this.loadingInvoiceSales = false
              this.$notify.error({
                title: '错误',
                message: res.message
              })
            }
          })
        } else {
          return false
        }
      })
    },
    resetSearchForm() {
      this.searchForm = {
        orderNo: '', // 订单号
        applyNo: '', // 申请号
        status: '', // 状态
        costStatus: '', // 结转状态
        applyDeptNo: [], // 申请部门
        modelNo: '', // 型号
        corderNo: '', // 客户订单号
        cmodelNo: '',
        userNo: '', // 用户代码
        applyTimeStart: '', // 申请时间
        applyTimeEnd: '',
        orderDateStart: '', // 订单生成时间
        orderDateEnd: '',
        answerTimeStart: '', // 处理时间
        answerTimeEnd: ''
      }
      this.sampleOrderDate = ''
      this.daySelectVal = ''
      this.$refs.selDeptNo.checkedValue = ''
    },
    resetSampleBalForm() {
      this.sampleBalForm = {
        customerNo: '',
        rorderNo: '', // 接单号
        modelNo: '',
        optDateStart: '',
        optDateEnd: '',
        inDateStart: '',
        inDateEnd: '',
        expDateStart: '',
        creDateStart: '',
        creDateEnd: '',
        expDateEnd: '',
        optCode: '', // 处理状态
        applyCode: '' // 申请号
      }
      this.samplebalDaySelVal = ''
      this.samplebalSearchDay = ''
    },
    moreSearchForm() {
      this.isShowSearchSamBalFrom = !this.isShowSearchSamBalFrom
    },
    resetSplitForm() {
      this.splitForm = {
        quantity: '', // 拆分数量
        balType: '', // 结转类型
        id: '',
        deptNo: '',
        userNo: ''
      }
    },
    writeOff() {
      this.resetWriteOffForm()
      if (this.multipleSelection2.length > 1) {
        this.$message.error({
          title: '错误',
          message: '一次操作一条,不可同时操作多条'
        })
        return
      } else if (this.multipleSelection2.length === 1) {
        this.WriteOffZlzsForm.rorderNo = this.multipleSelection2[0].rorderNo
        this.WriteOffZlzsForm.modelNo = this.multipleSelection2[0].modelNo
      }
      this.WriteOffZlzsDialog = true
    },
    sureWriteOff(WriteOffZlzsForm) {
      this.$refs[WriteOffZlzsForm].validate((valid) => {
        if (valid) {
          writeoffForZlzsOrder(this.WriteOffZlzsForm).then(res => {
            console.log(res)
            if (res.success) {
              this.$notify({
                title: '成功',
                message: res.content,
                type: 'success'
              })
              this.WriteOffZlzsDialog = false
              this.fetchList2()
            } else {
              this.$notify.error({
                title: '错误',
                message: res.message
              })
            }
          })
        }
      })
    },
    batchWriteOff() {
      this.uploadFileDialogVisible = true
      this.file = null
    },
    updateZSDeptNo() {
      if (this.multipleSelection2.length > 1) {
        this.$message({
          message: '不可同时操作多条',
          type: 'warning'
        })
        return
      }
      this.getDeptAllData()
      this.upZlzsRcvDeptNoDialog = true
      if (this.multipleSelection2.length === 1) {
        this.upZlzsRcvDeptNoForm.rorderNo = this.multipleSelection2[0].rorderNo
        this.upZlzsRcvDeptNoForm.optUserNo = this.$store.getters.position.psnsmcId
      }
    },
    resetWriteOffForm() {
      this.WriteOffZlzsForm = {
        rorderNo: '',
        modelNo: '',
        qty: '',
        remark: '',
        pcNo: ''
      }
    },
    downLoadExcel() {
      this.$message.success('已开始下载，请耐心等待...')
      downExcelForWriteOff().then(res => {
        const fileName = '展览展示品销账模板.xlsx'
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
        this.$message.warning('导出失败' + error)
      })
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    closeClick2() {
      this.file = null
      this.uploadFileDialogWithUpRcvDeptNo = false
    },
    importData() {
      this.uploadLoading = true
      if (this.file == null) {
        this.uploadLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        importWriteOffData(formData).then(res => {
          this.uploadLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.fetchList2()
            this.file = null
            this.uploadFileDialogVisible = false
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.$message.error('销账失败:' + error)
        })
      }
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    sureUpdateZlzsRcvDeptNo() {
      if (this.isEmptyStr(this.upZlzsRcvDeptNoForm.rorderNo)) {
        this.$message({
          message: '订单号不可为空',
          type: 'warning'
        })
        return
      }
      if (this.isEmptyStr(this.upZlzsRcvDeptNoForm.rcvDeptNo)) {
        this.$message({
          message: '展示品实物所在部门不可为空',
          type: 'warning'
        })
        return
      }
      this.upRcvDeptNoLoad = true
      upZlzsRcvDeptNo(this.upZlzsRcvDeptNoForm).then(res => {
        this.upRcvDeptNoLoad = false
        if (res.success) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.fetchList2()
          this.upZlzsRcvDeptNoDialog = false
        } else {
          this.$notify({
            duration: 5000,
            title: '失败',
            message: res.message,
            type: 'error'
          })
        }
      })
    },
    downLoadExcelWithUpRcvDeptNo() {
      this.$message.success('已开始下载，请耐心等待...')
      downExcelForUpRcvDeptNo().then(res => {
        const fileName = '批量变更展示品实物所在部门模板.xlsx'
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
        this.$message.warning('导出失败' + error)
      })
    },
    importDataWithUpRcvDeptNo() {
      this.impUpRcvDeptNoLoading = true
      if (this.file == null) {
        this.impUpRcvDeptNoLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        batchUpRcvDeptNo(formData).then(res => {
          this.impUpRcvDeptNoLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.fetchList2()
            this.file = null
            this.uploadFileDialogWithUpRcvDeptNo = false
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.$message.error('变更失败:' + error)
        })
      }
    },
    batchUpdateZSDeptNo() {
      this.uploadFileDialogWithUpRcvDeptNo = true
      this.file = null
    }
  }
}
</script>
<style scoped>
.searchHead{
  /* margin-top: 15px; */
  margin-left: 15px;
  width: 100%;
}
.state{
  margin-left: -82%;
}
.costStatus{
  margin-left: -126%;
}
.applyNo{
  margin-left: -40%;
}
.search{
  /* border: 1px dashed #0076BD; */
  /* height: 55px; */
  width: 100%;
}
.searchmoreOrder {
  margin-left: 25px;
  margin-top: 15px;
}
.btu{
  margin-left: 15px;
}
.daypick{
  margin-left: -5px;
}
.cancel{
  margin-left: -10px;
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
  .SampleBalApplyNo {
    margin-left: -60px;
  }
  .SampleBalCustomerNo {
    margin-left: -70px;
  }
  .SampleBalModelNo {
    margin-left: -80px;
  }
  .SampleBalOptCode {
    margin-left: -60px;
  }
  .samplebalDaySelVal {
    margin-left: 15px;
  }
  .salmpleBalBtu {
    position: absolute;
    margin-top: -47px;
    margin-left: 84%;

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
  .searchBtu1 {
    margin-left: -255px;
  }
  .searchMoreColumn {
    margin-left: 15px;
  }
  .searchDeptNoClass{
    margin-left: 20px;
  }
  .costTypeClass {
    margin-left: -88px;
  }
  .applyTypeClass {
    margin-left: -220px;
  }
  .handStatus {
    margin-left: -55px;
  }
  .zlzsExportOrderBalClass {
    width: 900px;
    margin-left: 20%;
  }
  .WriteOffZlzsDialog{
    width: 85%;
  }
  .menu-department /deep/ .el-cascader__search-input{
  min-width: 1px!important;
  height: 0px!important;
}
</style>
