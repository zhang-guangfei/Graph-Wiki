<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-card>
        <div>
          <el-form ref="queryCondition" :model="queryCondition" :inline="true" size="mini">
            <el-form-item label="采购单号">
              <el-input v-model="queryCondition.orderFno" placeholder="请输入采购单号" style="width:100%" clearable @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="变更类别" >
              <el-select v-model="queryCondition.modifyType" placeholder="请选择变更类别" clearable>
                <el-option
                  v-for="item in DictData.modifyType"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="处理状态" >
              <el-select v-model="queryCondition.status" placeholder="请选择处理状态" clearable>
                <el-option
                  v-for="item in DictData.status"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item label="型号">
              <el-input v-model="queryCondition.modelno" placeholder="请输入型号" clearable @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item label="申请号">
              <el-input v-model="queryCondition.applyNo" placeholder="请输入申请号" clearable @keyup.enter.native="getList" />
            </el-form-item>
            <el-form-item>
              <span class="operation-button">
                <el-button class="filter-item" type="primary" size="mini" icon="el-icon-search" @click="getList()">查询</el-button>
                <el-button class="filter-item" type="primary" size="mini" icon="el-icon-camera" @click="openBatchSearch()">批量</el-button>
                <el-tooltip effect="light" content="展开" placement="top">
                  <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
                </el-tooltip>
              </span>
            </el-form-item>
          </el-form>
        </div>
        <div v-if="searchMoreForm" class="search" style="margin-top: 7px">
          <el-form ref="form" :model="queryCondition" :inline="true" size="mini" clearable>
            <el-form-item label="营业所">
              <department style="margin-left: 0px; width: 200px" @handleScopeChange="handleScopeChange"/>
            </el-form-item>
            <!-- bug 7538,增加供应商可以多选-->
            <el-form-item label="供应商">
              <el-select
                v-model="queryCondition.supplierId"
                placeholder="请选择供应商"
                clearable>
                <el-option
                  v-for="item in suppilyList"
                  :key="item.id"
                  :label="item.name"
                  :value="item.id"/>
              </el-select>
            </el-form-item>
            <el-form-item label="运输方式" >
              <el-select v-model="queryCondition.transType" placeholder="请选择运输方式" clearable>
                <el-option
                  v-for="item in DictData.transList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"/>
              </el-select>
            </el-form-item>
            <el-form-item class="day" label="日期">
              <el-select v-model="daySelectVal" style="width: 120px" clearable placeholder="日期类别:">
                <el-option label="申请时间" value="applyTime"/>
                <el-option label="审批时间" value="handleTime" />
              </el-select>
              <el-date-picker
                v-model="selectDate"
                :picker-options="pickerOptions"
                type="daterange"
                align="right"
                unlink-panels
                style="width: 250px"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"/>
            </el-form-item>
          </el-form>
        </div>
      </el-card>
      <el-button :disabled="tableData.length === 0 || selectList.length === 0" size="mini" plain type="success" icon="el-icon-check" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="confirmData">处理</el-button>
      <el-button :disabled="tableData.length === 0 || selectList.length === 0" size="mini" plain type="danger" icon="el-icon-close" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="handelNegate">否决</el-button>
      <el-button :disabled="tableData.length === 0 || selectList.length === 0" size="mini" plain type="warning" icon="el-icon-minus" style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="handelShelve">暂不处理</el-button>
      <el-button size="mini" type="info" icon="el-icon-upload2" plain style="margin-top:5px;margin-bottom:5px;margin-right:5px;float:left;" @click="impdataBatch()">导入</el-button>
      <el-button size="mini" type="primary" icon="el-icon-download" plain style="margin-top:5px;margin-bottom:5px;float:left;" @click="openSonItemExport">导出</el-button>
      <div style="margin-top:20px;">
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '12px'}"
          :cell-style="{padding: '2px 3px'}"
          element-loading-text="拼命加载中"
          border
          fit
          highlight-current-row
          height="660"
          style="width:100%"
          @selection-change="selectChangeEvent"
          @select-all="selectAllEvent"
        >
          <el-table-column
            type="selection"
            width="55"/>
          <el-table-column label="序号" align="center" fixed="left" prop="id" min-width="70">
            <template slot-scope="scope">
              <span>{{ scope.$index + 1 }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="采购单号" align="left" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.orderFno }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="modifyTypeFormatter" prop="modifyType" show-overflow-tooltip label="变更类型" align="left" min-width="130"/>
          <el-table-column :formatter="statusFormatter" prop="status" show-overflow-tooltip label="处理状态" align="left"/>
          <el-table-column show-overflow-tooltip label="型号" prop= "modelno" align="left" min-width="140">
            <template slot-scope="scope">
              <span>{{ scope.row.modelno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="数量" prop="quantity" align="left">
            <template slot-scope="scope">
              <span>{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="deptFormatter" prop="deptNo" show-overflow-tooltip label="营业所" align="center"/>
          <el-table-column show-overflow-tooltip label="申请内容" align="left" min-width="120">
            <template slot-scope="scope">
              {{ applyContentFormatter(scope.row.modifyType,scope.row.applyContent) }}
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="变更理由" align="left" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.applyReason }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="实际采购单号" align="left" min-width="120">
            <template slot-scope="scope">
              <span v-if="scope.row.splitItemNo==null">
                {{ scope.row.orderNo+"-"+scope.row.itemNo }}
              </span>
              <span v-if="scope.row.splitItemNo!==null">
                {{ scope.row.orderNo+"-"+scope.row.itemNo+"-"+scope.row.splitItemNo }}
              </span>
            </template>
          </el-table-column>
          <el-table-column :formatter="statecodeFormatter" prop="purchaseStatecode" label="采购状态" align="center" min-width="100" />
          <el-table-column :formatter="suppilyFormatter" prop="supplierId" show-overflow-tooltip label="供应商" min-width="130" align="center"/>
          <el-table-column show-overflow-tooltip label="重量" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.netweight }}</span>
            </template>
          </el-table-column>
          <el-table-column :formatter="transTypeFormatter" prop="transType" show-overflow-tooltip label="运输方式" align="center" min-width="90"/>
          <el-table-column show-overflow-tooltip label="制造出荷日" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopeExportDate | formatDate2 }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="申请号" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.applyNo }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="客户" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.customerNo }}</span>
            </template>
          </el-table-column>
          <!--<el-table-column show-overflow-tooltip label="用户" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.userNo }}</span>
            </template>
          </el-table-column>-->
          <el-table-column show-overflow-tooltip label="最终用户" min-width="100" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.endUser }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="申请人" min-width="130" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.applyPersonNo }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="申请时间" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.applyTime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="业务处理人" min-width="130" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.handler }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="业务审批回复内容" min-width="150" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.handleResult }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="业务审批时间" min-width="140" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.handleTime | formatDate }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="日本手配号" align="left" min-width="120">
            <template slot-scope="scope">
              <span>{{ scope.row.supplierOrderno }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
    <!-- 订单号批量查询 -->
    <el-dialog
      :visible.sync="batchSearchDialog.show"
      :close-on-click-modal="false"
      title="请输入订单号，以回车为间隔"
      width="400px">
      <el-row>
        <el-input
          v-model="batchSearchDialog.text"
          :autosize="{minRows: 10 ,maxRows: 20}"
          type="textarea"
        />
      </el-row>
      <el-row style="text-align: center">
        <el-button
          type="primary"
          size="small"
          style="margin-top:5px;margin-bottom:5px;"
          @click="batchSearchEvent"
        >查询
        </el-button>
      </el-row>
    </el-dialog>
    <!--运输方式弹窗-->
    <el-dialog
      :visible.sync="transDeal.transReplyDialog"
      :close-on-click-modal="false"
      title="业务审批回复"
      width="30%"
      center>
      <el-form :inline="true" :model="transDeal.replyInfo" :rules="rules" class="demo-form-inline">
        <el-form-item label="回复内容">
          <el-input
            :rows="2"
            v-model="transDeal.replyInfo.text"
            style="width: 300px;margin-left: 15px"
            type="textarea"
            placeholder="请输入回复内容"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="text-align:center">
        <el-button size="mini" @click="transDeal.transReplyDialog = false">取 消</el-button>
        <el-button :loading="transDeal.loading" size="mini" type="primary" @click="transTypeHandelResult()">确 定
        </el-button>
      </span>
    </el-dialog>
    <!--变更供应商弹窗-->
    <el-dialog
      :visible.sync="approveReplyDeal.approveReplyDialog"
      :close-on-click-modal="false"
      title="业务审批回复"
      width="30%"
      center>
      <el-form :inline="true" :model="approveReplyDeal.replyInfo" :rules="rules" class="demo-form-inline">
        <el-form-item label="回复内容">
          <el-input
            :rows="2"
            v-model="approveReplyDeal.replyInfo.text"
            style="width: 300px;margin-left: 15px"
            type="textarea"
            placeholder="请输入回复内容"/>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="text-align:center">
        <el-button size="mini" @click="approveReplyDeal.approveReplyDialog = false">取 消</el-button>
        <el-button :loading="approveReplyDeal.loading" size="mini" type="primary" @click="suppilyHandelResult()">确 定
        </el-button>
      </span>
    </el-dialog>
    <!--采购删单弹窗-->
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
        <!-- bug 12446，加入 按钮loading限制，同时加入清空 请求参数-->
        <el-button type="primary" size="small" @click="cancelClose">取消</el-button>
        <el-button :loading="cancelDialog.loading" type="primary" size="small" @click="cancelData()">确认</el-button>
      </div>
    </el-dialog>
    <el-dialog
      :visible.sync="dialogreturnFormVisible"
      :close-on-click-modal="false"
      :title="title"
      width="30%"
      center
    >
      <el-form ref="returnForm" :inline="true" :model="returnForm" class="demo-form-inline">
        <el-form-item prop="text" label="理由">
          <el-input
            :rows="2"
            v-model="returnForm.text"
            style="width: 300px;margin-left: 15px"
            autocomplete="off"
            type="textarea"
            placeholder="请输入理由"
          />
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer" style="text-align:center">
        <el-button size="mini" @click="returnOrderClose">取 消</el-button>
        <el-button :loading="dialogreturnLoading" size="mini" type="primary" @click="returnOrderModify()">确 定
        </el-button>
      </span>
    </el-dialog>
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="importRequest.importDataDialog"
      :before-close="closeClick3"
      title="批量导入发单配置数据"
      width="400px"
      append-to-body
    >
      <div class="upload">
        <el-upload
          :action="importRequest.actionUrl"
          :before-upload="beforeUploadFile"
          class="upload-demo"
          drag
          multiple
        >
          <div class="el-upload__text">支持xlsx格式</div>
          <div v-if="importRequest.file !== null" class="el-upload__text">
            {{ importRequest.file.name }}
          </div>
          <div v-else class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
        </el-upload>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button size="small" type="success" @click="downloadTemplate('../../../static/template/downLoadTemplate/采购批量变更模板.xlsx')" >下载模板</el-button>
        <el-button :loading="importRequest.importDataLoad" size="mini" type="primary" @click="importRemarkData()">导 入
        </el-button>
        <el-button size="mini" @click="importRequest.importDataDialog = false">取 消</el-button>
      </div>
    </el-dialog>
    <pagination v-show="total>0" :total="total" :page.sync="page.pageNumber" :limit.sync="page.pageSize" @pagination="getList()" />
    <exportExcel ref="exportExcelVue" />
  </div>
</template>
<script>
import { fetchList, transTypeApprove, suppilyDataApprove, deleteApprove, negateApprove, importBatchData } from '@/api/po/purchaseModify'
import { deleteReasonOptions } from '@/api/purchaseOrder'
import { findDeptDict } from '@/api/warehouseManage'
import { getSuppily, getWarehouse } from '@/api/intercept'
import Pagination from '@/components/Pagination'
import Department from '@/components/Department'
import { getDataCodesTree } from '@/api/common/dict'
import { shortcuts } from '@/utils/dataFormat'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'PurchaseModify',
  components: { Pagination, exportExcel, Department },
  data() {
    return {
      props: { multiple: true },
      DictData: {
        type: [],
        department: [],
        status: [],
        specmark: [],
        producttag: [],
        // 取消菜单
        calcelReason: [],
        modifyType: [],
        transList: [],
        statecode: []
      },
      daySelectVal: '',
      selectDate: '',
      pickerOptions: shortcuts,
      queryCondition: {
        orderFno: '', // 采购单号
        orderNoList: [],
        applyNo: '', // 申请号
        modifyType: '', // 变更类别
        status: '0', // 处理状态
        modelno: '', // 型号
        supplierId: '', // 供应商
        transType: '', // 运输方式
        deptNo: '', // 部门代码
        applyTimeStart: '',
        applyTimeEnd: '',
        handleTimeStart: '',
        handleTimeEnd: '',
        deptNoList: []
      },
      transDeal: {
        transReplyDialog: false,
        loading: false,
        replyInfo: {
          text: '',
          batchNo: [],
          idList: [],
          optUserNo: ''
        }
      },
      approveReplyDeal: {
        approveReplyDialog: false,
        loading: false,
        replyInfo: {
          text: '',
          idList: [],
          batchNo: [],
          optUserNo: ''
        }
      },
      replyInfo: {
        text: ''
      },
      searchMoreForm: false,
      selectList: [],
      tableData: [],
      listLoading: false,
      total: 0,
      suppilyList: [],
      warehouseList: [],
      page: {
        pageNumber: 1,
        pageSize: 50
      },
      exportPage: {
        pageNumber: 1,
        pageSize: 10000
      },
      rules: {
        supplierid: [{ required: true, message: '请选择供应商', trigger: 'blur' }],
        transtype: [{ required: true, message: '请选择运输方式', trigger: 'blur' }],
        serverremark: [{ required: true, message: '请选择转订原因', trigger: 'blur' }],
        hopedeliverydate: [{ required: true, message: '请选择出库日', trigger: 'blur' }]
      },
      // 窗口：取消订单
      cancelDialog: {
        show: false, // 弹出层显示
        loading: false,
        cancelObject: {}, // 取消的对象
        reason: '',
        other: false, // 其他原因输入框显示
        otherReason: '' // 其他原因的文本
      },
      cancelCondition: {
        text: '',
        idList: [],
        batchNo: [],
        optUserNo: ''
      },
      cancelRequestData: {},
      tableDataRequest: [],
      batchSearchDialog: {
        show: false
      },
      // 否决弹窗事件
      dialogreturnFormVisible: false,
      dialogreturnLoading: false,
      title: '',
      returnForm: {
        idList: [],
        text: '',
        status: 0
      },
      // 导入需要的参数
      importRequest: {
        importDataDialog: false,
        importDataLoad: false,
        file: null,
        actionUrl: ''
      }
    }
  },
  mounted() {
    this.DictData.calcelReason = deleteReasonOptions()
  },
  created() {
    this.initData()
    this.getList()
    this.getSuppily()
    this.getWarehouseList()
  },
  methods: {
    // 处理操作的弹窗
    confirmData() {
      if (this.selectList.length > 0) {
        const filteredArray = this.selectList.filter((item, index, self) =>
          index === self.findIndex((t) => t.modifyType === item.modifyType)
        )
        const filteredArray2 = this.selectList.filter((item, index, self) =>
          index === self.findIndex((t) => t.changeType === item.changeType)
        )
        // 校验是否勾选的为同一部分订单
        if (filteredArray.length > 1 || filteredArray2.length > 1) {
          this.$message({
            type: 'warning',
            message: '批量操作时,请确保变更类别为同一类！'
          })
          return
        }
        // 变更操作分为三类
        // 1.变更采购运输方式 Moditype=T,调用后台 变更运输方式接口（目前暂不支持变更运输方式，由业务自行去变更，后台只变更状态 以及追加备注信息）
        switch (this.selectList[0].modifyType) {
          case 'T':
            this.transDeal.transReplyDialog = true
            break
          case 'C':
            this.cancelDialog.show = true
            break
          default:
            this.approveReplyDeal.approveReplyDialog = true
        }
        return
      } else {
        this.$message({
          type: 'warning',
          message: '请选择单项！'
        })
        return
      }
    },
    transTypeHandelResult() {
      this.transDeal.loading = true
      if (this.selectList.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      if (this.transDeal.replyInfo.text.length > 100) {
        this.$message.error('请控制回复内容于100个汉字之内')
        return
      }
      // 根据id,来进行处理
      this.selectList.forEach((item, index) => {
        this.transDeal.replyInfo.idList.push(item.id)
        this.transDeal.replyInfo.batchNo.push(item.batchNo)
      })
      this.transDeal.replyInfo.optUserNo = this.$store.getters.name
      // 调用后端处理运输方式方法
      transTypeApprove(this.transDeal.replyInfo).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
        this.transTypeClose()
        this.getList()
      }).catch(res => {
        this.transTypeClose()
        this.$message.error(res)
      })
    },
    transTypeClose() {
      this.transDeal.transReplyDialog = false
      this.transDeal.loading = false
      this.transDeal.replyInfo.text = ''
      this.transDeal.replyInfo.idList = []
      this.transDeal.replyInfo.batchNo = []
    },
    // 确认处理操作
    suppilyHandelResult() {
      this.approveReplyDeal.loading = true
      if (this.selectList.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      if (this.approveReplyDeal.replyInfo.text.length > 100) {
        this.$message.error('请控制回复内容于100个汉字之内')
        return
      }
      // 根据id,来进行处理
      this.selectList.forEach((item, index) => {
        this.approveReplyDeal.replyInfo.idList.push(item.id)
      })
      this.approveReplyDeal.replyInfo.optUserNo = this.$store.getters.name
      // 调用后端处理运输方式方法
      suppilyDataApprove(this.approveReplyDeal.replyInfo).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.getList()
        } else {
          this.$message.error(res.message)
        }
        this.suppilyClose()
      }).catch(res => {
        this.suppilyClose()
        this.$message.error(res)
      })
    },
    suppilyClose() {
      this.approveReplyDeal.approveReplyDialog = false
      this.approveReplyDeal.loading = false
      this.approveReplyDeal.replyInfo.text = ''
      this.approveReplyDeal.replyInfo.idList = []
      this.approveReplyDeal.replyInfo.batchNo = []
    },
    handleScopeChange(val) {
      // this.queryCondition.deptNo = val[0]
      this.queryCondition.deptNoList = val
    },
    initData() {
      // 变更类别
      getDataCodesTree('2081').then(result => {
        result.content.forEach(dict => {
          this.DictData.modifyType.push({ value: dict.code, label: dict.codeName })
        })
      }).catch(error => {
        console.log(error)
        this.$message.error(error.message)
      })
      // 处理状态
      getDataCodesTree('2082').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.status.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // 采购单
      getDataCodesTree('2033').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.statecode.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // 运输方式
      getDataCodesTree('3003').then(result => {
        if (result.code === '200') {
          result.content.forEach(dict => {
            this.DictData.transList.push({ value: dict.code, label: dict.codeName })
          })
        }
      }).catch(error => {
        console.log(error)
      })
      // 营业所
      findDeptDict().then(result => {
        result.forEach(dict => {
          this.DictData.department.push({ code: dict.deptId, desc: dict.deptName })
        })
      })
    },
    // bug11997 单条拦截不处理
    interceptHandle(row) {
      row.operator = this.$store.getters.position.psnsmcId
      this.selectList = []
      this.selectList.push(row)
      this.unHandle()
    },
    // 列表复选框改变事件
    selectChangeEvent(records) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element)
      })
    },
    // 列表全选事件
    selectAllEvent(records) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element)
      })
    },
    rowStyle({ row, rowIndex }) {
      return 'height:2px'
    },
    // 营业所字典格式化
    deptFormatter(row, column, cellValue) {
      const item = this.DictData.department.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 变更内容字段字典显示
    applyContentFormatter(modifyType, applyContent) {
      if (modifyType === 'A') {
        const item = this.suppilyList.find(dict => dict.id === applyContent)
        return item ? item.name : applyContent
      } else if (modifyType === 'T') {
        const item = this.DictData.transList.find(dict => dict.value === applyContent)
        return item ? item.label : applyContent
      } else {
        return applyContent
      }
    },
    // bug 10526 修正组装标识前端显示
    modifyTypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.modifyType.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    statecodeFormatter(row, column, cellValue, index) {
      const item = this.DictData.statecode.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
    },
    statusFormatter(row, column, cellValue, index) {
      const item = this.DictData.status.find(dict => dict.value === cellValue.toString())
      return item ? item.label : cellValue
    },
    transTypeFormatter(row, column, cellValue, index) {
      const item = this.DictData.transList.find(dict => dict.value === cellValue)
      return item ? item.label : cellValue
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
    // 查询所有数据
    getList() {
      this.listLoading = true
      this.searchTime()
      fetchList(this.queryCondition, this.page).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
          this.queryCondition.orderNoList = []
          this.listLoading = false
        }
      }).catch(error => {
        this.listLoading = false
        console.info(error)
        this.$message.error(error.message)
      })
    },
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    // 展开更多选项
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    suppilyFormatter(row, column, cellValue, index) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    // 跳转至3未处理请购单
    toUntreated() {
      // 详情跳转方法
      this.$router.push({
        path: '/purchase/untreatedQuery'
      })
    },
    // 取消订单，调用wm接口
    cancelData() {
      this.cancelDialog.loading = true
      // 根据id,来进行处理
      this.selectList.forEach((item, index) => {
        this.cancelCondition.idList.push(item.id)
        this.cancelCondition.batchNo.push(item.batchNo)
      })
      this.cancelCondition.optUserNo = this.$store.getters.position.psnsmcId
      if (this.isEmpty(this.cancelCondition.optUserNo)) {
        this.$message({
          showClose: true,
          message: '当前登录人信息为空，请重新登录后重试',
          type: 'error'
        })
        return
      }
      // bug12344 采购删单新增 删单原因，记录到删单表中
      var reason = this.cancelDialog.reason[1] === '其他' ? this.cancelDialog.otherReason : this.cancelDialog.reason[1]
      this.cancelCondition.text = reason
      deleteApprove(this.cancelCondition).then(res => {
        if (res.success) {
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
        this.cancelClose()
        this.getList()
      }).catch(error => {
        console.info(error)
        this.cancelClose()
        this.$message.error(error.message)
      })
    },
    // bug 12446，加入 按钮loading限制，同时加入清空 请求参数
    cancelClose() {
      this.cancelDialog.show = false
      this.cancelDialog.loading = false
      this.cancelDialog.reason = ''
      this.cancelDialog.other = false // 其他原因输入框显示
      this.cancelDialog.otherReason = ''
      this.cancelRequestData = {}
      this.cancelCondition.text = ''
      this.cancelCondition.idList = []
      this.cancelCondition.batchNo = []
      this.cancelCondition.optUserNo = ''
    },
    // 取消选项弹出输入框
    cancelMenu() {
      this.cancelDialog.other = this.cancelDialog.reason[1] === '其他'
    },
    getWarehouseList() {
      getWarehouse().then(res => {
        this.warehouseList = res.data
      }).catch(error => {
        console.log(error)
      })
    },
    warehouseFormatter(row, column, cellValue, index) {
      const item = this.warehouseList.find(dict => dict.warehouseCode === cellValue)
      return item ? item.warehouseName : cellValue
    },
    searchTime() {
      this.restDate()
      // 判断是否选择日期，未选择 抛异常
      if (!this.isEmpty(this.selectDate) && this.isEmpty(this.daySelectVal)) {
        this.$message({
          showClose: true,
          message: '请选择日期类别后，再选择日期',
          type: 'error'
        })
        return
      } else if (this.isEmpty(this.selectDate) && !this.isEmpty(this.daySelectVal)) {
        this.$message({
          showClose: true,
          message: '请选择日期后，再查询！',
          type: 'error'
        })
        return
      } else {
        if (this.daySelectVal === 'applyTime') {
          this.queryCondition.applyTimeStart = this.dayjs(this.selectDate[0]).format('YYYY-MM-DD')
          this.queryCondition.applyTimeEnd = this.dayjs(this.selectDate[1]).format('YYYY-MM-DD')
        } else if (this.daySelectVal === 'handleTime') {
          this.queryCondition.handleTimeStart = this.dayjs(this.selectDate[0]).format('YYYY-MM-DD')
          this.queryCondition.handleTimeEnd = this.dayjs(this.selectDate[1]).format('YYYY-MM-DD')
        }
      }
    },
    restDate() {
      this.queryCondition.applyTimeStart = ''
      this.queryCondition.applyTimeEnd = ''
      this.queryCondition.handleTimeStart = ''
      this.queryCondition.handleTimeEnd = ''
    },
    // 批量查询操作
    batchSearchEvent() {
      const text = this.batchSearchDialog.text
      if (this.isEmpty(text)) {
        this.smcInfoMsg('请填写至少一个订单号')
        return false
      }
      if (text.replace(/\s*/g, '').length < 7) {
        this.smcInfoMsg('请填写至少一个订单号')
        return false
      }
      const orderFno = text.split('\n')
      // todo,去除多余的空格
      this.queryCondition.orderNoList = orderFno
      this.batchSearchDialog.show = false
      this.getList()
      this.batchSearchDialog.text = ''
    },
    // 订单号批量查询按钮事件
    openBatchSearch() {
      this.batchSearchDialog.show = true
    },
    returnReset() {
      this.returnForm.text = ''
    },
    handelNegate() {
      this.title = '否决'
      this.dialogreturnFormVisible = true
    },
    handelShelve() {
      this.title = '暂不处理'
      this.dialogreturnFormVisible = true
    },
    returnOrderModify() {
      if (this.selectList.length === 0) {
        this.$message({
          type: 'warning',
          message: '请选中列表数据'
        })
        return
      }
      if (this.returnForm.text.length > 100) {
        this.$message.error('请控制回复内容于100个汉字之内')
        return
      }
      this.returnForm.idList = []
      this.selectList.forEach((item, index) => {
        this.returnForm.idList.push(item.id)
      })
      this.returnForm.optUserNo = this.$store.getters.name
      this.dialogreturnLoading = true
      if (this.title === '否决') {
        this.returnForm.status = 7
      } else if (this.title === '暂不处理') {
        this.returnForm.status = 8
      }
      negateApprove(this.returnForm).then(res => {
        if (res.success) {
          this.$message.success(res.message)
        } else {
          this.$message.error(res.message)
        }
        this.returnOrderClose()
        this.getList()
      }).catch(error => {
        this.returnOrderClose()
        this.$message.error(error + '请稍后再尝试')
      })
    },
    returnOrderClose() {
      this.returnForm.text = ''
      this.dialogreturnFormVisible = false
      this.dialogreturnLoading = false
    },
    // 日期格式转换
    dateFormatter(val) {
      if (val === null) {
        return
      }
      var value = new Date(val)
      var year = value.getFullYear()
      var padDate = function(va) {
        va = va < 10 ? '0' + va : va
        return va
      }
      var month = padDate(value.getMonth() + 1)
      var day = padDate(value.getDate())
      // var hour = padDate(value.getHours())
      // var minutes = padDate(value.getMinutes())
      // var seconds = padDate(value.getSeconds())
      return year + '-' + month + '-' + day
      // + ' ' + hour + ':' + minutes + ':' + seconds
    },
    // bug 10558 导出营业所别翻译成中文
    deptnoTrans(val) {
      const item = this.DictData.department.find(dict => dict.code === val)
      return item ? item.desc : val
    },
    openSonItemExport() {
      const tableColumn = [
        { field: 'modifyType', title: '变更类别' },
        { field: 'orderFno', title: '采购单号' },
        { field: 'orderNo', title: '实际采购单号' }, // bug 16092 采购修改界面导出增加实际采购单号字段
        { field: 'status', title: '处理状态' },
        { field: 'applyNo', title: '申请号' },
        { field: 'applyContent', title: '申请变更内容' },
        { field: 'applyReason', title: '申请理由' },
        { field: 'applyPersonNo', title: '申请人' },
        { field: 'applyTime', title: '申请时间' },
        { field: 'modelno', title: '型号' },
        { field: 'quantity', title: '数量' },
        { field: 'deptNo', title: '营业所' },
        { field: 'customerNo', title: '客户代码' },
        { field: 'purchaseStatecode', title: '采购单状态' },
        { field: 'supplierId', title: '供应商' },
        { field: 'netweight', title: '单重' },
        { field: 'transType', title: '运输方式' },
        { field: 'hopeExportDate', title: '工厂出荷日' },
        { field: 'supplierOrderno', title: '日本手拍号' }
      ]
      this.searchTime()
      // this.exportPage.pageNumber = this.page.pageNumber
      // this.exportPage.pageSize = this.page.pageSize
      fetchList(this.queryCondition, this.exportPage).then(res => {
        if (res.success) {
          res.content.list.forEach((item) => {
            item.deptno = this.deptnoTrans(item.deptno)
            item.applyTime = this.dateFormatter(item.applyTime)
            item.hopeExportDate = this.dateFormatter(item.hopeExportDate)
            // bug 16092 采购修改界面导出增加实际采购单号字段
            if (item.splitItemNo == null) {
              item.orderNo = item.orderNo + '-' + item.itemNo
            } else {
              item.orderNo = item.orderNo + '-' + item.itemNo + '-' + item.splitItemNo
            }
          })
          this.$refs.exportExcelVue.initExportExcel(res.content.list, tableColumn)
        } else {
          this.$message({
            type: 'warning',
            message: res.message
          })
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    // 导入功能
    impdataBatch() {
      this.importRequest.importDataDialog = true
      this.importRequest.file = null
    },
    closeClick3() {
      this.importRequest.file = null
      this.importRequest.importDataDialog = false
      this.importRequest.importDataLoad = false
    },
    beforeUploadFile(file) {
      this.importRequest.file = file
      return false
    },
    importRemarkData() {
      this.importRequest.importDataLoad = true
      if (this.importRequest.file == null) {
        this.importRequest.importDataLoad = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.importRequest.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        importBatchData(formData).then(res => {
          this.importRequest.importDataLoad = false
          this.importRequest.file = null
          if (res.success) {
            this.$notify({
              title: '导入成功',
              message: res.message,
              type: 'success'
            })
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
          this.getList()
          this.importRequest.importDataDialog = false
        }).catch(error => {
          this.closeClick3()
          this.$message.error('导入失败:' + error)
        })
      }
    },
    downLoadHistorySampleOrderManageExcel() {
      const tableColumn = [
        { field: 'orderFno', title: '采购单号' },
        { field: 'modifyType', title: '变更类别' },
        { field: 'applyContent', title: '申请内容' },
        { field: 'applyReason', title: '申请理由' }
      ]
      this.exportPage.pageNumber = this.page.pageNumber
      this.exportPage.pageSize = 1
      fetchList(this.queryCondition, this.exportPage).then(res => {
        if (res.success) {
          res.content.list.forEach((item) => {
            item.orderFno = 'XXX-X'
            item.modifyType = '变更供应商'
            item.applyContent = ''
            item.applyReason = '变更类别为-变更供应商-时：内容填写供应商代码，变更类别为-变更指定工厂出荷日-时：内容需填写变更日期，变更类别为-删除采购单-时:内容可不填写；请覆盖此行数据！！！'
          })
          this.$refs.exportExcelVue.initExportExcel(res.content.list, tableColumn)
          this.$refs.exportExcelVue.drawer.filename = '采购批量变更模板'
          this.$refs.exportExcelVue.drawerVisible = false
          this.$refs.exportExcelVue.handleDownload()
        }
      }).catch(error => {
        console.log(error)
      })
    },
    // 模板下载
    downloadTemplate(url) {
      const index = url.lastIndexOf('/') + 1
      const link = document.createElement('a')
      link.style.display = 'none'
      link.href = url
      link.download = url.substring(index)
      link.click()
      this.$message({
        message: `正在下载~`,
        type: 'success'
      })
    }
  }
}
</script>
<style scoped>
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
