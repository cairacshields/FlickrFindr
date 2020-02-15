package com.example.flickrfindr.base

import android.content.Context
import com.example.flickrfindr.base.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.logger.Level
import org.koin.core.module.Module

object KoinConfig {
    private val modules = listOf<Module>(
        appModule,
        apiModule,
        networkModule,
        viewModel,
        repositoryModule,
        databaseModule
    )

    fun start(context: Context) {
        startKoin {
            printLogger(Level.DEBUG)
            androidContext(androidContext = context)
            modules(modules)
        }
    }

    fun stop() = stopKoin()
}