package com.example.flickrfindr.service.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.flickrfindr.service.model.BookMark

@Dao
interface BookMarkDao {
    @Query("SELECT * FROM bookmarks")
    fun findAll(): LiveData<List<BookMark>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(items: BookMark)
}