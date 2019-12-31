package com.yryz.module_core.rx

import com.laj.standardkotlinmvp.base.IBaseView
import io.reactivex.disposables.Disposable

/**
 * Desc:
 * Author: hy
 * Date: 2019/4/3 9:29
 **/
abstract class RxProgressObserver<T>(mView: IBaseView?, loadType:Int) :RxErrorObserver<T>(mView,loadType) {

    private var mIsErrorOccurred = false

    override fun onSubscribe(d: Disposable) {
        mView?.showLoading()
    }

    override fun onComplete() {
        if (!mIsErrorOccurred) {
            mView?.dismissLoading()
        }
    }

    override fun onError(e: Throwable) {
        super.onError(e)
        mIsErrorOccurred = true

    }
}