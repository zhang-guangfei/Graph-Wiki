const List = [
  {
    id: 0,
    label: '系统权限',
    name: '系统权限',
    type: 'menu',
    pId: ''
  },
  {
    id: 1,
    label: '系统管理',
    name: '系统管理',
    type: 'menu',
    pId: '0'
  },
  {
    id: 2,
    label: '用户管理',
    name: '用户管理',
    type: 'menu',
    pId: '1'
  },
  {
    id: 3,
    label: '菜单管理',
    name: '菜单管理',
    type: 'menu',
    pId: '1'
  }, {
    id: 4,
    label: '角色管理',
    name: '角色管理',
    type: 'menu',
    pId: '1'
  }
]

export default {
  getList: config => {
    return List
  },
  createAuthority: config => {
    const data = JSON.parse(config.body)
    return data
  },
  updateAuthority: config => {
    const data = JSON.parse(config.body)
    return data
  },
  deleteAuthority: config => {
    const data = JSON.parse(config.body)
    return data
  },
  bindResource: config => {
    const data = JSON.parse(config.body)
    return data
  },
  findBindResourceById: config => {
    return '1,2,3,4'
  }
}
