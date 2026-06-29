import { computeSingleErate } from '@/utils/computeRate'
import { varIsUnable, math } from '@/utils'
/**
 * 获取E价格
 * @param {*} list minQuantity正序
 * @param {*} quantity 需求数量
 * @return
 */
export function getEpriceByPriceListAndQty(list, quantity) {
  var eprice = 0
  if (!list || list.length === 0) {
    return eprice
  }
  if (list.length === 1) {
    eprice = list[0].ePrice
    return eprice
  }
  list.find((v, index) => {
    if (quantity >= v.minQuantity && (index === list.length - 1 || quantity < list[index + 1].minQuantity)) {
      eprice = v.ePrice
      return true
    }
  })
  return eprice
}
/**
 * 计算E价格
 * @param {*} row row包含 productPriceList多段价格 quantity数量
 * @returns eprice E价格
 */
export function computeEprice(row) {
  if (varIsUnable(row.productPriceList) || row.productPriceList.length < 0 || row.productPriceList.length === 0) {
    return Number.isNaN(Number(row.eprice)) ? null : Number(row.eprice).toFixed(2)
  }
  if (row.shikomiNo && row.shikomiQuanity) {
    return getEpriceByPriceListAndQty(row.productPriceList, row.shikomiQuanity)
  }
  return getEpriceByPriceListAndQty(row.productPriceList, row.quantity)
  // if (varIsUnable(row.productPriceList) || row.productPriceList.length < 0 || row.productPriceList.length === 0) {
  //   return Number.isNaN(Number(row.eprice)) ? null : Number(row.eprice).toFixed(2)
  // }
  // for (let i = 0; i < row.productPriceList.length; i++) {
  //   const element = row.productPriceList[i]
  //   if (i === 0 && row.quantity > 0 && row.quantity < row.productPriceList[0].minQuantity) {
  //     return Number.isNaN(Number(row.eprice)) ? null : Number(row.eprice).toFixed(2)
  //   }
  //   if (i + 1 < row.productPriceList.length && row.quantity >= element.minQuantity && row.quantity < row.productPriceList[i + 1].minQuantity) {
  //     row.minQuantity = Number(element.minQuantity)
  //     return Number.isNaN(Number(element.ePrice)) ? null : Number(element.ePrice).toFixed(2)
  //   }
  //   if (i + 1 === row.productPriceList.length && row.quantity >= element.minQuantity) {
  //     row.minQuantity = Number(element.minQuantity)
  //     return Number.isNaN(Number(element.ePrice)) ? null : Number(element.ePrice).toFixed(2)
  //   }
  // }
}

// 计算E率
export function computeErate(row) {
  var eprice = computeEprice(row)
  if (varIsUnable(eprice)) {
    return null
  }
  if (varIsUnable(row.unitPrice)) {
    return null
  }
  const eRate = computeSingleErate(eprice, row.unitPrice)
  // const eRate = Number(((Number(row.unitPrice) / Number(eprice)) - 1)).toFixed(3)
  return Number.isNaN(eRate) ? null : eRate
}

// 计算金额
export function computeAmount(row) {
  if (varIsUnable(row.unitPrice)) {
    return null
  }
  if (varIsUnable(row.quantity)) {
    return null
  }
  const amount = Number(Number(row.unitPriceWithTax) * Number(row.quantity)).toFixed(2)
  return Number.isNaN(amount) ? null : amount
}

// 计算溢价率
export function computePremiumRate(row) {
  const premiumRate = Number((varIsUnable(row.unitPrice) || varIsUnable(row.competitorPrice)) ? null : Number(((Number(row.unitPrice) - Number(row.competitorPrice)) * 0.5) / (Number(row.competitorPrice) * 0.5))).toFixed(2)
  return Number.isNaN(premiumRate) ? null : premiumRate
}

/**
 * 计算含税单价
 * @param {*} unitPrice 未税单价
 * @param {*} taxRate 税率
 * @returns 含税单价
 */
export function computePriceWithTax(unitPrice, taxRate) {
  if (!Number.isFinite(unitPrice) || !Number.isFinite(taxRate)) {
    return null
  }
  return +Number(math.multiply(unitPrice, math.add(1, taxRate))).toFixed(2)
}

/**
 * 计算未税单价
 * @param {*} unitPrice 含税单价
 * @param {*} taxRate 税率
 * @returns 含税单价
 */
export function computePriceWithoutTax(unitPriceWithTax, taxRate) {
  if (varIsUnable(unitPriceWithTax) || varIsUnable(taxRate)) {
    return null
  }
  return +Number(math.divide(unitPriceWithTax, 1 + taxRate)).toFixed(2)
}

/**
 * 计算含税金额
 * @param {*} unitPrice 未税单价
 * @param {*} quantity 数量
 * @param {*} taxRate 税率
 * @returns 含税金额
 */
export function computeAmountWithTax(unitPrice, quantity, taxRate) {
  if (varIsUnable(unitPrice) || varIsUnable(taxRate) || varIsUnable(quantity)) {
    return null
  }
  var unitPriceWithTax = Number(computePriceWithTax(unitPrice, taxRate)).toFixed(2)
  return +Number(math.multiply(unitPriceWithTax, quantity)).toFixed(2)
}

/**
 * 计算含税金额
 * @param {*} unitPriceWithTax 含税单价
 * @param {*} quantity 数量
 * @returns 含税金额
 */
export function computeAmountWithTax2(unitPriceWithTax, quantity) {
  if (varIsUnable(unitPriceWithTax) || varIsUnable(quantity)) {
    return null
  }
  return +Number(math.multiply(unitPriceWithTax, quantity)).toFixed(2)
}

/**
 * 单项税额
 * @param {单项税额} unitPriceWithTax 含税单价
 * @param {*} unitPrice 未税单价
 * @returns 单项税额
 */
export function computeSingleTaxAmount(unitPriceWithTax, unitPrice) {
  if (varIsUnable(unitPriceWithTax) || varIsUnable(unitPrice)) {
    return null
  }
  return +Number(math.subtract(unitPriceWithTax, unitPrice)).toFixed(2)
}

/**
 * 标准价
 * @param {*} eprice 未税E价
 * @param {*} taxRate 税率
 * @returns 标准价
 */
export function computeStandardPrice(eprice, taxRate) {
  if (varIsUnable(eprice) || varIsUnable(taxRate)) {
    return null
  }
  return +Number(math.add(math.divide(math.multiply(eprice, math.add(taxRate, 1)), 0.7), 0.5)).toFixed(0)
}
