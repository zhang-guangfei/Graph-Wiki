<template>
  <div class="product-search-content">
    <div class="search-title-content">
      <el-form ref="form" :inline="true" size="mini">
        <!-- MDM上线 需要注释掉改功能 -->
        <!-- <el-form-item style="float:left">
          <el-button v-permission="['827846']" icon="el-icon-plus" size="small" @click="saveModel()">新增型号项</el-button>
        </el-form-item> -->
        <el-form-item>
          <el-date-picker
            v-model="dateTime"
            :picker-options="pickerOptions"
            style="top: 1px; width: 250px"
            type="daterange"
            align="right"
            size="small"
            unlink-panels
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            value-format="yyyy-MM-dd"/>
        </el-form-item>
        <el-form-item style="margin-right: 300px;">
          <el-input
            v-model="wholeModelNo"
            class="search-input-class"
            placeholder="请输入查询的型号"
            prefix-icon="el-icon-search"
            clearable
            size="small">
            <el-button slot="suffix" type="text" @click="fuzzy=!fuzzy">
              <i :class="{fuzzyBTN:fuzzy}" class="el-icon-setting" />
            </el-button>
          </el-input>
          <el-button v-permission="['915267']" style="margin-left: 10px;" type="primary" size="small" @click="searchAllModelByDim(wholeModelNo, dateTime)">查询</el-button>
          <!-- <el-button v-permission="['827846']" style="margin-left: 10px;" type="primary" size="small" @click="updateModel()">编辑</el-button> -->
          <el-button v-permission="['827846']" class="filter-item" type="primary" size="mini" @click="openSonItemExport(wholeModelNo)">导出</el-button>
        </el-form-item>
      </el-form>
      <el-divider/>
      <!-- :span-method="objectSpanMethod" // 合并单元格-->
      <el-table
        v-loading="loading"
        v-if="tabDisplay"
        :data="tableData.list"
        :row-style="{height:'43px'}"
        :header-cell-style="{padding: '1px'}"
        :cell-style="{padding: '5px 5px'}"
        border
        stripe
        height="730">
        <el-table-column
          v-for="column in tableColumns"
          :key="column.prop"
          :prop="column.prop"
          :label="column.label"
          :width="column.width"
          :formatter="column.formatter"
          :align="column.align"
          header-align="center"
          show-overflow-tooltip
        />
      </el-table>
      <!-- 分页工具 -->
      <el-pagination
        v-show="tabDisplay"
        :current-page="tableData.pageNum"
        :page-sizes="[ 10,15, 30, 50, 100]"
        :page-size="tableData.pageSize"
        :total="tableData.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
      <exportExcel ref="exportExcelVue"/>
      <el-dialog :visible.sync="isModelVisible" :show-close="showClo" :close-on-click-modal="false" align="center" style="top: 35px; width: 100%">
        <div slot="title" class="dialog-title">
          <div style="float:left">
            <el-button icon="el-icon-plus" size="mini" @click="saveSplitModel(tableDataDetail)">添加</el-button>
            <el-input
              v-model="modelNo"
              class="search-input-class"
              placeholder="请输入准确的整型号"
              prefix-icon="el-icon-search"
              clearable
              size="mini"/>
            <el-button icon="el-icon-search" size="mini" @click="selectSplitInfoByWholeNo(modelNo)">查询</el-button>
          </div>
          <div style="float:right">
            <span class="required-star">*
              <span style="font-size: 14px; color: #909399; font-style:italic;">优先整型号采购：</span>
            </span>
            <el-select v-model="priorityComplete" size="mini" placeholder="请选择" style="width: 120px;">
              <el-option :value="1" label="是"/>
              <el-option :value="0" label="否"/>
            </el-select>
          </div>
          <el-table
            v-loading="loadingBall"
            :data="tableDataDetail"
            style="top: 10px;">
            <el-table-column label="bomId" prop="bomId" width="80">
              <template slot-scope="scope">
                <span style="font-size: 12px;">{{ scope.row.bomId }}</span>
              </template>
            </el-table-column>
            <el-table-column label="整型号" prop="wholeModelNo" min-width="190">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-input v-model="scope.row.wholeModelNo" size="mini" placeholder="请输入整型号" maxlength="45"/>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.wholeModelNo }}</span>
              </template>
            </el-table-column>
            <el-table-column label="部件型号" prop="splitModelNo" min-width="190">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-input v-model="scope.row.splitModelNo" size="mini" placeholder="请输入部件型号" maxlength="45"/>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.splitModelNo }}</span>
              </template>
            </el-table-column>
            <el-table-column label="数量" prop="orgcountryid" min-width="90">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-input v-model="scope.row.quantity" onkeyup="value=value.replace(/[^\d]/g,'')" size="mini" placeholder="请输入"/>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.quantity }}</span>
              </template>
            </el-table-column>
            <el-table-column label="本体" prop="classify" min-width="90">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-select v-model="scope.row.classify" size="mini" placeholder="请选择" style="width: 80px;">
                    <el-option :value="true" label="是"/>
                    <el-option :value="false" label="否"/>
                  </el-select>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.classify ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否有效" prop="valid" min-width="100">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-select v-model="scope.row.isValid" size="mini" placeholder="请选择" style="width: 80px;">
                    <el-option :value="1" label="有效"/>
                    <el-option :value="0" label="无效"/>
                  </el-select>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.valid ? '有效' : '无效' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-button type="text" size="mini" @click.stop="delectSupplier(scope.$index)">删除</el-button>
                </span>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin-left: 30px; margin-top: 20px; font-style:italic;">
            <a style="color: #337AB7; font-size: 14px; text-decoration-line: none">此为新增产品型号功能，请仔细检查填写的信息再继续操作！</a>
          </div>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="cancelModel()">取 消</el-button>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="submitForm(priorityComplete)">继 续</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="isUpdateModelVisible" :show-close="showClo" :close-on-click-modal="false" align="center" style="top: 35px; width: 100%">
        <div slot="title" class="dialog-title">
          <div style="float:left">
            <el-input
              v-model="EModelNo"
              class="search-input-class"
              placeholder="请输入准确的整型号"
              prefix-icon="el-icon-search"
              clearable
              size="mini"/>
            <el-button icon="el-icon-search" size="mini" @click="selectWholeModelInfoByModelNo(EModelNo)">查询</el-button>
          </div>
          <el-table :data="tableUpdateDataDetail" style="top: 10px;">
            <el-table-column label="bomId" prop="bomid">
              <template slot-scope="scope">
                <span style="font-size: 12px;">{{ scope.row.bomid }}</span>
              </template>
            </el-table-column>
            <el-table-column label="整型号" prop="modelno">
              <template slot-scope="scope">
                <span style="font-size: 12px;">{{ scope.row.modelno }}</span>
              </template>
            </el-table-column>
            <el-table-column label="优先整型号" prop="priorityComplete">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-select v-model="scope.row.priorityComplete" size="mini" placeholder="请选择" style="width: 80px;">
                    <el-option :value="true" label="是"/>
                    <el-option :value="false" label="否"/>
                  </el-select>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.priorityComplete ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="优先级" prop="priorityLevel">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-input v-model="scope.row.priorityLevel" size="mini" onkeyup="value=value.replace(/\D|^0/g,'')" placeholder="请输入" maxlength="1"/>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.priorityLevel }}</span>
              </template>
            </el-table-column>
            <el-table-column label="是否有效" prop="isvalid">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-select v-model="scope.row.isvalid" size="mini" placeholder="请选择" style="width: 80px;">
                    <el-option :value="true" label="有效"/>
                    <el-option :value="false" label="无效"/>
                  </el-select>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.isvalid ? '有效' : '无效' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <span>
                  <el-button type="text" size="mini" @click.stop="updateSupplier(scope.row, scope.$index)">修改</el-button>
                </span>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin-left: 30px; margin-top: 20px; font-style:italic;">
            <a style="color: #337AB7; font-size: 14px; text-decoration-line: none">此为编辑产品型号功能，请仔细检查修改的信息再继续操作！</a>
          </div>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="cancelUpdateModel()">取 消</el-button>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="submitUpdateInfo()">继 续</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { findProductModelByNo, insertProductModel, findProductModelListByNo, findSplitInfoByWholeNo, findWholeModelInfoByModelNo, updateWholeModelInfoByModelNo } from '@/api/basicsMaintenance'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'ProductBom',
  components: { exportExcel },
  data() {
    return {
      fuzzy: false,
      tabDisplay: false,
      loading: false,
      loadingBall: false,
      isModelVisible: false, // 新增型号弹框
      isUpdateModelVisible: false, // 修改型号弹框
      showClo: false,
      modelNo: '', // 型号
      wholeModelNo: '', // 查询时型号
      EModelNo: '', // 编辑时型号
      dateTime: [],
      priorityComplete: 0,
      tableDataDetail: [],
      tableUpdateDataDetail: [],
      spanArr: [],
      position: 0,
      tableData: {},
      page: { pageNumber: 1, pageSize: 100 },
      tableColumns: [
        {
          label: '是否有效',
          prop: 'isValid',
          align: 'center'
        },
        {
          label: 'bomId',
          prop: 'bomId',
          width: 100
        },
        {
          label: '整型号',
          prop: 'wholeModelNo',
          width: 330
        },
        {
          label: '部件型号',
          prop: 'splitModelNo',
          width: 180
        },
        {
          label: '部件数量',
          prop: 'quantity',
          align: 'center'
        },
        {
          label: '本体',
          prop: 'classify',
          align: 'center'
        },
        {
          label: '优先整型号',
          prop: 'priorityComplete',
          align: 'center'
        },
        {
          label: '更新日期',
          prop: 'updateTime',
          align: 'center'
        },
        {
          label: '操作担当',
          prop: 'updateUser',
          align: 'center'
        },
        {
          label: '登录日期',
          prop: 'loginDate',
          align: 'center'
        }
        // {
        //   label: '优先级',
        //   prop: 'versionNum',
        //   align: 'center'
        // }
      ],
      temp: {
        bomId: '',
        wholeModelNo: '',
        splitModelNo: '',
        quantity: 0,
        classify: true,
        priorityComplete: 0,
        isValid: 1,
        madeFlag: true
      },
      updateTemp: {
        id: '',
        modelno: '',
        orgcountryid: '',
        supplyid: '',
        isprimary: 'true',
        stddlvdatemaxnumber: ''
      },
      pickerOptions: {
        shortcuts: [{
          text: '最近一天',
          onClick(picker) {
            const end = new Date()
            const start = new Date()
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 1)
            picker.$emit('pick', [start, end])
          }
        }, {
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
        }]
      }
    }
  },
  created() {
    this.test()
  },
  methods: {
    test() {
      var i = '    '
      if (i === '') {
        console.log(1)
      } else {
        console.log(2)
      }
    },
    selectSplitInfoByWholeNo(modelNo) {
      this.loadingBall = true
      findSplitInfoByWholeNo(modelNo).then(res => {
        this.loadingBall = false
        if (!res || res.length === 0) { this.$message({ showClose: true, message: '暂无数据返回，请手动添加！', type: 'warning' }); return false }
        res.forEach(item => { item.madeFlag = false })
        this.tableDataDetail = res
      }).catch(res => {
        this.smcErrorMsg(res.message)
        this.loadingBall = false
      })
    },
    selectWholeModelInfoByModelNo(EModelNo) {
      this.tableUpdateDataDetail = []
      findWholeModelInfoByModelNo(EModelNo).then(res => {
        res.forEach(item => { item.madeFlag = false })
        this.tableUpdateDataDetail = res
        this.loading = false
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
        this.loading = false
      })
    },
    saveSplitModel(data) {
      let num = 0
      data.forEach(i => {
        if (!i.splitModelNo) { num = num + 1 }
      })
      if (num > 0) {
        this.$message({ showClose: true, message: '完善上一条拆分型号信息后再操作！', type: 'info' })
      } else {
        if (this.modelNo !== '') { this.temp.wholeModelNo = this.modelNo }
        this.temp.bomId = '新方案'
        var temp = Object.assign({}, this.temp)
        this.tableDataDetail.unshift(temp)
      }
    },
    delectSupplier(index) {
      this.tableDataDetail.splice(index, 1)
    },
    updateSupplier(row, index) {
      this.tableUpdateDataDetail[index].madeFlag = !row.madeFlag
    },
    // 获取表格数据事件
    searchAllModelByDim(wholeModelNo, dateTime) {
      if (!wholeModelNo) {
        this.$message({ showClose: true, message: '请输入需要查询的型号！', type: 'info' })
        this.tableData = []
        this.tabDisplay = false
        return false
      }
      if (dateTime === null || dateTime === undefined) { dateTime = [] }
      this.loading = true; this.tabDisplay = true
      findProductModelByNo(wholeModelNo, this.fuzzy, this.page, dateTime[0], dateTime[1]).then(res => {
        // BUG 11419加入非空的校验 B91717
        if (this.isEmpty(res.list)) {
          this.loading = false
          return
        } else {
          res.list.forEach(item => {
            if (item.valid) { item.isValid = '是' } else { item.isValid = '否' }
            if (item.classify) { item.classify = '是' } else { item.classify = '否' }
            if (item.priorityComplete) { item.priorityComplete = '是' } else { item.priorityComplete = '否' }
          })
          this.tableData = res
          this.loading = false
        }
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
        this.loading = false
      })
    },
    isEmpty(val) {
      if (typeof val === 'undefined' || val == null || val.toString().trim() === '') {
        return true
      }
      return false
    },
    // 改变每页条数
    handleSizeChange(newSize) {
      this.page.pageSize = newSize
      this.searchAllModelByDim(this.wholeModelNo)
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.page.pageNumber = newCurrent
      this.searchAllModelByDim(this.wholeModelNo)
    },
    saveModel() {
      this.isModelVisible = true
    },
    updateModel() {
      this.isUpdateModelVisible = true
    },
    cancelModel() {
      this.modelNo = ''
      this.tableDataDetail = []
      this.isModelVisible = !this.isModelVisible
    },
    cancelUpdateModel() {
      this.wholeModelNo = ''
      this.tableUpdateDataDetail = []
      this.isUpdateModelVisible = !this.isUpdateModelVisible
    },
    submitForm(priorityComplete) {
      if (priorityComplete !== 0 && priorityComplete !== 1) { this.$message({ showClose: true, message: '优先整型号采购是必填项，请选择！', type: 'warning' }); return false }
      var arrValues = []
      this.tableDataDetail.forEach(item => {
        if (item.madeFlag) { item.bomId = '0'; item.priorityComplete = this.priorityComplete; this.temp.wholeModelNo = item.wholeModelNo; arrValues.push(item) }
      })
      if (arrValues.length === 0) { this.$message({ showClose: true, message: '暂无新增的拆分型号，不可提交！', type: 'info' }); return false }
      let num = 0; let count = 0
      arrValues.forEach(item => {
        if (!item.wholeModelNo) {
          this.$message({ showClose: true, message: '整型号为必填项，请填写！', type: 'warning' }); num = num + 1
        } else if (!item.splitModelNo) {
          this.$message({ showClose: true, message: '部件型号为必填项，请填写！', type: 'warning' }); num = num + 1
        } else if (item.quantity === '') {
          this.$message({ showClose: true, message: '数量为必填项，请填写！', type: 'warning' }); num = num + 1
        } else if (item.classify) {
          count = count + 1
        }
        // bug8640 修改更新人直接从前端获取，B91717
        item.updateUser = this.$store.getters.position.psnsmcId
      })
      if (num < 1 && count !== 1) {
        this.$message({ showClose: true, message: '本体标记不正确，不同方案 有且只有一个本体标记！', type: 'warning' }); return false
      }
      if (num < 1) {
        insertProductModel(arrValues).then(res => {
          if (res === 40001) {
            this.$message({ showClose: true, message: '该方案已存在，无需再次添加！', type: 'warning' })
          } else {
            this.isModelVisible = !this.isModelVisible
            this.wholeModelNo = this.temp.wholeModelNo
            this.searchAllModelByDim(this.wholeModelNo)
            this.$message({ showClose: true, message: '添加成功！', type: 'success' })
            this.modelNo = ''
            this.tableDataDetail = []
          }
        }).catch(res => {
          this.loading = false
        })
      }
    },
    submitUpdateInfo() {
      var val = this.tableUpdateDataDetail
      val.forEach(item => {
        // 8640 修改更新人直接从前端获取，B91717
        item.updateuser = this.$store.getters.position.psnsmcId
      })
      updateWholeModelInfoByModelNo(val).then(res => {
        if (res === 30001) { this.$message({ showClose: true, message: '优先级数据不能为空！', type: 'warning' }); return false }
        this.isUpdateModelVisible = !this.isUpdateModelVisible
        this.wholeModelNo = val[0].modelno
        this.searchAllModelByDim(val[0].modelno)
        this.$message({ showClose: true, message: '编辑成功！', type: 'success' })
        this.EModelNo = ''
        this.tableUpdateDataDetail = []
      }).catch(res => {
        this.loading = false
      })
    },
    objectSpanMethod({ row, column, rowIndex, columnIndex }) {
      if (columnIndex === 0) {
        const _row = this.spanArr[rowIndex]
        const _col = _row > 0 ? 1 : 0
        return {
          rowspan: _row,
          colspan: _col
        }
      }
    },
    rowspan(tableData) {
      tableData.forEach((item, index) => {
        if (index === 0) {
          this.spanArr.push(1)
          this.position = 0
        } else {
          if (tableData[index].wholeModelNo === tableData[index - 1].wholeModelNo) {
            this.spanArr[this.position] += 1
            this.spanArr.push(0)
          } else {
            this.spanArr.push(1)
            this.position = index
          }
        }
      })
    },
    openSonItemExport(modelNo) {
      const tableColumn = [
        { field: 'wholeModelNo', title: '整型号' },
        { field: 'splitModelNo', title: '部件型号' },
        { field: 'quantity', title: '数量' },
        { field: 'classify', title: '型号区分' },
        { field: 'priorityComplete', title: '优先整型号' },
        { field: 'updateTime', title: '修改时间' },
        { field: 'updateUser', title: '修改人' },
        { field: 'loginDate', title: '登录时间' },
        { field: 'versionNum', title: '版本号' },
        { field: 'isValid', title: '有效' }
      ]
      if (modelNo && modelNo !== '') {
        findProductModelListByNo(modelNo).then(res => {
          res.forEach(item => {
            if (item.valid) { item.isValid = '是' } else { item.isValid = '否' }
          })
          this.$refs.exportExcelVue.initExportExcel(res, tableColumn)
        }).catch(res => {
          this.smcErrorMsg(res.message)
          this.loading = false
        })
      } else {
        this.$message({ showClose: true, message: '必须输入检索条件，才能进行导出操作！', type: 'error' })
      }
    }
  }
}
</script>

<style scoped lang="scss">
.product-search-content{
  height: 100%;
  .search-title-content {
    padding: 10px 15px 0px 15px;
     text-align: left;
    .search-input-class {
      width: 300px;
    }
    .el-form-item--mini.el-form-item {
      margin-bottom: 5px;
    }
  }
}
.required-star {
    color: #c30;
    top: 0;
    font-weight: 400;
    font-size: 13px;
}
.fuzzyBTN:before {
  content: "\e7ac"
}
</style>
