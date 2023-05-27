package com.example.makanapa.view.register

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makanapa.api.ApiConfig
import com.example.makanapa.api.AuthBody
import com.example.makanapa.model.RegisterResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterVIewModel : ViewModel() {

    private val _registerResponse = MutableLiveData<RegisterResponse>()
    val registerResponse : LiveData<RegisterResponse> = _registerResponse

    fun postRegister(authBody : AuthBody, context : Context) {
            ApiConfig.getApiService().postRegister(authBody).enqueue(object : Callback<RegisterResponse>{
                override fun onResponse(
                    call: Call<RegisterResponse>,
                    response: Response<RegisterResponse>
                ) {
                    if(response.isSuccessful){
                        _registerResponse.postValue(response.body())
                        Log.d("TAG", response.body().toString())
                    }
                }
                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    Log.d("TAG", t.message.toString())
                    Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show()
                }

            })


    }

}
