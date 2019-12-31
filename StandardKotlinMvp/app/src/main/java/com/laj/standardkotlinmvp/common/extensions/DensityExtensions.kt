package com.laj.standardkotlinmvp.common.extensions

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

/**
 * Desc:
 * Author: hy
 * Date: 2019/4/8 14:54
 **/

private val displayMetrics = Resources.getSystem().displayMetrics

val Float.dp: Float
    @JvmName("dp2px")
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, displayMetrics)

val Int.dp: Int
    @JvmName("dp2px")
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), displayMetrics).toInt()

val Float.sp: Float
    @JvmName("sp2px")
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this, displayMetrics)

val Int.sp: Int
    @JvmName("sp2px")
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, this.toFloat(), displayMetrics).toInt()

val Number.px: Number
    get() = this

val Number.px2dp: Int
    @JvmName("px2dp")
    get() = (this.toFloat() / displayMetrics.density).toInt()

val Number.px2sp: Int
    @JvmName("px2sp")
    get() = (this.toFloat() / displayMetrics.scaledDensity).toInt()

val SCREEN_WIDTH: Int
    @JvmName("SCREEN_WIDTH")
    get() = displayMetrics.widthPixels

val SCREEN_HEIGHT: Int
    @JvmName("SCREEN_HEIGHT")
    get() = displayMetrics.heightPixels

val STATUS_BAR_HEIGHT: Int
    @JvmName("STATUS_BAR_HEIGHT")
    get() {
        val resourceId = Resources.getSystem().getIdentifier("status_bar_height", "dimen", "android")
        return Resources.getSystem().getDimensionPixelSize(resourceId)
    }

val Context.ACTION_BAR_HEIGHT: Int
    @JvmName("ACTION_BAR_HEIGHT")
    get() {
        val tv = TypedValue()
        return if (theme.resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
            TypedValue.complexToDimensionPixelSize(tv.data, resources.displayMetrics)
        } else 0
    }
val NAVIGATION_BAR_HEIGHT: Int
    @JvmName("NAVIGATION_BAR_HEIGHT")
    get() {
        val resourceId = Resources.getSystem().getIdentifier("navigation_bar_height", "dimen", "android")
        return if (resourceId != 0) {
            Resources.getSystem().getDimensionPixelOffset(resourceId)
        } else {
            0
        }
    }