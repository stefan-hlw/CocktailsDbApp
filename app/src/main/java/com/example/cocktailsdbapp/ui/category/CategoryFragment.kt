package com.example.cocktailsdbapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentCategoryBinding
import com.example.cocktailsdbapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoryFragment : Fragment(), FilterAdapter.OnItemClickListener {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

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
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFilterAdapter(initialFilters)
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