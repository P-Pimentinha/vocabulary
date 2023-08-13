package com.example.wordoftheday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.wordoftheday.MainActivity

import com.example.wordoftheday.R
import com.example.wordoftheday.adapter.WordAdapter
import com.example.wordoftheday.databinding.FragmentHomeBinding
import com.example.wordoftheday.databinding.FragmentNewWordBinding
import com.example.wordoftheday.model.Word
import com.example.wordoftheday.viewModel.WordViewModel


class NewWordFragment : Fragment(R.layout.fragment_new_word), MenuProvider {

    private var _binding: FragmentNewWordBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordsViewModel: WordViewModel
    private lateinit var wordAdapter: WordAdapter
    private lateinit var mView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.addMenuProvider(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNewWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordsViewModel = (activity as MainActivity).wordViewModel

        mView = view

    }


    override fun onDestroyView() {
        super.onDestroyView()
        activity?.removeMenuProvider(this)
        _binding = null
    }


    private fun saveWord(view: View) {
        val germanWord = binding.etGermanWord.text.toString().trim()
        val englishWord = binding.etEnglishWord.text.toString().trim()
        val example = binding.etExample.text.toString().trim()

        if (germanWord.isNotEmpty()) {
            val word = Word(0, germanWord, englishWord, example)
            wordsViewModel.addWord(word)
            Toast.makeText(mView.context, "Word Saved", Toast.LENGTH_LONG).show()

            view.findNavController().navigate(R.id.action_newWordFragment_to_homeFragment)
        } else {
            Toast.makeText(mView.context, "Please enter a word", Toast.LENGTH_LONG).show()
        }

    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_new_word, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.menu_save -> {
                saveWord(mView)
            }
        }
        return true
    }


}