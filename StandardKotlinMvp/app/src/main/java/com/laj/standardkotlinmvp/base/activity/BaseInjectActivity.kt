package com.laj.standardkotlinmvp.base.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

/**
 * Desc:dagger依赖注入基类，没有特殊需求尽量都继承此Activity
 * Author: hy
 * Date: 2019/7/15 19:32
 **/
open class BaseInjectActivity :RxAppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }
    override fun supportFragmentInjector(): AndroidInjector<Fragment> =mFragmentInjector
}