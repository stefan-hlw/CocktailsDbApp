package com.example.cocktailsdbapp.ui.cocktails

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentCocktailsBinding
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailsFragment: BaseFragment<FragmentCocktailsBinding>(FragmentCocktailsBinding::inflate), CocktailAdapter.OnFavoriteClickListener, CocktailAdapter.OnItemClickListener {

    private val cocktailsViewModel: CocktailsViewModel by viewModels()

    private var cocktailAdapter: CocktailAdapter? = null

    private val args: CocktailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setFilterText()
        communicator.disableBackButton()
    }

    override fun onStart() {
        super.onStart()
        communicator.apply {
            getCurrentLoggedInUser()?.let { cocktailsViewModel.fetchData(it, args.filterCategory, args.filter) }
            showSearchIconView(true)
            showSearchInputView(false)
            showFilterView(true)
        }
    }

    private fun setObservers() {
        cocktailsViewModel.cocktailsData.observe(viewLifecycleOwner) {
            setCocktailsAdapter(it)
            it?.let {
                cocktailAdapter?.updateData(it)
            }
        }
    }

    private fun setFilterText() {
        binding.tvFilterParam.text = getString(
            R.string.filter_param,
            args.filter.ifEmpty {
                getString(R.string.filter_param_default)
            }
        )
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
            cocktailsViewModel.favoriteCocktail(
                it,
                cocktail
            )
        }
    }

    override fun openCocktailDetails(cocktailId: String) {
        val args = bundleOf(getString(R.string.argument_cocktail_id) to cocktailId)
        findNavController().navigate(R.id.action_cocktailsFragment_to_CocktailDetailsFragment, args)
    }

}
