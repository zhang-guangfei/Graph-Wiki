<template>
  <el-container>
    <el-main>
      <el-tabs v-model="activeName" @tab-click="tabchange">
        <el-tab-pane label="借库处理" name="first">
          <div>
            <el-form ref="form" :inline="true" :model="form" style="height:40px">
              <el-form-item prop="status">
                <el-select v-model="form.status" placeholder="状态" style="width: 165px" size="small">
                  <el-option v-for="item in statusOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
                </el-select>
              </el-form-item>
              <el-form-item prop="brwNo">
                <el-input v-model="form.brwNo" placeholder="申请号" style="width: 165px" size="small"/>
              </el-form-item>
              <el-form-item>
                <el-button v-permission="['387285']" type="primary" size="small" style="margin-bottom:3px" @click="findList('form')">查询</el-button>
              </el-form-item>
              <el-form-item>
                <el-button v-permission="['387285']" type="primary" size="small" style="margin-bottom:3px" @click="searchMoreData">更多查询</el-button>
              </el-form-item>
            </el-form>
          </div>
          <div v-if="searchMoreForm" class="search">
            <el-form ref="form" :inline="true" :model="form" size="mini" class="searchform">
              <el-form-item prop="customerNo">
                <el-input v-model="form.customerNo" style="width: 150px" placeholder="客户代码"/>
              </el-form-item>
              <el-form-item prop="customerName">
                <el-input v-model="form.customerName" style="width: 150px;margin-left: 10px;" placeholder="客户名称"/>
              </el-form-item>
              <el-form-item prop="deptNo">
                <!-- <el-select v-model="form.deptNo" placeholder="部门" style="width: 55%;margin-left: 10px;"> -->
                <el-input v-model="form.deptNo" style="width: 100px;" placeholder="部门代码"/>
                <!-- <el-option v-for="item in deptOptions" :key="item.code" :label="item.codeName" :value="item.code"/> -->
                <!-- </el-select> -->
              </el-form-item>
              <el-form-item class="day" label="完成时间" style="margin-left: 0px">
                <el-date-picker
                  v-model="startTimeAndEndTime"
                  :picker-options="pickerOptions"
                  type="daterange"
                  align="right"
                  unlink-panels
                  style="width: 250px"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"/>
              </el-form-item>
              <!-- <el-form-item>
                <el-button size="mini" @click="searchMoreForm = false">取 消</el-button>
                <el-button :loading="loadingButton" type="primary" size="mini" @click="searchMoreForm = false; findList('form')" >查询</el-button>
              </el-form-item> -->
            </el-form>
          </div>
          <vxe-grid
            ref="xGrid"
            :loading="loading"
            :pager-config="tablePage"
            :data="tableData"
            :columns="tableColumn"
            :auto-resize="true"
            border
            size="mini"
            show-overflow
            resizable
            highlight-hover-row
            @page-change="handlePageChange">
            <template v-slot:operate="{ row }">
              <el-button size="mini" icon="el-icon-view" title="查看" circle @click="handdle(row)"/>
            </template>
            <template v-slot:CTfmtDate="{ row }">
              <span>{{ dateFormat('YYYY-mm-dd HH:MM',row.createTime) }}</span>
            </template>
            <template v-slot:ERfmtDate="{ row }">
              <span>{{ dateFormat('YYYY-mm-dd HH:MM',row.esReturnDate) }}</span>
            </template>
            <template v-slot:statustype="{ row }">
              <span>{{ getStatusType(row.status) }}</span>
            </template>
            <template v-slot:depttType="{ row }">
              <span>{{ getdeptType(row.deptNo) }}</span>
            </template>
          </vxe-grid>
        </el-tab-pane>

        <el-tab-pane label="未归还查询" name="second">
          <el-form ref="NOform" :inline="true" :model="NOform">
            <el-form-item prop="brwNo">
              <el-input v-model="NOform.brwNo" placeholder="申请号" style="width: 165px" size="small"/>
            </el-form-item>
            <el-form-item prop="modelNo">
              <el-input v-model="NOform.modelNo" placeholder="型号" style="width: 165px" size="small"/>
            </el-form-item>
            <el-form-item>
              <el-button v-permission="['387285']" type="primary" size="small" @click="flash('NOform')">刷新</el-button>
            </el-form-item>
            <el-form-item>
              <el-button v-permission="['387285']" type="primary" size="small" style="margin-bottom:3px" @click="findreturnList('form')">查询</el-button>
            </el-form-item>
          </el-form>
          <vxe-grid
            ref="xGrid"
            :loading="loading"
            :pager-config="NOtablePage"
            :data="NOtableData"
            :columns="NOtableColumn"
            :auto-resize="true"
            border
            size="mini"
            show-overflow
            resizable
            highlight-hover-row
            @page-change="NOhandlePageChange">
            <template v-slot:ERTfmtDate="{ row }">
              <span>{{ dateFormat('YYYY-mm-dd HH:MM',row.esReturnDate) }}</span>
            </template>
            <!-- <template v-slot:deptType="{ row }">
              <span>{{ getdeptType(row.deptNo) }}</span>
            </template> -->
            <template v-slot:brStatustype="{ row }">
              <span>{{ getStatusType(row.optStatus) }}</span>
            </template>
          </vxe-grid>
        </el-tab-pane>

        <el-tab-pane label="申请明细" name="third">
          <div>
            <span class="collapse-title-class" style="font-size:13px;margin-left:5%" @click="baseInfoVisible = !baseInfoVisible">
              <i class="el-icon-tickets" style="color:#008080;"/>
              基础信息
              <i :title="baseInfoVisible === false? '展开': '收起'" :class="baseInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
            </span>
          </div>
          <div v-show="baseInfoVisible === true" class="product-base-content-body">
            <el-form ref="applyForm" label-width="100px" style="width:100%" label-suffix="" size="mini">
              <el-row type="flex">
                <el-form-item prop="brwNo" style="margin-left:4%;width:150px">
                  <span slot="label">
                    <span class="span-box">
                      <span>申请号</span>
                    </span>
                  </span>
                  <span>{{ applyForm.brwNo }}</span>
                </el-form-item>
                <el-form-item prop="brwPsnDept" class="elform" style="width:200px">
                  <span slot="label">
                    <span class="span-box">
                      <span>申请部门</span>
                    </span>
                  </span>
                  <span>{{ applyForm.brwPsnDept }}</span>
                </el-form-item>
                <el-form-item prop="createTime">
                  <span slot="label">
                    <span class="span-box">
                      <span>申请日期</span>
                    </span>
                  </span>
                  <span>{{ dateFormat('YYYY-mm-dd HH:MM', applyForm.createTime) }}</span>
                </el-form-item>
              </el-row>
              <el-row type="flex">
                <el-form-item prop="customerName" style="margin-left:4%;width:150px">
                  <span slot="label">
                    <span class="span-box">
                      <span>客户</span>
                    </span>
                  </span>
                  <span>{{ applyForm.customerName }}</span>
                </el-form-item>
                <el-form-item prop="userName" style="width:200px">
                  <span slot="label">
                    <span class="span-box">
                      <span>用户</span>
                    </span>
                  </span>
                  <span>{{ applyForm.userName }}</span>
                </el-form-item>
                <el-form-item prop="brwType">
                  <span slot="label">
                    <span class="span-box">
                      <span>借货类型</span>
                    </span>
                  </span>
                  <span>{{ applyForm.brwType }}</span>
                </el-form-item>
              </el-row>
              <el-row type="flex">
                <el-form-item prop="brwPsn" style="margin-left:4%;width:150px">
                  <span slot="label">
                    <span class="span-box">
                      <span>借货人</span>
                    </span>
                  </span>
                  <span>{{ applyForm.brwPsn }}</span>
                </el-form-item>
                <el-form-item prop="brwPsnTel" style="width:200px">
                  <span slot="label">
                    <span class="span-box">
                      <span>联系电话</span>
                    </span>
                  </span>
                  <span>{{ applyForm.brwPsnTel }}</span>
                </el-form-item>
                <el-form-item prop="deptNo">
                  <span slot="label">
                    <span class="span-box">
                      <span>部门</span>
                    </span>
                  </span>
                  <span>{{ applyForm.deptNo }}</span>
                </el-form-item>
              </el-row>
              <el-row type="flex">
                <el-form-item prop="salesPsn" style="margin-left:4%;width:150px">
                  <span slot="label">
                    <span class="span-box">
                      <span>业务员</span>
                    </span>
                  </span>
                  <span>{{ applyForm.salesPsn }}</span>
                </el-form-item>
                <el-form-item prop="salesPsnTel" style="width:200px">
                  <span slot="label">
                    <span class="span-box">
                      <span>联系电话</span>
                    </span>
                  </span>
                  <span>{{ applyForm.salesPsnTel }}</span>
                </el-form-item>
                <el-form-item prop="esReturnDate">
                  <span slot="label">
                    <span class="span-box">
                      <span>预计归还日期</span>
                    </span>
                  </span>
                  <span>{{ dateFormat('YYYY-mm-dd HH:MM', applyForm.esReturnDate) }}</span>
                </el-form-item>
              </el-row>
              <el-row type="flex">
                <el-form-item prop="purpose" style="margin-left:4%">
                  <span slot="label">
                    <span class="span-box">
                      <span>借货用途</span>
                    </span>
                  </span>
                  <span>{{ applyForm.purpose }}</span>
                </el-form-item>
              </el-row>
              <el-row type="flex">
                <el-form-item prop="remark" style="margin-left:4%">
                  <span slot="label" class="span-box">
                    <span>
                      <span>备注</span>
                    </span>
                  </span>
                  <span>{{ applyForm.remark }}</span>
                </el-form-item>
              </el-row>
            </el-form>
          </div>
          <div>
            <div>
              <div style="margin-top:15px">
                <span class="collapse-title-class" style="font-size:13px;margin-left:5%" @click="receInfoVisible = !receInfoVisible">
                  <i class="el-icon-suitcase" style="color: #A52A2A"/>
                  收货信息
                  <i :title="receInfoVisible === false? '展开': '收起'" :class="receInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'" />
                </span>
              </div>
              <div v-if="receInfoVisible === true" style="height:100%;width:60%;padding: 0 20px;margin-left:5%" class="base-index" >
                <vxe-table
                  :data="table"
                  border
                  auto-resize
                  resizable
                  width="80%"
                  align="center"
                  size="mini">
                  <vxe-table-column field="receiverName" min-width="80" title="收货人" show-overflow/>
                  <vxe-table-column field="receiverPhone" min-width="80" title="收货电话" show-overflow/>
                  <vxe-table-column field="receiverAddress" min-width="150" title="收货地址" show-overflow/>
                  <vxe-table-column field="receiverCompany" min-width="110" title="收货公司" show-overflow/>
                </vxe-table>
              </div>
            </div>
            <el-row style="margin-bottom: 10px;margin-top: 30px;margin-left:5%">
              <el-button type="primary" size="mini" plain icon="el-icon-close" @click="chuku('applyForm')">出库</el-button>
            </el-row>
            <el-table
              v-loading="listLoading"
              :data="detailTableData"
              border
              show
              show-overflow-tooltip
              size="mini"
              style="width: 60%; margin-left:5%">
              <el-table-column
                prop="modelNo"
                label="型号"
                align="center"/>
              <el-table-column
                prop="quantity"
                label="借货数量"
                align="center"/>
              <el-table-column
                prop="optStatus"
                label="状态"
                align="center"/>
              <el-table-column
                prop="remark"
                label="备注"
                align="center"/>
              <el-table-column
                :formatter="formatDate"
                prop="shipDate"
                label="出库时间"
                align="center"/>
              <el-table-column
                prop="returnQty"
                label="已归还数量"
                align="center"/>
              <el-table-column
                prop="expQty"
                align="center"
                label="已出库数量"/>
            </el-table>
        </div></el-tab-pane>
      </el-tabs>
    </el-main>
  </el-container>
</template>
<script>
import { findbrwstock, findNotReturn, findbrwDetail, exWarehouse } from '@/api/stock/borrow'
import { getDictDataByPid } from '@/api/common/dict'
export default {
  name: 'Borrow',
  components: {

  },
  data() {
    return {
      baseInfoVisible: true,
      receInfoVisible: true,
      listLoading: false,
      detailTableData: [],
      detailform: {},
      applyForm: {},
      table: [],
      exreturn: {
        optType: '+',
        userName: '',
        dtoList: [],
        userDto: {}
      },
      activeName: 'first',
      startTimeAndEndTime: '',
      searchForm: {},
      searchMoreForm: false,
      form: {
        timeStart: '',
        timeEnd: ''
      },
      exWarehouseData: {
        optType: '',
        item: []
      },
      loading: false,
      tableData: [],
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 20,
        align: 'right',
        pageSizes: [20, 50, 100, 200, 500],
        layouts: [
          'Sizes',
          'PrevJump',
          'PrevPage',
          'Number',
          'NextPage',
          'NextJump',
          'FullJump',
          'Total'
        ],
        perfect: true
      },
      tableColumn: [
        { field: 'brwNo', title: '申请号', align: 'center' },
        { field: 'status', title: '状态', align: 'center', slots: { default: 'statustype' }},
        { field: 'deptNo', title: '部门', align: 'center' },
        { field: 'customerNo', title: '客户代码', align: 'center' },
        { field: 'customerName', title: '客户名称', align: 'center' },
        { field: 'userNo', title: '用户代码', align: 'center' },
        { field: 'userName', title: '用户名称', align: 'center' },
        { field: 'purpose', title: '用途', align: 'center' },
        { field: 'remark', title: '备注', align: 'center' },
        { field: 'brwPsn', title: '借贷人', align: 'center' },
        { field: 'createTime', title: '申请时间', align: 'center', slots: { default: 'CTfmtDate' }},
        { field: 'esReturnDate', title: '预计归还时间', align: 'center', slots: { default: 'ERfmtDate' }},
        { title: '操作', slots: { default: 'operate' }, align: 'center', fixed: 'right' }
      ],
      statusCode: '2004',
      deptCode: '2025',
      statusOptions: [],
      deptOptions: [],
      dept: [
        { value: 1, label: '广州' }
      ],
      NOform: {
        brwNo: '',
        modelNo: ''
      },
      NOtableData: [],
      NOtablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 20,
        align: 'right',
        pageSizes: [20, 50, 100, 200, 500],
        layouts: [
          'Sizes',
          'PrevJump',
          'PrevPage',
          'Number',
          'NextPage',
          'NextJump',
          'FullJump',
          'Total'
        ],
        perfect: true
      },
      NOtableColumn: [
        { field: 'brwNo', title: '申请号', align: 'center' },
        { field: 'optStatus', title: '归还状态', align: 'center', slots: { default: 'brStatustype' }},
        { field: 'modelNo', title: '型号', align: 'center' },
        { field: 'quantity', title: '借出数量', align: 'center' },
        { field: 'returnQty', title: '已归还数量', align: 'center' },
        { field: 'customerName', title: '客户名称', align: 'center' },
        { field: 'userName', title: '用户名称', align: 'center' },
        { field: 'brwPsn', title: '借货人', align: 'center' },
        { field: 'brwPsnTel', title: '借货人电话', align: 'center' },
        { field: 'salesPsn', title: '担当', align: 'center' },
        { field: 'salesPsnTel', title: '担当电话', align: 'center' },
        { field: 'deptNo', title: '部门', align: 'center' },
        { field: 'purpose', title: '用途', align: 'center' },
        { field: 'esReturnDate', title: '预计归还时间', align: 'center', slots: { default: 'ERTfmtDate' }}
      ]
    }
  },
  created() {
    this.iniData()
    this.findList()
    // this.findreturnList()
  },
  methods: {
    iniData() {
      getDictDataByPid(this.statusCode).then(result => {
        console.log(result)
        this.statusOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.deptCode).then(result => {
        console.log(result)
        this.deptOptions = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    tabchange() {
      if (this.activeName === 'second') {
        this.findreturnList()
      }
    },
    setDateCondition() {
      if (this.startTimeAndEndTime === null) {
        this.form.timeStart = ''
        this.form.timeEnd = ''
      } else {
        this.form.timeStart = this.startTimeAndEndTime[0]
        this.form.timeEnd = this.startTimeAndEndTime[1]
      }
    },
    findList() {
      this.loading = true
      this.setDateCondition()
      const data = {
        status: this.form.status,
        brwNo: this.form.brwNo,
        customerNo: this.form.customerNo,
        customerName: this.form.customerName,
        deptNo: this.form.deptNo,
        timeStart: this.form.timeStart,
        timeEnd: this.form.timeEnd
      }
      const page = {
        pageNumber: this.tablePage.currentPage,
        pageSize: this.tablePage.pageSize
      }
      findbrwstock(data, page).then(res => {
        this.tableData = res.content.list
        if (res.code === '200') {
          // this.$message.success('查询成功')
        }
        this.tablePage.total = res.content.total
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.log(error)
      })

      // this.reset()
    },
    findreturnList() {
      this.loading = true
      const data = {
        brwNo: this.NOform.brwNo,
        modelNo: this.NOform.modelNo
      }
      const page = {
        pageNumber: this.tablePage.currentPage,
        pageSize: this.tablePage.pageSize
      }
      findNotReturn(data, page).then(res => {
        if (res.code === '200') {
          // this.$message.success('查询成功')
        }
        this.NOtableData = res.content.list
        this.NOtablePage.total = res.content.total
        this.loading = false
      }).catch(error => {
        this.loading = false
        console.log(error)
      })
    },
    NOhandlePageChange({ currentPage, pageSize }) {
      this.NOtablePage.currentPage = currentPage
      this.NOtablePage.pageSize = pageSize
      this.findList()
    },
    flash(NOform) {
      this.$refs[NOform].resetFields()
      this.findreturnList()
    },
    reset(form) {
      this.startTimeAndEndTime = ''
      this.form = this.$options.data().form
    },
    handlePageChange({ currentPage, pageSize }) {
      this.tablePage.currentPage = currentPage
      this.tablePage.pageSize = pageSize
      this.findList()
    },
    handdle(row) {
      // console.log('点击选中行的数据 ->', row)
      this.applyForm = row
      const table = []
      table.push(row)
      this.table = table
      // console.log(this.applyForm.id)19
      this.activeName = 'third'
      const data = {
        id: this.applyForm.id
      }
      this.listLoading = true
      findbrwDetail(data).then(res => {
        this.detailTableData = res.content
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    dateFormat(fmt, date) {
      if (date == null) {
        return null
      }
      let ret = ''
      date = new Date(date)
      const opt = {
        'Y+': date.getFullYear().toString(), // 年
        'm+': (date.getMonth() + 1).toString(), // 月
        'd+': date.getDate().toString(), // 日
        'H+': date.getHours().toString(), // 时
        'M+': date.getMinutes().toString(), // 分
        'S+': date.getSeconds().toString() // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
      }
      for (const k in opt) {
        ret = new RegExp('(' + k + ')').exec(fmt)
        if (ret) {
          fmt = fmt.replace(
            ret[1],
            ret[1].length === 1 ? opt[k] : opt[k].padStart(ret[1].length, '0')
          )
        }
      }
      return fmt
    },
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    formatDate(row, column) {
      // 获取单元格数据
      const data = row[column.property]
      if (data == null) {
        return null
      }
      const dt = new Date(data)
      return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
    },
    getStatusType(val) {
      const stu = val + ''
      const obj = this.statusOptions.filter(item => item.code === stu)[0]
      return obj ? obj.codeName : ''
    },
    getdeptType(val) {
      console.log(val)
      const stu = val + ''
      const obj = this.deptOptions.filter(item => item.code === stu)[0]
      return obj ? obj.codeName : ''
    },
    chuku(applyForm) {
      this.exreturn.dtoList = []
      for (let i = 0; i < this.detailTableData.length; i++) {
        const data = {
          orderId: this.applyForm.brwNo,
          qaStatus: this.detailTableData[i].optStatus,
          modelno: this.detailTableData[i].modelNo,
          qty: this.detailTableData[i].quantity,
          warehouseCode: this.detailTableData[i].warehouseCode,
          customerNo: this.applyForm.customerNo,
          projectCode: this.detailTableData[i].itemId
        }

        this.exreturn.dtoList.push(data)
      }
      this.exreturn.userDto.userName = this.applyForm.userName
      exWarehouse(this.exreturn).then(res => {
        if (res.code === 200) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
    }
  }
}
</script>

<style scoped>
.span-box {
  display: inline-block;
  position: relative;
  white-space: nowrap;
  font-size: 7px;
  color: 96, 98, 102;
}
.searchform{
  margin-top: 15px;
}
.col-class{
  margin-left: 5%;
  font-size: 7px;
}

</style>
