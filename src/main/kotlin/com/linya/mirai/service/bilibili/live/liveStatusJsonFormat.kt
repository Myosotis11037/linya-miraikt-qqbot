package com.linya.mirai.service.bilibili.live

import kotlinx.serialization.Serializable

@Serializable
data class TouhouLiveJson(
    val code: Int,
    val `data`: Data,
    val message: String,
    val ttl: Int
)

@Serializable
data class Data(
    val birthday: String,
    val coins: Double,
    val face: String,
    val fans_badge: Boolean,
    val is_followed: Boolean,
    val jointime: Int,
    val level: Int,
    val live_room: LiveRoom,
    val mid: Int,
    val moral: Int,
    val name: String,
    val nameplate: Nameplate,
    val official: Official,
    val pendant: Pendant,
    val rank: Int,
    val sex: String,
    val sign: String,
    val silence: Int,
    val sys_notice: SysNotice,
    val theme: Theme,
    val top_photo: String,
    val user_honour_info: UserHonourInfo,
    val vip: Vip
)

@Serializable
data class LiveRoom(
    val broadcast_type: Int,
    val cover: String,
    val liveStatus: Int,
    val online: Int,
    val roomStatus: Int,
    val roomid: Int,
    val roundStatus: Int,
    val title: String,
    val url: String
)

@Serializable
data class Nameplate(
    val condition: String,
    val image: String,
    val image_small: String,
    val level: String,
    val name: String,
    val nid: Int
)

@Serializable
data class Official(
    val desc: String,
    val role: Int,
    val title: String,
    val type: Int
)

@Serializable
data class Pendant(
    val expire: Int,
    val image: String,
    val image_enhance: String,
    val image_enhance_frame: String,
    val name: String,
    val pid: Int
)

@Serializable
class SysNotice

@Serializable
class Theme

@Serializable
data class UserHonourInfo(
    val colour: String? = null,
    val mid: Int,
    val tags: String? = null
)

@Serializable
data class Vip(
    val avatar_subscript: Int,
    val avatar_subscript_url: String,
    val due_date: Long,
    val label: Label,
    val nickname_color: String,
    val role: Int,
    val status: Int,
    val theme_type: Int,
    val type: Int,
    val vip_pay_type: Int
)

@Serializable
data class Label(
    val bg_color: String,
    val bg_style: Int,
    val border_color: String,
    val label_theme: String,
    val path: String,
    val text: String,
    val text_color: String
)