package com.epitech.cashmanager.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.epitech.cashmanager.MainActivity

import com.epitech.cashmanager.R
import com.epitech.cashmanager.services.ApiService

class LoginFragment : Fragment() {

    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginViewModel = ViewModelProvider(this).get(LoginViewModel::class.java)

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailInput: EditText = view.findViewById(R.id.input_email)
        val passwordInput: EditText = view.findViewById(R.id.input_password)

        view.findViewById<TextView>(R.id.button_register).setOnClickListener {
            findNavController().navigate(R.id.action_login_register)
        }
        view.findViewById<Button>(R.id.button_login).setOnClickListener { login() }
        emailInput.doAfterTextChanged {
            loginViewModel.email = it.toString()
        }
        passwordInput.doAfterTextChanged {
            loginViewModel.password = it.toString()
        }
        loginViewModel.error.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                emailInput.error = resources.getString(it)
                passwordInput.error = resources.getString(it)
            }
        })
    }

    private fun login() {
        loginViewModel.login().observe(viewLifecycleOwner, Observer {
            if (it) {
                val intent = Intent(activity, MainActivity::class.java)

                startActivity(intent)
            }
        })
    }
}
