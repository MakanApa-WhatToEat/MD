package com.example.makanapa.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(

	@field:SerializedName("data")
	val data: Test,

	@field:SerializedName("success")
	val success: Boolean,

	@field:SerializedName("status")
	val status: Int
)

data class Test(

	@field:SerializedName("display_url")
	val displayUrl: String,

	@field:SerializedName("image")
	val image: Image,

	@field:SerializedName("thumb")
	val thumb: Thumb,

	@field:SerializedName("delete_url")
	val deleteUrl: String,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("url_viewer")
	val urlViewer: String,

	@field:SerializedName("url")
	val url: String,

	@field:SerializedName("size")
	val size: Int,

	@field:SerializedName("width")
	val width: Int,

	@field:SerializedName("expiration")
	val expiration: Int,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("time")
	val time: Int,

	@field:SerializedName("height")
	val height: Int
)

data class Thumb(

	@field:SerializedName("extension")
	val extension: String,

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("mime")
	val mime: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("url")
	val url: String
)

data class Image(

	@field:SerializedName("extension")
	val extension: String,

	@field:SerializedName("filename")
	val filename: String,

	@field:SerializedName("mime")
	val mime: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("url")
	val url: String
)
