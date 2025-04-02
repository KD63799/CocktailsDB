package com.example.cocktails.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktails.R
import com.example.cocktails.databinding.ItemCocktailBinding
import com.example.cocktails.utils.CocktailAll
import com.squareup.picasso.Picasso

class CocktailAdapter(
    private val onItemClick: (CocktailAll) -> Unit
) : ListAdapter<CocktailAll, CocktailAdapter.CocktailViewHolder>(CocktailDiffCallback()) {

    inner class CocktailViewHolder(private val binding: ItemCocktailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(cocktail: CocktailAll) {
            binding.tvItemTitle.text = cocktail.title

            if (cocktail.imageUrl.isEmpty()) {
                binding.ivItemImage.setImageResource(R.drawable.ic_launcher_foreground)
            } else {
                Picasso.get()
                    .load(cocktail.imageUrl)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(binding.ivItemImage)
            }

            binding.root.setOnClickListener { onItemClick(cocktail) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCocktailBinding.inflate(inflater, parent, false)
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CocktailDiffCallback : DiffUtil.ItemCallback<CocktailAll>() {
    override fun areItemsTheSame(oldItem: CocktailAll, newItem: CocktailAll): Boolean =
        oldItem.id == newItem.id || oldItem.uuid == newItem.uuid

    override fun areContentsTheSame(oldItem: CocktailAll, newItem: CocktailAll): Boolean =
        oldItem == newItem
}
