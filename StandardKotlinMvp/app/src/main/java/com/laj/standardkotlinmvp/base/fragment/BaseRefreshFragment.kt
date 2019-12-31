package com.laj.standardkotlinmvp.base.fragment

import android.view.ViewStub
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.laj.standardkotlinmvp.R
import com.laj.standardkotlinmvp.base.IBaseView
import com.laj.standardkotlinmvp.base.fragment.BaseFragment
import com.laj.standardkotlinmvp.base.presenter.BasePresenter
import com.laj.standardkotlinmvp.common.extensions.find
import com.laj.standardkotlinmvp.common.extensions.gridLayoutManager
import com.laj.standardkotlinmvp.common.extensions.verticalLinearLayoutManager
import com.laj.standardkotlinmvp.widget.RvLoadMoreView
import com.laj.standardkotlinmvp.widget.refreshrv.SwipeRefreshRecyclerView

/**
 * Desc:
 * Author: hy
 * Date: 2019/4/1 16:41
 **/

abstract class BaseRefreshFragment<V : IBaseView, P : BasePresenter<V>> : BaseFragment<V, P>(), BaseFragment.ViewProvider {

    protected lateinit var mRefreshRoot: SwipeRefreshRecyclerView
    protected lateinit var mRefreshLayout: SwipeRefreshLayout
    protected lateinit var mRecyclerView: RecyclerView
    protected lateinit var mViewStub: ViewStub

    protected var mIsUseGridLayoutManager = false

    protected var mCurrentPage = 1
    protected var mPageSize = 10

    protected var mCanLoadMore: Boolean = false

    private var mAdapter: BaseQuickAdapter<*, *>? = null


    override fun getLayoutRes(): Int = R.layout.base_refresh_layout

    override fun initView() {
        mRootView.apply {
            mRefreshRoot = find(R.id.base_refresh_rv)
            mViewStub = find(R.id.base_refresh_vs)
        }

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

//            emptyView = inflate(R.layout.base_empty_view)
//            //解决 emptyView 显示不全
//            mRootView.post {
//                emptyView.layoutParams = ViewGroup.LayoutParams(mRootView.width, mRootView.height)
//
//            }

            setLoadMoreView(RvLoadMoreView())
            isUseEmpty(false)
        }
        mRefreshRoot.setOnRefreshListener {
            onRefreshData()
        }

        mViewProvider = this
    }

    override fun adapter(): Array<BaseQuickAdapter<*, *>> = arrayOf(getAdapter())

    override fun refreshLayout(): SwipeRefreshLayout = mRefreshLayout


    protected open fun getSpanCount(): Int = 2

    protected abstract fun onRefreshData()

    protected abstract fun onLoadMoreData()

    protected abstract fun getAdapter(): BaseQuickAdapter<*, *>

}