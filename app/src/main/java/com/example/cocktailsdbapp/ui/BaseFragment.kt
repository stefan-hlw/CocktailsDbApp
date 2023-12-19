package com.example.cocktailsdbapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.example.cocktailsdbapp.Communicator
import com.example.cocktailsdbapp.R


typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T

abstract class BaseFragment<VB: ViewBinding>(
    private val inflate: Inflate<VB>
) : Fragment() {

    private var _binding: VB? = null
    val binding get() = _binding!!

    protected lateinit var communicator: Communicator

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        communicator = context as Communicator
    }

     protected fun showSuccessPopUp(message: String, navAction: Int?) {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        dialogBuilder.setMessage(message)
        dialogBuilder.setPositiveButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
            navAction?.let { findNavController().navigate(it) }
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

}
