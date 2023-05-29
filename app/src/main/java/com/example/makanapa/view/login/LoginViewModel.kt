package com.example.makanapa.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makanapa.api.ApiConfig
import com.example.makanapa.api.LoginBody
import com.example.makanapa.model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel : ViewModel() {

        private val _loginResponse = MutableLiveData<LoginResponse>()
        val loginResponse : LiveData<LoginResponse> = _loginResponse

        fun postLogin(loginBody: LoginBody){
                ApiConfig.getApiService().postLogin(loginBody).enqueue(object : Callback<LoginResponse>{
                        override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                        ) {
                                if(response.isSuccessful){
                                        _loginResponse.postValue(response.body())
                                        Log.d("TAG", response.body().toString())
                                }else{

                                        Log.d("TAG", response.message())
                                }
                        }

                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                Log.d("TAG", t.message.toString())

                        }

                })
        }
}