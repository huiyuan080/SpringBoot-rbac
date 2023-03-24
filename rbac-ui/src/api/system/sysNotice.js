import request from "@/utils/request";

const finalName = "sysNotice"

export default {
  getByPage(data) {
    return request({
      url: `${finalName}/getByPage`,
      method: 'post',
      data: data
    })
  },

  save(data) {
    return request({
      url: `${finalName}/save`,
      method: "post",
      data: data
    })
  },
  update(data) {
    return request({
      url: `${finalName}/update`,
      method: "put",
      data: data
    })
  },
  delete(noticeId) {
    return request({
      url: `${finalName}/delete/` + noticeId,
      method: "delete"
    })
  },
  get(noticeId) {
    return request({
      url: `${finalName}/get/` + noticeId,
      method: "get"
    })
  },



}
