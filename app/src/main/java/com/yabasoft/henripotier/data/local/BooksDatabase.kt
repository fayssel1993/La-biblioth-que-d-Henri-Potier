package com.yabasoft.henripotier.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.yabasoft.henripotier.data.entities.Book
import com.yabasoft.henripotier.data.local.BooksDatabase.Companion.DB_VERSION
import com.yabasoft.henripotier.data.local.dao.BookDao

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */

@Database(entities = [Book::class], version = DB_VERSION, exportSchema = false)
abstract class BooksDatabase : RoomDatabase() {
    abstract fun getBookDao(): BookDao

    companion object {
        const val DB_VERSION = 2
        const val DB_NAME = "henri_potier.db"
    }
}