<template>
  <div class="container">
    <div class="header">
      <el-form :inline="true" :model="searchForm" size="mini" class="demo-form-inline">
        <!-- <el-form-item>
          <el-select v-model="searchForm.pdType" clearable placeholder="票据类型">
            <el-option
              v-for="item in pdTypeList"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"
            />
          </el-select>
        </el-form-item> -->
        <el-form-item>
          <el-select v-model="searchForm.warehouseType" clearable placeholder="仓库类别" @change="selTypeChange">
            <el-option
              v-for="item in warehouseTypeList"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="searchForm.warehouseCodes"
            :remote-method="remoteMethodByWareHouseCode"
            :loading="selWareHouseCodeLoading"
            filterable
            multiple
            collapse-tags
            remote
            clearable
            placeholder="仓库代码"
            style="width: 220px">
            <el-option
              v-for="item in wareHouseCodeList"
              :key="item.warehouseCode"
              :label="'【'+item.warehouseCode+'】'+item.warehouseName"
              :value="item.warehouseCode"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-autocomplete
            v-model="searchForm.pdBillNo"
            :fetch-suggestions="querySearchAsync"
            :debounce="0"
            :popper-append-to-body="false"
            popper-class="el-autocomplete-suggestion"
            highlight-first-item
            type="text"
            size="mini"
            placeholder="请输入盘点票号"
            class="select"
            @select="Changeselect">
            <template slot-scope="{ item }">
              <div style="width: 220px" class="name">{{ item.pdBillNo }}</div>
            </template>
          </el-autocomplete>
        </el-form-item>
        <!-- <el-form-item>
          <el-select
            v-model="searchForm.pdBillNo"
            :remote-method="queryPdBillNoASync"
            :loading="selPdBillNo"
            filterable
            remote
            reserve-keyword
            placeholder="请输入盘点票号"
            style="width: 220px"
            @change="change()"
            @keyup.enter.native="clickEnter()">
            <el-option
              v-for="item in pdBillNolist"
              :key="item.pdBillNo"
              :label="item.pdBillNo"
              :value="item.pdBillNo"
            />
          </el-select>
        </el-form-item> -->
        <el-form-item>
          <el-input v-model="searchForm.modelNo" clearable placeholder="请输入型号" />
        </el-form-item>
        <el-form-item>
          <el-input v-model="searchForm.pdInputort" clearable placeholder="录入担当" />
        </el-form-item>
        <el-form-item>
          <i class="el-icon-delete" style="margin-left: 30px;font-weight: 600;" @click="clear()" />
          <el-button type="primary" style="margin-left: 20px;" @click="searchList()">查询</el-button>
          <el-button-group>
            <el-button type="primary" icon="el-icon-arrow-left" @click="lastPdBillNo()">上一页</el-button>
            <el-button type="primary" @click="nextPdBillNo()">下一页<i class="el-icon-arrow-right el-icon--right"/></el-button>
          </el-button-group>
        </el-form-item>
      </el-form>
    </div>
    <div class="tab">
      <el-tabs v-model="activeName" @tab-click="handleClick">
        <el-tab-pane label="现品票录入" name="first">
          <el-button-group>
            <el-button size="mini" type="primary" @click="up()">更新</el-button>
            <el-button size="mini" type="primary" @click="clearSelDataQty()">清空当前选中票数量</el-button>
          </el-button-group>
          <div>
            <vxe-table
              ref="table"
              :data="xpImputDataList"
              :edit-config="{trigger: 'dblclick', mode: 'cell'}"
              :keyboard-config="keyboardConfig"
              :mouse-config="{selected: true}"
              :row-config="{height: 25}"
              :column-config="{resizable: true}"
              show-overflow
              height="600"
              border
              size="mini"
              @keydown="handleEnterKey({ $event })"
              @cell-selected="cellMouse"
              @checkbox-change="xpSelHand"
              @checkbox-all="xpSelHand"
            >
              <vxe-table-column type="checkbox" width="50"/>
              <vxe-table-column field="id" title="ID" width="80"/>
              <vxe-table-column field="pdBatchNo" title="盘点批次号" show-overflow/>
              <vxe-table-column field="pdBillNo" title="盘点票号" />
              <vxe-table-column field="modelNo1" title="型号"/>
              <vxe-table-column field="shelvesNo" title="货架号"/>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="pdQty1" title="盘点数量" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.pdQty1" type="text" @change="edit(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column field="updateUser" title="录入人"/>
              <vxe-table-column field="updateTimeUI" title="录入时间"/>
            </vxe-table>
            <pagination
              v-show="total > 10"
              :total="total"
              :page-sizes="[10, 20, 100, 150, 200]"
              :page.sync="searchForm.page.pageNumber"
              :limit.sync="searchForm.page.pageSize"
              @pagination="searchList()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="现品空白票录入" name="second">
          <el-button-group>
            <el-button size="mini" type="primary" @click="upBlankBill()">更新</el-button>
            <el-button size="mini" type="primary" @click="clearSelBlankDataQty()">清空录入信息</el-button>
          </el-button-group>
          <div>
            <vxe-table
              ref="tableXpBlank"
              :data="blankBillImputDataList"
              :edit-config="{trigger: 'dblclick', mode: 'cell'}"
              :keyboard-config="keyboardConfig"
              :mouse-config="{selected: true}"
              :row-config="{height: 25}"
              :column-config="{resizable: true}"
              show-overflow
              height="600"
              border
              size="mini"
              @keydown="handleEnterKeyXpBlank({ $event })"
              @cell-selected="cellMouseXPBlank"
              @checkbox-change="blankBillTableHand"
              @checkbox-all="blankBillTableHand"
            >
              <vxe-table-column type="checkbox" width="50"/>
              <vxe-table-column field="id" title="ID" width="80"/>
              <vxe-table-column field="pdBatchNo" title="盘点批次号" show-overflow/>
              <vxe-table-column field="pdBillNo" title="盘点票号" />
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="shelvesNo" title="货架号" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.shelvesNo" type="text" @change="editXpBlankBill(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="modelNo1" title="型号" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.modelNo1" type="text" @change="editXpBlankBill(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="pdQty1" title="盘点数量" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.pdQty1" type="text" @change="editXpBlankBill(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column field="updateUser" title="修改者"/>
              <vxe-table-column field="updateTimeUI" title="修改时间"/>
            </vxe-table>
            <pagination
              v-show="total > 10"
              :total="total"
              :page-sizes="[10, 20, 100, 150, 200]"
              :page.sync="searchForm.page.pageNumber"
              :limit.sync="searchForm.page.pageSize"
              @pagination="searchList()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="到货未入空白票录入" name="third">
          <el-button-group>
            <el-button size="mini" type="primary" @click="upArriveNotInBlankData()" >更新</el-button>
            <el-button size="mini" type="primary" @click="clearArriveNotInBlankDataQty()">清空录入信息</el-button>
          </el-button-group>
          <div>
            <vxe-table
              ref="tableXPBlank"
              :data="arriveNoEnterInputDataList"
              :edit-config="{trigger: 'dblclick', mode: 'cell'}"
              :keyboard-config="{isArrow: true,isEnter: true,isEdit: true,isTab: true}"
              :mouse-config="{selected: true}"
              :row-config="{height: 25}"
              :column-config="{resizable: true}"
              show-overflow
              height="600"
              border
              size="mini"
              @keydown="handleEnterKeyXpBlank({ $event })"
              @cell-selected="cellMouseXPBlank"
              @checkbox-change="arriveNoEnterTableHand"
              @checkbox-all="arriveNoEnterTableHand"
            >
              <vxe-table-column type="checkbox" width="50"/>
              <vxe-table-column field="id" title="ID" width="80"/>
              <vxe-table-column field="pdBatchNo" title="盘点批次号" show-overflow/>
              <vxe-table-column field="pdBillNo" title="盘点票号" />
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="invoiceNo" title="发票号" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.invoiceNo" type="text" @change="editArriveNotIn(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="caseNo" title="拍号" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.caseNo" type="text" @change="editArriveNotIn(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="modelNo1" title="型号" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.modelNo1" type="text" @change="editArriveNotIn(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="barcode" title="BarCode" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.barcode" type="text" @change="editArriveNotIn(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="pdQty1" title="盘点数量" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.pdQty1" type="text" @change="editArriveNotIn(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column field="updateUser" title="修改者"/>
              <vxe-table-column field="updateTimeUI" title="修改时间"/>
            </vxe-table>
            <pagination
              v-show="total > 10"
              :total="total"
              :page-sizes="[10, 20, 100, 150, 200]"
              :page.sync="searchForm.page.pageNumber"
              :limit.sync="searchForm.page.pageSize"
              @pagination="searchList()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="线下数据导入" name="fourth">
          <el-button-group>
            <el-button size="mini" type="primary" @click="showImpDialog()">导入</el-button>
            <!-- <el-button size="mini" type="primary">编辑</el-button> -->
            <el-button size="mini" type="primary" @click="sureUpBorrowData()">确认</el-button>
            <el-button size="mini" type="primary" @click="delBorrowData()">删除</el-button>
          </el-button-group>
          <div>
            <vxe-table
              ref="borrowData"
              :data="borrowDataList"
              :edit-config="{trigger: 'click', mode: 'cell'}"
              :keyboard-config="{isArrow: true,isEnter: true,isEdit: true,isTab: true}"
              :mouse-config="{selected: true}"
              :row-config="{height: 25}"
              :column-config="{resizable: true}"
              show-overflow
              height="600"
              border
              size="mini"
              @keydown="handleEnterKeyXpBlank({ $event })"
              @cell-selected="cellMouseXPBlank"
              @checkbox-change="offlineTableHand"
              @checkbox-all="offlineTableHand"
            >
              <vxe-table-column type="checkbox" width="50"/>
              <vxe-table-column field="pdBatchNo" title="盘点批次号" show-overflow/>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="orderNo" title="单号" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.orderNo" type="text" @change="editBorrowData(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="modelNo" title="型号" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.modelNo" type="text" @change="editBorrowData(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="qty" title="数量" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.qty" type="text" @change="editBorrowData(row)" />
                </template>
              </vxe-table-column>
              <!-- <vxe-table-column field="borrowPerson" title="借出方"/> -->
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="borrowDateUI" title="借库日期" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.borrowDateUI" type="date" @change="editBorrowData(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="borrowPerson" title="借库人" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.borrowPerson" type="text" @change="editBorrowData(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="borrowDept" title="借库部门" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.borrowDept" type="text" @change="editBorrowData(row)" />
                </template>
              </vxe-table-column>
              <vxe-table-column field="updateUser" title="录入人"/>
              <vxe-table-column field="updateTimeUI" title="录入时间"/>
            </vxe-table>
            <pagination
              v-show="total > 10"
              :total="total"
              :page-sizes="[10, 20, 100, 150, 200]"
              :page.sync="searchForm.page.pageNumber"
              :limit.sync="searchForm.page.pageSize"
              @pagination="searchBorrowData()"/>
          </div>
        </el-tab-pane>
        <el-tab-pane label="其他数据录入" name="five">
          <!-- <el-button-group>
            <el-button size="mini" type="primary">编辑</el-button>
            <el-button size="mini" type="primary">确认</el-button>
            <el-button size="mini" type="primary">删除</el-button>
          </el-button-group> -->
          <div>
            <vxe-table
              :data="otherDataList"
              :edit-config="{trigger: 'click', mode: 'cell'}"
              :keyboard-config="{isArrow: true,isEnter: true,isEdit: true,isTab: true}"
              :mouse-config="{selected: true}"
              :row-config="{height: 25}"
              :column-config="{resizable: true}"
              show-overflow
              height="600"
              border
              size="mini"
              @checkbox-change="selectOtherChangeEvent"
              @checkbox-all="selectOtherChangeEvent"
            >
              <vxe-table-column type="checkbox" width="50"/>
              <vxe-table-column field="pdBatchNo" title="盘点批次号"/>
              <vxe-table-column field="impDataType" title="导入数据类型" show-overflow/>
              <vxe-table-column field="jobType" title="作业类型" show-overflow/>
              <!-- <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="jobExecTimeUI" title="自动执行时间" >
                <template #edit="{ row }">
                  <vxe-input v-model="row.jobExecTimeUI" type="datetime" @change="editOtherData(row)" />
                </template>
              </vxe-table-column> -->
              <vxe-table-column field="execFlagName" title="执行结果" show-overflow/>
              <vxe-table-column field="updateTimeUI" title="设置时间"/>
              <vxe-table-column field="updateUser" title="设置人"/>
              <vxe-table-column field="opt" title="操作">
                <template #default="{ row }">
                  <vxe-button type="text" status="primary" content="手动执行" @click="exportData(row)" />
                  <vxe-button type="text" status="primary" content="导出" @click="exportDataByExcel(row)" />
                  <vxe-button type="text" status="primary" content="删除" @click="delOtherDataById(row)" />
                </template>
              </vxe-table-column>
            </vxe-table>
          </div>
        </el-tab-pane>
      </el-tabs>
      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadFileDialogVisible"
        :before-close="closeClick"
        title="到货未入数据导入"
        width="420px"
      >
        <div class="upload">
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
            class="upload-demo"
            drag
            multiple
          >
            <div class="el-upload__text">支持xlsx格式</div>
            <div v-if="file !== null" class="el-upload__text">
              {{ file.name }}
            </div>
            <div v-else class="el-upload__text">
              将文件拖到此处，或<em>点击上传</em>
            </div>
          </el-upload>
        </div>
        <div slot="footer" class="dialog-footer">
          <el-button :loading="downLoadExcelBtuLoad" size="small" type="success" class="downLoadExcel" @click="downLoadExcel" >下载模板</el-button>
          <el-button size="mini" @click="uploadFileDialogVisible = false">取 消</el-button>
          <el-button :loading="uploadLoading" size="mini" type="primary" @click="importData()">导 入</el-button>
        </div>
      </el-dialog>
      <el-dialog
        :visible.sync="dialogVisible"
        :close-on-click-modal="false"
        title="提示"
        width="30%"
        height="200px">
        <span><h2>{{ msg }}</h2></span>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import { getDictDataByPid, findWareHouseInfoWithLike } from '@/api/common/dict'
import { listPdBillData, updatePdBillDataById, clearPdXpBillDataQtyById, upXpBlankBillInputInfo, clearPdXpBlankBillDataQtyById,
  upArriveNotINBlankBillInputInfo, clearArriverNotInBlankBillDataQtyById, downBorrowExel, batchImpBorrowData, delBorrowDataById, upBorrowInfo,
  listBorrowDataList, editOtherData, delOtherDataById, listPdOtherDataImp, exportOtherData, remotePdBillNo, exportOtherDataWithExcel } from '@/api/pd/pd'
import Pagination from '@/components/Pagination'
export default {
  name: 'PdInput',
  components: { Pagination },
  data() {
    return {
      searchForm: {
        pdType: '',
        warehouseType: 'MASTER',
        warehouseCodes: [],
        pdBillNo: '',
        modelNo: '',
        pdInputort: '',
        pdDataType: '',
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      selectedPdBillNo: '',
      remotePdBillNoVo: {
        warehouseCodes: [],
        pdBillNo: ''
      },
      pdInputDto: {
        dto: []
      },
      PdBorrowDto: {
        dto: []
      },
      PdOtherDataDto: {
        dto: []
      },
      tableFiled: '',
      curColumIndex: 0,
      columnIndex: 0,
      rowSize: 0,
      total: 0,
      curcount: 0,
      msg: '',
      uploadFileDialogVisible: false,
      downLoadExcelBtuLoad: false,
      uploadLoading: false,
      dialogVisible: false,
      selPdBillNo: false,
      pdBillNolist: [],
      xpTabDataWithInput: [],
      file: null,
      actionUrl: '',
      pdTypeCode: '5003',
      warehouseTypeClassCode: '4001',
      selWareHouseCodeLoading: false,
      tableFlag: '',
      xpImputDataList: [],
      blankBillImputDataList: [],
      arriveNoEnterInputDataList: [],
      borrowDataList: [],
      otherDataList: [],
      pdTypeList: [],
      tabShowName: 'first',
      warehouseTypeList: [],
      wareHouseCodeList: [],
      selDataList: [],
      selBlankBillData: [],
      selArriveNotInList: [],
      selBorrowList: [],
      activeName: 'first',
      stockCodeList: [
        {
          value: '选项1',
          label: '北京物流中心'
        },
        {
          value: '选项2',
          label: '上海物流中心'
        }
      ],
      keyboardConfig: {
        isArrow: () => true, // 允许使用箭头键导航
        isTab: () => true, // 允许使用 Tab 键导航
        isEnter: () => true, // 允许使用回车键导航
        isEdit: () => true
      },
      keyboardConfigXpBill: {
        isArrow: () => true, // 允许使用箭头键导航
        isTab: () => true, // 允许使用 Tab 键导航
        isEnter: () => true, // 允许使用回车键导航
        isEdit: () => true,
        editMethod({ row, column }) {
          const $table = this.$refs.tableXpBlank.value
          // 重写默认的覆盖式，改为追加式
          $table.setEditCell(row, column)
        }
      }
    }
  },
  mounted() {
    this.getPdType()
    this.getDataCodesTreeBywarehouseType()
    this.searchList()
    this.remoteMethodByWareHouseCode()
    this.queryPdBillNoASync(this.searchForm.pdBillNo)
  },
  methods: {
    nextPdBillNo() {
      if (this.searchForm.pdBillNo !== '') {
        // 原始字符串
        const originalString = this.searchForm.pdBillNo
        // 截取最后5位
        const lastFiveChars = originalString.slice(-5)
        // 将截取到的字符串转换为整数
        let number = parseInt(lastFiveChars, 10)
        // 对整数加1
        number += 1
        const newLastFiveChars = number.toString().padStart(5, '0')
        // 拼接回原始字符串的前部分
        this.searchForm.pdBillNo = originalString.slice(0, -5) + newLastFiveChars
        this.searchList()
      }
    },
    lastPdBillNo() {
      if (this.searchForm.pdBillNo !== '') {
        // 原始字符串
        const originalString = this.searchForm.pdBillNo
        // 截取最后5位
        const lastFiveChars = originalString.slice(-5)
        // 将截取到的字符串转换为整数
        let number = parseInt(lastFiveChars, 10)
        // 对整数加1
        number -= 1
        const newLastFiveChars = number.toString().padStart(5, '0')
        // 拼接回原始字符串的前部分
        this.searchForm.pdBillNo = originalString.slice(0, -5) + newLastFiveChars
        this.searchList()
      }
    },
    Changeselect(item) {
      this.searchForm.pdBillNo = item.pdBillNo
    },
    exportDataByExcel(row) {
      this.dialogVisible = true
      this.msg = '已开始导出,请耐心等待'
      exportOtherDataWithExcel(row.methodCode).then(res => {
        console.log('数据导出响应=>', res)
        if (res.size === 0) {
          this.dialogVisible = true
          this.msg = '暂无数据导出'
          return
        }
        var fileName = row.impDataType + '导出清单.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.dialogVisible = false
      }).catch(error => {
        console.error(error)
        this.dialogVisible = true
        this.msg = '导出失败: ' + error
      })
    },
    cellMouse({ row, rowIndex, $rowIndex, column, columnIndex, $columnIndex }) {
      this.rowSize = rowIndex
      // console.log(row, rowIndex)
    },
    handleEnterKey($event) {
      if ($event.$event.$event.keyCode === 13) {
        if (this.rowSize + 1 === this.$refs.table.getData().length) {
          this.xpTabDataWithInput = []
          this.xpTabDataWithInput = this.$refs.table.getData()
          for (var i in this.xpTabDataWithInput) {
            if (this.xpTabDataWithInput[i].pdQty1 === null || this.xpTabDataWithInput[i].pdQty1 === '' || this.xpTabDataWithInput[i].pdQty1 === undefined) {
              this.$message.warning('本票未录入完毕，请核查.')
              return
            }
          }
          if (this.searchForm.pdBillNo.trim().length > 5) {
            var str = this.searchForm.pdBillNo
            var num = parseInt(str.substr(-5)) + 1
            this.searchForm.pdBillNo = str.substring(0, str.length - 5) + num.toString().padStart(5, '0')
            this.searchForm.page.pageNumber = 1
          } else {
            this.searchForm.page.pageNumber += 1
          }
          this.searchList()
          setTimeout(() => {
            const row = this.$refs.table.getData(0)
            this.$refs.table.setSelectCell(row, 'pdQty1')
          }, 1000)
        }
      }
    },
    isNullStr(str) {
      if (str === null || str === '' || str === undefined) {
        return true
      }
      return false
    },
    setSelectCell(row, fieldOrColumn) {
      const table = this.$refs.table
      table.setSelectCell(row, fieldOrColumn)
    },
    cellMouseXPBlank({ row, rowIndex, $rowIndex, column, columnIndex, $columnIndex }) {
      this.rowSize = rowIndex
      this.columnIndex = columnIndex
    },
    handleEnterKeyXpBlank({ $event }) {
      if ($event.$event.keyCode === 13) {
        if (this.rowSize + 1 === this.$refs.tableXpBlank.getData().length) {
          this.xpTabDataWithInput = []
          this.xpTabDataWithInput = this.$refs.tableXpBlank.getData()
          if (this.tableFlag === 'second') {
            for (var i in this.xpTabDataWithInput) {
              console.log('>>>> ', i)
              if (this.checkPass(this.xpTabDataWithInput[i].shelvesNo, this.xpTabDataWithInput[i].modelNo1, this.xpTabDataWithInput[i].pdQty1)) {
                this.$message.error('本票未录入完毕，请核查.')
                return
              }
            }
          }
          if (this.searchForm.pdBillNo.trim().length > 5) {
            var str = this.searchForm.pdBillNo
            var num = parseInt(str.substr(-5)) + 1
            this.searchForm.pdBillNo = str.substring(0, str.length - 5) + num.toString().padStart(5, '0')
            this.searchForm.page.pageNumber = 1
          } else {
            this.searchForm.page.pageNumber += 1
          }
          this.searchList()
          setTimeout(() => {
            const row = this.$refs.tableXpBlank.getData(0)
            this.$refs.tableXpBlank.setSelectCell(row, this.tableFiled)
          }, 1000)
        }
      }
      if ($event.$event.keyCode === 9) {
        if (this.columnIndex === this.curColumIndex) {
          if (this.tableFlag === 'second') {
            const row = this.$refs.tableXpBlank.getData(this.rowSize)
            if (this.checkPass(row.shelvesNo, row.modelNo1, row.pdQty1)) {
              this.$message.error('当前票未录入完毕,请核查')
              var indexFiled = ''
              if (this.isNullStr(row.shelvesNo)) {
                indexFiled = 'shelvesNo'
              } else if (this.isNullStr(row.modelNo1)) {
                indexFiled = 'modelNo1'
              } else if (this.isNullStr(row.pdQty1)) {
                indexFiled = 'pdQty1'
              }
              setTimeout(() => {
                this.$refs.tableXpBlank.setSelectCell(row, indexFiled)
              }, 200)
              return
            }
          }
          const row = this.$refs.tableXpBlank.getData(this.rowSize + 1)
          setTimeout(() => {
            this.$refs.tableXpBlank.setSelectCell(row, this.tableFiled)
          }, 200)
        }
      }
    },
    checkPass(s1, s2, s3) {
      if (!this.isNullStr(s1) && !this.isNullStr(s2) && !this.isNullStr(s3)) {
        return false
      }
      if (this.isNullStr(s1) && this.isNullStr(s2) && this.isNullStr(s3)) {
        return false
      }
      return true
    },
    change() {
      this.searchList()
    },
    clickEnter() {
      this.searchList()
    },
    querySearchAsync(pdBillNo, cb) {
      this.remotePdBillNoVo.warehouseCodes = this.searchForm.warehouseCodes
      this.remotePdBillNoVo.pdBillNo = pdBillNo
      remotePdBillNo(this.remotePdBillNoVo).then(data => {
        var customerArray = []
        var results = []
        if (data.success) {
          customerArray = data.content
          results = customerArray
          cb(results)
        }
      })
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.pdBillNo.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    queryPdBillNoASync(queryString) {
      var pdBillNo = ''
      if (queryString === undefined || queryString === '') {
        pdBillNo = ''
      } else {
        pdBillNo = queryString
      }
      this.selPdBillNo = true
      this.remotePdBillNoVo.warehouseCodes = this.searchForm.warehouseCodes
      this.remotePdBillNoVo.pdBillNo = pdBillNo
      remotePdBillNo(this.remotePdBillNoVo).then(res => {
        this.selPdBillNo = false
        if (res.success) {
          this.pdBillNolist = res.content
        } else {
          this.pdBillNolist = []
        }
      })
    },
    delOtherDataById() {
      this.PdOtherDataDto.dto = this.selOtherDateList
      delOtherDataById(this.PdOtherDataDto).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.searchOtherData()
          this.selOtherDateList = []
        } else {
          this.$message.error(res.message)
        }
      })
    },
    editOtherData(row) {
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      this.PdOtherDataDto.dto = []
      row.updateUser = this.$store.getters.position.psnsmcId
      this.PdOtherDataDto.dto.push(row)
      editOtherData(this.PdOtherDataDto).then(res => {
        console.log('其他数据编辑: ', res)
      })
    },
    sureUpBorrowData() {
      if (this.selBorrowList.length === 0) {
        this.$message.error('请选择需要更新的数据')
      } else {
        this.$message.success('更新成功')
        this.searchBorrowData()
        this.selBorrowList = []
      }
    },
    delBorrowData() {
      this.PdBorrowDto.dto = this.selBorrowList
      delBorrowDataById(this.PdBorrowDto).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.searchBorrowData()
          this.selBorrowList = []
        } else {
          this.$message.error(res.message)
        }
      })
    },
    editBorrowData(row) {
      // if (isNaN(Number(row.qty))) {
      //   this.$message.error('请输入正整数')
      //   const $table = this.$refs.borrowData
      //   $table.clearActived().then(() => {
      //   })
      // }
      if (!this.isPositiveInteger(row.qty)) {
        this.$message.error('请输入正整数')
        const $table = this.$refs.table
        $table.clearActived().then(() => {
        })
      }
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      this.PdBorrowDto.dto = []
      row.updateUser = this.$store.getters.position.psnsmcId
      this.PdBorrowDto.dto.push(row)
      upBorrowInfo(this.PdBorrowDto).then(res => {
        console.log('借库编辑: ', res)
      })
    },
    showImpDialog() {
      this.uploadFileDialogVisible = true
      this.file = null
    },
    importData() {
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      this.uploadLoading = true
      if (this.file == null) {
        this.uploadLoading = false
        this.$message.warning('请先选择文件.')
      } else {
        const formData = new FormData() // form表单格式提交数据
        formData.append('file', this.file)
        formData.append('loginUser', this.$store.getters.position.psnsmcId)
        batchImpBorrowData(formData).then(res => {
          this.uploadLoading = false
          if (res.success) {
            this.$notify({
              title: '成功',
              message: res.content,
              type: 'success'
            })
            this.searchBorrowData()
            this.file = null
            this.uploadFileDialogVisible = false
          } else {
            this.$notify({
              duration: 5000,
              title: '失败',
              message: res.message,
              type: 'error'
            })
          }
        }).catch(error => {
          this.uploadLoading = false
          this.$message.error('操作失败:' + error)
        })
      }
    },
    downLoadExcel() {
      this.$message.success('已开始下载，请耐心等待...')
      this.downLoadExcelBtuLoad = true
      downBorrowExel().then(res => {
        this.downLoadExcelBtuLoad = false
        const fileName = '借库数据导入模板.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
      }).catch(error => {
        this.downLoadExcelBtuLoad = false
        this.$message.warning('导出失败' + error)
      })
    },
    beforeUploadFile(file) {
      this.file = file
      return false
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
    },
    editArriveNotIn(row) {
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      // if (isNaN(Number(row.pdQty1))) {
      //   this.$message.error('请输入正整数')
      //   const $table = this.$refs.tableXPBlank
      //   $table.clearActived().then(() => {
      //   })
      // }
      if (!this.isPositiveInteger(row.pdQty1)) {
        this.$message.error('请输入正整数')
        const $table = this.$refs.table
        $table.clearActived().then(() => {
        })
      }
      this.pdInputDto.dto = []
      row.updateUser = this.$store.getters.position.psnsmcId
      this.pdInputDto.dto.push(row)
      upArriveNotINBlankBillInputInfo(this.pdInputDto).then(res => {
      })
    },
    clearArriveNotInBlankDataQty() {
      if (this.selArriveNotInList.length === 0) {
        this.$message.error('请选择需要操作的数据')
        return
      }
      const msg = '此操作将重置选中 ' + this.selArriveNotInList.length + ' 条数据的型号,货架号,盘点数量, 是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.pdInputDto.dto = this.selArriveNotInList
        clearArriverNotInBlankBillDataQtyById(this.pdInputDto).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.searchList()
            this.selArriveNotInList = []
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    upArriveNotInBlankData() {
      if (this.selArriveNotInList.length === 0) {
        this.$message.error('请选择需要更新的数据')
      } else {
        this.$message.success('更新成功')
        this.searchList()
        this.selArriveNotInList = []
      }
    },
    clearSelDataQty() {
      if (this.selDataList.length === 0) {
        this.$message.error('请选择需要操作的数据')
        return
      }
      const msg = '此操作将重置选中 ' + this.selDataList.length + ' 条数据的盘点数量, 是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.pdInputDto.dto = this.selDataList
        clearPdXpBillDataQtyById(this.pdInputDto).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.searchList()
            this.selDataList = []
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    clearSelBlankDataQty() {
      if (this.selBlankBillData.length === 0) {
        this.$message.error('请选择需要操作的数据')
        return
      }
      const msg = '此操作将重置选中 ' + this.selBlankBillData.length + ' 条数据的型号,货架号,盘点数量, 是否继续?'
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.pdInputDto.dto = this.selBlankBillData
        clearPdXpBlankBillDataQtyById(this.pdInputDto).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.searchList()
            this.selBlankBillData = []
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    editXpBlankBill(row) {
      // if (isNaN(Number(row.pdQty1))) {
      //   this.$message.error('请输入正整数')
      //   const $table = this.$refs.tableXpBlank
      //   $table.clearActived().then(() => {
      //   })
      // }
      if (!this.isPositiveInteger(row.pdQty1)) {
        this.$message.error('请输入正整数')
        const $table = this.$refs.tableXpBlank
        $table.clearActived().then(() => {
        })
      }
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      if (!this.isNullStr(row.modelNo1)) {
        row.modelNo1 = row.modelNo1.toUpperCase().trim()
      }
      if (!this.isNullStr(row.shelvesNo)) {
        row.shelvesNo = row.shelvesNo.toUpperCase().trim()
      }
      this.pdInputDto.dto = []
      row.updateUser = this.$store.getters.position.psnsmcId
      if (this.isPositiveInteger(row.pdQty1) && !this.isNullStr(row.pdQty1)) {
        row.pdQty1 = parseInt(row.pdQty1)
      }
      this.pdInputDto.dto.push(row)
      upXpBlankBillInputInfo(this.pdInputDto).then(res => {
      })
    },
    up() {
      if (this.selDataList.length === 0) {
        this.$message.error('请选择需要更新的数据')
      } else {
        this.$message.success('更新成功')
        this.searchList()
        this.selBlankBillData = []
      }
    },
    upBlankBill() {
      if (this.selBlankBillData.length === 0) {
        this.$message.error('请选择需要更新的数据')
      } else {
        this.$message.success('更新成功')
        this.searchList()
        this.selBlankBillData = []
      }
    },
    selTypeChange() {
      this.remoteMethodByWareHouseCode('')
    },
    isPositiveInteger(str) {
      if (this.isNullStr(str)) {
        return true
      }
      return /^[0-9]\d*$/.test(str)
    },
    edit(row) {
      console.log(this.isPositiveInteger(row.pdQty1), row.pdQty1)
      if (!this.isPositiveInteger(row.pdQty1)) {
        this.$message.error('请输入正整数')
        const $table = this.$refs.table
        $table.clearActived().then(() => {
        })
      }
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      this.pdInputDto.dto = []
      row.updateUser = this.$store.getters.position.psnsmcId
      if (this.isPositiveInteger(row.pdQty1) && !this.isNullStr(row.pdQty1)) {
        row.pdQty1 = parseInt(row.pdQty1)
      }
      this.pdInputDto.dto.push(row)
      updatePdBillDataById(this.pdInputDto).then(res => {
      })
    },
    getDataCodesTreeBywarehouseType() {
      getDictDataByPid(this.warehouseTypeClassCode).then(res => {
        if (res.success) {
          this.warehouseTypeList = res.content
        } else {
          this.warehouseTypeList = []
        }
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
      findWareHouseInfoWithLike(warehouseCode, this.searchForm.warehouseType).then(res => {
        if (res.success) {
          this.wareHouseCodeList = res.content
        } else {
          this.wareHouseCodeList = []
        }
        this.selWareHouseCodeLoading = false
      })
    },
    getPdType() {
      getDictDataByPid(this.pdTypeCode).then(res => {
        if (res.success) {
          this.pdTypeList = res.content
        } else {
          this.pdTypeList = []
        }
      })
    },
    searchList() {
      console.log('this.tabShowName', this.tabShowName)
      if (this.tabShowName === 'first') {
        this.searchXpBillData()
      } else if (this.tabShowName === 'second') {
        this.tableFlag = 'second'
        this.curColumIndex = 6
        this.tableFiled = 'shelvesNo'
        this.searchXpBlankBillData()
      } else if (this.tabShowName === 'third') {
        this.curColumIndex = 8
        this.tableFiled = 'invoiceNo'
        this.searchArriveNotInList()
      } else if (this.tabShowName === 'fourth') {
        this.curColumIndex = 7
        this.tableFiled = 'orderNo'
        this.searchBorrowData()
      } else if (this.tabShowName === 'five') {
        this.searchOtherData()
      }
    },
    searchOtherData() {
      listPdOtherDataImp().then(res => {
        if (res.success) {
          this.otherDataList = res.content
        } else {
          this.otherDataList = []
        }
      })
    },
    searchXpBillData() {
      this.searchForm.pdType = '1'
      this.searchForm.pdDataType = '6'
      listPdBillData(this.searchForm).then(res => {
        if (res.success) {
          this.xpImputDataList = res.content.list
          this.total = res.content.total
        } else {
          this.xpImputDataList = []
          this.total = 0
        }
      })
    },
    searchArriveNotInList() {
      this.searchForm.pdType = '5'
      this.searchForm.pdDataType = '6'
      listPdBillData(this.searchForm).then(res => {
        if (res.success) {
          this.arriveNoEnterInputDataList = res.content.list
          this.total = res.content.total
        } else {
          this.arriveNoEnterInputDataList = []
          this.total = 0
        }
      })
    },
    searchBorrowData() {
      listBorrowDataList(this.searchForm).then(res => {
        if (res.success) {
          this.borrowDataList = res.content.list
          this.total = res.content.total
        } else {
          this.borrowDataList = []
          this.total = 0
        }
      })
    },
    searchXpBlankBillData() {
      this.searchForm.pdType = '4'
      this.searchForm.pdDataType = ''
      listPdBillData(this.searchForm).then(res => {
        if (res.success) {
          this.blankBillImputDataList = res.content.list
          this.total = res.content.total
        } else {
          this.blankBillImputDataList = []
          this.total = 0
        }
      })
    },
    handleClick(tab, event) {
      this.tabShowName = tab.name
      this.searchForm.page.pageNumber = 1
      this.searchList()
    },
    xpSelHand(row) {
      this.selDataList = row.records
    },
    arriveNoEnterTableHand(row) {
      this.selArriveNotInList = row.records
    },
    blankBillTableHand(row) {
      this.selBlankBillData = row.records
    },
    offlineTableHand(row) {
      this.selBorrowList = row.records
    },
    exportData(row) {
      var msg = ''
      if (row.execFlag === '1') {
        msg = '该作业已执行过,是否选择重新执行, 是否继续?'
      } else {
        msg = '请确认是否执行, 是否继续?'
      }
      this.$confirm(msg, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        closeOnClickModal: false,
        type: 'warning'
      }).then(() => {
        this.$message.warning('开始执行,请耐心等候 请注意观察执行结果...')
        exportOtherData(row.methodCode).then(res => {
          if (res.success) {
            this.$message.success(res.content)
            this.searchOtherData()
          } else {
            this.$message.error(res.message)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    selectOtherChangeEvent(row) {
      this.selOtherDateList = row.records
    },
    clear() {
      this.searchForm.pdType = ''
      this.searchForm.warehouseType = ''
      this.searchForm.warehouseCodes = []
      this.searchForm.pdBillNo = ''
      this.searchForm.modelNo = ''
      this.searchForm.pdInputort = ''
      this.searchForm.pdDataType = ''
    }
  }
}
</script>
<style scoped>
.header{
  margin: 10px;
}
.el-icon-delete {
  transition: all 0.3s
}
.el-icon-delete:hover {
   transform: scale(1.3)
}
.tab {
  margin-top: -25px;
}
</style>
