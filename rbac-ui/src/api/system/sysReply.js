import request from "@/utils/request";

const constName = 'sysReply'

export default {


  get(topicId){
    return request({
      url:`${constName}/get/`+topicId,
      method:'get',
    })
  },
  countNum(topicId){
    return request({
      url:`${constName}/count/`+topicId,
      method:'get',
    })
  },
  save(data) {
    return request({
      url:`${constName}/save`,
      method:'post',
      data:data
    })
  },



}
