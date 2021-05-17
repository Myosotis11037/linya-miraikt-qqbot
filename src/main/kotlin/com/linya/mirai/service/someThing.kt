package com.linya.mirai.service

import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.code.MiraiCode
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.Face
import net.mamoe.mirai.message.data.PlainText
import net.mamoe.mirai.message.data.buildMessageChain

fun someThingEntrance() {
    GlobalEventChannel.subscribeGroupMessages() {
//        机器人的帮助文档
        case("help") {
            group.sendMessage(
                "目前已经公开的功能有：\n\n" +
                    "①打招呼功能，输入hi说不定可以得到妹妹的回应哦~\n\n" +
                    "②查bv号和av号的功能，并且能够解析任何形式分享的b站视频，能够显示视频的详细信息~\n\n" +
                    "③随机提供涩图的功能，输入‘色图时间’或者‘来点涩图’就可以随机发送一张图片了~(输入‘来点车万’可以得到车万图，输入‘来点辉夜’可以获得辉夜图哦)\n\n" +
                    "④整点报时功能~\n\n" +
                    "⑤提供b站车万区周榜功能~\n\n" +
                    "⑥碧蓝航线实时推送功能，并且输入'碧蓝航线最新动态'可以得到碧蓝航线官方账号发送的最新动态哦~\n\n" +
                    "⑦点歌功能。输入【点歌 xxx】就可以查找到你喜欢的歌曲哦~\n" +
                    "凛夜sama赛高！（不要忘了所有的功能都是凛夜亲手敲的代码哦）"
            )
        }
//打招呼功能
        case("hi") {
//先构建消息链
            var chain = buildMessageChain {
                +At(sender.id)
            }
            chain = when (sender.id.toInt()) {
                5980403 -> chain.plus(PlainText(" 哥哥爱死你了mua~"))
                349468958 -> chain.plus(PlainText(" 哥哥我也爱你呢?"))
                865734287 -> chain.plus(PlainText(" mu..(害怕)mua?"))
                744938425 -> chain.plus(PlainText(" 欧尼酱要吃饭呢，要洗澡呢，还是要先吃我呢")).plus(Face(111))
                else -> {
                    chain.plus(PlainText(" hi~"))
                }
            }
            group.sendMessage(chain)
        }

        case("晚安") {
            var chain = buildMessageChain {
                +At(sender.id)
            }
            chain = when (sender.id.toInt()) {
                5980403 -> chain.plus(PlainText(" 哥哥晚安")).plus(Face(75))
                else -> chain.plus(PlainText(" 晚安~"))
            }
            group.sendMessage(chain)
        }

        finding("([草艹])".toRegex()) {
            if ((0..100).random() < 25) group.sendMessage("草")
        }

        atBot {
            group.sendMessage(buildMessageChain {
                +At(sender.id)
                +Face(111)
            })
        }

        at(5980403.toLong()).invoke {
            var chain = buildMessageChain {
                +PlainText("${sender.nick}(${sender.id})在${group.name}(${group.id})中对主人说：\n")
            }
            chain = chain.plus(
                MiraiCode.deserializeMiraiCode(
                    message
                        .serializeToMiraiCode()
                        .replace("[mirai:at:5980403]", "")
                )
            )
            bot.getFriend(5980403.toLong())!!.sendMessage(chain)
        }

    }
}