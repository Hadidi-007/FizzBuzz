package com.hassine.fizzbuzz.ui.result.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.hassine.fizzbuzz.data.FizzBuzz
import com.hassine.fizzbuzz.databinding.ViewHolderFizzBuzzItemBinding
import com.hassine.fizzbuzz.ui.result.viewholders.FizzBuzzViewHolder

class ResultAdapter(val fizzBuzz: FizzBuzz?, val mapper: (Int) -> String) :
    RecyclerView.Adapter<FizzBuzzViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FizzBuzzViewHolder {
        return FizzBuzzViewHolder(
            ViewHolderFizzBuzzItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: FizzBuzzViewHolder, position: Int) {
        holder.bindView(mapper(position + 1))
    }

    override fun getItemCount(): Int = fizzBuzz?.limitNumber ?: 0

}