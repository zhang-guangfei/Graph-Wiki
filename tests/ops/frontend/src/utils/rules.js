// QQ号验证
var QQV = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('输入QQ号'))
  } else if (/^[1-9][0-9]{4,10}$/.test(value)) {
    callback()
  } else {
    callback(new Error('输入正确的QQ号'))
  }
}

// 类似金钱,首位不为0,最多2位小数
export function checkNumPot2(rule, value, callback) {
  const reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/
  if (!value) {
    return callback(new Error('请填写数字'))
  } else if (!reg.test(value)) {
    return callback(new Error('请填写数字,最多2位小数'))
  } else {
    callback()
  }
}

// 身份证
export function checkIdNum(rule, value, callback) {
  const reg = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/
  if (!value) {
    return callback(new Error('证件号码不能为空'))
  } else if (!reg.test(value)) {
    return callback(new Error('证件号码不正确'))
  } else {
    callback()
  }
}

// 整数
export function checkMinute(rule, value, callback) {
  const reg = /^[0-9]*[1-9][0-9]*$/
  if (!value) {
    return callback(new Error('请填写面谈时间'))
  } else if (!reg.test(value)) {
    return callback(new Error('请输入整数'))
  } else if (value > 960) {
    return callback(new Error('请合理填写数据，不要超过960分钟'))
  } else {
    callback()
  }
}

// 所内工作时间
export function checkWorkMinute(rule, value, callback) {
  const reg = /^[0-9]*[1-9][0-9]*$/
  if (!value) {
    return callback(new Error('请填写所内工作时间'))
  } else if (!reg.test(value)) {
    return callback(new Error('请输入整数'))
  } else if (value > 960) {
    return callback(new Error('请合理填写数据，不要超过960分钟'))
  } else {
    callback()
  }
}

// 公里数校验
export function checkMileAge(rule, value, callback) {
  const reg = /^[0-9]*[1-9][0-9]*$/
  if (!value) {
    return callback(new Error('请填写公里数'))
  } else if (!reg.test(value)) {
    return callback(new Error('请输入整数'))
  } else {
    callback()
  }
}

// 起始公里数校验
export function checkStartMileAge(rule, value, callback) {
  const reg = /^[0-9]*[1-9][0-9]*$/
  if (!value) {
    return callback(new Error('请填写起始公里数'))
  } else if (!reg.test(value)) {
    return callback(new Error('请输入整数'))
  } else if (!reg.test(value)) {
    return callback(new Error('请输入整数'))
  } else {
    callback()
  }
}

// 公里数可空类型校验
export function checkMileAgeCanNull(rule, value, callback) {
  const reg = /^[0-9]*[0-9][0-9]*$/
  if (value === '' || value === null || value === undefined) {
    return true
  } else if (!reg.test(value)) {
    return callback(new Error('请输入整数作为公里数'))
  } else {
    callback()
  }
}

// 客户名称校验
export function checkName(rule, value, callback) {
  if (value === '' || value === null) {
    callback(new Error('请选择或填写客户名称'))
  } else {
    callback()
  }
}

// 型号校验
export function modelNoCheck(value) {
  const p = new RegExp('[.,\\-_/+]')
  const p1 = new RegExp('[A-Z0-9().,\\-_/+*#ÜÄÖ]')
  const p2 = new RegExp('[A-Z0-9ÜÄÖ*#()]')
  var pre = false
  for (var i = 0; i < value.length; i++) {
    var s = value.substring(i, i + 1)
    if (i === 0) {
      if (!p2.test(s)) {
        return false
      }
      pre = p.test(s)
    } else if (i === value.length - 1) {
      if ((pre && p.test(s)) || !p2.test(s)) {
        return false
      }
    } else {
      if ((pre && p.test(s)) || !p1.test(s)) {
        return false
      } else {
        pre = p.test(s)
      }
    }
  }
  return true
}

export default {
  QQ: [{ required: true, validator: QQV, trigger: 'blur' }],
  phone: [{ required: true, pattern: /^1[34578]\d{9}$/, message: '目前只支持中国大陆的手机号码', trigger: 'blur' }],
  numPot2: [{ required: true, validator: checkNumPot2, trigger: 'blur' }],
  minute: [{ required: true, validator: checkMinute, trigger: ['blur', 'change'] }],
  industry: [{ required: true, message: '请选择行业', trigger: 'change' }],
  mileAge: [{ required: true, validator: checkMileAge, trigger: ['blur', 'change'] }],
  mileAgeCanNull: [{ validator: checkMileAgeCanNull, trigger: ['blur', 'change'] }],
  edp: [{ required: true, message: '请选择edp', trigger: 'change' }],
  customerCode: [{ required: true, message: '请填写客户编码', trigger: ['blur', 'change'] }],
  customerName: [{ required: true, message: '请填写客户信息', trigger: ['blur', 'change'] }],
  workItem: [{ required: true, message: '请填写非面谈工作事项', trigger: ['blur'] }],
  // startTimeAndEndTime: [{ required: true, message: '请选择时间段', trigger: ['blur', 'change'] }],
  visit: [{ required: true, message: '请选择拜访', trigger: ['blur', 'change'] }],
  goal: [{ required: true, message: '请选择目的', trigger: ['blur', 'change'] }],
  nameCheck: [{ required: true, validator: checkName, trigger: ['blur', 'change'] }],
  workMinute: [{ required: true, validator: checkWorkMinute, trigger: ['blur', 'change'] }],
  jobContent: [{ required: true, message: '请选择所内工作', trigger: ['blur'] }]
  // timenotnull: [{ required: true, message: '请选择时间', trigger: ['blur', 'change'] }]
  // customerDept: [{ message: '请选择拜访部门', trigger: ['blur', 'change'] }]
  // startMileAge: [{ required: true, validator: checkStartMileAge, trigger: ['blur', 'change'] }]
  // minute: [{ required: true, message: '请填写面谈时间', trigger: ['blur', 'change'] }, { type: 'number', message: '面谈时间必须为数字值' }]
}
