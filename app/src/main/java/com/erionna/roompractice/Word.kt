package com.erionna.roompractice

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "word")
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val text: String,
    val mean: String,
    val type: String
)
