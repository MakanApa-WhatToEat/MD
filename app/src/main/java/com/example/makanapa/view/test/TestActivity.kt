package com.example.makanapa.view.test

import NodeRecipeResponseItem
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.makanapa.R
import com.example.makanapa.api.FlaskApiConfig
import com.example.makanapa.api.NodeApiConfig
import com.example.makanapa.databinding.ActivityTestBinding
import com.example.makanapa.model.FlaskImageResponse
import com.example.makanapa.view.camera.convertImageToBase64
import com.example.makanapa.view.camera.reduceFileImage
import com.example.makanapa.view.foodlist.FoodListActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.Locale

class TestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding
    private var getFile: File? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val stringFile = intent.getStringExtra("picture")
        Log.d("TAG", stringFile.toString())
        getFile = File(stringFile)
        binding.btnUpload.setOnClickListener {
            showLoading(true)
            uploadImage(getFile!!)
        }
        binding.ivImagePreview.setImageBitmap(BitmapFactory.decodeFile(stringFile))
    }



    private fun uploadImage(file : File) {

        if (file != null) {

            val file = reduceFileImage(file as File)
            val requestImageFile = file.asRequestBody("image/*".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            val base64Img = convertImageToBase64(file)
            FlaskApiConfig.getApiService().postImage(base64Img).enqueue(object :
                Callback<FlaskImageResponse> {
                override fun onResponse(
                    call: Call<FlaskImageResponse>,
                    response: Response<FlaskImageResponse>
                ) {
                    if(response.isSuccessful){
                        Log.d("TAG", response.body().toString())
                        NodeApiConfig.getApiService().getRecipe(response.body()?.prediction.toString()
                            .lowercase(Locale.getDefault())).enqueue(object :
                            Callback<List<NodeRecipeResponseItem>> {
                            override fun onResponse(
                                call: Call<List<NodeRecipeResponseItem>>,
                                response: Response<List<NodeRecipeResponseItem>>
                            ) {
                                if(response.isSuccessful){
                                    showLoading(false)
                                    val intent = Intent(this@TestActivity, FoodListActivity::class.java)
                                    Log.d("TAG", "from camera data response: ${response.body()}")
                                    val test = ArrayList(response.body())
                                    Log.d("TAG", "from camera data test: $test")
                                    intent.putParcelableArrayListExtra("nodeFoodList", ArrayList(response.body()))
                                    startActivity(intent)
                                }else{
                                    Log.d("TAG", response.errorBody().toString())
                                }
                            }

                            override fun onFailure(
                                call: Call<List<NodeRecipeResponseItem>>,
                                t: Throwable
                            ) {
                                Log.d("TAG", t.message.toString())
                            }

                        })
                    }
                }

                override fun onFailure(call: Call<FlaskImageResponse>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })

        } else {
            showLoading(false)
            Toast.makeText(
                this@TestActivity,
                "Silakan masukkan berkas gambar terlebih dahulu.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }



    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbProgressBar.visibility = View.VISIBLE
        } else {
            binding.pbProgressBar.visibility = View.GONE
        }
    }
}