import request from '@/utils/request'

// 登录方法 C14717 bugid：14930  2024/8/14
export function loginSso(queryParam) {
  return request({
    url: process.env.AUTH_API + '/sso/login',
    method: 'post',
    data: queryParam
  })
}

export function login(username, password) {
  const grant_type = 'password'
  const client_id = '07b6532b36094573b7d4562st235xdjk'
  const client_secret = '8dc7179a449148f897a21094885039c8'
  const scope = 'app'
  return request({
    url: process.env.AUTH_API + '/oauth/token',
    headers: {
      Authorization: 'Basic MDdiNjUzMmIzNjA5NDU3M2I3ZDQ1NjJzdDIzNXhkams6OGRjNzE3OWE0NDkxNDhmODk3YTIxMDk0ODg1MDM5Yzg='
    },
    method: 'post',
    params: { username, password, grant_type, scope, client_id, client_secret }
  })
}

export function refreshToken(refresh_token) {
  const grant_type = 'refresh_token'
  const client_id = '07b6532b36094573b7d4562st235xdjk'
  const client_secret = '8dc7179a449148f897a21094885039c8'
  return request({
    url: process.env.AUTH_API + '/oauth/token',
    headers: {
      Authorization: 'Basic MDdiNjUzMmIzNjA5NDU3M2I3ZDQ1NjJzdDIzNXhkams6OGRjNzE3OWE0NDkxNDhmODk3YTIxMDk0ODg1MDM5Yzg='
    },
    method: 'post',
    params: { grant_type, refresh_token, client_id, client_secret }
  })
}

export function getInfo() {
  return request({
    url: process.env.BASE_API + '/user/getUserInfo/',
    method: 'post'
  })
}

export function logout(data) {
  return request({
    url: process.env.BASE_API + '/logoutSuccess',
    method: 'post',
    params: { token: data.token }
  })
}
