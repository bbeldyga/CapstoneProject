package com.example.capstoneproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.databinding.FragmentLoginBinding

/**
 * Home Screen UI Interaction
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        binding.feedButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToFeedFragment()
            view.findNavController().navigate(action)
        }

        binding.settingsButton.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
            view.findNavController().navigate(action)
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}