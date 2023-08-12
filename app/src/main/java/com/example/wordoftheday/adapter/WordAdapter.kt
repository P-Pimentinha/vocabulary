package com.example.wordoftheday.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.wordoftheday.databinding.WordItemBinding
import com.example.wordoftheday.fragments.HomeFragmentDirections
import com.example.wordoftheday.model.Word
import java.util.Random


class WordAdapter : RecyclerView.Adapter<WordAdapter.WordViewHolder>() {

    //initializes dataBinding with word_item.xml
    class WordViewHolder(val itemBinding: WordItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    /*DiffUtil.ItemCallback is a utility class provided by Android's RecyclerView library
    to efficiently calculate the differences between two lists of items and update a
     RecyclerView accordingly. This is crucial for optimizing the performance of RecyclerViews,
     especially when dealing with dynamic data updates.*/
    private val differCallBack = object : DiffUtil.ItemCallback<Word>() {
        override fun areItemsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem.id == newItem.id &&
                    oldItem.germanWord == newItem.germanWord &&
                    oldItem.englishWord == newItem.englishWord &&
                    oldItem.examples == newItem.examples
        }

        override fun areContentsTheSame(oldItem: Word, newItem: Word): Boolean {
            return oldItem == newItem
        }

    }


    /* AsyncListDiffer is a utility class provided by Android's RecyclerView library that helps you
    efficiently calculate and apply the differences between two lists of data to update a RecyclerView's
    items. It's an improvement over the older DiffUtil class and is designed to perform calculations on
    a background thread, making it suitable for larger datasets without blocking the main UI thread. */

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordAdapter.WordViewHolder {
        return WordViewHolder(
            WordItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val currentWord = differ.currentList[position]

        holder.itemBinding.tvWordGerman.text = currentWord.germanWord
        holder.itemBinding.tvWordEnglish.text = currentWord.englishWord

        val random = Random()
        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )

        holder.itemBinding.ibColor.setBackgroundColor(color)

        holder.itemView.setOnClickListener {
            val direction =
                HomeFragmentDirections.actionHomeFragmentToUpdateWordFragment(currentWord)
            it.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }


}