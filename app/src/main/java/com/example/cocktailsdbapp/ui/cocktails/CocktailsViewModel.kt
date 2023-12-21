package com.example.cocktailsdbapp.ui.cocktails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.model.CocktailResponse
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.utils.Constants
import com.example.cocktailsdbapp.utils.markFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsViewModel @Inject constructor(private val cocktailsRepo: CocktailsRepo) : ViewModel() {

    var cocktailsData = MutableLiveData<List<Cocktail>?>()

    fun fetchData(userEmail: String, filterCategory: String, filter: String) {
        viewModelScope.launch {
            try {
                val response : CocktailResponse = when (filterCategory) {
                    Constants.FILTER_ALCOHOL -> cocktailsRepo.getCocktailsByAlcoholContent(filter)
                    Constants.FILTER_LETTER -> cocktailsRepo.getCocktailsByFirstLetter(filter)
                    Constants.FILTER_INGREDIENT -> cocktailsRepo.getCocktailsByIngredient(filter)
                    Constants.FILTER_GLASS -> cocktailsRepo.getCocktailsByGlass(filter)
                    Constants.FILTER_CATEGORY -> cocktailsRepo.getCocktailsByCategory(filter)
                    else -> {
                        CocktailResponse(null)
                    }
                }
                val processedResponse = cocktailsRepo.getFavorites(userEmail)?.let {
                    response.markFavorites(it)
                }
                // Handle the response
                cocktailsData.postValue(processedResponse?.drinks)
            } catch (e: Exception) {
                // Handle errors
            }
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
