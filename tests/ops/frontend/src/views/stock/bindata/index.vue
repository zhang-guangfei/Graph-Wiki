<template>
  <div class="app-container">
    <el-form
      ref="form"
      :inline="true"
      :model="form"
      size="mini"
      label-width="100px"
      style="margin-top: 10px"
    >
      <el-form-item size="mini">
        <el-select
          v-model="form.stockType"
          placeholder="请选择"
          style="width: 125px"
          size="mini"
        >
          <el-option
            v-for="item in stockTypeOptions"
            :key="item.code"
            :value="item.code"
            :label="item.codeName"
          />
        </el-select>
      </el-form-item>
      <el-form-item size="mini">
        <el-input v-model="form.warehouseCode" placeholder="仓库代码" style="width: 100px" clearable @change="searchData" />
        <el-button type="primary" size="mini" @click="selectWarehouses">···</el-button>
      </el-form-item>
      <el-form-item size="mini">
        <el-input v-model="form.modelNo" placeholder="型号" clearable />
      </el-form-item>
      <el-form-item size="mini">
        <el-input v-model="form.customerNo" placeholder="客户" style="width: 90px" clearable />
      </el-form-item>
      <el-form-item size="mini">
        <el-input v-model="form.propertyID" placeholder="库存属性ID" style="width: 100px" clearable />
        <el-button icon="el-icon-plus" plain type="success" size="mini" @click="showproperty">选择</el-button>
      </el-form-item>
      <el-form-item size="mini">
        <el-input v-model="form.orderType" placeholder="订单类别" style="width: 100px" clearable />
      </el-form-item>
      <el-form-item size="mini">
        <el-input v-model="form.supplierCode" placeholder="供应商" style="width: 100px" clearable />
        <el-button type="primary" size="mini" @click="selectSupplier">···</el-button>
      </el-form-item>
      <el-form-item>
        <el-checkbox v-model="form.isdelFlag" size="mini">包括已删除</el-checkbox>
      </el-form-item>
      <el-form-item>
        <el-button v-permission="['480475']" type="primary" size="mini" icon="el-icon-search" @click="searchData">查询</el-button>
      </el-form-item>
    </el-form>
    <!--仓库弹窗-->
    <el-dialog :visible.sync="dialogWarehouseVisible" title="仓库" width="500px">
      <el-table :data="warehouses.filter(data => !warehouses.name || data.name.includes(warehouses.name))">
        <el-table-column prop="warehouseCode" label="仓库代码" />
        <el-table-column prop="warehouseName" label="仓库名称" />
        <el-table-column label="操作" align="center" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="editWarehouses(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <!--供应商弹窗-->
    <el-dialog :visible.sync="dialogFormVisible" title="供应商" width="500px">
      <el-form ref="warehouseForm" :inline="true" :model="supplierForm" size="small">
        <el-form-item >
          <el-input v-model="supplierForm.companyId" placeholder="供应商代码" style="width:100px" clearable/>
        </el-form-item>
        <el-form-item >
          <el-input v-model="supplierForm.name" placeholder="供应商名称" clearable/>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="small" @click="listSupplierinfo"/>
        </el-form-item>
      </el-form>
      <el-table :data="supplieData.filter(data => !supplierForm.name || data.name.includes(supplierForm.name))">
        <el-table-column prop="companyId" label="供应商代码" />
        <el-table-column prop="name" label="供应商名称" />
        <el-table-column label="操作" align="center" width="150">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="edit(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
    <el-divider />
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="上传"
      width="420px"
    >
      <div class="upload">
        <el-upload
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          :on-success="uploadSuccess"
          class="upload-demo"
          drag
          multiple
        >
          <div class="el-upload__text">{{ impMessage }}</div>
          <div class="el-upload__text" style="color:red">excel表太大则断开后台自动导入;支持xlsx格式</div>
          <div v-if="file !== null" class="el-upload__text">
            {{ file.name }}
          </div>
          <div v-else class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
        </el-upload>
      </div>
      <span slot="footer" class="dialog-footer">
        <!-- <el-button type="primary" @click="closeClick">关闭</el-button> -->
        <el-button type="primary" @click="checkImpBindataStatus">刷新</el-button>
        <el-button :loading="dowmBinDataImportLoad" type="primary" @click="dowmBinDataImport" >下载模板</el-button>
        <el-button type="success" @click="importData">
          <span v-loading="uploadStatus" element-loading-text="拼命上传中..." element-loading-spinner="el-icon-loading" element-loading-background="#66CDAA">{{
            uploadStatus ? "拼命上传中..." : "上传文件"
          }}</span>
        </el-button>
      </span>
    </el-dialog>
    <el-dialog
      :visible.sync="dialogaddVisible"
      :close-on-click-modal="false"
      title="添加"
      width="680px"
    >
      <el-form ref="addForm" :inline="true" :model="addForm">
        <el-form-item :required="true" label-width="110px" prop="stockType" label="库存类型" size="mini">
          <el-select
            v-model="addForm.stockType"
            placeholder="请选择"
            style="width: 180px"
          >
            <el-option
              v-for="item in stockTypeOptions"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="仓库代码" size="mini">
          <el-input
            v-model="addForm.warehouseCode"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="客户代码" size="mini">
          <el-input
            v-model="addForm.customerNo"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="型号" size="mini">
          <el-input
            v-model="addForm.modelNo"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="库房属性ID" size="mini">
          <el-input
            v-model="addForm.propertyID"
            style="width: 180px"
            autocomplete="off"
            @change="getOpsInventoryProperty"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="ppl代码" size="mini">
          <el-input
            v-model="addForm.ppl"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="项目号" size="mini">
          <el-input
            v-model="addForm.projectNo"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="库存类型代码" size="mini">
          <el-input
            v-model="addForm.inventoryTypeCode"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="客户群号" size="mini">
          <el-input
            v-model="addForm.groupCustomerNo"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="BIN数量" size="mini">
          <el-input
            v-model="addForm.qtyBin"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="BIN数" size="mini">
          <el-input
            v-model="addForm.binCell"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="产品类型" size="mini">
          <el-input
            v-model="addForm.prodType"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="箱型" size="mini">
          <el-input
            v-model="addForm.caseType"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="箱入数" size="mini">
          <el-input
            v-model="addForm.inCaseQty"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <!-- <el-form-item label-width="110px" label="位置代码" size="mini">
          <el-input
            v-model="addForm.positionNo"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item> -->
        <el-form-item label-width="110px" label="网框类别" size="mini">
          <el-input
            v-model="addForm.meshType"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="暂停补库" size="mini">
          <el-input
            v-model="addForm.adjustable"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="安全在库" size="mini">
          <el-input
            v-model="addForm.safeQty"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="设定频率" size="mini">
          <el-input
            v-model="addForm.setFreq"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="8频率" size="mini">
          <el-input
            v-model="addForm.freq"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <!-- <el-form-item :required="true" label-width="110px" label="新频率" size="mini">
          <el-input
            v-model="addForm.newFreq"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item> -->
        <el-form-item :required="true" label-width="110px" label="8平均" size="mini">
          <el-input
            v-model="addForm.mean"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <!-- <el-form-item :required="true" label-width="110px" label="新用量" size="mini">
          <el-input
            v-model="addForm.newMean"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item> -->
        <el-form-item label-width="110px" label="等级" size="mini">
          <el-input
            v-model="addForm.setLevel"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <!-- <el-form-item label-width="110px" label="新等级" size="mini">
          <el-input
            v-model="addForm.newLevel"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item> -->
        <el-form-item label-width="110px" label="采购分类" size="mini">
          <!-- <el-input
            v-model="addForm.poType"
            style="width: 180px"
            autocomplete="off"
          /> -->
          <el-select
            v-model="addForm.poType"
            placeholder="请选择"
            style="width: 180px"
            size="mini"
          >
            <el-option
              v-for="item in poTypeOptions"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <el-form-item label-width="110px" label="订单类别" size="mini">
          <el-input
            v-model="addForm.orderType"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="公/英制区分" size="mini">
          <el-input
            v-model="addForm.prodSeri"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="辖区" size="mini">
          <el-input
            v-model="addForm.stateRange"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="最小包装数量" size="mini">
          <el-input
            v-model="addForm.minPackageQty"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="直采1" size="mini">
          <el-input
            v-model="addForm.directPurchase"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="直送1" size="mini">
          <el-input
            v-model="addForm.directDelivery"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="自动周转补货" size="mini">
          <el-input
            v-model="addForm.autoRepl"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item :required="true" label-width="110px" label="#E价" size="mini">
          <el-input
            v-model="addForm.eprice"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="#Ecode" size="mini">
          <el-input
            v-model="addForm.ecode"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="#产品系列" size="mini">
          <el-input
            v-model="addForm.modelSeries"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="#供应商" size="mini">
          <el-input
            v-model="addForm.supplierCode"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" label="#原产地" size="mini">
          <el-input
            v-model="addForm.origin"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" prop="binType" label="运营区分" size="mini">
          <el-input
            v-model="addForm.binType"
            style="width: 180px"
            autocomplete="off"
          />
        </el-form-item>
        <el-form-item label-width="110px" prop="binType" label="中央仓管理" size="mini">
          <el-select v-model="addForm.centreFlag" placeholder="请选择" @change="exchangeCenterFlagAddForm">
            <el-option
              v-for="item in centreFlagOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"/>
          </el-select>
        </el-form-item>
        <el-form-item v-if="addForm.centreFlag==='1' " label-width="110px" prop="client" label="被集约方" size="mini">
          <el-select v-model="addForm.client" placeholder="请选择" multiple collapse-tags style="width: 180px">
            <el-option
              v-for="item in warehouses"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"/>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogaddVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="newdata()">新增</el-button>
        <el-button
          size="mini"
          type="primary"
          @click="saveData();">保存</el-button>
      </div>
    </el-dialog>
    <el-dialog :visible.sync="dialogpropertyVisible" title="库存属性选择" width="800px">
      <el-form ref="propertyform" :inline="true" :model="propertyform" size="mini">
        <el-form-item label="库存类型:" size="mini">
          <el-select
            v-model="propertyform.inventoryTypeCode"
            placeholder="请选择"
            style="width: 125px"
            size="mini"
          >
            <el-option
              v-for="item in inventoryTypeOptions"
              :key="item.code"
              :value="item.code"
              :label="item.codeName"
            />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="客户：" size="mini">
          <el-input v-model="propertyform.customerNo" style="width: 90px" clearable />
        </el-form-item> -->
        <el-form-item prop="customerNo" label="客户">
          <el-autocomplete
            v-model="form.customerNo"
            :fetch-suggestions="querySearchAsync"
            :debounce="0"
            :popper-append-to-body="false"
            popper-class="el-autocomplete-suggestion"
            highlight-first-item
            clearable
            type="text"
            size="mini"
            style="width: 90px"
            @select="Changeselect">
            <template slot-scope="{ item }">
              <div class="name">{{ item.customerNo }}</div>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="PPL代码：" size="mini">
          <el-input v-model="propertyform.ppl" style="width: 90px" clearable />
        </el-form-item>
        <el-form-item label="项目号：" size="mini">
          <el-input v-model="propertyform.projectCode" style="width: 90px" clearable />
        </el-form-item>
        <el-form-item label="客户群代码：" size="mini">
          <el-input v-model="propertyform.groupCustomerNo" style="width: 90px" clearable />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-search" @click="searchproperty">查询</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="propertydata" :row-style="{height: '0'}" size="mini" style="width: 100%; font-size: 10px" border stripe>
        <el-table-column property="inventoryPropertyId" align="center" label="库存属性ID" />
        <el-table-column :formatter="inventoryTypeformatter" property="inventoryTypeCode" align="center" label="库存类型" />
        <el-table-column property="customerNo" align="center" label="客户" />
        <el-table-column property="ppl" align="center" label="PPL代码" />
        <el-table-column property="projectCode" align="center" label="项目号" />
        <el-table-column property="groupCustomerNo" align="center" label="客户群代码" />
        <!--操作栏 -->
        <el-table-column label="操作" width="90px">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="selectproperty(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="propertyform.total > 0"
        :total="propertyform.total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="propertyform.pageNum"
        :limit.sync="propertyform.pageSize"
        @pagination="searchproperty"
      />
    </el-dialog>

    <el-row class="row-button" >
      <el-button v-permission="['500127']" type="danger" size="mini" @click="todelete">取消</el-button>
      <el-button v-permission="['500127']" type="primary" size="mini" @click="exportbin()">导入</el-button>
      <el-button v-permission="['500127']" type="primary" size="mini" @click="addBin">添加</el-button>
      <el-button v-permission="['500127']" v-loading="exportLoading" type="primary" icon="el-icon-upload2" size="mini" @click="exportBinData" >导出</el-button>
      <el-button v-permission="['500127']" v-loading="updinfoLoading" type="primary" icon="el-icon-upload2" size="mini" @click="updateProductInfo" >更新产品信息</el-button>
    </el-row>
    <!-- <el-row>
      <el-button-group>
        <el-button type="danger" plain icon="el-icon-delete" @click="todelete">删除</el-button>
        <el-button
          type="success"
          plain
          icon="el-icon-upload"
          @click="uploadFileDialogVisible = true">导入</el-button>
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          @click="dialogaddVisible = true">添加</el-button>
      </el-button-group>
    </el-row> -->
    <div class="tableCloumn" style="margin-top: 5px">
      <el-table
        v-loading="listLoading"
        ref="multipleTable"
        :data="listdata"
        :cell-style="{ padding: '5px' }"
        size="mini"
        stripe
        border
        style="width: 100%; font-size: 10px"
        height="60vh"
        @selection-change="handleSelection"
      >
        <el-table-column type="selection" width="30" />

        <el-table-column :formatter="binTypeformatter" fixed prop="stockType" label="库存类型" />

        <el-table-column prop="warehouseCode" fixed label="仓库代码" />

        <el-table-column prop="customerNo" fixed label="客户" />

        <el-table-column prop="modelNo" fixed label="型号" width="130" show-overflow-tooltip/>

        <el-table-column prop="propertyID" label="库存属性ID" />

        <el-table-column prop="ppl" label="ppl代码" />

        <el-table-column prop="projectNo" label="项目号" />

        <el-table-column prop="inventoryTypeCode" label="库存类型代码" width="120" />

        <el-table-column prop="groupCustomerNo" label="客户群号" />

        <el-table-column prop="qtyBin" label="BIN数量" >
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.qtyBin'" label-width="0">
              <el-input v-model="scope.row.qtyBin" placeholder="BIN数量" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.qtyBin }}</span>
          </template>
        </el-table-column>

        <el-table-column prop="binCell" label="BIN数" >
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.binCell'" label-width="0">
              <el-input v-model="scope.row.binCell" placeholder="BIN数" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.binCell }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="prodType" label="产品类型" >
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.prodType'" label-width="0">
              <el-input v-model="scope.row.prodType" placeholder="产品类型" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.prodType }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="caseType" label="箱型" >
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.caseType'" label-width="0">
              <el-input v-model="scope.row.caseType" placeholder="箱型" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.caseType }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="inCaseQty" label="箱入数" >
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.inCaseQty'" label-width="0">
              <el-input v-model="scope.row.inCaseQty" placeholder="箱入数" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.inCaseQty }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="positionNo" label="位置代码" >
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.positionNo'" label-width="0">
              <el-input v-model="scope.row.positionNo" placeholder="位置代码" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.positionNo }}</span>
          </template>
        </el-table-column> -->
        <el-table-column prop="meshType" label="网筐类型" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.meshType'" label-width="0">
              <el-input v-model="scope.row.meshType" placeholder="网筐类型" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.meshType }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="safeQty" label="安全在库">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.safeQty'" label-width="0">
              <el-input v-model="scope.row.safeQty" placeholder="安全在库" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.safeQty }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="freq" label="8频率">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.freq'" label-width="0">
              <el-input v-model="scope.row.freq" placeholder="8频率" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.freq }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="newFreq" label="新频率">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.newFreq'" label-width="0">
              <el-input v-model="scope.row.newFreq" placeholder="新频率" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.newFreq }}</span>
          </template>
        </el-table-column> -->
        <el-table-column prop="meany" label="8平均">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.mean'" label-width="0">
              <el-input v-model="scope.row.mean" placeholder="8平均" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.mean }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="newMean" label="新用量">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.newMean'" label-width="0">
              <el-input v-model="scope.row.newMean" placeholder="新用量" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.newMean }}</span>
          </template>
        </el-table-column> -->
        <el-table-column prop="setLevel" label="等级">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.setLevel'" label-width="0">
              <el-input v-model="scope.row.setLevel" placeholder="等级" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.setLevel }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column prop="newLevel" label="新等级">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.newLevel'" label-width="0">
              <el-input v-model="scope.row.newLevel" placeholder="新等级" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.newLevel }}</span>
          </template>
        </el-table-column> -->
        <el-table-column prop="poType" label="采购分类" width="110" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.poType'" label-width="0">
              <!-- <el-input v-model="scope.row.poType" placeholder="采购分类" size="mini"/> -->
              <el-select
                v-model="scope.row.poType"
                placeholder="请选择"
                style="width: 100px"
                size="mini"
              >
                <el-option
                  v-for="item in poTypeOptions"
                  :key="item.code"
                  :value="item.code"
                  :label="item.codeName"
                />
              </el-select>
            </span>
            <span v-show="!scope.row.isEditable">{{ poTypeformatter(scope.row) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="orderType" label="订单类别">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.orderType'" label-width="0">
              <el-input v-model="scope.row.orderType" placeholder="订单类别" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.orderType }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="prodSeri" label="公/英制区分">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.prodSeri'" label-width="0">
              <el-input v-model="scope.row.prodSeri" placeholder="公/英制区分" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.prodSeri }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="stateRange" label="辖区">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.stateRange'" label-width="0">
              <el-input v-model="scope.row.stateRange" placeholder="辖区" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.stateRange }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="minPackageQty" label="最小包装数量" width="120">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.minPackageQty'" label-width="0">
              <el-input v-model="scope.row.minPackageQty" placeholder="最小包装数量" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.minPackageQty }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="setFreq" label="设定平均">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.setFreq'" label-width="0">
              <el-input v-model="scope.row.setFreq" placeholder="设定平均" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.setFreq }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="centreFlag" label="中央仓标识">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" label-width="0">
              <el-input v-model="scope.row.centreFlag" placeholder="中央仓标识" size="mini" @change="exchangeCentreFlagEditForm(scope.row)"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.centreFlag }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="client" label="被集约方" width="200">
          <!-- scope.row.isEditable==true时，改为多选框 -->
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" label-width="0">
              <el-select v-model="scope.row.client" :disabled="scope.row.centreFlag != '1'" placeholder="请选择被集约方" multiple collapse-tags size="mini" >
                <el-option
                  v-for="item in warehouses"
                  :key="item.warehouseCode"
                  :label="item.warehouseName"
                  :value="item.warehouseCode"/>
              </el-select>
            </span>
            <span v-show="!scope.row.isEditable">{{ clientformatter(scope.row.client) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="directPurchase" label="直采">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.directPurchase'" label-width="0">
              <el-input v-model="scope.row.directPurchase" placeholder="直采" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.directPurchase }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="directDelivery" label="直送">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.directDelivery'" label-width="0">
              <el-input v-model="scope.row.directDelivery" placeholder="直送" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.directDelivery }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="autoRepl" label="自动周转补货">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.autoRepl'" label-width="0">
              <el-input v-model="scope.row.autoRepl" placeholder="自动周转补货" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.autoRepl }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="binType" label="运营区分" show-overflow-tooltip>
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.binType'" label-width="0" >
              <el-input v-model="scope.row.binType" placeholder="运营区分" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.binType }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="setSupplierCode" label="#设置供应商" width="90px">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.setSupplierCode'" label-width="0">
              <el-input v-model="scope.row.setSupplierCode" placeholder="#设置供应商" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.setSupplierCode }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="supplierCode" label="#供应商">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.supplierCode'" label-width="0">
              <el-input v-model="scope.row.supplierCode" placeholder="#供应商" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.supplierCode }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="origin" label="#原产地">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.origin'" label-width="0">
              <el-input v-model="scope.row.origin" placeholder="#原产地" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.origin }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="eprice" label="#E价"/>
        <el-table-column prop="ecode" label="#Ecode"/>
        <el-table-column prop="modelSeries" label="#产品系列"/>
        <el-table-column prop="adjustable" label="暂停补库">
          <template slot-scope="scope">
            <span v-show="scope.row.isEditable" :prop="'listdata.' + scope.$index + '.adjustable'" label-width="0">
              <el-input v-model="scope.row.adjustable" placeholder="暂停补库" size="mini"/>
            </span>
            <span v-show="!scope.row.isEditable">{{ scope.row.adjustable }}</span>
          </template>
        </el-table-column>
        <el-table-column :formatter="delFlagformatter" prop="delFlag" label="已删除"/>
        <el-table-column prop="updateTime" label="更新时间" show-overflow-tooltip/>
        <el-table-column prop="loginDate" label="登录时间" show-overflow-tooltip/>
        <el-table-column prop="createTime" label="首次登录时间" show-overflow-tooltip/>
        <!--操作栏 -->
        <el-table-column label="操作" fixed="right" width="150px">
          <template slot-scope="scope">
            <el-button v-permission="['500127']" v-show="scope.row.isEditable" type="success" size="mini" @click="updateBindata(scope.row)">修改</el-button>
            <el-button v-permission="['500127']" v-show="scope.row.isEditable" type="primary" size="mini" @click="showDisEditable(scope.row)">关闭</el-button>
            <el-button v-permission="['500127']" v-show="!scope.row.isEditable" type="primary" size="mini" @click="showEditable(scope.row)">编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="form.total > 0"
        :total="form.total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="form.pageNum"
        :limit.sync="form.pageSize"
        style="margin-top: 0px"
        @pagination="searchData"
      />
    </div>
  </div>

</template>

<script>
import Pagination from '@/components/Pagination'
import { listBindata, uploadFile, saveBindata, deleteBindata, listOpsInventoryProperty, exportBinData, updateProductInfo, checkImpBindataStatus, getOpsInventoryProperty, dowmBinDataImport } from '@/api/stock/bindata'
import { codeFormatter } from '@/api/common/comm'
import { getCustomerByNo } from '@/api/common/customer'
import { getDictDataByPid, listWarehouseNoWT } from '@/api/common/dict'
import { findSupplierByIdOrName } from '@/api/common/supplier'
// import { downloadTemplate } from '@/utils/downLoadAndUpLoad'
export default {
  name: 'BinDataManage',
  components: { Pagination },
  data() {
    return {
      warehouses: [],
      uploadFileDialogVisible: false,
      listLoading: true,
      loading: true,
      uploadStatus: false,
      uploadRankStatus: false,
      dialogaddVisible: false,
      dialogpropertyVisible: false,
      dialogFormVisible: false,
      dialogWarehouseVisible: false,
      impMessage: '',
      file: null,
      actionUrl: '',
      supplierForm: {
        companyId: '',
        name: ''
      },
      supplieData: [],
      listdata: [],
      form: {
        stockType: '1',
        warehouseCode: '',
        modelNo: '',
        setSupplierCode: '',
        supplierCode: '',
        orderType: '',
        customerNo: '',
        propertyID: '',
        isdelFlag: false,
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      addForm: {
        stockType: '1',
        warehouseCode: '',
        customerNo: '',
        modelNo: '',
        propertyID: 1,
        ppl: '',
        projectNo: '',
        inventoryTypeCode: 'TY',
        groupCustomerNo: '',
        qtyBin: 0,
        binCell: 0,
        binType: '',
        caseType: '',
        prodType: '',
        positionNo: '',
        meshType: '',
        inCaseQty: 0,
        adjustable: '',
        safeQty: 0,
        freq: 0,
        mean: 0,
        newFreq: 0,
        newMean: 0,
        setLevel: '',
        newLevel: '',
        poType: '',
        setSupplierCode: '',
        supplierCode: '',
        orderType: '',
        prodSeri: '',
        stateRange: '',
        minPackageQty: 0,
        setFreqe: 0,
        directPurchase: 0,
        directDelivery: 0,
        autoRepl: 0,
        eprice: 0,
        ecode: '',
        modelSeries: '',
        origin: '',
        centreFlag: '',
        client: []
      },
      propertyform: {
        inventoryTypeCode: '',
        customerNo: '',
        ppl: '',
        projectCode: '',
        groupCustomerNo: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      classCode: '2010',
      stockTypeOptions: [],
      inventoryTypeOptions: [],
      propertydata: [],
      multipleSelection: [],
      poTypeOptions: [],
      exportLoading: false,
      updinfoLoading: false,
      dowmBinDataImportLoad: false,
      centreFlagOptions: [
        {
          value: '0',
          label: '非中央仓管理'
        }, {
          value: '1',
          label: '中央仓管理'
        }
      ],
      resetEditTableData: []
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (newVal && newVal.name === 'BinDataManage') {
          // 仅当通过其他页面带参跳转过来时才覆盖查询条件并重新查询
          // 否则（如切换Tab后再切回本页面）保留原有查询条件与结果
          const query = this.$route.query || {}
          if (query.modelNo || query.warehouseCode || query.calcType) {
            this.form.modelNo = query.modelNo
            this.form.warehouseCode = query.warehouseCode
            this.form.calcType = query.calcType
            this.searchData()
          }
        }
      }
    }
  },
  created() {
    this.iniData()
    this.searchData()
  },
  methods: {

    exchangeCentreFlagEditForm(row) {
      if (String(row.centreFlag) !== '1') {
        row.client = []
      }
    },
    addBin() {
      this.dialogaddVisible = true
      this.addForm.centreFlag = ''
      this.exchangeCenterFlagAddForm()
    },
    exchangeCenterFlagAddForm() {
      this.addForm.client = String(this.addForm.centreFlag) !== '1' ? this.addForm.client : []
    },
    dowmBinDataImport() {
      this.dowmBinDataImportLoad = true
      this.$message.success('已开始下载，请耐心等待...')
      dowmBinDataImport().then(res => {
        this.dowmBinDataImportLoad = false
        const fileName = '导入模板.xlsx'
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
        this.dowmBinDataImportLoad = false
        this.$message.warning('导出失败' + error)
      })
    },
    exportbin() {
      this.uploadFileDialogVisible = true
      this.checkImpBindataStatus()
    },
    checkImpBindataStatus() {
      checkImpBindataStatus('binimp:').then(result => {
        this.impMessage = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    iniData() {
      getDictDataByPid(this.classCode).then(result => {
        this.stockTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2001').then(result => {
        this.inventoryTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2024').then(result => {
        this.poTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      listWarehouseNoWT().then(result => {
        this.warehouses = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    newdata() {
      this.addForm.stockType = '1'
      this.addForm.warehouseCode = ''
      this.addForm.customerNo = ''
      this.addForm.modelNo = ''
      this.addForm.propertyID = 0
      this.addForm.pl = ''
      this.addForm.projectNo = ''
      this.addForm.inventoryTypeCode = 'TY'
      this.addForm.groupCustomerNo = ''
      this.addForm.qtyBin = 0
      this.addForm.binCell = 0
      this.addForm.binType = ''
      this.addForm.caseType = ''
      this.addForm.prodType = ''
      this.addForm.positionNo = ''
      this.addForm.meshType = ''
      this.addForm.inCaseQty = 0
      this.addForm.adjustable = ''
      this.addForm.safeQty = 0
      this.addForm.freq = 0
      this.addForm.mean = 0
      this.addForm.newFreq = 0
      this.addForm.newMean = 0
      this.addForm.setLevel = ''
      this.addForm.newLevel = ''
      this.addForm.poType = ''
      this.addForm.setSupplierCode = ''
      this.addForm.supplierCode = ''
      this.addForm.orderType = ''
      this.addForm.prodSeri = ''
      this.addForm.stateRange = ''
      this.addForm.minPackageQty = 0
      this.addForm.setFreqe = 0
      this.addForm.directPurchase = 0
      this.addForm.directDelivery = 0
      this.addForm.autoRepl = 0
      this.addForm.eprice = 0
      this.addForm.ecode = ''
      this.addForm.modelSeries = ''
      this.addForm.origin = ''
      this.addForm.centreFlag = ''
      this.addForm.client = []
    },
    selectproperty(row) {
      this.form.propertyID = row.inventoryPropertyId
      this.dialogpropertyVisible = false
    },
    searchproperty() {
      console.log(this.propertyform)
      if (this.propertyform.inventoryTypeCode === '' && this.propertyform.customerNo === '' && this.propertyform.ppl === '' &&
      this.propertyform.projectCode === '' && this.propertyform.groupCustomerNo === '') {
        this.$message.error('至少输入一个查询条件')
        return
      }
      if (this.propertyform.inventoryTypeCode === 'GK-TY' && this.propertyform.customerNo === '') {
        this.$message.error('选择顾客在库通用必须要输入客户！')
        return
      }
      listOpsInventoryProperty(this.propertyform).then(result => {
        console.log(result)
        this.propertydata = result.content.list
        this.propertyform.total = result.content.total
      }).catch(error => {
        console.log(error)
      })
    },
    selectWarehouses() {
      this.dialogWarehouseVisible = true
      this.listWarehousesinfo()
    },
    listWarehousesinfo() {
      listWarehouseNoWT().then(result => {
        this.warehouses = result.content
        this.warehouses.unshift({
          warehouseCode: 'ALL',
          warehouseName: '自动化BIN'
        })
      }).catch(error => {
        console.log(error)
      })
    },
    editWarehouses(row) {
      this.form.warehouseCode = row.warehouseCode
      this.searchData()
      this.dialogWarehouseVisible = false
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
      this.form.supplierCode = row.companyId
      this.dialogFormVisible = false
    },
    exportBinData() {
      this.exportLoading = true
      const expdate = this.dayjs(new Date()).format('YYYY-MM')
      exportBinData(this.form).then(res => {
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
    searchData() {
      this.listLoading = true
      listBindata(this.form).then(result => {
        console.log(result)
        this.listdata = result.content.list
        for (const i in this.listdata) {
          this.$set(this.listdata[i], 'isEditable', false)
        }
        this.resetEditTableData = JSON.parse(JSON.stringify(this.listdata))
        this.form.total = result.content.total
        // this.total = data.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    binTypeformatter(row) {
      return codeFormatter(this.stockTypeOptions, row.stockType)
    },
    inventoryTypeformatter(row) {
      return codeFormatter(this.inventoryTypeOptions, row.inventoryTypeCode)
    },
    clientformatter(value) {
      return value.join(',')
    },
    poTypeformatter(row) {
      return codeFormatter(this.poTypeOptions, row.poType)
    },
    delFlagformatter(row) {
      if (row.delFlag === 0) {
        return '否'
      } else {
        return '是'
      }
    },
    showEditable(row) {
      row.isEditable = true
      this.exchangeCentreFlagEditForm(row)
    },
    showDisEditable(row) {
      row.isEditable = false
      // 根据row的id字段找到listdata中对应的数据，重置该行数据
      const resetRow = this.resetEditTableData.find(item => item.id === row.id)
      if (resetRow) {
        Object.assign(row, JSON.parse(JSON.stringify(resetRow)))
      }
    },
    showproperty() {
      this.dialogpropertyVisible = true
    },
    handleSelection(val) {
      this.multipleSelection = val
    },
    todelete() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中需要修改的单'
        })
        return
      }
      const ids = []
      this.multipleSelection.forEach((item, index) => {
        ids.push(item.id)
      })
      const formData = { ids: ids }
      deleteBindata(formData)
        .then(res => {
          console.log(res)
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
    updateBindata(row) {
      saveBindata(row)
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
    updateProductInfo() {
      this.updinfoLoading = true
      updateProductInfo().then(result => {
        if (result.success) {
          this.$message.success(result.message)
          this.updinfoLoading = false
          this.searchData()
        } else {
          this.updinfoLoading = false
          this.$message.error(result.message)
        }
      }).catch(error => {
        this.$message.error(error + '请稍后再常试')
        this.updinfoLoading = false
      })
    },
    saveData() {
      console.log('this.addForm', this.addForm)
      if (this.addForm.stockType === '' || this.addForm.warehouseCode === '' || this.addForm.modelNo === '' || this.addForm.propertyID === '') {
        this.$message.error('带*号的不能为空！')
        return
      }
      saveBindata(this.addForm)
        .then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.searchData()
            this.dialogaddVisible = false
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    querySearchAsync(customerNo, cb) {
      // const cus = { customerNo: this.form.customerNo }
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray

        cb(results)
      })
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.customerNo.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    Changeselect(item) {
      this.propertyform.customerNo = item.customerNo
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
    getOpsInventoryProperty() {
      if (this.addForm.propertyID === '') {
        return
      }
      console.log(this.addForm.propertyID)
      const id = this.addForm.propertyID
      getOpsInventoryProperty(id).then(result => {
        console.log(result)
        if (result.success && result.content !== null) {
          this.addForm.ppl = result.content.ppl
          this.addForm.projectNo = result.content.projectCode
          this.addForm.inventoryTypeCode = result.content.inventoryTypeCode
          this.addForm.groupCustomerNo = result.content.groupCustomerNo
        }
      }).catch(error => {
        console.log(error)
      })
    },
    importData() {
      if (this.uploadStatus === true) {
        this.$message.error('还在上传中，请不要重复点击！')
        return
      }
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
    },
    _importEmployeeAssistant(formData) {
      uploadFile(formData)
        .then(res => {
          console.log(res)
          if (res.success) {
            this.file = null
            this.$message.success(res.content)
            this.uploadStatus = false
            this.uploadFileDialogVisible = false
            this.searchData()
          } else {
            this.uploadStatus = false
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.uploadStatus = false
          this.$message.error(error + '请稍后再常试')
        })
    },
    downloadTemplate(url) {
      const index = url.lastIndexOf('/') + 1
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.download = url.substring(index)
      link.click()
      this.$message({
        message: `正在下载,请稍后在本地查看`,
        type: 'success'
      })
    },
    createtimeFormatter: function(row, column) {
      if (row.rorddate === null) {
        return ''
      } else {
        return this.dayjs(row.rorddate).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    updateTimeFormatter: function(row, column) {
      if (row.rorddate === null) {
        return ''
      } else {
        return this.dayjs(row.rorddate).format('YYYY-MM-DD HH:mm:ss')
      }
    }
  }
}
</script>
<style scoped>
.line {
  text-align: center;
  margin-left: 5%;
}
.tableCloumn {
  position: absolute;
  margin-top: 10px;
  padding-right: 10px;
  width: 99%;
}
.row-button {
  margin-bottom: 10px;
}
.el-form-item {
    margin-bottom: 5px;
}
</style>

