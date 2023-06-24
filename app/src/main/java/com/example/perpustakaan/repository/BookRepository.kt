package com.example.perpustakaan.repository

import com.example.perpustakaan.dao.BookDao
import com.example.perpustakaan.model.Book
import kotlinx.coroutines.flow.Flow

class BookRepository(private val BookDao: BookDao) {
    val allBook: Flow<List<Book>> = BookDao.getAllBook()

    suspend fun insertBook(book: Book) {
        BookDao.insertBook(book)
    }

    suspend fun deleteBook(book: Book) {
        BookDao.deleteBook(book)
    }

    suspend fun updateBook(book: Book) {
        BookDao.updateBook(book)
    }
}
