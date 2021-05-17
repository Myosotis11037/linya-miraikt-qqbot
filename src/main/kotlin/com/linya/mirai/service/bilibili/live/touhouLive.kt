package com.linya.mirai.service.bilibili.live

import com.linya.mirai.service.tool.*
import net.mamoe.mirai.contact.getMemberOrFail
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages

fun touhouLiveEntrance(){
    GlobalEventChannel.subscribeGroupMessages {
        case("直播help"){
            group.sendMessage("目前红群直播的开启功能已经基本上由凛夜哥哥写完了，下面是大概的使用方法~\n" +
                "首先，直播开启的权限是由白名单决定的，白名单存储在服务器上，只能由红群和tfcc群的管理员以及凛夜本人进行添加。添加方法为：【白名单添加 qq号】。管理员必须要注意的是：添加完qq号以后，请马上输入【添加id 名字】来添加qq号成员对应的名字，不然白名单可能会无法正常运行！\n" +
                "然后开启直播的方法是【开始直播】。只有白名单上的成员才能开启直播哦！如果你想查看白名单的成员，请输入【直播白名单】，或者是之前莉莉白的查看方式【！白名单】。这里凛夜哥哥是为了迎合大家之前的习惯做的~\n" +
                "如果需要修改白名单，请发送【白名单删除 qq号】。请管理员注意，删除完白名单上的qq号以后请务必删除qq号对应的id！方法为【删除id 名字】！\n" +
                "如果有人在开启直播，其他白名单上的成员还发送了【开始直播】，那么机器人会提醒你有人在使用直播~如果使用直播的人需要下线，请发送【关闭直播】。而且关闭直播的权限只有开启直播的本人有哦~\n" +
                "在进行tfcc比赛时可能会需要随时更改直播标题，请发送【修改直播标题 目标名字】即可~")
        }

        finding("^(直播白名单|！白名单)\$".toRegex()){
            var nameList = "直播白名单如下：\n"
            getUserInformation().forEach{
                nameList += "${bot.getGroupOrFail(909918392).getMemberOrFail(it.id!!).nick}(${it.id})\n"
            }
            group.sendMessage(nameList)
        }

        finding("^((白名单删除|删除id) ([0-9]+))\$".toRegex()){
            val asker = sender.id
            if(getUserAuthority(asker)!!) {
                val id = it.groups[3]!!.value
                val flag = deleteUserAuthority(id.toLong())
                group.sendMessage(
                    when(flag){
                        true -> "成功从白名单上删除此成员"
                        else -> "白名单上没有这个成员！"
                    }
                )
            }
            else{
                group.sendMessage("你不是超级管理员，你没有权限进行此操作！")
            }
        }

        finding("^((白名单添加|添加id) ([0-9]+))\$".toRegex()){
            val asker = sender.id
            if(getUserAuthority(asker)!!){
                val id = it.groups[3]!!.value
                val flag = addUserAuthority(id.toLong())
                group.sendMessage(
                    when(flag){
                        true -> "成功在白名单上添加此成员！"
                        else -> "白名单上已经有了这个成员！"
                    }
                )
            }
            else{
                group.sendMessage("你不是超级管理员，没有权限进行此操作！")
            }
        }

        case("开始直播"){
            if(getGroupAuthority(group.id)){
                val liveUsers = getUserAuthority(sender.id)
                val liveStatus = getLiveStatus()
                if(liveUsers!!){
                    when(liveStatus){
                        1 -> group.sendMessage("直播间现在是开启状态，请不要重复开启直播间！")
                        else -> {
                            val startStatus = startLive()
                            when(startStatus){
                                true -> group.sendMessage("直播间开启成功")
                                else -> group.sendMessage("直播间开启失败，可能是cookie错误")
                            }
                        }
                    }
                }
                else group.sendMessage("你不在白名单上，无权开启直播！")
            }
        }

        case("关闭直播"){
            if(getGroupAuthority(group.id)){
                val liveUsers = getUserAuthority(sender.id)
                val liveStatus = getLiveStatus()
                if(liveUsers!!){
                    when(liveStatus){
                        0 -> group.sendMessage("直播间现在是关闭状态，请不要重复关闭直播间！")
                        else -> {
                            val startStatus = startLive()
                            when(startStatus){
                                true -> group.sendMessage("直播间关闭成功")
                                else -> group.sendMessage("直播间关闭失败，可能是cookie错误")
                            }
                        }
                    }
                }
                else group.sendMessage("你不在白名单上，无权关闭直播！")
            }
        }

        startsWith("修改直播标题 "){
            if(getGroupAuthority(group.id)) {
                val newLiveName = message.contentToString().replace("修改直播标题 ", "")
                val liveUsers = getUserAuthority(sender.id)
                if(liveUsers!!) {
                    val nameStatus = changeLiveName(newLiveName)
                    when(nameStatus){
                        true -> group.sendMessage("直播间标题修改为：${newLiveName}")
                        else -> group.sendMessage("直播间标题修改失败，可能是cookie错误")
                    }
                }

            }
        }
    }
}