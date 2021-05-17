package com.linya.mirai.service.bilibili.video

import com.linya.mirai.service.tool.downloadImage
import com.linya.mirai.service.tool.parsingVideoDataString
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.utils.ExternalResource.Companion.uploadAsImage


fun b23ShortLinkEntrance() {
    GlobalEventChannel.subscribeGroupMessages {
        finding(regular.b23Find).invoke {
            suspend fun uJsonVideo(uJsonVideo:String){
                val pJson = format.decodeFromString<VideoDataJson>(uJsonVideo)
                group.sendMessage(
                    downloadImage(pJson.data.pic)!!
                        .uploadAsImage(group, "jpg")
                        .plus(parsingVideoDataString(pJson))
                )
            }
            val b23Data = b23DataGet(regular.b23Find.find(it)!!.value)
            when {
                regular.bvFind.containsMatchIn(b23Data) -> uJsonVideo(
                    videoDataGet(
                        regular.bvFind.find(b23Data)!!
                            .value,
                        "bvid"
                    )
                )
            }
        }

    }
}


suspend fun b23DataGet(url: String): String {
    return HttpClient {
        followRedirects = false
        expectSuccess = false
    }.use { clien -> clien.get(url) }
}