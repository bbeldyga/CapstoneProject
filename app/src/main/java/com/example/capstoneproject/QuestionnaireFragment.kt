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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentQuestionnaireBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(QuestionnaireViewModel::class.java)

        binding.submitButton.setOnClickListener {
            val action = QuestionnaireFragmentDirections.actionQuestionnaireFragmentToHomeFragment()
            view.findNavController().navigate(action)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}