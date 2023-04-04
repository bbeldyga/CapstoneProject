package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentSignInBinding
import com.example.capstoneproject.viewmodels.SignInViewModel
import com.google.android.material.snackbar.Snackbar

/**
 * Questionnaire Intro Screen UI Interaction
 */
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignInViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        binding.signInViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.notRegistered.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
                view.findNavController().navigate(action)
            }
        })

        viewModel.signInAttempt.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                view.findNavController().navigate(action)
            }
        })

        viewModel.emptyFields.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val snack = Snackbar.make(view, "Empty fields are not allowed", Snackbar.LENGTH_SHORT)
                snack.show()
            }
        })

        return view
    }

    override fun onStart() {
            super.onStart()

            if(viewModel.getCurrentUserValid()){
                val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                view?.findNavController()?.navigate(action)
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}