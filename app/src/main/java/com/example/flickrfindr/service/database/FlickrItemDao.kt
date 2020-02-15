package com.example.flickrfindr.service.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.flickrfindr.service.model.Photo
import io.reactivex.Single

@Dao
interface FlickrItemDao {
    @Query("SELECT * FROM items")
    fun findAll(): LiveData<List<Photo>>

    @Insert(onConflict = REPLACE)
    fun add(items: List<Photo>)

    @Query("SELECT * FROM items WHERE title = :tag")
    fun findPhotos(tag: String): List<Photo>
}