package com.example.cocktailsdbapp.repository

import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.AlcoholContentResponse
import com.example.cocktailsdbapp.model.CocktailDetailsResponse
import com.example.cocktailsdbapp.model.CocktailResponse
import com.example.cocktailsdbapp.model.FilterResponse
import com.example.cocktailsdbapp.model.GlassesResponse
import com.example.cocktailsdbapp.model.IngredientListResponse

interface CocktailsRepo {

    suspend fun getCocktailsByAlcoholContent(alcoholContent: String): CocktailResponse
    suspend fun getCocktailsByGlass(glass: String): CocktailResponse
    suspend fun getCocktailsByCategory(category: String): CocktailResponse
    suspend fun getCocktailsByFirstLetter(letter: String): CocktailResponse
    suspend fun getCocktailsByIngredient(ingredient: String): CocktailResponse
    suspend fun getAlcoholContent(): AlcoholContentResponse
    suspend fun getCategories(): FilterResponse
    suspend fun getGlasses(): GlassesResponse
    suspend fun getIngredients(): IngredientListResponse
    suspend fun getCocktailDetails(cocktailId: String): CocktailDetailsResponse
    suspend fun getSearch(searchParam: String): CocktailResponse
    suspend fun getFavorites(userEmail: String): List<RoomCocktail>?
    suspend fun removeFavorite(userEmail: String, idDrink: String)
    suspend fun insertCocktail(userEmail: String, roomCocktail: RoomCocktail)
    suspend fun findFavoriteCocktail(userEmail: String, idDrink: String): RoomCocktail?
}