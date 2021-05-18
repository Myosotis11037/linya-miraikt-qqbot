package com.linya.mirai.service.bilibili.live

import kotlinx.serialization.Serializable

@Serializable
data class TouhouLiveSettings(
    val code: Int,
    val `data`: Daata,
    val message: String,
    val msg: String
)

@Serializable
data class Daata(
    val change: Int,
    val live_key: String,
    val notice: Notice,
    val protocols: List<Protocol>,
    val room_type: Int,
    val rtmp: Rtmp,
    val status: String,
    val sub_session_key: String,
    val try_time: String
)

@Serializable
data class Notice(
    val button_text: String,
    val button_url: String,
    val msg: String,
    val status: Int,
    val title: String,
    val type: Int
)

@Serializable
data class Protocol(
    val addr: String,
    val code: String,
    val new_link: String,
    val protocol: String,
    val provider: String
)

@Serializable
data class Rtmp(
    val addr: String,
    val code: String,
    val new_link: String,
    val provider: String
)