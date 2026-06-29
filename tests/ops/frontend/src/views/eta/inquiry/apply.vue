<template>
  <div v-loading="editQueryLoading" class="order-edit-class base-index" element-loading-text="加载中">
    <div class="list-content">
      <el-card shadow="hover">
        <el-descriptions :column="4" size="mini" border>
          <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
            <template slot="label">
              <i class="el-icon-user"/>
              申请人
            </template>
            {{ '【' + applyForm.inquiryUser + '】' + applyForm.inquiryUserName }}
          </el-descriptions-item>
          <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
            <template slot="label">
              <i class="el-icon-office-building" />
              申请部门
            </template>
            {{ applyForm.inquiryDept }}
          </el-descriptions-item>
          <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
            <template slot="label">
              <i class="el-icon-time" />
              申请日期
            </template>
            {{ applyForm.inquiryTime }}
          </el-descriptions-item>
          <el-descriptions-item label-class-name="my-label" content-class-name="my-content">
            <template slot="label">
              <i class="el-icon-connection" />
              催促类型
            </template>
            <el-select v-model="applyForm.inquiryType" size="mini" placeholder="请选择催促类型" @change="typeSelectChange">
              <el-option
                v-for="item in DictData.inquirytype"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-descriptions-item>
        </el-descriptions>
      </el-card>
      <div class="hx-div" style="float: clear;margin-top: 0px">
        <div class="base-h3" style="margin-bottom:0px;padding-bottom:0px;margin-top:10px">
          <el-form :inline="true" size="mini">
            <!-- <el-form-item>
              <div style="font-weight:bold;width:150px;font-size:14px">明细信息</div>
            </el-form-item> -->
            <el-form-item>
              <el-button size="mini" type="primary" @click="insertEvent()">新增一行</el-button>
            </el-form-item>
            <el-form-item style="margin-left:10px">
              <el-button size="mini" type="primary" @click="batchImport()">批量导入</el-button>
            </el-form-item>
            <el-form-item>
              <el-button :disabled="checkboxSelected.length === 0" style="margin-left:10px" size="mini" type="primary" @click="deleteModelNoBatch()">删除</el-button>
            </el-form-item>
            <el-form-item>
              <el-button :disabled="tableData.length === 0 && orderTableData.length=== 0 " style="margin-left:10px" size="mini" type="primary" @click="submit()">提交</el-button>
            </el-form-item>
          </el-form>
        </div>
      </div>
      <div>
        <vxe-grid
          v-if="applyForm.inquiryType === '0'"
          ref="inquiryApplyTable"
          :loading="inquiryTableLoading"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)'}"
          :align="allAlign"
          :data="inquiryApplyList"
          :columns="inquiryApplyColumns"
          :edit-rules="validRules"
          :edit-config="{trigger: 'click', mode: 'row'}"
          :checkbox-config="{highlight: true, range: true}"
          :pager-config="tablePage"
          border
          resizable
          auto-resize
          highlight-current-row
          highlight-hover-row
          highlight-hover-column
          show-overflow
          footer-align="center"
          stripe
          size="small"
          class="mytable-scrollbar"
          @checkbox-all="selectAllEvent"
          @checkbox-change="selectChangeEvent"
          @page-change="handlePageChange">
          <template v-slot:operate="scope">
            <el-tooltip placement="top-start" content="删除">
              <el-button icon="el-icon-delete" size="mini" type="primary" circle @click="deleteRow(scope)"/>
            </el-tooltip>
          </template>
          <template v-slot:OrderNo_edit="{ row }">
            <el-input v-model="row.orderNo" placeholder="请输入业务单号" type="text" size="mini" @change="findOrderData(row)" />
          </template>
          <template v-slot:ReasonParam_edit="{ row }">
            <span v-if="row.reasonParamDisabled">
              <el-input-number v-if="row.reasonParamType === 'NUMBER'" v-model="row.inquiryReasonParam" :min="0" controls-position="right" size="mini"/>
              <el-date-picker
                v-else-if="row.reasonParamType === 'DATE'"
                v-model="row.inquiryReasonParam"
                :picker-options="pickerOptions"
                align="right"
                type="date"
                size="mini"
                placeholder="选择日期"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd"
                @change="dataPickerchange(row)"/>
              <el-input v-else v-model="row.inquiryReasonParam" placeholder="请输入参数" type="text" size="mini"/>
            </span>
          </template>
          <template v-slot:DeliveryDate_edit="{ row }">
            <el-date-picker
              v-model="row.hopeDeliveryDate"
              :picker-options="pickerOptions"
              :disabled= "row.deliveryDateEdit"
              align="right"
              type="date"
              size="mini"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"/>
          </template>
          <template v-slot:validateType="{ row }">
            <!--通过0-->
            <span v-if="row.validateType === 0 ">
              <el-tooltip :content="row.validateDescription" placement="top-start" >
                <i class="el-icon-success" style="color: #67C23A; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
            <!-- 错误单号1 -->
            <span v-else-if="row.validateType === 1 ">
              <el-tooltip :content="row.validateDescription" placement="top-start">
                <i class="el-icon-error" style="color: #F56C6C; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
            <!-- 重复单号2 -->
            <span v-else-if="row.validateType === 2 ">
              <el-tooltip :content="row.validateDescription" placement="top-start">
                <i class="el-icon-warning" style="color: #FFC400; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
            <!-- 不可催促3 -->
            <span v-else-if="row.validateType === 3 ">
              <el-tooltip :content="row.validateDescription" placement="top-start">
                <i class="el-icon-question" style="color: #F56C6C; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
          </template>
          <template v-slot:assembleSelect="{ row }">
            <el-radio-group v-model="row.assemble" size="mini">
              <el-radio v-for="item in assembleList" :label="item.label" :key="item.value">{{ item.label }}</el-radio>
            </el-radio-group>
          </template>
        </vxe-grid>
        <!--订单催促表格 -->
        <vxe-grid
          v-if="applyForm.inquiryType === '1'"
          ref="inquiryOrderApplyTable"
          :loading="orderMainTableLoading"
          :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)'}"
          :align="allAlign"
          :data="orderInquiryApplyList"
          :columns="orderMainApplyColumns"
          :edit-rules="validRules"
          :edit-config="{trigger: 'click', mode: 'row'}"
          :checkbox-config="{highlight: true, range: true}"
          :pager-config="orderTablePage"
          :expand-config="{accordion: true, lazy: true, loadMethod: toggleExpandMethod}"
          border
          resizable
          auto-resize
          highlight-current-row
          highlight-hover-row
          highlight-hover-column
          show-overflow
          footer-align="center"
          stripe
          size="small"
          class="mytable-scrollbar"
          @checkbox-all="selectAllEvent"
          @checkbox-change="selectChangeEvent"
          @page-change="handlePageChangeOrder"
        >
          <template v-slot:orderOperate="scope">
            <el-tooltip placement="top-start" content="删除">
              <el-button icon="el-icon-delete" size="mini" type="primary" circle @click="deleteRow(scope)"/>
            </el-tooltip>
          </template>
          <template v-slot:orderInquiry_OrderNo_edit="{ row }">
            <el-input v-model="row.orderNo" placeholder="请输入业务单号" type="text" size="mini" @change="findOrderInquiryData(row)" />
          </template>
          <template v-slot:orderInquiry_ReasonParam_edit="{ row }">
            <span v-if="row.reasonParamDisabled">
              <el-input-number v-if="row.reasonParamType === 'NUMBER'" v-model="row.inquiryReasonParam" :min="0" controls-position="right" size="mini"/>
              <el-date-picker
                v-else-if="row.reasonParamType === 'DATE'"
                v-model="row.inquiryReasonParam"
                :picker-options="pickerOptions"
                align="right"
                type="date"
                size="mini"
                placeholder="选择日期"
                format="yyyy 年 MM 月 dd 日"
                value-format="yyyy-MM-dd"
                @change="dataPickerchange(row)"/>
              <el-input v-else v-model="row.inquiryReasonParam" placeholder="请输入参数" type="text" size="mini"/>
            </span>
          </template>
          <template v-slot:orderInquiry_DeliveryDate_edit="{ row }">
            <!-- :disabled= "row.deliveryDateEdit" -->
            <el-date-picker
              v-model="row.hopeDeliveryDate"
              :picker-options="pickerOptions"
              align="right"
              type="date"
              size="mini"
              placeholder="选择日期"
              format="yyyy 年 MM 月 dd 日"
              value-format="yyyy-MM-dd"
              @change="orderDeliveryDateVerify(row)"/>
          </template>
          <template v-slot:validateType="{ row }">
            <!--通过0-->
            <span v-if="row.validateType === 0 ">
              <el-tooltip :content="row.validateDescription" placement="top-start" >
                <i class="el-icon-success" style="color: #67C23A; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
            <!-- 错误单号1 -->
            <span v-else-if="row.validateType === 1 ">
              <el-tooltip :content="row.validateDescription" placement="top-start">
                <i class="el-icon-error" style="color: #F56C6C; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
            <!-- 重复单号2 -->
            <span v-else-if="row.validateType === 2 ">
              <el-tooltip :content="row.validateDescription" placement="top-start">
                <i class="el-icon-warning" style="color: #FFC400; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
            <!-- 不可催促3 -->
            <span v-else-if="row.validateType === 3 ">
              <el-tooltip :content="row.validateDescription" placement="top-start">
                <i class="el-icon-question" style="color: #F56C6C; cursor: pointer; font-size: 12px;"/>
              </el-tooltip>
            </span>
          </template>
          <!-- 添加子列表 -->
          <template v-slot:expand="{ row }">
            <el-form label-position="left" label-suffix=":" inline size="small" class="demo-table-expand">
              <vxe-grid
                v-if="row.inquiryOrderDetails && row.inquiryOrderDetails.length > 0"
                :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)'}"
                :expand-config="{key: 'orderNo'}"
                :data="row.inquiryOrderDetails"
                :columns="orderDetailColumns"
                :loading="childrenLoading"
                :align="allAlign"
                border
                show-overflow
                footer-align="center"
                stripe
                size="small"
                class="detailtable-scrollbar"
                style="margin-left: 180px;">
                <!-- 插槽定义 -->
                <template v-slot:status-slot="{ row }">
                  <span :style="{ color: row.canPress ? 'green' : 'red' }">
                    {{ row.canPress ? '是' : '否' }}
                  </span>
                </template>
              </vxe-grid>
            </el-form>
          </template>
        </vxe-grid>
      </div>
      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="importDataDialog"
        :before-close="closeClick3"
        title="批量导入催促订单数据"
        width="400px"
        append-to-body
      >
        <div class="upload">
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
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
        <div slot="footer" class="dialog-footer">
          <el-button size="small" type="success" @click="downloadTemplate('../../../static/template/downLoadTemplate/OPS问询业务批量导入模板.xlsx')">下载模板</el-button>
          <el-button :loading="importDataLoad" size="mini" type="primary" @click="importBatchDatas()">导 入</el-button>
          <el-button size="mini" @click="importDataDialog = false">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
// import { getDataCodesTree } from '@/api/common/dict'
import {
  getOrderData, addInquiryData, importBatchData, creatApplyNo, getAllReason, getReasonBySuppily,
  getReasonParam, getOrderInquiryVerify, addInquiryOrderData, importOrderBatchData, deliveryDataVerify } from '@/api/inquiry/apply'
export default {
  name: 'InquiryApply',
  data() {
    const orderNoValid = ({ row, cellValue }) => {
      return new Promise((resolve, reject) => {
        const status = row.validateType
        if (status && status === 1) {
          reject(new Error('错误的业务单号'))
          return
        }
        if (status && status === 2) {
          reject(new Error('业务单号不能重复'))
          return
        }
        if (status && status === 3) {
          reject(new Error(row.validateDescription))
          return
        }
        resolve()
      })
    }
    const hopeDeliveryDateValid = ({ row, cellValue }) => {
      return new Promise((resolve, reject) => {
        const hopedate = row.hopeDeliveryDate
        const currentDate = new Date()
        const dlvModdate = row.dlvModdate
        if (new Date(hopedate) < currentDate) {
          reject(new Error('期望货期必须大于当前日期'))
          return
        }
        // 催促日期是否超过一年？
        if (new Date(hopedate) > new Date(currentDate.getFullYear() + 1, currentDate.getMonth(), currentDate.getDate())) {
          reject(new Error('当前期望货期设置超过一年，请重新选择'))
          return
        }
        // 返信纳期的校验
        // bug 14504,期望货期与返信日期做对比,返信日期小于催促日期时，弹窗提示
        if (!this.isEmpty(dlvModdate) && this.isValidDate(hopedate) && this.isValidDate(dlvModdate) && new Date(dlvModdate) < new Date(hopedate)) {
          const hopedateVerify = row.hopeDeliveryDateVerify
          // 校验与上次填写的日期是否相同，则不再弹窗提示
          if (this.isEmpty(hopedateVerify) || +new Date(hopedate) !== +new Date(hopedateVerify)) {
            this.$confirm('催促日期晚于返信日期，是否确定继续催促?', '提示', {
              confirmButtonText: '确定',
              cancelButtonText: '取消',
              type: 'warning'
            }).then(() => {
              row.hopeDeliveryDateVerify = hopedate
              console.log(hopedate + '催促日期晚于返信日期,继续保存催促')
            }).catch(() => {
              row.hopeDeliveryDateVerify = hopedate
              row.hopeDeliveryDate = null
              console.log(hopedate + '催促日期晚于返信日期,取消保存催促')
              this.$message({ type: 'info', message: '请重新选择期望货期' })
              return
            })
          }
        }
        if (this.applyForm.inquiryType === '0' && row.inquiryReasonType === '35' && row.inquiryReasonParam !== null) {
          const date1 = this.dayjs(row.inquiryReasonParam)
          const date2 = this.dayjs(hopedate)
          if (!(date1.isSame(date2, 'day'))) {
            console.log(date1 + '--' + date2)
            reject(new Error('当前期望货期必须与日期参数相同'))
            return
          }
        }
        resolve()
      })
    }
    const reasonParamValid = ({ row, cellValue }) => {
      return new Promise((resolve, reject) => {
        const paramValue = row.inquiryReasonParam
        const paramType = row.reasonParamType
        const qty = row.quantity
        if (!this.isEmpty(paramType)) {
          if (this.isEmpty(paramValue)) {
            reject(new Error('催促原因参数不能为空，请补充！'))
            return
          }
          const isFixedLength = row.isFixedLength
          const dataLength = parseInt(row.dataLength)
          if (isFixedLength && dataLength !== paramValue.length) {
            reject(new Error('催促原因参数数据超出配置长度，请重新输入！'))
            return
          }
          if (!isFixedLength && paramValue.length > dataLength) {
            reject(new Error('催促原因参数数据超出配置长度，请重新输入！'))
            return
          }
          if (paramType === 'NUMBER') {
            if (paramValue === 0) {
              reject(new Error('催促分纳，催促数量不可为0'))
              return
            }
            if (paramValue >= qty) {
              reject(new Error('催促分纳，催促数量不能等于或超出订单数量'))
              return
            }
          }
        }
        resolve()
      })
    }
    return {
      applyForm: {
        inquiryType: '0' // 催促类别，默认为采购催促
      },
      DictData: {
        inquiryReasonTypeAll: [],
        inquiryReasonType: [],
        inquiryReasonParam: [],
        inquirytype: [
          {
            value: '0',
            label: '采购催促'
          },
          {
            value: '1',
            label: '订单催促'
          }
        ]
      },
      inquiryReasonTypeList: [],
      orderNoList: [],
      orderNoInquiryList: [], // 订单催促订单号集合
      editQueryLoading: false,
      allAlign: 'center',
      inquiryTableLoading: false,
      inquiryApplyList: [],
      tableData: [], // 列表数据
      checkboxSelected: [], // 复选框数据
      inquiryApplyColumns: [
        { fixed: 'left', type: 'checkbox', width: 40 },
        { field: 'operate', title: '操作', fixed: 'left', width: 60, slots: { default: 'operate' }},
        { field: 'itemNo', title: '序号', fixed: 'left', width: 50 },
        { field: 'inquiryApplyNo', title: '申请号', fixed: 'left', width: 120, sortable: true },
        { field: 'orderNo', title: '业务单号', fixed: 'left', minWidth: 120, editRender: {}, slots: { edit: 'OrderNo_edit' }, showOverflow: true, sortable: true },
        { field: 'validateType', title: '*', width: 36, slots: { default: 'validateType' }},
        { field: 'modelNo', title: '型号', minWidth: 120, showOverflow: true, sortable: true },
        { field: 'quantity', title: '数量', minWidth: 60, showOverflow: true },
        { field: 'replyDept', title: '供应商', minWidth: 80 },
        { field: 'customerNo', title: '客户', minWidth: 80 },
        { field: 'endUser', title: '最终用户', minWidth: 80 },
        { field: 'purchaseNo', title: '客户PO', minWidth: 100, showOverflow: true },
        { field: 'orderStatus', title: '订单状态', minWidth: 80, showOverflow: true },
        { field: 'hopeExportDate', title: '指定出荷日', minWidth: 100, sortable: true },
        { field: 'dlvmoddateStr', title: '当前返信纳期', minWidth: 100 },
        { field: 'dlvModdate', title: '返信纳期', minWidth: 100, visible: false },
        // { field: 'purchaseStatus', title: '采购状态', minWidth: 100, sortable: true },
        // editRender: { name: '$input', props: { type: 'date', disabledMethod: this.disabledDateMethod }}
        { field: 'hopeDeliveryDate', title: '期望出库日', minWidth: 130, editRender: {}, slots: { edit: 'DeliveryDate_edit' }, showOverflow: true },
        { field: 'hopeDeliveryDateVerify', title: '校验出库日', minWidth: 130, visible: false },
        { field: 'inquiryReason', title: '催促原因', minWidth: 140, editRender: { name: '$select', options: [], events: { change: this.findReasonType }}, showOverflow: true },
        { field: 'inquiryReasonType', title: '催促分类码', minWidth: 140, sortable: true, visible: false },
        // slots: { edit: 'ReasonParam_edit' },
        { field: 'inquiryReasonParam', title: '催促原因参数', minWidth: 140, editRender: {}, slots: { edit: 'ReasonParam_edit' }},
        { field: 'inquiryDescription', title: '催促描述', minWidth: 120, editRender: { name: '$input', props: { type: 'text', maxlength: 50 }}, showOverflow: true, visible: false },
        { field: 'inquiryRemark', title: '催促备注', minWidth: 120, editRender: { name: '$input', props: { type: 'text', maxlength: 50 }}, showOverflow: true }, // BUG14435,催促备注限制30个字符
        { field: 'inquiryLevel', title: '催促级别', minWidth: 100, showOverflow: true }
        // { field: 'advanceReason', title: '货期提前原因', minWidth: 140, editRender: { name: '$select', options: [] }, sortable: true },
        // { field: 'remark', title: '询问备注', minWidth: 120, editRender: { name: '$input', props: { type: 'text', maxlength: 50 }}, sortable: true }
      ],
      orderMainApplyColumns: [
        { fixed: 'left', type: 'checkbox', width: 40 },
        { field: 'operate', title: '操作', fixed: 'left', width: 60, slots: { default: 'orderOperate' }},
        { field: 'itemNo', title: '序号', fixed: 'left', width: 50 },
        { type: 'expand', width: 30, fixed: 'left', slots: { content: 'expand' }},
        { field: 'inquiryApplyNo', title: '申请号', fixed: 'left', width: 120 },
        { field: 'orderNo', title: '业务单号', fixed: 'left', minWidth: 120, editRender: {}, slots: { edit: 'orderInquiry_OrderNo_edit' }, showOverflow: true, sortable: true },
        { field: 'validateType', title: '*', width: 36, slots: { default: 'validateType' }},
        { field: 'modelNo', title: '型号', minWidth: 120, showOverflow: true },
        { field: 'quantity', title: '数量', minWidth: 60, showOverflow: true },
        { field: 'stockDesc', title: '出库区分', minWidth: 120 },
        { field: 'customerNo', title: '客户', minWidth: 80 },
        { field: 'endUser', title: '最终用户', minWidth: 80 },
        { field: 'purchaseNo', title: '客户PO', minWidth: 100, showOverflow: true },
        { field: 'orderStatus', title: '订单状态', minWidth: 80, showOverflow: true },
        { field: 'hopeExportDate', title: '当前客户货期', minWidth: 120, sortable: true },
        { field: 'hopeDeliveryDate', title: '期望货期', minWidth: 130, editRender: {}, slots: { edit: 'orderInquiry_DeliveryDate_edit' }, showOverflow: true },
        { field: 'hopeDeliveryDateVerify', title: '校验出库日', minWidth: 130, visible: false },
        { field: 'inquiryReason', title: '催促原因', minWidth: 140, editRender: { name: '$select', options: [], events: { change: this.findReasonType }}, showOverflow: true },
        { field: 'inquiryReasonType', title: '催促分类码', minWidth: 140, sortable: true, visible: false },
        { field: 'inquiryReasonParam', title: '催促原因参数', minWidth: 140, editRender: {}, slots: { edit: 'orderInquiry_ReasonParam_edit' }},
        { field: 'inquiryDescription', title: '催促描述', minWidth: 120, editRender: { name: '$input', props: { type: 'text', maxlength: 50 }}, showOverflow: true, visible: false },
        { field: 'inquiryRemark', title: '催促备注', minWidth: 120, editRender: { name: '$input', props: { type: 'text', maxlength: 50 }}, showOverflow: true } // BUG14435,催促备注限制30个字符 // bug 16422非日本不能超过50个字符
      ],
      validRules: {
        orderNo: [
          { required: true, message: '业务单号不能为空，请输入完整订单号！' },
          { validator: orderNoValid }
        ],
        hopeDeliveryDate: [
          { required: true, message: '期望货期不能为空' },
          { validator: hopeDeliveryDateValid }
        ],
        inquiryRemark: [
          { message: '催促备注不能超过50个字符' }
        ],
        inquiryDescription: [
          { message: '催促描述不能超过50个字符' }
        ],
        inquiryReason: [
          { required: true, message: '货期提前原因不能为空' }
        ],
        // 确定订单催促时，是否需要？
        inquiryReasonParam: [
          { validator: reasonParamValid }
        ]
      },
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        pageSizes: [5, 10, 20, 50, 100, 200, 500],
        layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
        perfect: true
      },
      orderTablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 10,
        pageSizes: [5, 10, 20, 50, 100, 200, 500],
        layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
        perfect: true
      },
      importDataDialog: false,
      actionUrl: '',
      file: null,
      importDataLoad: false,
      applynoNew: null,
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now()
        }
      },
      orderInquiryApplyList: [],
      selectedMainRow: null, // 当前选中的主列表项
      orderTableData: [], // 订单催促主表数据
      orderMainTableLoading: false, // 订单催促朱标的加载状态，
      // 订单催促自列表项
      orderDetailColumns: [
        { type: 'seq', width: 50 },
        { field: 'rorderSplitno', title: '拆分单号', minWidth: 120, showOverflow: true },
        { field: 'modelNo', title: '型号', minWidth: 120, showOverflow: true },
        { field: 'quantity', title: '数量', minWidth: 60, showOverflow: true },
        { field: 'orderStatusDesc', title: '当前状态', minWidth: 120, showOverflow: true },
        { field: 'inventoryDesc', title: '状态信息', minWidth: 150, showOverflow: true },
        { field: 'dlvmoddateStr', title: '当前返信', minWidth: 100 },
        { field: 'hopeDeliveryDate', title: '期望发货日', minWidth: 130, showOverflow: true },
        { field: 'canPress', title: '是否可催促', minWidth: 100, slots: { default: 'status-slot' }},
        { field: 'inquiryCount', title: '催促次数', minWidth: 100 },
        { field: 'inquiryLevel', title: '催促级别', minWidth: 100, showOverflow: true }
        // ... 其他子列表列
      ],
      deliveryDateRequest: {}, // 订单货期校验
      childrenLoading: false
    }
  },
  watch: {
    tableData(newVal, oldVal) {
      if (newVal && oldVal && newVal.length !== oldVal.length) {
        this.tableData.map((v, index) => {
          v.itemNo = index + 1
          return v
        })
      }
    },
    orderTableData(newVal, oldVal) {
      if (newVal && oldVal && newVal.length !== oldVal.length) {
        this.orderTableData.map((v, index) => {
          v.itemNo = index + 1
          return v
        })
      }
    }
  },
  created() {
    this.initData()
  },
  methods: {
    isEmpty(obj) {
      if (typeof obj === 'undefined' || obj == null || obj === '') {
        return true
      } else {
        return false
      }
    },
    getOptions() {
      let $grid = this.$refs.inquiryApplyTable
      if (this.applyForm.inquiryType === '1') {
        $grid = this.$refs.inquiryOrderApplyTable
      }
      // const column = $grid.getColumnByField('inquiryReason')
      // this.DictData.inquiryReasonType = []
      if ($grid && this.DictData.inquiryReasonTypeAll.length === 0) {
        getAllReason().then(result => {
          if (result.success) {
            const column = $grid.getColumnByField('inquiryReason')
            result.content.forEach(dict => {
              // this.DictData.inquiryReasonType.push({ value: dict.opsReasonCode, label: dict.opsReasonDesc })
              this.DictData.inquiryReasonTypeAll.push({ value: dict.opsReasonCode, label: dict.opsReasonDesc })
              // if (!this.isEmpty(dict.paramName)) {
              //   this.DictData.inquiryReasonParam.push(dict)
              // }
            })
            column.editRender.options = this.DictData.inquiryReasonTypeAll
          } else {
            this.$message({
              dangerouslyUseHTMLString: true,
              showClose: true,
              message: result.message,
              type: 'error',
              duration: 0
            })
          }
        }).catch(error => {
          console.info(error)
          this.$message.error(error.message)
        })
        // 获取参数配置表
        if (this.DictData.inquiryReasonParam.length === 0) {
          getReasonParam().then(data => { this.DictData.inquiryReasonParam = data.content })
        }
        // getDataCodesTree('2086').then(result => {
        // if (result.code === '200') {
        // const column = $grid.getColumnByField('inquiryReason')
        // result.content.forEach(dict => {
        // this.DictData.inquiryReasonType.push({ value: dict.code, label: dict.codeName })
        // })
        // column.editRender.options = this.DictData.inquiryReasonType
        // }
        // }).catch(error => {
        // console.info(error)
        // this.$message.error(error.message)
        // })
      } else if ($grid && this.DictData.inquiryReasonTypeAll.length > 0) {
        const column = $grid.getColumnByField('inquiryReason')
        column.editRender.options = this.DictData.inquiryReasonTypeAll // 重新给选择框赋值
      }
      // 调用后端，依次生成催促申请号
      return new Promise((resolve, reject) => {
        creatApplyNo(this.applyForm.inquiryType).then(res => {
          if (res.success) {
            this.applynoNew = res.content
            resolve() // 解析Promise，表示getOptions完成
          } else {
            reject(new Error('Failed to create inquiry apply number')) // 如果失败，拒绝Promise
          }
        })
      })
    },
    // 页面初始加载方法
    initData() {
      // 提交成功，生成申请号
      this.applyForm.inquiryUser = this.$store.getters.info.userId
      this.applyForm.inquiryUserName = this.$store.getters.info.psnName
      this.applyForm.inquiryDept = '【' + this.$store.getters.info.employeePositions[0].unitId + '】' + this.$store.getters.info.employeePositions[0].unitName
      this.applyForm.inquiryTime = this.dayjs(new Date()).format('YYYY-MM-DD')
      this.getOptions()
    },
    async insertEvent(orderNo, obj) {
      // this.getOptions() // 生成新的申请号
      // 返回一个Promise，以便在异步操作完成后继续执行
      return this.getOptions().then(() => {
        var row = {
          inquiryApplyNo: this.applynoNew, // 采购新申请号
          orderNo: orderNo || null,
          customerNo: obj ? obj.customerNo : '',
          endUser: obj ? obj.endUser : '',
          modelNo: obj ? obj.modelNo : '',
          quantity: obj ? obj.quantity : null,
          purchaseNo: obj ? obj.purchaseNo : null,
          replyDept: obj ? obj.replyDept : null,
          orderStatus: obj ? obj.orderStatus : null,
          hopeExportDate: obj ? obj.hopeExportDate : null,
          dlvModdate: obj ? obj.dlvModdate : null,
          dlvmoddateStr: obj ? obj.dlvmoddateStr : null,
          hopeDeliveryDate: null,
          inquiryReason: null,
          inquiryReasonType: null,
          inquiryRemark: null,
          inquiryDescription: null,
          inquiryReasonParam: undefined,
          reasonParamDisabled: false, // 默认参数列不可编辑
          inquiryLevel: null,
          deliveryDateEdit: false
        }
        if (this.applyForm.inquiryType === '0') {
          row.itemNo = this.tableData.length + 1
          this.tableData.push(row)
          this.tablePage.total = this.tableData.length
          this.handlePageChange(this.tablePage)
        } else if (this.applyForm.inquiryType === '1') {
          row.stockDesc = null
          row.itemNo = this.orderTableData.length + 1
          row.inquiryOrderDetails = null
          this.orderTableData.push(row)
          this.orderTablePage.total = this.orderTableData.length
          this.handlePageChangeOrder(this.orderTablePage)
        }
      })
    },
    selectChangeEvent({ checked, records }) {
      this.checkboxSelected = records
      console.log(this.checkboxSelected)
    },
    selectAllEvent({ checked, records }) {
      this.checkboxSelected = records
      console.log(this.checkboxSelected)
    },
    // 删除单条数据
    deleteRow(scope) {
      this.$confirm('确认删除该条数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.applyForm.inquiryType === '0') {
          var tableList = JSON.parse(JSON.stringify(this.tableData))
          const index = tableList.findIndex(item => item.itemNo === scope.row.itemNo)
          if (index > -1) {
            // 如果是当前页最后一条数据，则在删除成功后 跳到上一页
            if (this.inquiryApplyList.length === 1 && this.tablePage.currentPage > 1) {
              this.tablePage.currentPage--
            }
            tableList.splice(index, 1)
            this.tableData = tableList
            this.tablePage.total = this.tableData.length
            this.handlePageChange(this.tablePage)
          }
        } else if (this.applyForm.inquiryType === '1') {
          var ordertableList = JSON.parse(JSON.stringify(this.orderTableData))
          const indexOrder = ordertableList.findIndex(item => item.itemNo === scope.row.itemNo)
          if (indexOrder > -1) {
            // 如果是当前页最后一条数据，则在删除成功后 跳到上一页
            if (this.orderInquiryApplyList.length === 1 && this.orderTablePage.currentPage > 1) {
              this.orderTablePage.currentPage--
            }
            ordertableList.splice(indexOrder, 1)
            this.orderTableData = ordertableList
            this.orderTablePage.total = this.orderTableData.length
            this.handlePageChangeOrder(this.orderTablePage)
          }
        }
      }).catch(() => {
        console.log('取消')
      })
    },
    // 多选删除
    deleteModelNoBatch() {
      this.$confirm('确认删除所选数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        if (this.applyForm.inquiryType === '0') {
          this.checkboxSelected.forEach(item => {
            // this.tableData = this.tableData.filter(v => item.inquiryApplyNo !== v.inquiryApplyNo)
            this.tableData = this.tableData.filter(v => item.itemNo !== v.itemNo)
          })
          if (this.inquiryApplyList.length <= this.checkboxSelected.length && this.tablePage.currentPage > 1) {
            this.tablePage.currentPage--
          }
          this.tablePage.total = this.tableData.length
          this.handlePageChange(this.tablePage)
          this.checkboxSelected = []
          this.$refs.inquiryApplyTable.clearCheckboxRow()
        } else if (this.applyForm.inquiryType === '1') {
          this.checkboxSelected.forEach(item => {
            this.orderTableData = this.orderTableData.filter(v => item.itemNo !== v.itemNo)
          })
          if (this.orderInquiryApplyList.length <= this.checkboxSelected.length && this.orderTablePage.currentPage > 1) {
            this.orderTablePage.currentPage--
          }
          this.orderTablePage.total = this.orderTableData.length
          this.handlePageChangeOrder(this.orderTablePage)
          this.checkboxSelected = []
          this.$refs.inquiryOrderApplyTable.clearCheckboxRow()
        }
      }).catch(() => {
        console.log('取消')
      })
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      var start = (currentPage - 1) * pageSize
      var end = currentPage * pageSize
      const table = this.tableData.slice(start, end)
      this.inquiryApplyList = table
    },
    handlePageChangeOrder({ currentPage, pageSize }) {
      this.orderTablePage.currentPage = currentPage
      this.orderTablePage.pageSize = pageSize
      var start = (currentPage - 1) * pageSize
      var end = currentPage * pageSize
      const table = this.orderTableData.slice(start, end) // todo 此处需要筛选出主表数据
      this.orderInquiryApplyList = table
    },
    disabledDateMethod({ date }) {
      return date < new Date()
    },
    // 催促原因选择框的选择
    findReasonType({ row, rowIndex }) {
      // 20240626 新增全部类别数组，存储所有集合
      const item = this.DictData.inquiryReasonTypeAll.find(dict => dict.value === row.inquiryReason)
      row.inquiryReasonType = item ? item.value : row.inquiryReason
      row.inquiryReason = item ? item.label : row.inquiryReason
      // 校验所选的原因是否存在参数，存在时 激活催促原因参数的框的编辑
      row.inquiryReasonParam = undefined
      row.reasonParamType = null
      row.isFixedLength = null
      row.dataLength = null
      row.reasonParamDisabled = false
      const reasonParam = this.findReasonParam(row, this.applyForm.inquiryType)
      if (reasonParam) {
        row.reasonParamType = reasonParam.paramType
        row.isFixedLength = reasonParam.isFixedLength
        row.dataLength = reasonParam.dataLength
        row.reasonParamDisabled = true
      }
      if (row.inquiryReasonType === '35') {
        row.deliveryDateEdit = true
      } else {
        row.deliveryDateEdit = false
      }
    },
    // 增加订单催促的选择参数判断，只要选择原因一致就激活参数的输入框，不需要校验供应商
    findReasonParam(row, inquiryType) {
      if (inquiryType === '0') {
        return this.DictData.inquiryReasonParam.find(dict => dict.opsReasonCode === row.inquiryReasonType && dict.supplierId === row.replyDept)
      } else if (inquiryType === '1') {
        return this.DictData.inquiryReasonParam.find(dict => dict.opsReasonCode === row.inquiryReasonType)
      }
    },
    findOrderData(row) {
      this.orderNoList = []
      this.clearRow(row) // 重新输入订单号后，清空列表数据
      this.$refs.inquiryApplyTable.clearEdit()
      this.$refs.inquiryApplyTable.setEditCell(row, row.column)
      var orderNoData = this.inquiryApplyList.filter(item => item.orderNo === row.orderNo)
      if (orderNoData && orderNoData.length > 1) {
        row.validateType = 2
        row.validateDescription = '重复的业务单号'
        return
      }
      this.inquiryTableLoading = true
      this.orderNoList.push(row.orderNo)
      getOrderData(this.orderNoList).then(data => {
        if (data.success && data.content.length > 0 && data.content[0].canPress) {
          const rowData = data.content[0]
          row.customerNo = rowData.customerNo
          row.endUser = rowData.endUser
          // row.customerName = rowData.customerName
          // row.endUserName = rowData.enduserName != null ? rowData.enduserName : rowData.customerName
          // row.endUserNo = rowData.enduser != null ? rowData.enduser : rowData.customerNo
          row.modelNo = rowData.modelNo
          row.quantity = rowData.quantity
          row.hopeExportDate = rowData.hopeExportDate
          row.dlvmoddateStr = rowData.dlvmoddateStr
          row.dlvModdate = rowData.dlvModdate
          row.orderStatus = rowData.orderStatusDesc
          row.purchaseNo = rowData.purchaseNo
          row.replyDept = rowData.replyDept
          row.inquiryLevel = rowData.inquiryLevel
          row.validateType = 0
          row.validateDescription = '验证通过'
          // 验证通过后，根据供应商，调用可选的催促原因列表
          getReasonBySuppily(rowData.supplierId).then(data => {
            if (data.success) {
              this.DictData.inquiryReasonType = []
              const column = this.$refs.inquiryApplyTable.getColumnByField('inquiryReason')
              data.content.forEach(dict => {
                this.DictData.inquiryReasonType.push({ value: dict.opsReasonCode, label: dict.opsReasonDesc })
                // if (!this.isEmpty(dict.paramName)) {
                //   this.DictData.inquiryReasonParam.push(dict)
                // }
              })
              column.editRender.options = this.DictData.inquiryReasonType
            }
          })
          this.inquiryTableLoading = false
        } else if (data.success && data.content.length > 0 && !data.content[0].canPress) {
          row.validateType = 3
          row.validateDescription = data.content[0].checkFailureMsg // 不可催促原因
          this.inquiryTableLoading = false
        } else if (!data.success || !data.content) {
          row.validateType = 1
          row.validateDescription = data.message
          this.inquiryTableLoading = false
        } else if (!data.content) {
          row.validateType = 1
          row.validateDescription = '错误的业务单号'
          this.inquiryTableLoading = false
        }
      }).catch(err => {
        this.inquiryTableLoading = false
        console.log(err)
        this.$message.error(err.message)
      })
      // this.$forceUpdate()
    },
    // 订单催促获取校验结果
    findOrderInquiryData(row) {
      this.orderNoInquiryList = []
      this.clearRow(row) // 重新输入订单号后，清空列表数据
      this.$refs.inquiryOrderApplyTable.clearEdit()
      this.$refs.inquiryOrderApplyTable.setEditCell(row, row.column)
      var orderNoData = this.orderInquiryApplyList.filter(item => item.orderNo === row.orderNo)
      if (orderNoData && orderNoData.length > 1) {
        row.validateType = 2
        row.validateDescription = '重复的业务单号'
        return
      }
      this.orderMainTableLoading = true
      this.orderNoInquiryList.push(row.orderNo)
      getOrderInquiryVerify(this.orderNoInquiryList).then(data => {
        if (!data.success || !data.content) {
          row.validateType = 1
          row.validateDescription = data.message
          this.orderMainTableLoading = false
        } else if (!data.content) {
          row.validateType = 1
          row.validateDescription = '错误的业务单号'
          this.orderMainTableLoading = false
        } else if (data.success && data.content.length > 0) {
          const rowData = data.content[0].inquiryOrderMaster
          if (rowData.canPress) {
            row.customerNo = rowData.customerNo
            row.endUser = rowData.endUser
            row.modelNo = rowData.modelNo
            row.quantity = rowData.quantity
            row.hopeExportDate = rowData.hopeExportDate
            // row.dlvModdate = rowData.dlvModdate
            row.orderStatus = rowData.orderStatusDesc
            row.purchaseNo = rowData.purchaseNo
            row.stockDesc = rowData.stockDesc
            // row.inquiryLevel = rowData.inquiryLevel
            row.validateType = 0
            row.validateDescription = '验证通过'
            // todo 子项赋值
            row.inquiryOrderDetails = data.content[0].inquiryOrderDetails
          } else {
            row.validateType = 3
            row.validateDescription = rowData.checkFailureMsg // 不可催促原因
            const inquiryOrderDetails = data.content[0].inquiryOrderDetails
            if (inquiryOrderDetails) {
              for (let i = 0; i < inquiryOrderDetails.length; i++) {
                if (inquiryOrderDetails[i].canPress === false) {
                  row.validateDescription = inquiryOrderDetails[i].checkFailureMsg
                  break
                }
              }
            }
          }
          this.orderMainTableLoading = false
        }
      }).catch(err => {
        this.orderMainTableLoading = false
        console.log(err)
        this.$message.error(err.message)
      })
      // this.$forceUpdate()
    },
    clearRow(row) {
      if (this.applyForm.inquiryType === '0') {
        row.customerNo = null
        row.endUser = null
        row.modelNo = null
        row.quantity = null
        row.hopeExportDate = null
        row.dlvModdate = null
        row.dlvmoddateStr = null
        row.orderStatus = null
        row.purchaseNo = null
        row.replyDept = null
        row.hopeDeliveryDate = null
        row.inquiryReason = null
        row.inquiryReasonType = null
        row.inquiryRemark = null
        row.inquiryDescription = null
        row.inquiryReasonParam = undefined
        row.reasonParamDisabled = false // 默认参数列不可编辑
        row.inquiryLevel = null
        row.deliveryDateEdit = false
      } else if (this.applyForm.inquiryType === '1') { // 订单催促清空列属性
        row.customerNo = null
        row.endUser = null
        row.modelNo = null
        row.quantity = null
        row.hopeExportDate = null
        // row.dlvModdate = null
        row.orderStatus = null
        row.purchaseNo = null
        row.stockDesc = null
        row.hopeDeliveryDate = null
        row.inquiryReason = null
        row.inquiryReasonType = null
        row.inquiryRemark = null
        row.inquiryDescription = null
        row.inquiryReasonParam = undefined
        row.reasonParamDisabled = false // 默认参数列不可编辑
        row.inquiryLevel = null
        row.inquiryOrderDetails = null
      }
    },
    // 组合保存数据
    initInquiryBean() {
      if (this.applyForm.inquiryType === '0') {
        this.tableData.forEach((item) => {
          // item.inquiryApplyNo = this.applyForm.inquiryApplyNo
          item.inquiryDept = this.$store.getters.info.employeePositions[0].unitId
          item.inquiryUser = this.applyForm.inquiryUser
          item.inquiryUserName = this.applyForm.inquiryUserName
          item.inquiryType = this.applyForm.inquiryType
          item.dataSources = 'ops'
          item.inquiryTime = this.dayjs(new Date())
        })
        this.inquiryApplyList = this.tableData
      } else if (this.applyForm.inquiryType === '1') {
        this.orderTableData.forEach((item) => {
          // item.inquiryApplyNo = this.applyForm.inquiryApplyNo
          item.inquiryDept = this.$store.getters.info.employeePositions[0].unitId
          item.inquiryUser = this.applyForm.inquiryUser
          item.inquiryUserName = this.applyForm.inquiryUserName
          item.inquiryType = this.applyForm.inquiryType
          item.dataSources = 'ops'
          item.inquiryTime = this.dayjs(new Date())
        })
        this.orderInquiryApplyList = this.orderTableData
      }
    },
    // 提交公共方法
    handleSubmission(applyList, addDataFunction) {
      addDataFunction(applyList).then(data => {
        if (data.success) {
          this.smcSuccessMsg('提交成功')
          this.editQueryLoading = false
          this.$store.dispatch('tagsView/delView', this.$route)
          this.$router.push({
            path: '/eta/inquiry/index',
            query: { refresh: '1' }
          })
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: data.message,
            type: 'error',
            duration: 0
          })
          this.editQueryLoading = false
        }
      }).catch(err => {
        this.$message.error(err)
        console.log(err)
        this.editQueryLoading = false
      })
    },
    submit() {
      this.initInquiryBean()
      this.editQueryLoading = true
      this.$confirm('确认提交催促申请吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        // 提交时，是否需要校验
        let errMap
        try {
          if (this.applyForm.inquiryType === '0') {
            errMap = await this.$refs.inquiryApplyTable.validate(this.tableData)
          } else if (this.applyForm.inquiryType === '1') {
            errMap = await this.$refs.inquiryOrderApplyTable.validate(this.orderTableData)
          }
        } catch (error) {
          errMap = error // 或者进行其他错误处理
        }
        // const errMap = await this.$refs.inquiryApplyTable.validate(this.tableData).catch(errMap => errMap)
        if (errMap) {
          const msgList = []
          this.editQueryLoading = false
          Object.values(errMap).forEach(errList => {
            errList.forEach(params => {
              const { rowIndex, column, rules } = params
              rules.forEach(rule => {
                msgList.push(`第 ${rowIndex + 1} 行 ${column.title} 校验错误：${rule.message}`)
              })
            })
          })
          var message = ''
          msgList.map(msg => {
            message = message + '<div>' + msg + '</div>'
          })
          this.$notify({
            title: '失败',
            dangerouslyUseHTMLString: true,
            message: ' <div class="red" style="max-height: 400px;overflow: auto;">' + message + '</div>',
            type: 'error',
            duration: 2000
          })
        } else {
          this.handleSubmission(this.applyForm.inquiryType === '0' ? this.inquiryApplyList : this.orderInquiryApplyList, this.applyForm.inquiryType === '0' ? addInquiryData : addInquiryOrderData)
        }
      }).catch(() => {
        console.log('取消')
        this.editQueryLoading = false
      })
    },
    // 批量导入功能
    batchImport() {
      this.importDataDialog = true
      this.file = null
    },
    closeClick3() {
      this.file = null
      this.importDataDialog = false
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    purchaseImportDatas() {
      this.importDataLoad = true
      if (this.file == null) {
        this.importDataLoad = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        this.inquiryTableLoading = true
        importBatchData(formData).then(data => {
          this.importDataLoad = false
          this.file = null
          if (data.success) {
            // this.$notify({ title: '导入成功', message: data.message, type: 'success' })
            this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + data.message + '</div>', '导入结果', {
              dangerouslyUseHTMLString: true
            })
            this.importDataDialog = false
            // 填充table列表
            data.content.forEach(element => {
              if (!element.canPress) {
                element.validateType = 3
                element.validateDescription = element.checkFailureMsg
              } else if (element.canPress) {
                element.validateType = 0
                element.validateDescription = '验证通过'
              }
              this.tableData.some(v => {
                if (v.orderNo === element.orderNo) {
                  element.validateType = 2
                  element.validateDescription = '重复的业务单号'
                  return true
                }
              })
              this.tableData.push({
                inquiryApplyNo: element.inquiryApplyNo,
                orderNo: element.orderNo,
                customerNo: element.customerNo,
                endUser: element.endUser,
                modelNo: element.modelNo,
                quantity: element.quantity,
                hopeExportDate: element.hopeExportDate,
                dlvmoddateStr: element.dlvmoddateStr,
                dlvModdate: element.dlvModdate,
                orderStatus: element.orderStatusDesc,
                purchaseNo: element.purchaseNo,
                replyDept: element.replyDept,
                hopeDeliveryDate: element.hopeDeliveryDate,
                inquiryReason: element.inquiryReason,
                inquiryReasonType: element.inquiryReasonType,
                inquiryRemark: element.inquiryRemark,
                inquiryDescription: element.inquiryDescription,
                validateType: element.validateType,
                validateDescription: element.validateDescription,
                inquiryReasonParam: undefined,
                inquiryLevel: element.inquiryLevel,
                deliveryDateEdit: false
              })
              this.tableData.map((v, index) => {
                if (!v.hasOwnProperty('itemNo')) {
                  v.itemNo = index + 1
                }
                return v
              })
            })
            this.getOptions()
            this.tablePage.total = this.tableData.length
            this.handlePageChange(this.tablePage)
            this.inquiryTableLoading = false
          } else {
            this.$notify({ duration: 5000, title: '失败', message: data.message, type: 'error' })
            this.inquiryTableLoading = false
          }
        }).catch(error => {
          this.importDataLoad = false
          this.inquiryTableLoading = false
          this.file = null
          this.$message.error('导入失败:' + error)
        })
      }
    },
    orderImportDatas() {
      this.importDataLoad = true
      if (this.file == null) {
        this.importDataLoad = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        this.orderMainTableLoading = true
        importOrderBatchData(formData).then(data => {
          this.importDataLoad = false
          this.file = null
          if (data.success) {
            // this.$notify({ title: '导入成功', message: data.message, type: 'success' })
            this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + data.message + '</div>', '导入结果', {
              dangerouslyUseHTMLString: true
            })
            this.importDataDialog = false
            // 填充table列表
            data.content.forEach(element => {
              console.log(element)
              console.log(element.inquiryOrderMaster)
              var orderdata = element.inquiryOrderMaster
              if (!orderdata.canPress) {
                orderdata.validateType = 3
                orderdata.validateDescription = orderdata.checkFailureMsg
              } else if (orderdata.canPress) {
                orderdata.validateType = 0
                orderdata.validateDescription = '验证通过'
              }
              this.orderTableData.some(v => {
                if (v.orderNo === orderdata.orderNo) {
                  orderdata.validateType = 2
                  orderdata.validateDescription = '重复的业务单号'
                  return true
                }
              })
              this.orderTableData.push({
                inquiryApplyNo: orderdata.inquiryApplyNo,
                orderNo: orderdata.orderNo,
                customerNo: orderdata.customerNo,
                endUser: orderdata.endUser,
                modelNo: orderdata.modelNo,
                quantity: orderdata.quantity,
                hopeExportDate: orderdata.hopeExportDate,
                // dlvModdate: orderdata.dlvModdate,
                orderStatus: orderdata.orderStatusDesc,
                stockDesc: orderdata.stockDesc,
                purchaseNo: orderdata.purchaseNo,
                // replyDept: orderdata.replyDept,
                hopeDeliveryDate: orderdata.hopeDeliveryDate,
                inquiryReason: orderdata.inquiryReason,
                inquiryReasonType: orderdata.inquiryReasonType,
                inquiryRemark: orderdata.inquiryRemark,
                inquiryDescription: orderdata.inquiryDescription,
                validateType: orderdata.validateType,
                validateDescription: orderdata.validateDescription,
                inquiryReasonParam: undefined,
                inquiryOrderDetails: element.inquiryOrderDetails
                // inquiryLevel: orderdata.inquiryLevel //催促级别在子项中展示
              })
              this.orderTableData.map((v, index) => {
                if (!v.hasOwnProperty('itemNo')) {
                  v.itemNo = index + 1
                }
                return v
              })
            })
            this.getOptions()
            this.orderTablePage.total = this.orderTableData.length
            this.handlePageChangeOrder(this.orderTablePage)
            this.orderMainTableLoading = false
          } else {
            this.$notify({ duration: 5000, title: '失败', message: data.message, type: 'error' })
            this.orderMainTableLoading = false
          }
        }).catch(error => {
          this.importDataLoad = false
          this.orderMainTableLoading = false
          this.file = null
          this.$message.error('导入失败:' + error)
        })
      }
    },
    // 导入提交文件
    importBatchDatas() {
      if (this.applyForm.inquiryType === '0') {
        this.purchaseImportDatas()
      } else if (this.applyForm.inquiryType === '1') {
        this.orderImportDatas()
      }
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
    },
    // 辅助函数，用于检查日期字符串是否有效
    isValidDate(dateString) {
      const date = new Date(dateString)
      return !isNaN(date.getTime())
    },
    dataPickerchange(row) {
      if (row.inquiryReasonType === '35') {
        row.hopeDeliveryDate = row.inquiryReasonParam
      }
      this.orderDeliveryDateVerify(row)
    },
    typeSelectChange(val) {
      // this.typeSelectDialogVisible = true
      this.$confirm('请注意，切换催促类型将会改变表格，请确认是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        console.log(val)
      }).catch(() => {
        console.log('取消')
        this.applyForm.inquiryType = this.applyForm.inquiryType === '0' ? '1' : '0'
        // 清空列表数据？？？
        console.log(this.applyForm.inquiryType)
      })
    },
    // 懒加载获取回复信息
    toggleExpandMethod({ row }) {
      return new Promise(resolve => {
        if (row.validateType !== 0) {
          resolve()
        }
        this.selectedMainRow = this.orderTableData.find(item => item.orderNo === row.orderNo)
        resolve()
      })
    },
    formatCanPress({ row, column, cellValue }) {
      if (cellValue) {
        return '<span style="color: green;">是</span>'
        // return '是'
      } else {
        return '否'
      }
    },
    // 订单催促时校验子项的货期
    async orderDeliveryDateVerify(row) {
      if (!this.isEmpty(row.hopeDeliveryDate)) {
        this.deliveryDateRequest.orderNo = row.orderNo
        this.deliveryDateRequest.hopeDeliveryDate = row.hopeDeliveryDate
        const deliveryDateDo = [this.deliveryDateRequest]
        deliveryDataVerify(deliveryDateDo).then(data => {
          if (data.success && data.content.length > 0 && data.content[0].canPress) {
            row.validateType = 0
            row.validateDescription = '验证通过'
          } else if (data.success && data.content.length > 0 && !data.content[0].canPress) {
            row.validateType = 3
            row.validateDescription = data.content[0].checkFailureMsg // 不可催促原因
            this.inquiryTableLoading = false
          } else if (!data.success || !data.content) {
            row.validateType = 1
            row.validateDescription = data.message
          }
        })
      }
    }
  }
}
</script>
<style>
.el-descriptions-item__label.is-bordered-label {
  font-weight: bold;
  color: rgba(253, 253, 253, 0.938);
  background: rgb(137 171 201);
}
.my-content {
  background: #f1f2f3;
  color: rgba(26, 24, 24, 0.938);
  font-weight:bold;
 }
 .detailtable-scrollbar {
    min-height: 100; /* 设置最小高度 */
    max-height: 600px; /* 设置最大高度 */
    overflow-y: auto;  /* 当内容超出最大高度时显示垂直滚动条 */
  }
</style>
