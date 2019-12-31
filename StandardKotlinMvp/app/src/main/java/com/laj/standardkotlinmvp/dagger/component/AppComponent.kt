package com.laj.standardkotlinmvp.dagger.component

import com.laj.standardkotlinmvp.dagger.module.AllFragmentModules
import com.laj.standardkotlinmvp.dagger.module.AppModule
import com.laj.standardkotlinmvp.dagger.module.HttpModule
import com.laj.standardkotlinmvp.module.AllActivityModules
import com.laj.standardkotlinmvp.ui.LAJApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

//import dagger.Component
//import dagger.Module
//import javax.inject.Singleton

/**
 * Desc:
 * Author: hy
 * Date: 2019/3/27 17:19
 **/

@Singleton
@Component(modules = [
    AppModule::class,
    HttpModule::class,
    AndroidInjectionModule::class,
    AllFragmentModules::class,
    AllActivityModules::class,
    AndroidSupportInjectionModule::class])
interface AppComponent {
    fun inject(application: LAJApplication)
}