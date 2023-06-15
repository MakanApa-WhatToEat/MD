package com.example.makanapa.view.userpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.makanapa.databinding.ActivityUserPageBinding
import com.example.makanapa.sharedpreference.SharedPreferencesManager
import com.example.makanapa.view.login.LoginActivity
import com.example.makanapa.view.welcome.WelcomeActivity

class UserPageActivity : AppCompatActivity() {

    private lateinit var binding : ActivityUserPageBinding
    private lateinit var sp : SharedPreferencesManager
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityUserPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sp = SharedPreferencesManager(this)

        binding.tvAvatarName.text = sp.getUsername()
        binding.btnLogOut.setOnClickListener {
            sp.clearToken()
            val intent = Intent(this@UserPageActivity, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            startActivity(intent)
            finish()
        }

    }
}