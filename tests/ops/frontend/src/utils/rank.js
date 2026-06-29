export function getRankByKsCode(code, isFuZeRen) {
  if (!code) {
    return null
  }
  if (isFuZeRen === 1) {
    return code.split('!').length * 2 - 1
  }
  return code.split('!').length * 2
}
