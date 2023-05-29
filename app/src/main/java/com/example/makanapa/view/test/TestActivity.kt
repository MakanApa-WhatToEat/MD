package com.example.makanapa.view.test

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.makanapa.R
import com.example.makanapa.databinding.ActivityTestBinding
import com.example.makanapa.sharedpreference.SharedPreferencesManager
import com.example.makanapa.view.welcome.WelcomeActivity

class TestActivity : AppCompatActivity() {

    private lateinit var binding : ActivityTestBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferencesManager = SharedPreferencesManager(this)
        val name = sharedPreferencesManager.getUsername()
        binding.tvTest.text = "Halo $name!"

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