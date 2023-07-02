package com.example.perpustakaan.repository

import com.example.perpustakaan.application.BookDatabase
import com.example.perpustakaan.dao.BookDao
import com.example.perpustakaan.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepository(private val bookDao: BookDao) {
    val allBook: Flow<List<Book>> = bookDao.getAllBook()

    suspend fun insertBook(book: Book) {
        bookDao.insertBook(book)
    }

    suspend fun deleteBook(book: Book) {
        bookDao.deleteBook(book)
    }

    suspend fun updateBook(book: Book) {
        bookDao.updateBook(book)
    }
}
