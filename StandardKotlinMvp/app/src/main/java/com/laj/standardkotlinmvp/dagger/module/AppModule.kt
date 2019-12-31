package com.laj.standardkotlinmvp.dagger.module

import android.content.Context
import com.google.gson.Gson
import com.laj.standardkotlinmvp.ui.LAJApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Desc:
 * Author: hy
 * Date: 2019/3/27 17:21
 **/

@Module
class AppModule(private val application: LAJApplication){

    @Singleton
    @Provides
    fun provideGson():Gson = Gson()

    @Singleton
    @Provides
    fun provideContext():Context = application

}