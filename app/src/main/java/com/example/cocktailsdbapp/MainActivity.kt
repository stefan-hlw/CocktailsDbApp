package com.example.cocktailsdbapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.example.cocktailsdbapp.ui.search.SearchFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), Communicator {

    private lateinit var navController: NavController
    private var searchIcon: MenuItem? = null
    private var searchInput: MenuItem? = null
    private var micOn: MenuItem? = null
    private var filter: MenuItem? = null

    private var currentUser: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupNavigation()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        searchIcon = menu?.findItem(R.id.action_search)
        searchInput = menu?.findItem(R.id.action_search_input)
        micOn = menu?.findItem(R.id.action_mic)
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

        R.id.action_mic -> {
            val navHostFragment = supportFragmentManager.fragments.first()
            val wantedFragment = navHostFragment.childFragmentManager.fragments.first()
            if (wantedFragment is SearchFragment) {
                wantedFragment.startVoiceInput()
            }
            true
        }

        else -> {
            // The user's action isn't recognized.
            // Invoke the superclass to handle it.
            false
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

    override fun showSearchIconView(show: Boolean) {
        searchIcon?.isVisible = show
    }

    override fun showSearchInputView(show: Boolean) {
        searchInput?.isVisible = show
        micOn?.isVisible = show
    }

    override fun showFilterView(show: Boolean) {
        filter?.isVisible = show
    }

    override fun getCurrentLoggedInUser(): String? {
        return currentUser
    }

    override fun setCurrentLoggedInUser(email: String?) {
        currentUser = email
    }


    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun disableBackButton() {
        onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // this disables the Android native back button
                }
            }
        )
    }

    override fun getSearchInputViewReference(): SearchView? {
        return searchInput?.actionView as? SearchView
    }
}
