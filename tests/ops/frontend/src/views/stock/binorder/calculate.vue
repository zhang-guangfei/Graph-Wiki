<template>
  <div class="app-container">
    <el-form ref="qryform" :inline="true" :model="qryform" size="small" label-width="100px">
      <el-form-item>
        <el-button plain type="success" size="mini" @click="showNew">New</el-button>
        <el-input v-model="qryform.calcId" size="mini" style="width:80px" placeholder="计算批号" />
        <el-button icon="el-icon-plus" plain type="success" size="mini" @click="showcacdata">选择</el-button>
      </el-form-item>
      <el-form-item>
        <el-select v-model="qryform.calcType" placeholder="请选择" style="width:125px" size="mini" @change="initData(0)">
          <el-option
            v-for="item in binClasssData"
            :key="item.code"
            :value="item.code"
            :label="item.codeName"
          />
        </el-select>
        <el-select v-model="qryform.warehouseCode" placeholder="仓库" size="mini" clearable @change="initData(0)">
          <el-option
            v-for="item in warehouses"
            :key="item.warehouseCode"
            :label="item.warehouseName"
            :value="item.warehouseCode"
          />
        </el-select>
        <!-- <el-input
          v-model="qryform.warehouseCode"
          size="mini"
          style="width:80px"
          placeholder="仓库代码"
        /> -->
        <el-form-item>
          <el-checkbox v-model="qryform.onlyCaculate" size="mini">仅查看计算结果</el-checkbox>
        </el-form-item>
        <!-- <el-input v-if="qryform.calcType==4" v-model="qryform.customerNo" style="width: 90px" size="mini" placeholder="客户" clearable />
        <el-input v-if="qryform.calcType==4" v-model="qryform.propertyID" style="width: 90px" size="mini" placeholder="属性ID" clearable />
        <el-button v-if="qryform.calcType==4" plain type="success" size="mini" @click="showbindata">+</el-button> -->
        <el-button icon="el-icon-s-tools" size="mini" style="width:100px" @click="calculate">
          <span v-loading="caculating" element-loading-text="计算中.." element-loading-spinner="el-icon-loading">{{
            "计算"
          }}</span></el-button>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="qryform.onlyHasOrderQty" size="mini">补货数量大于0</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-select v-model="qryform.status" placeholder="请选择" style="width:125px" size="mini" @change="initData(0)">
          <el-option
            v-for="item in statusOptions"
            :key="item.id"
            :value="item.id"
            :label="item.name"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-input v-model="qryform.appId" size="mini" style="width:80px" placeholder="申请号" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="qryform.modelNo" size="mini" style="width:130px" placeholder="型号" />
      </el-form-item>
      <el-form-item>
        <el-input v-model="qryform.orderNo" size="mini" style="width:130px" placeholder="订单号" />
      </el-form-item>
      <el-form-item>
        <el-button v-permission="['732139']" type="primary" icon="el-icon-search" size="mini" @click="initData(0)">查看</el-button>
      </el-form-item>
    </el-form>
    <!-- <el-divider /> -->
    <el-dialog :visible.sync="dialogbinDataVisible" title="客户bin选择" width="500px">
      <el-form ref="binDataform" :inline="true" :model="binDataform" size="mini">
        <el-form-item label="客户：" size="mini">
          <el-input v-model="binDataform.customerNo" style="width: 90px" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="searchbinData">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="bindata" :row-style="{height: '0'}" size="mini" style="width: 100%; font-size: 10px" border stripe>
        <el-table-column property="warehouseCode" align="center" label="仓库代码" />
        <el-table-column property="customerNo" align="center" label="客户" />
        <el-table-column property="propertyID" align="center" label="库存属性ID">
          <template slot-scope="scope">
            <el-popover placement="right" width="400">
              库存类型:{{ Inventoryproperty.inventoryTypeCode }};客户:{{ Inventoryproperty.customerNo }};PPL代码:{{ Inventoryproperty.ppl }};
              项目号:{{ Inventoryproperty.projectCode }};客户群代码:{{ Inventoryproperty.groupCustomerNo }}
              <span
                slot="reference"
                title="查看详情"
                style="cursor: pointer;color:#337AB7;"
                @click="showProperty(scope.row)"
              >{{ scope.row.propertyID }}</span>
            </el-popover>
          </template>
        </el-table-column>
        <!--操作栏 -->
        <el-table-column label="操作" width="90px">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="selectbinData(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="binDataform.total > 0"
        :total="binDataform.total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="binDataform.pageNum"
        :limit.sync="binDataform.pageSize"
        @pagination="searchbinData"
      />
    </el-dialog>
    <el-dialog
      :visible.sync="dialogupddlvVisible"
      :close-on-click-modal="false"
      title="批量修改"
      width="360px"
    >
      <el-form ref="dlvForm" :model="dlvForm" label-width="90px">
        <el-form-item prop="selectType" label="选中申请项">
          <el-select
            v-model="dlvForm.selectType"
            placeholder="请选择"
            style="width:125px"
            size="mini"
          >
            <el-option
              v-for="item in selectTypeOptions"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="交货期">
          <el-date-picker
            :picker-options="pickerOptions"
            v-model="dlvForm.dlvDate"
            type="date"
            size="mini"
            placeholder="交货期"
            value-format="yyyy-MM-dd"
            style="width: 130px"
            clearable
          />
        </el-form-item>
        <el-form-item label="运输方式">
          <el-select v-model="dlvForm.transType" placeholder="请选择" style="width:130px" size="mini" clearable>
            <el-option
              v-for="item in getTransIds"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="订单类型">
          <el-select v-model="dlvForm.orderType" placeholder="请选择" style="width:125px" size="mini" clearable>
            <el-option
              v-for="item in orderTypeData"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="采购数量">
          <el-input v-model="dlvForm.poQty" size="mini" style="width:130px" placeholder="采购数量" />
        </el-form-item>
        <el-form-item label="调库数量">
          <el-input v-model="dlvForm.transQty" size="mini" style="width:130px" placeholder="调库数量" />
        </el-form-item>
        <el-form-item label="供应商">
          <el-select v-model="dlvForm.supplierCode" placeholder="请选择" style="width:130px" size="mini" clearable @change="setSupplierInfo">
            <el-option
              v-for="item in supplierData"
              :key="item.id"
              :value="item.id"
              :label="item.name"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogupddlvVisible = false;updateDlv()">确定</el-button>
      </div>
    </el-dialog>

    <el-dialog
      :visible.sync="dialogapplyVisible"
      :close-on-click-modal="false"
      title="申请"
      width="420px"
    >
      <el-form ref="applyForm" :model="applyForm" label-width="90px">
        <el-form-item prop="selectType" label="选中申请项">
          <el-select
            v-model="applyForm.selectType"
            placeholder="请选择"
            style="width:125px"
            size="mini"
          >
            <el-option
              v-for="item in selectTypeOptions"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item prop="price" label="申请备注" label-width="90px">
          <el-input
            :rows="4"
            v-model="applyForm.reason"
            style="width:280px"
            autocomplete="off"
            type="textarea"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="applyData()">
          <span v-loading="applying" element-loading-text="确定中.." element-loading-spinner="el-icon-loading">{{ "确定" }}</span>
        </el-button>
      </div>
    </el-dialog>
    <!-- <el-row>
      <el-button-group>
        <el-button type="success" plain icon="el-icon-search" title="查看" size="medium" @click="initData"/>
        <el-button type="warning" icon="el-icon-s-fold" title="设计交货期" size="medium" />
        <el-button type="success" icon="el-icon-check" title="申请" size="medium" />
      </el-button-group>
    </el-row>-->
    <el-dialog
      :visible.sync="dialogMoreQryDialogVisible"
      :close-on-click-modal="false"
      title="筛选"
      width="460px"
    >
      <div>
        <div style="margin-bottom: 5px">
          <el-select
            v-model="moreqryForm.qryitem"
            placeholder="请选择"
            style="width:120px"
            size="mini"
          >
            <el-option
              v-for="item in selectqryitemOptions"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
          <el-select
            v-model="moreqryForm.qryType"
            placeholder="请选择"
            style="width:80px"
            size="mini"
          >
            <el-option
              v-for="item in selectqryTypeOptions"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
          <el-input v-model="moreqryForm.qryContent" size="mini" style="width:138px" placeholder="筛选条件" />
          <el-button size="mini" @click="addqryData" >+</el-button>
        </div>
        <el-button type="primary" size="mini" @click="deletemoreqry">删除</el-button>
        <el-button type="primary" size="mini" @click="dialogMoreQryDialogVisible = false;moreseach()">筛选</el-button>
        <el-table
          ref="multipleTable"
          :data="moreqrys"
          :cell-style="{ padding: '5px' }"
          :row-class-name="tableRowClassName"
          size="mini"
          border
          stripe
          style="width: 100%;margin-top: 5px;margin-bottom: 10px"
          @selection-change="handlemoreqrySelection"
        >
          <!-- 表头字段 -->
          <el-table-column type="selection" width="55" />
          <el-table-column :formatter="qryitemFormatter" prop="qryitem" label="字段" width="90px" />
          <el-table-column prop="qryType" label="条件" width="65px" />
          <el-table-column prop="qryContent" label="条件内容" width="130px" />
        </el-table>
      </div>
      <!-- <div slot="footer" class="dialog-footer">
      </div> -->
    </el-dialog>
    <el-row class="row-button">
      <el-button v-permission="['362393']" type="primary" size="mini" @click="updateDlvDate">批量修改</el-button>
      <el-button v-permission="['362393']" type="primary" size="mini" @click="toApply">申请</el-button>
      <el-button v-permission="['362393']" type="primary" size="mini" @click="showAddDetailDialog" >批量添加</el-button>
      <el-button v-permission="['362393']" type="primary" size="mini" @click="clearTransQty" >清除调库数量</el-button>
      <el-button v-permission="['362393']" type="primary" size="mini" @click="showMoreQryDialog" >筛选</el-button>
      <el-button v-permission="['362393']" :loading="exportLoading" type="primary" size="mini" @click="exportOrder" >导出</el-button>
      <div style="position:absolute;right:250px; top: 0px" >
        <span style="font-size: 12px">公司库存控制最大月数</span>
        <el-input v-model="maxMonth" style="width:60px" size="mini" type="number" max="24" />
        <el-button plain type="primary" size="mini" @click="updateMaxMonth">更改</el-button>
      </div>
      <div style="position:absolute;right:10px; top: 0px" >
        <span style="font-size: 12px">最大总可用月数</span>
        <el-input v-model="maxMonthAll" style="width:60px" size="mini" type="number" max="24" step="any" />
        <el-button plain type="primary" size="mini" @click="updateMaxMonthAll">更改</el-button>
      </div>
    </el-row>
    <!--申请表-->
    <el-table
      ref="multipleTable"
      :data="detailData"
      :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '12px'}"
      size="mini"
      border
      fit
      stripe
      highlight-current-row
      style="width: 100%"
      height="62vh"
      @selection-change="handleSelection"
    >
      <!-- 表头字段 -->
      <el-table-column type="selection" width="40" />

      <el-table-column fixed sortable prop="modelNo" label="型号" width="140" show-overflow-tooltip/>
      <el-table-column fixed sortable prop="confirmQty" label="补货数量" width="95px">
        <template slot-scope="scope">
          <el-link type="primary" @click="showsplit(scope.row)">{{ scope.row.confirmQty }}</el-link>
        </template>
      </el-table-column>

      <el-table-column fixed sortable prop="months" label="可用月数" width="95px"/>

      <el-table-column fixed sortable prop="mean" label="月用量" width="95px">
        <template slot-scope="scope">
          <el-link type="primary" @click="showBindata(scope.row)">{{ scope.row.mean }}</el-link>
        </template>
      </el-table-column>

      <el-table-column fixed sortable prop="freq" label="频率" width="80px">
        <template slot-scope="scope">
          <el-link type="primary" @click="showrate(scope.row)">{{ scope.row.freq }}</el-link>
        </template>
      </el-table-column>
      <el-table-column :formatter="replenishmentFormatter" fixed prop="replenishmentMethod" label="补库方式" width="90px" show-overflow-tooltip/>
      <el-table-column :formatter="orderTypeFormatter" fixed sortable prop="orderType" label="订单类型" width="95px" show-overflow-tooltip/>
      <el-table-column fixed prop="supplierCode" sortable label="供应商" width="95px" show-overflow-tooltip/>
      <el-table-column :formatter="transTypeFormatter" fixed sortable prop="transType" label="运输方式" width="95px" show-overflow-tooltip/>
      <el-table-column slot :formatter="dateFormatter" fixed sortable prop="dlvDate" label="货期" width="90px" show-overflow-tooltip="" />
      <el-table-column fixed sortable prop="poQty" label="采购数量" width="95px" show-overflow-tooltip/>
      <el-table-column fixed sortable prop="transQty" label="调拨数量" width="95px" show-overflow-tooltip/>
      <el-table-column prop="warehouseCode" label="库房" width="65px" show-overflow-tooltip/>
      <el-table-column :formatter="poTypeFormatter" sortable prop="poType" label="采购分类" width="95px" show-overflow-tooltip/>
      <el-table-column prop="propertyId" label="库存属性ID" width="95px" show-overflow-tooltip/>
      <el-table-column prop="customerNo" label="客户代码" show-overflow-tooltip/>
      <el-table-column prop="ppl" label="ppl代码" show-overflow-tooltip/>
      <el-table-column prop="projectNo" label="项目号" show-overflow-tooltip/>
      <el-table-column prop="inventoryTypeCode" label="库存类型代码" width="110px" show-overflow-tooltip/>
      <el-table-column prop="groupCustomerNo" label="客户群号" show-overflow-tooltip/>
      <el-table-column prop="stockQty" label="在库" show-overflow-tooltip/>
      <el-table-column sortable prop="ordingQty" label="订货中" width="95px">
        <template slot-scope="scope">
          <el-popover placement="right" width="800">
            <el-table :data="ordingData" :row-style="{height: '0'}" style="width: 100%;font-size:10px" size="mini">
              <el-table-column property="warehouseCode" align="center" label="仓库" show-overflow-tooltip/>
              <el-table-column property="orderFno" align="center" label="订单号" width="130px" show-overflow-tooltip/>
              <el-table-column property="modelno" align="center" label="型号" show-overflow-tooltip/>
              <el-table-column property="quantity" align="center" label="数量" show-overflow-tooltip/>
              <el-table-column property="orderDate" align="center" label="下单日期" show-overflow-tooltip/>
              <el-table-column property="expDate" align="center" label="交货期" show-overflow-tooltip/>
              <el-table-column property="supplier" align="center" label="供应商" show-overflow-tooltip/>
              <el-table-column property="transType" align="center" label="运输方式" show-overflow-tooltip/>
              <!-- <el-table-column property="inQty" align="center" label="已入库数量" show-overflow-tooltip/> -->
            </el-table>
            <span
              slot="reference"
              title="查看详情"
              style="cursor: pointer;color:#337AB7;"
              @click="showOrding(scope.row)"
            >{{ scope.row.ordingQty }}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column sortable prop="preQty" label="被预约数" width="110px">
        <template slot-scope="scope">
          <el-popover placement="right" width="600">
            <el-table :data="prepareOrder" :row-style="{height: '0'}" style="width: 100%;font-size:10px" size="mini">
              <el-table-column property="poOrderNo" align="center" label="被预约单号" show-overflow-tooltip/>
              <el-table-column property="orderNo" align="center" label="预约单号" show-overflow-tooltip/>
              <el-table-column property="modelno" align="center" label="型号" show-overflow-tooltip/>
              <el-table-column property="quantity" align="center" label="数量" show-overflow-tooltip/>
              <el-table-column property="remark" align="center" label="备注" show-overflow-tooltip/>
            </el-table>
            <span
              slot="reference"
              title="查看详情"
              style="cursor: pointer;color:#337AB7;"
              @click="showPrepareOrder(scope.row)"
            >{{ scope.row.preQty }}</span>
          </el-popover>
        </template>
      </el-table-column>
      <el-table-column
        v-for="column in detailTableHeader"
        :fixed="column.fixed"
        :key="column.prop"
        :prop="column.prop"
        :label="column.label"
        :width="column.width"
        :formatter="column.formatter"
        sortable
        show-overflow-tooltip
      />
      <!--操作栏 -->
      <!-- <el-table-column label="操作" width="140px">
        <template slot-scope="scope">
          <el-button type="primary" size="mini" @click="showApplyDetail(scope.row)">修改</el-button>
        </template>
      </el-table-column>-->
    </el-table>

    <!-- 分页工具 -->
    <pagination
      v-show="qryform.total > 0"
      :total="qryform.total"
      :page-sizes="[10,20, 50, 80,100,200]"
      :page.sync="qryform.pageNum"
      :limit.sync="qryform.pageSize"
      style="margin-top: 0px"
      @pagination="initData(2)"
    />
    <el-divider />
    <!--dialog 点击行-->
    <el-dialog :visible.sync="dialogshoseVisible" title="选择计算批号" width="700px">
      <el-table :data="calculatedata" :row-style="{height: '0'}" border stripe class="dialogTable">
        <el-table-column property="id" align="center" label="计算批量号" />
        <el-table-column property="warehouseCode" align="center" label="仓库代码" />
        <el-table-column :formatter="calcTypeformatter" property="calcType" align="center" label="库存类型" />
        <el-table-column property="calcTime" align="center" label="计算时间" />
        <el-table-column property="calcFinishTime" align="center" label="计算完成时间" />
        <el-table-column property="calcPsn" align="center" label="计算者" />
        <!--操作栏 -->
        <el-table-column label="操作" width="200px">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="selectcalc(scope.row)">选择</el-button>
            <el-button v-if="scope.row.status!==6 && scope.row.status!==4" type="primary" size="mini" @click="finishbinordercalc(scope.row)">结束申请</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog :visible.sync="dialogsplitVisible" title="编辑" width="850px" size="mini">
      <el-row style="margin-bottom: 10px;float:right">
        <el-button size="mini" @click="addsplit" >+</el-button>
        <el-button size="mini" @click="deletesplit" >-</el-button>
        <el-button size="mini" @click="savesplit">提交</el-button>
      </el-row>
      <el-table :data="splitDetail" :row-style="{height: '0'}" border stripe size="mini" style="margin-bottom: 20px">
        <el-table-column property="modelNo" align="center" label="型号" width="140px"/>
        <!-- <template slot-scope="scope">
            <el-input v-model="scope.row.modelNo" placeholder="型号" size="mini"/>
          </template>
        </el-table-column> -->
        <el-table-column property="confirmQty" align="center" label="补货数量">
          <template slot-scope="scope">
            <el-input v-model="scope.row.confirmQty" placeholder="补货数量" size="mini"/>
          </template>
        </el-table-column>
        <el-table-column property="supplierCode" align="center" label="供应商">
          <template slot-scope="scope">
            <el-input v-model="scope.row.supplierCode" placeholder="供应商或库房" size="mini"/>
          </template>
        </el-table-column>
        <el-table-column property="orderType" align="center" label="订单类型">
          <template slot-scope="scope">
            <el-select v-model="scope.row.orderType" placeholder="请选择" style="width:125px" size="mini">
              <el-option
                v-for="item in orderTypeData"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
            <!-- <el-input v-model="scope.row.orderType" placeholder="订单类型" size="mini"/> -->
          </template>
        </el-table-column>
        <el-table-column property="transType" align="center" label="运输方式2">
          <template slot-scope="scope">
            <el-select v-model="scope.row.transType" placeholder="请选择" style="width:125px" size="mini">
              <el-option
                v-for="item in splitDetailTransIds"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
            <!-- <el-input v-model="scope.row.transType" placeholder="运输方式" size="mini"/> -->
          </template>
        </el-table-column>
        <el-table-column :formatter="dateFormatter" property="dlvDate" align="center" label="交货期" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-date-picker
              v-model="scope.row.dlvDate"
              type="date"
              size="mini"
              placeholder="交货期"
              value-format="yyyy-MM-dd"
            />
            <!-- <el-input v-model="scope.row.dlvDate" placeholder="交货期" size="mini" /> -->
          </template>
        </el-table-column>
        <el-table-column property="orderNo" align="center" label="订单" width="110px"/>
        <el-table-column :formatter="splitstatusformatter" property="status" align="center" label="状态" width="100px"/>
        <!--操作栏 -->
        <!-- <el-table-column label="操作" width="90px">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="selectcalc(scope.row)">选择</el-button>
          </template>
        </el-table-column> -->
      </el-table>
      <span style="font-size:12px">在库</span>
      <el-table :data="WwarehouseStock" :row-style="{height: '0'}" border stripe class="dialogTable" size="mini">
        <el-table-column property="warehouseCode" align="center" label="库房" />
        <el-table-column property="avaQty_ty" align="center" label="通用在库" />
        <el-table-column property="avaQty_zy" align="center" label="专用在库" />
        <el-table-column property="qtyBin" align="center" label="Bin数量" />
        <el-table-column property="binCell" align="center" label="Bin数" />
        <el-table-column property="freq" align="center" label="频率" />
        <el-table-column property="mean" align="center" label="月用量" />
        <el-table-column property="months" align="center" label="可用月数" />
        <el-table-column property="excessQty" align="center" label="过剩数量" />
      </el-table>
    </el-dialog>
    <!--dialog 点击行-->
    <!-- <el-dialog :visible.sync="dialoguseVisible" title="月用量明细" width="500px">
      <el-table :data="detaildata" :row-style="{height: '0'}" border stripe class="dialogTable">
        <el-table-column property="warehousecode" align="center" label="月份" />
        <el-table-column property="deptno" align="center" label="用量"/>
        <el-table-column property="modelno" align="center" label="客户数"/>
        <el-table-column property="customerno" align="center" label="订单数" />
      </el-table>
    </el-dialog>-->
    <!--dialog 点击行-->
    <el-dialog :visible.sync="dialograteVisible" title="频率明细" width="1000px">
      <el-table :data="ratedata" :row-style="{height: '0'}" border stripe class="dialogTable">
        <el-table-column align="center" label="最近8个月">
          <el-table-column property="warehousecode" align="center" label="总数量" />
          <el-table-column property="deptno" align="center" label="月数" />
          <el-table-column property="modelno" align="center" label="月平均用量" />
          <el-table-column property="customerno" align="center" label="客户数" />
        </el-table-column>
        <el-table-column align="center" label="最近12个月">
          <el-table-column property="warehousecode" align="center" label="总数量" />
          <el-table-column property="deptno" align="center" label="月数" width="50" />
          <el-table-column property="modelno" align="center" label="月平均用量" />
          <el-table-column property="customerno" align="center" label="客户数" />
        </el-table-column>
        <el-table-column align="center" label="最近36个月">
          <el-table-column property="warehousecode" align="center" label="总数量" />
          <el-table-column property="deptno" align="center" label="月数" />
          <el-table-column property="modelno" align="center" label="月平均用量" />
          <el-table-column property="customerno" align="center" label="客户数" />
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-dialog :visible.sync="dialogAddDetailVisible" title="批量添加" width="350px" >
      <el-form ref="detailForm" :model="detailForm" size="mini" >
        <el-form-item prop="details">
          <el-input :rows="6" v-model="detailForm.details" type="textarea" placeholder="请按：型号,数量,补货方式(0采购，2系统自选) 或excel复制； 多条数据换行" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogAddDetailVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="addDetails">添加</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getDictDataByPid, getDictByClassCodeAndCode, updateDataParam, listWarehouseNoWT } from '@/api/common/dict'
import moment from 'moment'
import { codeFormatter } from '@/api/common/comm'
import Pagination from '@/components/Pagination'
import { listBinOrderDetail, listBinOrderCalc, applyBinOrder, calcBinOrder, updateBinOrderDetail, listBinOrderDetailSplit, listWarehouseStock, listModeldetail, addBinOrderDetails, listCstmBindata, getOpsInventoryProperty, finishbinordercalc, exportCalcBinOrderData, listOrdingOrder, listPrepareOrderView, newBinOrderCalcId, clearTransQty, batchUpdate } from '@/api/stock/binorder'
import { findSupplierInfo } from '@/api/common/supplier'
import { getTransIds } from '@/api/purchaseOrder'
export default {
  name: 'BinOrderCalc',
  components: { Pagination },
  data() {
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7
        }
      },
      statusData: [],
      statusdesc: [],
      warehouses: [],
      ordingData: [],
      supplierData: [],
      prepareOrder: [],
      dialogshoseVisible: false,
      dialoguseVisible: false,
      dialograteVisible: false,
      dialogapplyVisible: false,
      dialogupddlvVisible: false,
      dialogsplitVisible: false,
      dialogAddDetailVisible: false,
      dialogMoreQryDialogVisible: false,
      dialogbinDataVisible: false,
      caculating: false,
      applying: false,
      isCstbin: false,
      exportLoading: false,
      moreqryForm: {
        qryitem: 'model_no',
        qryType: '=',
        qryContent: ''
      },
      moreqrys: [],
      selectqryTypeOptions: [{
        code: '>',
        codeName: '>'
      },
      {
        code: '<',
        codeName: '<'
      },
      {
        code: '=',
        codeName: '='
      },
      {
        code: 'like',
        codeName: 'like'
      }],
      selectqryitemOptions: [{
        code: 'model_no',
        codeName: '型号'
      },
      {
        code: 'model_class',
        codeName: '设定级别'
      },
      {
        code: 'freq',
        codeName: '频率'
      },
      {
        code: 'supplier_code',
        codeName: '供应商'
      },
      {
        code: 'ording_qty',
        codeName: '订货中'
      },
      {
        code: 'pre_qty',
        codeName: '被预约数'
      },
      {
        code: 'confirm_qty',
        codeName: '补货数量'
      },
      {
        code: 'mean',
        codeName: '月用量'
      },
      {
        code: 'months',
        codeName: '可用月数'
      },
      {
        code: 'po_qty',
        codeName: '采购数'
      },
      {
        code: 'trans_qty',
        codeName: '调拨数'
      },
      {
        code: 'qtybin',
        codeName: 'bin数量'
      },
      {
        code: 'bincell',
        codeName: 'bin数'
      },
      {
        code: 'stock_months',
        codeName: '在库可用月数'
      }],
      qryform: {
        calcId: 0,
        calculGroupNo: '',
        todate: '',
        calcType: '1',
        warehouseCode: 'KGZ',
        customerNo: '',
        propertyID: '',
        isGSScalcul: '',
        onlyCaculate: false,
        onlyHasOrderQty: false,
        orderNo: '',
        appId: null,
        modelNo: '',
        status: '',
        moreContent: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      binDataform: {
        stockType: '4',
        customerNo: '',
        propertyID: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      dlvForm: {
        calcId: 0,
        selectType: 1,
        transType: '',
        dlvDate: '',
        poQty: '',
        transQty: '',
        modelNos: [],
        orderType: '',
        ids: [],
        moreWhere: '',
        supplierCode: ''
      },
      applyForm: {
        selectType: 1,
        reason: ''
      },
      detailForm: {
        details: ''
      },
      binClasssData: [],
      orderTypeData: [],
      selectTypeOptions: [{
        code: 1,
        codeName: '仅选中项'
      },
      {
        code: 3,
        codeName: '按筛选条件'
      },
      {
        code: 2,
        codeName: '全部未申请项'
      }],
      statusOptions: [{
        id: 0,
        name: '所有'
      },
      {
        id: 1,
        name: '未申请的'
      },
      {
        id: 2,
        name: '已申请的'
      }],
      // 申请表头
      detailTableHeader: [
        {
          label: '直采',
          prop: 'directpurchase'
        },
        {
          label: '在库可用月数',
          prop: 'stockMonths',
          width: '120px'
        },
        {
          label: 'BIN类型',
          prop: 'binClass',
          formatter: this.binClassformatter,
          width: '100px'
        },
        {
          label: 'BIN数量',
          prop: 'qtybin',
          width: '100px'
        },
        {
          label: 'BIN数',
          prop: 'bincell'
        },
        {
          label: 'E价',
          prop: 'eprice'
        },
        {
          label: '状态',
          prop: 'status',
          formatter: this.statusformatter
          //   formatter: this.statusFormatter
        },
        // {
        //   label: 'BIN数量-公司',
        //   prop: 'qtybinAll'
        // },
        // {
        //   label: 'BIN数-公司',
        //   prop: 'bincellAll'
        // },
        // {
        //   label: '订货中',
        //   prop: 'ordingQty'
        // },
        {
          label: '专备库在库',
          prop: 'specStockQty',
          width: '120px'
        },
        {
          label: '专备库订货中',
          prop: 'specOrdingQty',
          width: '120px'
        },
        {
          label: '上次货期',
          prop: 'lastDlvdate',
          width: '100px'
        },
        {
          label: '级别',
          prop: 'modelClass'
        },
        {
          label: '箱入数',
          prop: 'boxQty',
          width: '100px'
        },
        {
          label: '箱型',
          prop: 'boxNo'
        },
        {
          label: '最小包装',
          prop: 'minPackQty',
          width: '120px'
        },
        {
          label: '备注',
          prop: 'remark'
        },
        {
          label: '订单号',
          prop: 'orderNo',
          width: '95px'
        },
        {
          label: '申请号',
          prop: 'appId',
          width: '95px'
        },
        {
          label: '总在库',
          prop: 'stockQtyAll',
          width: '95px'
        },
        {
          label: '总月均',
          prop: 'meanAll',
          width: '95px'
        },
        {
          label: '总可用月数',
          prop: 'stockMonthsAll',
          width: '120px'
        }
        // {
        //   label: '其他在库',
        //   prop: 'remark27'
        // }
      ],
      Inventoryproperty: '',
      classCode: 2010,
      maxMonthCode: 9002, // 4
      maxMonth: 0, // 总库存控制最大月数
      maxMonthRaw: 0, // 原来总库存控制最大月数

      maxMonthAll: 0, // 最大总可用月数
      maxMonthAllRaw: 0, // 原来最大总可用月数
      // 表格数据
      detailData: [],
      calculatedata: [],
      ratedata: [],
      multipleSelection: [],
      splitDetail: [],
      updDetail: {
        id: 0,
        modelNo: '',
        items: []
      },
      fromid: 0,
      modelNo: '',
      orderType: '',
      initRow: '',
      WwarehouseStock: [],
      transTypeData: [],
      getTransIds: [],
      splitDetailTransIds: [],
      poTypeData: [],
      multiplemoreqrySelection: [],
      gridData: [],
      bindata: [],
      inventoryTypeOptions: []
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal && newVal.name === 'BinOrderCalc' && this.$route.query.active === 'calculatePage') {
          this.qryform.appId = this.$route.query.appid
          this.qryform.calcId = this.$route.query.calcId
          this.qryform.warehouseCode = ''
          this.initData(0)
        }
      }
    }
  },
  created() {
    this.initDictData()
    this.initData(0)
    this.moreqrys = JSON.parse(localStorage.getItem('MoreBinQry'))
  },

  methods: {
    setSupplierInfo() {
      this.getTransIds = []
      this.dlvForm.transType = ''
      var transParam = {}
      transParam.supplierId = this.dlvForm.supplierCode
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
          this.getTransIds = this.transTypeData
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    isshow(val) {
      if (val === '4') {
        this.isCstbin = true
      } else {
        this.isCstbin = false
      }
      return true
    },
    addqryData() {
      if (this.moreqryForm.qryContent === '' || this.moreqryForm.qryContent === null) {
        this.$message.error('筛选内容不能为空！')
        return
      }
      if (this.moreqryForm.qryType === 'like' && this.moreqryForm.qryContent.indexOf('%') === -1) {
        this.$message.error('like模糊查询筛选内容必须包含%！')
        return
      }
      this.moreqrys = []
      this.moreqrys.push(this.moreqryForm)
      var vals = JSON.parse(localStorage.getItem('MoreBinQry'))
      if (vals != null && vals.length > 0) {
        this.moreqrys.push.apply(this.moreqrys, vals)
      }
      localStorage.setItem('MoreBinQry', JSON.stringify(this.moreqrys))
      this.moreqrys = JSON.parse(localStorage.getItem('MoreBinQry'))
    },
    qryitemFormatter(row) {
      return codeFormatter(this.selectqryitemOptions, row.qryitem)
    },
    moreseach() {
      if (this.multiplemoreqrySelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      //   var val = this.multiplemoreqrySelection[0]
      //   console.log(val)
      this.qryform.moreContent = ''
      for (const i in this.multiplemoreqrySelection) {
        var item = this.multiplemoreqrySelection[i]
        if (i === '0') {
          this.qryform.moreContent = item.qryitem + ' ' + item.qryType + "'" + item.qryContent + "'"
        } else {
          this.qryform.moreContent = this.qryform.moreContent + ' and ' + item.qryitem + ' ' + item.qryType + "'" + item.qryContent + "'"
        }
      }
      //   this.qryform.moreContent = val.qryitem + ' ' + val.qryType + "'" + val.qryContent + "'"
      this.initData(1)
    },
    handlemoreqrySelection(val) {
      this.multiplemoreqrySelection = val
    },
    tableRowClassName(row, index) {
      // 给每条数据添加一个索引
      row.row.index = row.rowIndex
    },
    deletemoreqry() {
      if (this.multiplemoreqrySelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      this.multiplemoreqrySelection.forEach((val, index) => {
        this.moreqrys.forEach((v, i) => {
          if (val.index === v.index) {
            this.moreqrys.splice(i, 1)
          }
        })
      })
      localStorage.setItem('MoreBinQry', JSON.stringify(this.moreqrys))
    },
    showMoreQryDialog() {
      this.dialogMoreQryDialogVisible = true
    },
    addDetails() {
      if (this.qryform.warehouses === null || this.qryform.warehouses === '') {
        this.$message.error('仓库代码不能为空')
        return
      }
      let details = this.detailForm.details
      const itemlist = []
      details = (details.substring(details.length - 1) === '\n') ? details.substring(0, details.length - 1) : details
      const infos = details.split('\n')
      infos.forEach(el => {
        var items = el.split('\t').join(',')
        const info = items.split(',')
        const item = {
          calcType: this.qryform.calcType,
          warehouseCode: this.qryform.warehouseCode,
          calcId: this.qryform.calcId,
          modelNo: info[0],
          confirmQty: info[1],
          replenishmentMethod: info[2] == null ? 2 : info[2]
        }
        itemlist.push(item)
      })
      console.log('itemlist => ', itemlist)
      addBinOrderDetails(itemlist).then(result => {
        if (result.success) {
          this.qryform.calcId = result.content
          this.initData(0)
          this.$message.success(result.message)
          this.dialogAddDetailVisible = false
        } else {
          this.$message.error(result.message)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    calculate() {
    //   if (this.qryform.calcType !== '4') {
    //     this.qryform.customerNo = ''
    //     this.qryform.propertyID = 0
    //   }
      if (this.qryform.calcId === '' || this.qryform.calcId === 0) {
        this.$message.error('计算号不能为0或空！')
        return
      }
      if (this.caculating) {
        this.$message.error('还在计算中，请不要重复点击！')
        return
      }
      this.caculating = true
      calcBinOrder(this.qryform).then(result => {
        if (result.success) {
          this.qryform.calcId = result.content.id
          this.$message.success('正在计算中，请稍后再查！')
          this.initData(0)
          this.caculating = false
        } else {
          this.$message.error(result.message)
          this.caculating = false
        }
      }).catch(error => {
        this.caculating = false
        console.log(error)
      })
    },
    showNew() {
      if (this.qryform.warehouseCode === '') {
        this.$message.error('请输入仓库代码再计算！')
        return
      }
      newBinOrderCalcId(this.qryform).then(result => {
        if (result.success) {
          this.qryform.calcId = result.content.id
          this.$message.success('new计算号成功，请计算！')
        } else {
          this.$message.error(result.message)
        }
      }).catch(error => {
        this.$message.error(error)
        console.log(error)
      })
    },
    orderTypeFormatter(row) {
      if (row.orderType != null) {
        if (row.orderType === '9') {
          return '拆分单'
        }
        return codeFormatter(this.orderTypeData, row.orderType)
      }
    },
    transTypeFormatter(row) {
      if (row.transType != null) {
        return codeFormatter(this.transTypeData, row.transType)
      }
    },
    replenishmentFormatter(row) {
      if (row.replenishmentMethod != null) {
        if (row.replenishmentMethod === 0) {
          return '采购'
        } else if (row.replenishmentMethod === 1) {
          return '调拨'
        } else if (row.replenishmentMethod === 2) {
          return '系统自选'
        }
      }
    },
    poTypeFormatter(row) {
      if (row.poType != null) {
        return codeFormatter(this.poTypeData, row.poType)
      }
    },
    showAddDetailDialog() {
      this.dialogAddDetailVisible = true
    },
    selectcalc(row) {
      this.qryform.calcId = row.id.toString()
      this.qryform.calcType = row.calcType === null ? '' : row.calcType.toString()
      //   if (this.qryform.calcType === 4) {
      //     this.qryform.customerNo = row.customerNo
      //     this.qryform.propertyID = row.propertyID
      //   }
      this.qryform.warehouseCode = row.warehouseCode
      this.qryform.appId = ''
      this.initData(0)
      this.dialogshoseVisible = false
    },
    finishbinordercalc(row) {
      finishbinordercalc(row.id).then(result => {
        if (result.success) {
          this.$message.success(result.content)
          this.dialogshoseVisible = false
        } else {
          this.$message.success(result.message)
          this.dialogshoseVisible = false
        }
      }).catch(error => {
        console.log(error)
      })
    },
    exportOrder() {
      this.exportLoading = true
      const expdate = this.dayjs(new Date()).format('YYYY-') + this.qryform.calcId
      exportCalcBinOrderData(this.qryform).then(res => {
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
        this.exportLoading = false
      }).catch(error => {
        console.error(error)
        this.exportLoading = false
      })
    },
    showmean(row) {
      const data = {
        modelNo: row.modelNo,
        stockcode: row.warehouseCode
      }
      listModeldetail(data).then(result => {
        var data = result.content
        this.gridData = data
      }).catch(error => {
        console.log(error)
      })
    },
    showOrding(row) {
      // id不能缺，其他参数可有可无
      const data = {
        modelNo: row.modelNo,
        warehouseCode: row.warehouseCode,
        detailId: row.id
      }
      listOrdingOrder(data).then(result => {
        var data = result.content
        this.ordingData = data
      }).catch(error => {
        console.log(error)
      })
    },
    showPrepareOrder(row) {
      // id不能缺，其他参数可有可无
      const data = {
        modelNo: row.modelNo,
        warehouseCode: row.warehouseCode,
        detailId: row.id
      }
      listPrepareOrderView(data).then(result => {
        var data = result.content
        this.prepareOrder = data
      }).catch(error => {
        console.log(error)
      })
    },
    showProperty(row) {
      const data = {
        id: row.propertyID
      }
      getOpsInventoryProperty(data).then(result => {
        this.Inventoryproperty = result.content
        this.Inventoryproperty.inventoryTypeCode = codeFormatter(this.inventoryTypeOptions, this.Inventoryproperty.inventoryTypeCode)
        // var data = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    showsplit(row) {
      this.splitDetailTransIds = []
      var transParam = {}
      transParam.supplierId = null
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      getTransIds(transParam).then(res => {
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.splitDetailTransIds.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          this.splitDetailTransIds = this.transTypeData
        }
      }).catch(error => {
        this.$message.error(error.message)
      })

      this.initRow = row
      this.splitDetail = []
      this.fromid = row.id
      this.modelNo = row.modelNo
      this.orderType = row.orderType
      var excessQtyJsonArray = row.excessQty
      // var excessQtyJsonArray = '{"KTJ":0,"KSH":30,"SCZ":33047,"KBJ":1848,"KGZ":49254}'
      listBinOrderDetailSplit(this.fromid).then(result => {
        if (result.content != null && result.content.length > 0) {
          result.content.forEach(item => {
            if (item.orderNo === null || item.orderNo === undefined) {
              item.orderNo = ''
            } else {
              item.orderNo = item.orderNo + '-' + item.itemNo
            }
          })
          this.splitDetail.push.apply(this.splitDetail, result.content)
        }
      }).catch(error => {
        console.log(error)
      })
      // 仓库在库数
      this.WwarehouseStock = []
      listWarehouseStock(row.modelNo).then(result => {
        if (result.content != null && result.content.length > 0) {
          this.WwarehouseStock = result.content
          // WwarehouseStock赋值过剩数量
          excessQtyJsonArray = JSON.parse(excessQtyJsonArray)

          this.WwarehouseStock.forEach(item => {
            item.excessQty = 0
            if (excessQtyJsonArray[item.warehouseCode] != null) {
              item.excessQty = excessQtyJsonArray[item.warehouseCode]
            }
          })
        }
      }).catch(error => {
        console.log(error)
      })
      this.dialogsplitVisible = true
    },
    addsplit() {
      this.splitDetail.push({
        id: 0,
        confirmQty: 0,
        modelNo: this.modelNo,
        supplierCode: '',
        orderType: '',
        transType: '',
        dlvDate: '',
        orderNo: '',
        status: ''
      })
    },
    savesplit() {
      this.updDetail.id = this.fromid
      this.updDetail.modelNo = this.modelNo
      this.updDetail.orderType = this.orderType
      this.updDetail.items = this.splitDetail
      updateBinOrderDetail(this.updDetail).then(result => {
        if (result.success) {
          this.$message.success(result.message)
          this.initData(0)
          this.dialogsplitVisible = false
        } else {
          this.$message.error(result.message)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    deletesplit() {
      this.splitDetail.splice(this.splitDetail.length - 1, 1)
    },
    showcacdata() {
      listBinOrderCalc().then(result => {
        this.calculatedata = result.content
      }).catch(error => {
        console.log(error)
      })
      this.dialogshoseVisible = true
    },
    toApply() {
      this.dialogapplyVisible = true
    },
    updateDlv() {
      this.dlvForm.calcId = this.qryform.calcId
      this.dlvForm.modelNos = []
      this.dlvForm.ids = []
      this.dlvForm.moreWhere = ''
      if (this.dlvForm.selectType === 1) {
        if (this.multipleSelection.length === 0) {
          this.$message({
            type: 'warning',
            message: '仅申请选中项一定要选择数据'
          })
          return
        }
        for (const i in this.multipleSelection) {
          var item = this.multipleSelection[i]
          if (item.confirmQty <= 0) {
            this.$message.error('存在' + item.modelNo + '的补货数量为0')
            return
          }
          this.dlvForm.modelNos.push(item.modelNo)
          this.dlvForm.ids.push(item.id)
        }
      }
      if (this.dlvForm.selectType === 3) {
        this.dlvForm.moreWhere = this.qryform.moreContent
      }

      if (this.dlvForm.selectType !== 1) {
        this.$message.info('已提交批量修改，如果项数过多请过后再查询')
      }

      batchUpdate(this.dlvForm)
        .then(res => {
          if (res.success) {
            this.initData(0)
            this.$message.success(res.message)
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    applyData() {
      if (this.applying === true) {
        return this.$message.error('不能重复申请')
      }
      this.applying = true
      var allHasOrdQtyItem = true
      const ids = []
      if (this.applyForm.selectType === 1) {
        allHasOrdQtyItem = false
        if (this.multipleSelection.length === 0) {
          this.$message({
            type: 'warning',
            message: '仅申请选中项一定要选择数据'
          })
          this.applying = false
          return
        }
        for (const i in this.multipleSelection) {
          var item = this.multipleSelection[i]
          if (item.confirmQty <= 0) {
            this.$message.error('存在' + item.modelNo + '的补货数量为0')
            this.applying = false
            return
          }
          ids.push(item.id)
        }
      }
      //   var queryDTO = this.qryform
      //   if (this.applyForm.selectType !== 3) {
      //     queryDTO = null
      //   }
      const data = {
        calcId: this.qryform.calcId,
        applyText: this.applyForm.reason,
        allHasOrdQtyItem: allHasOrdQtyItem,
        // modelNos: modelNos,
        selectType: this.applyForm.selectType,
        queryDTO: this.qryform,
        stockType: this.qryform.calcType,
        ids: ids
      }
      applyBinOrder(data)
        .then(res => {
          if (res.success) {
            this.applying = false
            this.initData(0)
            this.$message.success(res.content)
            this.dialogapplyVisible = false
          } else {
            this.applying = false
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.applying = false
          this.$message.error(error + '请稍后再常试')
        })
    //   this.$message.error('申请失败')
    },
    handleSelection(val) {
      this.multipleSelection = val
    },
    updateDlvDate() {
      this.getTransIds = []
      var transParam = {}
      transParam.supplierId = null
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
          this.getTransIds = this.transTypeData
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      this.dialogupddlvVisible = true
    },
    clearTransQty() {
      if (!this.qryform.calcId) {
        return
      }
      this.$confirm('确认要取消' + this.qryform.calcId + '所有调库数量吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        clearTransQty(this.qryform.calcId).then(result => {
          this.$message.success(result.message)
        }).catch(error => {
          this.$message.error(error)
        })
      })
    },
    initDictData() {
      getDictByClassCodeAndCode(this.maxMonthCode, 4).then(result => {
        this.maxMonth = result.content.extNote1
        this.maxMonthRaw = this.maxMonth
        this.maxMonthAll = result.content.extNote2
        this.maxMonthAllRaw = this.maxMonthAll
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.classCode).then(result => {
        this.binClasssData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2029').then(result => {
        this.orderTypeData = result.content
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
            this.transTypeData.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      getDictDataByPid('2024').then(result => {
        this.poTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2001').then(result => {
        this.inventoryTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2028').then(result => {
        this.statusdesc = result.content
      }).catch(error => {
        console.log(error)
      })
      listWarehouseNoWT().then(result => {
        this.warehouses = result.content
      }).catch(error => {
        console.log(error)
      })
      findSupplierInfo().then(result => {
        this.supplierData = result.content
      })
    },
    showuse(modelNo) {
      this.dialoguseVisible = true
    },
    showBindata(row) {
      this.$router.push('')
      this.$router.push({
        name: 'BinDataManage',
        query: {
          modelNo: row.modelNo,
          warehouseCode: row.warehouseCode,
          stockType: row.calcType
        }
      })
    //   console.log(modelNo)
    //   this.dialograteVisible = true
    },
    showrate(row) {
      this.$router.push('')
      this.$router.push({
        name: 'BinCalculate',
        query: {
          active: 'salesratePage',
          modelNo: row.modelNo,
          warehouseCode: row.warehouseCode,
          stockType: row.stockType
        }
      })
    //   console.log(modelNo)
    //   this.dialograteVisible = true
    },
    // 菜单数据初始化
    initData(flag) {
      if (flag === 0) {
        this.qryform.moreContent = ''
      }
      this.isshow(this.qryform.calcType)
      listBinOrderDetail(this.qryform).then(result => {
        this.detailData = result.content.list
        this.qryform.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    searchbinData() {
      listCstmBindata(this.binDataform).then(result => {
        this.bindata = result.content.list
        this.binDataform.total = result.content.total
      }).catch(error => {
        console.log(error)
      })
    },
    showbindata() {
      this.dialogbinDataVisible = true
      this.searchbinData()
    },
    selectbinData(row) {
      this.qryform.propertyID = row.propertyID
      this.qryform.customerNo = row.customerNo
      this.dialogbinDataVisible = false
    },
    showApplyDetail(row) {
    },
    // 获取表格数据
    getapplyData() {
      console.log('查询条件：')
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
    //    <!--add by C12961  bin补库增加修改最大总可用月数 -->
    updateMaxMonthAll() {
      if (this.maxMonthAll === this.maxMonthAllRaw) {
        this.$message.error('数据没有变动，不必更新！')
        return
      }
      const param = {
        classCode: this.maxMonthCode,
        code: '4',
        extNote2: this.maxMonthAll
      }
      updateDataParam(param).then(result => {
        this.maxMonthAllRaw = this.maxMonthAll
        this.$message.success('更新成功！')
      }).catch(error => {
        console.log(error)
        this.$message.error('更新失败！')
      })
    },
    //    <!--add by WuWeiDong 20230327 bug 10113 bin补库增加修改控制的最大月数 -->
    updateMaxMonth() {
      if (this.maxMonth === this.maxMonthRaw) {
        this.$message.error('数据没有变动，不必更新！')
        return
      }
      const param = {
        classCode: this.maxMonthCode,
        code: '4',
        extNote1: this.maxMonth
      }
      updateDataParam(param).then(result => {
        this.maxMonthRaw = this.maxMonth
        this.$message.success('更新成功！')
      }).catch(error => {
        console.log(error)
        this.$message.error('更新失败！')
      })
    },
    dateFormatter: function(row) {
      if (row.dlvDate === null || row.dlvDate === '') {
        return ''
      } else {
        return moment(row.dlvDate).format('MM-DD')
      }
    },
    binClassformatter: function(row) {
      return codeFormatter(this.binClasssData, row.binClass)
    },
    calcTypeformatter: function(row) {
      return codeFormatter(this.binClasssData, row.calcType)
    },
    statusformatter(row) {
      return codeFormatter(this.statusdesc, row.status)
    },
    splitstatusformatter(row) {
      return codeFormatter(this.statusdesc, row.status)
    }
    // statusFormatter(row, column, cellValue, index, menu) {
    //   return this.statusData.find(item => item.code === cellValue).desc
    // }
  }
}
</script>
<style scoped>
 .el-table /deep/ .el-table__body-wrapper{
    z-index: 2
  }
</style>
