import router from './router'
import store from './store'
import NProgress from 'nprogress' // Progress 进度条
import 'nprogress/nprogress.css'// Progress 进度条样式
// import { Message } from 'element-ui'
import { getToken, removeToken, isShowGoogleChromeTip } from '@/utils/auth' // 验权

const whiteList = ['/login', '/ssoLogin'] // 不重定向白名单
router.beforeEach((to, from, next) => {
  /*  console.log(to.path)
  console.log(from.path)
  console.log(to)
  console.log(to.meta.keepAlive) */
  if (to.path === '/ssoLogin') {
    console.log('门户单点登录to', to)
    if (getToken()) {
      console.log('门户单点登录to,清空')
      removeToken()
    }
  }
  // C14717 bugid：14930  2024/8/14
  if (getToken()) {
    if (getToken() === 0) {
      removeToken()
    }
  }
  if (isShowGoogleChromeTip()) {
    if (to.path === '/chromeLimit') {
      next()
    } else {
      next(`/chromeLimit`) // 否则全部重定向到登录页
      NProgress.done()
    }
    // next({ path: '/' })
    // NProgress.done()
  } else {
    NProgress.start()
    // 存在 token 说明已经登录
    if (getToken()) {
      if (to.path === '/login') {
        // 登录过就不能访问登录界面，需要中断这一次路由守卫，执行下一次路由守卫，并且下一次守卫的to是主页'
        next({ path: '/' })
        NProgress.done() // if current page is dashboard will not trigger	afterEach hook, so manually handle it
      } else {
        if (store.getters.roles.length === 0) {
          store.dispatch('GetInfo').then(res => { // 拉取用户信息
            console.log('用户信息 res ->', res)
            const roles = res.content.roles // note: roles must be a array! such as: ['editor','develop']
            store.dispatch('GenerateRoutes', { roles }).then(() => { // 根据roles权限生成可访问的路由表
              router.addRoutes(store.getters.addRouters) // 动态添加可访问路由表
              // 如果 addRoutes 并未完成，路由守卫会一层一层的执行执行，直到 addRoutes 完成，找到对应的路由
              next({ ...to, replace: true }) // hack方法 确保addRoutes已完成 ,设置 replace: true 这样导航就不会留下历史记录
            })
          }).catch(() => {
            store.dispatch('FedLogOut').then(() => {
              // 以下部分消息提示可注释 request.js有加
              // Message.error(err || 'Verification failed, please login again')
              // Message.error(err)
              next({ path: '/' })
            })
          })
        } else {
          if (to.name === null) {
            console.log('to name ->', to.name)
            next({ path: '/401', replace: true, query: { noGoBack: true }})
          } else {
            next()
          }
        }
      }
    } else {
      if (whiteList.indexOf(to.path) !== -1) {
        next()
      } else {
        next(`/login`) // 否则全部重定向到登录页
        NProgress.done()
      }
    }
  }
})

router.afterEach(() => {
  NProgress.done() // 结束Progress
})
