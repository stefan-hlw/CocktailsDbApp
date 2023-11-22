package com.example.cocktailsdbapp.ui.authorization

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.MainActivity
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentLoginBinding
import com.example.cocktailsdbapp.utils.makeLastNCharactersBold
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerListeners()
        binding.tvLogin.makeLastNCharactersBold(3)
        disableBackButton()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerListeners() {
        binding.etEmailInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Check if the input is valid (e.g., more than 3 characters)
                val isInputValid = (s?.length ?: 0) >= 3

                // Show/hide error message based on input validity
                if (!isInputValid) {
                    binding.etEmailInput.error = "Input must be at least 3 characters"
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
                val isInputValid = (s?.length ?: 0) >= 3

                // Show/hide error message based on input validity
                if (!isInputValid) {
                    binding.etPasswordInput.error = "Input must be at least 3 characters"
                } else {
                    binding.etPasswordInput.error = null // Clear error if input is valid
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })
        binding.btLogin.setOnClickListener {
            val email = binding.etEmailInput.text.toString()
            val password = binding.etPasswordInput.text.toString()
            if(email.length >= 3 && password.length >= 3 ) {
                val success = authViewModel.isUserInfoValid(email, password)
                if(success) {
                    (activity as MainActivity).currentUser = binding.etEmailInput.text.toString()
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
        dialogBuilder.setMessage("Login Successful!")
        dialogBuilder.setPositiveButton("Ok"
        ) { _, _ ->
            findNavController().navigate(R.id.action_loginFragment_to_cocktailsFragment)
        }
        val dialog = dialogBuilder.create()
        dialog.show()
    }

    private fun disableBackButton() {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    // this disables the Android native back button
                }
            }
        )
    }

}
