package com.example.cocktailsdbapp.model

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("strDrink")
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: String,
    var isFavorite: Boolean = false
)
