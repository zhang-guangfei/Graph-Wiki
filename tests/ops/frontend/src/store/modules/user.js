import { login, loginSso, logout, getInfo, refreshToken } from '@/api/login'
import { findMenuByUserId } from '@/api/menu'
import { findBtnsByUserId } from '@/api/authority'
import { getToken, setToken, setTokenSso, setRefreshToken, removeToken, removeRefreshToken, setPosition, getPosition, removePosition, removeAgentMarketConditionToken, removeAgentMarketPageToken } from '@/utils/auth'
import { arrayToTree } from '@/utils'

const user = {
  state: {
    code: '',
    token: getToken(),
    refresh_token: '',
    name: '',
    info: {},
    position: getPosition(),
    avatar: '',
    menus: '',
    roles: [],
    positions: {
      positionNames: [],
      unitIds: [],
      unitCodes: [],
      positionIds: []
    }
  },

  mutations: {
    SET_CODE: (state, code) => {
      state.code = code
    },
    SET_TOKEN: (state, token) => {
      state.token = token
    },
    SET_TOKENSSO: (state, userCode) => {
      state.userCode = userCode
    },
    SET_REFRESH_TOKEN: (state, refresh_token) => {
      state.refresh_token = refresh_token
    },
    SET_NAME: (state, name) => {
      state.name = name
    },
    /**
     * 用户信息*/
    SET_INFO: (state, info) => {
      state.info = info
    },
    /**
     * 岗位信息*/
    SET_POSITION: (state, position) => {
      state.position = position
    },
    SET_POSITIONS: (state, positions) => {
      state.positions = positions
    },
    SET_AVATAR: (state, avatar) => {
      state.avatar = avatar
    },
    SET_ROLES: (state, roles) => {
      state.roles = roles
    },
    SET_BTNS: (state, btns) => {
      state.btns = btns
    },
    SET_MENUS: (state, menus) => {
      state.menus = menus
    }
  },

  actions: {
    // 登录
    Login({ commit }, userInfo) {
      console.log('用户表单 => ', userInfo)
      const username = userInfo.username.trim()
      return new Promise((resolve, reject) => {
        login(username, userInfo.password).then(data => {
          if (typeof data.status === 'undefined') {
            removeToken()
            setToken(data.access_token)
            // alert(data.access_token)
            setRefreshToken(data.refresh_token)
            commit('SET_TOKEN', data.access_token)
            commit('SET_REFRESH_TOKEN', data.refresh_token)
            resolve()
          }
        }).catch(error => {
          console.log(error)
          reject(error)
        })
      })
    },
    // 统一门户中转 C14717 bugid：14930  2024/8/14
    SsoLogin({ commit }, userInfo) {
      return new Promise((resolve, reject) => {
        commit('SET_TOKEN', '')
        commit('SET_REFRESH_TOKEN', '')
        commit('SET_ROLES', [])
        commit('SET_POSITION', {})
        commit('SET_POSITIONS', {})
        removeToken()
        removePosition()
        removeRefreshToken()
        removeAgentMarketConditionToken()
        removeAgentMarketPageToken()
        localStorage.clear()
        loginSso(userInfo).then(res => {
          if (res.success) {
            setToken(res.content.token)
            setTokenSso(res.content.userCode)
            commit('SET_TOKEN', res.content.token)
            commit('SET_TOKENSSO', res.content.userCode)
            resolve()
          } else {
            reject(res.message)
          }
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 刷新token
    RefreshToken({ commit }, refresh_token) {
      return new Promise((resolve, reject) => {
        refreshToken(refresh_token).then(data => {
          setToken(data.access_token)
          setRefreshToken(data.refresh_token)
          commit('SET_TOKEN', data.access_token)
          commit('SET_REFRESH_TOKEN', data.refresh_token)
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 获取用户信息
    GetInfo({ commit, state }) {
      return new Promise((resolve, reject) => {
        getInfo().then(data => {
          if (data === undefined) {
            return
          }
          const roles = data.content.roles
          const username = data.content.remoteUser.username
          const btns = data.content.authorityCodes
          const info = data.content.remoteUser
          if (roles && roles.length > 0) { // 验证返回的roles是否是一个非空数组
            commit('SET_ROLES', roles)
          } else {
            reject('getInfo: roles must be a non-null array !')
          }
          commit('SET_NAME', username)
          commit('SET_INFO', info)
          var position = {}
          if (!state.position) {
            position = data.content.remoteUser.employeePositions[0]
          } else {
            position = typeof (state.position) === 'string' ? JSON.parse(state.position) : state.position
            if (JSON.stringify(position) === '{}') {
              position = data.content.remoteUser.employeePositions[0]
            }
          }
          commit('SET_POSITION', position)
          setPosition(position)
          var positions = {}
          positions.positionIds = []
          positions.positionNames = []
          positions.unitIds = []
          positions.unitCodes = []
          data.content.remoteUser.employeePositions.forEach((item, index) => {
            positions.positionIds.push(item.positionId)
            positions.positionNames.push(item.positionName)
            positions.unitIds.push(item.unitId)
            positions.unitCodes.push(item.unitCode)
          })
          commit('SET_POSITIONS', positions)
          // console.log(data.content.remoteUser.positions[0])
          commit('SET_BTNS', btns)
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },
    // 登出
    LogOut({ commit, state }) {
      return new Promise((resolve, reject) => {
        logout({ token: getToken() }).then(() => {
          commit('SET_TOKEN', '')
          commit('SET_REFRESH_TOKEN', '')
          commit('SET_ROLES', [])
          commit('SET_POSITION', {})
          commit('SET_POSITIONS', {})
          removeToken()
          removePosition()
          removeRefreshToken()
          // removeDailyConditionToken()
          // removeDailyPageToken()
          // removeMarketConditionToken()
          // removeMarketPageToken()
          removeAgentMarketConditionToken()
          removeAgentMarketPageToken()
          // removeSalesplanConditionToken()
          // removeSalesplanPageToken()
          localStorage.clear()
          resolve()
        }).catch(error => {
          reject(error)
        })
      })
    },

    // 前端 登出
    FedLogOut({ commit }) {
      return new Promise(resolve => {
        commit('SET_TOKEN', '')
        commit('SET_REFRESH_TOKEN', '')
        commit('SET_ROLES', [])
        commit('SET_POSITION', {})
        commit('SET_POSITIONS', {})
        commit('SET_TOKENSSO', {})
        removeToken()
        removePosition()
        removeRefreshToken()
        // removeDailyConditionToken()
        // removeDailyPageToken()
        // removeMarketConditionToken()
        // removeMarketPageToken()
        removeAgentMarketConditionToken()
        removeAgentMarketPageToken()
        // removeSalesplanConditionToken()
        // removeSalesplanPageToken()
        localStorage.clear()
        resolve()
      })
    },

    GetAsyncRouter({ commit, state }) {
      return new Promise((resolve, reject) => {
        findMenuByUserId(state.name, state.positions.positionIds.join(',')).then(data => {
          const menus = []
          data.forEach((item, index) => {
            const menu = {}
            const meta = {}
            menu.id = item.id
            menu.pid = item.pid
            menu.path = item.path
            menu.name = item.name
            menu.component = item.component
            menu.hidden = !(item.hidden === '0')
            meta.title = item.title
            meta.icon = item.icon
            meta.keepAlive = item.keepAlive
            menu.meta = meta
            menu.level = item.level
            menus.push(menu)
          })
          commit('SET_MENUS', arrayToTree(menus))
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    },

    GetAsyncBtns({ commit, state }) {
      return new Promise((resolve, reject) => {
        findBtnsByUserId(state.name).then(data => {
          commit('SET_BTNS', data)
          resolve(data)
        }).catch(error => {
          reject(error)
        })
      })
    }
  }
}

export default user
