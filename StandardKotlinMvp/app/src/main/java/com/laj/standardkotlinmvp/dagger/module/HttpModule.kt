package com.laj.standardkotlinmvp.dagger.module

import com.blankj.utilcode.util.LogUtils
import com.google.gson.Gson
import com.laj.standardkotlinmvp.BuildConfig
import com.laj.standardkotlinmvp.net.ApiService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Desc:
 * Author: hy
 * Date: 2019/3/27 17:24
 **/
@Module
class HttpModule {
//    @Singleton
//    @Provides
//    fun provideLoggingInterceptor() =
//            HttpLoggingInterceptor().apply {
//                level = if (BuildConfig.DEBUG) {
//                    HttpLoggingInterceptor.Level.BODY
//                } else {
//                    HttpLoggingInterceptor.Level.NONE
//                }
//            }
//
//    @Singleton
//    @Provides
//    fun provideOkhttp(interceptor: HttpLoggingInterceptor, gson: Gson):OkHttpClient =
//            OkHttpClient.Builder()
//
//                    .addInterceptor(interceptor)
//                    .build()

    @Singleton
    @Provides
    fun provideOkhttp(gson: Gson):OkHttpClient =
            OkHttpClient.Builder()
                    .addInterceptor {
                        val request = it.request()
                        val newRequestBuilder = request.newBuilder()
                        /*UserUtils.getUserInfo()?.apply {
                            newRequestBuilder.addHeader("accessToken",accessToken?:"")
                        }*/
                        val newRequest = newRequestBuilder.build()
                        val response = it.proceed(newRequest)
                        val method = newRequest.method()
                        val startTime = System.currentTimeMillis()
                        val endTime = System.currentTimeMillis()
                        val duration = endTime - startTime
                        val mediaType = response.body()!!.contentType()
                        val content = response.body()!!.string()
                        val httpStatus = response.code()
                        if (BuildConfig.DEBUG) {
//                            LogUtils.json("http_module", content)
                            val logSB = "-------start:$method|" +
                                    request.toString() + "\n|" +
//                                    (if (method.equals("POST", ignoreCase = true)) "post参数{$postBodyString}\n|" else "") +
                                    "httpCode=" + httpStatus + ";Response:" + content + "\n|" +
                                    "----------End:" + duration + "毫秒----------"
                            LogUtils.json(logSB)

                        }
                        return@addInterceptor response.newBuilder()
                                .body(ResponseBody.create(mediaType, content))
                                .build()
                    }
                    .connectTimeout(5,TimeUnit.SECONDS)
                    .readTimeout(5L,TimeUnit.SECONDS)
                    .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit =
            Retrofit.Builder()
                    .client(okHttpClient)
                    .baseUrl(ApiService.BASE_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService =
            retrofit.create(ApiService::class.java)

}