package com.laj.standardkotlinmvp.base.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Desc:懒加载Fragment
 * Author: hy
 * Date: 2019/7/15 8:53
 **/

open class LazyFragment : RxFragment() {

    private val fragmentationStateSaveIsInvisibleWhenLeave = "fragmentation_invisible_when_leave"
    private val fragmentationStateSaveCompatReplace = "fragmentation_compat_replace"

    private var mIsSupportVisible: Boolean = false
    private var mNeedDispatch = true
    private var mInvisibleWhenLeave: Boolean = false
    private var mIsFirstVisible = true
    private var mFirstCreateViewCompatReplace = true

    private var mHandler: Handler? = null
    private var mSaveInstanceState: Bundle? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSaveInstanceState = savedInstanceState

        mInvisibleWhenLeave = savedInstanceState?.getBoolean(fragmentationStateSaveIsInvisibleWhenLeave)
                ?: false
        mFirstCreateViewCompatReplace = savedInstanceState?.getBoolean(fragmentationStateSaveCompatReplace)
                ?: true
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (!mFirstCreateViewCompatReplace && tag != null && tag!!.startsWith("android:switcher:")) {
            return
        }

        if (mFirstCreateViewCompatReplace) {
            mFirstCreateViewCompatReplace = false
        }

        if (!mInvisibleWhenLeave && !isHidden && userVisibleHint) {
            if ((parentFragment != null && isFragmentVisible(parentFragment!!))
                    || parentFragment == null) {
                mNeedDispatch = false
                safeDispatchUserVisibleHint(true)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mIsFirstVisible) {
            if (!mIsSupportVisible && !mInvisibleWhenLeave && isFragmentVisible(this)) {
                mNeedDispatch = false
                dispatchSupportVisible(true)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if (mIsSupportVisible && isFragmentVisible(this)) {
            mNeedDispatch = false
            mInvisibleWhenLeave = false
            dispatchSupportVisible(false)
        } else {
            mInvisibleWhenLeave = true
        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isResumed || !isAdded && isVisibleToUser) {
            if (!mIsSupportVisible && isVisibleToUser) {
                safeDispatchUserVisibleHint(true)
            } else if (mIsSupportVisible && !isVisibleToUser) {
                dispatchSupportVisible(false)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && !isResumed) {
            //if fragment is shown but not resumed, ignore...
            mInvisibleWhenLeave = false
            return
        }
        if (hidden) {
            safeDispatchUserVisibleHint(false)
        } else {
            enqueueDispatchVisible()
        }
    }


    private fun safeDispatchUserVisibleHint(visible: Boolean) {
        if (mIsFirstVisible) {
            if (!visible) return
            enqueueDispatchVisible()
        } else {
            dispatchSupportVisible(visible)
        }
    }

    private fun enqueueDispatchVisible() {
        getHandler().post { dispatchSupportVisible(true) }
    }

    private fun dispatchSupportVisible(visible: Boolean) {
        //父Fragment不可见时，直接Return
        //  if (visible && (parentFragment?.isVisible != true)) return
        if (visible) {
            var vis = parentFragment?.isVisible ?: true
            if (!vis) return
        }

        if (mIsSupportVisible == visible) {
            mNeedDispatch = true
            return
        }

        mIsSupportVisible = visible

        if (visible) {
            if (checkAddState()) return
            onSupportVisible()

            if (mIsFirstVisible) {
                mIsFirstVisible = false
                onLazyInitView(mSaveInstanceState)
            }
            dispatchChild(true)
        } else {
            dispatchChild(false)
            onSupportInvisible()
        }
    }

    private fun dispatchChild(visible: Boolean) {
        if (!mNeedDispatch) {
            mNeedDispatch = true
        } else {
            if (checkAddState()) return
            val fragmentManager = childFragmentManager

            //val childFragments = FragmentationMagician.getActiveFragments(fragmentManager)
            val childFragments = fragmentManager.fragments
            if (childFragments != null) {
                for (child in childFragments) {
                    if (child.isHidden && child.userVisibleHint) {
                        dispatchSupportVisible(visible)
                    }
                }
            }
        }
    }


    private fun checkAddState(): Boolean {
        if (!isAdded) {
            mIsSupportVisible = !mIsSupportVisible
            return true
        }
        return false
    }

    private fun isFragmentVisible(fragment: Fragment): Boolean {
        return !fragment.isHidden && fragment.userVisibleHint
    }

    private fun getHandler(): Handler {
        if (mHandler == null) {
            mHandler = Handler(Looper.getMainLooper())
        }
//        mHandler = mHandler?:Handler(Looper.getMainLooper())
        return mHandler!!
    }

    /**
     * Fragment对用户可见时回调
     */
    open fun onSupportVisible() {

    }

    /**
     * Fragment对用户不可见时回调
     */
    open fun onSupportInvisible() {

    }

    /**
     * 懒加载回调
     */
    open fun onLazyInitView(savedInstanceState: Bundle?) {

    }

    override fun onDestroyView() {
        mIsFirstVisible = true
        super.onDestroyView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(fragmentationStateSaveIsInvisibleWhenLeave, mInvisibleWhenLeave)
        outState.putBoolean(fragmentationStateSaveCompatReplace, mFirstCreateViewCompatReplace)
    }
}