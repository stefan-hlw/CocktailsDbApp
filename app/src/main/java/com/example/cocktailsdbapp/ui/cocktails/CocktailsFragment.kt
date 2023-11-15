package com.example.cocktailsdbapp.ui.cocktails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.cocktailsdbapp.databinding.FragmentCocktailsBinding
import com.example.cocktailsdbapp.network.Cocktail
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CocktailsFragment: Fragment() {

    private var _binding: FragmentCocktailsBinding? = null
    private val binding get() = _binding!!

    private val cocktailsViewModel: CocktailsViewModel by viewModels()

    private var cocktailAdapter: CocktailAdapter? = null

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
        cocktailsViewModel.fetchData()

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

    private fun setCocktailsAdapter(cocktails: List<Cocktail>?) {
        cocktailAdapter = cocktails?.let { CocktailAdapter(it) }
//        cocktailAdapter?.setOnItemClickListener(this)
        binding.rvCocktails.adapter = cocktailAdapter
    }

}
