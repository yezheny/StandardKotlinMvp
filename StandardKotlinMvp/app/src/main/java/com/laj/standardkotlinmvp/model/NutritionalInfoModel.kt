package com.laj.standardkotlinmvp.model

/**
 * created by yezhengyu on 2019/7/28 15:47
 */
data class NutritionalInfoModel (
        var foodKid: Long = 0,
        var foodName: String = "",
        var description: String = "",
        var foodCompInfoVOList: List<NutritionalEntity>
)

data class NutritionalEntity(
        var percentage: Int = 0,
        var name: String = "",
        var normalUnit: String = "",
        var normalValue: String = "",
        var alias: String = "", //成分别名
        var calcdValue: Long = 0,   //计算值
        var calcdUnit: String = ""
)