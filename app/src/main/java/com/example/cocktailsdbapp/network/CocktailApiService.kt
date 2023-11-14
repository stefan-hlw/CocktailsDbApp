package com.example.cocktailsdbapp.network

import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {

    @GET("filter.php")
    suspend fun getAlcoholicCocktails(@Query("a") type: String): CocktailResponse
}
