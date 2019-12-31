package com.laj.standardkotlinmvp.common.extensions

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.core.os.ParcelCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager

/**
 * Desc:Context相关拓展函数、顶层函数
 * Author: hy
 * Date: 2019/7/15 16:16
 **/

inline fun <reified T : Activity> Fragment.startActivity(vararg params: Pair<String, Any?>) =
        activity?.let {
            Internals.internalStartActivity(it, T::class.java, params)
        }

inline fun <reified T : Activity> Context.startActivity(vararg params: Pair<String, Any?>) =
        Internals.internalStartActivity(this, T::class.java, params)

inline fun <reified T : Activity> Activity.startActivity(vararg params: Pair<String, Any?>) =
        Internals.internalStartActivity(this, T::class.java, params)

inline fun <reified T : Activity> Fragment.startActivityForRes(requestCode: Int, vararg params: Pair<String, Any?>) =
        activity?.let {
            startActivityForResult(Internals.createIntent(it, T::class.java, params), requestCode)
        }

inline fun <reified T : Activity> Activity.startActivityForRes(requestCode: Int, vararg params: Pair<String, Any?>) =
        startActivityForResult(Internals.createIntent(this, T::class.java, params), requestCode)

fun Context.getDrawableByAttr(@AttrRes attr: Int): Drawable? {
    val ta = obtainStyledAttributes(intArrayOf(attr))
    val drawable = ta.getDrawable(0)
    ta.recycle()
    return drawable
}

fun Context.getThemeByAttr(@AttrRes attr: Int): Int {
    val ta = obtainStyledAttributes(intArrayOf(attr))
    val theme = ta.getResourceId(0, 0)
    ta.recycle()
    return theme
}

/**
 * 复制到粘贴板
 */
/*fun Context.copyText(string: String) {
    val cm = getSystemService(Context.CLIPBOARD_SERVICE)
            as android.content.ClipboardManager
    val clip = ClipData.newPlainText("", string)
    cm.primaryClip = clip
}*/


fun Context.getString(@StringRes id: Int): String = getString(id)

fun Fragment.inflate(@LayoutRes id: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View = LayoutInflater.from(context).inflate(id, root, attachToRoot)

fun Activity.inflate(@LayoutRes id: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View = LayoutInflater.from(this).inflate(id, root, attachToRoot)

fun View.inflate(@LayoutRes id: Int, root: ViewGroup? = null, attachToRoot: Boolean = false): View = LayoutInflater.from(this.context).inflate(id, root, attachToRoot)

fun Fragment.horizontalLinearLayoutManager() = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
fun Fragment.verticalLinearLayoutManager() = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
fun Fragment.gridLayoutManager(spanCount: Int) = GridLayoutManager(context, spanCount)

fun Activity.horizontalLinearLayoutManager() = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
fun Activity.verticalLinearLayoutManager() = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
fun Activity.gridLayoutManager(spanCount: Int) = GridLayoutManager(this, spanCount)

fun Context.horizontalLinearLayoutManager() = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
fun Context.verticalLinearLayoutManager() = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
fun Context.gridLayoutManager(spanCount: Int) = GridLayoutManager(this, spanCount)

fun View.findColor(@ColorRes corRes: Int): Int = ContextCompat.getColor(context, corRes)
fun Fragment.findColor(@ColorRes corRes: Int): Int = ContextCompat.getColor(context!!, corRes)
fun Context.findColor(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)
fun Activity.findColor(@ColorRes id: Int): Int = ContextCompat.getColor(this, id)

/**
 * 返回contentView
 */
inline val Activity.contentView: View?
    get() = findOptional<ViewGroup>(android.R.id.content)?.getChildAt(0)

inline fun <reified T : View> View.find(@IdRes id: Int): T = findViewById(id)
inline fun <reified T : View> Activity.find(@IdRes id: Int): T = findViewById(id)
inline fun <reified T : View> Fragment.find(@IdRes id: Int): T = view?.findViewById(id) as T
inline fun <reified T : View> Dialog.find(@IdRes id: Int): T = findViewById(id)

inline fun <reified T : View> View.findOptional(@IdRes id: Int): T? = findViewById(id) as? T
inline fun <reified T : View> Activity.findOptional(@IdRes id: Int): T? = findViewById(id) as? T
inline fun <reified T : View> Fragment.findOptional(@IdRes id: Int): T? = view?.findViewById(id) as? T
inline fun <reified T : View> Dialog.findOptional(@IdRes id: Int): T? = findViewById(id) as? T

fun <T : Fragment> T.withArguments(params: Array<out Pair<String, Any?>>): T {
    arguments = Internals.bundleOf(params)
    return this
}


/**
 * 显示软键盘
 */
fun View.showSoftInput() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    postDelayed({
        requestFocus()
        imm.showSoftInput(this, InputMethodManager.SHOW_FORCED)
    }, 60)
}

/**
 * 隐藏软键盘
 */
fun View.hideSoftInput() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun Activity.hideSoftInput() {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (currentFocus != null) {
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

/** Write a boolean to a Parcel. */
fun Parcel.writeBooleanUsingCompat(value: Boolean) = ParcelCompat.writeBoolean(this, value)

/** Read a boolean from a Parcel. */
fun Parcel.readBooleanUsingCompat() = ParcelCompat.readBoolean(this)