package com.epitech.cashmanager.ui.auth.register

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epitech.cashmanager.R
import com.epitech.cashmanager.models.User
import com.epitech.cashmanager.services.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {
    private var apiService: ApiService = ApiService()

    var email: String = ""
    var password: String = ""
    var passwordConfirmation = ""

    val error = MutableLiveData<Int>(null)

    val loading = MutableLiveData(false)

    private fun checkPasswords(): Boolean {
        return if (password == passwordConfirmation) {
            return true
        } else {
            error.apply { value = R.string.register_error_passwords_mismatch }
            false
        }
    }

    fun register(): MutableLiveData<Boolean> {
        val state = MutableLiveData(false)
        val user = User(null, email, password)
        val jsonUser = Gson().toJson(user)

        if (!checkPasswords())
            return state
        loading.apply { value = true }
        apiService.user.register(jsonUser)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    registerError(call, null, state, t)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    registerSuccess(call, response, state)
                }
            })
        return state
    }

    private fun registerSuccess(call: Call<User>, response: Response<User>, state: MutableLiveData<Boolean>) {
        if (!response.isSuccessful) {
            registerError(call, response, state, null)
        } else {
            state.apply { value = true }
        }
    }

    private fun registerError(call: Call<User>, response: Response<User>?, state: MutableLiveData<Boolean>, t: Throwable?) {
        if (t != null) {
            error.apply { value = R.string.error_server_unreachable }
            loading.apply { value = false }
            Log.e(this.javaClass.name, "Error [register]:  ${t.localizedMessage}")
        } else {
            error.apply { value = R.string.register_error_email_already_taken }
        }
    }
}