<template>
  <div class="app-container">
    <el-tabs v-model="activeTabName" type="card" @tab-click="handleTabClick">
      <!--退货计算-->
      <el-tab-pane label="退货计算" name="pane1">
        <div>
          <el-row>
            <el-form
              ref="calcForm"
              :inline="true"
              :model="calcForm"
              size="mini"
            >
              <el-form-item>
                <el-button
                  v-permission="['105149']"
                  :loading="jsLoading"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="listApplyMaster(1)"
                >计算全部</el-button
                >
              </el-form-item>
              <!-- <el-form-item label="营业所">
                <el-input
                  v-model="calcForm.deptNo"
                  style="width: 100px"
                  clearable
                  size="small"
                  placeholder="营业所"
                />
              </el-form-item> -->
              <el-form-item label="营业所">
                <el-cascader
                  ref="cascaderHandle"
                  :options="deptNoOptions"
                  :props="{ value: 'id', label: 'name',checkStrictly: true }"
                  :show-all-levels="false"
                  placeholder="选择营业所"
                  filterable
                  clearable
                  style="width: 150px"
                  size="mini"
                  @change="deptChange" />
              </el-form-item>
              <el-form-item label="客户代码">
                <el-input
                  v-model="calcForm.agentNo"
                  style="width: 100px"
                  clearable
                  size="small"
                  placeholder="客户代码"
                  disabled
                />
              </el-form-item>

              <el-form-item>
                <!-- <el-autocomplete
                  v-model.trim="calcForm.warehouseCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  style="width: 200px"
                  type="text"
                  size="small"
                  placeholder="请输入库房代码"
                  class="search-input-class"
                  prefix-icon="el-icon-search"
                  round
                  highlight-first-item
                  select-when-unmatched
                  clearable
                  @clear="clearAgentInput(1)"
                  @select="handleSelect">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete> -->

                <el-autocomplete
                  v-model.trim="calcForm.warehouseCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  :popper-append-to-body="false"
                  popper-class="el-autocomplete-suggestion"
                  highlight-first-item
                  style="width: 200px"
                  type="text"
                  size="small"
                  placeholder="请输入库房代码"
                  class="select"
                  prefix-icon="el-icon-search"
                  clearable
                  @clear="clearAgentInput(1)"
                  @select="handleSelect">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['826680']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="listApplyMaster(0)">查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['340533']"
                  :loading="listLoading"
                  :disabled="createShow"
                  type="primary"
                  icon="el-icon-check"
                  size="mini"
                  @click="createApply">生成退货</el-button>
              </el-form-item>
            </el-form>
          </el-row>
          <el-table
            ref="calcMasterList"
            :data="calcMasterList"
            tooltip-effect="dark"
            border
            max-height="600"
            size="mini"
            style="width: 100%"
            element-loading-text="正在加载中..."
            element-loading-spinner="el-icon-loading"
            stripe
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55"/>
            <el-table-column :formatter="deptformatter" prop="deptNo" label="营业所" />
            <el-table-column prop="agentNo" label="客户代码" />
            <el-table-column prop="warehouseCode" label="仓库代码" />
            <el-table-column prop="models" label="型号数" />
            <el-table-column prop="returnQty" label="退货数量	" />
            <el-table-column prop="overTimeQty" label="逾期数量" />
            <el-table-column label="操作">
              <template slot-scope="scope">
                <el-button v-permission="['826680']" type="primary" icon="el-icon-view" plain size="mini" @click="lookDetail(scope.row)">查看</el-button>
              </template>
            </el-table-column>

          </el-table>
          <pagination
            v-show="calcForm.total > 0"
            :total="calcForm.total"
            :page-sizes="[20, 50, 100, 200, 500]"
            :page.sync="calcForm.pageNum"
            :limit.sync="calcForm.pageSize"
            style="margin-top: -20px"
            @pagination="listApplyMaster(0)"
          />
        </div>
        <!-- <el-divider class="divider"/> -->

      </el-tab-pane>
      <el-tab-pane label="计算明细" name="pane2">
        <div>
          <el-row>
            <el-form
              ref="calcForm"
              :inline="true"
              :model="calcForm"
              size="mini"
            >
              <el-form-item label="客户代码">
                <el-input
                  v-model="calcDetailForm.agentNo"
                  style="width: 100px"
                  clearable
                  size="small"
                  placeholder="客户代码"
                  disabled
                />
              </el-form-item>

              <el-form-item>
                <!-- <el-autocomplete
                  v-model.trim="calcDetailForm.warehouseCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  style="width: 200px"
                  type="text"
                  size="small"
                  placeholder="请输入仓库代码"
                  class="search-input-class"
                  prefix-icon="el-icon-search"
                  round
                  highlight-first-item
                  select-when-unmatched
                  clearable
                  @clear="clearAgentInput(2)"
                  @select="handleSelect2">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete> -->
                <el-autocomplete
                  v-model.trim="calcDetailForm.warehouseCode"
                  :fetch-suggestions="querySearchStockCodeAsync"
                  :debounce="0"
                  :popper-append-to-body="false"
                  popper-class="el-autocomplete-suggestion"
                  highlight-first-item
                  style="width: 200px"
                  type="text"
                  size="small"
                  placeholder="请输入库房代码"
                  class="select"
                  prefix-icon="el-icon-search"
                  clearable
                  @clear="clearAgentInput(2)"
                  @select="handleSelect2">
                  <template slot-scope="{ item }">
                    <div class="name">{{ item.warehouseCode }}-{{ item.warehouseName }}</div>
                  </template>
                </el-autocomplete>
              </el-form-item>
              <el-form-item label="型号">
                <el-input
                  v-model="calcDetailForm.modelNo"
                  style="width: 150px"
                  clearable
                  size="small"
                  placeholder="型号"
                />
              </el-form-item>
              <el-form-item>
                <el-select
                  v-model="calcDetailForm.applyType"
                  clearable
                  size="small"
                  style="width: 120px"
                  placeholder="提案方"
                >
                  <el-option
                    v-for="item in applyTypeData"
                    :key="item.code"
                    :value="item.code"
                    :label="item.codeName"/>
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-checkbox v-model="calcDetailForm.overTimeQty" border size="small">
                  <span style="font-size: 14px;font-weight: 400;">
                    仅查逾期数大于0
                  </span>
                </el-checkbox>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['826680']"
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="getCalcData()">查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['105149']"
                  :loading="exportLoading"
                  type="success"
                  icon="el-icon-download"
                  size="mini"
                  @click="exportReturnData()">导出</el-button>
              </el-form-item>
              <el-form-item>
                <el-button
                  v-permission="['340533']"
                  :loading="listLoading"
                  type="primary"
                  icon="el-icon-check"
                  size="mini"
                  @click="createApply(1)">生成退货</el-button>
              </el-form-item>
            </el-form>
          </el-row>

          <el-table
            v-loading="listLoading"
            ref="calcList"
            :data="calcList"
            tooltip-effect="dark"
            border
            max-height="600"
            size="mini"
            style="width: 100%"
            element-loading-text="正在加载中..."
            element-loading-spinner="el-icon-loading"
            stripe
          >
            <el-table-column :formatter="deptformatter" prop="deptNo" label="营业所" min-width="120"/>
            <el-table-column prop="agentNo" label="客户代码" />
            <el-table-column :formatter="applyTypeFormatter" prop="applyType" label="提案方" />
            <el-table-column prop="modelNo" label="型号" min-width="120"/>
            <el-table-column prop="qtyOnhand" label="在库数" />
            <el-table-column prop="qtyprepare" label="被预约数" />
            <el-table-column prop="returningQty" label="退货中数	" />
            <el-table-column prop="overTimeQty" label="逾期数" />
            <!-- <el-table-column prop="returnQty" label="可退货数量" /> -->
            <el-table-column
              prop="returnQty"
              label="退货数量">
              <template slot-scope="scope">
                <span v-show="scope.row.isEditable" :prop="'calcList.'+scope.$index + '.returnQty'">
                  <el-input v-model="scope.row.returnQty" placeholder="数量" size="mini"/>
                </span>
                <span v-show="!scope.row.isEditable">{{ scope.row.returnQty }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="lastDataformatter" prop="lastImpDate" label="最后入库日期" min-width="110"/>
            <el-table-column :formatter="minShipDateformatter" prop="minShipDate" label="逾期日期" min-width="90"/>
            <el-table-column prop="overDay" label="在库天数" />
            <el-table-column prop="orders" label="接单频率" />
            <el-table-column prop="customers" label="客户数" />
            <el-table-column prop="eprice" label="E价" />
            <el-table-column :formatter="binformatter" prop="isBinModel" label="BIN型号" />
            <el-table-column fixed="right" label="操作">
              <template slot-scope="scope">
                <el-button v-permission="['105149']" v-show="scope.row.isEditable" type="success" size="mini" @click="updateReturnQty(scope.row)">确认</el-button>
                <el-button v-permission="['105149']" v-show="!scope.row.isEditable" type="primary" size="mini" @click="showEditable(scope.row)">修改</el-button>
              </template>
            </el-table-column>

          </el-table>
          <pagination
            v-show="calcDetailForm.total > 0"
            :total="calcDetailForm.total"
            :page-sizes="[20, 50, 100, 200, 500]"
            :page.sync="calcDetailForm.pageNum"
            :limit.sync="calcDetailForm.pageSize"
            style="margin-top: -20px"
            @pagination="getCalcData()"
          />
        </div>
      </el-tab-pane>
      <!--退货申请-->
      <el-tab-pane label="退货申请" name="paneApply">
        <div>

          <el-card>
            <el-form
              ref="applyQueryform"
              :inline="true"
              :model="applyQueryform"
              size="mini"
              label-width="100px"
            >
              <el-form-item label="申请号">
                <el-input
                  v-model="applyQueryform.applyId"
                  style="width: 100px"
                  placeholder="申请号"
                  clearable
                  size="mini"
                />
              </el-form-item>

              <el-form-item>
                <!-- add by 伍家闻 2022/11/10 bug 8626 -->
                <el-autocomplete
                  v-model.trim="applyQueryform.stockCode"
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
              <!-- add by 伍家闻 2022/11/10 bug 8626 -->
              <el-form-item label="客户代码">
                <el-input
                  v-model="applyQueryform.agentNo"
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
                  v-permission="['826680']"
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
              <el-table-column fixed prop="applyId" label="申请号" />
              <el-table-column fixed prop="agentNo" label="客户代码" />
              <el-table-column :formatter="warehouseNameformatter" prop="warehouseCode" label="归属库房" />
              <el-table-column prop="totalQty" label="项数" />
              <el-table-column prop="modelQty" label="退回总数" />
              <el-table-column prop="createUser" label="创建人" />
              <el-table-column :formatter="createTimeformatter" prop="createTime" label="创建时间" />
              <el-table-column :formatter="applyTimeformatter" prop="applyTime" label="申请时间" />
              <el-table-column :formatter="applyStatusformatter" prop="status" label="状态" />
              <el-table-column prop="toWareouseCode" label="收货仓库" />
              <el-table-column :formatter="deptformatter" prop="deptNo" label="营业所名称" />
              <el-table-column prop="remark" label="备注" />
              <!--操作栏 -->
              <el-table-column
                fixed="right"
                label="操作"
                align="center"
                min-width="180">
                <template slot-scope="scope">
                  <el-button v-permission="['340533']" v-if="scope.row.status == 2" size="mini" icon="el-icon-check" @click="createReturnOrder(scope.$index, scope.row)" />
                  <el-button v-permission="['105149']" size="mini" icon="el-icon-view" @click="selShowDetail(scope.$index, scope.row)" />
                  <el-button v-permission="['105149']" v-if="scope.row.editeBtnApply" :loading="scope.row.loadingBtnApply" size="mini" icon="el-icon-printer" @click="handlePrintApply(scope.row)"/>
                  <el-button v-permission="['105149']" v-if="scope.row.editeBtnApply" size="mini" icon="el-icon-delete" @click="removeApply(scope.$index, scope.row)" />
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

          <el-divider />
      </div></el-tab-pane>

      <!--申请明细-->
      <el-tab-pane label="申请明细" name="tabDetail" type="border-card">
        <el-card>
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
                <el-form-item style="width:150px">
                  <span slot="label">
                    <span class="span-box">
                      <span>申请号</span>
                    </span>
                  </span>
                  <span>{{ detailData.master.applyId }} </span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>客户代码</span>
                    </span>
                  </span>
                  <span>{{ detailData.master.agentNo }}</span>
                </el-form-item>
                <el-form-item style="width:180px">
                  <span slot="label">
                    <span class="span-box">
                      <span>归属库房</span>
                    </span>
                  </span>
                  <span>{{ detailData.master.warehouseCode }}</span>
                </el-form-item>

              </el-row>
              <!--第二行-->
              <el-row type="flex" >

                <el-form-item style="width:200px">
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
                <el-form-item style="width:200px" >
                  <span slot="label">
                    <span class="span-box">
                      <span>可退总数</span>
                    </span>
                  </span>
                  <span> {{ detailData.master.modelQty }}</span>
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
                <el-form-item style="width:220px">
                  <span slot="label">
                    <span class="span-box">
                      <span>创建日期</span>
                    </span>
                  </span>
                  <span>{{ detailData.master.createUser }} {{ momentformatter(detailData.master.createTime ) }}</span>
                </el-form-item>
                <el-form-item style="width:220px">
                  <span slot="label">
                    <span class="span-box">
                      <span>更新日期</span>
                    </span>
                  </span>
                  <span>{{ detailData.master.updateUser }} {{ momentformatter(detailData.master.updateTime) }}</span>
                </el-form-item>
              </el-row>

            </el-form>
          </div>
          <hr size="5" color="#0066cc" >
          <div v-show="baseInfoVisible === true" class="product-base-content-body">

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
              <el-table-column property="createUser" align="center" label="创建人" />
              <el-table-column :formatter="createTimeformatter" property="createTime" align="center" label="创建时间" width="120"/>

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

      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
import Pagination from '@/components/Pagination'
import { listApply, listDetail, createApply, listApplyMaster, pageListCalcReturn, printReturnApply, updateReturnQty, confirmCsReturnApply, deleteCsReturnApply, exportReturnData } from '@/api/stock/csReturn.js'
import { listWarehourseStockCode } from '@/api/stock/csStockApply.js'
import { findDeptsToTree } from '@/api/organ'
import { getDictDataByPid } from '@/api/common/dict'
import { findDepartments } from '@/api/common/department'

export default {
  name: 'CSStockReturn',
  components: { Pagination },
  data() {
    return {
      activeTabName: 'pane1',
      dialogdetialVisible: false,
      dialogAddDetailVisible: false,
      exportLoading: false,
      createShow: true,
      // 表格数据
      calcList: [],
      calcMasterList: [],
      deptNoOptions: [],
      calcForm: {
        agentNo: '',
        warehouseCode: '',
        deptNo: '',
        calcType: 0,
        total: 0,
        pageNum: 1,
        pageSize: 20
      },
      calcDetailForm: {
        agentNo: '',
        warehouseCode: '',
        modelNo: '',
        applyType: '',
        overTimeQty: false,
        total: 0,
        pageNum: 1,
        pageSize: 20
      },
      applyListData: [],
      applyQueryform: {
        applyId: '',
        agentNo: '',
        stockCodeName: '',
        stockCode: '',
        fromDate: '',
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
      jsLoading: false,
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
      returnData: [],
      deptInfos: [], // 营业所部门信息
      // 寄售退货状态
      applyTypeClassCode: '2035',
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
    this.listApplyMaster()
    this.findDeptNos()
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
    listApplyMaster(val) {
      if (val === 1) {
        this.calcForm.calcType = 1
        this.jsLoading = true
      } else {
        this.calcForm.calcType = 0
      }
      listApplyMaster(this.calcForm).then(res => {
        this.calcMasterList = res.content.list
        this.calcForm.total = res.content.total
        this.jsLoading = false
        if (val === 1 && res.success) {
          this.$message.success('计算完成')
        }
      }).catch(error => {
        this.jsLoading = false
        console.log(error)
      })
    },
    // 获取计算数据
    getCalcData() {
      pageListCalcReturn(this.calcDetailForm).then(result => {
        this.calcList = result.content.list
        this.calcDetailForm.total = result.content.total
        this.calcList.forEach(res => {
          this.$set(res, 'isEditable', false)
        })
      }).catch(error => {
        console.log(error)
      })
    },
    createReturnOrder(index, row) {
      this.$confirm('此操作生成退货订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        confirmCsReturnApply(row.applyId).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.getApplyData()
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消操作'
        })
      })
      this.getApplyData()
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      // if (val.length > 1) {
      //   this.$refs.calcMasterList.clearSelection()
      //   this.$refs.calcMasterList.toggleRowSelection(val.pop())
      // }
      if (val.length > 0) {
        this.createShow = false
      } else {
        this.createShow = true
      }
      // this.$nextTick(() => {
      //   this.calcForm.agentNo = this.$refs.calcMasterList.selection[0].agentNo
      //   this.calcForm.warehouseCode = this.$refs.calcMasterList.selection[0].warehouseCode
      // })
    },
    lookDetail(row) {
      this.activeTabName = 'pane2'
      this.calcDetailForm.warehouseCode = row.warehouseCode
      this.calcDetailForm.agentNo = row.agentNo
      this.getCalcData()
    },
    removeApply(index, row) {
      this.$confirm('此操作将删除申请, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteCsReturnApply(row.applyId).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.getApplyData()
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          console.log(error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消操作'
        })
      })
    },
    querySearchStockCodeAsync(warehouseCode, cb) {
      if (warehouseCode == null) {
        warehouseCode = ''
      }
      const warehouseCodeParams = { 'warehouseCode': warehouseCode, 'warehouseType': 'WT' }
      listWarehourseStockCode(warehouseCodeParams).then(result => {
        console.log(result)
        this.stockCodeOptions = result.content
        cb(result.content)
      }).catch(error => {
        console.log(error)
      })
    },
    handleSelect(item) {
      this.calcForm.agentNo = item.customerNo
      this.calcForm.warehouseCode = item.warehouseCode
    },
    handleSelect2(item) {
      this.calcDetailForm.agentNo = item.customerNo
      this.calcDetailForm.warehouseCode = item.warehouseCode
    },
    // 查询申请清单
    getApplyData() {
      this.listLoading = true
      listApply(this.applyQueryform).then(result => {
        this.applyListData = result.content.list
        // 增加字段标志
        this.applyListData.forEach(item => {
          this.$set(item, 'loadingBtnApply', false)
          if (item.status === 2) {
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
    exportReturnData() {
      this.exportLoading = true
      this.$message.success('开始导出，请耐心等待')
      exportReturnData().then(res => {
        const fileName = '退货申请.xlsx'
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
        console.log(error)
        this.exportLoading = false
      })
    },
    clearAgentInput(val) {
      if (val === 1) {
        this.calcForm.agentNo = ''
        this.calcForm.warehouseCode = ''
      }
      if (val === 2) {
        this.calcDetailForm.agentNo = ''
        this.calcDetailForm.warehouseCode = ''
      }
    },
    warehouseNameformatter(row) {
      if (row.warehouseCode != null) {
        return this.descFormatter(this.stockCodeOptions, row.warehouseCode)
      }
    },
    deptformatter(row) {
      if (row.deptNo != null) {
        return this.descFormatter(this.deptInfos, row.deptNo)
      }
    },
    applyTypeFormatter(row) {
      if (row.applyType != null) {
        return this.descFormatter(this.applyTypeData, row.applyType)
      }
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
    minShipDateformatter(row) {
      if (row.minShipDate != null) {
        return this.dayjs(row.minShipDate).format('YYYY-MM-DD')
      }
    },
    lastDataformatter(row) {
      if (row.lastImpDate != null) {
        return this.dayjs(row.lastImpDate).format('YYYY-MM-DD HH:mm')
      }
    },
    binformatter(row) {
      if (row.isBinModel === 1) {
        return '是'
      } else {
        return '否'
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
    deptChange(value) {
      if (value.length === 0) {
        this.calcForm.deptNo = ''
        return
      }
      this.calcForm.deptNo = value[value.length - 1]
    },
    momentformatter(date) { // 日期格式化
      if (date != null) {
        return this.dayjs(date).format('YYYY-MM-DD')
      }
    },
    handleQuerySelect(item) {
      this.applyQueryform.stockCodeName = item.warehouseCode + '-' + item.warehouseName
      // add by 伍家闻 2022/11/10 bug 8626
      this.applyQueryform.stockCode = item.warehouseCode
    },
    handleTabClick(tab, event) {
      console.log(tab, event)
    },
    updateReturnQty(row) {
      row.isEditable = false
      updateReturnQty(row).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
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
    // 显示明细
    showDetail() {
      this.listLoading = true
      listDetail(this.applyDetailForm).then(result => {
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
    // 生成申请号
    createApply(val) {
      this.$confirm('此操作生成申请数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 生成申请号
        if (val === 1) {
          if (this.calcDetailForm.agentNo == null || this.calcDetailForm.warehouseCode == null ||
          this.calcDetailForm.agentNo === '' || this.calcDetailForm.warehouseCode === '') {
            this.$message.warning('请选择仓库代码')
            return
          }
          this.returnData = [{ agentNo: this.calcDetailForm.agentNo, warehouseCode: this.calcDetailForm.warehouseCode }]
        } else {
          this.returnData = this.$refs.calcMasterList.selection
        }
        this.listLoading = true
        this.returnData.forEach(item => {
          const confirmParams = { 'agentNo': item.agentNo, 'warehouseCode': item.warehouseCode }
          createApply(confirmParams).then(result => {
            if (result.success) {
              this.calcListdata = null
              this.calcForm.agentNo = ''
              this.calcForm.warehouseCode = ''
              this.$message.success(result.content)
            } else {
              this.$message.error(result.message)
            }
            this.listLoading = false
          }).catch(error => {
            this.listLoading = false
            console.log(error)
          })
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '取消操作'
        })
      })
    },
    applyStatusformatter(row) {
      if (row != null) {
        return this.descFormatter(this.applyStatusDesc, row.status)
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
    // 申请状态
    applyStatusDec(status) {
      if (status != null) {
        return this.descFormatter(this.applyStatusDesc, status)
      }
    },
    // 编辑子项可编辑
    showEditable(row) {
      console.log(row)
      row.isEditable = true
    },
    // 打印退货申请单据
    handlePrintApply(row) {
      // this.$message({
      //   type: 'info',
      //   message: '功能暂未完善'
      // })

      const applyPrintParams = { 'applyId': row.applyId }

      printReturnApply(applyPrintParams).then(result => {
        console.log('result.type')
        console.log(result.type)
        const fileName = result.type
        const blob = new Blob([result], { type: result.type })
        this.printFile(blob, fileName)
      }).catch(error => {
        console.log(error)
      })
    },
    printFile(blob, fileName) {
      if (window.navigator.msSaveOrOpenBlob) { // 适配IE10
        navigator.msSaveBlob(blob, fileName)
      } else {
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        window.open(link.href, '_blank')
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
.search-input-class /deep/ .el-autocomplete-suggestion {
  width: auto!important;
}
.divider {
  height: 5px;
  margin: 30px 0;
}
.select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
  }
</style>
