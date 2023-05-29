package com.example.makanapa.model

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: RegisterData
)



data class RegisterData(

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("__v")
	val v: Int,



)

