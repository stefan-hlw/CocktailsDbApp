package com.example.cocktailsdbapp.ui.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cocktailsdbapp.R
import com.example.cocktailsdbapp.databinding.FragmentRegistrationBinding
import com.example.cocktailsdbapp.model.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun registerListeners() {
        binding.btLogin.setOnClickListener {
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
        binding.btRegister.setOnClickListener {
            val email = binding.etEmailInput.text.toString()
            val name = binding.etNameInput.text.toString()
            val password = binding.etPasswordInput.text.toString()
            val success = authViewModel.saveUserData(User(name, email, password))
            if(success) {
                findNavController().navigate(R.id.action_registrationFragment_to_cocktailsFragment)
            }
        }

    }

}
