package com.example.cocktailsdbapp.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktailsdbapp.databinding.ItemFilterBinding

class FilterAdapter(private val filterList: List<String>) : RecyclerView.Adapter<FilterAdapter.FilterViewHolder>() {

    private var _binding: ItemFilterBinding? = null
    private val binding get() = _binding!!

    private var onItemClickListener: OnItemClickListener? = null

    private var filters: List<String> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        _binding = ItemFilterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FilterViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        holder.bindView(filterList[position])
    }

    inner class FilterViewHolder(private val binding: ItemFilterBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindView(filter: String?) {
            binding.tvFilterText.text = filter
            binding.clFilterView.setOnClickListener {
                if (filter != null) {
                    onItemClickListener?.openFilter(filter)
                }
            }
        }

    }

    interface OnItemClickListener {
        fun openFilter(filter: String)
    }

    fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.onItemClickListener = onItemClickListener
    }

    fun updateData(newFilters: List<String>) {
        newFilters.let {
            val diffResult = DiffUtil.calculateDiff(FilterDiffCallback(filters, newFilters))
            filters = newFilters
            diffResult.dispatchUpdatesTo(this)
        }
    }
    override fun getItemCount(): Int {
        return filterList.size
    }


    inner class FilterDiffCallback(
        private val oldList: List<String>,
        private val newList: List<String>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}
