package com.example.flickrfindr.service.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class Photo (
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "owner")
    val owner: String,
    @ColumnInfo(name = "farm")
    val farm: Int,
    @ColumnInfo(name = "server")
    val server: String,
    @ColumnInfo(name = "secret")
    val secret: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "isPublic")
    val isPublic: Int,
    @ColumnInfo(name = "isFriend")
    val isFriend: Int,
    @ColumnInfo(name = "isFamily")
    val isFamily: Int
)
