package com.briefing.brifieng6.database.`object`

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "register_data")
data class RegisterData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "login")
    val login: String,

    @ColumnInfo(name = "password_")
    val password_: String,

    @ColumnInfo(name = "token")
    val token: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "name_")
    val name_: String,

    @ColumnInfo(name = "surname")
    val surname: String,

    @ColumnInfo(name = "teacher")
    val teacher: Boolean
)