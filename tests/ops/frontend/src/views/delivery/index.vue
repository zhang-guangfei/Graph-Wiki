<template>
  <div class="product-search-content">
    <div class="search-title-content">
      <el-form ref="form" :inline="true" size="mini">
        <!-- MDM上线 需要注释掉改功能 -->
        <!-- <el-form-item style="float:left">
          <el-button v-permission="['350431']" icon="el-icon-plus" size="small" @click="saveSupplier()">新增供应商</el-button>
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
            v-model="modelNo"
            class="search-input-class"
            placeholder="请输入供应商的型号"
            prefix-icon="el-icon-search"
            clearable
            size="small"/>
          <el-button v-permission="['731285']" style="margin-top: 1px; margin-left: 10px;" type="primary" size="small" @click="getDeliveryByDim(modelNo, dateTime)">查询</el-button>
          <el-button v-permission="['350431']" class="filter-item" type="primary" size="small" @click="openSonItemExport(modelNo)">导出</el-button>
        </el-form-item>
      </el-form>
      <el-divider/>
      <el-table
        v-loading="loading"
        v-show="tabDisplay"
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
        <!--bug8411 B91717 供应商维护画面增加是否有效字段的显示 -->
        <el-table-column show-overflow-tooltip label="有效性" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.isDeleted==true">
              {{ '无效' }}
            </span>
            <span v-if="scope.row.isDeleted==false">
              {{ '有效' }}
            </span>
          </template>
        </el-table-column>
        <!-- MDM上线 需要注释掉改功能 -->
        <!-- <el-table-column
          label="操作"
          width="200">
          <template slot-scope="scope">
            <span>
              <el-button type="text" size="mini" @click="updateRow(scope.row)">编辑</el-button>
              <el-button type="text" size="mini" @click="deleteRow(scope.row)">删除</el-button>
            </span>
          </template>
        </el-table-column> -->
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
      <el-dialog :visible.sync="IsNewSupplierVisible" :show-close="showClo" :close-on-click-modal="false" align="center" style="top: 35px; width: 100%">
        <div slot="title" class="dialog-title">
          <div style="float:left">
            <el-button icon="el-icon-plus" size="mini" @click="saveOneSupplier(tableDataDetail)">添加</el-button>
            <el-input
              v-model="specificModelNo"
              class="search-input-class"
              placeholder="请输入准确的供应商型号"
              prefix-icon="el-icon-search"
              clearable
              size="mini"/>
            <el-button icon="el-icon-search" size="mini" @click="findBeingSuppliers(specificModelNo)">查询</el-button>
          </div>
          <el-table
            v-loading="loadingBall"
            :data="tableDataDetail"
            :row-class-name="tableRowClassName"
            style="top: 10px;">
            <el-table-column label="型号" prop="modelno" width="200">
              <template slot-scope="scope">
                <span v-if="scope.row.madeFlag">
                  <el-input v-model="scope.row.modelno" size="mini" placeholder="请输入型号" />
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.modelno }}</span>
              </template>
            </el-table-column>
            <el-table-column label="原产国" prop="orgcountryid" width="170">
              <template slot-scope="scope">
                <span v-if="scope.row.flag || scope.row.madeFlag">
                  <el-select v-model="scope.row.orgcountryid" placeholder="请选择" size="mini">
                    <el-option v-for="item in countryArry" :key="item.id" :label="item.name" :value="item.id"/>
                  </el-select>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.orgcountryid }}</span>
              </template>
            </el-table-column>
            <el-table-column label="供应商" prop="supplyid" width="200">
              <template slot-scope="scope">
                <span v-if="scope.row.flag || scope.row.madeFlag">
                  <el-select v-model="scope.row.supplyid" placeholder="请选择" size="mini" @change="getCountry(scope.row.supplyid, scope.$index)">
                    <el-option v-for="item in supplierArry" :key="item.id" :label="item.name" :value="item.id"/>
                  </el-select>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.supplyid }}</span>
              </template>
            </el-table-column>
            <el-table-column label="主供应商" prop="isprimary" width="85">
              <template slot-scope="scope">
                <span v-if="scope.row.flag || scope.row.madeFlag || distinctiveFlag">
                  <el-select v-model="scope.row.isprimary" size="mini" placeholder="选择是否是主供应商" @change="mutex(scope.row.isprimary, scope.$index)">
                    <el-option :value="true" label="是"/>
                    <el-option :value="false" label="否"/>
                  </el-select>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.isprimary ? '是' : '否' }}</span>
              </template>
            </el-table-column>
            <el-table-column label="最大订货量" prop="maxprodqty" width="100">
              <template slot-scope="scope">
                <span v-if="scope.row.flag || scope.row.madeFlag">
                  <el-input v-model="scope.row.maxprodqty" onkeyup="value=value.replace(/[^\d]/g,'')" size="mini" placeholder="请输入"/>
                </span>
                <span v-else style="padding-left:16px; font-size: 12px;">{{ scope.row.maxprodqty }}</span>
              </template>
            </el-table-column>
            <el-table-column label="数据来源" prop="resultsourcedesc">
              <template slot-scope="scope">
                <span style="font-size: 12px">{{ scope.row.resultsourcedesc }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作">
              <template slot-scope="scope">
                <span v-if="scope.row.flag || scope.row.madeFlag">
                  <el-button type="text" size="mini" @click.stop="delectSupplier(scope.$index)">删除</el-button>
                </span>
              </template>
            </el-table-column>
          </el-table>
          <div style="margin-left: 30px; margin-top: 20px; font-style:italic;">
            <a style="color: #337AB7; font-size: 14px; text-decoration-line: none">此为新增供应商功能，请仔细检查填写的信息再继续操作！</a>
          </div>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="cancelSupplier()">取 消</el-button>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="submitSupplier()">继 续</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="IsSaveSupplierVisible" :show-close="showClo" :close-on-click-modal="false">
        <div slot="title" class="dialog-title">
          <el-divider>
            <span style="font-size:16px;">编辑供应商</span>
          </el-divider>
          <el-card align="center" style="margin-top: 20px;">
            <el-form ref="tempFrom" :model="updateTemp" :rules="rules" style="width:350px; text-align:left;" size="small" class="demo-ruleForm">
              <el-form-item label="型号：" prop="modelno" label-width="110px">
                <el-input v-model="updateTemp.modelno" placeholder="请输入型号" style="width: 200px;" />
              </el-form-item>
              <el-form-item label="原产国：" prop="orgcountryid" label-width="110px">
                <el-select v-model="updateTemp.orgcountryid" placeholder="请选择供应商" style="width: 200px;">
                  <el-option v-for="item in countryArry" :key="item.id" :label="item.name" :value="item.id"/>
                </el-select>
              </el-form-item>
              <el-form-item label="供应商：" prop="supplyid" label-width="110px" style="margin-top: -3px;">
                <el-select v-model="updateTemp.supplyid" placeholder="请选择供应商" style="width: 200px;" @change="getCountry(updateTemp.supplyid)">
                  <el-option v-for="item in supplierArry" :key="item.id" :label="item.name" :value="item.id"/>
                </el-select>
              </el-form-item>
              <el-form-item label="主供应商：" prop="isprimary" label-width="110px" style="margin-top: -3px;">
                <el-select v-model="updateTemp.isprimary" placeholder="选择是否为主供应商" style="width: 200px;">
                  <el-option :value="true" label="是"/>
                  <el-option :value="false" label="否"/>
                </el-select>
              </el-form-item>
              <el-form-item label="最大订货量：" prop="maxprodqty" label-width="110px" style="margin-top: -3px;">
                <el-input v-model.number="updateTemp.maxprodqty" placeholder="请输入最大订货数量" style="width: 200px;" onkeyup="value=value.replace(/[^\d]/g,'')"/>
              </el-form-item>
            </el-form>
            <div style="margin-left: 30px; font-style:italic;">
              <a style="color: #337AB7; font-size: 14px; text-decoration-line: none">编辑供应商，请仔细确认填写信息后再继续操作！</a>
            </div>
            <el-button type="primary" size="small" style="margin-left: 15px; margin-top: 10px;" @click="cancelModelUpdate()">取 消</el-button>
            <el-button type="primary" size="small" style="margin-left: 15px; margin-top: 10px;" @click="updateForm()">继 续</el-button>
          </el-card>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { findProductDeliveryByNo, findAllSupplier, insertProductDelivery, deleteProductDelivery, findDeliveryByNo,
  updateProductDelivery, findDeliveryListByNo, findCountryBySupplyId, findBeingSuppliersByModelNo, findAllCountry } from '@/api/basicsMaintenance'
import exportExcel from '@/components/ExportExcel/index'
export default {
  name: 'ProductDelivery',
  components: { exportExcel },
  data() {
    return {
      tabDisplay: false,
      loading: false,
      loadingBall: false,
      distinctiveFlag: false,
      IsNewSupplierVisible: false, // 新增供应商弹框
      IsSaveSupplierVisible: false, // 编辑供应商弹框
      showClo: false,
      modelNo: '',
      dateTime: '',
      specificModelNo: '',
      tableData: {},
      tableDataDetail: [],
      page: { pageNumber: 1, pageSize: 100 },
      supplierArry: [],
      countryArry: [],
      tableColumns: [
        {
          label: '型号',
          prop: 'modelno',
          width: '330px'
        },
        {
          label: '原产国',
          prop: 'orgcountryid',
          align: 'center'
        },
        {
          label: '供应商',
          prop: 'supplyid',
          align: 'center',
          width: '200px'
        },
        {
          label: '主供应商标识',
          prop: 'isprimary',
          align: 'center'
        },
        {
          label: '最大订货数量',
          prop: 'maxprodqty',
          align: 'center'
        },
        {
          label: '数据来源',
          prop: 'resultsourcedesc',
          align: 'center'
        },
        {
          label: '更新人',
          prop: 'updateuser',
          align: 'center'
        },
        {
          label: '更新日期',
          prop: 'updatetime',
          align: 'center'
        }
      ],
      saveTemp: {
        modelno: '',
        orgcountryid: '',
        supplyid: '',
        isprimary: false,
        maxprodqty: 0,
        resultsource: 5,
        resultsourcedesc: '人工维护',
        madeFlag: true
      },
      updateTemp: {
        id: '',
        modelno: '',
        orgcountryid: '',
        supplyid: '',
        isprimary: 'true',
        maxprodqty: '',
        updateuser: ''
      },
      prestoreVal: false,
      rules: {
        modelno: [{
          required: true,
          message: '请输入型号',
          trigger: 'blur'
        }, {
          min: 2,
          max: 100,
          message: '长度在 2 个字母以上'
        }],
        supplyid: [{
          required: true,
          message: '请选择供应商',
          trigger: 'change'
        }],
        isprimary: [{
          required: true,
          message: '请选择是否为主供应商',
          trigger: 'change'
        }],
        maxprodqty: [{
          type: 'number',
          message: '最大订货数量请填写整数'
        }]
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
      },
      startTime: ' 00:00:00',
      endTime: ' 23:59:59'
    }
  },
  created() {
  },
  methods: {
    tableRowClassName({ row, rowIndex }) {
      if (rowIndex === 1) {
        return 'warning-row'
      } else if (rowIndex === 3) {
        return 'success-row'
      }
      return ''
    },
    // 查询供应商弹框查询功能
    findBeingSuppliers(modelNo) {
      this.loadingBall = true
      findBeingSuppliersByModelNo(modelNo).then(res => {
        this.loadingBall = false
        if (!res || res.length === 0) { this.$message({ showClose: true, message: '暂无数据返回，请手动添加！', type: 'warning' }); return false }
        res.forEach(item => { item.flag = item.resultsource.slice(0, 1) === '!' })
        if (res.length > 0) { this.tableDataDetail = res.reverse() }
        this.getAllSupplier()
        this.getAllCountry()
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    mutex(flag, index) {
      if (flag) {
        for (var i in this.tableDataDetail) {
          if (parseInt(i) !== index) { this.tableDataDetail[i].isprimary = false }
        }
        this.distinctiveFlag = false
      } else {
        this.distinctiveFlag = true
      }
    },
    saveOneSupplier(data) {
      let num = 0
      data.forEach(i => {
        if (!i.modelno || !i.supplyid || !i.orgcountryid) { num = num + 1 }
      })
      if (num > 0) {
        this.$message({ showClose: true, message: '完善上一条供应商的信息后再操作！', type: 'info' })
      } else {
        if (this.specificModelNo !== '') { this.saveTemp.modelno = this.specificModelNo }
        var temp = Object.assign({}, this.saveTemp)
        this.tableDataDetail.unshift(temp)
        this.getAllSupplier()
        this.getAllCountry()
      }
    },
    delectSupplier(index) {
      this.tableDataDetail.splice(index, 1)
    },
    // 获取表格数据事件
    getDeliveryByDim(modelNo, dateTime) {
      if (dateTime === '2') { dateTime = ''; this.IsSaveSupplierVisible = !this.IsSaveSupplierVisible }
      if (!modelNo) {
        this.$message({ showClose: true, message: '请输入需要查询的型号！', type: 'info' })
        this.tableData = []; this.tabDisplay = false; return false
      }
      if (dateTime === null || dateTime === '') {
        dateTime = []
      } else if (dateTime != null && dateTime[0].length === 10 && dateTime[1].length === 10) {
        // BUG 7435 B91717 页面筛选当天时，需要显示选择当天的数据！
        dateTime[0] = dateTime[0] + this.startTime; dateTime[1] = dateTime[1] + this.endTime
      }
      this.loading = true; this.tabDisplay = true
      findProductDeliveryByNo(modelNo, this.page, dateTime[0], dateTime[1]).then(res => {
        res.list.forEach(item => {
          if (item.updatetime != null) { item.updatetime = this.dayjs(item.updatetime).format('YYYY-MM-DD') }
          if (item.isprimary) { item.isprimary = '是' } else { item.isprimary = '否' }
        })
        this.tableData = res
        this.loading = false
      }).catch(res => {
        this.smcErrorMsg(res.message)
        this.loading = false
      })
    },
    // 改变每页条数
    handleSizeChange(newSize) {
      this.page.pageSize = newSize
      this.getDeliveryByDim(this.modelNo, this.dateTime)
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.page.pageNumber = newCurrent
      this.getDeliveryByDim(this.modelNo, this.dateTime)
    },
    // 设置供应商弹框
    saveSupplier() {
      this.IsNewSupplierVisible = true
    },
    // 获取所有的供应商
    getAllSupplier() {
      findAllSupplier().then(res => {
        this.supplierArry = res
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    // 查询所有原产国
    getAllCountry() {
      findAllCountry().then(res => {
        this.countryArry = res
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    getCountry(supplyId, index) {
      findCountryBySupplyId(supplyId).then(res => {
        if (index >= 0) {
          this.tableDataDetail[index].orgcountryid = res.id
        }
        this.updateTemp.orgcountryid = res.id
        this.country = res
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    cancelSupplier() {
      this.specificModelNo = ''
      this.tableDataDetail = []
      this.IsNewSupplierVisible = !this.IsNewSupplierVisible
    },
    cancelModelUpdate() {
      this.IsSaveSupplierVisible = !this.IsSaveSupplierVisible
      this.$refs['tempFrom'].resetFields()
    },
    // 提交供应商
    submitSupplier() {
      if (this.tableDataDetail.length > 0) {
        var arr = this.tableDataDetail
        const ids = arr.map(i => i.supplyid)
        if (ids.length > new Set(ids).size) { this.$message({ showClose: true, message: '供应商不允许存在重复，请仔细检查！', type: 'error' }); return false }
        let num = 0
        arr.forEach(i => {
          // bug8640 修改更新人直接从前端获取，B91717
          i.updateuser = this.$store.getters.position.psnsmcId
          i.createuser = this.$store.getters.position.psnsmcId
          if (!i.modelno || !i.orgcountryid || !i.supplyid) { this.$message({ showClose: true, message: '以下输入框全部为必填项，请完善信息后才能提交！', type: 'warning' }); num = num + 1 }
        })
        if (num < 1) {
          insertProductDelivery(this.tableDataDetail).then(res => {
            if (res === 20001) {
              this.$message({ showClose: true, message: '添加成功！', type: 'success' })
              this.modelNo = arr[0].modelno
              this.IsNewSupplierVisible = !this.IsNewSupplierVisible
              this.getDeliveryByDim(this.modelNo, this.dateTime)
              this.specificModelNo = ''; this.tableDataDetail = []
            }
          }).catch(res => {
            this.loading = false
          })
        }
      }
    },
    updateRow(row) {
      this.getAllSupplier()
      this.getAllCountry()
      this.IsSaveSupplierVisible = !this.IsSaveSupplierVisible
      findDeliveryByNo(row.modelno, row.supplyid).then(res => {
        this.updateTemp = res
        this.prestoreVal = res.isprimary
      }).catch(res => {
        this.loading = false
      })
    },
    deleteRow(row) {
      if (row.isprimary === '是') {
        this.$message({ showClose: true, message: '主供应商不可删除！', type: 'warning' }); return false
      }
      this.$confirm('确认删除, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteProductDelivery(row.modelno, row.supplyid).then(res => {
          this.$message({ showClose: true, message: '删除成功！', type: 'success' })
          this.getDeliveryByDim(row.modelno)
        }).catch(res => {
          this.loading = false
        })
      }).catch(() => {
        this.loading = false
      })
    },
    updateForm() {
      this.$refs['tempFrom'].validate((valid) => {
        if (valid) {
          // bug8640 修改更新人直接从前端获取，B91717
          this.updateTemp.updateuser = this.$store.getters.position.psnsmcId
          var temp = this.updateTemp
          // 1.当前是 主供应商，需要变更为非主供应商，不允许变更，如果变更了，就没有主供应商了
          // 2.当前是 非主供应商，需要变更为主供应商，允许变更，只需要把当前供应商变更为主，其余供应商都变更为非主
          if (this.prestoreVal && this.prestoreVal !== temp.isprimary) { this.$message({ showClose: true, message: '不可编辑主供应商选项，主供应商必须存在一个！', type: 'error' }); return false }
          this.loading = true
          updateProductDelivery(temp).then(res => {
            this.$message({ showClose: true, message: '编辑成功！', type: 'success' })
            this.getDeliveryByDim(temp.modelno, '2')
            this.loading = false
          }).catch(res => {
            this.loading = false
          })
        } else {
          return false
        }
      })
    },
    openSonItemExport(modelNo) {
      const tableColumn = [
        { field: 'modelno', title: '型号' },
        { field: 'orgcountryid', title: '原产国' },
        { field: 'supplyid', title: '供应商' },
        { field: 'isprimary', title: '主供应商标识' },
        { field: 'maxprodqty', title: '最大订货数量' },
        { field: 'updatetime', title: '更新时间' }
      ]
      if (modelNo && modelNo !== '') {
        findDeliveryListByNo(modelNo).then(res => {
          res.forEach(item => {
            if (item.isprimary) { item.isprimary = '是' } else { item.isprimary = '否' }
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
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>
