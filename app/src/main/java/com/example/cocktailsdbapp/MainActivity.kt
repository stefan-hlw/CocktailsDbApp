package com.example.cocktailsdbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private var searchIcon: MenuItem? = null
    var searchInput: MenuItem? = null
    private var filter: MenuItem? = null

    var currentUser: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        searchIcon = menu?.findItem(R.id.action_search)
        searchInput = menu?.findItem(R.id.action_search_input)
        filter = menu?.findItem(R.id.action_filter)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
        R.id.action_filter -> {
            // User chooses the "Filter" item. Open filter fragment.
            navController.navigate(R.id.action_global_category_fragment)
            true
        }

        R.id.action_search -> {
            // User chooses the "Search" action. Open Search fragment.
            navController.navigate(R.id.action_global_search_fragment)
            true
        }

        else -> {
            // The user's action isn't recognized.
            // Invoke the superclass to handle it.
            super.onOptionsItemSelected(item)
        }
    }

    private fun setupNavigation() {
        navController = findNavController(R.id.nav_host_fragment)
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigationMenu)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_cocktails, R.id.navigation_favorites, R.id.navigation_profile
            )
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)
        setSupportActionBar(toolbar)

        bottomNav.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.loginFragment || destination.id == R.id.registrationFragment) {
                bottomNav.visibility = View.GONE
                toolbar.visibility = View.GONE
            } else {
                bottomNav.visibility = View.VISIBLE
                toolbar.visibility = View.VISIBLE
            }
        }
    }

    fun showSearchIconView(show: Boolean) {
        searchIcon?.isVisible = show
    }

    fun showSearchInputView(show: Boolean) {
        searchInput?.isVisible = show
    }

    fun showFilterView(show: Boolean) {
        filter?.isVisible = show
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
