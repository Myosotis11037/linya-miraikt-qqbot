package com.linya.mirai

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.AutoSavePluginConfig
import net.mamoe.mirai.console.data.value
import net.mamoe.yamlkt.Comment

object PluginConfig : AutoSavePluginConfig("linya.linyaBotConfig") {
    val myDataBase by value<DatabaseConfig>()
}

@Serializable
data class DatabaseConfig(
    @Comment(
        """
        数据库地址，支持MariaDB和MySQL数据库.
        默认值：localhost
        """
    )
    val address: String = "localhost",
    @Comment(
        """
        数据库登入用户.
        默认值：root
    """
    )
    val user: String = "root",
    @Comment("数据库登入密码")
    val password: String = "",
    @Comment("数据库表单，表示要将数据存储在这个表单里")
    var table: String = "",
    @Comment("SQL连接的附加参数，记得以问号开头")
    val AdditionalParameters: String = "",
    @Comment("最大连接数，也许是连接池的大小？？？")
    var maximumPoolSize: Int? = 10,
    @Comment("bilibiliCookie")
    val biliCookie: String = "",
    @Comment("bilibiliToken")
    val biliToken: String = "",
    @Comment("百度贴吧的登录cookie")
    val tiebaCookie: String = "",
    @Comment("网易云音乐搜索功能需要的cookie")
    val netcloudCookie: String = "",
    @Comment("loliconAPIkey")
    val loliconKey: String = ""
)