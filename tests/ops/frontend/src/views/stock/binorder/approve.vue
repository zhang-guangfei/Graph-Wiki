<template>
  <div class="app-container">
    <div>
      <el-form ref="form" :inline="true" :model="form" size="small" label-width="100px">
        <el-form-item>
          <el-select v-model="form.applyType" placeholder="申请类型" style="width: 100px" size="mini" clearable @change="initData">
            <el-option
              v-for="item in applyTypes"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"
            />
          </el-select>
          <el-select v-model="form.status" placeholder="状态" style="width: 120px" size="mini" clearable @change="initData">
            <el-option
              v-for="item in statusdesc"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.appId" size="mini" style="width: 80px" placeholder="申请号" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="form.calcId" size="mini" style="width: 80px" placeholder="计算号" />
        </el-form-item>
        <el-form-item>
          <el-select v-model="form.warehouseCode" placeholder="仓库" size="mini" clearable @change="initData">
            <el-option
              v-for="item in warehouses"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"
            />
          </el-select>
          <el-button type="primary" size="mini" @click="selectWarehouse">选择</el-button>
        </el-form-item>
        <!-- <el-form-item>
          <el-input v-if="form.applyType==4" v-model="form.customerNo" style="width: 90px" size="mini" placeholder="客户" clearable />
          <el-input v-if="form.applyType==4" v-model="form.propertyID" style="width: 90px" size="mini" placeholder="属性ID" clearable />
          <el-button v-if="form.applyType==4" plain type="success" size="mini" @click="showbindata">+</el-button>
        </el-form-item> -->
        <el-form-item>
          <span>申请日期</span>
          <el-date-picker
            v-model="form.fromdate"
            type="date"
            size="mini"
            placeholder="开始时间"
            value-format="yyyy-MM-dd"
            style="width: 130px"
          />
          <el-date-picker
            v-model="form.todate"
            type="date"
            size="mini"
            placeholder="结束时间"
            value-format="yyyy-MM-dd"
            style="width: 130px"
          />
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['732139']" icon="el-icon-search" title="搜索" size="mini" @click="initData">查询</el-button>
        </el-form-item>
      </el-form>
      <el-divider />
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
        :visible.sync="dialogapproveVisible"
        :close-on-click-modal="false"
        title="审批"
        width="420px"
      >
        <el-form ref="aproveForm" :model="aproveForm">
          回复说明
          <el-form-item prop="price">
            <el-input
              :rows="4"
              v-model="aproveForm.reason"
              style="width:380px"
              autocomplete="off"
              type="textarea"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button style="float:left" @click="dialogapproveVisible = false;approveData(false)">退回</el-button>
          <el-button
            type="primary"
            @click="dialogapproveVisible = false;approveData(true)"
          >同意</el-button>
        </div>
      </el-dialog>
      <!-- 仓库选择弹窗 -->
      <el-dialog :visible.sync="dialogFormVisible" title="仓库" width="650px">
        <el-form ref="form" :inline="true" :model="warehouseForm" size="small">
          <el-form-item >
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
          </el-form-item>
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
      <!-- <el-row>
        <el-button-group>
          <el-button
            type="success"
            icon="el-icon-check"
            title="审批"
            size="medium"
          />
          <el-button
            type="warning"
            icon="el-icon-shopping-cart-full"
            title="生成订单"
            size="medium"
          />
        </el-button-group>
      </el-row>-->
      <el-row class="row-button">
        <el-button v-permission="['966764']" type="primary" size="mini" @click="conforn()">审批</el-button>
        <el-button v-permission="['362393']" type="primary" size="mini" @click="createOrder()">
        <span v-loading="createOrdering" element-loading-text="生成订单.." element-loading-spinner="el-icon-loading">{{ "生成订单" }}</span></el-button>
        <el-button v-permission="['362393']" type="primary" size="mini" @click="sendToInStock()">
        <span v-loading="sending" element-loading-text="审批.." element-loading-spinner="el-icon-loading">{{ "推送给门户补库" }}</span></el-button>
      </el-row>

      <!--申请表-->
      <el-table
        ref="multipleTable"
        :data="appData"
        :cell-style="{ padding: '5px' }"
        size="mini"
        border
        stripe
        style="width: 100%;margin-top: 5px"
        height="60vh"
        @selection-change="handleSelection"
      >
        <!-- 表头字段 -->
        <el-table-column type="selection" width="55" />

        <el-table-column label="申请号">
          <template slot-scope="scope">
            <el-link type="primary" @click="querycalculate(scope.row)">{{ scope.row.appId }}</el-link>
          </template>
        </el-table-column>
        <el-table-column
          v-for="column in applyTableHeader"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
        />
        <!--操作栏 -->
        <!-- <el-table-column label="操作" width="140px">
          <template slot-scope="scope">
            <el-button
              type="primary"
              size="mini"
              @click="showApplyDetail(scope.row)"
            >审批</el-button
            >
          </template>
        </el-table-column>-->
      </el-table>
      <!-- 分页工具 -->
      <pagination
        v-show="form.total > 0"
        :total="form.total"
        :page-sizes="[20, 50, 100, 200, 500]"
        :page.sync="form.pageNum"
        :limit.sync="form.pageSize"
        @pagination="initData"
      />
      <el-divider />
    </div>
  </div>
</template>

<script>
import { getDictDataByPid, listWarehouse, listWarehouseNoWT } from '@/api/common/dict'
import moment from 'moment'
import { listBinOrderApp, approveBinOrder, createOrder, sendToInStock, listCstmBindata } from '@/api/stock/binorder'
import Pagination from '@/components/Pagination'

export default {
  components: { Pagination },
  data() {
    return {
      applyTypes: [],
      warehoseCodeoption: [],
      multipleSelection: [],
      statusdesc: [],
      warehouses: [],
      dialogapproveVisible: false,
      dialogFormVisible: false,
      dialogbinDataVisible: false,
      createOrdering: false,
      sending: false,
      bindata: [],
      Inventoryproperty: '',
      warehouseForm: {
        warehouseCode: '',
        warehouseName: '',
        warehouseType: ''
      },
      warehousetypeList: [],
      warehouseData: [],
      aproveForm: {
        reason: ''
      },
      form: {
        applyType: '1',
        appId: '',
        calcId: '',
        warehouseCode: 'KBJ',
        fromdate: '',
        todate: '',
        status: '',
        customerNo: '',
        propertyID: '',
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
      // 申请表头
      applyTableHeader: [
        {
          label: '计算号',
          prop: 'calcId'
        },
        {
          label: '仓库代码',
          prop: 'warehouseCode'
        },
        {
          label: '库存类别',
          prop: 'stockType',
          formatter: this.stockTypeformatter
        },
        {
          label: '状态',
          prop: 'status',
          formatter: this.statusformatter
          //   formatter: this.statusFormatter
        },
        {
          label: '型号数',
          prop: 'modelCount'
          //   formatter: this.typeFormatter
        },
        {
          label: '数量',
          prop: 'modelQty'
          //   formatter: this.typeFormatter
        },
        {
          label: '金额',
          prop: 'eamount'
          //   formatter: this.typeFormatter
        },
        {
          label: '申请内容',
          prop: 'applyText'
        },
        // {
        //   label: '客户',
        //   prop: 'customerNo'
        // },
        // {
        //   label: '库存属性ID',
        //   prop: 'propertyId'
        // },
        {
          label: '申请人',
          prop: 'appUser'
        },
        {
          label: '申请时间',
          prop: 'applyTime',
          formatter(row, column, cellValue, index) {
            return cellValue == null ? '无' : moment(cellValue).format('YYYY-MM-DD')
          }
        },
        {
          label: '批示',
          prop: 'approveText'
        },
        {
          label: '审批人',
          prop: 'approveUser'
        }
      ],
      classCode1: 2010,
      classCode2: 2012,
      appData: [],
      // 表格数据
      applyData: {
        pageNum: 0,
        pageSize: 0,
        total: 0,
        data: [
          {
            modelNo: 'CM87-HJU-C',
            quanty: 3,
            userMan: 3,
            remark: '普陀区'
          }
        ]
      }
    }
  },
  created() {
    this.initData()
    // this.initwarehouseType()
    this.initsdesc()
    this.listWarehouseinfo()
  },

  methods: {
    querycalculate(row) {
      this.$router.push('')
      this.$router.push({
        name: 'BinOrderCalc',
        query: {
          active: 'calculatePage',
          appid: row.appId,
          calcId: row.calcId
        }
      })
    },
    conforn() {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      this.dialogapproveVisible = true
    },
    createOrder() {
      if (this.createOrdering) {
        this.$message.error('不要重复点击')
        return
      }
      this.createOrdering = true
      if (this.multipleSelection.length !== 1) {
        this.$message({
          type: 'warning',
          message: '请选择一条需要处理的数据'
        })
        this.createOrdering = false
        return
      }
      const appIds = []
      this.multipleSelection.forEach((item, index) => {
        appIds.push(item.appId)
      })
      const data = {
        appIds: appIds
      }
      createOrder(data)
        .then(res => {
          if (res.success) {
            this.initData()
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
          this.createOrdering = false
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
          this.createOrdering = false
        })
    //   this.$message.error('生成订单失败')
    },
    sendToInStock() {
      if (this.sending) {
        this.$message.error('不要重复点击')
        return
      }
      this.sending = true
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        this.sending = false
        return
      }
      const appIds = []
      this.multipleSelection.forEach((item, index) => {
        appIds.push(item.appId)
      })
      const data = {
        appIds: appIds
      }
      sendToInStock(data)
        .then(res => {
          if (res.success) {
            this.initData()
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
          this.sending = false
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
          this.sending = false
        })
    },
    initsdesc() {
      getDictDataByPid('2028').then(result => {
        this.statusdesc = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('4001').then(result => {
        this.warehousetypeList = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid('2012').then(result => {
        this.warehoseCodeoption = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.classCode1).then(result => {
        this.applyTypes = result.content
      }).catch(error => {
        console.log(error)
      })
      listWarehouseNoWT().then(result => {
        this.warehouses = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    // 菜单数据初始化
    initData() {
    //   if (this.form.applyType !== '4') {
    //     this.form.customerNo = ''
    //     this.form.propertyID = 0
    //   }
      listBinOrderApp(this.form).then(result => {
        this.appData = result.content.list
        this.form.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    handleSelection(val) {
      this.multipleSelection = val
    },
    approveData(agree) {
      if (this.multipleSelection.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      const appIds = []
      this.multipleSelection.forEach((item, index) => {
        appIds.push(item.appId)
      })
      //   if (this.aproveForm.reason === null || this.aproveForm.reason === '') {
      //     this.$message.error('审批回复不能为空')
      //     return
      //   }
      const data = {
        appIds: appIds,
        pass: agree,
        approveText: this.aproveForm.reason
      }
      approveBinOrder(data)
        .then(res => {
          if (res.success) {
            this.initData()
            this.$message.success(res.content)
            this.dialogapproveVisible = false
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    showApplyDetail(row) { },

    // 获取表格数据
    getapplyData() {
      console.log('查询条件：')
    },
    selectWarehouse() {
      this.dialogFormVisible = true
    },
    listWarehouseinfo() {
      const formData = {
        warehouseCode: this.warehouseForm.warehouseCode,
        warehouseType: this.warehouseForm.warehouseType,
        keywords: this.warehouseForm.warehouseName
      }
      listWarehouse(formData).then(res => {
        this.warehouseData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    edit(row) {
      this.form.warehouseCode = row.warehouseCode
      this.warehoseCodeoption = []
      const list = { code: row.warehouseCode, codeName: row.warehouseName }
      this.warehoseCodeoption.push(list)
      this.dialogFormVisible = false
    },
    showbindata() {
      this.dialogbinDataVisible = true
      this.searchbinData()
    },
    searchbinData() {
      listCstmBindata(this.binDataform).then(result => {
        this.bindata = result.content.list
        this.binDataform.total = result.content.total
      }).catch(error => {
        console.log(error)
      })
    },
    selectbinData(row) {
      this.form.propertyID = row.propertyID
      this.form.customerNo = row.customerNo
      this.dialogbinDataVisible = false
    },
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    // 改变每页条数
    handlePageSizeChange(newSize) {
      // this.queryCondition.pageSize = newSize
      this.getapplyData()
    },
    statusformatter(row) {
      return this.descFormatter(this.statusdesc, row.status)
    },
    stockTypeformatter(row) {
      return this.descFormatter(this.applyTypes, row.stockType)
    },
    descFormatter(data, id) {
      if (data === null) {
        return id
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === (id === null ? '' : id.toString())) {
          return item.codeName
        }
      }
      return id
    },
    // 换页
    handlePageIndexChange(newCurrent) {
      this.getapplyData()
    },
    // statusFormatter(row, column, cellValue, index, menu) {
    //   return this.statusData.find(item => item.code === cellValue).desc
    // },
    handleTabClick(tab, event) {
      console.log(tab, event)
    }
  }
}
</script>
