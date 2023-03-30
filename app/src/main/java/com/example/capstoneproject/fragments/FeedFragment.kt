package com.example.capstoneproject.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.capstoneproject.News4You
import com.example.capstoneproject.databinding.FragmentFeedBinding
import com.example.capstoneproject.dataobjects.AppContainer
import com.example.capstoneproject.viewmodels.FeedViewModel

/**
 * Feed Screen
 */
class FeedFragment : Fragment() {

    private var _binding: FragmentFeedBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FeedViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var appContainer: AppContainer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentFeedBinding.inflate(inflater, container, false)
        val view = binding.root

        appContainer = (requireContext().applicationContext as News4You).appContainer
        viewModelFactory = FeedViewModel.provideFactory(appContainer.newsAPI,
                                                        appContainer.userPreferencesDAO,
                                                        appContainer.firebaseAuth)
        viewModel = ViewModelProvider(this, viewModelFactory).get(FeedViewModel::class.java)

        binding.feedViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.imageValue.observe(viewLifecycleOwner, Observer<String> { imageValue ->
            if (imageValue != "") {
                binding.newsImage.load(imageValue)
            }
        })

        viewModel.urlValue.observe(viewLifecycleOwner, Observer<String> { urlValue ->
            if (urlValue != "") {
                binding.exploreButton.setOnClickListener {
                    val webpage = Uri.parse(urlValue)
                    val intent = Intent(Intent.ACTION_VIEW, webpage)
                    startActivity(intent)
                }
            }
        })

        binding.nextButton.setOnClickListener{
            viewModel.updateUI()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}