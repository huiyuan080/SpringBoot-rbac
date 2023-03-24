import {asyncRoutes, constantRoutes} from '@/router'
import sysMenuApi from '@/api/system/sysMenu'
import Layout from '@/layout'

/**
 * Use meta.role to determine if the current user has permission
 * @param roles
 * @param route
 */
function hasPermission(roles, route) {
  if (route.meta && route.meta.roles) {
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = {...route}
    /*if (hasPermission(roles, tmp)) {
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }*/
    if (tmp.component) {
      // Layout组件特殊处理
      if (tmp.component === 'Layout') {
        tmp.component = Layout
      } else {
        tmp.component = loadView(route.component)
      }
    }
    if (tmp.children && tmp.children[0]) {
      tmp.children = filterAsyncRoutes(tmp.children)
    }
    res.push(tmp)
  })
  return res

}

//在我们接口提供的数据中，并不是完整的引入路径。
//如果提供了以 `@` 开头的完整路径，会报错。因此我们需要手动引入，就是上面代码中的 `loadView`
export const loadView = (view) => { // 路由懒加载
  return (resolve) => require([`@/views/${view}`], resolve)
}


const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({commit},) {
    return new Promise(resolve => {
      /*  let accessedRoutes = null
        if (roles.includes('admin')) {
          accessedRoutes = asyncRoutes || []
        } else {
          accessedRoutes = filterAsyncRoutes(asyncRoutes, roles)
        }*/
      //动态获取用户菜单记录
      sysMenuApi.getRouters().then(res => {
        let accessedRoutes = filterAsyncRoutes(res.data)
        commit('SET_ROUTES', accessedRoutes)
        resolve(accessedRoutes)
      })

    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
