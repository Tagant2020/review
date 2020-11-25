package com.epitech.cashmanager.services.api

import com.epitech.cashmanager.models.User
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @GET("/user/{id}")
    fun read(@Path("id") id: Int): Call<User>

    @Headers("Content-Type: application/json")
    @POST("/user/register")
    fun register(@Body user: String): Call<User>

    @Headers("Content-Type: application/json")
    @POST("/user/login")
    fun login(@Body user: String): Call<User>

    @PUT("/user/{id}")
    fun update(@Path("id") id: Int, @Body user: String): Call<User>

    @DELETE("/user/{id}")
    fun delete(@Path("id") id: Int): Call<User>
}