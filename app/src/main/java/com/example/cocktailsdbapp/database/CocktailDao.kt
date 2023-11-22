package com.example.cocktailsdbapp.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CocktailDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCocktail(cocktail: RoomCocktail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(favorite: RoomFavorite)

    @Query("DELETE FROM roomFavorite WHERE userEmail = :userEmail AND idDrink = :idDrink")
    fun removeFavorite(userEmail: String, idDrink: String)

    @Query("SELECT roomCocktail.strDrinkThumb, roomCocktail.strDrink, roomCocktail.idDrink FROM roomFavorite INNER JOIN roomCocktail ON roomFavorite.idDrink = roomCocktail.idDrink WHERE userEmail = :userEmail")
    suspend fun getFavoriteCocktails(userEmail: String) : List<RoomCocktail>?

    @Query("SELECT roomCocktail.strDrinkThumb, roomCocktail.strDrink, roomCocktail.idDrink FROM roomFavorite INNER JOIN roomCocktail ON roomFavorite.idDrink = roomCocktail.idDrink WHERE userEmail = :userEmail AND roomCocktail.idDrink = :idDrink")
    suspend fun findFavoriteCocktail(userEmail: String, idDrink: String) : RoomCocktail?
}