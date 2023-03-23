package com.example.capstoneproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentQuestionnaireBinding

/**
 * Questionnaire Screen UI Interaction
 */
class QuestionnaireFragment : Fragment() {

    private var _binding: FragmentQuestionnaireBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: QuestionnaireViewModel
    private val action = QuestionnaireFragmentDirections.actionQuestionnaireFragmentToHomeFragment()


    private val questionnaireData = mutableListOf("Technology", "General", "Entertainment", "Sports", "Business", "Health", "Science")
    private var userNewsPrefs = mutableListOf(3, 3, 3, 3, 3, 3, 3)
    private var questionnaireCount = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(QuestionnaireViewModel::class.java)

        showNextQuestion()

        binding.veryDisinterestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount] = 1
                showNextQuestion()
            }
            else {
                view.findNavController().navigate(action)
            }

        }
        binding.disinterestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount] = 2
                showNextQuestion()
            }
            else {
                view.findNavController().navigate(action)
            }
        }
        binding.noPreferenceButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount] = 3
                showNextQuestion()
            }
            else {
                view.findNavController().navigate(action)
            }
        }
        binding.interestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount] = 4
                showNextQuestion()
            }
            else {
                view.findNavController().navigate(action)
            }
        }
        binding.veryInterestedButton.setOnClickListener{
            if (questionnaireCount < 7) {
                userNewsPrefs[questionnaireCount] = 5
                showNextQuestion()
            }
            else {
                view.findNavController().navigate(action)
            }
        }


        binding.skipButton.setOnClickListener {

            view.findNavController().navigate(action)
        }
        return view
    }

    private fun showNextQuestion() {
        binding.topicText.text = questionnaireData[questionnaireCount]
        if (questionnaireCount !=7) {
            questionnaireCount++
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}