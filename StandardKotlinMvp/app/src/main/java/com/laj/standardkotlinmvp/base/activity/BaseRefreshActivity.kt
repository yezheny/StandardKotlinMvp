package com.laj.standardkotlinmvp.base.activity

import android.view.ViewStub
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.laj.standardkotlinmvp.R
import com.laj.standardkotlinmvp.base.IBaseView
import com.laj.standardkotlinmvp.base.presenter.BasePresenter
import com.laj.standardkotlinmvp.common.extensions.find
import com.laj.standardkotlinmvp.common.extensions.gridLayoutManager
import com.laj.standardkotlinmvp.common.extensions.inflate
import com.laj.standardkotlinmvp.common.extensions.verticalLinearLayoutManager
import com.laj.standardkotlinmvp.widget.RvLoadMoreView
import com.laj.standardkotlinmvp.widget.refreshrv.SwipeRefreshRecyclerView

/**
 * created by yezhengyu on 2019/6/16 17:52
 */
abstract class BaseRefreshActivity<V : IBaseView, P : BasePresenter<V>> : BaseActivity<V, P>(), BaseActivity.ViewProvider {

    protected lateinit var mRefreshRoot: SwipeRefreshRecyclerView
    protected lateinit var mRefreshLayout: SwipeRefreshLayout
    protected lateinit var mRecyclerView: RecyclerView
    protected lateinit var mViewStub: ViewStub
    protected var mIsUseGridLayoutManager = false

    protected var mCurrentPage = 1

    protected var mCanLoadMore: Boolean = false

    private var mAdapter: BaseQuickAdapter<*, *>? = null

    override fun getLayout(): Int = R.layout.activity_base_refresh_layout


    override fun initData() {
    }

    override fun initView() {
        mRefreshRoot = find(R.id.base_refresh_rv)
        mViewStub = find(R.id.base_refresh_vs)

        mRefreshRoot.apply {
            mRecyclerView = getRecyclerView()
            mRefreshLayout = getSwipeRefreshLayout()
        }
        mAdapter = getAdapter()

        mRecyclerView.layoutManager =
                if (mIsUseGridLayoutManager)
                    gridLayoutManager(getSpanCount())
                else
                    verticalLinearLayoutManager()

        mAdapter?.apply {
            mRecyclerView.adapter = this

            setOnLoadMoreListener({
                if (mCanLoadMore) onLoadMoreData()
            }, mRecyclerView)
            emptyView = inflate(R.layout.base_empty_view)
            setLoadMoreView(RvLoadMoreView())
            isUseEmpty(false)
        }
        mRefreshLayout.setOnRefreshListener {
            onRefreshData()
        }
        mViewProvider = this

    }

    //设置标题
    fun setTitleRefresh(title: String) {
        setTitle(title)
    }

    @Deprecated(message = "This method exists some bugs", replaceWith = ReplaceWith("BaseActivity #showEmpty()"))
    protected fun showRvEmpty(type: EmptyViewType? = null) {

        mAdapter?.apply {
            isUseEmpty(true)
            val emptyDesc = emptyView.find<TextView>(R.id.base_empty_tv_text)
            when (type) {
                EmptyViewType.EMPTY_ADDRESS -> {
                    emptyDesc.text = type.desc
                }
                else -> {
                }
            }
        }
    }

    protected open fun getSpanCount(): Int = 2

    override fun adapter(): Array<BaseQuickAdapter<*, *>> = arrayOf(getAdapter())

    override fun refreshLayout(): SwipeRefreshLayout = mRefreshLayout

    protected abstract fun onRefreshData()

    protected abstract fun onLoadMoreData()

    protected abstract fun getAdapter(): BaseQuickAdapter<*, *>


    enum class EmptyViewType(var desc: String) {
        EMPTY_ADDRESS("收获地址为空")
    }

}