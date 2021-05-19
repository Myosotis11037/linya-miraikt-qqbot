package com.linya.mirai.service.setu

import kotlinx.serialization.Serializable

@Serializable
data class loliconJson(
    val code: Int,
    val count: Int,
    val `data`: List<Data>,
    val msg: String,
    val quota: Int,
    val quota_min_ttl: Int
)

@Serializable
data class Data(
    val author: String,
    val height: Int,
    val p: Int,
    val pid: Int,
    val r18: Boolean,
    val tags: List<String>,
    val title: String,
    val uid: Int,
    val url: String,
    val width: Int
)