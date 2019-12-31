package com.laj.standardkotlinmvp.dagger.component

import com.laj.standardkotlinmvp.base.activity.BaseActivity

import dagger.Subcomponent
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * Create by aragon
 * Date: 2018/11/27 18:55
 * description:
 */
@Subcomponent(modules = [AndroidInjectionModule::class])
interface BaseActivityComponent : AndroidInjector<BaseActivity<*, *>> {

    /**
     * 每一个继承BaseActivity的Activity，都共享同一个SubComponent
     */
    @Subcomponent.Builder
    abstract class BaseBuilder : AndroidInjector.Builder<BaseActivity<*, *>>()

}
