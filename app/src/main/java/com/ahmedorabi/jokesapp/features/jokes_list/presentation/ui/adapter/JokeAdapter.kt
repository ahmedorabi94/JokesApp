package com.ahmedorabi.jokesapp.features.jokes_list.presentation.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ahmedorabi.jokesapp.databinding.JokeItemBinding
import com.ahmedorabi.jokesapp.domain.Joke

class JokeAdapter(private val callback: (joke: Joke) -> Unit) :
    ListAdapter<Joke, JokeAdapter.MyViewHolder>(DiffCallback) {


    companion object DiffCallback : DiffUtil.ItemCallback<Joke>() {
        override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
            return oldItem.type == newItem.type
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.cardView.setOnClickListener {
            callback.invoke(item)
        }
    }


    class MyViewHolder(var binding: JokeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(item: Joke) {
            binding.jokeTypeTV.text = "Joke Type : ${item.type}"
            binding.jokeCategoryTv.text = "Joke Category : ${item.category}"
            //   binding.tvTime.text =  "Creation Time : ${Calendar.getInstance().time}"


        }

    }


}