package com.example.wordoftheday.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

import kotlinx.parcelize.Parcelize

// Parcelize erialization and deserialization when passing data between fragments
@Entity(tableName = "words")
@Parcelize
data class Word(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val germanWord: String,
    val englishWord: String,
    val examples: String
) : Parcelable


