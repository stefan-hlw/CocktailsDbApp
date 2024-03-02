package com.example.cocktailsdbapp.ui.authorization

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cocktailsdbapp.model.User
import com.example.cocktailsdbapp.utils.Constants
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
        editor.putString("${userData.email}${Constants.SHARED_PREF_NAME}", userData.name)
        editor.putString("${userData.email}${Constants.SHARED_PREF_EMAIL}", userData.email)
        editor.putString("${userData.email}${Constants.SHARED_PREF_PASSWORD}", userData.password)
        editor.apply()
        userPool.add(userData.email)
        return true
    }

    fun getUserData(userEmail: String): User {
            return User(
                sharedPreferences.getString("${userEmail}${Constants.SHARED_PREF_NAME}", "") ?: "",
                sharedPreferences.getString("${userEmail}${Constants.SHARED_PREF_EMAIL}", "") ?: "",
                sharedPreferences.getString("${userEmail}${Constants.SHARED_PREF_PASSWORD}", "") ?: ""
            )
        }

    fun isUserInfoValid(userEmail: String, password: String): Boolean {
        return !(sharedPreferences.getString("${userEmail}${Constants.SHARED_PREF_EMAIL}", "").isNullOrEmpty() ||
                sharedPreferences.getString("${userEmail}${Constants.SHARED_PREF_PASSWORD}", "") != password)
    }

    fun editUserName(userEmail: String, newName: String) {
        // Copy the current user using updated name
        val updatedUser = getUserData(userEmail).copy(name = newName)

        // Save the updated user data back to shared preferences
        with(sharedPreferences.edit()) {
            putString("${userEmail}${Constants.SHARED_PREF_NAME}", updatedUser.name)
            apply()
        }
    }
    fun editPassword(userEmail: String, newPassword: String) {
        // Update the name
        val updatedUser = getUserData(userEmail).copy(password = newPassword)

        // Save the updated user data back to shared preferences
        with(sharedPreferences.edit()) {
            putString("${userEmail}${Constants.SHARED_PREF_PASSWORD}", updatedUser.password)
            apply()
        }
    }
}
