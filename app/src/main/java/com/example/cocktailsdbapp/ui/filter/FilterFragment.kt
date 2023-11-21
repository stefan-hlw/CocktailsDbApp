package com.example.cocktailsdbapp.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.databinding.FragmentFilterBinding
import com.example.cocktailsdbapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment(), FilterAdapter.OnItemClickListener {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val filterViewModel: FilterViewModel by viewModels()

    private var filterAdapter: FilterAdapter? = null

    private val initialFilters = listOf(
        Constants.FILTER_ALCOHOL,
        Constants.FILTER_CATEGORY,
        Constants.FILTER_GLASS,
        Constants.FILTER_INGREDIENT,
        Constants.FILTER_LETTER,
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        setFilterAdapter(initialFilters)
        (activity as MainActivity).let {
            it.showSearchView(false)
            it.showFilterView(false)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
        filterViewModel.fetchData(filter)
    }

}