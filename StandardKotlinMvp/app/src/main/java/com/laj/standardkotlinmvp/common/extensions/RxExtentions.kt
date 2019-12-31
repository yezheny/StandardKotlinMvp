package com.laj.standardkotlinmvp.common.extensions

import com.laj.standardkotlinmvp.base.IBaseView
import com.laj.standardkotlinmvp.common.exception.BaseException
import com.laj.standardkotlinmvp.common.exception.handleError
import com.laj.standardkotlinmvp.model.BaseModel
import com.laj.standardkotlinmvp.model.Optional
import com.yryz.module_core.rx.RxCommonObserver
import com.yryz.module_core.rx.RxProgressObserver
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Desc: RxJava相关拓展函数
 * Author: hy
 * Date: 2019/4/3 9:56
 **/

fun <T> IBaseView.applySchedulersWithLifecycle(): ObservableTransformer<T, T> = ObservableTransformer {
    it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .compose(bindToLife())
}

fun <T> applySchedulers(): ObservableTransformer<T, T> = ObservableTransformer {
    it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> applyIOSchedulers(): ObservableTransformer<T, T> = ObservableTransformer {
    it.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
}

fun <T> applyMainSchedulers(): ObservableTransformer<T, T> = ObservableTransformer {
    it.subscribeOn(AndroidSchedulers.mainThread())
            .observeOn(AndroidSchedulers.mainThread())
}

fun <T> IBaseView.transformData(): ObservableTransformer<BaseModel<T>, Optional<T>> {
    return ObservableTransformer {
//        it.doOnError(ErrorConsumer())
              it  .flatMap { baseModel ->
                    if (baseModel.code == "200") {
                        return@flatMap generateNullableData(Optional(baseModel.data))
                    } else {
                        val exception = with(BaseException()) {
                            error_code = baseModel.code
                            error_msg = baseModel.msg
                            this@with
                        }
                        return@flatMap Observable.error<Optional<T>>(exception)
                    }
                }
                .compose(applySchedulersWithLifecycle())
    }
}

fun <T> transformData(): ObservableTransformer<BaseModel<T>, Optional<T>> {
    return ObservableTransformer {
        it.flatMap { baseModel ->
            if (baseModel.code == "200") {
                return@flatMap generateNullableData(Optional(baseModel.data))
            } else {
                val exception = with(BaseException()) {
                    error_code = baseModel.code
                    error_msg = baseModel.msg
                    this@with
                }
                return@flatMap Observable.error<Optional<T>>(exception)
            }
        }
    }
}

/**
 * 后台data可能返回为Null的情况，RxJava onNext不接受null值，所以包装一层
 */
fun <T> generateNullableData(t: Optional<T>): Observable<Optional<T>> {
    return Observable.create {
        with(it) {
            try {
                onNext(t)
                onComplete()
            } catch (e: Exception) {
                onError(e)
            }
        }
    }
}


inline fun <T> Observable<T>.rxSubscribe(crossinline data: (T) -> Unit, crossinline error: (e: Throwable) -> Unit) {
    subscribe(object : RxCommonObserver<T>() {
        override fun onNext(t: T) {
            try {
                //T是经过Optional包装过的参数，可能为空，
                //若不经过try-catch处理，下游直接使用其值
                //会导致应用崩溃
                data(t)
            } catch (e: Exception) {
                onError(e)
            }
        }

        override fun onError(e: Throwable) {
            super.onError(e)
            error(e as? BaseException?: handleError(e))
        }
    })
}

inline fun <T> Observable<T>.rxSubscribe(crossinline data: (T) -> Unit) {
    subscribe(object : RxCommonObserver<T>() {
        override fun onNext(t: T) {
            try {
                //T是经过Optional包装过的参数，可能为空，
                //若不经过try-catch处理，下游直接使用其值
                //会导致应用崩溃
                data(t)
            } catch (e: Exception) {
                onError(e)
            }
        }
    })
}


inline fun <T> Observable<T>.rxProgressSubscribe(view: IBaseView?, loadType: Int, crossinline data: (T) -> Unit) {
    subscribe(object : RxProgressObserver<T>(view, loadType) {
        override fun onNext(t: T) {
            try {
                //T是经过Optional包装过的参数，可能为空，
                //若不经过try-catch处理，下游直接使用其值
                //会导致应用崩溃
                data(t)
            } catch (e: Exception) {
                onError(e)
            }
        }
    })
}