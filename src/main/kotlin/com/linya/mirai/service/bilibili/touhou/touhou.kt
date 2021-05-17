package com.linya.mirai.service.bilibili.touhou

import com.linya.mirai.database.Bilibili
import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages

val format = Json { ignoreUnknownKeys = true }
val regular = Bilibili()

fun touhouEntrance() {
    GlobalEventChannel.subscribeGroupMessages {
        finding("(�����ܰ�|�����ܰ�)".toRegex()) {
            val touhouData = touhouDataGet()
            val touhouJson = regular.touhouFormat.find(touhouData)!!
            val touhouResults = format.decodeFromString<TouhouDataJson>(touhouJson.value)
            var sstr = "�������ܰ�ÿ30���Ӹ���һ�Σ���\n"
            val nums: CharArray = charArrayOf('һ', '��', '��', '��', '��', '��', '��', '��', '��', 'ʮ')

            for (i in touhouResults.items.indices) {
                sstr = sstr + "��" + nums[i] + "����" + touhouResults.items[i].name + "\n"
                sstr = sstr + "up����" + touhouResults.items[i].author_name + "\n"
                sstr = sstr + "bv�ţ�" + touhouResults.items[i].bvid + "\n"
                sstr = sstr + "��Ƶʱ����" + touhouResults.items[i].duration + "\n"
                sstr = sstr + "��������" + touhouResults.items[i].like_count + "\n"
                sstr = sstr + "��������" + touhouResults.items[i].view_count + "\n"
                sstr = sstr + "��Ƶ���ӣ�" + "https://www.bilibili.com/video/" + touhouResults.items[i].bvid
                sstr += if(i < 9) "\n\n" else "\n"
            }

            group.sendMessage(sstr)
        }
    }
}

suspend fun touhouDataGet(): String {
    return HttpClient().use { client -> client.get("https://api.bilibili.com/x/web-interface/web/channel/multiple/list?channel_id=166&sort_type=hot&page_size=10") }
}

