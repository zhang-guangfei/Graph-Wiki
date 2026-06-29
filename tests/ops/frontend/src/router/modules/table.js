/** When your routing table is too long, you can split it into small modules**/

import Layout from '@/layout/Layout'

const tableRouter = {
  path: '/role',
  component: Layout,
  redirect: '/role/list',
  name: 'Role',
  meta: {
    title: '角色管理',
    icon: 'example'
  },
  children: [
    {
      path: 'list',
      component: () => import('@/views/role/roleList'),
      name: 'RoleList',
      meta: { title: '角色管理' }
    }
  ]
}
export default tableRouter
