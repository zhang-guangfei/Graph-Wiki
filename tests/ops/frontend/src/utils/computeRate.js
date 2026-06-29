import { computeEprice, computeAmount } from '@/utils/saleUtils/saleCompute'
import { varIsUnable, math } from '@/utils'
/**
 * 计算平均E率/整单E率等
 */
export function computeRate(data) {
  var sumForm = {
    alphaE: Number(0),
    BetaE: Number(0),
    gammaE: Number(0),
    nE: Number(0),
    applyAllTypeNum: Number(0),
    applyAllNum: Number(0),
    totalAmount: Number(0),
    totalEAmount: Number(0),
    averageERate: Number(0)
  }
  const size = Number(data.length)
  var alphaSize = Number(0)
  var Betasize = Number(0)
  var gammaSize = Number(0)
  var nSize = Number(0)
  // var alphaETotal = Number(0)
  var alphaUnitPriceTotal = Number(0)
  var alphaEpriceTotal = Number(0)
  // var BetaETotal = Number(0)
  var BetaUnitPriceTotal = Number(0)
  var BetaEpriceTotal = Number(0)
  // var gammaETotal = Number(0)
  var gammaUnitPriceTotal = Number(0)
  var gammaEpriceTotal = Number(0)
  // var nETotal = Number(0)
  var nEUnitPriceTotal = Number(0)
  var nEEpriceTotal = Number(0)
  // var erateTotal = Number(0)
  data.forEach(element => {
    var eprice = computeEprice(element)
    if (element.competitivenessName === 'α') {
      alphaUnitPriceTotal = alphaUnitPriceTotal + math.multiply(Number(element.unitPrice), Number(element.quantity))
      alphaEpriceTotal = alphaEpriceTotal + math.multiply(Number(eprice), Number(element.quantity))
      // alphaETotal = alphaETotal + Number(computeAverageErate(element))
      alphaSize = alphaSize + Number(1)
    }
    if (element.competitivenessName === 'β') {
      BetaUnitPriceTotal = BetaUnitPriceTotal + math.multiply(Number(element.unitPrice), Number(element.quantity))
      BetaEpriceTotal = BetaEpriceTotal + math.multiply(Number(eprice), Number(element.quantity))
      // BetaETotal = BetaETotal + Number(computeAverageErate(element))
      Betasize = Betasize + Number(1)
    }
    if (element.competitivenessName === 'γ') {
      gammaUnitPriceTotal = gammaUnitPriceTotal + math.multiply(Number(element.unitPrice), Number(element.quantity))
      gammaEpriceTotal = gammaEpriceTotal + math.multiply(Number(eprice), Number(element.quantity))
      // gammaETotal = gammaETotal + Number(computeAverageErate(element))
      gammaSize = gammaSize + Number(1)
    }
    if (element.competitivenessName === 'N') {
      nEUnitPriceTotal = nEUnitPriceTotal + math.multiply(Number(element.unitPrice), Number(element.quantity))
      nEEpriceTotal = nEEpriceTotal + math.multiply(Number(eprice), Number(element.quantity))
      // nETotal = nETotal + Number(computeAverageErate(element))
      nSize = nSize + Number(1)
    }
    // erateTotal += Number(computeAverageErate(element))
    sumForm.applyAllNum += Number(element.quantity)
    // element.unitPriceWithTax = Number(computePriceWithTax(element.unitPrice, element.tax))
    sumForm.totalAmount += Number(computeAmount(element))
    sumForm.totalEAmount += Number(computeEprice(element) * Number(element.quantity))
  })
  var unitPriceTotal = Number(alphaUnitPriceTotal) + Number(BetaUnitPriceTotal) + Number(gammaUnitPriceTotal) + Number(nEUnitPriceTotal)
  var epriceTotal = Number(alphaEpriceTotal) + Number(BetaEpriceTotal) + Number(gammaEpriceTotal) + Number(nEEpriceTotal)
  sumForm.applyAllNum = +Number(sumForm.applyAllNum)
  sumForm.totalAmount = +Number(sumForm.totalAmount).toFixed(2)
  sumForm.totalEAmount = +Number(sumForm.totalEAmount).toFixed(2)
  sumForm.alphaE = alphaSize === +Number(0) ? Number(0) : Number(((Number(alphaUnitPriceTotal) / Number(alphaEpriceTotal)) - 1)).toFixed(3)
  sumForm.BetaE = Betasize === Number(0) ? Number(0) : Number(((Number(BetaUnitPriceTotal) / Number(BetaEpriceTotal)) - 1)).toFixed(3)
  sumForm.gammaE = gammaSize === Number(0) ? Number(0) : Number(((Number(gammaUnitPriceTotal) / Number(gammaEpriceTotal)) - 1)).toFixed(3)
  sumForm.nE = nSize === Number(0) ? Number(0) : Number(((Number(nEUnitPriceTotal) / Number(nEEpriceTotal)) - 1)).toFixed(3)
  sumForm.applyAllTypeNum = size
  sumForm.averageERate = size === Number(0) ? Number(0) : Number(((Number(unitPriceTotal) / Number(epriceTotal)) - 1)).toFixed(3)
  sumForm.wholeErate = +math.subtract(math.divide(sumForm.totalAmount, sumForm.totalEAmount), 1).toFixed(4)
  if (isNaN(sumForm.wholeErate)) {
    sumForm.wholeErate = null
  }
  return sumForm
}

/**
 * 单项E率
 * @param {} eprice E价格
 * @param {*} unitPrice 未税单价
 * @returns 单项E率
 */
export function computeSingleErate(eprice, unitPrice) {
  if (varIsUnable(eprice) || varIsUnable(unitPrice)) {
    return null
  }
  return +Number(math.subtract(math.divide(unitPrice, eprice), 1)).toFixed(3)
}

// 在库补库申请总计计算
export function computeTotal(data) {
  var sumForm = {
    applyAllTypeNum: Number(0),
    applyAllNum: Number(0),
    totalEAmount: Number(0)
  }
  sumForm.applyAllTypeNum = Number(data.length)
  data.forEach(element => {
    sumForm.applyAllNum += Number(element.quantity)
    sumForm.totalEAmount += Number(element.eprice * element.quantity)
  })
  sumForm.totalEAmount = Number(sumForm.totalEAmount).toFixed(2)
  return sumForm
}
