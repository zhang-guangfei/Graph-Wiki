import request from '@/utils/request'

const saleApi = process.env.SALE_MANAGE_API
const adapterApi = process.env.ADAPTER_API
const mdmApi = process.env.SALE_MDM_API
const baApi = process.env.OPS_API_BA
const productApi = process.env.OPS_STOCK_API

/**
 * 数组转化为两层树形结构（重复部分为父节点）
 * @param {数组转化为两层树形结构} sNodes 数组
 */
export function arrayToTreeByParam(sNodes, parentId, parentName, id, name) {
  const childKey = 'children'
  if (!id || id === '' || !sNodes) return []

  if (Array.isArray(sNodes)) {
    const tmpMap = new Map()
    const r = []
    sNodes.forEach((item, index) => {
      tmpMap[item[parentId]] = Object.assign({}, sNodes[index])
      tmpMap[item[parentId]]['level'] = '0'
      tmpMap[item[parentId]]['label'] = item[parentName]
      tmpMap[item[parentId]]['value'] = item[parentId]
      tmpMap[item[parentId]]['level']
    })
    sNodes.forEach((item) => {
      if (!tmpMap[item[parentId]][childKey]) {
        tmpMap[item[parentId]][childKey] = []
      }
      item['level'] = '1'
      item['label'] = '【' + item[id] + '】' + item[name]
      item['value'] = item[id]
      tmpMap[item[parentId]][childKey].push(item)
    })
    console.log(tmpMap)
    for (var key in tmpMap) {
      r.push(tmpMap[key])
    }
    return r
  } else {
    return [sNodes]
  }
}

export function getInventoryColumn() {
  return [
    { type: 'expand', width: 30, slots: { content: 'detailExpand' }},
    { field: 'inventoryName', title: '库存类别', width: 90 },
    { field: 'inventoryQuantity', title: '可用数量', minWidth: 80 }
  ]
}

export function getInventoryDetailColumn() {
  return [
    { field: 'stock', title: '场所', minWidth: 120 },
    { field: 'stockCode', title: '库位', minWidth: 80 },
    { field: 'customerNo', title: '客户', minWidth: 200 },
    { field: 'quantity', title: '有效库存', minWidth: 80 },
    { field: 'qtyOnHand', title: '实际库存', minWidth: 80 },
    { field: 'qtyPrePare', title: '预约数量', minWidth: 80 },
    { field: 'qtyForecast', title: '预测占用数量', minWidth: 80 }
  ]
}

/**
 * 库存信息
 * @param {String} modelNo 型号
 * @param {String} deptNo 部门代码
 */
export function findTnventoryListByModelNo(modelNo, deptNo) {
  return request({
    url: adapterApi + '/inventory/detail',
    method: 'GET',
    params: { modelNo, deptNo }
  })
}

/**
 * 通过型号查询所有有效库存
 * @param {String} modelNo 型号
 * @param {String} deptNo 部门代码
 */
export function quantityCanUseAll(modelNo, deptNo) {
  var customerNo = ''
  return request({
    url: adapterApi + '/inventory/quantityCanUseAll',
    method: 'GET',
    params: { modelNo, deptNo, customerNo }
  })
}

export function findEosInfoByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findProductEosByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

export function findRestrictInfoByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findProductRestrictModelNoByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

export function findRestrictInfoCustomerNoByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findRestrictInfoCustomerNoByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 国别转换
 */
export function getSuppliercompany() {
  return request({
    url: baApi + '/ops/product/getSuppliercompany',
    method: 'GET'
  })
}

export function findSubstituteInfoByModelNo(modelNo) {
  return request({
    url: baApi + '/product/substitute/findByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号查询产品信息
 * @param {String} modelNo 型号
 */
export function findProductByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/product',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号集合查询产品详细信息（包含价格、验证状态）
 * @param {String} modelNo 型号
 */
export function findProductDetailByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findProductDetailByModelNo',
    method: 'POST',
    data: modelNo
  })
}

/**
 * 获取是否可交易信息
 * @param {*} conditionList [{modelNo 型号,customerNo 客户编码,deptNo 部门代码, quantity 申请数量}]
 */
// export function findProductTradeableByCondition(conditionList) {
//   return request({
//     url: mdmApi + '/product/findProductTradeableByCondition',
//     method: 'POST',
//     data: conditionList
//   })
// }

/**
 * 根据SMC型号、竞争对手型号、竞争对手等信息查询竞争对手型号信息
 * @param {*} params smc型号(精确)、竞争对手(模糊)、竞争型号(模糊)
 */
export function findCompetitorModelMapping(condition) {
  return request({
    url: baApi + '/ops/product/findCptModelMapping',
    method: 'GET',
    params: condition
  })
}

/**
 * 根据竞争名称模糊查询竞争对手信息
 * @param {*} params name
 */
export function findCompetitorList(params) {
  return request({
    url: mdmApi + '/product/cptModelMapping/findCompetitorByName',
    method: 'GET',
    params: params
  })
}

/**
 * 通过型号模糊查询产品信息列表前十条
 * @param {String} modelNo 型号
 */
export function findList(modelNo) {
  return request({
    url: baApi + '/ops/product/findByModelNoLike',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号查询价格信息
 * @param {String} modelNo 型号
 */
export function findPriceByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findProductPriceByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号查询产品交付相互参考信息
 * @param {String} modelNo 型号
 */
export function findProductDeliveryByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findProductDeliveryByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号查询产品物理信息
 * @param {String} modelNo 型号
 */
export function findProductPhysicsByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findProductPhysicsByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过CompetitivenessID查询竞争系数
 * @param {String} id 代码
 */
export function findCompetitionCoefficient(id) {
  return request({
    url: baApi + '/ops/product/findProductCompetitivenessType',
    method: 'GET',
    params: { id }
  })
}

/**
 * 通过modelNo查询贩卖限制品
 * @param {String} modelNo 型号
 */
export function findRestrictByModelNoLike(modelNo) {
  return request({
    url: mdmApi + '/product/restrict/findByModelNoLike',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号查询产品拆分信息
 * @param {String} modelNo 型号
 */
export function findProductBomByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/bom',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号查询产品拆分信息
 * @param {String} modelNo 型号
 */
export function findProductBomDetailByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/bom/detailbymodel',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 通过型号查询SHIKOMI信息
 * @param {String} modelNo 型号
 */
export function findShikomiInfoByModelNo(modelNo) {
  return request({
    url: adapterApi + '/shikomi/findByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 分页查询相似型号
 * @param {String} modelNo 型号
 */
export function findFuzzyInventory(data, page) {
  return request({
    url: adapterApi + '/inventory/findFuzzyInventory',
    method: 'POST',
    params: page,
    data
  })
}

/**
 * 通过型号查询产品来源
 * @param {String} modelNo 型号
 */
export function findSourceByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findSourceByModelNo',
    method: 'GET',
    params: { modelNo }
  })
}

/**
 * 查询客户物料号
 * @param {*} condition cutomerNo modelNos materialNumber
 * @returns
 */
export function findMaterialNumber(data) {
  return request({
    url: saleApi + '/materialNumber/findMaterialNumber',
    method: 'POST',
    data
  })
}

export function findDeliveryByModelNoList(data) {
  return request({
    url: mdmApi + '/product/delivery/findByModelNoList',
    method: 'POST',
    data
  })
}

/**
 * 批量查询物理信息
 * @param {*} data 型号集合
 * @returns 物理信息
 */
export function findPhysicsByModelNoList(data) {
  return request({
    url: mdmApi + '/product/physics/findByModelNoList',
    method: 'POST',
    data
  })
}

/**
 * 模糊查询列表的列
 */
export function getFuzzyColumn() {
  return [
    { field: 'modelNo', title: '型号', minWidth: 150, showOverflow: true, sortable: true },
    { field: 'quantity', title: '有效库存', sortable: true, minWidth: 100, showOverflow: true },
    { field: 'stockCode', title: '库房名称', sortable: true, slots: { default: 'stockCodeDefault' }, minWidth: 80, showOverflow: true },
    { field: 'customerNo', title: '客户', sortable: true, slots: { default: 'customerDefault' }, minWidth: 160, showOverflow: true },
    { field: 'stdDlvDate', title: '标准货期(天)', minWidth: 70, showOverflow: true },
    { field: 'standardPrice', slots: { default: 'standardPriceDefault' }, title: '标准价(元)', minWidth: 80, showOverflow: true },
    { field: 'eprice', title: 'E价格(元)', minWidth: 80, showOverflow: true },
    { field: 'ecode', title: 'Ecode', minWidth: 80, showOverflow: true },
    { field: 'netweight', title: '净重(kg)', minWidth: 80, showOverflow: true }
  ]
}

export function findAllFuzzyData(condition, page) {
  const mainData = new Promise(function(resolve) {
    findFuzzyInventory(condition, page).then(data => {
      if (!data.content || data.content.list.length === 0) {
        resolve(null)
        return
      }
      resolve(data.content)
    }).catch(e => {
      resolve()
    })
  })
  return new Promise(function(resolve, reject) {
    mainData.then((data) => {
      if (!data) {
        resolve(null)
        return
      }
      var modelNoList = []
      data.list.map(f => {
        f.eprice = null
        f.stdDlvDate = null
        f.netweight = null
        if (f.modelNo) {
          modelNoList.push(f.modelNo)
        }
        return f
      })
      if (!modelNoList || modelNoList.length === 0) {
        resolve(null)
        return
      }
      findProductDetailByModelNoList(modelNoList).then(data2 => {
        if (!data2) {
          resolve(data)
          return
        }
        data.list.map(f => {
          var info = data2.find(v => v.modelNo === f.modelNo)
          if (info) {
            f.eprice = info.eprice
            f.stdDlvDate = info.stdDlvDate
            f.netweight = info.netweight
            f.ecode = info.ecode
          }
          return f
        })
        resolve(data)
      })
    })
  })
}

/**
 * 批量查询产品信息
 * @param {*} modelNoList 型号集合
 */
export function findProductDetailByModelNoList(modelNoList) {
  const product = new Promise(function(resolve) {
    findProductDetailByModelNo(modelNoList).then(data => {
      if (!data || !data.content) {
        resolve([])
        return
      }
      resolve(data.content)
    })
  })
  const dlvDateList = new Promise(function(resolve) {
    findDeliveryByModelNoList(modelNoList).then(data => {
      if (!data || !data.content) {
        resolve([])
        return
      }
      resolve(data.content)
    })
  })
  const physicsData = new Promise(function(resolve) {
    findPhysicsByModelNoList(modelNoList).then(data => {
      if (!data || !data.content) {
        resolve([])
        return
      }
      resolve(data.content)
    })
  })
  return new Promise(function(resolve, reject) {
    // saleAmount,
    Promise.all([product, dlvDateList, physicsData]).then((result) => {
      if (!result || result.length === 0) {
        resolve([])
      }
      var allData = []
      var productData = result[0]
      var dlvDateData = result[1]
      var physicsData = result[2]
      modelNoList.forEach(f => {
        var obj = {}
        obj.modelNo = f
        var product = productData.find(v => f === v.modelNo)
        var dlvDateObj = dlvDateData.find(v => v.modelNo === f)
        var physicsObj = physicsData.find(v => v.modelNo === f)
        if (product && product.productPriceList) {
          // obj.priceList = product.productPriceList
          if (product.productPriceList && product.productPriceList.length > 0) {
            obj.eprice = Number(product.productPriceList[0].ePrice).toFixed(2)
          }
          if (product.product) {
            obj.ecode = product.product.eCode
          }
        }
        if (dlvDateObj) {
          obj.stdDlvDate = dlvDateObj.stdDlvDate
        }
        if (physicsObj) {
          obj.netweight = physicsObj.netweight
        }
        allData.push(obj)
      })
      resolve(allData)
    })
  })
}

/**
 * 查询是否对指定客户是贩卖限制品
 * @param {*} data 型号集合
 * @returns 物理信息
 */
export function findIsRestrictProduct(modelNo, customerNo) {
  return request({
    url: baApi + '/ops/product/findProductRestrictCustomerWhiteListByCustomerNo',
    method: 'GET',
    params: { customerNo }
  })
}
/**
 * 查询贩卖限制品白名单
 * @param {*} data
 * bug7782 B91717 获取白名单客户部门等信息
 * @returns
 */
export function findWhitelistByModelNo(modelNo) {
  return request({
    url: baApi + '/ops/product/findRestrictCustomerWhiteList',
    method: 'GET',
    params: { modelNo }
  })
}

// 根据型号查询在库通用库存与在库专用库存
export function getWarehouseStock(params) {
  return request({
    url: baApi + '/ops/product/findInventoryByModel',
    method: 'post',
    params: params
  })
}

// 查询客户专备情况
export function getSpecStock(data) {
  return request({
    url: productApi + '/product/inventory/listCustomerSpecStock',
    method: 'post',
    data: data
  })
}

export function getModelInfoForOrder(modelNo) {
  return request({
    url: productApi + '/product/model/getModelInfoForOrder',
    method: 'get',
    params: { modelNo }
  })
}

export function findInventorySupplierByModel(params) {
  return request({
    url: baApi + '/ops/product/findInventorySupplier',
    method: 'POST',
    params: params
  })
}

// 判断多段价格区间 设置E价格和最小起订量
export function getEPriceByQuantity(priceList, quantity) {
  const productPrice = {
    minQuantity: 0,
    ePrice: 0
  }
  const length = priceList.length
  if (!(priceList && quantity)) {
    return productPrice
  }
  let elPrice = null
  for (let i = 0; i < priceList.length; i++) {
    elPrice = priceList[i]
    if (length === 1) {
      break
    }
    if (i + 1 < length && quantity <= elPrice.minQuantity && quantity > priceList[i + 1].minQuantity) {
      break
    }
    if (i + 1 === length) {
      break
    }
  }
  if (elPrice) {
    productPrice.ePrice = Number(elPrice.eprice)
    productPrice.minQuantity = Number(elPrice.minquantity)
  }
  return productPrice
}

export function downloadTemplate() {
  return request({
    url: productApi + '/product/SMSAdapter/downloadTemplate',
    method: 'get',
    responseType: 'blob'
  })
}
export function importIntercepterData(data) {
  return request({
    url: productApi + '/product/SMSAdapter/importIntercepterData',
    method: 'post',
    data: data
  })
}
