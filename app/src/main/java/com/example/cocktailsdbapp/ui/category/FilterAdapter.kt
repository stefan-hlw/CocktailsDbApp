package com.example.cocktailsdbapp.ui.category

import androidx.recyclerview.widget.DiffUtil
import com.example.cocktailsdbapp.databinding.ItemFilterBinding
import com.example.cocktailsdbapp.ui.BaseAdapter

class FilterAdapter(private val onItemClickListener: OnItemClickListener? = null) :
    BaseAdapter<String, ItemFilterBinding>(
        inflate = { inflater, parent, attachToParent ->
            ItemFilterBinding.inflate(inflater, parent, attachToParent)
        }
    ) {

    override fun bindItem(holder: ViewHolder, item: String) {
        binding.tvFilterText.text = item
        binding.clFilterView.setOnClickListener {
            onItemClickListener?.openFilter(item)
        }
    }

    interface OnItemClickListener {
        fun openFilter(filter: String)
    }

    override fun getDiffCallback(
        oldList: List<String>,
        newList: List<String>
    ): DiffUtil.Callback {
        return DiffCallback(
            oldList = oldList,
            newList = newList,
            areItemsTheSame = { oldItem, newItem ->
                oldItem == newItem
            },
            areContentsTheSame = { oldItem, newItem ->
                oldItem == newItem
            }
        )
    }
}
