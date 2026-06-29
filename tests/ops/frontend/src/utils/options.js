export function getpickerOptions1() {
  return {
    disabledDate(time) {
      return time.getTime() > Date.now()
    },
    shortcuts: [{
      text: '今天',
      onClick(picker) {
        picker.$emit('pick', new Date())
      }
    }, {
      text: '昨天',
      onClick(picker) {
        const date = new Date()
        date.setTime(date.getTime() - 3600 * 1000 * 24)
        picker.$emit('pick', date)
      }
    }, {
      text: '一周前',
      onClick(picker) {
        const date = new Date()
        date.setTime(date.getTime() - 3600 * 1000 * 24 * 7)
        picker.$emit('pick', date)
      }
    }]
  }
}
// 最近三个月、一周、一天时间控件
export function getPickerOptionsDate() {
  return {
    shortcuts: [
      {
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
}

export function getOptionsDeptInfo() {
  return [{
    value: '技术部',
    label: '技术部'
  }, {
    value: '设计部',
    label: '设计部'
  }, {
    value: '研发部',
    label: '研发部'
  }, {
    value: '项目部',
    label: '项目部'
  }, {
    value: '工程部',
    label: '工程部'
  }, {
    value: '设备部',
    label: '设备部'
  }, {
    value: '制造部',
    label: '制造部'
  }, {
    value: '自动化部',
    label: '自动化部'
  }, {
    value: '电气部',
    label: '电气部'
  }, {
    value: '设备维保',
    label: '设备维保'
  }, {
    value: '采购部',
    label: '采购部'
  }, {
    value: '仓库',
    label: '仓库'
  }, {
    value: '财务部',
    label: '财务部'
  }, {
    value: '其他',
    label: '其他'
  }]
}

/**
 * sql筛选条件
 */
export function getOptionsSqlTypeOption() {
  return [
    { key: 1, label: '等于', value: 'equal' },
    { key: 2, label: '不等于', value: 'notEqual' },
    { key: 3, label: '包含', value: 'contain' },
    { key: 4, label: '不包含', value: 'notContain' },
    { key: 5, label: '大于', value: 'greater' },
    { key: 6, label: '小于', value: 'less' }
  ]
}

export function getTableColumnPrice() {
  return [
    { fixed: 'left', field: 'modelNo', title: '型号', editRender: { name: 'input' }},
    { field: 'series', title: '系列' },
    { field: 'ePrice', title: 'E价格' },
    { field: 'orgCountry', title: '原产国' },
    { field: 'prodCountry', title: '供应国' },
    { field: 'updatedDate', title: '更新日期', width: '240' },
    { field: 'updatedUser', title: '更新用户' },
    { field: 'modelInCHN', title: '中文名称', width: '200' },
    { field: 'classifyLarge', title: '大分类' },
    { field: 'classifyMiddle', title: '中分类' },
    { field: 'classifySmall', title: '小分类' },
    { field: 'weight', title: '毛重' },
    { field: 'netWeight', title: '净重' },
    { field: 'length', title: '长' },
    { field: 'width', title: '宽' },
    { field: 'height', title: '高' },
    { field: 'ePriceJp', title: '日元E价', width: '80' },
    { field: 'ePricePra', title: 'E价格系数' },
    { field: 'ePriceRMB', title: '人民币E价' },
    { field: 'sPriceRMB', title: '人民币标准价' },
    { field: 'fobPrice', title: '进口fob价' },
    { field: 'priceLowest', title: '最低售价' },
    { field: 'saleType', title: '销售类型' },
    { field: 'lotPrice', title: '多段价格' },
    { field: 'qtyInterval', title: '数量区间', width: '160' },
    { field: 'lotEPriceRMB', title: '多段人民币E价' },
    { field: 'lotSPriceRMB', title: '多段人民币标准价' },
    { field: 'lotFobPrice', title: '多段fob价' },
    { field: 'email', title: '联络Email' },
    { field: 'department', title: '联络部门' },
    { field: 'splitInfo', title: '拆分信息' }
  ]
}
