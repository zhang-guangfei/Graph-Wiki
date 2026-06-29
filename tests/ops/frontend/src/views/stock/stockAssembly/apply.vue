<template>
  <div class="stock-assembly-apply">
    <el-row v-if="disabled" style="margin-bottom: 10px;">
      <el-button type="primary" size="mini" icon="el-icon-plus" @click="newApply">新建</el-button>
      <el-button v-show="applyForm.status === '2'" type="primary" size="mini" icon="el-icon-check" @click="dialogApproveVisible = true">审核确认</el-button>
      <el-button v-show="applyForm.status === '3'" type="primary" size="mini" @click="dialogHandleVisible = true" >执行处理</el-button>
    </el-row>
    <el-row v-else style="margin-bottom: 10px;">
      <el-button v-permission="['752635']" type="primary" size="mini" icon="el-icon-plus" @click="newApply">新建</el-button>
      <el-button v-permission="['752635']" :loading="saveLoading" type="primary" size="mini" icon="el-icon-folder-add" @click="onSave">保存</el-button>
      <el-button v-show="applyForm.applyNo" :loading="submitLoading" type="primary" size="mini" icon="el-icon-check" @click="onSubmit">提交</el-button>
      <el-button v-show="applyForm.applyNo" :loading="handleLoading" type="primary" size="mini" icon="el-icon-check" @click="onFastHandle">直接执行</el-button>
    </el-row>
    <el-form ref="applyForm" :model="applyForm" :rules="applyRules" size="mini" label-width="115px">
      <el-row type="flex" justify="center">
        <el-col :span="6">
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
        <el-col :span="6">
          <el-form-item label="申请目的:" prop="assembleType">
            <span v-if="disabled">{{ getAssembleType(applyForm.assembleType) }}</span>
            <el-select v-else v-model="applyForm.assembleType" placeholder="请选择申请目的" style="width: 100%" @change="refreshData" >
              <el-option v-for="item in assembleTypeList" :key="item.code" :label="item.codeName" :value="item.code"/>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="申请状态:" prop="status">
            <span >{{ getApplyStatus(applyForm.status) }}</span>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="6">
          <el-form-item label="订单号:" prop="needForOrderNo">
            <span v-if="disabled">{{ applyForm.needForOrderNo }}</span>
            <el-input v-else v-model="applyForm.needForOrderNo" placeholder="请输入订单号" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所需型号:" prop="needModelNo">
            <span v-if="disabled">{{ applyForm.needModelNo }}</span>
            <el-input v-else v-model="applyForm.needModelNo" placeholder="请输入型号" />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item label="所需数量:" prop="needQuantity">
            <span v-if="disabled">{{ applyForm.needQuantity }}</span>
            <el-input v-else v-model.number="applyForm.needQuantity" autocomplete="off" placeholder="请输入数量" />
          </el-form-item>
        </el-col>
      </el-row>
      <el-row type="flex" justify="center">
        <el-col :span="6">
          <el-form-item label="希望交货日期:" prop="dlvDate">
            <span v-if="disabled">{{ applyForm.dlvDate }}</span>
            <el-date-picker v-else v-model="applyForm.dlvDate" :picker-options="handlePickerOptions" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%;" />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="备注:" prop="remark">
            <span v-if="disabled">{{ applyForm.remark }}</span>
            <el-input v-else v-model="applyForm.remark" :maxlength="250" type="textarea" placeholder="请输入备注" show-word-limit />
          </el-form-item>
        </el-col>
      </el-row>
      <!-- 申请项表格 -->
      <el-row>
        <!-- 调出型号申请项表格 -->
        <el-col :span="12">
          <el-card :body-style="{ padding: 0 }">
            <span style="font-weight: bold;"><i class="el-icon-sort-up" />调出型号</span>
            <el-button-group v-if="!disabled">
              <el-button v-permission="['752635']" type="primary" size="mini" @click="addDetail('out')" >添加项</el-button>
              <el-button v-permission="['752635']" type="primary" size="mini" @click="showAddBatchDetailDialog" >批量添加项</el-button>
              <el-button v-permission="['752635']" type="danger" size="mini" @click="removeBatchDetail('out')" >批量删除</el-button>
              <el-button v-permission="['752635']" type="primary" size="mini" @click="showSetUpWarehouseDialog('out')">设置库房</el-button>
              <el-button v-permission="['752635']" v-show="applyForm.assembleType === '3'" type="primary" size="mini" @click="showCopyToTransferInDialog('copy')">复制调入</el-button>
            </el-button-group>
            <el-button type="info" size="mini" icon="el-icon-search" @click="dialogQueryVisible=true,queryForm.id='',queryForm.modelNo=''" >筛选</el-button>
            <vxe-table
              ref="outItemTable"
              :data="applyForm.outItems"
              :loading="outItemsTableLoading"
              :edit-config="{ trigger: 'manual', mode: 'row' }"
              :edit-rules="applyRules.items"
              header-align="center"
              border
              stripe
              keep-source
              max-height="400"
              size="mini">
              <vxe-table-column type="checkbox" align="center" width="40"/>
              <vxe-table-column key="outItem.index" type="seq" title="项号" align="center" width="45" />
              <vxe-table-column key="outItem.modelNo" :edit-render="{}" field="modelNo" title="型号" min-width="130">
                <template #default="{ row }">
                  <span :style="{ color: isErrorId(row.id) }">{{ row.modelNo }}</span>
                  <vxe-button type="text" size="mini" icon="el-icon-view" @click="modelNoClick(row)" />
                </template>
                <template #edit="scope">
                  <vxe-input v-model="scope.row.modelNo" type="text" @input="$refs.outItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.quantity" :edit-render="{}" field="quantity" title="数量" align="center" min-width="60">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.quantity" type="text" @change="handerQuantity(scope.row)" @input="$refs.outItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.warehouseCode" :edit-render="{}" field="warehouseCode" title="仓库" show-overflow min-width="110">
                <template #default="{ row }">
                  <span>{{ getWarehouseName(row.warehouseCode) }}</span>
                </template>
                <template #edit="scope">
                  <vxe-select v-model="scope.row.warehouseCode" filterable transfer @input="$refs.outItemTable.updateStatus(scope)">
                    <vxe-option v-for="item in warehouseList" :key="item.code" :value="item.code" :label="item.codeName" />
                  </vxe-select>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.inventoryType" :edit-render="{}" field="inventoryType" title="库存类型" show-overflow min-width="90">
                <template #default="{ row }">
                  <span>{{ getInventoryType(row.inventoryType) }}</span>
                </template>
                <template #edit="scope">
                  <vxe-select v-model="scope.row.inventoryType" transfer @input="$refs.outItemTable.updateStatus(scope)">
                    <vxe-option v-for="item in inventoryTypeList" :key="item.code" :value="item.code" :label="item.codeName" />
                  </vxe-select>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.customerNo" :edit-render="{}" field="customerNo" title="客户代码" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.customerNo" type="text" @input="$refs.outItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.groupCustomerNo" :edit-render="{}" field="groupCustomerNo" title="集团号" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.groupCustomerNo" type="text" @input="$refs.outItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.pplNo" :edit-render="{}" field="pplNo" title="PPL号" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.pplNo" type="text" @input="$refs.outItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.projectNo" :edit-render="{}" field="projectNo" title="项目号" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.projectNo" type="text" @input="$refs.outItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="outItem.remark" :edit-render="{}" field="remark" title="备注" show-overflow min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.remark" type="text" @input="$refs.outItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column v-if="disabled" key="outItem.optCode" fixed="right" field="optCode" title="状态" align="center" min-width="65">
                <template #default="{ row }">
                  <span>{{ getDetailStatusName(row.optCode) }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column v-if="!disabled" key="outItem.manage" fixed="right" field="manage" title="操作" align="center" width="95">
                <template #default="{ row }">
                  <template v-if="$refs.outItemTable.isActiveByRow(row)">
                    <vxe-button status="primary" icon="el-icon-check" circle @click="saveDetail(row)" />
                  </template>
                  <template v-else>
                    <vxe-button status="primary" icon="el-icon-edit" circle @click="showEditable(row)" />
                    <vxe-button status="danger" icon="el-icon-delete" circle @click="removeDetail(row)" />
                  </template>
                </template>
              </vxe-table-column>
            </vxe-table>
            <pagination :total="outItemTotal" :page-sizes="[20, 50, 100]" :page.sync="pageNum" :limit.sync="pageSize" style="margin-top: 0px" @pagination="listItemDetail" />
          </el-card>
        </el-col>
        <!-- 调入型号申请项表格 -->
        <el-col :span="12">
          <el-card :body-style="{ padding: 0 }">
            <span style="font-weight: bold;"><i class="el-icon-sort-down" />调入型号</span>
            <el-button-group v-if="!disabled" >
              <el-button v-permission="['752635']" type="primary" size="mini" @click="addDetail('in')" >添加项</el-button>
              <el-button v-permission="['752635']" type="primary" size="mini" @click="showAddBatchDetailDialog" >批量添加项</el-button>
              <el-button v-permission="['752635']" type="danger" size="mini" @click="removeBatchDetail('in')" >批量删除</el-button>
              <el-button v-permission="['752635']" type="primary" size="mini" @click="showSetUpWarehouseDialog('in')">设置库房</el-button>
            </el-button-group>
            <vxe-table
              ref="inItemTable"
              :data="applyForm.inItems"
              :loading="inItemsTableLoading"
              :edit-config="{ trigger: 'manual', mode: 'row' }"
              :edit-rules="applyRules.items"
              header-align="center"
              border
              stripe
              keep-source
              max-height="400"
              size="mini">
              <vxe-table-column type="checkbox" align="center" width="40"/>
              <vxe-table-column key="inItem.index" type="seq" title="项号" align="center" width="45"/>
              <vxe-table-column key="inItem.modelNo" :edit-render="{}" field="modelNo" title="型号" min-width="130">
                <template #default="{ row }">
                  <span>{{ row.modelNo }}</span>
                  <vxe-button type="text" size="mini" icon="el-icon-view" @click="modelNoClick(row)" />
                </template>
                <template #edit="scope">
                  <vxe-input v-model="scope.row.modelNo" type="text" @input="$refs.inItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.quantity" :edit-render="{}" field="quantity" title="数量" align="center" min-width="60">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.quantity" type="text" @change="handerQuantity(scope.row)" @input="$refs.inItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.warehouseCode" :edit-render="{}" field="warehouseCode" title="仓库" show-overflow min-width="110">
                <template #default="{ row }">
                  <span>{{ getWarehouseName(row.warehouseCode) }}</span>
                </template>
                <template #edit="scope">
                  <vxe-select v-model="scope.row.warehouseCode" filterable transfer @input="$refs.inItemTable.updateStatus(scope)">
                    <vxe-option v-for="item in warehouseList" :key="item.code" :value="item.code" :label="item.codeName"/>
                  </vxe-select>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.inventoryType" :edit-render="{}" field="inventoryType" title="库存类型" show-overflow min-width="90">
                <template #default="{ row }">
                  <span>{{ getInventoryType(row.inventoryType) }}</span>
                </template>
                <template #edit="scope">
                  <vxe-select v-model="scope.row.inventoryType" transfer @input="$refs.inItemTable.updateStatus(scope)">
                    <vxe-option v-for="item in inventoryTypeList" :key="item.code" :value="item.code" :label="item.codeName"/>
                  </vxe-select>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.customerNo" :edit-render="{}" field="customerNo" title="客户代码" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.customerNo" type="text" @input="$refs.inItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.groupCustomerNo" :edit-render="{}" field="groupCustomerNo" title="集团号" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.groupCustomerNo" type="text" @input="$refs.inItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.pplNo" :edit-render="{}" field="pplNo" title="PPL号" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.pplNo" type="text" @input="$refs.inItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.projectNo" :edit-render="{}" field="projectNo" title="项目号" min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.projectNo" type="text" @input="$refs.inItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column key="inItem.remark" :edit-render="{}" field="remark" title="备注" show-overflow min-width="65">
                <template #edit="scope">
                  <vxe-input v-model="scope.row.remark" type="text" @input="$refs.inItemTable.updateStatus(scope)"/>
                </template>
              </vxe-table-column>
              <vxe-table-column v-if="disabled" key="inItem.optCode" fixed="right" field="optCode" title="状态" align="center" min-width="65">
                <template #default="{ row }">
                  <span>{{ getDetailStatusName(row.optCode) }}</span>
                </template>
              </vxe-table-column>
              <vxe-table-column v-if="!disabled" key="inItem.manage" fixed="right" field="manage" title="操作" align="center" width="95">
                <template #default="{ row }">
                  <template v-if="$refs.inItemTable.isActiveByRow(row)">
                    <vxe-button status="primary" icon="el-icon-check" circle @click="saveDetail(row)" />
                  </template>
                  <template v-else>
                    <vxe-button status="primary" icon="el-icon-edit" circle @click="showEditable(row)" />
                    <vxe-button status="danger" icon="el-icon-delete" circle @click="removeDetail(row)" />
                  </template>
                </template>
              </vxe-table-column>
            </vxe-table>
            <pagination :total="inItemTotal" :page-sizes="[20, 50, 100]" :page.sync="pageNum" :limit.sync="pageSize" style="margin-top: 0px" @pagination="listItemDetail" />
          </el-card>
        </el-col>
      </el-row>
    </el-form>

    <!-- 批量添加调库信息弹窗 -->
    <el-dialog :visible.sync="dialogAddDetailVisible" title="批量添加" width="700px" >
      <el-form ref="itemForm" :model="itemForm" size="mini" label-width="75px" >
        <el-row>
          <el-col>
            <el-form-item icon="el-icon-warning-outline" prop="stockInfo">
              <span slot="label">
                <span class="span-box">
                  <el-tooltip class="item" effect="dark" content="注意：数据项【型号、数量】为必需项！" placement="top-start">
                    <i class="el-icon-warning-outline" />
                  </el-tooltip>
                  <span>数据项</span>
                </span>
              </span>
              <el-input
                v-model="itemForm.stockInfo"
                type="textarea"
                placeholder="请从Excel表格中选择复制下面14项数据来批量添加
                型号,数量,调出仓库代码,调出库存类型,调出客户代码,调出集团号,调出ppl,调出项目号,调入仓库代码,调入库存类型,调入客户代码,调入集团号,调入ppl,调入项目号"
                show-word-limit
                style="white-space: pre-wrap;" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="3">
            <el-button :loading="exportTemplateLoading" size="mini" type="primary" icon="el-icon-download" @click="exportDetailTemplate">模板</el-button>
          </el-col>
          <el-col :span="4">
            <el-upload
              :multiple="false"
              :before-upload="beforeUploadFile"
              :show-file-list="false"
              accept=".xlsx"
              action=""
              class="upload-demo">
              <el-button slot="trigger" :loading="importDetailLoading" size="mini" type="primary" icon="el-icon-plus">选择文件</el-button>
            </el-upload>
          </el-col>
          <el-col :span="4">
            <el-button :loading="importDetailLoading" size="mini" type="success" icon="el-icon-upload2" @click="importDetailByTemplate">{{ importDetailLoading ? '导入中...' : '导入' }}</el-button>
          </el-col>
          <el-col :span="12">
            <div v-if="file !== null" class="el-upload__text">
              <span>{{ file.name }}</span>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-card :body-style="{ padding: '0px' }">
              <div slot="header" style="font-weight: bold;">
                <span>调出库房</span>
              </div>
              <el-form-item label="库房" prop="warehouseCode" >
                <el-select v-model="itemForm.outItem.warehouseCode" filterable placeholder="请选择库房" style="width: 100%">
                  <el-option v-for="item in warehouseList" :key="item.code" :label="item.codeName" :value="item.code" />
                </el-select>
              </el-form-item>
              <el-form-item label="库存类型" prop="inventoryType">
                <el-select v-model="itemForm.outItem.inventoryType" placeholder="请选择类型" style="width: 100%">
                  <el-option v-for="item in inventoryTypeList" :key="item.code" :label="item.codeName" :value="item.code" />
                </el-select>
              </el-form-item>
              <el-form-item label="客户代码" prop="customerNo">
                <el-input v-model.trim="itemForm.outItem.customerNo" placeholder="请输入客户代码" />
              </el-form-item>
              <el-form-item label="集团号" prop="groupCustomerNo">
                <el-input v-model.trim="itemForm.outItem.groupCustomerNo" placeholder="请输入集团号" />
              </el-form-item>
              <el-form-item label="PPL号" prop="pplNo">
                <el-input v-model.trim="itemForm.outItem.pplNo" placeholder="请输入PPL号" />
              </el-form-item>
              <el-form-item label="项目号" prop="projectNo">
                <el-input v-model.trim="itemForm.outItem.projectNo" placeholder="请输入项目号" />
              </el-form-item>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card :body-style="{ padding: '0px' }">
              <div slot="header" style="font-weight: bold;">
                <span>调入库房</span>
              </div>
              <el-form-item label="库房" prop="warehouseCode" >
                <el-select v-model="itemForm.inItem.warehouseCode" filterable placeholder="请选择库房" style="width: 100%">
                  <el-option v-for="item in warehouseList" :key="item.code" :label="item.codeName" :value="item.code" />
                </el-select>
              </el-form-item>
              <el-form-item label="库存类型" prop="inventoryType">
                <el-select v-model="itemForm.inItem.inventoryType" placeholder="请选择类型" style="width: 100%">
                  <el-option v-for="item in inventoryTypeList" :key="item.code" :label="item.codeName" :value="item.code" />
                </el-select>
              </el-form-item>
              <el-form-item label="客户代码" prop="customerNo">
                <el-input v-model.trim="itemForm.inItem.customerNo" placeholder="请输入客户代码" />
              </el-form-item>
              <el-form-item label="集团号" prop="groupCustomerNo">
                <el-input v-model.trim="itemForm.inItem.groupCustomerNo" placeholder="请输入集团号" />
              </el-form-item>
              <el-form-item label="PPL号" prop="pplNo">
                <el-input v-model.trim="itemForm.inItem.pplNo" placeholder="请输入PPL号" />
              </el-form-item>
              <el-form-item label="项目号" prop="projectNo">
                <el-input v-model.trim="itemForm.inItem.projectNo" placeholder="请输入项目号" />
              </el-form-item>
            </el-card>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注" prop="remark">
            <el-input v-model.trim="itemForm.remark" :maxlength="50" type="textarea" placeholder="请输入备注" show-word-limit />
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogAddDetailVisible = false">取 消</el-button>
        <el-tooltip class="item" effect="dark" content="注意：每次最多批量添加100行数据！" placement="top-start">
          <el-button type="primary" size="mini" @click="addBatchDetail">添 加</el-button>
        </el-tooltip>
      </div>
    </el-dialog>

    <!-- 批量设置调库项库房信息弹窗 -->
    <el-dialog :visible.sync="dialogInventoryPropertyVisible" title="设置库房属性" width="340px" >
      <el-form ref="warehouseInfoForm" :model="warehouseInfoForm" size="mini" label-width="75px">
        <el-form-item label="库房" prop="warehouseCode">
          <el-select v-model="warehouseInfoForm.warehouseCode" filterable placeholder="请选择库房" style="width: 100%">
            <el-option v-for="item in warehouseList" :key="item.code" :label="item.codeName" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="库存类型" prop="inventoryType">
          <el-select v-model="warehouseInfoForm.inventoryType" placeholder="请选择类型" style="width: 100%">
            <el-option v-for="item in inventoryTypeList" :key="item.code" :label="item.codeName" :value="item.code" />
          </el-select>
        </el-form-item>
        <el-form-item label="客户代码" prop="customerNo">
          <el-input v-model.trim="warehouseInfoForm.customerNo" placeholder="请输入客户代码" />
        </el-form-item>
        <el-form-item label="集团号" prop="groupCustomerNo">
          <el-input v-model.trim="warehouseInfoForm.groupCustomerNo" placeholder="请输入集团号" />
        </el-form-item>
        <el-form-item label="PPL号" prop="pplNo">
          <el-input v-model.trim="warehouseInfoForm.pplNo" placeholder="请输入PPL号" />
        </el-form-item>
        <el-form-item label="项目号" prop="projectNo">
          <el-input v-model.trim="warehouseInfoForm.projectNo" placeholder="请输入项目号" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogInventoryPropertyVisible = false">取 消</el-button>
        <el-button v-show="selectionType === 'in' || selectionType === 'out'" type="primary" size="mini" @click="handleSelectionWarehouse(selectionType)">确 定</el-button>
        <el-button v-show="selectionType === 'copy'" type="primary" size="mini" @click="copyOutSelection">复制调入</el-button>
      </div>
    </el-dialog>
    <!-- 型号信息弹窗 -->
    <el-dialog :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="780px" class="dialog">
      <product-search v-if="productVisible" ref="ProductSearch"/>
    </el-dialog>
    <!-- 审核确认弹窗 -->
    <el-dialog :visible.sync="dialogApproveVisible" title="审核确认" width="320px" >
      <span style="font-size: 14px; color: #606266; font-weight: 700;">回复说明:</span>
      <el-input v-model="answerText" :maxlength="50" type="textarea" show-word-limit/>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="approveLoading" type="error" size="mini" @click="onApprove('4')">退 回</el-button>
        <el-button :loading="approveLoading" type="primary" size="mini" @click="onApprove('1')">确 定</el-button>
      </div>
    </el-dialog>
    <!-- 执行处理弹窗 -->
    <el-dialog :visible.sync="dialogHandleVisible" title="执行处理" width="320px" >
      <span style="font-size: 14px; color: #606266; font-weight: 700;">回复说明:</span>
      <el-input v-model="answerText" :maxlength="50" type="textarea" show-word-limit/>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="handleLoading" type="error" size="mini" @click="onHandle('4')">退 回</el-button>
        <el-button :loading="handleLoading" type="primary" size="mini" @click="onHandle('3')">处 理</el-button>
      </div>
    </el-dialog>
    <!-- 筛选弹窗 -->
    <el-dialog :visible.sync="dialogQueryVisible" title="筛选" width="320px" >
      <span style="font-size: 14px; color: #606266; font-weight: 700;">型号:</span>
      <el-input v-model="queryForm.modelNo" :maxlength="200" type="textarea" show-word-limit clearable placeholder="加%号模糊查询，换行多项查询"/>
      <div slot="footer" class="dialog-footer">
        <el-button :loading="handleLoading" type="primary" size="mini" @click="listItemDetail">筛 选</el-button>
        <el-button :loading="handleLoading" type="primary" size="mini" @click="listItemDetailForNoQuery">取消筛选</el-button>
        <el-button type="info" size="mini" @click="dialogQueryVisible=false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import { saveApply, getApplyByNo, removeDetail, submitApply, approveApply, handleApply,
  exportTemplate, fastHandleApply, getWarehouseStock, getSpecStock, importApplyDetail, listApplyDetail } from '@/api/stock/stockAssembly'
import ProductSearch from '../../product/index'
export default {
  name: 'StockAssemblyApply',
  components: { Pagination, ProductSearch },
  props: {
    params: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      applyForm: {
        id: '',
        applyNo: '',
        deptNo: '',
        assembleType: '3',
        applyType: '',
        needForOrderNo: '',
        needModelNo: '',
        needQuantity: '',
        dlvDate: '',
        remark: '',
        status: '1',
        outItems: [], // 调出申请项
        inItems: [] // 调入申请项
      },
      queryForm: {
        applyNo: '',
        id: '',
        modelNo: ''
      },
      applyRules: {
        assembleType: [
          { required: true, message: '请选择申请目的', trigger: 'change' }
        ],
        items: {
          modelNo: [{
            required: true, validator: ({ cellValue }) => {
              if (!cellValue) {
                return new Error('型号不能为空')
              }
            }
          }],
          quantity: [{
            required: true, validator: ({ cellValue }) => {
              if (cellValue) {
                if (!/^[1-9]\d*$|^-[1-9]\d*$/.test(cellValue)) {
                  return new Error('错误的数量')
                }
              } else {
                return new Error('数量必填')
              }
            }
          }],
          warehouseCode: [{
            required: true, validator: ({ cellValue }) => {
              if (!cellValue) {
                return new Error('仓库必填')
              }
            }
          }],
          inventoryType: [{
            required: true, validator: ({ cellValue }) => {
              if (!cellValue) {
                return new Error('库存类型必填')
              }
            }
          }],
          customerNo: [{
            required: true, validator: ({ cellValue, row }) => {
              if (row.inventoryType.indexOf('GK') !== -1 && !cellValue) {
                return new Error('客户必填')
              }
              if (row.inventoryType.indexOf('GK') === -1 && cellValue) {
                return new Error('客户不用填')
              }
            }
          }],
          groupCustomerNo: [{
            required: true, validator: ({ cellValue, row }) => {
              if (!cellValue && (row.inventoryType.indexOf('JT') !== -1 || row.inventoryType.indexOf('HY') !== -1)) {
                return new Error('集团号必填')
              }
              if (cellValue && (row.inventoryType.indexOf('JT') === -1 && row.inventoryType.indexOf('HY') === -1)) {
                return new Error('集团号不用填')
              }
            }
          }],
          pplNo: [{
            required: true, validator: ({ cellValue, row }) => {
              if (row.inventoryType.indexOf('PPL') !== -1 && !cellValue) {
                return new Error('PPL号必填')
              }
              if (row.inventoryType.indexOf('PPL') === -1 && cellValue) {
                return new Error('PPL号不用填')
              }
            }
          }],
          projectNo: [{
            required: true, validator: ({ cellValue, row }) => {
              if (row.inventoryType.indexOf('PJ') !== -1 && !cellValue) {
                return new Error('项目号必填')
              }
              if (row.inventoryType.indexOf('PJ') === -1 && cellValue) {
                return new Error('项目号不用填')
              }
            }
          }]
        }
      },
      applyStatusClassCode: 2007,
      assembleTypeClassCode: 2008,
      inventoryTypeClassCode: 2001,
      warehouseClassCode: 4001,
      detailStatusClassCode: 2058,
      assembleTypeList: [],
      inventoryTypeList: [],
      warehouseList: [],
      applyStatusList: [],
      detailStatusList: [],
      handlePickerOptions: {
        disabledDate(date) {
          return date.getTime() < Date.now() - (24 * 3600 * 1000)
        }
      },
      dialogAddDetailVisible: false,
      itemForm: {
        stockInfo: '', // 批量型号与数量
        remark: '',
        // 调出库房信息
        outItem: {
          warehouseCode: '',
          inventoryType: '',
          customerNo: '',
          groupCustomerNo: '',
          pplNo: '',
          projectNo: ''
        },
        // 调入库房信息
        inItem: {
          warehouseCode: '',
          inventoryType: '',
          customerNo: '',
          groupCustomerNo: '',
          pplNo: '',
          projectNo: ''
        }
      },
      dialogInventoryPropertyVisible: false,
      selectionType: '',
      outSelection: [],
      inSelection: [],
      ids: [],
      modelNos: [],
      warehouseInfoForm: {
        warehouseCode: '',
        inventoryType: '',
        customerNo: '',
        groupCustomerNo: '',
        pplNo: '',
        projectNo: ''
      },
      disabled: true,
      itemIndex: 0,
      dialogApproveVisible: false,
      dialogHandleVisible: false,
      dialogQueryVisible: false,
      answerText: '',
      warehouseForm: {
        warehouseCode: '',
        warehouseName: '',
        warehouseType: ''
      },
      warehousetypeList: [],
      warehouseData: [],
      warehouseNameData: [],
      rowData: {},
      warehouseStock: [],
      warehouseStockData: [],
      file: null,
      saveLoading: false,
      submitLoading: false,
      approveLoading: false,
      handleLoading: false,
      productVisible: false,
      exportTemplateLoading: false,
      importDetailLoading: false,
      outItemsTableLoading: false,
      inItemsTableLoading: false,
      pageNum: 1,
      pageSize: 100,
      outItemsPages: 1,
      inItemsPages: 1,
      outItemTotal: 0,
      inItemTotal: 0,
      modelError: [],
      idError: [] // 提交返回误Id数组
    }
  },
  watch: {
    params: {
      handler(newVal, oldVal) {
        if (newVal) {
          this.applyForm.id = newVal.applyId
          this.initData()
        }
      },
      immediate: true,
      deep: true
    }
  },
  created() {
    this.applyForm.id = this.params.applyId
    this.queryForm.id = ''
    this.queryForm.modelNo = ''
    this.modelNos = []
    this.ids = []
    this.initData()
    this.initAssembleTypeList()
    this.initInventoryTypeList()
    this.initWarehouseList()
    this.listWarehouseinfo()
    this.listWarehouseinfo()
    this.initWarehouseinfo()
    this.initApplyStatusList()
    this.initDetailStatusList()
  },
  methods: {
    async initAssembleTypeList() {
      getDataCodesTree(this.assembleTypeClassCode).then(res => {
        if (res.success) {
          this.assembleTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initApplyStatusList() {
      getDataCodesTree(this.applyStatusClassCode).then(res => {
        if (res.success) {
          this.applyStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initInventoryTypeList() {
      getDataCodesTree(this.inventoryTypeClassCode).then(res => {
        if (res.success) {
          this.inventoryTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initWarehouseList() {
      getDataCodesTree(this.warehouseClassCode).then(res => {
        if (res.success) {
          this.warehousetypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initDetailStatusList() {
      getDataCodesTree(this.detailStatusClassCode).then(res => {
        if (res.success) {
          this.detailStatusList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initData() {
      if (!this.applyForm.id) {
        this.disabled = false
        return
      }
      let result = false
      const params = { 'applyId': this.applyForm.id }
      await getApplyByNo(params).then(res => {
        if (res.success) {
          this.applyForm = res.content
          // 控制可编辑性
          if (this.applyForm.status === '1') {
            this.disabled = false
            this.itemIndex = 0
          } else {
            this.disabled = true
          }
          result = true
        } else {
          result = false
          this.$message.error(res.message)
        }
      }).catch(error => {
        result = false
        console.error(error)
      })
      if (result) {
        this.selection = []
        this.pageNum = 1
        this.outItemTotal = 0
        this.inItemTotal = 0
        this.outItemsPages = 1
        this.inItemsPages = 1
        await this.listItemDetail()
      }
    },
    // 取消筛选
    listItemDetailForNoQuery() {
      this.queryForm.id = ''
      this.queryForm.modelNo = ''
      this.listItemDetail()
    },
    async listItemDetail() {
      await this._getQueryParam()
      this.applyForm.outItems = []
      this.applyForm.inItems = []
      await this.getApplyDetail('out')
      await this.getApplyDetail('in')
    },
    async getApplyDetail(transType) {
      const isTransOut = (transType === 'out')
      if (isTransOut) {
        this.outItemsTableLoading = true
      } else {
        this.inItemsTableLoading = true
      }
      let pageNum = this.pageNum
      const pageSize = this.pageSize
      if (isTransOut && this.outItemsPages && this.outItemsPages < pageNum) {
        pageNum = this.outItemsPages
      }
      if (!isTransOut && this.inItemsPages && this.inItemsPages < pageNum) {
        pageNum = this.inItemsPages
      }
      const data = {
        id: this.applyForm.id,
        isTransOut: isTransOut,
        ids: this.ids,
        modelNos: this.modelNos,
        pageNum: pageNum,
        pageSize: pageSize
      }
      await listApplyDetail(data).then(res => {
        if (res.success) {
          const items = res.content.list
          if (isTransOut) {
            this.outItemsPages = res.content.pages
            this.outItemTotal = res.content.total
            this.applyForm.outItems = items
          } else {
            this.inItemsPages = res.content.pages
            this.inItemTotal = res.content.total
            this.applyForm.inItems = items
          }
        }
        if (isTransOut) {
          this.outItemsTableLoading = false
        } else {
          this.inItemsTableLoading = false
        }
      }).catch(error => {
        if (isTransOut) {
          this.outItemsTableLoading = false
        } else {
          this.inItemsTableLoading = false
        }
        console.log(error)
      })
    },
    initWarehouseinfo() {
      listWarehouse(this.warehouseForm).then(res => {
        this.warehouseNameData = res.content
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
        this.warehouseData = res.content
        this.warehouseList = []
        for (const item of this.warehouseData) {
          this.warehouseList.push({
            code: item.warehouseCode,
            codeName: '【' + item.warehouseCode + '】' + item.warehouseName
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },
    getWarehouseName(val) {
      const obj = this.warehouseList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    newApply() {
      this.selection = []
      this.applyForm = {}
      this.$refs['applyForm'].resetFields()
      this.applyForm.id = ''
      this.applyForm.applyNo = ''
      this.applyForm.deptNo = ''
      this.applyForm.status = '1'
      // <!--add by WuWeiDong 20230614 bug 11089 新建时，默认申请目的为：调库-->
      this.applyForm.assembleType = '3'
      this.applyForm.outItems = []
      this.applyForm.inItems = []
      this.disabled = false
    },
    async onSave(formName) {
      if (this.submitLoading || this.handleLoading) {
        return this.$message.warning('处理中，请稍后')
      }
      // 表单校验
      let result = await this.checkApplyDataValidate()
      if (!result) {
        return
      }
      // 保存
      this.saveLoading = true
      this.handleApplyType()
      const data = JSON.parse(JSON.stringify(this.applyForm)) // 深拷贝
      const transType = [true, false]
      transType.forEach((type) => {
        const $table = type ? this.$refs.outItemTable : this.$refs.inItemTable
        const { insertRecords, updateRecords } = $table.getRecordset()
        const items = insertRecords.concat(updateRecords)
        if (type) {
          data.outItems = items.length > 0 ? items : []
        } else {
          data.inItems = items.length > 0 ? items : []
        }
      })
      await saveApply(data).then(res => {
        if (res.success) {
          this.applyForm.id = res.content
          result = true
          this.$message.success('保存成功')
        } else {
          result = false
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error'
          })
        }
      }).catch(error => {
        result = false
        this.saveLoading = false
        console.error(error)
      })
      if (result) {
        this.queryForm.modelNo = ''
        await this.initData()
      }
      this.saveLoading = false
    },
    async onSubmit(formName) {
      this.idError = []
      if (this.saveLoading || this.handleLoading) {
        return this.$message.warning('处理中，请稍后')
      }
      // 表单校验
      let result = await this.checkApplyDataValidate()
      if (!result) {
        return
      }
      // 提交
      this.submitLoading = true
      this.handleApplyType()
      const data = JSON.parse(JSON.stringify(this.applyForm)) // 深拷贝
      const transType = [true, false]
      let errorContent = null
      let errorMsg = ''
      transType.forEach((type) => {
        const $table = type ? this.$refs.outItemTable : this.$refs.inItemTable
        const { insertRecords, updateRecords } = $table.getRecordset()
        const items = insertRecords.concat(updateRecords)
        if (type) {
          data.outItems = items.length > 0 ? items : []
        } else {
          data.inItems = items.length > 0 ? items : []
        }
      })
      await submitApply(data).then(res => {
        if (res.success) {
          result = true
          this.applyForm.id = res.content
          this.$message.success('提交成功')
        } else {
          result = false
          errorContent = res.content
          errorMsg = res.message
        }
        this.submitLoading = false
      }).catch(error => {
        result = false
        this.submitLoading = false
        console.error(error)
      })
      if (result) {
        await this.initData()
      } else {
        if (errorContent !== null) {
          await this.modelErrorShow(errorContent)
          if (this.modelNos === null || this.modelNos.length === 0) {
            await this.initData()
          } else {
            await this.listItemDetail()
            this.queryForm.modelNo = ''
          }
        }
        this.$message({
          dangerouslyUseHTMLString: true,
          showClose: true,
          message: errorMsg,
          type: 'error'
        })
      }
      this.submitLoading = false
    },
    // 提交回返回错误的id,model整理
    modelErrorShow(data) {
      console.log(data)
      this.modelError = JSON.parse(data)
      this.idError = []
      this.modelNos = []
      for (const Key in this.modelError) {
        const obj = this.modelError[Key]
        this.idError.push(obj.id)
        this.modelNos.push(obj.modelNo)
      }
      this.queryForm.modelNo = this.modelNos.join('\n')
      this.modelNos = []
    },
    showError(id) {
      const obj = this.modelError.filter(item => parseInt(item.id) === id)[0]
      if (obj) {
        const msg = obj.message
        console.log(msg)
        this.$alert(msg, id + '错误信息', {
          confirmButtonText: '关闭'
        })
      }
    },
    onApprove(handleType) {
      this.approveLoading = true
      const params = {
        'applyIds': [this.applyForm.id],
        'answerText': this.answerText,
        'handleType': handleType
      }
      approveApply(params).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success(res.content)
          this.dialogApproveVisible = false
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.approveLoading = false
      }).catch(error => {
        console.error(error)
      })
    },
    onHandle(handleType) {
      this.handleLoading = true
      const params = {
        'applyIds': [this.applyForm.id],
        'answerText': this.answerText,
        'handleType': handleType
      }
      handleApply(params).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success(res.content)
          this.dialogHandleVisible = false
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.handleLoading = false
      }).catch(error => {
        console.error(error)
      })
    },
    async onFastHandle(formName) {
      if (this.saveLoading || this.submitLoading) {
        this.$message.warning('处理中，请稍后')
        return false
      }
      // 表单校验
      let result = await this.checkApplyDataValidate()
      if (!result) {
        return
      }
      this.handleLoading = true
      this.handleApplyType()
      const data = this.applyForm
      const transType = [true, false]
      transType.forEach((type) => {
        const $table = type ? this.$refs.outItemTable : this.$refs.inItemTable
        const { insertRecords, updateRecords } = $table.getRecordset()
        const items = insertRecords.concat(updateRecords)
        if (type) {
          data.outItems = items
        } else {
          data.inItems = items
        }
      })
      result = false
      let errorContent = null
      let errorMsg = ''
      await fastHandleApply(data).then(res => {
        if (res.success) {
          result = true
          this.$message.success('执行成功')
        } else {
          result = false
          errorContent = res.content
          errorMsg = res.message
        }
        this.handleLoading = false
      }).catch(error => {
        result = false
        this.handleLoading = false
        console.error(error)
      })
      if (result) {
        await this.initData()
      } else {
        if (errorContent !== null) {
          await this.modelErrorShow(errorContent)
          if (this.modelNos === null || this.modelNos.length === 0) {
            await this.initData()
          } else {
            await this.listItemDetail()
            this.queryForm.modelNo = ''
          }
        }
        this.$message({
          dangerouslyUseHTMLString: true,
          showClose: true,
          message: errorMsg,
          type: 'error'
        })
      }
    },
    async addDetail(transType) {
      const isTransOut = (transType === 'out')
      const $table = isTransOut ? this.$refs.outItemTable : this.$refs.inItemTable
      const item = {
        id: '',
        isTransOut: isTransOut,
        modelNo: '',
        quantity: '',
        optCode: 0,
        remark: '',
        inventoryType: '',
        warehouseCode: '',
        customerNo: '',
        groupCustomerNo: '',
        pplNo: '',
        projectNo: ''
      }
      const { row: newRow } = await $table.insertAt(item, -1) // 在最后行插入新增
      await $table.setActiveRow(newRow)
      // 插入一行数据并触发校验
      const errMap = await $table.validate(newRow).catch(errMap => errMap)
      if (errMap) {
        // 校验不通过
      }
    },
    async checkApplyDataValidate() {
      // 表单校验
      let result = true
      await this.$refs.applyForm.validate(valid => {
        result = valid
      })
      if (!result) {
        this.$message.error('保存失败，请输入必填项！')
        return false
      }
      const $outTable = this.$refs.outItemTable
      const outErrMap = await $outTable.validate(true).catch(errMap => errMap)
      if (outErrMap) {
        this.$message.error('调出项：校验失败')
        return false
      }
      const $inTable = this.$refs.inItemTable
      const inErrMap = await $inTable.validate(true).catch(errMap => errMap)
      if (inErrMap) {
        this.$message.error('调入项：校验失败')
        return false
      }
      return true
    },
    exportDetailTemplate() {
      this.exportTemplateLoading = true
      this.$message.success('正在导出，请耐心等待')
      exportTemplate().then(res => {
        const fileName = '组换调库申请项导入模板.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportTemplateLoading = false
      }).catch(error => {
        console.error(error)
        this.exportTemplateLoading = false
      })
    },
    async importDetailByTemplate() {
      const file = this.file
      if (!file) {
        return this.$message.warning('请选择文件')
      }
      const fileType = file.name.substring(file.name.lastIndexOf('.') + 1)
      if (fileType !== 'xlsx') {
        return this.$message.warning('文件格式错误，请重新导入(.xlsx)')
      }
      this.importDetailLoading = true
      this.saveLoading = true
      // 保存申请-生成申请号
      this.handleApplyType()
      await saveApply(this.applyForm).then(res => {
        if (res.success) {
          this.applyForm.id = res.content
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.saveLoading = false
      }).catch(error => {
        this.saveLoading = false
        this.importDetailLoading = false
        console.error(error)
      })
      if (this.applyForm.id === '') {
        this.importDetailLoading = false
        return this.$message.error('导入失败')
      }
      // 导入申请项
      const formData = new FormData()
      formData.append('applyId', this.applyForm.id)
      formData.append('file', file)
      importApplyDetail(formData).then(res => {
        if (res.success) {
          this.initData()
          this.$message.success(res.content)
          this.dialogAddDetailVisible = false
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.importDetailLoading = false
      }).catch(error => {
        this.importDetailLoading = false
        console.error(error)
      })
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    addBatchDetail() {
      let stockInfo = this.itemForm.stockInfo
      if (stockInfo.indexOf('\t') < 0 && stockInfo.indexOf(',') < 0) {
        this.$message.warning('请从Excel中复制数据项或手动输入使用","分隔数据项')
        return
      }
      if (stockInfo.substring(stockInfo.length - 1) === '\n') {
        stockInfo = stockInfo.substring(0, stockInfo.length - 1)
      }
      const rows = stockInfo.split('\n')
      let lineNum = 0
      const tmpOutItems = []
      const tmpInItems = []
      for (const i in rows) {
        if (lineNum === 100) {
          throw new Error('每次最多批量添加100行数据！')
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
        // 添加调出申请项
        const outItem = {
          id: '',
          isTransOut: true,
          remark: this.itemForm.remark,
          modelNo: columns[0] ? columns[0].trim() : '',
          quantity: columns[1] ? -Math.abs(columns[1].trim()) : 0,
          warehouseCode: columns[2] ? columns[2].trim().split('~')[0] : this.itemForm.outItem.warehouseCode,
          inventoryType: columns[3] ? columns[3].trim().split('~')[0] : this.itemForm.outItem.inventoryType,
          customerNo: columns[4] ? columns[4].trim() : this.itemForm.outItem.customerNo,
          groupCustomerNo: columns[5] ? columns[5].trim() : this.itemForm.outItem.groupCustomerNo,
          pplNo: columns[6] ? columns[6].trim() : this.itemForm.outItem.pplNo,
          projectNo: columns[7] ? columns[7].trim() : this.itemForm.outItem.projectNo,
          optCode: 0
        }
        if (outItem.warehouseCode && outItem.warehouseCode !== '') {
          tmpOutItems.push(outItem)
        }
        // 添加调入申请项
        const inItem = {
          id: '',
          isTransOut: false,
          modelNo: columns[0] ? columns[0].trim() : '',
          quantity: columns[1] ? Math.abs(columns[1].trim()) : 0,
          warehouseCode: columns[8] ? columns[8].trim().split('~')[0] : this.itemForm.inItem.warehouseCode,
          inventoryType: columns[9] ? columns[9].trim().split('~')[0] : this.itemForm.inItem.inventoryType,
          customerNo: columns[10] ? columns[10].trim() : this.itemForm.inItem.customerNo,
          groupCustomerNo: columns[11] ? columns[11].trim() : this.itemForm.inItem.groupCustomerNo,
          pplNo: columns[12] ? columns[12].trim() : this.itemForm.inItem.pplNo,
          projectNo: columns[13] ? columns[13].trim() : this.itemForm.inItem.projectNo,
          optCode: 0
        }
        if (inItem.warehouseCode && inItem.warehouseCode !== '') {
          tmpInItems.push(inItem)
        }
        lineNum++
      }
      if (tmpOutItems.length > 0) {
        const $outTable = this.$refs.outItemTable
        $outTable.insertAt(tmpOutItems, -1) // 在最后行插入新增
      }
      if (tmpInItems.length > 0) {
        const $inTable = this.$refs.inItemTable
        $inTable.insertAt(tmpInItems, -1) // 在最后行插入新增
      }
      this.dialogAddDetailVisible = false
    },
    async saveDetail(row) {
      const $table = row.isTransOut ? this.$refs.outItemTable : this.$refs.inItemTable
      const errMap = await $table.validate(row).catch(errMap => errMap)
      if (errMap) {
        // 校验不通过
        this.$message.warning('请输入必填项！')
      } else {
        $table.clearActived()
      }
    },
    showEditable(row) {
      const $table = row.isTransOut ? this.$refs.outItemTable : this.$refs.inItemTable
      $table.setActiveRow(row)
    },
    removeDetail(row) {
      const $table = row.isTransOut ? this.$refs.outItemTable : this.$refs.inItemTable
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
          this.$message.error(error.message)
        })
      }
      $table.remove(row)
      this.$message.success('删除成功')
    },
    removeBatchDetail(transType) {
      const $table = transType === 'out' ? this.$refs.outItemTable : this.$refs.inItemTable
      const checkboxRecords = $table.getCheckboxRecords()
      if (checkboxRecords.length === 0) {
        return
      }
      const detailIds = []
      for (const item of checkboxRecords) {
        if (item.id && item.id > 0) {
          detailIds.push(item.id)
        }
      }
      // 删除后台数据
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
      // 删除前端表格数据
      $table.remove(checkboxRecords)
      this.$message.success('删除成功')
    },
    showPopver(row) {
      this.warehouseStockData = []
      for (const index in this.warehouseStock) {
        if (this.warehouseStock[index].modelNo === row.modelNo) {
          this.warehouseStockData = this.warehouseStock[index].stockData
          break
        }
      }
      if (this.warehouseStockData && this.warehouseStockData.length > 0) {
        if (row.isTransOut) {
          this.$refs.outItemPulldown.togglePanel()
          console.info('调出项型号库存弹窗状态切换1')
        } else {
          this.$refs.inItemPulldown.togglePanel()
          console.info('调入项型号库存弹窗状态切换1')
        }
        return
      }
      const params = { 'modelNo': row.modelNo }
      getWarehouseStock(params).then(res => {
        if (res.success) {
          const stockData = res.content
          for (const item of stockData) {
            if (item.avaQty_ty === 0 && item.avaQty_zy === 0) {
              continue
            }
            this.warehouseStockData.push({
              warehouseCode: item.warehouseCode,
              avaQty_ty: item.avaQty_ty,
              avaQty_zy: item.avaQty_zy,
              specStock: []
            })
          }
          this.warehouseStock.push({ 'modelNo': row.modelNo, 'stockData': this.warehouseStockData })
          if (row.isTransOut) {
            this.$refs.outItemPulldown.togglePanel()
            console.info('调出项型号库存弹窗状态切换2')
          } else {
            this.$refs.inItemPulldown.togglePanel()
            console.info('调入项型号库存弹窗状态切换2')
          }
        }
      })
    },
    showSpecStock(stockTable, modelNo, row) {
      this.warehouseStockData.map((item) => {
        if (item.warehouseCode !== row.warehouseCode) {
          this.$refs[stockTable].toggleRowExpansion(item, false)
        }
      })
      this.$refs[stockTable].toggleRowExpansion(row)
      if (row.specStock.length > 0) {
        return
      }
      const data = {
        'warehouseCode': row.warehouseCode,
        'modelNos': [modelNo]
      }
      getSpecStock(data).then(res => {
        if (res.success) {
          for (const index in this.warehouseStockData) {
            if (row.warehouseCode === this.warehouseStockData[index].warehouseCode) {
              this.warehouseStockData[index].specStock = res.content
            }
          }
        }
      })
    },
    showAddBatchDetailDialog() {
      this.dialogAddDetailVisible = true
    },
    showSetUpWarehouseDialog(transType) {
      this.selectionType = transType
      const $table = transType === 'out' ? this.$refs.outItemTable : this.$refs.inItemTable
      const checkboxRecords = $table.getCheckboxRecords()
      if (checkboxRecords.length > 0) {
        this.warehouse = ''
        this.dialogInventoryPropertyVisible = true
        this.$refs.warehouseInfoForm.resetFields()
      } else {
        this.$message.warning('请选择需要设置库房信息的申请项')
      }
    },
    showCopyToTransferInDialog(transType) {
      this.selectionType = transType
      const $table = this.$refs.outItemTable
      const checkboxRecords = $table.getCheckboxRecords()
      if (checkboxRecords.length > 0) {
        this.dialogInventoryPropertyVisible = true
        this.$refs.warehouseInfoForm.resetFields()
      } else {
        this.$message.warning('请选择需要复制的调出申请项')
      }
    },
    handleApplyType() {
      if (this.applyForm.assembleType === '3') {
        this.applyForm.applyType = '2' // 调库申请
      } else {
        this.applyForm.applyType = '1' // 组换申请
      }
    },
    handleOutSelectionChange(val) {
      const selection = []
      for (const row of val) {
        selection.push(row.rowIndex)
      }
      this.outSelection = selection
    },
    handleInSelectionChange(val) {
      const selection = []
      for (const row of val) {
        selection.push(row.rowIndex)
      }
      this.inSelection = selection
    },
    handleSelectionWarehouse(transType) {
      const $table = transType === 'out' ? this.$refs.outItemTable : this.$refs.inItemTable
      const checkboxRecords = $table.getCheckboxRecords()
      for (const item of checkboxRecords) {
        item.warehouseCode = this.warehouseInfoForm.warehouseCode
        item.inventoryType = this.warehouseInfoForm.inventoryType
        item.customerNo = this.warehouseInfoForm.customerNo
        item.groupCustomerNo = this.warehouseInfoForm.groupCustomerNo
        item.pplNo = this.warehouseInfoForm.pplNo
        item.projectNo = this.warehouseInfoForm.projectNo
      }
      this.dialogInventoryPropertyVisible = false
    },
    copyOutSelection() {
      const $copyTable = this.$refs.outItemTable
      const checkboxRecords = $copyTable.getCheckboxRecords()
      const copyItems = []
      for (const outItem of checkboxRecords) {
        const inItem = {
          id: '',
          isTransOut: false,
          modelNo: outItem.modelNo,
          quantity: Math.abs(outItem.quantity),
          remark: '',
          inventoryType: this.warehouseInfoForm.inventoryType,
          warehouseCode: this.warehouseInfoForm.warehouseCode,
          customerNo: this.warehouseInfoForm.customerNo,
          groupCustomerNo: this.warehouseInfoForm.groupCustomerNo,
          pplNo: this.warehouseInfoForm.pplNo,
          projectNo: this.warehouseInfoForm.projectNo,
          isEditable: false,
          rowIndex: this.itemIndex++
        }
        copyItems.push(inItem)
      }
      const $table = this.$refs.inItemTable
      $table.insertAt(copyItems, -1)
      this.dialogInventoryPropertyVisible = false
    },
    modelNoClick(row) {
      const item = { modelno: row.modelNo }
      this.productVisible = true
      this.$nextTick(() => {
        this.$refs.ProductSearch.handleSelect(item)
        this.$refs.ProductSearch.productSearchChange()
      })
    },
    _getQueryParam() {
      this.dialogQueryVisible = false
      this.modelNos = []
      this.ids = []
      if (this.queryForm.modelNo !== null && this.queryForm.modelNo !== undefined && this.queryForm.modelNo.length > 0) {
        const model = this.queryForm.modelNo.split('\n')
        for (const val in model) {
          if (model[val].length > 0) {
            this.modelNos.push(model[val])
          }
        }
      }
      if (this.queryForm.id !== null && this.queryForm.id !== undefined && this.queryForm.id.length > 0) {
        const id = this.queryForm.id.split('\n')
        for (const val in id) {
          if (id[val].length > 0) {
            this.ids.push(id[val])
          }
        }
      }
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
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    getAssembleType(val) {
      const obj = this.assembleTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getApplyStatus(val) {
      const obj = this.applyStatusList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getWarehouse(val) {
      const obj = this.warehouseList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getInventoryType(val) {
      const obj = this.inventoryTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getDetailStatusName(val) {
      const obj = this.detailStatusList.filter(item => parseInt(item.code) === val)[0]
      return obj ? obj.codeName : ''
    },
    handerQuantity(row) {
      row.quantity = row.isTransOut ? -Math.abs(row.quantity) : Math.abs(row.quantity)
    },
    isErrorId(val) {
      // const obj = this.idError.filter(item => parseInt(item) === val)[0]
      const obj = this.modelError.filter(item => parseInt(item.id) === val)[0]
      return obj ? 'red' : 'black'
    },
    idErrorMessage(val) {
      // const obj = this.idError.filter(item => parseInt(item) === val)[0]
      const obj = this.modelError.filter(item => parseInt(item.id) === val)[0]
      return obj ? obj.message : ''
    },
    refreshData() {
      this.$forceUpdate()
    }
  }
}
</script>
<style scoped>
.span-box {
  display: inline-block;
  position: relative;
}
.stock-assembly-apply /deep/ .vxe-body--column .vxe-cell {
  padding: 3px;
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
