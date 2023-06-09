package com.example.makanapa.view.camera

import NodeRecipeResponse
import NodeRecipeResponseItem
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.makanapa.R
import com.example.makanapa.api.FlaskApiConfig
import com.example.makanapa.api.NodeApiConfig

import com.example.makanapa.databinding.ActivityCameraMainBinding
import com.example.makanapa.model.FlaskImageResponse
import com.example.makanapa.sharedpreference.SharedPreferencesManager
import com.example.makanapa.view.foodlist.FoodListActivity
import com.example.makanapa.view.welcome.WelcomeActivity
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody

import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.io.File
import java.lang.Exception


class CameraMainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraMainBinding
    private lateinit var sharedPreferenceManager: SharedPreferencesManager
    private var getFile: File? = null
    private lateinit var viewModel: CameraMainViewModel
    private lateinit var viewModelX : CameraMainXViewModel


    companion object {
        const val CAMERA_X_RESULT = 200

        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferenceManager = SharedPreferencesManager(this)
        viewModel = ViewModelProvider(this)[CameraMainViewModel::class.java]
        viewModelX = ViewModelProvider(this)[CameraMainXViewModel::class.java]


        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding.btnCamerax.setOnClickListener { startCameraX() }
        binding.buttonAdd.setOnClickListener {
            showLoading(true)
            uploadImage()
        }
        binding.btnGalery.setOnClickListener { startGallery() }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraXActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }


    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg = result.data?.data as Uri
            selectedImg.let { uri ->
                val myFile = uriToFile(uri, this@CameraMainActivity)
                getFile = myFile
                binding.ivImagePreview.setImageURI(uri)
            }
        }
    }


    private val launcherIntentCameraX =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == CAMERA_X_RESULT) {
                val myFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    it.data?.getSerializableExtra("picture", File::class.java)
                } else {
                    @Suppress("DEPRECATION")
                    it.data?.getSerializableExtra("picture")
                } as? File

                myFile?.let { file ->
                    getFile = file
                    binding.ivImagePreview.setImageBitmap(BitmapFactory.decodeFile(file.path))
                }
            }
        }


    private fun uploadImage() {

        if (getFile != null) {

            val file = reduceFileImage(getFile as File)
            val requestImageFile = file.asRequestBody("image/*".toMediaType())
            val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
                "image",
                file.name,
                requestImageFile
            )

            val base64Img = convertImageToBase64(file)
            FlaskApiConfig.getApiService().postImage(base64Img).enqueue(object : Callback<FlaskImageResponse>{
                override fun onResponse(
                    call: Call<FlaskImageResponse>,
                    response: Response<FlaskImageResponse>
                ) {
                    if(response.isSuccessful){
                        Log.d("TAG", response.body().toString())
                        NodeApiConfig.getApiService().getRecipe(response.body()?.prediction.toString().toLowerCase()).enqueue(object : Callback<List<NodeRecipeResponseItem>>{
                            override fun onResponse(
                                call: Call<List<NodeRecipeResponseItem>>,
                                response: Response<List<NodeRecipeResponseItem>>
                            ) {
                                if(response.isSuccessful){
                                    showLoading(false)
                                    val intent = Intent(this@CameraMainActivity, FoodListActivity::class.java)
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
                this@CameraMainActivity,
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        sharedPreferenceManager.clearToken()
        startActivity(Intent(this, WelcomeActivity::class.java))
        finish()
        true

        return super.onOptionsItemSelected(item)
    }

}


