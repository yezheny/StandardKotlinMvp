package com.laj.standardkotlinmvp.widget.refreshrv

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.laj.standardkotlinmvp.R

/**
 * Desc:简易刷新recyclerView
 * Author: hy
 * Date: 2019/7/17 13:27
 **/
class SwipeRefreshRecyclerView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mRv: RecyclerView
    private lateinit var mRetryText: TextView
    private lateinit var mRootView: ConstraintLayout
    private lateinit var mSwipeRefreshLayout: SwipeRefreshLayout

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.swipe_refresh_rv_layout, this, true)
        initView(view)
    }


    private var mRefresh: (() -> Unit)? = null

//
//    override fun onFinishInflate() {
//        super.onFinishInflate()
//        initView()
//    }

    private fun initView(view: View) {
        mRv = view.findViewById(R.id.refresh_rv)
        mRootView = view.findViewById(R.id.refresh_rv_root)
        mRetryText = view.findViewById(R.id.refresh_rv_tv_retry)
        mSwipeRefreshLayout = view.findViewById(R.id.refresh_rv_swipe_refresh)

        mSwipeRefreshLayout.setOnRefreshListener {
            mRefresh?.invoke()
        }
//        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(ContextCompat.getColor(context, R.color.COLOR_12C254))
//        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(context, R.color.COLOR_12C254))
        mSwipeRefreshLayout.isRefreshing = false
    }

    fun setOnRefreshListener(refresh: () -> Unit) {
        mRefresh = refresh
    }

    /**
     * 显示内容
     */
    fun showContent() {
        mRv.visibility = View.VISIBLE
        mSwipeRefreshLayout.isRefreshing = false
    }

    /**
     * 启动刷新
     */
    fun startRefresh() {
        mSwipeRefreshLayout.isRefreshing = true
    }

    fun getRecyclerView() = mRv

    fun getSwipeRefreshLayout() = mSwipeRefreshLayout


}