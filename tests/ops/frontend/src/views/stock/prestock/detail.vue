<template>
  <div>
    <el-card v-if="disabled">
      <el-button type="primary" size="mini" @click="goBack" >返回</el-button>
      <el-button type="primary" size="mini" icon="el-icon-plus" @click="newApply">新建</el-button>
      <el-button v-if="applyForm.status === '2'" :loading="approveLoading" type="primary" size="mini" icon="el-icon-check" @click="onApprove">审核确认</el-button>
      <el-button v-if="applyForm.applyType === '2' && applyForm.status === '3'" :loading="saveApplyLoading" type="primary" size="mini" icon="el-icon-folder-add" @click="saveShikomi">确认SHIKOMI</el-button>
    </el-card>
    <el-card v-else>
      <el-button type="primary" size="mini" @click="goBack" >返回</el-button>
      <el-button type="primary" size="mini" icon="el-icon-plus" @click="newApply">新建</el-button>
      <el-button :loading="saveApplyLoading" type="primary" size="mini" icon="el-icon-folder-add" @click="onSave('applyForm')">保存</el-button>
      <el-button v-if="applyForm.applyNo" :loading="submitLoading" type="primary" size="mini" icon="el-icon-check" @click="onSubmit('applyForm')">提交</el-button>
    </el-card>

    <el-form ref="applyForm" :model="applyForm" :rules="applyRules" label-width="90px" size="mini" label-position="right" >
      <el-card>
        <!-- <span class="span-title">基本信息</span>-->
        <el-row :gutter="20" >
          <el-col :span="4">
            <el-form-item label=" " prop="applyNo">
              <span slot="label">
                <span class="span-box">
                  <el-tooltip class="item" effect="dark" content="点击【保存】生成申请号" placement="top-start">
                    <i class="el-icon-warning-outline" />
                  </el-tooltip>
                  <span>申请号:</span>
                </span>
              </span>
              <span>{{ applyForm.applyNo }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="申请类型:" prop="applyType" >
              <span v-if="disabled">{{ getApplyType(applyForm.applyType) }}</span>
              <el-select v-else v-model="applyForm.applyType" placeholder="请选择申请类型" style="width: 100%">
                <el-option v-for="item in applyTypeList" :key="item.code" :label="item.codeName" :value="item.code" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="2">
            <el-form-item label="" prop="replType" label-width="0">
              <span v-if="disabled">{{ getReplType(applyForm.replType) }}</span>
              <el-select v-else v-model="applyForm.replType" placeholder="请选择申请类型" style="width: 100%">
                <el-option v-for="item in replTypeList" :key="item.code" :label="item.codeName" :value="item.code" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item label="申请状态:" prop="status" >
              <span>{{ getApplyStatus(applyForm.status) }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="申请部门:" prop="deptNo" >
              <el-cascader v-if="!disabled && applyForm.stockType && applyForm.stockType.startsWith('ZL')" :options="deptTreeList" :props="{ checkStrictly: true }" placeholder="请选择部门" clearable @change="changeDept"/>
              <span v-else>{{ applyForm.deptName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="申请担当:" prop="applyPsnNo" >
              <el-select v-if="!disabled && applyForm.stockType && applyForm.stockType.startsWith('ZL')" v-model="applyForm.applyPsnNo" placeholder="请选择申请类型" style="width: 100%" clearable @change="changeApplyPsn">
                <el-option v-for="item in deptEmpList" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
              <span v-else>{{ '【' + applyForm.applyPsnNo + '】' + applyForm.applyPsn }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="applyForm.applyType === '2'" :gutter="20">
          <el-col :span="4" >
            <el-form-item label="担当邮箱:" prop="applyPsnMail" >
              <el-input v-if="applyForm.status === '3'" v-model="applyForm.applyPsnMail" placeholder="担当邮箱" maxlength="50" clearable/>
              <span v-else>{{ applyForm.applyPsnMail }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="负责人:" prop="approverNo" >
              <span>{{ '【' + applyForm.approverNo + '】' + applyForm.approverName }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="负责人邮箱:" prop="approverMail" >
              <el-input v-if="applyForm.status === '3'" v-model="applyForm.approverMail" placeholder="负责人邮箱" maxlength="50" clearable/>
              <span v-else>{{ applyForm.approverMail }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <span class="span-title">用户信息</span>-->
        <el-row :gutter="20" >
          <el-col :span="6">
            <el-form-item label="客户:" prop="customerNo" >
              <span v-if="disabled">{{ applyForm.customerName }}</span>
              <!-- <el-input v-else v-model="applyForm.customerNo" placeholder="请输入客户代码" clearable/> -->
              <el-autocomplete
                v-else
                v-model="applyForm.customerName"
                :fetch-suggestions="querySearchAsync"
                :debounce="0"
                :popper-append-to-body="false"
                popper-class="el-autocomplete-suggestion"
                highlight-first-item
                type="text"
                size="mini"
                placeholder="请输入客户代码"
                class="select"
                clearable
                @select="Changeselect">
                <template slot-scope="{ item }">
                  <div class="name">{{ '【' + item.customerNo + '】' + item.name }}</div>
                </template>
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <el-col v-if="applyForm.applyType === '2'" :span="4" >
            <el-form-item label="" prop="enname" label-width="0">
              <el-input v-if="applyForm.status === '3'" v-model="applyForm.enname" placeholder="客户英文名称" maxlength="30" show-word-limit clearable/>
              <span v-else>{{ applyForm.enname }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="用户:" prop="userNo" >
              <span v-if="disabled">{{ applyForm.userName }}</span>
              <!-- <el-input v-else v-model="applyForm.userNo" placeholder="请输入用户代码" clearable/> -->
              <el-autocomplete
                v-else
                v-model="applyForm.userName"
                :fetch-suggestions="querySearchAsync"
                :debounce="0"
                :popper-append-to-body="false"
                popper-class="el-autocomplete-suggestion"
                highlight-first-item
                type="text"
                size="mini"
                placeholder="请输入用户代码"
                class="select"
                clearable
                @select="ChangeUserselect">
                <template slot-scope="{ item }">
                  <div class="name">{{ '【' + item.customerNo + '】' + item.name }}</div>
                </template>
              </el-autocomplete>
            </el-form-item>
          </el-col>
          <!-- 当申请类型为服务备库补库时，主项需增加PO号填写展示 -->
          <el-col v-if="applyForm.applyType==3" :span="4">
            <el-form-item label="PO号:" prop="corderNo" >
              <span v-if="disabled">{{ applyForm.corderNo }}</span>
              <el-input v-else v-model="applyForm.corderNo" placeholder="请输入PO号" clearable/>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <span class="span-title">备库信息</span>-->
        <el-row :gutter="20" >
          <el-col :span="4">
            <el-form-item label="库存类型:" prop="stockType" >
              <span v-if="disabled">{{ getInventoryType(applyForm.stockType) }}</span>
              <el-select v-else v-model="applyForm.stockType" placeholder="请选择库存类型" style="width: 100%" clearable>
                <el-option v-for="item in inventoryTypeList" :key="item.code" :label="item.codeName" :value="item.code" clearable/>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="仓库:" prop="warehouseCode" >
              <span v-if="disabled">{{ getWarehouseName(applyForm.warehouseCode) }}</span>
              <el-select v-else v-model="applyForm.warehouseCode" placeholder="请选择仓库" style="width: 71%" clearable @change="getWarehouse(applyForm.warehouseCode)">
                <el-option v-for="item in warehouseList" :key="item.code" :label="item.codeName" :value="item.code" />
              </el-select>
              <el-button v-if="!disabled" type="primary" size="mini" @click="selectWarehouse">选择</el-button>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="PPL号:" prop="pplNo" >
              <span v-if="disabled">{{ applyForm.pplNo }}</span>
              <el-input v-else v-model="applyForm.pplNo" placeholder="请输入PPL号" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="项目号:" prop="projectNo" >
              <span v-if="disabled">{{ applyForm.projectNo }}</span>
              <el-input v-else v-model="applyForm.projectNo" placeholder="请输入项目编号" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="集团组:" prop="groupCustomerNo" >
              <span v-if="disabled">{{ applyForm.groupCustomerNo }}</span>
              <el-input v-else v-model="applyForm.groupCustomerNo" placeholder="请输入集团组" clearable/>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <span class="span-title">扩展信息</span>-->
        <el-row :gutter="20" >
          <el-col :span="4">
            <el-form-item label="项目名称:" prop="projectName" >
              <span v-if="disabled">{{ applyForm.projectName }}</span>
              <el-input v-else v-model="applyForm.projectName" placeholder="请输入项目名称" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="项目状态:" prop="projectStatus" >
              <span v-if="disabled">{{ applyForm.projectStatus }}</span>
              <el-input v-else v-model="applyForm.projectStatus" placeholder="请输入项目状态" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="应用设备:" prop="equipment" >
              <span v-if="disabled">{{ applyForm.equipment }}</span>
              <el-input v-else v-model="applyForm.equipment" maxlength="50" placeholder="请输入应用设备" clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="行业代码:" prop="indCode" >
              <span v-if="disabled">{{ applyForm.indCode }}</span>
              <el-input v-else v-model="applyForm.indCode" placeholder="请输入行业代码" clearable/>
            </el-form-item>
          </el-col>
          <el-col v-if="applyForm.applyType === '2'" :span="4">
            <el-form-item label-width="120px" label="Shikomi共享:" prop="shikomiClass" >
              <span v-if="disabled">{{ getShikomiType(applyForm.shikomiClass) }}</span>
              <el-select v-else v-model="applyForm.shikomiClass" placeholder="请选择" style="width: 100%">
                <el-option v-for="item in shikomiTypeList" :key="item.code" :label="item.codeName" :value="item.code" clearable/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <span class="span-title">用途和原因</span>-->
        <el-row :gutter="20" >
          <el-col v-if="applyForm.applyType !== '2'" :span="8">
            <el-form-item label="用途:" prop="purpose" >
              <span v-if="disabled">{{ applyForm.purpose }}</span>
              <el-input v-else v-model="applyForm.purpose" maxlength="250" type="textarea" placeholder="请输入用途" show-word-limit clearable/>
            </el-form-item>
          </el-col>
          <el-col :span="8" >
            <el-form-item label="申请原因:" prop="reason" >
              <el-input v-if="!disabled || (applyForm.applyType === '2' && applyForm.status === '3')" v-model="applyForm.reason" maxlength="400" type="textarea" placeholder="请输入申请原因" show-word-limit clearable/>
              <span v-else >{{ applyForm.reason }}</span>
            </el-form-item>
          </el-col>
          <el-col v-if="applyForm.applyType === '2'" :span="8" >
            <el-form-item label="申请原因:(英文)" prop="reasonEn" >
              <el-input v-if="!disabled || (applyForm.applyType === '2' && applyForm.status === '3')" v-model="applyForm.reasonEn" maxlength="400" type="textarea" placeholder="请输入申请原因（英文）" show-word-limit clearable/>
              <span v-else >{{ applyForm.reasonEn }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="备注:" prop="remark" >
              <span v-if="disabled">{{ applyForm.remark }}</span>
              <el-input v-else v-model="applyForm.remark" maxlength="400" type="textarea" placeholder="请输入备注" show-word-limit clearable/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-card>
      <!-- 申请项 -->
      <el-card>
        <el-row class="row-button">
          <span class="span-title">申请项</span>
          <el-button v-if="disabled && applyForm.applyType !== '2' && applyForm.status === '3'" :loading="showProcessResultDialogLoading" type="primary" size="mini" @click="onGetResultDetail" >手动处理</el-button>
          <el-tooltip v-if="disabled && applyForm.applyType !== '2' && applyForm.status === '3'" class="item" effect="dark" content="按所选型号处理" placement="top-start">
            <el-button :loading="autoProcessLoading" type="primary" size="mini" @click="onAutoProcess" >自动处理</el-button>
          </el-tooltip>
          <el-button v-if="!disabled" type="primary" size="mini" icon="el-icon-plus" @click="showEditDetailDialog('add')" >添加</el-button>
          <el-button v-if="!disabled" type="primary" size="mini" icon="el-icon-plus" @click="dialogAddBatchDetailVisible = true" >批量添加</el-button>
          <el-button v-if="!disabled || (applyForm.applyType === '2' && applyForm.status === '3')" type="primary" size="mini" icon="el-icon-edit" @click="showEditDetailDialog('edit')" >修改</el-button>
          <el-button v-if="!disabled" type="primary" size="mini" icon="el-icon-edit" @click="showEditBatchDetailDialog('edit')" >批量修改</el-button>
          <el-button v-if="!disabled" type="danger" size="mini" icon="el-icon-delete" @click="removeBatchDetail" >批量删除</el-button>
          <el-button v-if="!disabled" :loading="autoPreStockDetailLoading" type="primary" size="mini" icon="el-icon-refresh" @click="autoPreStockDetail" >自动补库清单</el-button>
          <el-button v-if="applyForm.applyType === '2' && applyForm.status === '5'" :loading="resetShikomiLoading" type="warning" size="mini" icon="el-icon-refresh-left" @click="showEditDetailDialog('resetSKM')">修改</el-button>
          <el-button v-if="applyForm.applyType === '2' && (applyForm.status === '5' || applyForm.status === '6')" :loading="resetShikomiLoading" type="warning" size="mini" icon="el-icon-refresh-left" @click="resetProcessStatus">重置SHIKOMI</el-button>
          <el-checkbox v-model="applyForm.transFlag" :disabled="applyForm.applyType == '3'" border @change="updatePreStockDetailTranFlag" >是否必须调至备库仓</el-checkbox>
          <!-- <el-button :loading="refreshInventoryInfoLoading" type="primary" size="mini" icon="el-icon-refresh" @click="refreshApplyModelInventoryInfo">刷新库存信息</el-button>-->
        </el-row>
        <el-table
          ref="applyItemTable"
          :row-key="getRowKeys"
          :data="applyForm.details"
          :cell-style="tableCellStyle"
          :header-cell-style="tableHeaderStyle"
          height="300"
          border
          stripe
          size="mini"
          style="width: 100%;"
          @select-all="selectAll"
          @select="selectionChange">
          <el-table-column key="selected" type="selection"/>
          <el-table-column key="itemNo" fixed prop="itemNo" label="申请项号" width="80" sortable/>
          <el-table-column key="applyModelNo" fixed prop="applyModelNo" label="申请型号" width="180" sortable/>
          <el-table-column v-if="disabled" key="modelNo" fixed prop="modelNo" label="处理型号" width="180" sortable/>
          <el-table-column key="checkModel" prop="errorDesc" label="校验结果" width="110">
            <template slot-scope="scope">
              <el-tooltip v-if="scope.row.errorType==1 || scope.row.errorType==2" :content="scope.row.errorDesc" :style="{ 'text-align': 'center' }" class="item" effect="light" placement="right-start">
                <div>
                  <el-icon v-if="scope.row.errorType==1" class="el-icon-error" style="color: red; font-size: 15px;"/>
                  <el-icon v-if="scope.row.errorType==2" class="el-icon-info" style="color: orange; font-size: 15px;"/>
                </div>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column
            key="status"
            :filters="detailFilterStatus"
            :filter-method="filterDetailByStatus"
            :filter-multiple="false"
            prop="status"
            label="状态"
            width="100">
            <template slot-scope="scope">
              <span >{{ getDetailStatus(scope.row.status) }}</span>
            </template>
          </el-table-column>
          <el-table-column key="supplierCode" prop="supplierCode" label="供应商"/>
          <el-table-column v-if="applyForm.applyType === '2'" key="modelName" prop="modelName" label="产品名称" width="200"/>
          <el-table-column key="quantity" prop="quantity" label="数量" width="100"/>
          <el-table-column v-if="applyForm.replType === '3'" key="qtybin" prop="qtybin" label="发注点" width="100"/>
          <el-table-column v-if="applyForm.applyType === '2'" key="dlvDateJp" prop="dlvDateJp" label="Shikomi要求日本货期" width="110"/>
          <el-table-column
            v-if="applyForm.applyType !== '2'"
            key="dlvDate"
            :filters="filtersForDlvDate"
            :filter-method="filterDetailByDlvDate"
            prop="dlvDate"
            label="客户要求货期"
            width="160"
            sortable
            column-key="dlvDate"
          />
          <el-table-column key="confirmTime" prop="confirmTime" label="审批时间" width="120"/>
          <el-table-column v-if="applyForm.applyType !== '2'" key="expType" prop="expType" label="希望备库方式" width="110">
            <template slot-scope="scope">
              <span>{{ getExpType(scope.row.expType) }}</span>
            </template>
          </el-table-column>
          <el-table-column v-if="applyForm.applyType !== '2' && disabled" key="stockQty" prop="stockQty" label="调库数" width = "70"/>
          <el-table-column v-if="applyForm.applyType !== '2' && disabled" key="poQty" prop="poQty" label="请购数" width = "70"/>
          <el-table-column key="rohs10" prop="rohs10" label="Rohs10" width="80" >
            <template slot-scope="scope">
              <span>{{ scope.row.rohs10 ? '是' : '否' }}</span>
            </template>
          </el-table-column>
          <el-table-column key="eprice" prop="eprice" label="E价格" width="70"/>
          <el-table-column key="eAmount" prop="eAmount" label="E金额" width="120"/>
          <el-table-column key="newFlag" prop="newFlag" label="类型" width="80">
            <template slot-scope="scope">
              <span>{{ getNewFlag(scope.row.newFlag) }}</span>
            </template>
          </el-table-column>
          <el-table-column v-if="applyForm.applyType === '2'" key="price" prop="price" label="含税单价" width="90">
            <template slot-scope="scope">
              <span>{{ aomuntFilter(scope.row.price) }}</span>
            </template>
          </el-table-column>
          <el-table-column v-if="applyForm.applyType === '2'" key="amount" prop="amount" label="金额" width="90">
            <template slot-scope="scope">
              <span>{{ aomuntFilter(scope.row.quantity * scope.row.price) }}</span>
            </template>
          </el-table-column>
          <el-table-column v-if="applyForm.applyType === '2'" key="sprAnswerNo" prop="sprAnswerNo" label="特价号" width="90"/>
          <!-- add by lyc 2024-05-08 bug 14106 重复显示问题 -->
          <!-- <el-table-column v-if="applyForm.applyType === '2'" key="eprice" prop="eprice" label="E价格" width="90">
            <template slot-scope="scope">
              <span>{{ aomuntFilter(scope.row.eprice) }}</span>
            </template>
          </el-table-column> -->
          <el-table-column v-if="applyForm.applyType === '2'" key="ediscount" prop="ediscount" label="E率" width="90">
            <template slot-scope="scope">
              <span>{{ eDiscountFilter(scope.row.ediscount) }}</span>
            </template>
          </el-table-column>
          <el-table-column key="stockType" prop="stockType" label="库存类型" width="130">
            <template slot-scope="scope">
              <span>{{ getInventoryType(scope.row.stockType) }}</span>
            </template>
          </el-table-column>
          <el-table-column key="cproductNo" prop="cproductNo" label="物料号" width="130">
            <template slot-scope="scope">
              <span>{{ scope.row.cproductNo }}</span>
            </template>
          </el-table-column>
          <el-table-column key="pplNo" prop="pplNo" label="PPL号" width="130"/>
          <el-table-column key="projectNo" prop="projectNo" label="项目号" width="100"/>
          <el-table-column v-if="applyForm.applyType === '2'" key="equipment" prop="equipment" label="用户装置名称" width="110"/>
          <el-table-column v-if="applyForm.applyType === '2'" key="equipmentQty" prop="equipmentQty" label="配置数量" width="90"/>
          <el-table-column v-if="applyForm.applyType === '2'" key="purpose" show-overflow-tooltip prop="purpose" label="用途" width="90"/>
          <el-table-column key="specMark" prop="specMark" label="组装标识" width="100" >
            <template slot-scope="scope">
              <span>{{ getSpecMark(scope.row.specMark) }}</span>
            </template>
          </el-table-column>
          <el-table-column key="transType" prop="transType" label="运输方式" width="100" >
            <template slot-scope="scope">
              <span>{{ getTransType(scope.row.transType) }}</span>
            </template>
          </el-table-column>
          <el-table-column key="processText" show-overflow-tooltip prop="processText" label="处理备注"/>
          <el-table-column key="qtyOnHand" prop="qtyOnHand" label="在库"/>
          <!--<template slot-scope="scope">-->
          <!--<span>{{ getQtyOnHand(scope.row.modelNo) }}</span>-->
          <!--</template>-->
          <!--</el-table-column>-->
          <el-table-column key="ordingQty" prop="ordingQty" label="在途"/>
          <!--<template slot-scope="scope">-->
          <!--<span >{{ getOrdingQty(scope.row.modelNo) }}</span>-->
          <!--</template>-->
          <!--</el-table-column>-->
          <el-table-column
            key="binQty"
            :filters="filtersForBinQty"
            :filter-method="filterDetailByBinQty"
            prop="binQty"
            label="Bin数量"
            width="130"
            sortable
            column-key="binQty"
          />
          <!--<template slot-scope="scope">-->
          <!--<span>{{ getBinQty(scope.row.modelNo) }}</span>-->
          <!--</template>-->
          <!--</el-table-column>-->
          <el-table-column key="monthAvgQty" prop="monthAvgQty" label="月用量" width="100"/>
          <!--<template slot-scope="scope">-->
          <!--<span>{{ getMonthAvgQty(scope.row.modelNo) }}</span>-->
          <!--</template>-->
          <!--</el-table-column>-->
          <el-table-column key="canUseMonths" prop="canUseMonths" label="可用月数"/>
          <!--<template slot-scope="scope">-->
          <!--<span>{{ getCanUseMonths(scope.row.modelNo) }}</span>-->
          <!--</template>-->
          <!--</el-table-column>-->
          <el-table-column key="replyStatus" prop="replyStatus" label="同步状态">
            <template slot-scope="scope">
              <span>{{ getReplyStatus(scope.row.replyStatus) }}</span>
            </template>
          </el-table-column>
          <el-table-column key="replyResult" show-overflow-tooltip prop="replyResult" label="同步结果"/>
          <el-table-column key="remark" show-overflow-tooltip prop="remark" label="备注"/>
          <el-table-column v-if="!disabled" key="action" label="操作" align="center" fixed="right" >
            <template slot-scope="scope">
              <el-button type="danger" size="mini" icon="el-icon-delete" @click="removeDetail(scope.$index, scope.row)" />
            </template>
          </el-table-column>
        </el-table>
        <pagination v-show="total > 0" :total="total" :page-sizes="[20, 50, 100, 500]" :page.sync="pageNum" :limit.sync="pageSize" style="margin-top: 0px" @pagination="listDetail" />
      </el-card>
    </el-form>
    <!-- 处理结果 -->
    <el-card v-show="showProcessResult">
      <span class="span-title">处理结果</span>
      <el-table :data="applyForm.resultDetails" :cell-style="tableCellStyle" :header-cell-style="tableHeaderStyle" height="300" border stripe size="mini" style="width: 100%;">
        <el-table-column prop="itemNo" label="申请项号" width="90"/>
        <el-table-column prop="applyModelNo" label="申请型号" width="180"/>
        <el-table-column prop="modelNo" label="处理型号" width="180"/>
        <el-table-column prop="orderQty" label="数量" width="80"/>
        <el-table-column prop="processType" label="处理类型" width="90">
          <template slot-scope="scope">
            <span>{{ getProcessType(scope.row.processType) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="orderStock" label="供应商" width="90"/>
        <el-table-column prop="optStatus" label="处理状态" width="90">
          <template slot-scope="scope">
            <span>{{ getOptStatus(scope.row.optStatus) }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="orderNo" label="关联单号" width="120"/>
        <el-table-column prop="dlvDateJp" label="交货日期" width="90"/>
        <el-table-column prop="execTime" label="执行时间" width="90"/>
        <el-table-column prop="remark" label="备注" width="300"/>
        <el-table-column
          align="center"
          label="操作"
          width="200">
          <template slot-scope="scope">
            <el-button v-if="scope.row.processType == '6'" type="primary" size="small" @click="handleClick(scope.row)">转订</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!-- 申请项编辑窗口 -->
    <el-dialog :visible.sync="dialogEditDetailVisible" title="编辑申请项" width="400px">
      <el-form ref="detailForm" :model="detailForm" :rules="detailRules" size="mini" label-width="110px" >
        <el-form-item prop="applyModelNo" label="申请型号" >
          <el-input :disabled="disabled" v-model="detailForm.applyModelNo" placeholder="申请型号" />
        </el-form-item>
        <el-form-item v-if="disabled" prop="modelNo" label="处理型号" >
          <el-input :disabled="disabled" v-model="detailForm.modelNo" placeholder="处理型号" />
        </el-form-item>
        <el-form-item v-if="applyForm.applyType === '2'" prop="supplierCode" label="供应商">
          <el-input v-model="detailForm.supplierCode" :disabled="disabled && detailForm.status !== '2'" placeholder="供应商" @change ="changeSupplierCode"/>
        </el-form-item>
        <el-form-item v-if="applyForm.applyType === '2'" prop="modelName" label="产品名称" >
          <el-input v-model="detailForm.modelName" :disabled="disabled && detailForm.status !== '2'" placeholder="英文名称" maxlength="30" show-word-limit />
        </el-form-item>
        <el-form-item v-if="applyForm.replType === '3'" prop="qtybin" label="发注点" >
          <el-input v-model.number="detailForm.qtybin" :disabled="disabled" autocomplete="off" placeholder="发注点" />
        </el-form-item>
        <el-form-item prop="quantity" label="数 量:" >
          <el-input v-model.number="detailForm.quantity" :disabled="disabled && detailForm.status !== 'resetSKM'" autocomplete="off" placeholder="数量" />
        </el-form-item>
        <el-form-item v-if="applyForm.applyType !== '2'" prop="dlvDate" label="客户要求货期" >
          <el-date-picker v-model="detailForm.dlvDate" :disabled="disabled" :picker-options="handlePickerOptions" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%;" />
        </el-form-item>
        <el-form-item v-if="applyForm.applyType === '2'" prop="dlvDateJp" label="Shikomi要求日本货期" >
          <el-date-picker v-model="detailForm.dlvDateJp" :disabled="disabled && detailForm.status !== 'resetSKM'" :picker-options="handlePickerOptions" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%;" />
        </el-form-item>
        <el-form-item v-if="applyForm.applyType !== '2'" prop="expType" label="希望备库方式">
          <el-select v-model="detailForm.expType" :disabled="disabled" placeholder="希望备库方式" style="width: 100%">
            <el-option v-for="item in expTypeList" :key="item.code" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item prop="rohs10" label="Rohs10">
          <el-radio-group :disabled="disabled" v-model="detailForm.rohs10" >
            <el-radio :label="true" border>是</el-radio>
            <el-radio :label="false" border>否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="applyForm.applyType !== '2'" prop="specMark" label="组装标识">
          <el-select v-model="detailForm.specMark" :disabled="disabled" placeholder="组装标识" style="width: 100%">
            <el-option v-for="item in specMarkList" :key="item.code" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item v-if="applyForm.applyType !== '2'" prop="transType" label="运输方式">
          <el-select v-model="detailForm.transType" :disabled="disabled" placeholder="运输方式" style="width: 100%">
            <el-option v-for="item in getTransIds" :key="item.code" :label="item.codeName" :value="item.code"/>
          </el-select>
        </el-form-item>
        <el-form-item v-if="applyForm.applyType == 3" prop="cproductNo" label="物料号">
          <el-input v-model="detailForm.cproductNo" :disabled="disabled" placeholder="物料号" />
        </el-form-item>
        <el-form-item prop="remark" label="备注" >
          <el-input v-model="detailForm.remark" :rows="2" :disabled="disabled" type="textarea" placeholder="备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogEditDetailVisible = false">取消</el-button>
        <el-button v-if="detailForm.status !== 'resetSKM'" type="primary" size="mini" @click="addDetail">确定</el-button>
        <el-button v-if="detailForm.status === 'resetSKM'" :loading="resetShikomiLoading" type="primary" size="mini" icon="el-icon-edit" @click="resetShikomi('1')">修改</el-button>
        <el-button v-if="detailForm.status === 'resetSKM'" :loading="resetShikomiLoading" type="primary" size="mini" icon="el-icon-document-copy" @click="resetShikomi('3')">新增</el-button>
        <el-button v-if="detailForm.status === 'resetSKM'" :loading="resetShikomiLoading" type="danger" size="mini" icon="el-icon-delete" @click="resetShikomi('2')">删除</el-button>
      </div>
    </el-dialog>
    <!-- 批量添加申请项弹窗 -->
    <el-dialog :visible.sync="dialogAddBatchDetailVisible" title="批量添加" width="300">
      <el-form ref="batchDetailForm" :model="batchDetailForm" size="mini" label-width="75px" >
        <el-form-item icon="el-icon-warning-outline" prop="batchDetailInfo">
          <span slot="label">
            <span class="span-box">
              <el-tooltip class="item" effect="dark" content="注意：数据项【型号、数量】为必需项！" placement="top-start">
                <i class="el-icon-warning-outline" />
              </el-tooltip>
              <span>数据项</span>
            </span>
          </span>
          <el-input v-model="batchDetailForm.batchDetailInfo" type="textarea" placeholder="请从Excel表格选择复制【型号, 数量, 交货期, 希望备库方式, 运输方式】这四项数据来批量添加" show-word-limit />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogAddBatchDetailVisible = false">取 消</el-button>
        <el-tooltip class="item" effect="dark" content="注意：每次批量最多添加100行数据！" placement="top-start">
          <el-button :loading="saveApplyLoading" type="primary" size="mini" @click="addBatchDetail">确 定</el-button>
        </el-tooltip>
      </div>
    </el-dialog>
    <!-- 批量修改申请项弹窗 -->
    <el-dialog :visible.sync="dialogEditBatchDetailVisible" title="批量修改" width="300px">
      <el-form ref="batchDetailForm" :model="batchDetailForm" size="mini" label-width="100px" >
        <el-form-item prop="dlvDate" label="客户要求货期">
          <el-date-picker v-model="batchDetailForm.dlvDate" :disabled="disabled" :picker-options="handlePickerOptions" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%;" />
        </el-form-item>
      </el-form>
      <el-form ref="batchDetailForm" :model="batchDetailForm" size="mini" label-width="100px" >
        <el-form-item prop="cproductNo" label="物料号">
          <el-input v-model="batchDetailForm.cproductNo" :disabled="disabled" placeholder="物料号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogEditBatchDetailVisible = false">取 消</el-button>
        <el-button type="primary" size="mini" @click="editBatchDetail">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 仓库选择弹窗 -->
    <el-dialog :visible.sync="dialogFormVisible" title="仓库" width="650px">
      <el-form ref="warehouseForm" :inline="true" :model="warehouseForm" size="small">
        <el-form-item >
          <el-input v-model="warehouseForm.warehouseCode" placeholder="仓库代码" style="width:100px" clearable @clear="listWarehouse"/>
        </el-form-item>
        <el-form-item >
          <el-input v-model="warehouseForm.keywords" placeholder="仓库名称" clearable @clear="listWarehouse"/>
        </el-form-item>
        <el-form-item >
          <el-select v-model="warehouseForm.warehouseType" placeholder="仓库类别" style="width:100px" clearable @change="listWarehouse">
            <el-option v-for="item in warehousetypeList" :key="item.code" :label="item.codeName" :value="item.code" clearable/>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="small" @click="listWarehouse"/>
        </el-form-item>
      </el-form>
      <el-table :data="warehouseData.filter(data => !warehouseForm.keywords || data.warehouseName.includes(warehouseForm.keywords))">
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
    <!-- 手动处理的窗口 -->
    <ManualDialog ref="child" @onManualProcess="onManualProcess"/>
    <!--准备订单转订窗口-->
    <el-dialog :visible.sync="dialogFormVisiblePrepareOrder" title="转订" style="margin-left: 55vh;width: 100vh;">
      <el-form :model="editForm" label-position="right" size="mini">
        <el-form-item label="客户交货期">
          <el-date-picker
            v-model="editForm.dlvDate"
            type="date"
            clearable
            style="width: 150px"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"/>
        </el-form-item>
        <el-form-item label="可转订数量">
          <el-input v-model="editForm.qty" disabled style="width: 150px" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogFormVisiblePrepareOrder = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="sureEdit()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { isObjectEqual } from '@/utils/index'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
// import { multiply } from '@/utils/numberToCurrency'
import { saveApply, getApply, removeDetail, submitApply, approveApply, autoHandleApply, listApplyDetail, getApplyDetailResult,
  getInventoryPropertyId, listInventorySummary, saveShikomiInfo, resetApplyProcessStatus, resetShikomiProcess, updatePreStockDetailTranFlag } from '@/api/stock/prestock'
import { getCustomerByNo } from '@/api/common/customer'
import { getEmployeeNameByNo, getEmployeeCodeByDeptNo } from '@/api/common/employee'
import { getDeptNameByNo, getDeptTreeByNo } from '@/api/common/department'
import { getCustomerNameByNo } from '@/api/common/customer'
import { getBinDataForAutoPreStock } from '@/api/stock/bindata'
import { findSupplierByIdOrName } from '@/api/common/supplier'
// import { getEPriceByQuantity } from '@/api/product'
import Pagination from '@/components/Pagination'
import ManualDialog from '@/views/stock/prestock/manualDialog'
import { findProductDetailByModelNo } from '@/api/product'
import { getPrepareOrderInfo, prepareOrderTransfer } from '@/api/prepareOrder/prepareOrder'
import { getTransIds } from '@/api/purchaseOrder'
export default {
  name: 'PreStockDetail',
  components: { Pagination, ManualDialog },
  data() {
    return {
      applyForm: {
        id: '',
        applyNo: '',
        deptNo: '',
        deptName: '',
        status: '1',
        applyType: '1',
        replType: '1',
        stockType: '',
        warehouseCode: '',
        customerNo: '',
        customerName: '',
        userNo: '',
        userName: '',
        groupCustomerNo: '',
        indCode: '',
        pplNo: '',
        projectNo: '',
        projectName: '',
        projectStatus: '',
        equipment: '',
        purpose: '',
        reason: '',
        reasonEn: '',
        remark: '',
        passkey: '',
        shikomiClass: '',
        applyPsn: '',
        applyPsnNo: '',
        approverNo: '',
        approverName: '',
        applyPsnMail: '',
        approverMail: '',
        enname: '',
        transFlag: false, // 是否异仓调拨
        details: [],
        corderNo: ''
      },
      detailForm: {
        status: '1',
        applyModelNo: '',
        modelNo: '',
        modelName: '',
        qtybin: '',
        quantity: '',
        dlvDate: '',
        dlvDateJp: '',
        expType: '0',
        rohs10: false,
        specMark: '0',
        transType: '0',
        supplierCode: '',
        remark: '',
        rowNum: null
      },
      applyRules: {
        applyType: [
          { required: true, message: '请选择申请类型', trigger: 'change' }
        ],
        replType: [
          { required: true, message: '请选择申请类型', trigger: 'change' }
        ],
        customerNo: [
          {
            required: true, validator: (rule, value, callback) => {
              // if (value) {
              //   if (this.applyForm.applyType !== '2' && this.applyForm.stockType.indexOf('GK') === -1) {
              //     return callback(new Error('客户不用填'))
              //   }
              // } else {
              if (!value) {
                if (this.applyForm.applyType !== '2' && this.applyForm.stockType.indexOf('GK') !== -1) {
                  return callback(new Error('客户必填'))
                }
                if (this.applyForm.applyType === '2') {
                  return callback(new Error('客户必填'))
                }
              }
              return callback()
            }
          }
        ],
        userNo: [
          {
            required: true, validator: (rule, value, callback) => {
              if (value && !this.applyForm.customerNo) {
                return callback(new Error('用户不为空,客户必填'))
              }
              return callback()
            }
          }
        ],
        stockType: [
          {
            required: true, validator: (rule, value, callback) => {
              if (this.applyForm.applyType === '2') {
                return callback()
              }
              if (value) {
                this.$refs.applyForm.validateField('customerNo')
                this.$refs.applyForm.validateField('pplNo')
                this.$refs.applyForm.validateField('projectNo')
                this.$refs.applyForm.validateField('groupCustomerNo')
                return callback()
              } else {
                return callback(new Error('请选择备库类型'))
              }
            },
            trigger: 'change'
          }
        ],
        warehouseCode: [
          {
            required: true, validator: (rule, value, callback) => {
              if (value) {
                return callback()
              } else {
                if (this.applyForm.applyType !== '2') {
                  return callback(new Error('请选择仓库'))
                }
              }
              return callback()
            }, trigger: 'change' }
        ],
        pplNo: [
          {
            required: true, validator: (rule, value, callback) => {
              if (this.applyForm.applyType === '2' || !this.applyForm.stockType) {
                return callback()
              }
              if (this.applyForm.stockType.indexOf('PPL') !== -1) {
                if (!value) {
                  return callback(new Error('PPL必填'))
                }
              } else {
                if (value) {
                  return callback(new Error('PPL不用填'))
                }
              }
              return callback()
            }
          }
        ],
        projectNo: [
          {
            required: true, validator: (rule, value, callback) => {
              if (this.applyForm.applyType === '2' || !this.applyForm.stockType) {
                return callback()
              }
              if (this.applyForm.stockType.indexOf('PJ') !== -1) {
                if (!value) {
                  return callback(new Error('项目号必填'))
                }
              } else {
                if (value) {
                  return callback(new Error('项目号不用填'))
                }
              }
              return callback()
            }
          }
        ],
        groupCustomerNo: [
          {
            required: true, validator: (rule, value, callback) => {
              if (!this.applyForm.stockType) {
                return callback()
              }
              if (this.applyForm.stockType.indexOf('JT') !== -1 || this.applyForm.stockType.indexOf('HY') !== -1) {
                if (!value) {
                  return callback(new Error('集团号必填'))
                }
              } else {
                if (value) {
                  return callback(new Error('集团号不用填'))
                }
              }
              return callback()
            }
          }
        ]
      },
      detailRules: {
        applyModelNo: [
          {
            required: true, validator: (rule, value, callback) => {
              if (!value || value === '' || value.length === 0) {
                return callback(new Error('型号必填'))
              } else {
                return callback()
              }
            }
          }
        ],
        quantity: [
          {
            required: true, validator: (rule, value, callback) => {
              if (!value) {
                return callback(new Error('数量必填'))
              } else {
                if (isNaN(Number(value))) {
                  return callback(new Error('请输入数字'))
                } else {
                  if (typeof (Number(value)) === 'number' && Number(value) > 0) {
                    return callback()
                  } else {
                    return callback(new Error('错误的数量'))
                  }
                }
              }
            }
          }
        ],
        dlvDate: [
          {
            required: true, validator: (rule, value, callback) => {
              if (this.applyForm.applyType === '1') {
                if (value) {
                  return callback()
                } else {
                  return callback(new Error('货期必填'))
                }
              } else {
                return callback()
              }
            }
          }
        ],
        dlvDateJp: [
          {
            required: true, validator: (rule, value, callback) => {
              if (this.applyForm.applyType === '2') {
                if (value) {
                  return callback()
                } else {
                  return callback(new Error('货期必填'))
                }
              } else {
                return callback()
              }
            }
          }
        ]
      },
      rowNum: 0,
      disabled: true,
      dialogFormVisible: false,
      dialogFormVisiblePrepareOrder: false,
      dialogEditDetailVisible: false,
      dialogAddBatchDetailVisible: false,
      dialogEditBatchDetailVisible: false,
      batchDetailForm: {
        batchDetailInfo: '',
        cproductNo: '',
        dlvDate: ''
      },
      preApplyForm: {},
      applyTypeClassCode: 2013,
      applyStatusClassCode: 2005,
      stockTypeClassCode: 2009,
      processTypeClassCode: 2030,
      warehouseClassCode: 4001,
      expTypeClassCode: 2016,
      shikomiTypeClassCode: 2022,
      inventoryTypeClassCode: 2001,
      newFlagClassCode: 2017,
      detailStatusClassCode: 2018,
      replTypeClassCode: 2019,
      applyTypeList: [],
      applyStatusList: [],
      stockTypeList: [],
      processTypeList: [],
      warehouseList: [],
      expTypeList: [],
      selection: [],
      // selectModelNo: [],
      warehouseData: [],
      warehousetypeList: [],
      shikomiTypeList: [],
      inventoryTypeList: [],
      newFlagList: [],
      detailStatusList: [],
      transTypeList: [],
      getTransIds: [],
      replTypeList: [],
      deptTreeList: [],
      employeeList: [],
      deptEmpList: [],
      specMarkList: [
        { code: '0', codeName: '正常' },
        { code: '1', codeName: '板' },
        { code: '2', codeName: '阀' }
      ],
      handlePickerOptions: {
        disabledDate(date) {
          return date.getTime() < Date.now() - (24 * 3600 * 1000)
        }
      },
      dialogProcessResultVisible: false,
      processResultForm: {
        id: '',
        applyId: '',
        modelNo: '',
        quantity: '',
        binQty: '',
        inventoryType: '',
        freq: '',
        mean: '',
        resultDetails: [],
        inventoryInfos: [],
        createProcessItems: []
      },
      resultProcessBaseInfo: [],
      modelMonthlyDetail: [],
      modelMonthlyDetailTitle: ['交货期', '数量'],
      modelMonthlyDetailData: [],
      resultItemData: [],
      inventoryInfos: [],
      orderInfos: [],
      supplieData: [],
      supplierList: [],
      warehouseForm: {
        keywords: '', // warehouseName
        warehouseCode: '',
        warehouseType: ''
      },
      processResultRules: {
        processType: [
          { required: true, message: '备库方式必填', trigger: 'change' }
        ],
        orderQty: [
          { required: true, message: '数量必填', trigger: 'blur' },
          { type: 'number', required: true, min: 1, message: '数量>=1', trigger: 'blur' }
        ]
      },
      optStatusList: [
        { code: '1', codeName: '待处理' },
        { code: '2', codeName: '已处理' },
        { code: '3', codeName: '已转定' },
        { code: '4', codeName: '取消处理' }
      ],
      replyStatusList: [
        { code: 1, codeName: '待同步' },
        { code: 2, codeName: '已同步' }
      ],
      propertyId: '',
      inventorySummaryData: [],
      approveLoading: false,
      saveApplyLoading: false,
      submitLoading: false,
      showProcessResultDialogLoading: false,
      autoProcessLoading: false,
      refreshInventoryInfoLoading: false,
      saveProcessItemLoading: false,
      onManualProcessLoading: false,
      autoPreStockDetailLoading: false,
      resetShikomiLoading: false,
      showProcessResult: false,
      dataLoading: false,
      pageNum: 1,
      pageSize: 20,
      total: 0,
      detailFilterStatus: [],
      filtersForBinQty: [],
      filtersForDlvDate: [],
      isValidateOrderQty: true,
      checkModelErrorType: 0,
      checkModelErrorDesc: '',
      ePrice: 0,
      productPriceList: [],
      editForm: {
        dlvDate: '',
        orderFno: '',
        oldPreQty: 0,
        qty: 0,
        fromId: '',
        applyId: ''
      }
    }
  },
  watch: {
    // 监听路由传参变化
    $route: {
      immediate: true,
      handler(newVal, oldVal) {
        if (oldVal && newVal && newVal.name === 'PreStockDetail') {
          // this.newApply() // 重置页面
          this.applyForm.id = this.$route.query.applyId
          this.applyForm.passkey = this.$route.query.passkey
          // this.selectModelNo = []
          this.pageNum = 1
          this.initData()
        }
      }
    }
  },
  created() {
    this.initApplyTypeList()
    this.initApplyStatusList()
    this.initProcessTypeList()
    this.listWarehouse()
    this.initWarehouseList()
    this.initExpTypeList()
    this.initShikomiTypeList()
    this.initInventoryTypeList()
    this.initNewFlagList()
    this.initDetailStatusList()
    this.initTransTypeList()
    this.initDeptTreeList()
    this.applyForm.id = this.$route.query.applyId
    this.applyForm.passkey = this.$route.query.passkey
    this.initData()
    this.listSupplierinfo()
  },
  methods: {
    changeSupplierCode() {
      this.getTransIds = []
      this.detailForm.transType = ''
      var transParam = {}
      transParam.supplierId = this.detailForm.supplierCode
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      getTransIds(transParam).then(res => {
        if (res.success && res.data.length > 0) {
          res.data.forEach(dict => {
            this.getTransIds.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          this.getTransIds = this.transTypeOptions
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
    },
    sureEdit() {
      if (this.editForm.dlvDate === '' || this.editForm.dlvDate === null || this.editForm.dlvDate === undefined) {
        this.$message.error('请选择客户交货期')
        return
      }
      prepareOrderTransfer(this.editForm).then(res => {
        if (res.success) {
          this.$message.success('转订成功')
          this.dialogFormVisiblePrepareOrder = false
          this.listDetail()
        } else {
          console.log('转订失败', res)
          this.$message.error(res.message)
        }
      })
    },
    handleClick(row) {
      console.log('转订', row)
      if (row.orderNo === undefined || row.orderNo === null || row.orderNo === '') {
        this.$message.error('无关联单号，无法获取准备订单信息，无法转订')
        return
      }
      if (row.optStatus === '3') {
        this.$message.error('已转订，无需重复转订')
        return
      }
      getPrepareOrderInfo(row.orderNo).then(res => {
        if (res.success) {
          this.dialogFormVisiblePrepareOrder = true
          this.editForm.qty = res.content.remainQty - res.content.preQty
          this.editForm.orderFno = res.content.orderFno
          this.editForm.oldPreQty = res.content.preQty
          this.editForm.fromId = row.fromId
          this.editForm.applyId = row.applyId
        } else {
          console.log('获取准备订单信息失败', res)
          this.$message.error(res.message)
        }
      })
    },
    initApplyTypeList() {
      getDataCodesTree(this.applyTypeClassCode).then(res => {
        if (res.success) {
          this.applyTypeList = res.content
        }
      })
      getDataCodesTree(this.replTypeClassCode).then(res => {
        if (res.success) {
          this.replTypeList = res.content
        }
      })
    },
    initApplyStatusList() {
      getDataCodesTree(this.applyStatusClassCode).then(res => {
        if (res.success) {
          this.applyStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initProcessTypeList() {
      getDataCodesTree(this.processTypeClassCode).then(res => {
        if (res.success) {
          this.processTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initWarehouseList() {
      getDataCodesTree(this.warehouseClassCode).then(res => {
        if (res.success) {
          this.warehousetypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initExpTypeList() {
      getDataCodesTree(this.expTypeClassCode).then(res => {
        if (res.success) {
          this.expTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initShikomiTypeList() {
      getDataCodesTree(this.shikomiTypeClassCode).then(res => {
        if (res.success) {
          this.shikomiTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initInventoryTypeList() {
      getDataCodesTree(this.inventoryTypeClassCode).then(res => {
        if (res.success) {
          this.inventoryTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initNewFlagList() {
      getDataCodesTree(this.newFlagClassCode).then(res => {
        if (res.success) {
          this.newFlagList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    initDetailStatusList() {
      getDataCodesTree(this.detailStatusClassCode).then(res => {
        if (res.success) {
          this.detailStatusList = res.content
          this.detailFilterStatus = []
          for (const item of this.detailStatusList) {
            this.detailFilterStatus.push({ text: item.codeName, value: item.code })
          }
        }
      })
    },
    initTransTypeList() {
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
            this.transTypeList.push({ code: dict.id, codeName: dict.name })
          })
        } else {
          console.log(res)
        }
      }).catch(error => {
        this.$message.error(error.message)
      })
      // getDataCodesTree(this.transTypeClassCode).then(res => {
      //   if (res.success) {
      //     this.transTypeList = res.content
      //   }
      // })
    },
    initDeptTreeList() {
      const params = { deptNos: '235000,233200' } // 行业开发部,市场部
      getDeptTreeByNo(params).then(res => {
        if (res.success) {
          this.deptTreeList = res.content
        }
      })
    },
    showEditDetailDialog(type) {
      if (this.$refs.detailForm) {
        this.$refs.detailForm.resetFields()
      }
      var transParam = {}
      // 添加
      if (type === 'add') {
        this.getTransIds = []
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
            this.getTransIds = this.transTypeList
          }
        }).catch(error => {
          this.$message.error(error.message)
        })
        this.dialogEditDetailVisible = true
      }
      // 修改
      if (type === 'edit') {
        if (this.selection.length !== 1) {
          this.$message.warning('请选择一个申请项')
          return
        }
        const row = this.selection[0]
        this.detailForm.status = row.status
        this.detailForm.applyModelNo = row.applyModelNo
        this.detailForm.modelNo = row.modelNo
        this.detailForm.modelName = row.modelName
        this.detailForm.qtybin = row.qtybin
        this.detailForm.quantity = row.quantity
        this.detailForm.dlvDate = row.dlvDate
        this.detailForm.dlvDateJp = row.dlvDateJp
        this.detailForm.expType = row.expType
        this.detailForm.rohs10 = row.rohs10
        this.detailForm.specMark = row.specMark
        this.detailForm.transType = row.transType
        this.detailForm.supplierCode = row.supplierCode
        this.detailForm.cproductNo = row.cproductNo
        this.detailForm.remark = row.remark
        this.detailForm.rowNum = row.rowNum === undefined ? null : row.rowNum
        this.getTransIds = []
        transParam.supplierId = row.supplierCode
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
            this.getTransIds = this.transTypeList
          }
        }).catch(error => {
          this.$message.error(error.message)
        })
        this.dialogEditDetailVisible = true
      }
      // shikomi重置
      if (type === 'resetSKM') {
        if (this.selection.length !== 1) {
          this.$message.warning('请选择一个申请项')
          return
        }
        const resetStatus = 'resetSKM'
        const row = this.selection[0]
        this.detailForm.id = row.id
        this.detailForm.status = resetStatus
        this.detailForm.applyModelNo = row.applyModelNo
        this.detailForm.modelNo = row.modelNo
        this.detailForm.modelName = row.modelName
        this.detailForm.qtybin = row.qtybin
        this.detailForm.quantity = row.quantity
        this.detailForm.dlvDate = row.dlvDate
        this.detailForm.dlvDateJp = row.dlvDateJp
        this.detailForm.expType = row.expType
        this.detailForm.rohs10 = row.rohs10
        this.detailForm.specMark = row.specMark
        this.detailForm.transType = row.transType
        this.detailForm.supplierCode = row.supplierCode
        this.detailForm.remark = row.remark
        this.detailForm.rowNum = row.rowNum === undefined ? null : row.rowNum
        this.dialogEditDetailVisible = true
      }
    },
    // 批量修改申请项数据
    showEditBatchDetailDialog() {
      if (this.selection.length === 0) {
        this.$message.warning('请选择申请项')
        return
      }
      this.dialogEditBatchDetailVisible = true
    },
    editBatchDetail() {
      const rows = this.selection
      if (rows.length > 0) {
        const rowNums = []
        for (const i in rows) {
          rowNums.push(rows[i].rowNum)
        }
        for (const i in this.applyForm.details) {
          if (rowNums.indexOf(this.applyForm.details[i].rowNum) > -1) {
            this.applyForm.details[i].dlvDate = this.batchDetailForm.dlvDate
          }
        }
        // 批量修改时，更新物料号
        if (this.batchDetailForm.cproductNo !== '') {
          for (const i in this.applyForm.details) {
            if (rowNums.indexOf(this.applyForm.details[i].rowNum) > -1) {
              this.applyForm.details[i].cproductNo = this.batchDetailForm.cproductNo
            }
          }
        }
      }
      this.dialogEditBatchDetailVisible = false
      this.$message.success('已修改')
    },
    addDetail() {
      const _this = this
      _this.$refs.detailForm.validate((valid) => {
        if (valid) {
          if (_this.detailForm.rowNum !== null) {
            this.applyForm.details.forEach(item => {
              if (item.rowNum === _this.detailForm.rowNum) {
                item.status = _this.detailForm.status
                item.applyModelNo = _this.detailForm.applyModelNo
                item.modelNo = _this.detailForm.applyModelNo
                item.modelName = _this.detailForm.modelName
                item.qtybin = _this.detailForm.qtybin
                item.quantity = _this.detailForm.quantity
                item.dlvDate = _this.detailForm.dlvDate
                item.dlvDateJp = _this.detailForm.dlvDateJp
                item.expType = _this.detailForm.expType
                item.rohs10 = _this.detailForm.rohs10
                item.specMark = _this.detailForm.specMark
                item.transType = _this.detailForm.transType
                item.supplierCode = _this.detailForm.supplierCode
                item.cproductNo = _this.detailForm.cproductNo
                item.remark = _this.detailForm.remark
              }
              if (item.modelNo === _this.detailForm.applyModelNo) {
                item.modelName = _this.detailForm.modelName
                item.supplierCode = _this.detailForm.supplierCode
              }
            })
          } else {
            this.applyForm.details.push({
              id: null,
              itemNo: '',
              applyModelNo: _this.detailForm.applyModelNo,
              modelNo: _this.detailForm.applyModelNo,
              qtybin: _this.detailForm.qtybin,
              quantity: _this.detailForm.quantity,
              dlvDate: _this.detailForm.dlvDate,
              dlvDateJp: _this.detailForm.dlvDateJp,
              expType: _this.detailForm.expType,
              rohs10: _this.detailForm.rohs10,
              specMark: _this.detailForm.specMark,
              transType: _this.detailForm.transType,
              newFlag: '1',
              qtyOnHand: '',
              ordingQty: '',
              supplierCode: _this.detailForm.supplierCode,
              binQty: '',
              monthAvgQty: '',
              canUseMonths: '',
              status: '1',
              cproductNo: _this.detailForm.cproductNo,
              remark: _this.detailForm.remark,
              rowNum: this.rowNum++
            })
          }
          this.dialogEditDetailVisible = false
        } else {
          console.log('申请项编辑失败')
          return false
        }
      })
    },
    async initData() {
      if (!this.applyForm.id) {
        this.disabled = false
        // this.preApplyForm = JSON.parse(JSON.stringify(this.applyForm))
        return
      }
      if (!this.applyForm.passkey) {
        this.$message.error('登录失效，请重新登录。')
        return
      }
      let result = false
      this.dataLoading = true

      const params = { 'applyId': this.applyForm.id, 'passkey': this.applyForm.passkey }
      await getApply(params).then(res => {
        if (res.success) {
          this.applyForm = res.content
          // 控制可编辑性
          // for (const i in this.applyForm.details) {
          //   if (this.applyForm.status === '1') {
          //     this.$set(this.applyForm.details[i], 'isEditable', false)
          //   }
          //   this.$set(this.applyForm.details[i], 'rowNum', this.rowNum++)
          // }
          result = true
        } else {
          result = false
          this.$message.error(res.message)
        }
      }).catch(error => {
        result = false
        this.dataLoading = false
        console.error(error)
      })
      if (!result) {
        return false
      }
      this.getDeptName(this.applyForm.deptNo)
      this.getApplyPsnName(this.applyForm.applyPsnNo)
      this.getApproverName(this.applyForm.approverNo)
      this.getCustomerName(this.applyForm.customerNo)
      this.getUserName(this.applyForm.userNo)
      await this.listDetail()

      this.disabled = this.applyForm.status !== '1'
      this.showProcessResult = this.disabled && this.applyForm.applyType !== '2'
      this.dataLoading = false
    },
    // 分页查询申请项
    async listDetail() {
      const data = {
        applyId: this.applyForm.id,
        applyType: this.applyForm.applyType,
        stockType: this.applyForm.stockType,
        customerNo: this.applyForm.customerNo,
        userNo: this.applyForm.userNo,
        projectNo: this.applyForm.projectNo,
        pplNo: this.applyForm.pplNo,
        groupCustomerNo: this.applyForm.groupCustomerNo,
        warehouseCode: this.applyForm.warehouseCode,
        pageNum: this.pageNum,
        pageSize: this.pageSize
      }
      let result = false
      const detailIds = []
      await listApplyDetail(data).then(res => {
        if (res.success) {
          const details = res.content
          this.applyForm.details = details.list
          this.total = details.total
          // 控制可编辑性
          for (const i in this.applyForm.details) {
            if (this.applyForm.status === '1') {
              this.$set(this.applyForm.details[i], 'isEditable', false)
            } else {
              if (this.applyForm.applyType !== '2') {
                detailIds.push(this.applyForm.details[i].id)
              }
            }
            this.$set(this.applyForm.details[i], 'rowNum', this.rowNum++)
          }
          result = true
        } else {
          result = false
          this.$message.error('查询申请项失败,' + res.message)
        }
      }).catch(error => {
        result = false
        console.info(error)
      })
      if (!result) {
        return false
      }
      // 校验型号
      for (const i in this.applyForm.details) {
        this.checkModelErrorType = 0
        this.checkModelErrorDesc = ''
        this.productPriceList = null
        //    <!--add by WuWeiDong 20231023  bug 12235 校验型号，显示E价  -->
        await this.checkModel(this.applyForm.details[i].modelNo)
        // let productPrice = {
        //   minQuantity: 0,
        //   ePrice: 0
        // }
        // const qty = this.applyForm.details[i].quantity
        if (this.productPriceList) {
          // const newProductPricelist = this.productPriceList.filter(i => i.minquantity <= qty)
          // 判断多段价格区间 设置E价格和最小起订量
          // productPrice = getEPriceByQuantity(newProductPricelist, qty)
          // productPrice.ePrice = rtnPrice.ePrice
        }
        // this.applyForm.details[i].eprice = productPrice.ePrice
        this.$set(this.applyForm.details[i], 'errorType', this.checkModelErrorType)
        this.$set(this.applyForm.details[i], 'errorDesc', this.checkModelErrorDesc)
        // this.$set(this.applyForm.details[i], 'eAmount', multiply(productPrice.ePrice, qty))
        // bug 12235 审批时间：门户接到ops的时间或者ops自行申请审核确认的时间
        if (!this.applyForm.details[i].confirmTime) {
          this.applyForm.details[i].confirmTime = this.applyForm.details[i].createTime
        }
      }
      // 获取处理结果
      this.getDetailResult(detailIds)

      if (this.applyForm.details.length > 0) {
      //  this.refreshApplyModelInventoryInfo()
        this.getDistinctDlvDate()
        this.getDistinctBinQty()
      }
    },
    // 根据申请项id获取对应的处理结果
    getDetailResult(detailIds) {
      if (detailIds.length === 0) {
        return false
      }
      const params = { 'detailIds': detailIds.join(',') }
      getApplyDetailResult(params).then(res => {
        if (res.success) {
          this.applyForm.resultDetails = res.content
          this.showProcessResult = this.applyForm.resultDetails.length > 0
        }
      })
    },
    newApply() {
      if (this.$refs['applyForm']) {
        this.applyForm = {}
        this.$refs['applyForm'].resetFields()
        this.applyForm.id = ''
        this.applyForm.applyNo = ''
        this.applyForm.deptName = ''
        this.applyForm.customerNo = ''
        this.applyForm.userNo = ''
        this.applyForm.customerName = ''
        this.applyForm.userName = ''
        this.applyForm.details = []
        this.applyForm.transFlag = false
        // this.selectModelNo = []
        this.selection = []
        this.disabled = false
        this.preApplyForm = JSON.parse(JSON.stringify(this.applyForm))
      }
    },
    async onSave(formName) {
      if (this.saveApplyLoading) {
        return false
      }
      let result = false
      await this.$refs[formName].validate((valid) => {
        result = valid
      })
      if (!result) {
        return this.$message.error('保存失败，请输入必填项！')
      }
      this.saveApplyLoading = true
      await saveApply(this.applyForm).then(res => {
        if (res.success) {
          this.applyForm.id = res.content.applyId
          this.applyForm.passkey = res.content.passkey
          result = true
          this.$message.success('保存成功')
        } else {
          result = false
          this.$message.error(res.message)
        }
        this.saveApplyLoading = false
      }).catch(error => {
        result = false
        this.saveApplyLoading = false
        console.error(error)
      })
      if (result) {
        await this.initData()
      }
      this.saveApplyLoading = false
    },
    async onSubmit(formName) {
      if (this.dataLoading) {
        return false
      }
      let result = false
      await this.$refs[formName].validate((valid) => {
        result = valid
      })
      if (!result) {
        return this.$message.error('提交失败，请输入必填项！')
      }
      this.submitLoading = true
      await submitApply(this.applyForm).then(res => {
        if (res.success) {
          result = true
          this.$message.success(res.content)
        } else {
          result = false
          this.$message.error(res.message)
        }
      }).catch(error => {
        result = false
        this.submitLoading = false
        console.error(error)
      })
      if (result) {
        await this.initData()
      }
      this.submitLoading = false
    },
    onApprove() {
      if (this.approveLoading) {
        return false
      }
      this.approveLoading = true
      const params = { 'applyIds': [this.applyForm.id] }
      approveApply(params).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.initData()
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.error(error)
      }).finally(
        this.approveLoading = false
      )
    },
    updatePreStockDetailTranFlag() {
      if (!this.applyForm.id) {
        return false
      }
      if (this.saveApplyLoading) {
        return false
      }
      this.saveApplyLoading = true
      const formData = new FormData()
      formData.append('id', this.applyForm.id)
      formData.append('transFlag', this.applyForm.transFlag)
      updatePreStockDetailTranFlag(formData).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      }).finally(
        this.saveApplyLoading = false
      )
    },
    listSupplierinfo() {
      const parm = { 'companyId': '', 'name': '' }
      findSupplierByIdOrName(parm).then(res => {
        const data = res.content
        this.supplieData = []
        for (const item of data) {
          this.supplieData.push({
            code: item.id,
            codeName: item.name
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 保存shikomi申请信息
    saveShikomi() {
      this.saveApplyLoading = true
      const data = {
        id: this.applyForm.id,
        applyPsnMail: this.applyForm.applyPsnMail,
        approverMail: this.applyForm.approverMail,
        customerNo: this.applyForm.customerNo,
        enname: this.applyForm.enname,
        reason: this.applyForm.reason,
        reasonEn: this.applyForm.reasonEn,
        details: []
      }
      for (const detail of this.applyForm.details) {
        data.details.push({
          id: detail.id,
          modelNo: detail.modelNo,
          supplierCode: detail.supplierCode ? detail.supplierCode.trim().toUpperCase() : null,
          dlvDateJp: detail.dlvDateJp,
          modelName: detail.modelName
        })
      }
      saveShikomiInfo(data).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.saveApplyLoading = false
      })
    },
    // 重置申请处理状态
    resetProcessStatus() {
      if (!this.selection || this.selection.length === 0) {
        this.$message.warning('请选择申请项')
        return
      }
      const detailIds = []
      for (const index in this.selection) {
        detailIds.push(this.selection[index].id)
      }
      const data = {
        applyType: this.applyForm.applyType,
        applyId: this.applyForm.id,
        detailIds: detailIds
      }
      resetApplyProcessStatus(data).then(res => {
        if (res.success) {
          this.$message.success('重置成功')
          this.initData()
        } else {
          this.$message.error('重置失败')
        }
      })
    },
    // 重置SHIKOMI处理状态
    resetShikomi(type) {
      this.resetShikomiLoading = true
      const data = {
        applyType: this.applyForm.applyType,
        resetType: type,
        applyId: this.applyForm.id,
        detailIds: [this.detailForm.id],
        quantity: this.detailForm.quantity,
        dlvDateJP: this.detailForm.dlvDateJp
      }
      resetShikomiProcess(data).then(res => {
        this.resetShikomiLoading = false
        if (res.success) {
          this.$message.success('修改成功')
          this.initData()
        } else {
          this.$message.error('修改失败')
        }
      })
    },
    saveDetail(row) {
      row.isEditable = false
    },
    showEditable(row) {
      row.isEditable = true
    },
    removeDetail(index, row) {
      if (row.id) {
        // 1.如果detailId不为空,则先调用后台接口执行删除
        const params = { 'detailIds': row.id }
        removeDetail(params).then(res => {
          if (!res.success) {
            this.$message.error(res.message)
            return
          }
        }).catch(error => {
          console.error(error)
        })
      }
      // 2.删除前端数组数据项
      this.applyForm.details.splice(index, 1)
      this.$refs.applyForm.validate() // 刷新表单校验
      this.$message.success('删除成功')
    },
    removeBatchDetail() {
      const rows = this.selection
      if (rows.length > 0) {
        const detailIds = []
        for (const i in rows) {
          if (rows[i].id) {
            detailIds.push(rows[i].id)
          }
        }

        if (detailIds.length > 0) {
          const params = { 'detailIds': detailIds.join(',') }
          removeDetail(params).then(res => {
            if (!res.success) {
              this.$message.error(res.message)
              return
            }
          }).catch(error => {
            console.error(error)
          })
        }
        rows.forEach(row => {
          this.applyForm.details.forEach((item, index) => {
            if (row.rowNum === item.rowNum) {
              this.applyForm.details.splice(index, 1)
            }
          })
        })

        this.$refs.applyForm.validate() // 刷新表单校验
        this.$refs.applyItemTable.clearSelection()
        this.$message.success('删除成功')
      } else {
        this.$message.warning('请选择申请项删除')
      }
    },
    addBatchDetail() {
      let batchDetailInfo = this.batchDetailForm.batchDetailInfo
      if (batchDetailInfo.indexOf('\t') < 0 && batchDetailInfo.indexOf(',') < 0) {
        return this.$message.warning('请从Excel中复制数据项或手动输入使用","分隔数据项')
      }
      this.saveApplyLoading = true
      if (batchDetailInfo.substring(batchDetailInfo.length - 1) === '\n') {
        batchDetailInfo = batchDetailInfo.substring(0, batchDetailInfo.length - 1)
      }
      const rows = batchDetailInfo.split('\n')
      let lineNum = 0
      for (const i in rows) {
        if (lineNum === 100) {
          this.saveApplyLoading = false
          throw new Error('每次最多批量添加100条数据！')
        }
        let columns = []
        if (rows[i].indexOf('\t') > 0) {
          columns = rows[i].split('\t')
        } else {
          columns = rows[i].split(',')
        }
        if (columns.length < 2) {
          this.$message.warning('错误数据项,请从Excel复制数据项或手动输入使用","分隔数据项')
          continue
        }
        this.applyForm.details.push({
          id: null,
          applyModelNo: columns[0].trim(),
          modelNo: columns[0].trim(),
          quantity: columns[1].replace(/\D+/, '').trim(), // 限制数量只能输入正整数
          dlvDate: this.applyForm.applyType !== '2' && columns[2] ? this.dateFormat(columns[2].trim()) : '',
          dlvDateJp: this.applyForm.applyType === '2' && columns[2] ? this.dateFormat(columns[2].trim()) : '',
          expType: columns[3] ? this.setExpType(columns[3].trim()) : '0',
          transType: columns[4] ? this.setTransType(columns[4].trim()) : '0',
          cproductNo: this.applyForm.applyType === '3' ? columns[5].trim() : '',
          newFlag: '1',
          qtyOnHand: '',
          ordingQty: '',
          supplierCode: '',
          binQty: '',
          monthAvgQty: '',
          canUseMonths: '',
          status: '1',
          remark: '',
          rowNum: this.rowNum++,
          isEditable: true
        })
        lineNum++
      }
      this.dialogAddBatchDetailVisible = false
      this.saveApplyLoading = false
      this.$message.success('批量添加完成')
      // 更新detail.newFlag字段的值
      // this.refreshApplyModelInventoryInfo()
    },
    // 日期格式化 yyyy-MM-dd
    dateFormat(val) {
      if (val) {
        const date = new Date(val)
        let dateStr = date.getFullYear() + '-'
        if (date.getMonth() < 9) {
          dateStr += '0'
        }
        dateStr += (date.getMonth() + 1) + '-'
        if (date.getDate() < 10) {
          dateStr += '0'
        }
        dateStr += date.getDate()
        return dateStr
      } else {
        return ''
      }
    },
    // 字符串转日期
    StrToDate(datestr) {
      return new Date(datestr)
    },
    // 刷新自动补库清单
    async autoPreStockDetail() {
      // if (this.applyForm.replType !== '4') {
      //   return
      // }
      this.autoPreStockDetailLoading = true
      await this.getPropertyId()
      const data = {
        'warehouseCode': this.applyForm.warehouseCode,
        'propertyID': this.propertyId,
        'customerNo': this.applyForm.userNo ? this.applyForm.userNo : this.applyForm.customerNo
      }
      getBinDataForAutoPreStock(data).then(res => {
        if (res.success) {
          const binDataList = res.content
          if (binDataList && binDataList.length > 0) {
            for (const i in binDataList) {
              this.applyForm.details.push({
                id: null,
                modelNo: binDataList[i].modelNo,
                applyModelNo: binDataList[i].modelNo,
                quantity: binDataList[i].replQty,
                dlvDate: this.applyForm.applyType !== '2' ? binDataList[i].loginDate : '',
                dlvDateJp: this.applyForm.applyType === '2' ? binDataList[i].loginDate : '',
                expType: '0',
                newFlag: '1',
                qtyOnHand: '',
                ordingQty: '',
                supplierCode: '',
                binQty: '',
                monthAvgQty: '',
                canUseMonths: '',
                status: '1',
                remark: '',
                rowNum: this.rowNum++,
                isEditable: true
              })
            }
          } else {
            this.$message.warning('自动补库清单为空')
          }
        }
        this.autoPreStockDetailLoading = false
      })
    },
    // 自动处理
    async onAutoProcess() {
      if (this.selection && this.selection.length > 0) {
        const detailIds = []
        for (const item of this.selection) {
          detailIds.push(item.id)
        }
        this.autoProcessLoading = true
        const params = {
          'applyIds': [this.applyForm.id],
          'detailIds': detailIds
        }
        autoHandleApply(params).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.initData()
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: res.message,
              type: 'error',
              duration: 0
            })
          }
          this.autoProcessLoading = false
        }).catch(error => {
          console.error(error)
        })
      } else {
        this.$message.warning('请选择需要自动处理的型号')
      }
    },
    // 手动处理-执行处理项数据
    onGetResultDetail() {
      if (this.selection.length !== 1) {
        this.$message.warning('请选择一个申请项,手动处理每次仅处理一个申请项')
        return
      }
      const param = {
        'applyId': Number(this.applyForm.id),
        'detailId': this.selection[0].id,
        'applyFormWarehouseCode': this.applyForm.warehouseCode
      }
      // 直接调用子组件的方法
      this.$refs.child.onGetResultDetailByParam(param)
      // this.dialogProcessResultVisible = true
    },
    onManualProcess(val) {
      if (val) {
        // 获取手动执行处理后的数据
        // this.getResultInfo()
        // 刷新申请
        this.initData()
      }
    },
    async refreshApplyModelInventoryInfo() {
      if (this.applyForm.details.length === 0) {
        this.$message.error('申请型号库存信息获取失败: 申请项为空')
        return
      }
      this.refreshInventoryInfoLoading = true
      await this.getPropertyId()
      this.getApplyModelInventoryInfo()
      this.refreshInventoryInfoLoading = false
    },
    async getApplyModelInventoryInfo() {
      if (!this.propertyId) {
        this.$message.error('申请型号库存信息获取失败,请确库存信息是否正确: inventoryPropertyId为空')
        this.refreshInventoryInfoLoading = false
        return
      }
      const params = {
        'propertyId': this.propertyId,
        'warehouseCode': this.applyForm.warehouseCode,
        'modelNos': []
      }
      for (const i in this.applyForm.details) {
        if (params.modelNos.indexOf(this.applyForm.details[i].modelNo) === -1) {
          params.modelNos.push(this.applyForm.details[i].modelNo)
        }
      }
      await listInventorySummary(params).then(res => {
        if (res.success) {
          this.inventorySummaryData = res.content
          this.$message.success('库存信息刷新成功')
        } else {
          this.$message.error('申请型号库存信息获取失败：' + res.message)
        }
      }).catch(error => {
        console.error(error)
      })
      this.refreshInventoryInfoLoading = false
      // 如果处于编辑状态中，则更新newFlag
      this.updateNewFlag()
    },
    async getPropertyId() {
      if (this.applyForm.applyType === '2' || this.applyForm.stockType === 'TY') {
        this.propertyId = 1
        return
      }
      let errorMsgs = ''
      const fields = ['customerNo', 'stockType', 'warehouseCode']
      for (const i in fields) {
        this.$refs.applyForm.validateField(fields[i], (errorMsg) => {
          if (errorMsg) {
            errorMsgs = errorMsgs + errorMsg + '</br>'
          }
        })
      }
      if (errorMsgs !== '') {
        this.$message({ dangerouslyUseHTMLString: true, showClose: true, message: '申请型号库存信息获取失败：</br>' + errorMsgs, type: 'error', duration: 0 })
        this.refreshInventoryInfoLoading = false
        return
      }
      const params = {
        'inventoryTypeCode': this.applyForm.stockType,
        'customerNo': this.applyForm.customerNo,
        'ppl': this.applyForm.pplNo,
        'projectCode': this.applyForm.projectNo,
        'groupCustomerNo': this.applyForm.groupCustomerNo
      }
      await getInventoryPropertyId(params).then(res => {
        if (res.success) {
          this.propertyId = res.content.inventoryPropertyId
        } else {
          this.propertyId = ''
        }
      }).catch(error => {
        console.error(error)
      })
      this.refreshInventoryInfoLoading = false
    },
    querySearchAsync(customerNo, cb) {
      // const cus = { customerNo: this.form.customerNo }
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray

        if (results.length <= 0) {
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
      this.applyForm.customerNo = item.customerNo
      this.applyForm.customerName = '【' + item.customerNo + '】' + item.name
    },
    ChangeUserselect(item) {
      this.applyForm.userNo = item.customerNo
      this.applyForm.userName = '【' + item.customerNo + '】' + item.name
    },
    // 单选处理
    selectAll(selection) {
      this.selection = selection
    },
    selectionChange(selection, row) {
      this.selection = selection
    },
    changeDept(val) {
      if (val) {
        this.applyForm.deptNo = val[val.length - 1]
      } else {
        this.applyForm.deptNo = ''
      }
      this.deptEmpList = []
      this.applyForm.applyPsnNo = ''
      this.applyForm.applyPsn = ''
      if (this.applyForm.deptNo) {
        for (const index in this.employeeList) {
          if (this.applyForm.deptNo === this.employeeList[index].value) {
            this.deptEmpList = this.employeeList[index].children
          }
        }
        if (this.deptEmpList.length === 0) {
          this.changeDeptEmpList(this.applyForm.deptNo)
        }
      }
    },
    changeDeptEmpList(deptNo) {
      getEmployeeCodeByDeptNo({ deptNo }).then(res => {
        if (res.success) {
          this.employeeList.push(res.content)
          this.deptEmpList = res.content.children
        }
      })
    },
    changeApplyPsn(val) {
      for (const i in this.deptEmpList) {
        if (val === this.deptEmpList[i].value) {
          this.applyForm.applyPsn = this.deptEmpList[i].label
        }
      }
    },
    goBack() {
      if (!this.disabled && isObjectEqual(this.applyForm, this.preApplyForm)) {
        this.$router.back()
      } else {
        this.$confirm('检测到未保存的内容，是否在离开页面前保存修改？', '确认信息', {
          distinguishCancelAndClose: true,
          confirmButtonText: '保存',
          cancelButtonText: '放弃修改'
        }).then(() => {
          this.onSave('applyForm') // 保存
        }).catch(action => {
          if (action === 'cancel') {
            this.$router.back() // 放弃保存并离开页面
          }
        })
      }
    },
    async listWarehouse() {
      await listWarehouse(this.warehouseForm).then(res => {
        this.warehouseData = res.content
        for (const item of this.warehouseData) {
          if (item.warehouseType === 'MASTER') {
            this.warehouseList.push({
              code: item.warehouseCode,
              codeName: '【' + item.warehouseCode + '】' + item.warehouseName
            })
          }
        }
      }).catch(error => {
        console.log(error)
      })
    },
    selectWarehouse() {
      this.warehouseForm.warehouseCode = ''
      this.dialogFormVisible = true
      // this.listWarehouse()
    },
    getWarehouseName(code) {
      const obj = this.warehouseData.filter(item => item.warehouseCode === code)[0]
      return obj ? '【' + code + '】' + obj.warehouseName : ''
    },
    async getDeptName(deptNo) {
      if (!deptNo) {
        return
      }
      this.applyForm.deptName = deptNo
      const params = { 'deptNo': deptNo }
      await getDeptNameByNo(params).then(res => {
        if (res.success) {
          this.applyForm.deptName = '【' + deptNo + '】' + res.content
        }
      })
    },
    async getApplyPsnName(applyPsnNo) {
      if (!applyPsnNo) {
        return
      }
      if (this.applyForm.applyPsn && this.applyForm.applyPsn !== this.applyForm.applyPsnNo) {
        return
      }
      this.applyForm.applyPsn = ''
      const params = { 'employeeNo': applyPsnNo }
      await getEmployeeNameByNo(params).then(res => {
        if (res.success) {
          this.applyForm.applyPsn = res.content
        }
      })
    },
    async getApproverName(approverNo) {
      if (!approverNo) {
        return
      }
      if (this.applyForm.approverName && this.applyForm.approverName !== this.applyForm.approverNo) {
        return
      }
      this.applyForm.approverName = ''
      const params = { 'employeeNo': approverNo }
      await getEmployeeNameByNo(params).then(res => {
        if (res.success) {
          this.applyForm.approverName = res.content
        }
      })
    },
    async getCustomerName(customerNo) {
      if (!customerNo) {
        return
      }
      this.applyForm.customerName = customerNo
      const params = { 'customerNo': customerNo }
      await getCustomerNameByNo(params).then(res => {
        if (res.success) {
          this.applyForm.customerName = '【' + customerNo + '】' + res.content
        }
      })
    },
    async getUserName(userNo) {
      if (!userNo) {
        return
      }
      this.applyForm.userName = userNo
      const params = { 'customerNo': userNo }
      await getCustomerNameByNo(params).then(res => {
        if (res.success) {
          this.applyForm.userName = '【' + userNo + '】' + res.content
        }
      })
    },
    descFormatter(data, value) {
      if (value === null) {
        return value
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === value.toString()) {
          return item.codeName
        }
      }
      return value
    },
    changeResultProcessType(row) {
      // 同仓调库，供应商等于申请备库的仓库
      if (row.processType === '4' || row.processType === '5') {
        this.supplierList = this.warehouseList
        row.orderStock = this.applyForm.warehouseCode
      }
      // 采购时,运输方式默认为海运
      if (row.processType === '1' || row.processType === '2') {
        this.supplierList = this.supplieData
        row.transType = '0'
      }
    },
    filterDetailByStatus(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    // <!--add by WuWeiDong 20230512 bug 10730  -->
    filterDetailByBinQty(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    // <!--add by WuWeiDong 20230512 bug 10730  -->
    filterDetailByDlvDate(value, row, column) {
      const property = column['property']
      return row[property] === value
    },
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    getApplyType(val) {
      const obj = this.applyTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getReplType(val) {
      const obj = this.replTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getApplyStatus(val) {
      const obj = this.applyStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getWarehouse(val) {
      const obj = this.warehouseData.filter(item => item.warehouseCode === val)[0]
      if (obj) {
        this.warehouseList = []
        const list = { code: obj.warehouseCode, codeName: obj.warehouseName }
        this.warehouseList.push(list)
      }
    },
    getProcessType(val) {
      const obj = this.processTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getOptStatus(val) {
      const obj = this.optStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getExpType(val) {
      const obj = this.expTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    setExpType(val) {
      const obj = this.expTypeList.filter(item => item.codeName === val)[0]
      return obj ? obj.code : ''
    },
    getShikomiType(val) {
      const obj = this.shikomiTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getInventoryType(val) {
      const obj = this.inventoryTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getNewFlag(val) {
      const obj = this.newFlagList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getDetailStatus(val) {
      const obj = this.detailStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getTransType(val) {
      const obj = this.transTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    setTransType(val) {
      const obj = this.transTypeList.filter(item => item.codeName === val)[0]
      return obj ? obj.code : ''
    },
    getSpecMark(val) {
      const obj = this.specMarkList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getReplyStatus(val) {
      const obj = this.replyStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    tableHeaderStyle() {
      return {
        'padding': '2px',
        'font-size': '14px',
        'text-align': 'center'
      }
    },
    tableCellStyle() {
      return {
        'padding': '2px'
      }
    },
    // 编辑状态中刷新库存信息时，如果在库+在途>0，则把申请类型改成旧有备库
    async updateNewFlag() {
      if (this.disabled) {
        return
      }
      for (const detail of this.applyForm.details) {
        const summary = this.inventorySummaryData.filter(item => item.modelNo === detail.modelNo)[0]
        if (summary) {
          if (summary.qtyOnHand > 0 || summary.ordingQty > 0) {
            detail.newFlag = '2'
          } else {
            detail.newFlag = '1'
          }
        } else {
          detail.newFlag = ''
        }
      }
    },
    getQtyOnHand(val) {
      const summary = this.inventorySummaryData.filter(item => item.modelNo === val)[0]
      return summary ? summary.qtyOnHand : ''
    },
    getOrdingQty(val) {
      const summary = this.inventorySummaryData.filter(item => item.modelNo === val)[0]
      return summary ? summary.ordingQty : ''
    },
    getBinQty(val) {
      const summary = this.inventorySummaryData.filter(item => item.modelNo === val)[0]
      return summary ? summary.binQty : ''
    },
    getMonthAvgQty(val) {
      const summary = this.inventorySummaryData.filter(item => item.modelNo === val)[0]
      return summary ? summary.monthAvgQty : ''
    },
    getCanUseMonths(val) {
      const summary = this.inventorySummaryData.filter(item => item.modelNo === val)[0]
      return summary ? summary.canUseMonths : ''
    },
    aomuntFilter(value) {
      return value ? parseFloat(value).toFixed(2) : 0.00
    },
    eDiscountFilter(value) {
      return parseFloat(value * 100).toFixed(2) + '%'
    },
    getRowKeys(row) {
      return row.id
    },
    // <!--add by WuWeiDong 20230512 bug 10730  -->
    // bin数量去重，用于选择筛选
    getDistinctBinQty() {
      const map = new Map()
      this.filtersForBinQty = []
      // const result = this.inventorySummaryData.filter(key => !map.has(key.binQty) && map.set(key.binQty, key.binQty))
      this.applyForm.details.filter(key => !map.has(key.binQty) && map.set(key.binQty, key.binQty))
      map.forEach((key, val) => {
        const tmp = { text: val, value: val }
        this.filtersForBinQty.push(tmp)
      })
    },
    // <!--add by WuWeiDong 20230512 bug 10730  -->
    // 客户交货期去重，用于选择筛选
    getDistinctDlvDate() {
      const map = new Map()
      this.filtersForDlvDate = []
      // const result = this.applyForm.details.filter(key => !map.has(key.dlvDate) && map.set(key.dlvDate.toString(), key.dlvDate.toString()))
      this.applyForm.details.filter(key => key.dlvDate && !map.has(key.dlvDate) && map.set(key.dlvDate, key.dlvDate.toString()))
      map.forEach((key, val) => {
        const tmp = { text: val, value: val }
        this.filtersForDlvDate.push(tmp)
      })
    },
    async checkModel(modelNo) {
      const modelNoList = [modelNo]
      await findProductDetailByModelNo(modelNoList).then(res => {
        if (res.data) {
          const modelInfoS = res.data
          if (modelInfoS) {
            const modelInfo = modelInfoS[0]
            this.checkModelErrorType = 0
            this.checkModelErrorDesc = ''
            if (modelInfo.productValidateResultList) {
              switch (modelInfo.productValidateResultList[0].code) {
                case 23: // 未知型号，请先E价格问询
                case 24: // 此型号为错误型号，请重新输入
                  this.checkModelErrorType = 1
                  this.checkModelErrorDesc = modelInfo.productValidateResultList[0].description
                  break
                case 21: // 此型号为收敛型号，请谨慎选择
                case 22: // 此型号为贩卖限制型号，如要继续选择，请先解除贩卖限制
                  this.checkModelErrorType = 2
                  this.checkModelErrorDesc = modelInfo.productValidateResultList[0].description
                  break
                default:
                  this.checkModelErrorType = 0
                  this.checkModelErrorDesc = ''
              }
            }
            this.productPriceList = modelInfo.productPriceList
            if (this.productPriceList && (this.checkModelErrorType === 0 || this.checkModelErrorType === 2)) {
              const productPrice = this.productPriceList[0]
              console.log('最小起订量：' + productPrice.minquantity)
              if (productPrice.minquantity && productPrice.minquantity > 1) {
                this.checkModelErrorType = 2
                this.checkModelErrorDesc = this.checkModelErrorDesc + ' 最小起订量：' + productPrice.minquantity
              }
            }
            if (modelInfo.product && (this.checkModelErrorType === 0 || this.checkModelErrorType === 2)) {
              if (modelInfo.product.minPackUnit && modelInfo.product.minPackUnit > 1) {
                this.checkModelErrorType = 2
                this.checkModelErrorDesc = this.checkModelErrorDesc + ' 最小包装数：' + modelInfo.product.minPackUnit
              }
            }
          }
        }
      })
    },
    edit(row) {
      this.applyForm.warehouseCode = row.warehouseCode
      const obj = this.warehouseList.filter(item => item.warehouseCode === row.warehouseCode)[0]
      if (!obj) {
        const list = { code: row.warehouseCode, codeName: row.warehouseName }
        this.warehouseList.push(list)
      }
      this.dialogFormVisible = false
    }
  }
}
</script>
<style scoped>
.row-button {
  margin-bottom: 10px;
}
.span-title {
  margin-top: 10px;
  margin-bottom: 10px;
  color: rgba(0,0,0,.85);
  font-weight: bold;
  font-size: 16px;
  line-height: 1.5;
}
.select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
}
.el-form-item--mini.el-form-item {
  margin-bottom: 5px;
  margin-top: 5px;
}
</style>
