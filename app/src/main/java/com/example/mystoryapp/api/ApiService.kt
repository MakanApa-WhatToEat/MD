package com.example.mystoryapp.api

import com.example.mystoryapp.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

//    @FormUrlEncoded
    @POST("register")
    fun postRegister(
        @Body authBody : AuthBody
    ) : Call<RegisterResponse>

}