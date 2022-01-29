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
                //���ڸ�ʽ��Json������
                suspend fun uJsonVideo(uJsonVideo: String) {
                    /**
                     * ���pJson�к���data�ֶ�ʱ�����׳�[SerializationException]����������֮
                     * ��δ�׳�[SerializationException]�쳣ʱ������ִ�У�ʹ��[VideoDataJson]��ʽ��������
                     * ���׳�[SerializationException]�쳣ʱ����catchץס��ʹ��[AbnormalVideoDataJson]��ʽ��������
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
                            -404 -> group.sendMessage("��, ��Ƶ������Ŷ")
                            62002 -> group.sendMessage("��Ƶ���ɼ���")
                        }
                    }
                }


                //�����Ϣ��AV/BV
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
 *����GET��Ƶ��Ϣ
 * id����Ƶ��ID(AV|BV)
 * mode�ǿ���GET��ģʽ��(aid|bvid)
 * PS:��Ϊmode��String���͵����룬���Զ������м��ߵ�Ҫ���������-400�����ȼ��һ�����
 **/
suspend fun videoDataGet(id: String, mode: String): String {
    return HttpClient().use { clien -> clien.get("https://api.bilibili.com/x/web-interface/view?$mode=$id") }
}