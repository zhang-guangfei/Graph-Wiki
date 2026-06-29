<template>
  <div class="app-container">
    <el-divider>
      <el-radio-group v-model="radio1" @change="changeRadio(radio1)">
        <el-tooltip effect="dark" content="SHIKOMI拦截~" placement="left-start">
          <el-radio-button :disabled="shikomiIntercept" label="shikomi" style=".el-radio-button__inner{ font-size: 30px }"><i class="el-icon-search"/>SHIKOMI拦截</el-radio-button>
        </el-tooltip>
        <el-tooltip effect="dark" content="其它原因拦截~" placement="right-start">
          <el-radio-button :disabled="otherIntercept" label="other" style=".el-radio-button__inner{ font-size: 30px }"><i class="el-icon-user"/>其它原因拦截</el-radio-button>
        </el-tooltip>
      </el-radio-group>
    </el-divider>
    <div class="filter-containers">
      <div v-show="radio1 === 'shikomi'">
        <el-card>
          <div>
            <el-form ref="formShikomi" :model="formShikomi" :inline="true">
              <el-form-item label="请购单号：">
                <el-input v-model="formShikomi.orderno" placeholder="请输入请购单号" style="width=100%" clearable size="medium" @keyup.enter.native="getSHIKOMI" />
              </el-form-item>
              <el-form-item label="型号：">
                <el-input v-model="formShikomi.modelno" placeholder="请输入型号" clearable size="medium" @keyup.enter.native="getSHIKOMI"/>
              </el-form-item>
              <el-form-item label="客户:">
                <el-input v-model="formShikomi.customerno" placeholder="请输入客户代码" clearable size="medium" @keyup.enter.native="getSHIKOMI"/>
              </el-form-item>
              <el-form-item label="拦截原因：">
                <el-input v-model="formShikomi.interceptmsg" placeholder="请输入拦截原因" clearable size="medium" @keyup.enter.native="getSHIKOMI"/>
              </el-form-item>
              <span class="operation-button">
                <el-form-item>
                  <!-- <el-tooltip effect="light" content="重置" placement="top">
                  <el-button type="info" icon="el-icon-close" size="medium" circle @click="resetForm()"/>
                </el-tooltip> -->
                  <el-button v-permission="['896417']" class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getSHIKOMI()">查询</el-button>
                  <el-button v-permission="['872776']" class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="openSonItemExport">导出</el-button>
                  <el-tooltip effect="light" content="展开" placement="top">
                    <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
                  </el-tooltip>
                </el-form-item>
              </span>
            </el-form>
          </div>
          <div v-if="searchMoreFormShikomi" class="search">
            <el-form ref="formShikomi" :model="formShikomi" :inline="true">
              <el-form-item label="营业所" style="margin-left: 11px">
                <!-- <el-cascader
                  ref ="deptCascader"
                  :props="props"
                  :options="scopeOptions"
                  :show-all-levels="false"
                  collapse-tags
                  placeholder="营业所代码"
                  style="min-width: 200px"
                  size="small"
                  filterable
                  clearable
                  @change="handleScopeChange"
                /> -->
                <department style="width: 200px" @handleScopeChange="handleScopeChange"/>
              </el-form-item>
              <el-form-item label="供应商">
                <el-select v-model="formShikomi.supplierid" placeholder="请选择供应商" clearable size="medium">
                  <el-option
                    v-for="item in suppilyList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"/>
                </el-select>
              </el-form-item>
              <el-form-item label="运输方式" >
                <el-select v-model="formShikomi.transtype" placeholder="请选择运输方式" clearable size="medium">
                  <el-option
                    v-for="item in DictData.transtype"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
              <el-form-item label="请购单类别: " >
                <el-select v-model="formShikomi.purchasetype" placeholder="请选择请购单类别" clearable size="medium">
                  <el-option
                    v-for="item in DictData.type"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
              <el-form-item label="订单类别" >
                <el-select v-model="formShikomi.ordtype" placeholder="请选择订单类别" clearable size="medium">
                  <el-option
                    v-for="item in DictData.ordtype"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
        <div style="margin-top:20px;">
          <el-table
            v-loading="ShikomilistLoading"
            :data="tableDataShikomi"
            :row-style="rowStyle"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
            :header-row-style="{'padding': '2px'}"
            element-loading-text="拼命加载中"
            border
            fit
            height="600"
            highlight-current-row
            style="width:100%"
            @sort-change="sortChange">
            <el-table-column label="序号" align="center" fixed="left" min-width="50">
              <template slot-scope="scope">
                <span>{{ scope.$index + 1 }}</span>
              </template>
            </el-table-column>
            <!-- <el-table-column :formatter="deptFormatter" prop="applyDeptNo" fixed="left" show-overflow-tooltip label="申请部门" min-width="90" align="center"/> -->
            <el-table-column :formatter="deptFormatter" prop="deptno" show-overflow-tooltip label="营业所" min-width="110" align="center"/>
            <el-table-column show-overflow-tooltip sortable="custom" label="请购单号" prop= "orderno" align="center" min-width="150">
              <template slot-scope="scope">
                <span v-if="scope.row.splititemno===null">
                  {{ scope.row.orderno+"-"+scope.row.itemno }}
                </span>
                <span v-if="scope.row.splititemno!==null">
                  {{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}
                </span>
                <!-- <span>{{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}</span> -->
              </template>
            </el-table-column>
            <el-table-column label="型号" sortable="custom" show-overflow-tooltip prop= "modelno" min-width="160" align="center">
              <template slot-scope="scope">
                <el-popover
                  placement="right-end"
                  trigger="click"
                  visible-arrow="false"
                  @show="getEpriceList(scope.row.modelno)">
                  <div slot="reference">
                    <span>{{ scope.row.modelno }}</span>
                  </div>
                  <vxe-table
                    v-if="ePriceList && ePriceList.length > 0"
                    :data="ePriceList"
                    min-height="50px"
                    border
                    auto-resize
                    resizable
                    align="center"
                    size="mini">
                    <vxe-table-column field="minquantity" min-width="80" title="最小订货量" show-overflow/>
                    <vxe-table-column field="eprice" min-width="150" title="E价格（元）" show-overflow/>
                    <vxe-table-column field="epriceWithTax" min-width="150" title="E价格（含税，元）" show-overflow/>
                    <vxe-table-column field="standardPrice" min-width="150" title="标准价（元）"/>
                    <vxe-table-column field="invQty" min-width="150" title="通用在库数量"/>
                    <vxe-table-column field="rInvQty" min-width="150" title="风险在库数量"/>
                  </vxe-table>
                  <span v-else>
                    未登录型号
                  </span>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="数量" sortable="custom" prop= "quantity" min-width="90" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.quantity }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="拦截原因" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.interceptmsg }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="准备订单号" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.prepareorderno }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="SHIKOMI" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.shikomianswerno }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="SHIKOMI残数" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.shikomiremainqty }}</span>
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
            <el-table-column show-overflow-tooltip label="请购单类别" sortable="custom" prop="purchasetype" min-width="120" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.purchasetype | ordTypeFormat }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="ordtypeFormatter" show-overflow-tooltip prop="ordtype" label="订单类别" min-width="110" align="center"/>
            <el-table-column show-overflow-tooltip label="订货日期" sortable="custom" prop="orderdate" min-width="120" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.orderdate | formatDate }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="期望货期" sortable="custom" prop="hopedeliverydate" min-width="120" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.hopedeliverydate | formatDate2 }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="warehouseFormatter" prop="requestwarehouseid" show-overflow-tooltip label="入库仓库" min-width="100" align="center"/>
            <el-table-column :formatter="suppilyFormatter" prop="supplierid" sortable="custom" show-overflow-tooltip label="供应商" min-width="110" align="center"/>
            <el-table-column show-overflow-tooltip label="指定出荷日" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" min-width="100" align="center"/>
            <el-table-column show-overflow-tooltip label="备注信息" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.releasereason }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="操作人" min-width="90" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.operator }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" min-width="160" fixed="right" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-tooltip effect="light" content="编辑" placement="top">
                  <span>
                    <el-button v-permission="['872776']" circle icon="el-icon-edit" type="primary" size="small" @click="editRemark(scope.row)"/>
                  </span>
                </el-tooltip>
                <el-tooltip effect="light" content="放行" placement="top">
                  <span>
                    <el-button v-permission="['872776']" circle icon="el-icon-position" type="primary" size="small" @click="edit(scope.row)"/>
                  </span>
                </el-tooltip>
                <el-tooltip effect="light" content="订单取消" placement="top">
                  <span>
                    <!--bug 12991 拦截画面增加删除按钮 -->
                    <el-button v-permission="['872776']" size="small" circle type="primary" icon="el-icon-delete-solid" @click="cancelVisible(scope.row)"/>
                  </span>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-dialog :visible.sync="editFormEditShikomiVisible" title="修改数据" align="center" width="40%">
          <el-card>
            <el-form ref="updateRemark" :rules="rulesEdit" :model="updateEiditShikomiData" label-position="right" label-width="120px" style="width: 400px;">
              <el-form-item label="请购单号" prop="orderno">
                <el-input v-model="updateEiditShikomiData.orderno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="营业所" prop="deptno">
                <el-input v-model="updateEiditShikomiData.deptno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="型号" prop="modelno">
                <el-input v-model="updateEiditShikomiData.modelno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="数量" prop="quantity">
                <el-input v-model="updateEiditShikomiData.quantity" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="拦截原因" prop="interceptmsg">
                <el-input v-model="updateEiditShikomiData.interceptmsg" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="拦截备注" prop="releasereason">
                <el-input v-model="updateEiditShikomiData.releasereason" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
            </el-form>
          </el-card>
          <div slot="footer" style="text-align:center">
            <el-button type="primary" @click="editFormEditShikomiVisible = false">取消</el-button>
            <el-button :loading="buttonLoading" type="primary" @click="updateEidit()">确认</el-button>
          </div>
        </el-dialog>
        <el-dialog :visible.sync="editShikomiFormVisible" title="订单放行" align="center" width="40%">
          <el-card>
            <el-form ref="updateForm" :rules="rulesShikomi" :model="updateRequestShikomiData" label-position="right" label-width="120px" style="width: 400px;">
              <el-form-item label="请购单号" prop="orderno">
                <el-input v-model="updateRequestShikomiData.orderno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="子项号" prop="itemno">
                <el-input v-model="updateRequestShikomiData.itemno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="营业所" prop="deptno">
                <el-input v-model="updateRequestShikomiData.deptno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="型号" prop="modelno">
                <el-input v-model="updateRequestShikomiData.modelno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="数量" prop="quantity">
                <el-input v-model="updateRequestShikomiData.quantity" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="拦截原因" prop="interceptmsg">
                <el-input v-model="updateRequestShikomiData.interceptmsg" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="SHIKOMI" prop="shikomianswerno">
                <el-input v-model="updateRequestShikomiData.shikomianswerno" :disabled="false" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="准备订单号" prop="prepareOrderNo">
                <el-input v-model="updateRequestShikomiData.prepareOrderNo" :disabled="false" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="供应商" prop="supplierid">
                <el-select v-model="updateRequestShikomiData.supplierid" :disabled="suppilyVisible" placeholder="请选择供应商" style="margin-left:20px;margin-bottom:5px;width:300px" clearable >
                  <el-option
                    v-for="item in suppilyList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"/>
                </el-select>
              </el-form-item>
              <!-- <el-form-item label="放行原因" prop="releasereason">
                <el-input v-model="updateRequestShikomiData.releasereason" placeholder="如不使用SHIKOMI请清空输入框" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item> -->
            </el-form>
          </el-card>
          <div slot="footer" style="text-align:center">
            <el-button @click="editShikomiFormVisible = false">取消</el-button>
            <el-button :loading="buttonLoading" type="primary" @click="updateData()">放行</el-button>
          </div>
        </el-dialog>
        <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getSHIKOMI()" />
        <exportExcel ref="exportExcelVueShikomi"/>
      </div>
      <div v-show="radio1 === 'other'">
        <el-card>
          <div>
            <el-form ref="form" :model="form" :inline="true">
              <el-form-item label="请购单号：">
                <el-input v-model="form.orderno" placeholder="请输入请购单号" style="width=100%" clearable size="medium" @keyup.enter.native="getList"/>
              </el-form-item>
              <el-form-item label="型号：">
                <el-input v-model="form.modelno" placeholder="请输入型号" clearable size="medium" @keyup.enter.native="getList"/>
              </el-form-item>
              <el-form-item label="客户:">
                <el-input v-model="form.customerno" placeholder="请输入客户代码" clearable size="medium" @keyup.enter.native="getList"/>
              </el-form-item>
              <el-form-item label="拦截原因：">
                <el-input v-model="form.interceptmsg" placeholder="请输入拦截原因" clearable size="medium" @keyup.enter.native="getList"/>
              </el-form-item>
              <span class="operation-button">
                <el-form-item>
                  <!-- <el-tooltip effect="light" content="重置" placement="top">
                  <el-button type="info" icon="el-icon-close" size="medium" circle @click="resetForm()"/>
                </el-tooltip> -->
                  <el-button v-permission="['896417']" class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList()">查询</el-button>
                  <el-button v-permission="['872776']" class="filter-item" type="primary" size="mini" icon="el-icon-download" @click="openSonItemExport">导出</el-button>
                  <el-tooltip effect="light" content="展开" placement="top">
                    <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
                  </el-tooltip>
                </el-form-item>
              </span>
            </el-form>
          </div>
          <div v-if="searchMoreForm" class="search" style="margin-left: 11px">
            <el-form ref="form" :model="form" :inline="true">
              <el-form-item label="营业所">
                <!-- <el-cascader
                  ref ="deptCascader"
                  :props="props"
                  :options="scopeOptions"
                  :show-all-levels="false"
                  collapse-tags
                  placeholder="营业所代码"
                  style="min-width: 200px"
                  size="small"
                  filterable
                  clearable
                  @change="handleScopeChange"
                /> -->
                <department style="width: 200px" @handleScopeChange="handleScopeChange"/>
              </el-form-item>
              <el-form-item label="供应商">
                <el-select v-model="form.supplierid" placeholder="请选择供应商" clearable size="medium">
                  <el-option
                    v-for="item in suppilyList"
                    :key="item.id"
                    :label="item.name"
                    :value="item.id"/>
                </el-select>
              </el-form-item>
              <el-form-item label="运输方式" >
                <el-select v-model="form.transtype" placeholder="请选择运输方式" clearable size="medium">
                  <el-option
                    v-for="item in DictData.transtype"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
              <el-form-item label="请购单类别: " >
                <el-select v-model="form.purchasetype" placeholder="请选择请购单类别" clearable size="medium">
                  <el-option
                    v-for="item in DictData.type"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
              <el-form-item label="订单类别" >
                <el-select v-model="form.ordtype" placeholder="请选择订单类别" clearable size="medium">
                  <el-option
                    v-for="item in DictData.ordtype"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"/>
                </el-select>
              </el-form-item>
            </el-form>
          </div>
        </el-card>
        <el-button v-permission="['872776']" :disabled="selectList.length <1" size="mini" type="primary" icon="el-icon-s-promotion" style="margin-top:5px;margin-bottom:5px;float:left;" @click="handelReleaseBatch()">批量放行</el-button>
        <el-button v-permission="['872776']" :disabled="selectList.length <1" size="mini" type="primary" icon="el-icon-s-help" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="restoreBatch()">批量还原</el-button>
        <div style="margin-top:20px;">
          <el-table
            v-loading="listLoading"
            :data="tableData"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
            :cell-style="{padding: '2px 3px'}"
            :row-style="rowStyle"
            element-loading-text="拼命加载中"
            border
            fit
            height="600"
            highlight-current-row
            style="width:100%"
            @sort-change="sortChange"
            @selection-change="selectChangeEvent"
            @select-all="selectAllEvent">
            <el-table-column
              type="selection"
              width="55"/>
            <el-table-column label="序号" align="center" fixed="left" min-width="50">
              <template slot-scope="scope">
                <span>{{ scope.$index + 1 }}</span>
              </template>
            </el-table-column>
            <!-- <el-table-column :formatter="deptFormatter" prop="applyDeptNo" fixed="left" show-overflow-tooltip label="申请部门" min-width="90" align="center"/> -->
            <el-table-column :formatter="deptFormatter" prop="deptno" show-overflow-tooltip label="营业所" min-width="110" align="center"/>
            <el-table-column show-overflow-tooltip sortable="custom" label="请购单号" prop= "orderno" align="center" min-width="150">
              <template slot-scope="scope">
                <span v-if="scope.row.splititemno===null">
                  {{ scope.row.orderno+"-"+scope.row.itemno }}
                </span>
                <span v-if="scope.row.splititemno!==null">
                  {{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}
                </span>
                <!-- <span>{{ scope.row.orderno+"-"+scope.row.itemno+"-"+scope.row.splititemno }}</span> -->
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="型号" min-width="150" sortable="custom" prop= "modelno" align="center">
              <template slot-scope="scope">
                <!-- <span>{{ scope.row.modelno }}</span> -->
                <el-popover
                  placement="right-end"
                  trigger="click"
                  visible-arrow="false"
                  @show="getEpriceList(scope.row.modelno)">
                  <div slot="reference">
                    <span>{{ scope.row.modelno }}</span>
                  </div>
                  <vxe-table
                    v-if="ePriceList && ePriceList.length > 0"
                    :data="ePriceList"
                    min-height="50px"
                    border
                    auto-resize
                    resizable
                    align="center"
                    size="mini">
                    <vxe-table-column field="minquantity" min-width="80" title="最小订货量" show-overflow/>
                    <vxe-table-column field="eprice" min-width="150" title="E价格（元）" show-overflow/>
                    <vxe-table-column field="epriceWithTax" min-width="150" title="E价格（含税，元）" show-overflow/>
                    <vxe-table-column field="standardPrice" min-width="150" title="标准价（元）"/>
                    <vxe-table-column field="invQty" min-width="150" title="通用在库数量"/>
                    <vxe-table-column field="rInvQty" min-width="150" title="风险在库数量"/>
                  </vxe-table>
                  <span v-else>
                    未登录型号
                  </span>
                </el-popover>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip sortable="custom" prop= "quantity" label="数量" min-width="90" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.quantity }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="拦截原因" min-width="130" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.interceptmsg }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="SHIKOMI" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.shikomianswerno }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="SHIKOMI残数" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.shikomiremainqty }}</span>
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
            <el-table-column show-overflow-tooltip label="请购单类别" sortable="custom" min-width="120" prop="purchasetype" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.purchasetype | ordTypeFormat }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="ordtypeFormatter" prop="ordtype" label="订单类别" min-width="110" align="center"/>
            <el-table-column show-overflow-tooltip label="订货日期" sortable="custom" prop= "orderdate" min-width="120" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.orderdate | formatDate }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="期望货期" min-width="120" sortable="custom" prop= "hopedeliverydate" orderdatealign="center">
              <template slot-scope="scope">
                <span>{{ scope.row.hopedeliverydate | formatDate2 }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="warehouseFormatter" prop="requestwarehouseid" show-overflow-tooltip label="入库仓库" min-width="100" align="center"/>
            <el-table-column :formatter="warehouseFormatter" prop="purchasewarehouseid" show-overflow-tooltip label="采购仓库" min-width="100" align="center"/>
            <el-table-column :formatter="suppilyFormatter" sortable="custom" prop="supplierid" show-overflow-tooltip label="供应商" min-width="100" align="center"/>
            <el-table-column show-overflow-tooltip label="指定出荷日" min-width="120" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" min-width="100" align="center"/>
            <el-table-column show-overflow-tooltip label="备注信息" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.releasereason }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="操作人" min-width="90" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.operator }}</span>
              </template>
            </el-table-column>
            <!-- <el-table-column show-overflow-tooltip label="请购单状态" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.statecode }}</span>
              <span v-if="scope.row.statecode==='4'">
                <el-tooltip placement="top-start" effect="light" content="拦截原因">
                  <i class="el-icon-warning" style="color: #FFD700; cursor: pointer; font-size: 15px;"/>
                </el-tooltip>
              </span>
            </template>
          </el-table-column> -->
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right" min-width="190">
              <template slot-scope="scope">
                <el-tooltip effect="light" content="编辑" placement="top">
                  <span>
                    <el-button v-permission="['872776']" circle icon="el-icon-edit" type="primary" size="small" @click="editRemark(scope.row)"/>
                  </span>
                </el-tooltip>
                <el-tooltip effect="light" content="放行" placement="top">
                  <span>
                    <el-button v-permission="['872776']" :loading="editloading" circle icon="el-icon-position" type="primary" size="small" @click="edit(scope.row)"/>
                  </span>
                </el-tooltip>
                <el-tooltip effect="light" content="还原" placement="top">
                  <span>
                    <el-button v-permission="['872776']" circle icon="el-icon-refresh-right" type="primary" size="small" @click="restoreData(scope.row)"/>
                  </span>
                </el-tooltip>
                <el-tooltip effect="light" content="订单取消" placement="top">
                  <span>
                    <!--bug 12991 拦截画面增加删除按钮 -->
                    <el-button v-permission="['872776']" size="small" circle type="primary" icon="el-icon-delete-solid" @click="cancelVisible(scope.row)" />
                  </span>
                </el-tooltip>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <el-dialog :visible.sync="editFormEditVisible" title="修改数据" align="center" width="40%">
          <el-card>
            <el-form ref="updateRemark" :rules="rulesEdit" :model="updateEiditData" label-position="right" label-width="120px" style="width: 400px;">
              <el-form-item label="请购单号" prop="orderno">
                <el-input v-model="updateEiditData.orderno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="拦截备注" prop="releasereason">
                <el-input v-model="updateEiditData.releasereason" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
            </el-form>
          </el-card>
          <div slot="footer" style="text-align:center">
            <el-button type="primary" @click="editFormEditVisible = false">取消</el-button>
            <el-button :loading="buttonLoading" type="primary" @click="updateEidit()">确认</el-button>
          </div>
        </el-dialog>
        <el-dialog :visible.sync="releaseBatch.visible" title="批量放行" align="center" width="40%">
          <el-card>
            <el-form :rules="rules" :model="releaseBatch.data" label-position="right" label-width="120px" style="width: 400px;">
              <el-form-item label="指定出荷日" prop="hopeexportdate">
                <el-date-picker
                  v-model="releaseBatch.data.hopeexportdate"
                  style="margin-left:20px;margin-bottom:5px;width:300px"
                  size="middle"/>
              </el-form-item>
            </el-form>
          </el-card>
          <div slot="footer" style="text-align:center">
            <el-button type="primary" @click="releaseBatchClose">取消</el-button>
            <el-button :loading="releaseBatch.loading" type="primary" @click="releaseDataBatch()">放行</el-button>
          </div>
        </el-dialog>
        <el-dialog :visible.sync="editFormVisible" title="放行订单" align="center" width="40%">
          <el-card>
            <el-form ref="updateForm" :rules="rules" :model="updateRequestData" label-position="right" label-width="120px" style="width: 400px;">
              <el-form-item label="请购单号" prop="orderno">
                <el-input v-model="updateRequestData.orderno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="子项号" prop="itemno">
                <el-input v-model="updateRequestData.itemno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="型号" prop="modelno">
                <el-input v-model="updateRequestData.modelno" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="订货数量" prop="quantity">
                <el-input v-model="updateRequestData.quantity" :disabled="true" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="供应商" prop="supplierid">
                <el-select v-model="updateRequestData.supplierid" placeholder="请选择供应商" style="margin-left:20px;margin-bottom:5px;width:300px" clearable @change="transSelect()">
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
              <el-form-item label="smccode" prop="smccode">
                <el-input v-model="updateRequestData.smccode" placeholder="请输入smccode,默认为9501200" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item>
              <el-form-item label="采购仓库" prop="purchasewarehouseid">
                <!-- <el-input v-model="updateRequestData.purchasewarehouseid" placeholder="请选择入库仓库" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/> -->
                <el-select v-model="updateRequestData.purchasewarehouseid" placeholder="请选择采购仓库" clearable style="margin-left:20px;margin-bottom:5px;width:300px" @change="transSelect()">
                  <el-option
                    v-for="item in warehouseList"
                    :key="item.warehouseCode"
                    :label="item.warehouseName"
                    :value="item.warehouseCode"/>
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
              <!-- <el-form-item label="放行原因" prop="releasereason">
                <el-input v-model="updateRequestData.releasereason" clearable style="margin-left:20px;margin-bottom:5px;width:300px"/>
              </el-form-item> -->
            </el-form>
          </el-card>
          <div slot="footer" style="text-align:center">
            <el-button type="primary" @click="editFormVisible = false">取消</el-button>
            <el-button :loading="buttonLoading" type="primary" @click="updateStateCode()">放行</el-button>
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
        <exportExcel ref="exportExcelVue"/>
      </div>
    </div>
  </div>
</template>
<script>
// import { updateSuppily } from '@/api/requestPurchase'
import { getSuppily, orderPass, interceptRestore, editRemarkData, getWarehouse, restoreBatch, orderpassBatch } from '@/api/intercept'
import { findDeptDict } from '@/api/warehouseManage'
import { findListShikomi, updateShikomiRequest } from '@/api/interceptShikomi'
import Pagination from '@/components/Pagination'
import { findPriceByModelNo } from '@/api/product'
import { returnFloat } from '@/utils'
import { computePriceWithTax, computeStandardPrice } from '@/utils/saleUtils/saleCompute'
import { getDataCodesTree } from '@/api/common/dict'
import exportExcel from '@/components/ExportExcel/index'
import Department from '@/components/Department'
import { cancelDatas } from '@/api/requestPurchase'
import { purchaseDel, deleteReasonOptions, getTransIds } from '@/api/purchaseOrder'
export default {
  name: 'Intercept',
  components: { Pagination, exportExcel, Department },
  filters: {
  },
  data() {
    return {
      props: { multiple: true },
      DictData: {
        type: [],
        department: [],
        ordtype: [],
        transtype: [],
        supplierAssignType: []
      },
      scopeOptions: [],
      selectList: [],
      visible: false,
      radio1: 'other',
      shikomiIntercept: false,
      otherIntercept: false,
      tableData: [],
      tableDataShikomi: [],
      ShikomilistLoading: false,
      editFormVisible: false,
      editFormEditVisible: false,
      editShikomiFormVisible: false,
      editFormEditShikomiVisible: false,
      listLoading: false,
      updateRequestData: {},
      updateRequestShikomiData: {},
      updateEiditShikomiData: {},
      updateEiditData: {},
      total: 0,
      queryCondition: {},
      shikomiqueryCondition: {},
      page: {
        pageNumber: 1,
        pageSize: 30
      },
      form: {
      },
      formShikomi: {},
      suppilyList: [],
      warehouseList: [],
      rulesShikomi: {
        // shikomianswerno: [{ required: true, message: '请选择供应商', trigger: 'blur' }]
      },
      rules: {
        supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        transtype: [{ required: true, message: '请选择运输方式', trigger: 'blur' }],
        hopeexportdate: [{ required: true, message: '请选择指定出荷日', trigger: 'blur' }],
        smccode: [{ required: true, message: '请输入smccode', trigger: 'blur' }],
        purchasewarehouseid: [{ required: true, message: '请选择入库仓库', trigger: 'blur' }],
        supplierAssignType: [{ required: true, message: '请选择指定供应商类别', trigger: 'blur' }]
      },
      rulesEdit: {
        releasereason: [{ required: true, message: '请输入备注信息', trigger: 'blur' }]
      },
      suppilyVisible: true,
      transList: [],
      canSelectTransList: [],
      canSelectsupplierList: [],
      ePriceList: [],
      taxRate: 0.13,
      searchMoreFormShikomi: false,
      searchMoreForm: false,
      releaseBatch: {
        visible: false,
        loading: false,
        data: {}
      },
      // 通用按钮加载
      buttonLoading: false,
      // 放行按钮加载
      editloading: false,
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
      tableDataRequest: []
    }
  },
  mounted() {
    this.DictData.calcelReason = deleteReasonOptions()
  },
  created() {
    this.getList()
    this.initData()
    this.getSuppily()
    this.getWarehouseList()
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
    // 展开更多选项
    searchMoreData() {
      if (this.radio1 === 'other') {
        this.searchMoreForm = !this.searchMoreForm
      } else {
        this.searchMoreFormShikomi = !this.searchMoreFormShikomi
      }
    },
    // 查询产品价格
    getEpriceList(modelNo) {
      if (!modelNo) {
        return []
      }
      findPriceByModelNo(modelNo).then(data => {
        // var ePriceList = data.data
        this.ePriceList = this.computePice(data.data)
      })
    },
    computePice(ePriceList) {
      if (ePriceList && ePriceList.length > 0) {
        ePriceList.map(v => {
          v.epriceWithTax = computePriceWithTax(v.eprice, this.taxRate) // taxRate=0.13
          v.eprice = returnFloat(v.eprice)
          const price = v.eprice
          v.standardPrice = computeStandardPrice(price, this.taxRate) // 取整
          return v
        })
      }
      return ePriceList
    },
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // bug 10558 导出营业所别翻译成中文
    deptnoTrans(val) {
      const item = this.DictData.department.find(dict => dict.code === val)
      return item ? item.desc : val
    },
    // 订单类别转换
    ordtypeFormatter(row, column, cellValue) {
      const item = this.DictData.ordtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    // 排序
    sortChange(column, prop, order) {
      if (this.radio1 === 'other') {
        if (column.order === 'descending') {
          this.queryCondition.orderBy = column.prop + ' desc'
        }
        if (column.order === 'ascending') {
          this.queryCondition.orderBy = column.prop + ' asc'
        }
        this.getList()
      } else {
        if (column.order === 'descending') {
          this.shikomiqueryCondition.orderBy = column.prop + ' desc'
        }
        if (column.order === 'ascending') {
          this.shikomiqueryCondition.orderBy = column.prop + ' asc'
        }
        this.getSHIKOMI()
      }
      // this.queryCondition.orderBy = column.prop
    },
    rowStyle({ row, rowIndex }) {
      return 'height:10px'
    },
    changeRadio(val) {
      if (val === 'other') {
        this.getList()
      } else {
        this.getSHIKOMI()
      }
    },
    // 重置表单
    resetForm() {
      // this.$refs.form.resetFields()
      if (this.radio1 === 'other') {
        this.form.orderno = ''
        this.form.modelno = ''
        this.form.ordtype = ''
      } else {
        this.formShikomi.orderno = ''
        this.formShikomi.modelno = ''
        this.formShikomi.ordtype = ''
      }
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
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
    // 查询所有数据,正常拦截
    getList() {
      this.listLoading = true
      // if (!this.isEmpty(this.form.orderno) || !this.isEmpty(this.form.modelno) || !this.isEmpty(this.form.ordtype)) {
      //   this.queryCondition.condition = this.form
      // }
      this.queryCondition.condition = this.form
      this.queryCondition.condition.statecode = '4'
      this.queryCondition.pageNumber = this.page.pageNumber
      this.queryCondition.pageSize = this.page.pageSize
      findListShikomi(this.queryCondition).then(res => {
        this.tableData = res.data.list
        this.total = res.data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    getSHIKOMI() {
      this.ShikomilistLoading = true
      this.shikomiqueryCondition.condition = this.formShikomi
      this.shikomiqueryCondition.condition.statecode = '5'
      this.shikomiqueryCondition.pageNumber = this.page.pageNumber
      this.shikomiqueryCondition.pageSize = this.page.pageSize
      findListShikomi(this.shikomiqueryCondition).then(res => {
        this.tableDataShikomi = res.data.list
        this.total = res.data.total
        this.ShikomilistLoading = false
      }).catch(error => {
        this.ShikomilistLoading = false
        console.log(error)
      })
    },
    updateData() {
      this.$refs['updateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将放行该SHIKOMI拦截请购单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.buttonLoading = true
            // bug10483 修改更新人直接从前端获取，B91717
            this.updateRequestShikomiData.operator = this.$store.getters.position.psnsmcId
            updateShikomiRequest(this.updateRequestShikomiData).then(res => {
              this.buttonLoading = false
              if (res.success === true) {
                this.$notify({
                  title: '成功',
                  message: 'SHIKOMI放行成功',
                  type: 'success',
                  duration: 2000
                })
                this.getSHIKOMI()
              } else {
                this.$message({
                  dangerouslyUseHTMLString: true,
                  showClose: true,
                  message: res.message,
                  type: 'error',
                  duration: 0
                })
              }
              this.editShikomiFormVisible = false
            }).catch(error => {
              this.buttonLoading = false
              this.editShikomiFormVisible = false
              console.info(error)
              this.$message.error(error.message)
            })
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消拦截订单放行'
            })
          })
        }
      })
    },
    // 变更拦截状态
    updateStateCode(row) {
      this.$refs['updateForm'].validate((valid) => {
        if (valid) {
          this.$confirm('此操作将放行该拦截请购单, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.buttonLoading = true
            // bug10483 修改更新人直接从前端获取，B91717
            this.updateRequestData.operator = this.$store.getters.position.psnsmcId
            orderPass(this.updateRequestData).then(res => {
              if (res.success) {
                this.editFormVisible = false
                this.$notify({
                  title: '成功',
                  message: '拦截订单放行成功',
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
            }).catch(error => {
              this.loading = false
              console.info(error)
              this.$message.error(error.message)
            })
            this.buttonLoading = false
          }).catch(() => {
            this.$message({
              type: 'info',
              message: '已取消拦截订单放行'
            })
          })
        }
      })
    },
    updateEidit() {
      this.$refs['updateRemark'].validate((valid) => {
        var condition = {}
        if (valid) {
          if (this.radio1 === 'other') {
            condition = this.updateEiditData
          } else {
            condition = this.updateEiditShikomiData
          }
          this.buttonLoading = true
          editRemarkData(condition).then(() => {
            // 增加返回状态校验
            this.$notify({
              title: '成功',
              message: '添加备注成功',
              type: 'success',
              duration: 2000
            })
            if (this.radio1 === 'other') {
              this.editFormEditVisible = false
              this.getList()
            } else {
              this.editFormEditShikomiVisible = false
              this.getSHIKOMI()
            }
            this.buttonLoading = false
          }).catch(() => {
            this.buttonLoading = false
            this.$notify.error({
              title: '错误',
              message: '添加备注失败'
            })
          })
        }
      })
    },
    restoreData(row) {
      this.$confirm('此操作将还原该拦截订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // bug10483 修改更新人直接从前端获取，B91717
        row.operator = this.$store.getters.position.psnsmcId
        interceptRestore(row).then(res => {
          if (res.success) {
            this.$notify({
              title: '成功',
              message: '拦截订单还原成功',
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
        }).catch(() => {
          this.loading = false
          this.$notify.error({
            title: '错误',
            message: '拦截订单还原成功'
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消拦截订单还原'
        })
      })
    },
    edit(row) {
      if (this.radio1 === 'other') {
        this.editloading = true
        this.canSelectTransList = []
        this.canSelectsupplierList = []
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
      } else {
        this.editShikomiFormVisible = true
        this.updateRequestShikomiData = Object.assign({}, row)
      }
    },
    editRemark(row) {
      if (this.radio1 === 'other') {
        this.editFormEditVisible = true
        this.updateEiditData = Object.assign({}, row)
      } else {
        this.editFormEditShikomiVisible = true
        this.updateEiditShikomiData = Object.assign({}, row)
      }
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
    selectDictLabel(datas, value) {
      var actions = []
      Object.keys(datas).some(key => {
        if (datas[key].dictValue === '' + value) {
          actions.push(datas[key].dictLabel)
          return true
        }
      })
      return actions.join('')
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    warehouseFormatter(row, column, cellValue, index) {
      const item = this.warehouseList.find(dict => dict.warehouseCode === cellValue)
      return item ? item.warehouseName : cellValue
    },
    handleScopeChange(val) {
      if (this.radio1 === 'other') {
        console.log('other=>所选部门 ', val)
        this.form.deptNos = val
      } else {
        console.log('shikomi=>所选部门 ', val)
        this.formShikomi.deptNos = val
      }
    },
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
      getDataCodesTree('1002').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.ordtype.push({ value: dict.code, label: dict.codeName })
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
        { field: 'prepareorderno', title: '准备订单号' },
        { field: 'shikomianswerno', title: 'SHIKOMI' },
        { field: 'shikomiremainqty', title: 'SHIKOMI残数' },
        { field: 'customerno', title: '客户代码' },
        { field: 'userno', title: '用户代码' },
        { field: 'endUser', title: '最终用户' },
        { field: 'supplierid', title: '供应商' },
        { field: 'purchasedate', title: '采购日期' },
        { field: 'hopeexportdate', title: '制造出荷日' },
        { field: 'hopedeliverydate', title: '期望货期' },
        { field: 'requestwarehouseid', title: '收货仓库' },
        { field: 'transtype', title: '运输方式' },
        { field: 'qtyreceive', title: '处理状态' },
        { field: 'finishdate', title: '完成日期' },
        { field: 'interceptmsg', title: '拦截原因' },
        { field: 'releasereason', title: '备注信息' },
        { field: 'deptno', title: '营业所' }
      ]
      if (this.radio1 === 'other') {
        this.queryCondition.condition = this.form
        this.queryCondition.condition.statecode = '4'
        this.queryCondition.pageNumber = this.page.pageNumber
        this.queryCondition.pageSize = 1000
        findListShikomi(this.queryCondition).then(res => {
          res.data.list.forEach((item) => {
            item.deptno = this.deptnoTrans(item.deptno)
          })
          this.$refs.exportExcelVue.initExportExcel(res.data.list, tableColumn)
        }).catch(error => {
          console.log(error)
        })
      } else {
        this.shikomiqueryCondition.condition = this.formShikomi
        this.shikomiqueryCondition.condition.statecode = '5'
        this.shikomiqueryCondition.pageNumber = this.page.pageNumber
        this.shikomiqueryCondition.pageSize = 1000
        findListShikomi(this.shikomiqueryCondition).then(res => {
          res.data.list.forEach((item) => {
            item.deptno = this.deptnoTrans(item.deptno)
          })
          this.$refs.exportExcelVueShikomi.initExportExcel(res.data.list, tableColumn)
        }).catch(error => {
          console.log(error)
        })
      }
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
    // 批量放行操作
    handelReleaseBatch() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要批量放行的请购单'
        })
        return
      }
      this.releaseBatch.visible = true
      // this.releaseBatch.data = Object.assign({}, this.selectList[0])
    },
    /**
     * 供应商运输方式联动
     */
    transSelect() {
      this.updateRequestData.transtype = ''
      this.canSelectTransList = []
      var transParam = {}
      transParam.supplierId = this.updateRequestData.supplierid
      transParam.modelNo = this.updateRequestData.modelno
      transParam.orderQty = this.updateRequestData.quantity
      transParam.ordType = this.updateRequestData.ordtype
      transParam.warehouse = this.updateRequestData.purchasewarehouseid
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
    releaseBatchClose() {
      this.releaseBatch.visible = false
      this.releaseBatch.loading = false
      this.releaseBatch.data = {}
    },
    releaseDataBatch() {
      if (this.isEmpty(this.releaseBatch.data.hopeexportdate)) {
        this.$message({
          dangerouslyUseHTMLString: true,
          showClose: true,
          message: '请选择指定出荷日后，再操作放行',
          type: 'error',
          duration: 0
        })
        return
      }
      this.selectList.forEach(element => {
        element.hopeexportdate = this.releaseBatch.data.hopeexportdate
      })
      this.releaseBatch.loading = true
      orderpassBatch(this.selectList).then(res => {
        if (res.success) {
          this.$notify({
            title: '成功',
            message: '拦截订单放行成功',
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
        this.releaseBatchClose()
      }).catch(error => {
        this.releaseBatch.loading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 批量还原操作
    restoreBatch() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要批量还原的请购单'
        })
        return
      }
      this.$confirm('此操作将还原勾选的拦截订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        restoreBatch(this.selectList).then(res => {
          if (res.success) {
            this.$notify({
              title: '成功',
              message: '拦截订单还原成功',
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
        }).catch(error => {
          this.loading = false
          console.info(error)
          this.$message.error(error.message)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消拦截订单还原'
        })
      })
    },
    // 取消订单的弹窗
    cancelVisible(row) {
      this.tableDataRequest = []
      this.tableDataRequest.push(row)
      this.cancelDialog.show = true
      this.cancelRequestData = Object.assign({}, row)
      console.log(this.cancelRequestData)
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
              this.cancelDialog.show = false
              this.getList()
            }).catch(error => {
              this.cancelDialog.loading = false
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
          }
          this.cancelDialog.loading = false
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
    // 运输方式转换
    transtypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.transtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    }
  }
}
</script>
<style  scoped>
.app-container .filter-containers .divHeader{
  position: relative;
  text-align: left;
}
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
.el-table th{
  /* color: rgba(253, 253, 253, 0.938);
  background-color: rgb(117, 144, 168); */
  padding: 6px;
  font-size: 14px;
}
.el-table td{
  padding: 2px;
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

