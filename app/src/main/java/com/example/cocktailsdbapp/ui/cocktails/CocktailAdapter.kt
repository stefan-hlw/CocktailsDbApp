package com.example.cocktailsdbapp.ui.cocktails


import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.ItemCocktailBinding
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.ui.BaseAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CocktailAdapter(
    private val onItemClickListener: OnItemClickListener? = null,
    private val onFavoriteClickListener: OnFavoriteClickListener? = null
) : BaseAdapter<Cocktail, ItemCocktailBinding>(
    inflate = { inflater, parent, attachToParent ->
        ItemCocktailBinding.inflate(inflater, parent, attachToParent)
    }
) {

    private var isFavoriteClickable = true

    interface OnItemClickListener {
        fun openCocktailDetails(cocktailId: String)
    }

    interface OnFavoriteClickListener {
        fun favoriteCocktail(cocktail: Cocktail)

    }

    override fun bindItem(holder: ViewHolder, item: Cocktail) {
        with(binding) {
            tvCocktailName.text = item.strDrink
            ivFavorite.isSelected = item.isFavorite

            cvItem.setOnClickListener {
                onItemClickListener?.openCocktailDetails(item.idDrink)
            }
            ivFavorite.setOnClickListener {
                if (isFavoriteClickable) {
                    isFavoriteClickable = false

                    ivFavorite.isSelected = !ivFavorite.isSelected

                    // Load the animation
                    val animationId = if (ivFavorite.isSelected) {
                        R.anim.ic_favorite_animation // Selection animation
                    } else {
                        R.anim.ic_favorite_animation_deselect // Deselection animation
                    }
                    val animation = AnimationUtils.loadAnimation(root.context, animationId)

                    // Apply the animation to the ImageView
                    ivFavorite.startAnimation(animation)

                    onFavoriteClickListener?.favoriteCocktail(item)

                    // Enable clickable after a delay (e.g., 1000 milliseconds)
                    CoroutineScope(Dispatchers.Main).launch {
                        delay(600)
                        isFavoriteClickable = true
                    }
                }
            }
            Glide.with(root.context)
                .load(item.strDrinkThumb)
                .into(ivDrink)
        }
    }

    override fun getDiffCallback(
        oldList: List<Cocktail>,
        newList: List<Cocktail>
    ): DiffUtil.Callback {
        return DiffCallback(
            oldList = oldList,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.idDrink == newItem.idDrink
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    }

}
