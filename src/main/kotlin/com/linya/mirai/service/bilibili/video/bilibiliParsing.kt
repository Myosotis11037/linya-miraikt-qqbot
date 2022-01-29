package com.linya.mirai.service.bilibili.video

import com.linya.mirai.database.Bilibili
import com.linya.mirai.service.tool.downloadImage
import com.linya.mirai.service.tool.parsingVideoDataString
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.SerializationException
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage

var regular = Bilibili()
val format = Json { ignoreUnknownKeys = true }

fun bilibiliVideoEntrance(){
    GlobalEventChannel.subscribeGroupMessages(){
        always{
            if(sender.id != 2157510360 && sender.id != 1405703587.toLong()) {
                //用于格式化Json并发送
                suspend fun uJsonVideo(uJsonVideo: String) {
                    /**
                     * 如果pJson中含有data字段时不会抛出[SerializationException]，不含有则反之
                     * 当未抛出[SerializationException]异常时，正常执行，使用[VideoDataJson]格式化并发送
                     * 当抛出[SerializationException]异常时，被catch抓住并使用[AbnormalVideoDataJson]格式化并发送
                     */
                    try {
                        val pJson = format.decodeFromString<VideoDataJson>(uJsonVideo)
                        group.sendMessage(
                            downloadImage(pJson.data.pic)!!
                                .uploadAsImage(group)
                                .plus(parsingVideoDataString(pJson))
                        )
                    } catch (e: SerializationException) {
                        val pJson = format.decodeFromString<AbnormalVideoDataJson>(uJsonVideo)
                        when (pJson.code) {
                            -404 -> group.sendMessage("喵, 视频不存在哦")
                            62002 -> group.sendMessage("视频不可见惹")
                        }
                    }
                }


                //检测消息中AV/BV
                when {
                    regular.bvFind.containsMatchIn(message.contentToString()) -> {
                        uJsonVideo(videoDataGet(regular.bvFind.find(it)!!.value, "bvid"))
                    }

                    regular.avFind.containsMatchIn(message.contentToString()) -> {
                        uJsonVideo(
                            videoDataGet(
                                regular.avFind.find(message.contentToString())!!.value.replace(
                                    Regex("(av|AV)"),
                                    ""
                                ), "aid"
                            )
                        )
                    }
                }
            }
        }
    }
}

/**
 *用于GET视频信息
 * id是视频的ID(AV|BV)
 * mode是控制GET的模式的(aid|bvid)
 * PS:因为mode是String类型的输入，所以对输入有极高的要求，如果出现-400可以先检查一下这个
 **/
suspend fun videoDataGet(id: String, mode: String): String {
    return HttpClient().use { clien -> clien.get("https://api.bilibili.com/x/web-interface/view?$mode=$id") }
}