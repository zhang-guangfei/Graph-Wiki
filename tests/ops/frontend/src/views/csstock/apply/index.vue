<template>
  <div class="app-container">
    <!-- <el-tabs v-model="activeTabName" type="card" @tab-click="handleTabClick"> -->
    <!-- <el-tab-pane label="备库计算" name="pane1"> -->
    <div>
      <el-row style="margin-top:10px">
        <el-form
          ref="calcForm"
          :inline="true"
          :model="calcForm"
          size="mini"
          label-width="100px"
        >
          <el-form-item label="客户代码">
            <el-input
              v-model="calcForm.agentNo"
              style="width: 100px"
              clearable
              size="mini"
              placeholder="客户代码"
              disabled
            />
          </el-form-item>

          <el-form-item>
            <el-autocomplete
              v-model.trim="calcForm.stockCodeName"
              :fetch-suggestions="querySearchStockCodeAsync"
              :debounce="0"
              type="text"
              size="small"
              placeholder="请输入库房代码"
              class="search-input-class"
              prefix-icon="el-icon-search"
              round
              highlight-first-item
              select-when-unmatched
              clearable
              @clear="clearAgentInput(data)"
              @select="handleSelect">
              <template slot-scope="{ item }">
                <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
              </template>
            </el-autocomplete>
            <el-button type="primary" size="mini" @click="selectWarehouse()">选择</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              v-permission="['816457']"
              :disabled="!calcForm.stockCodeName"
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="getcalcData"
            >计算备库</el-button
            >
          </el-form-item>
          <el-form-item>
            <el-button
              v-permission="['816457']"
              :disabled="!calcForm.stockCodeName"
              type="primary"
              icon="el-icon-check"
              size="mini"
              @click="createApply">生成订单</el-button>
          </el-form-item>
          <el-form-item>
            <el-button
              v-permission="['910214']"
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="toPreStock">申请查询</el-button>
          </el-form-item>
        </el-form>
      </el-row>
      <el-divider />
      <el-table
        v-loading="listLoading"
        ref="multipleTable"
        :data="calcListdata"
        :cell-style="{ padding: '5px' }"
        border
        stripe
        style="width: 100%"
        element-loading-text="正在加载中..."
        element-loading-spinner="el-icon-loading"
        size="mini"
        @selection-change="handleSelection"
      >
        <!-- 表头字段 -->
        <!-- <el-table-column type="selection" width="55" /> -->

        <el-table-column
          v-loading="listLoading"
          v-for="column in applyTableHeader"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
        />
      </el-table>
      <pagination
        v-show="calcTotal > 5"
        :total="calcTotal"
        :page.sync="calcForm.pageNum"
        :limit.sync="calcForm.pageSize"
        :page-sizes="[20, 50, 100, 200, 500]"
        @pagination="getcalcData()"
      />
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
    </div>

    <el-divider />
    <!-- </el-tab-pane> -->
    <!-- <el-tab-pane label="申请查询" name="pane2" type="border-card"> -->
    <div v-if="false">
      <el-card>
        <el-form
          ref="applyQueryform"
          :inline="true"
          :model="applyQueryform"
          size="mini"
          label-width="100px"
        >
          <el-form-item label="ID">
            <el-input
              v-model="applyQueryform.applyId"
              style="width: 100px"
              placeholder="ID"
              clearable
              size="mini"
            />
          </el-form-item>

          <el-form-item label="申请号">
            <el-input
              v-model="applyQueryform.capplyNo"
              style="width: 150px"
              placeholder="申请号"
              clearable
              size="mini"
            />
          </el-form-item>

          <el-form-item>
            <el-autocomplete
              v-model.trim="applyQueryform.stockCodeName"
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
          </el-form-item>
          <el-form-item label="客户代码">
            <el-input
              v-model="applyQueryform.customerNo"
              style="width: 100px"
              placeholder="客户代码"
              clearable
              size="mini"
            />
          </el-form-item>
          <el-form-item>
            <el-select
              v-model="applyQueryform.status"
              placeholder="状态"
              style="width: 150px"
              clearable
              size="mini"
            >
              <el-option
                v-for="item in applyStatusDesc"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="申请日期">
            <el-date-picker
              v-model="applyQueryform.fromDate"
              type="date"
              size="mini"
              placeholder="开始时间"
              value-format="yyyy-MM-dd"
              style="width: 130px"
            />
            <el-date-picker
              v-model="applyQueryform.toDate"
              type="date"
              size="mini"
              placeholder="结束时间"
              value-format="yyyy-MM-dd"
              style="width: 130px"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="getApplyData"
            >查询</el-button
            >
          </el-form-item>
        </el-form>

        <hr size="5" color="#0066cc" >
      </el-card>
      <div style="margin-top: 10px">
        <el-table
          v-loading="listLoading"
          ref="applyListData"
          :data="applyListData"
          tooltip-effect="dark"
          border
          size="mini"
          style="width: 100%"
          element-loading-text="正在加载中..."
          element-loading-spinner="el-icon-loading"
          stripe
        >
          <el-table-column fixed label="ID">
            <template slot-scope="scope">
              <el-link type="primary" @click="selShowDetail(scope.$index, scope.row)">{{ scope.row.applyId }}</el-link>
            </template>
          </el-table-column>

          <el-table-column fixed prop="capplyNo" label="申请号" />

          <el-table-column fixed prop="customerNo" label="客户代码" />

          <el-table-column fixed prop="userNo" label="用户代码" />

          <el-table-column :formatter="stoctCodeNameformatter" prop="stockCode" label="归属库房" />

          <el-table-column prop="totalQty" label="项数" />

          <el-table-column prop="pplNo" label="PPL号" />

          <el-table-column prop="projectNo" label="项目号" />

          <el-table-column prop="groupCustomerNo" label="客户集团号" />

          <el-table-column prop="createUser" label="创建人" />

          <el-table-column :formatter="createTimeformatter" prop="createTime" label="创建时间" />

          <el-table-column :formatter="applyTimeformatter" prop="applyTime" label="申请时间" />

          <el-table-column :formatter="applyTypeDec" prop="applyType" label="申请类型" />

          <el-table-column :formatter="applyStatusformatter" prop="status" label="状态" />

          <el-table-column :formatter="deptformatter" prop="deptNo" label="营业所名称" />

          <el-table-column prop="remark" label="备注" />
          <!--操作栏 -->
          <el-table-column
            fixed="right"
            label="操作"
            align="center"
            min-width="180">
            <template slot-scope="scope">
              <el-button size="mini" icon="el-icon-view" @click="selShowDetail(scope.$index, scope.row)" />
              <el-button v-if="scope.row.editeBtnApply" :loading="scope.row.loadingBtnApply" round size="mini" icon="el-icon-check" @click="handleSubmitApply(scope.$index, scope.row)"/>
              <el-button v-if="scope.row.editeBtnApply" size="mini" icon="el-icon-delete" @click="removeApply(scope.$index, scope.row)" />

            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="applyTotal > 0"
          :total="applyTotal"
          :page-sizes="[20, 50, 100, 200, 500]"
          :page.sync="applyQueryform.pageNum"
          :limit.sync="applyQueryform.pageSize"
          @pagination="getApplyData"
        />
      </div>
    </div>
    <!-- </el-tab-pane> -->
    <!--申请明细-->
    <!-- <el-tab-pane label="申请明细" name="tabDetail" type="border-card"> -->
    <el-card v-if="false">
      <div>
        <span class="collapse-title-class" style="font-size:13px;margin-left:5%" @click="baseInfoVisible = !baseInfoVisible">
          <i class="el-icon-tickets" style="color:#008080;"/>
          申请信息
          <i :title="baseInfoVisible === false? '展开': '收起'" :class="baseInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
        </span>
      </div>
      <div v-show="baseInfoVisible === true" class="product-base-content-body">

        <el-form label-width="100px" style="width:100%" label-suffix="" size="mini">
          <!--第一行-->
          <el-row type="flex" >
            <el-form-item style="width:100px">
              <span slot="label">
                <span class="span-box">
                  <span>ID</span>
                </span>
              </span>
              <span>{{ detailData.master.applyId }} </span>
            </el-form-item>
            <el-form-item style="width:150px">
              <span slot="label">
                <span class="span-box">
                  <span>申请号</span>
                </span>
              </span>
              <span>{{ detailData.master.capplyNo }} </span>
            </el-form-item>
            <el-form-item style="width:180px" label-width="150px">
              <span slot="label">
                <span class="span-box">
                  <span>客户代码</span>
                </span>
              </span>
              <span>{{ detailData.master.userNo }}</span>
            </el-form-item>
            <el-form-item style="width:180px">
              <span slot="label">
                <span class="span-box">
                  <span>归属库房</span>
                </span>
              </span>
              <span>{{ detailData.master.stockCode }}</span>
            </el-form-item>

          </el-row>
          <el-row type="flex">
            <el-form-item style="width:180px">
              <span slot="label">
                <span class="span-box">
                  <span>用户代码</span>
                </span>
              </span>
              <span>{{ detailData.master.customerNo }}</span>
            </el-form-item>
            <el-form-item style="width:180px">
              <span slot="label">
                <span class="span-box">
                  <span>PPL号</span>
                </span>
              </span>
              <span>{{ detailData.master.pplNo }}</span>
            </el-form-item>
            <el-form-item style="width:180px">
              <span slot="label">
                <span class="span-box">
                  <span>项目号</span>
                </span>
              </span>
              <span>{{ detailData.master.projectNo }}</span>
            </el-form-item>
            <el-form-item style="width:180px">
              <span slot="label">
                <span class="span-box">
                  <span>客户集团号</span>
                </span>
              </span>
              <span>{{ detailData.master.groupCustomerNo }}</span>
            </el-form-item>
          </el-row>
          <!--第二行-->
          <el-row type="flex" >

            <el-form-item style="width:180px">
              <span slot="label">
                <span class="span-box">
                  <span>申请类型</span>
                </span>
              </span>
              <span>{{ applyTypeDec2( detailData.master.applyType) }}</span>
            </el-form-item>

            <el-form-item style="width:180px">
              <span slot="label">
                <span class="span-box">
                  <span>状态</span>
                </span>
              </span>
              <span>{{ applyStatusDec( detailData.master.status) }}</span>
            </el-form-item>

            <el-form-item style="width:120px">
              <span slot="label">
                <span class="span-box">
                  <span>项数</span>
                </span>
              </span>
              <span>{{ detailData.master.totalQty }}</span>
            </el-form-item>
            <el-form-item style="width:180px" >
              <span slot="label">
                <span class="span-box">
                  <span>订单号</span>
                </span>
              </span>
              <span> {{ detailData.master.orderNo }}</span>
            </el-form-item>
          </el-row>
          <!--第三行-->
          <el-row type="flex" >
            <el-form-item style="width:180px" >
              <span slot="label">
                <span class="span-box">
                  <span>营业所</span>
                </span>
              </span>
              <span> {{ detailData.master.deptNo }}</span>
            </el-form-item>
            <el-form-item style="width:200px">
              <span slot="label">
                <span class="span-box">
                  <span>创建日期</span>
                </span>
              </span>
              <span>{{ detailData.master.createUser }} {{ momentformatter(detailData.master.createTime ) }}</span>
            </el-form-item>
            <el-form-item style="width:200px">
              <span slot="label">
                <span class="span-box">
                  <span>更新日期</span>
                </span>
              </span>
              <span>{{ detailData.master.updateUser }} {{ momentformatter(detailData.master.updateTime) }}</span>
            </el-form-item>
          </el-row>
          <!--第四行-->
          <el-row type="flex" >
            <el-form-item >
              <span slot="label" class="span-box">
                <span>
                  <span>备注</span>
                </span>
              </span>
              <span>{{ detailData.master.remark }}</span>
            </el-form-item>
          </el-row>

        </el-form>
      </div>
      <hr size="5" color="#0066cc" >
      <div v-show="baseInfoVisible === true" class="product-base-content-body">
        <el-button v-if="baseInfoVisible" v-show="detailData.master.status==1" size="mini" icon="el-icon-plus" @click="handleAddDetailRow()">新增行</el-button>
        <el-table :data="detailData.detail" :row-style="{height: '0'}" border mini style="width: 100%;font-size:10px" stripe>
          <el-table-column label="序号" width="70" align="center">
            <template slot-scope="scope">
              {{ scope.$index+1 }}
            </template>
          </el-table-column>
          <el-table-column property="applyId" align="center" label="申请号" width="80"/>
          <el-table-column property="modelNo" align="center" label="型号"/>
          <el-table-column property="quantity" align="center" label="数量" >
            <template slot-scope="scope">
              <span v-show="scope.row.isEditable" :prop="'detailData.' + scope.$index + '.quantity'" label-width="0">
                <el-input v-model="scope.row.quantity" placeholder="数量" size="mini" maxlength="5" onkeyup="value=value.replace(/[^\d]/g,'')"/>
              </span>
              <span v-show="!scope.row.isEditable">{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column property="orderNo" align="center" label="订单号"/>
          <el-table-column property="remark" align="center" label="备注" >
            <template slot-scope="scope">
              <span v-show="scope.row.isEditable" :prop="'detailData.' + scope.$index + '.remark'" label-width="0">
                <el-input v-model="scope.row.remark" placeholder="备注" size="mini"/>
              </span>
              <span v-show="!scope.row.isEditable">{{ scope.row.remark }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="createTimeformatter" property="createTime" align="center" label="创建时间" width="120"/>
          <el-table-column label="操作" align="center" width="140">
            <template slot-scope="scope">
              <el-button v-if="scope.row.isEditable && scope.row.status==1" size="mini" icon="el-icon-check" @click="handleUpdateDetail(scope.row)"/>
              <el-button v-if="!scope.row.isEditable && scope.row.status==1" size="mini" icon="el-icon-edit" @click="showEditable(scope.row)"/>
              <el-button v-if="scope.row.status==1" size="mini" icon="el-icon-delete" @click="removeDetail(scope.$index, scope.row)" />
            </template>
          </el-table-column>
        </el-table>
      </div>
      <pagination
        v-show="applyDetailForm.total > 0"
        :total="applyDetailForm.total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="applyDetailForm.pageNum"
        :limit.sync="applyDetailForm.pageSize"
        @pagination="showDetail"
      />
    </el-card>
    <div style="margin-top: 0px"/>
    <!-- </el-tab-pane> -->
    <!--提交委托在库申请dialog-->
    <el-dialog
      :visible.sync="dialogAddDetailVisible"
      :close-on-click-modal="false"
      title="新增行"
      width="420px"
    >
      <el-form ref="editeDetailFrom" :model="editeDetailFrom">

        <el-row>
          <el-col :span="24">
            <el-form-item label-width="100px" label="申请号">
              <el-input
                v-model="editeDetailFrom.applyId"
                disabled
                style="width:100px"
                type="text"
                size="mini"
              />
            </el-form-item>
          </el-col>

        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label-width="100px" label="型号">
              <el-input
                v-model="editeDetailFrom.modelNo"
                style="width:200px"
                type="text"
                size="mini"
                placeholder="请输入型号"
              />

          </el-form-item></el-col>
        </el-row>

        <el-row>
          <el-col :span="24">
            <el-form-item label-width="100px" label="数量" >
              <el-input
                v-model="editeDetailFrom.quantity"
                style="width:100px"
                type="text"
                size="mini"
                placeholder="请输入数量"
                onkeyup="value=value.replace(/[^\d]/g,'')"
                maxlength="5"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label-width="100px" label="备注" >
              <el-input
                v-model="editeDetailFrom.remark"
                style="width:200px"
                type="text"
                size="mini"
                placeholder="请输入备注"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button style="float:left" @click="dialogAddDetailVisible = false;">取消</el-button>
        <el-button
          type="primary"
          @click="handleAddDetail()"
        >保存</el-button>
      </div>
    </el-dialog>
    <!-- </el-tabs> -->
  </div>

</template>

<script>
import Pagination from '@/components/Pagination'
import { listWarehourseStockCode, listApply, listReplDetail, createApply, listApplyDetail, removeDetail, removeApply, confirmApply, addDetail, updateDetail } from '@/api/stock/csStockApply.js'
import { getDictDataByPid, listWarehouse } from '@/api/common/dict'
import { findDepartments } from '@/api/common/department'
import moment from 'moment'
export default {
  name: 'CSStockApply',
  components: { Pagination },
  data() {
    return {
      activeTabName: 'pane1',
      dialogdetialVisible: false,
      dialogAddDetailVisible: false,
      dialogFormVisible: false,
      // 申请表头
      applyTableHeader: [
        {
          label: '客户代码',
          prop: 'customerNo'
        },
        {
          label: '归属库房',
          prop: 'stockCode',
          formatter: this.stoctCodeNameformatter
        },
        {
          label: '型号',
          prop: 'modelNo'
        },
        {
          label: '单位数量',
          prop: 'initUnitQty'
        },
        {
          label: '设置备库数',
          prop: 'initStockQty'
        },
        {
          label: '在库可用数',
          prop: 'onhandCanUseQty'
        },
        {
          label: '申请中数量',
          prop: 'applyQty'
        },
        {
          label: '在途数',
          prop: 'transQty'
        },
        {
          label: '需补货数量',
          prop: 'replQty'
        },
        {
          label: '补货后数量',
          prop: 'totalQty'
        },
        {
          label: 'E价',
          prop: 'eprice'
        },
        {
          label: '备注',
          prop: 'remark'
        },
        {
          label: '操作',
          prop: 'createTime',
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '无' : moment(cellValue).format('YYYY-MM-DD')
          }
        }
      ],
      // 表格数据
      calcListData: [],
      calcForm: {
        agentNo: '',
        warehouseCode: '',
        stockCodeName: '',
        pageNum: 0,
        pageSize: 20
      },
      warehouseForm: {
        warehouseCode: '',
        warehouseName: ''
      },
      warehouseData: [],
      applyListData: [],
      applyQueryform: {
        applyId: '',
        customerNo: '',
        stockCodeName: '',
        stockCode: '',
        fromDate: '',
        capplyNo: '',
        toDate: '',
        status: '',
        pageNum: 0,
        pageSize: 20
      },
      applyDetailForm: {
        applyId: '',
        pageNum: 0,
        pageSize: 20,
        total: 0
      },
      calcTotal: 0,
      applyTotal: 0,
      approveApplyId: '',
      listLoading: false,
      detailData: {
        master: '',
        detail: []
      },
      editeDetailFrom: {
        applyId: '',
        id: '',
        modelNo: '',
        quantity: '',
        remark: ''

      },
      applyStatusDesc: [],
      stockCodeOptions: [],
      deptInfos: [], // 营业所部门信息
      // 寄售申请状态
      applyTypeClassCode: '2026',
      applyTypeCode: '2020',
      applyTypeData: [],
      baseInfoVisible: true
    }
  },
  watch: {
    'calcForm.warehouseCode': {
      handler(val, oldV) {
        if (val) {
          this.calcForm.warehouseCode = val.toUpperCase()
        }
      },
      deep: true
    }
  },
  created() {
    this.initstatusdesc()
    this.initstDeptInfo()
  },
  methods: {
    // 申请状态初始化
    initstatusdesc() {
      getDictDataByPid(this.applyTypeClassCode).then(result => {
        console.log(result)
        this.applyStatusDesc = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.applyTypeCode).then(result => {
        console.log(result)
        this.applyTypeData = result.content
      }).catch(error => {
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
    // 获取计算数据
    getcalcData() {
      this.listLoading = true
      this.calcForm.warehouseCode = this.calcForm.stockCodeName.substring(0, this.calcForm.stockCodeName.lastIndexOf('-'))

      listReplDetail(this.calcForm).then(result => {
        console.log(result)
        this.calcListdata = result.content
        this.calcTotal = result.content.length
        console.log('返回：' + result.content.length)
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 查询申请清单
    getApplyData() {
      this.listLoading = true
      listApply(this.applyQueryform).then(result => {
        this.applyListData = result.content.list
        // 增加字段标志
        this.applyListData.forEach(item => {
          this.$set(item, 'loadingBtnApply', false)
          if (item.status === 1 || item.status === 3) {
            this.$set(item, 'editeBtnApply', true)
          } else {
            this.$set(item, 'editeBtnApply', false)
          }
        })
        this.applyTotal = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    // 提交申请号
    handleSubmitApply(index, row) {
      row.loadingBtnApply = true
      this.$confirm('是否继续提交生成订单?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const confirmParams = { 'applyId': row.applyId, 'approvePass': true, 'approveText': '' }
        confirmApply(confirmParams)
          .then(res => {
            row.loadingBtnApply = false
            if (res.success) {
              // 隐藏提交按钮\删除
              row.editeBtnApply = false
              this.$message.success(res.content)
            } else {
              this.$message.error(res.message)
            }
          }).catch(error => {
            this.$message.error(error + '请稍后再常试')
          })
      }).catch(() => {
        row.loadingBtnApply = false
        this.$message({
          type: 'info',
          message: '取消操作'
        })
      })
    },
    toPreStock() {
      this.$router.push({
        path: '/stock/prestock/index'
      })
    },
    // 生成申请号
    createApply() {
      if (this.calcListdata === undefined || this.calcListdata == null || this.calcListdata.length <= 0) {
        this.$message.error('无补货数据')
        return
      }
      const confirmParams = { 'agentNo': this.calcForm.agentNo, 'warehouseCode': this.calcForm.warehouseCode }
      this.$confirm('此操作生成申请数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.listLoading = true
        // 生成申请号
        createApply(confirmParams).then(result => {
          if (result.success) {
            this.calcListdata = null
            this.calcForm.agentNo = ''
            this.calcForm.stockCodeName = ''
            this.$message.success(result.content)
          } else {
            this.$message.error(result.message)
          }
          this.listLoading = false
        }).catch(error => {
          this.listLoading = false
          console.log(error)
        })
        console.log(this.multipleSelection)
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消操作'
        })
      })
    },
    handleTabClick(tab, event) {
      console.log(tab, event)
    },
    handleSelection(val) {
      // this.multipleSelection = val
    },
    selShowDetail(index, row) {
      if (row.applyId) {
        console.log('ok')
        this.applyDetailForm.applyId = row.applyId
        this.detailData.master = row
        this.showDetail()
      }
    },
    selectWarehouse(val) {
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
      this.calcForm.agentNo = row.warehouseCode.substring(1, 6)
      this.calcForm.stockCodeName = row.warehouseCode + '-' + row.warehouseName
      this.dialogFormVisible = false
    },
    // 显示明细
    showDetail() {
      this.listLoading = true
      listApplyDetail(this.applyDetailForm).then(result => {
        this.detailData.detail = result.content.list
        // 增加字段标志
        this.detailData.detail.forEach(item => {
          this.$set(item, 'isEditable', false)
        })
        this.applyDetailForm.total = result.content.total
        this.activeTabName = 'tabDetail'
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
      this.dialogdetialVisible = true
    },
    removeApply(index, row) {
      if (row.applyId) {
        this.$confirm('此操作将删除该申请, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          const params = { 'applyId': row.applyId }
          removeApply(params).then(res => {
            if (!res.success) {
              this.$message.error(res.message)
              return
            }
            this.applyListData.splice(index, 1)
            this.$message.success('删除成功')
          }).catch(error => {
            console.info(error)
            this.$message.error(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消操作'
          })
        })
      }
    },
    // 新增行
    handleAddDetailRow() {
      this.editeDetailFrom.applyId = this.detailData.master.applyId
      this.editeDetailFrom.modelNo = ''
      this.editeDetailFrom.quantity = ''
      this.editeDetailFrom.remark = ''
      this.dialogAddDetailVisible = true
    },
    // 保存新增
    handleAddDetail() {
      console.log(this.editeDetailFrom)
      addDetail(this.editeDetailFrom)
        .then(res => {
          if (res.success) {
            this.dialogAddDetailVisible = false
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    removeDetail(index, row) {
      if (row.id) {
        this.$confirm('此操作将删除行, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          // 1.如果detailId不为空,则先调用后台接口执行删除
          const params = { 'applyId': row.applyId, 'modelNo': row.modelNo }
          removeDetail(params).then(res => {
            if (!res.success) {
              this.$message.error(res.message)
              return
            }
            // 2.删除前端数组数据项
            this.detailData.detail.splice(index, 1)
            this.$message.success('删除成功')
          }).catch(error => {
            console.info(error)
            this.$message.error(error.message)
          })
        }).catch(() => {
          this.$message({
            type: 'info',
            message: '取消操作'
          })
        })
      }
    },
    showEditable(row) {
      console.log(row)
      row.isEditable = true
    },

    handleUpdateDetail(row) {
      updateDetail(row)
        .then(res => {
          if (res.success) {
            this.$message.success(res.content)
            row.isEditable = false
          } else {
            this.$message.error(res.message)
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
        if (item.code === id.toString()) {
          return item.codeName
        }
      }
      return id
    },
    createTimeformatter(row) {
      return this.dayjs(row.createTime).format('YYYY-MM-DD HH:mm')
    },
    applyTimeformatter(row) {
      if (row.applyTime != null) {
        return this.dayjs(row.applyTime).format('YYYY-MM-DD HH:mm')
      }
    },
    momentformatter(date) { // 日期格式化
      if (date != null) {
        return this.dayjs(date).format('YYYY-MM-DD')
      }
      return ''
    },
    applyStatusformatter(row) {
      if (row != null) {
        return this.descFormatter(this.applyStatusDesc, row.status)
      }
    },
    applyStatusDec(status) {
      if (status != null) {
        return this.descFormatter(this.applyStatusDesc, status)
      }
    },
    applyTypeDec2(type) {
      if (type != null) {
        return this.descFormatter(this.applyTypeData, type)
      }
    },
    applyTypeDec(row) {
      if (row != null && row.applyType != null) {
        return this.descFormatter(this.applyTypeData, row.applyType)
      }
    },
    stoctCodeNameformatter(row) {
      return this.descFormatter(this.stockCodeOptions, row.stockCode)
    },
    deptformatter(row) {
      if (row.deptNo != null) {
        return this.descFormatter(this.deptInfos, row.deptNo)
      }
    },
    handleSelect(item) {
      this.calcForm.stockCodeName = item.warehouseCode + '-' + item.warehouseName
      this.calcForm.agentNo = item.customerNo
      this.calcForm.warehouseCode = item.warehouseCode
    },
    handleQuerySelect(item) {
      this.applyQueryform.stockCodeName = item.warehouseCode + '-' + item.warehouseName
      this.applyQueryform.customerNo = item.customerNo
    },
    clearAgentInput(data) { // 清空客户代码文本
      this.calcForm.agentNo = ''
      this.calcForm.warehouseCode = ''
    }

  }
}
</script>
<style scoped>
.line {
  text-align: center;
  margin-left: 5%;
}
</style>
