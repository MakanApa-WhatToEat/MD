package com.example.makanapa.view.homesearch

import NodeRecipeResponseItem
import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.makanapa.R
import com.example.makanapa.adapter.FoodResultListAdapter
import com.example.makanapa.api.NodeApiConfig
import com.example.makanapa.databinding.ActivityHomeSearchBinding
import com.example.makanapa.sharedpreference.SharedPreferencesManager
import com.example.makanapa.view.camera.CameraXActivity
import com.example.makanapa.view.camera.uriToFile
import com.example.makanapa.view.foodlist.FoodDetailActivity
import com.example.makanapa.view.foodlist.FoodListActivity
import com.example.makanapa.view.test.TestActivity
import com.example.makanapa.view.welcome.WelcomeActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.util.Locale

class HomeSearchActivity : AppCompatActivity() {

    private var getFile: File? = null
    lateinit var binding: ActivityHomeSearchBinding
    private lateinit var adapter: FoodResultListAdapter
    private lateinit var sharedPreferenceManager: SharedPreferencesManager

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferenceManager = SharedPreferencesManager(this)
        supportActionBar?.title = "Home"

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        binding = ActivityHomeSearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnCamera.setOnClickListener {
            showOptionsDialog()
        }

        binding.etSearch.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUser()
                binding.ivSearchText.visibility = View.GONE
                Log.d("TAG", "event Clicked")
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

    }

    private fun showOptionsDialog() {
        val options = arrayOf("Gallery", "Camera")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
            .setItems(options) { dialog, which ->
                when (which) {
                    0 -> startGallery()
                    1 -> startCameraX()
                }
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
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

    private fun startCameraX() {
        Log.d("TAG", "Get Camera")
        val intent = Intent(this, CameraXActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
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
                val myFile = uriToFile(uri, this@HomeSearchActivity)
                getFile = myFile
//                    binding.ivImagePreview.setImageURI(uri)
                val intent = Intent(this, TestActivity::class.java)
                intent.putExtra("picture", myFile.path)
                startActivity(intent)
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
                    val intent = Intent(this, TestActivity::class.java)
                    intent.putExtra("picture", file.path)
                    startActivity(intent)

                }
            }
        }


    private fun searchUser() {
        val searchResult = binding.etSearch.text.toString()
        if (searchResult.isEmpty()) {
            return
        } else {
            showLoading(true)
            NodeApiConfig.getApiService().getRecipe(
                searchResult
                    .lowercase(Locale.getDefault())
            ).enqueue(object :
                Callback<List<NodeRecipeResponseItem>> {
                override fun onResponse(
                    call: Call<List<NodeRecipeResponseItem>>,
                    response: Response<List<NodeRecipeResponseItem>>
                ) {
                    if (response.isSuccessful) {
                        showLoading(false)
                        if(response.body() != null){
                            setAdapter(ArrayList(response.body()))
                        }else{
                            binding.ivSearchTextNoData.visibility = View.VISIBLE
                        }
                    } else {
                        showLoading(false)
                        Log.d("TAG", response.errorBody().toString())
                    }
                }

                override fun onFailure(
                    call: Call<List<NodeRecipeResponseItem>>,
                    t: Throwable
                ) {
                    showLoading(false)
                    Log.d("TAG", t.message.toString())
                }

            })
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.pbProgressBar.visibility = View.VISIBLE
        } else {
            binding.pbProgressBar.visibility = View.GONE
        }
    }


    private fun setAdapter(foodList: ArrayList<NodeRecipeResponseItem>) {
        adapter = FoodResultListAdapter(foodList)
        binding.rvFoodList.layoutManager = LinearLayoutManager(this@HomeSearchActivity)
        binding.rvFoodList.adapter = adapter
        adapter.setOnClickCallBack(object : FoodResultListAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: NodeRecipeResponseItem) {
                val intent = Intent(this@HomeSearchActivity, FoodDetailActivity::class.java)
                intent.putExtra(FoodDetailActivity.EXTRA_ID, data.id)
                intent.putExtra(FoodDetailActivity.EXTRA_MENU, data.menu)
                intent.putExtra(FoodDetailActivity.EXTRA_COOKING_TIME, data.cookingTime)
                intent.putExtra(FoodDetailActivity.EXTRA_KCAL, data.kcal.toString())
                intent.putExtra(FoodDetailActivity.EXTRA_RECIPE, data.recipe)
                intent.putStringArrayListExtra(
                    FoodDetailActivity.EXTRA_INGREDIENTS,
                    data.ingredients
                )
                startActivity(intent)
            }

        })
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

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }


}