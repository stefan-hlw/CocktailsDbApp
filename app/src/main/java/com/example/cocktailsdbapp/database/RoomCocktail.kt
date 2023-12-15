package com.example.cocktailsdbapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roomCocktail")
data class RoomCocktail(
    @ColumnInfo(name = "strDrink") val strDrink: String,
    @ColumnInfo(name = "strDrinkThumb") val strDrinkThumb: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idDrink") val idDrink: String,
)