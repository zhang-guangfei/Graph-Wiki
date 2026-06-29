<template>
  <div>
    <el-tabs v-model="activeTabPane" type="card" @tab-click="handleTabsClick">
      <!--状态管理-->
      <el-tab-pane :label="tabPane[0].lable" :name="tabPane[0].name" >
        <vxe-table
          v-loading="orderStatus.isLoading"
          :data="orderStatus.tableData"
          :height="tableHeight"
          :header-cell-style="tableStyle.tableHeaderCellStyle"
          :cell-style="tableStyle.tableCellStyle"
          :sort-config="{defaultSort: {field: 'splitNo', order: 'asc'}, orders: ['desc', 'asc', null]}"
          :span-method="spanMethod"
          header-align="center"
          stripe
          border
          resizable>
          <vxe-table-column v-if="orderNo.dlvEntire==='5'" :formatter="nofityFormatter" field="planNo" title="已发/分配/计划" width="130" align="center"/>
          <vxe-table-column v-if="orderNo.dlvEntire==='5'" formatter="formatDateNull" title="计划客户货期" field="hopeDate" width="110" align="center"/>
          <vxe-table-column :formatter="['formatDict',DictData.warehouse]" field="warehouseCode" title="发货仓" width="120" align="center" />
          <vxe-table-column field="wmOrderId" title="发货单号" width="200" />
          <vxe-table-column formatter="formatDateNull" title="预计送达日" field="estimatedDeliveryDay" width="100" align="center"/>
          <vxe-table-column formatter="formatOrderFullNo" field="pcoItem" title="订单号" width="140" />
          <vxe-table-column field="modelno" title="型号" width="150" show-overflow/>
          <vxe-table-column field="qty" title="数量" width="50" align="right"/>
          <vxe-table-column field="qtyIn" title="货齐数" width="65" align="right"/>
          <vxe-table-column field="qtyOut" title="已发数" width="65" align="right"/>
          <vxe-table-column :formatter="['formatStatus',DictData.status]" field="statusDesc" title="当前状态" width="150" align="center"/>
          <vxe-table-column
            field="statusInfo"
            show-overflow="ellipsis"
            title="状态信息"
            min-width="100">
            <template v-slot="{ row }" >
              <!--悬浮窗-->
              <el-popover
                ref="popover"
                placement="top-start"
                trigger="click"
              >
                <!--悬浮的展示表格-->
                <vxe-table
                  :show-header="false"
                  :data="statusInfoData(row.statusInfo)"
                  :cell-style="popoverTableCellStyle"
                >
                  <vxe-table-column field="label" min-width="120"/>
                  <vxe-table-column field="value" min-width="200"/>
                </vxe-table>
                <!--触发器-->
                <div slot="reference" style="cursor: pointer">
                  <!--加粗的描述信息-->
                  <div v-if="row.statusInfo" v-html="formatStatusInfo(row.statusInfo)"/>
                </div>
              </el-popover>
            </template>
          </vxe-table-column>
          <vxe-table-column field="associateNo" title="关联单号" width="180" show-overflow="ellipsis" ><template
            v-slot="{ row }" >
            <el-popover
              ref="popover"
              placement="top-start"
              trigger="click"
            >
              <span>
                {{ row.associateNo }}
              </span>
              <div slot="reference" style="cursor: pointer">
                {{ row.associateNo }}
              </div>
            </el-popover>
          </template>
          </vxe-table-column>
          <vxe-table-column formatter="formatDateTime" field="modifyTime" title="操作时间" width="150" show-overflow />
          <vxe-table-column title="操作" width="60">
            <template v-slot="{row}">
              <el-button
                v-permission="['132053','618583']"
                v-if="showZDButton(row) && row.orderId"
                type="text"
                size="small"
                @click.stop="openReorderWindow(row,1)">转订
              </el-button>
            </template>
          </vxe-table-column>

        </vxe-table>
      </el-tab-pane>
      <!-- 订单分配管理-->
      <el-tab-pane :label="tabPane[1].lable" :name="tabPane[1].name">
        <vxe-table
          v-loading="oldOrderAssign.isLoading"
          :data="oldOrderAssign.tableData"
          :height="tableHeight"
          :header-cell-style="tableStyle.tableHeaderCellStyle"
          :cell-style="tableStyle.tableCellStyle"
          header-align="center"
          stripe
          border
          resizable>
          <vxe-table-column :formatter="orderAssignOrderNoFormatter" field="orderNo" title="订单号"/>
          <vxe-table-column field="modelno" title="型号"/>
          <vxe-table-column field="quantity" title="分配数量" />
          <vxe-table-column :formatter="['formatDict',DictData.oldStockType]" field="stockType" title="出库区分" />
          <vxe-table-column :formatter="['formatDict',DictData.warehouse]" field="stockCode" title="出库区分代码" />
          <vxe-table-column :formatter="['formatDict',DictData.invType]" field="inventoryTypeCode" title="库存类别" />
          <vxe-table-column
            :formatter="orderAssignAssociateNoFormatter"
            field="associateNo"
            title="关联单号"
            width="150"
            show-overflow/>
          <vxe-table-column :formatter="['formatDict',DictData.warehouse]" field="supplierid" title="供应商" />
          <vxe-column field="inventoryRisk" title="风险在库">
            <template slot-scope="scope">
              <span>{{ scope.row.inventoryRisk ? '是' : '否' }}</span>
            </template>
          </vxe-column>
        </vxe-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
<script>

import { getOrderStatus, getOldOrderAssign } from '@/api/order/search'
import { findAllHouse, findSupplier, findInventoryType } from '@/api/warehouseManage'
import { getDataCodesTree } from '@/api/common/dict'
export default {
  name: 'OrderStatusTable',
  props: {
    orderNo: {
      type: Object,
      required: true
    },
    height: {
      type: Number,
      default: 200
    }
  },
  data() {
    return {
      tabPane: [
        { lable: '状态管理', name: 'orderStatus' },
        { lable: '分配管理', name: 'oldOrderAssign' }
      ],
      activeTabPane: 'orderStatus',
      // 字段数据
      DictData: {
        warehouse: new Map(),
        invType: new Map(),
        status: new Map(),
        oldStatus: new Map(),
        oldStatusDetail: new Map(),
        oldStockType: new Map(),
        expressCompany: new Map()
      },
      // 表格样式
      tableStyle: {
        tableHeaderCellStyle: {
          backgroundColor: 'rgb(117, 144, 168)',
          color: 'rgba(253, 253, 253, 0.938)',
          fontSize: '14px',
          padding: '1px'
        },
        tableCellStyle: { padding: '0px 1px', height: '33px' }
      },
      // 查询状态
      orderStatus: {
        // 订单明细表数据
        tableData: [],
        isLoading: false,
        mergeColumn: ['planNo', 'hopeDate', 'wmOrderId', 'warehouseCode', 'splitNo', 'pcoItem', 'modelno', 'estimatedDeliveryDay'],
        majorMergeColumn: 'wmOrderId',
        mergeCellsArray: {},
        mergeCells: {},
        mergeOpen: true
      },
      oldOrderAssign: {
        // 订单明细表数据
        tableData: [],
        isLoading: false
      }

    }
  },
  computed: {
    tableHeight() {
      return this.height - 50
    }
  },
  watch: {
    orderNo: {
      handler: function(newObj) {
        this.findTableData(newObj)
      },
      immediate: true // 调用handler方法以初始化表格数据
    }
  },
  created() {
    // 仓库名称
    findAllHouse().then(result => {
      result.data.forEach(data => {
        this.DictData.warehouse.set(data.warehouseCode, data.warehouseName)
      })
    })
    findSupplier().then(result => {
      result.content.forEach(data => {
        this.DictData.warehouse.set(data.id, data.name)
      })
    })
    findInventoryType().then(result => {
      result.data.forEach(data => {
        this.DictData.invType.set(data.inventoryTypeCode, data.description)
      })
    }).catch(res => {
      this.smcErrorMsg(res.message)
    })
    // 状态名称
    this.getDict('4501', this.DictData.status)
    // 老状态字典
    this.getDict('4305', this.DictData.oldStatus)
    this.getDict('4304', this.DictData.oldStatusDetail)
    this.getDict('4306', this.DictData.oldStockType)
    this.getDict('4004', this.DictData.oldStockType)
    this.getDict('1024', this.DictData.expressCompany)
    this.DictData.oldStockType.set('EXCEPTION', '异常')
    this.DictData.oldStockType.set('NA', '')
  },
  methods: {
    popoverTableCellStyle({ row, column, rowIndex, columnIndex }) {
      // 这里可以根据具体条件设置颜色
      return { padding: '0px 1px', height: '33px', color: row.color }
    },
    // 是否应该显示转订按钮
    showZDButton(row) {
      // WMTask 等待出库 去掉
      var statusArr = ['WMReceive', 'WMDelivery', 'Transition', 'Finish', 'CANCEL']
      return !statusArr.includes(row.status)
    },

    formatStatusInfo(value) {
      try {
        const jsonObject = JSON.parse(value)
        // 创建一个空字符串来存储格式化后的结果
        let formattedString = ''
        // 遍历对象的每一个属性
        for (const key in jsonObject) {
          // 检查对象自身是否有此属性，防止来自原型链的属性干扰
          if (jsonObject.hasOwnProperty(key)) {
            // 将当前键值对添加到字符串中，用逗号分隔
            let value = jsonObject[key]
            if (this.DictData.warehouse.has(value)) {
              value = this.DictData.warehouse.get(value)
            }
            if (this.DictData.expressCompany.has(value)) {
              value = this.DictData.expressCompany.get(value)
            }
            var keyParam = key
            var color = 'black'
            if (keyParam === '到场日期') {
              keyParam = '预计到物流日'
              color = 'red'
            }
            formattedString += `<span style="font-weight: bold;color:${color}">${keyParam}</span>:<span style="color:${color}">${value}</span>,`
          }
        }
        // 移除最后一个逗号
        formattedString = formattedString.slice(0, -1)
        // 输出格式化后的字符串
        return '<div>【' + formattedString + '】</div>'
      } catch (e) {
        return value
      }
    },
    statusInfoData(str) {
      var tableData = []
      try {
        var map = JSON.parse(str)
        for (const key in map) {
        // 检查对象自身是否有此属性，防止来自原型链的属性干扰
          if (map.hasOwnProperty(key)) {
          // 将当前键值对添加到字符串中，用逗号分隔
            let value = map[key]
            if (this.DictData.warehouse.has(value)) {
              value = this.DictData.warehouse.get(value)
            }
            if (this.DictData.expressCompany.has(value)) {
              value = this.DictData.expressCompany.get(value)
            }
            var keyParam = key
            var color = 'black'
            if (keyParam === '到场日期') {
              keyParam = '预计到物流日'
              color = 'red'
            }
            tableData.push({ label: keyParam, value: value, color: color })
          }
        }
      } catch (e) {
        console.log(e)
        return tableData
      }
      return tableData
    },

    handleTabsClick() {
      this.findTableData(this.orderNo)
    },
    findTableData(query) {
      this.orderStatus.isLoading = true
      this.orderStatus.tableData = []
      if (this.activeTabPane === this.tabPane[0].name) {
        this.findOrderStatusData(query)
      }
      if (this.activeTabPane === this.tabPane[1].name) {
        this.findoldOrderAssignData(query)
      }
    },
    findoldOrderAssignData() {
      if (this.orderNo.orderId === undefined) {
        this.oldOrderAssign.tableData = []
        this.oldOrderAssign.isLoading = false
        return false
      }
      this.oldOrderAssign.isLoading = true
      getOldOrderAssign(this.orderNo.orderId, this.orderNo.orderItem).then(res => {
        if (res.success) {
          this.oldOrderAssign.tableData = res.data
        } else {
          this.smcErrorMsg(res.message)
        }
        this.oldOrderAssign.isLoading = false
      }).catch(error => {
        console.log(error)
        this.oldOrderAssign.isLoading = false
      })
    },

    // 查询订单状态表
    findOrderStatusData() {
      if (this.orderNo.orderId === undefined) {
        this.orderStatus.tableData = []
        this.orderStatus.isLoading = false
        return false
      }
      this.orderStatus.isLoading = true
      getOrderStatus(this.orderNo.orderId, this.orderNo.orderItem).then(res => {
        if (res.success) {
          this.orderStatus.tableData = res.data
          if (this.orderStatus.mergeOpen) {
            this.handleMergeCells(this.orderStatus.tableData)
          }
        } else {
          this.smcErrorMsg(res.message)
        }
        this.orderStatus.isLoading = false
      }).catch(error => {
        console.log(error)
        this.orderStatus.isLoading = false
      })
    },

    nofityFormatter({ row }) {
      if (row.planQty === 0 || row.planQty == null) {
        return '-/-/-'
      }
      var outQty = '-'
      var matchQty = '-'
      var planQty = '-'
      if (row.planQty) {
        planQty = row.planQty
      }
      if (row.matchQty) {
        matchQty = row.matchQty
      }
      if (row.outQty) {
        outQty = row.outQty
      }
      var str = outQty + '/' + matchQty + '/' + planQty
      return str
    },
    // 查字典
    getDict(code, dict) {
      getDataCodesTree(code).then(result => {
        if (result.code === '200') {
          result.content.forEach(data => {
            dict.set(data.code, data.codeName)
          })
        }
      }).catch(error => {
        console.log(error)
      })
    },

    orderAssignOrderNoFormatter({ row }) {
      var number = ''
      if (row.orderNo !== null && row.orderNo !== '') {
        number = number + row.orderNo
        if (row.orderItem !== null && row.orderItem !== 0) {
          number = number + '-' + row.orderItem
        }
      }
      return number
    },

    orderAssignAssociateNoFormatter({ row }) {
      var number = ''
      if (row.associateNo !== null && row.associateNoItem !== '') {
        number = number + row.associateNo
        if (row.associateNoItem !== null && row.associateNoItem !== 0) {
          number = number + '-' + row.associateNoItem
          if (row.associateNoSplitNo !== null && row.associateNoSplitNo !== 0) {
            number = number + '-' + row.associateNoSplitNo
          }
        }
      }
      return number
    },

    openReorderWindow(row, index) {
      this.$emit('openReorderWindow', row, index)
    },

    handleMergeCells(arr) {
      // 要合并的字段
      var mergeColumns = this.orderStatus.mergeColumn
      // 1.按照字段分组数据
      var dataObj = {} // { wm_order_id=['DO001','DO002','DO002'],warehouse_code=['KBJ','KSH','KSH'] }
      for (const mergeColumn of mergeColumns) {
        dataObj[mergeColumn] = arr.map(element => element[mergeColumn])
      }
      // 打印本列的所有数据
      // console.log(dataObj)
      // 2.检查相同的值
      var mergeData = {} // 创建合并行数obj    // { wm_order_id=[1,2,0],warehouse_code=[1,2,0] }
      for (const mergeColumn of mergeColumns) {
        var dataArr = dataObj[mergeColumn]
        var mergeArr = [] // 合并的行数组成的数组，为输出数据
        for (let i = 0; i < dataArr.length; i++) {
          // 要校验的行
          var validateArr = mergeData[this.orderStatus.majorMergeColumn]
          // 被校验列已经被合并
          var validateFlag = !validateArr || (validateArr[i] !== undefined && validateArr[i] === 0)
          // 先看本行i会不会被合并， 本行i的值等于上一行i-1的值，
          // 如果本行和上一行相同，则值为0
          if (i !== 0 && dataArr[i] === dataArr[i - 1] && validateFlag) {
            mergeArr[i] = 0
          } else { // 如果本行i和上一行不同，则向下看
            // 如果本行和下一行i+1相同,且校验列的下一行也被合并，则+1
            // 如果本行i和下一行i+1不同，则为1
            // 设初值1
            mergeArr[i] = 1
            for (let j = i; dataArr[j + 1] !== undefined && dataArr[j] === dataArr[j + 1]; j++) {
              // 如果为空，则+1，
              // 如果被合并，则+1
              // 如果没有被合并，则退出
              var validateNextFlag = !validateArr || (validateArr[j + 1] === 0)
              if (!validateNextFlag) {
                break
              }
              mergeArr[i]++
            }
          }
        }
        mergeData[mergeColumn] = mergeArr
      }
      // console.log(mergeData)// { wm_order_id=[1,2,0],warehouse_code=[1,2,0] }
      this.orderStatus.mergeCells = mergeData
    },

    spanMethod({ _rowIndex, column }) {
      var mergeData = this.orderStatus.mergeCells
      if (mergeData[column.property]) {
        var countRowSpan = mergeData[column.property][_rowIndex]
        if (countRowSpan === 0) {
          return { rowspan: 0, colspan: 0 }
        } else {
          return { rowspan: countRowSpan, colspan: 1 }
        }
      }
    }

  }
}
</script>
<style scoped lang="scss">
</style>
