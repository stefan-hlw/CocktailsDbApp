package com.example.cocktailsdbapp.model

import com.google.gson.annotations.SerializedName

data class AlcoholContentResponse(
    @SerializedName("drinks")
    val drinks: List<AlcoholContent>
)

data class AlcoholContent(
    @SerializedName("strAlcoholic")
    val strAlcoholic: String
)