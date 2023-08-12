package com.example.wordoftheday.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.wordoftheday.model.Word


@Dao
interface WordDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWord(word : Word)

    @Update
    suspend fun updateWord(word : Word)

    @Delete
    suspend fun deleteWord(word : Word)

    @Query("SELECT * FROM WORDS ORDER BY id DESC")
    fun getAllWords() : LiveData<List<Word>>

    @Query("SELECT * FROM WORDS WHERE germanWord LIKE :query OR englishWord LIKE :query")
    fun searchWord(query: String?) : LiveData<List<Word>>

}