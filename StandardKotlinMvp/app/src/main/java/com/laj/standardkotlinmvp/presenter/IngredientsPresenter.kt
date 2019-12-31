package com.laj.standardkotlinmvp.presenter

import com.laj.standardkotlinmvp.base.presenter.BasePresenter
import com.laj.standardkotlinmvp.common.extensions.rxProgressSubscribe
import com.laj.standardkotlinmvp.common.extensions.transformData
import com.laj.standardkotlinmvp.net.ApiService
import com.laj.standardkotlinmvp.presenter.views.IIngredientsView
import javax.inject.Inject

/**
 * Desc:食材presenter
 * Author: hy
 * Date: 2019/4/3 15:05
 **/
class IngredientsPresenter @Inject constructor(var apiService: ApiService)
    : BasePresenter<IIngredientsView>() {

    fun getMoreNutritions(params: HashMap<String, Any>, loadType: Int = -1) {
        apiService.getMoreNutritions(params)
                .compose(mRealView?.transformData())
                .rxProgressSubscribe(mRealView, loadType) {
                    mRealView?.showResponse(it.get(), loadType)
                }
    }
}
