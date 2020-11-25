package com.epitech.cashmanager.models

import com.google.gson.annotations.SerializedName

open class User(@SerializedName("id") var id: Number?,
                @SerializedName("username") var username: String,
                @SerializedName("password") var password: String? = null
) {}