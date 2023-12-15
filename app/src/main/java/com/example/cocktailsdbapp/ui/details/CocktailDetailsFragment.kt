package com.example.cocktailsdbapp.ui.details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.databinding.FragmentCocktailDetailsBinding
import com.example.cocktailsdbapp.model.CocktailDetails
import com.example.cocktailsdbapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailDetailsFragment: BaseFragment<FragmentCocktailDetailsBinding>(FragmentCocktailDetailsBinding::inflate) {

    private val cocktailDetailsViewModel: CocktailDetailsViewModel by viewModels()

    private var ingredientsAdapter: IngredientsAdapter? = null

    private val args: CocktailDetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvName.text = args.cocktailId
        setObservers()
        cocktailDetailsViewModel.getDetails(args.cocktailId)
        (activity as MainActivity).let {
            it.showSearchIconView(false)
            it.showSearchInputView(false)
            it.showFilterView(false)
        }
    }

    private fun setObservers() {
        cocktailDetailsViewModel.cocktailDetailsData.observe(viewLifecycleOwner) {
            setupUi(it)
        }
    }

    private fun setupUi(cocktail: CocktailDetails) {
        with(binding) {
            Glide.with(root.context)
                .load(cocktail.strDrinkThumb)
//                    .placeholder(R.drawable.placeholder_image)
//                    .error(R.drawable.placeholder_image)
                .into(ivDrink)
            tvName.text = cocktail.strDrink
            tvCategory.text = cocktail.strCategory
            tvGlass.text = cocktail.strGlass
            expandTextView.text = cocktail.strInstructions
        }
        setIngredientsAdapter(cocktail.getIngredientsWithMeasures())
    }

    private fun setIngredientsAdapter(ingredients: List<Pair<String, String>>?) {
        ingredientsAdapter = ingredients?.let { IngredientsAdapter(it) }
        binding.rvIngredients.adapter = ingredientsAdapter
    }
}
