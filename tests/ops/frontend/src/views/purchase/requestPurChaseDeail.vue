<template>
  <div class="app-container">
    <!-- <div style = "text-align:right;margin-top:5px;">
      <el-button type="primary" icon="el-icon-back" size="small" @click="goBack()">返回列表</el-button>
    </div>
    <br> -->
    <div class="filter-containers">
      <!-- <el-card class="card">
        <el-row :gutter="20">
          <el-col :span="6">
            <div class="cusbox">
              <el-form label-position="left">
                <el-form-item style="margin-bottom:0px;" label="请购单号:">
                  {{ orderno }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="18">
            <div>
              <el-form label-position="left">
                <el-form-item style="margin-bottom:0px;" label="客户名称:">
                  {{ orderno }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
      </el-card>
      <div style="margin-top:25px;margin-bottom:25px">
        <el-divider/>
      </div> -->
      <el-card style="margin-bottom: -15px">
        <el-row :gutter="20" type="flex">
          <el-col :span="6">
            <div class="cusbox">
              <el-form label-position="left">
                <el-form-item label="请购单号:">
                  {{ orderno }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="12">
            <div>
              <el-form label-position="left">
                <el-form-item style="margin-bottom:0px;" label="客户名称:">
                  {{ 请购单号 }}
                </el-form-item>
              </el-form>
            </div>
          </el-col>
          <el-col :span="6">
            <div style = "text-align:right;margin-top:5px;">
              <el-form label-position="left">
                <el-button type="primary" icon="el-icon-back" size="small" @click="goBack()">返回列表</el-button>
              </el-form>
            </div>
          </el-col>
        </el-row>
      </el-card>
      <div style="margin-top:10px;">
        <div style="margin-top: 48px">
          <el-divider content-position="left">
            <el-tag>请购单详情：</el-tag>
          </el-divider>
        </div>
        <!-- <div style="margin-top:25px;margin-bottom:25px">
        <el-divider/>
      </div> -->
        <el-table
          v-loading="listLoading"
          :data="tableData"
          :header-cell-style="{'text-align':'center','background-color': 'rgb(117, 144, 168)','color': 'rgba(253, 253, 253, 0.938)','padding': '6px','font-size': '14px'}"
          :header-row-style="{'padding': '2px'}"
          :row-style="rowStyle"
          border
          fit
          highlight-current-rowstyle="width: 100%" >
          <el-table-column show-overflow-tooltip label="型号" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.modelno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购数量" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="SHIKOMI" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.shikomianswerno }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="入库仓库" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.requestwarehouseid }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="供应商" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.supplierid }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购日期" min-width="120" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.requesttime }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="指定制造出荷日" min-width="90" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.hopeexportdate }}</span>
            </template>
          </el-table-column>
          <el-table-column label="请购单类别" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.ordtype }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="运输方式" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.transtype }}</span>
            </template>
          </el-table-column>
          <el-table-column show-overflow-tooltip label="请购单状态" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.statecode }}</span>
            </template>
          </el-table-column>
          <el-table-column label="KMU" align="center" min-width="80" >
            <template slot-scope="scope">
              <span>{{ scope.row.iskmu === 'Y' ? '是' : '否' }}</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </div>
  </div>
</template>
<script>
import { findByOrderNo } from '@/api/requestPurchase'
export default {
  name: 'RequestPurchaseDetail',
  data() {
    return {
      orderno: '',
      listLoading: false,
      tableData: [],
      headParam: {}
    }
  },
  created() {
    this.orderno = this.$route.query.orderno
    this.findData()
  },
  methods: {
    rowStyle({ row, rowIndex }) {
      return 'height:37px'
    },
    findData() {
      this.listLoading = true
      findByOrderNo(this.orderno).then(res => {
        this.tableData = res
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
      console.log(this.orderno)
    },
    goBack() {
      this.$router.push({ path: '/purchase/requestQuery' })
    }
  }
}
</script>
