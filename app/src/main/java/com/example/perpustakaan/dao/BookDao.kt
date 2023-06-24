package com.example.perpustakaan.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.perpustakaan.model.Book
import kotlinx.coroutines.flow.Flow


@Dao
interface BookDao {
    @Query("SELECT * FROM `book_table`ORDER BY name ASC")
    fun getAllBook(): Flow<List<Book>>

    @Insert
    suspend fun insertBook(book: Book)

    @Delete
    suspend fun deleteBook(book: Book)

    @Update fun updateBook (book: Book)


}