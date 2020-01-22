package com.jeff.startproject.di

import androidx.room.Room
import com.jeff.startproject.Constant
import com.jeff.startproject.MyApplication
import com.jeff.startproject.dao.UserDao
import com.jeff.startproject.model.db.AppDatabase
import org.koin.dsl.module

val dbModule = module {
    single { provideAppDatabase() }
    single { provideUserDao(get()) }
}

fun provideAppDatabase(): AppDatabase {
    return Room.databaseBuilder(
        MyApplication.applicationContext(),
        AppDatabase::class.java,
        Constant.DB_NAME
    )
        .allowMainThreadQueries()
        .fallbackToDestructiveMigration()
        .build()
}

fun provideUserDao(appDatabase: AppDatabase): UserDao {
    return appDatabase.userDao()
}