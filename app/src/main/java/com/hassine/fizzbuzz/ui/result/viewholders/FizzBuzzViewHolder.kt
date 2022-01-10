package com.hassine.fizzbuzz.ui.result.viewholders

import androidx.recyclerview.widget.RecyclerView
import com.hassine.fizzbuzz.databinding.ViewHolderFizzBuzzItemBinding

class FizzBuzzViewHolder(private val binding: ViewHolderFizzBuzzItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindView(text: String) {
        binding.fizzBuzzText.text = text

    }
}