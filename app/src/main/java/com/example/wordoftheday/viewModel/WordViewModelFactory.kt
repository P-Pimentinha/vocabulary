package com.example.wordoftheday.viewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.wordoftheday.database.WordRepository

class WordViewModelFactory(val app: Application, private val wordRepository: WordRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WordViewModel::class.java)) {
            return WordViewModel(app, wordRepository) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}


