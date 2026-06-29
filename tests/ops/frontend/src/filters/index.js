// set function parseTime,formatTime to filter
export { parseTime, formatTime } from '@/utils'
function pluralize(time, label) {
  if (time === 1) {
    return time + label
  }
  return time + label + 's'
}

export function timeAgo(time) {
  const between = Date.now() / 1000 - Number(time)
  if (between < 3600) {
    return pluralize(~~(between / 60), ' minute')
  } else if (between < 86400) {
    return pluralize(~~(between / 3600), ' hour')
  } else {
    return pluralize(~~(between / 86400), ' day')
  }
}

/* 数字 格式化*/
export function numberFormatter(num, digits) {
  const si = [
    { value: 1E18, symbol: 'E' },
    { value: 1E15, symbol: 'P' },
    { value: 1E12, symbol: 'T' },
    { value: 1E9, symbol: 'G' },
    { value: 1E6, symbol: 'M' },
    { value: 1E3, symbol: 'k' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (num >= si[i].value) {
      return (num / si[i].value + 0.1).toFixed(digits).replace(/\.0+$|(\.[0-9]*[1-9])0+$/, '$1') + si[i].symbol
    }
  }
  return num.toString()
}

export function toThousandFilter(num) {
  return (+num || 0).toString().replace(/^-?\d+/g, m => m.replace(/(?=(?!\b)(\d{3})+$)/g, ','))
}

export function formatPercent0(value) {
  let realVal = ''
  if (!isNaN(value) && value !== '' && value !== null) {
    realVal = (parseFloat(value) * 100).toFixed(0) + '%'
  }
  return realVal
}

export function formatPercent1(value) {
  let realVal = ''
  if (!isNaN(value) && value !== '' && value !== null) {
    realVal = (parseFloat(value) * 100).toFixed(1) + '%'
  }
  return realVal
}

export function formatDate(val) {
  if (val === null) {
    return
  }
  var value = new Date(val)
  var year = value.getFullYear()
  var padDate = function(va) {
    va = va < 10 ? '0' + va : va
    return va
  }
  var month = padDate(value.getMonth() + 1)
  var day = padDate(value.getDate())
  var hour = padDate(value.getHours())
  var minutes = padDate(value.getMinutes())
  var seconds = padDate(value.getSeconds())
  return year + '-' + month + '-' + day + ' ' + hour + ':' + minutes + ':' + seconds
}

export function formatDate2(val) {
  if (val === null) {
    return
  }
  var value = new Date(val)
  var year = value.getFullYear()
  var padDate = function(va) {
    va = va < 10 ? '0' + va : va
    return va
  }
  var month = padDate(value.getMonth() + 1)
  var day = padDate(value.getDate())
  return year + '-' + month + '-' + day
}

export const formatMoney = (number, decimals = 0, decPoint = '.', thousandsSep = ',') => {
  number = (number + '').replace(/[^0-9+-Ee.]/g, '')
  const n = !isFinite(+number) ? 0 : +number
  const prec = !isFinite(+decimals) ? 0 : Math.abs(decimals)
  const sep = (typeof thousandsSep === 'undefined') ? ',' : thousandsSep
  const dec = (typeof decPoint === 'undefined') ? '.' : decPoint
  let s = ''
  const toFixedFix = function(n, prec) {
    const k = Math.pow(10, prec)
    return '' + Math.ceil(n * k) / k
  }
  s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.')
  const re = /(-?\d+)(\d{3})/
  while (re.test(s[0])) {
    s[0] = s[0].replace(re, '$1' + sep + '$2')
  }
  if ((s[1] || '').length < prec) {
    s[1] = s[1] || ''
    s[1] += new Array(prec - s[1].length + 1).join('0')
  }
  return s.join(dec)
}

/**
 * 保留两位小数的千分符过滤器，主要针对金额的数据规范，例：1,123,111.12
 * @param {数字} val
 */
export function thousandFilter(val) {
  if (typeof (val) === 'number') {
    return (val).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')
  }
  if (typeof (val) === 'string' && !isNaN(Number(val))) {
    return (val).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')
  }
}

/**
 * 整数的千分符过滤器，主要针对数量的数据规范，例：1,123,111
 * @param {数字} val
 */
export function thousandNumFilter(val) {
  if (typeof (val) === 'number') {
    return val.toFixed(0).replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
  }
  if (typeof (val) === 'string' && !isNaN(Number(val))) {
    return val.replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
  }
}

/**
 * 百分制过滤器，保留一位小数，主要针对E率的数据规范，例：0.238 -> 23.8%
 * @param {*} val
 */
export function percentFilter(val) {
  if (typeof (val) === 'number') {
    return Number(val * 100).toFixed(1) + '%'
  }
  if (typeof (val) === 'string' && !isNaN(Number(val))) {
    return Number(Number(val) * 100).toFixed(1) + '%'
  }
}

/**
 * 百分制过滤器，保留一位小数，例：23.8 -> 23.8%
 * @param {*} val
 */
export function percentNoPlusFilter(val) {
  if (typeof (val) === 'number') {
    return Number(val).toFixed(1) + '%'
  }
  if (typeof (val) === 'string' && !isNaN(Number(val))) {
    return Number(Number(val)).toFixed(1) + '%'
  }
}

/**
 * 请购单类别
 * @param {*} val
 *
 */
export function ordTypeFormat(row) {
  const si = [
    { code: 'A', name: '销售订单' },
    { code: 'K', name: 'BIN补库' },
    { code: 'B', name: '先行在库补库' },
    { code: 'U', name: '加急订单' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].code) {
      return si[i].name
    }
  }
}

/**
 * 状态
 * @param {*} val
 *
 */
export function stateCodeFormat(row) {
  const si = [
    { code: '0', name: '待处理' },
    { code: '1', name: '处理中' },
    { code: '2', name: '待采购' },
    { code: '3', name: '已发送' },
    { code: '4', name: '拦截' },
    { code: '5', name: 'SHIKOMI拦截' },
    { code: '6', name: '已完成' },
    { code: '9', name: '拒单' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].code) {
      return si[i].name
    }
  }
}

/**
 * 运输方式
 */
export function transFormat(row) {
  const si = [
    { id: '0', name: '海运' },
    { id: '1', name: '空运' },
    { id: '3', name: '陆运' },
    { id: '4', name: '快船' },
    { id: '5', name: '铁路' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].id) {
      return si[i].name
    }
  }
}

/**
 * 采购单状态
 */
export function purchaseTypeFormat(row) {
  const si = [
    { id: '0', name: '待发送' },
    { id: '1', name: '已发送' },
    { id: '2', name: '已接单' },
    { id: '3', name: '运输中' },
    { id: '5', name: '已完成' },
    { id: '7', name: '转订删除' },
    { id: '8', name: '数据异常' },
    { id: '9', name: '删除' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].id) {
      return si[i].name
    }
  }
}

/**
 * 请购拦截配置信息
 * @param {*} row
 * @returns
 */
export function interceptFormat(row) {
  const si = [
    { id: '0', name: '按型号匹配' },
    { id: '1', name: '按客户匹配' },
    { id: '2', name: '自定义匹配' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].id) {
      return si[i].name
    }
  }
}

/**
 * 入库仓库
 * @param {*} row
 * @returns
 */
export function warehouseIdFormat(row) {
  const si = [
    { id: 'KBJ', name: '北京物流中心' },
    { id: 'KGZ', name: '广州物流中心' },
    { id: 'KSH', name: '上海物流中心' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].id) {
      return si[i].name
    }
  }
}

/**
 * 特殊标识，是否组装。。。
 * @param {*} row
 * @returns
 */
export function istrueFormat(row) {
  const si = [
    { code: '0', name: '否' },
    { code: '1', name: '是' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].code) {
      return si[i].name
    }
  }
}

/**
 * 特殊标识，是否组装。。。
 * @param {*} row
 * @returns
 */
export function isBooleanFormat(row) {
  const si = [
    { code: true, name: '是' },
    { code: false, name: '否' }
  ]
  for (let i = 0; i < si.length; i++) {
    if (row === si[i].code) {
      return si[i].name
    }
  }
}
