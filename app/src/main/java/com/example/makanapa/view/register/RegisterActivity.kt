package com.example.makanapa.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast

import com.example.makanapa.R
import com.example.makanapa.api.ApiConfig
import com.example.makanapa.api.AuthBody
import com.example.makanapa.databinding.ActivityRegisterBinding
import com.example.makanapa.model.RegisterResponse
import com.example.makanapa.view.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private var isEmailValid = false
    private var isPasswordValid = false
    private var isUsernameValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)




        validateButtonLogin()
        setPasswordVisibility()

        binding.etRegisterEmail.addTextChangedListener(createEmailTextWatcher())
        binding.etRegisterPassword.addTextChangedListener(createPasswordTextWatcher())
        binding.etRegisterName.addTextChangedListener(createUsernameTextWatcher())
        supportActionBar?.hide()

        binding.tvMoveRegister.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
        }
    }

    private fun validateButtonLogin() {
        binding.btnRegisterReg.setOnClickListener {
            if(isEmailValid && isPasswordValid && isUsernameValid){
                showLoading(true)
                val name = binding.etRegisterName.text.toString().trim()
                val email = binding.etRegisterEmail.text.toString().trim()
                val password = binding.etRegisterPassword.text.toString().trim()

                ApiConfig.getApiService().postRegister(AuthBody(name, email, password)).enqueue(object :
                    Callback<RegisterResponse> {
                    override fun onResponse(
                        call: Call<RegisterResponse>,
                        response: Response<RegisterResponse>
                    ) {
                        if(response.isSuccessful){
                            showLoading(false)
                            Log.d("TAG", "Error t : ${response.body()}")
                            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                            finish()


                        }else{
                            showLoading(false)
                            Log.d("TAG", "Error m : ${response.message()}")
                            Toast.makeText(applicationContext, "Sign up Gagal. Coba lagi", Toast.LENGTH_SHORT).show()

                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        showLoading(false)
                        Toast.makeText(applicationContext, "Sign up Gagal. Coba lagi", Toast.LENGTH_SHORT).show()
                        Log.d("TAG", "Error t : ${t.message.toString()}")
                    }

                })


            }else{
                Toast.makeText(this, "Pastikan semua input telah terisi dengan benar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun createEmailTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val emailText = s.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches() && emailText.isNotEmpty()) {
                binding.tvEmailError.text = "Alamat Email tidak valid"
                isEmailValid = false
            } else if (emailText.isEmpty()) {
                binding.tvEmailError.text = "Email tidak boleh kosong"
                isEmailValid = false
            } else {
                binding.tvEmailError.text = ""
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
                binding.tvPasswordError.text = "Password harus lebih dari 8 karakter"
                isPasswordValid = false
            } else if (passText.isEmpty()) {
                binding.tvPasswordError.text = "Password tidak boleh kosong"
                isPasswordValid = false
            } else {
                binding.tvPasswordError.text = ""
                isPasswordValid = true
            }
        }
    }

    private fun createUsernameTextWatcher() = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

        }

        override fun afterTextChanged(s: Editable?) {
            val usernameText = s.toString().trim()
            if(usernameText.isEmpty()){
                binding.tvUsernameError.text = "Username tidak boleh kosong"
                isUsernameValid = false
            }else{
                binding.tvUsernameError.text = ""
                isUsernameValid = true
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
                binding.etRegisterPassword.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.btnTogglePassword.setImageResource(R.drawable.visibility_grey)
                binding.etRegisterPassword.transformationMethod = PasswordTransformationMethod.getInstance()
            }
            binding.etRegisterPassword.setSelection(binding.etRegisterPassword.text.length)
        }
    }




}