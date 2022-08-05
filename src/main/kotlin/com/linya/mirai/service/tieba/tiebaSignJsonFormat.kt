package com.linya.mirai.service.tieba

import kotlinx.serialization.Serializable


@Serializable
data class tiebaSignJson(
    val `data`: Data,
    val error: String,
    val no: Int
)

@Serializable
data class Data(
    val forum_list: String? = null,
    val gradeNoVip: Int,
    val gradeVip: Int,
    val signedForumAmount: Int,
    val signedForumAmountFail: Int,
    val unsignedForumAmount: Int,
    val vipExtraSignedForumAmount: Int
)