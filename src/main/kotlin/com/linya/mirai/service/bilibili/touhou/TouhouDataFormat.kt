package com.linya.mirai.service.bilibili.touhou

import kotlinx.serialization.Serializable

@Serializable
data class TouhouDataJson(
    val card_type: String,
    val items: List<Item>,
    val publish_range: Int,
    val title: String,
    val update_time: Int
)

@Serializable
data class Item(
    val author_id: Int,
    val author_name: String,
    val bvid: String,
    val cover: String,
    val duration: String,
    val filt: Int,
    val id: Int,
    val like_count: String,
    val name: String,
    val sort: String,
    val view_count: String
)