package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentSettingsBinding
import com.example.capstoneproject.viewmodels.SettingsViewModel
import com.google.firebase.auth.FirebaseAuth

/**
 * Settings Screen UI Interaction
 */
class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
        firebaseAuth = FirebaseAuth.getInstance()

        binding.test.text= firebaseAuth.currentUser?.email.toString()

        binding.homeButton.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }

        binding.questionnaireButton.setOnClickListener {
            val action = SettingsFragmentDirections.actionSettingsFragmentToQuestionnaireIntroFragment()
            view.findNavController().navigate(action)
        }

        binding.signOutButton.setOnClickListener {
            firebaseAuth.signOut()
            val action = SettingsFragmentDirections.actionSettingsFragmentToSignInFragment()
            view.findNavController().navigate(action)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}