package com.example.cocktailsdbapp.ui.cocktails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.utils.toCocktail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(private val cocktailsRepo: CocktailsRepo) : ViewModel() {

    var favoritesData = MutableLiveData<List<Cocktail>?>()
    fun getFavorites(userEmail: String) {
        viewModelScope.launch {
            try {
                val favorites = cocktailsRepo.getFavorites(userEmail)
                favoritesData.postValue(favorites?.map { it.toCocktail(true) })
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
