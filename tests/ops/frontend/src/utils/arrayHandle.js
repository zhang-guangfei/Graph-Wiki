/**
 * @Description 将[ {a: 'x', b: 'value1' }, {a: 'x', b: 'value2'}]
 *              转换为 [ {a: 'x', b: 'value1', data: [{a: 'x', b: 'value1' }, {a: 'x', b: 'value2'}] } ]
 *              类型数据
 *              如有单独需求请自行替换
 * @Author sunrui
 * @param {数组}
 * @return {对象}
 * @Date  2019-05-30 14:16:28
 */
export function getTransOption(data) {
  var arr = data
  var hash = []
  for (var i = 0; i < arr.length; i++) {
    for (var j = i + 1; j < arr.length; j++) {
      if (arr[i].mCode === arr[j].mCode) {
        ++i
      }
    }
    hash.push(Object.assign({}, arr[i]))
  }
  const mcodeResult = Object.assign([], hash)
  for (var a = 0; a < hash.length; a++) {
    hash[a].data = []
    for (var b = 0; b < arr.length; b++) {
      if (arr[b].mCode === hash[a].mCode) {
        hash[a].data.push(arr[b])
      }
    }
  }
  const mdescriptionResult = Object.assign([], hash)

  return { mcodeResult: mcodeResult, mdescriptionResult: mdescriptionResult }
}
