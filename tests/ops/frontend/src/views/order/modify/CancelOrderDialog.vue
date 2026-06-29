<template>
  <div>
    <el-dialog :visible.sync="show" title="取消订单" top="15vh" width="160vh" @close="close">
      <el-form>
        <el-divider content-position="left" class="divider_margin">采购单状态</el-divider>
        <el-row style="margin: 10px 10px">
          <el-table
            v-loading="loading"
            :data="purchaseTableData"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','font-size': '15px'}"
            :cell-style="{'font-size': '15px'}"
            height="50vh"
            highlight-current-row
            border
            stripe
          >
            <!-- 表头字段 -->
            <el-table-column
              label="供应商同意删单"
              align="center"
              header-align="center">
              <template v-slot="{row}">
                <el-checkbox v-model="row.deleteok" :disabled="row.lock" @change="changeHandle(row)"/>
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
                <el-input v-model="row.endUser" :disabled=" row.transfer===false" clearable size="small" />
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
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="9" :span="2">
            <el-button :loading="cancelBtnLoading" type="primary" @click="cancelEvent">取消订单</el-button>
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
  findRequestPurchase,
  findSupplier,
  cancelOrder
} from '@/api/warehouseManage'
import { getDataCodesTree } from '@/api/common/dict'
import { updateOrderModifyStatusById } from '@/api/order/modifyorder'
export default {
  name: 'OpsCancelOrderDialog',
  props: {
    // 是否显示弹窗
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
      // 弹窗显示
      show: false,
      DictData: {
        supplier: [],
        requestStatus: [],
        purchaseStatus: []
      },
      // 采购删单的列表
      loading: false,
      cancelBtnLoading: false,
      // 采购单表格字段
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
        },
        {
          label: '责任人',
          width: 90,
          prop: 'duty'
        }, {
          label: '原因',
          prop: 'reason',

          width: 90
        }
      ],
      // 采购单表格数据
      purchaseTableData: []
    }
  },
  computed: {
  },
  watch: {
    visible: {
      deep: true,
      async handler(newVal) {
        if (newVal) {
          this.show = newVal
          await this.getPurchase()
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
  },
  methods: {
    changeHandle(row) {
      if (row.deleteok) {
        row.transfer = false
      }
    },
    async getPurchase() {
      try {
        this.loading = true
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
            for (const item of purchaseDataList) {
              await this.initCancelPurchaseCheckbox(item, order.orderModify)
              item.duty = order.orderModify.responsibleParty
              item.reason = order.orderModify.cancelReason === '其他' ? order.orderModify.reason : order.orderModify.cancelReason
            }
            // 将返回的数据添加到采购单表格数据中
            this.purchaseTableData.push(...purchaseDataList)
            order.purchaseData = purchaseDataList
          }
        }
      } catch (error) {
        console.log('获取采购单失败:', error)
      } finally {
        this.loading = false
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

    // 取消按钮的操作
    async cancelEvent() {
      try {
        this.cancelBtnLoading = true
        for (const order of this.orderData) {
          await this.cancelSingleOrder(order)
        }
      } catch (error) {
        this.smcErrorMsg('订单删除异常：' + error)
      } finally {
        this.cancelBtnLoading = false
      }
      // 关闭弹窗，清理数据
      this.close()
      // 取消成功后刷新数据
      this.$emit('handleSuccess', '订单取消完成')
    },
    /**
       * canceOrderData={
       * orderFno:'',
       * orderId:'',
       * orderItem:'',
       * orderType:'',
       * orderModify:{},
       * purchaseData:[]
       */
    // 取消单个订单
    async cancelSingleOrder(cancelOrderData) {
      const updateOrderModifyParam = {
        id: cancelOrderData.orderModify.id,
        orderFno: cancelOrderData.orderModify.orderNo,
        status: 5
      }
      // 更新orderModify表的状态为5处理中
      const res = await updateOrderModifyStatusById(updateOrderModifyParam)
      if (res.success) {
        const cancelOrderParam = {
          purchaseList: cancelOrderData.purchaseData,
          cancelForOrderDto: {
            orderFno: cancelOrderData.orderFno,
            orderId: cancelOrderData.orderId,
            orderItem: String(cancelOrderData.orderItem),
            orderType: cancelOrderData.orderType,
            duty: cancelOrderData.orderModify.responsibleParty,
            reason: cancelOrderData.orderModify.cancelReason === '其他' ? cancelOrderData.orderModify.reason : cancelOrderData.orderModify.cancelReason,
            userDto: { userName: this.$store.getters.name, ip: '' },
            origin: '接单查询删单'
          }
        }
        const res = await cancelOrder(cancelOrderParam)
        if (res.code === 200 || res.code === 201) {
          this.smcInfoMsg(updateOrderModifyParam.orderFno + ':' + res.data)
        } else {
          // 更新orderModify表的状态为2待处理
          updateOrderModifyParam.status = 2
          await updateOrderModifyStatusById(updateOrderModifyParam)
          this.smcErrorMsg(updateOrderModifyParam.orderFno + ':' + res.message)
        }
      }
    },
    // 关闭窗口
    close() {
      this.show = false
      this.purchaseTableData = []
      this.cancelBtnLoading = false
      // 通知父组件更新显示状态，不然下次打不开弹窗
      this.$emit('update:visible', false)
    },
    // 格式化供应商代码
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
