package com.example.perpustakaan.application

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.perpustakaan.dao.BookDao
import com.example.perpustakaan.model.Book
import com.example.perpustakaan.repository.BookRepository

@Database(entities = [Book::class], version = 2, exportSchema = false)
abstract class BookDatabase: RoomDatabase() {
    abstract fun bookdao(): BookDao

    companion object {
        private var INSTANCE: BookDatabase? = null

        private val migration1To2: Migration = object: Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE book_table ADD COLUMN latitude Double DEFAULT 0.0")
                database.execSQL("ALTER TABLE book_table ADD COLUMN longitude Double DEFAULT 0.0")
            }

        }

        fun getDatabase(context: Context): BookDatabase? {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookDatabase::class.java,
                    "book_database"
                )
                    .addMigrations(migration1To2)
                    .allowMainThreadQueries()
                    .build()

                INSTANCE = instance
                INSTANCE
            }
        }
    }
}