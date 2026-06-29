<template>
  <div>
    <el-dialog :visible.sync="show" title="取消订单" top="10vh" width="1200px" @close="close">
      <el-form>
        <!--取消原因-->
        <el-row style="margin: 10px 10px; min-height: 60px">
          <el-col :span="9">
            <el-form-item label="取消原因：">
              <el-cascader
                v-model="reason"
                :options="DictData.cancelReasonOption"
                style="width: 250px;"
              />
            </el-form-item>
          </el-col>
          <el-col v-show="showOtherReasonInput" :span="12">
            <el-form-item label="输入原因：" label-width="100px">
              <el-input
                v-model="otherReasonText"
                :autosize="{minRows: 2 }"
                type="textarea"
                style="width: 500px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <!--采购单列表-->
        <el-row style="margin: 10px 10px">
          <el-form-item label="取消采购单："/>
          <el-table
            v-loading="loading"
            :data="purchaseTableData"
            :header-cell-style="{'background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','font-size': '15px'}"
            :cell-style="{'font-size': '15px'}"
            highlight-current-row
            height="180px"
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
        </el-row>
      </el-form>
      <!--操作按钮-->
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
export default {
  name: 'OpsCancelOrderDialog',
  props: {
    // 是否显示弹窗
    visible: {
      type: Boolean,
      default: false
    },
    // 取消原因传入
    cancelReason: {
      type: Array,
      default: () => (['', '', ''])
    },
    orderData: {
      type: Object,
      default: () => ({
        rorderFno: '',
        rorderNo: '',
        rorderItem: '',
        orderType: '',
        origin: '接单查询删单'
      })
    }
  },
  data() {
    return {
      show: false,
      DictData: {
        cancelReasonOption: [
          // 取消菜单
          {
            value: '客户',
            label: '客户',
            children: [
              {
                value: '项目取消',
                label: '项目取消'
              },
              {
                value: '选型错误',
                label: '选型错误'
              },
              {
                value: '对手替换',
                label: '对手替换'
              },
              {
                value: '客户货期提前',
                label: '客户货期提前'
              },
              {
                value: '制造货期延迟',
                label: '制造货期延迟'
              },
              {
                value: '客户订单错误',
                label: '客户订单错误'
              },
              {
                value: '欠款问题',
                label: '欠款问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '代理店',
            label: '代理店',
            children: [
              {
                value: '选型错误',
                label: '选型错误'
              },
              {
                value: '客户订单错误',
                label: '客户订单错误'
              },
              {
                value: '欠款问题',
                label: '欠款问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '营业所',
            label: '营业所',
            children: [
              {
                value: '选型错误',
                label: '选型错误'
              },
              {
                value: '客户订单错误',
                label: '客户订单错误'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '业务处理中心',
            label: '业务处理中心',
            children: [
              {
                value: 'SMC内部订单错误',
                label: 'SMC内部订单错误'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '中国制造通知',
            label: '中国制造通知',
            children: [
              {
                value: '制造货期延迟',
                label: '制造货期延迟'
              },
              {
                value: '错误型号',
                label: '错误型号'
              },
              {
                value: '收敛品',
                label: '收敛品'
              },
              {
                value: '贩卖限制品',
                label: '贩卖限制品'
              },
              {
                value: '产地问题',
                label: '产地问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '日本通知',
            label: '日本通知',
            children: [
              {
                value: '制造货期延迟',
                label: '制造货期延迟'
              },
              {
                value: '组装问题',
                label: '组装问题'
              },
              {
                value: '错误型号',
                label: '错误型号'
              },
              {
                value: '收敛品',
                label: '收敛品'
              },
              {
                value: '贩卖限制品',
                label: '贩卖限制品'
              },
              {
                value: '申请特注',
                label: '申请特注'
              },
              {
                value: 'SHIKOMI不足',
                label: 'SHIKOMI不足'
              },
              {
                value: '产地问题',
                label: '产地问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          },
          {
            value: '业务课',
            label: '业务课',
            children: [
              {
                value: '错误型号',
                label: '错误型号'
              },
              {
                value: '收敛品',
                label: '收敛品'
              },
              {
                value: '贩卖限制品',
                label: '贩卖限制品'
              },
              {
                value: '申请特注',
                label: '申请特注'
              },
              {
                value: '海关不可进口',
                label: '海关不可进口'
              },
              {
                value: 'SHIKOMI不足',
                label: 'SHIKOMI不足'
              },
              {
                value: '产地问题',
                label: '产地问题'
              },
              {
                value: '其他',
                label: '其他'
              }
            ]
          }
        ],
        supplier: [],
        requestStatus: [],
        purchaseStatus: []
      },
      // 选择的删单原因
      reason: [],
      otherReasonText: '',
      // 采购删单的列表
      loading: true,
      cancelBtnLoading: false,
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
      purchaseTableData: []
    }
  },
  computed: {
    // 其他原因输入框显示
    showOtherReasonInput() {
      return this.reason && this.reason[1] === '其他'
    }
  },
  watch: {
    visible: {
      deep: true, // 深度监听
      handler(newObj, oldObj) {
        if (newObj) {
          this.show = newObj
          this.reason = this.cancelReason.slice(0, 2)
          this.otherReasonText = this.cancelReason[2]
          this.getPurchase()
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
    // 查询采购单信息
    getPurchase() {
      this.loading = true
      findRequestPurchase({
        orderId: this.orderData.rorderNo,
        orderItem: this.orderData.rorderItem
      }).then(res => {
        this.loading = false
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
    },

    // 取消操作
    cancelEvent() {
      this.cancelBtnLoading = true
      this.cancelOrderMethod()
    },
    cancelOrderMethod() {
      cancelOrder({
        purchaseList: this.purchaseTableData,
        cancelForOrderDto: {
          orderFno: this.orderData.rorderFno,
          orderId: this.orderData.rorderNo,
          orderItem: String(this.orderData.rorderItem),
          orderType: this.orderData.orderType,
          duty: this.reason[0],
          reason: this.showOtherReasonInput ? this.otherReasonText : this.reason[1],
          userDto: { userName: this.$store.getters.name, ip: '' },
          origin: this.orderData.origin
        }
      }).then(res => {
        this.cancelBtnLoading = false
        this.close()
        if (res.code === 200) {
          this.smcInfoMsg(res.data)
          this.$emit('handleSuccess', res.message)
        } else {
          this.smcErrorMsg(res.message)
          this.$emit('handleSuccess', res.message)
        }
      })
    },
    // 关闭窗口
    close() {
      this.show = false
      this.purchaseTableData = []
      this.cancelBtnLoading = false
      this.reason = []
      this.otherReasonText = ''
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
</style>
