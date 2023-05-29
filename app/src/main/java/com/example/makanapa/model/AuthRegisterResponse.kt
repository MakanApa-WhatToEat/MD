package com.example.makanapa.model

import com.google.gson.annotations.SerializedName

data class AuthRegisterResponse(


	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("data")
	val data: Data
)

data class Data(

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("__v")
	val v: Int,

	@field:SerializedName("_id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String
)
