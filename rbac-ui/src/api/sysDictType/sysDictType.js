import request from "@/utils/request";

const sysDictType = "/sysDictType"

export default {
  Search(data) {
    return request({
      url: `${sysDictType}/getByPage`,
      method: 'post',
      data: data
    })
  },

  save(data) {
    return request({
      url: `${sysDictType}/save`,
      method: 'post',
      data: data
    })
  },
  update(data) {
    return request({
      url: `${sysDictType}/update`,
      method: 'put',
      data: data
    })
  },

  delete(dictId) {
    return request({
      url: `${sysDictType}/delete/` + dictId,
      method: 'delete'
    })
  },

  get(dictId) {
    return request({
      url: `${sysDictType}/get/` + dictId,
      method: 'get'
    })
  },
  refreshCache() {
    return request({
      url: `${sysDictType}/refreshCache`,
      method: 'get'
    })
  }

}
