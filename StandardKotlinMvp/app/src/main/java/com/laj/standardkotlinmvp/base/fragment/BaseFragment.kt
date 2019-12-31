package com.laj.standardkotlinmvp.base.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.laj.standardkotlinmvp.R
import com.laj.standardkotlinmvp.base.IBaseView
import com.laj.standardkotlinmvp.base.presenter.BasePresenter
import com.laj.standardkotlinmvp.common.FIRST_LOAD
import com.laj.standardkotlinmvp.common.LOAD_MORE
import com.laj.standardkotlinmvp.common.REFRESH
import com.laj.standardkotlinmvp.common.eventbus.EventItem
import com.laj.standardkotlinmvp.common.exception.BaseException
import com.laj.standardkotlinmvp.common.extensions.find
import com.laj.standardkotlinmvp.widget.stateview.StateView
import com.trello.rxlifecycle2.LifecycleTransformer
import dagger.android.support.AndroidSupportInjection
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * Desc:懒加载Fragment
 * Author: hy
 * Date: 2019/7/15 8:40
 **/
abstract class BaseFragment<V : IBaseView, P : BasePresenter<V>> : LazyFragment(), IBaseView {

    @Inject
    lateinit var mPresenter: P
    private lateinit var mStateView: StateView
    protected var mViewProvider: ViewProvider? = null

    private var mIsFirstEnter = true

    protected var mContext: Context? = null
    protected lateinit var mRootView: View

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        val weakReference = WeakReference<Context>(context)
        mContext = weakReference.get()
        mPresenter.attachView(getThis())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        EventBus.getDefault().register(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mRootView = inflater.inflate(getLayoutRes(), container, false)
//        mBinder = ButterKnife.bind(this, mRootView)
        mStateView = StateView.inject(mRootView)
        initData()//初始化数据，例如通过bundle传递的数据
        return mRootView

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        loadData()
        mIsFirstEnter = false
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    open fun handleEvent(eventItem: EventItem) {

    }

    protected open fun loadData() {

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

    fun showContent() {
        mStateView.showContent()
    }

    override fun showError(e: Throwable, loadType: Int) {

    }

    override fun showError(e: Throwable) {
    }

    override fun showEmpty(type: Int,text:String) {
        mContext?.let {
            /*0 无数据 1 无内容  3 无评价*/
            val showEmpty = mStateView.showEmpty()
            val ivEmpty = showEmpty.findViewById<ImageView>(R.id.empty_iv_type_of)
            val tvEmpty = showEmpty.findViewById<TextView>(R.id.base_empty_tv_text)
            when (type) {
                0 -> {
                    ivEmpty.setImageResource(R.mipmap.wushuju)
                    tvEmpty.text = "暂无相关数据"
                }
                1 -> {
                    ivEmpty.setImageResource(R.mipmap.wushuju)
                    tvEmpty.text = "暂无内容~"
                }

                3 -> {
                    /*ivEmpty.setImageResource(R.mipmap.wupinglun)
                    tvEmpty.text = "暂无评论"*/
                }
                4 -> {
                    ivEmpty.setImageResource(R.mipmap.wangluoyichang)
                    tvEmpty.text = text
                }
            }
        }
    }

    fun retryClick(msg: String, code: String) {

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

    //    //设置标题
    fun setTitle(title: String) {
        val titleText = find<TextView>(R.id.tv_title)
        titleText.text = title
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    override fun <T> bindToLife(): LifecycleTransformer<T> = bindToLifecycle()

    interface ViewProvider {
        fun adapter(): Array<BaseQuickAdapter<*, *>>
        fun refreshLayout(): SwipeRefreshLayout
    }

    @LayoutRes
    abstract fun getLayoutRes(): Int

    abstract fun initData()
    abstract fun initView()
    protected abstract fun getThis(): V

}