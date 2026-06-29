// 将字符串格式得日期转换为Date类型得日期，第二个参数为日期分格符号，不传则默认为 '-'
// e.g. --> 应用页面 import { formatDate } from '@/utils/dateHandle'   import { stringToDate } from '@/utils/dateHandle'
export function stringToDate(dateStr, separator) {
  const str = dateStr.replace(/-/g, '/')
  return new Date(str)
}

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
      fmt = fmt.replace(RegExp.$1, RegExp.$1.length === 1 ? str : padLeftZero(str))
    }
  }
  return fmt
}

function padLeftZero(str) {
  return ('00' + str).substr(str.length)
}

/**
 * 日期相减
 * @param {Date} date1 日期1
 * @param {Date} date2 日期2
 */
export function getNumberOfDays(date1, date2) { // 获得天数
  // date1：开始日期，date2结束日期
  var a1 = Date.parse(date1)
  var a2 = Date.parse(date2)
  var day = parseInt((a2 - a1) / (1000 * 60 * 60 * 24)) + 1 // 核心：时间戳相减，然后除以天数
  return day
}

/**
 * 日期加天数
 * @param {*} date 日期
 * @param {*} day 天数
 */
export function addDay(date, day) {
  date.setDate(date.getDate() + day)
  return date
}

/**
 * 日期加天数
 * @param {*} date 日期
 * @param {*} m 月数
 */
export function addMonth(date, m) {
  date.setMonth(date.getMonth() + m)
  return date
}

/**
 * 获取去年年份
 * @param {*} resType 返回年份类型 String int
 * @param {*} lastNum 前几年 默认去年 有值则为前几年的数字
 */
export function getLastYear(resType, lastNum) {
  const nowYear = Number(new Date().getFullYear())
  if (lastNum) {
    if (resType && resType === 'String') {
      return (nowYear - Number(lastNum)).toString()
    }
    return nowYear - Number(lastNum)
  }
  if (resType && resType === 'String') {
    return (nowYear - Number(1)).toString()
  }
  return nowYear - Number(1)
}

/**
 * 给指定日期加天数
 * @param {*} date
 * @param {*} days
 * @returns
 */
export function getAddDate(dateInput, days) {
  if (days === undefined || days === '') {
    days = 1
  }

  const date = new Date(dateInput)
  date.setDate(date.getDate() + days)
  var month = date.getMonth() + 1
  var day = date.getDate()
  var mm = "'" + month + "'"
  var dd = "'" + day + "'"

  // 单位数前面加0
  if (mm.length === 3) {
    month = '0' + month
  }
  if (dd.length === 3) {
    day = '0' + day
  }

  const time = date.getFullYear() + '-' + month + '-' + day
  return time
}

