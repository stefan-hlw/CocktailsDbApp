package com.example.cocktailsdbapp.ui.category

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktailsdbapp.MainActivity
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
        filterViewModel.fetchData(args.filter)
        (activity as MainActivity).let {
            it.showSearchIconView(false)
            it.showSearchInputView(false)
            it.showFilterView(false)
        }
    }

    private fun setObservers() {
        filterViewModel.filterData.observe(viewLifecycleOwner) {
            setFilterAdapter(it)
            it?.let {
                filterAdapter?.updateData(it)
            }
        }
    }

    private fun setFilterAdapter(filters: List<String>?) {
        filterAdapter = filters?.let { FilterAdapter(it) }
        filterAdapter?.setOnItemClickListener(this)
        binding.rvFilter.adapter = filterAdapter
    }

    override fun openFilter(filter: String) {
        val args = bundleOf("filterCategory" to args.filter, "filter" to filter)
        findNavController().navigate(R.id.action_filterFragment_to_CocktailsFragment, args)
    }
}
