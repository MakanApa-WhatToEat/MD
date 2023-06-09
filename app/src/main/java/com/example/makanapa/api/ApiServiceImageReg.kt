package com.example.makanapa.api

import com.example.makanapa.model.ApiResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiServiceImageReg {

    @Multipart
    @POST("1/upload")
    fun uploadImage(
        @Part("expiration") expiration: RequestBody,
        @Part("key") key: RequestBody,
        @Part image: MultipartBody.Part
    ): Call<ApiResponse>

}