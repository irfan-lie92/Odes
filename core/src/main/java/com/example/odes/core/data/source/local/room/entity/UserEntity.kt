package com.example.odes.core.data.source.local.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.odes.core.domain.model.User

@Entity(tableName = "user")
data class UserEntity(

    @ColumnInfo(name = "email")
    @PrimaryKey
    val email: String,

    @ColumnInfo(name = "password")
    val password: String,

    @ColumnInfo(name = "full_name")
    val name: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String
)

fun UserEntity.toDomain() = User (
    this.email,
    this.name,
    this.password,
    this.phoneNumber
)