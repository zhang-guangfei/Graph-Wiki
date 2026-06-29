<template>
  <div class="product-search-content">
    <div class="search-title-content">
      <el-form ref="form" :inline="true" size="mini">
        <el-form-item style="margin-right: 300px;">
          <el-input
            v-model="modelNo"
            class="search-input-class"
            placeholder="请输入查询的型号"
            prefix-icon="el-icon-search"
            clearable
            size="small"/>
          <el-button v-permission="['219013']" style="margin-top: 1px; margin-left: 10px;" type="primary" size="small" @click="queryPriceByModelNo(modelNo)">查询</el-button>
        </el-form-item>
      </el-form>
      <el-divider/>
      <el-table
        v-loading="loading"
        v-if="tabDisplay"
        :data="tableData"
        :row-style="{height:'43px'}"
        :header-cell-style="{padding: '1px'}"
        :cell-style="{padding: '5px 5px'}"
        border
        stripe
        height="730">
        <el-table-column label="整型号" prop="modelno">
          <template slot-scope="scope">
            <a style="color: #409EFF; text-align:left" type="text" @click="popup(scope.row.modelno, scope.row.id, scope.row)">{{ scope.row.modelno }}</a>
          </template>
        </el-table-column>
        <el-table-column align="center" label="供应商" prop="supplierid">
          <template slot-scope="scope">
            <span>{{ scope.row.supplierid }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="生产周期" prop="stddlvday">
          <template slot-scope="scope">
            <span>{{ scope.row.stddlvday }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="货期最大数量" prop="stddlvdatemaxnumber">
          <template slot-scope="scope">
            <span>{{ scope.row.stddlvdatemaxnumber }}</span>
          </template>
        </el-table-column>
      </el-table>
      <el-dialog :visible.sync="isModelVisible" :show-close="showClo" :close-on-click-modal="false" align="center" style="top: 35px; width: 100%">
        <div slot="title" class="dialog-title">
          <div style="float:left">
            <el-button icon="el-icon-plus" size="mini" @click="saveOneSupplier(tableDataDetail)">添加</el-button>
            <el-input
              v-model="specificModelNo"
              class="search-input-class"
              prefix-icon="el-icon-search"
              clearable
              size="mini"/>
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
            <a style="color: #337AB7; font-size: 14px; text-decoration-line: none">此为新增询价功能，请仔细检查填写的信息再继续操作！</a>
          </div>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="cancelSupplier()">取 消</el-button>
          <el-button type="primary" size="mini" style="margin-left: 15px; margin-top: 5px;" @click="submitSupplier()">继 续</el-button>
        </div>
      </el-dialog>
      <!-- 分页工具 -->
      <!-- <el-pagination
        v-show="tabDisplay"
        :current-page="tableData.pageNum"
        :page-sizes="[ 10,15, 30, 50, 100]"
        :page-size="tableData.pageSize"
        :total="tableData.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"/>
      <exportExcel ref="exportExcelVue"/> -->
    </div>
  </div>
</template>

<script>
import { findQueryPriceNewModelByModelNo, findBeingSuppliersByModelNo, findAllSupplier, findAllCountry, findCountryBySupplyId, insertQueryPrice } from '@/api/basicsMaintenance'
export default {
  data() {
    return {
      tabDisplay: false,
      loading: false,
      loadingBall: false,
      isModelVisible: false,
      distinctiveFlag: false, // 主供应商判断是否
      showClo: false,
      modelNo: '',
      specificModelNo: '',
      prestoreId: '', // 预存Id
      tableData: [],
      tableDataDetail: [],
      supplierArry: [],
      countryArry: [],
      saveTemp: {
        id: '',
        modelno: '',
        orgcountryid: '',
        supplyid: '',
        isprimary: false,
        maxprodqty: 0,
        resultsource: 5,
        resultsourcedesc: '人工维护',
        madeFlag: true
      }
    }
  },
  created() {
    this.queryPriceByModelNo()
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
    // 获取表格数据事件
    queryPriceByModelNo(modelNo) {
      this.loading = true; this.tabDisplay = true
      findQueryPriceNewModelByModelNo(modelNo).then(res => {
        this.tableData = res
        this.loading = false
      }).catch(res => {
        this.smcErrorMsg(res.message)
        this.loading = false
      })
    },
    // 改变每页条数
    // handleSizeChange(newSize) {
    //   this.page.pageSize = newSize
    //   this.queryPriceByModelNo(this.modelNo)
    // },
    // 换页
    // handleCurrentChange(newCurrent) {
    //   this.page.pageNumber = newCurrent
    //   this.queryPriceByModelNo(this.modelNo)
    // },
    popup(modelNo, Id, row) {
      this.loadingBall = true
      this.specificModelNo = modelNo; this.prestoreId = Id
      this.isModelVisible = !this.isModelVisible
      findBeingSuppliersByModelNo(modelNo).then(res => {
        this.loadingBall = false
        if (!row.supplierid && (!res || res.length === 0)) { this.$message({ showClose: true, message: '暂无数据返回，请手动添加！', type: 'warning' }); return false }
        res.forEach(item => { item.flag = item.resultsource.slice(0, 1) === '!' })
        if (res.length > 0) { this.tableDataDetail = res.reverse() }
        this.saveThisInfo(row)
        this.getAllSupplier()
        this.getAllCountry()
      }).catch(res => {
        this.loadingBall = false
        this.smcErrorMsg(res.message)
      })
    },
    saveThisInfo(row) {
      var temp = this.saveTemp
      temp.modelno = row.modelno
      temp.supplyid = row.supplierid
      temp.orgcountryid = row.orgcountryid
      temp.resultsource = 6
      temp.resultsourcedesc = 'vb人工维护'
      this.tableDataDetail.unshift(Object.assign({}, temp))
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
        if (this.prestoreId !== '') { this.saveTemp.id = this.prestoreId } else { this.$message({ showClose: true, message: '程序错误，暂不支持新增！', type: 'error' }); return false }
        var temp = Object.assign({}, this.saveTemp)
        this.tableDataDetail.unshift(temp)
        this.getAllSupplier()
        this.getAllCountry()
      }
    },
    getCountry(supplyId, index) {
      findCountryBySupplyId(supplyId).then(res => {
        if (index >= 0) {
          this.tableDataDetail[index].orgcountryid = res.id
        }
        this.country = res
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    cancelSupplier() {
      this.tableDataDetail = []
      this.isModelVisible = !this.isModelVisible
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
    delectSupplier(index) {
      this.tableDataDetail.splice(index, 1)
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
          insertQueryPrice(this.tableDataDetail).then(res => {
            if (res === 20001) {
              this.$message({ showClose: true, message: '添加成功！', type: 'success' })
              this.isModelVisible = !this.isModelVisible
              this.queryPriceByModelNo()
              this.specificModelNo = ''; this.tableDataDetail = []
            }
          }).catch(res => {
            this.loading = false
          })
        }
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
