package com.linya.mirai.service.bilibili.live

import com.linya.mirai.service.tool.*
import net.mamoe.mirai.contact.getMemberOrFail
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages

fun touhouLiveEntrance(){
    GlobalEventChannel.subscribeGroupMessages {
        case("ֱ��help"){
            group.sendMessage("Ŀǰ��Ⱥֱ���Ŀ��������Ѿ�����������ҹ���д���ˣ������Ǵ�ŵ�ʹ�÷���~\n" +
                "���ȣ�ֱ��������Ȩ�����ɰ����������ģ��������洢�ڷ������ϣ�ֻ���ɺ�Ⱥ��tfccȺ�Ĺ���Ա�Լ���ҹ���˽�����ӡ���ӷ���Ϊ������������� qq�š�������Ա����Ҫע����ǣ������qq���Ժ����������롾���id ���֡������qq�ų�Ա��Ӧ�����֣���Ȼ���������ܻ��޷��������У�\n" +
                "Ȼ����ֱ���ķ����ǡ���ʼֱ������ֻ�а������ϵĳ�Ա���ܿ���ֱ��Ŷ���������鿴�������ĳ�Ա�������롾ֱ������������������֮ǰ����׵Ĳ鿴��ʽ��������������������ҹ�����Ϊ��ӭ�ϴ��֮ǰ��ϰ������~\n" +
                "�����Ҫ�޸İ��������뷢�͡�������ɾ�� qq�š��������Աע�⣬ɾ����������ϵ�qq���Ժ������ɾ��qq�Ŷ�Ӧ��id������Ϊ��ɾ��id ���֡���\n" +
                "��������ڿ���ֱ���������������ϵĳ�Ա�������ˡ���ʼֱ��������ô�����˻�������������ʹ��ֱ��~���ʹ��ֱ��������Ҫ���ߣ��뷢�͡��ر�ֱ���������ҹر�ֱ����Ȩ��ֻ�п���ֱ���ı�����Ŷ~\n" +
                "�ڽ���tfcc����ʱ���ܻ���Ҫ��ʱ����ֱ�����⣬�뷢�͡��޸�ֱ������ Ŀ�����֡�����~")
        }

        finding("^(ֱ��������|��������)\$".toRegex()){
            var nameList = "ֱ�����������£�\n"
            getUserInformation().forEach{
                nameList += "${bot.getGroupOrFail(909918392).getMemberOrFail(it.id!!).nick}(${it.id})\n"
            }
            group.sendMessage(nameList)
        }

        finding("^((������ɾ��|ɾ��id) ([0-9]+))\$".toRegex()){
            val asker = sender.id
            if(getUserAuthority(asker)!!) {
                val id = it.groups[3]!!.value
                val flag = deleteUserAuthority(id.toLong())
                group.sendMessage(
                    when(flag){
                        true -> "�ɹ��Ӱ�������ɾ���˳�Ա"
                        else -> "��������û�������Ա��"
                    }
                )
            }
            else{
                group.sendMessage("�㲻�ǳ�������Ա����û��Ȩ�޽��д˲�����")
            }
        }

        finding("^((���������|���id) ([0-9]+))\$".toRegex()){
            val asker = sender.id
            if(getUserAuthority(asker)!!){
                val id = it.groups[3]!!.value
                val flag = addUserAuthority(id.toLong())
                group.sendMessage(
                    when(flag){
                        true -> "�ɹ��ڰ���������Ӵ˳�Ա��"
                        else -> "���������Ѿ����������Ա��"
                    }
                )
            }
            else{
                group.sendMessage("�㲻�ǳ�������Ա��û��Ȩ�޽��д˲�����")
            }
        }

        case("��ʼֱ��"){
            if(getGroupAuthority(group.id)){
                val liveUsers = getUserAuthority(sender.id)
                val liveStatus = getLiveStatus()
                if(liveUsers!!){
                    when(liveStatus){
                        1 -> group.sendMessage("ֱ���������ǿ���״̬���벻Ҫ�ظ�����ֱ���䣡")
                        else -> {
                            val startStatus = startLive()
                            when(startStatus){
                                true -> group.sendMessage("ֱ���俪���ɹ�")
                                else -> group.sendMessage("ֱ���俪��ʧ�ܣ�������cookie����")
                            }
                        }
                    }
                }
                else group.sendMessage("�㲻�ڰ������ϣ���Ȩ����ֱ����")
            }
        }

        case("�ر�ֱ��"){
            if(getGroupAuthority(group.id)){
                val liveUsers = getUserAuthority(sender.id)
                val liveStatus = getLiveStatus()
                if(liveUsers!!){
                    when(liveStatus){
                        0 -> group.sendMessage("ֱ���������ǹر�״̬���벻Ҫ�ظ��ر�ֱ���䣡")
                        else -> {
                            val startStatus = startLive()
                            when(startStatus){
                                true -> group.sendMessage("ֱ����رճɹ�")
                                else -> group.sendMessage("ֱ����ر�ʧ�ܣ�������cookie����")
                            }
                        }
                    }
                }
                else group.sendMessage("�㲻�ڰ������ϣ���Ȩ�ر�ֱ����")
            }
        }

        startsWith("�޸�ֱ������ "){
            if(getGroupAuthority(group.id)) {
                val newLiveName = message.contentToString().replace("�޸�ֱ������ ", "")
                val liveUsers = getUserAuthority(sender.id)
                if(liveUsers!!) {
                    val nameStatus = changeLiveName(newLiveName)
                    when(nameStatus){
                        true -> group.sendMessage("ֱ��������޸�Ϊ��${newLiveName}")
                        else -> group.sendMessage("ֱ��������޸�ʧ�ܣ�������cookie����")
                    }
                }

            }
        }
    }
}