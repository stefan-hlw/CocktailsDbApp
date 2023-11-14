package com.example.cocktailsdbapp.ui.cocktails

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.network.CocktailApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsViewModel @Inject constructor(private val apiService: CocktailApiService) : ViewModel() {

    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = apiService.getAlcoholicCocktails("Alcoholic")
                // Handle the response
                val cocktails = response.drinks
                Log.d("####_DRINKS", cocktails.toString())
                // Do something with the list of cocktails
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }
}
