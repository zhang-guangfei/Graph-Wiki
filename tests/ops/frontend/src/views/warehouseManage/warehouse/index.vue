<template>
  <div class="app-container">
    <el-card style="height:880px;">
      <!-- 搜索栏 -->
      <el-row :gutter="20">
        <el-col :span="3">
          <el-input
            v-model="queryCondition.condition.warehouseCode"
            placeholder="仓库代码"
            clearable
            size="mini"
            @clear="getTableDataEvent"
            @keyup.enter.native="getTableDataEvent"
          />
        </el-col>
        <el-col :span="3">
          <el-input
            v-model="queryCondition.condition.warehouseName"
            placeholder="仓库名称"
            clearable
            size="mini"
            @clear="getTableDataEvent"
            @keyup.enter.native="getTableDataEvent"
          />
        </el-col>
        <el-col :span="3">
          <template>
            <el-select
              v-model="queryCondition.condition.warehouseType"
              placeholder="仓库类别"
              clearable
              size="mini"
              @change="getTableDataEvent"
            >
              <el-option
                v-for="item in menuData.warehouseTypeMenu"
                :key="item.code"
                :label="item.desc"
                :value="item.code"
              />
            </el-select>
          </template>
        </el-col>
        <el-col :span="3">
          <el-checkbox v-model="checkbox" @change="checkboxClick()">是否有效</el-checkbox>
        </el-col>
        <el-col :span="3">
          <el-button
            v-permission="['611878']"
            icon="el-icon-search"
            size="mini"
            style="margin-left: -80px"
            @click="getTableDataEvent()"
          />
        </el-col>
      </el-row>
      <el-divider/>
      <!-- 操作按钮 -->
      <el-row :gutter="20">
        <el-col :span="1.5">
          <el-button
            v-permission="['676964']"
            type="primary"
            size="mini"
            @click="showAddDialog()"
          >添加
          </el-button>
        </el-col>
        <!-- <el-col :span="1.5">
          <el-button
            v-permission="['676964']"
            type="primary"
            size="mini"
            @click="deletesEvent()"
          >无效
          </el-button>
        </el-col> -->
      </el-row>
      <!-- 表格区域 -->
      <el-table
        ref="multipleTable"
        :data="tableData.list"
        :header-cell-style="{padding: '1px'}"
        :cell-style="{padding: '5px 5px'}"
        border
        stripe
        height="650"
      >
        <!-- 表头字段 -->
        <template v-for="column in tableColumns">
          <el-table-column
            :key="column.prop"
            :prop="column.prop"
            :label="column.label"
            :type="column.type"
            :width="column.width"
            :formatter="column.formatter"
            show-overflow-tooltip
          />
        </template>
        <!--操作栏 -->
        <el-table-column
          label="操作"
          width="180px"
          fixed="right"
        >
          <template slot-scope="scope">
            <!-- 多地址 -->
            <el-col :span="8">
              <el-button
                type="primary"
                icon="el-icon-add-location"
                size="mini"
                @click="showMultAddressDialog(scope.row)"
              />
            </el-col>
            <!-- 修改 -->
            <el-col :span="8">
              <el-button
                v-permission="['676964']"
                type="primary"
                icon="el-icon-edit"
                size="mini"
                @click="showEditDialog(scope.row)"
              />
            </el-col>
            <!-- 删除 -->
            <!-- <el-col :span="8">
              <el-button
                v-permission="['676964']"
                type="danger"
                icon="el-icon-delete"
                size="mini"
                @click="deleteEvent(scope.row)"
              />
            </el-col> -->
            <el-col :span="8">
              <el-switch
                v-permission="['676964']"
                v-model="scope.row.delflag"
                active-color="#ff4949"
                inactive-color="#13ce66"
                @change="changeIsDelFlag(scope.row)"/>
            </el-col>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页工具 -->
      <el-pagination
        :current-page="tableData.pageNum"
        :page-sizes="[ 15, 20, 50, 100]"
        :page-size="tableData.pageSize"
        :total="tableData.total"
        background
        layout="total, sizes, prev, pager, next"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>

    <!-- 弹出层区域 -->
    <!-- 添加 -->
    <el-dialog
      :visible.sync="addDialog.display"
      title="添加"
      width="800px"
      @close="addFormReset"
    >
      <el-form
        ref="addFormRef"
        :rules="rules"
        :model="addDialog.addForm"
        label-width="90px"
      >
        <el-divider content-position="left">仓库信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="仓库代码: "
              prop="warehouseCode"
            >
              <el-input v-model="addDialog.addForm.warehouseCode"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="上级代码: "
              prop="parentCode"
            >
              <template>
                <el-select
                  v-model="addDialog.addForm.parentCode"
                  placeholder="上级代码"
                >
                  <el-option
                    v-for="item in menuData.parentCode"
                    :key="item.code"
                    :label="item.desc"
                    :value="item.code"
                  />
                </el-select>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="仓库名称: "
              prop="warehouseName"
            >
              <el-input v-model="addDialog.addForm.warehouseName"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="仓库类别: "
              prop="warehouseType"
            >
              <el-select
                v-model="addDialog.addForm.warehouseType"
                placeholder="仓库类别"
              >
                <el-option
                  v-for="item in menuData.warehouseTypeMenu"
                  :key="item.code"
                  :label="item.desc"
                  :value="item.code"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="邮政编码: "
              prop="postCode"
            >
              <el-input v-model="addDialog.addForm.postCode"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="省市区: "
              prop="Areas"
            >
              <el-cascader
                v-model="addDialog.area"
                :options="menuData.Areas"
                style="width: 100%"
                @change="areaAddCode()"
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人:"
              prop="linkman"
            >
              <el-input v-model="addDialog.addForm.linkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人英文:"
            >
              <el-input v-model="addDialog.addForm.englishLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="联系邮箱:"
              prop="mail"
            >
              <el-input v-model="addDialog.addForm.mail" style="width:580px"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系电话:"
              prop="linkPhone"
            >
              <el-input v-model="addDialog.addForm.linkPhone"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系手机:"
              prop="linkMobile"
            >
              <el-input v-model="addDialog.addForm.linkMobile"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址: "
              prop="adress">
              <el-input v-model="addDialog.addForm.adress"/>
              <!-- <el-select v-model="priorityComplete" size="mini" placeholder="请选择" style="width: 120px;">
                <el-option :value="1" label="是"/>
              </el-select> -->
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址英文: "
              label-width="110px"
            >
              <el-input v-model="addDialog.addForm.englishAddress"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">退货收货联系信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="联系人:"
              prop="rcvLinkman"
            >
              <el-input v-model="addDialog.addForm.rcvLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系邮箱:"
              prop="rcvLinkEmail"
            >
              <el-input v-model="addDialog.addForm.rcvLinkEmail"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="联系电话:"
              prop="rcvLinkTel"
            >
              <el-input v-model="addDialog.addForm.rcvLinkTel"/>
            </el-form-item>
          </el-col>
        </el-row>
        <div v-if="addDialog.addForm.warehouseType=='WT'">
          <el-divider content-position="left">客户信息</el-divider>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                label="客户名称:"
                prop="customerLinkman"
              >
                <el-input v-model="addDialog.addForm.customerLinkman"/>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                label="客户代码:"
                prop="customerNo"
              >
                <el-input v-model="addDialog.addForm.customerNo"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                label="客户电话:"
                prop="customerPhone"
              >
                <el-input v-model="addDialog.addForm.customerPhone"/>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                label="客户邮箱:"
                prop="customerMail"
              >
                <el-input v-model="addDialog.addForm.customerMail"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                label="最大备库E金额:"
                prop="maxEamount"
                label-width="120px"
              >
                <el-input v-model="addDialog.addForm.maxEamount"/>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <el-divider content-position="left">SMC负责人</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="负责人:"
              prop="smcLinkman"
            >
              <el-input v-model="addDialog.addForm.smcLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="电话:"
              prop="smcPhone"
            >
              <el-input v-model="addDialog.addForm.smcPhone"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="邮箱:"
              prop="smcMail"
            >
              <el-input v-model="addDialog.addForm.smcMail"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left">仓库功能</el-divider>
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="暂停出库:"
              prop="disableFlag"
            >
              <el-switch
                v-model="addDialog.addForm.disableFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="组装能力:"
              prop="assemblyFlag"
            >
              <el-switch
                v-model="addDialog.addForm.assemblyFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="出库集约:"
              prop="centralizeFlag"
            >
              <el-switch
                v-model="addDialog.addForm.centralizeFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="自动分配:"
              prop="autodispatchFlag"
            >
              <el-switch
                v-model="addDialog.addForm.autodispatchFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="采购能力:"
              prop="purchaseFlag"
            >
              <el-switch
                v-model="addDialog.addForm.purchaseFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="退货能力:"
              prop="returnableFlag"
            >
              <el-switch
                v-model="addDialog.addForm.returnableFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="调库能力:"
              prop="transferFlag"
            >
              <el-switch
                v-model="addDialog.addForm.transferFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="补库能力:"
              prop="prestockFlag"
            >
              <el-switch
                v-model="addDialog.addForm.prestockFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>

        </el-row>

      </el-form>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="addButton('addFormRef')"
        >确 定</el-button>
        <el-button
          type="info"
          @click="addDialog.display = false"
        >取 消</el-button>
      </span>
    </el-dialog>
    <!-- 多地址 -->
    <el-dialog
      :visible.sync="multAddressDialog.display"
      title="仓库多地址维护"
      width="800px"
      destroy-on-close
      @close="multAddressReset"
    >
      <el-form
        ref="multAddressFormRef"
        :rules="rules"
        :model="multAddressDialog.multAddressForm"
        label-width="90px"
      >
        <el-divider content-position="left" height="20px">查询仓库绑定地址信息</el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item
              label="查询地址:"
              prop="doType"
            >
              <el-select v-model="multAddressDialog.query" placeholder="请选择地址类别" clearable @change="multAddressChangeVauleQueryTable($event)">
                <el-option
                  v-for="item in multAddressOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-divider content-position="left" height="10px">地址信息</el-divider>
        <span style="color: red;size:8px" >*备注：仅选择地址，不选择类别可删除关联关系</span>
        <el-row>
          <el-table
            v-loading="multAddressDialog.Loading"
            :data="multAddressDialog.tableData"
            height="25vh"
            highlight-current-row
            border
            @row-click="selectMulAddress">
            >
            <el-table-column align="center" width="55" label="选择">
              <template slot-scope="scope">
                <!-- 可以手动的修改label的值，从而控制选择哪一项 -->
                <el-radio
                  v-model="multAddressDialog.Selection"
                  :label="scope.row.id"
                  class="radio"
                  @click.native.prevent="()=>{}"
                >&nbsp;</el-radio>
              </template>
            </el-table-column>
            <el-table-column
              v-for="column in multAddressDialog.tableColumns"
              :key="column.prop"
              :prop="column.prop"
              :label="column.label"
              :width="column.width"
              :type="column.type"
              :align="column.align"
              :formatter="column.formatter"
              header-align="center"
            />
          </el-table>
        </el-row>
        <el-divider content-position="left">仓库地址类别</el-divider>
        <el-row>
          <el-col :span="22">
            <el-form-item
              label="地址类别:"
              prop="doType"
            >
              <el-select v-model="multAddressDialog.select" multiple placeholder="请选择地址类别" @change="multAddressChangeVaule($event)">
                <el-option
                  v-for="item in multAddressOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="multAddressSaveButton('multAddressFormRef')"
        >保 存</el-button>
        <el-button
          type="info"
          @click="multAddressDialog.display = false"
        >关 闭</el-button>
      </span>
    </el-dialog>

    <!-- 修改 -->
    <el-dialog
      :visible.sync="editDialog.display"
      title="修改"
      width="800px"
      destroy-on-close
      @close="editFormReset"
    >
      <el-form
        ref="editFormRef"
        :rules="rules"
        :model="editDialog.editForm"
        label-width="90px"
      >
        <el-divider content-position="left">仓库信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="仓库代码: "
              prop="warehouseCode"
            >
              <el-input
                v-model="editDialog.editForm.warehouseCode"
                disabled
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="上级代码: "
              prop="parentCode"
            >
              <template>
                <el-select
                  v-model="editDialog.editForm.parentCode"
                  placeholder="上级代码"
                >
                  <el-option
                    v-for="item in menuData.parentCode"
                    :key="item.code"
                    :label="item.desc"
                    :value="item.code"
                  />
                </el-select>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="仓库名称: "
              prop="warehouseName"
            >
              <el-input v-model="editDialog.editForm.warehouseName"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="仓库类别: "
              prop="warehouseType"
            >
              <template>
                <el-select
                  v-model="editDialog.editForm.warehouseType"
                  placeholder="仓库类别"
                >
                  <el-option
                    v-for="item in menuData.warehouseTypeMenu"
                    :key="item.code"
                    :label="item.desc"
                    :value="item.code"
                  />
                </el-select>
              </template>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="邮政编码: "
              prop="postCode"
            >
              <el-input v-model="editDialog.editForm.postCode"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="省市区: "
            >
              <el-cascader
                v-model="editDialog.area"
                :options="menuData.Areas"
                style="width: 100%"
                @change="areaEditCode()"
              />
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人:"
              prop="linkman"
            >
              <el-input v-model="editDialog.editForm.linkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系人英文:"
            >
              <el-input v-model="editDialog.editForm.englishLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="联系邮箱:"
              prop="mail"
            >
              <el-input v-model="editDialog.editForm.mail" style="width: 580px"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系电话:"
              prop="linkPhone"
            >
              <el-input v-model="editDialog.editForm.linkPhone"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系手机:"
              prop="linkMobile"
            >
              <el-input v-model="editDialog.editForm.linkMobile"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址: "
              prop="adress"
            >
              <el-input v-model="editDialog.editForm.adress"/>
            </el-form-item>
          </el-col>
          <el-col :span="22">
            <el-form-item
              label="仓库地址英文: "
              label-width="110px"
            >
              <el-input v-model="editDialog.editForm.englishAddress"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">退货收货联系信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="联系人:"
              prop="rcvLinkman"
            >
              <el-input v-model="editDialog.editForm.rcvLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="联系邮箱:"
              prop="rcvLinkEmail"
            >
              <el-input v-model="editDialog.editForm.rcvLinkEmail"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="联系电话:"
              prop="rcvLinkTel"
            >
              <el-input v-model="editDialog.editForm.rcvLinkTel"/>
            </el-form-item>
          </el-col>

        </el-row>
        <div v-if="editDialog.editForm.warehouseType=='WT'">
          <el-divider content-position="left">客户信息</el-divider>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                label="客户名称:"
                prop="customerLinkman"
              >
                <el-input v-model="editDialog.editForm.customerLinkman"/>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                label="客户代码:"
                prop="customerNo"
              >
                <el-input v-model="editDialog.editForm.customerNo"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                label="客户电话:"
                prop="customerPhone"
              >
                <el-input v-model="editDialog.editForm.customerPhone"/>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item
                label="客户邮箱:"
                prop="customerMail"
              >
                <el-input v-model="editDialog.editForm.customerMail"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="11">
              <el-form-item
                label="最大备库E金额:"
                prop="maxEamount"
                label-width="120px"
              >
                <el-input v-model="editDialog.editForm.maxEamount"/>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <el-divider content-position="left">SMC负责人</el-divider>
        <el-row :gutter="20">
          <el-col :span="11">
            <el-form-item
              label="负责人:"
              prop="smcLinkman"
            >
              <el-input v-model="editDialog.editForm.smcLinkman"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="电话:"
              prop="smcPhone"
            >
              <el-input v-model="editDialog.editForm.smcPhone"/>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item
              label="邮箱:"
              prop="smcMail"
            >
              <el-input v-model="editDialog.editForm.smcMail"/>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">仓库功能</el-divider>
        <el-row>
          <el-col :span="6">
            <el-form-item
              label="暂停出库:"
              prop="disableFlag"
            >
              <el-switch
                v-model="editDialog.editForm.disableFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="组装能力:"
              prop="assemblyFlag"
            >
              <el-switch
                v-model="editDialog.editForm.assemblyFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="出库集约:"
              prop="centralizeFlag"
            >
              <el-switch
                v-model="editDialog.editForm.centralizeFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="自动分配:"
              prop="autodispatchFlag"
            >
              <el-switch
                v-model="editDialog.editForm.autodispatchFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="采购能力:"
              prop="purchaseFlag"
            >
              <el-switch
                v-model="editDialog.editForm.purchaseFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>

          <el-col :span="6">
            <el-form-item
              label="退货能力:"
              prop="returnableFlag"
            >
              <el-switch
                v-model="editDialog.editForm.returnableFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="调库能力:"
              prop="transferFlag"
            >
              <el-switch
                v-model="editDialog.editForm.transferFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item
              label="补库能力:"
              prop="prestockFlag"
            >
              <el-switch
                v-model="editDialog.editForm.prestockFlag"
                :active-value="true"
                :inactive-value="false"
              />
            </el-form-item>
          </el-col>
        </el-row>

      </el-form>

      <span
        slot="footer"
        class="dialog-footer"
      >
        <el-button
          type="primary"
          @click="editButton('editFormRef')"
        >确 定</el-button>
        <el-button
          type="info"
          @click="editDialog.display = false"
        >取 消</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { findHouse, getMultAdressConfig, saveMultAddressConfig, getMultAdress, addHouse, editHouse, deleteHouse, deletesHouse, findAreas, findHouseCodeAndNameByType } from '@/api/warehouseManage'
import { getDataByPid } from '@/api/dictionary'
import { Constants } from '@/utils/optionUtils'
import { upWarehouseDelFlag } from '@/api/common/dict'

export default {
  name: 'Warehouse',
  data() {
    return {
      // 菜单数据
      menuData: {
        // 仓库类别菜单
        Areas: [],
        warehouseTypeMenu: [],
        parentCode: [
        ]
      },
      // 多地址类别
      multAddressOptions: [
        {
          value: 'DBCK',
          label: '调拨'
        }, {
          value: 'TKCK',
          label: '调库'
        }, {
          value: 'CGDBCK',
          label: '采购补库调拨'
        }
      ],
      // 查询条件
      queryCondition: {
        condition: {},
        pageNumber: 1,
        pageSize: 20,
        orderBy: ''
      },
      upinfo: {
        warehouseCode: '',
        delflag: 0
      },
      // 表头字段
      tableColumns: [
        {
          type: 'selection',
          label: '选择框'
        },
        {
          label: '仓库代码',
          prop: 'warehouseCode'
        },
        {
          label: '仓库名称',
          prop: 'warehouseName',
          width: 250
        },
        {
          label: '仓库类别',
          prop: 'warehouseType',
          formatter: this.warehouseTypeFormatter,
          width: 130
        },
        {
          label: '仓库地址',
          prop: 'adress',
          width: 200
        },
        {
          label: '邮编',
          prop: 'postCode',
          width: 80
        },
        {
          label: '联系人',
          prop: 'linkman',
          width: 80
        },
        {
          label: '联系人邮箱',
          prop: 'mail',
          width: 200
        },

        {
          label: '暂停出库',
          prop: 'disableFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '组装能力',
          prop: 'assemblyFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '出库集约',
          prop: 'centralizeFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '自动分配',
          prop: 'autodispatchFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '采购能力',
          prop: 'purchaseFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '退货能力',
          prop: 'returnableFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '调库能力',
          prop: 'transferFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '补库能力',
          prop: 'prestockFlag',
          formatter(row, column, cellValue, index) {
            return cellValue === true ? '是' : '否'
          }
        },
        {
          label: '客户代码',
          prop: 'customerNo'
        },
        {
          label: '客户负责人',
          prop: 'customerLinkman',
          width: 100
        },
        {
          label: '客户负责人电话',
          prop: 'customerPhone',
          width: 150
        },
        {
          label: '客户负责人邮箱',
          prop: 'customerMail',
          width: 250
        },
        {
          label: 'SMC负责人',
          prop: 'smcLinkman',
          width: 100
        },
        {
          label: 'SMC负责人电话',
          prop: 'smcPhone',
          width: 150
        },
        {
          label: 'SMC负责人邮箱',
          prop: 'smcMail',
          width: 250
        }
      ],
      // 数据表格
      tableData: {},

      // 添加对话框
      addDialog: {
        display: false,
        area: [],
        addForm: {
          disableFlag: false,
          assemblyFlag: false,
          autodispatchFlag: false,
          centralizeFlag: false,
          purchaseFlag: false,
          returnableFlag: false,
          transferFlag: false,
          prestockFlag: false

        }

      },
      // 多地址对话框
      multAddressDialog: {
        display: false,
        select: [],
        area: [],
        mulDoType: [],
        multAddressForm: {
        },
        tableData: [],
        tableColumns: [
          {
            label: 'ID',
            prop: 'id',
            width: 80
          },
          {
            label: '仓库代码',
            prop: 'warehouseCode',
            width: 80
          },
          {
            label: '仓库地址',
            prop: 'adress',
            width: 300
          },
          {
            label: '联系人',
            prop: 'linkman',
            width: 120
          }
        ]
      },
      // 修改对话框
      editDialog: {
        display: false,
        area: [],
        editForm: {
        }
      },
      checkbox: true,
      isDel: true,
      // 校验规则
      rules: {
        warehouseCode: [
          { required: true, message: '请输入仓库代码', trigger: 'blur' }
        ],
        warehouseName: [
          { required: true, message: '请输入仓库名称', trigger: 'blur' }
        ],
        warehouseType: [
          { required: true, message: '请选择仓库类别', trigger: 'blur' }
        ],
        postCode: [
          { required: true, message: '此项不能为空', trigger: 'blur' }
        ],
        // Areas: [
        //   { type: 'array', required: true, message: '此项不能为空', trigger: 'change' }
        // ],
        adress: [
          { required: true, message: '此项不能为空', trigger: 'blur' }
        ],
        linkman: [
          { required: true, message: '此项不能为空', trigger: 'blur' }
        ],
        mail: [
          { required: true, message: '此项不能为空', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }
        ],
        linkPhone: [
          { required: true, message: '此项不能为空', trigger: 'blur' },
          { pattern: /^((0\d{2,3}(-)?\d{7,8})|(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8})((-)?\d{3,})?$/, message: '电话号格式不正确', trigger: ['blur'] }
        ],
        linkMobile: [
          { required: true, message: '此项不能为空', trigger: 'blur' },
          { pattern: /^1[34578]\d{9}$/, message: '手机号格式不正确', trigger: ['blur'] }
        ],
        customerLinkman: [
          { required: true, message: '请输入客户名称', trigger: 'blur' }
        ],
        customerNo: [
          { required: true, message: '请输入客户代码', trigger: 'blur' }
        ],
        customerPhone: [
          { required: true, message: '请输入客户电话', trigger: 'blur' }
        ],
        customerMail: [
          { required: true, message: '请输入客户邮箱', trigger: 'blur' },
          { type: 'email', message: '邮箱格式不正确', trigger: ['blur', 'change'] }
        ]
      }
    }
  },
  created() {
    this.initData()
    this.getAreas()
    this.getTableDataEvent()
    this.findMasterWarehouse()
    this.checkbox = true
  },
  methods: {
    changeIsDelFlag(row) {
      console.log('==> ', row)
      this.upinfo.warehouseCode = row.warehouseCode
      this.upinfo.delflag = row.delflag
      console.log(this.upinfo)
      upWarehouseDelFlag(this.upinfo).then(res => {
        console.log('res => ', res)
        if (res.success) {
          this.$message.success(res.content)
          this.getTableDataEvent()
        }
      }).catch(res => {
        console.log(res)
        this.$message.error(res)
      })
    },
    checkboxClick() {
      this.getTableDataEvent()
    },
    // 菜单数据初始化
    initData() {
      // 从数据字典中获取仓库类别菜单信息
      getDataByPid(Constants.warehouseTypeList).then(res => {
        res.forEach(element => {
          this.menuData.warehouseTypeMenu.push({ code: element.extcode, desc: element.name })
        })
        console.log('获取仓库类别菜单信息：')
        console.log(this.menuData.warehouseTypeMenu)
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    // 获取省市区根目录
    getAreas() {
      findAreas().then(res => {
        if (res.success) {
          this.menuData.Areas = res.data
        }
      })
    },
    areaAddCode() {
      this.addDialog.addForm.province = this.addDialog.area[0]
      this.addDialog.addForm.city = this.addDialog.area[1]
      this.addDialog.addForm.district = this.addDialog.area[2]
    },
    areaEditCode() {
      this.editDialog.editForm.province = this.editDialog.area[0]
      this.editDialog.editForm.city = this.editDialog.area[1]
      this.editDialog.editForm.district = this.editDialog.area[2]
    },
    // 获取表格数据事件
    getTableDataEvent() {
      console.log('查询条件：')
      console.log(this.queryCondition)
      if (this.checkbox !== undefined && this.checkbox !== '' && this.checkbox !== null) {
        if (this.checkbox === true) {
          this.queryCondition.condition.delflag = false
        } else {
          this.queryCondition.condition.delflag = null
        }
      }
      findHouse(this.queryCondition).then(res => {
        console.log('获取表格数据')
        console.log(res)
        this.tableData = res.data
      }).catch(res => {
        this.smcErrorMsg(res.message)
      })
    },
    // 改变每页条数
    handleSizeChange(newSize) {
      this.queryCondition.pageSize = newSize
      this.getTableDataEvent()
    },
    // 换页
    handleCurrentChange(newCurrent) {
      this.queryCondition.pageNumber = newCurrent
      this.getTableDataEvent()
    },
    // 仓库类型格式化
    warehouseTypeFormatter(row, column, cellValue, index) {
      var item = this.menuData.warehouseTypeMenu.find(item => item.code === cellValue)
      return item ? item.desc : cellValue
    },
    // 显示添加对话框
    showAddDialog() {
      this.addDialog.display = true
    },
    // 重置表单
    addFormReset() {
      this.addDialog.addForm = {
        disableFlag: false,
        assemblyFlag: false,
        autodispatchFlag: false,
        centralizeFlag: false,
        purchaseFlag: false
      }
      this.$refs['addFormRef'].resetFields()
    },
    // 添加按钮事件
    addButton(formNameRef) {
      // 表单校验
      this.$refs[formNameRef].validate((valid) => {
        if (valid) {
          this.addEvent()
        } else {
          this.smcErrorMsg('表单校验失败，请输入正确的格式')
        }
      })
    },
    // 增添事件
    addEvent() {
      if (this.addDialog.addForm.province == null) {
        this.smcErrorMsg('请填写省市区')
        return false
      }

      if (this.addDialog.addForm.warehouseType !== 'WT') {
        this.addDialog.addForm.customerNo = ''
        this.addDialog.addForm.customerLinkman = ''
        this.addDialog.addForm.customerPhone = ''
        this.addDialog.addForm.customerMail = ''
      }
      addHouse(this.addDialog.addForm).then(res => {
        this.addDialog.display = false
        this.getTableDataEvent()
        this.smcSuccessMsg(res.message)
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 显示修改对话框
    showEditDialog(row) {
      this.editDialog.area = [this.editDialog.editForm.province, this.editDialog.editForm.city, this.editDialog.editForm.district]
      this.editDialog.display = true
      const form = {
        ...row,
        disableFlag: !!row.disableFlag,
        assemblyFlag: !!row.assemblyFlag,
        autodispatchFlag: !!row.autodispatchFlag,
        centralizeFlag: !!row.centralizeFlag,
        purchaseFlag: !!row.purchaseFlag,
        returnableFlag: !!row.returnableFlag,
        transferFlag: !!row.transferFlag,
        prestockFlag: !!row.prestockFlag
      }
      this.editDialog.editForm = form
    },
    // 多地址对话框点击地址按钮
    selectMulAddress(row) {
      this.multAddressDialog.Selection = row.id
      this.multAddressDialog.multAddressForm.warehouseCode = row.warehouseCode
      this.multAddressDialog.multAddressForm.addressId = row.id
      getMultAdressConfig(this.multAddressDialog.multAddressForm).then(res => {
        this.multAddressDialog.select = res.data
        this.multAddressDialog.multAddressForm.doTypeList = res.data
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 显示多地址对话框
    showMultAddressDialog(row) {
      this.multAddressDialog.Selection = null
      this.multAddressDialog.display = true
      this.multAddressDialog.multAddressForm.warehouseCode = row.warehouseCode
      // 赋值到表单上
      getMultAdress(this.multAddressDialog.multAddressForm).then(res => {
        this.multAddressDialog.tableData = res.data
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 重置表单多地址
    multAddressReset() {
      this.multAddressDialog.multAddressForm = {}
      this.multAddressDialog.select = ''
      this.multAddressDialog.query = ''
    },
    // 多地址类别选择事件
    multAddressChangeVaule(selectValue) {
      this.multAddressDialog.multAddressForm.doTypeList = selectValue
    },
    // 多地址查询按钮
    multAddressChangeVauleQueryTable(selectValue) {
      // 赋值到表单上
      this.multAddressDialog.multAddressForm.updator = selectValue
      getMultAdress(this.multAddressDialog.multAddressForm).then(res => {
        this.multAddressDialog.tableData = res.data
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 多地址保存按钮
    multAddressSaveButton(formNameRef) {
      this.$refs[formNameRef].validate((valid) => {
        if (valid) {
          // 验证
          if (this.multAddressDialog.multAddressForm.addressId == null) {
            this.smcErrorMsg('请选择地址')
            return false
          }
          this.multAddressDialog.multAddressForm.creater = this.$store.getters.info.username
          saveMultAddressConfig(this.multAddressDialog.multAddressForm).then(res => {
            if (res.code === 200) {
              this.multAddressDialog.display = false
              this.smcSuccessMsg(res.message)
            } else {
              this.smcErrorMsg(res.message)
            }
          }).catch(res => {
            this.smcErrorMsg(res.message)
          })
        } else {
          this.smcErrorMsg('表单校验失败，请输入正确的格式')
        }
      })
    },
    // 重置表单
    editFormReset() {
      this.editDialog.editForm = {}
    },

    // 修改事件
    editEvent() {
      if (this.editDialog.editForm.province == null) {
        this.smcErrorMsg('请填写省市区')
        return false
      }
      if (this.editDialog.editForm.warehouseType !== 'WT') {
        this.editDialog.editForm.customerNo = ''
        this.editDialog.editForm.customerLinkman = ''
        this.editDialog.editForm.customerPhone = ''
        this.editDialog.editForm.customerMail = ''
      }
      editHouse(this.editDialog.editForm).then(res => {
        this.editDialog.display = false
        this.getTableDataEvent()
        this.smcSuccessMsg(res.message)
      }).catch(res => {
        console.log(res)
        this.smcErrorMsg(res.message)
      })
    },
    // 修改按钮事件
    editButton(formNameRef) {
      this.$refs[formNameRef].validate((valid) => {
        if (valid) {
          this.editEvent()
        } else {
          this.smcErrorMsg('表单校验失败，请输入正确的格式')
        }
      })
    },
    // 删除本行事件
    deleteEvent(row) {
      this.$confirm('此操作将永久删除本条数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteHouse(row.warehouseCode).then(res => {
          this.getTableDataEvent()
          console.log(res)
          this.smcSuccessMsg(res.message)
        }).catch(res => {
          this.smcErrorMsg(res.message)
        })
      })
    },
    // 删除多行事件
    deletesEvent() {
      this.$confirm('此操作将选中的多条数据变成无效, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var deleteList = []
        this.$refs.multipleTable.selection.forEach(item => {
          deleteList.push(item.warehouseCode)
        })
        console.log(deleteList)
        deletesHouse(deleteList).then(res => {
          this.getTableDataEvent()
          console.log(res)
          this.smcSuccessMsg(res.message)
        }).catch(res => {
          this.smcErrorMsg(res.message)
        })
      })
    },
    findMasterWarehouse() {
      findHouseCodeAndNameByType('MASTER').then(result => {
        // 供应商
        result.data.forEach(warehouse => {
          this.menuData.parentCode.push({ code: warehouse.warehouseCode, desc: warehouse.warehouseName })
        })
      })
    }

  }
}
</script>
<style scoped>
.el-card {
  margin: 5px;
}

.el-row {
  margin: 10px !important;
}

.el-card .el-table {
  margin: 10px;
  width: auto;
}

.el-form {
  margin: 0;
}

div /deep/ .el-input__inner {
  width: 100%;
}

/* input的宽度*/
input.el-input__inner {
  width: 100%;
}

/* select选择框宽度 */
.el-select {
  width: 100%;
}

/*时间选择框宽度*/
.el-date-editor.el-input {
  width: 100%;
}

/*超链接字体大小*/
.el-link {
  font-size: 18px;
}

/*input的lable的字体高度*/
.el-form-item .el-form-item__label {
  margin-top: 15px;
}

ul,
li {
  list-style-type: none;
  padding: 5px;
}
</style>
