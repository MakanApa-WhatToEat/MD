package com.example.makanapa.view.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.makanapa.R
import com.example.makanapa.api.ApiConfig
import com.example.makanapa.api.LoginBody
import com.example.makanapa.databinding.ActivityLoginBinding
import com.example.makanapa.model.LoginResponse
import com.example.makanapa.sharedpreference.SharedPreferencesManager
import com.example.makanapa.view.camera.CameraMainActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isEmailValid = false
    private var isPasswordValid = false
    private lateinit var sharedPreferencesManager: SharedPreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        validateButtonLogin()
        sharedPreferencesManager = SharedPreferencesManager(this)

        binding.etLoginEmail.addTextChangedListener(createEmailTextWatcher())
        binding.etLoginPassword.addTextChangedListener(createPasswordTextWatcher())
        setPasswordVisibility()
        supportActionBar?.title = "Login"
    }

    private fun validateButtonLogin() {
        binding.btnLoginLog.setOnClickListener {
            showLoading(true)
           if(isEmailValid && isPasswordValid){
               val email = binding.etLoginEmail.text.toString().trim()
               val password = binding.etLoginPassword.text.toString().trim()

               ApiConfig.getApiService().postLogin(LoginBody(email, password)).enqueue(object :
                   Callback<LoginResponse>{
                   override fun onResponse(
                       call: Call<LoginResponse>,
                       response: Response<LoginResponse>
                   ) {
                       if(response.isSuccessful){
                           Log.d("TAG", response.body().toString())
                           val response = response.body()
                          if(response?.message == "Login Berhasil!"){
                              //success
                                showLoading(false)
                                sharedPreferencesManager.saveToken(response.data.accessToken, response.data.username, response.data.email)
                                startActivity(Intent(applicationContext, CameraMainActivity::class.java))
                                finish()
                          }else{
                                showLoading(false)
                              Toast.makeText(this@LoginActivity, "Login Gagal. Coba lagi!", Toast.LENGTH_SHORT).show()
                          }
                       }else{
                                showLoading(false)
                              Log.d("TAG", response.message())
                              Toast.makeText(this@LoginActivity, "Login Gagal. Coba lagi!", Toast.LENGTH_SHORT).show()

                       }
                   }

                   override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                              showLoading(false)
                              Log.d("TAG", t.message.toString())
                              Toast.makeText(this@LoginActivity, "Login Gagal. Coba lagi!", Toast.LENGTH_SHORT).show()
                   }

               })


           }else{
               showLoading(false)
               Toast.makeText(this@LoginActivity, "Pastikan semua input telah terisi dengan benar", Toast.LENGTH_SHORT).show()

           }
        }
    }

    private fun createEmailTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val emailText = s.toString().trim()
         if (emailText.isEmpty()) {
                binding.tvLoginUsernameError.text = "Username tidak boleh kosong"
                isEmailValid = false
            } else {
                 binding.tvLoginUsernameError.text = ""
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
                binding.tvLoginPasswordError.text = "Password harus lebih dari 8 karakter"
                isPasswordValid = false
            } else if (passText.isEmpty()) {
                binding.tvLoginPasswordError.text = "Password tidak boleh kosong"
                isPasswordValid = false
            } else {
                binding.tvLoginPasswordError.text = ""
                isPasswordValid = true
            }
        }
    }

    private fun showLoading(state : Boolean){
        if(state){
            binding.pbProgressBar.visibility = View.VISIBLE
        }else{
            binding.pbProgressBar.visibility = View.GONE
        }
    }

    private fun setPasswordVisibility() {
        var isVisible = false
        binding.btnTogglePassword.setOnClickListener {
            isVisible = !isVisible

            if (isVisible) {
                binding.btnTogglePassword.setImageResource(R.drawable.baseline_visibility_off_24)
                binding.etLoginPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.btnTogglePassword.setImageResource(R.drawable.visibility_grey)
                binding.etLoginPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.etLoginPassword.setSelection(binding.etLoginPassword.text.length)
        }
    }



}



