import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'


/**
 * 静态路由
 */
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect/index')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    //默认隐藏
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      {
        path: 'dashboard',
        component: () => import('@/views/dashboard/index'),
        name: 'Dashboard',
        meta: {title: '工作台', icon: 'dashboard', affix: true}
      }
    ]
  },

  {
    path: '/system/dictData',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'index/:dictType',
        component: () => import('@/views/system/dictData/sys-dict-data-list'),
        name: 'Data',
        meta: {title: '字典数据', activeMenu: '/system/dict'}
      }
    ]
  }
]

/**
 * 动态路由
 */

export const asyncRoutes = [

  // 404 页面
  {path: '*', redirect: '/404', hidden: true}
]

const createRouter = () => new Router({
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})

const router = createRouter()


export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
