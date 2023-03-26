package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentQuestionnaireIntroBinding
import com.example.capstoneproject.viewmodels.QuestionnaireIntroViewModel

/**
 * Questionnaire Intro Screen UI Interaction
 */
class QuestionnaireIntroFragment : Fragment() {

    private var _binding: FragmentQuestionnaireIntroBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: QuestionnaireIntroViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentQuestionnaireIntroBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[QuestionnaireIntroViewModel::class.java]

        binding.skipButton.setOnClickListener {
            val action = QuestionnaireIntroFragmentDirections.actionQuestionnaireIntroFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
        binding.proceedButton.setOnClickListener{
            val action = QuestionnaireIntroFragmentDirections.actionQuestionnaireIntroFragmentToQuestionnaireFragment()
            view.findNavController().navigate(action)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}