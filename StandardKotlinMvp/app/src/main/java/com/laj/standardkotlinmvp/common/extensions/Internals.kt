package com.laj.standardkotlinmvp.common.extensions

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import java.io.Serializable

/**
 * Desc:
 * Author: hy
 * Date: 2019/7/15 16:54
 **/

object Internals {

    @JvmStatic
    fun <T> createIntent(ctx: Context, clazz: Class<out T>, params: Array<out Pair<String, Any?>>): Intent {
        val intent = Intent(ctx, clazz)
        if (params.isNotEmpty()) fillIntentArguments(intent, params)
        return intent
    }

    @JvmStatic
    fun internalStartActivity(
            ctx: Context,
            activity: Class<out Activity>,
            params: Array<out Pair<String, Any?>>
    ) {
        ctx.startActivity(createIntent(ctx, activity, params))
    }

    @JvmStatic
    fun internalStartActivityForResult(
            act: Activity,
            activity: Class<out Activity>,
            requestCode: Int,
            params: Array<out Pair<String, Any?>>
    ) {
        act.startActivityForResult(createIntent(act, activity, params), requestCode)
    }

    @JvmStatic
    fun internalStartService(
            ctx: Context,
            service: Class<out Service>,
            params: Array<out Pair<String, Any?>>
    ): ComponentName? = ctx.startService(createIntent(ctx, service, params))

    @JvmStatic
    fun internalStopService(
            ctx: Context,
            service: Class<out Service>,
            params: Array<out Pair<String, Any?>>
    ): Boolean = ctx.stopService(createIntent(ctx, service, params))

    @JvmStatic
    private fun fillIntentArguments(intent: Intent, params: Array<out Pair<String, Any?>>) {
        params.forEach {
            when (val value = it.second) {
                null -> intent.putExtra(it.first, null as Serializable?)
                is Int -> intent.putExtra(it.first, value)
                is Long -> intent.putExtra(it.first, value)
                is CharSequence -> intent.putExtra(it.first, value)
                is String -> intent.putExtra(it.first, value)
                is Float -> intent.putExtra(it.first, value)
                is Double -> intent.putExtra(it.first, value)
                is Char -> intent.putExtra(it.first, value)
                is Short -> intent.putExtra(it.first, value)
                is Boolean -> intent.putExtra(it.first, value)
                is Serializable -> intent.putExtra(it.first, value)
                is Bundle -> intent.putExtra(it.first, value)
                is Parcelable -> intent.putExtra(it.first, value)
                is Array<*> -> when {
                    value.isArrayOf<CharSequence>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<String>() -> intent.putExtra(it.first, value)
                    value.isArrayOf<Parcelable>() -> intent.putExtra(it.first, value)
                    else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
                }
                is IntArray -> intent.putExtra(it.first, value)
                is LongArray -> intent.putExtra(it.first, value)
                is FloatArray -> intent.putExtra(it.first, value)
                is DoubleArray -> intent.putExtra(it.first, value)
                is CharArray -> intent.putExtra(it.first, value)
                is ShortArray -> intent.putExtra(it.first, value)
                is BooleanArray -> intent.putExtra(it.first, value)
                else -> throw Exception("Intent extra ${it.first} has wrong type ${value.javaClass.name}")
            }
            return@forEach
        }
    }

    fun bundleOf(params: Array<out Pair<String, Any?>>): Bundle {
        val b = Bundle()
        for (p in params) {
            val (k, v) = p
            when (v) {
                null -> b.putSerializable(k, null)
                is Boolean -> b.putBoolean(k, v)
                is Byte -> b.putByte(k, v)
                is Char -> b.putChar(k, v)
                is Short -> b.putShort(k, v)
                is Int -> b.putInt(k, v)
                is Long -> b.putLong(k, v)
                is Float -> b.putFloat(k, v)
                is Double -> b.putDouble(k, v)
                is String -> b.putString(k, v)
                is CharSequence -> b.putCharSequence(k, v)
                is Parcelable -> b.putParcelable(k, v)
                is Serializable -> b.putSerializable(k, v)
                is BooleanArray -> b.putBooleanArray(k, v)
                is ByteArray -> b.putByteArray(k, v)
                is CharArray -> b.putCharArray(k, v)
                is DoubleArray -> b.putDoubleArray(k, v)
                is FloatArray -> b.putFloatArray(k, v)
                is IntArray -> b.putIntArray(k, v)
                is LongArray -> b.putLongArray(k, v)
                is Array<*> -> {
                    @Suppress("UNCHECKED_CAST")
                    when {
                        v.isArrayOf<Parcelable>() -> b.putParcelableArray(k, v as Array<out Parcelable>)
                        v.isArrayOf<CharSequence>() -> b.putCharSequenceArray(k, v as Array<out CharSequence>)
                        v.isArrayOf<String>() -> b.putStringArray(k, v as Array<out String>)
                        else -> throw Exception("Unsupported bundle component (${v.javaClass})")
                    }
                }
                is ShortArray -> b.putShortArray(k, v)
                is Bundle -> b.putBundle(k, v)
                else -> throw Exception("Unsupported bundle component (${v.javaClass})")
            }
        }
        return b
    }


}
