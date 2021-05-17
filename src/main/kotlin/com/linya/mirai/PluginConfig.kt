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
        ���ݿ��ַ��֧��MariaDB��MySQL���ݿ�.
        Ĭ��ֵ��localhost
        """
    )
    val address: String = "localhost",
    @Comment(
        """
        ���ݿ�����û�.
        Ĭ��ֵ��root
    """
    )
    val user: String = "root",
    @Comment("���ݿ��������")
    val password: String = "",
    @Comment("���ݿ��������ʾҪ�����ݴ洢�����������")
    var table: String = "",
    @Comment("SQL���ӵĸ��Ӳ������ǵ����ʺſ�ͷ")
    val AdditionalParameters: String = "",
    @Comment("�����������Ҳ�������ӳصĴ�С������")
    var maximumPoolSize: Int? = 10
)