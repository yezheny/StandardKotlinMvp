package com.laj.standardkotlinmvp.base

import com.laj.standardkotlinmvp.common.exception.BaseException
import com.trello.rxlifecycle2.LifecycleTransformer

/**
 * Desc:
 * Author: hy
 * Date: 2019/7/15 8:39
 **/
interface IBaseView {

    fun showLoading()

    fun dismissLoading()

    fun showError(e:Throwable)

    fun showError(e:Throwable, loadType:Int)

    fun showCommonError(exception: BaseException, loadType:Int)

    fun showEmpty(type:Int = -1,text:String = "")

    fun <K>showResponse(k: K, msg: Int)

    fun <T> bindToLife(): LifecycleTransformer<T>

//    fun getContext(): Context

}