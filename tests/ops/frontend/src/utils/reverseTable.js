export function reverseTable(columns, list) {
  const buildData = columns.map(column => {
    const item = { col0: column.title }
    list.forEach((row, index) => {
      item[`col${index + 1}`] = row[column.field]
    })
    return item
  })
  const buildColumns = [{
    field: 'col0',
    fixed: 'left',
    width: 170
  }]
  list.forEach((item, index) => {
    buildColumns.push({
      field: `col${index + 1}`,
      minWidth: 120
    })
  })
  return { columns: buildColumns, data: buildData }
}
