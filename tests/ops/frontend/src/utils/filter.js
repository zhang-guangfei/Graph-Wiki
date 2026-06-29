export function thousandFilter(val) {
  return (val).toFixed(2).replace(/(\d)(?=(\d{3})+\.)/g, '$1,')
}
