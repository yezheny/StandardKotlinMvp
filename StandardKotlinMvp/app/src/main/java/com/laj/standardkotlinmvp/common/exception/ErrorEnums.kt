package com.laj.standardkotlinmvp.common.exception

/**
 * Desc:
 * Author: hy
 * Date: 2019/5/22 14:44
 **/

enum class ErrorEnums(val code:Int, val msg:String) {

    ERROR_UNKNOWN(-1,"服务器数据异常"),
    ERROR_HTTP(0x1,"HttpException"),
    ERROR_SOCKET(0x2,"网络不可用"),
    ERROR_SOCKET_TIMEOUT(0x3,"网络超时"),
    ERROR_TOKEN_ILLEGAL(101,"TOKEN过期"),
    ERROR_HTTP_400(400,"BAD_REQUEST"),
    ERROR_HTTP_404(404,"服务器链接异常"),
    ERROR_HTTP_500(500,"服务器错误"),
}