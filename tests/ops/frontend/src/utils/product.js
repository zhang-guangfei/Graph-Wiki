
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
