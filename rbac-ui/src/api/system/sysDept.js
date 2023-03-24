import request from "@/utils/request";

const sysDept = "/sysDept"

export default {
  listDept(data) {
    return request({
      url: `${sysDept}/listDept`,
      method: "get",
      params: data
    })
  },
  save(data) {
    return request({
      url: `${sysDept}/save`,
      method: "post",
      data: data
    })
  },
  update(data) {
    return request({
      url: `${sysDept}/update`,
      method: "put",
      data: data
    })
  },
  delete(deptId) {
    return request({
      url: `${sysDept}/delete/`+deptId,
      method: "delete"
    })
  },
  get(deptId) {
    return request({
      url: `${sysDept}/get/`+deptId,
      method: "get"
    })
  },


}
