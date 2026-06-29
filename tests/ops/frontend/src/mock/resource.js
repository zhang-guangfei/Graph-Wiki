const List = [
  {
    id: 0,
    label: '系统资源',
    name: '系统资源',
    pattern: 'menu',
    pId: ''
  },
  {
    id: 1,
    label: '系统管理',
    name: '系统管理',
    pattern: 'menu',
    pId: '0'
  },
  {
    id: 2,
    label: '用户管理',
    name: '用户管理',
    pattern: 'menu',
    pId: '1'
  },
  {
    id: 3,
    label: '菜单管理',
    name: '菜单管理',
    pattern: 'menu',
    pId: '1'
  }, {
    id: 4,
    label: '角色管理',
    name: '角色管理',
    pattern: 'menu',
    pId: '1'
  }
]

export default {
  getList: config => {
    return List
  },
  createResource: config => {
    const data = JSON.parse(config.body)
    return data
  },
  updateResource: config => {
    const data = JSON.parse(config.body)
    return data
  },
  deleteResource: config => {
    const data = JSON.parse(config.body)
    return data
  }
}
