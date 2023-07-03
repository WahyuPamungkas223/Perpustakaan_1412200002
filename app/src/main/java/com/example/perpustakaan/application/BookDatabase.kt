package com.example.perpustakaan.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.perpustakaan.dao.BookDao
import com.example.perpustakaan.model.Book
import com.example.perpustakaan.repository.BookRepository

@Database(entities = [Book::class], version = 1, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookdao(): BookDao

    companion object {
        private var INSTANCE: BookDatabase? = null


        fun getDatabase(context: Context): BookDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                )
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                INSTANCE
            }
        }
    }
}