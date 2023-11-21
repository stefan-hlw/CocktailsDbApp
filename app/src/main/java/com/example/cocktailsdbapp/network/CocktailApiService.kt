package com.example.cocktailsdbapp.network

import com.example.cocktailsdbapp.model.AlcoholContentResponse
import com.example.cocktailsdbapp.model.CocktailDetailsResponse
import com.example.cocktailsdbapp.model.CocktailResponse
import com.example.cocktailsdbapp.model.FilterResponse
import com.example.cocktailsdbapp.model.GlassesResponse
import com.example.cocktailsdbapp.model.IngredientListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CocktailApiService {


    // TODO Rename to filter by alcoholic!!!
    @GET("filter.php")
    suspend fun getCocktailsByAlcoholContent(@Query("a") type: String = "list"): CocktailResponse

    @GET("list.php")
    suspend fun getAlcoholContent(@Query("a") type: String = "list"): AlcoholContentResponse
    @GET("list.php")
    suspend fun getCategories(@Query("c") type: String = "list"): FilterResponse

    @GET("list.php")
    suspend fun getGlasses(@Query("g") type: String = "list"): GlassesResponse

    @GET("list.php")
    suspend fun getIngredients(@Query("i") type: String = "list"): IngredientListResponse

    @GET("lookup.php")
    suspend fun getCocktailDetails(@Query("i") cocktailId: String): CocktailDetailsResponse

    @GET("search.php")
    suspend fun getSearch(@Query("s") queryParam: String): CocktailDetailsResponse

}
