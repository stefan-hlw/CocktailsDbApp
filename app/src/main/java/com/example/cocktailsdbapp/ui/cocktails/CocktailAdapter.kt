package com.example.cocktailsdbapp.ui.cocktails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cocktailsdbapp.databinding.ItemCocktailBinding
import com.example.cocktailsdbapp.network.Cocktail

class CocktailAdapter(private val cocktailList: List<Cocktail>) : RecyclerView.Adapter<CocktailAdapter.CocktailViewHolder>() {

    private var _binding: ItemCocktailBinding? = null
    private val binding get() = _binding!!

    private var cocktails: List<Cocktail> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CocktailViewHolder {
        _binding = ItemCocktailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CocktailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CocktailViewHolder, position: Int) {
//        val currentCocktail = cocktailList[position]
//        val animation: Animation =
//            AnimationUtils.loadAnimation(binding.root.context, R.anim.animation_resources)
//        holder.itemView.startAnimation(animation)
        holder.bindView(cocktailList[position])
    }


    inner class CocktailViewHolder(private val binding: ItemCocktailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(cocktail: Cocktail) {
            with(binding) {
                tvCocktailName.text = cocktail.strDrink
//                imageViewFavorite.drawable =
                Glide.with(root.context)
                    .load(cocktail.strDrinkThumb)
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.placeholder_image)
                    .into(ivDrink)
            }
        }

    }

    fun updateData(newCocktails: List<Cocktail>) {
        newCocktails.let {
            val diffResult = DiffUtil.calculateDiff(CocktailDiffCallback(cocktails, newCocktails))
            cocktails = newCocktails
            diffResult.dispatchUpdatesTo(this)
        }
    }
    override fun getItemCount(): Int {
        return cocktailList.size
    }


    inner class CocktailDiffCallback(
        private val oldList: List<Cocktail>,
        private val newList: List<Cocktail>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Check if items have the same unique identifier.
            return oldList[oldItemPosition].idDrink == newList[newItemPosition].idDrink
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            // Check if the contents of items are the same.
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
