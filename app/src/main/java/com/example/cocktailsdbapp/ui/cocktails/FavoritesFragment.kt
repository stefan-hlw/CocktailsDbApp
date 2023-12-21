package com.example.cocktailsdbapp.ui.cocktails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentFavoritesBinding
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: BaseFragment<FragmentFavoritesBinding>(FragmentFavoritesBinding::inflate), CocktailAdapter.OnFavoriteClickListener, CocktailAdapter.OnItemClickListener {

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private var cocktailAdapter: CocktailAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        communicator.apply {
            getCurrentLoggedInUser()?.let { favoritesViewModel.getFavorites(it) }
            showSearchIconView(true)
            showSearchInputView(false)
            showFilterView(true)
        }
    }

    private fun setObservers() {
        favoritesViewModel.favoritesData.observe(viewLifecycleOwner) { favoriteCocktailsData ->
            setCocktailsAdapter(favoriteCocktailsData)
            favoriteCocktailsData?.let {
                cocktailAdapter?.updateData(favoriteCocktailsData)
            }
        }
    }

    private fun setCocktailsAdapter(cocktails: List<Cocktail>?) {
        cocktailAdapter = CocktailAdapter(this, this)
        cocktailAdapter?.let { adapter ->
            cocktails?.let { items -> adapter.updateData(items) }
            binding.rvCocktails.adapter = adapter
        }
    }

    override fun favoriteCocktail(cocktail: Cocktail) {
        communicator.getCurrentLoggedInUser()?.let {
            favoritesViewModel.favoriteCocktail(
                it,
                cocktail
            )
        }
    }

    override fun openCocktailDetails(cocktailId: String) {
        val args = bundleOf(getString(R.string.argument_cocktail_id) to cocktailId)
        findNavController().navigate(R.id.action_favoritesFragment_to_CocktailDetailsFragment, args)
    }

}
