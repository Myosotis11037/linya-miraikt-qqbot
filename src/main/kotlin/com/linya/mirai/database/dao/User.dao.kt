package com.linya.mirai.database.dao

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.long

interface User : Entity<User> {
    val id: Long
    val admin: Boolean
    val live_superadmin: Boolean
    val bot: Boolean
}

object Users : Table<User>("Users") {
    val id = long("id")
    val live_admin = boolean("live_admin")
    val live_superadmin = boolean("live_superadmin")
    val bot = boolean("bot")
}