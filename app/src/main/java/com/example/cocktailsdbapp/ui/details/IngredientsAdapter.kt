package com.example.cocktailsdbapp.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailsdbapp.databinding.ItemIngredientBinding

class IngredientsAdapter(private val ingredientsWithMeasures: List<Pair<String, String>>) :
    RecyclerView.Adapter<IngredientsAdapter.IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding =
            ItemIngredientBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredientsWithMeasures[position])
    }

    override fun getItemCount(): Int = ingredientsWithMeasures.size

    inner class IngredientViewHolder(private val binding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredientWithMeasure: Pair<String, String>) {
            with(binding) {
                tvIngredient.text = ingredientWithMeasure.first
                tvMeasure.text = ingredientWithMeasure.second
            }
        }
    }
}
