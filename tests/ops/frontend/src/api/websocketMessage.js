import request from '@/utils/request'

export function findMessageRecords(params) {
  return request({
    url: process.env.WEBSOCKET_API + '/findMessagesByUser',
    method: 'get',
    params: params
  })
}

export function readMessage(toUser, fromUser) {
  return request({
    url: process.env.WEBSOCKET_API + '/readMessage',
    method: 'post',
    params: { toUser, fromUser }
  })
}

export function getwebSocketUri() {
  return process.env.WEBSOCKET_API + '/api'
}

export function findUserRelationByUserId(userId) {
  return request({
    url: process.env.WEBSOCKET_API + '/findUserRelationByUserId',
    method: 'get',
    params: { userId }
  })
}

export function addUserRelation(userRelation) {
  return request({
    url: process.env.WEBSOCKET_API + '/addUserRelation',
    method: 'post',
    data: userRelation
  })
}

