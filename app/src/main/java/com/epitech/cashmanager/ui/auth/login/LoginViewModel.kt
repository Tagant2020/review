package com.epitech.cashmanager.ui.auth.login

import android.util.Log
import androidx.lifecycle.*
import com.epitech.cashmanager.R
import com.epitech.cashmanager.models.User
import com.epitech.cashmanager.services.ApiService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private var apiService: ApiService = ApiService()

    var email: String = ""
    var password: String = ""

    val error = MutableLiveData<Int>(null)

    val loading = MutableLiveData(false)

    fun login(): MutableLiveData<Boolean> {
        val state = MutableLiveData<Boolean>()
        val user = User(null, email, password)
        val jsonUser = Gson().toJson(user)

        state.apply { value = false }
        loading.apply { value = true }
        apiService.user.login(jsonUser)
            .enqueue(object : Callback<User> {
                override fun onFailure(call: Call<User>, t: Throwable) {
                    loginError(call, null, state, t)
                }

                override fun onResponse(call: Call<User>, response: Response<User>) {
                    loginSuccess(call, response, state)
                }
            })
        return state
    }

    private fun loginSuccess(call: Call<User>, response: Response<User>, state: MutableLiveData<Boolean>) {
        if (!response.isSuccessful) {
            loginError(call, response, state, null)
        } else {
            state.apply { value = true }
        }
    }

    private fun loginError(call: Call<User>, response: Response<User>?, state: MutableLiveData<Boolean>, t: Throwable?) {
        if (t != null) {
            error.apply { value = R.string.error_server_unreachable }
            loading.apply { value = false }
            Log.e(this.javaClass.name, "Error [login]:  ${t.localizedMessage}")
        } else {
            error.apply { value = R.string.login_error_invalid_email_password }

        }
    }
}