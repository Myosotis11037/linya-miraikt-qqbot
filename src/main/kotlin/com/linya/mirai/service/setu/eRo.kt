package com.linya.mirai.service.setu

import com.linya.mirai.service.tool.downloadImage
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages


fun eroEntrance() {
    GlobalEventChannel.subscribeGroupMessages() {
        finding("(����ɬͼ|����ɫͼ|ɫͼʱ��)".toRegex()) {
            subject.sendImage(downloadImage("https://api.nmb.show/1985acg.php")!!)
        }
        finding("(ͭͼʱ��|����ͭͼ)".toRegex()) {
            subject.sendImage(downloadImage("https://api.ixiaowai.cn/api/api.php")!!)
        }
    }
}