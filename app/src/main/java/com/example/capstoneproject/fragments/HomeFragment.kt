package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentHomeBinding
import com.example.capstoneproject.viewmodels.HomeViewModel

/**
 * Home Screen UI Interaction
 */
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        binding.homeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.feed.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = HomeFragmentDirections.actionHomeFragmentToFeedFragment()
                view.findNavController().navigate(action)
            }
        })

        viewModel.settings.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = HomeFragmentDirections.actionHomeFragmentToSettingsFragment()
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