<template>
  <div class="app-container"> <el-tabs v-model="activeTabName" type="card" @tab-click="handleTabClick">
    <el-tab-pane label="库存设置" name="pane1">
      <div>
        <el-row>
          <el-card>
            <el-form
              ref="form"
              :inline="true"
              :model="form"
              size="mini"
              label-width="100px"
            >
              <el-form-item label="客户代码：">
                <el-input
                  v-model="form.customerNo"
                  style="width: 100px"
                  clearable
                  placeholder="客户代码"
                  size="mini"
                />
              </el-form-item>
              <el-form-item label="库房代码">
                <el-autocomplete
                  v-model.trim="form.stockCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  type="text"
                  size="mini"
                  placeholder="请输入库房代码"
                  class="search-input-class"
                  round
                  highlight-first-item
                  select-when-unmatched
                  clearable
                  @select="handleQuerySelect">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete>
                <el-button type="primary" size="mini" @click="selectWarehouse(1)">选择</el-button>
              </el-form-item>
              <el-form-item label="型号：">
                <el-input
                  v-model="form.modelNo"
                  clearable
                  placeholder="请输入型号"
                  size="mini" />
              </el-form-item>
              <!-- <el-form-item label="货架号">
                <el-input
                  v-model="form.locationNo"
                  style="width: 100px"
                  clearable
                  placeholder="请输入货架号"
                  size="mini"
                />
              </el-form-item> -->
              <el-form-item label="补货类型">
                <el-select
                  v-model="form.replType"
                  placeholder="请选择"
                  style="width: 125px"
                  clearable
                  size="mini"
                >
                  <el-option
                    v-for="item in replTypeOptions"
                    :key="item.code"
                    :value="item.code"
                    :label="item.codeName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="提案方">
                <el-select
                  v-model="form.sponsor"
                  placeholder="请选择"
                  style="width: 125px"
                  clearable
                  size="mini"
                >
                  <el-option
                    v-for="item in sponsorOptions"
                    :key="item.value"
                    :value="item.value"
                    :label="item.label"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['503754']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="searchData"
                >查询</el-button
                >
              </el-form-item>

              <el-form-item>
                <el-button
                  v-permission="['805447']"
                  type="primary"
                  icon="el-icon-upload2"
                  size="mini"
                  @click="uploadFileDialogVisible = true">导入</el-button>
              </el-form-item>

              <el-form-item>
                <el-button v-permission="['805447']" :loading="sendLoading" type="primary" size="mini" @click="sendAgentOrder">发送型号订货统计</el-button>
              </el-form-item>
            </el-form>

          </el-card>
        </el-row>
      </div>
      <div style="margin-top: 10px">
        <el-table
          ref="multipleTable"
          :data="listdata"
          :default-sort = "{prop: 'sponsor', order: 'ascending'}"
          tooltip-effect="dark"
          border
          size="mini"
          style="width: 100%"
          stripe
          @selection-change="handleSelection"
        >
          <!-- 表头字段 -->
          <!-- <el-table-column type="selection" width="55"/> -->
          <el-table-column fixed prop="customerNo" label="客户代码" />
          <el-table-column fixed prop="stockCode" label="库房代码" />
          <el-table-column prop="modelNo" label="型号" />
          <!-- <el-table-column prop="locationNo" label="货架位" >
            <template slot-scope="scope">
              <span v-if="scope.row.isEditPropertyShow">
                <el-input v-model="scope.row.locationNo" size="mini" placeholder="货架位" />
              </span>
              <span v-else>{{ scope.row.locationNo }}</span>
            </template>
          </el-table-column> -->
          <el-table-column prop="initStockQty" label="备库总数" >
            <template slot-scope="scope">
              <span v-if="scope.row.isEditPropertyShow">
                <el-input v-model="scope.row.initStockQty" size="mini" placeholder="备库总数" />
              </span>
              <span v-else>{{ scope.row.initStockQty }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="initUnitQty" label="单位数量" >
            <template slot-scope="scope">
              <span v-if="scope.row.isEditPropertyShow">
                <el-input v-model="scope.row.initUnitQty" size="mini" placeholder="备库总数" />
              </span>
              <span v-else>{{ scope.row.initUnitQty }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="eprice" label="E价(单价)" >
            <template slot-scope="scope">
              <span v-if="scope.row.eprice">
                <el-input v-model="scope.row.eprice" size="mini" placeholder="E价(单价)" />
              </span>
              <span v-else>{{ scope.row.eprice }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="sponsor" sortable label="提案方" >
            <template slot-scope="scope">
              <span v-if="scope.row.eprice">
                <el-input v-model="scope.row.sponsor" size="mini" placeholder="提案方" />
              </span>
              <span v-else>{{ scope.row.sponsor }}</span>
            </template>
          </el-table-column>
          <el-table-column label="补库类型" align="center" >
            <template slot-scope="scope">
              <span v-if="scope.row.isEditPropertyShow">
                <el-select
                  v-model="scope.row.replType"
                  style="width: 100px"
                  size="mini"
                >
                  <el-option
                    v-for="item in replTypeOptions"
                    :key="parseInt(item.code)"
                    :value="parseInt(item.code)"
                    :label="item.codeName"
                  />
                </el-select>
              </span>
              <span v-else> {{ replTypedescformatter( scope.row ) }}</span>

            </template>
          </el-table-column>
          <el-table-column label="状态" align="center" >
            <template slot-scope="scope">
              <el-tooltip :content="settingStatusformatter(scope.row)" placement="top">
                <el-switch
                  v-permission="['805447']"
                  v-model="scope.row.status"
                  :active-value="1"
                  :inactive-value="9"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  @change="handleUpdateCsSettingStatus(scope.$index,scope.row)"/>
              </el-tooltip>
            </template>

          </el-table-column>

          <el-table-column :formatter="updateTimeformatter" prop="updateTime" label="更新时间" />

          <el-table-column label="操作" align="center" width="140">
            <template slot-scope="scope">
              <el-button v-permission="['805447']" v-if="!scope.row.isEditPropertyShow" size="mini" icon="el-icon-edit" @click="editProperty(scope.row,scope.$index)"/>
              <div v-else>
                <el-button plain size="small" @click="saveProperty(scope.row,scope.$index)">保存</el-button>
                <el-button size="small" @click="cancelProperty(scope.row,scope.$index)">取消</el-button>
              </div>
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="settingFromTotal > 0"
          :total="settingFromTotal"
          :page-sizes="[20, 50, 100, 200, 500]"
          :page.sync="form.pageNum"
          :limit.sync="form.pageSize"
          @pagination="searchData"
        />
      </div>
      <div/>
    </el-tab-pane>

    <!-- 仓库选择弹窗 -->
    <el-dialog :visible.sync="dialogFormVisible" title="仓库" width="550px">
      <el-form ref="form" :inline="true" :model="warehouseForm" size="small">
        <el-form-item >
          <el-input v-model="warehouseForm.warehouseCode" placeholder="仓库代码" style="width:100px" clearable @clear="listWarehouseinfo"/>
        </el-form-item>
        <el-form-item >
          <el-input v-model="warehouseForm.warehouseName" placeholder="仓库名称" clearable @clear="listWarehouseinfo"/>
        </el-form-item>
        <!-- <el-form-item >
            <el-select v-model="warehouseForm.warehouseType" placeholder="仓库类别" style="width:100px" clearable @change="listWarehouseinfo">
              <el-option v-for="item in warehousetypeList" :key="item.code" :label="item.codeName" :value="item.code" clearable/>
            </el-select>
          </el-form-item> -->
        <el-form-item>
          <el-button icon="el-icon-search" size="small" @click="listWarehouseinfo"/>
        </el-form-item>
      </el-form>
      <el-table :data="warehouseData.filter(data => !warehouseForm.warehouseName || data.warehouseName.includes(warehouseForm.warehouseName))">
        <el-table-column property="warehouseCode" label="仓库代码" width="100px"/>
        <el-table-column property="warehouseName" label="仓库名称" width="250px"/>
        <!-- <el-table-column :formatter="getWarehouseType" property="warehouseType" label="仓库类别" width="100px"/> -->
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button type="primary" size="mini" @click="edit(scope.row)">选择</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

    <el-tab-pane label="库房清单" name="tabWarehourse" type="border-card">
      <el-card>
        <el-form
          ref="warehourseForm"
          :inline="true"
          :model="warehourseForm"
          size="mini"
          label-width="100px"
          height="250"
        >
          <el-form-item label="客户代码：">
            <el-input
              v-model="warehourseForm.agentNo"
              style="width: 120px"
              clearable
              placeholder="客户代码"
            />
          </el-form-item>

          <el-form-item label="库房代码">
            <el-autocomplete
              v-model.trim="warehourseForm.warehouseCode"
              :fetch-suggestions="querySearchStockCodeAsync"
              :debounce="0"
              type="text"
              size="mini"
              placeholder="请输入库房代码"
              class="search-input-class"
              round
              highlight-first-item
              select-when-unmatched
              clearable
              @select="handleQuerySelectWare">
              <template slot-scope="{ item }">
                <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
              </template>
            </el-autocomplete>
            <el-button type="primary" size="mini" @click="selectWarehouse(2)">选择</el-button>
          </el-form-item>

          <!-- <el-form-item label="库房类型">
            <el-select
              v-model="warehourseForm.warehouseType"
              placeholder="请选择"
              style="width: 125px"
              clearable
              size="mini"
            >
              <el-option
                v-for="item in stockTypeOptions"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
          </el-form-item> -->

          <el-form-item>
            <el-button
              v-permission="['503754']"
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="searchWarehouseData"
            >查询</el-button>
            <el-button v-permission="['805447']" :loading="exportLoading" type="primary" icon="el-icon-download" size="mini" @click="exportData()">导出</el-button>
          </el-form-item>
          <!-- <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-edit"
              size="mini"
              @click="showAddCsWarehourse"
            >新增</el-button
            >
          </el-form-item> -->
        </el-form>

      </el-card>

      <div style="margin-top: 10px">
        <el-table
          ref="warehourselistdata"
          :data="warehourselistdata"
          tooltip-effect="dark"
          border
          size="mini"
          style="width: 100%"
          stripe
        >
          <el-table-column prop="warehouseCode" label="库房代码" fixed />
          <el-table-column :formatter="stockTypeCodeNameformatter" prop="warehouseType" label="库房类型" />
          <el-table-column :show-overflow-tooltip="true" prop="warehouseName" label="库房名称" />
          <el-table-column prop="customerNo" label="客户代码" />
          <el-table-column prop="deptNo" label="营业所" />
          <el-table-column :show-overflow-tooltip="true" :formatter="deptformatter" prop="deptNo" label="营业所名称" />
          <el-table-column label="客户联系方式" >
            <el-table-column :show-overflow-tooltip="true" prop="customerLinkman" label="联系人" />
            <el-table-column :show-overflow-tooltip="true" prop="customerPhone" label="电话" />
            <el-table-column :show-overflow-tooltip="true" prop="customerMail" label="邮箱"/>
          </el-table-column>
          <el-table-column label="仓库联系方式" >
            <el-table-column :show-overflow-tooltip="true" prop="linkMan" label="联系人" />
            <el-table-column :show-overflow-tooltip="true" prop="mail" label="邮箱"/>
            <el-table-column :show-overflow-tooltip="true" prop="address" label="地址"/>
          </el-table-column>
          <el-table-column label="SMC联系方式" >
            <el-table-column :show-overflow-tooltip="true" prop="smcLinkman" label="联系人" />
            <el-table-column :show-overflow-tooltip="true" prop="smcPhone" label="电话" />
            <el-table-column :show-overflow-tooltip="true" prop="smcmail" label="邮箱" />
          </el-table-column>

          <el-table-column prop="modifier" label="更新人" />
          <el-table-column :formatter="updateTimeformatter" prop="modifyTime" label="更新时间" />
          <el-table-column prop="status" label="停用" align="center" >
            <template slot-scope="scope">
              <el-tooltip :content="'状态: ' + warehourseStatusformatter(scope.row)" placement="top">
                <el-switch
                  v-permission="['805447']"
                  v-model="scope.row.disableFlag"
                  :active-value="0"
                  :inactive-value="1"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                  @change="handleUpdateChangeWareStatus(scope.$index,scope.row)"/>
              </el-tooltip>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" width="140">
            <template slot-scope="scope">
              <el-button v-permission="['805447']" size="mini" icon="el-icon-view" @click="showEditDetail(scope.row)" />
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="warehourseForm.total > 0"
          :total="warehourseForm.total"
          :page-sizes="[20, 50, 100, 200, 500]"
          :page.sync="warehourseForm.pageNum"
          :limit.sync="warehourseForm.pageSize"
          @pagination="searchWarehouseData"
        />
      </div>
    </el-tab-pane>
  </el-tabs>
    <el-divider />
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="上传"
      width="420px"
    >
      <div class="upload">
        <el-select v-model="uploadType" size="mini" style="width:120px" placeholder="请选择">
          <el-option
            v-for="item in uploadTypeData"
            :key="item.value"
            :label="item.label"
            :value="item.value"/>
        </el-select>
        <el-upload
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          :on-success="uploadSuccess"
          style="margin-top:10px"
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
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeClick">关闭</el-button>
        <el-button
          type="primary"
          @click="downloadTemplate('../../../static/template/downLoadTemplate/csstocksetting.xlsx')">下载模板</el-button>
        <el-button type="success" @click="importData">
          <span :loading="uploadStatus">{{
            uploadStatus ? "上传中..." : "上传文件"
          }}</span>
        </el-button>
      </span>
    </el-dialog>
    <!--dialog 库存清单新增-->
    <el-dialog :visible.sync="dialogStockVisible" title="寄售库房" width="620px">
      <el-divider content-position="left">寄售库房</el-divider>
      <el-form ref="editeForm" :model="editeForm" size="mini">
        <!--第一行-->
        <el-row>
          <el-col :span="8">
            <el-form-item label-width="100px" label="客户代码" >
              <el-input
                v-model="editeForm.customerNo"
                :disabled="editeForm.isEditable ? true : false"
                show-word-limit
                maxlength="5"
                size="mini"
                style="width:100px"
                autocomplete="off"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="100px" label="库房代码">
              <el-input
                v-model="editeForm.warehouseCode"
                :disabled="editeForm.isEditable ? true : false"
                style="width:100px"
                size="mini"
                maxlength="3"
                show-word-limit
                autocomplete="off"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="100px" label="库房类型">
              <el-select
                v-model="editeForm.warehouseType"
                placeholder="请选择"
                style="width: 100px"
                clearable
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
          </el-col>
        </el-row>
        <!--第二行-->
        <el-row>
          <el-col :span="13">
            <el-form-item label-width="100px" label="库房名称">
              <el-input
                v-model="editeForm.warehouseName"
                type="text"
                style="width:200px"
                size="mini"
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label-width="120px" label="最大备库E金额">
              <el-input
                v-model="editeForm.maxEamount"
                style="width:150px"
                size="mini"
                type="text"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <!--第二行-->
        <el-row>
          <el-col :span="8">
            <el-form-item label-width="100px" label="收货人电话">
              <el-input
                v-model="editeForm.postCode"
                style="width:100px"
                size="mini"
                type="text"
              />
            </el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label-width="100px" label="收货人邮箱">
              <el-input
                v-model="editeForm.contactTelno"
                style="width:100px"
                size="mini"
                type="text"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <!--第三行-->
        <el-row>
          <el-col :span="8">
            <el-form-item label-width="100px" label="客户联系人">
              <el-input
                v-model="editeForm.customerLinkman"
                style="width:100px"
                type="text"
                size="mini"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="100px" label="联系电话">
              <el-input
                v-model="editeForm.customerPhone"
                style="width:100px"
                type="text"
                size="mini"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="100px" label="邮箱地址">
              <el-input
                v-model="editeForm.customerMail"
                style="width:100px"
                type="text"
                size="mini"
              />
          </el-form-item></el-col>
        </el-row>
        <!--第四行-->
        <el-row>
          <el-col :span="8">
            <el-form-item label-width="100px" label="SMC联系人">
              <el-input
                v-model="editeForm.smcLinkman"
                style="width:100px"
                type="text"
                size="mini"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="100px" label="联系电话">
              <el-input
                v-model="editeForm.smcPhone"
                style="width:100px"
                type="text"
                size="mini"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label-width="100px" label="邮箱地址">
              <el-input
                v-model="editeForm.smcMail"
                style="width:100px"
                type="text"
                size="mini"
              />
          </el-form-item></el-col>
        </el-row>
        <!--第五行-->
        <el-row>
          <el-col :span="24">
            <el-form-item label-width="100px" label="收货地址">
              <el-input
                v-model="editeForm.address"
                style="width:420px"
                type="text"
                size="mini"
              />
            </el-form-item>
          </el-col>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogStockVisible = false">关闭</el-button>
        <!-- <el-button
          type="primary"
          @click="dialogStockVisible = false;saveCsWarehourse()"
        >保存</el-button> -->
      </div>

    </el-dialog>
</div></template>

<script>
import Pagination from '@/components/Pagination'
import { listSetting, uploadFile, listCsWarehouse, saveCsWarehourse, deleteCsSetting, updateCsWarehourseStatus, updateCsSetting, updateCsSettingStatus, exportWareHouseData } from '@/api/stock/csStockSetting.js'
import { listWarehourseStockCode } from '@/api/stock/csStockApply.js'
import { onSendAgentOrderFreqReport } from '@/api/stock/report.js'
import { findDepartments } from '@/api/common/department'
import { getDictDataByPid, listWarehouse } from '@/api/common/dict'
export default {
  name: 'CSStockSetting',
  components: { Pagination },
  data() {
    return {
      activeTabName: 'pane1',
      listLoading: true,
      dialogFormVisible: false,
      actionUrl: '',
      listdata: [],
      warehourselistdata: [],
      form: {
        modelNo: '',
        customerNo: '',
        locationNo: '',
        stockCode: '',
        replType: '',
        sponsor: '',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      warehouseForm: {
        warehouseCode: '',
        warehouseName: ''
      },
      wareType: 0,
      warehouseData: [],
      warehourseForm: {
        warehouseCode: '',
        agentNo: '',
        warehouseType: 'WT',
        pageNum: 1,
        pageSize: 20,
        total: 0
      },
      editeForm: {
        id: 0,
        warehouseType: '',
        warehouseCode: '',
        warehouseName: '',
        postCode: '',
        linkMan: '',
        address: '',
        mail: '',
        contactPsn: '',
        maxEamount: '',
        customerNo: '',
        customerLinkman: '',
        customerPhone: '',
        customerMail: '',
        smcLinkman: '',
        smcPhone: '',
        smcMail: '',
        disableFlag: '',
        assemblyFlag: '',
        delflag: '',
        centralizeFlag: '',
        purchaseFlag: '',
        parentCode: '',
        isEditable: false
      },
      uploadStatus: false,
      sendLoading: false,
      settingFromTotal: 0,
      uploadFileDialogVisible: false,
      dialogStockVisible: false,
      exportLoading: false,
      file: null,
      sponsorOptions: [
        { value: 'SMC', label: 'SMC提案' },
        { value: '代理', label: '代理提案' }
      ],
      uploadTypeData: [
        { value: 0, label: '更新' },
        { value: 1, label: '替换' }
      ],
      uploadType: 0,
      stockCodeOptions: [],
      stockTypeOptions: [],
      deptInfos: [], // 营业所部门信息
      // 补货类型
      replTypeOptions: [],
      // 寄售设置补货类型
      replTypeClassCode: '2027',
      // 寄售仓库类型
      stockTypeClassCode: '4001'

    }
  },
  created() {
    this.initReplTypedesc()
    this.initStockTypeDesc()
    this.initstDeptInfo()
  },
  methods: {
    searchData() {
      this.listLoading = true
      listSetting(this.form).then(result => {
        console.log(result)
        this.listdata = result.content.list
        this.settingFromTotal = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    initstDeptInfo() {
      findDepartments().then(result => {
        console.log(result)
        this.deptInfos = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    exportData() {
      console.log('导出 =>')
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      console.log('参数==》', this.warehourseForm)
      exportWareHouseData(this.warehourseForm).then(res => {
        const fileName = '库房清单.xlsx'
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
    // 补货类型
    initReplTypedesc() {
      getDictDataByPid(this.replTypeClassCode).then(result => {
        this.replTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    initStockTypeDesc() {
      getDictDataByPid(this.stockTypeClassCode).then(result => {
        this.stockTypeOptions = result.content
      }).catch(error => {
        console.log(error)
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
      uploadFile(formData, this.uploadType)
        .then(res => {
          console.log(res)
          if (res.success) {
            this.file = null
            this.$message.success(res.content)
            this.uploadFileDialogVisible = false
            this.searchData()
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    downloadTemplate(url) {
      console.log('路径：')
      console.log(url)
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
    searchWarehouseData() {
      this.listLoading = true
      listCsWarehouse(this.warehourseForm).then(result => {
        this.warehourselistdata = result.content.list
        this.warehourseForm.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    updateTimeformatter(row) {
      if (row.updateTime == null) {
        return ''
      }
      return this.dayjs(row.updateTime).format('YYYY-MM-DD')
    },

    descFormatter(data, id) {
      if (data === null) {
        return id
      }
      if (id === null) {
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
    warehourseStatusformatter(row) {
      if (row.disableFlag === 1) {
        return '停用'
      } else if (row.disableFlag === 0) {
        return '启用'
      } else {
        return ''
      }
    },
    // 型号设置状态
    settingStatusformatter(row) {
      if (row.status === 1) {
        return '启用'
      } else if (row.status === 9) {
        return '停用'
      } else {
        return ''
      }
    },
    selectWarehouse(val) {
      this.wareType = val
      this.dialogFormVisible = true
      this.listWarehouseinfo()
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: 'WT',
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    edit(row) {
      if (this.wareType === 1) {
        this.form.stockCode = row.warehouseCode
      }
      if (this.wareType === 2) {
        this.warehourseForm.warehouseCode = row.warehouseCode
      }
      this.dialogFormVisible = false
    },
    deptformatter(row) {
      return this.descFormatter(this.deptInfos, row.deptNo)
    },
    saveCsWarehourse() {
      saveCsWarehourse(this.editeForm).then(res => {
        console.info('saveCsWarehourse response ', res)
        if (!res.success) {
          this.$message.error(res.content)
          return
        }
        this.$message.success(res.content)
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    showEditDetail(row) {
      this.dialogStockVisible = true
      this.editeForm = row
      console.log(this.editeForm)
      this.editeForm.isEditable = true
    },
    showAddCsWarehourse(row) {
      this.dialogStockVisible = true
      this.editeForm = ''
    },
    sendAgentOrder() {
      if (this.form.customerNo === '' || this.form.customerNo == null) {
        this.$message.warning('请填写客户')
        return
      }
      var agentNo = this.form.customerNo
      this.sendLoading = true
      onSendAgentOrderFreqReport(agentNo).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
        this.sendLoading = false
      }).catch(error => {
        this.sendLoading = false
        console.log(error)
      })
    },
    deleteCsStockSettingById(index, row) {
      if (row.id) {
        this.$confirm('此操作将永久删除该, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 1.如果detailId不为空,则先调用后台接口执行删除
          const params = { 'id': row.id }
          deleteCsSetting(params).then(res => {
            if (!res.success) {
              this.$message.error(res.content)
              return
            }
            // 2.删除前端数组数据项
            this.listdata.splice(index, 1)
            this.$message.success('删除成功')
          }).catch(error => {
            console.info(error)
            this.$message.error(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      }
    },
    // 启用停用仓库
    handleUpdateChangeWareStatus(index, row) {
      const statusdata = {
        warehouseCode: row.warehouseCode,
        status: row.status
      }
      updateCsWarehourseStatus(statusdata).then(res => {
        if (!res.success) {
          // 不成功不改状态
          this.warehourselistdata[index].status = row.status === 1 ? 9 : 1
          this.$message.error(res.content)
          return
        }
        this.warehourselistdata[index].status = row.status
        this.$message.success(res.content)
      }).catch(error => {
        console.error(error)
      })
    },
    // 启用停用型号备库
    handleUpdateCsSettingStatus(index, row) {
      const statusdata = {
        agentNo: row.customerNo,
        warehouseCode: row.stockCode,
        modelNo: row.modelNo,
        status: row.status
      }
      updateCsSettingStatus(statusdata).then(res => {
        if (!res.success) {
          // 不成功不改状态
          this.listdata[index].status = row.status === 1 ? 9 : 1
          this.$message.error(res.content)
          return
        }
        this.listdata[index].status = row.status
        this.$message.success(res.content)
      }).catch(error => {
        console.error(error)
      })
    },
    handleSelection(val) {
      this.multipleSelection = val
    },
    stockTypeCodeNameformatter(row) {
      return this.descFormatter(this.stockTypeOptions, row.warehouseType)
    },
    replTypedescformatter(row) {
      return this.descFormatter(this.replTypeOptions, row.replType)
    },

    handleTabClick(tab) {
      console.log(tab.name)
      if (tab.name === 'tabWarehourse') {
        this.searchWarehouseData()
      }
    },
    // 修改子项属性
    editProperty(row, index) {
      // isEditPropertyShow为ture展示输入框
      this.$set(this.listdata[index], 'isEditPropertyShow', true)
    },
    // 保存子项属性
    saveProperty(row, index) {
      updateCsSetting(this.listdata[index]).then(res => {
        if (!res.success) {
          this.$message.error(res.message)
          return
        }
        this.listdata[index].impOrderNo = row.orderNo + '-' + row.itemNo
        this.$set(this.listdata[index], 'isEditPropertyShow', false)
        this.$message.success('保存成功')
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 取消商品属性编辑
    cancelProperty(row, index) {
      if (sessionStorage.getItem('oldPropertValue') !== 'null') {
        this.$set(this.listdata[index], 'propertyValue', sessionStorage.getItem('oldPropertValue'))
      } else {
        this.$set(this.listdata[index], 'propertyValue', '')
      }
      this.$set(this.listdata[index], 'isEditPropertyShow', false)
    },
    querySearchStockCodeAsync(warehouseCode, cb) {
      const warehouseCodeParams = { 'warehouseCode': warehouseCode, 'warehouseType': 'WT' }
      listWarehourseStockCode(warehouseCodeParams).then(result => {
        console.log(result)
        this.stockCodeOptions = result.content
        cb(result.content)
      }).catch(error => {
        console.log(error)
      })
    },
    handleQuerySelect(item) {
      this.form.stockCode = item.warehouseCode
      this.form.customerNo = item.customerNo
    },
    handleQuerySelectWare(item) {
      this.warehourseForm.warehouseCode = item.warehouseCode
      this.warehourseForm.agentNo = item.customerNo
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
  margin-top: 30px;
  padding-right: 10px;
  width: 99%;
}
</style>
