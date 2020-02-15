package com.example.flickrfindr.base.modules

import androidx.room.Room
import com.example.flickrfindr.service.database.AppDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "flickrItems.database")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    single {
        get<AppDatabase>().flickrItemDao
    }

    single {
        get<AppDatabase>().bookMarkDao
    }
}