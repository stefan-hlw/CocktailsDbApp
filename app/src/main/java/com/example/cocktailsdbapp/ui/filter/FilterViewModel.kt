package com.example.cocktailsdbapp.ui.filter


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cocktailsdbapp.repository.CocktailsRepo
import com.example.cocktailsdbapp.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
    class FilterViewModel @Inject constructor(private val cocktailsRepo: CocktailsRepo) : ViewModel() {

    var filterData = MutableLiveData<List<String>?>()

//    fun fetchData(type: String) {
//        when(type) {
//            Constants.FILTER_ALCOHOL -> getAlcoholContent()
//            Constants.FILTER_CATEGORY -> getCategories()
//            Constants.FILTER_GLASS -> getCategories()
//            Constants.FILTER_INGREDIENT -> getCategories()
//            Constants.FILTER_LETTER -> getCategories()
//        }
//    }
//

    fun fetchData(type: String) {
        val functionMap = mapOf(
            Constants.FILTER_ALCOHOL to ::getAlcoholContent,
            Constants.FILTER_CATEGORY to ::getCategories,
            Constants.FILTER_GLASS to ::getGlasses,
            Constants.FILTER_INGREDIENT to ::getIngredients,
            Constants.FILTER_LETTER to ::getFirstLetters
        )

        val fetchFunction = functionMap[type]
        fetchFunction?.invoke()
    }

    private fun getAlcoholContent() {
        viewModelScope.launch {
            try {
                val response = cocktailsRepo.getAlcoholContent()
                filterData.postValue(response.drinks.map { it.strAlcoholic })
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                val response = cocktailsRepo.getCategories()
                filterData.postValue(response.drinks.map { it.strCategory })
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getGlasses() {
        viewModelScope.launch {
            try {
                val response = cocktailsRepo.getGlasses()
                filterData.postValue(response.drinks.map { it.strGlass })
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getIngredients() {
        viewModelScope.launch {
            try {
                val response = cocktailsRepo.getIngredients()
                filterData.postValue(response.drinks.map { it.strIngredient })
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getFirstLetters() {
        val alphabetLettersList: List<String> = ('A'..'Z').map { it.toString() }
        filterData.postValue(alphabetLettersList)
    }


}