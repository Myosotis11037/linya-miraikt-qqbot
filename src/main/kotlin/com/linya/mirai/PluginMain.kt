package com.linya.mirai

import com.linya.mirai.database.Database.connectEntrance
import com.linya.mirai.service.bilibili.live.touhouLiveEntrance
import com.linya.mirai.service.bilibili.touhou.touhouEntrance
import com.linya.mirai.service.bilibili.video.b23ShortLinkEntrance
import com.linya.mirai.service.bilibili.video.bilibiliVideoEntrance
import com.linya.mirai.service.netcloud.musicEntrance
import com.linya.mirai.service.setu.eroEntrance
import com.linya.mirai.service.someThingEntrance
import com.linya.mirai.service.tieba.tiebaSignEntrance
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.version
import net.mamoe.mirai.utils.info

object Version {
    const val ID = "com.linya.plugin"
    const val NAME = "linya-miraikt-qqbot"
    const val PLUGINVERSION = "0.1.0"
}

object PluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = Version.ID,
        name = Version.NAME,
        version = Version.PLUGINVERSION
    )
) {
    override fun onEnable() {

        PluginConfig.reload()
        connectEntrance()


        logger.info { "插件加载完成! 版本：$version Java版本:${System.getProperty("java.version")}" }

        eroEntrance()
        bilibiliVideoEntrance()
        b23ShortLinkEntrance()
        someThingEntrance()
        touhouEntrance()
        touhouLiveEntrance()
        tiebaSignEntrance()
        musicEntrance()

    }
}