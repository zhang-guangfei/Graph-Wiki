import request from '@/utils/request'

// 查询所有请购单
export function findList(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/findList',
    method: 'post',
    data
  })
}

/**
 *采购单转定
 * @param {*修改供应商,运输方式，指定出库日的方法} data
 */
export function transOrder(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/transOrder',
    method: 'post',
    data
  })
}

// 按id获取采购单详情
export function findPurchaseByid(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/findByid',
    method: 'post',
    data
  })
}

// 根据采购单号，获取请购单详情
export function findRequestByOrder(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/findRequest',
    method: 'post',
    data
  })
}

// 更新交货期
export function updateDeliveryData(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/updateDelivery',
    method: 'post',
    data
  })
}

// // 强制完纳功能
// export function compelComplete(data) {
//   return request({
//     url: process.env.OPS_API_PO + '/purchase/compelComplete',
//     method: 'post',
//     data
//   })
// }

export function findGoods(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/findGoodsDetail',
    method: 'POST',
    data
  })
}

export function findGoodsByOrder(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/findGoodsDetailByOrder',
    method: 'POST',
    data
  })
}

export function apiRepo(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/apiRepo',
    method: 'POST',
    data
  })
}

export function wmSerch(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/rcvorder/assign/search/po',
    method: 'POST',
    // params: { poNo: data.poNo, poItem: data.poItem, poSplitNo: data.poSplitNo }
    data
  })
}

export function purchaseDel(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/purchase/delete',
    method: 'POST',
    data
  })
}
export function getDlvdate(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/dlvDate',
    method: 'POST',
    data
  })
}

export function downloadFile(filepath) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/downloadFile',
    method: 'get',
    params: { filepath },
    responseType: 'blob'
  })
}

// 采购完纳，根据采购查询 完纳实体信息
export function getFinishPo(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/getFinishPo',
    method: 'POST',
    data
  })
}

// 采购完纳，po后端接口
export function purchaseWn(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/finishPoHandel',
    method: 'POST',
    data
  })
}

// 采购完纳,订单校验接口
// 暂时先不用
export function orderWnVerify(data) {
  return request({
    url: process.env.OPS_API_WM + '/order/purchase/orderWnVerify',
    method: 'POST',
    data
  })
}

// 调用采购后端接口，显示当前型号可选择的运输方式
export function getTransIds(data) {
  return request({
    url: process.env.OPS_API_PO + '/purchase/getTransIds',
    method: 'POST',
    data
  })
}

// 采购完纳,订单校验接口
// 暂时先不用
export function deleteReasonOptions() {
  return [
    // 取消菜单
    {
      value: '客户',
      label: '客户',
      children: [
        {
          value: '项目取消',
          label: '项目取消'
        },
        {
          value: '选型错误',
          label: '选型错误'
        },
        {
          value: '对手替换',
          label: '对手替换'
        },
        {
          value: '客户货期提前',
          label: '客户货期提前'
        },
        {
          value: '制造货期延迟',
          label: '制造货期延迟'
        },
        {
          value: '客户订单错误',
          label: '客户订单错误'
        },
        {
          value: '欠款问题',
          label: '欠款问题'
        },
        {
          value: '其他',
          label: '其他'
        }
      ]
    },
    {
      value: '代理店',
      label: '代理店',
      children: [
        {
          value: '选型错误',
          label: '选型错误'
        },
        {
          value: '客户订单错误',
          label: '客户订单错误'
        },
        {
          value: '欠款问题',
          label: '欠款问题'
        },
        {
          value: '其他',
          label: '其他'
        }
      ]
    },
    {
      value: '营业所',
      label: '营业所',
      children: [
        {
          value: '选型错误',
          label: '选型错误'
        },
        {
          value: '客户订单错误',
          label: '客户订单错误'
        },
        {
          value: '其他',
          label: '其他'
        }
      ]
    },
    {
      value: '业务处理中心',
      label: '业务处理中心',
      children: [
        {
          value: 'SMC内部订单错误',
          label: 'SMC内部订单错误'
        },
        {
          value: '其他',
          label: '其他'
        }
      ]
    },
    {
      value: '中国制造通知',
      label: '中国制造通知',
      children: [
        {
          value: '制造货期延迟',
          label: '制造货期延迟'
        },
        {
          value: '错误型号',
          label: '错误型号'
        },
        {
          value: '收敛品',
          label: '收敛品'
        },
        {
          value: '贩卖限制品',
          label: '贩卖限制品'
        },
        {
          value: '产地问题',
          label: '产地问题'
        },
        {
          value: '其他',
          label: '其他'
        }
      ]
    },
    {
      value: '日本通知',
      label: '日本通知',
      children: [
        {
          value: '制造货期延迟',
          label: '制造货期延迟'
        },
        {
          value: '组装问题',
          label: '组装问题'
        },
        {
          value: '错误型号',
          label: '错误型号'
        },
        {
          value: '收敛品',
          label: '收敛品'
        },
        {
          value: '贩卖限制品',
          label: '贩卖限制品'
        },
        {
          value: '申请特注',
          label: '申请特注'
        },
        {
          value: 'SHIKOMI不足',
          label: 'SHIKOMI不足'
        },
        {
          value: '产地问题',
          label: '产地问题'
        },
        {
          value: '其他',
          label: '其他'
        }
      ]
    },
    {
      value: '业务课',
      label: '业务课',
      children: [
        {
          value: '错误型号',
          label: '错误型号'
        },
        {
          value: '收敛品',
          label: '收敛品'
        },
        {
          value: '贩卖限制品',
          label: '贩卖限制品'
        },
        {
          value: '申请特注',
          label: '申请特注'
        },
        {
          value: '海关不可进口',
          label: '海关不可进口'
        },
        {
          value: 'SHIKOMI不足',
          label: 'SHIKOMI不足'
        },
        {
          value: '产地问题',
          label: '产地问题'
        },
        {
          value: '其他',
          label: '其他'
        }
      ]
    }
  ]
}

