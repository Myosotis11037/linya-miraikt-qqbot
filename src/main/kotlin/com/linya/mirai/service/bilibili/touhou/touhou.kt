package com.linya.mirai.service.bilibili.touhou

import com.linya.mirai.database.Bilibili
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages

val format = Json { ignoreUnknownKeys = true }
val regular = Bilibili()

fun touhouEntrance() {
    GlobalEventChannel.subscribeGroupMessages {
        finding("(车万周榜|东方周榜)".toRegex()) {
            val touhouData = touhouDataGet()
            val touhouJson = regular.touhouFormat.find(touhouData)!!
            val touhouResults = format.decodeFromString<TouhouDataJson>(touhouJson.value)
            var sstr = "车万区周榜（每30分钟更新一次）：\n"
            val nums: CharArray = charArrayOf('一', '二', '三', '四', '五', '六', '七', '八', '九', '十')

            for (i in touhouResults.items.indices) {
                sstr = sstr + "第" + nums[i] + "名：" + touhouResults.items[i].name + "\n"
                sstr = sstr + "up主：" + touhouResults.items[i].author_name + "\n"
                sstr = sstr + "bv号：" + touhouResults.items[i].bvid + "\n"
                sstr = sstr + "视频时长：" + touhouResults.items[i].duration + "\n"
                sstr = sstr + "点赞数：" + touhouResults.items[i].like_count + "\n"
                sstr = sstr + "播放量：" + touhouResults.items[i].view_count + "\n"
                sstr = sstr + "视频链接：" + "https://www.bilibili.com/video/" + touhouResults.items[i].bvid
                sstr += if(i < 9) "\n\n" else "\n"
            }

            group.sendMessage(sstr)
        }
    }
}

suspend fun touhouDataGet(): String {
    return HttpClient().use { client -> client.get("https://api.bilibili.com/x/web-interface/web/channel/multiple/list?channel_id=166&sort_type=hot&page_size=10") }
}

