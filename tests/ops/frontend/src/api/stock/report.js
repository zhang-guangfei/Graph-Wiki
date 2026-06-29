import request from '@/utils/request'
const url = process.env.OPS_REPORT_API
// const url = 'http://localhost:8104'

export function onSendAgentOrderFreqReport(agentNo) {
  return request({
    url: url + '/report/order/onSendAgentOrderFreqReport',
    method: 'get',
    params: { agentNo }
  })
}
