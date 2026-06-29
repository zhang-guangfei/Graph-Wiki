<template>
  <div id="product-detail-class" class="product-detail-class">
    <div v-if="productStatus === true" style="margin-bottom: 50px">
      <div id="product-detail-content" class="stock-content">
        <div class="collapse-title-class" style="font-size:20px;margin: 5px 0;">
          {{ product.baseInfo.chineseName ? product.baseInfo.chineseName : '型号' }} <span style="font-size:18px;">({{ product.modelNo }})</span>
        </div>
        <el-divider style="margin: 5px 0 !important"/>
        <el-row>
          <el-col>
            <div>
              <span class="collapse-title-class" style="font-size:12px;" @click="baseInfoVisible = !baseInfoVisible">
                <i class="el-icon-tickets" style="color:#008080;"/>
                基础信息
                <i :title="baseInfoVisible === false? '展开': '收起'" :class="baseInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
              </span>
            </div>
            <div v-show="baseInfoVisible === true" class="product-base-content-body">
              <!-- <el-divider style="margin: 5px 0 !important"/> -->
              <el-form ref="form" :inline="true" label-position="left" label-width="100px" label-suffix="" size="mini">
                <el-form-item label="竞争系数" class="col-class" style="margin-right:30px; margin:3px 0;">
                  <div>
                    {{ product.baseInfo.competitivenessName ? product.baseInfo.competitivenessName : '无' }}
                  </div>
                </el-form-item>

                <el-form-item label="性格区分" class="col-class" style="margin-right:30px; margin:3px 0;">
                  <div>
                    {{ product.baseInfo.designTypeId }}
                  </div>
                </el-form-item>
                <el-form-item label="Ecode码" class="col-class" style="margin-right:30px; margin:3px 0;">
                  <div>
                    {{ product.baseInfo.eCode }}
                  </div>
                </el-form-item>
                <el-form-item class="col-class" label="贩卖限制品" style="margin-right:30px; margin:3px 0;">
                  <div>
                    <!-- <span :class="product.isRestrict == false ? '' : 'warning-col-class'">
                      {{ product.isRestrict === true ? '是' : '否' }}
                    </span> -->
                    <span v-if="product.isRestrict === true">
                      <span :class="product.isRestrict == false ? '' : 'warning-col-class'">
                        {{ '是' }}
                      </span>
                      <!--bug7782  B91717 增加贩卖限制品白名单客户，点击显示 -->
                      <el-popover
                        ref="popover"
                        placement="right"
                        width="600"
                        trigger="hover">
                        <vxe-table
                          v-if="whitelist && whitelist.length > 0"
                          :data="whitelist"
                          min-height="50px"
                          highlight-current-column
                          highlight-hover-row
                          border
                          auto-resize
                          resizable
                          align="center"
                          max-height="400">
                          <vxe-table-column type="seq" width="60"/>
                          <vxe-table-column field="customerNo" header-align="center" align="left" min-width="200" title="客户" show-overflow>
                            <template v-slot="{ row }">
                              <span v-if="!isEmpty(row.customerNo)">{{ '【' + row.customerNo + '】' }}</span>
                              <span v-if="!isEmpty(row.customerName)">{{ row.customerName }}</span>
                            </template>
                          </vxe-table-column>
                          <vxe-table-column field="deptNo" header-align="center" align="left" min-width="120" title="部门" show-overflow>
                            <template v-slot="{ row }">
                              <span v-if="!isEmpty(row.deptNo)">{{ '【' + row.deptNo + '】' }}</span>
                              <span v-if="!isEmpty(row.deptName)">{{ row.deptName }}</span>
                            </template>
                          </vxe-table-column>
                          <vxe-table-column header-align="center" align="center" min-width="120" field="mainCustomer" title="主责" show-overflow>
                            <template slot-scope="scope">
                              <span v-if="scope.row.mainCustomer===false">
                                {{ "否" }}
                                <i class="el-icon-warning" style="color: #FF5809; cursor: pointer; font-size: 15px;"/>
                              </span>
                              <span v-if="scope.row.mainCustomer===true">
                                {{ "是" }}
                                <i class="el-icon-success" style="color: #00BB00; cursor: pointer; font-size: 15px;"/>
                              </span>
                            </template>
                          </vxe-table-column>
                        </vxe-table>
                        <span slot="reference" title="查看详情" style="cursor: pointer;color:#337AB7;">查</span>
                      </el-popover>
                    </span>
                    <span v-else>
                      否
                    </span>
                  </div>
                </el-form-item>
                <!-- <el-form-item v-if="product.isRestrict === true" class="col-class" label="权限客户">
                  <div>
                    {{ product.restrictCustomer }}
                  </div>
                </el-form-item> -->
                <el-form-item class="col-class" label="是否收敛品" style="margin-right:30px; margin:3px 0;">
                  <div>
                    <span :class="product.isEos == false ? '' : 'warning-col-class'">
                      {{ product.isEos === true ? '是' : '否' }}
                    </span>
                  </div>
                </el-form-item>
                <el-form-item v-if="product.isEos === true" class="col-class" label="推荐型号">
                  <span v-if="product.eosInfo.modelnoRecommend && getByteLen(product.eosInfo.modelnoRecommend) >= 40">
                    <!-- {{ product.eosInfo.modelnoRecommend.substring(0,10) }} -->
                    <el-tooltip placement="bottom" trigger="hover" effect="light">
                      <div slot="content">{{ product.eosInfo.modelnoRecommend }}</div>
                      <span title="查看详情" style="cursor: pointer;color:#337AB7;">查</span>
                    </el-tooltip>
                  </span>
                  <span v-else>
                    {{ product.eosInfo.modelnoRecommend }}
                  </span>
                </el-form-item>
                <el-form-item v-if="product.eosInfo.warningdate != null" class="col-class" label="收敛警告开始日">
                  <div>
                    {{ product.eosInfo.warningdate | formatDate2 }}
                  </div>
                </el-form-item>
                <el-form-item v-if="product.eosInfo.stopdate != null" class="col-class" label="接单停止日">
                  <div>
                    {{ product.eosInfo.stopdate | formatDate2 }}
                  </div>
                </el-form-item>

                <!-- <el-form-item v-if="product.substituteList.length > 0" class="col-class" label="推荐型号">
                  <div>
                    <el-dropdown @command="selectModelNo">
                      <span class="el-dropdown-link">
                        {{ product.substitute && product.substitute.length > 10 ?product.substitute.substring(0,10) + '...' : product.substitute }} <i class="el-icon-arrow-down el-icon--right"/>
                      </span>
                      <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item v-for="(item, index) in product.substituteList" :key="index" :command="item">{{ item.substitute }}</el-dropdown-item>
                      </el-dropdown-menu>
                    </el-dropdown>
                    <span title="查看详情" style="cursor: pointer;color:#337AB7;" @click="toDetail()">查</span>
                  </div>
                </el-form-item> -->
                <!-- <el-form-item v-if="product.isRestrict === true" class="col-class" label="主管国">
                  <div>
                    {{  }}
                  </div>
                </el-form-item>
                <el-form-item v-if="product.isRestrict === true" class="col-class" label="权限客户">
                  <div>
                    {{  }}
                  </div>
                </el-form-item> -->
                <el-form-item class="col-class" label="SHIKOMI" effect="light">
                  <div>
                    <span v-if="product.shikomiInfoList.length > 0">
                      是
                      <el-popover placement="bottom" trigger="hover">
                        <vxe-table
                          v-if="shikomiList && shikomiList.length > 0"
                          :data="shikomiList"
                          min-height="50px"
                          border
                          auto-resize
                          resizable
                          align="center"
                          size="mini">
                          <vxe-table-column field="supplierCode" min-width="80" title="备库地点" show-overflow/>
                          <vxe-table-column field="shikomiNo" min-width="90" title="Shikomi号" show-overflow/>
                          <vxe-table-column field="mainModelFlag" min-width="80" title="型号类型" show-overflow/>
                          <vxe-table-column field="remainQty" min-width="80" title="Shikomi残数" show-overflow/>
                          <vxe-table-column field="qtyOrdPre" min-width="80" title="已预约数量" show-overflow/>
                          <vxe-table-column :formatter="formatDate" field="registDate" min-width="80" title="初回工作日"/>
                          <vxe-table-column field="asseDays" min-width="80" title="组装工作日" show-overflow/>
                          <vxe-table-column field="partPrepareDays" min-width="80" title="部品准备工作日" show-overflow/>
                          <vxe-table-column field="indCode" min-width="80" title="行业代码" show-overflow/>
                          <vxe-table-column field="customerNos" min-width="60" title="客户代码"/>
                          <vxe-table-column field="classType" min-width="100" title="Shikomi区分" show-overflow/>
                          <vxe-table-column field="classCode" min-width="90" title="Shikomi客户范围"/>
                          <vxe-table-column field="customerNo" min-width="60" title="归属客户"/>
                          <vxe-table-column field="lotQty" min-width="60" title="使用中LOT数"/>
                        </vxe-table>
                        <span slot="reference" title="查看详情" style="cursor: pointer;color:#337AB7;">查</span>
                      </el-popover>
                    </span>
                    <span v-else>
                      否
                    </span>
                  </div>
                </el-form-item>
                <el-form-item class="col-class" label="最小包装量">
                  <div>
                    {{ product.baseInfo.minPackUnit ? product.baseInfo.minPackUnit : 0 }}
                  </div>
                </el-form-item>
                <el-form-item class="col-class" label="最小起订量">
                  <div>
                    {{ product.baseInfo.minQuantity ? product.baseInfo.minQuantity : 0 }}
                  </div>
                </el-form-item>
              </el-form>
            </div>
          </el-col>
        </el-row>
        <div>
          <div>
            <span class="collapse-title-class" style="font-size:12px;" @click="priceInfoVisible = !priceInfoVisible">
              <i class="el-icon-coin" style="color: #FFA500;"/>
              产品价格
              <i :title="priceInfoVisible === false? '展开': '收起'" :class="priceInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'" />
            </span>
          </div>
          <div v-if="priceInfoVisible === true" style="height:100%;width:100%;padding: 0 20px;" class="base-index" >
            <vxe-table
              v-if="ePriceList && ePriceList.length > 0"
              :data="ePriceList"
              min-height="50px"
              border
              auto-resize
              resizable
              align="center"
              size="mini">
              <vxe-table-column field="minquantity" min-width="80" title="最小订货量" show-overflow/>
              <vxe-table-column field="eprice" min-width="150" title="E价格（元）" show-overflow/>
              <vxe-table-column field="epriceWithTax" min-width="110" title="E价格（含税，元）" show-overflow/>
              <vxe-table-column field="standardPrice" min-width="60" title="标准价（元）"/>
            </vxe-table>
            <span v-else>无</span>
          </div>
        </div>
        <div v-if="product.productPrompt && product.productPrompt.length > 0">
          <div class="collapse-title-class" style="font-size:12px;margin-top:5px">
            <span @click="productPromptVisible = !productPromptVisible">
              <i class="el-icon-s-data" style="color: #A52A2A"/>提示信息<i :title="productPromptVisible === false? '展开': '收起'" :class="productPromptVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
            </span>
          </div>
          <div v-show="productPromptVisible === true" class="product-base-content-body">
            <div class="base-index">
              <vxe-table
                :data="product.productPrompt"
                :loading="productPromptLoading"
                min-height="50px"
                show-overflow
                show-header-overflow
                border
                auto-resize
                resizable
                align="center"
                size="mini">
                <vxe-table-column type="seq" title="序号" width="100"/>
                <vxe-table-column field="classify" width="400" title="分类">
                  <template v-slot="{ row }">
                    <span>{{ row.classify }}</span>
                  </template>
                </vxe-table-column>
                <vxe-table-column field="prompt" title="提示信息" min-width="100">
                  <template v-slot="{ row }">
                    <span style="color:red">{{ row.prompt }}</span>
                  </template>
                </vxe-table-column>
              </vxe-table>
            </div>
          </div>
        </div>
        <div>
          <div>
            <span class="collapse-title-class" style="font-size:12px;" @click="shikomiInfoVisible = !shikomiInfoVisible">
              <i class="el-icon-s-home" style="color: #4F9D9D;"/>
              产品库存
              <i :title="shikomiInfoVisible === false? '展开': '收起'" :class="shikomiInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'" />
            </span>
          </div>
          <div v-if="shikomiInfoVisible === true" style="height:100%;width:100%;padding: 0 20px;" class="base-index" >
            <vxe-table
              ref="stockTable"
              :data="warehouseStockData"
              min-height="50px"
              auto-resize
              resizable
              align="center"
              size="mini"
              max-height="300">
              <vxe-table-column field="warehouse_code" title="仓库代码"/>
              <vxe-table-column field="warehouse_name" title="仓库名称"/>
              <vxe-table-column field="inventorycount" title="有效在库" min-width="80"/>
              <vxe-table-column field="safeQty" title="安全库存" />
              <vxe-table-column field="qtybin" title="BIN数量" />
              <vxe-table-column field="BinCell" title="BIN数" />
            </vxe-table>
            <!-- <template #default="{ row }">
                  <el-button type="text" size="mini" @click="showSpecStock(row)">{{ row.avaQty_zy }}</el-button>
                </template>

              <vxe-table-column type="expand" width="1">
                <template v-slot:content="{ row }">
                  <vxe-table
                    :data="row.specStock"
                    auto-resize
                    resizable
                    align="center"
                    size="mini"
                    max-height="160">
                    <vxe-table-column field="inventoryTypeCode" title="库存类型" min-width="80"/>
                    <vxe-table-column field="avaQty" title="数量" min-width="80"/>
                    <vxe-table-column field="customerNo" title="客户代码" min-width="80"/>
                    <vxe-table-column field="ppl" title="PPL" min-width="80"/>
                    <vxe-table-column field="projectCode" title="项目号" min-width="80"/>
                    <vxe-table-column field="groupCustomerNo" title="集团号" min-width="80"/>
                  </vxe-table>
                </template>
              </vxe-table-column>
            </vxe-table>-->
            <vxe-table
              v-if="shikomiList && shikomiList.length > 0"
              :data="shikomiList"
              min-height="50px"
              border
              auto-resize
              resizable
              align="center"
              size="mini">
              <vxe-table-column field="supplierCode" min-width="80" title="备库地点" show-overflow/>
              <vxe-table-column field="shikomiNo" min-width="100" title="Shikomi号" show-overflow/>
              <vxe-table-column field="mainModelFlag" min-width="80" title="型号类型" show-overflow/>
              <vxe-table-column field="remainQty" min-width="80" title="Shikomi残数" show-overflow/>
              <vxe-table-column field="qtyOrdPre" min-width="80" title="已预约数量" show-overflow/>
              <vxe-table-column :formatter="formatDate" field="registDate" min-width="80" title="初回工作日" show-overflow/>
              <vxe-table-column field="asseDays" min-width="80" title="组装工作日" show-overflow/>
              <vxe-table-column field="partPrepareDays" min-width="80" title="部品准备工作日" show-overflow/>
              <vxe-table-column field="indCode" min-width="80" title="行业代码" show-overflow/>
              <vxe-table-column field="customerNos" min-width="60" title="客户代码"/>
              <vxe-table-column field="classType" min-width="110" title="Shikomi区分" show-overflow/>
              <vxe-table-column field="classCode" min-width="90" title="Shikomi客户范围"/>
              <vxe-table-column field="customerNo" min-width="60" title="归属客户"/>
              <vxe-table-column field="lotQty" min-width="60" title="使用中LOT数"/>
            </vxe-table>

            <vxe-table
              v-if="inventorySupplierList && inventorySupplierList.length > 0"
              :data="inventorySupplierList"
              min-height="50px"
              border
              auto-resize
              resizable
              align="center"
              size="mini">
              <vxe-table-column :formatter= "suppilyFormatter" field="supplierid" title="供应商" show-overflow/>
              <vxe-table-column field="quantity" title="在库数量" show-overflow/>
              <vxe-table-column field="quantityassembly" title="可组装数量" show-overflow/>
              <vxe-table-column field="quantityproduce" title="可生产数量" show-overflow/>
              <vxe-table-column field="binflagStr" title="是否Bin" show-overflow/>
            </vxe-table>
            <!-- <span v-else>无</span> -->
          </div>
        </div>
        <div class="collapse-title-class" style="font-size:12px;" >
          <span @click="physicsInfoVisible = !physicsInfoVisible">
            <i class="el-icon-odometer" style="color:#2E8B57"/>
            物理特征
            <i :title="physicsInfoVisible === false? '展开': '收起'" :class="physicsInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
          </span>
        </div>
        <div v-show="physicsInfoVisible === true" class="product-base-content-body">
          <el-form v-if="product.physics" ref="physicsForm" :inline="true" label-position="left" label-width="100px" label-suffix=":" size="mini">
            <el-row class="bg-purple-light">
              <el-col :span="6">
                <el-form-item label="净重（kg）" class="form-col-class">
                  {{ product.physics.netweight }}
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item class="form-col-class" label="长（mm）">
                  {{ product.physics['length'] }}
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item class="form-col-class" label="宽（mm）">
                  {{ product.physics.width }}
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item class="form-col-class" label="高（mm）">
                  {{ product.physics.height }}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <br>
        </div>
        <!-- <div class="collapse-title-class" style="font-size:12px;">
          <span @click="inventoryInfoVisible = !inventoryInfoVisible">
            <i class="el-icon-house" style="color: #FF7F50;font-weight: bold"/>
            库存信息
            <i :title="inventoryInfoVisible === false? '展开': '收起'" :class="inventoryInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
            <span>
              <el-button size="mini" icon="el-icon-lollipop" round style="margin-left: 10px" @click="inqBConsult">InqB</el-button>
            </span>
          </span>
          <i :title="inventDetailVisible === false? '展开': '收起'" :class="inventDetailVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
        </div>
        <div v-show="inventoryInfoVisible === true" class="product-base-content-body">
          <div style="border: 1px solid #e7e9ec;margin-bottom: 0px;">
            <el-row class="bg-purple-light">
              <el-form ref="form" :inline="true" label-width="80px" label-position="left" label-suffix=":" size="mini">
                <el-col :span="6">
                  <el-form-item label="可用库存" class="form-col-class">
                    {{ sumEffectiveInventory }}
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item class="form-col-class" label="供应国">
                    {{ product.supplierCountry }}
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item class="form-col-class" label="原产地">
                    {{ product.orgCountry }}
                  </el-form-item>
                </el-col>
              </el-form>
              <el-col v-if="sumEffectiveInventory > 0" :span="6">
                <div style="text-align: right;line-height: 28px;">
                  <i :title="inventDetailVisible === false? '展开': '收起'" :class="inventDetailVisible === false?'el-icon-circle-plus-outline' +' el-icon-class' : 'el-icon-remove-outline' +' el-icon-class'" @click="openList"/>
                </div>
              </el-col>
            </el-row>
            <div v-show="inventDetailVisible === true" class="invent-table-class base-index">
              <vxe-grid
                :data="inventoryList"
                :columns="inventoryColumn"
                :loading="inventoryLoading"
                border
                stripe
                highlight-hover-row
                highlight-current-row
                auto-resize
                resizable
                size="mini">
                <template v-slot:detailExpand="{ row }">
                  <vxe-grid
                    v-if="row.list && row.list.length > 0"
                    :data="row.list"
                    :columns="inventoryDetailColumn"
                    :span-method="mergeStockRowMethod"
                    border
                    stripe
                    highlight-hover-row
                    highlight-current-row
                    auto-resize
                    resizable
                    align="center"
                    size="mini"/>
                  <div v-else>
                    暂无数据
                  </div>
                </template>
              </vxe-grid>
            </div>
          </div>
        </div> -->
        <div class="collapse-title-class" style="font-size:12px;margin-top:5px">
          <span @click="splitInfoVisible = !splitInfoVisible">
            <i class="el-icon-setting" style="color: #A52A2A"/>
            拆分信息
            <i :title="splitInfoVisible === false? '展开': '收起'" :class="splitInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
          </span>
        </div>
        <div v-show="splitInfoVisible === true" class="product-base-content-body">
          <div class="base-index">
            <vxe-table
              :data="product.bom"
              :span-method="mergeBomRowMethod"
              :loading="bomLoading"
              min-height="50px"
              border
              auto-resize
              resizable
              align="center"
              size="mini">
              <!-- <vxe-table-column field="Priority_level" min-width="80" title="版本号">
                <template slot-scope="scope">
                  <span>{{ "版本"+scope.row.Priority_level }}</span>
                </template>
              </vxe-table-column> -->
              <vxe-table-column field="bomId" title="版本号" min-width="100" show-header-overflow show-overflow show-footer-overflow/>
              <vxe-table-column field="ModelNo" title="子型号" min-width="100" show-header-overflow show-overflow show-footer-overflow/>
              <vxe-table-column field="Quantity" min-width="80" title="构成数量"/>
              <!-- <vxe-table-column field="effectiveInventory" min-width="80" title="有效库存">
                <template #header>
                  <span>有效库存</span>
                  <a v-if="product && product.bom && product.bom.length > 0" style="color: blue; cursor: pointer; font-size: 12px;" @click="getBomDetail">
                    <span>详情</span>
                  </a>
                </template>
              </vxe-table-column> -->
              <!-- <vxe-table-column field="orgCountry" min-width="80" title="产地" show-overflow /> -->
            </vxe-table>
          </div>
        </div>
        <!--供应商信息-->
        <div class="collapse-title-class" style="font-size:12px;" >
          <span @click="deliveryInfoVisible = !deliveryInfoVisible">
            <i class="el-icon-s-goods" style="color:#2E8B57"/>
            供应商
            <i :title="deliveryInfoVisible === false? '展开': '收起'" :class="deliveryInfoVisible === false?'el-icon-d-arrow-right' +' el-icon-class' : 'el-icon-arrow-down' +' el-icon-class'"/>
          </span>
        </div>
        <div v-if="deliveryInfoVisible === true" style="height:100%;width:100%;padding: 0 20px;" class="base-index" >
          <vxe-table
            v-if="deliveryList && deliveryList.length > 0"
            :data="deliveryList"
            min-height="50px"
            border
            auto-resize
            resizable
            align="center"
            size="mini">
            <vxe-table-column :formatter= "suppilyCompanyFormatter" field="orgcountryid" min-width="80" title="原产国" show-overflow/>
            <vxe-table-column :formatter= "suppilyFormatter" field="supplyid" min-width="80" title="供应商" show-overflow/>
            <vxe-table-column field="stddlvday" min-width="110" title="标准货期" show-overflow/>
            <vxe-table-column field="stddlvdatemaxnumber" min-wivdth="120" title="标准货期最大数量" show-overflow/>
            <vxe-table-column field="supplierpartno" min-width="110" title="供应商型号" show-overflow/>
            <vxe-table-column field="isprimary" min-width="100" title="是否主供应商" show-overflow>
              <template slot-scope="scope">
                <span v-if="scope.row.isprimary===false">
                  {{ "否" }}
                  <i class="el-icon-warning" style="color: #FF5809; cursor: pointer; font-size: 15px;"/>
                </span>
                <span v-if="scope.row.isprimary===true">
                  {{ "是" }}
                  <i class="el-icon-success" style="color: #00BB00; cursor: pointer; font-size: 15px;"/>
                </span>
              </template>
            </vxe-table-column>
          </vxe-table>
          <span v-else>无</span>
        </div>
      </div>
      <!-- <el-divider style="margin: 5px 0 !important"/> -->
      <div v-if="customerNo">
        <!-- <price-info ref="priceInfo" :model-no="modelNo" :customer-no="customerNo"/> -->
      </div>
    </div>
    <div v-else-if="errorCode && (errorCode === 23 || errorCode === 24)" class="tips-content">
      <div>
        {{ errorCode === 23 ? '未知型号，请价格问询': (errorCode === 24 ? '错误型号，请重新输入': '') }}
        <!-- <el-button v-if="errorCode && errorCode === 23" size="mini" icon="el-icon-phone" style="margin-left:10px" round @click="consult">问询</el-button> -->
      </div>
      <div v-if="errorCode === 23" class="base-index" style="margin-top: 20px">
        <vxe-grid
          ref="xGrid"
          :columns="fuzzyColumn"
          :data="fuzzyInventoryData"
          :loading="fuzzyLoading"
          :pager-config="fuzzyPage"
          align="center"
          size="mini"
          show-overflow
          show-header-overflow
          highlight-current-row
          highlight-hover-row
          border
          auto-resize
          keep-source
          resizable
          @sort-change="sortChange">
          <!--@page-change="getPageFuzzyInventoryData"  -->
          <template #standardPriceDefault="{ row }">
            {{ computeStandardPrice(row.eprice, taxRate) }}
          </template>
          <template #customerDefault="{ row }">
            {{ row.customerName }}
            <span v-if="row.customerNo && row.customerNo != ''">
              【{{ row.customerNo }}】
            </span>
          </template>
          <template #stockCodeDefault="{ row }">
            {{ row.stockName }}
            <span v-if="row.stockCode && row.stockCode != ''">
              ({{ row.stockCode }})
            </span>
          </template>
        </vxe-grid>
      </div>
    </div>
    <!-- <check-list ref="checkRef"/> -->
    <!-- <restrict-apply v-if="restrictApplyVisible" ref="restrictApply" :add-flag="addFlag" :model-no="product.modelNo" @close-applyForm="closeRemove"/> -->
    <!-- <consult ref="consult" :show-type="consultType" :model-no="condition.modelNo" consult-type="three" /> -->
    <!-- <bom-invent-list v-if="bomDetailVisible" ref="bomInventRef" :data="bomInventoryList" :bomdata="bomList" @close="closeBomInvent"/> -->
  </div>
</template>

<script>
// import { Loading } from 'element-ui'
// import consult from './consult/consult'
// import apply from './restrictedProductManage/apply'
// import { findProductDetailByModelNo, findProductByModelNo, findSourceByModelNo, findShikomiInfoByModelNo
//    , findProductBomByModelNo, findProductDeliveryByModelNo, findCompetitionCoefficient
//    , findProductPhysicsByModelNo, findPriceByModelNo, findTnventoryListByModelNo, findEosInfoByModelNo, findRestrictInfoByModelNo, findSubstituteInfoByModelNo
//    , getInventoryColumn, getInventoryDetailColumn, quantityCanUseAll, getFuzzyColumn, findIsRestrictProduct, findAllFuzzyData } from '@/api/product'
import { findProductDetailByModelNo, findProductByModelNo, findSourceByModelNo
  , findProductBomByModelNo, findProductDeliveryByModelNo, findCompetitionCoefficient
  , findProductPhysicsByModelNo, findPriceByModelNo, findEosInfoByModelNo
  , getInventoryColumn, getInventoryDetailColumn, getFuzzyColumn, findProductBomDetailByModelNo
  , getWarehouseStock, getSpecStock, findRestrictInfoCustomerNoByModelNo, getSuppliercompany, findInventorySupplierByModel, findWhitelistByModelNo } from '@/api/product'
import { getDictDataByPid } from '@/api/common/dict'
import { findList } from '@/api/stock/shikomi'
// import { findSupplierByModelNo, findOrgCountryByModelNo } from '@/api/supplier'
// import { findLatest } from '@/api/taxRate'
// import checkList from './checkList'
import { varIsNotUnable, returnFloat } from '@/utils'
// import bomInventList from '@/views/saleManageSystem/common/bomInventList/index'
// import priceInfo from '@/views/saleManageSystem/quotation/quotation/priceInfo'
import { computePriceWithTax, computeStandardPrice } from '@/utils/saleUtils/saleCompute'
import { getSuppily } from '@/api/intercept'
// import { arrayToTreeLabel } from '@/utils'

export default {
  name: 'ProductDetail',
  inject: ['reload'],
  components: {
  //   'consult': consult,
  //   'restrict-apply': apply,
  //   'check-list': checkList
    // 'consult': consult,
    // 'restrict-apply': apply,
    // 'check-list': checkList,
    // 'price-info': priceInfo,
    // 'bom-invent-list': bomInventList
  },
  props: {
    customerNo: {
      type: String,
      default: null
    },
    customerName: {
      type: String,
      default: null
    },
    addFlag: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      // addFlag: true,
      // addFlag: true,
      customerNoTemp: this.customerNo,
      inventoryVisible: true,
      priceVisible: true,
      baseInfoVisible: true,
      priceInfoVisible: true,
      shikomiInfoVisible: true,
      physicsInfoVisible: true,
      deliveryInfoVisible: true,
      inventoryInfoVisible: true,
      splitInfoVisible: true,
      inventDetailVisible: true,
      shikomiTipDisabled: true,
      restrictApplyVisible: false,
      bomDetailVisible: false,
      bomLoading: false,
      condition: {
        modelNo: ''
      },
      productStatus: false,
      product: {
        baseInfo: {},
        eosInfo: { },
        physics: {},
        delivery: {},
        shikomiInfo: {},
        shikomiInfoList: [],
        isEos: '',
        canShikomi: true,
        isRestrict: false,
        restrictCustomer: '',
        substituteList: [],
        bom: [],
        modelNo: '',
        competitionCoefficient: '',
        orgCountry: ''
      },
      consultVisible: false,
      checkListLoading: false,
      page: {
        currentPage: 1,
        pageSize: 10,
        totalResult: 1
      },
      fuzzyPage: {
        currentPage: 1,
        pageSize: 20,
        pageSizes: [20, 50, 100, 200, 500],
        total: 1
      },
      defaultProps: {
        children: 'children',
        label: 'label'
      },
      agreementPriceList: [],
      suppilyList: [],
      suppilyCompanyList: [],
      agreePriceNodeList: [],
      ePriceList: [],
      deliveryList: [],
      noDataText: '无数据',
      noDataRequire: '请至少输入三个字符',
      noDataTextDefault: '无数据',
      userListloading: false,
      userId: this.$store.getters.info.userId,
      modelList: [],
      inventoryColumn: [],
      inventoryDetailColumn: [],
      inventoryList: [],
      binList: [],
      binUnfoldStatus: false,
      checkListVisible: false,
      productDetailStatus: false,
      tax: null,
      consultType: null,
      errorCode: null,
      inventoryLoading: false,
      fuzzyColumn: [],
      fuzzyLoading: false,
      fuzzyInventoryData: [],
      shikomiList: [],
      inventorySupplierList: [],
      fuzzyCondition: {},
      // bomInventoryObj: {},
      bomList: [],
      bomInventoryList: [],
      bomInventLoading: false,
      classType_typeid: '2021',
      classCode_typeid: '2022',
      modelClassCode: '2015',
      modelClassData: [],
      classTypes: [],
      classCodes: [],
      warehouseStockData: [],
      whitelist: []
    }
  },
  computed: {
    sumEffectiveInventory() {
      var sum = 0
      if (this.inventoryList) {
        this.inventoryList.map(v => {
          sum = sum + v.inventoryQuantity
        })
      }
      return sum
    }
  },
  watch: {
    $route: {
      // immediate: true,
      handler(val) {
        const query = val.query
        if (!Object.is(val.name, 'ProductDetail')) {
          return
        }
        this.customerNo = query.customerNo
        this.modelNo = this.$route.params.modelNo
      },
      deep: true
    },
    'condition.modelNo': {
      handler(val, oldV) {
        if (val) {
          this.condition.modelNo = val.toUpperCase()
          this.productDetail()
        }
      },
      deep: true
    }
  },
  activated() {
  },
  created() {
    this.getLatestRate()
    this.getSuppily()
    this.getSuppilyCompany()
    const params = this.$route.params
    if (Object.is(this.$route.name, 'ProductDetail') && params.modelNo) {
      this.condition.modelNo = params.modelNo
      this.customerNo = this.$route.query.customerNo
      this.modelNo = params.modelNo
      this.productDetail()
    }
    this.inventoryColumn = getInventoryColumn()
    this.inventoryDetailColumn = getInventoryDetailColumn()
    this.fuzzyColumn = getFuzzyColumn()

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
    getDictDataByPid(this.modelClassCode).then(result => {
      this.modelClassData = result.content
    }).catch(error => {
      console.log(error)
    })
  },
  methods: {

    closeBomInvent() {
      this.bomDetailVisible = false
    },
    getBomDetail() {
      this.bomDetailVisible = true
      this.$nextTick(() => {
        this.$refs.bomInventRef.bomInventLoading = this.bomInventLoading
      })
    },
    getSuppily() {
      getSuppily().then(res => {
        this.suppilyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    getSuppilyCompany() {
      getSuppliercompany().then(res => {
        this.suppilyCompanyList = res.data
      }).catch(error => {
        this.listLoading = false
        console.log(error)
      })
    },
    suppilyFormatter({ cellValue }) {
      const item = this.suppilyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    suppilyCompanyFormatter({ cellValue }) {
      const item = this.suppilyCompanyList.find(dict => dict.id === cellValue)
      return item ? item.name : cellValue
    },
    // getInventoryListByModelNoList(bomList) {
    //   if (!bomList || bomList.length === 0) {
    //     return
    //   }
    //   this.bomInventLoading = true
    //   findInventoryListByModelNoList(bomList, this.$store.getters.position.unitId).then(data => {
    //     this.bomInventLoading = false
    //     if (!data.content) {
    //       this.bomInventoryList = []
    //       return
    //     }
    //     this.bomInventoryList = data.content
    //   }).catch(e => {
    //     this.bomInventoryList = []
    //     this.bomInventLoading = false
    //   })
    // },
    // getIsRestrictProduct() {
    //   if (!this.customerNo || !this.product.modelNo) {
    //     return
    //   }
    //   findIsRestrictProduct(this.product.modelNo, this.customerNo).then(data => {
    //     if (!data.hasOwnProperty('data') || data.data === null) {
    //       return
    //     }
    //     var isRestrictWhiteList = data.data
    //     this.product.isRestrict = !isRestrictWhiteList
    //   })
    // },
    // bug7782  B91717 增加贩卖限制品白名单客户，点击显示
    getWhitelistByModelNo() {
      findWhitelistByModelNo(this.condition.modelNo).then(data => {
        if (data.data == null) {
          this.whitelist = []
          return
        }
        // if (!data.data || data.data.length === 0) {
        //   this.whitelist = []
        //   return
        // }
        this.whitelist = data.data
      }).catch(e => {
      })
    },
    sortChange({ column, property, order }) {
      console.log(property, order)
      if (order === null) {
        return
      }
      this.fuzzyCondition.property = property
      this.fuzzyCondition.order = order
      this.queryAll()
    },
    computeStandardPrice(eprice, taxRate) {
      return computeStandardPrice(eprice, taxRate)
    },
    findFuzzyData() {
      // var page = {
      //   pageNumber: this.fuzzyPage.currentPage,
      //   pageSize: this.fuzzyPage.pageSize
      // }
      this.fuzzyCondition.modelNo = this.condition.modelNo
      this.fuzzyLoading = true
      // findAllFuzzyData(this.fuzzyCondition, page).then(data => {
      //   this.fuzzyLoading = false
      //   if (!data || data.list.length === 0) {
      //     this.fuzzyInventoryData = []
      //     return
      //   }
      //   this.fuzzyInventoryData = data.list
      //   this.fuzzyPage.total = data.total
      // }).catch(e => {
      //   this.fuzzyLoading = false
      // })
    },
    getPageFuzzyInventoryData(val) {
      this.fuzzyPage.currentPage = val.currentPage
      this.fuzzyPage.pageSize = val.pageSize
      this.findFuzzyData()
    },
    getLatestRate() {
      // findLatest().then(data => {
      //   if (data) {
      this.taxRate = 0.13
      // }
      // })
    },
    handleCommand(item) {
      this.shikomiContent = '残数：' + item.onHandQuantity + ' 已预约:' + item.lockQuantity + '可用量:' + item.quantity
      this.product.shikomiId = item.shikomiId
      this.$forceUpdate()
    },
    selectModelNo(item) {
      this.product.substitute = item.substitute
      this.$forceUpdate()
    },
    openList() {
      this.inventDetailVisible = !this.inventDetailVisible
    },
    getTnventoryList() {
      console.log(this.$store.getters.position.unitId)
      this.inventoryLoading = true
      // findTnventoryListByModelNo(this.condition.modelNo, this.$store.getters.position.unitId).then(data => {
      //   this.inventoryLoading = false
      //   if (!data.content || data.content.length === 0) {
      //     this.inventoryList = []
      //     return
      //   }
      //   var inventoryList = data.content
      //   inventoryList.map(v => {
      //     var list = v.list
      //     if (!list || list.length === 0) {
      //       return
      //     }
      //     v.list = list.filter(m => {
      //       return m.qtyOnHand > 0
      //     })
      //     return v
      //   })
      //   this.inventoryList = inventoryList
      //   if (this.sumEffectiveInventory > 0) {
      //     this.inventDetailVisible = true
      //   } else {
      //     this.inventDetailVisible = false
      //   }
      // }).catch(e => {
      //   this.inventoryLoading = false
      // })
    },
    getBomInventoryList() {
      var bomList = []
      this.product.bom.forEach(m => {
        if (!bomList.includes(m.modelNo)) {
          bomList.push(m.modelNo)
        }
      })
      this.bomList = bomList
      // this.getInventoryListByModelNoList(bomList)
      // bomList.forEach(modelNo => {
      //   this.getInventoryByModelNo(modelNo).then(data => {
      //     if (data) {
      //       this.bomInventoryObj[data.modelNo] = data.inventoryList
      //     }
      //   })
      // })
    },
    getInventoryByModelNo(modelNo) {
      // const that = this
      return new Promise(function(resolve, reject) {
        // findTnventoryListByModelNo(modelNo, that.$store.getters.position.unitId).then(data => {
        //   if (!data.content && data.content.length === 0) {
        //     resolve()
        //     return
        //   }
        //   var obj = {}
        //   obj.modelNo = modelNo
        //   var inventoryList = data.content
        //   inventoryList.map(v => {
        //     var list = v.list
        //     if (!list || list.length === 0) {
        //       return
        //     }
        //     v.list = list.filter(m => {
        //       return m.qtyOnHand > 0
        //     })
        //     return v
        //   })
        //   obj.inventoryList = inventoryList
        //   resolve(obj)
        // }).catch(() => {
        //   // this.$refs.bomInventRef.bomInventLoading = false
        //   resolve()
        // })
      })
    },
    getBinList() {
      this.binUnfoldStatus = !this.binUnfoldStatus
    },
    mergeStockRowMethod({ row, rowIndex, column, data }) {
      const fields = ['stock', 'stockCode']
      const cellValue = row[column.property]
      if (cellValue && fields.includes(column.property)) {
        const preRow = data[rowIndex - 1]
        let nextRow = data[rowIndex + 1]
        let countRowspan = 1
        if (preRow && preRow[column.property] === cellValue) { // 如果上一行的field值存在，并且跟当前行的field值相等，说明已经合计完，不再合并行
          return { rowspan: 0, colspan: 0 }
        }
        while (nextRow && nextRow[column.property] === cellValue) { // 如果下一行数据存在，并且下一行的field值跟当前field相等，那么开始计算行数，并合计行
          nextRow = data[++countRowspan + rowIndex]
        }
        if (countRowspan > 1) {
          return { rowspan: countRowspan, colspan: 1 }
        }
      }

      return { rowspan: 1, colspan: 1 }
    },
    mergeBomRowMethod({ row, rowIndex, column, data }) {
      const fields = ['priorityLevel']
      const cellValue = row[column.property]
      if (cellValue && fields.includes(column.property)) {
        const preRow = data[rowIndex - 1]
        let nextRow = data[rowIndex + 1]
        let countRowspan = 1
        if (preRow && preRow[column.property] === cellValue) { // 如果上一行的field值存在，并且跟当前行的field值相等，说明已经合计完，不再合并行
          return { rowspan: 0, colspan: 0 }
        }
        while (nextRow && nextRow[column.property] === cellValue) { // 如果下一行数据存在，并且下一行的field值跟当前field相等，那么开始计算行数，并合计行
          nextRow = data[++countRowspan + rowIndex]
        }
        if (countRowspan > 1) {
          return { rowspan: countRowspan, colspan: 1 }
        }
      }

      return { rowspan: 1, colspan: 1 }
    },
    getEpriceList() {
      if (!this.condition.modelNo) {
        return []
      }
      findPriceByModelNo(this.condition.modelNo).then(data => {
        var ePriceList = data.data
        this.ePriceList = this.computePice(ePriceList)
      })
    },
    computePice(ePriceList) {
      if (ePriceList && ePriceList.length > 0) {
        ePriceList.map(v => {
          v.epriceWithTax = computePriceWithTax(v.eprice, this.taxRate) // taxRate=0.13
          v.eprice = returnFloat(v.eprice)
          const price = v.eprice
          v.standardPrice = computeStandardPrice(price, this.taxRate) // 取整
          return v
        })
      }
      return ePriceList
    },
    cancel() {
      this.checkListVisible = false
    },
    remove() {
      this.restrictApplyVisible = true
      this.$nextTick(() => {
        if (varIsNotUnable(this.customerName) && varIsNotUnable(this.customerNoTemp)) {
          this.$refs.restrictApply.formData.customerName = this.customerName
          this.$refs.restrictApply.formData.customerNo = this.customerNoTemp
          this.$refs.restrictApply.customerCodesOptionsForApply.push({ customerNo: this.customerNoTemp, cstmName: this.customerName })
        }
        this.$refs.restrictApply.windowVisibleForApply = true
      })
    },
    closeRemove() {
      this.restrictApplyVisible = false
    },
    inqBConsult() {
      this.consultVisible = true
      if (varIsNotUnable(this.customerNoTemp)) {
        this.$refs.consult.inqBForm.customerName = this.customerName
        this.$refs.consult.inqBForm.customerNo = this.customerNoTemp
        this.$refs.consult.customerCodesOptions.push({ customerNo: this.$refs.consult.inqBForm.customerNo, cstmName: this.$refs.consult.inqBForm.customerName })
        this.$refs.consult.customerVisiable = true
      } else {
        this.$refs.consult.customerVisiable = false
      }
      this.$refs.consult.condition.modelNo = this.condition.modelNo
      this.$refs.consult.getSupplier()
      this.$refs.consult.consultVisible = true
      this.consultType = 'inqb'
    },
    consult() {
      this.consultVisible = true
      this.$refs.consult.inqBForm.customerName = this.customerName
      this.$refs.consult.inqBForm.customerNo = this.customerNoTemp
      this.$refs.consult.customerCodesOptions.push({ customerNo: this.$refs.consult.inqBForm.customerNo, cstmName: this.$refs.consult.inqBForm.customerName })
      this.$refs.consult.consultVisible = true
      this.$refs.consult.condition.modelNo = this.condition.modelNo
      this.consultType = 'eprice'
    },
    confirm() {
      this.checkListVisible = false
    },
    getCheckList() {
      this.getModelList()
    },
    initFoldStatus() {
      this.priceVisible = true
      this.baseInfoVisible = true
      this.priceInfoVisible = true
      this.inventDetailVisible = true
    },
    findProductBeanByModelNo(modelNo) {
      return new Promise((resolve, reject) => {
        findProductDetailByModelNo([modelNo]).then(result => {
          if (!result || !result.data || result.data.length === 0) {
            resolve()
          }
          resolve(result.data[0])
        })
      })
    },
    productDetail() {
      return new Promise((resolve, reject) => {
        this.productStatus = false
        if (!this.condition.modelNo) {
          resolve()
          return
        }
        this.initFoldStatus()
        var modelNo = this.condition.modelNo.trim()
        this.condition.modelNo = modelNo
        // = Loading.service({ fullscreen: false, text: '加载中', target: document.querySelector('#product-detail-content') })
        this.findProductBeanByModelNo(this.condition.modelNo).then(data => {
          console.log('findProductBeanByModelNo', data)
          if (!data) {
            resolve()
            return
          }
          // const that = this
          if (data.productValidateResultList.some(validate => {
            if (validate.code >= 23) {
              this.errorCode = validate.code
              if (validate.code === 23) {
                // this.findFuzzyData()
              }
              return true
            }
          })) {
            this.$emit('change', data.productValidateResultList)
            resolve()
            return
          }
          this.$emit('change', data.productValidateResultList)
          this.product.modelNo = data.modelNo
          var product = data.product
          if (!product) {
            resolve()
            return
          }
          // bug 7782，B91717 判断是否为贩卖限制品或者收敛品时，分别查询 product_eos/product_restrict表
          this.findRestrictByModelNo()
          this.findEosByModelNo()
          // this.product.isEos = product.eos
          // this.product.isRestrict = product.restrict
          // if (this.product.isRestrict && this.product.isRestrict === true) {
          //   // this.getIsRestrictProduct()
          //   this.findRestrictByModelNo()
          // }
          this.ePriceList = this.computePice(data.productPriceList)
          // this.$emit('change', data.productValidateResultList)
          this.productStatus = true
          // this.getCompetitionCoefficient(data.competitivenessID)
          this.product.baseInfo = product
          this.product.isEos = product.eos
          // findEcode({ modelNo: this.condition.modelNo }).then(result => {
          // })
          // if (this.product.isEos) {
          //   this.findEosByModelNo()
          // }
          // this.findEosByModelNo()
          // this.getTnventoryList()
          this.getShikomiInfoByModelNo()
          this.findSubstitutesByModelNo()
          this.getEpriceList()
          this.getProductDeliveryByModelNo()
          this.getProductPhysicsByModelNo()
          // this.getProductBomByModelNo()
          this.getProductBomDetailByModelNo()
          // this.getSourceByModelNo()
          this.getSupplierByModelNo()
          this.getOrgCountryByModelNo()
          // 获取贩卖限制品白名单客户
          this.getWhitelistByModelNo()
          // 获取型号在各仓库的库存
          this.getWarehouseStockByModel()
          // 获取各个供应商库存
          this.getInventorySupplier()
          // bug7782  B91717 增加贩卖限制品白名单客户，点击显示
          resolve()
        })
      })
    },
    getSupplierByModelNo() {
      this.product.supplierCountry = ''
      // findSupplierByModelNo(this.condition.modelNo).then(data => {
      //   if (data && data.content) {
      //     this.product.supplierCountry = data.content.name
      //   }
      // })
    },
    getOrgCountryByModelNo() {
      this.product.orgCountry = ''
      // findOrgCountryByModelNo(this.condition.modelNo).then(data => {
      //   if (data && data.content) {
      //     this.product.orgCountry = data.content.name
      //   }
      // })
    },
    getSourceByModelNo() {
      this.product.sourceType = ''
      findSourceByModelNo(this.condition.modelNo).then(data => {
        if (data && data.data) {
          this.product.sourceType = data.data
          this.$forceUpdate()
        }
      })
    },
    getCustomerNos(list) {
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
    getShikomiInfoByModelNo() {
      this.product.shikomiInfo = {}
      this.product.shikomiInfoList = []
      this.shikomiList = []
      this.product.shikomiId = ''
      const data = {
        modelNo: this.condition.modelNo,
        pageSize: 20,
        pageNumber: 1
      }
      findList(data).then(data => {
        if (data.content && data.content.list.length > 0) {
          this.product.shikomiInfoList = data.content.list
          const item = this.product.shikomiInfoList[0]
          this.product.shikomiInfo = item
          this.shikomiList = []
          data.content.list.forEach(element => {
            // var customerNo
            // element.customerNo.forEach(elementCst => {
            //   if (customerNo) {
            //     customerNo = customerNo + ',' + elementCst
            //   } else {
            //     customerNo = elementCst
            //   }
            // })
            this.shikomiList.push({
              supplierCode: this.getSupplierCode(element.supplierCode),
              shikomiNo: element.shikomiNo,
              remainQty: element.remainQty,
              classType: this.getClassType(element.classType),
              classCode: this.getClassCode(element.classCode),
              customerNos: this.getCustomerNos(element.customerNos),
              asseDays: element.asseDays,
              partPrepareDays: element.partPrepareDays,
              mainModelFlag: this.getModelClassCode(element.mainModelFlag),
              lotQty: element.lotQty,
              registDate: element.registDate,
              qtyOrdPre: element.qtyOrdPre,
              customerNo: element.customerNo,
              indCode: element.indCode
            })
          })
        }
      })
    },
    // 获取型号在各仓库的在库
    getWarehouseStockByModel() {
      this.warehouseStockData = []
      const params = { 'modelNo': this.condition.modelNo }
      getWarehouseStock(params).then(res => {
        if (res.success) {
          this.warehouseStockData = res.data
        }
      })
    },

    // 获取各个供应商库存
    getInventorySupplier() {
      this.inventorySupplierList = []
      const params = { 'modelNo': this.condition.modelNo }
      findInventorySupplierByModel(params).then(res => {
        if (res.success) {
          if (res.data === '没有记录' || res.data == null) {
            return
          }
          this.inventorySupplierList = res.data
        }
      })
    },
    // 获取型号在某仓库的专备信息
    showSpecStock(row) {
      const $table = this.$refs.stockTable
      $table.clearRowExpand()
      $table.setRowExpand([row], true)
      if (row.specStock) {
        return
      }
      const data = {
        'modelNos': [row.modelNo],
        'warehouseCode': row.warehouseCode
      }
      getSpecStock(data).then(res => {
        if (res.success) {
          this.$set(row, 'specStock', res.content)
          $table.loadData(this.warehouseStockData)
        }
      })
    },
    getWarehouseName(val) {
      const obj = this.warehouseList.filter(item => item.warehouseCode === val)[0]
      return obj ? obj.warehouseName : ''
    },
    getClassType(val) {
      const obj = this.classTypes.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getClassCode(val) {
      const obj = this.classCodes.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getModelClassCode(val) {
      val = val + ''
      const obj = this.modelClassData.filter(item => item.code === val)[0]
      return obj ? obj.codeName : ''
    },
    getSupplierCode(val) {
      const obj = this.suppilyList.filter(item => item.id === val)[0]
      return obj ? obj.name : val
    },
    getProductByModelNo() {
      findProductByModelNo(this.condition.modelNo).then(data => {
        this.product.baseInfo = data.data
      })
    },
    getProductBomByModelNo() {
      this.bomLoading = true
      findProductBomByModelNo(this.condition.modelNo).then(data => {
        this.bomLoading = false
        if (!data.data || data.data.length === 0) {
          this.product.bom = []
          return
        }
        this.product.bom = data.data.map(v => {
          // v.effectiveInventory = 0
          // v.orgCountry = ''
          return v
        })
        // this.createBomInventory()
      }).catch(e => {
        this.bomLoading = false
      })
    },
    getProductBomDetailByModelNo() {
      this.bomLoading = true
      findProductBomDetailByModelNo(this.condition.modelNo).then(data => {
        this.bomLoading = false
        if (!data.data || data.data.length === 0) {
          this.product.bom = []
          return
        }
        this.product.bom = data.data.map(v => {
          // v.effectiveInventory = 0
          // v.orgCountry = ''
          return v
        })
        // this.createBomInventory()
      }).catch(e => {
        this.bomLoading = false
      })
    },
    createBomInventory() {
      const bom = this.product.bom
      for (let index = 0; index < bom.length; index++) {
        // var item = bom[index]
        // quantityCanUseAll(item.modelNo, this.$store.getters.position.unitId).then(data => {
        //   if (data.content) {
        //     bom[index].effectiveInventory = data.content
        //   }
        //   this.getBomInventoryList()
        // })
      }
    },
    getProductDeliveryByModelNo() {
      this.product.stdDlvDate = null
      // this.product.delivery = {}
      findProductDeliveryByModelNo(this.condition.modelNo).then(data => {
        if (data.data) {
          this.product.stdDlvDate = data.data[0].stddlvday
          this.deliveryList = data.data
          this.$forceUpdate()
        }
      })
    },
    toDetail() {
      var modelNo = this.product.substitute
      this.$router.push({
        path: '/product/detail/' + modelNo
      })
    },
    findEosByModelNo() {
      this.product.eosInfo = { }
      // this.product.isEos = false
      findEosInfoByModelNo(this.condition.modelNo).then(data => {
        console.log('findEosByModelNo', data)
        if (data.data) {
          this.product.eosInfo = data.data
          // bug 11053,前端显示收敛品根据 停止接单日期来判断
          // 判断停止接单日期是否为空
          // bug 11313 前端收敛标识从product取 is_eos 不在进行判断   update by lyc in 2023-7-5
          // this.product.isEos = true
          // if (!this.isEmpty(data.data.stopdate)) {
          //   const stopdate = new Date(data.data.stopdate)
          //   const dateNow = new Date()
          //   if (stopdate >= dateNow) {
          //     this.product.isEos = false
          //   }
          // }
        }
      })
    },
    findRestrictByModelNo() {
      this.product.isRestrict = false
      findRestrictInfoCustomerNoByModelNo(this.condition.modelNo).then(data => {
        if (data.data) {
          this.product.isRestrict = true
          this.product.restrictCustomer = data.data.customerno
        }
      })
    },
    findSubstitutesByModelNo() {
      this.product.substituteList = []
      // findSubstituteInfoByModelNo(this.condition.modelNo).then(data => {
      //   if (data.content && data.content.length > 0) {
      //     this.product.substitute = data.content[0].substitute
      //     this.product.substituteList = data.content
      //   }
      // })
    },
    getProductPhysicsByModelNo() {
      this.product.physics = {}
      findProductPhysicsByModelNo(this.condition.modelNo).then(data => {
        if (data.data) {
          this.product.physics = data.data
          this.$forceUpdate()
        }
      })
    },
    getCompetitionCoefficient(competitivenessID) {
      if (!competitivenessID) {
        this.product.competitionCoefficient = ''
        return
      }
      findCompetitionCoefficient(competitivenessID).then(data => {
        this.product.competitionCoefficient = data.data
      })
    },
    addCheckList() {
      this.$refs.checkRef.checkListVisible = true
    },
    formatDate(row) {
      // 获取单元格数据
      const data = row.row[row.column.property]
      if (data == null) {
        return null
      }
      const dt = new Date(data)
      return this.dayjs(dt).format('YYYY-MM-DD')
    },
    renderContentMenu(h, { node, data, store }) {
      if (data.level === '0') {
        return (
          <span class='custom-tree-node'>
            <span>{node.label}</span>
            <span>{data.priceExcludingTax}</span>
          </span>)
      }
      return (
        <el-tooltip content={node.label} open-delay={1000} placement='top' effect='light'>
          <span class='custom-tree-node'>
            <span>{node.label}</span>
          </span>
        </el-tooltip>)
    },
    isEmpty(val) {
      if (typeof val === 'undefined' || val == null || val.toString().trim() === '') {
        return true
      }
      return false
    },
    // 获取字符串长度（汉字算两个字符，字母数字算一个）
    getByteLen(str) {
      var realLength = 0
      var len = str.length
      var charCode = -1
      for (var i = 0; i < len; i++) {
        charCode = str.charCodeAt(i)
        if (charCode >= 0 && charCode <= 128) realLength += 1
        else realLength += 2
      }
      return realLength
    }
  }
}
</script>
