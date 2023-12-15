package com.example.cocktailsdbapp.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.widget.TextView
import com.example.cocktailsdbapp.database.RoomCocktail
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.model.CocktailDetails
import com.example.cocktailsdbapp.model.CocktailDetailsResponse
import com.example.cocktailsdbapp.model.CocktailResponse

// Transform Room model
fun RoomCocktail.toCocktail(isFavorite: Boolean): Cocktail {
    return Cocktail(strDrink, strDrinkThumb, idDrink, isFavorite)
}

fun TextView.makeLastNCharactersBold(n: Int) {
    val currentText = text.toString()

    // Check if the text has at least N characters
    if (currentText.length >= n) {
        val spannable = SpannableStringBuilder(currentText)

        // Apply bold style to the last N characters
        val boldStyle = StyleSpan(Typeface.BOLD)
        spannable.setSpan(boldStyle, currentText.length - n, currentText.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)

        // Set the text with the applied styles
        text = spannable
    }
}

fun CocktailResponse?.markFavorites(favorites: List<RoomCocktail>?): CocktailResponse? {
    // Check if either the response or favorites is null
    if (this == null || favorites.isNullOrEmpty()) {
        return this
    }

    // Create a map from the drinks list for faster lookups
    val drinksMap = drinks?.associateBy { it.idDrink }

    // Mark cocktails received from API as favorite if they are already saved
    favorites.forEach { favorite ->
        drinksMap?.get(favorite.idDrink)?.isFavorite = true
    }

    return this
}

fun CocktailDetails.toCocktail(): Cocktail {
    return Cocktail(
        strDrink = strDrink ?: "",
        strDrinkThumb = strDrinkThumb ?: "",
        idDrink = idDrink ?: "",
        isFavorite = isFavorite
    )
}

fun CocktailDetailsResponse.toCocktailsResponse(): CocktailResponse {
    val cocktails = drinks?.map { it.toCocktail() }
    return CocktailResponse(cocktails)
}

