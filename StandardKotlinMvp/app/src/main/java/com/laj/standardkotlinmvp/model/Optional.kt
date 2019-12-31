package com.laj.standardkotlinmvp.model

/**
 * Desc:data返回值包装类，data可能为null，而rxJava onNext方法不接收null值
 * Author: hy
 * Date: 2019/4/22 18:26
 */
class Optional<T>(
       private val nullableData: T?
) {

    val isNull: Boolean
        get() = this.nullableData == null

    fun get(): T? = nullableData?: throw NoSuchElementException("No value present")
    

}