package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentSignUpBinding
import com.example.capstoneproject.viewmodels.SignUpViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Questionnaire Intro Screen UI Interaction
 */
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        binding.signUpViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.alreadyRegistered.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
                view.findNavController().navigate(action)
            }
        })

        viewModel.emptyFields.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val snack = Snackbar.make(view, "Empty fields are not allowed", Snackbar.LENGTH_SHORT)
                snack.show()
            }
        })

        viewModel.passwordMismatch.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val snack = Snackbar.make(view, "Passwords do not match", Snackbar.LENGTH_SHORT)
                snack.show()
            }
        })

        viewModel.signUp.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = SignUpFragmentDirections.actionSignUpFragmentToQuestionnaireIntroFragment()
                view.findNavController().navigate(action)
            }
        })

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}