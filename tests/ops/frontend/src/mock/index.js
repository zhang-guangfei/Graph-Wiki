import Mock from 'mockjs'
import loginAPI from './login'
import roleAPI from './role'
import menuAPI from './menu'
import authorityAPI from './authority'
import marketAPI from './market'
import resourceAPI from './resource'
import transactionAPI from './transaction'

// 修复在使用 MockJS 情况下，设置 withCredentials = true，且未被拦截的跨域请求丢失 Cookies 的问题
// https://github.com/nuysoft/Mock/issues/300
Mock.XHR.prototype.proxy_send = Mock.XHR.prototype.send
Mock.XHR.prototype.send = function() {
  if (this.custom.xhr) {
    this.custom.xhr.withCredentials = this.withCredentials || false
  }
  this.proxy_send(...arguments)
}

// Mock.setup({
//   timeout: '350-600'
// })

// 登录相关
Mock.mock(/login/, 'post', loginAPI.login)
Mock.mock(/\/user\/logout/, 'post', loginAPI.logout)
Mock.mock(/getUserInfo/, 'get', loginAPI.getUserInfo)

// 文章相关
/* Mock.mock(/\/article\/list/, 'get', articleAPI.getList)
Mock.mock(/\/article\/detail/, 'get', articleAPI.getArticle)
Mock.mock(/\/article\/pv/, 'get', articleAPI.getPv)
Mock.mock(/\/article\/create/, 'post', articleAPI.createArticle)
Mock.mock(/\/article\/update/, 'post', articleAPI.updateArticle) */

/**
 *角色相关
 **/
Mock.mock(/\/role\/list/, 'get', roleAPI.getList)
Mock.mock(/\/role\/update/, 'post', roleAPI.updateRole)
Mock.mock(/\/role\/create/, 'post', roleAPI.createRole)
Mock.mock(/\/role\/bind/, 'post', roleAPI.bindAuthority)
Mock.mock(/\/role\/findAuthorityById/, 'get', roleAPI.findAuthorityById)

/**
 * 菜单相关
 */
Mock.mock(/\/menu\/list/, 'get', menuAPI.getList)
Mock.mock(/\/menu\/create/, 'post', menuAPI.createMenu)

/**
 * 权限相关
 */
Mock.mock(/\/authority\/list/, 'get', authorityAPI.getList)
Mock.mock(/\/authority\/create/, 'post', authorityAPI.createAuthority)
Mock.mock(/\/authority\/update/, 'post', authorityAPI.updateAuthority)
Mock.mock(/\/authority\/delete/, 'post', authorityAPI.deleteAuthority)
Mock.mock(/\/authority\/bind/, 'post', authorityAPI.bindResource)
Mock.mock(/\/authority\/findBindResourceById/, 'get', authorityAPI.findBindResourceById)

/**
 * 资源相关
 */
Mock.mock(/\/resource\/list/, 'get', resourceAPI.getList)
Mock.mock(/\/resource\/create/, 'post', resourceAPI.createResource)
Mock.mock(/\/resource\/update/, 'post', resourceAPI.updateResource)

// 搜索相关
// Mock.mock(/\/search\/user/, 'get', remoteSearchAPI.searchUser)

// 账单相关
Mock.mock(/\/transaction\/list/, 'get', transactionAPI.getList)

// 市况管理
Mock.mock(/\/market\/query\/getScopeOptions/, 'post', marketAPI.getScopeOptions)

export default Mock
