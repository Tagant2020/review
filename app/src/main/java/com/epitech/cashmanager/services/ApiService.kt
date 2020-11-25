package com.epitech.cashmanager.services

import com.epitech.cashmanager.services.api.AuthenticationInterceptor
import com.epitech.cashmanager.services.api.UserApi
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiService {

    var user: UserApi

    init {
        val host = "86.213.63.181"
        val port = 8080
        val gson = GsonBuilder()
            .enableComplexMapKeySerialization()
            .serializeNulls()
            .setPrettyPrinting()
            .setLenient()
            .create()
        val retrofit = Retrofit.Builder()
            .client(httpClient())
            .baseUrl("http://$host:$port")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        user = retrofit.create(UserApi::class.java)
    }

    private fun httpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(AuthenticationInterceptor())
            .build()
    }
}