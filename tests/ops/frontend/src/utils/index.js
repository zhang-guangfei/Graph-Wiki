/**
 * Created by jiachenpan on 16/11/18.
 */

export function parseTime(time, cFormat) {
  if (arguments.length === 0) {
    return null
  }
  const format = cFormat || '{y}-{m}-{d} {h}:{i}:{s}'
  let date
  if (typeof time === 'object') {
    date = time
  } else {
    if (('' + time).length === 10) time = parseInt(time) * 1000
    date = new Date(time)
  }
  const formatObj = {
    y: date.getFullYear(),
    m: date.getMonth() + 1,
    d: date.getDate(),
    h: date.getHours(),
    i: date.getMinutes(),
    s: date.getSeconds(),
    a: date.getDay()
  }
  const time_str = format.replace(/{(y|m|d|h|i|s|a)+}/g, (result, key) => {
    let value = formatObj[key]
    // Note: getDay() returns 0 on Sunday
    if (key === 'a') {
      return ['日', '一', '二', '三', '四', '五', '六'][value]
    }
    if (result.length > 0 && value < 10) {
      value = '0' + value
    }
    return value || 0
  })
  return time_str
}

export function formatTime(time, option) {
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 30) {
    return '刚刚'
  } else if (diff < 3600) {
    // less 1 hour
    return Math.ceil(diff / 60) + '分钟前'
  } else if (diff < 3600 * 24) {
    return Math.ceil(diff / 3600) + '小时前'
  } else if (diff < 3600 * 24 * 2) {
    return '1天前'
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getMonth() +
      1 +
      '月' +
      d.getDate() +
      '日' +
      d.getHours() +
      '时' +
      d.getMinutes() +
      '分'
    )
  }
}

export function formatTimeM(time, option) {
  if (time === undefined || time === null) {
    return ''
  }
  const d = new Date(time)
  const now = Date.now()

  const diff = (now - d) / 1000

  if (diff < 3600 * 24) {
    return parseTime(time, '{h}:{i}')
  }
  if (option) {
    return parseTime(time, option)
  } else {
    return (
      d.getFullYear() === null ? null : d.getFullYear().toString().substr(2, 2) +
      '/' +
      d.getMonth() +
      1 +
      '/' +
      d.getDate()
    )
  }
}

export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

export function param2Obj(url) {
  const search = url.split('?')[1]
  if (!search) {
    return {}
  }
  return JSON.parse(
    '{"' +
    decodeURIComponent(search)
      .replace(/"/g, '\\"')
      .replace(/&/g, '","')
      .replace(/=/g, '":"') +
    '"}'
  )
}

/**
 *将数组转换为树形结构
 *
 * @export
 * @param {*} sNodes
 * @returns
 */
export function arrayToTree(sNodes) {
  const idKey = 'id'
  const parentKey = 'pid'
  const childKey = 'children'
  if (!idKey || idKey === '' || !sNodes) return []

  if (Array.isArray(sNodes)) {
    const r = []
    const tmpMap = {}
    sNodes.forEach((item, index) => {
      tmpMap[sNodes[index][idKey]] = sNodes[index]
      tmpMap[sNodes[index][idKey]].label = sNodes[index].name
      tmpMap[sNodes[index][idKey]].value = sNodes[index].id
    })
    sNodes.forEach((item, index) => {
      if (tmpMap[sNodes[index][parentKey]] && sNodes[index][idKey] !== sNodes[index][parentKey]) {
        if (!tmpMap[sNodes[index][parentKey]][childKey]) {
          tmpMap[sNodes[index][parentKey]][childKey] = []
        }
        tmpMap[sNodes[index][parentKey]][childKey].push(sNodes[index])
      } else {
        r.push(sNodes[index])
      }
    })
    return r
  } else {
    return [sNodes]
  }
}
/**
 *获取树形结构所有子节点
 *
 * @export
 * @param {*} sNodes
 * @returns
 */
export function getChilds(id, childs) {
  var newArr = []
  for (var i = 0; i < childs.length; i++) {
    if (childs[i].pid === id) {
      newArr.push(childs[i])
      getChilds(childs[i].id, childs[i].children)
    } else {
      continue
    }
  }
  return newArr
}

/**
 *通过id获取子节点
 * @export
 * @param id  选中的部门编码
 * @param bool true 获取所有子节点，false获取直接子节点（均包含所选节点code）
 * @param datas  所有部门数据
 * @returns
 */
export function getChildsByK3Code(id, bool, datas) {
  var deptCodes = []
  for (var i = 0; i < datas.length; i++) {
    if (!bool) {
      var k3CodeArray = datas[i].k3Code.split('!')
      if (k3CodeArray[k3CodeArray.length - 2] === id) {
        deptCodes.push(datas[i].code)
      }
      if (k3CodeArray[k3CodeArray.length - 1] === id) {
        deptCodes.push(datas[i].code)
      }
    } else {
      if (datas[i].k3Code.indexOf(id) !== -1) {
        deptCodes.push(datas[i].code)
      }
    }
  }
  return deptCodes
}
/**
 *将数组转换为树形结构
 *
 * @export
 * @param {*} sNodes
 * @returns
 */
export function arrayToTreeLabel(sNodes, id, parentId, name) {
  const idKey = id
  const parentKey = parentId
  const childKey = 'children'
  if (!idKey || idKey === '' || !sNodes) return []

  if (Array.isArray(sNodes)) {
    const r = []
    const tmpMap = {}
    sNodes.forEach((item, index) => {
      tmpMap[sNodes[index][idKey]] = sNodes[index]
      tmpMap[sNodes[index][idKey]]['label'] = sNodes[index][name]
      tmpMap[sNodes[index][idKey]]['value'] = sNodes[index][id]
    })
    sNodes.forEach((item, index) => {
      if (tmpMap[sNodes[index][parentKey]] && sNodes[index][idKey] !== sNodes[index][parentKey]) {
        if (!tmpMap[sNodes[index][parentKey]][childKey]) {
          tmpMap[sNodes[index][parentKey]][childKey] = []
        }
        tmpMap[sNodes[index][parentKey]][childKey].push(sNodes[index])
      } else {
        r.push(sNodes[index])
      }
    })
    return r
  } else {
    return [sNodes]
  }
}

/**
 *将树形结构转换为数组
 *
 * @export
 * @param {*} nodes
 * @returns
 */
export function transformToArrayFormat(nodes) {
  if (!nodes) return []
  const childKey = 'children'
  let r = []
  if (Array.isArray(nodes)) {
    nodes.forEach((item, index) => {
      r.push(nodes[index])
      if (nodes[index][childKey]) {
        r = r.concat(transformToArrayFormat(nodes[index][childKey]))
      }
    })
  } else {
    r.push(nodes)
    if (nodes[childKey]) {
      r = r.concat(transformToArrayFormat(nodes[childKey]))
    }
  }
  return r
}

/**
 * Check if an element has a class
 * @param {HTMLElement} elm
 * @param {string} cls
 * @returns {boolean}
 */
export function hasClass(ele, cls) {
  return !!ele.className.match(new RegExp('(\\s|^)' + cls + '(\\s|$)'))
}

/**
 * Add class to element
 * @param {HTMLElement} elm
 * @param {string} cls
 */
export function addClass(ele, cls) {
  if (!hasClass(ele, cls)) ele.className += ' ' + cls
}

/**
 * Remove class from element
 * @param {HTMLElement} elm
 * @param {string} cls
 */
export function removeClass(ele, cls) {
  if (hasClass(ele, cls)) {
    const reg = new RegExp('(\\s|^)' + cls + '(\\s|$)')
    ele.className = ele.className.replace(reg, ' ')
  }
}
/**
 * 判断值是否为空
 * @param {*} val
 * @returns true false
 */
export function varIsEmpty(val) {
  return (val === '' || val === null)
}

/**
 * 判断值是否是不可使用（空或者未定义）
 * @param {*} val
 * @returns true false
 */
export function varIsUnable(val) {
  return (val === '' || val === null || val === undefined)
}

/**
 * 判断值不是不可使用（不是空不是未定义)
 * @param {*} val
 * @returns true false
 */
export function varIsNotUnable(val) {
  return (val !== '' && val !== null && val !== undefined)
}

/**
 * 判断数组是不为空得
 * @param {*} array
 * @returns true false
 */
export function arrayIsNotEmpty(array) {
  return Array.isArray(array) && (array.length > 0)
}
/**
 * 判断数组是为空
 * @param {*} array
 * @returns true false
 */
export function arrayIsEmpty(array) {
  return Array.isArray(array) && (array.length === 0)
}
/**
 * 去除 undefined 变为null
 * @param {*} val
 * @returns
 */
export function overcomeUndef(val) {
  return val || null
}

const $math = require('mathjs')
export const math = {
  add() {
    return comp('add', arguments)
  },
  subtract() {
    return comp('subtract', arguments)
  },
  multiply() {
    return comp('multiply', arguments)
  },
  divide() {
    return comp('divide', arguments)
  }
}

function comp(_func, args) {
  let t = $math.chain($math.bignumber(args[0]))
  for (let i = 1; i < args.length; i++) {
    t = t[_func]($math.bignumber(args[i]))
  }
  // 防止超过6位使用科学计数法
  return parseFloat(t.done())
}

/**
 * 保留两位有效数字
 * @param {*} value
 * @returns
 */
export function returnFloat(value) {
  value = Math.round(parseFloat(value) * 100) / 100
  var s = value.toString().split('.')
  if (s.length === 1) {
    value = value.toString() + '.00'
    return value
  }
  if (s.length > 1) {
    if (s[1].length < 2) {
      value = value.toString() + '0'
    }
    return value
  }
}

// 比较两个相似结构的对象是否值相等
export function isObjectEqual(left, right) {
  if (left instanceof Object) {
    if (left.length !== right.length) {
      return false
    }
    for (const key in left) {
      if (left[key] instanceof Object || left[key] instanceof Array) {
        if (!isObjectEqual(left[key], right[key])) {
          return false
        }
      } else {
        if (left[key] !== right[key]) {
          return false
        }
      }
    }
  }
  if (left instanceof Array) {
    if (left.length !== right.length) {
      return false
    }
    for (let i = 0, len = left.length; i < len; i++) {
      if (!isObjectEqual(left[i], right[i])) {
        return false
      }
    }
  }
  return true
}
