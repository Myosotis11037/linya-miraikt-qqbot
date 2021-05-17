package com.linya.mirai.service.bilibili.live

import com.linya.mirai.PluginConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

var token = "4409a8f84719c8b946b9c4a3da3e3568"
val format = Json { ignoreUnknownKeys = true }

suspend fun bilibiliGet(url: String): String {
    val client = HttpClient(OkHttp)
    val resp = client.post<String> {
        url(url)
        header("cookie", PluginConfig.myDataBase.biliCookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", token)
        parameter("csrf", token)
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
        parameter("csrf_token", token)
        parameter("csrf", token)
        parameter("platform", "pc")
    }
    client.close()
    val touhouLiveStatus = format.decodeFromString<TouhouLiveJson>(resp).data.live_room.liveStatus
    return touhouLiveStatus
}


suspend fun changeLiveStatus(mode: Boolean): Boolean {
    bilibiliGet("https://api.live.bilibili.com/room/v1/Room/${if (mode) "startLive" else "stopLive"}")
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
        parameter("csrf_token", token)
        parameter("csrf", token)
        parameter("platform", "pc")
        parameter("title", liveName)
    }
    client.close()
    return resp.contains("\"msg\":\"ok\"")
}