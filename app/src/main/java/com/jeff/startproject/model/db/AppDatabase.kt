package com.jeff.startproject.model.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jeff.startproject.dao.UserDao

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
}