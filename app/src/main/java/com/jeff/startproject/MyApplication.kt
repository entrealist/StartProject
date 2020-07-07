package com.jeff.startproject

import android.app.Application
import com.jeff.startproject.di.apiModule
import com.jeff.startproject.di.appModule
import com.jeff.startproject.di.dbModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApplication : Application() {

    companion object {
        lateinit var self: Application
        fun applicationContext(): Application {
            return self
        }
    }

    init {
        self = this
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MyApplication)
            modules(listOf(appModule, dbModule, apiModule))
        }
    }
}