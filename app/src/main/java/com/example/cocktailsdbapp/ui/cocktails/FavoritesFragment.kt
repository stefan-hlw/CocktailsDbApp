package com.example.cocktailsdbapp.ui.cocktails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.MainActivity
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
        (activity as MainActivity).let { mainActivity ->
            mainActivity.currentUser?.let { favoritesViewModel.getFavorites(it) }
            mainActivity.showSearchIconView(true)
            mainActivity.showSearchInputView(false)
            mainActivity.showFilterView(true)
        }
    }

    private fun setObservers() {
        favoritesViewModel.favoritesData.observe(viewLifecycleOwner) {
            setCocktailsAdapter(it)
            it?.let {
                cocktailAdapter?.updateData(it)
            }
        }
    }

    private fun setCocktailsAdapter(cocktails: List<Cocktail>?) {
        cocktailAdapter = cocktails?.let { CocktailAdapter(it) }
        cocktailAdapter?.setOnItemClickListener(this)
        cocktailAdapter?.setOnFavoriteClickListener(this)
        binding.rvCocktails.adapter = cocktailAdapter
    }

    override fun favoriteCocktail(cocktail: Cocktail) {
        (activity as MainActivity).currentUser?.let {
            favoritesViewModel.favoriteCocktail(
                it,
                cocktail
            )
        }
    }

    override fun openCocktailDetails(cocktailId: String) {
        val args = bundleOf("cocktailId" to cocktailId)
        findNavController().navigate(R.id.action_favoritesFragment_to_CocktailDetailsFragment, args)
    }

}
