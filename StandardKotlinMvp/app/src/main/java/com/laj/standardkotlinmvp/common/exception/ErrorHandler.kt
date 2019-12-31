package com.laj.standardkotlinmvp.common.exception


//import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Desc: 异常信息处理
 * Author: hy
 * Date: 2019/4/2 16:41
 **/

fun handleError(e: Throwable): BaseException {
    return BaseException().apply {
        error_enum = when (e) {
            is UnknownHostException -> ErrorEnums.ERROR_SOCKET
            is SocketTimeoutException -> ErrorEnums.ERROR_SOCKET_TIMEOUT
            /*is TokenIllegalStateException -> {
                EventBus.getDefault().post(EventBusTokenLose())
                ErrorEnums.ERROR_TOKEN_ILLEGAL
            }*/
            else -> ErrorEnums.ERROR_UNKNOWN
        }
        error_msg = error_enum!!.msg
        error_code = error_enum!!.code.toString()
    }
}

fun showErrorMsg(e: BaseException) {
//    LogUtils.e(e.message+"\n"+e.mErrorMsg)
//    ToastUtils.showShort(e.mErrorMsg)
}

