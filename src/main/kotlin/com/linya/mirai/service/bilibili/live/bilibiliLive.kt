package com.linya.mirai.service.bilibili.live

import com.linya.mirai.PluginConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.contact.asFriend

val format = Json { ignoreUnknownKeys = true }

suspend fun bilibiliGet(url: String): String {
    val client = HttpClient(OkHttp)
    val resp = client.post<String> {
        url(url)
        header("cookie", PluginConfig.myDataBase.biliCookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", PluginConfig.myDataBase.biliToken)
        parameter("csrf", PluginConfig.myDataBase.biliToken)
        parameter("platform", "pc")
        parameter("area_v2", 235)
    }
    client.close()
    return resp
}

suspend fun getLiveStatus(): Int {
    val liveStatusUrl = "https://api.bilibili.com/x/space/acc/info?mid=611240532&jsonp=jsonp"
    val client = HttpClient(OkHttp)
    val resp = client.get<String> {
        url(liveStatusUrl)
        header("cookie", PluginConfig.myDataBase.biliCookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", PluginConfig.myDataBase.biliToken)
        parameter("csrf", PluginConfig.myDataBase.biliToken)
        parameter("platform", "pc")
    }
    client.close()
    return format.decodeFromString<TouhouLiveJson>(resp).data.live_room.liveStatus
}


suspend fun changeLiveStatus(mode: Boolean, sender: Member): Boolean {
    val resp = bilibiliGet("https://api.live.bilibili.com/room/v1/Room/${if (mode) "startLive" else "stopLive"}")
    if(mode){
        val addr = format.decodeFromString<TouhouLiveSettings>(resp).data.rtmp.addr
        val code = format.decodeFromString<TouhouLiveSettings>(resp).data.rtmp.code
        when (sender.id.toInt()) {
            5980403 -> {
                val user = sender.asFriend()
                user.sendMessage(addr)
                user.sendMessage(code)
            }
            else -> {
                val user = sender
                sender.sendMessage(addr)
                user.sendMessage(code)
            }
        }
    }

    return when (getLiveStatus()) {
        1 -> mode
        else -> !mode
    }
}

suspend fun changeLiveName(liveName: String): Boolean {
    val changeLiveUrl = "https://api.live.bilibili.com/room/v1/Room/update"
    val client = HttpClient(OkHttp)
    val resp = client.post<String> {
        url(changeLiveUrl)
        header("cookie", PluginConfig.myDataBase.biliCookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", PluginConfig.myDataBase.biliToken)
        parameter("csrf", PluginConfig.myDataBase.biliToken)
        parameter("platform", "pc")
        parameter("title", liveName)
    }
    client.close()
    return resp.contains("\"msg\":\"ok\"")
}