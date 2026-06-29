/**
 * 将 getAllTypeData from '@/api/dictionary'
 * 查出的data数据转换为[{ label: ***, value: ***}]格式数据
 * 如果 description 有值则 label值为 name 的值， value值为 description 的值
 * 否则 label，value 都为name的值
 * @param {数据字典查出的数据} dictionaryData
 */

export function transformOptions(dictionaryData) {
  var resArray = []
  if (dictionaryData === null || dictionaryData === undefined || dictionaryData.length === 0) {
    return resArray
  }
  dictionaryData.forEach(element => {
    resArray.push({ label: element.name, value: (element.description === '' || element.description === null || element.description === undefined) ? element.name : element.description })
  })
  return resArray.reverse()
}

export var Constants = {
  // 报价单状态列表
  quotationStatusList: { pid: '000002000000' },
  // 版本号状态列表
  versionList: { pid: '000002000001' },
  // 报价单激活状态列表
  activiStatusList: { pid: '000002000002' },
  // 报价分类列表
  quotationPriceTypeList: { pid: '000002000003' },
  approvalStatusList: { pid: '000003000000' },
  filterApprovalStatusList: ['000003000000005'],
  // 出货方式
  deliveryMethodOptionList: { pid: '000006000' },
  // 交货方式
  deliveryModeOptionList: { pid: '000006009' },
  // 运费承担方
  freightBearOptionList: { pid: '000006002' },
  // 集约方式
  intensiveOptionList: { pid: '000006001' },
  // 承运商
  carrierList: { pid: '000006003' },
  productConsultStatusCode: { pid: '000004000' },
  // 订单类型
  orderTypeList: { pid: '000006004' },
  // 催促问题
  questionList: { pid: '000006005' },
  // 货期提前原因
  reasonList: { pid: '000006006' },
  // 催促状态
  statusCodeList: { pid: '000006007' },
  // 逾期期限
  overdueDay: { pid: '000006008' },
  // 确认状态
  confirmStatusList: { pid: '000006012' },
  // 特价申请类型
  specialPriceTypeList: { pid: '000003000002' },
  // 特价状态
  specialPriceStatusList: { pid: '000003000001' },
  // 预占库存-申请状态
  applyStatusList: { pid: '000008001004' },
  // 预占库存-审批状态
  inventoryApprovalStatusList: { pid: '000008001005' },
  // 顾客在库补库申请-
  // 申请状态
  inStockApplyStatusList: { pid: '000008000000000' },
  // 申请类别
  inStockApplyTypeList: { pid: '000008000000001' },
  // 顾客在库补库审批-审批状态
  inStockApprovalStatusList: { pid: '000008000001000' },
  // 报价单-交易条款信息-运费负担
  freightPayerList: { pid: '000002001000' },
  // 报价单-交易条款信息-出货方式
  deliveryModeList: { pid: '000002001001' },
  // 报价单-交易条款信息-集约方式
  intensiveModeList: { pid: '000002001002' },
  // 报价单-交易条款信息-收货地址
  receivingAddressList: { pid: '000002001003' },
  // 报价单-交易条款信息-发票类型
  invoiceTypeList: { pid: '000002001004' },
  // 报价单-交易条款信息-赔偿条款
  indemnityClauseList: { pid: '000002001005' },
  // 报价单-交易条款信息-开票方式
  invoiceModeList: { pid: '000002001006' },
  // 客户在库调库-申请状态
  inStockTransferStatusList: { pid: '000008002000' },
  inStockTransferApprovalStatusList: { 'pid': '000008002001' },
  billingMethodList: { pid: '000002001006' },
  // 订单类型
  orderTypePid: { pid: '000010001' },
  credentialTypePid: { pid: '000010002' },
  assembleTypePid: { pid: '000004001' },
  orderStatusPid: { pid: '000010000' },
  rcvClassifyPid: { pid: '000010004' },
  orderApprovalStatusPid: { pid: '000010005' },
  // 订单问询-问题类型
  questionTypeList: { pid: '000006010' },
  // 订单问询-问询状态
  queryStatusList: { pid: '000006011' },
  // 采购单状态列表
  purchaseOrderStatusList: { pid: '000008003000' },
  supplierList: { pid: '000008003001' },
  // 仓库类别列表
  warehouseTypeList: { pid: '000011000' },
  // 库存状态列表
  inventoryStatusList: { pid: '000011001' },
  // 质量状态
  qualityStatusList: { pid: '000011002' },
  // 入库类型-收货指令
  roTypeList: { pid: '000011003' },
  // 仓库名-收货指令
  warehouseNameList: { pid: '000011004' },
  // 收货状态-收货指令
  roStatusList: { pid: '000011005' },
  // 供应商-收货指令
  supplierIdList: { pid: '000011006' }

}
