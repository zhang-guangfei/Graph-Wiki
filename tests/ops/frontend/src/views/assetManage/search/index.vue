<template>
  <div class="app-container">
    <div class="filter-containers">
      <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
        <el-tab-pane label="年度盘点" name="first">
          <el-card class="box-card">
            <div class="header">
              <el-form ref="searchForm" :inline="true" :model="searchForm" class="demo-form-inline" size="mini">
                <el-row>
                  <el-col :span="3">
                    <el-form-item>
                      <el-select
                        v-model="searchForm.pdBatchNo"
                        :remote-method="remoteMethod"
                        :loading="selLoading"
                        filterable
                        remote
                        clearable
                        placeholder="盘点批次号">
                        <el-option
                          v-for="item in pdBatchNoList"
                          :key="item.pdBatchNo"
                          :label="item.lable"
                          :value="item.pdBatchNo"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="3">
                    <el-form-item>
                      <el-select v-model="searchForm.pdState" clearable placeholder="盘点状态">
                        <el-option
                          v-for="item in pdStateList"
                          :key="item.code"
                          :label="item.codeName"
                          :value="item.code"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <!-- <el-col :span="3">
              <el-form-item>
                <el-select v-model="searchForm.wareHouseType" clearable placeholder="仓库类别">
                  <el-option
                    v-for="item in wareHouseTypeList"
                    :key="item.code"
                    :label="item.codeName"
                    :value="item.code"
                  />
                </el-select>
              </el-form-item>
            </el-col> -->
                  <!-- <el-col :span="3">
              <el-form-item>
                <el-select
                  v-model="searchForm.wareHouseCode"
                  :remote-method="remoteMethodByWareHouseCode"
                  :loading="selWareHouseCodeLoading"
                  filterable
                  remote
                  clearable
                  placeholder="仓库代码">
                  <el-option
                    v-for="item in wareHouseCodeList"
                    :key="item.warehouseCode"
                    :label="'【'+item.warehouseCode+'】'+item.warehouseName"
                    :value="item.warehouseCode"
                  />
                </el-select>
              </el-form-item>
            </el-col> -->
                  <!-- <el-col :span="6">
            </el-col> -->
                  <el-form-item>
                    <el-date-picker
                      v-model="dateVal"
                      :picker-options="pickerOptions"
                      type="daterange"
                      align="right"
                      unlink-panels
                      style="width: 270px"
                      range-separator="至"
                      start-placeholder="盘点开始日期"
                      end-placeholder="盘点结束日期"
                    />
                  </el-form-item>
                  <!-- <el-col :span="3">
            </el-col> -->
                  <el-form-item>
                    <el-input v-model="searchForm.createUser" style="width: 150px" placeholder="录入担当"/>
                  </el-form-item>
                  <el-form-item>
                    <i class="el-icon-delete" style="margin-left: 30px;font-weight: 600;" @click="clear()" />
                    <el-button type="primary" style="margin-left: 20px;" @click="searchData()">查询</el-button>
                  </el-form-item>
                </el-row>
              </el-form>
            </div>
          </el-card>
          <div class="tableBody">
            <el-button-group>
              <el-button size="mini" type="primary" @click="newPd()">新建盘点</el-button>
              <el-button size="mini" type="primary" @click="startPd()">盘点数据抽取</el-button>
              <el-button :loading="cleanDataloadBtu" size="mini" type="primary" @click="clearPlanData()">清空本次准备数据</el-button>
              <el-button size="mini" type="primary" @click="makeCurPBatchCancel()">作废当前盘点批次</el-button>
            </el-button-group>
            <vxe-table
              :data="tableData"
              border
              size="mini"
              @checkbox-change="selectChangeEvent"
            >
              <vxe-table-column type="checkbox" width="50"/>
              <vxe-table-column field="pdBatchNo" title="盘点批次号"/>
              <vxe-table-column field="pdStartTime" title="盘点开始日期" width="150px" show-overflow/>
              <vxe-table-column field="pdWmsCount" title="抽取WMS盘点条目" width="150px" show-overflow/>
              <vxe-table-column field="pdDataEndTime" title="盘点结束日期" width="150px" show-overflow/>
              <vxe-table-column field="name4" title="盘点总型号数"/>
              <vxe-table-column field="name5" title="OPS总账簿数"/>
              <vxe-table-column field="name6" title="WMS总账簿数"/>
              <vxe-table-column field="name7" title="OPS和WMS差异型号个数"/>
              <vxe-table-column field="pdState" title="盘点状态"/>
              <vxe-table-column field="isActive" title="激活状态"/>
              <vxe-table-column field="createUser" title="录入担当"/>
              <vxe-table-column field="name9" title="操作">
                <template #default="{ row }">
                  <a href="#" @click="detailClick(row)">详情</a>
                </template>
              </vxe-table-column>
            </vxe-table>
          </div>
          <pagination
            v-show="total > 10"
            :total="total"
            :page-sizes="[15, 50, 100, 150, 200]"
            :page.sync="searchForm.page.pageNumber"
            :limit.sync="searchForm.page.pageSize"
            @pagination="searchData()"/>
        </el-tab-pane>
        <el-tab-pane label="月度盘点" name="second">
          <el-form :inline="true" :model="ydPdSearchForm" class="demo-form-inline" size="mini" style="margin: 10px">
            <el-form-item label="月度盘点批次号">
              <el-input v-model="ydPdSearchForm.pdBatchNo" style="width: 150px" placeholder="月度盘点批次号" clearable />
            </el-form-item>
            <el-form-item label="执行结果">
              <el-select v-model="ydPdSearchForm.status" style="width: 100px" clearable placeholder="执行结果">
                <el-option
                  v-for="item in statusList"
                  :key="item.code"
                  :label="item.codeName"
                  :value="item.code"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="执行时间">
              <el-date-picker
                v-model="yddateVal"
                :default-time="['00:00:00', '23:59:59']"
                :picker-options="pickerOptions"
                type="daterange"
                align="right"
                unlink-panels
                style="width: 270px"
                range-separator="至"
                start-placeholder="-"
                end-placeholder="-"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchYdPd()">查询</el-button>
              <el-button type="primary" @click="execPlan()">执行计划</el-button>
            </el-form-item>
          </el-form>
          <vxe-table
            :data="ydtableData"
            border
            size="mini"
            style="width: 500px"
            @checkbox-change="selectChangeEvent"
          >
            <vxe-table-column type="checkbox" width="50"/>
            <vxe-table-column field="pdBatchNo" title="盘点批次号" width="150px"/>
            <vxe-table-column field="pdStartTime" title="执行日期" width="150px" show-overflow/>
            <vxe-table-column field="statusName" title="执行结果" width="150px"/>
          </vxe-table>
          <pagination
            v-show="ydtotal > 10"
            :total="ydtotal"
            :page-sizes="[20, 50, 100, 150, 200]"
            :page.sync="ydPdSearchForm.page.pageNumber"
            :limit.sync="ydPdSearchForm.page.pageSize"
            @pagination="searchYdPd()"/>
        </el-tab-pane>
        <el-dialog :visible.sync="dialogTableVisible" :close-on-click-modal="false" title="月度盘点执行计划">
          <el-form :inline="true" :model="execPlanParam" size="mini" class="demo-form-inline">
            <el-form-item label="计划年份">
              <el-date-picker
                v-model="execPlanParam.execDate"
                type="year"
                placeholder="选择年"
                style="width: 100px"
                value-format="yyyy"/>
            </el-form-item>
            <el-form-item label="状态">
              <el-select v-model="execPlanParam.execFlag" placeholder="状态" style="width: 100px">
                <el-option label="已执行" value="1"/>
                <el-option label="未执行" value="0"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" @click="searchExecPlan()">查询</el-button>
              <el-button @click="insertEvent(-1)">在最后行插入</el-button>
              <el-button @click="makeExecPlan()">生成未来两年计划</el-button>
            </el-form-item>
          </el-form>
          <vxe-table
            ref="xTable"
            :data="execPlanData"
            :edit-config="{trigger: 'click', mode: 'cell'}"
            border
            height="500"
            size="mini"
            @checkbox-change="selectChangeEvent">
            <vxe-table-column type="checkbox" width="50"/>
            <vxe-column :edit-render="{autofocus: '.vxe-input--inner'}" field="execDate" title="执行时间">
              <template #edit="{ row }">
                <vxe-input v-model="row.execDate" type="date" @change="updateExecTime(row)"/>
              </template>
            </vxe-column>
            <vxe-table-column field="execFlagName" title="执行结果" />
          </vxe-table>
          <pagination
            v-show="execPlanTotal > 0"
            :total="execPlanTotal"
            :page-sizes="[20, 50, 100, 150, 200]"
            :page.sync="execPlanParam.page.pageNumber"
            :limit.sync="execPlanParam.page.pageSize"
            @pagination="searchExecPlan()"/>
        </el-dialog>
      </el-tabs>
    </div>
  </div>
</template>
<script>
import { listPdBatchList, findBatchNo, newPd, cleanPdData, pdDataImp, getOpsAsWmsTaskNoticeStatus, makeCurPBatchCancel, listYdPdBatchList, listPdExecPlan, updateOrAddPdExecPlan, makeExecPlan } from '@/api/pd/pd'
import { getDataCodesTree, findWareHouseInfoWithLike } from '@/api/common/dict'
import Pagination from '@/components/Pagination'
export default {
  name: 'AssetManageQuery',
  components: { Pagination },
  data() {
    return {
      searchForm: {
        wareHouseType: '',
        wareHouseCode: '',
        pdStartTime: '',
        pdDataEndTime: '',
        invoiceNo: '',
        pdBatchNo: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      statusList: [
        {
          'code': 0,
          'codeName': '未执行'
        },
        {
          'code': 1,
          'codeName': '已执行'
        }
      ],
      ydPdSearchForm: {
        pdBatchNo: '',
        pdStartTime: '',
        status: '',
        pdDataEndTime: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      execPlanParam: {
        execDate: '',
        execFlag: '0',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      dialogTableVisible: false,
      execPlanData: [],
      execPlanTotal: 0,
      ydtotal: 0,
      activeName: 'first',
      loading: '',
      total: 0,
      selLoading: false,
      selWareHouseCodeLoading: false,
      cleanDataloadBtu: false,
      pdStateClassCode: '5001',
      wareHouseTypeClassCode: '4001',
      ydtableData: [],
      tableData: [],
      pdBatchNoList: [],
      pdStateList: [],
      wareHouseTypeList: [],
      wareHouseCodeList: [],
      dateVal: [],
      yddateVal: [],
      timer: '',
      pickerOptions: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
            picker.$emit('pick', [start, end])
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90)
            picker.$emit('pick', [start, end])
          }
        }]
      }
    }
  },
  created() {
    this.getDataCodesTreeByPdState()
    this.getDataCodesTreeByWareHouseType()
    this.searchData()
    this.remoteMethod('')
  },
  beforeDestroy() {
    clearInterval(this.timer)
  },
  methods: {
    makeExecPlan() {
      makeExecPlan().then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.searchExecPlan()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    async insertEvent(row) {
      const $table = this.$refs.xTable
      console.log('$table=>', $table)
      const record = {
        execDate: '',
        execFlagName: '0'
      }
      const { row: newRow } = await $table.insertAt(record, row)
      await $table.setEditCell(newRow, 'execDate')
    },
    execPlan() {
      this.dialogTableVisible = true
      this.searchExecPlan()
    },
    searchExecPlan() {
      listPdExecPlan(this.execPlanParam).then(res => {
        if (res.success) {
          this.execPlanData = res.content.list
          this.execPlanTotal = res.content.total
        } else {
          this.execPlanData = []
          this.execPlanTotal = 0
        }
      })
    },
    updateExecTime(Row) {
      updateOrAddPdExecPlan(Row).then(res => {
        console.log('updateExecTime =>', res)
        this.searchExecPlan()
      })
    },
    searchYdPd() {
      if (this.yddateVal != null && this.yddateVal.length > 0) {
        this.ydPdSearchForm.pdStartTime = this.dayjs(this.yddateVal[0]).format('YYYY-MM-DD HH:mm:ss')
        this.ydPdSearchForm.pdDataEndTime = this.dayjs(this.yddateVal[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.ydPdSearchForm.pdStartTime = ''
        this.ydPdSearchForm.pdDataEndTime = ''
      }
      listYdPdBatchList(this.ydPdSearchForm).then(res => {
        if (res.success) {
          this.ydtableData = res.content.list
          this.ydtotal = res.content.total
        } else {
          this.ydtableData = []
          this.ydtotal = 0
        }
      })
    },
    handleClick(tab, event) {
      if (tab.name === 'first') {
        this.searchData()
      }
      if (tab.name === 'second') {
        this.searchYdPd()
      }
    },
    makeCurPBatchCancel() {
      makeCurPBatchCancel().then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.searchData()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    start() {
      this.timer = setInterval(this.getOpsAsWmsTaskNoticeStatus, 1000)
    },
    getOpsAsWmsTaskNoticeStatus() {
      getOpsAsWmsTaskNoticeStatus().then(res => {
        if (res.success) {
          if (res.content === '4') {
            this.$message.success('数据已抽取完毕')
            this.loading.close()
            this.searchData()
            clearInterval(this.timer)
            return
          }
        }
      })
    },
    searchData() {
      if (this.dateVal !== null && this.dateVal.length > 0) {
        this.searchForm.pdStartTime = this.dayjs(this.dateVal[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchForm.pdDataEndTime = this.dayjs(this.dateVal[1]).format('YYYY-MM-DD HH:mm:ss')
      } else {
        this.searchForm.pdStartTime = ''
        this.searchForm.pdDataEndTime = ''
      }
      listPdBatchList(this.searchForm).then(res => {
        if (res.success) {
          this.tableData = res.content.list
          this.total = res.content.total
        } else {
          this.tableData = []
          this.total = 0
        }
      })
    },
    handleSelectionChange(val) {
    },
    detailClick(val) {
      console.log('详情=>', val)
      this.$message.warning('详情后续会跳转报表.')
    },
    remoteMethod(batchNo) {
      this.selLoading = true
      findBatchNo(batchNo).then(res => {
        if (res.success) {
          this.pdBatchNoList = res.content
        } else {
          this.pdBatchNoList = []
        }
        this.selLoading = false
      })
    },
    remoteMethodByWareHouseCode(code) {
      var warehouseCode = ''
      if (code === undefined) {
        warehouseCode = ''
      } else {
        warehouseCode = code
      }
      this.selWareHouseCodeLoading = true
      findWareHouseInfoWithLike(warehouseCode).then(res => {
        if (res.success) {
          this.wareHouseCodeList = res.content
        } else {
          this.wareHouseCodeList = []
        }
        this.selWareHouseCodeLoading = false
      })
    },
    getDataCodesTreeByPdState() {
      getDataCodesTree(this.pdStateClassCode).then(res => {
        if (res.success) {
          this.pdStateList = res.content
        } else {
          this.pdStateList = []
        }
      })
    },
    getDataCodesTreeByWareHouseType() {
      getDataCodesTree(this.wareHouseTypeClassCode).then(res => {
        if (res.success) {
          this.wareHouseTypeList = res.content
        } else {
          this.wareHouseTypeList = []
        }
      })
    },
    newPd() {
      newPd().then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
        this.searchData()
      })
    },
    startPd() {
      pdDataImp().then(res => {
        if (res.success) {
          this.loading = this.$loading({
            lock: true,
            text: res.content,
            spinner: 'el-icon-loading',
            background: 'rgba(0, 0, 0, 0.7)'
          })
          this.start()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    clearPlanData() {
      this.$confirm('此操作将清空本次准备数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.cleanDataloadBtu = true
        cleanPdData().then(res => {
          this.cleanDataloadBtu = false
          if (res.success) {
            this.$message.success(res.content)
            this.searchData()
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消操作'
        })
      })
    },
    selectChangeEvent(val) {
      console.log('selectChangeEvent=>', val.records)
    },
    clear() {
      this.searchForm = {
        wareHouseType: '',
        wareHouseCode: '',
        invoiceNo: '',
        pdBatchNo: '',
        pdStartTime: '',
        pdDataEndTime: '',
        page: {
          pageNumber: 1,
          pageSize: 15
        }
      }
      this.dateVal = []
    }
  }
}
</script>
<style scoped>
.header{
  margin-top: 10px;
  width: 100%
}
.el-icon-delete {
  transition: all 0.3s
}
.el-icon-delete:hover {
   transform: scale(1.3)
}
</style>
