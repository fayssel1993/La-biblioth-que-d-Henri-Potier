package com.yabasoft.henripotier.data.local.dao

import androidx.room.*
import com.yabasoft.henripotier.data.entities.Book
import kotlinx.coroutines.flow.Flow

/**
 * Created by Fayssel Yabahddou on 4/14/22.
 */
@Dao
interface BookDao {
    @Query("SELECT * FROM books ORDER BY price ASC")
    fun getAll(): Flow<List<Book>>

    @Query("SELECT isbn FROM books")
    suspend fun findAllIsbnCode(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(book: Book)

    @Delete
    suspend fun delete(book: Book)
}