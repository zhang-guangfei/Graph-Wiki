<template>
  <div class="maindiv">
    <el-row class="btu">
      <el-button size="mini" plain icon="el-icon-refresh" style="margin-left: 2%" @click="searchData()">刷新</el-button>
    </el-row>

    <div class="searchInput">
      <el-form ref="searchRequest" :inline="true" :model="searchRequest" size="mini" class="demo-form-inline">
        <el-form-item prop="applyNo">
          <el-input v-model="searchRequest.applyNo" clearable class="in" placeholder="请输入申请号" />
        </el-form-item>
        <el-form-item prop="orderNo">
          <el-input v-model="searchRequest.orderNo" clearable class="in" placeholder="请输入订单号" />
        </el-form-item>
        <el-select v-model="searchRequest.status" clearable placeholder="退货状态" multiple collapse-tags size="mini" class="in" @change="changeStateCode">
          <el-option
            v-for="item in statusDataList"
            :key="item.code"
            :label="item.codeName"
            :value="item.code"/>
        </el-select>
        <el-form-item>
          <el-button class="search_btu" type="primary" @click="searchData()">查询</el-button>
          <!--add by LiYingChao from 20221101 bugId 8523-->
          <el-popconfirm
            confirm-button-text="仅拒收单项"
            cancel-button-text="全部拒收"
            title="是否整单拒收?"
            @confirm="cancel()"
            @cancel="confirm()">
            <el-button slot="reference" :loading="refuseBtu" type="danger" >包裹拒收</el-button>
          </el-popconfirm>
          <el-button type="danger" @click="cancelReturnOrder()">取消退货</el-button>
          <el-button :loading="exportLoading" class="search_btu" type="primary" @click="exportReturnOrder()">导出</el-button>
          <!-- <el-button :loading="loadingPrint" class="search_btu" type="primary" @click="printReturnOrder()">打印</el-button> -->
        </el-form-item>
        <el-form-item>
          <el-button type="primary" size="mini" icon="el-icon-more" @click="searchMoreData" />
        </el-form-item>
      </el-form>
      <el-form :model="rcvReGisterForm" label-position="right" label-width="80px" size="mini" style="margin-left:80px">
        <el-form-item label="收货仓库" class="searchWareHouse">
          <el-select ref="wareHouseList" v-model="rcvReGisterForm.warehouseCode" style="width: 130px" clearable filterable placeholder="请选择/搜索" @change="handleChange">
            <el-option
              v-for="item in wareHouseOptions"
              :key="item.warehouseCode"
              :label="item.warehouseName"
              :value="item.warehouseCode"
            />
          </el-select>
        </el-form-item>
      </el-form>
      <el-dialog :visible.sync="addOrderDialog" :close-on-click-modal="false" title="新增退货" width="650px" >
        <el-form ref="addOrderForm" :model="addOrderForm" :rules="addFormRule" size="mini">
          <el-row type="flex">
            <el-form-item label-width="130px" prop="applyNo" label="申请号">
              <el-input v-model="addOrderForm.applyNo" style="width:150px"/>
              <el-button type="primary" size="mini" @click="creatApplyNo">
                生成
              </el-button>
            </el-form-item>
            <el-form-item label-width="70px" prop="itemNo" label="项号">
              <el-input v-model="addOrderForm.itemNo" style="width:100px" />
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="employee" label="录入担当">
              <div style="font-weight:bold;width:150px;font-size:14px">{{ "["+addOrderForm.employeeNo+"]"+addOrderForm.employee }}</div>
            </el-form-item>
            <el-form-item label-width="130px" prop="applyTime" label="录入时间">
              <div style="font-weight:bold;width:150px;font-size:14px">{{ addOrderForm.applyTime }}</div>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="applicant" label="申请担当">
              <el-input v-model="addOrderForm.applicant" style="width:150px" />
            </el-form-item>
            <el-form-item label-width="130px" prop="reason" label="寄回目的">
              <el-select v-model="addOrderForm.reason" style="width: 150px" clearable placeholder="请选择">
                <el-option
                  v-for="item in reasonData"
                  :key="item.codeName"
                  :label="item.codeName"
                  :value="item.codeName"
                />
              </el-select>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="orderNo" label="订单号">
              <el-input v-model="addOrderForm.orderNo" style="width:150px" @keyup.enter.native="findDetailByOrder"/>
            </el-form-item>
            <el-form-item label-width="130px" prop="applyQty" label="申请数量">
              <el-input v-model="addOrderForm.applyQty" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="modelNo" label="型号">
              <el-input :disabled="true" v-model="addOrderForm.modelNo" style="width:150px"/>
            </el-form-item>
            <el-form-item label-width="130px" prop="orderQty" label="下单数量">
              <el-input :disabled="true" v-model="addOrderForm.orderQty" style="width:150px"/>
            </el-form-item>
          </el-row>
          <!-- <el-row type="flex">
            <el-form-item label-width="130px" prop="expStockCode" label="出库区分">
              <el-input :disabled="true" v-model="addOrderForm.expStockCode" style="width:150px"/>
            </el-form-item>
            <el-form-item label-width="130px" prop="price" label="单价">
              <el-input :disabled="true" v-model="addOrderForm.price" style="width:150px"/>
            </el-form-item>
          </el-row> -->
          <el-row type="flex">
            <el-form-item label-width="130px" prop="customerNo" label="客户代码">
              <!-- <el-input v-model="addOrderForm.customerNo" style="width:150px"/> -->
              <el-autocomplete
                v-model="addOrderForm.customerNo"
                :fetch-suggestions="querySearchAsync"
                :debounce="0"
                :popper-append-to-body="false"
                popper-class="el-autocomplete-suggestion"
                highlight-first-item
                type="text"
                size="mini"
                style="width: 150px"
                placeholder="客户代码"
                class="select"
                @select="Changeselect">
                <template slot-scope="{ item }">
                  <div class="name">{{ item.customerNo + ',' + item.name }}</div>
                </template>
              </el-autocomplete>
            </el-form-item>
            <el-form-item label-width="130px" prop="customerName" label="客户名称">
              <el-input v-model="addOrderForm.customerName" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="userNo" label="用户代码">
              <!-- <el-input v-model="addOrderForm.userName" style="width:150px"/> -->
              <el-autocomplete
                v-model="addOrderForm.userNo"
                :fetch-suggestions="querySearchAsync"
                :debounce="0"
                :popper-append-to-body="false"
                popper-class="el-autocomplete-suggestion"
                highlight-first-item
                type="text"
                size="mini"
                style="width: 150px"
                class="select"
                placeholder="用户代码"
                @select="ChangeUserNo">
                <template slot-scope="{ item }">
                  <div class="name">{{ item.customerNo + ',' + item.name }}</div>
                </template>
              </el-autocomplete>
            </el-form-item>
            <el-form-item label-width="130px" prop="userName" label="用户名称">
              <el-input v-model="addOrderForm.userName" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="invoiceNo" label="发票号">
              <el-input v-model="addOrderForm.invoiceNo" style="width:150px"/>
              <el-button type="text" @click="cleanInvoice">清除</el-button><br>
              <span style="color:red;font-size:12px;margin-left: -40px;">如果不需要退票,请去掉发票号和开票日期</span>
            </el-form-item>
            <!-- <div><span>111</span></div> -->
            <el-form-item label-width="100px" prop="invoiceDate" label="开票日期">
              <el-date-picker
                v-model="addOrderForm.invoiceDate"
                type="date"
                style="width:150px"
                placeholder="选择日期"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="toUserStock" label="退回用户专备">
              <el-select v-model="addOrderForm.toUserStock" style="width: 150px" clearable placeholder="请选择">
                <el-option
                  v-for="item in userStockData"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label-width="130px" prop="feeRate" label="退货手续费率">
              <el-input v-model="addOrderForm.feeRate" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="warehouseCode" label="收货仓库">
              <!-- <el-input v-model="addOrderForm.warehouseCode" style="width:150px"/> -->
              <el-select v-model="addOrderForm.warehouseCode" style="width: 150px" clearable placeholder="请选择/搜索">
                <el-option
                  v-for="item in wareHouseOptions"
                  :key="item.warehouseCode"
                  :label="item.warehouseName"
                  :value="item.warehouseCode"
                />
              </el-select>
            </el-form-item>
            <el-form-item label-width="130px" prop="file" label="附件">
              <el-upload
                ref="upload"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :auto-upload="false"
                :before-upload="beforeUploadFile"
                :on-change="handdleChange"
                class="upload-demo"
                action="">
                <el-button slot="trigger" size="mini" type="primary">选取文件</el-button>
                <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
              </el-upload>
            </el-form-item>
          </el-row>
          <!-- <el-row type="flex">
            <el-form-item label-width="130px" prop="deptNo" label="营业所代码">
              <el-input v-model="addOrderForm.deptNo" style="width:150px"/>
            </el-form-item>
            <el-form-item label-width="130px" prop="deptName" label="营业所名称">
              <el-input v-model="addOrderForm.deptName" style="width:150px"/>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item label-width="130px" prop="returnType" label="退货类别">
              <el-select v-model="addOrderForm.returnType" style="width: 150px" clearable placeholder="请选择">
                <el-option
                  v-for="item in returnTypeData"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label-width="130px" prop="dutyType" label="退货责任">
              <el-select v-model="addOrderForm.dutyType" style="width: 150px" clearable placeholder="请选择">
                <el-option
                  v-for="item in dutyTypeData"
                  :key="item.code"
                  :label="item.codeName"
                  :value="item.code"
                />
              </el-select>
            </el-form-item>
          </el-row> -->
          <el-form-item label-width="130px" prop="remark" label="备注">
            <el-input v-model="addOrderForm.remark" type="textarea" autosize style="width:430px"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="addOrderDialog = false">取 消</el-button>
          <el-button :loading="applyLoading" type="primary" @click="insertReturnOrder()">确认</el-button>
        </div>
      </el-dialog>
    </div>
    <div v-if="searchMoreForm" class="search">
      <el-form ref="form" :inline="true" :model="searchRequest" size="mini" clearable class="demo-form-inline">
        <el-form-item label="型号:">
          <el-input v-model="searchRequest.modelNo" style="width: 160px" placeholder="型号" clearable=""/>
        </el-form-item>
        <el-form-item class="day">
          <el-select v-model="daySelectVal" style="width: 120px" clearable placeholder="请选择:">
            <el-option label="申请时间" value="applyTime"/>
            <el-option label="收货时间" value="receiveTime"/>
            <el-option label="入库时间" value="inTime"/>
          </el-select>
          <el-date-picker
            v-model="selectDate"
            :picker-options="pickerOptions"
            type="daterange"
            align="right"
            unlink-panels
            style="width: 250px"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-form-item>
        <el-button class="search_btu" type="info" size="mini" @click="reset()">重置</el-button>
        <el-button type="success" size="mini" @click="addReturnOrder()">新增退货</el-button>
      </el-form>
    </div>
    <div>
      <hr :style="cssForHr" class="h" >
    </div>
    <div class="tab">
      <div class="showcolumClass">
        <vxe-toolbar id="toolbar_demo3" ref="xToolbar1" custom/>
      </div>
      <vxe-table
        id="toolbar_demo3"
        ref="multipleTable"
        :custom-config="{storage: true, checkMethod: checkColumnMethod}"
        :data="tableData"
        :cell-style="cellStyle"
        :style="tableCss"
        :seq-config="{startIndex: (page.pageNumber - 1) * page.pageSize}"
        size="mini"
        resizable
        border
        height="595"
        width="100%"
        @checkbox-all="selectAllChangeEvent"
        @checkbox-change="selectChangeEvent"
        @resizable-change="resizableChangeEvent"
        @custom="customColums"
        @cell-click="cellClick">
        <vxe-table-column type="seq" width="50" />
        <vxe-table-column type="checkbox" width="40" />
        <vxe-column field="applyNo" title="申请号" width="115" show-overflow="ellipsis"/>
        <vxe-table-column field="itemNo" title="项号" width="50" show-overflow="ellipsis" />
        <vxe-table-column field="orderNo" title="订单号" width="110" show-overflow="ellipsis" />
        <vxe-table-column field="statusName" title="状态" width="90" show-overflow="ellipsis" />
        <vxe-table-column field="modelNo" title="型号" width="150" show-overflow="ellipsis" >
          <!-- <template v-slot="{ row }">
            <el-link :underline="false" @click="handleClick(row)">{{ row.modelNo }}</el-link>
          </template> -->
        </vxe-table-column>
        <vxe-table-column field="orderQty" title="下单数量" width="70" show-overflow="ellipsis" />
        <vxe-table-column field="applyQty" title="申请数量" width="70" show-overflow="ellipsis" />
        <vxe-table-column field="applyBadqty" title="申请不良品数量" width="105" show-overflow="ellipsis" />
        <vxe-table-column field="rcvFineqty" title="收到数量" width="90" show-overflow="ellipsis" />
        <vxe-table-column field="returnQty" title="退货入库数量" width="100" show-overflow="ellipsis" />
        <vxe-table-column field="userNo" title="用户" width="130" show-overflow="ellipsis" />
        <vxe-table-column field="customerNo" title="客户" width="130" show-overflow="ellipsis" />
        <vxe-table-column field="employee" title="客户担当" width="100" show-overflow="ellipsis" />
        <vxe-table-column field="expStockCode" title="出库区分" width="100" show-overflow="ellipsis" />
        <vxe-table-column field="requestWarehouseCodeName" title="要求退货仓库" width="140" show-overflow="ellipsis"/>
        <vxe-table-column field="rcvWarehouseCodeName" title="实际退货仓库" width="140" show-overflow="ellipsis"/>
        <vxe-table-column field="expStocktype" title="出库类别" width="100" show-overflow="ellipsis"/>
        <vxe-table-column field="salesPrice" title="销售单价" width="70" show-overflow="ellipsis"/>
        <vxe-table-column field="feeRate" title="手续费率" width="90" show-overflow="ellipsis"/>
        <vxe-table-column field="returnFee" title="手续费" width="100" show-overflow="ellipsis"/>
        <vxe-table-column field="invoiceNo" title="发票号" width="90" show-overflow="ellipsis"/>
        <vxe-table-column field="expressNo" title="快递单号" width="140" show-overflow="ellipsis"/>
        <vxe-table-column field="invoiceDateStr" title="开票日期" width="120" show-overflow="ellipsis"/>
        <vxe-table-column field="rcvBadqty" title="收到坏品数量" width="100" show-overflow="ellipsis"/>
        <vxe-table-column field="applyTimeStr" title="申请时间" width="120" show-overflow="ellipsis"/>
        <vxe-table-column field="applicant" title="申请人" width="100" show-overflow="ellipsis"/>
        <vxe-table-column field="applicantTel" title="申请人电话" width="100" show-overflow="ellipsis"/>
        <vxe-table-column field="applicantEmail" title="申请人邮箱" width="100" show-overflow="ellipsis"/>
        <vxe-table-column field="locationNo" title="货架号" width="80" show-overflow="ellipsis"/>
        <vxe-table-column field="toUserStockName" title="退回用户专备" width="97" show-overflow="ellipsis"/>
        <vxe-table-column field="inTimeStr" title="入库时间" width="120" show-overflow="ellipsis"/>
        <vxe-table-column field="receiveTimeStr" title="收货时间" width="140" show-overflow="ellipsis"/>
        <vxe-table-column field="deptName" title="部门" width="100" show-overflow="ellipsis"/>
        <vxe-table-column field="consignee" title="收货人" width="110" show-overflow="ellipsis"/>
        <vxe-table-column field="reason" title="退货原因" width="110" show-overflow="ellipsis"/>
        <vxe-table-column field="returnType" title="退货类别" width="110" show-overflow="ellipsis"/>
        <vxe-table-column field="proDangerLevel" title="产品风险级别" width="110" show-overflow="ellipsis"/>
        <vxe-table-column field="fileName" title="附件" width="110" show-overflow="ellipsis" >
          <template v-slot="{ row }">
            <el-button v-if="existFile(row.fileName)" plain type="primary" size="mini" @click="downLoadFile(row.fileRealName, row.applyNo)">下载附件</el-button>
          </template>
        </vxe-table-column>
      </vxe-table>
    </div>
    <div v-show="showReceiving" class="rcvReGisterCss">
      <el-form :model="rcvReGisterForm" label-position="right" label-width="80px" size="mini" >
        <el-form-item label="收货日期">
          <el-date-picker
            v-model="rcvReGisterForm.dlvDate"
            type="date"
            style="width: 130px"
            placeholder="选择日期" />
        </el-form-item>
        <el-form-item label="申请号">
          <el-input v-model="rcvReGisterForm.applyNo" style="width: 140px" disabled placeholder="申请号" clearable/>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="rcvReGisterForm.isRcvTogether" label="整单接收" border size="mini" /><br>
          <span style="color: red">(整单收取必须保证收到的项数[良品数]必须和申请数完全一致)</span>
        </el-form-item>
        <el-form-item>
          <el-checkbox v-model="rcvReGisterForm.isAssOrder" label="组装订单" border size="mini" disabled />
        </el-form-item>
        <el-form-item label="订单号">
          <el-input v-model="rcvReGisterForm.orderNo" style="width: 130px" disabled placeholder="订单号" />
        </el-form-item>
        <el-form-item label="型号">
          <el-input v-model="rcvReGisterForm.modelNo" style="width: 130px" disabled placeholder="型号" />
        </el-form-item>
        <el-form-item label="临时货架">
          <el-input v-model="rcvReGisterForm.locationNo" class="inputs" style="width: 120px" placeholder="临时货架号" />
        </el-form-item>
        <el-form-item label="申请数量">
          <el-input v-model="rcvReGisterForm.applyNumber" class="inputs" disabled placeholder="申请数量" />
        </el-form-item>
        <el-form-item label="良品数量">
          <el-input :disabled="rcvReGisterForm.isRcvTogether" v-model="rcvReGisterForm.goodNumber" class="inputs" placeholder="良品数量" />
        </el-form-item>
        <el-form-item label="坏品数量">
          <el-input :disabled="rcvReGisterForm.isRcvTogether" v-model="rcvReGisterForm.badNumber" class="inputs" placeholder="坏品数量" />
        </el-form-item>
        <el-form-item label="快递单号">
          <el-input v-model="rcvReGisterForm.expressNo" style="width: 150px" placeholder="快递单号(扫描快递单)" />
        </el-form-item>
        <el-form-item label="收货仓库">
          <el-input v-model="rcvReGisterForm.requestWarehouseCodeName" disabled style="width: 150px" />
        </el-form-item>
        <el-form-item>
          <el-button :loading="rcvReGisterLoading" type="primary" size="mini" @click="submitForm('rcvReGisterForm')">确定收货</el-button>
        </el-form-item>
      </el-form>
    </div>
    <pagination
      v-show="total>0"
      :total="total"
      :page-sizes="[20, 50, 100, 200, 500]"
      :page.sync="page.pageNumber"
      :limit.sync="page.pageSize"
      style="margin-right: 50px"
      @pagination="fetchList()"
    />
    <el-dialog :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
      <product-search v-if="productVisible" ref="ProductSearch"/>
    </el-dialog>
    <el-dialog :visible.sync="dialogFormVisible" :close-on-click-modal="false" title="修改手续费">
      <el-form ref="handlingFeeForm" :model="handlingFeeForm" :rules="rules">
        <el-form-item label-width="300px" prop="orderNo" label="订单号">
          <el-input v-model="handlingFeeForm.orderNo" style="width:200px" autocomplete="off"/>
        </el-form-item>
        <el-form-item label-width="300px" prop="salesPrice" label="单价">
          <el-input v-model="handlingFeeForm.salesPrice" style="width:200px" autocomplete="off"/>
        </el-form-item>
        <el-form-item label-width="300px" prop="returnQty" label="退货数量">
          <el-input v-model="handlingFeeForm.returnQty" style="width:200px" autocomplete="off"/>
        </el-form-item>
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label-width="300px" prop="feeRate" label="比例">
              <el-input v-model="handlingFeeForm.feeRate" style="width:200px" autocomplete="off"/>
            </el-form-item>
          </el-col>
          <el-col :span="6" style="padding-left: 290px;">
            <el-button size="mini" type="primary">计算</el-button>
          </el-col>
        </el-row>
        <el-form-item label-width="300px" label="手续费">
          <el-input v-model="handlingFeeForm.returnFee" style="width:200px" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false;saveData('handlingFeeForm')">保存</el-button>
      </div>
    </el-dialog>

    <!-- 取消退货 -->
    <el-dialog :visible.sync="cancelDialog" :close-on-click-modal="false" width="420px" title="取消退货">
      <el-form ref="cancelForm" :model="cancelForm" :rules="cancelRule">
        <span class="delOrder">{{ "正在执行订单"+cancelOrderNo+"的删除操作" }}</span>
        <el-form-item prop="reason" label="取消原因">
          <el-input v-model="cancelForm.reason" type="textarea" autosize style="width:300px" size="mini" autocomplete="off"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button size="samll" @click="cancelDialog = false">取 消</el-button>
        <el-button size="samll" type="primary" @click="cancelOrder()">确认</el-button>
      </div>
    </el-dialog>

  </div>
</template>
<script>
import Pagination from '@/components/Pagination'
import { fetchList as queryData, backOrder, printReturnOrder, rcvOrderReGister, refuseRcv, exportReturnOrder, cancelReturnOrder, creatApplyNo, addReturnOrder, getWareHouseByCustomerNo, downloadFile, saveTableToRedis, getTableFromRedis, getAllTableFromRedis } from '@/api/order/returnorder'
import { shortcuts } from '@/utils/dataFormat'
import { getRcvOrderDataByNo } from '@/api/order/rcvorder'
import { getDictDataByPid, listWarehouseNoWT } from '@/api/common/dict'
// import { listDepartment } from '@/api/common/department'
import { mapGetters } from 'vuex'
import ProductSearch from '../../product/index'
import { getCustomerByNo } from '@/api/common/customer'
export default {
  name: 'ReturnOrderProcess',
  components: { Pagination, ProductSearch },
  data() {
    return {
      searchRequest: {
        applyNo: '', // 申请号
        orderNo: '', // 订单号
        modelNo: '',
        applyTimeStart: '',
        applyTimeEnd: '',
        receiveTimeStart: '',
        receiveTimeEnd: '',
        inTimeStart: '',
        inTimeEnd: '',
        status: ['3'],
        requestWarehouseCode: ''
      },
      refuseRcvRequest: {
        ids: [],
        allRefuseRcv: 0 // 0 不整单拒收 1 整单拒收
      },
      refuseBtu: false,
      addOrderDialog: false,
      applyLoading: false,
      addOrderForm: {
        applyNo: '',
        itemNo: '',
        orderNo: '',
        applyQty: '',
        modelNo: '',
        orderQty: '',
        expStockCode: '',
        price: '',
        warehouseCode: '',
        toUserStock: '',
        feeRate: '',
        customerNo: '',
        userNo: '',
        deptNo: '',
        customerName: '',
        userName: '',
        deptName: '',
        invoiceNo: '',
        invoiceDate: '',
        returnType: '',
        dutyType: '',
        remark: '',
        employee: '',
        employeeNo: '',
        applyTime: '',
        reason: '',
        applicant: ''
      },
      fileList: [],
      formData: new FormData(),
      addFormRule: {
        applyNo: [
          { required: true, message: '请填写申请号', trigger: 'change' }
        ],
        orderNo: [
          { required: true, message: '请填写订单号', trigger: 'blur' }
        ],
        modelNo: [
          { required: true, message: '请填写型号', trigger: 'blur' }
        ],
        applyQty: [
          { required: true, message: '请填写申请数量', trigger: 'blur' }
        ],
        feeRate: [
          { required: true, message: '请填写手续费率', trigger: 'blur' }
        ],
        toUserStock: [
          { required: true, message: '请选择', trigger: 'change' }
        ],
        warehouseCode: [
          { required: true, message: '请选择仓库', trigger: 'change' }
        ],
        customerNo: [
          { required: true, message: '请填写客户代码', trigger: 'change' }
        ],
        itemNo: [
          { required: true, message: '请填写项号', trigger: 'blur' }
        ],
        returnType: [
          { required: true, message: '请选择退货类别', trigger: 'change' }
        ],
        dutyType: [
          { required: true, message: '请选择退货责任', trigger: 'change' }
        ],
        applicant: [
          { required: true, message: '请输入申请担当', trigger: 'blur' }
        ],
        reason: [
          { required: true, message: '请选择寄回目的', trigger: 'change' }
        ]
      },
      userStockData: [
        { value: 1, label: '是' },
        { value: 0, label: '否' }
      ],
      returnTypeData: [
        { value: '产品完好', label: '产品完好' },
        { value: '转报废', label: '转报废' }
      ],
      stockCodeData: [],
      // 分页
      total: 1,
      page: {
        pageNumber: 1,
        pageSize: 20
      },
      tableData: [],
      daySelectVal: '',
      classCode: '',
      statusDataList: [],
      pickerOptions: shortcuts,
      selectDate: '',
      isSuccess: false,
      loadingPrint: false,
      rcvReGisterLoading: false,
      searchMoreForm: false,
      multipleSelection: '',
      saveTableSettingVO: {
        optUser: '',
        uiViewId: 'returnProcess',
        jsonTableStr: ''
      },
      dialogFormVisible: false,
      productVisible: false,
      exportLoading: false,
      cancelDialog: false,
      wareHouseOptions: [],
      dutyTypeData: [],
      cssForHr: {
        width: '83%',
        marginLeft: '17px'
      },
      tableCss: {
        width: '84%',
        position: 'relative'
      },
      showReceiving: true, // 是否显示收货登记界面
      // 收货登记
      rcvReGisterForm: {
        dlvDate: '', // 收货日期
        applyNo: '', // 申请号
        isRcvTogether: false, // 整单接收
        isAssOrder: false, // 组装订单
        orderNo: '', // 订单号
        modelNo: '', // 型号
        applyNumber: null, // 申请数量
        goodNumber: null, // 良品数量
        badNumber: null, // 坏品数量
        expressNo: '', // 快递单号
        warehouseCode: '',
        requestWarehouseCodeName: '',
        locationNo: '',
        id: ''
      },
      printReturnOrderParams: {
        applyNo: []
      },
      applyNoStr: '',
      receivingAuth: [],
      reasonData: [],
      // 修改手续费 dialog表单
      handlingFeeForm: {
        orderNo: '',
        salesPrice: '', // 单价
        returnQty: null, // 退货数量
        feeRate: '', // 手续费率
        returnFee: '' // 手续费
      },
      rules: {
        salesPrice: [{ required: true, message: '单价不能为空', trigger: 'blur' }],
        orderNo: [{ required: true, message: '订单号不能为空', trigger: 'blur' }]
      },
      cancelForm: {
        ids: [],
        reason: ''
      },
      cancelRule: {
        reason: [
          { required: true, message: '请输入取消原因', trigger: 'blur' }
        ]
      },
      cancelOrderNo: '',
      itemNo: '',
      applyNo: ''
    }
  },
  computed: {
    ...mapGetters(['roles'])
  },
  mounted() {
    this.getReceivingAuth()
    this.getStatusDataList()
    this.fetchList()
    this.listWarehouseNoWT()
    this.getExpStockCodeList()
    this.getDutyTypeList()
    this.getReasonList()
    this.loadColums()
    // this.listDepartment()
    this.$nextTick(() => {
      // 手动将表格和工具栏进行关联
      this.$refs.multipleTable.connect(this.$refs.xToolbar1)
    })
  },
  methods: {
    cellStyle({ row, column }) {
      if (column.field === 'applyNo' || column.field === 'modelNo') {
        return {
          fontWeight: 600,
          color: '#002fa7',
          textDecoration: 'underline'
        }
      }
    },
    cellClassName({ row, column }) {
      if (column.field === 'applyNo') {
        return 'col-red'
      }
      return null
    },
    cellClick({ row, column }) {
      if (column !== null || column !== undefined) {
        if (column.field === 'applyNo') {
          this.handdle(row)
        }
        if (column.field === 'modelNo') {
          this.handleClick(row)
        }
      }
    },
    customColums(type) {
      if (type.type === 'confirm') {
        const columns = type.$table.collectColumn
        const hiddenColumns = columns.filter(column => column.visible === false)
        const settings = {
          columns,
          hiddenColumns
        }
        this.saveTableSettingVO.optUser = this.$store.getters.info.userId
        this.saveTableSettingVO.jsonTableStr = JSON.stringify(settings) // 将对象转换为字符串
        saveTableToRedis(this.saveTableSettingVO).then(res => {
        })
      }
      if (type.type === 'reset') {
        // 加载全部的列配置
        this.loadColums2()
      }
    },
    loadColums() {
      this.saveTableSettingVO.optUser = this.$store.getters.info.userId
      getTableFromRedis(this.saveTableSettingVO).then(res => {
        if (res.success) {
          const settings = JSON.parse(res.content)
          this.$refs.multipleTable.loadColumn(settings.columns) // 加载列信息
          settings.hiddenColumns.forEach(hiddenColumn => {
            this.$refs.multipleTable.hideColumn(hiddenColumn.field) // 设置隐藏列
          })
        }
      })
    },
    loadColums2() {
      this.saveTableSettingVO.optUser = this.$store.getters.info.userId
      getAllTableFromRedis(this.saveTableSettingVO).then(res => {
        if (res.success) {
          const settings = JSON.parse(res.content)
          this.$refs.multipleTable.loadColumn(settings.columns) // 加载列信息
          settings.hiddenColumns.forEach(hiddenColumn => {
            this.$refs.multipleTable.hideColumn(hiddenColumn.field) // 设置隐藏列
          })
          this.saveTableSettingVO.optUser = this.$store.getters.info.userId
          this.saveTableSettingVO.jsonTableStr = JSON.stringify(settings) // 将对象转换为字符串
          saveTableToRedis(this.saveTableSettingVO).then(res => {
          })
        }
      })
    },
    checkColumnMethod({ column }) {
      if (column.field === 'applyNo' || column.field === 'modelNo') {
        return false
      }
      return true
    },
    resizableChangeEvent() {
      const columns = this.$refs.multipleTable.getColumns()
      const customData = columns.map(column => {
        return {
          width: column.renderWidth
        }
      })
      console.log('customData=>', customData)
    },
    selectAllChangeEvent(val) {
      this.multipleSelection = val.records
    },
    selectChangeEvent(val) {
      this.multipleSelection = val.records
    },
    /** 当选项发送改变时触发该方法 */
    handleSelectionChange(val) {
      this.multipleSelection = val.records
    },
    handdle(row, event, column) {
      this.rcvReGisterForm.id = row.id
      this.rcvReGisterForm.dlvDate = new Date()
      if (row.receiveTime !== null) {
        this.rcvReGisterForm.dlvDate = this.dayjs(row.receiveTime).format('YYYY-MM-DD HH:mm:ss')
      }
      this.rcvReGisterForm.applyNo = row.applyNo
      this.rcvReGisterForm.orderNo = row.orderNo
      this.rcvReGisterForm.modelNo = row.modelNo
      if (row.applyQty === null || row.applyQty === undefined) {
        this.rcvReGisterForm.applyNumber = 0
      } else {
        this.rcvReGisterForm.applyNumber = row.applyQty
      }
      if (row.applyBadqty === null || row.applyBadqty === undefined) {
        this.rcvReGisterForm.badNumber = 0
      } else {
        this.rcvReGisterForm.badNumber = row.applyBadqty
      }
      this.rcvReGisterForm.goodNumber = row.applyQty - row.applyBadqty
      this.rcvReGisterForm.expressNo = row.expressNo
      this.rcvReGisterForm.isAssOrder = row.isAssOrder
      this.rcvReGisterForm.locationNo = row.locationNo
    },
    listWarehouseNoWT() {
      listWarehouseNoWT().then(res => {
        if (res.success === true) {
          this.wareHouseOptions = res.content
        }
      })
    },
    fetchList() {
      queryData(this.searchRequest, this.page).then(res => {
        if (res.success) {
          this.isSuccess = true
        } else {
          this.isSuccess = false
        }
        this.tableData = res.content.list
        this.total = res.content.total
      })
    },
    refuseRcv() {},
    changeStateCode() {
      this.fetchList()
    },
    exportReturnOrder() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportReturnOrder(this.searchRequest).then(res => {
        const fileName = '退货数据.xlsx'
        const blob = new Blob([res], { type: res.type })
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        link.download = fileName
        document.body.appendChild(link)
        link.click()
        window.URL.revokeObjectURL(link.href)
        document.body.removeChild(link)
        this.exportLoading = false
      }).catch(error => {
        console.error(error)
        this.$message.error('导出失败.' + error)
        this.exportLoading = false
      })
    },
    downLoadFile(val, applyNo) {
      const files = val.split(';')
      files.forEach(fileName => {
        downloadFile(fileName, applyNo).then(res => {
          // const fileName = fileName
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
          console.error(error)
          this.$message.error('下载失败.' + error)
          this.exportLoading = false
        })
      })
    },
    existFile(val) {
      if (val == null || val === '') {
        return false
      } else {
        return true
      }
    },
    printReturnOrder() {
      this.loadingPrint = true
      this.applyNoStr = ''
      this.multipleSelection.forEach(s => {
        this.applyNoStr += s.applyNo + ','
      })
      this.applyNoStr = this.applyNoStr.substring(0, this.applyNoStr.length - 1)
      this.printReturnOrderParams.applyNo = this.applyNoStr.split(',')
      this.printReturnOrderParams.applyNo = Array.from(new Set(this.printReturnOrderParams.applyNo))
      if (this.printReturnOrderParams.applyNo.length !== 1) {
        this.$message({
          showClose: true,
          message: '一次只能打印一个喔!',
          type: 'error'
        })
        this.loadingPrint = false
      } else {
        printReturnOrder(this.printReturnOrderParams).then(result => {
          if (result.size === 0 || result.type === 'text/xml') {
            this.$message({
              showClose: true,
              message: '打印失败,请检查数据是否有误',
              type: 'error'
            })
            this.loadingPrint = false
          } else {
            this.loadingPrint = false
            const fileName = this.printReturnOrderParams.applyNo[0] + '.pdf'
            const blob = new Blob([result], { type: result.type })
            this.printFile(blob, fileName)
          }
        }).catch(error => {
          console.log('error =>', error)
          this.loadingPrint = false
        })
      }
    },
    printFile(blob, fileName) {
      if (window.navigator.msSaveOrOpenBlob) { // 适配IE10
        navigator.msSaveBlob(blob, fileName)
      } else {
        const link = document.createElement('a')
        link.style.display = 'none'
        link.href = window.URL.createObjectURL(blob)
        window.open(link.href, '_blank')
      }
    },
    getStatusDataList() {
      this.classCode = '2061'
      getDictDataByPid(this.classCode).then(res => {
        this.statusDataList = res.content
      })
    },
    getExpStockCodeList() {
      this.classCode = '4306'
      getDictDataByPid(this.classCode).then(res => {
        this.stockCodeData = res.content
      })
    },
    getDutyTypeList() {
      this.classCode = '2068'
      getDictDataByPid(this.classCode).then(res => {
        this.dutyTypeData = res.content
      })
    },
    getReasonList() {
      this.classCode = '2071'
      getDictDataByPid(this.classCode).then(res => {
        this.reasonData = res.content
      })
    },
    statusFormatter(row) {
      return this.valFormatter(this.statusDataList, row.status + '')
    },
    valFormatter(list, val) {
      for (var i = 0; i < list.length; i++) {
        if (list[i].code === val) {
          return list[i].codeName
        }
      }
    },
    searchData() {
      this.searchTime()
      this.fetchList()
      // if (this.isSuccess) {
      //   this.$notify({
      //     title: '成功',
      //     message: '操作成功',
      //     type: 'success'
      //   })
      // } else {
      //   this.$notify.error({
      //     title: '错误',
      //     message: '操作失败'
      //   })
      // }
    },
    cancelReturnOrder() {
      if (this.multipleSelection.length <= 0) {
        this.$message.warning('请选择订单一个或多个订单')
        return
      }
      this.cancelForm.ids = []
      this.cancelOrderNo = ''
      this.cancelDialog = true
      this.multipleSelection.forEach(item => {
        this.cancelForm.ids.push(item.id)
        this.cancelOrderNo += item.orderNo + ','
      })
    },
    cancelOrder() {
      this.$refs.cancelForm.validate((valid) => {
        if (valid) {
          this.cancelDialog = false
          cancelReturnOrder(this.cancelForm).then(res => {
            if (res.success) {
              this.searchData()
              this.$message.success(res.content)
            } else {
              this.$message.error(res.message)
            }
          }).catch(error => {
            console.log(error)
          })
        } else {
          this.$message.error('请输入必填选项！')
          return false
        }
      })
    },
    executeBack() {
      const backOrderList = this.multipleSelection
      backOrder(backOrderList).then(res => {
        if (res.isSuccess) {
          this.$notify({
            title: '成功',
            message: res.message,
            type: 'success'
          })
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
        }
      })
    },
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    confirm() {
      this.$confirm('此操作将全部拒收, 是否继续?', '提示', {
        confirmButtonText: '继续',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.refuseBtu = true
        this.refuseRcvRequest.ids = []
        this.multipleSelection.forEach(item => {
          this.refuseRcvRequest.ids.push(item.id)
        })
        this.refuseRcvRequest.allRefuseRcv = 1
        refuseRcv(this.refuseRcvRequest).then(res => {
          if (res.success === true) {
            this.refuseBtu = false
            this.$message({
              message: res.content,
              type: 'success'
            })
            this.searchData()
          } else {
            this.refuseBtu = false
            this.$message.error(res.message)
          }
        }).catch(() => {
          this.$message.error('接口异常.')
          this.refuseBtu = false
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        })
      })
    },
    cancel() {
      this.refuseRcvRequest.ids = []
      this.refuseBtu = true
      this.multipleSelection.forEach(item => {
        this.refuseRcvRequest.ids.push(item.id)
      })
      this.refuseRcvRequest.allRefuseRcv = 0
      refuseRcv(this.refuseRcvRequest).then(res => {
        if (res.success === true) {
          this.refuseBtu = false
          this.$message({
            message: res.content,
            type: 'success'
          })
          this.searchData()
        } else {
          this.refuseBtu = false
          this.$message.error(res.message)
        }
      }).catch(() => {
        this.$message.error('接口异常.')
        this.refuseBtu = false
      })
    },
    handleClick(row) {
      const item = { modelno: row.modelNo }
      this.productVisible = true
      this.$nextTick(() => {
        this.$refs.ProductSearch.handleSelect(item)
        this.$refs.ProductSearch.productSearchChange()
      })
    },
    addReturnOrder() {
      this.$nextTick(() => {
        this.$refs.addOrderForm.resetFields()
        if (this.itemNo !== null && this.itemNo !== '') {
          this.addOrderForm.itemNo = this.itemNo
        }
        if (this.applyNo !== null && this.applyNo !== '') {
          this.addOrderForm.applyNo = this.applyNo
        }
      })
      this.addOrderForm.employee = this.$store.getters.info.psnName
      this.addOrderForm.employeeNo = this.$store.getters.info.userId
      this.addOrderForm.applyTime = this.dayjs(new Date()).format('YYYY-MM-DD')
      this.addOrderDialog = true
    },
    handleRemove(file, fileList) {
      console.log(file, fileList)
    },
    handlePreview(file) {
      console.log(file)
    },
    beforeUploadFile(file) {
      console.log(file)
    },
    handdleChange(file, fileList) {
      console.log(file, fileList)
      this.fileList = fileList
    },
    getWareHouseByCustomerNo(customerNo) {
      getWareHouseByCustomerNo(customerNo).then(res => {
        if (res.success) {
          this.addOrderForm.warehouseCode = res.content[0].wareHouseCode
        }
      })
    },
    insertReturnOrder() {
      this.applyLoading = true
      this.$refs.addOrderForm.validate((valid) => {
        if (valid) {
          if (this.addOrderForm.invoiceNo === '' && this.addOrderForm.invoiceDate !== '') {
            this.applyLoading = false
            this.$message.error('请输入发票号')
            return false
          }
          if (this.addOrderForm.invoiceDate === '' && this.addOrderForm.invoiceNo !== '') {
            this.applyLoading = false
            this.$message.error('请输入开票日期')
            return false
          }
          this.formData = new FormData()
          if (this.fileList !== null && this.fileList.length > 0) {
            for (let i = 0; i < this.fileList.length; i++) {
              this.formData.append('fileList', this.fileList[i].raw)
            }
          }
          this.formData.delete('addOrderData')
          this.formData.append('addOrderData', JSON.stringify(this.addOrderForm))
          addReturnOrder(this.formData).then(res => {
            if (res.success) {
              this.applyLoading = false
              this.addOrderDialog = false
              this.$message.success(res.content)
              this.searchRequest.applyNo = this.addOrderForm.applyNo
              this.searchData()
              this.itemNo = parseInt(this.addOrderForm.itemNo) + 1
              this.applyNo = this.addOrderForm.applyNo
            } else {
              this.applyLoading = false
              this.$message.error(res.message)
            }
          }).catch(error => {
            this.applyLoading = false
            console.log(error)
          })
        } else {
          this.applyLoading = false
          this.$message.error('请输入必填选项')
          return false
        }
      })
    },
    creatApplyNo() {
      creatApplyNo().then(res => {
        if (res.success) {
          this.addOrderForm.applyNo = res.content
          this.addOrderForm.itemNo = 1
          this.fileList = []
        } else {
          this.$message.error('生成退货申请号失败')
        }
      })
    },
    reset() {
      this.searchRequest = {
        applyNo: '', // 申请号
        orderNo: '', // 订单号
        modelNo: '',
        applyTimeStart: '',
        applyTimeEnd: '',
        receiveTimeStart: '',
        receiveTimeEnd: '',
        inTimeStart: '',
        inTimeEnd: '',
        status: []
      }
      this.selectDate = ''
      this.daySelectVal = ''
    },
    saveData(handlingFeeForm) {
      this.$refs[handlingFeeForm].validate(valid => {
        if (valid) {
          alert('commit success! ')
        } else {
          return false
        }
      })
    },
    dateFormatterForInvoiceDate(val) {
      if (val.invoiceDate != null) {
        return this.dayjs(val.invoiceDate).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dateFormatterForApplyTime(val) {
      if (val.applyTime != null) {
        return this.dayjs(val.applyTime).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dateFormatterForInTime(val) {
      if (val.inTime != null) {
        return this.dayjs(val.inTime).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    dateFormatterForReceiveTime(val) {
      if (val.receiveTime != null) {
        return this.dayjs(val.receiveTime).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    toUserStockFormatter(val) {
      if (val.toUserStock !== null) {
        if (val.toUserStock === 1) {
          return '是'
        } else {
          return '否'
        }
      } else {
        return ''
      }
    },
    searchTime() {
      if (this.daySelectVal === 'applyTime') {
        this.restDate()
        this.searchRequest.applyTimeStart = this.dayjs(this.selectDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchRequest.applyTimeEnd = this.dayjs(this.selectDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'receiveTime') {
        this.restDate()
        this.searchRequest.receiveTimeStart = this.dayjs(this.selectDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchRequest.receiveTimeEnd = this.dayjs(this.selectDate[1]).format('YYYY-MM-DD HH:mm:ss')
      } else if (this.daySelectVal === 'inTime') {
        this.restDate()
        this.searchRequest.inTimeStart = this.dayjs(this.selectDate[0]).format('YYYY-MM-DD HH:mm:ss')
        this.searchRequest.inTimeEnd = this.dayjs(this.selectDate[1]).format('YYYY-MM-DD HH:mm:ss')
      }
    },
    querySearchAsync(customerNo, cb) {
      // const cus = { customerNo: this.form.customerNo }
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray

        if (results !== null && results.length <= 0) {
          results = customerNo ? customerArray.filter(this.createFilter(customerNo)) : customerArray
        }
        cb(results)
      })
    },
    createStateFilter(queryString) {
      return (state) => {
        return (state.customerNo.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    createFilter(queryString) {
      return (state) => {
        return (state.name.toLowerCase().indexOf(queryString.toLowerCase()) === 0)
      }
    },
    Changeselect(item) {
      this.addOrderForm.customerNo = item.customerNo
      this.addOrderForm.customerName = item.name
    },
    ChangeUserNo(item) {
      this.addOrderForm.userNo = item.customerNo
      this.addOrderForm.userName = item.name
    },
    findDetailByOrder() {
      if (this.addOrderForm.orderNo != null && this.addOrderForm.orderNo !== '') {
        getRcvOrderDataByNo(this.addOrderForm.orderNo).then(res => {
          if (res.success) {
            console.log(res.content)
            this.addOrderForm.modelNo = res.content.modelNo
            this.addOrderForm.orderQty = res.content.quantity
            this.addOrderForm.price = res.content.price
            this.addOrderForm.modelNo = res.content.modelNo
            this.addOrderForm.deptNo = res.content.deptNo
            this.addOrderForm.customerNo = res.content.customerNo
            this.addOrderForm.userNo = res.content.userNo
            this.addOrderForm.expStockCode = res.content.stockCode
            if (res.content.invoiceNo !== null) {
              this.addOrderForm.invoiceNo = res.content.invoiceNo.trim()
            } else {
              this.addOrderForm.invoiceNo = ''
            }
            if (res.content.invoiceDate !== null) {
              this.addOrderForm.invoiceDate = res.content.invoiceDate
            } else {
              this.addOrderForm.invoiceDate = ''
            }

            if (res.content.customerNo != null && res.content.customerNo !== '') {
              getCustomerByNo(this.addOrderForm.customerNo).then(data => {
                this.addOrderForm.customerName = data.content[0].name
              })
              this.getWareHouseByCustomerNo(this.addOrderForm.customerNo)
            }
            if (res.content.userNo != null && res.content.userNo !== '') {
              getCustomerByNo(this.addOrderForm.userNo).then(data => {
                this.addOrderForm.userName = data.content[0].name
              })
            }
          } else {
            this.$message.error('未查询到订单信息，请检查')
          }
        })
      }
    },
    restDate() {
      this.searchRequest.applyTimeStart = ''
      this.searchRequest.applyTimeEnd = ''
      this.searchRequest.receiveTimeStart = ''
      this.searchRequest.receiveTimeEnd = ''
      this.searchRequest.inTimeStart = ''
      this.searchRequest.inTimeEnd = ''
    },
    handleChange() {
      this.resetRcvGisterForm()
      this.searchRequest.requestWarehouseCode = this.rcvReGisterForm.warehouseCode
      this.fetchList()
      this.$nextTick(() => {
        this.rcvReGisterForm.requestWarehouseCodeName = this.$refs.wareHouseList.selected.label
      })
    },
    cleanInvoice() {
      this.addOrderForm.invoiceNo = ''
      this.addOrderForm.invoiceDate = ''
    },
    // 获取收货登记界面的权限
    getReceivingAuth() {
      this.isShowReceiving(this.showReceiving)
    },
    isShowReceiving(flag) {
      if (flag === false) {
        this.cssForHr.width = '95%'
        this.tableCss.width = '100%'
      }
    },
    submitForm() {
      this.rcvReGisterLoading = true
      rcvOrderReGister(this.rcvReGisterForm).then(res => {
        if (res.success === true) {
          this.$notify({
            title: '成功',
            message: res.content,
            type: 'success'
          })
          this.rcvReGisterLoading = false
          this.searchData()
        } else {
          this.$notify.error({
            title: '错误',
            message: res.message
          })
          this.rcvReGisterLoading = false
        }
      }).catch(error => {
        this.rcvReGisterLoading = false
        console.log(error)
      })
    },
    resetRcvGisterForm() {
      this.rcvReGisterForm.applyNo = ''
      this.rcvReGisterForm.isRcvTogether = false
      this.rcvReGisterForm.isAssOrder = false
      this.rcvReGisterForm.orderNo = ''
      this.rcvReGisterForm.modelNo = ''
      this.rcvReGisterForm.locationNo = ''
      this.rcvReGisterForm.applyNumber = null
      this.rcvReGisterForm.goodNumber = null
      this.rcvReGisterForm.badNumber = null
      this.rcvReGisterForm.expressNo = ''
      this.rcvReGisterForm.id = ''
    }
    // listDepartment() {
    //   listDepartment().then(res => {
    //     this.deptNoList = res.content
    //   })
    // },
    // deptNoFormatter(val) {
    //   if (val.deptNo === null) {
    //     return null
    //   }
    //   return this.dataConversion(this.deptNoList, val.deptNo)
    // },
    // dataConversion(deptNoList, val) {
    //   for (var i = 0; i < deptNoList.length; i++) {
    //     if (deptNoList[i].deptNo === val) {
    //       return deptNoList[i].deptName
    //     }
    //   }
    // }
  }
}
</script>
<style scoped>
.maindiv{
  height: 100%;
  width: 95%;
}
  .btu{
     margin-top: 25px;
  }
.btu .el-button {
    padding: 6px;
    margin-top: 12px;
    border: 1px solid #0076bd;
}

.btu .el-button:hover{
    color: #fff;
    background-color: #0076BD;
  }

.in {
  width: 150px;
}
  .searchInput{
    margin-left: 10%;
    margin-top: -30px;
  }
  .h{
    position: relative;
    border-bottom: 2px solid rgb(0, 118, 189);
    margin-top: -10px;
 }
 .tab{
    margin-top: 15px;
  }
  .search{
  margin-left: 7%;
}
.inputs {
  width:  80px;
}
.rcvReGisterCss{
    position: absolute;
    width: 250px;
    border: 2px solid rgb(0, 118, 189);
    margin-left: 81%;
    margin-top: -615px;
}
.dialog {
    display: flex;
    justify-content: center;
    align-items: Center;
    overflow: hidden;
  }
  .dialog /deep/ .el-dialog {
        margin: 0 auto !important;
        height: 70%;
        overflow: hidden;
  }
  .dialog /deep/ .el-dialog .el-dialog__body {
            position: absolute;
            left: 0;
            top: 54px;
            bottom: 0;
            right: 0;
            padding: 0;
            z-index: 1;
            overflow: hidden;
            overflow-y: auto;
  }
  .searchWareHouse {
    position: absolute;
    margin-top: -45px;
    margin-left: 820px;
  }
  .pagination-container /deep/ .el-pagination {
    white-space: nowrap;
    color: #303133;
    font-weight: bold;
    padding: 1px 220px;
  }
  .delOrder {
    color: red;
    font-size: 7px;
  }
  .select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
  }
  .showcolumClass {
    /* position: absolute; */
    margin-top: -15px;
    margin-right: 15%;
  }
</style>
