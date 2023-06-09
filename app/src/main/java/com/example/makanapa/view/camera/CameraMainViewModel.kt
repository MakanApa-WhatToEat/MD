package com.example.makanapa.view.camera

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.makanapa.api.ApiConfigImageReg
import com.example.makanapa.model.ApiResponse
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


//Change based on the api
class CameraMainViewModel : ViewModel() {

    private val _addUploadImageResponse  = MutableLiveData<ApiResponse>()
    val addUploadImageResponse : LiveData<ApiResponse> = _addUploadImageResponse

    val expiration = "600".toRequestBody("text/plain".toMediaType())
    val key = "8218c64b526093e0074ac415ac0892b2".toRequestBody("text/plain".toMediaType())

    fun uploadImage(image : MultipartBody.Part ){

       try{
           ApiConfigImageReg.getApiService().uploadImage(expiration, key, image).enqueue(object : Callback<ApiResponse>{
               override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                   if(response.isSuccessful){
                       Log.d("TAG", response.body().toString())
                       _addUploadImageResponse.postValue(response.body())
                   }else{
                       Log.d("TAG", response.message())
                   }
               }

               override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                   Log.d("TAG", t.message.toString())
               }

           })
       }catch (e : Exception){
           Log.d("TAG", e.message.toString())
       }
    }

}