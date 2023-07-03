package com.example.perpustakaan.application

import android.app.Application
import com.example.perpustakaan.repository.BookRepository

class BookApp: Application() {
    val database by lazy { BookDatabase.getDatabase(this) }
    val repository by lazy {BookRepository(database!!.bookdao())}
}