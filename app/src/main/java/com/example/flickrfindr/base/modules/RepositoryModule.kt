package com.example.flickrfindr.base.modules

import com.example.flickrfindr.service.repository.FlickrItemRepository
import org.koin.dsl.module

val repositoryModule = module {
    single {
        FlickrItemRepository(get(), get(), get())
    }
}