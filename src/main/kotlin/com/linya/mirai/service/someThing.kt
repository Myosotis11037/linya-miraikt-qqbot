package com.linya.mirai.service

import com.linya.mirai.service.tool.helpDoc
import net.mamoe.mirai.contact.getMember
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.code.MiraiCode
import net.mamoe.mirai.message.data.*

fun someThingEntrance() {
    GlobalEventChannel.subscribeGroupMessages() {

        always {
            if ((sender.id == 5980403.toLong() && (message.contentToString() == "妹妹在吗" || message.contentToString() == "妹妹再见"))) {
                val member = group.getMember(sender.id)
                member!!.nudge().sendTo(group)
            }
        }

//        机器人的帮助文档
        case("help") {
            group.sendMessage(
                "目前已经公开的功能有：\n" +
                    "①打招呼功能，输入 hi 或者 晚安 ，说不定可以得到妹妹的回应哦~\n" +
                    "②解析bv号和av号的功能，并且能够解析以任何形式（电脑端、Android客户端、ios客户端）分享的b站视频，能够显示视频的详细信息~\n" +
                    "③随机提供涩图的功能，在部分群关闭了部分功能，具体使用方法可以输入‘setu --help’来查看\n" +
                    "④整点报时功能~\n" +
                    "⑤提供b站车万区周榜功能，输入’车万周榜‘即可查看半小时内b站车万区的前十榜单~\n" +
                    "⑥点歌功能。输入’点歌 xxx‘就可以查找到你喜欢的歌曲哦~\n" +
                    "凛夜sama赛高！（不要忘了所有的功能都是凛夜亲手敲的代码哦，如果遇到机器人出现bug请立即在群里at我，当收到at消息时我会马上通知哥哥，如果我还没有出现那说明凛夜哥哥真的不在（沮丧））"
            )
        }

        case("宝贝球"){
            group.sendMessage(PokeMessage.BaoBeiQiu)
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
            if(sender.id == 5980403.toLong()){
                if(message.contentToString() == "撤回"){
                    return@atBot
                }
            }
            else {
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +Face(111)
                })
            }
        }

        case("live --help"){
            group.sendMessage(helpDoc("live --help"))
        }
        case("setu --help"){
            group.sendMessage(helpDoc("setu --help"))
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