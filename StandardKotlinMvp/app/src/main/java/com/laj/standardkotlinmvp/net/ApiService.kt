package com.laj.standardkotlinmvp.net

import com.laj.standardkotlinmvp.model.BaseModel
import com.laj.standardkotlinmvp.model.NutritionalInfoModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * Desc:
 * Author: hy
 * Date: 2019/3/28 9:39
 **/
interface ApiService {


    companion object {

        const val BASE_URL = "https://api-dev.lajsf.com/gateway/"
    }

    //食材详情，更多营养成分
    @GET("nutrition/v1.0/pb/food-comps/action/selectByFoodKid")
    fun getMoreNutritions(@QueryMap params: HashMap<String, Any>): Observable<BaseModel<NutritionalInfoModel>>
}