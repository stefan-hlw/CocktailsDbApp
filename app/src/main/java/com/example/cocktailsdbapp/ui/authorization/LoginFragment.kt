package com.example.cocktailsdbapp.ui.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentLoginBinding
import com.example.cocktailsdbapp.ui.BaseFragment
import com.example.cocktailsdbapp.utils.Constants
import com.example.cocktailsdbapp.utils.makeLastNCharactersBold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val authViewModel: AuthViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
        binding.tvLogin.makeLastNCharactersBold(Constants.LOGIN_BOLD_LETTER_AMOUNT)
        communicator.disableBackButton()
    }

    private fun registerListeners() {
        binding.etEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the input is valid (e.g., more than 3 characters)
                val isInputValid = (s?.length ?: 0) >= Constants.VALIDATION_MINIMUM_CHARACTERS

                // Show/hide error message based on input validity
                if (!isInputValid) {
                    binding.etEmailInput.error = getString(R.string.input_validation_error)
                } else {
                    binding.etEmailInput.error = null // Clear error if input is valid
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.etPasswordInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the input is valid (e.g., more than 3 characters)
                val isInputValid = (s?.length ?: 0) >= Constants.VALIDATION_MINIMUM_CHARACTERS

                // Show/hide error message based on input validity
                if (!isInputValid) {
                    binding.etPasswordInput.error = getString(R.string.input_validation_error)
                } else {
                    binding.etPasswordInput.error = null // Clear error if input is valid
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.btLogin.setOnClickListener {
            val email = binding.etEmailInput.text.toString()
            val password = binding.etPasswordInput.text.toString()
            if(email.length >= Constants.VALIDATION_MINIMUM_CHARACTERS && password.length >= Constants.VALIDATION_MINIMUM_CHARACTERS ) {
                val success = authViewModel.isUserInfoValid(email, password)
                if(success) {
                    communicator.setCurrentLoggedInUser(binding.etEmailInput.text.toString())
                    showLoginPopUp()
                }
            }
        }

        binding.btRegister.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun showLoginPopUp() {
        val dialogBuilder = AlertDialog.Builder(this.requireContext())
        dialogBuilder.setMessage(getString(R.string.login_success_message))
        dialogBuilder.setPositiveButton(R.string.ok
        ) { _, _ ->
            findNavController().navigate(R.id.action_loginFragment_to_cocktailsFragment)
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

}
