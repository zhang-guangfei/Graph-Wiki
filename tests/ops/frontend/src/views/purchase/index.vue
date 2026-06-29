<template>
  <div class="base-index">
    <div class="public-table">
      <div style="margin-top:5px;margin-bottom:5px">
        <el-divider/>
      </div>
      <i class="el-icon-coin" style="color: #FFA500;"/><span style="font-weight: 800; font-size: 13px;">&nbsp;&nbsp;系统推荐合并采购</span>
      <el-tooltip effect="dark" content="刷新" placement="top">
        <!-- <el-button size="small" type="primary" icon="el-icon-refresh" circle @click="getList"/> -->
        <el-button v-permission="['134260']" size="small" type="primary" icon="el-icon-refresh" circle @click="refresh"/>
      </el-tooltip>
      <el-tooltip effect="dark" content="配置" placement="top">
        <el-button v-permission="['134260']" size="small" circle icon="el-icon-setting" @click.stop="openMergeConfigDialog"/>
      </el-tooltip>
      <div style="margin-top:0px;margin-bottom:5px"/>
      <el-button v-permission="['530576']" :loading="mergeButtonloading" :disabled="selectMergeList.length <1" plain size="small" type="primary" style="float:left;margin-top:5px;margin-left:10px;margin-bottom: 5px" @click="toPurCharse">采购</el-button>
      <el-button v-permission="['530576']" :disabled="selectMergeList.length <1" plain size="small" type="warning" style="float:left;margin-top:5px;margin-left:10px;margin-bottom: 5px" @click="splitAll">全部拆分</el-button>
      <el-table
        v-loading="loadingMerge"
        :data="tableData"
        :span-method="objectSpanMethod"
        :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
        :cell-style="{padding: '2px 3px'}"
        :fit="true"
        height="280px"
        border
        stripe
        resizable
        highlight-current-row
        style="width: 100%; margin-top: 3px"
        @selection-change="selectChangeEventMerge"
        @select-all="selectAllEventMerge"
        @filter-change="filterChangeMerge">
        <el-table-column type="selection"/>
        <el-table-column label="操作" align="center" fixed="left" min-width="100" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-tooltip effect="light" content="拆分" placement="top">
              <el-button v-permission="['530576']" type="primary" size="mini" icon="el-icon-document" @click="splitRow(scope.row)"/>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="序号" align="center" fixed="left" prop="id" min-width="70">
          <template slot-scope="scope">
            <span>{{ scope.$index + 1 }}</span>
          </template>
        </el-table-column>
        <el-table-column label="请购单号" align="center" min-width="140">
          <!-- <template slot-scope="scope">
            <el-tag size="mini"><a style="color: blue" @click="toDetail(scope.row.orderno)">{{ scope.row.orderno }}</a></el-tag>
          </template> -->
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
        <!--bug12361 OPS采购查询 营业所字段数据错误-->
        <!-- <el-table-column :formatter="deptFormatter" prop="applyDeptNo" fixed="left" show-overflow-tooltip label="申请部门" min-width="90" align="center"/> -->
        <el-table-column :formatter="deptFormatter" fixed="left" prop="deptno" show-overflow-tooltip label="营业所" min-width="110" align="center"/>
        <el-table-column label="多段" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.islot | isBooleanFormat }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="子项号" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.itemno }}</span>
          </template>
        </el-table-column> -->
        <el-table-column show-overflow-tooltip label="型号" align="center" min-width="150">
          <template slot-scope="scope">
            <span>{{ scope.row.modelno }}</span>
          </template>
        </el-table-column>
        <el-table-column label="请购数量" min-width="100" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.quantity }}</span>
          </template>
        </el-table-column>
        <el-table-column label="数量(合)" min-width="90" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.quantitySum }}</span>
          </template>
        </el-table-column>
        <el-table-column label="价格" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.stdprice }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip label="价格(合)" min-width="90" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.stdpriceMerge }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column label="多段" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.islot }}</span>
          </template>
        </el-table-column> -->
        <el-table-column label="客户" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.customerno }}</span>
          </template>
        </el-table-column>
        <!--
        <el-table-column label="用户" align="center">
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
        <el-table-column
          :filters="columnOptionMerge"
          :formatter="suppilyFormatter"
          filter-placement="bottom-start"
          prop="supplierid"
          column-key="supplierid"
          show-overflow-tooltip
          label="供应商"
          min-width="120"
          align="center"/>
        <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" align="center" min-width="100"/>
        <el-table-column :formatter="transtypeFormatter" prop="transTypeMerge" show-overflow-tooltip label="运输方式(合)" align="center" min-width="120"/>
        <el-table-column show-overflow-tooltip label="制造出荷日" min-width="150" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip label="制造出荷日(合)" min-width="160" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.hopeExportDateMerge | formatDate2 }}</span>
          </template>
        </el-table-column>
        <el-table-column :formatter="warehouseFormatter" prop="requestwarehouseid" show-overflow-tooltip label="入库仓库" align="center" min-width="120"/>
        <el-table-column :formatter="warehouseFormatter" prop="purchaseWarehouseIdMerge" show-overflow-tooltip label="入库仓库(合)" align="center" min-width="120"/>
        <el-table-column label="订单类别" align="center" min-width="110">
          <template slot-scope="scope">
            <span>{{ scope.row.purchasetype | ordTypeFormat }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip label="请购日期" min-width="120" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.requesttime | formatDate }}</span>
          </template>
        </el-table-column>
        <el-table-column label="smcCode" min-width="100" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.smccode }}</span>
          </template>
        </el-table-column>
        <el-table-column label="总重量" min-width="100" align="center">
          <template slot-scope="scope">
            <span>{{ (scope.row.netweightMerge).toFixed(4) }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip label="SHIKOMI" align="center" min-width="100">
          <template slot-scope="scope">
            <span>{{ scope.row.shikomianswerno }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip label="准备订单号" align="center" min-width="120">
          <template slot-scope="scope">
            <span>{{ scope.row.prepareorderno }}</span>
          </template>
        </el-table-column>
        <el-table-column label="供应商在库" min-width="110" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.supplierinventory }}</span>
          </template>
        </el-table-column>
        <el-table-column :formatter="specmarkFormatter" prop="specmark" show-overflow-tooltip label="组装标识" min-width="90" align="center"/>
        <el-table-column label="特殊标识" min-width="90" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.producttag }}</span>
          </template>
        </el-table-column>
        <el-table-column show-overflow-tooltip label="操作人" min-width="90" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.operator }}</span>
          </template>
        </el-table-column>
      </el-table>
      <div style="margin-top:10px;margin-bottom:10px">
        <el-divider/>
      </div>
      <i class="el-icon-coin" style="color: #FFA500;"/><span style="font-weight: 800; font-size: 13px">
        &nbsp;&nbsp;不合并清单 &nbsp;&nbsp;&nbsp;&nbsp;
        <el-dropdown trigger="click">
          <span class="el-dropdown-link">
            数量统计<i class="el-icon-caret-bottom el-icon--right"/>
          </span>
          <el-dropdown-menu slot="dropdown">
            <el-dropdown-item class="clearfix">
              中国工厂
              <el-badge :value="totalToSuppily.CN" class="mark" />
            </el-dropdown-item>
            <el-dropdown-item class="clearfix">
              日本订单
              <el-badge :value="totalToSuppily.JP" class="mark" />
            </el-dropdown-item>
            <el-dropdown-item class="clearfix">
              海外订单
              <el-badge :value="totalToSuppily.Oversea" class="mark" />
            </el-dropdown-item>
          </el-dropdown-menu>
          <el-dropdown/>
      </el-dropdown></span>
      <div style="margin-top:0px;margin-bottom:10px"/>
      <el-button v-permission="['530576']" :disabled="selectList.length <2" size="small" plain type="primary" @click="artificialMergeData">合并</el-button>
      <el-button v-permission="['530576']" :loading="oneButtonloading" :disabled="selectList.length <1" size="small" plain type="primary" @click="toPurCharseOne">采购</el-button>

      <div ref="scrollDom" class="list-view" @scroll="handleScroll">
        <div :style="{ height: tableDataOne.length * 24-11 + 'px' }" class="list-view-phantom"/>
        <div :style="{top:startIndex*24-12+'px'}" style="position:absolute;width: 100%;margin-top:3px">
          <!-- @select="selectEvent2" -->
          <el-table
            v-loading="loadingOne"
            ref="innerTable"
            :data="tableSplitData"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
            :row-key="getRowKey"
            border
            highlight-current-row
            stripe
            resizable
            select-on-indeterminate
            style="width: 100%; margin-top: 3px;position: absolute; width: auto; max-width: none;"
            show-overflow-tooltip
            @selection-change="selectChangeEvent2"
            @select-all="selectAllEvent2"
            @filter-change="filterChange"
          >
            <!-- @sort-change="sortChange" -->
            <el-table-column :reserve-selection="true" type="selection"/>
            <el-table-column label="序号" align="center" fixed="left" min-width="70">
              <template slot-scope="scope">
                <span>{{ startIndex + scope.$index + 1 }}</span>
              </template>
            </el-table-column>
            <!--bug12361 OPS采购查询 营业所字段数据错误-->
            <!-- <el-table-column :formatter="deptFormatter" prop="applyDeptNo" fixed="left" show-overflow-tooltip label="申请部门" min-width="90" align="center"/> -->
            <el-table-column :formatter="deptFormatter" prop="deptno" show-overflow-tooltip label="营业所" min-width="110" align="center"/>
            <el-table-column :sort-orders="['descending','ascending']" label="请购单号" fixed="left" align="center" min-width="150" prop= "orderno" sortable>
              <!-- <template slot-scope="scope">
            <el-tag size="mini"><a style="color: blue" @click="toDetail(scope.row.orderno)">{{ scope.row.orderno }}</a></el-tag>
          </template> -->
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
            <el-table-column :sort-orders="['descending','ascending']" show-overflow-tooltip prop= "modelno" label="型号" align="center" min-width="150" sortable>
              <template slot-scope="scope">
                <span>{{ scope.row.modelno }}</span>
              </template>
            </el-table-column>
            <el-table-column label="数量" align="center" min-width="100">
              <template slot-scope="scope">
                <span>{{ scope.row.quantity }}</span>
              </template>
            </el-table-column>
            <el-table-column :sort-orders="['descending','ascending']" prop= "customerno" label="客户" align="center" min-width="100" sortable>
              <template slot-scope="scope">
                <span>{{ scope.row.customerno }}</span>
              </template>
            </el-table-column>
            <!--
            <el-table-column :sort-orders="['descending','ascending']" prop= "userno" label="用户" align="center" sortable>
              <template slot-scope="scope">
                <span>{{ scope.row.userno }}</span>
              </template>
            </el-table-column>
            -->
            <el-table-column :sort-orders="['descending','ascending']" prop= "endUser" label="最终用户" align="center" min-width="130" sortable>
              <template slot-scope="scope">
                <span>{{ scope.row.endUser }}</span>
              </template>
            </el-table-column>
            <el-table-column :filters="columnOption" :formatter="suppilyFormatter" filter-placement="bottom-start" prop="supplierid" column-key="supplierid" min-width="120" show-overflow-tooltip label="供应商" align="center"/>
            <el-table-column :formatter="transtypeFormatter" prop="transtype" show-overflow-tooltip label="运输方式" align="center" min-width="100"/>
            <el-table-column show-overflow-tooltip label="指定制造出荷日" min-width="150" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.hopeexportdate | formatDate2 }}</span>
              </template>
            </el-table-column>
            <!-- <el-table-column label="价格" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.stdprice }}</span>
              </template>
            </el-table-column> -->
            <!-- :filter-method="filterHandler" -->
            <el-table-column label="smcCode" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.smccode }}</span>
              </template>
            </el-table-column>
            <el-table-column label="订单类别" align="center" min-width="110">
              <template slot-scope="scope">
                <span>{{ scope.row.purchasetype | ordTypeFormat }}</span>
              </template>
            </el-table-column>
            <el-table-column label="重量" min-width="100" align="center">
              <template slot-scope="scope">
                <span>{{ (scope.row.netweight * scope.row.quantity).toFixed(4) }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="SHIKOMI" align="center" min-width="100">
              <template slot-scope="scope">
                <span>{{ scope.row.shikomianswerno }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="准备订单号" align="center" min-width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.prepareorderno }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="warehouseFormatter" prop="requestwarehouseid" show-overflow-tooltip label="入库仓库" align="center" min-width="120"/>
            <el-table-column label="供应商在库" min-width="110" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.supplierinventory }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="specmarkFormatter" prop="specmark" show-overflow-tooltip label="组装标识" min-width="90" align="center"/>
            <el-table-column label="特殊标识" min-width="90" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.producttag }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="业务备注" min-width="90" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.serverremark }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="请购日期" min-width="120" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.requesttime | formatDate }}</span>
              </template>
            </el-table-column>
            <el-table-column show-overflow-tooltip label="操作人" min-width="90" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.operator }}</span>
              </template>
            </el-table-column>
          </el-table>
        </div>
      </div>
      <!-- </el-tab-pane>
      </el-tabs> -->
    </div>
    <!-- 订单取消 -->
    <MergeConfigDialog :visible.sync="mergeConfigDialog.show" />
  </div>
</template>

<script>
import { findRequestMergeList, artificialMerge, toPurchaseMerge, toPurchaseOne, splitRequestPurchase, filterData, sortData } from '@/api/requestPurchase'
import { findDeptDict } from '@/api/warehouseManage'
import Item from '../../layout/components/Sidebar/Item.vue'
import { getSuppily, getWarehouse } from '@/api/intercept'
import { getDataCodesTree } from '@/api/common/dict'
import MergeConfigDialog from '@/views/purchase/MergeConfigDialog'
import { getTransIds } from '@/api/purchaseOrder'
export default {
  name: 'RequestPurchaseIndex',
  components: { Item, MergeConfigDialog },
  inject: ['reload'],
  data() {
    return {
      // 订单取消
      mergeConfigDialog: {
        show: false
      },
      startIndex: 0,
      loadingMerge: false,
      loadingOne: false,
      warehouseList: [],
      modelArr: [],
      modelPos: 0,
      counts: 0,
      tableData: [],
      tableDataOne: [],
      tableDataOneFull: [],
      tableDataMergeFull: [],
      // 拆分渲染集合
      tableColumn: [],
      condition: {
      },
      params: {},
      selectList: [],
      selectMergeList: [],
      // 手工合并的uuid
      uuidMerge: '',
      moreVisible: false,
      columnOption: [],
      columnOptionMerge: [],
      suppilyList: [],
      DictData: {
        department: [],
        specmark: [],
        transtype: []
      },
      filtercondition: {},
      filterMergecondition: {},
      // 全选标识
      allCheck: false,
      // 考虑到，全选时，取消勾选的状态
      uncheckedArr: [],
      onedatatotal: 0,
      totalToSuppily: {},
      sortChangeColumn: {},
      mergeButtonloading: false,
      oneButtonloading: false
      // pageNumber: 1
    }
  },
  computed: {
    limitCount() {
      return 400 / 24 + 2
    },
    tableSplitData() {
      if (this.tableDataOne != null) {
        return this.tableDataOne.slice(this.startIndex, this.startIndex + this.limitCount)
      }
    }
  },
  watch: {
    tableSplitData: {
      handler(value) {
        if (this.allCheck) {
          this.tableSplitData.forEach(row => {
            if (row) {
              this.$refs.innerTable.toggleRowSelection(row, true)
            }
          })
        }
        // this.allCheck && this.chooseAllPages(this.allCheck)
        // !this.allCheck && this.checkedSelected()
      },
      deep: true
    },
    '$route'() {
      this.refresh()// 我的初始化方法
    }
  },
  // created() {
  //   this.init()
  // },
  created() {
    this.getList()
    this.getfilterSuppily()
    this.getSuppily()
    this.getWarehouseList()
    this.columnOption = this.uniqArrObject(this.columnOption)
    this.columnOptionMerge = this.uniqArrObject(this.columnOptionMerge)
    this.initData()
  },
  methods: {
    openMergeConfigDialog() {
      this.mergeConfigDialog.show = true
    },
    refresh() {
      // this.getList()
      this.reload()
      // this.$router.go(0) // 会出现一段空白页，用户体验不好
    },
    handleScroll() {
      // 根据滚动的距离，估算出这个滚动位置对应的数组序列，例如滚动100px，每条24px，对应第5条
      const scrollTop = this.$refs.scrollDom.scrollTop
      this.startIndex = Math.floor(scrollTop / 24)
    },
    initData() {
      // 营业所
      findDeptDict().then(result => {
        result.forEach(dict => {
          this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
        })
      }).catch(error => {
        console.log(error)
      })
      // bug 10526 修正组装标识前端显示
      getDataCodesTree('4401').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.specmark.push({ value: dict.code, label: dict.codeName })
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
    },
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    // bug 10526 修正组装标识前端显示
    specmarkFormatter(row, column, cellValue, index) {
      const item = this.DictData.specmark.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    getList() {
      this.loadingMerge = true
      this.loadingOne = true
      findRequestMergeList().then(res => {
        if (res.success === true) {
          this.tableData = []
          this.tableDataOne = []
          if (!this.isEmpty(res.data.mergedata)) {
            this.tableData = res.data.mergedata
          }
          if (!this.isEmpty(res.data.onedata)) {
            this.tableDataOne = res.data.onedata
            this.onedatatotal = this.tableDataOne.length
          }
          this.modelNoinit()
          this.getfilterSuppily()
          if (res.data.totalCount != null) {
            this.totalToSuppily = res.data.totalCount
          }
          this.columnOption = this.uniqArrObject(this.columnOption)
          this.columnOptionMerge = this.uniqArrObject(this.columnOptionMerge)
          // this.ngOnInit()
          this.loadingMerge = false
          this.loadingOne = false
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.loadingMerge = false
        this.loadingOne = false
      }).catch(res => {
        this.loadingMerge = false
        this.loadingOne = false
        console.info(res)
        this.$message.error(res.message)
      })
    },
    // 拆分单条数据摁扭
    splitRow(row) {
      this.loadingMerge = true
      row = row || {}
      // 获得合并表数据
      var datas = this.tableData
      var count = this.getCount(datas)[row.uuid]
      // 得到拆分元素在合并列表中的个数，小于等于2直接放到单项中，否则调用后来接口重新计算合并方法
      // 小于2的直接才分
      if (count <= 2) {
        // for (var i = 0; i < datas.length; i++) {
        for (let i = datas.length - 1; i >= 0; i--) {
          if (row.uuid === datas[i].uuid) {
            this.tableDataOne.push(datas[i])
            datas.splice(i, 1)
            // this.modelNoinit()
          }
        }
        this.loadingMerge = false
      } else {
        // 首先移除单条元素，然后重新请求后台接口，获取更新后的列表
        // for (var j = 0; j < datas.length; j++) {
        for (let j = datas.length - 1; j >= 0; j--) {
          if (row.id === datas[j].id) {
            datas.splice(j, 1)
            this.tableDataOne.push(row)
          }
        }
        // 调用后台接口，重新计算合并参数
        this.params.tabledatas = datas
        this.params.uuid = row.uuid
        splitRequestPurchase(this.params).then(res => {
          this.tableData = res.data
          this.modelNoinit()
          this.loadingMerge = false
        }).catch(res => {
          // this.smcErrorMsg(res.message)
          this.loadingMerge = false
          console.info(res)
          this.$message.error(res.message)
        })
      }
      this.onedatatotal = this.tableDataOne.length
    },
    getCount(dataMerge) {
      var obj = {}
      for (let i = 0; i < dataMerge.length; i++) {
        var t = dataMerge[i].uuid
        if (obj.hasOwnProperty(t)) { // 判断t属性是否为对象obj中的属性；
          obj[t] = obj[t] + 1
        } else {
          obj[t] = 1
        }
      }
      return obj
    },
    // 项目类别
    modelNoinit() {
      this.modelArr = []
      this.modelPos = 0
      for (let i = 0; i < this.tableData.length; i++) {
        if (i === 0) {
          this.modelArr.push(1)
          this.modelPos = 0
        } else {
          if (this.tableData[i].uuid === this.tableData[i - 1].uuid) {
            this.modelArr[this.modelPos] += 1
            this.modelArr.push(0)
          } else {
            this.modelArr.push(1)
            this.modelPos = i
          }
        }
      }
    },
    // 合并行的方法
    objectSpanMethod({ rowIndex, columnIndex }) {
      if (columnIndex === 0 || columnIndex === 5 || columnIndex === 6 || columnIndex === 8 || columnIndex === 10 || columnIndex === 13 || columnIndex === 15 || columnIndex === 17 || columnIndex === 19 || columnIndex === 23 || columnIndex === 27) {
        const row1 = this.modelArr[rowIndex]
        const col1 = row1 > 0 ? 1 : 0
        return {
          rowspan: row1,
          colspan: col1
        }
      }
    },
    groupBy(array, f) {
      const groups = {}
      array.forEach(function(o) {
        const group = JSON.stringify(f(o))
        groups[group] = groups[group] || []
        groups[group].push(o)
      })
      return Object.values(groups)
      // Object.keys(groups).map(group => groups[group])
    },
    // 详情跳转方法
    toDetail(value) {
      this.$router.push({
        path: '/purchase/requestPurChaseDeail',
        query: { orderno: value }
      })
    },
    // 重置表单
    resetForm() {
      this.$refs.form.resetFields()
      this.applyDateRange = []
    },
    getRowKey(row) {
      return row.id
    },
    // 全选所有页面
    chooseAllPages(val) {
      if (val) {
        // 全选
        this.$nextTick(() => {
          this.tableSplitData.forEach(row => {
            this.$refs.innerTable.toggleRowSelection(row, true)
          })
        })
      } else {
        // 取消全选
        this.uncheckedArr = []
        this.$nextTick(() => {
          this.$refs.innerTable.clearSelection()
        })
      }
    },
    // 切换分页时选择之前选中
    checkedSelected() {
      if (this.allCheck) {
        // 全选状态allCheck=true
        this.$nextTick(() => {
          this.tableSplitData.forEach(row => {
            this.$refs.innerTable.toggleRowSelection(row, true)
          })
        })
      } else {
        // 非全选状态下allCheck=false
        // uncheckedArr有值，切换页面时仍然选中全部（但需要排除包含在uncheckedArr中的值）
        if (this.uncheckedArr.length > 0) {
          this.$nextTick(() => {
            this.tableSplitData.forEach(row => {
              if (this.uncheckedArr.map(v => v.id).indexOf(row.id) < 0) {
                this.$refs.innerTable.toggleRowSelection(row, true)
              }
            })
          })
        }
      }
    },

    // 列表复选框改变事件
    selectChangeEvent2(records) {
      // this.selectList = []
      // if (this.allCheck) {
      //   // 全选状态下，列表行数据都处于被选中状态，手动勾选只能触发取消选中
      //   this.uncheckedArr.push(records)
      //   // this.$emit('update:allCheck', false)
      //   records.forEach(element => {
      //     this.selectList.push(element)
      //   })
      //   // this.selectList.splice(records, 1)
      //   this.allCheck = false
      // } else {
      //   records.forEach(element => {
      //     this.selectList.push(element)
      //   })
      //   // 选中所有选择框时勾选全选按钮
      //   if (this.selectList.length === this.onedatatotal) {
      //     this.allCheck = true
      //   }
      // }

      this.selectList = []
      records.forEach(element => {
        element.operator = this.$store.getters.position.psnsmcId
        this.selectList.push(element)
      })
      // // 选中所有选择框时勾选全选按钮
      // if (this.selectList.length === this.onedatatotal) {
      //   this.allCheck = true
      // }
    },
    selectChangeEventMerge(records) {
      // const test = tablefilter.filter(ele => ele.uuid = element.uuid)
      this.selectMergeList = []
      const tablefilter = this.tableData
      const uuidList = records.map(x => x.uuid)
      // const uuids = uuidList[0]
      // const datas = tablefilter.filter(item => item.uuid === uuids)
      uuidList.forEach(element => {
        tablefilter.filter(item => item.uuid === element).forEach(elements => {
          elements.operator = this.$store.getters.position.psnsmcId
          this.selectMergeList.push(elements)
        })
      })
    },
    selectAllEventMerge(records) {
      this.selectMergeList = []
      records.forEach(element => {
        element.operator = this.$store.getters.position.psnsmcId
        this.selectMergeList.push(element)
      })
    },
    // 单条列表选择事件
    selectEvent2(selection, row) {
      this.selectExceptCardIds(selection, row)
      this.$emit('selectEvent2', [selection, row])
    },
    // 单条列表全选事件
    selectAllEvent2(records) {
      // this.selectAllRows(records)
      // this.$emit('selectAll', records)
      const that = this
      that.selectList = []
      if (!that.allCheck) {
        // 全选选中时当前页所有数据选中,使用后台所有数据进行遍历.
        that.tableDataOne.forEach(element => {
          element.operator = that.$store.getters.position.psnsmcId
          that.selectList.push(element)
          that.$refs.innerTable.toggleRowSelection(element, true)
        })
        // that.tableSplitData.forEach(row => {
        //   if (row) {
        //     that.$refs.innerTable.toggleRowSelection(row, true)
        //   }
        // })
        that.uncheckedArr = []
        that.allCheck = true
      } else {
        that.$refs.innerTable.clearSelection()
        that.allCheck = false
      }

      // records = that.tableDataOne
      // that.selectList = []
      // records.forEach(element => {
      //   that.selectList.push(element)
      //   // this.$refs.innerTable.toggleRowSelection(element, true)
      // })
    },
    // 全选后取消单个选择事件
    selectExceptCardIds(selection, row) {
      if (this.allCheck) {
        // 全选状态下，列表行数据都处于被选中状态，手动勾选只能触发取消选中
        this.uncheckedArr.push(row)
        // this.$emit('update:allCheck', false)
        this.allCheck = false
      } else {
        // false, 非全选状态下，列表行数据可能被 取消勾选 或者 重新勾选
        if (selection.indexOf(row) > 0) {
          // 勾选
          selection.map(el => {
            this.uncheckedArr.map((v, index) => {
              if (el.id === v.id) {
                this.uncheckedArr.splice(index, 1)
                if (this.uncheckedArr.length === 0) {
                  // this.$emit('update:allCheck', true)
                  this.allCheck = true
                }
              }
            })
            if (selection.length === this.onedatatotal) {
              // this.$emit('update:allCheck', true)
              this.allCheck = true
            }
          })
        } else {
          // 取消勾选
          // 若是uncheckedArr.length > 0,取消勾选的数组加入uncheckedArr
          if (this.uncheckedArr.length > 0) {
            this.uncheckedArr.push(row)
          }
        }
      }
    },
    // 当前页面全选
    selectAllRows(selection) {
      if (this.allCheck) {
        // 全选状态下，列表行数据都处于被选中状态，手动勾选只能触发取消选中
        this.tableSplitData.map(row => {
          this.uncheckedArr.push(row)
        })
        // this.$emit('update:allCheck', false)
        this.allCheck = false
      } else {
        // false,部分选中状态下
        selection.map(row => {
          // 勾选
          this.uncheckedArr.map((v, index) => {
            if (row.id === v.id) {
              this.uncheckedArr.splice(index, 1)
            }
            if (this.uncheckedArr.length === 0) {
              // this.$emit('update:allCheck', true)
              this.allCheck = true
            }
          })
          if (selection.length === this.onedatatotal) {
            // this.$emit('update:allCheck', true)
            this.allCheck = true
          }
          // 取消勾选
          if (this.uncheckedArr.length > 0) {
            if (this.tableSplitData.map(v => v.id).indexOf(row.id) === -1) {
              this.uncheckedArr.push(row)
            }
          }
        })
      }
    },
    unit(arr) {
      const tmp = new Map()
      return arr.filter(item => {
        return !tmp.has(item) && tmp.set(item, 1)
      })
    },
    splitAll() {
      if (this.selectMergeList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要拆分的合并的请购单'
        })
        return
      }
      var datas = this.tableData
      this.loadingMerge = true
      if (this.selectMergeList.length === this.tableData.length) {
        this.tableDataOne.push.apply(this.tableDataOne, datas)
        this.tableData = []
      } else {
        // 定义去完重的UUid，用于后续循环删除
        const uuids = this.unit(this.selectMergeList.map(v => { return v.uuid }))
        // 获得合并表数据
        for (let i = datas.length - 1; i >= 0; i--) {
          for (let j = 0; j < uuids.length; j++) {
            if (uuids[j] === datas[i].uuid) {
              this.tableDataOne.push(datas[i])
              datas.splice(i, 1)
            }
          }
        }
      }
      this.loadingMerge = false
      this.onedatatotal = this.tableDataOne.length
    },
    // 合并订单执行采购
    toPurCharse() {
      if (this.selectMergeList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要手工合并的请购单'
        })
        return
      }
      this.mergeButtonloading = true
      this.loadingMerge = true
      toPurchaseMerge(this.selectMergeList).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: '采购成功',
            type: 'success',
            duration: 2000
          })
          this.getList()
          this.tableDataMergeFull = this.tableData
          this.loadingMerge = false
          this.mergeButtonloading = false
        } else {
          this.loadingMerge = false
          this.mergeButtonloading = false
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error'
          })
        }
      }).catch(res => {
        this.loadingMerge = false
        this.mergeButtonloading = false
        console.info(res)
        this.$message.error(res.message)
      })
    },
    // 单条订单执行采购
    toPurCharseOne() {
      if (this.selectList.length === 0) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要手工合并的请购单'
        })
        return
      }
      // 判断是否为全选，则选中整个列表数据
      // if (this.allCheck) {
      //   this.selectList = this.tableDataOne
      // }
      this.loadingOne = true
      this.oneButtonloading = true
      toPurchaseOne(this.selectList).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: '采购成功',
            type: 'success',
            duration: 2000
          })
          this.getfilterSuppily()
          this.getList()
          this.tableDataOneFull = this.tableDataOne
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error'
          })
        }
        this.$refs.innerTable.clearSelection()
        this.loadingOne = false
        this.oneButtonloading = false
      }).catch(res => {
        // console.log(res)
        // this.smcErrorMsg(res.message)
        this.loadingOne = false
        this.oneButtonloading = false
        console.info(res)
        this.$message.error(res.message)
      })
    },
    // 手工-合并采购方法
    artificialMergeData() {
      if (this.selectList.length < 2) {
        this.$notify.error({
          title: '错误',
          message: '请在列表中勾选要手工合并的请购单'
        })
        return
      }
      var dataMerge = []
      for (let i = 0; i < this.selectList.length; i++) {
        if (i === 0) {
          // this.uuidMerge = this.selectList[i].uuid
          if (!this.isEmpty(this.selectList[i].shikomianswerno) || this.selectList[i].specmark === '1' || this.selectList[i].specmark === '2') {
            this.$notify.error({
              title: '错误',
              message: '勾选的请购单有SHIKOMI号，或者为阀汇流板，不能参与合并，请重试'
            })
            return
          }
          dataMerge.push(this.selectList[i])
        } else {
          if (this.selectList[i].modelno === this.selectList[i - 1].modelno &&
           this.selectList[i].purchasetype === this.selectList[i - 1].purchasetype && this.selectList[i].supplierid === this.selectList[i - 1].supplierid) {
            if (!this.isEmpty(this.selectList[i].shikomianswerno) || this.selectList[i].specmark === '1' || this.selectList[i].specmark === '2') {
              this.$notify.error({
                title: '错误',
                message: '勾选的请购单有SHIKOMI号，或者为阀汇流板，不能参与合并，请重试'
              })
              return
            }
            dataMerge.push(this.selectList[i])
          } else {
            this.$notify.error({
              title: '错误',
              message: '勾选的请购单不符合合并要求，请重试'
            })
            return
          }
        }
      }
      if (this.selectList[0].islot === false && this.selectList.length < 5) {
        this.$notify.error({
          title: '错误',
          message: '合并的订单型号非多段型号，且出库日的项数小于5，不能合并！'
        })
        return
      }
      // 调用后端合并方法
      var splitData = this.tableDataOne
      var mergeData = []
      var newTableData = this.tableData
      this.loadingOne = true
      artificialMerge(dataMerge).then(res => {
        if (res.success === true) {
          mergeData = res.data
          if (this.isEmpty(mergeData)) {
            this.smcErrorMsg(res.message)
            this.loadingOne = false
            return
          }
          for (var i = 0; i < mergeData.length; i++) {
            for (var j = 0; j < splitData.length; j++) {
              if (mergeData[i].id === splitData[j].id) {
                splitData.splice(j, 1)
                newTableData.push(mergeData[i])
              }
            }
          }
          this.onedatatotal = this.tableDataOne.length
          this.modelNoinit()
          this.$refs.innerTable.clearSelection()
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
        this.loadingOne = false
        // this.ngOnInit()
      }).catch(res => {
        // console.log(res)
        // this.smcErrorMsg(res.message)
        this.loadingOne = false
        console.info(res)
        this.$message.error(res.message)
      })
    },
    // filterHandler(value, row, column) {
    //   return row[column.property].indexOf(value) >= 0
    // },
    // 合并筛选
    filterChangeMerge(filterObj) {
      this.loadingMerge = true
      if (filterObj.supplierid.length > 0) {
        this.filterMergecondition.filtersuppilyid = filterObj.supplierid
        if (this.tableData.length < this.tableDataMergeFull.length) {
          this.filterMergecondition.onedata = this.tableDataMergeFull
        } else {
          this.filterMergecondition.onedata = this.tableData
        }
      } else {
        this.filterMergecondition.filtersuppilyid = null
        this.filterMergecondition.onedata = this.tableDataMergeFull
      }

      filterData(this.filterMergecondition).then(res => {
        this.tableData = res.data.filterdata
        this.tableDataMergeFull = res.data.onedata
        this.modelNoinit()
        this.loadingMerge = false
      }).catch(error => {
        this.loadingMerge = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 不合并筛选
    filterChange(filterObj) {
      this.loadingOne = true
      if (filterObj.supplierid.length > 0) {
        this.filtercondition.filtersuppilyid = filterObj.supplierid
        if (this.tableDataOne.length < this.tableDataOneFull.length) {
          this.filtercondition.onedata = this.tableDataOneFull
        } else {
          this.filtercondition.onedata = this.tableDataOne
        }
      } else {
        this.filtercondition.filtersuppilyid = null
        this.filtercondition.onedata = this.tableDataOneFull
      }

      filterData(this.filtercondition).then(res => {
        this.tableDataOne = res.data.filterdata
        this.tableDataOneFull = res.data.onedata
        this.onedatatotal = this.tableDataOne.length
        // res.data.suppilylist.forEach(element => {
        //   console.log(element)
        //   this.columnOption.push({
        //     text: element,
        //     value: element
        //   })
        // })
        this.loadingOne = false
      }).catch(error => {
        this.loadingOne = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    getfilterSuppily() {
      if (this.tableDataOne.length !== 0) {
        this.tableDataOne.forEach(element => {
          var key = element.supplierid
          this.columnOption.push({
            text: key,
            value: key
          })
        })
      }
      if (this.tableData.length !== 0) {
        this.tableData.forEach(element => {
          var key = element.supplierid
          this.columnOptionMerge.push({
            text: key,
            value: key
          })
        })
      }
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 去重方法
    uniqArrObject(arr) {
      const result = {}
      const finalResult = []
      for (let i = 0; i < arr.length; i++) {
        result[arr[i].text] = arr[i]
      }
      for (const key in result) {
        finalResult.push(result[key])
      }
      return finalResult
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    warehouseFormatter(row, column, cellValue, index) {
      const item = this.warehouseList.find(dict => dict.warehouseCode === cellValue)
      return item ? item.warehouseName : cellValue
    },
    getWarehouseList() {
      getWarehouse().then(res => {
        this.warehouseList = res.data
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    sortChange(column, prop, order) {
      // this.queryCondition.orderBy = column.prop
      this.loadingOne = true
      this.sortChangeColumn.column = column.prop
      if (column.order === null) {
        this.loadingOne = false
      }
      this.sortChangeColumn.orderby = column.order
      if (this.tableDataOne.length > 0) {
        this.sortChangeColumn.onedata = this.tableDataOne
        sortData(this.sortChangeColumn).then(res => {
          this.tableDataOne = res.data.onedata
          this.loadingOne = false
        }).catch(error => {
          this.loadingOne = false
          console.info(error)
          this.$message.error(error.message)
        })
      }
    },
    // bug  运输方式转换
    transtypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.transtype.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    }
  }
}
</script>

<style lang="scss">
.list-view {
    height: 400px;
    overflow: auto;
    position: relative;
}
.list-view-phantom {
    position: absolute;
    left: 0;
    top: 0;
    right: 0;
    z-index: -1;
}
.el-divider--horizontal {
  margin: 5px 0;
}
.col-class {
  height: 20px;
}
.el-form-item__label {
  justify-content: space-between;
}
.el-button--mini.is-round {
  padding: 5px 5px;
}
.el-button--medium.is-round {
  padding: 8px 8px;
}
.stock-content-body {
  margin-bottom: 100px;
  background-color: rgb(159, 195, 231);
  // color: rgba(253, 253, 253, 0.938)
}
a:link {color: black} /* 未被访问bai的链接 黑色 */
a:visited {color: blue; text-decoration: underline} /* 已被访问过的链接 蓝色下划线 */
a:hover {color: blue; text-decoration: underline} /* 鼠标悬浮在上的链接 蓝色下划线 */
a:active {color: blue} /* 鼠标点中激活链接 蓝色 */
.el-icon-arrow-down:before {
  // content: "\e723";
  color: #010008;
  font-size:16PX;
}
</style>
