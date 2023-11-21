package com.example.cocktailsdbapp.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentFavoritesBinding
import com.example.cocktailsdbapp.model.Cocktail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment: Fragment(), CocktailAdapter.OnFavoriteClickListener, CocktailAdapter.OnItemClickListener {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    private val favoritesViewModel: FavoritesViewModel by viewModels()

    private var cocktailAdapter: CocktailAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        (activity as MainActivity).let {
            it.currentUser?.let { favoritesViewModel.getFavorites(it) }
            it.showSearchView(true)
            it.showFilterView(true)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
