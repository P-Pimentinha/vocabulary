package com.example.wordoftheday.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.wordoftheday.MainActivity

import com.example.wordoftheday.R
import com.example.wordoftheday.adapter.WordAdapter
import com.example.wordoftheday.databinding.FragmentHomeBinding
import com.example.wordoftheday.model.Word
import com.example.wordoftheday.viewModel.WordViewModel


class HomeFragment : Fragment(R.layout.fragment_home), SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var wordsViewModel: WordViewModel
    private lateinit var wordAdapter: WordAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
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
            } else {
                binding.cardVIew.visibility = View.VISIBLE
                binding.recyclerView.visibility = View.GONE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        menu.clear()
        inflater.inflate(R.menu.home_menu, menu)

        val mMenuSearch = menu.findItem(R.id.menu_search).actionView as SearchView
        mMenuSearch.isSubmitButtonEnabled = false
        mMenuSearch.setOnQueryTextListener(this)
    }

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

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}