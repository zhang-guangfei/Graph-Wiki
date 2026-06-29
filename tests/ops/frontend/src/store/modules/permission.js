import { asyncRouterMap, constantRouterMap } from '@/router'
import store from '@/store'
const _import = require('@/router/_import_' + process.env.NODE_ENV)
import Layout from '@/layout/Layout'
import EmptyTemplate from '@/layout/EmptyTemplate'

/**
 * 通过meta.role判断是否与当前用户权限匹配
 * @param roles
 * @param route
 */
/* function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
} */

/**
 * 递归过滤异步路由表，返回符合用户角色权限的路由表
 * @param routes asyncRouterMap
 * @param roles
 */
function filterAsyncRouter(routes, roles) {
  /* console.log(routes)
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRouter(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res */
  const accessedRouters = routes.filter(route => {
    if (route.component) {
      if (route.component === '/') {
        if (route.level >= 2) {
          route.component = EmptyTemplate
        } else {
          route.component = Layout
        }
        if (route.children && route.children.length >= 1) {
          /**
           * 解决菜单下只有一个隐藏菜单的问题，但是还无法避免多个隐藏子菜单的显示问题
           * 考虑做一个通用功能的隐藏父菜单,不能直接删除，因为权限拦截的时候要根据是否包含该路径放行
           */
          if (route.children.length === 1 && route.children[0].hidden === true) {
            route.hidden = true
          } else {
            route.alwaysShow = true
          }
        }
      } else {
        try {
          route.component = _import(route.component)
          if (route.children && route.children.length >= 1) {
            route.alwaysShow = true
          }
        } catch (error) {
          console.log(error)
          // alert(error)
          route.component = {}
        }
      }
    }
    if (route.children && route.children.length) {
      route.children = filterAsyncRouter(route.children)
    }
    return true
  })
  return accessedRouters
}

const permission = {
  state: {
    routers: constantRouterMap,
    addRouters: []
  },
  mutations: {
    SET_ROUTERS: (state, routers) => {
      state.addRouters = routers
      state.routers = constantRouterMap.concat(routers)
      // console.log('state.routers:')
      // console.log(state.routers)
    }
  },
  actions: {
    GenerateRoutes({ commit }, data) {
      return new Promise(resolve => {
        const { roles } = data
        let accessedRouters
        store.dispatch('GetAsyncRouter', '').then(() => {
          if (roles.includes('admin')) {
            accessedRouters = asyncRouterMap
          } else {
            accessedRouters = filterAsyncRouter(store.getters.menus, roles)
          }
          console.log('当前登录者的路由资源集合 --> ', accessedRouters)
          commit('SET_ROUTERS', accessedRouters)
          resolve()
        }).catch((error) => {
          console.log(error)
        })
      })
    }
  }
}

export default permission
