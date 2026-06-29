
<template>
  <div class="base-index">
    <div class="public-container">
      <el-card shadow="hover" >
        <el-form ref="form" :model="condition" :inline="true" class="info-form" label-suffix=":" size="small">
          <span class="operation-button">
            <el-form-item>
              <el-tooltip effect="light" content="重置" placement="top">
                <el-button type="info" icon="el-icon-close" size="mini" circle @click="resetForm()"/>
              </el-tooltip>
            </el-form-item>
            <el-form-item>
              <el-button size="mini" type="primary" @click="getList">查询</el-button>
            </el-form-item>
            <!-- <el-form-item>
              <i :title="moreVisible === false? '展开': '收起'" :class="(moreVisible === false?'el-icon-arrow-down' : 'el-icon-arrow-up') +' el-icon-class'" @click="moreVisible = !moreVisible"/>
            </el-form-item> -->
          </span>
          <el-form-item label="请购单号" prop="orderNo">
            <el-input
              v-model="condition.orderNo"
              type="text"
              size="mini"
              placeholder="特价号"
              clearable/>
          </el-form-item>
          <el-form-item label="型号" prop="modelNo">
            <el-input
              v-model="condition.modelNo"
              type="text"
              size="mini"
              placeholder="型号"
              clearable/>
          </el-form-item>
          <!-- <el-form-item label="客户" prop="customerNo">
            <el-select
              v-model="condition.customerNo"
              :remote-method="remoteFilter"
              :loading="userListloading"
              :no-data-text="noDataText"
              filterable
              clearable
              remote
              reserve-keyword
              size="mini"
              placeholder="客户名称/代码"
              @change="getUserInfo">
              <el-option v-for="item in customerCodesOptions" :key="item.customerNo" :label="'【' + item.customerNo + '】' + item.customerName" :value="item.customerNo">
                <span style="float: left">{{ item.customerName }}</span>
                <span style="padding-right:20px; color: #8492a6; font-size: 12px">【{{ item.customerNo }}】</span>
              </el-option>
            </el-select>
          </el-form-item> -->
          <el-form-item label="请购单类别" prop="ordType">
            <el-select v-model="condition.ordType" size="mini" placeholder="请选择" clearable >
              <el-option
                v-for="item in ordTypeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <el-form-item label="请购单状态" prop="stateCode">
            <el-select v-model="condition.stateCode" size="mini" placeholder="请选择" clearable>
              <el-option
                v-for="item in stateCodeList"
                :key="item.value"
                :label="item.label"
                :value="item.value"/>
            </el-select>
          </el-form-item>
          <!-- <el-form-item label="申请日期" prop="applyDateRange">
            <el-date-picker
              :picker-options="pickerOptions"
              v-model="applyDateRange"
              type="daterange"
              format="yyyy-MM-dd"
              value-format="yyyy-MM-dd"
              size="mini"
              style="font-size:12px"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"/>
          </el-form-item> -->
        </el-form>
      </el-card>
    </div>
    <div class="public-table">
      <el-tabs v-model="activeName" type="card" class="consult-tab">
        <el-tab-pane :lazy="true" label="系统推荐合并采购" name="first">
          <i class="el-icon-coin" style="color: #FFA500;"/><span style="font-weight: 800; font-size: 13px">&nbsp;&nbsp;系统推荐合并采购</span>
          <vxe-grid
            :columns="tableColumn"
            :checkbox-config="{highlight: true, range: true}"
            :data.sync="tableData"
            :span-method="objectSpanMethod"
            border
            align="center"
            size="mini"
            auto-resize
            stripe
            resizable
            show-overflow
            show-header-overflow
            highlight-hover-column
            max-height="800">
            <!-- <vxe-table-column prop="modelno" label="型号" width="180"/>
        <vxe-table-column prop="shikomianswerno" label="Shikomi"/>
        <vxe-table-column prop="statecode" label="StateCode"/>
        <vxe-table-column prop="supplierid" label="供应商"/>
        <vxe-table-column prop="quantity" label="数量"/> -->
            <template v-slot:operate="{ row }">
              <el-tooltip effect="light" content="合并" placement="top">
                <el-button type="primary" size="mini" icon="el-icon-document" circle @click="splitRow(row)"/>
              </el-tooltip>
              <!-- <el-tooltip effect="light" placement="top">
            <span>
              <el-button type="primary" size="mini" icon="el-icon-star-off" circle />
            </span>
          </el-tooltip>
          <el-tooltip v-permission="['000010000001011']" effect="light" content="编辑" placement="top">
            <el-button type="primary" size="mini" icon="el-icon-edit" circle />
          </el-tooltip>
          <el-tooltip effect="light" content="作废" placement="top">
            <el-button type="primary" size="mini" icon="el-icon-s-release" circle />
          </el-tooltip> -->
            </template>
          </vxe-grid>
          <div style="margin-top:25px;margin-bottom:25px">
            <el-divider/>
          </div>
          <i class="el-icon-coin" style="color: #FFA500;"/><span style="font-weight: 800; font-size: 13px">&nbsp;&nbsp;手工不合并清单</span>
          <el-button :disabled="selectList.length <2" size="mini" type="primary" style="float:right;" @click="artificialMergeData">合并</el-button>
          <!-- <el-table :data="tableData2" border style="width: 100%; margin-top: 20px">
            <el-table-column prop="modelno" label="型号" width="180"/>
            <el-table-column prop="shikomianswerno" label="Shikomi"/>
            <el-table-column prop="statecode" label="StateCode"/>
            <el-table-column prop="supplierid" label="供应商"/>
            <el-table-column prop="quantity" label="数量"/>
          </el-table> -->
          <vxe-grid
            :columns="splitColumn"
            :checkbox-config="{highlight: true, range: true}"
            :data.sync="tableData2"
            border
            align="center"
            size="mini"
            auto-resize
            stripe
            resizable
            show-overflow
            show-header-overflow
            highlight-hover-column
            max-height="800"
            @checkbox-change="selectChangeEvent"
            @checkbox-all="selectAllEvent"/>
        </el-tab-pane>
        <el-tab-pane label="手工不合并清单" name="second">
          <el-table :data="tableData2" border style="width: 100%; margin-top: 20px">
            <el-table-column prop="modelno" label="型号" width="180"/>
            <el-table-column prop="shikomianswerno" label="Shikomi"/>
            <el-table-column prop="statecode" label="StateCode"/>
            <el-table-column prop="supplierid" label="供应商"/>
            <el-table-column prop="quantity" label="数量"/>
          </el-table>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import { findRequestMergeList, requestMergeColumn, requestSplitColumn, artificialMerge } from '@/api/requestPurchase'
export default {
  name: 'RequestPurchaseIndex',
  data() {
    return {
      activeName: 'first',
      modelArr: [],
      modelPos: 0,
      counts: 0,
      tableData: [],
      tableData2: [],
      tableColumn: [],
      condition: {
      },
      selectList: [],
      // 手工合并的uuid
      uuidMerge: '',
      moreVisible: false,
      ordTypeList: [
        {
          value: '0',
          label: '销售订单'
        },
        {
          value: '1',
          label: 'BIN补库'
        },
        {
          value: '2',
          label: '先行在库补库'
        },
        {
          value: '3',
          label: '其他'
        }
      ],
      stateCodeList: [
        {
          value: '0',
          label: '待处理'
        },
        {
          value: '1',
          label: '处理中'
        },
        {
          value: '2',
          label: '采购中'
        },
        {
          value: '3',
          label: '等待采购'
        }
      ]

    }
  },
  // created() {
  //   this.init()
  // },
  mounted() {
    this.getList()
    this.tableColumn = requestMergeColumn()
    this.splitColumn = requestSplitColumn()
  },
  methods: {
    getList() {
      this.loading = true
      findRequestMergeList().then(res => {
        this.tableData = res.data
        this.modelNoinit()
        this.ngOnInit()
        this.loading = false
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
        this.loading = false
      })
    },
    // 拆分单条数据摁扭
    splitRow(row) {
      row = row || {}
      var datas = this.tableData
      for (var i = 0; i < datas.length; i++) {
        if (row.orderno === datas[i].orderno) {
          datas.splice(i, 1)
          this.modelNoinit()
          this.tableData2.push(row)
        }
      }
      // this.loading = true
      // this.condition.uuid = row.uuid
      // this.condition.orderno = row.orderno
      // this.condition.tabledatas = this.tableData
      // const params = this.condition
      // splitRequestPurchase(params).then(res => {
      //   this.tableData = res.data
      //   this.modelNoinit()
      //   // this.ngOnInit()
      //   this.loading = false
      // }).catch(error => {
      //   console.log(error)
      //   this.loading = false
      // })
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
          // 用于判断当前项的
          // if (this.tableData[i].modelno === this.tableData[i - 1].modelno &&
          // this.tableData[i].ordtype === this.tableData[i - 1].ordtype && this.tableData[i].supplierid === this.tableData[i - 1].supplierid) {
          //   if (this.tableData[i].shikomianswerno === this.tableData[i - 1].shikomianswerno) {
          //     this.modelArr[this.modelPos] += 1
          //     this.modelArr.push(0)
          //   } else if (this.tableData[i].supplierid !== 'CN0' && this.tableData[i].islot === '1') {
          //     this.counts += 1
          //     if (this.counts > 1) {
          //       this.modelArr[this.modelPos] += 1
          //       this.modelArr.push(0)
          //     } else {
          //       this.counts = 0
          //       this.modelArr.push(1)
          //       this.modelPos = i
          //     }
          //   } else {
          //     if (this.counts > 20) {
          //       this.modelArr[this.modelPos] += 1
          //       this.modelArr.push(0)
          //     } else {
          //       this.counts = 0
          //       this.modelArr.push(1)
          //       this.modelPos = i
          //     }
          //   }
          // }
          // if (this.tableData[i].modelno === this.tableData[i - 1].modelno &&
          // this.tableData[i].ordtype === this.tableData[i - 1].ordtype &&
          // this.tableData[i].supplierid === this.tableData[i - 1].supplierid &&
          // this.tableData[i].shikomianswerno === this.tableData[i - 1].shikomianswerno) {
          //   this.modelArr[this.modelPos] += 1
          //   this.modelArr.push(0)
          // }
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
      if (columnIndex === 4 || columnIndex === 6 || columnIndex === 9 || columnIndex === 10 || columnIndex === 14 || columnIndex === 16 || columnIndex === 18) {
        const row1 = this.modelArr[rowIndex]
        const col1 = row1 > 0 ? 1 : 0
        return {
          rowspan: row1,
          colspan: col1
        }
      }
    },
    // 前端对list进行分组，类似于groupby
    ngOnInit() {
      const sorted = this.groupBy(this.tableData, function(item) {
        return [item.modelno + ' ++ ' + item.ordtype + ' ++ ' + item.supplierid + ' ++ ' + item.shikomianswerno]
      })
      console.log(sorted)
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
    // 重置表单
    resetForm() {
      this.$refs.form.resetFields()
      this.applyDateRange = []
    },
    // 列表复选框改变事件
    selectChangeEvent({ records }) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element)
      })
    },
    // 列表全选事件
    selectAllEvent({ records }) {
      this.selectList = []
      records.forEach(element => {
        this.selectList.push(element)
      })
    },
    // 手工合并采购方法
    artificialMergeData() {
      if (this.selectList.length === 0) {
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
          dataMerge.push(this.selectList[i])
        } else {
          if (this.selectList[i].modelno === this.selectList[i - 1].modelno &&
           this.selectList[i].ordtype === this.selectList[i - 1].ordtype && this.selectList[i].supplierid === this.selectList[i - 1].supplierid) {
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
      // 调用后端合并方法
      var splitData = this.tableData2
      var mergeData = []
      var newTableData = this.tableData
      artificialMerge(dataMerge).then(res => {
        mergeData = res.data
        for (var i = 0; i < mergeData.length; i++) {
          for (var j = 0; j < splitData.length; j++) {
            if (mergeData[i].orderno === splitData[j].orderno) {
              splitData.splice(j, 1)
              newTableData.push(mergeData[i])
            }
          }
        }
        console.log(newTableData)
        this.modelNoinit()
        this.ngOnInit()
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    }

  }
}
</script>

<style scoped lang="scss">
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
}
a:link {color: black} /* 未被访问bai的链接 黑色 */
a:visited {color: blue; text-decoration: underline} /* 已被访问过的链接 蓝色下划线 */
a:hover {color: blue; text-decoration: underline} /* 鼠标悬浮在上的链接 蓝色下划线 */
a:active {color: blue} /* 鼠标点中激活链接 蓝色 */
</style>
