package com.example.cocktailsdbapp.network

import com.google.gson.annotations.SerializedName

data class CocktailResponse(
    @SerializedName("drinks")
    val drinks: List<Cocktail>
)
