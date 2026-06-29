export function numberToCurrencyNo(value) {
  if (!value) return 0
  // 获取整数部分
  const intPart = Math.trunc(value)
  // 整数部分处理，增加,
  const intPartFormat = intPart.toString().replace(/(\d)(?=(?:\d{3})+$)/g, '$1,')
  // 预定义小数部分
  let floatPart = ''
  // 将数值截取为小数部分和整数部分
  const valueArray = value.toString().split('.')
  if (valueArray.length === 2) { // 有小数部分
    floatPart = valueArray[1].toString() // 取得小数部分
    return intPartFormat + '.' + floatPart
  }
  return intPartFormat + floatPart
}
export function numberToFixedTwo(value) {
  // if (!value || value === '') return 0
  // return Number(value).toFixed(2)
  // const _self = this
  return numberToFixed(value, 2)
}
export function numberToFixedFour(value) {
  // if (!value || value === '') return 0
  // return Number(value).toFixed(4)
  // const _self = this
  return numberToFixed(value, 4)
}
// 解决js运算精度丢失问题，乘法
export function multiplyMore() {
  const len = arguments.length
  let rtnval = 0
  if (len === 1) {
    rtnval = arguments[0]
  } else {
    for (let i = 0; i < len; i++) {
      rtnval = multiply(rtnval, arguments[i])
    }
  }
  return rtnval
}
export function multiply(arg1, arg2) {
  let m = 0
  const s1 = arg1.toString()
  const s2 = arg2.toString()
  try {
    if (s1.split('.').length >= 2) {
      m += s1.split('.')[1].length
    }
  } catch (e) { console.log(e) }
  try {
    if (s2.split('.').length >= 2) {
      m += s2.split('.')[1].length
    }
  } catch (e) { console.log(e) }
  return Number(s1.replace('.', '')) * Number(s2.replace('.', '')) / Math.pow(10, m)
}

// 解决js运算精度丢失问题  除法
export function divide(arg1, arg2) {
  const s1 = arg1.toString()
  const s2 = arg2.toString()
  let t1 = 0
  let t2 = 0
  let r1 = 0
  let r2 = 0
  try {
    if (s1.split('.').length >= 2) {
      t1 = s1.split('.')[1].length
    }
  } catch (e) { t1 = 0 }
  try {
    if (s2.split('.').length >= 2) {
      t2 = s2.split('.')[1].length
    }
  } catch (e) { t2 = 0 }
  r1 = Number(s1.replace('.', ''))
  r2 = Number(s2.replace('.', ''))
  return (r1 / r2) * Math.pow(10, t2 - t1)
}
// 解决js运算精度丢失问题  加法
export function addMore() {
  const len = arguments.length
  let rtnval = 0
  if (len === 1) {
    rtnval = arguments[0]
  } else {
    for (let i = 0; i < len; i++) {
      rtnval = add(rtnval, arguments[i])
    }
  }
  return rtnval
}
export function add(arg1, arg2) {
  const s1 = arg1.toString()
  const s2 = arg2.toString()
  let r1 = 0
  let r2 = 0
  let m = 0
  try {
    if (s1.split('.').length >= 2) {
      r1 = s1.split('.')[1].length
    }
  } catch (e) { r1 = 0 }
  try {
    if (s2.split('.').length >= 2) {
      r2 = s2.split('.')[1].length
    }
  } catch (e) { r2 = 0 }
  m = Math.pow(10, Math.max(r1, r2))
  return divide((multiply(arg1, m) + multiply(arg2, m)), m)
}
// 解决js运算精度丢失问题  减法
export function subtract(arg1, arg2) {
  const s1 = arg1.toString()
  const s2 = arg2.toString()
  let r1 = 0
  let r2 = 0
  let m = 0
  // let n = 0
  try {
    if (s1.split('.').length >= 2) {
      r1 = s1.split('.')[1].length
    }
  } catch (e) { r1 = 0 }
  try {
    if (s2.split('.').length >= 2) {
      r2 = s2.split('.')[1].length
    }
  } catch (e) { r2 = 0 }
  m = Math.pow(10, Math.max(r1, r2))
  // 动态控制精度长度
  // n = (r1 >= r2) ? r1 : r2
  return divide((multiply(arg1, m) - multiply(arg2, m)), m) // .toFixed(n);
}
// 解决js四舍五入精度丢失问题
export function numberToFixed(number, n) {
  if (n > 20 || n < 0) {
    throw new RangeError('toFixed() digits argument must be between 0 and 20')
  }
  if (!number || number === '') return 0
  if (isNaN(number) || number >= Math.pow(10, 21)) {
    return number.toString()
  }
  if (typeof (n) === 'undefined' || n === 0) {
    return (Math.round(number)).toString()
  }
  let result = number.toString()
  const arr = result.split('.')
  // 整数的情况
  if (arr.length < 2) {
    result += '.'
    for (let i = 0; i < n; i += 1) {
      result += '0'
    }
    return result
  }
  const integer = arr[0]
  const decimal = arr[1]
  if (decimal.length === n) {
    return result
  }
  if (decimal.length < n) {
    for (let i = 0; i < n - decimal.length; i += 1) {
      result += '0'
    }
    return result
  }
  result = integer + '.' + decimal.substr(0, n)
  const last = decimal.substr(n, 1)
  // 四舍五入，转换为整数再处理，避免浮点数精度的损失
  if (parseInt(last, 10) >= 5) {
    const x = Math.pow(10, n)
    result = (Math.round((parseFloat(result) * x)) + 1) / x
    result = result.toFixed(n)
  }
  return result
}
