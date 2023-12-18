package com.example.cocktailsdbapp.network

import com.example.cocktailsdbapp.model.AlcoholContentResponse
import com.example.cocktailsdbapp.model.CocktailDetailsResponse
import com.example.cocktailsdbapp.model.CocktailResponse
import com.example.cocktailsdbapp.model.FilterResponse
import com.example.cocktailsdbapp.model.GlassesResponse
import com.example.cocktailsdbapp.model.IngredientListResponse
import com.example.cocktailsdbapp.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApiService {

    @GET(Constants.QUERY_FILTER)
    suspend fun getCocktailsByAlcoholContent(@Query("a") type: String = Constants.QUERY_PARAM_LIST): CocktailResponse
    @GET(Constants.QUERY_FILTER)
    suspend fun getCocktailsByGlass(@Query("g") type: String = Constants.QUERY_PARAM_LIST): CocktailResponse
    @GET(Constants.QUERY_FILTER)
    suspend fun getCocktailsByCategory(@Query("c") type: String = Constants.QUERY_PARAM_LIST): CocktailResponse
    @GET(Constants.QUERY_SEARCH)
    suspend fun getCocktailsByFirstLetter(@Query("f") type: String = Constants.QUERY_PARAM_LIST): CocktailResponse
    @GET(Constants.QUERY_FILTER)
    suspend fun getCocktailsByIngredient(@Query("i") type: String = Constants.QUERY_PARAM_LIST): CocktailResponse
    @GET(Constants.QUERY_LIST)
    suspend fun getAlcoholContent(@Query("a") type: String = Constants.QUERY_PARAM_LIST): AlcoholContentResponse
    @GET(Constants.QUERY_LIST)
    suspend fun getCategories(@Query("c") type: String = Constants.QUERY_PARAM_LIST): FilterResponse
    @GET(Constants.QUERY_LIST)
    suspend fun getGlasses(@Query("g") type: String = Constants.QUERY_PARAM_LIST): GlassesResponse
    @GET(Constants.QUERY_LIST)
    suspend fun getIngredients(@Query("i") type: String = Constants.QUERY_PARAM_LIST): IngredientListResponse
    @GET(Constants.QUERY_LOOKUP)
    suspend fun getCocktailDetails(@Query("i") cocktailId: String): CocktailDetailsResponse
    @GET(Constants.QUERY_SEARCH)
    suspend fun getSearch(@Query("s") queryParam: String): CocktailDetailsResponse

}
