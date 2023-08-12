package com.example.wordoftheday.database

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wordoftheday.model.Word

class WordRepository(private val db: WordDatabase) {

    suspend fun insertWord(word: Word) = db.getWordDao().insertWord(word)
    suspend fun updateWord(word: Word) = db.getWordDao().updateWord(word)
    suspend fun deleteWord(word: Word) = db.getWordDao().deleteWord(word)

    fun getAllWords() = db.getWordDao().getAllWords()
    fun searchWord(query: String?) = db.getWordDao().searchWord(query)

}