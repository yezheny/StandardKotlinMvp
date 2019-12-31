package com.laj.standardkotlinmvp.widget

import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.laj.standardkotlinmvp.R


/**
 * Desc:
 * Author: hy
 * Date: 2019/6/16 14:34
 **/
class RvLoadMoreView : LoadMoreView(){

    override fun getLayoutId(): Int {
        return R.layout.my_brvah_quick_view_load_more
    }

    override fun getLoadingViewId(): Int {
        return R.id.load_more_loading_view
    }

    override fun getLoadFailViewId(): Int {
        return R.id.load_more_load_fail_view
    }

    override fun getLoadEndViewId(): Int {
        return R.id.load_more_load_end_view
    }
}