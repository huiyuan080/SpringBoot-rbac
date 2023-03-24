import request from "@/utils/request";

const sysDictData = "/sysDictData"

export default {

  search(data){
    return request({
      url:`${sysDictData}/getByPage`,
      method:'post',
      data:data
    })
  },
  save(data){
    return request({
      url:`${sysDictData}/save`,
      method:'post',
      data:data
    })
  },
  update(data){
    return request({
      url:`${sysDictData}/update`,
      method:'put',
      data:data
    })
  },
  delete(dictCode){
    return request({
      url:`${sysDictData}/delete/`+dictCode,
      method:'delete',
    })
  },
  get(dictCode){
    return request({
      url:`${sysDictData}/get/`+dictCode,
      method:'get',
    })
  },
  getDictDataByDictType(dictType){
    return request({
      url:`${sysDictData}/getDictDataByDictType/`+dictType,
      method:'get',
    })
  },

}
