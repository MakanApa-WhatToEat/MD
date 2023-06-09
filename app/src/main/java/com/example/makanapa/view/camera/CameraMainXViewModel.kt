package com.example.makanapa.view.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makanapa.api.FlaskApiConfig
import com.example.makanapa.model.FlaskImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CameraMainXViewModel : ViewModel() {

    private val _addUploadImageResponse  = MutableLiveData<FlaskImageResponse>()
    val addUploadImageResponse : LiveData<FlaskImageResponse> = _addUploadImageResponse




    fun uploadImage(flaskImageBody: String){
        FlaskApiConfig.getApiService().postImage(flaskImageBody).enqueue(object : Callback<FlaskImageResponse>{
            override fun onResponse(
                call: Call<FlaskImageResponse>,
                response: Response<FlaskImageResponse>
            ) {
                if(response.isSuccessful){
                    Log.d("TAG", "berhasil : ${response.body().toString()}")
                    _addUploadImageResponse.postValue(response.body())
                }else{
                    Log.d("TAG", "failed 1 : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<FlaskImageResponse>, t: Throwable) {
                    Log.d("TAG", "failed 2 : ${t.message.toString()}")
            }

        })
    }




}