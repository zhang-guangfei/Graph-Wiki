
export function codeFormatter(data, code) {
  if (data === null) {
    return code
  }
  if (code === null || code === undefined) {
    code = ''
  } else {
    code = code.toString()
  }
  for (const i in data) {
    const item = data[i]
    if (item.code === code) {
      return item.codeName
    }
  }
  return code
}

export function codeAndFormatter(data, code) {
  if (data === null) {
    return code
  }
  var arr = []
  for (const i in data) {
    const item = data[i]
    if ((Number(item.code) & Number(code)) === Number(item.code)) {
      arr.push(item.codeName)
    }
  }
  return arr.join()
}

export function dateTimeFormatter(dateTime) {
  if (dateTime === null) {
    return ''
  } else {
    return this.dayjs(dateTime).format('YYYY-MM-DD HH:mm:ss')
  }
}

export function downloadDataFile(parent, response, fileName) {
  if (response.type === 'application/json') {
    const reader = new FileReader()
    reader.readAsText(response, 'utf-8')
    reader.onload = function() {
      const resutl = JSON.parse(reader.result)
      parent.$message.error(resutl.message)
    }
  } else {
    const blob = new Blob([response], { type: response.type })
    const link = document.createElement('a')
    link.style.display = 'none'
    link.href = window.URL.createObjectURL(blob)
    link.download = fileName
    document.body.appendChild(link)
    link.click()
    window.URL.revokeObjectURL(link.href)
    document.body.removeChild(link)
  }
}
