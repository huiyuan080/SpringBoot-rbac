import request from "@/utils/request";

const constName = 'sysTopic'

export default {


  getByPage(data) {
    return request({
      url: `${constName}/getByPage`,
      method: 'post',
      data: data
    })
  },
  save(data) {
    return request({
      url: `${constName}/save`,
      method: 'post',
      data: data
    })
  },
  delete(topicId) {
    return request({
      url: `${constName}/delete/` + topicId,
      method: 'delete',
    })
  },
  add(topicId) {
    return request({
      url: `${constName}/add/` + topicId,
      method: 'get',
    })
  },
}


