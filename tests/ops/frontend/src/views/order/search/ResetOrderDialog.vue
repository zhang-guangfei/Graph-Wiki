<template>
  <div>
    <el-dialog :visible.sync="show" title="订单还原" top="10vh" width="1200px" @close="close">
      <el-form>
        <el-divider content-position="left" class="divider_margin">采购单状态</el-divider>
        <el-table
          v-loading="purchaseTableLoading"
          :data="purchaseTableData"
          :header-cell-style="style.headerCell"
          :cell-style="style.tableCell"
          highlight-current-row
          height="180px"
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
              <el-input v-model="row.endUser" :disabled=" row.transfer===false" clearable />
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
        height="200px"
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
import { getDataCodesTree } from '@/api/common/dict'
import { resetOrder } from '@/api/order/search'
export default {
  name: 'OpsResetOrderDialog',
  props: {
    visible: {
      type: Boolean,
      default: false
    },
    // 订单修改界面需要填写此项，用于回更订单处理状态
    orderModify: {
      type: Object,
      default: () => ({})
    },
    orderData: {
      type: Object,
      // eslint-disable-next-line vue/require-valid-default-prop
      default: {
        rorderFno: '',
        rorderNo: '',
        rorderItem: '',
        orderType: '',
        origin: '接单查询删单'
      }
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
          label: '可用数量',
          prop: 'availableQuantity',
          align: 'right',
          width: 100
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
    getPurchase() {
      this.purchaseTableLoading = true
      findRequestPurchase({
        orderId: this.orderData.rorderNo,
        orderItem: this.orderData.rorderItem
      }).then(res => {
        this.purchaseTableLoading = false
        // 初始化采购单删除的勾选框
        if (res.code === 200) {
          res.data.forEach(item => {
            this.initCancelPurchaseCheckbox(item)
          })
          this.purchaseTableData = res.data
        } else {
          this.purchaseTableData = []
        }
      })
    },
    getAvailableInv() {
      this.AvailableInvTableLoading = true
      showInvByItemQtyRest({
        orderId: this.orderData.rorderNo,
        orderItem: this.orderData.rorderItem,
        qty: this.orderData.quantity,
        modelNo: this.orderData.modelNo
      }).then(res => {
        if (res.success) {
          this.AvailableInvTableLoading = false
          if (res.data.length > 0) {
            this.AvailableInvTableData = res.data
          } else {
            this.smcErrorMsg('没有可用库存')
          }
        }
      }).catch(error => {
        this.resetDialog.Loading = false
        this.smcErrorMsg('请求失败' + error.toString())
      })
    },

    // 初始化采购删单勾选框
    initCancelPurchaseCheckbox(item) {
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
      if (this.orderModify.id !== undefined || item.deleteok === false) {
        if (this.orderModify.transfer != null && this.orderModify.transfer) {
          if (item.deleteok === false) {
            item.transfer = this.orderModify.transfer
          }
          if (this.orderModify.endUserForTransferSpecial != null) {
            item.endUser = this.orderModify.endUserForTransferSpecial
          }
          this.$forceUpdate()
        }
      }
    },

    // 订单还原操作
    resetEvent() {
      this.resetOrderBtnLoading = true
      const purchaseDeleteData = this.purchaseTableData.find(item => item.deleteok === true)
      resetOrder({
        orderId: this.orderData.rorderNo,
        orderItem: this.orderData.rorderItem,
        delPo: purchaseDeleteData !== undefined,
        userName: this.$store.getters.name,
        po: this.purchaseTableData
      }).then(res => {
        this.close()
        if (res.code === 200 || res.code === 201) {
          this.smcInfoMsg(res.message)
          this.$emit('handleSuccess', res.message)
        } else {
          this.smcErrorMsg(res.message)
          this.$emit('handleFailure', res.message)
        }
      })
    },
    // 关闭窗口
    close() {
      this.show = false
      this.resetOrderBtnLoading = false
      this.purchaseTableData = []
      this.AvailableInvTableData = []
      this.$emit('update:visible', false)
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
