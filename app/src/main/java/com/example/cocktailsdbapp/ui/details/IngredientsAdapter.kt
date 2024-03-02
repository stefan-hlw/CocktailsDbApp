package com.example.cocktailsdbapp.ui.details

import androidx.recyclerview.widget.DiffUtil
import com.example.cocktailsdbapp.databinding.ItemIngredientBinding
import com.example.cocktailsdbapp.ui.BaseAdapter

class IngredientsAdapter :
    BaseAdapter<Pair<String, String>, ItemIngredientBinding>(
        inflate = { inflater, parent, attachToParent ->
            ItemIngredientBinding.inflate(inflater, parent, attachToParent)
        }
    ) {

    override fun bindItem(holder: ViewHolder, item: Pair<String, String>) {
        with(holder.binding) {
            tvIngredient.text = item.first
            tvMeasure.text = item.second
        }
    }

    override fun getDiffCallback(
        oldList: List<Pair<String, String>>,
        newList: List<Pair<String, String>>
    ): DiffUtil.Callback {
        return DiffCallback(
            oldList = oldList,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem.first == newItem.first
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    }
}
