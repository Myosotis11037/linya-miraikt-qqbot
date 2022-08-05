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

        finding("(里涩图|里色图)".toRegex()) {
            if (group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "凛夜哥哥" else "主人"
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" 正在获取${senderOfThisEvent}所需要的里涩图，请稍安勿躁")
                    +Face(111)
                })
                val url = downloadLoliconImage(group = group)
                if (url == "error") {
                    group.sendMessage(buildMessageChain {
                        +PlainText(" 没有找到此分类的涩图！")
                        +Face(111)
                    })
                }else if(url != "exceed"){
                    subject.sendImage(downloadImage(url)!!)
                }
            }
        }
        case("来点r18") {
            if (group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "凛夜哥哥" else "主人"
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" 正在获取${senderOfThisEvent}所需要的r18图片（害怕），请稍安勿躁")
                    +Face(111)
                })
                val url = downloadLoliconImage(1,group = group)
                if (url == "error") {
                    group.sendMessage(buildMessageChain {
                        +PlainText(" 没有找到此分类的涩图！")
                        +Face(111)
                    })
                }else if(url != "exceed"){
                    subject.sendImage(downloadImage(url)!!)
                }
            }
        }

        finding("(指定涩图 |指定色图 |指定r18涩图 |指定r18色图 )".toRegex()) {
            if (group.id != 182721157.toLong() && group.id != 451195420.toLong()) {
                val r18 = if (message.contentToString().contains("r18"))  1 else 0
                val senderOfThisEvent = if (sender.id == 5980403.toLong()) "凛夜哥哥" else "主人"
                val key = message.contentToString().replace("指定涩图 ", "").replace("指定色图 ", "").replace("指定r18色图 ","").replace("指定r18涩图 ","")
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(" 正在搜索${senderOfThisEvent}所需要的\"${key}\"分类下的图片，请稍安勿躁")
                    +Face(111)
                })
                val url = downloadLoliconImage(r18, key, group)
                if (url == "error") {
                    group.sendMessage(buildMessageChain {
                        +PlainText(" 没有找到此分类的涩图！")
                        +Face(111)
                    })
                }else if(url != "exceed"){
                    subject.sendImage(downloadImage(url)!!)
                }
            }
        }

    }
}