package com.example.makanapa.api

import com.example.makanapa.model.FlaskImageResponse
import com.example.makanapa.model.LoginResponse
import com.example.makanapa.model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface FlaskApiService {


    @FormUrlEncoded
    @POST("result2")
    fun postImage(
        @Field("image_data") base64File: String
    ): Call<FlaskImageResponse>

}


