package com.linya.mirai.service.netcloud

import com.linya.mirai.PluginConfig
import com.linya.mirai.database.netCloudMusic
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.message.data.MusicKind
import net.mamoe.mirai.message.data.MusicShare


val format = Json { ignoreUnknownKeys = true }
val regular = netCloudMusic()

suspend fun searchMusicInformation(songName: String): SearchResultsJson {
    val client = HttpClient(OkHttp)
    val api = "http://music.163.com/api/search/get/web?csrf_token="
    val name = "hlpretag=&hlposttag=&s=$songName&type=1&offset=0&total=true&limit=10"
    val apiUrl = api + name
    val resp = client.post<String> {
        url(apiUrl)
        header("cookie", PluginConfig.myDataBase.netcloudCookie)
        parameter("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
        parameter("content-type", "application/x-www-form-urlencoded; charset=utf-8")
    }
    return format.decodeFromString<SearchResultsJson>(resp)
}

suspend fun getMusicShare(result: SearchResultsJson, choice: Int): MusicShare {
    val kind = MusicKind.NeteaseCloudMusic
    val title = result.result.songs[choice - 1].name
    var summary = ""
    for (index in result.result.songs[choice - 1].artists.indices) {
        summary += result.result.songs[choice - 1].artists[index].name
        summary = summary + if(index < result.result.songs[choice - 1].artists.size - 1) "/" else ""
    }
    val jumpUrl = "https://y.music.163.com/m/song/${result.result.songs[choice - 1].id}/?userid=335141010"
    val pictureUrl = getPictureUrl(result.result.songs[choice - 1].id)
    val musicUrl = "http://music.163.com/song/media/outer/url?id=${result.result.songs[choice - 1].id}&userid=335141010"
    val brief = "[·ÖÏí]${title}"
    return MusicShare(kind, title, summary, jumpUrl, pictureUrl, musicUrl, brief)
}

suspend fun getPictureUrl(songId: Int): String {
    val client = HttpClient(OkHttp)
    val test = client.get<String> {
        url("https://music.163.com/song?id=$songId")
        header("cookie", PluginConfig.myDataBase.netcloudCookie)
        parameter("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.1")
        parameter("content-type", "application/x-www-form-urlencoded; charset=utf-8")
    }
    val pictureUrl = regular.songCover.find(test)!!.value
    return pictureUrl.replace("\"],", "")

}