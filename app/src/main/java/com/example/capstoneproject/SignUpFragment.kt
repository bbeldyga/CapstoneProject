package com.example.capstoneproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.android.material.snackbar.Snackbar

/**
 * Questionnaire Intro Screen UI Interaction
 */
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var viewModel: SignUpViewModel




    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SignUpViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()
        
        binding.alreadyRegisteredText.setOnClickListener {
            val action = SignUpFragmentDirections.actionSignUpFragmentToSignInFragment()
            view.findNavController().navigate(action)
        }
        binding.signUpButton.setOnClickListener{
            val email = binding.emailInput.text.toString()
            val pass = binding.passInput.text.toString()
            val retypePass = binding.retypePassInput.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty() && retypePass.isNotEmpty()) {
                if(pass == retypePass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val action = SignUpFragmentDirections.actionSignUpFragmentToQuestionnaireIntroFragment()
                            view.findNavController().navigate(action)
                        }
                        else {
                            val snack = Snackbar.make(view, it.exception.toString(), Snackbar.LENGTH_SHORT)
                            snack.show()
                        }
                    }

                }
                else {
                    val snack = Snackbar.make(it, "Passwords do not match", Snackbar.LENGTH_SHORT)
                    snack.show()
                }
            }
            else {
                val snack = Snackbar.make(it, "Empty fields are not allowed", Snackbar.LENGTH_SHORT)
                snack.show()
            }




        }
        return view
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}