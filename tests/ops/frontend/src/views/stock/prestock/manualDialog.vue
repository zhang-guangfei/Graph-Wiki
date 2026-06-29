<template>
  <div>
    <!-- 手动处理弹窗 -->
    <el-dialog
      :visible.sync="dialogProcessResultVisible"
      :before-close="onClose"
      title="手动处理"
      width="800px">
      <span class="span-title">基本信息</span>
      <vxe-table
        :data="resultProcessBaseInfo"
        :loading="showProcessResultDialogLoading"
        border
        align="center">
        <vxe-table-column field="modelNo" title="型号"/>
        <vxe-table-column field="quantity" title="总数量" width="80"/>
        <vxe-table-column field="qtyBin" title="Bin数量" width="80"/>
        <vxe-table-column field="inventoryType" title="库存类型">
          <template slot-scope="scope">{{ scope.row.inventoryType === '1' ? 'BIN' : '' }}</template>
        </vxe-table-column>
        <vxe-table-column field="minPackageQty" title="最小包装数" width="80"/>
        <vxe-table-column field="freq" title="频率" width="80"/>
        <vxe-table-column field="mean" title="用量" width="80" />
      </vxe-table>
      <!-- 申请型号明细 -->
      <vxe-table
        ref="modelMonthlyDetailTable"
        :data="modelMonthlyDetailData"
        :show-header="false"
        border
        align="center"
        size="mini">
        <template v-for="(item, index) in modelMonthlyDetailTitle">
          <vxe-table-column :key="index" :field="item" :title="item" >
            <template #default="{ row, rowIndex, columnIndex }">
              <span v-if="columnIndex === 0">{{ row['title'] }}</span>
              <span v-else-if="columnIndex > 0 && rowIndex < 2">{{ row[`value${columnIndex - 1}`] }}</span>
              <el-button v-else-if="columnIndex > 0 && rowIndex === 2 && row[`value${columnIndex - 1}`] > 0" type="primary" size="mini" icon="el-icon-plus" @click="createModelProcessItem(row[`value${columnIndex - 1}`])"/>
              <span v-else>已处理</span>
            </template>
          </vxe-table-column>
        </template>
      </vxe-table>
      <!-- 处理结果 -->
      <vxe-toolbar ref="resultToolbar">
        <template #buttons>
          <span class="span-title">处理方式</span>
          <el-button :loading="onManualProcessLoading" type="primary" size="mini" @click="onManualProcess" >处理</el-button>
        </template>
      </vxe-toolbar>
      <vxe-table
        ref="resultTable"
        :data="resultItemData"
        :edit-rules="processResultRules"
        :edit-config="{trigger: 'manual', mode: 'row'}"
        keep-source
        border>
        <vxe-table-column field="modelNo" title="型号" width="160"/>
        <vxe-table-column :edit-render="{}" field="processType" title="备库方式" width="110" >
          <template #default="{ row }">
            <span>{{ getProcessType(row.processType) }}</span>
          </template>
          <template #edit="{ row }">
            <el-select v-model="row.processType" size="mini" transfer type="text" placeholder="备库方式" style="width: 100%;" @change="changeResultProcessType(row)">
              <el-option v-for="item in processTypeList" :key="item.code" :label="item.codeName" :value="item.code"/>
            </el-select>
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{}" field="orderQty" title="数量" width="90">
          <template #edit="{ row }">
            <el-input v-model.number="row.orderQty" size="mini" placeholder="数量"/>
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{}" field="orderStock" title="供应商" width="120">
          <template #edit="{ row }">
            <!-- <el-input v-model="row.orderStock" size="mini" placeholder="供应商"/> -->
            <el-select v-model="row.orderStock" placeholder="供应商" size="mini" clearable style="width:100%">
              <el-option
                v-for="item in supplierList"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"/>
            </el-select>
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{}" field="transType" title="运输方式" width="90">
          <template #default="{ row }">
            <span>{{ getTransType(row.transType) }}</span>
          </template>
          <template #edit="{ row }">
            <el-input v-model="row.transType" size="mini" placeholder="运输方式"/>
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{}" field="orderNo" title="关联单号" width="100">
          <template #edit="{ row }">
            <el-input v-model="row.orderNo" size="mini" placeholder="关联单号"/>
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{}" field="remark" title="备注" width="200">
          <template #edit="{ row }">
            <el-input v-model="row.remark" size="mini" placeholder="备注"/>
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{}" field="dlvDateJp" title="交货日期" width="100">
          <template #edit="{ row }">
            <el-date-picker v-model="row.dlvDateJp" size="mini" type="date" placeholder="选择日期" value-format="yyyy-MM-dd" style="width: 100%;" />
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{}" field="execTime" title="执行时间" width="100">
          <template #edit="{ row }">
            <el-input v-model="row.execTime" size="mini" placeholder="执行时间"/>
          </template>
        </vxe-table-column>
        <vxe-table-column fixed="right" align="center" title="操作" width="120" >
          <template #default="{ index, row }">
            <el-button v-if="$refs.resultTable.isActiveByRow(row)" :disabled="row.optStatus === '2'" size="mini" type="primary" icon="el-icon-check" style="margin: 0;" @click="saveItemEvent(row)"/>
            <el-button v-else :disabled="row.optStatus === '2'" size="mini" type="primary" icon="el-icon-edit" style="margin: 0;" @click="editItemEvent(row)"/>
            <el-button :disabled="row.optStatus === '2'" size="mini" type="danger" icon="el-icon-delete" style="margin: 0;" @click="removeItem(row)"/>
          </template>
        </vxe-table-column>
      </vxe-table>
      <!-- 型号在库情况 -->
      <span class="span-title">在库情况</span>
      <vxe-table
        :data="inventoryInfos"
        border
        max-height="300">
        <vxe-table-column field="warehouseCode" title="库房"/>
        <vxe-table-column field="avaQty_ty" title="有效在库"/>
        <vxe-table-column field="avaQty_zy" title="专用在库"/>
        <vxe-table-column field="safeQty" title="安全库存"/>
        <vxe-table-column field="qtyBin" title="Bin数量"/>
        <vxe-table-column field="binCell" title="Bin数"/>
        <vxe-table-column field="freq" title="频率"/>
        <vxe-table-column field="mean" title="月用量"/>
        <vxe-table-column field="months" title="可用月数"/>
        <vxe-table-column field="excessQty" title="过剩数量"/>
      </vxe-table>
      <!-- 订货中订单 -->
      <!-- <span class="span-title">订货中订单</span>
      <vxe-table
        :data="orderInfos"
        border
        max-height="300">
        <vxe-table-column field="orderNo" title="采购订单"/>
        <vxe-table-column field="modelNo" title="型号"/>
        <vxe-table-column field="quantity" title="数量"/>
        <vxe-table-column field="address" title="到达地点"/>
        <vxe-table-column field="dlvDate" title="预计到达日期"/>
        <vxe-table-column field="sendDate" title="发出日期"/>
        <vxe-table-column field="remark" title="状态说明"/>
      </vxe-table> -->
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
  </div>
</template>

<script>
import { getResult, handleProcessItem, getProcessItem } from '@/api/stock/prestock'
import { getDeptTreeByNo } from '@/api/common/department'
import { findSupplierByIdOrName } from '@/api/common/supplier'
import { getDataCodesTree, listWarehouse } from '@/api/common/dict'
import { getTransIds } from '@/api/purchaseOrder'
export default {
  name: 'ManualDialog',
  data() {
    return {
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
      dialogFormVisible: false,
      dialogProcessResultVisible: false,
      resultProcessBaseInfo: [],
      modelMonthlyDetailData: [],
      resultItemData: [],
      inventoryInfos: [],
      processTypeList: [],
      warehouseList: [],
      warehousetypeList: [],
      warehouseData: [],
      transTypeList: [],
      applyId: 0,
      detailId: 0,
      applyFormWarehouseCode: '',
      modelMonthlyDetailTitle: ['交货期', '数量'],
      dialogLoading: false,
      onManualProcessLoading: false,
      showProcessResultDialogLoading: false,
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
      }
    }
  },
  created() {
    this.initProcessTypeList()
    this.initWarehouseList()
    this.initTransTypeList()
    this.initDeptTreeList()
    this.listWarehouse()
    this.listSupplierinfo()
  },
  methods: {
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
    async initProcessTypeList() {
      await getDataCodesTree(this.processTypeClassCode).then(res => {
        if (res.success) {
          this.processTypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initWarehouseList() {
      await getDataCodesTree(this.warehouseClassCode).then(res => {
        if (res.success) {
          this.warehousetypeList = res.content
        }
      }).catch(error => {
        console.error(error)
      })
    },
    async initTransTypeList() {
      var transParam = {}
      transParam.supplierId = null
      transParam.modelNo = null
      transParam.orderQty = null
      transParam.ordType = null
      transParam.warehouse = null
      transParam.translation = true
      await getTransIds(transParam).then(res => {
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
    },
    async initDeptTreeList() {
      const params = { deptNos: '235000,233200' } // 行业开发部,市场部
      await getDeptTreeByNo(params).then(res => {
        if (res.success) {
          this.deptTreeList = res.content
        }
      })
    },
    async listSupplierinfo() {
      const parm = { 'companyId': '', 'name': '' }
      await findSupplierByIdOrName(parm).then(res => {
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
    onClose() {
      this.dialogProcessResultVisible = false
    },
    // 父组件调用
    onGetResultDetailByParam(params) {
      this.applyId = params['applyId']
      this.detailId = params['detailId']
      this.applyFormWarehouseCode = params['applyFormWarehouseCode']
      this.onGetResultDetail()
    },
    onGetResultDetail() {
      if (this.applyId <= 0 && this.detailId <= 0) {
        this.$message.warning('请选择数据！')
        return
      }
      // 显示手动处理窗口
      this.dialogProcessResultVisible = true
      this.supplierList = this.supplieData
      // 将表格和工具栏进行关联
      this.$nextTick(() => {
        const $table = this.$refs.resultTable
        if ($table) {
          $table.connect(this.$refs.resultToolbar)
        }
      })
      // 获取处理结果基础信息
      this.getResultInfo()
    },
    // 获取处理结果基础信息
    async getResultInfo() {
      this.showProcessResultDialogLoading = true
      this.resultProcessBaseInfo = []
      this.resultItemData = []
      this.inventoryInfos = []
      const params = { 'applyId': this.applyId, 'detailId': this.detailId }
      await getResult(params).then(res => {
        if (res.success) {
          this.processResultForm = res.content
          // 设置基础信息
          this.resultProcessBaseInfo.push({
            applyId: this.processResultForm.applyId,
            modelNo: this.processResultForm.modelNo,
            quantity: this.processResultForm.quantity,
            qtyBin: this.processResultForm.qtyBin,
            inventoryType: this.processResultForm.inventoryType,
            freq: this.processResultForm.freq,
            mean: this.processResultForm.mean,
            minPackageQty: this.processResultForm.minPackageQty
          })
          // 设置申请型号需求月份情况
          this.modelMonthlyDetail = this.processResultForm.monthlyDetails
          // 设置在库信息
          this.inventoryInfos = this.processResultForm.inventoryInfos
        } else {
          this.$message.error(res.message)
        }
      })
      this.showProcessResultDialogLoading = false
      // 将申请型号需求月份情况转化为纵向表格数据
      this.handleApplyModelMonthlyDetail()
    },
    // 获取申请型号需求月份情况
    async handleApplyModelMonthlyDetail() {
      // 转换为纵向表格数据
      const data = []
      let title = ['交货期', '数量', '操作']
      for (const i in title) {
        data[i] = []
        data[i][0] = title[i]
      }
      title = ['title']
      for (const i in this.modelMonthlyDetail) {
        title.push(`value${i}`)
        for (const j in data) {
          if (data[j][0] === '交货期') {
            data[j].push(this.modelMonthlyDetail[i].dlvDate)
          }
          if (data[j][0] === '数量') {
            data[j].push(this.modelMonthlyDetail[i].quantity)
          }
          if (data[j][0] === '操作') {
            if (this.modelMonthlyDetail[i].status === '2' || this.modelMonthlyDetail[i].status === '7' || this.modelMonthlyDetail[i].status === '10') {
              data[j].push(this.modelMonthlyDetail[i].id)
            } else {
              data[j].push(0)
            }
          }
        }
      }
      for (const i in data) {
        const obj = {}
        for (const j in data[i]) {
          obj[title[j]] = data[i][j]
        }
        this.modelMonthlyDetailData[i] = obj
      }
      this.modelMonthlyDetailTitle = title
      this.$refs.modelMonthlyDetailTable.reloadData(this.modelMonthlyDetailData)
    },
    // 手动触发生成申请项处理数据
    createModelProcessItem(detailId) {
      const params = { 'detailId': detailId }
      getProcessItem(params).then(res => {
        if (res.success) {
          const resultItems = res.content
          if (resultItems && resultItems.length > 0) {
            this.$refs.resultTable.insert(resultItems)
          }
        } else {
          this.$message.error(res.message)
        }
      })
    },
    // 手动处理-执行处理项数据
    async onManualProcess() {
      const $table = this.$refs.resultTable
      const { insertRecords, updateRecords } = $table.getRecordset()
      if (insertRecords.length <= 0 && updateRecords.length <= 0) {
        this.$message.warning('无可处理数据')
        return
      }
      const errMap = await $table.validate().catch(errMap => errMap)
      if (errMap) {
        return
      }
      this.onManualProcessLoading = true
      const data = insertRecords.concat(updateRecords)
      console.log('res =>', data)
      await handleProcessItem(data).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.resultItemData = []
        } else {
          this.$message({
            dangerouslyUseHTMLString: true,
            showClose: true,
            message: res.message,
            type: 'error',
            duration: 0
          })
        }
      })
      this.onManualProcessLoading = false
      this.$emit('onManualProcess', true)
      this.dialogProcessResultVisible = false
    },
    // 删除处理项
    async removeItem(row) {
      // 2.删除前端数组数据项
      this.$refs.resultTable.remove(row)
      this.$message.success('删除成功')
    },
    async addItem() {
      const $table = this.$refs.resultTable
      const newItem = {
        applyId: this.processResultForm.applyId,
        modelNo: this.processResultForm.modelNo,
        processType: '',
        orderStock: '',
        orderQty: '',
        execTime: '',
        orderNo: '',
        dlvDateJp: '',
        applyModelNo: this.processResultForm.modelNo,
        optStatus: '1'
      }
      const { row: newRow } = await $table.insert(newItem)
      await $table.setActiveRow(newRow)
    },
    editItemEvent(row) {
      const $table = this.$refs.resultTable
      $table.setActiveRow(row)
    },
    saveItemEvent(row) {
      this.isValidateOrderQty = true
      this.validateOrderQty(row)
      if (this.isValidateOrderQty) {
        const $table = this.$refs.resultTable
        $table.clearActived()
      }
    },
    selectWarehouse() {
      this.warehouseForm.warehouseCode = ''
      this.dialogFormVisible = true
      // this.listWarehouse()
    },
    edit(row) {
      this.applyFormWarehouseCode = row.warehouseCode
      this.warehouseList = []
      const list = { code: row.warehouseCode, codeName: row.warehouseName }
      this.warehouseList.push(list)
      this.dialogFormVisible = false
    },
    getWarehouseName(code) {
      const obj = this.warehouseData.filter(item => item.warehouseCode === code)[0]
      return obj ? '【' + code + '】' + obj.warehouseName : ''
    },
    getProcessType(val) {
      const obj = this.processTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getTransType(val) {
      const obj = this.transTypeList.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getWarehouseType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.warehousetypeList, cellValue)
    },
    changeResultProcessType(row) {
      // 同仓调库，供应商等于申请备库的仓库
      if (row.processType === '4' || row.processType === '5') {
        this.supplierList = this.warehouseList
        row.orderStock = this.applyFormWarehouseCode
      }
      // 采购时,运输方式默认为海运
      if (row.processType === '1' || row.processType === '2') {
        this.supplierList = this.supplieData
        row.orderStock = this.supplierList[0].code
        row.transType = '0'
      }
    },
    //    <!--add by WuWeiDong 20230926  bug 10925  手动生成请购数量按最小包装数量 -->
    validateOrderQty(row) {
      if (row.processType === '1' || row.processType === '2') {
        if (row.orderQty > 0 && this.processResultForm.minPackageQty > 0) {
          if (row.orderQty % this.processResultForm.minPackageQty !== 0) {
            this.isValidateOrderQty = false
            this.$message.error('采购数量必须是最小包装数倍数。')
          }
        }
      }
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
