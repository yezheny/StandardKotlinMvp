package com.laj.standardkotlinmvp.module

import com.laj.standardkotlinmvp.dagger.component.BaseActivityComponent
import com.laj.standardkotlinmvp.ui.activity.MoreNutritionsActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Create by aragon
 * Date: 2018/11/27 18:54
 * description:
 */
@Module(subcomponents = [BaseActivityComponent::class])
abstract class AllActivityModules {

    @ContributesAndroidInjector
    abstract fun contributeMoreNutritionsActivityInjector(): MoreNutritionsActivity

}
