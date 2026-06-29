import axios from 'axios'
import { MessageBox, Notification } from 'element-ui'
import ElementUI from 'element-ui'
import store from '@/store'
import { getToken, getTokenSso, setToken, getRefreshToken } from '@/utils/auth'

// 创建axios实例
axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
const service = axios.create({
  baseURL: '', // api 的 base_url  process.env.SERVICE_API
  timeout: 3600000 // 请求超时时间
  // default: { withCredentials: false }
  // headers: {
  //   'Content-Type': 'application/json;charset=UTF-8'
  // }
})

// request拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['Authorization'] = 'Bearer ' + getToken() // 让每个请求携带自定义token 请根据实际情况自行修改
      config.headers['sso'] = getTokenSso() // 让每个请求携带自定义token 请根据实际情况自行修改 C14717 bugid：14930  2024/8/14
      config['refresh_token'] = false // 处理刷新token后重新请求的自定义变量
    }
    if (config.from === 'BJ') {
      config.headers['Authorization'] = '' // 调用北京接口，不需要携带token
    }
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  /**
   * response => response,
   */
  response => {
    /**
     * code为非20000是抛错 可结合自己业务进行修改//return response.data
     */
    const headers = response.headers
    if (headers.token) {
      setToken(headers.token)
    }
    // const data = response.data
    // if (data.success !== undefined && !data.success) {
    //   this.$notify({
    //     title: '错误',
    //     message: data.message,
    //     type: 'error'
    //   })
    //   return Promise.reject(data.message)
    // }
    return response.data
  },
  error => {
    let message = ''
    if (!error.response && error.message) {
      message = error.message
    } else if (error.response && error.response.status === 403) {
      message = error.response.data.message
      if (typeof (message) === 'undefined') {
        message = error.response.data.error_description
      }
    } else if (error.response.status === 400) {
      message = error.response.data.message
    } else if (error.response.status === 401) {
      let response = error.response
      message = response.data.message
      if (response.data.code) {
        const config = response.config
        config.refresh_token = true
        response = store.dispatch('RefreshToken', getRefreshToken()).then(() => {
          config.baseURL = ''
          config.headers.Authorization = 'Bearer ' + getToken()
          return service(config)
        }).catch((er) => {
          MessageBox.alert('会话失效，请重新登录', '登录过期', {
            confirmButtonText: '确定',
            callback: action => {
              store.dispatch('FedLogOut').then(() => {
                location.reload() // 为了重新实例化vue-router对象 避免bug
              })
            }
          })
          return response
        })
        return response
      }
    } else {
      message = error.response.data.message
    }

    if (message.indexOf('Invalid refresh token') !== -1) {
      return
    }

    if (message === '') {
      return
    }
    // if (message.indexOf('532') !== -1 || message.indexOf('773') !== -1) {
    //   let username = error.response.config.params.username
    //   username = username.trim()
    //   location.href = process.env.AUTH_API + '/resetPassword' + '?userId=' + username + '&sysUrl=' + window.location.href
    // }

    if (message.indexOf('532') !== -1 || message.indexOf('773') !== -1) {
      let username = error.response.config.params.username
      username = username.trim()
      // 判断是否是代理店账号
      var isAgencyStore = username.startsWith('ACC')
      if (isAgencyStore) {
        // 通过 忘记密码 修改
        ElementUI.Notification({
          title: '错误',
          message: '您的密码已过期，请点击忘记密码进行重置！',
          type: 'error'
        })
        return
      } else {
        // 跳转到统一门户修改
        location.href = 'http://10.116.1.241/resetPassword' + '?userId=' + username + '&sysUrl=' + window.location.href
      }
    }

    Notification({
      title: '错误',
      message: message,
      type: 'error'
    })
    return Promise.reject(error)
  }
)

export default service
