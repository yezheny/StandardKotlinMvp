package com.laj.standardkotlinmvp.ui

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject
import android.widget.Toast
import com.blankj.utilcode.util.LogUtils
import com.facebook.fresco.helper.Phoenix
import com.laj.standardkotlinmvp.dagger.component.DaggerAppComponent
import com.laj.standardkotlinmvp.dagger.module.AppModule
import com.laj.standardkotlinmvp.dagger.module.HttpModule
import com.laj.standardkotlinmvp.net.ApiService
import java.util.*
import kotlin.properties.Delegates


/**
 * Desc:
 * Author: hy
 * Date: 2019/4/1 9:33
 **/
class LAJApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var mDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = mDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        Phoenix.init(this)
        DaggerAppComponent.builder()
                .httpModule(HttpModule())
                .appModule(AppModule(this))
                .build()
                .inject(this)
        LogUtils.getConfig().globalTag = "H_TAG"
    }



    companion object {
       var INSTANCE: LAJApplication by Delegates.notNull()
    }

}