package com.linya.mirai.database

import com.linya.mirai.PluginConfig
import com.linya.mirai.PluginMain
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import net.mamoe.mirai.utils.info
import net.mamoe.mirai.utils.warning
import org.ktorm.database.Database
import org.ktorm.logging.ConsoleLogger
import org.ktorm.logging.LogLevel


object Database {
    sealed class ConnectionStatus {
        object CONNECTED : ConnectionStatus()
        object DISCONNECTED : ConnectionStatus()
    }

    lateinit var db: Database
    private var connectionStatus: ConnectionStatus = ConnectionStatus.DISCONNECTED

    fun connectEntrance() {
        try {
            db = Database.connect(
                hikariDataSourceProvider(),
                logger = ConsoleLogger(threshold = LogLevel.INFO)
            )
            connectionStatus = ConnectionStatus.CONNECTED
            PluginMain.logger.info { "Database ${PluginConfig.myDataBase.table} is connected." }
        } catch (ex: Exception) {
            when (ex) {
                //当配置文件的配置不符合要求时throw
                is InvalidDatabaseConfigException -> {
                    throw ex
                }
            }
        }
    }

    private fun hikariDataSourceProvider(): HikariDataSource = HikariDataSource(HikariConfig().apply {
        when {
            PluginConfig.myDataBase.address == "" -> throw InvalidDatabaseConfigException("Database address is not set in config file ${PluginConfig.saveName}.")
            PluginConfig.myDataBase.table == "" -> {
                PluginMain.logger.warning { "Database table is not set in config file ${PluginConfig.saveName} and now it will be default value 'sctimetabledb'." }
                PluginConfig.myDataBase.table = "mirai-404"
            }
            PluginConfig.myDataBase.user == "" -> throw InvalidDatabaseConfigException("Database user is not set in config file ${PluginConfig.saveName}.")
            PluginConfig.myDataBase.password == "" -> throw InvalidDatabaseConfigException("Database password is not set in config file ${PluginConfig.saveName}.")
            PluginConfig.myDataBase.maximumPoolSize == null -> {
                PluginMain.logger.warning { "Database maximumPoolSize is not set in config file ${PluginConfig.saveName} and now it will be default value 10." }
                PluginConfig.myDataBase.maximumPoolSize = 10
            }
        }
        jdbcUrl =
            "jdbc:mysql://${PluginConfig.myDataBase.address}/${PluginConfig.myDataBase.table}${PluginConfig.myDataBase.AdditionalParameters}"
        driverClassName = "com.mysql.cj.jdbc.Driver"
        username = PluginConfig.myDataBase.user
        password = PluginConfig.myDataBase.password
        maximumPoolSize = PluginConfig.myDataBase.maximumPoolSize!!
    })
}