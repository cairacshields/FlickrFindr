package com.example.flickrfindr.base.modules

import com.example.flickrfindr.viewmodel.FlickrItemViewModel
import org.koin.dsl.module

val viewModel = module {
    single {
        FlickrItemViewModel(get())
    }
}