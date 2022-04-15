package com.yabasoft.henripotier.di

import android.app.Application
import androidx.room.Room
import com.yabasoft.henripotier.data.local.BooksDatabase
import com.yabasoft.henripotier.data.local.BooksDatabase.Companion.DB_NAME
import com.yabasoft.henripotier.data.local.dao.BookDao
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.module

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */

val databaseModule = module {
    fun provideDataBase(application: Application): BooksDatabase {
        return Room.databaseBuilder(application, BooksDatabase::class.java, DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDao(database: BooksDatabase): BookDao = database.getBookDao()

    single { provideDataBase(androidApplication()) }
    single { provideDao(get()) }
}