package com.epitech.cashmanager.ui.auth.register

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController

import com.epitech.cashmanager.R

class RegisterFragment : Fragment() {

    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        registerViewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput: EditText = view.findViewById(R.id.input_email)
        val passwordInput: EditText = view.findViewById(R.id.input_password)
        val passwordConfirmationInput: EditText = view.findViewById(R.id.input_password_confirmation)

        emailInput.doAfterTextChanged { registerViewModel.email = it.toString() }
        passwordInput.doAfterTextChanged { registerViewModel.password = it.toString() }
        passwordConfirmationInput.doAfterTextChanged { registerViewModel.passwordConfirmation = it.toString() }

        view.findViewById<Button>(R.id.button_register).setOnClickListener { register() }
        view.findViewById<TextView>(R.id.button_login).setOnClickListener {
            findNavController().navigate(R.id.action_register_login)
        }

        registerViewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                when (it) {
                    R.string.register_error_passwords_mismatch -> {
                        passwordInput.error = resources.getString(it)
                        passwordConfirmationInput.error = resources.getString(it)
                    }
                    R.string.register_error_email_already_taken -> emailInput.error = resources.getString(it)
                    R.string.error_server_unreachable -> {
                        passwordInput.error = resources.getString(it)
                        passwordConfirmationInput.error = resources.getString(it)
                        emailInput.error = resources.getString(it)
                    }
                }
            } else {
                passwordInput.error = null
                passwordConfirmationInput.error = null
                emailInput.error = null
            }
        })
    }

    private fun register() {
        registerViewModel.register().observe(viewLifecycleOwner, Observer {
            if (it)
                findNavController().navigate(R.id.action_register_login)
            else {
                Log.d(this.javaClass.name, "error")
            }
        })
    }
}
