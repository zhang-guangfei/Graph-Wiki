import VXETable from 'vxe-table'
import moment from 'moment/moment'
// 自定义全局的格式化处理函数
VXETable.formats.mixin({

  formatDate({ cellValue }) {
    return cellValue ? moment(cellValue).format('YYYY-MM-DD') : ''
  },

  formatDateNull({ cellValue }) {
    return cellValue ? moment(cellValue).format('YYYY-MM-DD') : '-'
  },

  formatDateTime({ cellValue }) {
    return cellValue ? moment(cellValue).format('YYYY-MM-DD HH:mm:ss') : ''
  },

  // 输入一个单元格和一个map,将cellValue作为key，返回map中的value
  formatDict({ cellValue }, map) {
    return map.get(cellValue) || cellValue
  },

  // 格式化完整单号 orderNo, itemNo, splitNo
  formatOrderFullNo({ cellValue, row }) {
    if (row.orderId === null) {
      return ''
    }
    var str = row.orderId + '-' + row.orderItem
    if (row.splitNo !== 0) {
      str = str + '-' + row.splitNo
      if (row.pcoItem !== 0) {
        str = str + '-' + row.pcoItem
      }
    }
    return str
  },

  formatStatus({ cellValue }, dict) {
    return dict.get(cellValue) || cellValue
  },
  // 将json数据格式化为描述信息【key:value,key:value,...】
  // 将仓库号
  formatStatusInfo({ cellValue }, dicts) {
    try {
      const jsonObject = JSON.parse(cellValue)
      // 创建一个空字符串来存储格式化后的结果
      let formattedString = ''
      // 遍历对象的每一个属性
      for (const key in jsonObject) {
        // 检查对象自身是否有此属性，防止来自原型链的属性干扰
        if (jsonObject.hasOwnProperty(key)) {
          // 将当前键值对添加到字符串中，用逗号分隔
          let value = jsonObject[key]
          if (dicts.warehouse.has(value)) {
            value = dicts.warehouse.get(value)
          }
          formattedString += `<span style="font-weight: bold">${key}</span>:${value},`
        }
      }
      // 移除最后一个逗号
      formattedString = formattedString.slice(0, -1)
      // 输出格式化后的字符串
      return '<div>【' + formattedString + '】</div>'
    } catch (e) {
      return cellValue
    }
  }

})

