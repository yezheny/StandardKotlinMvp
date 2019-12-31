package com.laj.standardkotlinmvp.base.activity

import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.blankj.utilcode.util.BarUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.laj.standardkotlinmvp.R
import com.laj.standardkotlinmvp.base.IBaseView
import com.laj.standardkotlinmvp.base.presenter.BasePresenter
import com.laj.standardkotlinmvp.common.FIRST_LOAD
import com.laj.standardkotlinmvp.common.LOAD_MORE
import com.laj.standardkotlinmvp.common.REFRESH
import com.laj.standardkotlinmvp.common.eventbus.EventItem
import com.laj.standardkotlinmvp.common.exception.BaseException
import com.laj.standardkotlinmvp.common.extensions.contentView
import com.laj.standardkotlinmvp.common.extensions.find
import com.laj.standardkotlinmvp.common.extensions.findColor
import com.laj.standardkotlinmvp.widget.stateview.StateView
import com.trello.rxlifecycle2.LifecycleTransformer
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

/**
 * Desc:
 * Author: hy
 * Date: 2019/7/15 8:39
 **/
abstract class BaseActivity<V: IBaseView,P: BasePresenter<V>>: BaseInjectActivity(), IBaseView/*, HasSupportFragmentInjector*/ {

    @Inject
    lateinit var mPresenter: P
//    @Inject
//    lateinit var mFragmentInjector: DispatchingAndroidInjector<Fragment>

    private var mIsFirstEnter = true
    private lateinit var mStateView: StateView
    protected var mViewProvider: ViewProvider? = null

    override fun onCreate(savedInstanceState: Bundle?) {
//        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(getLayout())
//        mBinder = ButterKnife.bind(this)
//        mStateView = StateView.inject(this, true)
        mStateView = StateView.inject(this,true)
        mPresenter.attachView(getThis())
        EventBus.getDefault().register(this)
        setBack()
        initStatusBar()
        initData()
        initView()
        loadData()
        mIsFirstEnter = false
    }

    protected open fun initStatusBar() {
        BarUtils.setStatusBarColor(this, Color.WHITE)
        contentView?.let {
            BarUtils.addMarginTopEqualStatusBarHeight(it)
        }
        BarUtils.setStatusBarLightMode(this, true)
    }

    open fun loadData() {

    }

    override fun showEmpty(type: Int,text:String) {
        /*0 无数据 1 无内容  3 无评价*/
        var showEmpty = mStateView.showEmpty()
        var ivEmpty = showEmpty.findViewById<ImageView>(R.id.empty_iv_type_of)
        var tvEmpty = showEmpty.findViewById<TextView>(R.id.base_empty_tv_text)
        when(type) {
            0 -> {
                ivEmpty.setImageResource(R.mipmap.wushuju)
                tvEmpty.text = "暂无相关数据"
            }
            1 -> {
                ivEmpty.setImageResource(R.mipmap.wushuju)
                tvEmpty.text = "暂无内容~"
            }
            3 -> {
                ivEmpty.setImageResource(R.mipmap.wushuju)
                tvEmpty.text = "暂无评论"
            }
            4 -> {
                ivEmpty.setImageResource(R.mipmap.wushuju)
                tvEmpty.text = text
                showEmpty.setBackgroundColor(findColor(R.color.COLOR_EFEFEF))
            }
        }
    }

    fun retryClick() {

    }

    override fun <K> showResponse(k: K, msg: Int) {

    }

    override fun showCommonError(exception: BaseException, loadType: Int) {
        mViewProvider?.apply {
            val adapters = adapter()
            val refreshLayout = refreshLayout()
            when (loadType) {
                FIRST_LOAD -> {
                    showRetry(exception.error_msg, exception.error_code)
                }
                REFRESH -> {
                    if (refreshLayout.isRefreshing) {
                        refreshLayout.isRefreshing = false
                    }
                }
                LOAD_MORE -> {
                    if (adapters.isNotEmpty()) {
                        for (adapter in adapters) {
                            if (adapter.isLoading) {
                                adapter.loadMoreFail()
                            }
                        }
                    }
                }
            }
        }?:run {
            if (loadType == FIRST_LOAD ) {
                showRetry(exception.error_msg, exception.error_code)
            }
        }
    }

    fun showRetry(msg: String, code: String) {
        mStateView.post {
            mStateView.showRetry().apply {
                val iv = find<ImageView>(R.id.base_retry_iv_img)
                val tv = find<TextView>(R.id.base_tv_retry_text)
                tv.text = msg
            }

            mStateView.setOnRetryClickListener {
                loadData()
            }
        }

    }

    override fun showError(e: Throwable, loadType: Int) {

    }

    override fun showError(e: Throwable) {

    }
    //
//    //设置标题
    fun setTitle(title: String) {
        val titleText = find<TextView>(R.id.tv_title)
        titleText.text = title
    }

    fun setBack() {
        try {
            val rlBack = find<RelativeLayout>(R.id.rl_back)
            rlBack.setOnClickListener{ finish() }
        } catch (e: Exception) {
        }
    }

    override fun showLoading() {
        if (mIsFirstEnter) {
            mStateView.showLoading()
        }
    }

    override fun dismissLoading() {
        if (!mStateView.isEmptyShowing) {
            mStateView.showContent()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    interface ViewProvider {
        fun adapter(): Array<BaseQuickAdapter<*, *>>
        fun refreshLayout(): SwipeRefreshLayout
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun handleEvent(eventItem: EventItem) {

    }

    @LayoutRes
    protected abstract fun getLayout(): Int

    protected abstract fun initData()

    protected abstract fun initView()

    protected abstract fun getThis(): V

    override fun <T> bindToLife(): LifecycleTransformer<T> = this.bindToLifecycle()

//    override fun supportFragmentInjector(): AndroidInjector<Fragment> = mFragmentInjector

}