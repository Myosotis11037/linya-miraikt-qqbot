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
                    "①打招呼功能，输入 hi 或者 晚安 ，说不定可以得到妹妹的回应哦~\n\n" +
                    "②解析bv号和av号的功能，并且能够解析以任何形式（电脑端、Android客户端、ios客户端）分享的b站视频，能够显示视频的详细信息~\n\n" +
                    "③随机提供涩图的功能，在部分群关闭了部分功能，具体使用方法可以输入‘setu --help’来查看\n\n" +
                    "④整点报时功能~\n\n" +
                    "⑤提供b站车万区周榜功能，输入’车万周榜‘即可查看半小时内b站车万区的前十榜单~\n\n" +
                    "⑥点歌功能。输入’点歌 xxx‘就可以查找到你喜欢的歌曲哦~\n\n" +
                    "⑦开启红群直播间功能，只有红群和tfcc直播群两个群拥有权限，可以通过输入'live --help'查看直播相关模块的使用方法~" +
                    "凛夜sama赛高！（不要忘了所有的功能都是凛夜亲手敲的代码哦，如果遇到机器人出现bug请立即在群里at我，当收到at消息时我会马上通知哥哥，如果我还没有出现那说明凛夜哥哥真的不在（沮丧））"
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