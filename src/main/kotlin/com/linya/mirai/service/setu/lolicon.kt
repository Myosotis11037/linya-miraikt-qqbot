package com.linya.mirai.service.setu

import com.linya.mirai.PluginConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

val format = Json { ignoreUnknownKeys = true }

suspend fun downloadLoliconImage(r18: Int = 0, keyword: String = ""): String {
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
    val mention = format.decodeFromString<loliconJson>(resp).msg
    if(mention == ""){
        return format.decodeFromString<loliconJson>(resp).data[0].url
    }else{
        return "error"
    }

}
