package com.epitech.cashmanager.ui.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.epitech.cashmanager.R
import com.epitech.cashmanager.services.ApiService

import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
//        setSupportActionBar(toolbar)
    }
}
