package com.example.capstoneproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.capstoneproject.databinding.FragmentSignInBinding
import com.example.capstoneproject.viewmodels.SignInViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

/**
 * Questionnaire Intro Screen UI Interaction
 */
class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignInViewModel
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[SignInViewModel::class.java]

        firebaseAuth = FirebaseAuth.getInstance()

        binding.notRegisteredText.setOnClickListener {
            val action =
                SignInFragmentDirections.actionSignInFragmentToSignUpFragment()
            view.findNavController().navigate(action)
        }

        binding.signInButton.setOnClickListener{
            val action = //REMOVE THIS
                SignInFragmentDirections.actionSignInFragmentToHomeFragment() //THIS
            view.findNavController().navigate(action) //AND THIS

            val email = binding.emailInput.text.toString()
            val pass = binding.passInput.text.toString()

            if(email.isNotEmpty() && pass.isNotEmpty()) {
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
                        view.findNavController().navigate(action)
                    }
                    else {
                        val snack = Snackbar.make(view, it.exception.toString(), Snackbar.LENGTH_SHORT)
                        snack.show()
                    }
                }
            } else {
                val snack = Snackbar.make(it, "Empty fields are not allowed", Snackbar.LENGTH_SHORT)
                snack.show()
            }
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        if(firebaseAuth.currentUser != null){
            val action = SignInFragmentDirections.actionSignInFragmentToHomeFragment()
            view?.findNavController()?.navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}