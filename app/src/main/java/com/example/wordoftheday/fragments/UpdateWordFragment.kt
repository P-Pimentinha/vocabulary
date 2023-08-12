package com.example.wordoftheday.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.wordoftheday.MainActivity

import com.example.wordoftheday.R
import com.example.wordoftheday.adapter.WordAdapter
import com.example.wordoftheday.databinding.FragmentHomeBinding
import com.example.wordoftheday.databinding.FragmentUpdateWordBinding
import com.example.wordoftheday.model.Word
import com.example.wordoftheday.viewModel.WordViewModel


class UpdateWordFragment : Fragment(R.layout.fragment_update_word) {

    private var _binding: FragmentUpdateWordBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordsViewModel: WordViewModel

    private lateinit var currentWord: Word
    private val args: UpdateWordFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateWordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        wordsViewModel = (activity as MainActivity).wordViewModel
        currentWord = args.word!!

        binding.etGermanWordUpdate.setText(currentWord.germanWord)
        binding.etEnglishWordUpdate.setText(currentWord.englishWord)
        binding.etExampleUpdate.setText(currentWord.examples)

        //if user updates the note
        binding.fabAddWord.setOnClickListener {
            val germanWord = binding.etGermanWordUpdate.text.toString().trim()
            val englishWord = binding.etEnglishWordUpdate.text.toString().trim()
            val example = binding.etExampleUpdate.text.toString().trim()

            if (germanWord.isNotEmpty()) {
                val word = Word(currentWord.id, germanWord, englishWord, example)
                wordsViewModel.updateWord(word)
                view.findNavController().navigate(R.id.action_updateWordFragment_to_homeFragment)
            } else {
                Toast.makeText(context, "Please enter a word", Toast.LENGTH_LONG).show()
            }

        }


    }

    private fun deleteWord() {
        AlertDialog.Builder(activity).apply {
            setTitle("Delete Note")
            setMessage("Delete Word?")
            setPositiveButton("Delete") { _, _ ->
                wordsViewModel.deleteWord(currentWord)
                view?.findNavController()?.navigate(R.id.action_updateWordFragment_to_homeFragment)
            }
            setNegativeButton("Cancel", null)
        }.create().show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_update_word, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete -> {
                deleteWord()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}