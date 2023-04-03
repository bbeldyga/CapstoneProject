package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.News4You
import com.example.capstoneproject.databinding.FragmentQuestionnaireBinding
import com.example.capstoneproject.dataobjects.AppContainer
import com.example.capstoneproject.viewmodels.QuestionnaireViewModel

/**
 * Questionnaire Screen UI Interaction
 */
class QuestionnaireFragment : Fragment() {

    private var _binding: FragmentQuestionnaireBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuestionnaireViewModel
    private lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var appContainer: AppContainer

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        val view = binding.root

        appContainer = (requireContext().applicationContext as News4You).appContainer
        viewModelFactory = QuestionnaireViewModel.provideFactory(appContainer.userPreferencesDAO)
        viewModel = ViewModelProvider(this, viewModelFactory)[QuestionnaireViewModel::class.java]

        binding.questionnaireViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.finished.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = QuestionnaireFragmentDirections.actionQuestionnaireFragmentToHomeFragment()
                view.findNavController().navigate(action)
            }
        })

        viewModel.skip.observe(viewLifecycleOwner, Observer<Boolean> {
            if (it) {
                val action = QuestionnaireFragmentDirections.actionQuestionnaireFragmentToHomeFragment()
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