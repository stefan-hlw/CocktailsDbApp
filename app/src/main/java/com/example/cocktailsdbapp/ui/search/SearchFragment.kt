package com.example.cocktailsdbapp.ui.search

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentSearchBinding
import com.example.cocktailsdbapp.model.Cocktail
import com.example.cocktailsdbapp.ui.BaseFragment
import com.example.cocktailsdbapp.ui.cocktails.CocktailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment: BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate), CocktailAdapter.OnFavoriteClickListener, CocktailAdapter.OnItemClickListener {

    private val searchViewModel: SearchViewModel by viewModels()

    private var cocktailAdapter: CocktailAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setObservers()
    }

    private fun setObservers() {
        searchViewModel.searchResultsData.observe(viewLifecycleOwner) { cocktails ->
            if (cocktails.isNullOrEmpty()) {
                // Show empty view
                toggleEmptyViewVisibility(true)
            } else {
                // Show RecyclerView and update data
                toggleEmptyViewVisibility(false)
                setCocktailsAdapter(cocktails)
                cocktailAdapter?.updateData(cocktails)
            }
        }
    }

    private fun setCocktailsAdapter(cocktails: List<Cocktail>?) {
        cocktailAdapter = cocktails?.let { CocktailAdapter(it) }
        cocktailAdapter?.setOnItemClickListener(this)
        cocktailAdapter?.setOnFavoriteClickListener(this)
        binding.rvCocktails.adapter = cocktailAdapter
        binding.llLabel.visibility = View.VISIBLE
    }

    private fun toggleEmptyViewVisibility(showEmptyView: Boolean) {
        if (showEmptyView) {
            binding.rvCocktails.visibility = View.GONE
            binding.tvEmptyView.visibility = View.VISIBLE
            binding.llLabel.visibility = View.GONE
        } else {
            binding.rvCocktails.visibility = View.VISIBLE
            binding.tvEmptyView.visibility = View.GONE
        }
    }

    private fun setupToolbar() {
        (activity as MainActivity).let {
            it.showSearchIconView(false)
            it.showSearchInputView(true)
            it.showFilterView(false)
            val mic = it.micOn?.actionView
            val searchView = it.searchInput?.actionView as? SearchView
            searchView?.onActionViewExpanded()
            // Access the SearchView from the MainActivity
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Handle query submission if needed
                    searchViewModel.setSearchQuery(query.orEmpty())
                    it.currentUser?.let { user -> searchViewModel.fetchSearchData(user) }
                    binding.tvQueryParam.text = getString(R.string.search_param, query.orEmpty())
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Update the search query in the ViewModel or perform search directly
                    searchViewModel.setSearchQuery(newText.orEmpty())
                    it.currentUser?.let { user -> searchViewModel.fetchSearchData(user) }
                    binding.tvQueryParam.text = getString(R.string.search_param, newText.orEmpty())
                    return true
                }
            })
        }
    }

    override fun openCocktailDetails(cocktailId: String) {
        val args = bundleOf("cocktailId" to cocktailId)
        findNavController().navigate(R.id.action_searchFragment_to_CocktailDetailsFragment, args)
    }

    override fun favoriteCocktail(cocktail: Cocktail) {
        (activity as MainActivity).currentUser?.let {
            searchViewModel.favoriteCocktail(
                it,
                cocktail
            )
        }
    }

    private val REQUEST_CODE_SPEECH_INPUT = 100

    fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to search...")

        try {
            startActivityForResult(intent, REQUEST_CODE_SPEECH_INPUT)
        } catch (e: Exception) {
            Toast.makeText(this.requireContext(), "Speech recognition not supported on your device", Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            REQUEST_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    val spokenText = result?.get(0)

                    // Do something with the spokenText, like setting it to an EditText
                    val searchView = (activity as MainActivity).searchInput?.actionView as? SearchView
                    searchView?.setQuery(spokenText, false)
                    binding.tvQueryParam.text = getString(R.string.search_param, spokenText.orEmpty())
                    searchViewModel.setSearchQuery(spokenText.orEmpty())
                }
            }
        }
    }

}
