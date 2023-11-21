package com.example.cocktailsdbapp.model

import com.google.gson.annotations.SerializedName

data class IngredientListResponse(
    @SerializedName("drinks")
    val drinks: List<StrIngredient>
)

data class StrIngredient(
    @SerializedName("strIngredient1")
    val strIngredient: String
)
