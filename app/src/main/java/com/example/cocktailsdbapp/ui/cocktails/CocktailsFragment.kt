package com.example.cocktailsdbapp.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentCocktailsBinding
import com.example.cocktailsdbapp.model.Cocktail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailsFragment: Fragment(), CocktailAdapter.OnFavoriteClickListener, CocktailAdapter.OnItemClickListener {

    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!

    private val cocktailsViewModel: CocktailsViewModel by viewModels()

    private var cocktailAdapter: CocktailAdapter? = null

    private val args: CocktailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setFilterText()
        (activity as MainActivity).let { mainActivity ->
            mainActivity.currentUser?.let { cocktailsViewModel.fetchData(it, args.filterCategory, args.filter) }
            mainActivity.showSearchIconView(true)
            mainActivity.showSearchInputView(false)
            mainActivity.showFilterView(true)
        }
        disableBackButton()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        val filter = args.filter.ifEmpty {
            getString(R.string.filter_param_default)
        }
        binding.tvFilterParam.text = getString(R.string.filter_param, filter)
    }

    private fun setCocktailsAdapter(cocktails: List<Cocktail>?) {
        cocktailAdapter = cocktails?.let { CocktailAdapter(it) }
        cocktailAdapter?.setOnItemClickListener(this)
        cocktailAdapter?.setOnFavoriteClickListener(this)
        binding.rvCocktails.adapter = cocktailAdapter
    }

    override fun favoriteCocktail(cocktail: Cocktail) {
        (activity as MainActivity).currentUser?.let {
            cocktailsViewModel.favoriteCocktail(
                it,
                cocktail
            )
        }
    }

    override fun openCocktailDetails(cocktailId: String) {
        val args = bundleOf("cocktailId" to cocktailId)
        findNavController().navigate(R.id.action_cocktailsFragment_to_CocktailDetailsFragment, args)
    }

    private fun disableBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // this disables the Android native back button
                }
            }
        )
    }

}
