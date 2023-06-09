package com.example.makanapa.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.makanapa.api.LoginBody
import com.example.makanapa.databinding.ActivityLoginBinding
import com.example.makanapa.sharedpreference.SharedPreferencesManager
import com.example.makanapa.view.camera.CameraMainActivity
import com.example.makanapa.view.test.TestActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isEmailValid = false
    private var isPasswordValid = false
    private lateinit var viewModel: LoginViewModel
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()
        validateButtonLogin()
        sharedPreferencesManager = SharedPreferencesManager(this)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        binding.etLoginEmail.addTextChangedListener(createEmailTextWatcher())
        binding.etLoginPassword.addTextChangedListener(createPasswordTextWatcher())
    }

    private fun validateButtonLogin() {
        binding.btnLoginLog.setOnClickListener {
            showLoading(true)
           if(isEmailValid || isPasswordValid){
               val email = binding.etLoginEmail.text.toString().trim()
               val password = binding.etLoginPassword.text.toString().trim()
               Log.d("TAG", "$email - $password")
               viewModel.postLogin(LoginBody(email, password))
               viewModel.loginResponse.observe(this){
                   if(it.message == "Login Berhasil!"){
                       showLoading(false)
                       sharedPreferencesManager.saveToken(it.data.accessToken, it.data.username, it.data.email)
                       startActivity(Intent(this, CameraMainActivity::class.java))
                       finish()
                   }else{
                       showLoading(false)
                       Toast.makeText(this, "Login Gagal", Toast.LENGTH_SHORT).show()
                   }
               }
           }
        }
    }

    private fun createEmailTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val emailText = s.toString().trim()
         if (emailText.isEmpty()) {
                binding.etLoginEmail.error = "Email can't be empty"
                isEmailValid = false
            } else {
                isEmailValid = true
            }
        }
    }

    private fun createPasswordTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val passText = s.toString().trim()
            if (passText.length in 1..7) {
                binding.etLoginPassword.error = "Password can't be less than 8 chars"
                isPasswordValid = false
            } else if (passText.isEmpty()) {
                binding.etLoginPassword.error = "Password can't be empty"
                isPasswordValid = false
            } else {
                isPasswordValid = true
            }
        }
    }


    private fun playAnimation(){
        val login_title = ObjectAnimator.ofFloat(binding.tvLoginTitle, View.ALPHA, 1f).setDuration(500)
        val email_text = ObjectAnimator.ofFloat(binding.etLoginEmail, View.ALPHA, 1f).setDuration(500)
        val pass_text = ObjectAnimator.ofFloat(binding.etLoginPassword, View.ALPHA, 1f).setDuration(500)
        val login_bottom = ObjectAnimator.ofFloat(binding.btnLoginLog, View.ALPHA, 1f).setDuration(500)

        val together = AnimatorSet().apply {
            playTogether(email_text, pass_text)
        }

        AnimatorSet().apply{
            playSequentially(login_title, together, login_bottom)
            start()
        }

    }

    private fun showLoading(state : Boolean){
        if(state){
            binding.pbProgressBar.visibility = View.VISIBLE
        }else{
            binding.pbProgressBar.visibility = View.GONE
        }
    }

}



