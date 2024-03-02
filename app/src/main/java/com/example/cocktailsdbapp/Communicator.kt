package com.example.cocktailsdbapp

import androidx.appcompat.widget.SearchView


/**
 * This interface is used as a way to communicate between activity and fragments
 * */
interface Communicator {

    fun showSearchIconView(show: Boolean)

    fun showSearchInputView(show: Boolean)

    fun showFilterView(show: Boolean)

    fun getCurrentLoggedInUser(): String?

    fun setCurrentLoggedInUser(email: String?)

    fun disableBackButton()

    fun getSearchInputViewReference(): SearchView?
}
