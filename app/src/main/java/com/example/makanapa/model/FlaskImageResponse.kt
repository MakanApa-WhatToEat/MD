package com.example.makanapa.model

import com.google.gson.annotations.SerializedName

data class FlaskImageResponse(

	@field:SerializedName("prediction")
	val prediction: String
)
