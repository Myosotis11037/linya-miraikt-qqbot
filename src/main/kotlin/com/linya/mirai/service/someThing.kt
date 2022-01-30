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
            if ((sender.id == 5980403.toLong() && (message.contentToString() == "��������" || message.contentToString() == "�����ټ�"))) {
                val member = group.getMember(sender.id)
                member!!.nudge().sendTo(group)
            }
        }

//        �����˵İ����ĵ�
        case("help") {
            group.sendMessage(
                "Ŀǰ�Ѿ������Ĺ����У�\n" +
                    "�ٴ��к����ܣ����� hi ���� �� ��˵�������Եõ����õĻ�ӦŶ~\n" +
                    "�ڽ���bv�ź�av�ŵĹ��ܣ������ܹ��������κ���ʽ�����Զˡ�Android�ͻ��ˡ�ios�ͻ��ˣ������bվ��Ƶ���ܹ���ʾ��Ƶ����ϸ��Ϣ~\n" +
                    "������ṩɬͼ�Ĺ��ܣ��ڲ���Ⱥ�ر��˲��ֹ��ܣ�����ʹ�÷����������롮setu --help�����鿴\n" +
                    "�����㱨ʱ����~\n" +
                    "���ṩbվ�������ܰ��ܣ����롯�����ܰ񡮼��ɲ鿴��Сʱ��bվ��������ǰʮ��~\n" +
                    "�޵�蹦�ܡ����롯��� xxx���Ϳ��Բ��ҵ���ϲ���ĸ���Ŷ~\n" +
                    "��ҹsama���ߣ�����Ҫ�������еĹ��ܶ�����ҹ�����õĴ���Ŷ��������������˳���bug��������Ⱥ��at�ң����յ�at��Ϣʱ�һ�����֪ͨ��磬����һ�û�г�����˵����ҹ�����Ĳ��ڣ���ɥ����"
            )
        }

        case("������"){
            group.sendMessage(PokeMessage.BaoBeiQiu)
        }



//���к�����
        case("hi") {
//�ȹ�����Ϣ��
            var chain = buildMessageChain {
                +At(sender.id)
            }
            chain = when (sender.id.toInt()) {
                5980403 -> chain.plus(PlainText(" ��簮������mua~"))
                349468958 -> chain.plus(PlainText(" �����Ҳ������?"))
                865734287 -> chain.plus(PlainText(" mu..(����)mua?"))
                744938425 -> chain.plus(PlainText(" ŷ�ὴҪ�Է��أ�Ҫϴ���أ�����Ҫ�ȳ�����")).plus(Face(111))
                else -> {
                    chain.plus(PlainText(" hi~"))
                }
            }
            group.sendMessage(chain)
        }

        case("��") {
            var chain = buildMessageChain {
                +At(sender.id)
            }
            chain = when (sender.id.toInt()) {
                5980403 -> chain.plus(PlainText(" �����")).plus(Face(75))
                else -> chain.plus(PlainText(" ��~"))
            }
            group.sendMessage(chain)
        }

        finding("([��ܳ])".toRegex()) {
            if ((0..100).random() < 25) group.sendMessage("��")
        }

        atBot {
            if(sender.id == 5980403.toLong()){
                if(message.contentToString() == "����"){
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
                +PlainText("${sender.nick}(${sender.id})��${group.name}(${group.id})�ж�����˵��\n")
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