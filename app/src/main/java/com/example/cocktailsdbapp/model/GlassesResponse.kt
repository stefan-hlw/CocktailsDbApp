package com.example.cocktailsdbapp.model

import com.google.gson.annotations.SerializedName

data class GlassesResponse(
    @SerializedName("drinks")
    val drinks: List<Glass>
)

data class Glass(
    @SerializedName("strGlass")
    val strGlass: String
)
