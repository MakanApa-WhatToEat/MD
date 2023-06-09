package com.example.makanapa.view.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.makanapa.R
import com.example.makanapa.databinding.ActivityTestBinding
import com.example.makanapa.sharedpreference.SharedPreferencesManager
import com.example.makanapa.view.welcome.WelcomeActivity

class TestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager


    companion object{
        const val IMAGE_URL = "image_url"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)

        val imageUrl = intent.getStringExtra("IMAGE_URL")

        Glide.with(this).load(imageUrl).into(binding.ivImage)

        logOut()

    }

    private fun logOut(){
        binding.btnTest.setOnClickListener{
            sharedPreferencesManager.clearToken()
            Intent(this@TestActivity, WelcomeActivity::class.java)
            finish()
        }
    }


}