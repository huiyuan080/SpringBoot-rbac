import request from "@/utils/request";


const sysMenu = '/sysRole'

export default {
  getByPage(data) {
    return request({
      url: `${sysMenu}/getByPage`,
      method: 'post',
      data: data
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
  saveRoleMenu(roleId, menuIds) {
    let params = "roleId=" + roleId
    /**
     * springmvc 接收参数为数组
     *    menuIds=1&menuIds=2&menuIds=100 拼接
     */
    menuIds.filter(menuId => {
      params = params + "&menuIds=" + menuId
    })

    return request({
      url: `${sysMenu}/saveRoleMenu?` + params,
      method: "post",
    })
  },


  getCurrentRoleHasMenuIdsByRoleId(roleId) {
    return request({
      url: `${sysMenu}/getCurrentRoleHasMenuIdsByRoleId/` + roleId,
      method: "get"
    })
  },

  /**
   * 查询所有角色不分页
   */
  getAllRoles() {
    return request({
      url: `${sysMenu}/getAllRoles`,
      method: 'get',
    })
  },
  /**
   * 查询当前用户已拥有的角色IDS
   */
  getRoleIdsByUserId(userId) {
    return request({
      url: `${sysMenu}/getRoleIdsByUserId/` + userId,
      method: 'get',
    })
  },
  /**
   * 保存用户和角色之间的关系
   */
  saveUserRoles(userId, roleIds) {
    let params = "userId=" + userId
    roleIds.filter(roleId => {
      params += "&roleIds=" + roleId
    })
    return request({
      url: `${sysMenu}/saveUserRoles?` + params,
      method: 'post'
    })
  }


}
