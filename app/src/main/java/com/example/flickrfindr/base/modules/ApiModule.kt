package com.example.flickrfindr.base.modules

import com.example.flickrfindr.service.repository.FlickrItemApi
import org.koin.dsl.module
import retrofit2.Retrofit

val apiModule = module {
    single {
        get<Retrofit>().create(FlickrItemApi::class.java)
    }
}