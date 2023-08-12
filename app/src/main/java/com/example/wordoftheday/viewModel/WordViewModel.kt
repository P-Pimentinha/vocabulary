package com.example.wordoftheday.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wordoftheday.database.WordRepository
import com.example.wordoftheday.model.Word
import kotlinx.coroutines.launch

class WordViewModel(app: Application, private val wordRepository: WordRepository) : AndroidViewModel(app) {


    fun addWord (word: Word) = viewModelScope.launch {
        wordRepository.insertWord(word)
    }

    fun deleteWord (word: Word) = viewModelScope.launch {
        wordRepository.deleteWord(word)
    }

    fun updateWord (word: Word) = viewModelScope.launch {
        wordRepository.updateWord(word)
    }

    fun getAllWords() = wordRepository.getAllWords()

    fun searchWords(query: String?) = wordRepository.searchWord(query)

}