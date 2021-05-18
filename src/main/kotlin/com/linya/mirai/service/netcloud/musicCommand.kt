package com.linya.mirai.service.netcloud

import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Face
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain
import net.mamoe.mirai.message.nextMessageOrNull

fun musicEntrance() {
    GlobalEventChannel.subscribeGroupMessages {
        startsWith("点歌 ") {
            val results = searchMusicInformation(message.contentToString().replace("点歌 ", ""))
            if (results.result.songCount == 0) {
                val message = buildMessageChain {
                    +PlainText("找不到该歌曲")
                    +Face(107)
                }
                group.sendMessage(message)
            } else {
                var searchResult = "搜索结果为：\n"
                val loop_count = when {
                    results.result.songCount < 10 -> results.result.songCount - 1
                    else -> 9
                }
                for (i in 0..loop_count) {
                    searchResult += "No." +
                        (i + 1).toString() +
                        "  歌曲名：" +
                        results.result.songs[i].name +
                        "  |  歌手：" +
                        results.result.songs[i].artists[0].name +
                        when {
                            i < loop_count -> '\n'
                            else -> '\t'
                        }
                }
                val mention = "  有多首相关歌曲，请在30秒内输入数字【1-10】来选择你要播放的歌曲~\n" + searchResult
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(mention)
                })

                val timeLimit = 30000.toLong()
                var choice = 1
                while (true) {
                    val nextMsg = nextMessageOrNull(timeLimit)
                    //如果超时以后用户还没有发送消息，那么nextMsg的内容将会是null
                    if (nextMsg.isNullOrEmpty()) {
                        group.sendMessage(buildMessageChain {
                            +At(sender.id)
                            +PlainText(" 在30秒内你没有做出选择，点歌失败，将分享第一条搜索到的结果")
                        })
                        break
                    }
                    else {
                        try {
                            choice = nextMsg.contentToString().toInt()
                            if (choice > 10 || choice < 1 ||choice > results.result.songCount) {
                                //发送了超出数组范围的数字
                                group.sendMessage(buildMessageChain {
                                    +At(sender.id)
                                    +PlainText(" 请发送数字【1-${results.result.songCount}】,不要发送超出范围的数字！你将还有30秒做出选择")
                                    +Face(107)
                                })
                                choice = 1
                            } else break
                        } catch (e: NumberFormatException) {
                            //再次让用户选歌
                            group.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText(" 请输入数字【1-10】,不要发送非数字的字符或字符串！你将还有30秒做出选择")
                                +Face(107)
                            })
                        }
                    }
                }
                val share = getMusicShare(results,choice)
                group.sendMessage(share)
            }
        }
    }
}
