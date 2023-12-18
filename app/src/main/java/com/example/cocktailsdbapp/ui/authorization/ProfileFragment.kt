package com.example.cocktailsdbapp.ui.authorization

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentProfileBinding
import com.example.cocktailsdbapp.model.User
import com.example.cocktailsdbapp.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment: BaseFragment<FragmentProfileBinding>(FragmentProfileBinding::inflate) {

    private val authViewModel: AuthViewModel by viewModels()

    private var user: User? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        setListeners()
    }

    override fun onStart() {
        super.onStart()
        communicator.apply {
            getCurrentLoggedInUser()?.let { authViewModel.getUserData(it) }
            showSearchIconView(true)
            showSearchInputView(false)
            showFilterView(true)
        }
    }

    private fun setListeners() {
        with(binding) {
            btLogout.setOnClickListener {
                communicator.setCurrentLoggedInUser(null)
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
        dialogBuilder.setMessage(getString(R.string.edit_success_message))
        dialogBuilder.setPositiveButton(getString(R.string.ok)) { dialog, _ ->
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
