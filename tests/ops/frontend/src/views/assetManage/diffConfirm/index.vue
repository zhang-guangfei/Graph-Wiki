<template>
  <div class="container">
    <div class="header">
      <el-form :inline="true" :model="searchForm" size="mini" class="demo-form-inline">
        <el-form-item>
          <el-select v-model="searchForm.pdBillType" clearable placeholder="票据类型">
            <el-option
              v-for="item in pdBillTypeList"
              :key="item.code"
              :label="item.codeName"
              :value="item.code"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-select
            v-model="searchForm.diffTypes"
            clearable
            multiple
            collapse-tags
            placeholder="盘点差异类型">
            <el-option
              v-for="item in diffTypeList"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
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
        <!-- <el-form-item>
          <el-input v-model="searchForm.pdBillNo" clearable placeholder="请输入盘点票号" />
        </el-form-item> -->
        <el-form-item>
          <el-select
            v-model="searchForm.pdBillNo"
            :remote-method="queryPdBillNoASync"
            :loading="selPdBillNo"
            filterable
            remote
            clearable
            placeholder="请输入盘点票号"
            style="width: 220px"
            @keyup.enter.native="clickEnter()">
            <el-option
              v-for="item in pdBillNolist"
              :key="item.pdBillNo"
              :label="item.pdBillNo"
              :value="item.pdBillNo"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-input v-model="searchForm.modelNo" clearable placeholder="请输入型号" />
        </el-form-item>
        <el-form-item>
          <i class="el-icon-delete" style="margin-left: 30px;font-weight: 600;" @click="clear()" />
          <el-button type="primary" style="margin-left: 20px;" @click="searchList()">查询</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-button-group>
        <el-button :loading="suerAgainInputBtu" size="mini" type="primary" @click="suerAgainInput()">第一次录入完毕</el-button>
        <!-- <el-button :loading="surePdDataDiffBtu" size="mini" type="primary" @click="surePdDataDiff()">进入盘点差异确认中</el-button> -->
        <el-button :loading="exportLoadBtu" size="mini" type="primary" @click="exportData()">查询数据导出</el-button>
        <!-- <el-button size="mini" type="primary">编辑</el-button>
        <el-button size="mini" type="primary">确认</el-button> -->
        <el-button disabled size="mini" type="primary" @click="delDiffData()">删除</el-button>
      </el-button-group>
      <vxe-table
        :data="diffConfirmTableList"
        :edit-config="{trigger: 'dblclick', mode: 'cell'}"
        :keyboard-config="{isArrow: true,isEnter: true,isEdit: true}"
        :mouse-config="{selected: true}"
        :column-config="{resizable: true}"
        auto-resize
        height="600"
        border
        size="mini"
        @checkbox-change="diffConfirmHand"
        @checkbox-all="diffConfirmHand"
      >
        <vxe-table-column type="checkbox" width="40"/>
        <vxe-table-column field="id" title="ID" width="80"/>
        <vxe-table-column field="pdBatchNo" title="盘点批次号" min-width="100" show-overflow/>
        <vxe-table-column field="pdBillNo" title="盘点票号" min-width="130" show-overflow />
        <vxe-table-column field="pdBillType" title="票据类型" min-width="110" show-overflow/>
        <!-- <vxe-table-column field="shelvesNo" title="货架号" min-width="80" show-overflow/> -->
        <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="shelvesNo" title="货架号" min-width="80" show-overflow >
          <template #edit="{ row }">
            <vxe-input v-model="row.shelvesNo" type="text" @change="editPdBillInfo(row)" />
          </template>
        </vxe-table-column>
        <vxe-table-column field="locationNo" title="货位号" min-width="80" show-overflow/>
        <vxe-table-column field="invoiceNo" title="发票号" min-width="100" show-overflow/>
        <vxe-table-column field="caseNo" title="拍号" min-width="80" show-overflow/>
        <vxe-table-column field="barcode" title="Barcode" min-width="70" show-overflow/>
        <vxe-table-column field="billQty" title="账簿数量" min-width="80" show-overflow/>
        <vxe-table-column field="modelNo1" title="第一次盘点型号" min-width="160" show-overflow/>
        <vxe-table-column field="pdQty1" title="第一次盘点数量" min-width="70" show-overflow/>
        <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="modelNo2" title="第二次盘点型号" min-width="160" show-overflow >
          <template #edit="{ row }">
            <vxe-input v-model="row.modelNo2" type="text" @change="editPdBillInfo(row)" />
          </template>
        </vxe-table-column>
        <vxe-table-column :edit-render="{autofocus: '.vxe-input--inner'}" field="pdQty2" title="第二次盘点数量" min-width="80" show-overflow >
          <template #edit="{ row }">
            <vxe-input v-model="row.pdQty2" type="text" @change="editPdBillInfo(row)" />
          </template>
        </vxe-table-column>
        <vxe-table-column field="pdInputTime1UI" title="第一次录入时间" min-width="140" show-overflow/>
        <vxe-table-column field="pdInputTime2UI" title="第二次录入时间" min-width="140" show-overflow/>
        <vxe-table-column field="pdInputort1" title="第一次录入人" min-width="110" show-overflow/>
        <vxe-table-column field="pdInputort2" title="第二次录入人" min-width="110" show-overflow/>
      </vxe-table>
      <pagination
        v-show="total > 10"
        :total="total"
        :page-sizes="[10, 20, 100, 150, 200]"
        :page.sync="searchForm.page.pageNumber"
        :limit.sync="searchForm.page.pageSize"
        @pagination="searchList()"/>
    </div>
    <el-dialog
      :visible.sync="dialogVisible"
      :close-on-click-modal="false"
      title="提示"
      width="30%"
      height="200px">
      <span><h2>{{ msg }}</h2></span>
      <!-- <span slot="footer" class="dialog-footer">
        <el-button size="mini" @click="dialogVisible = false">取 消</el-button>
        <el-button size="mini" type="primary" @click="dialogVisible = false">确 定</el-button>
      </span> -->
    </el-dialog>
  </div>
</template>
<script>
import { getDictDataByPid, findWareHouseInfoWithLike } from '@/api/common/dict'
import { findPdBillDataListWithDiff, exportPdBillDiffFindDataList, updateDiffPdBillDataById, delDiffPdBillDataById, suerAgainInput, remotePdBillNo, surePdDataDiff } from '@/api/pd/pd'
import Pagination from '@/components/Pagination'
export default {
  name: 'PdDiffConfirm',
  components: { Pagination },
  data() {
    return {
      searchForm: {
        pdBillType: '',
        warehouseCodes: [],
        warehouseCode: '',
        warehouseType: 'MASTER',
        pdBillNo: '',
        modelNo: '',
        diffTypes: [],
        page: {
          pageNumber: 1,
          pageSize: 20
        }
      },
      pdInputDto: {
        dto: []
      },
      total: 0,
      pdBillTypeCode: '5003',
      warehouseTypeClassCode: '4001',
      selDiffData: [],
      pdBillNolist: [],
      selPdBillNo: false,
      wareHouseCodeList: [],
      diffConfirmTableList: [],
      warehouseTypeList: [],
      pdBillTypeList: [],
      dialogVisible: false,
      msg: '',
      exportLoadBtu: false,
      selWareHouseCodeLoading: false,
      suerAgainInputBtu: false,
      surePdDataDiffBtu: false,
      remotePdBillNoVo: {
        warehouseCodes: [],
        pdBillNo: ''
      },
      diffTypeList: [
        {
          value: '1',
          label: '型号差异'
        },
        {
          value: '2',
          label: '数量差异'
        }
      ]
    }
  },
  mounted() {
    this.getPdType()
    this.getDataCodesTreeBywarehouseType()
    this.searchList()
    this.remoteMethodByWareHouseCode('')
    this.queryPdBillNoASync('')
  },
  methods: {
    surePdDataDiff() {
      surePdDataDiff(this.$store.getters.position.psnsmcId).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        }
      })
    },
    queryPdBillNoASync(queryString) {
      var pdBillNo = ''
      if (queryString === undefined) {
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
    editPdBillInfo(row) {
      console.log(row)
      if (isNaN(Number(row.pdQty2))) {
        this.$message.error('请输入正整数.')
        return
      }
      this.pdInputDto.dto = []
      if (this.$store.getters.position.psnsmcId === undefined) {
        this.$message.warning('当前登录信息已过期,请退出重新登录')
        return
      }
      row.updateUser = this.$store.getters.position.psnsmcId
      row.pdQty2 = parseInt(row.pdQty2)
      this.pdInputDto.dto.push(row)
      updateDiffPdBillDataById(this.pdInputDto).then(res => {
      })
    },
    delDiffData() {
      if (this.selDiffData.length === 0) {
        this.$message.error('请选择需要操作的数据')
        return
      }
      this.pdInputDto.dto = this.selDiffData
      delDiffPdBillDataById(this.pdInputDto).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.searchList()
        } else {
          this.$message.error(res.message)
        }
      })
    },
    exportData() {
      // this.$message.success('已开始导出，请耐心等待')
      this.exportLoadBtu = true
      this.dialogVisible = true
      this.msg = '已开始导出,请耐心等待'
      exportPdBillDiffFindDataList(this.searchForm).then(res => {
        if (res.size === 0) {
          this.exportLoadBtu = false
          this.dialogVisible = true
          this.msg = '暂无数据导出'
          return
        }
        var fileName = '盘点差异调整数据.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportLoadBtu = false
        this.dialogVisible = false
        this.searchList()
      }).catch(error => {
        console.error(error)
        this.exportLoadBtu = false
        this.dialogVisible = true
        this.msg = '导出失败: ' + error
      })
    },
    suerAgainInput() {
      if (this.searchForm.warehouseCodes !== null && this.searchForm.warehouseCodes.length > 1) {
        this.$message.warning('只能选择一个仓库代码')
        return
      }
      this.$message.success('已开始确认二次录入，请耐心等待')
      this.suerAgainInputBtu = true
      this.searchForm.warehouseCode = this.searchForm.warehouseCodes[0]
      console.log('this.searchForm=>', this.searchForm)
      if (this.searchForm.warehouseCode === undefined) {
        this.searchForm.warehouseCode = ''
      }
      suerAgainInput(this.searchForm.warehouseCode).then(res => {
        if (res.success) {
          this.$message.success(res.content)
          this.searchList()
        } else {
          this.$message.error(res.message)
        }
        this.suerAgainInputBtu = false
      }).catch(error => {
        console.error(error)
        this.suerAgainInputBtu = false
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
    selTypeChange() {
      this.remoteMethodByWareHouseCode('')
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
    diffConfirmHand(row) {
      this.selDiffData = row.records
    },
    getPdType() {
      getDictDataByPid(this.pdBillTypeCode).then(res => {
        if (res.success) {
          this.pdBillTypeList = res.content
        } else {
          this.pdBillTypeList = []
        }
      })
    },
    searchList() {
      findPdBillDataListWithDiff(this.searchForm).then(res => {
        console.log('查询: ', res)
        if (res.success) {
          this.diffConfirmTableList = res.content.list
          this.total = res.content.total
        } else {
          this.diffConfirmTableList = []
          this.total = 0
        }
      })
    },
    clear() {
      this.searchForm.pdBillType = ''
      this.searchForm.warehouseCodes = []
      this.searchForm.warehouseType = ''
      this.searchForm.pdBillNo = ''
      this.searchForm.modelNo = ''
      this.searchForm.diffType = ''
    }
  }
}
</script>
<style scoped>
.header{
  margin: 10px;
}
 .el-table /deep/ .el-table__body-wrapper{
    z-index: 2
}
.el-icon-delete {
  transition: all 0.3s
}
.el-icon-delete:hover {
   transform: scale(1.3)
}
</style>
