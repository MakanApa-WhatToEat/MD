package com.example.makanapa.api

import com.example.makanapa.model.LoginResponse
import com.example.makanapa.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


interface ApiService {


    @POST("register")
    fun postRegister(
        @Body authBody : AuthBody
    ) : Call<RegisterResponse>

    @POST("login")
    fun postLogin(
        @Body authLoginBody: LoginBody
    ) : Call<LoginResponse>


}