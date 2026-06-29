import { findCustomerByCstmNoOrName, findTakenOnByCustomerNo, findPaymentTermByCustomerNo } from '@/api/customer'
// import { findCustomerDemandByYear } from '@/api/saleManageSystem/pneumaticDemand'
// import { findOrganizationByPsnSmcId } from '@/api/organ'
// import { findCustomerBalance } from '@/api/saleManageSystem/adapter'

const defaultAdress = [{ deliveryType: '3', address: '客户自提' }]
/**
 * 客户信息过滤
 * @param {*} userId 当前操作人Id
 * @param {*} key 搜索关键字
 * @returns {{ customerList: [], filterData: [] }}
 */
export function customerFilter(userId, key) {
  return new Promise(function(resolve, reject) {
    if (!key || key.length < 1) {
      resolve({ customerList: [], filterData: [] })
    } else {
      key = key.toUpperCase()
      findCustomerByCstmNoOrName(userId, key).then(data => {
        const customerCodesAllOptions = []
        for (const key in data) {
          customerCodesAllOptions[key] = data[key]
          // customerCodesAllOptions[key].customerNo = data[key].customerNo
          // customerCodesAllOptions[key].cstmName = data[key].cstmName
          customerCodesAllOptions[key].customerName = data[key].cstmName
          customerCodesAllOptions[key].customerGrade = data[key].cstmGrade
          var receiveAddressList = []
          if (data[key].customerShareInfo && data[key].customerShareInfo.length > 0) {
            var customerShareInfo = data[key].customerShareInfo
            var invoiceType = null
            const invoiceTypeList = customerShareInfo.filter(f => f.type === '1')
            if (Array.isArray(invoiceTypeList) && invoiceTypeList.length > 0) {
              invoiceType = invoiceTypeList[0].invoiceTypeDesc
            }
            data[key].invoiceType = invoiceType
            receiveAddressList = customerShareInfo.filter(f => f.type === '3')
            receiveAddressList = receiveAddressList.map(m => {
              // crm中0,1都是直发客户，2是直发营业所
              if (['0', '1'].includes(m.deliveryType)) {
                m.deliveryType = '1'
              }
              return m
            })
            receiveAddressList = defaultAdress.concat(receiveAddressList)
          }
          data[key].receiveAddressList = receiveAddressList
        }
        const filterData = customerCodesAllOptions.filter((item) => {
          if (!!~item.customerNo.toUpperCase().indexOf(key.toUpperCase()) || !!~item.cstmName.indexOf(key) || !!~item.cstmName.toUpperCase().indexOf(key.toUpperCase())) {
            return true
          }
        })
        resolve({ customerList: data, filterData: filterData })
      }).catch(error => {
        reject(error)
      })
    }
  })
}

export function initCustomerInfo(obj) {
  // 客户信息构建
  const p0 = new Promise(function(resolve) {
    const customerInfo = {}
    // 客户编码
    customerInfo.customerNo = obj.customerNo || null
    // 客户名称
    customerInfo.customerName = obj.cstmName || null
    // 客户等级
    customerInfo.customerGrade = obj.cstmGrade
    // 客户类型：直销代销
    customerInfo.customerType = obj.cstmType
    // 企业性质：中国民营、中国国企、日本、新加坡...
    customerInfo.customerProperty = obj.enterpriseNatureDes
    // 客户所属行业 行业代码 (中分类)
    // customerInfo.industry = obj.indCode
    // 客户担当Id
    customerInfo.customerTakeOnId = obj.psnsmcId
    // 客户担当所属部门
    customerInfo.customerTakeOnDepartCode = obj.hrunitId
    // 客户担当所属holon代码
    customerInfo.holonCode = obj.hlcode || null
    // 行业列表
    customerInfo.industryList = []
    // 客户地址
    customerInfo.addressList = obj.addressList
    var receiveAddressList = []
    var invoiceType = ''
    if (obj.customerShareInfo && obj.customerShareInfo.length > 0) {
      var customerShareInfo = obj.customerShareInfo
      const invoiceTypeList = customerShareInfo.filter(f => f.type === '1')
      if (Array.isArray(invoiceTypeList) && invoiceTypeList.length > 0) {
        invoiceType = invoiceTypeList[0].invoiceTypeDesc
      }
      receiveAddressList = customerShareInfo.filter(f => f.type === '3')
      receiveAddressList = receiveAddressList.map(m => {
        // crm中0,1都是直发客户，2是直发营业所
        if (['0', '1'].includes(m.deliveryType)) {
          m.deliveryType = '1'
        }
        return m
      })
      receiveAddressList = defaultAdress.concat(receiveAddressList)
    }
    customerInfo.invoiceType = invoiceType
    customerInfo.customerShareInfo = receiveAddressList
    // 交易主体
    customerInfo.tradeSubject = obj.tradeSubject
    if (obj.cstmTypeDetail) {
      if (obj.cstmTypeDetail === 'OEM') {
        customerInfo.customerTypeDetail = '配套客户'
      } else {
        customerInfo.customerTypeDetail = '配件客户'
      }
    }
    // 新的标准9位行业编码
    const industrys = obj.stanineIndustryList
    if (Array.isArray(industrys) && industrys.length > 0) {
      // 客户所属行业 行业代码 (中分类)
      customerInfo.industry = industrys[0].stanineCode
      industrys.forEach(element => {
        customerInfo.industryList.push({ label: element.stanineCode, value: element.stanineCode })
      })
    }
    // customerInfo.industryList.push({ label: obj.indCode, value: obj.indCode })

    resolve(customerInfo)
    // // 客户等级市场细分
    // customerInfo.marketCategoryCode = obj.marketCategoryCode
    // // 客户类型
    // customerInfo.customerType = obj.cstmType
    // // 客户状态 1 有效 0 无效
    // customerInfo.customerStatus = obj.laji
    // // 代理店代码
    // customerInfo.agentNo = obj.agentNo
    // // 客户地址
    // customerInfo.addressList = obj.addressList
    // 客户担当名称
    // customerInfo.customerTakeOnName = obj.customerTakeOnName
    // // 客户担当所属部门上级部门编码
    // customerInfo.customerTakeOnParentDepartCode = this.customerInfo.customerTakeOnParentDepartCode
    // // 客户担当所属部门名称
    // customerInfo.customerTakeOnDepartName = this.customerInfo.customerTakeOnDepartName
    // // 客户担当所属部门上级部门名称
    // customerInfo.customerTakeOnParentDepartName = this.customerInfo.customerTakeOnParentDepartName
  })

  const p1 = new Promise(function(resolve) {
    const saleDeptInfo = {}
    findTakenOnByCustomerNo(obj.customerNo).then(data => {
      if (!data.content || data.content.length === 0) {
        resolve(saleDeptInfo)
      }
      var customerTakeOn = []
      var hash = {}
      data.content.forEach(v => {
        if (hash.hasOwnProperty(v.userId)) {
          hash[v.userId].push(v)
          return
        }
        hash[v.userId] = [v]
      })
      Object.getOwnPropertyNames(hash).forEach(function(key) {
        const saleDeptList = hash[key]
        var obj = {}
        obj.userId = key
        obj.userName = hash[key][0].userName
        obj.saleDeptList = saleDeptList
        customerTakeOn.push(obj)
      })
      saleDeptInfo.customerTakeOn = customerTakeOn
      saleDeptInfo.customerTakeOnId = data.content[0].userId
      saleDeptInfo.customerTakeOnName = data.content[0].userName
      saleDeptInfo.customerTakeOnDepartCode = data.content[0].customerTakeOnDepartCode
      saleDeptInfo.customerTakeOnDepartName = data.content[0].customerTakeOnDepartName
      saleDeptInfo.customerTakeOnParentDepartCode = data.content[0].customerTakeOnParentDepartCode
      saleDeptInfo.customerTakeOnParentDepartName = data.content[0].customerTakeOnParentDepartName
      resolve(saleDeptInfo)
    }).catch(e => {
      resolve(saleDeptInfo)
    })
  })

  // const p1 = new Promise(function(resolve) {
  //   const saleDeptInfo = {}
  //   findOrganizationByPsnSmcId({ psnSmcId: obj.psnsmcId }).then(data => {
  //     if (!data.content || data.content.length === 0) {
  //       resolve(saleDeptInfo)
  //     }
  //     saleDeptInfo.saleDeptList = []
  //     data.content.forEach(element => {
  //       const saleDept = Object.assign({}, element)
  //       saleDept.label = element.name
  //       saleDept.value = element.id
  //       saleDeptInfo.saleDeptList.push(saleDept)
  //     })
  //     if (saleDeptInfo.saleDeptList.length === 0) {
  //       saleDeptInfo.hlcode = null
  //       saleDeptInfo.customerTakeOnParentDepartCode = null
  //       saleDeptInfo.customerTakeOnParentDepartName = null
  //       saleDeptInfo.customerTakeOnDepartName = null
  //       saleDeptInfo.holon = undefined
  //       resolve(saleDeptInfo)
  //     }
  //     saleDeptInfo.customerTakeOnDepartCode = saleDeptInfo.saleDeptList[0].id
  //     saleDeptInfo.customerTakeOnDepartName = saleDeptInfo.saleDeptList[0].name
  //     saleDeptInfo.customerTakeOnParentDepartCode = saleDeptInfo.saleDeptList[0].yysCode
  //     saleDeptInfo.customerTakeOnParentDepartName = saleDeptInfo.saleDeptList[0].yysName
  //     // boolean 类型看客户担当是否在holon中
  //     saleDeptInfo.holon = saleDeptInfo.saleDeptList[0].holon
  //     resolve(saleDeptInfo)
  //   }).catch(e => {
  //     resolve(saleDeptInfo)
  //   })
  // })

  // const p2 = new Promise(function(resolve) {
  //   const customerTakeOn = {}
  //   findCustomerBearList({ customerNo: obj.customerNo }).then(data => {
  //     if (!data.content || data.content.length === 0) {
  //       resolve(customerTakeOn)
  //     }
  //     data.content.forEach(element => {
  //       customerTakeOn.customerManagerList = []
  //       var customerManager = { label: '【' + element.userId + '】' + element.userName, value: element.userId }
  //       customerManager = Object.assign(customerManager, element)
  //       customerTakeOn.customerManagerList.push(customerManager)
  //       if (obj.psnsmcId === element.userId) {
  //         customerTakeOn.customerTakeOnName = element.userName
  //       }
  //     })
  //     resolve(customerTakeOn)
  //   }).catch(e => {
  //     resolve(customerTakeOn)
  //   })
  // })

  const p3 = new Promise(function(resolve) {
    // const customerAmountOwed = { customerAmountOwed: null }
    // findCustomerBalance(obj.customerNo).then(data => {
    //   if (!data.content) {
    //     resolve(customerAmountOwed)
    //   }
    //   customerAmountOwed.customerAmountOwed = data.content
    //   resolve(customerAmountOwed)
    // }).catch(e => {
    //   resolve(customerAmountOwed)
    // })
  })

  const p4 = new Promise(function(resolve) {
    // 获取某年smc气动需求及总气动需求
    // const pneumaticNeed = { smcPneumaticNeed: null, totalPneumaticNeed: null }
    // findCustomerDemandByYear({ customerNo: obj.customerNo, year: (new Date().getFullYear() - 1).toString() })
    //   .then((data) => {
    //     if (!data || !data.content) {
    //       resolve(pneumaticNeed)
    //     }
    //     pneumaticNeed.smcPneumaticNeed = data.content.smcPneumaticNeed
    //     pneumaticNeed.totalPneumaticNeed = data.content.totalPneumaticNeed
    //     resolve(pneumaticNeed)
    //   }).catch((e) => {
    //     resolve(pneumaticNeed)
    //   })
  })

  const p5 = new Promise(function(resolve) {
    const result = {}
    findPaymentTermByCustomerNo(obj.customerNo).then(data => {
      if (!data || !data.content) {
        resolve()
        return
      }
      result.paymentTerm = data.content
      result.paymentTerm.creditTerm = data.content.creditTerm | 60
      result.paymentTerm.settleDay = data.content.settleDay | 1
      resolve(result)
    })
  })

  return new Promise(function(resolve, reject) {
    Promise.all([p0, p1, p3, p4, p5])
      .then((result) => {
        if (!result || result.length !== 5) {
          resolve({})
        }
        // 将请求结果合并
        const resData = Object.assign({}, result[0], result[1], result[2], result[3], result[4])
        resolve(resData)
      }).catch((e) => {
        resolve({})
      })
  })
}

export function findAdressByCustomerNo(userId, customerNo) {
  return new Promise(function(resolve, reject) {
    findCustomerByCstmNoOrName(userId, customerNo).then(data => {
      if (!data) {
        resolve()
      }
      var customerInfo = data[0]
      var invoiceType = null
      const invoiceTypeList = customerShareInfo.filter(f => f.type === '1')
      if (Array.isArray(invoiceTypeList) && invoiceTypeList.length > 0) {
        invoiceType = invoiceTypeList[0].invoiceTypeDesc
      }
      customerInfo.invoiceType = invoiceType
      var customerShareInfo = customerInfo.customerShareInfo
      var receiveAddressList = customerShareInfo.filter(f => f.type === '3')
      receiveAddressList = receiveAddressList.map(m => {
        // crm中0,1都是直发客户，2是直发营业所
        if (['0', '1'].includes(m.deliveryType)) {
          m.deliveryType = '1'
        }
        return m
      })
      receiveAddressList = defaultAdress.concat(receiveAddressList)
      customerInfo.receiveAddressList = receiveAddressList
      resolve(customerInfo)
    })
  })
}

