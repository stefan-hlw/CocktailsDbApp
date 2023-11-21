package com.example.cocktailsdbapp.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [RoomCocktail::class, RoomFavorite::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cocktailDao(): CocktailDao
}