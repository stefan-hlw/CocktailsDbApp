package com.example.cocktailsdbapp.ui.cocktails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.utils.markFavorites
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CocktailsViewModel @Inject constructor(private val cocktailsRepo: CocktailsRepo) : ViewModel() {

    var cocktailsData = MutableLiveData<List<Cocktail>?>()
    fun fetchData(userEmail: String) {
        viewModelScope.launch {
            try {
                val response = cocktailsRepo.getCocktailsByAlcoholContent("Alcoholic")
                val favorites = cocktailsRepo.getFavorites(userEmail)
                val processedResponse = favorites?.let { response.markFavorites(it) }
                // Handle the response
                cocktailsData.postValue(processedResponse?.drinks)
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    fun favoriteCocktail(userEmail: String, cocktail: Cocktail) {
        CoroutineScope(Dispatchers.IO).launch {
            cocktailsRepo.insertCocktail(userEmail, RoomCocktail(cocktail.strDrink, cocktail.strDrinkThumb, cocktail.idDrink))
        }
    }

}
