package com.example.wordoftheday

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.wordoftheday.database.WordDatabase
import com.example.wordoftheday.database.WordRepository
import com.example.wordoftheday.databinding.ActivityMainBinding
import com.example.wordoftheday.viewModel.WordViewModel
import com.example.wordoftheday.viewModel.WordViewModelFactory

class MainActivity : AppCompatActivity() {

    lateinit var wordViewModel: WordViewModel
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpViewModel()
    }

    private fun setUpViewModel() {
        val wordRepository = WordRepository(WordDatabase(this))
        val viewModelProviderFactory = WordViewModelFactory(application, wordRepository)
        wordViewModel =
            ViewModelProvider(this, viewModelProviderFactory).get(WordViewModel::class.java)

    }
}