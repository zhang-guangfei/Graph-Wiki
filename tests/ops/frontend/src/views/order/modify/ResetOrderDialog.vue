<template>
  <div>
    <el-dialog :visible.sync="show" title="订单还原" top="10vh" width="150vh" @close="close">
      <el-form>
        <el-divider content-position="left" class="divider_margin">采购单状态</el-divider>
        <el-table
          v-loading="purchaseTableLoading"
          :data="purchaseTableData"
          :header-cell-style="style.headerCell"
          :cell-style="style.tableCell"
          highlight-current-row
          height="30vh"
          border
          stripe
        >
          <!-- 表头字段 -->
          <el-table-column
            label="供应商同意删单"
            align="center"
            width="150px"
            header-align="center">
            <template v-slot="{row}">
              <el-checkbox v-model="row.deleteok" :disabled="row.lock" @change="changeHandle(row)" />
            </template>
          </el-table-column>
          <el-table-column
            label="是否调入专备"
            align="center"
            header-align="center">
            <template v-slot="{row}">
              <el-checkbox v-model="row.transfer" :disabled=" row.deleteok===true"/>
            </template>
          </el-table-column>
          <el-table-column
            label="调入客户代码"
            align="center"
            width="120"
            header-align="center">
            <template v-slot="{row}">
              <el-input v-model="row.endUser" :disabled=" row.transfer===false" clearable size="small"/>
            </template>
          </el-table-column>
          <el-table-column
            v-for="column in purchaseTableColumns"
            :fixed="column.fixed"
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :width="column.width"
            :type="column.type"
            :formatter="column.formatter"
            align="center"
            header-align="center"
            show-overflow-tooltip
          />
        </el-table>
      </el-form>
      <el-divider content-position="left" class="divider_margin">可用库存</el-divider>
      <el-table
        v-loading="AvailableInvTableLoading"
        :data="AvailableInvTableData"
        :header-cell-style="style.headerCell"
        height="25vh"
        highlight-current-row
        border
      >
        <el-table-column
          v-for="column in AvailableInvTableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :type="column.type"
          :align="column.align"
          :formatter="column.formatter"
          header-align="center"
        />
      </el-table>

      <span slot="footer">
        <el-row>
          <el-col :offset="9" :span="2">
            <el-button :loading="resetOrderBtnLoading" type="primary" @click="resetEvent">订单还原</el-button>
          </el-col>
          <el-col :offset="1" :span="2">
            <el-button @click="close">关闭</el-button>
          </el-col>
        </el-row>
      </span>
    </el-dialog>

  </div>
</template>
<script>
import {
  findRequestPurchase, findSupplier, findInventoryType, showInvByItemQtyRest } from '@/api/warehouseManage'
import { orderChangeToInitStatus } from '@/api/order/modifyorder'
import { getDataCodesTree } from '@/api/common/dict'
import { resetOrder } from '@/api/order/search'
export default {
  name: 'OpsResetOrderDialog',
  props: {
    visible: {
      type: Boolean,
      default: false
    },

    orderData: {
      type: Array,
      default: () => []
    }
  },
  data() {
    return {
      show: false,
      DictData: {
        supplier: [],
        requestStatus: [],
        purchaseStatus: [],
        inventoryStatus: [],
        inventoryType: []
      },
      style: {
        headerCell: {
          backgroundColor: 'rgb(117, 144, 168)',
          color: 'rgba(253, 253, 253, 0.938)',
          fontSize: '15px'
        },
        tableCell: { fontSize: '15px' }
      },
      // 采购删单的列表
      purchaseTableLoading: true,
      purchaseTableColumns: [
        {
          label: '请购单号',
          width: 120,
          formatter: row => {
            var requestNo = row.requestno
            if (row.requestItemno !== null) {
              requestNo = requestNo + '-' + row.requestItemno
            }
            if (row.requestSplitno !== null) {
              requestNo = requestNo + '-' + row.requestSplitno
            }
            return requestNo
          }
        },
        {
          label: '采购单号',
          width: 120,
          formatter: row => {
            var purchaseNo = row.purchaseno
            if (row.purchaseItemno !== null) {
              purchaseNo = purchaseNo + '-' + row.purchaseItemno
            }
            if (row.purchaseSplitno !== null) {
              purchaseNo = purchaseNo + '-' + row.purchaseSplitno
            }
            return purchaseNo
          }
        },
        {
          label: '是否合并',
          formatter: row => {
            if (row.merge) {
              return 'Y'
            }
            return 'N'
          },
          width: 90
        },
        {
          label: '型号',
          prop: 'modelno'
        },
        {
          label: '请购数量',
          prop: 'requestQuantity'
        },
        {
          label: '采购数量',
          prop: 'quantity'
        },
        {
          label: '供应商',
          prop: 'supplierid',
          width: 120,
          formatter: this.supplierFormatter
        },
        {
          label: '状态',
          prop: 'status',
          width: 150,
          formatter: row => {
            if (row.purchaseno) {
              return this.purchaseStatusFormatter(row.status)
            } else {
              return this.requestStatusFormatter(row.status)
            }
          }
        },
        {
          label: 'BIN',
          formatter: row => {
            if (row.bin) {
              return 'Y'
            }
            return 'N'
          },
          width: 90
        }
      ],
      purchaseTableData: [],
      // 可用库存的列表
      AvailableInvTableLoading: true,
      AvailableInvTableColumns: [
        {
          label: '序号',
          type: 'index',
          prop: 'index',
          align: 'center',
          width: 60
        },
        {
          label: '仓库名称',
          prop: 'warehouseName',
          width: 150
        },
        {
          label: '型号',
          prop: 'modelno',
          width: 200
        },
        {
          label: '库存状态',
          prop: 'inventoryStatus',
          width: 100,
          formatter: this.inventoryStatusFormatter
        },
        {
          label: '库存属性',
          prop: 'inventoryTypeCode',
          width: 100,
          formatter: this.inventoryTypeFormatter
        },
        {
          label: '大口',
          prop: 'dk'
        },
        {
          label: '可用数量',
          prop: 'availableQuantity',
          align: 'right',
          width: 100
        },
        {
          label: '发票号',
          prop: 'invoiceNo'
        },
        {
          label: '预计到货日期',
          prop: 'preReceiveDate',
          width: 120,
          formatter: this.dateFormatter
        },
        {
          label: '关联单号',
          width: 120,
          formatter: row => {
            var associate = ''
            if (row.associateNo !== null) {
              associate = row.associateNo
            } else {
              return associate
            }
            if (row.associateItemNo !== null) {
              associate = associate + '-' + row.associateItemNo
            }
            if (row.associateSplitNo !== null && row.associateSplitNo !== 0) {
              associate = associate + '-' + row.associateSplitNo
            }
            return associate
          }
        },
        {
          label: '客户代码',
          prop: 'customerNo',
          width: 100
        },
        {
          label: 'ppl',
          prop: 'ppl'
        },
        {
          label: '项目代码',
          prop: 'projectCode',
          width: 100
        },
        {
          label: '集团客户代码',
          prop: 'groupCustomerNo',
          width: 120
        },
        {
          label: '营业情报号',
          prop: 'salesInfoNo',
          width: 120
        }
      ],
      AvailableInvTableData: [],
      //   按钮
      resetOrderBtnLoading: false
    }
  },
  watch: {
    visible: {
      deep: true, // 深度监听
      handler(newObj, oldObj) {
        if (newObj) {
          this.show = newObj
          this.purchaseTableData = []
          this.AvailableInvTableData = []
          this.getPurchase()
          this.getAvailableInv()
        }
      }
    }
  },

  created() {
    // 供应商
    findSupplier().then(result => {
      result.content.forEach(dict => {
        this.DictData.supplier.push({ code: dict.id, desc: dict.name })
      })
    })

    getDataCodesTree('2033').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.purchaseStatus.push({ code: dict.code, desc: dict.codeName })
        })
      }
    })
    getDataCodesTree('2032').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.requestStatus.push({ code: dict.code, desc: dict.codeName })
        })
      }
    })
    getDataCodesTree('4010').then(result => {
      if (result.code === '200') {
        result.content.forEach(dict => {
          this.DictData.inventoryStatus.push({ code: dict.code, desc: dict.codeName })
        })
      }
    })
    // 字典：库存类别
    findInventoryType().then(res => {
      res.data.forEach(dict => {
        this.DictData.inventoryType.push({ code: dict.inventoryTypeCode, desc: dict.description })
      })
    })
  },
  methods: {
    changeHandle(row) {
      if (row.deleteok) {
        row.transfer = false
      }
    },
    // 查询采购单信息
    async getPurchase() {
      try {
        this.purchaseTableLoading = true
        // 初始化采购单表格
        this.purchaseTableData = []
        // 遍历要删除的订单，同步逐条查询采购单
        for (const order of this.orderData) {
          if (!this.show) {
            break
          }
          const param = {
            orderId: order.orderId,
            orderItem: order.orderItem
          }
          // 每次查询采购单都等待返回结果
          const res = await findRequestPurchase(param)
          if (res.code === 200) {
            const purchaseDataList = res.data
            console.log('采购单:', purchaseDataList)
            for (const item of purchaseDataList) {
              await this.initCancelPurchaseCheckbox(item, order.orderModify)
            }
            // 将返回的数据添加到采购单表格数据中
            this.purchaseTableData.push(...purchaseDataList)
            order.purchaseData = purchaseDataList
          }
        }
      } catch (error) {
        console.log('获取采购单失败:', error)
      } finally {
        this.purchaseTableLoading = false
      }
    },
    // 查询可用库存
    async getAvailableInv() {
      try {
        this.AvailableInvTableLoading = true
        this.AvailableInvTableData = []

        for (const order of this.orderData) {
          if (!this.show) {
            break
          }
          const param = {
            orderId: order.orderModify.rorderNo,
            orderItem: order.orderModify.rorderItem,
            qty: order.orderModify.quantity,
            modelNo: order.orderModify.modelNo
          }
          const res = await showInvByItemQtyRest(param)
          if (res.success) {
            console.log('可用库存:', res.data)
            if (res.data.length > 0) {
              // 将可用库存添加到表格中
              this.AvailableInvTableData.push(...res.data)
              // 提前解锁遮罩，不然查的太慢了
              this.AvailableInvTableLoading = false
            }
          }
        }
      } catch (error) {
        this.smcErrorMsg('获取可用数量失败：' + error.toString())
      } finally {
        this.AvailableInvTableLoading = false
      }
    },

    // 初始化采购删单勾选框
    initCancelPurchaseCheckbox(item, orderModifyData) {
      // 无po 可删 处理中
      if (item.status === '1' && item.purchaseno == null) {
        item.lock = true
        item.deleteok = true
      }
      // 无po 可删 处理中
      if (item.status === '2' && item.purchaseno == null) {
        item.lock = true
        item.deleteok = true
      }
      // 无po 可删 拦截
      if (item.status === '4' && item.purchaseno == null) {
        item.lock = true
        item.deleteok = true
      }
      // 无po 可删 拦截
      if (item.status === '5' && item.purchaseno == null) {
        item.lock = true
        item.deleteok = true
      }
      // 有po 已发送 不可删
      if (item.status === '1' && item.purchaseno != null) {
        item.lock = true
        item.deleteok = false
      }
      // 有po 已接单 可选择删除
      if (item.status === '2' && item.purchaseno != null) {
        item.lock = false
        item.deleteok = true
      }
      // 有po 运输中 不可选择
      if (item.status === '3' && item.purchaseno != null) {
        item.lock = true
        item.deleteok = false
      }
      // 有po 已完成 不可选择
      if (item.status === '5' && item.purchaseno != null) {
        item.lock = true
        item.deleteok = false
      }
      // 如果为合并采购，不可删
      if (item.merge) {
        item.deleteok = false
        item.lock = true
      }
      // 如果不为空，则说明是订单修改页面，需要选择调库计划
      if (orderModifyData.id !== undefined || item.deleteok === false) {
        // 是否制定调库计划，来源来自orderModify表TransferSpecial字段
        if (orderModifyData.transfer != null && orderModifyData.transfer) {
          if (item.deleteok === false) {
            item.transfer = orderModifyData.transfer
          }
          if (orderModifyData.endUserForTransferSpecial != null) {
            item.endUser = orderModifyData.endUserForTransferSpecial
          }
          // 强制重新渲染数据
          this.$forceUpdate()
        }
      }
    },

    // 订单还原操作
    async resetEvent() {
      try {
        this.resetOrderBtnLoading = true
        for (const order of this.orderData) {
          await this.resetSingleOrder(order)
        }
      } catch (error) {
        this.smcErrorMsg('订单删除异常：' + error)
      } finally {
        this.resetOrderBtnLoading = false
      }
      // 关闭弹窗，清理数据
      this.close()
      // 取消成功后刷新数据
      this.$emit('handleSuccess', '订单还原完成')
    },
    /**
     * resetOrderData={
     * orderFno:'',
     * orderId:'',
     * orderItem:'',
     * orderModify:{},
     * purchaseData:[]
     */
    async resetSingleOrder(resetOrderData) {
      const purchaseDeleteData = resetOrderData.purchaseData.find(item => item.deleteok === true)
      const param = {
        orderId: resetOrderData.orderId,
        orderItem: resetOrderData.orderItem,
        delPo: purchaseDeleteData !== undefined,
        userName: this.$store.getters.name,
        po: this.purchaseTableData
      }
      // 开始还原订单
      const res = await resetOrder(param)
      const data = {
        batchNo: resetOrderData.orderModify.batchNo,
        applyNo: resetOrderData.orderModify.applyNo,
        handStatus: '1',
        handDesc: res.message,
        userName: this.$store.getters.name,
        optUserNo: this.$store.getters.name
      }
      // 如果还原成功，则更新成
      if (res.code === 200 || res.code === 201) {
        data.handStatus = '1'
        await orderChangeToInitStatus(data)
        this.smcInfoMsg(res.message)
      } else {
        data.handStatus = '0'
        await orderChangeToInitStatus(data)
        this.smcErrorMsg(res.message)
      }
    },
    // 关闭窗口
    close() {
      this.show = false
      this.resetOrderBtnLoading = false
      this.purchaseTableData = []
      this.AvailableInvTableData = []
      // 通知父组件更新显示状态，不然下次打不开弹窗
      this.$emit('update:visible', false)
    },

    // 新增方法：取消所有请求
    cancelAllRequests() {
      if (this.cancelTokens.purchase) {
        this.cancelTokens.purchase.cancel('用户关闭对话框，取消采购单查询')
        this.cancelTokens.purchase = null
      }
      if (this.cancelTokens.inventory) {
        this.cancelTokens.inventory.cancel('用户关闭对话框，取消库存查询')
        this.cancelTokens.inventory = null
      }
    },
    // 格式化方法
    supplierFormatter(row, column, cellValue) {
      const item = this.DictData.supplier.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    requestStatusFormatter(value) {
      const item = this.DictData.requestStatus.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    purchaseStatusFormatter(value) {
      const item = this.DictData.purchaseStatus.find(dict => dict.code === value)
      return item ? item.desc : value
    },
    inventoryStatusFormatter(row, column, cellValue) {
      const item = this.DictData.inventoryStatus.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    },
    inventoryTypeFormatter(row, column, cellValue) {
      const item = this.DictData.inventoryType.find(dict => dict.code === cellValue)
      return item ? item.desc : cellValue
    }
  }
}
</script>

<style scoped lang="scss">
.divider_margin {
  margin: 20px 0;
  font-size: 50px;
}

.el-dialog__body {
    padding-top: 10px;
}
</style>
