package com.example.perpustakaan.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "book_table")
class Book : Parcelable {
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0
    val name: String = ""
    val type: String = ""
}