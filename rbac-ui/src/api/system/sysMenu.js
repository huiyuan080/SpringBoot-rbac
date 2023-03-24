import request from "@/utils/request";


const sysMenu = '/sysMenu'

export default {
  listMenu(data) {
    return request({
      url: `${sysMenu}/listMenu`,
      method: 'get',
      params: data
    })
  },
  save(data) {
    return request({
      url: `${sysMenu}/save`,
      method: "post",
      data: data
    })
  },
  update(data) {
    return request({
      url: `${sysMenu}/update`,
      method: "put",
      data: data
    })
  },
  delete(menuId) {
    return request({
      url: `${sysMenu}/delete/` + menuId,
      method: "delete"
    })
  },
  get(menuId) {
    return request({
      url: `${sysMenu}/get/` + menuId,
      method: "get"
    })
  },
  listMenuMC() {
    return request({
      url: `${sysMenu}/listMenuMC/`,
      method: "get"
    })
  },

  getRouters() {
    return request({
      url: `${sysMenu}/getRouters/`,
      method: "get"
    })
  },
}
