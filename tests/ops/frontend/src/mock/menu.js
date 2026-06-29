const List = [
  {
    id: 0,
    label: '系统菜单',
    path: '',
    sort: 1,
    name: '系统菜单',
    title: '系统菜单',
    pId: ''
  },
  {
    id: 1,
    label: '系统管理',
    path: '',
    sort: 1,
    name: '系统管理',
    title: '系统管理',
    pId: '0'
  },
  {
    id: 2,
    label: '用户管理',
    path: '/user/list',
    name: '用户管理',
    title: '用户管理',
    sort: 2,
    pId: '1'
  },
  {
    id: 3,
    label: '菜单管理',
    path: '/menu/list',
    sort: 2,
    name: '用户管理',
    title: '用户管理',
    pId: '1'
  },
  {
    id: 4,
    label: '角色管理',
    path: '/role/list',
    name: '用户管理',
    title: '用户管理',
    sort: 2,
    pId: '1'
  }
]
/* const List = [{
  id: 1,
  label: '系统管理',
  path: '',
  sort: 1,
  pId: '',
  children: [{
    id: 2,
    label: '用户管理',
    pId: '1',
    children: [{
      id: 22,
      label: '用户管理22',
      pId: '2',
      children: [{
        id: 221,
        label: '用户管理221',
        pId: '22',
        children: [{
          id: 2211,
          label: '用户管理2211',
          pId: '221'
        }]
      }]
    }, {
      id: 23,
      label: '用户管理23',
      pId: '2'
    }]
  }, {
    id: 3,
    label: '菜单管理',
    pId: '1'
  }]
}] */

export default {
  getList: config => {
    return List
  },
  createMenu: config => {
    const data = JSON.parse(config.body)
    return data
  },
  updateMenu: () => ({
    data: 'success'
  })
}
