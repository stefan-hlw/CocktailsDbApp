package com.example.cocktailsdbapp.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : RecyclerView.Adapter<BaseAdapter<T, VB>.ViewHolder>() {

    protected var data: List<T> = emptyList()
    protected lateinit var context: Context

    private var _binding: VB? = null
    val binding get() = _binding!!
    inner class ViewHolder(val binding: VB) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        _binding = inflate.invoke(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        bindItem(holder, data[position])
    }

    abstract fun bindItem(holder: ViewHolder, item: T)

    fun updateData(newData: List<T>) {
        newData.let {
            val diffResult = DiffUtil.calculateDiff(getDiffCallback(data, newData))
            data = newData
            diffResult.dispatchUpdatesTo(this)
        }
    }

    abstract fun getDiffCallback(oldList: List<T>, newList: List<T>): DiffUtil.Callback

    inner class DiffCallback(
        private val oldList: List<T>,
        private val newList: List<T>,
        private val areItemsTheSame: (oldItem: T, newItem: T) -> Boolean,
        private val areContentsTheSame: (oldItem: T, newItem: T) -> Boolean
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areItemsTheSame(oldList[oldItemPosition], newList[newItemPosition])

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            areContentsTheSame(oldList[oldItemPosition], newList[newItemPosition])
    }
}
