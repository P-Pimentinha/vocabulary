package com.example.wordoftheday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView


import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wordoftheday.MainActivity

import com.example.wordoftheday.R
import com.example.wordoftheday.adapter.WordAdapter
import com.example.wordoftheday.databinding.FragmentHomeBinding
import com.example.wordoftheday.model.Word
import com.example.wordoftheday.viewModel.WordViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener,
    MenuProvider {

    // binding
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //viewModel
    private lateinit var wordsViewModel: WordViewModel

    //Adapter
    private lateinit var wordAdapter: WordAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.addMenuProvider(this)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        wordsViewModel = (activity as MainActivity).wordViewModel

        setUpRecyclerView()
        binding.fabAddWord.setOnClickListener {
            it.findNavController().navigate(
                R.id.action_homeFragment_to_newWordFragment
            )
        }

        binding.fabRandomWord.setOnClickListener{
            it.findNavController().navigate(
                R.id.action_homeFragment_to_wordOfTheDayFragment
            )
        }
    }

    private fun setUpRecyclerView() {
        wordAdapter = WordAdapter()

        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
            setHasFixedSize(true)
            adapter = wordAdapter
        }

        activity?.let {
            wordsViewModel.getAllWords().observe(
                viewLifecycleOwner, { word ->
                    wordAdapter.differ.submitList(word)
                    updateUI(word)
                }
            )
        }
    }

    private fun updateUI(word: List<Word>?) {
        if (word != null) {
            if (word.isNotEmpty()) {
                binding.cardVIew.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                binding.fabRandomWord.visibility = View.VISIBLE
            } else {
                binding.cardVIew.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
                binding.fabRandomWord.visibility = View.GONE
            }
        }
    }

    // Menu & SearchVIew
    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.home_menu, menu)
        val searchMenuItem = menu.findItem(R.id.menu_search)
        val searchView = searchMenuItem.actionView as SearchView
        searchView.isSubmitButtonEnabled = false
        searchView.setOnQueryTextListener(this)

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return true
    }


    //SearchView on QueryText operations
    override fun onQueryTextSubmit(query: String?): Boolean {
        // searchWord(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchWord(newText)
        }
        return true
    }

    private fun searchWord(query: String?) {
        val searchQuery = "%$query"
        wordsViewModel.searchWords(searchQuery)
            .observe(this, { list -> wordAdapter.differ.submitList(list) })
    }


    //OnDestroyOperation
    override fun onDestroyView() {
        super.onDestroyView()
        activity?.removeMenuProvider(this)
        _binding = null
    }


}

