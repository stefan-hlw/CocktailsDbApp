package com.example.cocktailsdbapp.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.databinding.FragmentCocktailDetailsBinding
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.model.CocktailDetails
import com.example.cocktailsdbapp.ui.cocktails.CocktailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailDetailsFragment: Fragment() {
    private var _binding: FragmentCocktailDetailsBinding? = null
    private val binding get() = _binding!!

    private val cocktailDetailsViewModel: CocktailDetailsViewModel by viewModels()

    private var ingredientsAdapter: IngredientsAdapter? = null

    private val args: CocktailDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCocktailDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
