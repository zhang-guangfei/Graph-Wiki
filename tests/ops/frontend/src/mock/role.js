import Mock from 'mockjs'
import { param2Obj } from '@/utils'

const List = []
const count = 100

for (let i = 0; i < count; i++) {
  List.push(Mock.mock({
    id: '@increment',
    timestamp: +Mock.Random.date('T'),
    name: '@first',
    'status|1': ['published', 'draft', 'deleted']
  }))
}

export default {
  getList: config => {
    const { page = 1, limit = 20, sort } = param2Obj(config.url)
    let mockList = List.filter(item => {
      return true
    })

    if (sort === '-id') {
      mockList = mockList.reverse()
    }

    const pageList = mockList.filter((item, index) => index < limit * page && index >= limit * (page - 1))

    return {
      total: mockList.length,
      items: pageList
    }
  },
  getPv: () => ({
    pvData: [{ key: 'PC', pv: 1024 }, { key: 'mobile', pv: 1024 }, { key: 'ios', pv: 1024 }, { key: 'android', pv: 1024 }]
  }),
  getRole: (config) => {
    const { id } = param2Obj(config.url)
    for (const role of List) {
      if (role.id === +id) {
        return role
      }
    }
  },
  createRole: () => ({
    data: 'success'
  }),
  updateRole: () => ({
    data: 'success'
  }),
  bindAuthority: () => ({
    data: 'success'
  }),
  findAuthorityById: (condig) => ({
    data: '1,2,3,4'
  })
}
