package com.linya.mirai.service.tool

import com.linya.mirai.database.Database
import com.linya.mirai.database.dao.Groups
import com.linya.mirai.database.dao.Users
import org.ktorm.dsl.*

data class User(
    val id: Long?,
    val live_admin: Boolean?,
    val live_superadmin: Boolean?,
    val bot: Boolean?,
)

data class Group(
    val id: Int?,
    val liveStatus: Boolean?,
)

fun getUserInformation(): List<User> {
    return Database.db
        .from(Users)
        .select()
        .map { row ->
            User(
                row[Users.id],
                row[Users.live_admin],
                row[Users.live_superadmin],
                row[Users.bot]
            )
        }

}

fun getUserAuthority(id: Long): Boolean? {
    var data: Boolean? = null
    Database.db
        .from(Users)
        .select()
        .where { (Users.id eq id) }.forEach { row ->
            data = row[Users.live_admin]
        }
    println(data)
    return data
}

fun deleteUserAuthority(id: Long): Boolean {
    var result: Boolean? = null
    Database.db
        .from(Users)
        .select()
        .where { Users.id eq id }
        .forEach { row -> result = row[Users.bot] }

    if (result != null) {
        Database.db
            .delete(Users) { Users.id eq id }
        return true
    } else return false
}

fun addUserAuthority(id: Long): Boolean {
    var result: Boolean? = null
    Database.db
        .from(Users)
        .select()
        .where { Users.id eq id }
        .forEach { row -> result = row[Users.bot] }

    if (result == null) {
        Database.db
            .insert(Users) {
                set(it.id, id)
                set(it.live_admin, true)
                set(it.live_superadmin, true)
                set(it.bot, false)
            }
        return true
    } else return false
}

fun getGroupAuthority(id: Long): Boolean {
    var result: Boolean? = null
    Database.db
        .from(Groups)
        .select()
        .where { Groups.id eq id }
        .forEach { row -> result = row[Groups.liveStatus] }

    return result != null

}
