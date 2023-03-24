import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login',
    method: 'post',
    params: data
  })
}

//获取验证码
export function getCaptcha() {
  return request({
    url: '/captcha/getCaptcha',
    method: 'get',
  })


}

export function getInfo() {
  return request({
    url: '/getUserInfo',
    method: 'get'
  })
}

export function logout() {
  return request({
    url: '/logout',
    method: 'get'
  })
}
