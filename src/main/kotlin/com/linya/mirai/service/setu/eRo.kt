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

        finding("(��ɬͼ|��ɫͼ)".toRegex()) {
            if (group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "��ҹ���" else "����"
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" ���ڻ�ȡ${senderOfThisEvent}����Ҫ����ɬͼ�����԰�����")
                    +Face(111)
                })
                val url = downloadLoliconImage(group = group)
                if (url == "error") {
                    group.sendMessage(buildMessageChain {
                        +PlainText(" û���ҵ��˷����ɬͼ��")
                        +Face(111)
                    })
                }else if(url != "exceed"){
                    subject.sendImage(downloadImage(url)!!)
                }
            }
        }
        case("����r18") {
            if (group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "��ҹ���" else "����"
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" ���ڻ�ȡ${senderOfThisEvent}����Ҫ��r18ͼƬ�����£������԰�����")
                    +Face(111)
                })
                val url = downloadLoliconImage(1,group = group)
                if (url == "error") {
                    group.sendMessage(buildMessageChain {
                        +PlainText(" û���ҵ��˷����ɬͼ��")
                        +Face(111)
                    })
                }else if(url != "exceed"){
                    subject.sendImage(downloadImage(url)!!)
                }
            }
        }

        finding("(ָ��ɬͼ |ָ��ɫͼ |ָ��r18ɬͼ |ָ��r18ɫͼ )".toRegex()) {
            if (group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val r18 = if (message.contentToString().contains("r18"))  1 else 0
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "��ҹ���" else "����"
                val key = message.contentToString().replace("ָ��ɬͼ ", "").replace("ָ��ɫͼ ", "").replace("ָ��r18ɫͼ ","").replace("ָ��r18ɬͼ ","")
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" ��������${senderOfThisEvent}����Ҫ��\"${key}\"�����µ�ͼƬ�����԰�����")
                    +Face(111)
                })
                val url = downloadLoliconImage(r18, key, group)
                if (url == "error") {
                    group.sendMessage(buildMessageChain {
                        +PlainText(" û���ҵ��˷����ɬͼ��")
                        +Face(111)
                    })
                }else if(url != "exceed"){
                    subject.sendImage(downloadImage(url)!!)
                }
            }
        }

    }
}