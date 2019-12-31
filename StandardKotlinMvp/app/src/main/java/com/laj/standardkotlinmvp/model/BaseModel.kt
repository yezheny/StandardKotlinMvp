package com.laj.standardkotlinmvp.model

class BaseModel<T> constructor(
        var status: Boolean = false, var code: String = "",
        var msg: String = "", var errorMsg: String = "",
        var data: T, var currentTime: Long = 0
) {
}