// 产品信息
import { findProductDetailByModelNo, findMaterialNumber } from '@/api/saleManageSystem/product'
// 获取参考价格， 交易价格校验->价格测算
import { findReferencePrice, validateQuotationPrice } from '@/api/saleManageSystem/quotationPrice'
// import { findReferencePrice, validateQuotationPrice } from '@/api/saleManageSystem/quotationPrice'

import { findProductTradeableByCondition } from '@/api/saleManageSystem/validate'
// 销售额获取
// import { isBinJudge, findPreengageWarehouseList, findBusinessPlaceList, findDeliveryDate, getSaleNumRecentMonths } from '@/api/saleManageSystem/adapter'
import { findSaleAmountByCondition, findSalesHistoryData } from '@/api/saleManageSystem/salesPriceAnalysis'
import { findShikomiData } from '@/api/saleManageSystem/shikomi'
// 预占库存数量校验
import { findValidateQuantity } from '@/api/saleManageSystem/preOccupiedInventoryApply'
// 在库调库平均半年月销售量获取
import { querySalesVolume } from '@/api/saleManageSystem/contractOrder'
// 型号产地获取
import { findSupplierListByModelNo } from '@/api/saleManageSystem/supplier'
// import { getAgreementPrice, getLastPrice } from '@/api/saleManageSystem/contractOrder'
import { lastPriceBatch, batchAgreementPrice } from '@/api/saleManageSystem/price'
import { getInStockBinModelInfo } from '@/api/saleManageSystem/customerInstockInventoryApply'
import { varIsUnable } from '@/utils'
import { varIsNotUnable } from '@/utils'
import { arrayIsEmpty } from '@/utils'
// import { overcomeUndef } from '@/utils'
// import { arrayIsNotEmpty } from '@/utils'
import { computeEprice, computeErate, computePriceWithoutTax, computeAmountWithTax2, computeSingleTaxAmount } from '@/utils/saleUtils/saleCompute'
import { computePremiumRate } from './saleCompute'
import sale from '@/constants/Sale.Constants'
import { availableSpecialPriceAutoMatch, availableSpecialPriceAutoMatchBatch } from '@/api/saleManageSystem/specialPrice'
/**
 * 型号校验
 * @param {* 型号列表}} modelNoList
 * @returns
 */
export function modelNoVerify(modelNoList) {
  return new Promise(function(resolve, reject) {
    findProductDetailByModelNo(modelNoList).then((data) => {
      if (!data.content || data.content.length === 0) {
        resolve(null)
      }
      const resData = []
      data.content.forEach((element, item) => {
        const product = element.product ? element.product : {}
        const productPriceList = element.productPriceList ? element.productPriceList : []
        const productValidateResultList = arrayIsEmpty(element.productValidateResultList) ? [] : element.productValidateResultList
        const detail = { modelNo: element.modelNo, itemNo: item + 1, delivery: 0,
          competitivenessName: null, competitivenessId: null, sourceTypeId: null,
          sourceTypeName: null, productName: null, assemble: null, minPackUnit: null }
        // 竞争系数 overcomeUndef 去除undefined 值
        detail.competitivenessName = product.competitivenessName || null
        // 竞争系数Id
        detail.competitivenessId = product.competitivenessId || null
        // 产品来源ID
        detail.sourceTypeId = product.sourceTypeId || null
        // 产品来源
        detail.sourceTypeName = product.sourceTypeName || null
        // 产品名称
        detail.productName = product.chineseName || null
        // 组装
        detail.assemble = product.assemble ? product.assemble : '正常'
        // 最小包装单位
        detail.minPackUnit = product.minPackUnit || null
        // 型号校验 将校验返回的code赋值
        if (productValidateResultList.length > 1) {
          detail.modelNoType = productValidateResultList[0].code + '' + productValidateResultList[1].code
          detail.modelTypeDescription = productValidateResultList[0].description + '并且' + productValidateResultList[1].description
        } else if (productValidateResultList.length === 0) {
          detail.modelNoType = sale.ERROR_MODEL_NO
          detail.modelTypeDescription = '该型号为错误型号！'
        } else {
          detail.modelNoType = productValidateResultList[0].code
          detail.modelTypeDescription = productValidateResultList[0].description
        }
        detail.productPriceList = productPriceList
        detail.shikomiNo = ''
        resData.push(detail)
        resolve(resData)
      })
    }).catch((e) => {
      reject(e)
    })
  })
}
/**
 * 型号校验
 * @param {* 型号列表}} modelNoList
 * @returns
 */
export function inStockModelNoVerify(modelNoList) {
  return new Promise(function(resolve, reject) {
    findProductDetailByModelNo(modelNoList).then((data) => {
      if (!data.content || data.content.length === 0) {
        resolve(null)
      }
      const resData = []
      data.content.forEach((element, item) => {
        const product = element.product ? element.product : {}
        const productPriceList = element.productPriceList ? element.productPriceList : []
        const productValidateResultList = arrayIsEmpty(element.productValidateResultList) ? [] : element.productValidateResultList
        const detail = { modelNo: element.modelNo, itemNo: item + 1, productName: null, minPackUnit: null }
        // 产品名称
        detail.productName = product.chineseName || null
        // 最小包装单位
        detail.minPackUnit = product.minPackUnit || 1
        // 型号校验 将校验返回的code赋值
        if (productValidateResultList.length > 1) {
          detail.modelNoType = productValidateResultList[0].code + '' + productValidateResultList[1].code
          detail.modelTypeDescription = productValidateResultList[0].description + '并且' + productValidateResultList[1].description
        } else {
          detail.modelNoType = productValidateResultList[0].code
          detail.modelTypeDescription = productValidateResultList[0].description
        }
        detail.productPriceList = productPriceList
        resData.push(detail)
        resolve(resData)
      })
    }).catch((e) => {
      reject(e)
    })
  })
}

/**
 * 特价-获取协议价和上次售价
 * @param {*} importData
 * @param {*} customerNo
 */
export function initAgreementPriceAndLastPrice(importData, customerNo, mergeData) {
  return new Promise(function(resolve, reject) {
    if (!importData || importData.length === 0) {
      resolve(null)
      return
    }
    var modelNos = []
    importData.forEach(v => {
      modelNos.push(v.modelNo)
    })
    batchAgreementPrice(customerNo, modelNos).then((result1) => {
      lastPriceBatch(modelNos, customerNo).then((result2) => {
        importData.map((m, index) => {
          for (let i = 0; i < mergeData.length; i++) {
            const elMerge = mergeData[i]
            if (elMerge.itemNo === (index + 1) && elMerge.modelNo === m.modelNo) {
              m = Object.assign(m, elMerge)
              if (varIsUnable(elMerge.unitPriceWithTax) || elMerge.unitPriceWithTax === 0 || elMerge.unitPriceWithTax === '0') {
                var lastPriceData
                if (result2.content) {
                  lastPriceData = result2.content.find(v => m.modelNo === v.modelNo && m.itemNo === v.itemNo)
                }
                if (lastPriceData) {
                  m.unitPriceWithTax = lastPriceData.salePrice
                }
                var agreementPriceData
                if (result1.content) {
                  agreementPriceData = result1.content.find(v => m.modelNo === v.modelNo && m.itemNo === v.itemNo)
                }
                if (agreementPriceData) {
                  m.unitPriceWithTax = agreementPriceData.priceExcludingTax
                }
              }
            }
          }
        })
        resolve(importData)
      }).catch((e) => {
        resolve(importData)
      })
    }).catch((e) => {
      resolve(importData)
    })
  })
}

/**
 * 初始化价格数据及率数据
 * @param {* 新增型号页面导入的产品数据} importData
 * @param {* 要合并的数据} mergeData
 * @returns
 */
export function initPriceAndRate(importData, mergeData, tax) {
  return new Promise(function(resolve, reject) {
    const resData = []
    if (!importData || importData.length === 0) {
      if (mergeData && mergeData.length >= 0) {
        resolve(mergeData)
      } else {
        resolve(resData)
      }
    }
    if (!mergeData || mergeData.length === 0) {
      if (importData && importData.length >= 0) {
        resolve(importData)
      } else {
        resolve(resData)
      }
    }
    // 计算总额 为了后面计算
    let totalAmout = 0
    importData.forEach(element => {
      if (varIsUnable(element.unitPrice) && varIsUnable(element.quantity)) {
        totalAmout = totalAmout + element.unitPrice * element.quantity
      }
    })
    importData.forEach((element, index) => {
      for (let i = 0; i < mergeData.length; i++) {
        const elMerge = mergeData[i]
        if (elMerge.itemNo === (index + 1) && elMerge.modelNo === element.modelNo) {
          const detail = Object.assign({}, element, elMerge)
          // 数量
          detail.quantity = Number(detail.quantity)
          // 客户协议价
          detail.customerAgreementPrice = varIsNotUnable(detail.customerAgreementPrice) ? Number(detail.customerAgreementPrice) : null
          // // 判断多段价格区间 设置E价格和最小起订量
          for (let i = 0; i < detail.productPriceList.length; i++) {
            const elPrice = detail.productPriceList[i]
            const length = detail.productPriceList.length
            if (detail.quantity > 0 && detail.quantity < elPrice.minQuantity) {
              detail.quantity = Number(elPrice.minQuantity)
            }
            if (i + 1 < length && detail.quantity >= elPrice.minQuantity && detail.quantity < detail.productPriceList[i + 1].minQuantity) {
              detail.eprice = Number(elPrice.ePrice)
              detail.minQuantity = Number(elPrice.minQuantity)
              break
            }
            if (i + 1 === length && detail.quantity >= elPrice.minQuantity) {
              detail.eprice = Number(elPrice.ePrice)
              detail.minQuantity = Number(elPrice.minQuantity)
              break
            }
          }
          detail.ePrice = computeEprice(detail)
          // 申请单价（含税） 如果没填直接赋值eprice
          detail.unitPriceWithTax = varIsUnable(detail.unitPriceWithTax) ? (varIsUnable(detail.eprice) ? null : Number(detail.eprice)) : Number(detail.unitPriceWithTax)
          // 申请单价未税
          detail.unitPrice = computePriceWithoutTax(detail.unitPriceWithTax, tax)
          // // e率 注意顺序 先有E价格才可以算
          // detail.erate = Number(detail.unitPrice / detail.eprice - 1).toFixed(2)
          // detail.amount = detail.unitPrice * detail.quantity
          detail.erate = computeErate(detail)
          // detail.amount = computeAmount(detail)
          detail.amount = computeAmountWithTax2(detail.unitPriceWithTax, detail.quantity)
          detail.singleTaxAmount = computeSingleTaxAmount(detail.unitPriceWithTax, detail.unitPrice)
          if (varIsUnable(detail.competitor)) {
            // 溢价率 （SMC销售价格-竞争对手5折价格）/ 竞争对手5折价格
            detail.premiumRate = 0
            detail.competitor = null
            detail.competitorModelNo = null
            detail.competitorPrice = null
            detail.shikomiNo = null
          } else {
            detail.competitor = detail.competitor
            detail.competitorModelNo = detail.competitorModelNo || null
            detail.competitorPrice = detail.competitorPrice || 0
            // 溢价率 （SMC销售价格-竞争对手5折价格）/ 竞争对手5折价格
            // detail.premiumRate = ((detail.unitPrice - detail.competitorPrice * 0.5) / (detail.competitorPrice * 0.5)).toFixed(2)
            detail.premiumRate = computePremiumRate(detail)
            detail.shikomiNo = null
          }
          detail.amountComposition = totalAmout === 0 ? Number(1).toFixed(2) : (Number(detail.unitPrice * detail.quantity) / totalAmout).toFixed(2)
          detail.expectDelivery = detail.expectDelivery ? detail.expectDelivery : null
          resData.push(detail)
          break
        }
      }
    })
    resolve(resData)
  })
}

/**
 * 初始化价格数据及率数据
 * @param {* 新增型号页面导入的产品数据} importData
 * @param {* 要合并的数据} mergeData
 * @returns
 */
export function inStockInitPrice(importData, mergeData) {
  return new Promise(function(resolve, reject) {
    const resData = []
    if (!importData || importData.length === 0) {
      if (mergeData && mergeData.length >= 0) {
        resolve(mergeData)
      } else {
        resolve(resData)
      }
    }
    if (!mergeData || mergeData.length === 0) {
      if (importData && importData.length >= 0) {
        resolve(importData)
      } else {
        resolve(resData)
      }
    }
    importData.forEach((element, index) => {
      for (let i = 0; i < mergeData.length; i++) {
        const elMerge = mergeData[i]
        if (elMerge.itemNo === (index + 1) && elMerge.modelNo === element.modelNo) {
          const detail = Object.assign({}, element, elMerge)
          // 数量
          if (varIsUnable(detail.quantity) && detail.productPriceList.length > 0) {
            detail.quantity = detail.productPriceList[0].minQuantity
          } else if (varIsUnable(detail.quantity) && detail.productPriceList.length === 0) {
            detail.quantity = 0
          }
          detail.quantity = Number(detail.quantity)
          // 判断多段价格区间 设置E价格和最小起订量
          if (detail.productPriceList.length > 0) {
            for (let i = 0; i < detail.productPriceList.length; i++) {
              const elPrice = detail.productPriceList[i]
              const length = detail.productPriceList.length
              if (detail.quantity > 0 && detail.quantity < elPrice.minQuantity) {
                detail.quantity = Number(elPrice.minQuantity)
              }
              if (i + 1 < length && detail.quantity >= elPrice.minQuantity && detail.quantity < detail.productPriceList[i + 1].minQuantity) {
                detail.eprice = Number(elPrice.ePrice).toFixed(2)
                detail.minQuantity = Number(elPrice.minQuantity)
                break
              }
              if (i + 1 === length && detail.quantity >= elPrice.minQuantity) {
                detail.eprice = Number(elPrice.ePrice).toFixed(2)
                detail.minQuantity = Number(elPrice.minQuantity)
                break
              }
            }
          } else {
            detail.eprice = 0.00
            detail.minQuantity = 1
          }
          // 申请单价 如果没填直接赋值eprice
          detail.eamount = Number(detail.eprice * detail.quantity).toFixed(2)
          detail.assemble = element.assemble ? element.assemble : '正常'
          resData.push(detail)
          break
        }
      }
    })
    resolve(resData)
  })
}
// 自动补库获取E价格
export function automaticInitPrice(importData, mergeData) {
  return new Promise(function(resolve, reject) {
    const resData = []
    if (!importData || importData.length === 0) {
      if (mergeData && mergeData.length >= 0) {
        resolve(mergeData)
      } else {
        resolve(resData)
      }
    }
    if (!mergeData || mergeData.length === 0) {
      if (importData && importData.length >= 0) {
        resolve(importData)
      } else {
        resolve(resData)
      }
    }
    importData.forEach((element, index) => {
      for (let i = 0; i < mergeData.length; i++) {
        const elMerge = mergeData[i]
        if (elMerge.itemNo === (index + 1) && elMerge.modelNo === element.modelNo) {
          const detail = Object.assign({}, element, elMerge)
          // 数量
          if (varIsUnable(detail.quantity) && detail.productPriceList.length > 0) {
            detail.quantity = 0
            detail.eprice = Number(detail.productPriceList[0].ePrice).toFixed(2)
            detail.minQuantity = Number(detail.productPriceList[0].minQuantity)
          } else if (!varIsUnable(detail.quantity) && detail.productPriceList.length > 0) {
            detail.quantity = Number(detail.quantity)
            detail.eprice = Number(detail.productPriceList[0].ePrice).toFixed(2)
            detail.minQuantity = Number(detail.productPriceList[0].minQuantity)
          } else {
            detail.minQuantity = 1
            detail.quantity = 0
            detail.eprice = 0.00
          }
          // 申请单价 如果没填直接赋值eprice
          detail.eamount = Number(detail.eprice * detail.quantity).toFixed(2)
          detail.assemble = element.assemble ? element.assemble : '正常'
          resData.push(detail)
          break
        }
      }
    })
    resolve(resData)
  })
}

export function initPrice(importData, mergeData) {
  return new Promise(function(resolve, reject) {
    const resData = []
    if (!importData || importData.length === 0) {
      if (mergeData && mergeData.length >= 0) {
        resolve(mergeData)
      } else {
        resolve(resData)
      }
    }
    if (!mergeData || mergeData.length === 0) {
      if (importData && importData.length >= 0) {
        resolve(importData)
      } else {
        resolve(resData)
      }
    }
    importData.forEach((element, index) => {
      for (let i = 0; i < mergeData.length; i++) {
        const elMerge = mergeData[i]
        if (elMerge.itemNo === (index + 1) && elMerge.modelNo === element.modelNo) {
          const detail = Object.assign({}, element, elMerge)
          // 数量
          if (detail.productPriceList.length === 0) {
            resolve(importData)
          }
          if (varIsUnable(detail.quantity)) {
            detail.quantity = detail.productPriceList[0].minQuantity ? detail.productPriceList[0].minQuantity : 1
          }
          detail.quantity = Number(detail.quantity)
          // 判断多段价格区间 设置E价格和最小起订量
          for (let i = 0; i < detail.productPriceList.length; i++) {
            const elPrice = detail.productPriceList[i]
            const length = detail.productPriceList.length
            if (detail.quantity > 0 && detail.quantity < elPrice.minQuantity) {
              break
            }
            if (i + 1 < length && detail.quantity >= elPrice.minQuantity && detail.quantity < detail.productPriceList[i + 1].minQuantity) {
              detail.eprice = Number(elPrice.ePrice)
              detail.minQuantity = Number(elPrice.minQuantity)
              break
            }
            if (i + 1 === length && detail.quantity >= elPrice.minQuantity) {
              detail.eprice = Number(elPrice.ePrice)
              detail.minQuantity = Number(elPrice.minQuantity)
              break
            }
          }
          // e率 注意顺序 先有E价格才可以算
          detail.epriceTotal = detail.eprice * detail.quantity
          resData.push(detail)
          break
        }
      }
    })
    resolve(resData)
  })
}
export function initInventoryBin(inputData, binModelCondition) {
  return new Promise(function(resolve, reject) {
    getInStockBinModelInfo(binModelCondition).then(data => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(inputData)
      }
      const binMap = data.content
      inputData.forEach(element => {
        for (var key in binMap) {
          if (key === element.modelNo) {
            element.beijingBin = binMap[key].binQuantityBJ
            element.shanghaiBin = binMap[key].binQuantitySH
            element.publicInstock = binMap[key].inventoryQuantity
            element.customerInstockInventory = binMap[key].customerQuantity
            element.historySalesQuantity = binMap[key].saleQuantity
            element.salesFrequency = binMap[key].saleRate
          }
        }
      })
      resolve(inputData)
    }).catch((e) => {
      resolve(inputData)
    })
  })
}
// 获取该客户的该型号近12月的平均销售量（月销售频率）/该分库该客户该型号的销售量
export function initSaleNumber(inputData, conditionList) {
  return new Promise(function(resolve, reject) {
    getSaleNumRecentMonths(conditionList).then(data => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(inputData)
      }
      const saleNumberData = data.content
      inputData.forEach(element => {
        for (let i = 0; i < saleNumberData.length; i++) {
          const saleNumber = saleNumberData[i]
          if (element.modelNo === saleNumber.modelNo) {
            if (conditionList[0].stockCode) {
              element.monthlySalesQuantity = (saleNumber.saleQuantity / 12).toFixed(2)
            } else {
              element.monthlySalesFrequency = (saleNumber.saleQuantity / 12).toFixed(2)
            }
            break
          }
        }
      })
      resolve(inputData)
    }).catch((e) => {
      console.log(e)
      resolve(inputData)
    })
  })
}
/** 近两年销售额的获取并拼接
 * @param {*} targetData
 * @param {*} modelNoList
 * @param {*} customerNo
 * @returns
 */
export function initSaleAmount(targetData, modelNoList, customerNo) {
  return new Promise(function(resolve, reject) {
    // 获取所有型号今年-去年销售量
    const saleAmountCondition = { modelNoList: modelNoList, customerNo: customerNo, yearNum: 2, year: new Date().getFullYear() }
    findSaleAmountByCondition(saleAmountCondition).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const saleAmountData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < saleAmountData.length; i++) {
          const elSaleAmount = saleAmountData[i]
          if (element.modelNo === elSaleAmount.modelNo) {
            element.currentYearSalesVolume = (elSaleAmount.salesAmount && elSaleAmount.salesAmount.length > 1) ? elSaleAmount.salesAmount[0].saleNum : 0
            element.lastYearSalesVolume = (elSaleAmount.salesAmount && elSaleAmount.salesAmount.length > 1) ? elSaleAmount.salesAmount[1].saleNum : 0
            break
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

// 获取所有型号客户物料号
export function initMaterialNumber(targetData, modelNoList, customerNo) {
  return new Promise(function(resolve, reject) {
    const materialNumberCondition = { modelNos: modelNoList, customerNo: customerNo }
    // 同步标识
    findMaterialNumber(materialNumberCondition).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      var materialNoList = data.content
      targetData.map(m => {
        var list = materialNoList.filter(f => f.modelNo === m.modelNo)
        m.materialNoList = !list ? [] : list
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/** 是否可交易校验 */
export function tradeableVerify(targetData, conditionList) {
  return new Promise(function(resolve, reject) {
    targetData.forEach(element => {
      for (let j = 0; j < conditionList.length; j++) {
        const elTrade = conditionList[j]
        if (element.modelNo === elTrade.modelNo) {
          elTrade.quantity = element.quantity
          break
        }
      }
    })
    findProductTradeableByCondition(conditionList).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const tradeData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < tradeData.length; i++) {
          const elTrade = tradeData[i]
          if (element.modelNo === elTrade.modelNo && element.quantity === elTrade.quantity) {
            element.tradeable = elTrade.tradeable
            element.errorMessage = elTrade.errorMessage
            element.modelTypeDescription = elTrade.errorMessage
            if (elTrade.tradeable) {
              element.modelNoType = sale.TRUE_MODEL_NO
              break
            }
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/**
 * 查询客户、型号在5年内是否具有销售数据
 * @param {*} targetData
 * @param {*} customerNo
 */
export function saleDataVerify(targetData, customerNo) {
  return new Promise(function(resolve, reject) {
    var modelNoList = []
    targetData.forEach(element => {
      modelNoList.push(element.modelNo)
    })
    const pramas = { customerNo: customerNo }
    findSalesHistoryData(modelNoList, pramas).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const tradeData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < tradeData.length; i++) {
          const elTrade = tradeData[i]
          if (elTrade !== null) {
            if (element.modelNo === elTrade.modelNo) {
              element.saleFlag = elTrade.dataFlag
            }
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/**
 * 查询型号客户是否有shikomi数据
 * @param {*} targetData
 * @param {*} customerNo
 */
export function shikomiVerify(targetData, customerNo) {
  return new Promise(function(resolve, reject) {
    var shikomiConditions = []
    targetData.forEach(element => {
      shikomiConditions.push({ modelNo: element.modelNo, quantity: element.quantity, customerNo: customerNo })
    })
    findShikomiData(shikomiConditions).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const tradeData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < tradeData.length; i++) {
          const elTrade = tradeData[i]
          if (elTrade !== null) {
            if (element.modelNo === elTrade.modelNo && element.quantity === elTrade.quantity) {
              element.shikomiFlag = elTrade.dataFlag
            }
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/**
 * 查询型号产地
 * @param {*} targetData
 * @param {*} modelNoList
 */
export function supplierVerify(targetData, modelNoList, quantityConditionList) {
  return new Promise(function(resolve, reject) {
    findSupplierListByModelNo(modelNoList).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const tradeData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < tradeData.length; i++) {
          const elTrade = tradeData[i]
          if (elTrade !== null) {
            const quantity = quantityConditionList[i]
            if (element.modelNo === elTrade.modelNo) {
              element.supplierId = elTrade.supplierId
              element.supplierName = elTrade.supplierName
              element.quantity = quantity.quantity
            }
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/**
 * 获取库房列表(预占库存)
 * @param {*} targetData
 * @param {*} condition
 */
export function initPreengageWarehouseList(targetData, customerNo, deptNo) {
  return new Promise(function(resolve, reject) {
    var modelNos = []
    targetData.forEach(element => {
      modelNos.push(element.modelNo)
    })
    const condition = { modelNo: modelNos, customerNo: customerNo, deptNo: deptNo }
    findPreengageWarehouseList(condition).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        targetData.forEach(element => {
          element.preengageWarehouseList = []
        })
        resolve(targetData)
      }
      const preengageWareHouseData = data.content
      targetData.forEach(element => {
        if (preengageWareHouseData[element.modelNo]) {
          element.preengageWarehouseList = preengageWareHouseData[element.modelNo]
        } else {
          element.preengageWarehouseList = []
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/**
 * 获取调库信息(在库调库)
 * @param {*} targetData
 * @param {*} customerNo
 * @param {*} deptNo
 */
export function initBusinessPlaceList(targetData, customerNo, deptNo) {
  return new Promise(function(resolve, reject) {
    var modelNos = []
    targetData.forEach(element => {
      modelNos.push(element.modelNo)
    })
    const condition = { modelNo: modelNos, customerNo: customerNo, deptNo: deptNo }
    findBusinessPlaceList(condition).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        targetData.forEach(element => {
          element.businessPlaceList = []
        })
        resolve(targetData)
      }
      const businessPlaceData = data.content
      targetData.forEach(element => {
        element.businessPlaceList = businessPlaceData[element.modelNo]
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/**
 * 获取半年平均月销售量
 * @param {*} targetData, 原始数据
 * @param {*} modelNoList, 型号列表
 * @param {*} customerNo, 客户编码
 */
export function getAverageVolume(targetData, modelNoList, customerNo) {
  return new Promise(function(resolve, reject) {
    const params = { customerNo: customerNo, modelNos: modelNoList }
    querySalesVolume(params).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const tradeData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < tradeData.length; i++) {
          const elTrade = tradeData[i]
          if (element.modelNo === elTrade.modelNo) {
            element.lastHalfYearAverageSalesVolume = elTrade.averageSalesQuantity
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/** 预占库存数量校验 */
export function quantityVerify(targetData, conditionList) {
  return new Promise(function(resolve, reject) {
    findValidateQuantity(conditionList).then((data) => {
      if (!data || !data.content) {
        resolve(targetData)
      }
      if (data.content.length === 0) {
        resolve(targetData)
      }
      const tradeData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < tradeData.length; i++) {
          const elTrade = tradeData[i]
          if ((element.itemNo) + '' === elTrade.itemNo) {
            element.quantityFlag = elTrade.success
            if (!elTrade.success) {
              element.quantityMessage = elTrade.message === undefined ? '该型号数量校验未通过' : elTrade.message
            }
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}

/**
 * 初始化参考价格
 * @param {*} targetData
 * @param {*} customerNo
 * @returns
 */
export function initReferencePrice(targetData, customerNo) {
  return new Promise(function(resolve, reject) {
    // 构建参考价格校验的数据
    const referenceConditionList = []
    targetData.forEach(element => {
      referenceConditionList.push({ customerNo: customerNo, modelNo: element.modelNo, itemNo: element.itemNo, eprice: element.eprice })
    })
    // 参考价格
    findReferencePrice(referenceConditionList).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const refData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < refData.length; i++) {
          const elRef = refData[i]
          if (element.itemNo === elRef.itemNo) {
            element.referencePrice = elRef.price
            element.referencePriceDescription = elRef.description
            element.referERate = elRef.erate
            if (element.referencePrice) {
              element.unitPriceWithTax = element.referencePrice
            }
            break
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      resolve(targetData)
    })
  })
}
/**
 * 交易价格校验
 * @param {*} targetData
 * @param {*} customerNo
 * @returns
 */
export function validateTransactionPrice(targetData, customerNo) {
  return new Promise(function(resolve, reject) {
    // 构建参考价格校验的数据以及交易价格校验数据
    const validateQuotationPriceConditions = []
    targetData.forEach(element => {
      if (element.modelNoType !== 23 && element.modelNoType !== 24) {
        validateQuotationPriceConditions.push({ itemNo: element.itemNo, customerNo: customerNo, competitivenessId: element.competitivenessId,
          modelNo: element.modelNo, quantity: element.quantity, applyPrice: element.unitPrice, shikomiNo: element.shikomiNo, availableQuantity: element.availableQuantity,
          shikomiQuantity: element.shikomiQuantity, specialPriceNo: element.specialPriceItemNo })
      }
    })
    // 交易价格校验
    validateQuotationPrice(validateQuotationPriceConditions).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const validateData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < validateData.length; i++) {
          const elValidate = validateData[i]
          if (Number(element.itemNo) === Number(elValidate.itemNo)) {
            element.calculationResult = elValidate.message
            element.priceCalculation = elValidate.success
            break
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      reject(e)
    })
  })
}

/**
 * 自动匹配特价号
 * @param {*} targetData
 * @param {*} customerNo
 */
export function specialPriceAutoMatch(targetData, customerNo, tableAllData) {
  return new Promise(function(resolve, reject) {
    const specialPriceList = []
    tableAllData.forEach(element => {
      if (varIsNotUnable(element.specialPriceItemNo)) {
        specialPriceList.push({ itemNo: element.itemNo, quantity: element.quantity, specialPriceNo: element.specialPriceItemNo })
      }
    })
    targetData.forEach(element => {
      // 价格测算未通过
      if (!element.priceCalculation) {
        // 自动匹配特价
        availableSpecialPriceAutoMatch(customerNo, element.itemNo, element.modelNo, element.quantity, element.unitPrice, specialPriceList).then((data) => {
          if (!data || !data.content) {
            resolve(targetData)
          }
          const autoMatchData = data.content
          for (var i = 0; i < specialPriceList.length; i++) {
            if (specialPriceList[i].itemNo === element.itemNo) {
              specialPriceList.splice(i, 1)
            }
          }
          if (varIsNotUnable(autoMatchData)) {
            element.specialPriceItemNo = autoMatchData.specialPriceNo
            specialPriceList.push({ itemNo: element.itemNo, quantity: element.quantity, specialPriceNo: autoMatchData.specialPriceNo })
          }
        }).catch((e) => {
          resolve(targetData)
          console.log(e)
        })
      }
    })
    resolve(targetData)
  })
}

/**
 * 自动匹配特价号批量
 * @param {*} targetData
 * @param {*} customerNo
 */
export function specialPriceAutoMatchBatch(targetData, customerNo, specialPriceList) {
  return new Promise(function(resolve, reject) {
    const usedQuantitylist = []
    targetData.forEach(element => {
      // 价格测算未通过
      if (!element.priceCalculation) {
        usedQuantitylist.push({ customerNo: customerNo, itemNo: element.itemNo, modelNo: element.modelNo, quantity: element.quantity, unitPrice: element.unitPrice, specialPriceList: specialPriceList })
      }
    })
    // 自动匹配特价
    availableSpecialPriceAutoMatchBatch(usedQuantitylist).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const autoMatchData = data.content
      targetData.forEach(element => {
        element.specialPriceItemNo = null
        for (let i = 0; i < autoMatchData.length; i++) {
          const elAutoMatch = autoMatchData[i]
          if (Number(element.itemNo) === Number(elAutoMatch.itemNo) && varIsNotUnable(elAutoMatch.specialPriceNo)) {
            element.specialPriceItemNo = elAutoMatch.specialPriceNo
            element.priceCalculation = true
            break
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      reject(e)
    })
  })
}

/**
 * bin品校验
 * @param {*} targetData
 * @param {*} customerTakeOnParentDepartCode
 */
export function validateBin(targetData, customerTakeOnParentDepartCode) {
  return new Promise(function(resolve, reject) {
    const modelNos = []
    targetData.forEach(element => {
      modelNos.push(element.modelNo)
    })
    const condition = { modelList: modelNos, deptNo: customerTakeOnParentDepartCode }
    isBinJudge(condition).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve(targetData)
      }
      const binData = data.content
      targetData.forEach(element => {
        for (let i = 0; i < binData.length; i++) {
          const elValidate = binData[i]
          if (element.modelNo === elValidate.modelNo) {
            element.binType = elValidate.modelType
            // 增加安全在库值
            element.binQuantity = elValidate.safeQuantity
            break
          }
        }
      })
      resolve(targetData)
    }).catch((e) => {
      reject(e)
    })
  })
}

/**
 * 合并两个表格数据按照型号相等规则
 * @param {* 目标数据} targetData
 * @param {* 源数据} sourceData
 * @returns
 */
export function mergeTableData(targetData, sourceData) {
  const resData = []
  if (targetData && sourceData && targetData.length >= 0 && sourceData.length >= 0) {
    targetData.forEach(elTarget => {
      for (let i = 0; i < sourceData.length; i++) {
        const elSource = sourceData[i]
        if (elTarget.modelNo === elSource.modelNo) {
          const detail = Object.assign(elTarget, elSource)
          resData.push(detail)
          break
        }
      }
    })
    return resData
  }
  return resData
}

export function getDeliveryDate(targetData, deptNo, customerNo) {
  return new Promise(function(resolve, reject) {
    const modelList = []
    targetData.forEach(element => {
      modelList.push({ modelNo: element.modelNo, quantity: element.quantity })
    })
    const condition = { deptNo: deptNo, customerNo: customerNo, modelList: modelList }
    findDeliveryDate(condition).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve([])
      }
      resolve(data.content)
    }).catch((e) => {
      reject(e)
    })
  })
}

export function getLogisticsDelivery(targetData, deptNo, customerNo) {
  return new Promise(function(resolve, reject) {
    const modelList = []
    targetData.forEach(element => {
      modelList.push({ modelNo: element.modelNo, quantity: element.quantity, dlvDate: element.expectDelivery, deliveryDay: element.delivery })
    })
    const condition = { deptNo: deptNo, customerNo: customerNo, modelList: modelList }
    findDeliveryDate(condition).then((data) => {
      if (!data || !data.content || data.content.length === 0) {
        resolve([])
      }
      resolve(data.content)
    }).catch((e) => {
      reject(e)
    })
  })
}
