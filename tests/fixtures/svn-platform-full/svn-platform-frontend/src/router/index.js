import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/index.vue'

const routes = [
  {
    path: '/index.html',
    redirect: '/dashboard'
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: { title: '首页', icon: 'HomeFilled' }
      }
    ]
  },
  {
    path: '/repo',
    component: Layout,
    redirect: '/repo/list',
    meta: { title: '仓库管理', icon: 'FolderOpened' },
    children: [
      {
        path: 'list',
        name: 'RepoList',
        component: () => import('@/views/repository/list.vue'),
        meta: { title: '仓库列表', icon: 'List' }
      }
    ]
  },
  {
    path: '/svn',
    component: Layout,
    redirect: '/svn/status',
    meta: { title: 'SVN操作', icon: 'Operation' },
    children: [
      {
        path: 'status/:repoId?',
        name: 'SvnStatus',
        component: () => import('@/views/svn/status.vue'),
        meta: { title: '工作副本状态', icon: 'Document' }
      },
      {
        path: 'log/:repoId?',
        name: 'SvnLog',
        component: () => import('@/views/svn/log.vue'),
        meta: { title: '提交记录', icon: 'Clock' }
      },
      {
        path: 'branch-tag',
        name: 'SvnBranchTag',
        component: () => import('@/views/svn/branch-tag.vue'),
        meta: { title: '分支/标签管理', icon: 'Share' }
      },
      {
        path: 'operation-history/:repoId?',
        name: 'OperationHistory',
        component: () => import('@/views/svn/operation-history.vue'),
        meta: { title: '操作历史', icon: 'Histogram' }
      },
      {
        path: 'merge',
        name: 'SvnMerge',
        component: () => import('@/views/svn/merge.vue'),
        meta: { title: '代码合并', icon: 'Connection' }
      },
      {
        path: 'local-repo/:repoId?',
        name: 'LocalRepo',
        component: () => import('@/views/svn/local-repo.vue'),
        meta: { title: '本地仓库管理', icon: 'Monitor' }
      }
    ]
  },
  {
    path: '/requirement',
    component: Layout,
    redirect: '/requirement/list',
    meta: { title: '需求管理', icon: 'Tickets' },
    children: [
      {
        path: 'list',
        name: 'RequirementList',
        component: () => import('@/views/requirement/requirement.vue'),
        meta: { title: '需求列表', icon: 'List' }
      }
    ]
  },
  {
    path: '/:pathMatch(.*)*',
    redirect: '/dashboard'
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
