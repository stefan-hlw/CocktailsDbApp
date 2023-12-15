package com.example.cocktailsdbapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.utils.markFavorites
import com.example.cocktailsdbapp.utils.toCocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val cocktailsRepo: CocktailsRepo): ViewModel() {

    private var searchJob: Job? = null

    var searchResultsData = MutableLiveData<List<Cocktail>?>()

    private val searchQueryFlow = MutableStateFlow("")

    fun setSearchQuery(query: String) {
        // Update the search query in the flow
        searchQueryFlow.value = query
    }

    fun fetchSearchData(userEmail: String) {
        // Cancel the previous search job if it exists
        searchJob?.cancel()

        // Start a new search job with debounce effect
        searchJob = viewModelScope.launch {
            searchQueryFlow
                .debounce(700)
                .distinctUntilChanged()
                .collect { query ->
                    // Use the collected query for the search
                    executeSearch(userEmail, query)
                }
        }
    }

    private suspend fun executeSearch(userEmail: String, queryParam: String) {
        try {
            val response = cocktailsRepo.getSearch(queryParam)
            val favorites = cocktailsRepo.getFavorites(userEmail)
            favorites?.map { it.toCocktail(true) }
            if (response.drinks.isNullOrEmpty()) {
                searchResultsData.postValue(null)
            } else {
                val processedResponse = response.markFavorites(favorites)
                searchResultsData.postValue(processedResponse?.drinks)
            }
        } catch (e: Exception) {
            // Handle errors
        }
    }

    fun favoriteCocktail(userEmail: String, cocktail: Cocktail) {
        viewModelScope.launch(Dispatchers.IO) {
            val isFavorite = cocktailsRepo.findFavoriteCocktail(userEmail, cocktail.idDrink)
            if(isFavorite != null) {
                cocktailsRepo.removeFavorite(userEmail, cocktail.idDrink)
            } else {
                cocktailsRepo.insertCocktail(userEmail, RoomCocktail(cocktail.strDrink, cocktail.strDrinkThumb, cocktail.idDrink))
            }
        }
    }
}
