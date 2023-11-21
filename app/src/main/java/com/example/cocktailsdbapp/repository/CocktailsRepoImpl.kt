package com.example.cocktailsdbapp.repository

import com.example.cocktailsdbapp.database.CocktailDao
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.database.RoomFavorite
import com.example.cocktailsdbapp.model.AlcoholContentResponse
import com.example.cocktailsdbapp.model.CocktailDetailsResponse
import com.example.cocktailsdbapp.model.CocktailResponse
import com.example.cocktailsdbapp.model.FilterResponse
import com.example.cocktailsdbapp.model.GlassesResponse
import com.example.cocktailsdbapp.model.IngredientListResponse
import com.example.cocktailsdbapp.network.CocktailApiService

class CocktailsRepoImpl(private val serviceApi: CocktailApiService, private val cocktailDao: CocktailDao): CocktailsRepo {
    override suspend fun getCocktailsByAlcoholContent(alcoholContent: String): CocktailResponse {
        return serviceApi.getCocktailsByAlcoholContent(alcoholContent)
    }

    override suspend fun getAlcoholContent(): AlcoholContentResponse {
        return serviceApi.getAlcoholContent()
    }

    override suspend fun getCategories(): FilterResponse {
        return serviceApi.getCategories()
    }

    override suspend fun getGlasses(): GlassesResponse {
        return serviceApi.getGlasses()
    }

    override suspend fun getIngredients(): IngredientListResponse {
        return serviceApi.getIngredients()
    }

    override suspend fun getCocktailDetails(cocktailId: String): CocktailDetailsResponse {
         return serviceApi.getCocktailDetails(cocktailId)
    }

    override suspend fun getSearch(searchParam: String): CocktailDetailsResponse {
        return serviceApi.getSearch(searchParam)
    }

    override suspend fun getFavorites(userEmail: String): List<RoomCocktail>? {
        return cocktailDao.getFavoriteCocktails(userEmail)
    }
    override suspend fun removeFavorite(userEmail: String, idDrink: String) {
        cocktailDao.removeFavorite(userEmail, idDrink)
    }

    override suspend fun insertCocktail(userEmail: String, roomCocktail: RoomCocktail) {
        cocktailDao.insertCocktail(roomCocktail)
        cocktailDao.insertFavorite(RoomFavorite(userEmail, roomCocktail.idDrink))
    }

}
