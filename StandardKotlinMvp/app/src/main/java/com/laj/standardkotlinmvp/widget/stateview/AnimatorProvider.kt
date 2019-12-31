package com.laj.standardkotlinmvp.widget.stateview
import android.animation.Animator
import android.view.View

/**
 * Desc:
 * Author: hy
 * Date: 2019/5/30 15:39
 */
interface AnimatorProvider {
    fun showAnimation(view: View): Animator?

    fun hideAnimation(view: View): Animator?
}
