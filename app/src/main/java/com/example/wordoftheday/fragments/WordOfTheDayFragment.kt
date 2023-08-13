package com.example.wordoftheday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.wordoftheday.MainActivity
import com.example.wordoftheday.R

import com.example.wordoftheday.databinding.FragmentWordOfTheDayBinding
import com.example.wordoftheday.viewModel.WordViewModel
import java.util.Random


class WordOfTheDayFragment : Fragment(R.layout.fragment_word_of_the_day), MenuProvider {

    private var _binding: FragmentWordOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordsViewModel: WordViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.addMenuProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWordOfTheDayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordsViewModel = (activity as MainActivity).wordViewModel

        generateRandomWord()

        binding.fabNewRandom.setOnClickListener {
            generateRandomWord()
        }
    }

    private fun generateRandomWord() {
        wordsViewModel.getAllWords().observe(viewLifecycleOwner, { words ->
            if (words.isNotEmpty()) {
                val random = Random()
                val randIndex = random.nextInt(words.size)
                val randomWord = words[randIndex]
                binding.tvNewGerman.text = randomWord.germanWord
                binding.tvNewEnglish.text = randomWord.englishWord
            }

        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        activity?.removeMenuProvider(this)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_return_home, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_home -> {
                view?.findNavController()?.navigate(
                    R.id.action_wordOfTheDayFragment_to_homeFragment
                )
            }
        }
        return true
    }
}

