package com.example.cocktailsdbapp.ui.search

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.model.CocktailDetails
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.utils.markFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val cocktailsRepo: CocktailsRepo): ViewModel() {

    var searchResultsData = MutableLiveData<List<CocktailDetails>?>()

    fun fetchSearchData(userEmail: String, queryParam: String) {
        viewModelScope.launch {
            try {
                val response = cocktailsRepo.getSearch(queryParam)
                val favorites = cocktailsRepo.getFavorites(userEmail)
                val processedResponse = favorites?.let { response.markFavorites(it) }
                searchResultsData.postValue(processedResponse?.drinks)
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

}