package com.example.makanapa.view.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import android.view.View
import com.example.makanapa.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private var isEmailValid = false
    private var isPasswordValid = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        playAnimation()
        validateButtonLogin()

        binding.etLoginEmail.addTextChangedListener(createEmailTextWatcher())
        binding.etLoginPassword.addTextChangedListener(createPasswordTextWatcher())
    }

    private fun validateButtonLogin() {
        binding.btnLoginLog.setOnClickListener {
           if(isEmailValid || isPasswordValid){
               val email = binding.etLoginEmail.text.toString().trim()
               val password = binding.etLoginPassword.text.toString().trim()
               Log.d("TAG", "$email - $password")
           }
        }
    }

    private fun createEmailTextWatcher() = object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            val emailText = s.toString().trim()
            if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                binding.etLoginEmail.error = "Invalid Email address"
                isEmailValid = false
            } else if (emailText.isEmpty()) {
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

}



