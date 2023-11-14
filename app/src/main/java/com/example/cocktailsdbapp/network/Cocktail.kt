package com.example.cocktailsdbapp.network

import com.google.gson.annotations.SerializedName

data class Cocktail(
    @SerializedName("strDrink")
    val strDrink: String,
    val strDrinkThumb: String,
    val idDrink: String
    // Add other properties as needed
)
