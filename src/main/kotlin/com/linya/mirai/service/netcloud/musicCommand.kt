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
        startsWith("��� ") {
            val results = searchMusicInformation(message.contentToString().replace("��� ", ""))
            if (results.result.songCount == 0) {
                val message = buildMessageChain {
                    +PlainText("�Ҳ����ø���")
                    +Face(107)
                }
                group.sendMessage(message)
            } else {
                var searchResult = "�������Ϊ��\n"
                val loop_count = when {
                    results.result.songCount < 10 -> results.result.songCount - 1
                    else -> 9
                }
                for (i in 0..loop_count) {
                    searchResult += "No." +
                        (i + 1).toString() +
                        "  ��������" +
                        results.result.songs[i].name +
                        "  |  ���֣�" +
                        results.result.songs[i].artists[0].name +
                        when {
                            i < loop_count -> '\n'
                            else -> '\t'
                        }
                }
                val mention = "  �ж�����ظ���������30�����������֡�1-10����ѡ����Ҫ���ŵĸ���~\n" + searchResult
                group.sendMessage(buildMessageChain {
                    +At(sender.id)
                    +PlainText(mention)
                })

                val timeLimit = 30000.toLong()
                var choice = 1
                while (true) {
                    val nextMsg = nextMessageOrNull(timeLimit)
                    //�����ʱ�Ժ��û���û�з�����Ϣ����ônextMsg�����ݽ�����null
                    if (nextMsg.isNullOrEmpty()) {
                        group.sendMessage(buildMessageChain {
                            +At(sender.id)
                            +PlainText(" ��30������û������ѡ�񣬵��ʧ�ܣ��������һ���������Ľ��")
                        })
                        break
                    }
                    else {
                        try {
                            choice = nextMsg.contentToString().toInt()
                            if (choice > 10 || choice < 1 ||choice > results.result.songCount) {
                                //�����˳������鷶Χ������
                                group.sendMessage(buildMessageChain {
                                    +At(sender.id)
                                    +PlainText(" �뷢�����֡�1-${results.result.songCount}��,��Ҫ���ͳ�����Χ�����֣��㽫����30������ѡ��")
                                    +Face(107)
                                })
                                choice = 1
                            } else break
                        } catch (e: NumberFormatException) {
                            //�ٴ����û�ѡ��
                            group.sendMessage(buildMessageChain {
                                +At(sender.id)
                                +PlainText(" ���������֡�1-10��,��Ҫ���ͷ����ֵ��ַ����ַ������㽫����30������ѡ��")
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
