package com.example.perpustakaan.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import org.jetbrains.annotations.NotNull

@Parcelize
@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val book_name:  String,
    val book_genre: String,
    val borrower_name: String,
    val latitude: Double?,
    val longitude: Double?
) : Parcelable