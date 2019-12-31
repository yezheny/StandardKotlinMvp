package com.yryz.module_core.common.exception
/**
 * Desc:
 * Author: hy
 * Date: 2019/7/3 14:33
 **/

/*API错误*/
const val API_ERROR = 0x0
/*网络错误*/
const val NETWORD_ERROR = 0x1
/*http_错误*/
const val HTTP_ERROR = 0x2
/*json错误*/
const val JSON_ERROR = 0x3
/*未知错误*/
const val UNKNOWN_ERROR = 0x4
/*运行时异常-包含自定义异常*/
const val RUNTIME_ERROR = 0x5
/*无法解析该域名*/
const val UNKOWNHOST_ERROR = 0x6
/*连接网络超时*/
const val SOCKET_TIMEOUT_ERROR = 0x7
/*无网络连接*/
const val SOCKET_ERROR = 0x8

const val ERROR_HTTP_400 = 400
const val ERROR_HTTP_404 = 404
const val ERROR_HTTP_405 = 405
const val ERROR_HTTP_500 = 500