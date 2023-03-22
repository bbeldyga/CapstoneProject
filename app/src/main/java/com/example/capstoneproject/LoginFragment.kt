package com.example.capstoneproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.capstoneproject.databinding.FragmentLoginBinding

/**
 * Login Screen UI Interaction
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]

        //binding.loginButton.setOnClickListener {
            //if a first time user, go to questionnaire, otherwise will go right to home screen, add this in later once we have user info more set up
            //val action = LoginFragmentDirections.actionLoginFragmentToQuestionnaireIntroFragment()
            //view.findNavController().navigate(action)
        //}
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}