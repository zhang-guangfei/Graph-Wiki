/* eslint-disable no-unused-vars */
const tablePage_vxe_30_100 = {
  total: 0,
  currentPage: 1,
  pageSize: 30,
  pageSizes: [30, 50, 100],
  layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
  perfect: true
}

const tablePage_vxe_100_1000 = {
  total: 0,
  currentPage: 1,
  pageSize: 30,
  pageSizes: [100, 500, 1000],
  layouts: ['Sizes', 'PrevJump', 'PrevPage', 'Number', 'NextPage', 'NextJump', 'FullJump', 'Total'],
  perfect: true
}

export function tablePageProd() {
  return tablePage_vxe_30_100
}
