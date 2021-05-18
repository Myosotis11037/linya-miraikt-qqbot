package com.linya.mirai.service.setu

import com.linya.mirai.service.tool.downloadImage
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import java.io.File


fun eroEntrance() {
    GlobalEventChannel.subscribeGroupMessages() {
        finding("(来点涩图|来点色图|色图时间)".toRegex()) {
            subject.sendImage(downloadImage("https://api.nmb.show/1985acg.php")!!)
        }
        finding("(铜图时间|来点铜图)".toRegex()) {
            subject.sendImage(downloadImage("https://api.ixiaowai.cn/api/api.php")!!)
        }
        finding("(来点辉夜|辉夜图)".toRegex()) {
            val filePath = "./data/net.mamoe.mirai-api-http/images/Kaguya"
            val fileNames: MutableList<String> = mutableListOf()
            val fileTree: FileTreeWalk = File(filePath).walk()
            fileTree.maxDepth(1)
                .filter { it.extension in listOf("jpg", "png") }
                .forEach { fileNames.add(it.name) }
            val index = (0..fileNames.size).random()
            val pictureLocation = filePath + "/" + fileNames[index]
            group.sendImage(file = File(pictureLocation))

        }
    }
}