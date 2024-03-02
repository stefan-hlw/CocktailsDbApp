package com.example.cocktailsdbapp.ui.category

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentFilterBinding
import com.example.cocktailsdbapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : BaseFragment<FragmentFilterBinding>(FragmentFilterBinding::inflate), FilterAdapter.OnItemClickListener {

    private val filterViewModel: FilterViewModel by viewModels()

    private var filterAdapter: FilterAdapter? = null

    private val args: FilterFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
    }

    override fun onStart() {
        super.onStart()
        filterViewModel.fetchData(args.filter)
        communicator.apply {
            showSearchIconView(false)
            showSearchInputView(false)
            showFilterView(false)
        }
    }

    private fun setObservers() {
        filterViewModel.filterData.observe(viewLifecycleOwner) { filters ->
            setFilterAdapter(filters)
            filters?.let {
                filterAdapter?.updateData(filters)
            }
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
        val args = bundleOf(getString(R.string.argument_filter_category) to args.filter, getString(R.string.argument_filter) to filter)
        findNavController().navigate(R.id.action_filterFragment_to_CocktailsFragment, args)
    }
}
