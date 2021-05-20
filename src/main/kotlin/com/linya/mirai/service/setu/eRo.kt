package com.linya.mirai.service.setu

import com.linya.mirai.service.tool.downloadImage
import net.mamoe.mirai.contact.Contact.Companion.sendImage
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Face
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain
import java.io.File


fun eroEntrance() {
    GlobalEventChannel.subscribeGroupMessages() {
        finding("(����ɬͼ|����ɫͼ|ɫͼʱ��)".toRegex()) {
            subject.sendImage(downloadImage("https://api.nmb.show/1985acg.php")!!)
        }
        finding("(ͭͼʱ��|����ͭͼ)".toRegex()) {
            subject.sendImage(downloadImage("https://api.ixiaowai.cn/api/api.php")!!)
        }
        finding("(�����ҹ|��ҹͼ)".toRegex()) {
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

        finding("(��ɬͼ|��ɫͼ)".toRegex()){
            if(group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "��ҹ���" else "����"
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" ���ڻ�ȡ${senderOfThisEvent}����Ҫ����ɬͼ�����԰�����")
                    +Face(111)
                })
                subject.sendImage(downloadImage(downloadLoliconImage(0))!!)
            }
        }
        case("����r18"){
            if(group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "��ҹ���" else "����"
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" ���ڻ�ȡ${senderOfThisEvent}����Ҫ��r18ͼƬ�����£������԰�����")
                    +Face(111)
                })
                subject.sendImage(downloadImage(downloadLoliconImage(1))!!)
            }
        }

    }
}