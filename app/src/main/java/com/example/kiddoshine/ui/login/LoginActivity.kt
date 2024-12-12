package com.example.kiddoshine.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.kiddoshine.MainActivity
import com.example.kiddoshine.api.repository.UserPreferences
import com.example.kiddoshine.databinding.ActivityLoginBinding
import com.example.kiddoshine.repository.ViewModelFactory
import com.example.kiddoshine.ui.register.RegisterActivity
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userPreferences: UserPreferences

    private val loginViewModel: LoginViewModel by viewModels {
        ViewModelFactory(UserPreferences.getInstance(applicationContext), applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        userPreferences = UserPreferences.getInstance(applicationContext)

        checkLoginStatus()
        setupActions()
        observeLoginStatus()
        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logoImageView, View.TRANSLATION_X, -60f, 60f).apply {
            duration = 1000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val logintext = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(300)
        val emailtext = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(300)
        val emaillayout = ObjectAnimator.ofFloat(binding.emailEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val pwtext = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(300)
        val pwlayout = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(300)
        val buttonlo = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(300)
        val tanya1 = ObjectAnimator.ofFloat(binding.txtAkun, View.ALPHA, 1f).setDuration(300)
        val tanya2 = ObjectAnimator.ofFloat(binding.toregister, View.ALPHA, 1f).setDuration(300)

        AnimatorSet().apply {
            playSequentially(logintext, emailtext, emaillayout, pwtext, pwlayout, buttonlo, tanya1, tanya2)
            start()
        }
    }

    private fun checkLoginStatus() {
        lifecycleScope.launch {
            userPreferences.getLoginStatus().collect { isLoggedIn ->
                if (isLoggedIn) {
                    navigateToInputDataAnak()
                }
            }
        }
    }

    private fun setupActions() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()

            loginViewModel.loginUser(email, password)
        }

        binding.toregister.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }

    private fun observeLoginStatus() {
        lifecycleScope.launch {
            loginViewModel.loginStatus.collect { status ->
                when (status) {
                    is LoginStatus.Loading -> {

                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is LoginStatus.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, status.message, Toast.LENGTH_SHORT).show()
                        navigateToInputDataAnak()
                    }
                    is LoginStatus.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, status.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun navigateToInputDataAnak() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}