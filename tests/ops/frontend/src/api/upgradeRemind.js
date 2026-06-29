/** 升级提醒API */
import request from '@/utils/request'

export function getUpgradeRemind() {
  return request({
    url: process.env.BASE_API + '/upgradeRemind/findStatus',
    method: 'get'
  })
}
