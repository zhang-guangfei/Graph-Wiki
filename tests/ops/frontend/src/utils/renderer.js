import VXETable from 'vxe-table'
import XEUtils from 'xe-utils'
import EditDownTable from '@/components/VxeTableRender/EditDownTable.vue'
import FilterInput from '@/components/VxeTableRender/FilterInput.vue'
VXETable.renderer.add('EditDownTable', {
  // 可编辑激活模板
  renderEdit(h, renderOpts, params) {
    return [
      <EditDownTable params={ params }></EditDownTable>
    ]
  },
  // 可编辑显示模板
  renderCell(h, renderOpts, { row, column }) {
    return [
      <span>{ row[column.property] }</span>
    ]
  }
})

VXETable.renderer.add('FilterInput', {
  // 筛选模板
  renderFilter(h, renderOpts, params) {
    return [
      <FilterInput params={ params }></FilterInput>
    ]
  },
  // 重置数据方法
  filterResetMethod({ options }) {
    options.forEach((option) => {
      option.data = ''
    })
  },
  // 筛选方法
  filterMethod({ option, row, column }) {
    const { data } = option
    const cellValue = XEUtils.get(row, column.property)
    if (cellValue) {
      return cellValue.indexOf(data) > -1
    }
    return false
  }
})

