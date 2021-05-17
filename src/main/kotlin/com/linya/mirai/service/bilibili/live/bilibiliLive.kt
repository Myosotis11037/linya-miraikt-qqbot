package com.linya.mirai.service.bilibili.live

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

var cookie =
    "SESSDATA=ac8c6fea,1633969795,c0f74*41; bili_jct=4409a8f84719c8b946b9c4a3da3e3568; DedeUserID=611240532; DedeUserID__ckMd5=125254c8db81339b;"
var token = "4409a8f84719c8b946b9c4a3da3e3568"
val format = Json { ignoreUnknownKeys = true }

suspend fun getLiveStatus(): Int {
    val liveStatusUrl = "https://api.bilibili.com/x/space/acc/info?mid=611240532&jsonp=jsonp"
    val client = HttpClient(OkHttp)
    val resp = client.get<String> {
        url(liveStatusUrl)
        header("cookie", cookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", token)
        parameter("csrf", token)
        parameter("platform", "pc")
    }
    client.close()
    val touhouLiveStatus = format.decodeFromString<touhouLiveJson>(resp).data.live_room.liveStatus
    return touhouLiveStatus
}

suspend fun startLive(): Boolean {
    val startLiveUrl = "https://api.live.bilibili.com/room/v1/Room/startLive"
    val client = HttpClient(OkHttp)
    client.post<String> {
        url(startLiveUrl)
        header("cookie", cookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", token)
        parameter("csrf", token)
        parameter("platform", "pc")
        parameter("area_v2", 235)
    }
    client.close()
    val liveSuccess = getLiveStatus()
    when (liveSuccess) {
        1 -> return true
        else -> return false
    }
}

suspend fun stopLive(): Boolean {
    val stopLiveUrl = "https://api.live.bilibili.com/room/v1/Room/stopLive"
    val client = HttpClient(OkHttp)
    client.post<String> {
        url(stopLiveUrl)
        header("cookie", cookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", token)
        parameter("csrf", token)
        parameter("platform", "pc")
        parameter("area_v2", 235)
    }
    client.close()
    val liveSuccess = getLiveStatus()
    when (liveSuccess) {
        1 -> return false
        else -> return true
    }
}

suspend fun changeLiveName(Livename: String): Boolean {
    val changeLiveUrl = "https://api.live.bilibili.com/room/v1/Room/update"
    val client = HttpClient(OkHttp)
    val resp = client.post<String> {
        url(changeLiveUrl)
        header("cookie", cookie)
        parameter("room_id", 22431055)
        parameter("csrf_token", token)
        parameter("csrf", token)
        parameter("platform", "pc")
        parameter("title", Livename)
    }
    println(resp)
    client.close()
    if(resp.contains("\"msg\":\"ok\""))return true
    else return false

}

