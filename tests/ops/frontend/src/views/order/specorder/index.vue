<template>
  <el-container>
    <!-- <el-header height="40px"/> -->
    <el-main>
      <el-tabs v-model="activeName" @tab-click="tabchange">
        <el-tab-pane :key="'first'" label="新增订单" name="first">
          <el-card>
            <el-button type="primary" size="mini" plain icon="el-icon-plus" @click="addOrder('form')">新增</el-button>
            <el-button :loading="loadadd" type="primary" size="mini" plain icon="el-icon-check" @click="saveData('form')">保存</el-button>
          </el-card>
          <el-form ref="form" :model="form" :rules="rule" size="mini" label-width="100px">
            <el-row type="flex" style="margin-top:10px">
              <el-form-item prop="groupNo" style="margin-left:23%">
                <span slot="label">
                  <span class="span-box">
                    <el-tooltip class="item" effect="dark" content="点击【保存】生成批次号" placement="top-start">
                      <i class="el-icon-warning-outline" />
                    </el-tooltip>
                    <span class="font">批次号</span>
                  </span>
                </span>
                <el-input :disabled="group" v-model="form.groupNo" type="text" size="mini" style="width: 150px" @blur="findDetail"/>
              </el-form-item>
              <el-form-item prop="orderType" label="申请类型">
                <el-select v-model="form.orderType" placeholder="申请类型" size="mini" style="width: 150px" @change="selectChange">
                  <el-option v-for="item in orderTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item prop="tradeCompanyId" label="交易公司">
                <el-select v-model="form.tradeCompanyId" placeholder="交易公司" size="mini" style="width: 150px">
                  <el-option v-for="item in companyTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item prop="customerNo" label="客户代码" style="margin-left:23%">
                <el-select
                  :remote-method="remoteMethodByCustomerNo"
                  :loading="customerNoLoading"
                  v-model="form.customerNo"
                  filterable
                  remote
                  clearable
                  style="width: 280px"
                  placeholder="请输入客户代码"
                  @change= "selCustomer"
                >
                  <el-option
                    v-for="item in customerNoOptions"
                    :key="item.customerNo"
                    :label="'【'+item.customerNo+'】'+item.name"
                    :value="item.customerNo" />
                </el-select>
              </el-form-item>
              <el-form-item prop="corderNo" label="客户订单号" style="margin-left: 120px">
                <el-input v-model="form.corderNo" type="text" size="mini" style="width: 150px" placeholder="请输入客户订单号"/>
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item prop="userNo" label="用户代码" style="margin-left:23%">
                <el-select
                  :remote-method="remoteMethodByUserNo"
                  :loading="userNoLoading"
                  v-model="form.userNo"
                  filterable
                  remote
                  style="width: 280px"
                  clearable
                  placeholder="请输入用户代码"
                  @change= "selUserNo"
                >
                  <el-option
                    v-for="item in userNoOptions"
                    :key="item.customerNo"
                    :label="'【'+item.customerNo+'】'+item.name"
                    :value="item.customerNo" />
                </el-select>
              </el-form-item>
              <el-form-item prop="dlvType" label="出货方式" style="margin-left: 120px">
                <el-select v-model="form.dlvType" placeholder="出货方式" size="mini" style="width: 150px">
                  <el-option v-for="item in outstockTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item
                v-if="handleshow"
                key="dlvEntire"
                :rules="handleshow?rule.dlvEntire:[]"
                prop="dlvEntire"
                label="处理方式"
                style="margin-left:23%">
                <el-select v-model="form.dlvEntire" placeholder="处理方式" size="mini" style="width: 150px" @change="dlvEntireChange">
                  <el-option v-for="item in handleTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item v-if="exportTypeShow" prop="exportType" label="出口方式" style="margin-left:23%">
                <el-select v-model="form.exportType" placeholder="出口方式" size="mini" style="width: 150px">
                  <el-option v-for="item in outTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item v-show="deptShow" label="营业所">
                <el-cascader
                  ref="cascaderHandle"
                  :options="deptNoOptions"
                  :props="{ value: 'id', label: 'name',checkStrictly: true }"
                  :show-all-levels="false"
                  v-model="form.deptNo"
                  placeholder="选择营业所"
                  filterable
                  clearable
                  style="width: 150px"
                  size="mini"
                  @change="deptChange" />
              </el-form-item>
              <el-form-item
                v-if="warehouseShow"
                key="exportWarehouse"
                :rules="warehouseShow?rule.exportWarehouse:[]"
                prop="exportWarehouse"
                label="报关地点">
                <el-select v-model="form.exportWarehouse" placeholder="报关地点" size="mini" style="width: 150px" clearable>
                  <el-option v-for="item in addressTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
            </el-row>
            <el-row v-if="receiver" type="flex">
              <el-form-item prop="receiverCompany" label="收货公司" style="margin-left:23%">
                <el-input v-model="form.receiverCompany" type="text" size="mini" style="width: 150px" placeholder="请输入收货公司"/>
              </el-form-item>
              <el-form-item prop="receiverName" label="收货人" >
                <el-input v-model="form.receiverName" type="text" size="mini" style="width: 150px" placeholder="请输入收货人"/>
              </el-form-item>
              <el-form-item prop="receiverPhone" label="收货人电话" >
                <el-input v-model="form.receiverPhone" type="text" size="mini" style="width: 150px" placeholder="请输入收货人电话"/>
              </el-form-item>
            </el-row>
            <el-row v-if="deptShow2" type="flex" style="margin-left:23%">
              <el-form-item label="营业所">
                <el-cascader
                  ref="cascaderHandle"
                  :options="deptNoOptions"
                  :props="{ value: 'id', label: 'name',checkStrictly: true }"
                  :show-all-levels="false"
                  v-model="form.deptNo"
                  placeholder="选择营业所"
                  filterable
                  clearable
                  style="width: 150px"
                  size="mini"
                  @change="deptChange" />
              </el-form-item>
            </el-row>
            <el-row v-if="receiver" type="flex">
              <el-form-item prop="receiverAddress" label="收货地址" style="margin-left:23%">
                <el-input v-model="form.receiverAddress" type="text" size="mini" style="width: 650px"/>
              </el-form-item>
            </el-row>
            <el-row v-if="receiver">
              <el-form-item label="省市区: " prop="Areas" style="margin-left:23%">
                <el-cascader
                  v-model="form.area"
                  :options="menuData.Areas"
                  style="width: 200px"
                  @change="areaAddCode()"
                />
              </el-form-item>
            </el-row>
            <el-row type="flex">
              <el-form-item prop="remark" label="备注" style="margin-left:23%">
                <el-input v-model="form.remark" :autosize="{ minRows: 2, maxRows: 4}" type="textarea" size="mini" style="width: 650px" @input="change($event)"/>
              </el-form-item>
            </el-row>

            <el-row v-if="disabled" style="margin-bottom: 10px;margin-top: 30px">
              <el-button type="primary" size="mini" @click="addDetail" >新增</el-button>
              <el-button type="primary" size="mini" @click="batchadd" >批量添加项</el-button>
              <!-- <el-button type="primary" size="mini" @click="deletemue" >删除</el-button> -->
            </el-row>
            <el-table
              :data="form.list"
              :row-class-name="tableRowClassName"
              stripe
              size="mini"
              style="width: 100%;"
              @selection-change="handleSelectionChange">
              <!-- 字段需要加Key防止刷新时顺序错乱 -->
              <el-table-column
                key="selection"
                type="selection"
                align="center"
                width="50"/>
              <el-table-column
                v-if="idshow"
                key="id"
                prop="id"
                label="id"
                width="100">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.id'" label-width="0">
                    <el-input v-model="scope.row.id" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.id }}</span>
                </template>
              </el-table-column>
              <!-- 项号 -->
              <el-table-column
                key="orderItem"
                prop="orderItem"
                label="项号"
                width="100">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.orderItem'" label-width="0">
                    <el-input v-model="scope.row.orderItem" placeholder="项号" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.orderItem }}</span>
                </template>
              </el-table-column>
              <!-- 产品型号 -->
              <el-table-column
                key="modelNo"
                prop="modelNo"
                label="产品型号"
                width="145"
                show-overflow-tooltip>
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.modelNo'" :rules="rule.list.modelNo" label-width="0">
                    <el-input v-model="scope.row.modelNo" placeholder="产品型号" size="small" @blur="checkEpric(scope.$index)"/>
                  </el-form-item>
                  <el-link v-show="!scope.row.isEditable" :underline="false" @click="handleClick(scope.row)">{{ scope.row.modelNo }}</el-link>
                  <!-- <span v-show="!scope.row.isEditable">{{ scope.row.modelNo }}</span> -->
                </template>
              </el-table-column>
              <!-- 数量 -->
              <el-table-column
                key="quantity"
                prop="quantity"
                label="数量"
                width="100">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.quantity'" :rules="rule.list.quantity" label-width="0">
                    <el-input v-model="scope.row.quantity" placeholder="数量" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.quantity }}</span>
                </template>
              </el-table-column>
              <!-- 原币 price-->
              <el-table-column
                v-if="price"
                key="orgCurrency"
                prop="orgCurrency"
                label="原币"
                width="150">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.orgCurrency'" label-width="0">
                    <el-select v-model="scope.row.orgCurrency" filterable allow-create placeholder="原币" style="width: 100%" size="small">
                      <el-option
                        v-for="item in orgTypeOptions"
                        :key="item.code"
                        :label="item.codeName"
                        :value="item.code" />
                    </el-select>
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ getCurrency(scope.row.orgCurrency) }}</span>
                </template>
              </el-table-column>
              <!-- 原币单价 price -->
              <el-table-column
                v-if="price"
                key="orgPrice"
                prop="orgPrice"
                label="原币单价"
                width="150">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.orgPrice'" label-width="0">
                    <el-input v-model="scope.row.orgPrice" placeholder="原币单价" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.orgPrice }}</span>
                </template>
              </el-table-column>
              <!-- 单价(RMB) price-->
              <el-table-column
                v-if="price"
                key="price"
                prop="price"
                label="单价(RMB)"
                width="150">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.price'" :rules="price?rule.list.price:[]" label-width="0">
                    <el-input v-model="scope.row.price" placeholder="单价(RMB)" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.price }}</span>
                </template>
              </el-table-column>
              <!-- 日本投诉号 complaint-->
              <el-table-column
                v-if="complaint"
                key="complaintNo"
                prop="complaintNo"
                label="日本投诉号">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.complaintNo'" label-width="0">
                    <el-input v-model="scope.row.complaintNo" placeholder="日本投诉号" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.complaintNo }}</span>
                </template>
              </el-table-column>
              <!-- 产品名称 -->
              <el-table-column
                key="productName"
                prop="productName"
                label="产品名称"
                width="150">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.productName'" label-width="0">
                    <el-input v-model="scope.row.productName" placeholder="产品名称" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.productName }}</span>
                </template>
              </el-table-column>
              <!-- E价 -->
              <el-table-column
                key="eprice"
                prop="eprice"
                label="E价"
                width="150">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.eprice'" :rules="rule.list.eprice" label-width="0">
                    <el-input :disabled="true" v-model="scope.row.eprice" placeholder="E价" size="small"/>
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.eprice }}</span>
                </template>
              </el-table-column>
              <!-- 交货期 dlvDate-->
              <el-table-column
                v-if="dlvDate"
                key="dlvDate"
                prop="dlvDate"
                label="交货期">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.dlvDate'" :rules="dlvDate?rule.list.dlvDate:[]" label-width="0">
                    <el-date-picker v-model="scope.row.dlvDate" :picker-options="handlePickerOptions" style="width:95%" size="small" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"/>
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.dlvDate }}</span>
                </template>
              </el-table-column>
              <!-- 制造指定出荷日 manuDlvDate-->
              <el-table-column
                v-if="manuDlvDate"
                key="manuDlvDate"
                prop="manuDlvDate"
                label="制造指定出荷日">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.manuDlvDate'" :rules="manuDlvDate?rule.list.manuDlvDate:[]" label-width="0">
                    <el-date-picker v-model="scope.row.manuDlvDate" :picker-options="handlePickerOptions" style="width:95%" size="small" type="date" placeholder="选择日期" value-format="yyyy-MM-dd"/>
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.manuDlvDate }}</span>
                </template>
              </el-table-column>
              <!-- 客户型号 -->
              <el-table-column
                key="cproductNo"
                prop="cproductNo"
                label="客户型号">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.cproductNo'" label-width="0">
                    <el-input v-model="scope.row.cproductNo" placeholder="客户型号" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.cproductNo }}</span>
                </template>
              </el-table-column>
              <!-- 运输方式 -->
              <el-table-column
                key="transType"
                prop="transType"
                label="运输方式">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.transType'" :rules="rule.list.transType" label-width="0">
                    <el-select v-model="scope.row.transType" placeholder="运输方式" size="small" style="width: 150px">
                      <el-option v-for="item in transTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                    </el-select>
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ getTransType(scope.row.transType) }}</span>
                </template>
              </el-table-column>
              <!-- 组装 -->
              <el-table-column
                key="specMark"
                prop="specMark"
                label="组装方式">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.specMark'" label-width="0">
                    <el-radio-group v-model="scope.row.specMark">
                      <el-radio v-for="item in specMarkOptions" :key="item.code" :label="item.code">{{ item.codeName }}</el-radio>
                    </el-radio-group>
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ getSpecMark(scope.row.specMark) }}</span>
                </template>
              </el-table-column>
              <!-- 备注 -->
              <el-table-column
                key="remark"
                prop="remark"
                label="备注">
                <template slot-scope="scope">
                  <el-form-item v-show="scope.row.isEditable" :prop="'list.' + scope.$index + '.remark'" label-width="0">
                    <el-input v-model="scope.row.remark" placeholder="备注" size="small" />
                  </el-form-item>
                  <span v-show="!scope.row.isEditable">{{ scope.row.remark }}</span>
                </template>
              </el-table-column>
              <!-- 操作 -->
              <el-table-column
                key="handle"
                fixed="right"
                label="操作"
                align="center"
                min-width="100">
                <template slot-scope="scope">
                  <el-button v-show="scope.row.isEditable" type="success" size="mini" icon="el-icon-check" @click="saveDetail(scope.row)" />
                  <el-button v-show="!scope.row.isEditable" type="primary" size="mini" icon="el-icon-edit" @click="showEditable(scope.row)" />
                  <el-button v-show="!scope.row.isEditable" type="danger" size="mini" icon="el-icon-delete" @click="removeDetail(scope.$index, scope.row)" />
                </template>
              </el-table-column>
            </el-table>
          </el-form>
          <!-- 仓库选择弹窗 -->
          <el-dialog :visible.sync="dialogFormVisible" title="仓库" width="650px">
            <el-form ref="warehouseForm" :inline="true" :model="warehouseForm" size="small">
              <!-- <el-form-item >
                <el-input v-model="warehouseForm.warehouseCode" placeholder="仓库代码" style="width:100px" clearable @clear="listWarehouseinfo"/>
              </el-form-item>
              <el-form-item >
                <el-input v-model="warehouseForm.warehouseName" placeholder="仓库名称" clearable @clear="listWarehouseinfo"/>
              </el-form-item>
              <el-form-item >
                <el-select v-model="warehouseForm.warehouseType" placeholder="仓库类别" style="width:100px" clearable @change="listWarehouseinfo">
                  <el-option v-for="item in warehousetypeList" :key="item.code" :label="item.codeName" :value="item.code" clearable/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button icon="el-icon-search" size="small" @click="listWarehouseinfo"/>
              </el-form-item> -->
            </el-form>
            <el-table :data="warehouseData.filter(data => !warehouseForm.warehouseName || data.warehouseName.includes(warehouseForm.warehouseName))">
              <el-table-column property="warehouseCode" label="仓库代码" width="100px"/>
              <el-table-column property="warehouseName" label="仓库名称" width="300px"/>
              <el-table-column :formatter="getWarehouseType" property="warehouseType" label="仓库类别" width="100px"/>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button type="primary" size="mini" @click="edit(scope.row)">选择</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-dialog>
          <!-- 省市区代码选择弹窗 -->
          <el-dialog :visible.sync="receiverDialogVisible" title="省市区" width="550px">
            <el-table :data="receiverData">
              <el-table-column property="province" label="省代码" width="120px"/>
              <el-table-column property="city" label="市代码" width="120px"/>
              <el-table-column property="region" label="区代码" width="120px"/>
              <el-table-column label="操作" align="center">
                <template slot-scope="scope">
                  <el-button type="primary" size="mini" @click="editPCR(scope.row)">选择</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-dialog>

          <el-dialog :visible.sync="dialogaddVisible" :close-on-click-modal="false" title="批量添加" width="800px">
            <el-form ref="addform" :model="addform" size="mini">
              <!-- <el-form-item label-width="200px" prop="modelNo" label="型号">
                <el-input v-model="addform.modelNo" size="small" style="width:200px" autocomplete="off" placeholder="请用逗号隔开每个型号"/>
              </el-form-item>
              <el-form-item label-width="200px" prop="quantity" label="数量">
                <el-input v-model="addform.quantity" size="small" style="width:200px" autocomplete="off" placeholder="请用逗号隔开每个数量"/>
              </el-form-item>
              <el-form-item label-width="200px" prop="price" label="单价">
                <el-input v-model="addform.price" size="small" style="width:200px" autocomplete="off"/>
              </el-form-item>
              <el-form-item label-width="200px" prop="dlvDate" label="交货期">
                <el-date-picker v-model="addform.dlvDate" :picker-options="handlePickerOptions" size="small" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width:200px"/>
              </el-form-item>
              <el-form-item label-width="200px" prop="orgCurrency" label="原币">
                <el-input v-model="addform.orgCurrency" size="small" style="width:200px" autocomplete="off"/>
              </el-form-item>
              <el-form-item label-width="200px" prop="orgPrice" label="原币单价">
                <el-input v-model="addform.orgPrice" size="small" style="width:200px" autocomplete="off"/>
              </el-form-item> -->
              <span class="spa">请按照 型号,数量,单价,交货期,原币,原币单价的顺序录入，可以只输入型号，数量。型号和数量是必须录入项 </span>
              <el-form-item label-width="70px" prop="price" label="数据项:" style="margin-top: 20px;">
                <el-input v-model="addform.list" :autosize="{ minRows: 9, maxRows: 10}" size="small" type="textarea" autocomplete="off" placeholder="请用逗号隔开每一项"/>
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button @click="dialogaddVisible = false">取 消</el-button>
              <el-button type="primary" @click="addBatchDetail('addform')">保存</el-button>
            </div>
          </el-dialog>
        </el-tab-pane>
        <el-dialog :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
          <product-search v-if="productVisible" ref="ProductSearch"/>
        </el-dialog>
        <el-tab-pane :key="'second'" label="生成订单" name="second">
          <el-form ref="condition" :inline="true" :model="condition" size="mini">
            <el-form-item prop="groupNo">
              <el-input v-model="condition.groupNo" size="small" style="width: 150px" placeholder="请输入批次号"/>
            </el-form-item>
            <el-form-item prop="customerNo">
              <el-input v-model="condition.customerNo" size="small" style="width: 100px" placeholder="客户代码"/>
            </el-form-item>
            <el-form-item prop="userNo">
              <el-input v-model="condition.userNo" size="small" style="width: 100px" placeholder="用户代码"/>
            </el-form-item>
            <el-form-item prop="orderNo">
              <el-input v-model="condition.orderNo" size="small" style="width: 150px" placeholder="订单号"/>
            </el-form-item>
            <el-form-item prop="createUser">
              <el-input v-model="condition.createUser" size="small" style="width: 150px" placeholder="创建人"/>
            </el-form-item>
            <el-form-item prop="status">
              <el-select v-model="condition.status" placeholder="订单状态" style="width: 115px" size="small">
                <el-option v-for="item in statusTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
            </el-form-item>
            <el-form-item prop="orderType">
              <el-select v-model="condition.orderType" placeholder="申请类型" style="width: 150px" size="small">
                <el-option v-for="item in orderTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-date-picker
                v-model="startTimeAndEndTime"
                type="daterange"
                align="center"
                unlink-panels
                range-separator="-"
                start-placeholder="从创建时间"
                end-placeholder="创建时间到"
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd HH:mm:ss"
                size="small"/>
            </el-form-item>
            <el-form-item>
              <el-button type="success" plain icon="el-icon-refresh" size="small" @click="flash('condition')">刷新</el-button>
              <el-button type="primary" plain icon="el-icon-search" size="small" @click="findList(1)">查询</el-button>
              <el-button type="warning" plain icon="el-icon-edit-outline" size="small" @click="update">编辑</el-button>
              <el-button type="danger" plain icon="el-icon-delete-solid" size="small" @click="deleteOrder">删除</el-button>
              <el-button :loading="loadingBtu" type="primary" size="small" icon="el-icon-check" @click="createOrders()" >生成订单</el-button>
              <el-button :loading="exportLoadingBtu" type="success" plain icon="el-icon-download" size="small" @click="exportData('condition')">导出</el-button>
            </el-form-item>
            <vxe-grid
              ref="multipleTable"
              :loading="loading"
              :pager-config="tablePage"
              :data="tableData"
              :columns="tableColumn"
              :auto-resize="true"
              border
              size="mini"
              show-overflow
              resizable
              highlight-hover-row
              @page-change="handlePageChange">
              <template v-slot:ManuDlvfmtDate="{ row }">
                <span>{{ dateFormat('YYYY-mm-dd',row.manuDlvDate) }}</span>
              </template>
              <template v-slot:DlvfmtDate="{ row }">
                <span>{{ dateFormat('YYYY-mm-dd',row.dlvDate) }}</span>
              </template>
              <template v-slot:fmtDate="{ row }">
                <span>{{ dateFormat('YYYY-mm-dd HH:MM',row.createTime) }}</span>
              </template>
              <template v-slot:ordertype="{ row }">
                <span>{{ getOrderType(row.orderType) }}</span>
              </template>
              <template v-slot:orgtype="{ row }">
                <span>{{ getOrgType(row.orgCurrency) }}</span>
              </template>
              <template v-slot:statustype="{ row }">
                <span>{{ getStatusType(row.status) }}</span>
              </template>
              <template v-slot:transtype="{ row }">
                <span>{{ getTransType(row.transType) }}</span>
              </template>
              <template v-slot:specMark="{ row }">
                <span>{{ getSpecMark(row.specMark) }}</span>
              </template>
            </vxe-grid>
          </el-form>
        </el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>

<script>
import { saveOrderdata, listOrderdata, createSpecOrder, findSpecOrder, deleteSpecOrder, exportListSpecOrder } from '@/api/order/specorder'
import { getCustomerByNo } from '@/api/common/customer'
// import { getModelEPrice } from '@/api/stock/shikomi'
import { findDeptsToTree } from '@/api/organ'
import { getDictDataByPid, listWarehouse } from '@/api/common/dict'
import { findyCusDeliveryInfo } from '@/api/common/customer'
import { getModelInfoForOrder } from '@/api/product'
import ProductSearch from '../../product/index'
import { findAreas } from '@/api/warehouseManage'

export default {
  name: 'SpecOrder',
  components: { ProductSearch },
  data() {
    return {
      group: false,
      loadadd: false,
      idshow: false,
      disabled: true,
      loadingBtu: false,
      exportTypeShow: false,
      handleshow: false,
      price: true,
      complaint: false,
      dlvDate: true,
      manuDlvDate: false,
      loading: false,
      receiver: false,
      dialogaddVisible: false,
      dialogFormVisible: false,
      productVisible: false,
      deptShow: false,
      deptShow2: false,
      warehouseShow: false,
      receiverDialogVisible: false,
      startTimeAndEndTime: '',
      activeName: 'first',
      selection: [],
      warehouseData: [],
      warehousetypeList: [],
      cpnysData: [],
      Areas: [],
      customerNoOptions: [],
      customerNoLoading: false,
      userNoOptions: [],
      userNoLoading: false,
      exportLoadingBtu: false,
      deptNoOptions: [],
      receiverData: [],
      aaaa: [],
      warehouseForm: {},
      forms: {
        orderItem: '',
        quantity: '',
        modelNo: '',
        orgCurrency: '',
        orgPrice: '',
        price: '',
        eprice: '',
        dlvDate: '',
        cproductNo: '',
        remark: ''
      },
      form: {
        groupNo: '',
        customerNo: '',
        customerName: '',
        userNo: '',
        userName: '',
        tradeCompanyId: 'CN0',
        corderNo: '',
        dlvType: '',
        orderType: '',
        dlvEntire: '',
        area: [],
        exportType: '',
        exportWarehouse: '',
        receiverCompany: '',
        receiverAddress: '',
        receiverName: '',
        receiverPhone: '',
        deptNo: '',
        remark: '',
        province: '',
        city: '',
        district: '',
        list: []
      },
      addform: {
        list: ''
      },
      condition: {
        groupNo: '',
        customerNo: '',
        status: '1',
        userNo: '',
        orderNo: '',
        orderType: '',
        createUser: '',
        shipTimeStart: '', // 创建时间开始
        shipTimeEnd: '' // 创建时间结束
      },
      rule: {
        customerNo: [
          { required: true, message: '请输入客户代码', trigger: 'blur' },
          { required: true, message: '请输入客户代码', trigger: 'change' }
        ],
        orderType: [
          { required: true, message: '请选择申请类型', trigger: 'change' }
        ],
        dlvType: [
          { required: true, message: '请选择出货方式', trigger: 'change' }
        ],
        dlvEntire: [
          { required: true, message: '请选择处理方式', trigger: 'change' }
        ],
        exportWarehouse: [
          { required: true, message: '请选择报关地点', trigger: 'change' }
        ],
        list: {
          quantity: [
            { required: true, message: '请填写数量', trigger: 'blur' }
          ],
          modelNo: [
            { required: true, message: '请填写型号', trigger: 'blur' }
          ],
          eprice: [
            { required: true, message: '无E价型号不允许添加!', trigger: 'change' }
          ],
          price: [
            { required: true, message: '无单价允许添加!', trigger: 'change' }
          ],
          dlvDate: [
            { required: true, message: '无交货期不允许添加!', trigger: 'change' }
          ],
          manuDlvDate: [
            { required: true, message: '无交货期不允许添加!', trigger: 'change' }
          ],
          transType: [
            { required: true, message: '无运输方式不允许添加!', trigger: 'change' }
          ]
        }
      },
      menuData: {
        // 仓库类别菜单
        Areas: []
      },
      tableData: [],
      orderTypeOptions: [],
      outTypeOptions: [],
      addressTypeOptions: [],
      companyTypeOptions: [],
      orgTypeOptions: [],
      handleTypeOptions: [],
      outstockTypeOptions: [],
      statusTypeOptions: [],
      transTypeOptions: [],
      specMarkOptions: [
        { code: '0', codeName: '正常' },
        { code: '1', codeName: '板' },
        { code: '2', codeName: '阀' }
      ],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 20,
        align: 'right',
        pageSizes: [20, 50, 100, 200, 500],
        layouts: [
          'Sizes',
          'PrevJump',
          'PrevPage',
          'Number',
          'NextPage',
          'NextJump',
          'FullJump',
          'Total'
        ],
        perfect: true
      },
      value: '',
      tableColumn: [
        { type: 'checkbox', align: 'center' },
        { field: 'id', title: 'id', align: 'center', visible: false },
        { field: 'groupNo', title: '批次号', align: 'center' },
        { field: 'orderNo', title: '订单号', align: 'center' },
        { field: 'orderItem', title: '项号', align: 'center' },
        { field: 'customerNo', title: '客户代码', align: 'center' },
        { field: 'userNo', title: '用户代码', align: 'center' },
        { field: 'status', title: '订单状态', align: 'center', slots: { default: 'statustype' }},
        { field: 'orderType', title: '申请类型', align: 'center', slots: { default: 'ordertype' }},
        { field: 'modelNo', title: '型号', align: 'center' },
        { field: 'quantity', title: '数量', align: 'center' },
        { field: 'orgCurrency', title: '原币', align: 'center', slots: { default: 'orgtype' }},
        { field: 'orgPrice', title: '原币单价', align: 'center' },
        { field: 'price', title: '单价(RMB)', align: 'center' },
        { field: 'dlvDate', title: '交货期', align: 'center', slots: { default: 'DlvfmtDate' }},
        { field: 'manuDlvDate', title: '制造出荷日', align: 'center', slots: { default: 'ManuDlvfmtDate' }},
        { field: 'cproductNo', title: '客户型号', align: 'center' },
        { field: 'corderNo', title: '客户订单号', align: 'center' },
        { field: 'complaintNo', title: '投诉号', align: 'center' },
        { field: 'transType', title: '运输方式', align: 'center', slots: { default: 'transtype' }},
        { field: 'specMark', title: '组装方式', align: 'center', slots: { default: 'specMark' }},
        { field: 'remark', title: '备注', align: 'center' },
        { field: 'createTime', title: '创建时间', align: 'center', slots: { default: 'fmtDate' }},
        { field: 'createUser', title: '创建人', align: 'center' }
      ],
      handlePickerOptions: {
        disabledDate(date) {
          return date.getTime() < Date.now() - (24 * 3600 * 1000)
        }
      },
      classCode: '1009',
      outCode: '1021',
      addressCode: '2012',
      companyCode: '1003',
      orgCode: '4303',
      handleCode: '1014',
      outstockCode: '4201',
      statusCode: '1016',
      warehouseType: '4001',
      transType: '3003'
    }
  },
  created() {
    this.iniData()
    // this.listWarehouseinfo()
    this.getAreas()
    this.findDeptNos()
    // this.findList()
  },
  methods: {
    exportData() {
      this.exportLoadingBtu = true
      const data = {
        groupNo: this.condition.groupNo,
        customerNo: this.condition.customerNo,
        userNo: this.condition.userNo,
        orderType: this.condition.orderType,
        shipTimeStart: this.condition.shipTimeStart,
        shipTimeEnd: this.condition.shipTimeEnd,
        status: this.condition.status,
        orderNo: this.condition.orderNo,
        createUser: this.condition.createUser
      }
      exportListSpecOrder(data).then(res => {
        console.log('size=> ', res.size)
        if (res.size === 0) {
          this.$message({
            duration: 4000,
            message: '暂无可导出数据',
            type: 'error'
          })
          this.exportLoadingBtu = false
          return
        }
        const fileName = '特殊订单数据导出.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportLoadingBtu = false
      }).catch(error => {
        console.error(error)
        this.exportLoadingBtu = false
      })
    },
    iniData() {
      getDictDataByPid(this.classCode).then(result => {
        console.log(result)
        this.orderTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.outCode).then(result => {
        console.log(result)
        this.outTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.addressCode).then(result => {
        console.log(result)
        this.addressTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.companyCode).then(result => {
        console.log(result)
        this.companyTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.orgCode).then(result => {
        console.log(result)
        this.orgTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.handleCode).then(result => {
        console.log(result)
        this.handleTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.outstockCode).then(result => {
        console.log(result)
        this.outstockTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.statusCode).then(result => {
        console.log(result)
        this.statusTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.warehouseType).then(result => {
        console.log(result)
        this.warehousetypeList = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.transType).then(result => {
        console.log(result)
        this.transTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    tabchange() {
      if (this.activeName === 'second') {
        this.findList()
      }
    },
    selCustomer(val) {
      this.findyCusDeliveryInfo(val)
      getCustomerByNo(val).then(res => {
        if (res.success) {
          this.form.customerNo = res.content[0].customerNo
          this.form.customerName = res.content[0].name
          if (res.content[0].hrunitId != null) {
            this.form.deptNo = res.content[0].hrunitId.toString()
          }
        }
      })
    },
    selUserNo(val) {
      getCustomerByNo(val).then(res => {
        if (res.success) {
          this.form.userNo = res.content[0].customerNo
          this.form.userName = res.content[0].name
        }
      })
    },
    remoteMethodByCustomerNo(query) {
      this.customerNoLoading = true
      getCustomerByNo(query).then(res => {
        this.customerNoLoading = false
        if (res.success) {
          this.customerNoOptions = res.content
        } else {
          this.customerNoOptions = []
        }
      })
    },
    remoteMethodByUserNo(query) {
      this.userNoLoading = true
      getCustomerByNo(query).then(res => {
        this.userNoLoading = false
        if (res.success) {
          this.userNoOptions = res.content
        } else {
          this.userNoOptions = []
        }
      })
    },
    saveData(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.loadadd = true
          if (this.form.area !== null) {
            this.form.province = this.form.area[0]
            this.form.city = this.form.area[1]
            this.form.district = this.form.area[2]
          }

          if (this.form.dlvEntire === '2') {
            this.form.receiverCompany = ''
            this.form.receiverAddress = ''
            this.form.receiverName = ''
            this.form.receiverPhone = ''
            this.form.province = ''
            this.form.city = ''
            this.form.district = ''
          }
          // console.log(this.form.list)
          saveOrderdata(this.form).then(res => {
            if (res.success) {
              this.$message.success(res.message)
              this.loadadd = false
              this.form.groupNo = res.content.groupNo + ''
              for (let i = 0; i < res.content.list.length; i++) {
                this.form.list[i].id = res.content.list[i].id + ''
              }
              this.findDetail()
            } else {
              this.loadadd = false
              this.$message.error(res.message)
            }
          }).catch(error => {
            this.loadadd = false
            console.log(error)
          })
        } else {
          this.$message.error('请输入必填选项！')
          return false
        }
      })
    },
    findDetail() {
      var groupNo = this.form.groupNo
      if (groupNo === '') {
        return null
      }
      // this.$refs.form.resetFields()
      this.form.area = []
      findSpecOrder(groupNo).then(res => {
        if (res.success) {
          var list = res.content
          this.form.orderType = list[0].orderType + ''
          this.selectChange(this.form.orderType)
          this.form.tradeCompanyId = list[0].tradeCompanyId + ''
          this.form.customerNo = list[0].customerNo
          this.form.corderNo = list[0].corderNo
          this.form.userNo = list[0].userNo
          this.form.deptNo = list[0].deptNo
          this.form.receiverCompany = list[0].receiverCompany
          this.form.receiverName = list[0].receiverName
          this.form.receiverPhone = list[0].receiverPhone
          this.form.receiverAddress = list[0].receiverAddress
          // this.form.province = list[0].province
          // this.form.city = list[0].city
          // this.form.district = list[0].district
          this.form.area.push(list[0].province)
          this.form.area.push(list[0].city)
          this.form.area.push(list[0].district)
          console.log(this.form.area)
          this.form.dlvType = list[0].dlvType + ''
          this.form.dlvEntire = list[0].dlvEntire + ''
          this.dlvEntireChange(this.form.dlvEntire)
          this.form.exportType = list[0].exportType + ''
          this.form.exportWarehouse = list[0].exportWarehouse + ''
          // if (this.form.exportWarehouse !== '' || this.form.exportWarehouse != null) {
          //   this.getWarehouse(this.form.exportWarehouse)
          // }
          this.form.remark = list[0].remark.split(' ')[0]
          this.form.list = []
          for (let i = 0; i < list.length; i++) {
            const item = {
              id: list[i].id,
              orderItem: list[i].orderItem,
              modelNo: list[i].modelNo,
              quantity: list[i].quantity,
              price: list[i].price,
              dlvDate: list[i].dlvDate,
              manuDlvDate: list[i].manuDlvDate,
              orgCurrency: list[i].orgCurrency,
              orgPrice: list[i].orgPrice,
              productName: list[i].productName,
              complaintNo: list[i].complaintNo,
              eprice: list[i].eprice,
              cproductNo: list[i].cproductNo,
              transType: list[i].transType,
              specMark: list[i].specMark,
              remark: list[i].remark.split(' ')[1],
              isEditable: false
            }
            // bug 16930.9
            if (list[i].price === null) {
              item.price = list[i].eprice
            }
            this.form.list.push(item)
          }
          this.group = true
          this.form.groupNo = groupNo
          getCustomerByNo(this.form.customerNo).then(data => {
            this.form.customerName = data.content[0].name
          })
          if (this.form.userNo !== '') {
            getCustomerByNo(this.form.userNo).then(data => {
              this.form.userName = data.content[0].name
            })
          }
        } else {
          this.group = false
          this.$message.error(res.message)
        }
      }).catch(error => {
        this.form.groupNo = groupNo
        this.group = false
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.startTimeAndEndTime === null) {
        this.condition.shipTimeStart = ''
        this.condition.shipTimeEnd = ''
      } else {
        this.condition.shipTimeStart = this.startTimeAndEndTime[0]
        this.condition.shipTimeEnd = this.startTimeAndEndTime[1]
      }
    },
    findList(val) {
      this.loading = true
      this.setDateCondition()
      const data = {
        groupNo: this.condition.groupNo,
        customerNo: this.condition.customerNo,
        userNo: this.condition.userNo,
        orderType: this.condition.orderType,
        shipTimeStart: this.condition.shipTimeStart,
        shipTimeEnd: this.condition.shipTimeEnd,
        status: this.condition.status,
        createUser: this.condition.createUser,
        orderNo: this.condition.orderNo
      }
      if (val === 1) {
        this.tablePage.currentPage = 1
      }
      const page = {
        pageNumber: this.tablePage.currentPage,
        pageSize: this.tablePage.pageSize
      }
      listOrderdata(data, page).then(res => {
        if (res.content == null) {
          this.tableData = []
        } else {
          this.tableData = res.content.list
        }
        this.loading = false
        this.tablePage.total = res.content.total
        if (res.code === '200') {
          // this.$message.success('查询成功')
        } else {
          this.tableData = []
        }
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    getAreas() {
      findAreas().then(res => {
        if (res.success) {
          this.menuData.Areas = res.data
        }
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
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.findList()
    },
    selectChange(val) {
      this.value = val
      // 重置所有字段的显示状态
      this.exportTypeShow = false
      this.idshow = false
      this.handleshow = false
      this.warehouseShow = false
      this.receiver = false
      this.deptShow2 = false
      this.price = false
      this.complaint = false
      this.dlvDate = false
      this.manuDlvDate = false
      this.form.dlvEntire = ''
      this.form.exportType = ''
      this.form.exportWarehouse = ''

      // 根据选择的值设置字段的显示状态
      switch (this.value) {
        case '12': // 一般贸易单
          this.exportTypeShow = true
          this.warehouseShow = true
          this.receiver = true
          this.price = true
          this.dlvDate = true
          break
        case '24': // DR补采购订单
          this.handleshow = true
          this.warehouseShow = true
          this.complaint = true
          this.price = true
          this.manuDlvDate = true
          this.form.dlvEntire = '2'
          break
        case '25': // CR补采购订单
          this.handleshow = true
          this.warehouseShow = true
          this.complaint = true
          this.price = true
          this.dlvDate = true
          this.form.dlvEntire = '2'
          break
        case '11': // 国内集团销售订单
        case '20': // BIN补库订单
        case '21': // 先行补库订单
          break
        default:
          break
      }
    },
    dlvEntireChange(val) {
      if (val === '1') {
        this.receiver = true
      }
      if (val === '2') {
        this.receiver = false
      }
    },
    batchadd(addform) {
      this.dialogaddVisible = true
      this.$refs.addform.resetFields()
    },
    addOrder(form) {
      this.$refs.form.resetFields()
      this.form.receiverAddress = ''
      this.form.receiverName = ''
      this.form.receiverPhone = ''
      this.form.deptNo = ''
      this.receiver = false
      this.group = false
      this.deptShow = false
      this.deptShow2 = false
      this.warehouseShow = false
      this.form.receiverCompany = ''
      this.form.province = ''
      this.form.city = ''
      this.form.district = ''
      this.form.list = []
      this.form.area = []
      this.price = true
      this.handleshow = false
      this.exportTypeShow = false
    },
    createOrders() {
      this.loadingBtu = true
      if (this.$refs.multipleTable.getCheckboxRecords().length <= 0) {
        this.$message.warning('请选择要生成的订单')
        this.loadingBtu = false
        return
      }
      if (this.$refs.multipleTable.getCheckboxRecords()[0].status === 2) {
        this.$message.error('请不要重复生成订单!')
        this.loadingBtu = false
        return
      }
      createSpecOrder(this.$refs.multipleTable.getCheckboxRecords()).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          const page = {
            pageNumber: this.tablePage.currentPage,
            pageSize: this.tablePage.pageSize
          }
          listOrderdata(this.condition, page).then(res => {
            if (res.content == null) {
              this.tableData = []
            } else {
              this.tableData = res.content.list
            }
            this.tablePage.total = res.content.total
          }).catch(error => {
            console.log(error)
          })
        } else {
          this.$message.error(res.message)
        }
        this.loadingBtu = false
      }).catch(error => {
        this.loadingBtu = false
        console.log(error)
      })
    },
    // selectable(row, index) {
    //   // console.log(row)
    //   if (row.id != null) {
    //     return false
    //   } else {
    //     return true
    //   }
    // },
    flash(condition) {
      this.startTimeAndEndTime = ''
      this.$refs[condition].resetFields()
      this.loading = true
      const data = {
        status: 1
      }
      const page = {
        pageNumber: this.tablePage.currentPage = 1,
        pageSize: this.tablePage.pageSize = 20
      }
      listOrderdata(data, page).then(res => {
        this.tableData = res.content.list
        this.loading = false
        this.tablePage.total = res.content.total
        this.$message.success('刷新成功')
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    editReceiver() {
      this.receiverDialogVisible = true
    },
    editPCR(row) {
      this.form.province = row.province
      this.form.city = row.city
      this.form.district = row.region
      this.receiverDialogVisible = false
    },
    findyCusDeliveryInfo(cusNo) {
      if (this.form.dlvEntire === '2') {
        return
      }
      findyCusDeliveryInfo(cusNo).then(res => {
        if (res.length <= 0) {
          this.$message.warning('未查询到收货地址，请自行填写')
          this.form.receiverCompany = ''
          this.form.receiverName = ''
          this.form.receiverPhone = ''
          this.form.receiverAddress = ''
          this.form.province = ''
          this.form.city = ''
          this.form.district = ''
        } else {
          this.receiverData = res
          this.form.receiverCompany = res[0].deliveryname
          this.form.receiverName = res[0].contactList[0].contactperson
          this.form.receiverPhone = res[0].contactList[0].phone
          this.form.receiverAddress = res[0].detailAddress
          this.form.province = res[0].province
          this.form.city = res[0].city
          this.form.district = res[0].region
        }
      }).catch(error => {
        console.log(error)
      })
    },
    checkEpric(scope) {
      // console.log(scope)
      if (this.form.list[scope].modelNo === '') {
        return null
      }
      getModelInfoForOrder(this.form.list[scope].modelNo).then(res => {
        this.form.list[scope].eprice = res.content.eprice
        this.form.list[scope].productName = res.content.productName
        // if (!res.success) {
        //   this.$message.error(res.message)
        // }
      }).catch(error => {
        this.form.list[scope].eprice = ''
        console.log(error)
      })
    },
    update() {
      if (this.$refs.multipleTable.getCheckboxRecords().length <= 0) {
        this.$message.error('请选择要编辑的数据')
        return null
      }
      if (this.$refs.multipleTable.getCheckboxRecords()[0].status === 2) {
        this.$message.error('已生成的订单不可编辑!')
        return
      }
      this.$refs.form.resetFields()
      this.form.deptNo = ''
      this.form.list = []
      this.form.groupNo = this.$refs.multipleTable.getCheckboxRecords()[0].groupNo
      this.activeName = 'first'
      this.findDetail()
    },
    deleteOrder() {
      var list = this.$refs.multipleTable.getCheckboxRecords()
      if (list.length <= 0) {
        this.$message.error('请选择要删除的数据~')
        return null
      }
      this.$confirm('确认要删除这些数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        for (let i = 0; i < list.length; i++) {
          deleteSpecOrder(list[i].id).then(res => {
            if (res.success) {
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              // this.$message.success(res.content)
            } else {
              this.$message.error(res.message)
            }
          })
        }
        listOrderdata(this.condition, page).then(res => {
          if (res.content == null) {
            this.tableData = []
          // this.loading = false
          } else {
            this.tableData = res.content.list
          // this.loading = false
          }
          this.tablePage.total = res.content.total
        }).catch(error => {
          console.log(error)
        // this.loading = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
      const page = {
        pageNumber: this.tablePage.currentPage,
        pageSize: this.tablePage.pageSize
      }
    },
    findDeptNos() {
      var classCode = '1003'
      getDictDataByPid(classCode).then(res => {
        this.cpnysData = res.content
      }).catch(error => {
        console.log(error)
        return
      })
      findDeptsToTree().then(res => {
        this.deptNoOptions = res.content
        if (!res.success) {
          return
        }
        for (var j = 0; j < this.deptNoOptions.length; j++) {
          for (var k = 0; k < this.cpnysData.length; k++) {
            if (this.deptNoOptions[j].id === this.cpnysData[k].extNote1) {
              this.deptNoOptions[j].flag = true
            }
          }
        }
        for (var j2 = 0; j2 < this.deptNoOptions.length; j2++) {
          if (!this.deptNoOptions[j2].flag) {
            this.deptNoOptions.splice(j2, 1)
            j2--
          }
        }
        if (res.content.length === 0) {
          this.findDeptNos()
          return
        }
      }).catch(error => {
        console.log(error)
      })
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: this.warehouseForm.warehouseType,
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        for (let i = 0; i < res.content.length; i++) {
          if (res.content[i].warehouseType === 'MASTER') {
            this.warehouseData.push(res.content[i])
          }
        }
        // this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    selectWarehouse() {
      this.dialogFormVisible = true
    },
    edit(row) {
      this.form.exportWarehouse = row.warehouseCode
      this.addressTypeOptions = []
      const list = { code: row.warehouseCode, codeName: row.warehouseName }
      this.addressTypeOptions.push(list)
      this.dialogFormVisible = false
    },
    dateFormat(fmt, date) {
      if (date == null) {
        return null
      }
      let ret = ''
      date = new Date(date)
      const opt = {
        'Y+': date.getFullYear().toString(), // 年
        'm+': (date.getMonth() + 1).toString(), // 月
        'd+': date.getDate().toString(), // 日
        'H+': date.getHours().toString(), // 时
        'M+': date.getMinutes().toString(), // 分
        'S+': date.getSeconds().toString() // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
      }
      for (const k in opt) {
        ret = new RegExp('(' + k + ')').exec(fmt)
        if (ret) {
          fmt = fmt.replace(
            ret[1],
            ret[1].length === 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
          )
        }
      }
      return fmt
    },
    getWarehouse(val) {
      if (val === '') {
        return
      }
      const stu = val + ''
      const obj = this.warehouseData.filter(item => item.warehouseCode === stu)[0]
      this.addressTypeOptions = []
      const list = { code: obj.warehouseCode, codeName: obj.warehouseName }
      this.addressTypeOptions.push(list)
    },
    getDeptNo(val) {
      const obj = this.deptNoOptions.filter(item => item.code === val)[0]
      this.deptNoOptions = obj
    },
    getCurrency(val) {
      const obj = this.orgTypeOptions.filter(item => item.code === val)[0]
      return obj ? obj.codeName : val
    },
    getOrderType(val) {
      const stu = val + ''
      const obj = this.orderTypeOptions.filter(item => item.code === stu)[0]
      return obj ? obj.codeName : ''
    },
    getOrgType(val) {
      const obj = this.orgTypeOptions.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getStatusType(val) {
      const stu = val + ''
      const obj = this.statusTypeOptions.filter(item => item.code === stu)[0]
      return obj ? obj.codeName : ''
    },
    getTransType(val) {
      const stu = val + ''
      const obj = this.transTypeOptions.filter(item => item.code === stu)[0]
      return obj ? obj.codeName : ''
    },
    getSpecMark(val) {
      const stu = val + ''
      const obj = this.specMarkOptions.filter(item => item.code === stu)[0]
      return obj ? obj.codeName : ''
    },
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    descFormatter(data, id) {
      if (data === null) {
        return id
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === id.toString()) {
          return item.codeName
        }
      }
      return id
    },
    change(e) {
      this.$forceUpdate()
    },
    tableRowClassName({ row, rowIndex }) {
      this.$set(row, 'rowIndex', rowIndex)
    },
    // handerQuantity(row) {
    //   row.quantity = row.isTransOut ? -Math.abs(row.quantity) : Math.abs(row.quantity)
    // },
    handleSelectionChange(val) {
      const rowIndexs = []
      for (const row of val) {
        rowIndexs.push(row.rowIndex)
      }
      this.selection = rowIndexs
    },
    addDetail() {
      this.form.list.push({
        orderItem: '',
        modelNo: '',
        quantity: '',
        orgCurrency: '',
        orgPrice: '',
        price: '',
        eprice: '',
        dlvDate: '',
        cproductNo: '',
        transType: '0',
        specMark: '0',
        remark: '',
        isEditable: true
      })
    },
    saveDetail(row) {
      row.isEditable = false
      if (row.price === '' && row.eprice !== '') {
        row.price = row.eprice
      }
    },
    showEditable(row) {
      row.isEditable = true
    },
    removeDetail(index, row) {
      if (!row.hasOwnProperty('id')) {
        this.form.list.splice(index, 1)
        this.$refs.form.validate() // 刷新表单较验
        return null
      }
      deleteSpecOrder(row.id).then(res => {
        if (res.success) {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          // this.$message.success(res.content)
        } else {
          this.$message.error(res.content)
        }
      })
      this.form.list.splice(index, 1)
      this.$refs.form.validate() // 刷新表单较验
    },
    addBatchDetail(addform) {
      // console.log(this.addform.list)
      const reg = new RegExp('\\n', 'g')
      const reg2 = new RegExp('，', 'g')
      var info = this.addform.list.replace(reg, '~').replace(/\s+|&nbsp;/ig, '~').replace(reg2, ',').split('~')

      // console.log(info)
      for (let i = 0; i < info.length; i++) {
        const item = {
          modelNo: info[i].split(',')[0],
          quantity: info[i].split(',')[1],
          price: info[i].split(',')[2],
          dlvDate: info[i].split(',')[3],
          orgCurrency: info[i].split(',')[4],
          orgPrice: info[i].split(',')[5],
          eprice: '',
          isEditable: false
        }
        this.form.list.push(item)
        var id = i + parseInt(this.form.list.length) - (i + 1)
        this.checkEpric(id)
      }
      this.dialogaddVisible = false
    },
    querySearchAsync(customerNo, cb) {
      // const cus = { customerNo: this.form.customerNo }
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray

        if (results !== null && results.length <= 0) {
          results = customerNo ? customerArray.filter(this.createFilter(customerNo)) : customerArray
        }
        cb(results)
      })
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.customerNo.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    createFilter(queryString) {
      return (state) => {
        return (state.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    Changeselect(item) {
      this.findyCusDeliveryInfo(item.customerNo)
      this.form.customerNo = item.customerNo
      // this.form.receiverCompany = item.cstmname
      this.form.customerName = item.name
      if (item.hrunitId != null) {
        this.form.deptNo = item.hrunitId.toString()
      }
    },
    ChangeUserNo(item) {
      this.form.userNo = item.customerNo
      this.form.userName = item.name
    },
    deptChange(value) {
      if (value.length === 0) {
        return
      }
      this.form.deptNo = value[value.length - 1]
    }

  }
}
</script>

<style scoped>
  .select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
  }
  .searchtable {
    margin-top: 5px;
  }
  .tab{
    /* width: 1500px; */
    height: 100px;
  }
  .button{
    margin-left: 30%;
  }
  .span-box {
  display: inline-block;
  position: relative;
}
  .font{
  font-weight: bold;
  margin-right: 10px;
  }
  .spa{
    margin-left: 70px;
    font-weight: lighter;
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
</style>
