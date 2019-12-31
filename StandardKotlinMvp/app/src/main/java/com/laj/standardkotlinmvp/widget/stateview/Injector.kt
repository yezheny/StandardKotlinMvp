package com.laj.standardkotlinmvp.widget.stateview

import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.laj.standardkotlinmvp.R

/**
 * Desc:
 * Author: hy
 * Date: 2019/5/30 15:40
 */

/**
 * When viewParent is ConstraintLayout
 * Change other view's Constraint to root, if the origin Constraint is parent
 *
 * {
 * Solve the ClassCastException: constraint-layout version 1.1.3
 * java.lang.ClassCastException: android.widget.FrameLayout$LayoutParams cannot be cast to android.support.constraint.ConstraintLayout$LayoutParams
 * at [ConstraintLayout.getTargetWidget]
 * at [ConstraintLayout.setChildrenConstraints]
 * at [ConstraintLayout.updateHierarchy]
 * at [ConstraintLayout.onMeasure]
 *
 * @param viewParent   injectView's parent
 * @param root         wrapper view, replace injectView
 * @param injectViewId injectView's Id
 */
fun changeChildrenConstraints(viewParent: ViewGroup, root: FrameLayout, injectViewId: Int) {
    if (viewParent is ConstraintLayout) {
        val rootId = R.id.root_id
        root.id = rootId
        var i = 0
        val count = viewParent.childCount
        while (i < count) {
            val child = viewParent.getChildAt(i)
            val layoutParams = child.layoutParams as ConstraintLayout.LayoutParams
            if (layoutParams.circleConstraint == injectViewId) {
                layoutParams.circleConstraint = rootId
            } else {
                if (layoutParams.leftToLeft == injectViewId) {
                    layoutParams.leftToLeft = rootId
                } else if (layoutParams.leftToRight == injectViewId) {
                    layoutParams.leftToRight = rootId
                }

                if (layoutParams.rightToLeft == injectViewId) {
                    layoutParams.rightToLeft = rootId
                } else if (layoutParams.rightToRight == injectViewId) {
                    layoutParams.rightToRight = rootId
                }

                if (layoutParams.topToTop == injectViewId) {
                    layoutParams.topToTop = rootId
                } else if (layoutParams.topToBottom == injectViewId) {
                    layoutParams.topToBottom = rootId
                }

                if (layoutParams.bottomToTop == injectViewId) {
                    layoutParams.bottomToTop = rootId
                } else if (layoutParams.bottomToBottom == injectViewId) {
                    layoutParams.bottomToBottom = rootId
                }

                if (layoutParams.baselineToBaseline == injectViewId) {
                    layoutParams.baselineToBaseline = rootId
                }
            }
            i++
        }
    }
}


