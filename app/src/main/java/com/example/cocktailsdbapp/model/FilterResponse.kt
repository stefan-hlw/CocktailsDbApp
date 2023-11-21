package com.example.cocktailsdbapp.model

import com.google.gson.annotations.SerializedName

data class FilterResponse(
    @SerializedName("drinks")
    val drinks: List<DrinkCategory>
)

data class DrinkCategory(
    @SerializedName("strCategory")
    val strCategory: String
)
