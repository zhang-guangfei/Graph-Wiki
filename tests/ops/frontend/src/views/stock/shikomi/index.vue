<template>
  <el-container>
    <el-main>
      <el-form ref="form" :inline="true" :model="condition" size="mini">
        <el-form-item label="备库地点">
          <template>
            <!-- <el-select v-model="condition.supplierCode" size="mini" style="width:150px" clearable>
              <el-option
                v-for="item in supplierOptions"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"/>
            </el-select> -->
            <el-select v-model="condition.supplierCodeList" size="mini" multiple style="width:150px" clearable>
              <el-option
                v-for="item in supplierData"
                :key="item.id"
                :label="item.name"
                :value="item.id"/>
            </el-select>
            <!-- <el-button type="primary" size="mini" @click="selectSupplier">···</el-button> -->
          </template>
        </el-form-item>
        <el-form-item label="型号">
          <el-input
            v-model.trim="condition.modelNo"
            type="text"
            size="mini"
            placeholder="请输入型号"
            clearable
            class="search-input-class"/>
        </el-form-item>
        <el-form-item label="shikomi号">
          <el-input
            v-model.trim="condition.shikomiNo"
            type="text"
            size="mini"
            placeholder="请输入shikomi号"
            clearable
            class="search-input-class"/>
        </el-form-item>
        <el-form-item label="归属客户">
          <!-- <el-input
            v-model.trim="condition.customerNo"
            type="text"
            size="mini"
            placeholder="请输入"
            clearable
            class="search-input-class"
            style="width:80px"/> -->
          <el-autocomplete
            v-model="condition.customerNo"
            :fetch-suggestions="querySearchAsync"
            :debounce="0"
            :popper-append-to-body="false"
            popper-class="el-autocomplete-suggestion"
            highlight-first-item
            type="text"
            size="mini"
            style="width: 100px"
            placeholder="归属客户"
            class="select"
            @select="ChangeInput">
            <template slot-scope="{ item }">
              <div class="name">{{ item.customerNo + ',' + item.name }}</div>
            </template>
          </el-autocomplete>
        </el-form-item>
        <el-form-item label="状态">
          <template>
            <el-select v-model="condition.status" style="width:80px" clearable>
              <el-option
                v-for="item in statusOptions"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"/>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="营业所">
          <DepartmentHL @change="handleDeptChange" />
        </el-form-item>
        <el-form-item>
          <el-button v-permission="['802110']" type="primary" size="small" @click="(getList(1))">查询</el-button>
        </el-form-item>
        <!-- <el-form-item>
          <el-button type="primary" size="small" @click="handleClick">查</el-button>
        </el-form-item> -->
        <el-form-item>
          <el-button v-permission="['802110']" type="primary" size="small" style="margin-bottom:3px" @click="searchMoreData">更多查询</el-button>
        </el-form-item>
      </el-form>

      <!--供应商弹窗-->
      <el-dialog :visible.sync="SupplierdialogVisible" title="供应商" width="500px">
        <el-form ref="warehouseForm" :inline="true" :model="supplierForm" size="small">
          <el-form-item >
            <el-input v-model="supplierForm.companyId" placeholder="供应商代码" style="width:100px" clearable/>
          </el-form-item>
          <el-form-item >
            <el-input v-model="supplierForm.name" placeholder="供应商名称" clearable/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" size="small" @click="listSupplierinfo"/>
          </el-form-item>
        </el-form>
        <el-table :data="supplieData.filter(data => !supplierForm.name || data.name.includes(supplierForm.name))">
          <el-table-column prop="id" label="供应商代码" />
          <el-table-column prop="name" label="供应商名称" />
          <el-table-column label="操作" align="center" width="150">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="editSupplier(scope.row)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>

      <el-dialog v-dialogDrag :visible.sync="productVisible" :modal="false" append-to-body title="型号信息" width="750px" class="dialog">
        <product-search v-if="productVisible" ref="ProductSearch"/>
      </el-dialog>

      <el-form v-if="searchMoreForm" ref="form" :inline="true" :model="condition" size="mini" class="searchform">
        <el-form-item label="SHIKOMI区分">
          <el-select v-model="condition.classType" placeholder="请选择" style="width:120px" clearable>
            <el-option v-for="item in classTypes" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="SHIKOMI客户范围">
          <el-select v-model="condition.classCode" placeholder="请选择" style="width:120px" clearable>
            <el-option v-for="item in classCodes" :key="item.code" :value="item.code" :label="item.codeName" />
          </el-select>
        </el-form-item>
        <el-form-item label="国内管理品">
          <el-select v-model="condition.isManageProduct" placeholder="请选择" style="width:100px" clearable>
            <el-option v-for="item in manages" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item label="主型号">
          <el-select v-model="condition.mainModelFlag" placeholder="请选择" style="width:100px" clearable>
            <el-option v-for="item in mainModel" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <el-form-item label="Rohs10">
          <el-select v-model="condition.rohs" placeholder="请选择" style="width:100px" clearable>
            <el-option v-for="item in RohsData" :key="item.value" :value="item.value" :label="item.label" />
          </el-select>
        </el-form-item>
        <!-- <el-form-item label="归属客户">
          <el-input v-model.trim="condition.mainCustomerNo" type="text" style="width:120px" placeholder="请输入" clearable/>
        </el-form-item> -->
        <el-form-item label="申请人工号">
          <el-input
            v-model.trim="condition.applicantNo"
            type="text"
            size="mini"
            placeholder="请输入"
            clearable
            class="search-input-class"
            style="width:120px"/>
        </el-form-item>
        <el-form-item label="申请号">
          <el-input
            v-model.trim="condition.applyNo"
            type="text"
            size="mini"
            placeholder="请输入"
            clearable
            class="search-input-class"
            style="width:150px"/>
        </el-form-item>
        <el-form-item label="申请担当">
          <el-input v-model.trim="condition.applicantName" type="text" style="width:120px" placeholder="请输入" clearable/>
        </el-form-item>
        <el-form-item label="未订货月数">
          <el-input v-model.trim="condition.qtyNoord1" type="text" style="width:80px" clearable/>
          -
          <el-input v-model.trim="condition.qtyNoord2" type="text" style="width:80px" clearable/>
        </el-form-item>
        <el-form-item label="行业代码">
          <el-input v-model.trim="condition.indCode" type="text" style="width:100px" clearable/>
        </el-form-item>
        <!-- <el-form-item label="更新时间" style="margin-left: 0px">
          <el-date-picker
            v-model="updateTime"
            type="daterange"
            align="right"
            unlink-panels
            style="width: 250px"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-form-item> -->
        <el-form-item label="点检状态">
          <template>
            <el-select v-model="condition.inspectStatus" style="width:90px" clearable>
              <el-option
                v-for="item in inspectStatusOptions"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"/>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item label="点检类别">
          <template>
            <el-select v-model="condition.inspectType" style="width:120px" clearable>
              <el-option
                v-for="item in inspectTypeOptions"
                :key="item.code"
                :label="item.codeName"
                :value="item.code"/>
            </el-select>
          </template>
        </el-form-item>
        <el-form-item>
          <el-select v-model="condition.dateType" placeholder="请选择" style="width: 100px" size="mini" clearable>
            <el-option
              v-for="item in dateTypeData"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
          <el-date-picker
            v-model="searchTime"
            type="daterange"
            align="right"
            unlink-panels
            style="width: 250px"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-form-item>
        <!-- <el-form-item label="点检发送时间" style="margin-left: 0px">
          <el-date-picker
            v-model="sendTime"
            type="daterange"
            align="right"
            unlink-panels
            style="width: 250px"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-form-item>
        <el-form-item label="点检回复时间" style="margin-left: 0px">
          <el-date-picker
            v-model="answerTime"
            type="daterange"
            align="right"
            unlink-panels
            style="width: 250px"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"/>
        </el-form-item> -->
        <el-form-item>
          <el-checkbox v-model="condition.checked" border size="small">残数不足（警告）</el-checkbox>
        </el-form-item>
      </el-form>

      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadFileDialogVisible"
        :before-close="closeClick"
        title="上传"
        width="420px">
        <div class="upload">
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
            :on-success="uploadSuccess"
            class="upload-demo"
            drag
            multiple>
            <div class="el-upload__text" >支持txt格式</div>
            <div v-if="file !== null" class="el-upload__text" > {{ file.name }} </div>
            <div v-else class="el-upload__text" >将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeClick">关闭</el-button>
          <el-button type="success" @click="importData(1)">
            <span v-loading="uploadStatus" element-loading-text="拼命上传中..." element-loading-spinner="el-icon-loading" element-loading-background="#66CDAA">{{
              uploadStatus ? "拼命上传中..." : "上传文件"
            }}</span>
          </el-button>
        </span>
      </el-dialog>

      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadCNFileDialog"
        :before-close="closeClick"
        title="上传"
        width="420px">
        <div class="upload">
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
            :on-success="uploadSuccess"
            class="upload-demo"
            drag
            multiple>
            <div class="el-upload__text" >支持xlsx格式</div>
            <div v-if="file !== null" class="el-upload__text" > {{ file.name }} </div>
            <div v-else class="el-upload__text" >将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeClick">关闭</el-button>
          <el-button type="success" @click="importData(2)">
            <span v-loading="uploadStatus" element-loading-text="拼命上传中..." element-loading-spinner="el-icon-loading" element-loading-background="#66CDAA">{{
              uploadStatus ? "拼命上传中..." : "上传文件"
            }}</span>
          </el-button>
        </span>
      </el-dialog>

      <el-dialog
        :close-on-click-modal="false"
        :visible.sync="uploadInspectionDialog"
        :before-close="closeClick"
        title="上传"
        width="420px">
        <div class="upload">
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
            :on-success="uploadSuccess"
            class="upload-demo"
            drag
            multiple>
            <div class="el-upload__text" >支持xlsx格式</div>
            <div v-if="file !== null" class="el-upload__text" > {{ file.name }} </div>
            <div v-else class="el-upload__text" >将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </div>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closeClick">关闭</el-button>
          <el-button type="success" @click="importData(3)">计算</el-button>
          <el-button type="success" @click="checkStatus">状态</el-button>
          <el-button type="success" @click="sendMail()">
            <span v-loading="uploadStatus" element-loading-text="邮件发送中..." element-loading-spinner="el-icon-loading" element-loading-background="#66CDAA">{{
              uploadStatus ? "邮件发送中..." : "发送邮件"
            }}</span>
          </el-button>
        </span>
      </el-dialog>

      <el-dialog :visible.sync="dialogaddVisible" :close-on-click-modal="false" title="添加/修改" width="770px">
        <el-form ref="addForm" :rules="shikomiRule" :model="addForm" :inline="true" label-width="130px" size="mini">
          <el-form-item prop="shikomiNo" label="shikomi号">
            <el-input :disabled="shikomi" v-model="addForm.shikomiNo" style="width:200px" @input="change($event)"/>
          </el-form-item>
          <el-form-item prop="modelNo" label="型号">
            <el-input :disabled="shikomi" v-model="addForm.modelNo" style="width:200px" @input="change($event)"/>
          </el-form-item>
          <el-form-item prop="modelNo" label="主型号">
            <el-input :disabled="shikomi" v-model="addForm.mainModelNo" style="width:200px" @input="change($event)"/>
          </el-form-item>
          <el-form-item prop="applyNo" label="申请号">
            <el-input v-model="addForm.applyNo" style="width:200px" />
          </el-form-item>
          <el-form-item prop="applyTime" label="申请日期">
            <el-date-picker
              v-model="addForm.applyTime"
              type="date"
              size="small"
              style="width:200px"
              placeholder="申请日期"
            />
          </el-form-item>
          <el-form-item prop="supplierCode" label="备库地点">
            <el-select v-model="addForm.supplierCode" placeholder="请选择" style="width:155px" size="small">
              <el-option v-for="item in supplierOptions2" :key="item.code" :value="item.code" :label="item.codeName" />
            </el-select>
            <el-button type="primary" size="mini" @click="selectSupplier(1)">···</el-button>
          </el-form-item>
          <el-form-item prop="status" label="状态">
            <el-select v-model="addForm.status" placeholder="请选择" style="width:200px" size="small">
              <el-option v-for="item in statusOptions" :key="item.code" :value="item.code" :label="item.codeName" />
            </el-select>
          </el-form-item>
          <el-form-item prop="classType" label="SHIKOMI区分">
            <el-select v-model="addForm.classType" placeholder="请选择" style="width:200px" size="small">
              <el-option v-for="item in classTypes" :key="item.code" :value="item.code" :label="item.codeName" />
            </el-select>
          </el-form-item>
          <el-form-item prop="classCode" label="SHIKOMI客户范围">
            <el-select v-model="addForm.classCode" placeholder="请选择" style="width:200px" size="small">
              <el-option v-for="item in classCodes" :key="item.code" :value="item.code" :label="item.codeName" />
            </el-select>
          </el-form-item>
          <el-form-item prop="customerNo" label="归属客户">
            <el-autocomplete
              v-model="addForm.customerNo"
              :fetch-suggestions="querySearchAsync"
              :debounce="0"
              :popper-append-to-body="false"
              popper-class="el-autocomplete-suggestion"
              highlight-first-item
              type="text"
              size="mini"
              style="width: 200px"
              placeholder="归属客户"
              class="select"
              @select="Changeselect">
              <template slot-scope="{ item }">
                <div class="name">{{ item.customerNo + ',' + item.name }}</div>
              </template>
            </el-autocomplete>
          </el-form-item>
          <!-- <el-form-item prop="customerNo" label="客户代码">
            <el-input v-model="addForm.customerNo" style="width:200px" autocomplete="off"/>
          </el-form-item> -->
          <el-form-item prop="deptNo" label="营业所">
            <!-- <el-input v-model="addForm.deptNo" style="width:200px" autocomplete="off" @input="change($event)"/> -->
            <el-cascader
              :options="deptNoOptions"
              :props="{ value: 'id', label: 'name',checkStrictly: true }"
              :show-all-levels="false"
              v-model="addForm.deptNo"
              placeholder="选择营业所"
              style="width:200px"
              filterable
              clearable
              @change="deptChange" />
          </el-form-item>
          <el-form-item prop="indCode" label="行业代码">
            <el-input v-model="addForm.indCode" style="width:200px" autocomplete="off"/>
          </el-form-item>
          <el-form-item prop="companyCode" label="公司">
            <el-input v-model="addForm.companyCode" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="isWarning" label="是否警告">
            <el-select v-model="addForm.isWarning" placeholder="请选择" style="width:200px" size="small">
              <el-option v-for="item in WarningTypes" :key="item.value" :value="item.value" :label="item.label" />
            </el-select>
          </el-form-item>
          <el-form-item prop="asseDays" label="组装工作日">
            <el-input v-model="addForm.asseDays" style="width:200px"/>
          </el-form-item>

          <el-form-item prop="qtyNoord" label="未订货月数">
            <el-input v-model="addForm.qtyNoord" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="qtyWarning" label="警告数量">
            <el-input v-model="addForm.qtyWarning" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="partPrepareDays" label="部品准备工作日">
            <el-input v-model="addForm.partPrepareDays" style="width:200px"/>
          </el-form-item>
          <!-- <el-form-item prop="optstate" label="运行状态">
            <el-input v-model="addForm.optstate" style="width:200px"/>
          </el-form-item> -->
          <el-form-item prop="planUseDate" label="预计晚使用月份">
            <el-date-picker
              v-model="addForm.planUseDate"
              type="date"
              size="small"
              style="width:200px"
              placeholder="预计晚使用月份"
            />
          </el-form-item>
          <el-form-item prop="registDate" label="初回出荷日">
            <el-date-picker
              v-model="addForm.registDate"
              type="date"
              size="small"
              style="width:200px"
              placeholder="初回出荷日"
            />
          </el-form-item>
          <el-form-item prop="applyQty" label="申请数量">
            <el-input v-model="addForm.applyQty" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="remainQty" label="SHIKOMI残数">
            <el-input v-model="addForm.remainQty" style="width:200px" @input="change($event)"/>
          </el-form-item>
          <el-form-item prop="qtyOrdPre" label="已预约数量">
            <el-input v-model="addForm.qtyOrdPre" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="lotQty" label="使用中LOT数">
            <el-input v-model="addForm.lotQty" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="priceLot" label="LOT价格">
            <el-input v-model="addForm.priceLot" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="projectNo" label="项目管理号/机型">
            <el-input v-model="addForm.projectNo" style="width:200px" />
          </el-form-item>
          <el-form-item prop="pplNo" label="PPL编号">
            <el-input v-model="addForm.pplNo" style="width:200px" />
          </el-form-item>
          <el-form-item prop="rohs" label="Rohs10">
            <el-select v-model="addForm.rohs" placeholder="请选择" style="width:200px" size="small">
              <el-option v-for="item in rohsTypes" :key="item.value" :value="item.value" :label="item.label" />
            </el-select>
          </el-form-item>
          <el-form-item prop="applicantNo" label="申请人工号">
            <el-input v-model="addForm.applicantNo" style="width:140px"/>
            <el-button type="primary" size="mini" @click="selectApplicantNo(1)">选择</el-button>
          </el-form-item>
          <el-form-item prop="applicantName" label="申请担当">
            <el-input v-model="addForm.applicantName" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="approverNo" label="负责人工号">
            <el-input v-model="addForm.approverNo" style="width:140px"/>
            <el-button type="primary" size="mini" @click="selectApplicantNo(2)">选择</el-button>
          </el-form-item>
          <el-form-item prop="approverName" label="负责人">
            <el-input v-model="addForm.approverName" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="applicantEmail" label="申请人邮箱">
            <el-input v-model="addForm.applicantEmail" style="width:200px"/>
          </el-form-item>
          <el-form-item prop="approverEmail" label="负责人邮箱">
            <el-input v-model="addForm.approverEmail" style="width:200px"/>
          </el-form-item>
          <!-- <el-form-item prop="remark" label="周转状况">
            <el-input v-model="addForm.remark" style="width:200px" />
          </el-form-item> -->
          <!-- <el-form-item prop="isRemind" label="是否警告">
            <el-input v-model="addForm.isRemind" style="width:200px" />
          </el-form-item> -->
          <!-- <el-form-item prop="a" label="消耗期限">
            <el-input v-model="addForm.a" style="width:200px" />
          </el-form-item> -->
          <!-- <el-form-item prop="answerText" label="回复说明">
            <el-input v-model="addForm.answerText" style="width:200px" @input="change($event)"/>
          </el-form-item> -->
          <el-form-item prop="remark" label="备注">
            <el-input :autosize="{ minRows: 2, maxRows: 4}" v-model="addForm.remark" type="textarea" style="width:530px" @input="change($event)"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogaddVisible = false">取 消</el-button>
          <el-button :loading="saveLoading" type="primary" @click="saveData()">保存</el-button>
        </div>
      </el-dialog>

      <!-- 工号选择弹窗 -->
      <el-dialog :visible.sync="dialogApplicantForm" title="工号选择" width="550px">
        <el-form ref="applicantForm" :inline="true" :model="applicantForm" size="small">
          <el-form-item >
            <el-input v-model="applicantForm.id" placeholder="工号" style="width:100px" clearable @clear="listApplicantinfo"/>
          </el-form-item>
          <el-form-item >
            <el-input v-model="applicantForm.name" placeholder="姓名" clearable @clear="listApplicantinfo"/>
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" size="small" @click="listApplicantinfo"/>
          </el-form-item>
        </el-form>
        <el-table :data="applicantData">
          <el-table-column property="id" label="工号" width="210px"/>
          <el-table-column property="name" label="姓名" width="210px"/>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="edit(scope.row)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          :page-sizes="[20, 50, 100, 200, 500]"
          :current-page="applicantPage.pageNumber"
          :page-size="applicantPage.pageSize"
          :total="applicantPage.total"
          :small="true"
          background
          layout="total, sizes, prev, pager, next"
          @size-change="PageSizeChange"
          @current-change="PageChange"/>
        <el-divider />
      </el-dialog>

      <el-dialog :visible.sync="dialogaddmodel" :close-on-click-modal="false" title="增加复数型号" width="620px" >
        <el-form ref="models" :model="models">
          <el-form-item label-width="200px" prop="shikomiNo" label="shikomi号">
            <el-input v-model="models.shikomiNo" style="width:200px" autocomplete="off"/>
          </el-form-item>
          <el-form-item label-width="200px" prop="serialModel" label="关联主型号">
            <el-input v-model="models.serialModel" style="width:200px" autocomplete="off"/>
          </el-form-item>
          <el-form-item label-width="200px" prop="modelNo" label="型号">
            <el-input v-model="models.modelNo" style="width:200px" autocomplete="off"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogaddmodel = false">取 消</el-button>
          <el-button type="primary" @click="dialogaddmodel = false;saveModels()">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogaddcustomer" :close-on-click-modal="false" title="增加许可用户" width="620px" >
        <el-form ref="cusData" :model="cusData">
          <el-form-item label-width="200px" prop="shikomiNo" label="shikomi号">
            <el-input v-model="cusData.shikomiNo" size="small" style="width:200px" autocomplete="off"/>
          </el-form-item>
          <!-- <el-form-item label-width="200px" prop="customerNo" label="客户代码">
            <el-input v-model="cusData.customerNo" style="width:200px" autocomplete="off"/>
          </el-form-item> -->
          <el-form-item prop="customerNo" label="客户代码" label-width="200px" >
            <el-autocomplete
              v-model="cusData.customerNo"
              :fetch-suggestions="querySearchAsync"
              :debounce="0"
              :popper-append-to-body="false"
              popper-class="el-autocomplete-suggestion"
              highlight-first-item
              type="text"
              size="small"
              style="width: 200px"
              placeholder="客户代码"
              class="select"
              @select="customerSelect">
              <template slot-scope="{ item }">
                <div class="name">{{ item.customerNo+ ',' + item.name }}</div>
              </template>
            </el-autocomplete>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogaddcustomer = false">取 消</el-button>
          <el-button type="primary" @click="dialogaddcustomer = false;saveCustomerdata()">保存</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="inspectionDialog" :close-on-click-modal="false" title="点检处理" width="620px" >
        <el-form ref="inspec" :model="handleData" :rules="inspection" size="mini">
          <el-form-item label-width="200px" prop="handleType" label="处理类型">
            <!-- <el-input v-model="inspecData.shikomiNo" style="width:200px" autocomplete="off"/> -->
            <el-select v-model="handleData.handleType" placeholder="请选择" style="width:200px" size="small">
              <el-option v-for="item in handleInspectOptions" :key="item.value" :value="item.value" :label="item.label" />
            </el-select>
          </el-form-item>
          <el-form-item label-width="200px" prop="inspectAnswerText" label="说明">
            <!-- <el-input v-model="handleData.inspectAnswerText" style="width:200px"/> -->
            <el-input :autosize="{ minRows: 2, maxRows: 4}" v-model="handleData.inspectAnswerText" type="textarea" style="width:200px"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="inspectionDialog = false">取 消</el-button>
          <el-button type="primary" @click="handleInspection()">确认</el-button>
        </div>
      </el-dialog>
      <el-dialog :visible.sync="dialogFormVisible" title="客户信息" width="450px">
        <el-table :data="gridData">
          <el-table-column v-if="false" property="id" width="200"/>
          <el-table-column property="shikomiNo" label="Shikomi号" width="150"/>
          <el-table-column property="customerNo" label="客户代码" width="120"/>
          <el-table-column label="操作" align="center">
            <template slot-scope="scope">
              <el-button type="primary" size="mini" @click="delCustomer(scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
      <el-dialog :visible.sync="dialogInspectVisible" title="点检信息">
        <el-table :data="inspectData">
          <el-table-column property="shikomiNo" label="Shikomi号" width="150"/>
          <el-table-column property="modelNo" label="型号" width="150"/>
          <el-table-column :formatter="formatInspectType" property="inspectType" label="点检类型" width="100"/>
          <el-table-column property="qtyWarning" label="警告数量" width="100"/>
          <el-table-column property="warnQtyOptCode" label="警告变更类型" width="120"/>
          <el-table-column property="demandQtyOptCode" label="需求变更类型" width="120"/>
          <el-table-column property="cancelQtyOptCode" label="逾期取消数量" width="120"/>
          <el-table-column property="openToWorld" label="开放给其他用户" width="100"/>
          <el-table-column property="delayToCancel" label="决算延期" width="150"/>createTime
          <el-table-column property="answerText" label="回复说明" width="150"/>
          <el-table-column :formatter="formatDate" property="createTime" label="创建时间" width="150"/>
        </el-table>
      </el-dialog>
      <el-dialog :visible.sync="dialogInspection" :close-on-click-modal="false" width="820px" >
        <el-form ref="Inspection" :model="Inspection" :rules="rule" :inline="true" size="mini">
          <el-form-item v-if="false" prop="id" label="id">
            <el-input v-model="Inspection.id" type="text" style="width:50px" />
          </el-form-item>
          <el-row type="flex" style="margin-top:10px">
            <el-form-item prop="shikomiNo" label="SHIKOMI NO:">
              <el-input v-model="Inspection.shikomiNo" type="text" style="width:400px" />
            </el-form-item>
            <el-form-item prop="inspectType" label="点检类型:">
              <el-select v-model="Inspection.inspectType" placeholder="点检类型" size="mini" style="width: 150px" @change="selectChange">
                <el-option v-for="item in inspectTypeOptions" :key="item.code" :label="item.codeName" :value="item.code"/>
              </el-select>
            </el-form-item>
          </el-row>
          <el-row type="flex">
            <el-form-item>
              <span class="font">点检内容</span>
            </el-form-item>
          </el-row>
          <el-row v-if="ashow" type="flex">
            <el-form-item label="是否追加SHIKOMI(若选否请修改警告数量或填写决算内容)" prop="warnQtyOptCode">
              <el-radio-group v-model="Inspection.warnQtyOptCode" @change="shikomichange">
                <el-radio label="0">是</el-radio>
                <el-radio label="1">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="警告数" prop="qtyWarning" style="margin-left:80px">
              <el-input :disabled="warning" v-model="Inspection.qtyWarning" size="mini" type="text" style="width:100px" @blur="qtycheck"/>
            </el-form-item>
          </el-row>
          <el-row v-if="bshow" type="flex">
            <el-form-item label="客户未来需求增加是否追加SHIKOMI(若选否请修改警告数量)" prop="warnQtyOptCode">
              <el-radio-group v-model="Inspection.warnQtyOptCode" @change="warnChange">
                <el-radio label="0">是</el-radio>
                <el-radio label="2">否</el-radio>
              </el-radio-group>
            </el-form-item>
            <el-form-item label="警告数" prop="qtyWarning" style="margin-left:80px">
              <el-input :disabled="warning" v-model="Inspection.qtyWarning" size="mini" type="text" style="width:100px"/>
            </el-form-item>
          </el-row>
          <el-row v-if="bshow" type="flex">
            <el-form-item label="客户未来需求减少是否如期消耗(若选择否请填写决算内容)" prop="demandQtyOptCode">
              <el-radio-group v-model="Inspection.demandQtyOptCode" style="margin-left:18px" @change="demandChange">
                <el-radio label="1">是</el-radio>
                <el-radio label="0">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-row>
          <el-row v-if="dshow" type="flex">
            <el-form-item label="是否再次到期(若选是请填写决算内容)" prop="draide">
              <el-radio-group v-model="Inspection.draide" style="margin-left:118px">
                <el-radio label="1">是</el-radio>
                <el-radio label="2">否</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-row>
          <div v-if="ashow" class="waring">
            <div class="waring-span">
              <span>决算内容</span>
            </div>
            <div class="waring-info">
              <el-form-item label="1. 请输入不追加SHIKOMI的理由" prop="answerText1">
                <el-input :disabled="ashikomi" v-model="Inspection.answerText1" size="mini" type="text" style="width:580px"/>
              </el-form-item>
              <el-form-item label="2. 请输入今后的交货期对策" prop="answerText2">
                <el-input :disabled="ashikomi" v-model="Inspection.answerText2" size="mini" type="text" style="width:580px"/>
              </el-form-item>
              <el-form-item label="3. 残数是否可以如期消耗(若选择否请填写下记两点)" prop="canradio">
                <el-radio-group v-model="Inspection.canradio">
                  <el-radio :disabled="bshikomi" label="1">是</el-radio>
                  <el-radio :disabled="bshikomi" label="2">否</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="3.1 是否开放全球使用权限" style="margin-left:10px" prop="openToWorld">
                <el-radio :disabled="ashikomi" v-model="Inspection.openToWorld" label="1">是</el-radio>
                <el-radio :disabled="ashikomi" v-model="Inspection.openToWorld" label="0">否</el-radio>
              </el-form-item>
              <el-form-item prop="answerText3">
                <el-input :disabled="ashikomi" v-model="Inspection.answerText3" size="mini" type="text" style="width:270px" placeholder="若不开通权限请描述理由"/>
              </el-form-item>
              <el-form-item label="3.2 是否延期" style="margin-left:10px" prop="delayToCancel">
                <el-radio-group v-model="Inspection.delayToCancel" style="margin-left:83px">
                  <el-radio :disabled="ashikomi" label="1">是</el-radio>
                  <el-radio :disabled="ashikomi" label="2">否</el-radio>
                </el-radio-group>
              </el-form-item>
            </div>
          </div>
          <el-row v-if="bshow">
            <el-container>
              <el-aside width="150px" class="bside">决算内容</el-aside>
              <el-main>
                <el-form-item label="1. 残数是否可以如期消耗(若选择否请填写下记两点)" prop="canradio">
                  <el-radio-group v-model="Inspection.canradio" @change="canChang">
                    <el-radio :disabled="bshikomi" label="1">是</el-radio>
                    <el-radio :disabled="bshikomi" label="2">否</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="1.1 是否开放全球使用权限" style="margin-left:10px" prop="openToWorld">
                  <el-radio :disabled="ashikomi" v-model="Inspection.openToWorld" label="1">是</el-radio>
                  <el-radio :disabled="ashikomi" v-model="Inspection.openToWorld" label="0">否</el-radio>
                </el-form-item>
                <el-form-item prop="answerText3">
                  <el-input :disabled="ashikomi" v-model="Inspection.answerText3" size="mini" type="text" style="width:270px" placeholder="若不开通权限请描述理由"/>
                </el-form-item>
                <el-form-item label="1.2 是否延期" style="margin-left:10px" prop="delayToCancel">
                  <el-radio-group :disabled="ashikomi" v-model="Inspection.delayToCancel" style="margin-left:83px">
                    <el-radio label="1">是</el-radio>
                    <el-radio label="2">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-main>
            </el-container>
          </el-row>
          <el-row v-if="cshow">
            <el-container>
              <el-aside width="150px" class="cside"><span style="color:red">*</span>点检说明</el-aside>
              <el-main>
                <el-form-item prop="answerText">
                  <el-input :autosize="{ minRows: 8, maxRows: 11}" v-model="Inspection.answerText" type="textarea" class="answer" placeholder="请输入点检说明"/>
                </el-form-item>
              </el-main>
            </el-container>
          </el-row>
          <el-row v-if="dshow">
            <el-container>
              <el-aside width="150px" class="bside">决算内容</el-aside>
              <el-main>
                <el-form-item label="1. 残数是否可以如期消耗(若选择否请填写下记两点)" prop="canradio">
                  <el-radio-group v-model="Inspection.canradio">
                    <el-radio :disabled="bshikomi" label="1">是</el-radio>
                    <el-radio :disabled="bshikomi" label="2">否</el-radio>
                  </el-radio-group>
                </el-form-item>
                <el-form-item label="1.1 是否开放全球使用权限" style="margin-left:10px" prop="openToWorld">
                  <el-radio v-model="Inspection.openToWorld" label="1">是</el-radio>
                  <el-radio v-model="Inspection.openToWorld" label="0">否</el-radio>
                </el-form-item>
                <el-form-item prop="answerText3">
                  <el-input :disabled="ashikomi" v-model="Inspection.answerText3" size="mini" type="text" style="width:270px" placeholder="若不开通权限请描述理由"/>
                </el-form-item>
                <el-form-item label="1.2 是否延期" style="margin-left:10px">
                  <el-radio-group v-model="Inspection.delayToCancel" style="margin-left:83px">
                    <el-radio label="1">是</el-radio>
                    <el-radio label="2">否</el-radio>
                  </el-radio-group>
                </el-form-item>
              </el-main>
            </el-container>
          </el-row>
          <el-upload
            :action="actionUrl"
            :before-upload="beforeUploadFile"
            class="upload-demo"
            drag
            multiple>
            <i class="el-icon-upload"/>
            <!-- <div class="el-upload__text" >支持xlsx格式</div> -->
            <div v-if="file !== null" class="el-upload__text" > {{ file.name }} </div>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          </el-upload>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="dialogInspection = false">保存</el-button>
          <el-button type="primary" @click="dialogInspection = false">提交</el-button>
        </div>
      </el-dialog>
      <el-row>
        <el-button-group>
          <el-button v-permission="['560729']" type="success" size="mini" plain icon="el-icon-upload" @click="uploadFileDialogVisible = true">导入日本回复文件</el-button>
          <el-button v-permission="['560729']" type="success" size="mini" plain icon="el-icon-upload" @click="uploadCNFileDialog = true">导入中国回复文件</el-button>
          <el-button v-permission="['560729']" type="primary" size="mini" plain icon="el-icon-plus" @click="reset('addForm')">新增</el-button>
          <el-button v-permission="['560729']" :disabled="dis" size="mini" type="primary" plain icon="el-icon-edit-outline" @click="check">修改</el-button>
          <el-button v-permission="['560729']" :disabled="dis" size="mini" type="primary" plain icon="el-icon-close" @click="delShikomi">删除</el-button>
          <el-button v-permission="['560729']" plain size="mini" type="primary" icon="el-icon-plus" @click="addCustomer">增加许可客户</el-button>
          <el-button v-permission="['560729']" plain size="mini" type="primary" icon="el-icon-plus" @click="addSerialModel">增加复数型号</el-button>
          <el-button v-permission="['560729']" plain size="mini" type="primary" icon="el-icon-plus" @click="sendInspec">推送点检</el-button>
          <el-button v-permission="['560729']" plain size="mini" type="primary" icon="el-icon-plus" @click="uploadInspectionDialog = true">发送点检邮件</el-button>
          <el-button v-permission="['560729']" :loading="exportLoading" plain size="mini" type="primary" icon="el-icon-download" @click="exportShikomi">导出</el-button>
        </el-button-group>
      </el-row>
      <div>
        <el-table
          v-loading="listLoading"
          ref="multipleTable"
          :data="tableData"
          tooltip-effect="dark"
          class="multipleTable"
          border
          max-height="700"
          style="width: 100%"
          size="mini"
          stripe
          @selection-change="handleSelectionChange">
          <el-table-column
            type="selection"
            width="55"/>
          <el-table-column
            v-if="show"
            prop="id"
            label="id"/>
          <el-table-column
            fixed
            prop="modelNo"
            show-overflow-tooltip
            label="型号"
            width="150px">
            <template slot-scope="scope">
              <el-link :underline="false" @click="handleClick(scope.row)">{{ scope.row.modelNo }}</el-link>
            </template>
          </el-table-column>

          <el-table-column
            :formatter="formatSupplier"
            fixed
            prop="supplierCode"
            label="备库地点"
            width="100px"/>

          <el-table-column
            fixed
            prop="shikomiNo"
            label="shikomi号"
            width="110px">
            <template slot-scope="scope">
              <el-link :underline="false" @click="getInspectionData(scope.row)">{{ scope.row.shikomiNo }}</el-link>
            </template>
          </el-table-column>

          <el-table-column
            :formatter="formatmodelType"
            fixed
            prop="mainModelFlag"
            label="型号类型"/>

          <!-- <el-table-column
            fixed
            :formatter="formatmodelType"
            prop="modelType"
            label="型号类型"/> -->

          <el-table-column
            fixed
            prop="applyNo"
            label="申请号"/>

          <el-table-column
            fixed
            prop="applyQty"
            label="申请数量"/>

          <el-table-column
            :formatter="formatDate"
            prop="applyTime"
            label="申请时间"/>

          <el-table-column
            :formatter="formatStatus"
            prop="status"
            label="状态"/>

          <el-table-column
            fixed
            prop="remainQty"
            label="SHIKOMI残数"/>

          <el-table-column
            prop="qtyOrdPre"
            label="已预约数量"/>

          <el-table-column
            :formatter="formatDate3"
            prop="registDate"
            width="85px"
            label="初回出荷日"/>

          <el-table-column
            :formatter="DayFormatter"
            prop="asseDays"
            label="组装工作日"/>
          <el-table-column
            :formatter="DayFormatter"
            prop="partPrepareDays"
            label="部品准备工作日"/>

          <el-table-column
            show-overflow-tooltip
            prop="customerNos"
            label="客户代码">
            <template slot-scope="scope">
              <el-link :underline="false" @click="getCustomerData(scope.row)">{{ formatterCustomer(scope.row.customerNos) }}</el-link>
            </template>
          </el-table-column>

          <el-table-column
            prop="customerNo"
            label="归属客户"/>

          <el-table-column
            :formatter="formatClassType"
            prop="classType"
            label="SHIKOMI区分"/>
          <el-table-column
            :formatter="formatClassCode"
            prop="classCode"
            label="SHIKOMI客户范围"/>

          <el-table-column
            prop="indCode"
            label="行业代码"/>

          <el-table-column
            prop="subsidiaryCode"
            label="子公司"/>

          <el-table-column
            prop="companyCode"
            label="公司"/>

          <el-table-column
            prop="branchCode"
            label="部门"/>

          <el-table-column
            prop="deptNo"
            label="营业所">
            <template slot-scope="scope">
              <span>{{ scope.row.deptName }}</span>
            </template>
          </el-table-column>

          <el-table-column
            prop="qtyNoord"
            label="未订货月数"/>

          <el-table-column
            prop="qtyWarning"
            label="警告数量"/>

          <el-table-column
            :formatter="isWaringFormatter"
            prop="isWarning"
            label="是否警告"/>

          <el-table-column
            prop="eprice"
            label="E价"/>

          <el-table-column
            prop="priceLot"
            label="多段价格"/>

          <el-table-column
            prop="lotQty"
            label="使用中LOT数"/>

          <el-table-column
            show-overflow-tooltip
            prop="answerText"
            label="回复说明"/>

          <el-table-column
            prop="remark"
            show-overflow-tooltip
            label="备注"/>

          <el-table-column
            prop="projectNo"
            label="项目管理号/机型"/>

          <el-table-column
            prop="pplNo"
            label="PPL编号"/>

          <el-table-column
            :formatter="formatRohs"
            prop="rohs"
            label="ROHS10"/>

          <el-table-column
            :formatter="formatDate"
            prop="dlvDate"
            label="预计下单时间"
            show-overflow-tooltip/>

          <el-table-column
            :formatter="formatDate2"
            prop="planUseDate"
            label="预计晚使用月份"
            show-overflow-tooltip/>

          <el-table-column
            prop="applicantName"
            label="申请担当"/>
          <el-table-column
            show-overflow-tooltip
            prop="applicantEmail"
            label="担当邮箱"/>
          <el-table-column
            prop="approverName"
            label="负责人"/>
          <el-table-column
            show-overflow-tooltip
            prop="approverEmail"
            label="负责人邮箱"/>

          <el-table-column
            prop="qtyOnhand"
            label="在库数量"/>

          <el-table-column
            prop="qtyPO"
            label="在途数量"/>

          <el-table-column
            :formatter="formatInspectStatus"
            prop="inspectStatus"
            label="点检状态"/>
          <el-table-column
            :formatter="formatDate"
            prop="inspectSendTime"
            label="点检发送时间"/>
          <el-table-column
            :formatter="formatDate"
            prop="inspectAnswerTime"
            label="点检回复时间"/>
          <el-table-column
            show-overflow-tooltip
            prop="inspectAnswerText"
            label="点检回复说明"/>
          <el-table-column
            prop="inspectDaily"
            label="是否每天点检"/>
          <el-table-column
            prop="inspectQty"
            label="点检数量"/>
          <el-table-column
            :formatter="formatInspectType"
            prop="inspectType"
            label="点检类型"/>
          <el-table-column
            :formatter="formatInspectApplyType"
            prop="inspectApplyType"
            label="点检申请类型"/>

          <el-table-column
            :formatter="formatDate"
            prop="lastOrdDate"
            label="最近下单时间"/>

          <el-table-column
            :formatter="formatDate"
            prop="updateTime"
            label="更新时间"
            show-overflow-tooltip />
          <el-table-column
            prop="updateUser"
            label="操作人"/>
        </el-table>
      </div>
      <el-pagination
        :page-sizes="[20, 50, 100, 200, 500]"
        :current-page="condition.pageNumber"
        :page-size="condition.pageSize"
        :total="tablePage.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handlePageSizeChange"
        @current-change="handlePageChange"/>
      <el-divider />
    </el-main>
  </el-container>
</template>

<script>
import { findList, saveShikomidata, savecusdata, uploadFile, saveModel, findCustomerDataByNo, sendInspectionReport,
  findInspectionData, inspectProcess, exportShikomi, importCNFile, delShikomiCustomerById, delShikomiByNo, getShikomiSupplier, checkShikomiInspectionByDept, calcShikomiInspection, checkStatus } from '@/api/stock/shikomi'
import { getDictDataByPid } from '@/api/common/dict'
import { listApplicant } from '@/api/common/employee'
import { findDeptsToTree } from '@/api/organ'
import { findSupplierByIdOrName } from '@/api/common/supplier'
import { getCustomerByNo } from '@/api/common/customer'
import ProductSearch from '../../product/index'
import { getDeptAllData } from '@/api/common/department'
import DepartmentHL from '@/components/departmentHL'
import { getDataCodesTree } from '@/api/common/dict'
export default {
  name: 'ShikomiManage',
  components: {
    ProductSearch,
    DepartmentHL
  },
  data() {
    return {
      saveLoading: false,
      productVisible: false,
      shikomi: false,
      searchMoreForm: false,
      insepec: true,
      ashow: true,
      bshow: false,
      cshow: false,
      dshow: false,
      ashikomi: false,
      bshikomi: false,
      warning: false,
      exportLoading: false,
      dis: true,
      show: false,
      dialogaddmodel: false,
      dialogaddcustomer: false,
      uploadFileDialogVisible: false,
      dialogaddVisible: false,
      dialogFormVisible: false,
      SupplierdialogVisible: false,
      uploadStatus: false,
      dialogInspection: false,
      dialogInspectVisible: false,
      dialogApplicantForm: false,
      uploadCNFileDialog: false,
      inspectionDialog: false,
      uploadInspectionDialog: false,
      actionUrl: '',
      updateTime: '',
      sendTime: '',
      answerTime: '',
      searchTime: '',
      file: null,
      listLoading: false,
      models: {
        shikomiNo: '',
        modelNo: '',
        serialModel: ''
      },
      supplierForm: {
        companyId: '',
        name: ''
      },
      Inspection: {
        id: '',
        shikomiNo: '',
        inspectType: '1',
        qtyWarning: '',
        warnQtyOptCode: '',
        demandQtyOptCode: '',
        draide: '',
        answerText1: '',
        answerText2: '',
        canradio: '',
        openToWorld: '',
        answerText3: '',
        delayToCancel: '',
        answerText: ''
      },
      shikomiRule: {
        modelNo: [
          { required: true, message: '请输入型号', trigger: 'blur' }
        ],
        shikomiNo: [
          { required: true, message: '请输入SHIKOMI号', trigger: 'blur' }
        ],
        supplierCode: [
          { required: true, message: '请选择备库地点', trigger: 'change' }
        ]
      },
      applicantForm: {
        id: '',
        name: '',
        pageNumber: 1,
        pageSize: 20
      },
      applicantPage: {
        pageNumber: 1,
        pageSize: 20,
        total: 0
      },
      index: 0,
      applicantData: [],
      rule: {
        shikomiNo: [
          { required: true, message: '请输入Shikomi号', trigger: 'blur' }
        ],
        warnQtyOptCode: [
          { required: true, message: '请选择是否追加SHIKOMI', trigger: 'change' }
        ],
        answerText1: [
          { required: true, message: '请输入不追加SHIKOMI的理由', trigger: 'blur' }
        ],
        answerText2: [
          { required: true, message: '请输入今后的交货期对策', trigger: 'blur' }
        ],
        canradio: [
          { required: true, message: '请选择残数是否可用如期消耗', trigger: 'change' }
        ],
        answerText: [
          { required: true, message: '请输入点检说明', trigger: 'blur' }
        ],
        demandQtyOptCode: [
          { required: true, message: '请选择需求是否如期消耗', trigger: 'change' }
        ],
        draide: [
          { required: true, message: '请选择是否再次到期', trigger: 'change' }
        ]
      },
      handleInspectOptions: [
        { value: 1, label: '已发供应商' },
        { value: 2, label: '供应商已终止' },
        { value: 3, label: '供应商不能终止' }
      ],
      WarningTypes: [
        { value: true, label: '是' },
        { value: false, label: '否' }
      ],
      rohsTypes: [
        { value: 1, label: '是' },
        { value: 0, label: '否' }
      ],
      inspection: {
        handleType: [
          { required: true, message: '请选择处理类型', trigger: 'change' }
        ]
      },
      gridData: [],
      supplieData: [],
      addForm: {
        mainModelNo: '',
        applyTime: '',
        supplierCode: '',
        status: '1',
        modelNo: '',
        shikomiNo: '',
        dlvDate: '',
        remainQty: '',
        customerNo: '',
        answerText: '',
        remark: '',
        deptNo: '',
        classType: '',
        classCode: '',
        qtyOrdPre: '',
        applicantNo: '',
        applicantName: '',
        applicantEmail: '',
        approverNo: '',
        approverName: '',
        approverEmail: '',
        asseDays: '',
        qtyNoord: '',
        partPrepareDays: '',
        planUseDate: '',
        applyNo: '',
        indCode: '',
        lotQty: '',
        qtyWarning: '',
        priceLot: '',
        projectNo: '',
        pplNo: '',
        companyCode: '',
        isWarning: '',
        applyQty: '',
        rohs: ''
      },
      cusData: {
        shikomiNo: '',
        customerNo: ''
      },
      condition: {
        mainCustomerNo: '',
        applicantName: '',
        qtyNoord1: '',
        qtyNoord2: '',
        dateType: 1,
        startTime: '',
        endTime: '',
        isManageProduct: '',
        applyNo: '',
        applicantNo: '',
        deptNo: '',
        deptNos: [],
        supplierCodeList: [],
        modelNo: '',
        customerNo: '',
        shikomiNo: '',
        supplierCode: '',
        mainModelFlag: '',
        rohs: '',
        indCode: '',
        status: '1',
        classType: '',
        classCode: '',
        checked: '',
        pageNumber: 1,
        pageSize: 20
      },
      dateTypeData: [
        { value: 1, label: '更新时间' },
        { value: 2, label: '申请时间' },
        { value: 3, label: '预计下单时间' },
        { value: 4, label: '点检发送时间' },
        { value: 5, label: '点检回复时间' }
      ],
      manages: [
        {
          value: 1,
          label: '是'
        },
        {
          value: 0,
          label: '否'
        }
      ],
      mainModel: [
        { value: 1, label: '是' },
        { value: 0, label: '否' }
      ],
      RohsData: [
        { value: 1, label: '是' },
        { value: 0, label: '否' }
      ],
      tableData: [],
      // form: {},
      // 分页参数
      tablePage: {
        total: 0,
        currentPage: 1,
        pageSize: 20
      },
      statusCode: '2003',
      supplierCode: '2002',
      classType_typeid: '2021',
      classCode_typeid: '2022',
      modelType: '2015',
      inspectType: '2051',
      inspectStatus: '2052',
      inspectApplyType: '2053',
      inspectApplyTypeData: [],
      modelTypes: [],
      statusOptions: [],
      supplierOptions: [],
      supplierOptions2: [],
      rowvalue: 0,
      classTypes: [],
      classCodes: [],
      deptNoOptions: [],
      cpnysData: [],
      inspectTypeOptions: [],
      inspectStatusOptions: [],
      inspectData: [],
      supplierData: [],
      supplierCodeData: [],
      deptInfos: [],
      handleData: {
        handleType: '',
        supplierCode: '',
        shikomiNo: '',
        inspectAnswerText: '',
        id: ''
      }
    }
  },
  created() {
    // this.getList()
    this.getShikomiSupplier()
    this.iniData()
    this.findDeptNos()
    this.listApplicantinfo()
    this.listSupplierinfo(2)
    this.initstDeptInfo()
  },
  methods: {
    iniData() {
      getDictDataByPid(this.statusCode).then(result => {
        console.log(result)
        this.statusOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      // getDictDataByPid(this.supplierCode).then(result => {
      //   console.log(result)
      //   this.supplierOptions = result.content
      // }).catch(error => {
      //   console.log(error)
      // })
      getDictDataByPid(this.classType_typeid).then(result => {
        this.classTypes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.classCode_typeid).then(result => {
        this.classCodes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.modelType).then(result => {
        this.modelTypes = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.inspectType).then(result => {
        this.inspectTypeOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.inspectStatus).then(result => {
        this.inspectStatusOptions = result.content
      }).catch(error => {
        console.log(error)
      })
      getDictDataByPid(this.inspectApplyType).then(result => {
        this.inspectApplyTypeData = result.content
      }).catch(error => {
        console.log(error)
      })
    },
    setDateCondition() {
      if (this.searchTime === null) {
        this.condition.startTime = ''
        this.condition.endTime = ''
      } else {
        this.condition.startTime = this.searchTime[0]
        this.condition.endTime = this.searchTime[1]
      }
    },
    initstDeptInfo() {
      var deptNo = ''
      getDeptAllData(deptNo).then(result => {
        this.deptInfos = result.content
      }).catch(error => {
        console.log(error)
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
    getList(val) {
      this.listLoading = true
      this.setDateCondition()
      // var self = this
      if (val === 1) {
        this.condition.pageNumber = 1
      }
      findList(this.condition).then(result => {
        this.tableData = result.content.list
        this.tablePage.total = result.content.total
        this.listLoading = false
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    saveData(addForm) {
      this.saveLoading = true
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          saveShikomidata(this.addForm)
            .then(res => {
              this.saveLoading = false
              if (res.success) {
                this.$message.success(res.content)
                this.dialogaddVisible = false
                this.getList()
              } else {
                this.$message.error(res.message)
              }
            }).catch(error => {
              this.saveLoading = false
              console.log(error)
            })
        } else {
          this.saveLoading = false
          this.$message.error('请输入必填选项！')
          return false
        }
      })
    },
    saveCustomerdata(cusData) {
      savecusdata(this.cusData)
        .then(res => {
          if (res.success) {
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
          this.getList()
        }).catch(error => {
          console.log(error)
        })
    },
    saveModels(models) {
      saveModel(this.models).then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
        this.getList()
      }).catch(error => {
        console.log(error)
      })
    },
    delCustomer(row) {
      this.$confirm('确认要删除这条数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        delShikomiCustomerById(row.id).then(res => {
          if (res.success) {
            this.$message.success(res.content + ',请刷新')
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          console.log(error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    delShikomi() {
      this.$confirm('确认要删除这条数据吗?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'error'
      }).then(() => {
        var shikomiNo = this.$refs.multipleTable.selection[0].shikomiNo
        var supplierCode = this.$refs.multipleTable.selection[0].supplierCode
        var modelNo = this.$refs.multipleTable.selection[0].modelNo
        delShikomiByNo(shikomiNo, modelNo, supplierCode).then(res => {
          if (res.success) {
            this.$message.success(res.content)
          } else {
            this.$message.error(res.message)
          }
          this.getList()
        }).catch(error => {
          console.log(error)
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    change(e) {
      this.$forceUpdate()
    },
    searchMoreData() {
      this.searchMoreForm = !this.searchMoreForm
    },
    selectSupplier(val) {
      this.rowvalue = val
      this.SupplierdialogVisible = true
      this.listSupplierinfo()
    },
    listSupplierinfo(val) {
      const parm = { 'companyId': this.supplierForm.companyId, 'name': this.supplierForm.name }
      findSupplierByIdOrName(parm).then(res => {
        this.supplieData = res.content
        if (val === 2) {
          this.supplierCodeData = this.supplieData
        }
      }).catch(error => {
        console.info(error)
        this.$message.error(error.message)
      })
    },
    editSupplier(row) {
      if (this.rowvalue === 1) {
        this.supplierOptions2 = []
        this.supplierOptions2.push({ code: row.id, codeName: row.name })
        this.addForm.supplierCode = row.id
        this.SupplierdialogVisible = false
      } else {
        this.supplierOptions = []
        this.supplierOptions.push({ code: row.id, codeName: row.name })
        this.condition.supplierCode = row.id
        this.SupplierdialogVisible = false
      }
    },
    getShikomiSupplier() {
      getShikomiSupplier().then(res => {
        this.supplierData = res.content
      })
    },
    handleInsepct() {
      if (this.$refs.multipleTable.selection.length <= 0) {
        this.$message.warning('请选择一个点检')
        return
      }
      this.inspectionDialog = true
    },
    handleInspection() {
      this.$refs.inspec.validate((valid) => {
        if (valid) {
          this.handleData.id = this.$refs.multipleTable.selection[0].id
          this.handleData.shikomiNo = this.$refs.multipleTable.selection[0].shikomiNo
          this.handleData.supplierCode = this.$refs.multipleTable.selection[0].supplierCode
          inspectProcess(this.handleData).then(res => {
            if (res.success) {
              this.$message.success(res.content)
            } else {
              this.$message.error(res.message)
            }
          })
          this.inspectionDialog = false
        } else {
          return false
        }
      })
    },
    check() {
      this.shikomi = true
      this.dialogaddVisible = true
      this.$nextTick(() => {
        this.$refs.addForm.clearValidate()
        this.addForm = JSON.parse(JSON.stringify(this.$refs.multipleTable.selection[0]))
        const parm = { 'companyId': this.addForm.supplierCode, 'name': '' }
        findSupplierByIdOrName(parm).then(res => {
          this.supplierOptions2 = []
          this.supplierOptions2.push({ code: res.content[0].id, codeName: res.content[0].name })
        }).catch(error => {
          console.error(error)
        })
        // if (this.addForm.customerNo.indexOf(',') >= 1) {
        //   const form = this.addForm.customerNo.split(',')
        //   const data = { customerNo: form[0], modelNo: this.addForm.modelNo, pageNumber: 1, pageSize: 20 }
        //   findList(data).then(result => {
        //     if (result.content.size !== 0 && result.content.list[0].customerNo === form[0]) {
        //       this.addForm.customerNo = result.content.list[0].customerNo
        //     } else {
        //       this.addForm.customerNo = ''
        //     }
        //   }).catch(error => {
        //     console.log(error)
        //   })
        // }
        this.addForm.status = this.$refs.multipleTable.selection[0].status.toString()
      })
      // console.log(this.$refs.multipleTable.seslection[0])
    },
    exportShikomi() {
      this.exportLoading = true
      this.$message.success('已开始导出，请耐心等待')
      exportShikomi(this.condition).then(res => {
        const fileName = 'SHIKOMI.xlsx'
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
        this.exportLoading = false
      })
    },
    querySearchAsync(customerNo, cb) {
      // const cus = { customerNo: this.form.customerNo }
      getCustomerByNo(customerNo).then(data => {
        var customerArray = []
        var results = []
        customerArray = data.content
        results = customerNo ? customerArray.filter(this.createStateFilter(customerNo)) : customerArray

        if (results.length <= 0) {
          results = customerNo ? customerArray.filter(this.createFilter(customerNo)) : customerArray
        }
        cb(results)
        // console.log(results)
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
      this.addForm.customerNo = item.customerNo
    },
    customerSelect(item) {
      this.cusData.customerNo = item.customerNo
    },
    ChangeInput(item) {
      this.condition.customerNo = item.customerNo
    },
    // 换页
    handlePageChange(newCurrent) {
      this.condition.pageNumber = newCurrent
      this.getList()
    },
    // 改变每页条数
    handlePageSizeChange(newSize) {
      this.condition.pageSize = newSize
      this.getList()
    },
    PageChange(newCurrent) {
      this.applicantPage.pageNumber = newCurrent
      this.listApplicantinfo()
    },
    // 改变每页条数
    PageSizeChange(newSize) {
      this.applicantPage.pageSize = newSize
      this.listApplicantinfo()
    },
    reset(addForm) {
      this.shikomi = false
      this.dialogaddVisible = true
      this.addForm.id = ''
      this.$nextTick(() => {
        this.$refs.addForm.resetFields()
      })
      this.addForm.dlvDate = null
    },
    handleSelectionChange(val) {
      this.multipleSelection = val
      if (val.length > 1) {
        this.$refs.multipleTable.clearSelection()
        this.$refs.multipleTable.toggleRowSelection(val.pop())
      }
      if (this.multipleSelection.length > 0) {
        this.dis = false
        this.insepec = false
      } else {
        this.dis = true
        this.insepec = true
      }
    },
    closeClick() {
      this.file = null
      this.uploadFileDialogVisible = false
      this.uploadCNFileDialog = false
      this.uploadInspectionDialog = false
    },
    closeDialog() {
      this.getList()
    },
    addCustomer() {
      this.dialogaddcustomer = true
      this.$nextTick(() => {
        this.$refs.cusData.resetFields()
        this.cusData.shikomiNo = this.$refs.multipleTable.selection[0].shikomiNo
      })
    },
    getCustomerData(row) {
      this.dialogFormVisible = true
      findCustomerDataByNo(row.shikomiNo).then(res => {
        this.gridData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    getInspectionData(row) {
      this.dialogInspectVisible = true
      findInspectionData(row.id).then(res => {
        this.inspectData = res.content
      }).catch(error => {
        console.log(error)
      })
    },
    addSerialModel() {
      this.dialogaddmodel = true
      this.$nextTick(() => {
        this.$refs.models.resetFields()
        this.models.shikomiNo = this.$refs.multipleTable.selection[0].shikomiNo
        this.models.serialModel = this.$refs.multipleTable.selection[0].modelNo
      })
    },
    selectApplicantNo(index) {
      this.index = index
      this.dialogApplicantForm = true
    },
    listApplicantinfo() {
      this.applicantForm.pageSize = this.applicantPage.pageSize
      this.applicantForm.pageNumber = this.applicantPage.pageNumber
      listApplicant(this.applicantForm).then(res => {
        this.applicantData = res.content.list
        this.applicantPage.total = res.content.total
      }).catch(error => {
        console.log(error)
      })
    },
    edit(row) {
      if (this.index === 1) {
        this.addForm.applicantNo = row.id
        this.addForm.applicantName = row.name
      } else if (this.index === 2) {
        this.addForm.approverNo = row.id
        this.addForm.approverName = row.name
      }
      this.dialogApplicantForm = false
    },
    sendInspectionInfo(Inspection) {
      const qty = this.Inspection.qtyWarning
      if (this.Inspection.warnQtyOptCode === '0' || parseInt(qty) > 0 || this.Inspection.demandQtyOptCode === '1' || this.Inspection.draide === '2') {
        if (this.Inspection.demandQtyOptCode === '') {
          this.$refs[Inspection].validateField('demandQtyOptCode')
        }
        this.$refs[Inspection].validateField(['shikomiNo'], (shikomi) => {
          if (!shikomi) {
            if (this.Inspection.inspectType === '1') {
              this.Inspection.answerText = '1.' + this.Inspection.answerText1 + ' 2.' + this.Inspection.answerText2 + ' 3.' + this.Inspection.answerText3
            } else if (this.Inspection.inspectType === '8') {
              this.Inspection.answerText = this.Inspection.answerText
            } else {
              this.Inspection.answerText = this.Inspection.answerText3
            }

            sendInspectionReport(this.Inspection).then(res => {
              if (res.success) {
                this.$message.success(res.content)
                this.dialogInspection = false
              } else {
                this.dialogInspection = false
                this.$message.error(res.message)
              }
            }).catch(error => {
              console.log(error)
            })
          } else {
            return false
          }
        })
      } else {
        this.$refs[Inspection].validate((valid) => {
          if (valid) {
            if (this.Inspection.inspectType === '1') {
              this.Inspection.answerText = '1.' + this.Inspection.answerText1 + ' 2.' + this.Inspection.answerText2 + ' 3.' + this.Inspection.answerText3
            } else if (this.Inspection.inspectType === '8') {
              this.Inspection.answerText = this.Inspection.answerText
            } else {
              this.Inspection.answerText = this.Inspection.answerText3
            }
            sendInspectionReport(this.Inspection).then(res => {
              if (res.success) {
                this.$message.success(res.content)
                this.dialogInspection = false
              } else {
                this.dialogInspection = false
                this.$message.error(res.message)
              }
            }).catch(error => {
              console.log(error)
            })
          } else {
            this.dialogInspection = true
            return false
          }
        })
      }
    },
    sendInspection() {
      this.dialogInspection = true
      this.$nextTick(() => {
        this.selectChange('1')
        this.Inspection.id = this.$refs.multipleTable.selection[0].id
        this.Inspection.shikomiNo = this.$refs.multipleTable.selection[0].shikomiNo
      })
    },
    checkShikomiInspection(formData) {
      this.$message.success('正在执行，请耐心等待~')
      calcShikomiInspection(formData).then(res => {
        console.log(res.message)
      }).catch(error => {
        console.log(error)
      })
    },
    sendMail() {
      checkShikomiInspectionByDept().then(res => {
        if (res.success) {
          this.$message.success(res.content)
        } else {
          this.$message.error(res.message)
        }
      }).catch(error => {
        console.log(error)
      })
    },
    checkStatus() {
      checkStatus().then(res => {
        this.$message.success(res.content)
      })
    },
    sendInspec() {
      this.$message.success('已开始推送点检~')
      sendInspectionReport().then(res => {
        console.log(res.message)
      }).catch(error => {
        console.log(error)
      })
    },
    formatterCustomer(list) {
      var data = ''
      for (let i = 0; i < list.length; i++) {
        if (data === '') {
          data = data + list[i]
        } else {
          data = data + ',' + list[i]
        }
      }
      return data
    },
    selectChange(val) {
      const value = this.Inspection.shikomiNo
      const id = this.Inspection.id
      if (val === '1') {
        this.ashow = true
        this.bshow = false
        this.cshow = false
        this.dshow = false
        this.ashikomi = false
        this.warning = false
        this.bshikomi = false
        this.Inspection = this.$options.data().Inspection
        this.Inspection.inspectType = val
        this.$nextTick(() => {
          this.$refs.Inspection.clearValidate()
        })
      }
      if (val === '2') {
        this.ashow = false
        this.bshow = true
        this.cshow = false
        this.dshow = false
        this.bshikomi = false
        this.ashikomi = false
        this.warning = false
        this.Inspection = this.$options.data().Inspection
        this.Inspection.inspectType = val
        this.$nextTick(() => {
          this.$refs.Inspection.clearValidate()
        })
      }
      if (val === '8') {
        this.cshow = true
        this.ashow = false
        this.bshow = false
        this.dshow = false
        this.Inspection = this.$options.data().Inspection
        this.Inspection.inspectType = val
      }
      if (val === '6' | val === '7') {
        this.dshow = true
        this.cshow = false
        this.ashow = false
        this.bshow = false
        this.bshikomi = false
        this.ashikomi = false
        this.Inspection = this.$options.data().Inspection
        this.Inspection.inspectType = val
        this.$nextTick(() => {
          this.$refs.Inspection.clearValidate()
        })
      }
      if (val === '3' | val === '4' | val === '5') {
        this.dshow = false
        this.cshow = false
        this.ashow = false
        this.bshow = false
        this.Inspection = this.$options.data().Inspection
        this.Inspection.inspectType = val
      }
      this.Inspection.shikomiNo = value
      this.Inspection.id = id
    },
    shikomichange(val) {
      if (val === '1') {
        this.ashikomi = false
        this.warning = false
        this.bshikomi = false
      } else {
        this.ashikomi = true
        this.warning = true
        this.bshikomi = true
        const value = this.Inspection.shikomiNo
        this.$refs.Inspection.resetFields()
        this.Inspection.shikomiNo = value
        this.Inspection.warnQtyOptCode = '0'
      }
    },
    warnChange(val) {
      if (val === '0') {
        this.warning = true
        this.Inspection.qtyWarning = ''
      } else {
        this.warning = false
      }
    },
    demandChange(val) {
      if (val === '1') {
        this.ashikomi = true
        this.bshikomi = true
        this.Inspection.canradio = ''
        this.Inspection.openToWorld = ''
        this.Inspection.answerText3 = ''
        this.Inspection.delayToCancel = ''
        this.Inspection.inspectType = '2'
        this.$nextTick(() => {
          this.$refs.Inspection.clearValidate()
        })
      } else {
        this.ashikomi = false
        this.bshikomi = false
      }
    },
    canChang(val) {
      if (val === '1') {
        this.Inspection.openToWorld = ''
        this.Inspection.answerText3 = ''
        this.Inspection.delayToCancel = ''
        this.ashikomi = true
      } else {
        this.bshikomi = false
        this.ashikomi = false
      }
    },
    qtycheck() {
      this.$refs.Inspection.clearValidate()
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
    importData(val) {
      if (this.uploadStatus === true) {
        this.$message.error('还在上传中，请不要重复点击！')
        return
      }
      this.uploadStatus = true
      const file = this.file
      if (!this.file) { // 防止空传
        this.uploadStatus = false
        this.$message.error('请选择文件上传')
        return false
      }
      const formData = new FormData() // form表单格式提交数据
      formData.append('file', file)
      if (val === 1) {
        this._importEmployeeAssistant(formData)
      }
      if (val === 2) {
        this.importFile(formData)
      }
      if (val === 3) {
        this.uploadInspectionDialog = false
        this.checkShikomiInspection(formData)
      }
      this.uploadStatus = false
    },
    importFile(formData) {
      importCNFile(formData)
        .then(res => {
          if (res.success) {
            this.file = null
            this.$message.success(res.content)
            this.uploadCNFileDialog = false
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    _importEmployeeAssistant(formData) {
      uploadFile(formData)
        .then(res => {
          // console.log(res)
          if (res.success) {
            this.file = null
            this.$message.success(res.content)
            this.uploadFileDialogVisible = false
          } else {
            this.$message.error(res.message)
          }
        }).catch(error => {
          this.$message.error(error + '请稍后再常试')
        })
    },
    DayFormatter(row, column) {
      const data = row[column.property]
      if (data == null || data === 0) {
        return 'TBD'
      }
      return data
    },
    formatDate(row, column) {
      // 获取单元格数据
      const data = row[column.property]
      if (data == null) {
        return null
      }
      const dt = new Date(data)
      // return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
      return this.dayjs(dt).format('YYYY-MM-DD HH:mm')
    },
    formatDate2(row, column) {
      // 获取单元格数据
      const data = row[column.property]
      if (data == null) {
        return null
      }
      const dt = new Date(data)
      // return dt.getFullYear() + '-' + (dt.getMonth() + 1) + '-' + dt.getDate() + ' ' + dt.getHours() + ':' + dt.getMinutes() + ':' + dt.getSeconds()
      return this.dayjs(dt).format('YYYY-MM-DD')
    },
    formatDate3(row, column) {
      // 获取单元格数据
      const data = row[column.property]
      if (data == null) {
        return 'TBD'
      }
      const dt = new Date(data)
      return this.dayjs(dt).format('YYYY-MM-DD')
    },
    descFormatter(data, value) {
      if (value === null) {
        return value
      }
      for (const i in data) {
        var item = data[i]
        if (item.code === value.toString()) {
          return item.codeName
        }
      }
      return value
    },
    formatRohs(row, column, cellValue, index, menu) {
      if (cellValue === null) {
        return '否'
      }
      if (cellValue === '1') {
        return '是'
      } else {
        return '否'
      }
    },
    isWaringFormatter(row, column, cellValue, index, menu) {
      if (cellValue === null) {
        return '否'
      }
      if (cellValue) {
        return '是'
      } else {
        return '否'
      }
    },
    formatClassCode(row, column, cellValue, index, menu) {
      return this.descFormatter(this.classCodes, cellValue)
    },
    formatClassType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.classTypes, cellValue)
    },
    formatStatus(row, column, cellValue, index, menu) {
      return this.descFormatter(this.statusOptions, cellValue)
    },
    formatSupplier(row, column, cellValue, index, menu) {
      if (cellValue === null) {
        return cellValue
      }
      const data = this.supplierCodeData
      for (const i in data) {
        var item = data[i]
        if (item.id === cellValue.toString()) {
          return item.name
        }
      }
      return cellValue
      // return this.descFormatter(this.supplierOptions, cellValue)
    },
    deptformatter(row, column, cellValue, index, menu) {
      // if (row.deptNo != null) {
      //   return this.descFormatter(this.deptInfos, cellValue)
      // }
      if (cellValue === null) {
        return cellValue
      }
      for (const i in this.deptInfos) {
        var item = this.deptInfos[i]
        if (item.id === cellValue.toString()) {
          return item.name
        }
      }
      return cellValue
    },
    formatmodelFlag(row, column, cellValue, index, menu) {
      if (cellValue === null) {
        return cellValue
      }
      if (cellValue === 1) {
        return '主型号'
      }
      if (cellValue === 0) {
        return '复数型号'
      }
    },
    formatmodelType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.modelTypes, cellValue)
    },
    formatInspectStatus(row, column, cellValue, index, menu) {
      return this.descFormatter(this.inspectStatusOptions, cellValue)
    },
    formatInspectType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.inspectTypeOptions, cellValue)
    },
    formatInspectApplyType(row, column, cellValue, index, menu) {
      return this.descFormatter(this.inspectApplyTypeData, cellValue)
    },
    findDeptNos() {
      // 第一步: 获取公司字典数据
      getDataCodesTree('9005').then(res => {
        if (!res.success) {
          this.$message.error('获取部门异常.')
          return
        }
        // 保存有权限的部门列表
        this.tempDeptNo = res.content
      })

      // 第二步: 获取营业所树形数据
      findDeptsToTree().then(res => {
        this.deptNoOptions = res.content
        if (!res.success) {
          return
        }

        // 第三步: 标记匹配的营业所
        for (var j = 0; j < this.deptNoOptions.length; j++) {
          for (var k = 0; k < this.tempDeptNo.length; k++) {
            if (this.deptNoOptions[j].id === this.tempDeptNo[k].code) {
              this.deptNoOptions[j].flag = true
              this.deptNoOptions[j].sort = j
              if (this.tempDeptNo[k].extNote1 === 'hideChildren') {
                this.deptNoOptions[j].children = undefined
                this.deptNoOptions[j].sort = 999 // 设置一个较大的值，确保这些节点排在最后
              }
            }
          }
        }

        // 第四步: 过滤掉不匹配的营业所
        for (var j2 = 0; j2 < this.deptNoOptions.length; j2++) {
          if (!this.deptNoOptions[j2].flag) {
            this.deptNoOptions.splice(j2, 1)
            j2--
          }
        }
        // 排序，从小到大
        this.deptNoOptions.sort(function(a, b) {
          return a.sort - b.sort
        })
        // 第五步: 如果数据为空,重新获取
        if (res.content.length === 0) {
          this.findDeptNos()
          return
        }
      })
    },
    handleDeptChange(deptNos) {
      this.condition.deptNos = deptNos
    },
    deptChange(value) {
      this.addForm.deptNo = value[value.length - 1]
    }

  }
}
</script>

<style scoped>

  .search-title-content{
    margin-top: -30px;
    /* position: fixed; */
  }

  .product-search-content{
    margin-top: 80px;
  }

  .multipleTable /deep/ thead th .el-checkbox{
    visibility: hidden;
  }

  .select /deep/ .el-autocomplete-suggestion {
  width: auto!important;
  }

  .font{
    font-size: 20px;
    font-weight: bold;
  }
  .answer{
    margin-top:-20px;
    width:550px;
    margin-left:-18px
  }
  .aside{
    height: 300px;
    text-align: center;
    line-height: 250px;
    font-weight: bold;
  }
  .bside{
    height: 160px;
    text-align: center;
    line-height: 120px;
    font-weight: bold;
  }
  .cside{
    height: 158px;
    text-align: center;
    line-height: 120px;
    font-weight: bold;
  }
  .waring-span{
    background-color: #eef1f6;
    width: 150px;
    height: 300px;
    line-height: 250px;
    font-weight: bold;
    font-size: 18px;
    text-align: center;
    float: left;
  }
  .waring-info{
    float: left;
    width: 600px;
    margin-left: 20px;
  }
  .waring{
   float: left;
   height: 320px;
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
  .el-table /deep/ .el-table__body-wrapper{
  z-index: 2
}

</style>
