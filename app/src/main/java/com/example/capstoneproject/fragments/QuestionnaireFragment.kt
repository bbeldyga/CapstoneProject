package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    private val action = QuestionnaireFragmentDirections.actionQuestionnaireFragmentToHomeFragment()
    private val questionnaireData = mutableListOf("General", "Technology", "Entertainment", "Sports", "Business", "Health", "Science")
    private var userNewsPrefs = mutableListOf(3, 3, 3, 3, 3, 3, 3)
    private var questionnaireCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        val view = binding.root

        appContainer = (requireContext().applicationContext as News4You).appContainer
        viewModelFactory = QuestionnaireViewModel.provideFactory(appContainer.userPreferencesDAO)
        viewModel = ViewModelProvider(this, viewModelFactory)[QuestionnaireViewModel::class.java]

        binding.topicText.text = questionnaireData[questionnaireCount]

        binding.veryDisinterestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount++] = 1
                showNextQuestion()
            } else {
                viewModel.saveResults(userNewsPrefs)
                view.findNavController().navigate(action)
            }
        }

        binding.disinterestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount++] = 2
                showNextQuestion()
            } else {
                viewModel.saveResults(userNewsPrefs)
                view.findNavController().navigate(action)
            }
        }

        binding.noPreferenceButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount++] = 3
                showNextQuestion()
            } else {
                viewModel.saveResults(userNewsPrefs)
                view.findNavController().navigate(action)
            }
        }

        binding.interestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount++] = 4
                showNextQuestion()
            } else {
                viewModel.saveResults(userNewsPrefs)
                view.findNavController().navigate(action)
            }
        }

        binding.veryInterestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount++] = 5
                showNextQuestion()
            } else {
                viewModel.saveResults(userNewsPrefs)
                view.findNavController().navigate(action)
            }
        }

        binding.skipButton.setOnClickListener {
            view.findNavController().navigate(action)
        }

        return view
    }

    private fun showNextQuestion() {
        if (questionnaireCount != 7) {
            binding.topicText.text = questionnaireData[questionnaireCount]
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}