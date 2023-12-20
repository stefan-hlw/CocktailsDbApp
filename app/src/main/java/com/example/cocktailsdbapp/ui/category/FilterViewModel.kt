package com.example.cocktailsdbapp.ui.category


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
                filterData.postValue(
                    cocktailsRepo.getAlcoholContent().drinks.map {
                        it.strAlcoholic
                    }
                )
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            try {
                filterData.postValue(cocktailsRepo.getCategories().drinks.map {
                    it.strCategory
                })
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getGlasses() {
        viewModelScope.launch {
            try {
                filterData.postValue(cocktailsRepo.getGlasses().drinks.map {
                    it.strGlass
                })
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getIngredients() {
        viewModelScope.launch {
            try {
                filterData.postValue(cocktailsRepo.getIngredients().drinks.map {
                    it.strIngredient
                })
            } catch (e: Exception) {
                // Handle errors
            }
        }
    }

    private fun getFirstLetters() {
        val alphabetLettersList: List<String> = (Constants.ALPHABET_FIRST_LETTER..Constants.ALPHABET_LAST_LETTER).map { it.toString() }
        filterData.postValue(alphabetLettersList)
    }

}
