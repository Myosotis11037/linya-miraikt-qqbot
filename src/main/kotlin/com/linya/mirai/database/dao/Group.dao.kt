package com.linya.mirai.database.dao

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.boolean
import org.ktorm.schema.long

interface Group : Entity<Group> {
    val id: Long
    val liveStatus: Boolean
}

object Groups: Table<Group>("Groups"){
    val id = long("id")
    val liveStatus = boolean("liveStatus")
}