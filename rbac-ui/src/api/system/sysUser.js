import request from "@/utils/request";

const sysUser = 'sysUser'

export default {
  getByPage(data){
    return request({
      url:`${sysUser}/getByPage`,
      method: 'post',
      data:data
    })
  },
  save(data){
    return request({
      url:`${sysUser}/save`,
      method: 'post',
      data:data
    })
  },
  update(data){
    return request({
      url:`${sysUser}/update`,
      method: 'put',
      data:data
    })
  },
  delete(userId){
    return request({
      url:`${sysUser}/delete/`+userId,
      method: 'delete',
    })
  },
  resetPwd(userId){
    return request({
      url:`${sysUser}/resetPwd/`+userId,
      method: 'get',
    })
  },
  get(userId){
    return request({
      url:`${sysUser}/get/`+userId,
      method: 'get',
    })
  },





}
