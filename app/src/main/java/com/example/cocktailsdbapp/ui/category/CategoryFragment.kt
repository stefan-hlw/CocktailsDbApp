package com.example.cocktailsdbapp.ui.category

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.MainActivity
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
        (activity as MainActivity).let {
            it.showSearchIconView(false)
            it.showSearchInputView(false)
            it.showFilterView(false)
        }
    }

    private fun setFilterAdapter(filters: List<String>?) {
        filterAdapter = filters?.let { FilterAdapter(it) }
        filterAdapter?.setOnItemClickListener(this)
        binding.rvFilter.adapter = filterAdapter
    }

    override fun openFilter(filter: String) {
        val args = bundleOf("filter" to filter)
        findNavController().navigate(R.id.action_categoryFragment_to_FilterFragment, args)
    }
}
