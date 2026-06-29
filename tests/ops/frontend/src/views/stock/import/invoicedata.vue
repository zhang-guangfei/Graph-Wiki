
<template>
  <div class="app-container"> <el-tabs v-model="activeTabName" type="card" @tab-click="handleTabClick">
    <el-tab-pane label="未入库发票" name="pane1">
      <div>
        <el-row>
          <el-card>
            <el-form
              ref="queryForm"
              :inline="true"
              :model="queryForm"
              size="mini"
              label-width="100px"
            >

              <el-form-item label="发票号">
                <el-input
                  v-model="queryForm.id"
                  style="width: 120px"
                  clearable
                  placeholder="发票号"
                />
              </el-form-item>
              <el-form-item label="供应商">
                <el-select
                  v-model="queryForm.id"
                  placeholder="请选择"
                  style="width: 125px"
                  clearable
                  size="mini"
                >
                  <el-option
                    v-for="item in stockCodeOptions"
                    :key="item.code"
                    :value="item.code"
                    :label="item.codeName"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="状态">
                <el-input v-model="queryForm.id" clearable placeholder="状态" />
              </el-form-item>

              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="searchData"
                >查询</el-button
                >
              </el-form-item>

            </el-form>

          </el-card>
        </el-row>
      </div>
      <div style="margin-top: 10px">
        <el-table
          ref="multipleTable"
          :data="listdata"
          tooltip-effect="dark"
          border
          size="mini"
          style="width: 100%"
          @selection-change="handleSelection"
        >
          <!-- 表头字段 -->
          <!-- <el-table-column type="selection" width="55" /> -->
          <el-table-column fixed prop="customerNo" label="发票号" />
          <el-table-column fixed prop="stockCode" label="供应商" />
          <el-table-column :formatter="stoctCodeNameformatter" prop="stockCode" label="发出日期" />
          <el-table-column prop="modelNo" label="预计到达" />
          <el-table-column prop="locationNo" label="运输方式" />
          <el-table-column prop="initStockQty" label="备注" />
          <el-table-column prop="initUnitQty" label="数据导入时间" />
          <el-table-column prop="initStockMonth" label="已导入数据" />
          <el-table-column prop="initStockMonth" label="入库票号" />
          <el-table-column prop="initStockMonth" label="发票金额" />
          <el-table-column prop="initStockMonth" label="发票订单" />
          <el-table-column prop="initStockMonth" label="发货箱数" />
          <el-table-column prop="initStockMonth" label="分包金额" />
          <el-table-column prop="initStockMonth" label="分包数量" />
          <el-table-column prop="initStockMonth" label="分包订单数" />
          <el-table-column prop="initStockMonth" label="到达物流时间" />
          <el-table-column prop="initStockMonth" label="入库时间" />
          <el-table-column prop="replType" label="类型" />
          <el-table-column :formatter="warehourseStatusformatter" prop="status" label="状态"/>
          <el-table-column :formatter="updateTimeformatter" prop="updateTime" label="更新时间" />

          <el-table-column label="操作" align="center" width="140">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" icon="el-icon-delete" @click="deleteCsStockSettingById(scope.$index, scope.row)" />
            </template>
          </el-table-column>
        </el-table>
        <pagination
          v-show="settingFromTotal > 0"
          :total="settingFromTotal"
          :page.sync="queryForm.pageNum"
          :limit.sync="queryForm.pageSize"
          @pagination="searchData"
        />
      </div>
      <div/>
    </el-tab-pane>

    <el-tab-pane label="导入发票数据" name="pane2" type="border-card">
      <el-card>
        <el-form
          ref="impform"
          :inline="true"
          :model="impform"
          size="mini"
          label-width="100px"
        >
          <el-form-item label="发票号">
            <el-input
              v-model="impform.customerNo"
              style="width: 120px"
              clearable
              placeholder="客户代码"
            />
          </el-form-item>

          <el-form-item label="发货日期">
            <el-date-picker
              v-model="impform.fromDate"
              type="date"
              size="mini"
              placeholder="开始时间"
              value-format="yyyy-MM-dd"
              style="width: 130px"
            />

          </el-form-item>
          <el-form-item label="运输方式">
            <el-select
              v-model="impform.stockCode"
              placeholder="请选择"
              style="width: 125px"
              size="mini"
              clearable
            >
              <el-option
                v-for="item in stockCodeOptions"
                :key="item.code"
                :value="item.code"
                :label="item.codeName"
              />
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-upload2"
              size="mini"
              @click="uploadFileDialogVisible = true">选择导入</el-button>

        </el-form-item></el-form>

      </el-card>
      <div style="margin-top: 10px">
        <el-table
          ref="multipleTable"
          :data="implistdata"
          tooltip-effect="dark"
          border
          size="mini"
          style="width: 100%"
        >
          <el-table-column fixed prop="id" label="ID" type="index" />
          <el-table-column fixed prop="invoiceNo" label="发票号" />
          <el-table-column prop="orderNo" label="订单号" />
          <el-table-column prop="modelNo" label="型号" />
          <el-table-column prop="quantity" label="数量" />
          <el-table-column prop="price" label="价格" />
          <el-table-column prop="shippedDate" label="发货日期" />
          <el-table-column prop="shippingLabel" label="条码" />
          <el-table-column prop="caseNo" label="箱号" />
          <el-table-column prop="shippingMethod" label="运输方式" />
          <el-table-column prop="supplierCode" label="供应商" />
          <el-table-column prop="origin" label="原产地" />
          <el-table-column prop="weight" label="重量" />
          <el-table-column prop="remark" label="备注" />
          <el-table-column label="操作" align="center" width="140">
            <!-- <template slot-scope="scope"> -->
            <!-- <el-button type="primary" size="mini" icon="el-icon-edit" @click="showEditDetail(scope.row)" />
              <el-button type="primary" size="mini" icon="el-icon-delete" @click="deleteCsWarehourse(scope.$index, scope.row)" /> -->
            <!-- </template> -->
          </el-table-column>
        </el-table>
        <pagination
          v-show="impform.total > 0"
          :total="impform.total"
          :page.sync="impform.pageNum"
          :limit.sync="impform.pageSize"
          @pagination="searchWarehourseData"
        />
      </div>
    </el-tab-pane>
  </el-tabs>
    <el-divider />
    <el-dialog
      :close-on-click-modal="false"
      :visible.sync="uploadFileDialogVisible"
      :before-close="closeClick"
      title="上传"
      width="420px"
    >
      <div class="upload">
        <el-upload
          :action="actionUrl"
          :before-upload="beforeUploadFile"
          :on-success="uploadSuccess"
          class="upload-demo"
          drag
          multiple
        >
          <div class="el-upload__text">支持txt格式</div>
          <div v-if="file !== null" class="el-upload__text">
            {{ file.name }}
          </div>
          <div v-else class="el-upload__text">
            将文件拖到此处，或<em>点击上传</em>
          </div>
        </el-upload>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="closeClick">关闭</el-button>
        <!-- <el-button
          type="primary"
          @click="downloadTemplate('../../../template/downLoadTemplate/csstocksetting.xlsx')">下载模板</el-button> -->
        <el-button type="success" @click="importData">
          <span :loading="uploadStatus">{{
            uploadStatus ? "上传中..." : "上传文件"
          }}</span>
        </el-button>
      </span>

  </el-dialog></div>
</template>

<script>
import Pagination from '@/components/Pagination'
import { uploadFile } from '@/api/invoicedata.js'
export default {
  name: '',
  components: { Pagination },
  data() {
    return {
      activeTabName: 'pane1',
      listLoading: true,
      actionUrl: '',
      listdata: [],
      implistdata: [],
      impform: {
        modelNo: '',
        pageNum: 0,
        pageSize: 10,
        total: 0
      },
      queryForm: {
        id: 0,
        isEditable: false,
        pageNum: 0,
        pageSize: 10,
        total: 0
      },
      settingFromTotal: 0,
      warehourseFromTotal: 0,
      uploadFileDialogVisible: false,
      dialogStockVisible: false,
      file: null,
      stockCodeOptions: [],
      // 寄售仓库
      stockCodeClassCode: '2027'
    }
  },
  created() {
    this.initStockCodedesc()
  },
  methods: {
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    uploadSuccess(response, file, fileList) {
      // console.log(response)
      // console.log(file)
      // console.log(fileList)
    },
    importData() {
      this.uploadStatus = true
      const file = this.file
      if (!this.file) { // 防止空传
        this.uploadStatus = false
        this.$message.error('请选择文件上传')
        return false
      }
      const formData = new FormData() // form表单格式提交数据
      formData.append('file', file)
      this._importEmployeeAssistant(formData)
      this.uploadStatus = false
    },
    _importEmployeeAssistant(formData) {
      uploadFile(formData)
        .then(res => {
          console.log(res)
          if (res.success) {
            this.file = null
            this.implistdata = res.content
            this.impform.total = res.content.length
            this.uploadFileDialogVisible = false
          } else {
            this.$message.error(res.content)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    }
  }

}
</script>
<style scoped>
.line {
  text-align: center;
  margin-left: 5%;
}
.tableCloumn {
  position: absolute;
  margin-top: 30px;
  padding-right: 10px;
  width: 99%;
}
</style>
