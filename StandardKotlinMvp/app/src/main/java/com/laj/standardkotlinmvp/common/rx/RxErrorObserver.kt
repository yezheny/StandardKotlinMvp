package com.yryz.module_core.rx

import com.blankj.utilcode.util.LogUtils
import com.laj.standardkotlinmvp.base.IBaseView
import com.laj.standardkotlinmvp.common.exception.BaseException
import com.laj.standardkotlinmvp.common.exception.handleError
import com.laj.standardkotlinmvp.common.exception.showErrorMsg
import io.reactivex.Observer

/**
 * Desc:
 * Author: hy
 * Date: 2019/4/2 14:34
 **/
abstract class RxErrorObserver<T>(protected val mView: IBaseView?, private var mLoadType: Int = 1) : Observer<T> {
    override fun onError(e: Throwable) {
        val baseException: BaseException =
                e as? BaseException?: handleError(e)
        showErrorMsg(baseException)
//        mView?.dismissLoading()
        mView?.showCommonError(baseException, mLoadType)
    }

}