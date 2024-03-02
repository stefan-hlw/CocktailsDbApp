package com.example.cocktailsdbapp.ui.category

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentCategoryBinding
import com.example.cocktailsdbapp.ui.BaseFragment
import com.example.cocktailsdbapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : BaseFragment<FragmentCategoryBinding>(FragmentCategoryBinding::inflate), FilterAdapter.OnItemClickListener {

    private var filterAdapter: FilterAdapter? = null

    private val initialFilters = listOf(
        Constants.FILTER_ALCOHOL,
        Constants.FILTER_CATEGORY,
        Constants.FILTER_GLASS,
        Constants.FILTER_INGREDIENT,
        Constants.FILTER_LETTER,
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilterAdapter(initialFilters)
    }

    override fun onStart() {
        super.onStart()
        communicator.apply {
            showSearchIconView(false)
            showSearchInputView(false)
            showFilterView(false)
        }
    }

    private fun setFilterAdapter(filters: List<String>?) {
        filterAdapter = FilterAdapter(this)
        filterAdapter?.let { adapter ->
            filters?.let { items -> adapter.updateData(items) }
            binding.rvFilter.adapter = adapter
        }
    }

    override fun openFilter(filter: String) {
        val args = bundleOf(getString(R.string.argument_filter) to filter)
        findNavController().navigate(R.id.action_categoryFragment_to_FilterFragment, args)
    }
}
