package com.example.flickrfindr.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.flickrfindr.service.model.BookMark
import com.example.flickrfindr.service.model.Photo

@Database(entities = [Photo::class, BookMark::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract val flickrItemDao: FlickrItemDao
    abstract val bookMarkDao: BookMarkDao
}