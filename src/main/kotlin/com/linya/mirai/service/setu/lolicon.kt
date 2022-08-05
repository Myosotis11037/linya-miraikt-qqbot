package com.linya.mirai.service.setu

import com.linya.mirai.PluginConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.contact.Group

val format = Json { ignoreUnknownKeys = true }

suspend fun downloadLoliconImage(r18: Int = 0, keyword: String = "", group: Group): String {
    val loliconApi = "https://api.lolicon.app/setu/"
    val client = HttpClient(OkHttp)
    val resp = client.get<String> {
        url(loliconApi)
        parameter("apikey", PluginConfig.myDataBase.loliconKey)
        parameter("r18", r18)
        parameter("num", 1)
        parameter("keyword", keyword)
    }
    println(resp)
    client.close()
    val mention = format.decodeFromString<LoliconJson>(resp)
    val data = mention.data[0]
    val url = data.url.replace("cat","re")
    if(mention.msg == ""){
        group.sendMessage(
            """
                pid : ${data.pid}
                标题 : ${data.title}
                作者 : ${data.author}
                图片链接 : $url
            """.trimIndent())
        return url
    }
    else if(mention.msg == "达到调用额度限制"){
        group.sendMessage("已达到今日额度限制!")
        return "exceed"
    }
    else {
        return "error"
    }
}
