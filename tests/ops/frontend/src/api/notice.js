/** 公告管理API */
import request from '@/utils/request'

export function queryNotices(condition, page) {
  return request({
    url: process.env.BASE_API + '/coreNotice/queryByReadPower',
    method: 'post',
    data: condition,
    params: page
  })
}

export function getNotices(condition, page) {
  return request({
    url: process.env.BASE_API + '/coreNotice/query',
    method: 'post',
    data: condition,
    params: page
  })
}

/* 查询最新的前五条公告 */
export function getTop5NoticeList() {
  return request({
    url: process.env.BASE_API + '/coreNotice/findTop5Notice',
    method: 'post'
  })
}

/* 查询最新的公告 */
export function getLatestNotice() {
  return request({
    url: process.env.BASE_API + '/coreNotice/findLatestNotice',
    method: 'post'
  })
}

/* 查询最新公告对应的附件 */
export function getLatestAttachedfileNotice() {
  return request({
    url: process.env.BASE_API + '/coreNotice/findLatestAttachedfileNotice',
    method: 'post'
  })
}

/* 查询公告id对应的附件 */
export function getAttachedfileNotice(noticeId) {
  return request({
    url: process.env.BASE_API + '/coreNotice/findAttachedfileNoticeByID',
    method: 'post',
    params: { noticeId: noticeId }
  })
}

/* 根据附件id删除对应的附件 */
export function deleteAttachedfileById(id) {
  return request({
    url: process.env.BASE_API + '/coreNotice/deleteFileById',
    method: 'post',
    params: { id: id }
  })
}

export function getNoticeById(id) {
  return request({
    url: process.env.BASE_API + '/coreNotice/findNoticeById',
    method: 'get',
    params: id
  })
}

export function queryUnreadUserDetailByNoticeId(noticeId, pageNumber, pageSize) {
  return request({
    url: process.env.BASE_API + '/coreNotice/queryUnreadUserDetailByNoticeId',
    method: 'post',
    params: noticeId, pageNumber, pageSize
  })
}

export function getNoticeByHeading(heading) {
  return request({
    url: process.env.BASE_API + '/coreNotice/findByHeading',
    method: 'get',
    params: heading
  })
}

export function reEdit(notice) {
  return request({
    url: process.env.BASE_API + '/coreNotice/reEdit',
    method: 'post',
    params: notice
  })
}

export function updateNoticeReadById(id, username) {
  return request({
    url: process.env.BASE_API + '/coreNotice/updateNoticeReadById',
    method: 'post',
    params: id, username
  })
}

export function addNotice(notice, readers) {
  return request({
    url: process.env.BASE_API + '/coreNotice/addNotice',
    method: 'post',
    params: notice,
    data: readers
  })
}

export function removeById(id) {
  return request({
    url: process.env.BASE_API + '/coreNotice/removeNoticeById',
    method: 'post',
    params: id
  })
}

// 获取用户阅读者
export function getNoticeReader(noticeId, username) {
  return request({
    url: process.env.BASE_API + '/coreNotice/findNoticeReader',
    method: 'get',
    params: { noticeId: noticeId, username: username }
  })
}

// 增加公告阅读者
export function addNoticeReader(noticeReader) {
  return request({
    url: process.env.BASE_API + '/coreNotice/addReader',
    method: 'post',
    data: noticeReader
  })
}

// 上传文件
export function uploadFiles(formData) {
  return request({
    url: process.env.BASE_API + '/coreNotice/publishNotice',
    method: 'post',
    data: formData
  })
}

// 更新公告文件
export function updateFile(formData) {
  return request({
    url: process.env.BASE_API + '/coreNotice/updateNotice',
    method: 'post',
    data: formData
  })
}

export function getAttachedFileByNoticeId(noticeId) {
  return request({
    url: process.env.BASE_API + '/coreNotice/findFileByNoticeId',
    method: 'get',
    params: noticeId
  })
}

export function downLoadNoticeFile(noticeId) {
  return request({
    url: process.env.BASE_API + '/coreNotice/download',
    responseType: 'blob',
    method: 'post',
    params: noticeId
  })
}
