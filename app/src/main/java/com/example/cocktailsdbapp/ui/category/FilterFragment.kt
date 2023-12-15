package com.example.cocktailsdbapp.ui.category

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment(), FilterAdapter.OnItemClickListener {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private val filterViewModel: FilterViewModel by viewModels()

    private var filterAdapter: FilterAdapter? = null

    private val args: FilterFragmentArgs by navArgs()


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
        filterViewModel.fetchData(args.filter)
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