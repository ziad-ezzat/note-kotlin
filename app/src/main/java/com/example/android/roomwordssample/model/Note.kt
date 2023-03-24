package com.example.android.roomwordssample.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word_table")
data class Note(@PrimaryKey @ColumnInfo(name = "word") val word: String)
