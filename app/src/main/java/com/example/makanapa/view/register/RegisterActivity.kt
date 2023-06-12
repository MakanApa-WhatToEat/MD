package com.example.makanapa.view.register

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.makanapa.R
import com.example.makanapa.api.AuthBody
import com.example.makanapa.databinding.ActivityRegisterBinding
import com.example.makanapa.view.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var viewModel : RegisterViewModel
    private var isEmailValid = false
    private var isPasswordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this)[RegisterViewModel::class.java]


        playAnimation()
        validateButtonLogin()
        setPasswordVisibility()

        binding.etRegisterEmail.addTextChangedListener(createEmailTextWatcher())
        binding.etRegisterPassword.addTextChangedListener(createPasswordTextWatcher())
    }

    private fun validateButtonLogin() {
        binding.btnRegisterReg.setOnClickListener {
            if(isEmailValid || isPasswordValid){
                showLoading(true)
                val name = binding.etRegisterName.text.toString().trim()
                val email = binding.etRegisterEmail.text.toString().trim()
                val password = binding.etRegisterPassword.text.toString().trim()
                viewModel.postRegister(AuthBody(name, email, password), this)
                viewModel.registerResponse.observe(this){
                            if(it != null){
                                showLoading(false)
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
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
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches() && emailText.isNotEmpty()) {
                binding.tvEmailError.text = "Invalid Email address"
                isEmailValid = false
            } else if (emailText.isEmpty()) {
                binding.tvEmailError.text = "Email can't be empty"
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
                binding.tvPasswordError.text = "Password can't be less than 8 characters"
                isPasswordValid = false
            } else if (passText.isEmpty()) {
                binding.tvPasswordError.text = "Password can't be empty"
                isPasswordValid = false
            } else {
                binding.tvPasswordError.text = ""
                isPasswordValid = true
            }
        }
    }
    private fun playAnimation(){

        val register_title = ObjectAnimator.ofFloat(binding.tvRegisterTitle, View.ALPHA, 1f).setDuration(500)
        val register_name = ObjectAnimator.ofFloat(binding.etRegisterName, View.ALPHA, 1f).setDuration(500)
        val register_email = ObjectAnimator.ofFloat(binding.etRegisterEmail, View.ALPHA, 1f).setDuration(500)
        val register_password = ObjectAnimator.ofFloat(binding.etRegisterPassword, View.ALPHA, 1f).setDuration(500)
        val register_button = ObjectAnimator.ofFloat(binding.btnRegisterReg, View.ALPHA, 1f).setDuration(500)

        val playTogether = AnimatorSet().apply{
            playTogether(register_name, register_email, register_password)
        }

        AnimatorSet().apply{
            playSequentially(register_title, playTogether, register_button)
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