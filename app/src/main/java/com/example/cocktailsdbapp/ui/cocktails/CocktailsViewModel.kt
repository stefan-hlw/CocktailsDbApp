package com.example.cocktailsdbapp.ui.cocktails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.network.Cocktail
import com.example.cocktailsdbapp.network.CocktailApiService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsViewModel @Inject constructor(private val apiService: CocktailApiService) : ViewModel() {

    var cocktailsData = MutableLiveData<List<Cocktail>?>()
    fun fetchData() {
        viewModelScope.launch {
            try {
                val response = apiService.getAlcoholicCocktails("Alcoholic")
                // Handle the response
                cocktailsData.postValue(response.drinks)
                // Do something with the list of cocktails
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

}
