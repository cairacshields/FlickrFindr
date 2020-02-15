package com.example.flickrfindr.base.modules

import com.google.gson.Gson
import org.koin.dsl.module

val appModule = module {
    factory {
        Gson()
    }
}