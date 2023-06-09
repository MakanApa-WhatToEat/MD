package com.example.makanapa.api

import NodeRecipeResponse
import NodeRecipeResponseItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface NodeApiService {

    @GET("search/{food}")
    fun getRecipe(
        @Path("food") food : String
    ) : Call<List<NodeRecipeResponseItem>>



}