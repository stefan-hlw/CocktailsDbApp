package com.example.cocktailsdbapp.ui.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentProfileBinding
import com.example.cocktailsdbapp.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).let {
            it.showSearchIconView(true)
            it.showFilterView(true)
        }
        (activity as MainActivity).let { mainActivity ->
            mainActivity.currentUser?.let { authViewModel.getUserData(it) }
            mainActivity.showSearchIconView(true)
            mainActivity.showSearchInputView(false)
            mainActivity.showFilterView(true)
        }
        initUi()
        setListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setListeners() {
        with(binding) {
            btLogout.setOnClickListener {
                (activity as MainActivity).currentUser = null
                findNavController().navigate(R.id.action_profileFragment_to_initialState)
            }
            tilNameInput.setEndIconOnClickListener {
                if(this.etNameInput.isEnabled) {
                    this.etNameInput.isEnabled = false
                    user?.email?.let { email -> authViewModel.editUserName(email, etNameInput.text.toString()) }
                    this.tilNameInput.endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.baseline_edit_24, null)
                    showEditSuccessPopUp()
                } else {
                    this.etNameInput.isEnabled = true
                    this.etNameInput.requestFocus()
                    this.tilNameInput.endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.baseline_save_24, null)
                }
            }

            tilPasswordInput.setEndIconOnClickListener {
                if(this.etPasswordInput.isEnabled) {
                    this.etPasswordInput.isEnabled = false
                    user?.email?.let { email -> authViewModel.editPassword(email, etPasswordInput.text.toString()) }
                    this.tilPasswordInput.endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.baseline_edit_24, null)
                    showEditSuccessPopUp()
                } else {
                    this.etPasswordInput.isEnabled = true
                    this.etPasswordInput.requestFocus()
                    this.tilPasswordInput.endIconDrawable = ResourcesCompat.getDrawable(resources, R.drawable.baseline_save_24, null)
                }
            }
        }
    }

    private fun showEditSuccessPopUp() {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        dialogBuilder.setMessage("Edit Successful!")
        dialogBuilder.setPositiveButton("OK") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }


    private fun initUi() {
        with(binding) {
            etNameInput.setText(user?.name)
        }
        with(binding) {
            etPasswordInput.setText(user?.password)
        }
    }

}
