import axios from 'axios'

const request = axios.create({
  baseURL: '/api',
  timeout: 60000,
  headers: {
    'Content-Type': 'application/json; charset=utf-8'
  }
})

// 请求拦截器
request.interceptors.request.use(
  config => config,
  error => Promise.reject(error)
)

// 响应拦截器
request.interceptors.response.use(
  response => {
    const res = response.data
    if (res.code !== 200) {
      // 不在拦截器中弹窗，由业务代码自行处理
      return Promise.reject(new Error(res.message || '请求失败'))
    }
    return res
  },
  error => {
    return Promise.reject(error)
  }
)

export default request
