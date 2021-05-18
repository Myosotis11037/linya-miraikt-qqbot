package com.linya.mirai.service.tieba

import com.linya.mirai.PluginConfig
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.buildMessageChain


val format = Json { ignoreUnknownKeys = true }

fun tiebaSignEntrance(){
    GlobalEventChannel.subscribeGroupMessages() {
        case("����ǩ��") {
            if(sender.id == 5980403.toLong()) tiebaSignIn(sender,group)
            else group.sendMessage("��û��ʹ�øù��ܵ�Ȩ��Ŷ~")
        }
    }
}

suspend fun tiebaSignIn(sender: Member, group : Group){
    val tiebaSignUrl = "https://tieba.baidu.com/tbmall/onekeySignin1"
    val client = HttpClient(OkHttp)
    val resp = client.post<String>{
        url(tiebaSignUrl)
        header("cookie", PluginConfig.myDataBase.tiebaCookie)
        parameter("ie","utf-8")
        parameter("tbs","dbcb633d0a5796b81612963177")
    }
    val tiebaSign = format.decodeFromString<tiebaSignJson>(resp)
    val signedForumAmount = tiebaSign.data.signedForumAmount
    val unsignedForumAmount = tiebaSign.data.unsignedForumAmount
    val message = buildMessageChain {
        +At(sender.id)
    }
    val signReport = "\nǩ���ɹ�������Ϊ" +
        signedForumAmount +
        "��\n" +
        "ǩ��ʧ�ܵ�����Ϊ" +
        unsignedForumAmount + "��"
    group.sendMessage(message.plus(signReport))
}