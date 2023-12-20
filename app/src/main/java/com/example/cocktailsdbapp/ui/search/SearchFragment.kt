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
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        setupToolbar()
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
        cocktailAdapter = CocktailAdapter(this, this)
        cocktailAdapter?.let { adapter ->
            cocktails?.let { items -> adapter.updateData(items) }
            binding.rvCocktails.adapter = adapter
        }
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
        communicator.apply {
            showSearchIconView(false)
            showSearchInputView(true)
            showFilterView(false)
            val searchView = getSearchInputViewReference()
            searchView?.onActionViewExpanded()
            // Access the SearchView from the MainActivity
            searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // Handle query submission if needed
                    handleQueryChange(query)
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    // Update the search query in the ViewModel or perform search directly
                    handleQueryChange(newText)
                    return true
                }
            })
        }
    }

    private fun handleQueryChange(newText: String?) {
        searchViewModel.setSearchQuery(newText.orEmpty())
        communicator.getCurrentLoggedInUser()?.let { searchViewModel.fetchSearchData(it) }
        binding.tvQueryParam.text = getString(R.string.search_param, newText.orEmpty())
    }

    override fun openCocktailDetails(cocktailId: String) {
        val args = bundleOf(getString(R.string.argument_cocktail_id) to cocktailId)
        findNavController().navigate(R.id.action_searchFragment_to_CocktailDetailsFragment, args)
    }

    override fun favoriteCocktail(cocktail: Cocktail) {
        communicator.getCurrentLoggedInUser()?.let {
            searchViewModel.favoriteCocktail(
                it,
                cocktail
            )
        }
    }

    private val requestCodeSpeechInput = 100

    fun startVoiceInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.search_speak_to_search))

        try {
            startActivityForResult(intent, requestCodeSpeechInput)
        } catch (e: Exception) {
            Toast.makeText(this.requireContext(), getString(R.string.search_speech_to_text_not_supported), Toast.LENGTH_SHORT).show()
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            requestCodeSpeechInput -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val spokenText = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)?.get(0)

                    // Do something with the spokenText, like setting it to an EditText
                    communicator.getSearchInputViewReference()?.setQuery(spokenText, false)
                    binding.tvQueryParam.text = getString(R.string.search_param, spokenText.orEmpty())
                    searchViewModel.setSearchQuery(spokenText.orEmpty())
                }
            }
        }
    }

}
