package com.example.cocktailsdbapp.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.model.CocktailDetails
import com.example.cocktailsdbapp.repository.CocktailsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailDetailsViewModel @Inject constructor(private val cocktailsRepo: CocktailsRepo): ViewModel() {

    var cocktailDetailsData = MutableLiveData<CocktailDetails>()

    fun getDetails(cocktailId: String) {
        viewModelScope.launch {
            try {
                val details = cocktailsRepo.getCocktailDetails(cocktailId)
                // Cocktail details endpoint response consists of a List containing single item, so we always take only the first item
                cocktailDetailsData.postValue(details.drinks?.first())
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }



}