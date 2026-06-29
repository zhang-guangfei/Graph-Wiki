import { parseTime } from '@/utils'
const shortcuts = {
  shortcuts: [{
    text: '最近一周',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(
        start.getTime() - 3600 * 1000 * 24 * 7
      )
      picker.$emit('pick', [start, end])
    }
  },
  {
    text: '最近一个月',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(
        start.getTime() - 3600 * 1000 * 24 * 30
      )
      picker.$emit('pick', [start, end])
    }
  },
  {
    text: '最近三个月',
    onClick(picker) {
      const end = new Date()
      const start = new Date()
      start.setTime(
        start.getTime() - 3600 * 1000 * 24 * 90
      )
      picker.$emit('pick', [start, end])
    }
  }
  ]
}

export { shortcuts }

export function getTimeArea(type) {
  const end = new Date()
  const start = new Date()
  start.setTime(start.getTime() - 3600 * 1000 * 24 * 7)
  if (type === 'str') {
    return [parseTime(start, '{y}-{m}-{d} {h}:{i}:{s}'), parseTime(end, '{y}-{m}-{d} {h}:{i}:{s}')]
  } else {
    return [start, end]
  }
}
/**
 *获取日期的年月日格式
 * @param {*} date
 */
export function timeChange(date) {
  const newTime = new Date(date)
  const y = newTime.getFullYear()
  const m = newTime.getMonth() + 1
  const d = newTime.getDate()
  return y + '.' + m + '.' + d
}

export function getTimeAreaMonth(type) {
  const end = new Date()
  const start = new Date()
  start.setTime(start.getTime() - 3600 * 1000 * 24 * 30)
  if (type === 'str') {
    return [parseTime(start, '{y}-{m}-{d} {h}:{i}:{s}'), parseTime(end, '{y}-{m}-{d} {h}:{i}:{s}')]
  } else {
    return [start, end]
  }
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

export function formatExcelTime(num, format = '-') {
  if (num === null || num === undefined || num === '') {
    return ''
  }
  num = Number(num)	// 强制类型转化，以防传来的值是字符串
  let millisecond = 0	// 转化后的毫秒数
  if (num > 60) {
    millisecond = (num - 25569) * 60 * 60 * 24 * 1000
  } else {
    millisecond = (num - 25568) * 60 * 60 * 24 * 1000
  }
  const date = new Date(millisecond)	// 根据转化后的毫秒数获取对应的时间
  const yy = date.getFullYear()
  const mm = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
  const dd = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
  return yy + format + mm + format + dd	// 返回格式化后的日期
}

// 时间戳转为 yyyy-MM-dd hh:mm
export function formatDate(date, fmt) {
  if (/(y+)/.test(fmt)) {
    fmt = fmt.replace(RegExp.$1, (date.getFullYear() + '').substr(4 - RegExp.$1.length))
  }
  const o = {
    'M+': date.getMonth() + 1,
    'd+': date.getDate(),
    'h+': date.getHours(),
    'm+': date.getMinutes(),
    's+': date.getSeconds()
  }
  for (const k in o) {
    if (new RegExp(`(${k})`).test(fmt)) {
      const str = o[k] + ''
      fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? str : padLeftZero(str))
    }
  }
  return fmt
}

function padLeftZero(str) {
  return ('00' + str).substr(str.length)
}
