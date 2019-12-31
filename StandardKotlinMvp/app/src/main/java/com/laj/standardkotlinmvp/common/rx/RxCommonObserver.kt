package com.yryz.module_core.rx

import com.blankj.utilcode.util.LogUtils
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * Desc:
 * Author: hy
 * Date: 2019/4/2 14:34
 **/
abstract class RxCommonObserver<T> : Observer<T> {

    override fun onSubscribe(d: Disposable) {

    }

    override fun onComplete() {

    }

    override fun onError(e: Throwable) {
        LogUtils.e("onError ${e.printStackTrace()}")
    }

}