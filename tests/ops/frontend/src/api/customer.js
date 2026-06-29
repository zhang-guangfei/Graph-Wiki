import request from '@/utils/request'

/**
 * 模糊查询
 * @param {登录人用户编码} userId
 * @param {搜索关键字} cstmNoOrName
 */
export function findCustomerByCstmNoOrName(userId, cstmNoOrName) {
  cstmNoOrName = cstmNoOrName ? cstmNoOrName.toUpperCase() : null
  return request({
    url: process.env.SALE_MANAGE_API + '/customer/findCustomerList',
    method: 'get',
    params: { userId, cstmNoOrName }
  })
}

export function findCustomerByNo(customerNo) {
  return request({
    url: process.env.OPS_API_BA + '/customer/findCustomerByNo',
    method: 'post',
    params: { customerNo }
  })
}

// 客户担当列表获取
export function findCustomerBearList(params) {
  return request({
    url: process.env.SALE_MANAGE_API + '/customer/findResponsibilityByCustomerNo',
    method: 'get',
    params: params
  })
}

// 客户担当列表(包含销售部门)获取
export function findTakenOnByCustomerNo(customerNo) {
  return request({
    url: process.env.SALE_MANAGE_API + '/customer/findTakenOnByCustomerNo',
    method: 'get',
    params: { customerNo }
  })
}

/**
 * 通过客户代码获取支付条件信息
 * @param {*} customerNo 客户代码
 * @returns 支付信息
 */
export function findPaymentTermByCustomerNo(customerNo) {
  return request({
    url: process.env.SALE_MANAGE_API + '/customer/findPaymentTermByCustomerNo',
    method: 'get',
    params: { customerNo }
  })
}

export function findDetailByCustomerNo(customerNo) {
  return new Promise(function(resolve, reject) {
    var data = {
      customerNo: customerNo,
      isArrears: true,
      arrearsAmount: 50000,
      industry: 'CP',
      holonName: 'HL01',
      customerManager: '张三',
      userGrade: '重要',
      haveSpecialPrice: true,
      deptName: '北京营业所'
    }
    resolve(data)
  })
}

export function findCustomersInStockByCustomerNo(customerNo) {
  return new Promise(function(resolve, reject) {
    var data = {
      customerNo: customerNo,
      isArrears: true,
      arrearsAmount: 50000,
      industry: 'CP',
      holonName: 'HL01',
      customerManager: '张三',
      userGrade: '重要',
      haveSpecialPrice: true,
      deptName: '北京营业所',
      customerType: '直销',
      customerLevel: 'vip',
      locaItem: '北京营业物流中心',
      isPeitao: '是',
      isBeijian: '是',
      isAutoTurnover: '是'
    }
    resolve(data)
  })
}
