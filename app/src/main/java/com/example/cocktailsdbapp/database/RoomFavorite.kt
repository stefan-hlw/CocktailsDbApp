package com.example.cocktailsdbapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "roomFavorite")
data class RoomFavorite(
    @ColumnInfo(name = "userEmail") val userEmail: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idDrink") val idDrink: String,
)