package com.example.cocktailsdbapp.ui.authorization

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cocktailsdbapp.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : ViewModel() {

    private var userPool = mutableSetOf<String>()

    fun saveUserData(userData: User) : Boolean {
        if(userPool.contains(userData.email)) return false
        val editor = sharedPreferences.edit()
        editor.putString("${userData.email}_name", userData.name)
        editor.putString("${userData.email}_email", userData.email)
        editor.putString("${userData.email}_password", userData.password)
        editor.apply()
        return true
    }

    fun getUserData(userEmail: String): User? {
            val name = sharedPreferences.getString("${userEmail}_name", "") ?: ""
            val email = sharedPreferences.getString("${userEmail}_email", "") ?: ""
            val password = sharedPreferences.getString("${userEmail}_password", "") ?: ""
            return User(name, email, password)
        }

}
